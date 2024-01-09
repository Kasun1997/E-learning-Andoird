package com.crystalmartin.crystalmartinelearning.SubContext;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class Locale_Ln {

    public static void setLocal(Activity activity, String lang) {  // for fragment use getActivity in fragment
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
