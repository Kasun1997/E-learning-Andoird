package com.crystalmartin.crystalmartinelearning.Screen.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.crystalmartin.crystalmartinelearning.R;

public class Reset_Password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
    }

    public void MoveToLogin(View view){
        Intent LoginIntent = new Intent(Reset_Password.this,Login_Screen.class);
        startActivity(LoginIntent);
        finish();
    }
}