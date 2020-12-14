package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetialsViewModel extends ViewModel {
    MutableLiveData<News>likeOrUnlikeList;

    public MutableLiveData<News>likeOrUnlikeNews(String id,String token){
        if(likeOrUnlikeList==null)
            likeOrUnlikeList=new MutableLiveData<>();

        apiLikeOrUnlikeNews(id,token);
        return likeOrUnlikeList;
    }

    private void apiLikeOrUnlikeNews(String id, String token) {
//        Constanturl.createService().likeOrUnlike(id, token).enqueue(new Callback<News>() {
//            @Override
//            public void onResponse(Call<News> call, Response<News> response) {
//                if(response.isSuccessful()&&response.body()!=null)
//                    likeOrUnlikeList.setValue(response.body());
//                else{
//                    Log.d("error",response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<News> call, Throwable t) {
//                Log.d("error",t.getMessage());
//            }
//        });
    }
}
