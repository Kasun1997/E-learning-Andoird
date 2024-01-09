package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Download;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.SplashScreen;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.VideoPlayer;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadFragment extends Fragment implements Adapter_Download.AdapterMultipleClickEvent {
    private View view;
    private Adapter_Download adapter_download;
    private List<File> videoList;
    private RecyclerView recyclerView;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_download, container, false);

        handler = new Handler();

        RuntimePermission();

        return view;
    }

    private void RuntimePermission() { //this method is for permision
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Log.e("ddd", "d");
                        displayVideo();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Log.e("ddd", "dgg");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    public ArrayList<File> GetDownloadedVideo(File file) {

        ArrayList<File> arrayList = new ArrayList<>();
        File[] files = file.listFiles();

        Log.e("FILE1", String.valueOf(file.listFiles()));

        if (files != null) {
            for (File singleFile : files) {

                if (singleFile.isDirectory() && !singleFile.isHidden()) {
                    arrayList.addAll(GetDownloadedVideo(singleFile));
                    Log.e("FILE3", String.valueOf(singleFile));

                } else {

                    //  if (singleFile.getName().endsWith(".tif")) {
                    arrayList.add(singleFile);
                    Log.e("FILE4", String.valueOf(singleFile)); //jpg,png,mp4,mp3,tif,gif,mov,ogg,tiff,psd,jpf.bmp
                    //  }
                }

            }
        } else {
            Log.e("TAG_ARAAY", "No values");
        }

        Log.e("TAG_ARAAY", String.valueOf(arrayList));
        return arrayList;
    }

    public void displayVideo() {

        recyclerView = view.findViewById(R.id.recycleDownload);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        videoList = new ArrayList<>();
        File file = new File(String.valueOf(getActivity().getApplicationContext().getExternalFilesDir("/File/Crystal")));
        videoList.addAll(GetDownloadedVideo(file));//+"/Aab"
//        videoList.addAll(GetDownloadedVideo(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS + "/base0/data")));//+"/Aab"

        if (videoList == null) {
            Log.e("TAG_CHECK", "NO COUNT");
        } else {
            adapter_download = new Adapter_Download(getActivity(), videoList, DownloadFragment.this);
            recyclerView.setAdapter(adapter_download);
        }


    }

    @Override
    public void onClickPlayVideo(File itemModel, int position) {

        String videoName = itemModel.getName();
        String videoPath = itemModel.getPath();
//        GetDuration(videoName);//id-length-trainigId-courseId-moduleId

        Intent intent = new Intent(getActivity(), VideoPlayer.class);
        intent.putExtra("MEDIA_PA", videoPath);
        intent.putExtra("MEDIA_NA", videoName);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClickDeleteVideo(File itemModel, int position) {

        SweetAlert.SweetAlertWarning(getActivity(), getString(R.string.delete), getString(R.string.delete_this), getString(R.string.yes), getString(R.string.no));
        SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Log.e("TAG_CHECK", "Deleted");
                String description = itemModel.getAbsolutePath(); //delete section done
                File filePath = new File(description); //delete section done

                deletefile(filePath); //delete section done
                sweetAlertDialog.dismiss();
            }
        });

        SweetAlert.pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });

    }

    private void deletefile(File filePath) {
        if (filePath.exists()) {
            filePath.delete();
            FancyToast.makeText(getContext(), "Video deleted successfully", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            RuntimePermission();
        } else {
            FancyToast.makeText(getContext(), "No video existing", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
        }
    }


//    private void GetDuration(String videoName) {
//        String array[] = videoName.split("-");
//        String id = array[0];
//        String length = array[1];
//        String trainigId = array[2];
//        String courseId = array[3];
//        String moduleId = array[4];
//        String sheduleId = array[5];
//
//        Log.e("Handeler", id + "//" + length + "//" + trainigId + "//" + courseId + "//" + moduleId + "//" + sheduleId);
//
//        int lengthTime = (Integer.parseInt(length) / 2) * 60000;
//
//        RunHandler(lengthTime, id, trainigId, courseId, moduleId, sheduleId);
//    }

//    private void RunHandler(int length, String id, String trainId, String courseId, String moduleId, String sheduleId) {
//        handler.removeMessages(0);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int watchLengthInMinute = length / 60000;
//                Log.e("DownloadFragment", "RunHandler : " + id + "// real watch length : " + watchLengthInMinute + "//" + sheduleId);
//                PostWatchLength(length, id, trainId, courseId, moduleId, sheduleId);
//            }
//        }, 3000); //milliseconds
//    }

//    private void PostWatchLength(int length, String video_id, String trainId, String courseId, String moduleId, String sheduleId) {
//        int watchLengthInMinute = length / 60000;
//        Log.e("DownloadFragment", "responce : " + watchLengthInMinute + "//" + video_id + "//" + courseId + "//" + sheduleId + "//" + moduleId + "//" + trainId);
//        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
//        Call<Boolean> call = api_interface.PostWatchLength("Bearer " + Login_Screen.gettoken, watchLengthInMinute, "true", Login_Screen.getid, video_id, courseId, sheduleId, moduleId, trainId, SplashScreen.languageID);
//        call.enqueue(new Callback<Boolean>() {
//            @Override
//            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
//                Log.e("DownloadFragment", "onResponce : " + response.code());
//
//                if (response.code() == 200) {
//                    Log.e("DownloadFragment", "res : " + response.body());
//                    FancyToast.makeText(getContext(), "Updated", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
//                } else {
//                    Log.e("DownloadFragment", "res : " + response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Boolean> call, Throwable t) {
//                Log.e("DownloadFragment", "res : " + t.getMessage());
//            }
//        });
//    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        Log.e("DownloadFragment", "destroy");
//        handler.removeMessages(0);
//    }

//    private void CancelOnBackPress() { //fragment can not get back
//        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                Log.e("DownloadFragment", "back");
//
//            }
//        };
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
//    }

}
