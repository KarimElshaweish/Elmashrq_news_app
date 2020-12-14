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
import com.mashreq.app.databinding.ReportsRecBinding;
import com.mashreq.app.model.modeldb.Report.Datum;
import com.mashreq.app.model.modeldb.Report.ReportsModel;
import com.mashreq.app.view.actvivties.Details;

import java.util.List;

public class ReportsAdapter
    extends RecyclerView.Adapter<ReportsAdapter.ResultViewHolder> {

  private List<Datum> resultModelList;
  Context  context;
  public ReportsAdapter(Context context) {
    this.context = context;
  }
  @NonNull
  @Override
  public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      ReportsRecBinding itemNewsBinding =
              DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
            R.layout.reports_rec, viewGroup, false);
    return new ResultViewHolder(itemNewsBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ResultViewHolder ResultViewHolder, final int i) {

    if(resultModelList!=null) {
      Datum data = resultModelList.get(i);
      Glide.with(context).load(data.getSourceLogo()).into(ResultViewHolder.itemNewsBinding.logo);
      ResultViewHolder.itemNewsBinding.newsName.setText(data.getSource());
      ResultViewHolder.itemNewsBinding.title.setText(data.getTitle());
      Glide.with(context).load(data.getImage()).into(ResultViewHolder.itemNewsBinding.newsImage);
    }
  ResultViewHolder.itemNewsBinding.card.setOnClickListener(new View.OnClickListener() {
  @Override
  public void onClick(View view) {
    /* Create an Intent that will start the Menu-Activity. */
    Intent mainIntent = new Intent(context, Details.class);
    mainIntent.putExtra("id",String.valueOf(resultModelList.get(i).getId()));
    mainIntent.putExtra("newsName", resultModelList.get(i).getSource());
    mainIntent.putExtra("title", resultModelList.get(i).getTitle());
    mainIntent.putExtra("content", resultModelList.get(i).getContent());
    mainIntent.putExtra("image", resultModelList.get(i).getImage());
    mainIntent.putExtra("link",resultModelList.get(i).getLink());
    // mainIntent.putExtra("",);
    context.startActivity(mainIntent);
    Animatoo.animateSwipeRight(context); //fire the slide left animation
  }
});

  }

  @Override
  public int getItemCount() {
    if (resultModelList != null) {
      return resultModelList.size();
    } else {
      return 10;
    }
  }

  public void setnewsList(List<Datum> resultModels) {
    this.resultModelList = resultModels;
    notifyDataSetChanged();
  }

  class ResultViewHolder extends RecyclerView.ViewHolder {
    private ReportsRecBinding  itemNewsBinding;

    public ResultViewHolder(@NonNull ReportsRecBinding itemNewsBinding) {
      super(itemNewsBinding.getRoot());
      this.itemNewsBinding = itemNewsBinding;
    }
  }
}
