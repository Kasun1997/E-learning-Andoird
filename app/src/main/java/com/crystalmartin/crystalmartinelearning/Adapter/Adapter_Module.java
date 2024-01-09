package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Model_Real.Module_Model;
import com.crystalmartin.crystalmartinelearning.R;

import java.util.List;

public class Adapter_Module extends RecyclerView.Adapter<Adapter_Module.ViewHolder> {

    private Context context;
    private List<Module_Model> module_model;
    private OnItemClickListener mListener;

    public Adapter_Module(Context context, List<Module_Model> module_model) {
        this.context = context;
        this.module_model = module_model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_module, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Module_Model model = module_model.get(position);

        if (model.getStatus()==0) {
            holder.moduleName.setText(model.getCourseName());
            holder.moduleName.setTextColor(ContextCompat.getColor(context,R.color.prohibited));
            holder.moduleNumber.setText(model.getModuleOrderNumber());
        }else {
            holder.moduleName.setText(model.getCourseName());
            holder.moduleName.setTextColor(ContextCompat.getColor(context,R.color.main_hardcord_text_color));
            holder.moduleNumber.setText(model.getModuleOrderNumber());
        }


    }

    @Override
    public int getItemCount() {
        return module_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView moduleName, moduleNumber;
        ImageView downIcon_1, downIcon_2, downIcon_3, downIcon_4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.txwvName);
            moduleNumber = itemView.findViewById(R.id.txwvNo);

            downIcon_1 = itemView.findViewById(R.id.downIcon_1);
            downIcon_2 = itemView.findViewById(R.id.downIcon_2);
            downIcon_3 = itemView.findViewById(R.id.downIcon_3);
            downIcon_4 = itemView.findViewById(R.id.downIcon_4);

            AnimationDrawable drawables = (AnimationDrawable) moduleNumber.getBackground();
            drawables.setEnterFadeDuration(1000);
            drawables.setExitFadeDuration(2500);
            drawables.start();

            AnimationDrawable drawables_1 = (AnimationDrawable) downIcon_1.getBackground();
            drawables_1.setEnterFadeDuration(1000);
            drawables_1.setExitFadeDuration(2500);
            drawables_1.start();

            AnimationDrawable drawables_2 = (AnimationDrawable) downIcon_2.getBackground();
            drawables_2.setEnterFadeDuration(1000);
            drawables_2.setExitFadeDuration(2500);
            drawables_2.start();

            AnimationDrawable drawables_3 = (AnimationDrawable) downIcon_3.getBackground();
            drawables_3.setEnterFadeDuration(1000);
            drawables_3.setExitFadeDuration(2500);
            drawables_3.start();

            AnimationDrawable drawables_4 = (AnimationDrawable) downIcon_4.getBackground();
            drawables_4.setEnterFadeDuration(1000);
            drawables_4.setExitFadeDuration(2500);
            drawables_4.start();

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
