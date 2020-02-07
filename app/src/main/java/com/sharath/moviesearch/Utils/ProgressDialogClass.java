package com.sharath.moviesearch.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.sharath.moviesearch.R;
import com.wang.avi.AVLoadingIndicatorView;


public class ProgressDialogClass {

    private final Activity activity;
    Dialog dialog;

    public ProgressDialogClass(Activity activity) {
        this.activity = activity;
    }

    public Dialog callProgress() {

        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.progress_dialog_layout);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmlp.gravity = Gravity.CENTER;
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setAttributes(wmlp);
        AVLoadingIndicatorView progressBar1 = dialog.findViewById(R.id.avi);
        progressBar1.setIndicator("BallClipRotateIndicator");

        dialog.show();
        return dialog;
    }
    public void hideDialog() {
        dialog.dismiss();
    }

}
