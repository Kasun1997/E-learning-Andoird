package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.R;

import java.io.File;
import java.util.List;

public class Adapter_Download extends RecyclerView.Adapter<Adapter_Download.ViewHolder> {

    private Context context;
    private List<File> videoFiles;
    private AdapterMultipleClickEvent clickEvent;

    public Adapter_Download(Context context, List<File> videoFiles, AdapterMultipleClickEvent clickEvent) {
        this.context = context;
        this.videoFiles = videoFiles;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.itemview_downloaded, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final File file = videoFiles.get(position);
        holder.txvwVideoName.setText(videoFiles.get(position).getName());
        holder.imgIcon.setImageResource(R.drawable.view_video);

        holder.imgIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.onClickPlayVideo(file, position);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.onClickDeleteVideo(file, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvwVideoName;
        ImageView imgIcon,imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txvwVideoName = itemView.findViewById(R.id.txvwTrainingName);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            imgDelete = itemView.findViewById(R.id.imgdelete);
        }
    }

    public interface AdapterMultipleClickEvent { //for button click event
        void onClickPlayVideo(File itemModel, int position);
        void onClickDeleteVideo(File itemModel, int position);

    }
}
