package com.crystalmartin.crystalmartinelearning.Util;

import android.app.Activity;

import com.kaopiz.kprogresshud.KProgressHUD;

public class PrograssHud {

    public static KProgressHUD hud;

    public static void KProgressHudStart(Activity activity, String title, String lable) {

        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(title)
                .setDetailsLabel(lable)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public static void KProgressHudStop(Activity activity) {
        hud.dismiss();
    }

}
