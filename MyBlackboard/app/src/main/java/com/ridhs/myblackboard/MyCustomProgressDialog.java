package com.ridhs.myblackboard;

import android.app.Activity;
import android.content.Context;

/**
 * Created by RIDHS on 4/6/2017.
 */

public class MyCustomProgressDialog {

    public android.app.ProgressDialog mProgressDialog;
    public Activity mContext;
    //public String mMsg;

    public MyCustomProgressDialog(Activity mContext) {
        this.mContext = mContext;
        //this.mMsg = mMsg;
    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new android.app.ProgressDialog(mContext);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
