package com.mashreq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.databinding.VersionItemBinding;
import com.mashreq.app.model.modeldb.export.Datum;
import com.mashreq.app.view.actvivties.Details;

import java.util.List;

public class VersionAdapter
    extends RecyclerView.Adapter<VersionAdapter.ResultViewHolder> {

  private List<com.mashreq.app.model.modeldb.export.Datum> resultModelList;
  Context  context;
  public VersionAdapter(Context context) {
    this.context = context;
  }
  @NonNull
  @Override
  public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      VersionItemBinding itemNewsBinding =
              DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
            R.layout.version_item, viewGroup, false);
    return new ResultViewHolder(itemNewsBinding);
  }

  @Override
  public void onBindViewHolder(@NonNull ResultViewHolder ResultViewHolder, final int i) {

    if(resultModelList!=null) {
      ResultViewHolder.itemNewsBinding.title.setText(resultModelList.get(i).getName());
      ResultViewHolder.itemNewsBinding.content.setText(resultModelList.get(i).getDesc());
//      Glide.with(context).load(resultModelList.get(i).getImage()).placeholder(R.drawable.version_place)
//              .into(ResultViewHolder.itemNewsBinding.image);
    }

    ResultViewHolder.itemNewsBinding.download.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(resultModelList.get(i).getFile()));
        context.startActivity(intent);
      }
    });
//ResultViewHolder.itemNewsBinding.card.setOnClickListener(new View.OnClickListener() {
//  @Override
//  public void onClick(View view) {
//    /* Create an Intent that will start the Menu-Activity. */
//    if(resultModelList!=null) {
//      com.mashreq.app.model.modeldb.export.Datum datum = resultModelList.get(i);
//      Intent mainIntent = new Intent(context, Details.class);
//      mainIntent.putExtra("title", datum.getTitle());
//      mainIntent.putExtra("content", datum.getContent());
//      mainIntent.putExtra("image", datum.getImage());
//      mainIntent.putExtra("logo", datum.getSourceLogo());
//      mainIntent.putExtra("newsName", "المشرق");
//      mainIntent.putExtra("date", datum.getCreatedAt());
//      context.startActivity(mainIntent);
//      Animatoo.animateSwipeRight(context);
//
//    }
//    //fire the slide left animation
//  }
//});

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
    private VersionItemBinding  itemNewsBinding;

    public ResultViewHolder(@NonNull VersionItemBinding itemNewsBinding) {
      super(itemNewsBinding.getRoot());
      this.itemNewsBinding = itemNewsBinding;
    }
  }
}
