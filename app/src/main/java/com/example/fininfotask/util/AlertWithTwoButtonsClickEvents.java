package com.example.fininfotask.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.fininfotask.R;


public class AlertWithTwoButtonsClickEvents extends Dialog {

    public AlertWithTwoButtonsClickEvents(Context context1, String message, String positiveButtonName, String negativeButtonName,
                                          final DialogClickEventListeners dialogClickEventListeners) {
        super(context1);
        try {
            final Dialog dialog = new Dialog(context1);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.alert_with_two_buttons);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            //
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.gravity = Gravity.CENTER;

            dialog.getWindow().setAttributes(lp);
            //

            TextView txtView = dialog.findViewById(R.id.tvMsg);
            txtView.setText(message);
            TextView tv_cancel = dialog.findViewById(R.id.tv_cancel);
            tv_cancel.setText(negativeButtonName);
            TextView tv_ok = dialog.findViewById(R.id.tv_ok);
            tv_ok.setText(positiveButtonName);

            tv_cancel.setOnClickListener(v -> {
                dialog.dismiss();
                dialogClickEventListeners.cancelClicked();
            });

            tv_ok.setOnClickListener(v -> {
                dialog.dismiss();
                dialogClickEventListeners.okClicked();
            });

            if (!((Activity) context1).isFinishing()) {
                dialog.show();
            }
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }


    public interface DialogClickEventListeners {
        void okClicked();

        void cancelClicked();
    }
}
