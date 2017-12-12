package ru.spbau.mit.karvozavr.cityquest.ui.util;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;

/**
 * Abstraction to provide Google sign in functionality for activity.
 */
public abstract class GoogleSignInActivity extends AppCompatActivity {

  protected static final int REQUEST_CODE_SIGN_IN = 0;

  protected GoogleSignInClient mGoogleSignInClient;

  /**
   * Start sign in activity.
   */
  protected void signIn() {
    mGoogleSignInClient = buildGoogleSignInClient();
    startActivityForResult(mGoogleSignInClient.getSignInIntent(), REQUEST_CODE_SIGN_IN);
  }

  /**
   * Sign out user and start sign in dialog.
   */
  protected void changeUser() {
    mGoogleSignInClient.signOut()
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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
      case REQUEST_CODE_SIGN_IN:
        // Called after user is signed in.
        if (resultCode == RESULT_OK) {
          // TODO
        } else {
          signIn();
        }
        break;
    }
  }
}
