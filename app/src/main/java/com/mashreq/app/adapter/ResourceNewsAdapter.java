package com.mashreq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.NewsRecBinding;
import com.mashreq.app.model.modeldb.ElmashrqNews.LatestNews;
import com.mashreq.app.model.modeldb.ResourceNews.Data;
import com.mashreq.app.model.modeldb.ResourceNews.ResourceNews;
import com.mashreq.app.view.actvivties.Details;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResourceNewsAdapter extends RecyclerView.Adapter<ResourceNewsAdapter.ViewGroup> {
    private List<Data> resultModelList=new ArrayList<>();
    Context context;
    @NonNull
    @Override
    public ViewGroup onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        context=parent.getContext();
        NewsRecBinding itemNewsBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.news_rec, parent, false);
        return new ViewGroup(itemNewsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroup ResultViewHolder, final int i) {
        if(resultModelList.size()>0) {
            ResultViewHolder.itemNewsBinding.content.setText(resultModelList.get(i).getTitle());
            ResultViewHolder.itemNewsBinding.newsGateName.setText(resultModelList.get(i).getSource());
            // ResultViewHolder.itemNewsBinding.date.setText(resultModelList.get(i).getFrom_time());

            Glide.with(context).load(resultModelList.get(i).getImage()).placeholder(R.drawable.image_de).into(
                    ResultViewHolder.itemNewsBinding.imageContent
            );
            ResultViewHolder.itemNewsBinding.cardItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(context, Details.class);
                    mainIntent.putExtra("newsName", resultModelList.get(i).getSource());
                    mainIntent.putExtra("title", resultModelList.get(i).getTitle());
                    mainIntent.putExtra("content", resultModelList.get(i).getContent());
                    mainIntent.putExtra("image", resultModelList.get(i).getImage());
                    // mainIntent.putExtra("",);
                    context.startActivity(mainIntent);
                    Animatoo.animateSwipeRight(context); //fire the slide left animation
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (resultModelList != null) {
            if(resultModelList.size()!=0)
                return resultModelList.size();
            else
                return 10;
        } else {
            return 10;
        }
    }
    public void setnewsList(List<Data> resultModels) {
        this.resultModelList = resultModels;
        notifyDataSetChanged();
    }
    public class ViewGroup extends RecyclerView.ViewHolder {
        private NewsRecBinding  itemNewsBinding;
        public ViewGroup(@NonNull NewsRecBinding itemView) {
            super(itemView.getRoot());
            this.itemNewsBinding = itemView;
        }
    }
}
