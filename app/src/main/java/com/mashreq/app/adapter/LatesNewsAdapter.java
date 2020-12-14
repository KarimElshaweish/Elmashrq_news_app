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
import com.mashreq.app.view.actvivties.Details;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LatesNewsAdapter
    extends RecyclerView.Adapter<LatesNewsAdapter.ResultViewHolder> {

  private List<LatestNews> resultModelList=new ArrayList<>();
  Context  context;
  public LatesNewsAdapter(Context context) {
    this.context = context;
  }
  @NonNull
  @Override
  public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      NewsRecBinding itemNewsBinding =
            DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.news_rec, viewGroup, false);
    return new ResultViewHolder(itemNewsBinding);
  }

  String timeDifferent(String date){
    SimpleDateFormat sdf=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    String currentDate=sdf.format(Calendar.getInstance().getTime());
    try{
      Date newsDate=sdf.parse(date);
      Date CurrentNewsDate=sdf.parse(currentDate);
      long different=CurrentNewsDate.getTime()-newsDate.getTime();
      long secondsInMilli = 1000;
      long minutesInMilli = secondsInMilli * 60;
      long hoursInMilli = minutesInMilli * 60;
      long daysInMilli = hoursInMilli * 24;

      long elapsedDays = different / daysInMilli;
      different = different % daysInMilli;

      long elapsedHours = different / hoursInMilli;
      different = different % hoursInMilli;

      long elapsedMinutes = different / minutesInMilli;
      different = different % minutesInMilli;

      long elapsedSeconds = different / secondsInMilli;
      String diff;
      if(elapsedDays==0)
        if(elapsedHours==0)
          if(elapsedMinutes==0)
            diff=elapsedSeconds+" ثانية ";
          else
            diff=elapsedMinutes+" دقيقة ";
        else
          diff=elapsedHours+" ساعة ";
      else
       diff=elapsedDays+" يوم "+elapsedHours+" ساعة ";
      return diff;
    }catch (Exception ex){
      return  "";
    }
  }
  @Override
  public void onBindViewHolder(@NonNull ResultViewHolder ResultViewHolder, final int i) {
    if(resultModelList.size()>0) {
      ResultViewHolder.itemNewsBinding.content.setText(resultModelList.get(i).getTitle());
      ResultViewHolder.itemNewsBinding.newsGateName.setText(resultModelList.get(i).getSource());
      ResultViewHolder.itemNewsBinding.date.setText(timeDifferent(resultModelList.get(i).getDate()));
      // ResultViewHolder.itemNewsBinding.date.setText(resultModelList.get(i).getFrom_time());

      Glide.with(context).load(resultModelList.get(i).getImage()).placeholder(R.drawable.image_de).into(
              ResultViewHolder.itemNewsBinding.imageContent
      );
      ResultViewHolder.itemNewsBinding.cardItem.setOnClickListener(new View.OnClickListener() {
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

  public void setnewsList(List<LatestNews> resultModels) {
    this.resultModelList = resultModels;
    notifyDataSetChanged();
  }

  class ResultViewHolder extends RecyclerView.ViewHolder {
    private NewsRecBinding  itemNewsBinding;

    public ResultViewHolder(@NonNull NewsRecBinding itemNewsBinding) {
      super(itemNewsBinding.getRoot());
    this.itemNewsBinding = itemNewsBinding;
    }
  }
}
