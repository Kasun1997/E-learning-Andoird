package com.crystalmartin.crystalmartinelearning.Screen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.BottomNavigation.Main_Container;
import com.crystalmartin.crystalmartinelearning.Model_Real.UserMain_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.SubContext.Network;
import com.crystalmartin.crystalmartinelearning.SubContext.Remember;
import com.crystalmartin.crystalmartinelearning.Util.AlertBar;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.crystalmartin.crystalmartinelearning.Util.SnackBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Screen extends AppCompatActivity {

    private EditText edtxtUsername, edtxtPassword;
    private String getUsername, getPassword;
    public static String gettoken, getid, getusername, getepf, getProfileImage, getProfName;
    private RelativeLayout mainTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        Initializing();
        CallTokenIdSharedPreferences();
    }

    private void Initializing() {
        edtxtUsername = findViewById(R.id.edtxtUsername);
        edtxtPassword = findViewById(R.id.edtxtPassword);
        mainTop = findViewById(R.id.LS_main_R_layout);
    }

    public void ClickToLogin(View view) {
        Validation();
    }

    private void Validation() {
        getUsername = edtxtUsername.getText().toString().trim();
        getPassword = edtxtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(getUsername)) {
            FancyToast.makeText(this, getString(R.string.please_enter_username), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            AlertBar.notifyDanger(Login_Screen.this, getString(R.string.please_enter_username));
        } else if (TextUtils.isEmpty(getPassword)) {
            FancyToast.makeText(this, getString(R.string.please_enter_password), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            AlertBar.notifyDanger(Login_Screen.this, getString(R.string.please_enter_password));
        } else {
            UserLogin();
        }
    }


    private void UserLogin() {

        Log.e("LoginScreen", "Values 1 " + edtxtUsername.getText().toString().trim());
        Log.e("LoginScreen", "Values 2 " + edtxtPassword.getText().toString().trim());


        Network network = new Network(this);
        if (network.isNetworkAvailable(this)) {

            PrograssHud.KProgressHudStart(Login_Screen.this, getString(R.string.loginloding), getString(R.string.loginloding_description));//start hud
            Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
            Call<UserMain_Model> call = api_interface.UserLoginCrystalApi(edtxtUsername.getText().toString().trim(), edtxtPassword.getText().toString().trim());
            call.enqueue(new Callback<UserMain_Model>() {
                @Override
                public void onResponse(Call<UserMain_Model> call, Response<UserMain_Model> response) {

                    Log.e("LoginScreen", "onResponce : " + response.code());//401/201

                    if (response.code() == 200) {

                        UserMain_Model userMSub = response.body();
                        Log.e("LoginScreen", "responce_body : " + userMSub.getUser().getUsername());
                        Log.e("LoginScreen", "responce_body : " + userMSub.getUser().getUserId());
                        Log.e("LoginScreen", "responce_body : " + userMSub.getToken());

                        String UserName = userMSub.getUser().getUsername();
                        int UserId = userMSub.getUser().getUserId();
                        String UserToken = userMSub.getToken();
                        String EpfNo = userMSub.getUser().getEpfNo();
                        String ProfImage = userMSub.getUser().getImageUrl();
                        String ProfName = userMSub.getUser().getProfileName();

                        getusername = UserName;
                        getid = String.valueOf(UserId);
                        gettoken = UserToken;
                        getepf = String.valueOf(EpfNo);
                        getProfileImage = ProfImage;
                        getProfName = ProfName;

                        Remember.SaveLoginCredentials(Login_Screen.this, UserToken, String.valueOf(UserId), UserName, String.valueOf(EpfNo),ProfImage,ProfName);//save detail to sharedpreferences

                        PrograssHud.KProgressHudStop(Login_Screen.this);//stop hud
                        FancyToast.makeText(Login_Screen.this, getString(R.string.logged_in_successfully), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                        AlertBar.notifySuccess(Login_Screen.this, getString(R.string.logged_in_successfully));

                        MoveToMainContainer();

                    } else if (response.code() == 401) {
                        FancyToast.makeText(Login_Screen.this, getString(R.string.Invalid_username_and_password), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        AlertBar.notifyDanger(Login_Screen.this, getString(R.string.Invalid_username_and_password));
                        Log.e("LoginScreen", "No lik that one");
                        PrograssHud.KProgressHudStop(Login_Screen.this);//stop hud
                    } else {
                        Log.e("LoginScreen", "No choice");
                        FancyToast.makeText(Login_Screen.this, getString(R.string.Invalid_username_and_password), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        AlertBar.notifyDanger(Login_Screen.this, getString(R.string.Invalid_username_and_password));
                        PrograssHud.KProgressHudStop(Login_Screen.this);//stop hud
                    }
                }

                @Override
                public void onFailure(Call<UserMain_Model> call, Throwable t) {
                    PrograssHud.KProgressHudStop(Login_Screen.this);//stop hud
                    Log.e("LoginScreen", "onFailure : " + t.getMessage());
                    FancyToast.makeText(Login_Screen.this, getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                }
            });

        } else {
            Log.e("LoginScreen", "Network : " + "not connected");
            FancyToast.makeText(Login_Screen.this, getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            AlertBar.notifyWarning(Login_Screen.this, getString(R.string.wifi_or_mobile_data));
            SnackBar.ShowSettingSnackbar(mainTop, this);
        }
    }

    public void MoveToMainContainer() {
        Intent intent = new Intent(Login_Screen.this, Main_Container.class);
        startActivity(intent);
        finish();
    }

    public void MoveToResetPassword(View view) {
        Intent resetIntent = new Intent(Login_Screen.this, Reset_Password.class);
        startActivity(resetIntent);
    }

    public void MoveToChangeLanguage(View view) {
        Intent languageIntent = new Intent(Login_Screen.this, Change_Language.class);
        startActivity(languageIntent);
        finish();
    }

    private void CallTokenIdSharedPreferences() {

        Network network = new Network(this);
        if (network.isNetworkAvailable(this)) {

            String SharedPreff[] = Remember.DisplayLoginCredentials(this); //display detail to sharedpreferences
            String token = SharedPreff[0];
            String id = SharedPreff[1];
            String name = SharedPreff[2];
            String epfno = SharedPreff[3];
            String profImage=SharedPreff[4];
            String profName=SharedPreff[5];
            Log.e("LoginScreen", "token and id all : " + token + "//" + id + "//" + name + "//" + epfno+"//**//"+profImage+"//"+profName);
            if (token == null && id == null) {
                Log.e("LoginScreen", "Token and Id is empty");

            } else {
                Log.e("LoginScreen", "token and id : " + token + "//" + id);
                gettoken = token;
                getusername = name;
                getid = id;
                getepf = epfno;
                getProfileImage = profImage;
                getProfName = profName;
                MoveToMainContainer();
            }

        } else {
            Log.e("LoginScreen", "Network : " + "not connected");
            SnackBar.ShowSettingSnackbar(mainTop, this);
        }

    }

    @Override
    public void onBackPressed() {
        CallTokenIdSharedPreferences();
    }
}
