package com.ace.android.supervise.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by ace on 2018/10/22.
 */

public class SnackbarUtil {

    private static Snackbar mSnackbar;

    private SnackbarUtil() {
        //no instance
    }

    public static void showSnackbar(View view, CharSequence text, int duration) {
        if (mSnackbar != null) {
            mSnackbar.setText(text);
        } else {
            mSnackbar = Snackbar.make(view, text, duration);
        }
        mSnackbar.show();
    }

    public static void destroy() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
        mSnackbar = null;
    }

}
