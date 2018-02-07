package ru.spbau.mit.karvozavr.cityquest.ui.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import ru.spbau.mit.karvozavr.cityquest.quest.Quest;
import ru.spbau.mit.karvozavr.cityquest.quest.QuestController;
import ru.spbau.mit.karvozavr.cityquest.quest.ServiceProvider;
import ru.spbau.mit.karvozavr.cityquest.quest.UserProgress;
import ru.spbau.mit.karvozavr.cityquest.ui.QuestGalleryActivity;

/**
 * Abstraction to provide Google services functionality for activity.
 */
public abstract class GoogleServicesActivity extends AppCompatActivity {

    protected static final int REQUEST_CODE_SIGN_IN = 0;
    protected GoogleSignInClient googleSignInClient;
    protected DriveResourceClient driveResourceClient;

    /**
     * Start sign in activity.
     */
    protected void signIn() {
        googleSignInClient = buildGoogleSignInClient();
        startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
    }

    /**
     * Sign out user and start sign in dialog.
     */
    protected void changeUser() {
        googleSignInClient.signOut()
            .addOnCompleteListener(this, task -> signIn());
    }

    /**
     * Build a Google SignIn client.
     */
    protected GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions signInOptions =
            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Drive.SCOPE_APPFOLDER)
                .build();
        return GoogleSignIn.getClient(this, signInOptions);
    }

    protected String getUserId() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        return account.getId();
    }

    protected void getUserProgress(Quest quest) {
        ServiceProvider.getInternetAccess(this);
        final String fileName = Integer.toString(quest.info.id) + ".cqp";

        final Task<DriveFolder> appFolderTask =
            driveResourceClient.getAppFolder()
                .continueWithTask(task -> {
                    final Task<MetadataBuffer> metadataBufferTask =
                        driveResourceClient.queryChildren(task.getResult(), new Query.Builder()
                            .addFilter(Filters.eq(SearchableField.TITLE, fileName))
                            .build());

                    metadataBufferTask
                        .addOnSuccessListener(
                            metadataBuffer -> {
                                try {
                                    DriveFile userProgressFile = metadataBuffer.get(0).getDriveId().asDriveFile();
                                    Task<DriveContents> openFileTask =
                                        driveResourceClient.openFile(userProgressFile, DriveFile.MODE_READ_ONLY);

                                    openFileTask
                                        .continueWithTask(fileTask -> {
                                            DriveContents contents = fileTask.getResult();
                                            try (Scanner scanner = new Scanner(contents.getInputStream())) {
                                                //String str = scanner.nextLine();
                                                int progress = scanner.nextInt();
                                                boolean finished = scanner.nextBoolean();
                                                QuestController.setUserProgress(new UserProgress(quest.info.id, progress, finished));

                                                onUserProgressReceived();
                                            } catch (Exception e) {
                                                int i = 0;
                                            }

                                            return null;
                                        });
                                } catch (Exception e) {
                                    UserProgress userProgress = new UserProgress(quest.info.id, 0, false);
                                    updateProgress(quest, userProgress);
                                    QuestController.setUserProgress(userProgress);
                                }
                            })
                        .addOnFailureListener(
                            e -> Log.e("Drive", "Load failure"));
                    return null;
                });
    }

    protected void onUserProgressReceived() {

    }

    public void updateProgress(Quest quest, UserProgress newProgress) {
        ServiceProvider.getInternetAccess(this);

        final String fileName = Integer.toString(quest.info.id) + ".cqp";
        final Task<DriveFolder> appFolderTask = driveResourceClient.getAppFolder();
        final Task<DriveContents> createContentsTask = driveResourceClient.createContents();
        Tasks.whenAll(appFolderTask, createContentsTask)
            .continueWithTask(
                task -> {
                    DriveFolder parent = appFolderTask.getResult();
                    DriveContents contents = createContentsTask.getResult();
                    OutputStream outputStream = contents.getOutputStream();
                    try (PrintWriter writer = new PrintWriter(outputStream)) {
                        if (newProgress.progress == quest.numberOfSteps()) {
                            writer.println(-1);
                            writer.println(true);
                        } else {
                            writer.println(newProgress.progress);
                            writer.println(newProgress.finished);
                        }
                    }

                    MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
                        .setTitle(fileName)
                        .setMimeType("text/plain")
                        .build();

                    return driveResourceClient.createFile(parent, changeSet, contents);
                })
            .addOnSuccessListener(this,
                driveFile -> {
                    Intent intent;
                    // If we have just finished quest
                    if (newProgress.progress == quest.numberOfSteps()) {
                        intent = new Intent(GoogleServicesActivity.this, QuestGalleryActivity.class);
                    } else {
                        intent = getIntent();
                    }
                    finish();
                    startActivity(intent);
                })
            .addOnFailureListener(this,
                e -> updateProgress(quest, newProgress));
    }

    // Login success callback.
    public void onLoginSucceed() {
        // Build a drive resource client
        driveResourceClient =
            Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this));
    }

    // Login failure callback.
    public void onLoginFailed() {
        // Ask user to sign in again
        Toast.makeText(this, "Login failed. Please, sign in again.", Toast.LENGTH_LONG).show();
        signIn();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_SIGN_IN:
                // Called after user is signed in.
                if (resultCode == RESULT_OK) {
                    onLoginSucceed();
                } else {
                    onLoginFailed();
                }
                break;
        }
    }
}
