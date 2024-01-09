package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.hsalf.smilerating.SmileRating;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.BottomNavigation.Main_Container;
import com.crystalmartin.crystalmartinelearning.Model_Real.Rating_Model;
import com.crystalmartin.crystalmartinelearning.Model_Sub.Rating_M_Get;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RatingFragment extends Fragment {

    private View view;
    private SmileRating smileyRating_1, smileyRating_2, smileyRating_3, smileyRating_4;
    private ArrayList<Rating_M_Get> arrayList;
    private String Id;
    private TextView txvwSend;
    private String moduleId;
    private EditText txvwDescription;
    private ImageView imgClose;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rating, container, false);
        CancelOnBackPress();

        Bundle bundle = this.getArguments();
        moduleId = bundle.getString("moduleID");

        Initializing();

        arrayList = new ArrayList<>();

        txvwSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmitRating();
            }
        });

        smileyRating_1.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                Id = "0";
                Rating(Id, smiley);
            }
        });

        smileyRating_2.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                Id = "1";
                Rating(Id, smiley);
            }
        });

        smileyRating_3.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                Id = "2";
                Rating(Id, smiley);
            }
        });

        smileyRating_4.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {
                Id = "3";
                Rating(Id, smiley);
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToHome();
            }
        });

        return view;
    }

    private void Initializing() {
        smileyRating_1 = view.findViewById(R.id.smile_rating_1);
        smileyRating_2 = view.findViewById(R.id.smile_rating_2);
        smileyRating_3 = view.findViewById(R.id.smile_rating_3);
        smileyRating_4 = view.findViewById(R.id.smile_rating_4);
        txvwSend = view.findViewById(R.id.txvwSend);
        imgClose = view.findViewById(R.id.imgClose);
        txvwDescription = view.findViewById(R.id.txvwDescription);
    }

    private void Rating(String id, int smiley) {

        if (smiley == 0) {
            Log.e("RatingFragment", "Question id : " + id + " Terible");
            GetData(id, "1");
        }
        if (smiley == 1) {
            Log.e("RatingFragment", "Question id : " + id + " Bad");
            GetData(id, "2");
        }
        if (smiley == 2) {
            Log.e("RatingFragment", "Question id : " + id + " Okay");
            GetData(id, "3");
        }
        if (smiley == 3) {
            Log.e("RatingFragment", "Question id : " + id + " Good");
            GetData(id, "4");
        }
        if (smiley == 4) {
            Log.e("RatingFragment", "Question id : " + id + " Great");
            GetData(id, "5");
        }
    }

    private void GetData(String id, String status) {
        if (arrayList.size() < 4) {
            arrayList.add(new Rating_M_Get(id, status));
        } else {
            arrayList.set(Integer.parseInt(id), new Rating_M_Get(id, status));
        }
    }


    private void SubmitRating() {
        if (arrayList.size() > 0) {
            ViewArrayData(arrayList);
        } else {
            Log.e("RatingFragment", "array is empty");
        }
    }

    private void ViewArrayData(List<Rating_M_Get> rating) {

        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);

        for (int i = 0; i < rating.size(); i++) {

            String rate = rating.get(i).getStatus();
            Log.e("RatingFragment", "Rating : " + rate);

            Call<List<Rating_Model>> call = api_interface.PostFeedBack("Bearer " + Login_Screen.gettoken, rate, Login_Screen.getid, moduleId, txvwDescription.getText().toString());
            call.enqueue(new Callback<List<Rating_Model>>() {
                @Override
                public void onResponse(Call<List<Rating_Model>> call, Response<List<Rating_Model>> response) {
                    Log.e("RatingFragment", "responce is : " + response.code());
                    if (response.code() == 200) {

                        PrograssHud.KProgressHudStop(getActivity());
                        MoveToHome();
                    } else {
                        PrograssHud.KProgressHudStop(getActivity());
                    }
                }

                @Override
                public void onFailure(Call<List<Rating_Model>> call, Throwable t) {
                    Log.e("RatingFragment", "error" + t.getMessage());
                    PrograssHud.KProgressHudStop(getActivity());
                }
            });
        }
    }

    private void MoveToHome() {
        Intent fragIntent = new Intent(getActivity(), Main_Container.class);
        getActivity().startActivity(fragIntent);
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
