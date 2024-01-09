package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.Model_final_exam_history;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Model_quiz_history;
import com.crystalmartin.crystalmartinelearning.R;

import java.util.List;

public class Adapter_Final_Exam_Summery extends RecyclerView.Adapter<Adapter_Final_Exam_Summery.ViewHolder> {

    private Context context;
    private List<Model_final_exam_history> quizAnswer_m_gets;
    private OnItemClickListener mListener;
    private static MutableLiveData<List<Model_final_exam_history>> allMutableCartItems = new MediatorLiveData<>();


    public Adapter_Final_Exam_Summery(Context context) {
        this.context = context;
    }

    public void setSummeryList(List<Model_final_exam_history> cartItems) {
        this.quizAnswer_m_gets = cartItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_summery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Model_final_exam_history model = quizAnswer_m_gets.get(position);

        holder.txwvAnswer.setText(model.getQuiz_question());
        Log.e("AAAA","uuuuu"+model.getCorrect_status());
        holder.qid.setText("Question " + String.valueOf(position+1));
        if (model.getCorrect_status().equals("1")) {
            holder.txvwAnswerStatus.setText("Correct");
            holder.txvwAnswerStatus.setBackgroundResource(R.drawable.answer_correct_backgrnd);

        } else {
            holder.txvwAnswerStatus.setText("Wrong");
            holder.txvwAnswerStatus.setBackgroundResource(R.drawable.answer_wrong_backgrnd);
        }


    }

    @Override
    public int getItemCount() {
        return quizAnswer_m_gets.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView qid, txwvAnswer, txvwAnswerStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvwAnswerStatus = itemView.findViewById(R.id.txvwAnswerStatus);
            qid = itemView.findViewById(R.id.qid);
            txwvAnswer = itemView.findViewById(R.id.txwvAnswer);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
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
