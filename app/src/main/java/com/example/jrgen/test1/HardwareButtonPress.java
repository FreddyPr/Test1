package com.example.jrgen.test1;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;

class HardwareButtonPress {

    private static MyListener listener;
    HardwareButtonPress() {
        listener = null;
    }

    interface MyListener {
        void yes();
        void no();
    }

    void setCustomObjectListener(MyListener pListener) {
        listener = pListener;
    }

    private static boolean showAlert(Activity activity, String title, String message, String buttonYes, String buttonNo) {
        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with function;
                        if (listener != null)
                            listener.yes();
                    }
                })
                .setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null)
                            listener.no();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return false;
    }

    boolean onKeyUp(int keyCode, Activity activity) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_POWER:
                showAlert(activity,"","","","");
                return true;
            case KeyEvent.KEYCODE_BACK:
                showAlert(activity, activity.getResources().getString(R.string.back), activity.getResources().getString(R.string.backText), activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
                return true;
            case KeyEvent.KEYCODE_MENU:
                showAlert(activity, activity.getResources().getString(R.string.menu), activity.getResources().getString(R.string.menuText), activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
                return true;
            case KeyEvent.KEYCODE_HOME:
                showAlert(activity, activity.getResources().getString(R.string.home), activity.getResources().getString(R.string.homeText), activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                showAlert(activity, activity.getResources().getString(R.string.volume), activity.getResources().getString(R.string.volumeText), activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                showAlert(activity, activity.getResources().getString(R.string.volume), activity.getResources().getString(R.string.volumeText), activity.getResources().getString(R.string.yes), activity.getResources().getString(R.string.no));
                return true;
            default:
                // Key not considered yet
                return true;

        }
    }
}
