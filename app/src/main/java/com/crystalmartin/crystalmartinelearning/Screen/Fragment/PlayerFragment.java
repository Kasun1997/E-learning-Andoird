package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.crystalmartin.crystalmartinelearning.R;


public class PlayerFragment extends Fragment {

    private View view;

    private VideoView videoView;
    private SeekBar seekBar;
    private TextView textView;
    private ImageButton play, restart, stop;

    private String MEDIA_PATH = "https://sanctumapi.stepheninnovations.com/video1.mp4";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player, container, false);



        return view;
    }


}