package com.crystalmartin.crystalmartinelearning.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.material.snackbar.Snackbar;

public class SnackBar {

    public static void ShowSettingSnackbar(RelativeLayout layout, Activity activity) { // settings.page
       Snackbar snackbar = Snackbar.make(layout, "Please turn on data...", Snackbar.LENGTH_INDEFINITE);
        snackbar .setAction("Network", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(intent1);
            }
        }).setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
