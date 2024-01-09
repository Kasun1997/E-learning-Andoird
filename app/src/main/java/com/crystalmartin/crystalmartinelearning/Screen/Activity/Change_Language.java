package com.crystalmartin.crystalmartinelearning.Screen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.SubContext.Locale_Ln;
import com.crystalmartin.crystalmartinelearning.SubContext.Remember;

public class Change_Language extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_language);


    }

    public void Sinhala(View view) {
        SplashScreen.languageID=1;
        Locale_Ln.setLocal(Change_Language.this, "si");
        Remember.SaveSelectedLanguage(Change_Language.this, "si");//save selected language
        MoveToLogin();
    }

    public void English(View view) {
        SplashScreen.languageID=2;
        Locale_Ln.setLocal(Change_Language.this, "en");
        Remember.SaveSelectedLanguage(Change_Language.this, "en");//save selected language
        MoveToLogin();
    }

    public void Tamil(View view) {
        SplashScreen.languageID=3;
        Locale_Ln.setLocal(Change_Language.this, "ta");
        Remember.SaveSelectedLanguage(Change_Language.this, "ta");//save selected language
        MoveToLogin();
    }

    private void MoveToLogin() {
        Intent loginIntent = new Intent(Change_Language.this, Login_Screen.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        MoveToLogin();

    }
}