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

import com.crystalmartin.crystalmartinelearning.Model_Real.Training_Model;

import com.crystalmartin.crystalmartinelearning.R;

import java.util.List;

public class Adapter_Trainings extends RecyclerView.Adapter<Adapter_Trainings.ViewHolder> {

    private Context context;
    private List<Training_Model> training_model;
    private OnItemClickListener mListener;

    public Adapter_Trainings(Context context, List<Training_Model> training_model) {
        this.context = context;
        this.training_model = training_model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_training, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Training_Model model = training_model.get(position);

        if (model.getStatus()==0) {
            holder.imageView.setImageResource(R.drawable.training_in_prograss);
            holder.trainingName.setText(model.getTrainingName());
        } else {
            holder.imageView.setImageResource(R.drawable.training_completed);
            holder.trainingName.setText(model.getTrainingName());
        }

//        holder.imageView.setImageResource(R.drawable.training_in_prograss);
//        holder.trainingName.setText(context.getString(R.string.structure_of_training) +" - "+ model.getTrainingName());
    }

    @Override
    public int getItemCount() {
        return training_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView trainingName;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trainingName = itemView.findViewById(R.id.txvwTrainingName);
            imageView = itemView.findViewById(R.id.imgIcon);
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
