package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAnswers_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.FinalExamFragment;
import com.crystalmartin.crystalmartinelearning.Screen.Fragment.QuizeFragment;

import java.util.Collections;
import java.util.List;

public class Adapter_Final_Answer extends RecyclerView.Adapter<Adapter_Final_Answer.ViewHolder> {

    private Context context;
    private List<FinalExamAnswers_Model> quizAnswer_m_gets;
    private OnItemClickListener mListener;
    private static int lastClickedPosition = -1;
    private int selectedpostion = -1;

    public Adapter_Final_Answer(Context context, List<FinalExamAnswers_Model> quizAnswer_m_gets) {
        this.context = context;
        this.quizAnswer_m_gets = quizAnswer_m_gets;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_answer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FinalExamAnswers_Model model = quizAnswer_m_gets.get(position);

        int no = position + 1;
        holder.txvwAnsNo.setText(String.valueOf(no));

        if (model.getExamAnswers_Id().equals(FinalExamFragment.savedAnswerId)) {
            holder.txvwAnswer.setTextColor(context.getResources().getColor(R.color.AlertYellow));
            holder.txvwAnswer.setText(model.getExamAnswersName());
        } else {
            holder.rLayout.setBackgroundColor(Color.WHITE);
            holder.txvwAnswer.setText(model.getExamAnswersName());

        }
//
//        if (QuizeFragment.savedAnswerId==null){
//            Log.e("Adapter_Answer", "QuizeFragment.savedAnswerId");
//        }else {
//            Log.e("Adapter_Answer", QuizeFragment.savedAnswerId);
//        }
//-------------------------------------------------------------------------------------------
        if (selectedpostion == position) {
            // holder.rLayout.setBackgroundColor(Color.BLUE);
            holder.rLayout.setBackgroundResource(R.drawable.button_background);
        } else {
            holder.rLayout.setBackgroundColor(Color.WHITE);
        }

    }

    public void setAnswerShuffle(List<FinalExamAnswers_Model> answer){
        Collections.shuffle(answer);
        quizAnswer_m_gets=answer;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return quizAnswer_m_gets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txvwAnswer, txvwAnsNo;
        RelativeLayout rLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvwAnswer = itemView.findViewById(R.id.txwvName);
            txvwAnsNo = itemView.findViewById(R.id.txwvNo);
            rLayout = itemView.findViewById(R.id.rLayout);

            AnimationDrawable drawables = (AnimationDrawable) txvwAnsNo.getBackground();
            drawables.setEnterFadeDuration(1000);
            drawables.setExitFadeDuration(2500);
            drawables.start();


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                    selectedpostion = position;
                    notifyDataSetChanged();

                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
