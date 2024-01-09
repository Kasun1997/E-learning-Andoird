package com.crystalmartin.crystalmartinelearning.Util;

import android.app.Activity;

import com.fede987.statusbaralert.StatusBarAlert;

public class AlertBar {

    public static void notifyDanger(Activity activity, String message){
        new StatusBarAlert.Builder(activity)
                .autoHide(true)
                .withDuration(2000)
                .withText(message)
                .withAlertColor(com.crystalmartin.crystalmartinelearning.R.color.AlertRed)
                .build();

    }

    public static void notifyWarning(Activity activity, String message){
        new StatusBarAlert.Builder(activity)
                .autoHide(true)
                .withDuration(2000)
                .withText(message)
                .withAlertColor(com.crystalmartin.crystalmartinelearning.R.color.AlertYellow)
                .build();
    }

    public static void notifyInfo(Activity activity, String message){
        new StatusBarAlert.Builder(activity)
                .autoHide(true)
                .withDuration(2000)
                .withText(message)
                .withAlertColor(com.crystalmartin.crystalmartinelearning.R.color.AlertBlue)
                .build();
    }

    public static void notifySuccess(Activity activity, String message){
        new StatusBarAlert.Builder(activity)
                .autoHide(true)
                .withDuration(2000)
                .withText(message)
                .withAlertColor(com.crystalmartin.crystalmartinelearning.R.color.AlertGreen)
                .build();
    }

}
