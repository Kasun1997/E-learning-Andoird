package com.crystalmartin.crystalmartinelearning.SubContext;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.shashank.sony.fancytoastlib.FancyToast;

public class Network {
    Context mContext;

    public Network(Context mContext) {
        this.mContext = mContext;
    }

    public static void NetworkCheck(Activity activity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            FancyToast.makeText(activity.getApplicationContext(), activity.getString(R.string.connection_success),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
        } else {
            FancyToast.makeText(activity.getApplicationContext(), activity.getString(R.string.connection_failed),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();
        }
    }

    public boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
