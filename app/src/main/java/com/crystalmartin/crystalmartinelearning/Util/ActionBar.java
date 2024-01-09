package com.crystalmartin.crystalmartinelearning.Util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;

import com.crystalmartin.crystalmartinelearning.R;

public class ActionBar {

    public static void ActionBarDrawable(Activity activity,int icon,int color) {
        Window windows = activity.getWindow();
        Drawable background = activity.getResources().getDrawable(icon);
        windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        windows.setStatusBarColor(activity.getResources().getColor(color));
        windows.setNavigationBarColor(activity.getResources().getColor(color));
        windows.setBackgroundDrawable(background);
    }

    public static void ActionBarColor(Activity activity,int color) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(activity.getResources().getColor(color)); //R.color.screen_background
    }
}
