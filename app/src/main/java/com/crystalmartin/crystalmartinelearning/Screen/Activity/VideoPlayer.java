package com.crystalmartin.crystalmartinelearning.Screen.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoPlayer extends AppCompatActivity {

    private VideoView videoView;
    private MediaController mediaC;
    private String videoPath, videoName;
    private Handler handler;
    private int x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_player);

        videoPath = getIntent().getStringExtra("MEDIA_PA");
        videoName = getIntent().getStringExtra("MEDIA_NA");

        handler = new Handler();

        videoView = findViewById(R.id.video);
        mediaC = new MediaController(this);

        videoPlay(videoPath, videoName);

    }

    public void videoPlay(String path, String name) {

        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaC);
        mediaC.setAnchorView(videoView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView.start();
        GetDuration(name);

    }

    private void GetDuration(String videoName) {
        String array[] = videoName.split("-");
        String id = array[0];
        String length = array[1];
        String trainigId = array[2];
        String courseId = array[3];
        String moduleId = array[4];
        String sheduleId = array[5];

        Log.e("VideoPlayer", id + "//" + length + "//" + trainigId + "//" + courseId + "//" + moduleId + "//" + sheduleId);

        int lengthTime = (Integer.parseInt(length) / 2) * 60000;

        RunHandler(lengthTime, id, trainigId, courseId, moduleId, sheduleId);

    }

    private void RunHandler(int length, String id, String trainId, String courseId, String moduleId, String sheduleId) {
        handler.removeMessages(0);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int watchLengthInMinute = length / 60000;
                Log.e("VideoPlayer", "RunHandler : " + id + "// real watch length : " + watchLengthInMinute + "//" + sheduleId);
                PostWatchLength(length, id, trainId, courseId, moduleId, sheduleId);
            }
        }, length); //milliseconds "length"
    }

    private void PostWatchLength(int length, String video_id, String trainId, String courseId, String moduleId, String sheduleId) {
        int watchLengthInMinute = length / 60000;

        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<Boolean> call = api_interface.PostWatchLength("Bearer " + Login_Screen.gettoken, watchLengthInMinute, "true", Login_Screen.getid, video_id, courseId, sheduleId, moduleId, trainId, SplashScreen.languageID);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.e("VideoPlayer", "onResponce : " + response.code());

                if (response.code() == 200) {
                    Log.e("VideoPlayer", "res : " + response.body());
                    FancyToast.makeText(VideoPlayer.this, "Updated", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                } else {
                    Log.e("VideoPlayer", "res : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("VideoPlayer", "res : " + t.getMessage());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("VideoPlayer", "destroy");
        handler.removeMessages(0);
    }


    @Override
    protected void onPause() { //send to recent app
        super.onPause();
        videoView.pause();
        x = videoView.getCurrentPosition();
        Log.e("VideoPlayer22", String.valueOf(videoView.getCurrentPosition()));
    }

    @Override
    protected void onResume() { //get from recent app
        super.onResume();
        if (x == 0) {
            Log.e("VideoPlayer22", "onResume 2 zero");
        } else {
            videoView.seekTo(x);
        }

//        Log.e("VideoPlayer22", "onResume 2: " + x);
    }

}