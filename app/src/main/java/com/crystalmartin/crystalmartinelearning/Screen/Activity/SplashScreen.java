package com.crystalmartin.crystalmartinelearning.Screen.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.SubContext.Locale_Ln;
import com.crystalmartin.crystalmartinelearning.SubContext.Remember;
import com.crystalmartin.crystalmartinelearning.Util.ActionBar;

public class SplashScreen extends AppCompatActivity {
    private Animation leftAnim;
    private ImageView logo;
    public static int languageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        ActionBar.ActionBarColor(this, R.color.white);
        Initializing();
        logo.setAnimation(leftAnim);
        GetSelectedLanguage();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MoveToLogin();
            }
        }, 3000);

    }

    private void Initializing() {
        leftAnim = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        logo = findViewById(R.id.imgMainlogo);
    }

    private void MoveToLogin() {
        Intent intent = new Intent(this, Login_Screen.class);
        startActivity(intent);
    }

    private void GetSelectedLanguage() {

        String SharedPreff[] = Remember.DisplaySelectedLanguage(this); //display detail to sharedpreferences
        String language = SharedPreff[0];

        if (language == null) {
            Locale_Ln.setLocal(this, "en");
            languageID = 2;
            Log.e("SplashScreen", "language");
        } else {
            Locale_Ln.setLocal(this, language);
            languageID = 3;
            SetLanguageId(language);
            Log.e("SplashScreen", language);
        }
    }

    private void SetLanguageId(String lan){
        if (lan.equals("en")){
            languageID = 2;
            Log.e("SplashScreen", String.valueOf(languageID));
        }
        if (lan.equals("si")){
            languageID = 1;
            Log.e("SplashScreen", String.valueOf(languageID));
        }
        if (lan.equals("ta")){
            languageID = 3;
            Log.e("SplashScreen", String.valueOf(languageID));
        }

    }
}