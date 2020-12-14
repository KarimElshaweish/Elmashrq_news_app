package com.mashreq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.CutsomeGridViewLiveStreamBinding;
import com.mashreq.app.model.modeldb.LiveStreamDB.LiveSteamDatum;
import com.mashreq.app.view.actvivties.LiveStreamPlayerActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ViewGroup> {
    Context _ctx;
    List<LiveSteamDatum>liveSteamData;

    public ChannelAdapter(Context _ctx) {
        this._ctx = _ctx;
    }
    @NotNull
    @Override
    public ViewGroup onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        CutsomeGridViewLiveStreamBinding itemBinding=
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.cutsome_grid_view_live_stream,parent,false);
        return new ViewGroup(itemBinding);
    }

    public void setNewList(List<LiveSteamDatum> list){
        liveSteamData=list;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewGroup holder, final int position) {
        if (liveSteamData != null&&position<liveSteamData.size()) {
            holder.itembinding.channelName.setText(liveSteamData.get(position).getName());
            Glide.with(_ctx).load(liveSteamData.get(position).getImage()).into(holder.itembinding.channelLogo);
            holder.itembinding.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url=liveSteamData.get(position).getLink();
                    Intent intent=new Intent(_ctx, LiveStreamPlayerActivity.class);
                    intent.putExtra("url",url);
                    intent.putExtra("logo",liveSteamData.get(position).getImage());
                    intent.putExtra("name",liveSteamData.get(position).getName());
                    _ctx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(liveSteamData!=null)
            if(liveSteamData.size()==0)
                return 2;
            else
                return liveSteamData.size();
        return 5;
    }

    public class ViewGroup extends RecyclerView.ViewHolder {
        private CutsomeGridViewLiveStreamBinding itembinding;
        public ViewGroup(@NonNull CutsomeGridViewLiveStreamBinding itemView) {
            super(itemView.getRoot());
            this.itembinding=itemView;
        }
    }
}
