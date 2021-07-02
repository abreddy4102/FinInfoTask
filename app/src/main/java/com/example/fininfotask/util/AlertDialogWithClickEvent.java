package com.example.fininfotask.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.fininfotask.R;


public class AlertDialogWithClickEvent extends Dialog {

    public AlertDialogWithClickEvent(Context context1, String message, String buttonName, final DialogClickEventListeners dialogClickEventListeners) {
        super(context1);
        try {
            final Dialog dialog = new Dialog(context1);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.alert_with_single_button);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            //
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.gravity = Gravity.CENTER;

            dialog.getWindow().setAttributes(lp);
            //
            TextView txtView = dialog.findViewById(R.id.tv_defaultedTitleMsg);
            txtView.setText(message);
            TextView tv_defaultedOKBtn = dialog.findViewById(R.id.tv_defaultedOKBtn);
            tv_defaultedOKBtn.setText(buttonName);

            tv_defaultedOKBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    dialogClickEventListeners.okClicked();
                }
            });

            if (!((Activity) context1).isFinishing()) {
                dialog.show();
            }
        } catch (WindowManager.BadTokenException e) {
            Log.e("dialogWindowException", " " + e.getLocalizedMessage() + " " + e.getMessage());
        }
    }

    public interface DialogClickEventListeners {
        void okClicked();
    }

}
