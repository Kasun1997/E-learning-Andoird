package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Trainings;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.Model_Real.Training_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.SubContext.Network;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements Adapter_Trainings.OnItemClickListener {

    private View view;

    private RecyclerView recyclerView;
    private Adapter_Trainings adapter_trainings;
    private List<Training_Model> training_model;
    private TextView txvwName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        CancelOnBackPress();

        recyclerView = view.findViewById(R.id.recycleTrainig);
        txvwName = view.findViewById(R.id.txvwName);
        txvwName.setText(getString(R.string.welcome) + "\n" + Login_Screen.getProfName);
        training_model = new ArrayList<>();

        Network network = new Network(getContext());
        if (network.isNetworkAvailable(getActivity())) {
            GetAllTrainings();
        } else {
            FancyToast.makeText(getContext(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }


        return view;
    }

    public void GetAllTrainings() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Log.e("HomeFragment", "token and id : " + Login_Screen.gettoken + "//" + Integer.parseInt(Login_Screen.getid));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Training_Model>> call = api_interface.GetTraining("Bearer " + Login_Screen.gettoken, Login_Screen.getid);
        call.enqueue(new Callback<List<Training_Model>>() {
            @Override
            public void onResponse(Call<List<Training_Model>> call, Response<List<Training_Model>> response) {
                Log.e("HomeFragment", "onResponce : " + response.code());//401/200

                if (response.code() == 200) {

                    Log.e("HomeFragment", "res : " + response.message());

                    List<Training_Model> data = response.body();
                    for (Training_Model d : data) {
                        Log.e("HomeFragment", "Name : " + d.getTrainingName() + "//" + d.getTrainingId());
                        Log.e("HomeFragment", "Size : " + data.size());
                        training_model.add(d);
                    }
                    SetUpRecycler(training_model);


                } else {
                    Log.e("HomeFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Training_Model>> call, Throwable t) {
                Log.e("HomeFragment", "onFailure : " + t.getMessage());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void SetUpRecycler(List<Training_Model> training_model) {

        if (training_model.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.not_available), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            Log.e("CheckRecyclerSize", "" + training_model.size());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_trainings = new Adapter_Trainings(getContext(), training_model);
            recyclerView.setAdapter(adapter_trainings);
            recyclerView.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
            adapter_trainings.setOnItemClickListener(HomeFragment.this);
        }
    }

    @Override
    public void onItemClick(int position) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Training_Model mGet = training_model.get(position);
            Log.e("HomeFragment", "onclick :" + mGet.getTrainingId() + "//" + mGet.getTrainingName());

            Bundle bundle = new Bundle();
            bundle.putString("TrainingID", String.valueOf(mGet.getTrainingId()));
            bundle.putString("TrainingName", mGet.getTrainingName());

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction t = fragmentManager.beginTransaction();
            Fragment mFrag = new CourseFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.frame, mFrag);
            t.addToBackStack(HomeFragment.class.getName());
            t.commit();

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }

    }

    private void CancelOnBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.e("QuizeFragment", "back");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}