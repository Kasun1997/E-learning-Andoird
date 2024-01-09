package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Model_Real.Course_Model;
import com.crystalmartin.crystalmartinelearning.R;

import java.util.List;

public class Adapter_Course extends RecyclerView.Adapter<Adapter_Course.ViewHolder> {

    private Context context;
    private List<Course_Model> course_model;
    private OnItemClickListener mListener;
    private AdapterMultipleClickEvent clickEvent;

    public Adapter_Course(Context context, List<Course_Model> course_model, AdapterMultipleClickEvent clickEvent) {
        this.context = context;
        this.course_model = course_model;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_training, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Course_Model model = course_model.get(position);

        if (model.getStatus()==0) {
            holder.imageView.setImageResource(R.drawable.training_in_prograss);
            holder.trainingName.setText(model.getCourse_Name());
            holder.imageQuiz.setVisibility(View.VISIBLE);
        } else {
            holder.imageView.setImageResource(R.drawable.training_completed);
            holder.trainingName.setText(model.getCourse_Name());
            holder.imageQuiz.setVisibility(View.VISIBLE);
        }

//        holder.imageView.setImageResource(R.drawable.training_in_prograss);
//        holder.trainingName.setText(context.getString(R.string.structure_of_training) +" "+ model.getCourse_Name());
//        holder.trainingName.setText(model.getCourse_Name());

        holder.imageQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.onClickOpenQuiz(model,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return course_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trainingName;
        ImageView imageView, imageQuiz;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainingName = itemView.findViewById(R.id.txvwTrainingName);
            imageView = itemView.findViewById(R.id.imgIcon);
            imageQuiz = itemView.findViewById(R.id.imgQuiz);
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

    public interface AdapterMultipleClickEvent {
        void onClickOpenQuiz(Course_Model model, int position);
    }
}
