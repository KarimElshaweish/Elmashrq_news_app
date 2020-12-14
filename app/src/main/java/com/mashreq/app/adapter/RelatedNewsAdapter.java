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
import com.mashreq.app.databinding.RelatedNewsItemBinding;
import com.mashreq.app.model.modeldb.SingleNew.RelatedNews;
import com.mashreq.app.view.actvivties.Details;

import java.util.List;

import okhttp3.internal.Util;

public class RelatedNewsAdapter extends RecyclerView.Adapter<RelatedNewsAdapter.ViewGroup> {
    List<RelatedNews>relatedNewsList;
    Context _ctx;
    @NonNull
    @Override
    public ViewGroup onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        RelatedNewsItemBinding itemBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.related_news_item,parent,false);
        this._ctx=parent.getContext();
        return new ViewGroup(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroup item, final int position) {

        if(relatedNewsList!=null){
            item.relatedNewsItemBinding.createdAt.setText(relatedNewsList.get(position).getCreatedAt());
            item.relatedNewsItemBinding.title.setText(relatedNewsList.get(position).getTitle());
            Glide.with(_ctx).load(relatedNewsList.get(position).getImage()).into(item.relatedNewsItemBinding.image);
        }

        item.relatedNewsItemBinding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(_ctx, Details.class);
                mainIntent.putExtra("id",String.valueOf(relatedNewsList.get(position).getId()));
                mainIntent.putExtra("newsName", relatedNewsList.get(position).getSource());
                mainIntent.putExtra("title", relatedNewsList.get(position).getTitle());
                mainIntent.putExtra("content", relatedNewsList.get(position).getContent());
                mainIntent.putExtra("image", relatedNewsList.get(position).getImage());
                mainIntent.putExtra("link",relatedNewsList.get(position).getLink());
                // mainIntent.putExtra("",);
                _ctx.startActivity(mainIntent);
                Animatoo.animateSwipeRight(_ctx); //fire the slide left animation

            }
        });
    }

    @Override
    public int getItemCount() {
        if(relatedNewsList==null)
            return 4;
        return  relatedNewsList.size();
    }

    public void  setNewsRelatedList(List<RelatedNews> relatedList){
        this.relatedNewsList=relatedList;
        notifyDataSetChanged();
    }

    public class ViewGroup extends RecyclerView.ViewHolder {
        RelatedNewsItemBinding relatedNewsItemBinding;
        public ViewGroup(@NonNull RelatedNewsItemBinding itemView) {
            super(itemView.getRoot());
            this.relatedNewsItemBinding=itemView;
        }
    }
}
