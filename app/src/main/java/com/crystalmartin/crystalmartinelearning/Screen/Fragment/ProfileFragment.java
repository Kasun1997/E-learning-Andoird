package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private TextView txvwUserName, txvwEpfNo;
    private View view;
    private CircleImageView profileImage;
    String im;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Initializing();
        CancelOnBackPress();
        txvwUserName.setText(Login_Screen.getProfName);
        txvwEpfNo.setText(Login_Screen.getepf);

        if (Login_Screen.getProfileImage==null){
            Picasso.get().load(R.drawable.profile_placeholder).placeholder(R.drawable.profile_placeholder).into(profileImage);
        }else {
            Picasso.get().load(Login_Screen.getProfileImage).placeholder(R.drawable.profile_placeholder).into(profileImage);
        }

        return view;
    }

    private void Initializing() {
        txvwUserName = view.findViewById(R.id.txvwName);
        txvwEpfNo = view.findViewById(R.id.txvwEpfNo);
        profileImage = view.findViewById(R.id.imgProfile);
    }

    private void CancelOnBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.e("DownloadFragment", "back");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}