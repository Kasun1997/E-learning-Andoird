package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Course;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.Model_Real.Course_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAttemptUpdate_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAvailability_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuiz_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.SplashScreen;
import com.crystalmartin.crystalmartinelearning.SubContext.Network;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseFragment extends Fragment implements Adapter_Course.OnItemClickListener, Adapter_Course.AdapterMultipleClickEvent {
    private View view;

    private RecyclerView recyclerView;
    private Adapter_Course adapter_course;
    private List<Course_Model> course_model;
    private String trainigId, trainigName;
    private TextView txtCoursename;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_course, container, false);


        Bundle bundle = this.getArguments();
        trainigId = bundle.getString("TrainingID");
        trainigName = bundle.getString("TrainingName");
        Log.e("CHECK_ALL_ID", trainigId);
        Log.e("CourseFragment", "From_fragment_bundle : " + trainigId + "//" + trainigName);

        recyclerView = view.findViewById(R.id.recycleCourse);
        txtCoursename = view.findViewById(R.id.txtvwCoursename);
        txtCoursename.setText(trainigName);
        course_model = new ArrayList<>();

        GetAllTrainings();

        return view;
    }

    public void GetAllTrainings() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Log.e("CourseFragment", "token and id : " + Login_Screen.gettoken + "//" + Integer.parseInt(Login_Screen.getid));

        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<Course_Model>> call = api_interface.GetCourse("Bearer " + Login_Screen.gettoken, Login_Screen.getid, trainigId);
        call.enqueue(new Callback<List<Course_Model>>() {
            @Override
            public void onResponse(Call<List<Course_Model>> call, Response<List<Course_Model>> response) {
                Log.e("CourseFragment", "onResponce : " + response.code());//401/200

                if (response.code() == 200) {

                    Log.e("CourseFragment", "res : " + response.message());

                    List<Course_Model> data = response.body();
                    for (Course_Model d : data) {
                        Log.e("CourseFragment", "Name : " + d.getCourse_Name() + "//" + d.getCourse_Id());
                        Log.e("CourseFragment", "Size : " + data.size());
                        course_model.add(d);
                    }
                    SetUpRecycler(course_model);

                } else {
                    Log.e("CourseFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<Course_Model>> call, Throwable t) {
                Log.e("CourseFragment", "onFailure : " + t.getMessage());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void SetUpRecycler(List<Course_Model> course_model) {

        if (course_model.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.not_available), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            Log.e("CheckRecyclerSize", "" + course_model.size());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_course = new Adapter_Course(getContext(), course_model, CourseFragment.this);
            recyclerView.setAdapter(adapter_course);
            recyclerView.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());
            adapter_course.setOnItemClickListener(CourseFragment.this);
        }
    }

    @Override
    public void onItemClick(int position) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Course_Model mGet = course_model.get(position);

            Bundle bundle = new Bundle();
            bundle.putString("CourseID", String.valueOf(mGet.getCourse_Id()));
            bundle.putString("TrainigId", trainigId);
            bundle.putString("CourseName", mGet.getCourse_Name());

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction t = fragmentManager.beginTransaction();
            Fragment mFrag = new ModuleFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.frame, mFrag);
            t.addToBackStack(CourseFragment.class.getName());
            t.commit();

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }
    }

    @Override
    public void onClickOpenQuiz(Course_Model model, int position) {
        Log.e("Quized", model.getCourse_Name());
        if (model.getStatus()==1){ //1
            GetFinalExamAvailability(String.valueOf(model.getCourse_Id()));
        }else {
            FancyToast.makeText(getActivity(), getString(R.string.complete_all_the_modules), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
        }
    }

    private void GetFinalExamAvailability(String course_id) {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamAvailability_Model>> call = api_interface.GetFinalExamAvailability("Bearer " + Login_Screen.gettoken, course_id, SplashScreen.languageID);
        call.enqueue(new Callback<List<FinalExamAvailability_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamAvailability_Model>> call, Response<List<FinalExamAvailability_Model>> response) {
                Log.e("CourseFragment", "onResponce : " + response.code());
                List<FinalExamAvailability_Model> data = response.body();
                if (data.size() == 0) {
                    Log.e("CourseFragment", "zero");
                    FancyToast.makeText(getActivity(),getString(R.string.no_final_exam) , FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                } else {
                    Log.e("ModuleFragment", "Namem : " + data.get(0).getExamName() + "//" + data.get(0).getTrainingExam_Id() + "///" + data.size() + "/////" + data.get(0).getOnline());
                    if (data.get(0).getOnline() == true) { //default true
                        Log.e("CourseFragment","final " + "true");
                        BuilderTrue(course_id);
                    } else {
                        Log.e("CourseFragment","final " + "false");
                        BuilderFales(data.get(0).getExamName(), data.get(0).getTrainingSheduleExam_Date(), data.get(0).getTrainingSheduleExam_StartTime(), data.get(0).getTrainingSheduleExam_EndTime(), data.get(0).getTrainingLocation_Name(), course_id);
                    }
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<FinalExamAvailability_Model>> call, Throwable t) {
                Log.e("CourseFragment", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void BuilderTrue(String course_id) {

        SweetAlert.SweetAlertNormal(getActivity(), getString(R.string.are_you_want_quiz), getString(R.string.once_you_begin), getString(R.string.start), getString(R.string.cancel));
        SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
               GetFinalQuiz(course_id);
                sweetAlertDialog.dismiss();
            }
        });
        SweetAlert.pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
            }
        });
    }

    private void BuilderFales(String examName, String examDate, String startTime, String endTime, String locationName, String course_id) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setIcon(R.drawable.final_exam);
        alertDialog.setTitle("Final Exam");
        alertDialog.setMessage("Exam Name : " + examName + "\n" + "Exam Date : " + examDate + "\n" + "Start Time : " + startTime + "\n" + "End Time : " + endTime + "\n" + "Location : " + locationName);

        alertDialog.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    private void GetFinalQuiz(String course_id) {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamQuiz_Model>> call = api_interface.GetFinalExam("Bearer " + Login_Screen.gettoken, course_id, SplashScreen.languageID);
        call.enqueue(new Callback<List<FinalExamQuiz_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamQuiz_Model>> call, Response<List<FinalExamQuiz_Model>> response) {
                List<FinalExamQuiz_Model> data = response.body();
                if (data.size() == 0) {
                    Log.e("CourseFragment","final " + "nodata - getfinal quiz");
                    FancyToast.makeText(getActivity(), getString(R.string.no_final_exam), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                } else {
                    Log.e("ModuleFragment", "quiz : " + data.get(0).getExamQuizz_Id() + "//" + data.get(0).getExamQuizzName() + "///" + data.size());
                    GetFinalExamAttempt(data.get(0).getExamQuizz_Id(),data.get(0).getCorrectAnswers());
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<FinalExamQuiz_Model>> call, Throwable t) {
                Log.e("CourseFragment", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });

    }

    private void GetFinalExamAttempt(String finalExamId,String requiredAnswers) {
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamAttemptUpdate_Model>> call = api_interface.GetFinalExamAttempt("Bearer " + Login_Screen.gettoken, "0", Login_Screen.getid, finalExamId, "0");
        call.enqueue(new Callback<List<FinalExamAttemptUpdate_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamAttemptUpdate_Model>> call, Response<List<FinalExamAttemptUpdate_Model>> response) {
                List<FinalExamAttemptUpdate_Model> data = response.body();
                if (response.code()==200){
                    if (data.size() == 0) {
                        Log.e("CourseFragment","final " + "nodata - getfinal exam");
                        FancyToast.makeText(getActivity(), getString(R.string.no_quize_to_open), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        PrograssHud.KProgressHudStop(getActivity());
                    } else {
                        Log.e("CourseFragment", "quizAttempt : " + data.get(0).getAttemptId());
                        Log.e("CourseFragment","final " + "move - getfinal exam");
                        MoveToFinalExam(finalExamId, data.get(0).getAttemptId(),requiredAnswers);
                        PrograssHud.KProgressHudStop(getActivity());
                    }
                }else {
                    FancyToast.makeText(getActivity(), getString(R.string.no_quize_to_open), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<FinalExamAttemptUpdate_Model>> call, Throwable t) {
                Log.e("CourseFragment", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void MoveToFinalExam(String final_exam_id, String attemptRawId,String requiredAnswers) {
        Network network = new Network(getActivity());
        if (network.isNetworkAvailable(getActivity())) {

            Bundle bundle = new Bundle();
            bundle.putString("FinalExamID", final_exam_id);
            bundle.putString("AttemptRawId", attemptRawId);
            bundle.putString("RequiredAnswers", requiredAnswers);

            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction t = fragmentManager.beginTransaction();
            Fragment mFrag = new FinalExamFragment();
            mFrag.setArguments(bundle);
            t.replace(R.id.frame, mFrag);
            t.addToBackStack(CourseFragment.class.getName());
            t.commit();

        } else {
            FancyToast.makeText(getActivity(), getString(R.string.wifi_or_mobile_data), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        }
    }
}