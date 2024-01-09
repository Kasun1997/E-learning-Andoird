package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Module;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.Model_Real.Class_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Module_Model;
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


public class ModuleFragment extends Fragment implements Adapter_Module.OnItemClickListener {
    private View view;

    private RecyclerView recyclerView;
    private Adapter_Module adapter_module;
    private List<Module_Model> module_model;

    private String courseId, trainigId, courseName, trainingSheduleId;
    private TextView txtvwModulename;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_module, container, false);

        Bundle bundle = this.getArguments();
        courseId = bundle.getString("CourseID");
        courseName = bundle.getString("CourseName");
        trainigId = bundle.getString("TrainigId");
        Log.e("CHECK_ALL_ID", trainigId + "//" + courseId);
        Log.e("ModuleFragment", "From_fragment_bundle : " + courseId + " // " + courseName + "//" + trainigId);


        recyclerView = view.findViewById(R.id.recycleModule);
        txtvwModulename = view.findViewById(R.id.txtvwModulename);
        module_model = new ArrayList<>();

        RuntimePermission();
        return view;
    }

    private void RuntimePermission() { //this method is for permision
        Dexter.withContext(getActivity()).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        GetClass();
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

    public void GetClass() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Class_Model>> call = api_interface.GetClass("Bearer " + Login_Screen.gettoken, Login_Screen.getid, courseId);
        call.enqueue(new Callback<List<Class_Model>>() {
            @Override
            public void onResponse(Call<List<Class_Model>> call, Response<List<Class_Model>> response) {
                Log.e("ModuleFragment", "onResponce : " + response.code());
                List<Class_Model> data = response.body();
                if (data.size() == 0) {
                    Log.e("ModuleFragment", "zero");
                    PrograssHud.KProgressHudStop(getActivity());
                } else {
                    Log.e("ModuleFragment", "Namem : " + data.get(0).getName() + "//" + data.get(0).getTrainingSheduleId() + "///" + data.size());
                    txtvwModulename.setText(data.get(0).getName());
                    trainingSheduleId = data.get(0).getTrainingSheduleId();
                    GetAllModules(data.get(0).getTrainingSheduleId());
                }

            }

            @Override
            public void onFailure(Call<List<Class_Model>> call, Throwable t) {
                Log.e("ModuleFragment", "onError : " + t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }


    public void GetAllModules(String shedule_id) {
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Module_Model>> call = api_interface.GetModule("Bearer " + Login_Screen.gettoken, courseId, Login_Screen.getid, shedule_id);
        call.enqueue(new Callback<List<Module_Model>>() {
            @Override
            public void onResponse(Call<List<Module_Model>> call, Response<List<Module_Model>> response) {
                Log.e("ModuleFragment", "onResponce : " + response.code());

                if (response.code() == 200) {

                    Log.e("ModuleFragment", "res : " + response.message());

                    List<Module_Model> data = response.body();
                    for (Module_Model d : data) {
                        Log.e("ModuleFragment", "Name : " + d.getCourseName() + "//" + d.getCourseModuleId());
                        Log.e("ModuleFragment", "Size : " + data.size());
                        module_model.add(d);
                    }

                    SetUpRecycler(module_model);

                } else {
                    Log.e("CourseFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Module_Model>> call, Throwable t) {
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

    private void SetUpRecycler(List<Module_Model> module_model) {

        if (module_model.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.not_available), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            Log.e("ModuleFragment", "CheckRecyclerSize" + " " + module_model.size());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_module = new Adapter_Module(getContext(), module_model);
            recyclerView.setAdapter(adapter_module);
            recyclerView.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
            adapter_module.setOnItemClickListener(ModuleFragment.this);
        }

    }

    @Override
    public void onItemClick(int position) {

        Module_Model mGet = module_model.get(position);
        int x = position - 1;
        Log.e("MDF", String.valueOf(x) + " x value");

        if (position == 0) {
            MoveToStudyMaterial(mGet);
        } else {

            if (module_model.get(x).getStatus() == 1) {
                MoveToStudyMaterial(mGet);
            } else {
                Log.e("MDF", "Can not access module 1");
            }

        }
    }

    private void MoveToStudyMaterial(Module_Model mGet){
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Bundle bundle = new Bundle();
            bundle.putString("ModuleID", mGet.getCourseModuleId());
            bundle.putString("ModuleName", mGet.getCourseName());
            bundle.putString("CourseID", courseId);
            bundle.putString("TrainigId", trainigId);
            bundle.putString("TrainigSheduleId", trainingSheduleId);
            Log.e("ModuleFragment", "MDF "+mGet.getCourseModuleId() + "//" + mGet.getCourseName() + "//" + mGet.getModuleOrderNumber());

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction t = fragmentManager.beginTransaction();
            Fragment mFrag = new StudyMaterialFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.frame, mFrag);
            t.addToBackStack(ModuleFragment.class.getName());
            t.commit();

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }
    }
}