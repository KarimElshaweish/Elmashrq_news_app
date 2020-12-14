package com.mashreq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.CitesLayoutBinding;
import com.mashreq.app.model.modeldb.Cities.CitesModel;
import com.mashreq.app.model.modeldb.Cities.City;
import com.mashreq.app.view.actvivties.citesActivity;

import java.util.List;

public class CitesAdapter extends RecyclerView.Adapter<CitesAdapter.ViewGroup> {
    CitesLayoutBinding citesLayoutBinding;
    Context _ctx;
    List<City>citesModelList;

    public CitesAdapter(Context _ctx) {
        this._ctx = _ctx;
    }

    @NonNull
    @Override
    public ViewGroup onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
        citesLayoutBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.cites_layout,parent,false);
        return new ViewGroup(citesLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewGroup holder, int position) {
        if(citesModelList!=null) {
            final City city = citesModelList.get(position);
            holder.citesLayoutBinding.cityName.setText(city.getName());
            Glide.with(_ctx).load(city.getImage()).into(holder.citesLayoutBinding.image);
            holder.citesLayoutBinding.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(_ctx,citesActivity.class);
                    intent.putExtra("city_name",city.getName());
                    intent.putExtra("city_id",city.getId());
                    _ctx.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(citesModelList==null)
            return 0;
        return citesModelList.size();
    }

    public void setNewList(List<City>newList){
        this.citesModelList=newList;
        notifyDataSetChanged();
    }
    public class ViewGroup extends RecyclerView.ViewHolder {
        private CitesLayoutBinding citesLayoutBinding;
        public ViewGroup(@NonNull CitesLayoutBinding itemView) {
            super(itemView.getRoot());
            this.citesLayoutBinding=itemView;
        }
    }
}
