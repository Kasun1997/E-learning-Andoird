package com.crystalmartin.crystalmartinelearning.Util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import com.crystalmartin.crystalmartinelearning.R;

import java.lang.reflect.Method;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SweetAlert {

    public static SweetAlertDialog pDialog;

    public static void SweetAlertPrograss(Activity activity, String title, String message) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setContentText(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void SweetAlertError(Activity activity, String title, String message) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE);
        pDialog.setTitleText(title);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setContentText(message);
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.cancel();
            }
        });
        pDialog.show();
    }

    public static void SweetAlertWarning(Activity activity, String title, String message, String confirm,String cancel) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText(title);
        pDialog.setContentText(message);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setConfirmText(confirm);
        pDialog.setCancelText(cancel);
        pDialog.show();
    }

    public static void SweetAlertNormal(Activity activity, String title, String message,String confirm,String cancel) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.NORMAL_TYPE);
        pDialog.setTitleText(title);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setContentText(message);
        pDialog.setConfirmText(confirm);
        pDialog.setCancelText(cancel);
        pDialog.show();
    }

    public static void SweetAlertSuccess(Activity activity, String title, String message,String confirm) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText(title);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setContentText(message);
        pDialog.setConfirmText(confirm);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
        pDialog.show();
    }

    public static void SweetAlertDialogStyle(Activity activity, String title, String message, String confirm) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        pDialog.setTitleText(title);
        pDialog.setContentText(message);
        pDialog.setConfirmText(confirm);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog
                        .setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        });
        pDialog.show();
    }
//activity.getResources().getDrawable(icon);
    public static void SweetAlertCustom(Activity activity, String title, String message, String confirm,int icon) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setCustomImage(activity.getResources().getDrawable(icon));
        pDialog.setTitleText(title);
        pDialog.setContentText(message);
        pDialog.setConfirmText(confirm);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sDialog) {
                sDialog
                        .setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
            }
        });
        pDialog.show();
    }

    public static void SweetAlertCommand(Activity activity, String title, String message, String confirm,int image) {
        pDialog = new SweetAlertDialog(activity, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setCustomImage(image); //R.drawable.view_video
        pDialog.setTitleText(title);
        pDialog.setContentText(message);
        pDialog.setConfirmText(confirm);
        pDialog.show();
    }

    public static void SweetAlertStop() {
        pDialog.dismiss();
    }
}
