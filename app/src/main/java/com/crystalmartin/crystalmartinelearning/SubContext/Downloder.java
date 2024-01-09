package com.crystalmartin.crystalmartinelearning.SubContext;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;

import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;

public class Downloder {


    public static void DownloadVideoTif(Activity activity, String Url, String videoName) {  //secrate video
        PrograssHud.KProgressHudStart(activity,activity.getString(R.string.Downloading),activity.getString(R.string.please_wait_till_finish));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(videoName);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_NOTIFICATIONS, "base0/data/" + videoName + ".tif");//.mp4
        request.setDestinationInExternalFilesDir(activity, "/File", "Crystal/"+ videoName+".mp4");
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        BroadcastReceiver downloadListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SweetAlert.SweetAlertSuccess(activity,activity.getString(R.string.download_completed),activity.getString(R.string.download_successfully),activity.getString(R.string.ok));
                PrograssHud.KProgressHudStop(activity);
            }
        };
        activity.registerReceiver(downloadListener, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }

    public static void DownloadVideoZip(Activity activity, String Url, String videoName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(videoName);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Crystal/Vfile/" + videoName + ".zip");//.mp4
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

    public static void DownloadPdfNotes(Activity activity, String Url, String pdfName) { //pdf
        PrograssHud.KProgressHudStart(activity,activity.getString(R.string.Downloading),activity.getString(R.string.please_wait_till_finish));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(pdfName);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "CM/Notes/" + pdfName + ".pdf");//.mp4
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        BroadcastReceiver downloadListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SweetAlert.SweetAlertSuccess(activity,activity.getString(R.string.download_completed),activity.getString(R.string.download_successfully),activity.getString(R.string.ok));
                PrograssHud.KProgressHudStop(activity);
            }
        };
        activity.registerReceiver(downloadListener, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void DownloadOtherResources_PDF(Activity activity, String Url, String resourcesName) { //other Pdf
        PrograssHud.KProgressHudStart(activity,activity.getString(R.string.Downloading),activity.getString(R.string.please_wait_till_finish));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(resourcesName);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOCUMENTS, "CM/ExtraNote/" + resourcesName+".pdf");//.mp4
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        BroadcastReceiver downloadListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SweetAlert.SweetAlertSuccess(activity,activity.getString(R.string.download_completed),activity.getString(R.string.download_successfully),activity.getString(R.string.ok));
                PrograssHud.KProgressHudStop(activity);
            }
        };
        activity.registerReceiver(downloadListener, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public static void DownloadOtherResources_VIDEO(Activity activity, String Url, String resourcesName) { //other Videos
        PrograssHud.KProgressHudStart(activity,activity.getString(R.string.Downloading),activity.getString(R.string.please_wait_till_finish));
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle(resourcesName);
        request.setDescription("Downloading file...");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "CM/Video/" + resourcesName+".mp4");//.mp4
        DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        BroadcastReceiver downloadListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SweetAlert.SweetAlertSuccess(activity,activity.getString(R.string.download_completed),activity.getString(R.string.download_successfully),activity.getString(R.string.ok));
                PrograssHud.KProgressHudStop(activity);
            }
        };
        activity.registerReceiver(downloadListener, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

}
