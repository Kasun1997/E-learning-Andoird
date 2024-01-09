package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_OtherResources;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Pdf;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Video;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.Model_Real.Other_R_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Pdf_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Quiz_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.UpdateAttempt_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.Video_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.SplashScreen;
import com.crystalmartin.crystalmartinelearning.SubContext.Downloder;
import com.crystalmartin.crystalmartinelearning.SubContext.Network;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudyMaterialFragment extends Fragment implements Adapter_Video.AdapterMultipleClickEvent, Adapter_Pdf.AdapterMultipleClickEvent, Adapter_OtherResources.AdapterMultipleClickEvent {
    private View view;
    private String moduleId, moduleName, courseId, trainigId, trainingSheduleId;
    private TextView txvwModulename;
    private RadioButton rbtVideo, rbtPdf, rbtOther, rbtQuize;
    private RelativeLayout rLayoutVideo, rLayoutPdf, rLayoutOther, rLayoutQuiz;
    private RecyclerView recycleVideo, recyclePdf, recycleOther;

    private Adapter_Video adapter_video;
    private List<Video_Model> video_model;

    private Adapter_Pdf adapter_pdf;
    private List<Pdf_Model> pdf_model;

    private Adapter_OtherResources adapter_otherResources;
    private List<Other_R_Model> othereResources_m_gets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_study_material, container, false);

        Initializing();

        Bundle bundle = this.getArguments();
        moduleId = bundle.getString("ModuleID");
        moduleName = bundle.getString("ModuleName");
        trainigId = bundle.getString("TrainigId");
        courseId = bundle.getString("CourseID");
        trainingSheduleId = bundle.getString("TrainigSheduleId");

        Log.e("CHECK_ALL_ID", trainigId + "//" + courseId + "//" + moduleId + "//" + trainingSheduleId);


        txvwModulename.setText(moduleName);

        video_model = new ArrayList<>();
        pdf_model = new ArrayList<>();
        othereResources_m_gets = new ArrayList<>();

        ShowVideo();

        rbtVideo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    rLayoutVideo.setVisibility(View.VISIBLE);
                    ShowVideo();
                    Log.e("StudyMaterialFragment", "CheckedVideo");
                } else {
                    Log.e("StudyMaterialFragment", "Not CheckedVideo");
                    rLayoutVideo.setVisibility(View.INVISIBLE);
                    video_model.removeAll(video_model);
                    recycleVideo.setVisibility(View.INVISIBLE);
                }

            }
        });

        rbtPdf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    rLayoutPdf.setVisibility(View.VISIBLE);
                    ShowPdf();
                    Log.e("StudyMaterialFragment", "CheckedPdf");
                } else {
                    Log.e("StudyMaterialFragment", "Not CheckedPdf");
                    rLayoutPdf.setVisibility(View.INVISIBLE);
                    pdf_model.removeAll(pdf_model);
                    recyclePdf.setVisibility(View.INVISIBLE);
                }

            }
        });

        rbtOther.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    rLayoutOther.setVisibility(View.VISIBLE);
                    ShowOther();
                    Log.e("StudyMaterialFragment", "CheckedOther");
                } else {
                    Log.e("StudyMaterialFragment", "Not CheckedOther");
                    rLayoutOther.setVisibility(View.INVISIBLE);
                    othereResources_m_gets.removeAll(othereResources_m_gets);
                    recycleOther.setVisibility(View.INVISIBLE);

                }

            }
        });

        rbtQuize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    rLayoutQuiz.setVisibility(View.VISIBLE);
                    ShowQuize();
                    Log.e("StudyMaterialFragment", "CheckedQuize");
                } else {
                    rLayoutQuiz.setVisibility(View.INVISIBLE);
                    Log.e("StudyMaterialFragment", "Not CheckedQuize");

                }

            }
        });

        return view;
    }

    private void Initializing() {
        rbtVideo = view.findViewById(R.id.radioBtnVideo);
        rbtPdf = view.findViewById(R.id.radioBtnPdf);
        rbtOther = view.findViewById(R.id.radioBtnOther);
        rbtQuize = view.findViewById(R.id.radioBtnQuize);

        rLayoutVideo = view.findViewById(R.id.rLayoutVideo);
        rLayoutPdf = view.findViewById(R.id.rLayoutPdf);
        rLayoutOther = view.findViewById(R.id.rLayoutOther);
        rLayoutQuiz = view.findViewById(R.id.rLayoutQuiz);


        txvwModulename = view.findViewById(R.id.txtvwMaterialName);

        recycleVideo = view.findViewById(R.id.recycleVideo);
        recyclePdf = view.findViewById(R.id.recyclePdf);
        recycleOther = view.findViewById(R.id.recycleOther);

    }

    private void ShowVideo() //display method
    {
        rbtVideo.setChecked(true);
        GetVideo();
    }

    private void ShowPdf() //display method
    {
        GetPdf();
    }

    private void ShowOther() //display method
    {
        GetOtherResources();
    }

    private void ShowQuize() //display method
    {
        GetQuizeAvailability();
    }

    //Api Call

    //Video
    private void GetVideo() {

        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        video_model.clear();
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Video_Model>> call = api_interface.GetVideo("Bearer " + Login_Screen.gettoken, moduleId, SplashScreen.languageID);
        call.enqueue(new Callback<List<Video_Model>>() {
            @Override
            public void onResponse(Call<List<Video_Model>> call, Response<List<Video_Model>> response) {
                Log.e("StudyMaterialFragment", "onResponce : " + response.code());
                if (response.code() == 200) {

                    Log.e("StudyMaterialFragment", "res : " + response.message());

                    List<Video_Model> data = response.body();
                    for (Video_Model d : data) {
                        video_model.add(d);
                    }
                    SetUpVideoRecycle(video_model);

                } else {
                    Log.e("StudyMaterialFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Video_Model>> call, Throwable t) {
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

    private void SetUpVideoRecycle(List<Video_Model> Video_Model) {
        if (Video_Model.size() == 0) {
            Log.e("StudyMaterialFragment", "Not available video");
            FancyToast.makeText(getContext(), getString(R.string.not_available_videos), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            recycleVideo.setHasFixedSize(true);
            recycleVideo.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_video = new Adapter_Video(getContext(), Video_Model, StudyMaterialFragment.this);
            recycleVideo.setAdapter(adapter_video);
            recycleVideo.setVisibility(View.VISIBLE);
            recycleVideo.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
        }
    }

    @Override
    public void onClickDownloadVideo(Video_Model itemModel, int position) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            String video_Path = itemModel.getVideoPath();
            String video_Name = itemModel.getId() + "-" + itemModel.getVideolength() + "-" + trainigId + "-" + courseId + "-" + moduleId + "-" + trainingSheduleId + "-" + "00";
            Log.e("StudyMaterialFragment", "download : " + video_Name + "///" + video_Path);

//            File existsFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS + "/base0/data/" + video_Name + ".tif")));
//            File existsFile =new File(String.valueOf(getActivity().getApplicationContext().getExternalFilesDir("/File/Crystal/"+video_Name+".mp4")));
//            Log.e("yyy", String.valueOf(existsFile));
//            if (existsFile.exists()) {
//                FancyToast.makeText(getContext(), getString(R.string.already_exists), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
//            } else {
            Downloder.DownloadVideoTif(getActivity(), video_Path, video_Name);
//                FancyToast.makeText(getContext(), "lodingddd", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
//            }

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }

    }


    @Override
    public void onClickPlayVideo(Video_Model itemModel, int position) {
        SweetAlert.SweetAlertCommand(getActivity(), getString(R.string.play_video), getString(R.string.download_and_watch), getString(R.string.ok), R.drawable.view_video);
        SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {

                sweetAlertDialog.dismiss();
            }
        });
    }


    //Pdf
    private void GetPdf() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        pdf_model.clear();
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Pdf_Model>> call = api_interface.GetPdf("Bearer " + Login_Screen.gettoken, moduleId, SplashScreen.languageID);
        call.enqueue(new Callback<List<Pdf_Model>>() {
            @Override
            public void onResponse(Call<List<Pdf_Model>> call, Response<List<Pdf_Model>> response) {
                Log.e("StudyMaterialFragment", "onResponce : " + response.code());
                if (response.code() == 200) {

                    Log.e("StudyMaterialFragment", "res : " + response.message());

                    List<Pdf_Model> data = response.body();
                    for (Pdf_Model d : data) {
                        pdf_model.add(d);
                    }
                    SetUpPdfRecycle(pdf_model);

                } else {
                    Log.e("StudyMaterialFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Pdf_Model>> call, Throwable t) {
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

    private void SetUpPdfRecycle(List<Pdf_Model> pdf_model) {
        if (pdf_model.size() == 0) {
            Log.e("StudyMaterialFragment", "Not available video");
            FancyToast.makeText(getContext(), getString(R.string.not_assign_pdf), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            recyclePdf.setHasFixedSize(true);
            recyclePdf.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_pdf = new Adapter_Pdf(getContext(), pdf_model, StudyMaterialFragment.this);
            recyclePdf.setAdapter(adapter_pdf);
            recyclePdf.setVisibility(View.VISIBLE);
            recyclePdf.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
        }
    }

    @Override
    public void onClickDownloadPdf(Pdf_Model itemModel, int position) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Log.e("StudyMaterialFragment", "pdf");
            String pdf_Path = itemModel.getNotesPath();
            String pdf_Name = itemModel.getNotesName();

            File existsFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "/CM/Notes/" + pdf_Name + ".pdf")));
            if (existsFile.exists()) {
                FancyToast.makeText(getContext(), getString(R.string.already_exists), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            } else {
                Downloder.DownloadPdfNotes(getActivity(), pdf_Path, pdf_Name);
            }

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }


    }

    //other resources
    private void GetOtherResources() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        othereResources_m_gets.clear();
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Other_R_Model>> call = api_interface.GetOtherResources("Bearer " + Login_Screen.gettoken, moduleId, SplashScreen.languageID);
        call.enqueue(new Callback<List<Other_R_Model>>() {
            @Override
            public void onResponse(Call<List<Other_R_Model>> call, Response<List<Other_R_Model>> response) {
                Log.e("StudyMaterialFragment", "onResponce : " + response.code());
                if (response.code() == 200) {

                    Log.e("StudyMaterialFragment", "res : " + response.message());

                    List<Other_R_Model> data = response.body();
                    for (Other_R_Model d : data) {
                        Log.e("StudyMaterialFragment", "Name : " + d.getOtherResourcesName() + "//" + d.getId() + "//**//" + d.getOtherResourceslength());
                        othereResources_m_gets.add(d);
                    }
                    SetUpOtherResourcesRecycle(othereResources_m_gets);

                } else {
                    Log.e("StudyMaterialFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Other_R_Model>> call, Throwable t) {
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void SetUpOtherResourcesRecycle(List<Other_R_Model> othereResources_m_gets) {
        if (othereResources_m_gets.size() == 0) {
            Log.e("StudyMaterialFragment", "Not available other resource");
            FancyToast.makeText(getContext(), getString(R.string.not_assign_other), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            recycleOther.setHasFixedSize(true);
            recycleOther.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_otherResources = new Adapter_OtherResources(getContext(), othereResources_m_gets, StudyMaterialFragment.this);
            recycleOther.setAdapter(adapter_otherResources);
            recycleOther.setVisibility(View.VISIBLE);
            recycleOther.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
        }
    }

    @Override
    public void onClickDownloadOtherResources(Other_R_Model itemModel, int position) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Log.e("StudyMaterialFragment", "pdf/video");
            String resourceType = itemModel.getOtherResourcesType();
            String other_resources_Path = itemModel.getOtherResourcesPath();
            String other_resources_Name = itemModel.getOtherResourcesName();    //Environment.DIRECTORY_DOCUMENTS, "CM/ExtraNote/" + resourcesName+".pdf")

            if (resourceType.equals(".pdf")) {

//                File existsFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS + "CM/ExtraNote/" + other_resources_Name + ".pdf")));
//                if (existsFile.exists()) {
//                    FancyToast.makeText(getContext(), getString(R.string.already_exists), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
//                } else {
                Downloder.DownloadOtherResources_PDF(getActivity(), other_resources_Path, other_resources_Name);
//                }

            } else if (resourceType.equals(".mp4")) {
                Downloder.DownloadOtherResources_VIDEO(getActivity(), other_resources_Path, other_resources_Name);
            } else {
                FancyToast.makeText(getActivity(), getString(R.string.this_is), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }
    }

    private void GetQuizeAvailability() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Quiz_Model>> call = api_interface.GetQuizeAvailability("Bearer " + Login_Screen.gettoken, moduleId, SplashScreen.languageID, trainigId, trainingSheduleId, courseId, Login_Screen.getid);
        call.enqueue(new Callback<List<Quiz_Model>>() {
            @Override
            public void onResponse(Call<List<Quiz_Model>> call, Response<List<Quiz_Model>> response) {
                Log.e("StudyMaterialFragment", "quiz: " + "onResponce_Q : " + response.code());
                if (response.code() == 200) {

                    List<Quiz_Model> data = response.body();

                    if (data.size() > 0) {

                        String quizid = data.get(0).getId();

                        if (data.get(0).getStatus() == 1) {
                            String requiredAnswers = data.get(0).getNoOfAnswerRequired();
                            SweetAlert.SweetAlertNormal(getActivity(), getString(R.string.are_you_want_quiz), getString(R.string.once_you_begin), getString(R.string.start), getString(R.string.cancel));
                            SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    PostQuizeAttempt(quizid, requiredAnswers);
                                    sweetAlertDialog.dismiss();
                                }
                            });
                            SweetAlert.pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            });
                        } else {
                            SweetAlert.SweetAlertCommand(getActivity(), getString(R.string.notice), getString(R.string.complete_watching), getString(R.string.ok), R.drawable.icon_quize_l);
                            SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.dismiss();
                                }
                            });
                            //FancyToast.makeText(getContext(), getString(R.string.watch_relevent), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                        }
                    } else {
                        FancyToast.makeText(getContext(), getString(R.string.no_quize_to_open), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
                    }
                    PrograssHud.KProgressHudStop(getActivity());

                    Log.e("StudyMaterialFragment", "quiz: " + "res_Q : " + response.message());

                } else {
                    Log.e("StudyMaterialFragment", "quiz: " + "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Quiz_Model>> call, Throwable t) {
                Log.e("StudyMaterialFragment", "quiz: " + "error : " + t.getMessage());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void PostQuizeAttempt(String quizId, String requiredAnswers) {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<UpdateAttempt_Model>> call = api_interface.PostQuizAttempt("Bearer " + Login_Screen.gettoken, "0", Login_Screen.getid, Integer.parseInt(quizId), "0", trainigId, courseId, moduleId, trainingSheduleId);
        call.enqueue(new Callback<List<UpdateAttempt_Model>>() {
            @Override
            public void onResponse(Call<List<UpdateAttempt_Model>> call, Response<List<UpdateAttempt_Model>> response) {
                Log.e("StudyMaterialFragment", "Res Attempt : " + response.code());
                if (response.code() == 200) {

                    List<UpdateAttempt_Model> data = response.body();
                    String attemptId = data.get(0).getId();
                    GetQuize(quizId, attemptId, requiredAnswers);
                    Log.e("StudyMaterialFragment", "Attempt id: " + attemptId);
                } else {
                    Log.e("StudyMaterialFragment", "not success");
                }
            }

            @Override
            public void onFailure(Call<List<UpdateAttempt_Model>> call, Throwable t) {
                Log.e("StudyMaterialFragment", "Error");
            }
        });
    }

    private void GetQuize(String quizid, String attempt, String requiredAnswers) {
        Bundle bundle = new Bundle();
        bundle.putString("QuizeID", quizid);
        bundle.putString("no_of_attempt", attempt);
        bundle.putString("moduleID", moduleId);
        bundle.putString("CourseID", courseId);
        bundle.putString("TrainigId", trainigId);
        bundle.putString("TrainigSheduleId", trainingSheduleId);
        bundle.putString("RequiredAnswers", requiredAnswers);
        bundle.putString("ModuleName", moduleName);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new QuizeFragment();
        mFrag.setArguments(bundle);
        t.replace(R.id.frame, mFrag);
        t.commit();
        PrograssHud.KProgressHudStop(getActivity());
    }


}
