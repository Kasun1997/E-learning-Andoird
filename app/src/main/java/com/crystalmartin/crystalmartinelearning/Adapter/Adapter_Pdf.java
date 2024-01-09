package com.crystalmartin.crystalmartinelearning.Adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Model_Real.Pdf_Model;

import com.crystalmartin.crystalmartinelearning.R;

import java.util.List;

public class Adapter_Pdf extends RecyclerView.Adapter<Adapter_Pdf.ViewHolder>{
    private Context context;
    private List<Pdf_Model> pdf_model;
    private AdapterMultipleClickEvent clickEvent;

    public Adapter_Pdf(Context context, List<Pdf_Model> pdf_model, AdapterMultipleClickEvent clickEvent) {
        this.context = context;
        this.pdf_model = pdf_model;
        this.clickEvent = clickEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview_download_meterial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pdf_Model model = pdf_model.get(position);
        holder.txvwVideoname.setText(model.getNotesName());
        holder.imgMeterialLogo.setImageResource(R.drawable.view_pdf);
        holder.imgMeterialLogo.setVisibility(View.INVISIBLE);
        int no = position+1;
        holder.pdfNumber.setText(String.valueOf(no));
        holder.imgDownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.onClickDownloadPdf(model,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdf_model.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txvwVideoname,pdfNumber;;
        ImageView imgMeterialLogo,imgDownloadBtn,downIcon_1, downIcon_2, downIcon_3, downIcon_4;
        ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txvwVideoname=itemView.findViewById(R.id.txwvName);
            imgMeterialLogo=itemView.findViewById(R.id.imgLogo);
            imgDownloadBtn=itemView.findViewById(R.id.imgDownloadBtn);

            pdfNumber = itemView.findViewById(R.id.txwvNo);
            downIcon_1 = itemView.findViewById(R.id.downIcon_1);
            downIcon_2 = itemView.findViewById(R.id.downIcon_2);
            downIcon_3 = itemView.findViewById(R.id.downIcon_3);
            downIcon_4 = itemView.findViewById(R.id.downIcon_4);

            AnimationDrawable drawables = (AnimationDrawable) pdfNumber.getBackground();
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
        }
    }

    public interface AdapterMultipleClickEvent { //for button click event
        void onClickDownloadPdf(Pdf_Model itemModel, int position);
    }
}
