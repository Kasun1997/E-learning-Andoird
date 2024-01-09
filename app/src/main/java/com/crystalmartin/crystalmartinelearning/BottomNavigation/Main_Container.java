package com.crystalmartin.crystalmartinelearning.BottomNavigation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.DownloadFragment;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.HomeFragment;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.ProfileFragment;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.RatingFragment;
import com.crystalmartin.crystalmartinelearning.SubContext.Remember;
import com.crystalmartin.crystalmartinelearning.Util.ActionBar;
import com.crystalmartin.crystalmartinelearning.Util.AlertBar;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Container extends AppCompatActivity {
    private MeowBottomNavigation bottomnavi;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView nvDrawer;
    private TextView device_name, total_storage, used_storage, free_storage,btnLogout;
    private CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);


        ActionBar.ActionBarDrawable(this, R.drawable.status_bar_2, android.R.color.transparent);

        btnLogout = findViewById(R.id.btnLogout);

        SideDrawerInitializing();
        bottomnavi = findViewById(R.id.bottomnavi);

        bottomnavi.add(new MeowBottomNavigation.Model(1, R.drawable.ic_tab_home));
        bottomnavi.add(new MeowBottomNavigation.Model(2, R.drawable.ic_tab_download));
        bottomnavi.add(new MeowBottomNavigation.Model(3, R.drawable.ic_tab_profile));

        bottomnavi.show(1, true);
        replace(new HomeFragment());

        bottomnavi.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new DownloadFragment());
                        break;

                    case 3:
                        replace(new ProfileFragment()); //ProfileFragment()
                        break;
                }
                return null;
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteLoginCredintials();
            }
        });

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    //-----------side drawer implementation---------------
    private void SideDrawerInitializing() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        nvDrawer = (NavigationView) findViewById(R.id.nav_view);
        mToggle.syncState();
        setupDrawerContent(nvDrawer);

        //----------------pass value to header---------------
        View hView = nvDrawer.getHeaderView(0);
        profileImage = hView.findViewById(R.id.imgProfile);
        device_name = hView.findViewById(R.id.txvwDevice);
        total_storage = hView.findViewById(R.id.txvwTotalStorage);
        used_storage = hView.findViewById(R.id.txvwUsed);
        free_storage = hView.findViewById(R.id.txvwFree);

        External_free_and_total_memory();
    }

    public void selectItemDrawer(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.db:

                Log.e("MainContainer", "1");
                break;
            case R.id.event:

                Log.e("MainContainer", "2");
                break;
            case R.id.search:

                Log.e("MainContainer", "3");
                break;
            case R.id.settings:

                Log.e("MainContainer", "4");
                break;

            case R.id.logout:
                Log.e("MainContainer", "6");

                break;
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectItemDrawer(item);
                return true;
            }
        });
    }

    public void setDrawerPosition(View view) {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
            mDrawerLayout.setVisibility(View.VISIBLE);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    //---------------Phone Info--------------------
    private void External_free_and_total_memory() {
        long freeBytesExternal = new File(getExternalFilesDir(null).toString()).getFreeSpace();
        int free = (int) (freeBytesExternal / (1024 * 1024));
        long totalSize = new File(getExternalFilesDir(null).toString()).getTotalSpace();
        int total = (int) (totalSize / (1024 * 1024));
        long totalStorage = Long.valueOf(total) / 1000;
        long freeStorage = Long.valueOf(free) / 1000;
        long usedStorage = totalStorage - freeStorage;

        String model = Build.MODEL;

        if (Login_Screen.getProfileImage==null){
            Picasso.get().load(R.drawable.profile_placeholder).placeholder(R.drawable.profile_placeholder).into(profileImage);
        }else {
            Picasso.get().load(Login_Screen.getProfileImage).placeholder(R.drawable.profile_placeholder).into(profileImage);
        }
        device_name.setText("Device Name : " + model);
        total_storage.setText("Total Storage : " + totalStorage + " GB");
        used_storage.setText("Used : " + usedStorage + " GB");
        free_storage.setText("Free : " + freeStorage + " GB");
    }

    private void MoveToLogin() {
        Intent loginIntent = new Intent(Main_Container.this, Login_Screen.class);
        startActivity(loginIntent);
        finish();
    }


    private void DeleteLoginCredintials() {
        SweetAlert.SweetAlertWarning(Main_Container.this, getString(R.string.signout), getString(R.string.a_u_want_to), getString(R.string.yes), getString(R.string.no));
        SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Remember.DeleteLoginCredentials(Main_Container.this);
                MoveToLogin();
            }
        });

        SweetAlert.pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                SweetAlert.pDialog.dismiss();
            }
        });

    }


}