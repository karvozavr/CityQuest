package ru.spbau.mit.karvozavr.cityquest.ui.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class InterfaceUtils {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
