package com.mashreq.app.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.Report.ReportsModel;


public class VersionViewModel extends ViewModel {
  private MutableLiveData<ReportsModel>mutableLiveData ;
  public LiveData<ReportsModel> getResponse(String catId) {
    //if the list is null
    if (mutableLiveData == null) {
      mutableLiveData = new MutableLiveData<ReportsModel>();
      //we will load it asynchronously from server in this method
//   getListResult(catId);
    }

    //finally we will return the list
    return mutableLiveData;
  }
//  public void getListResult(String catId){
//    Constanturl.createService(RetrofitInterfaces.class).getByCat(catId).enqueue(new Callback<NewsModel>()  {
//      @LatestNews
//      public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
//        if (response.isSuccessful()){
//          NewsModel body = response.body();
//          mutableLiveData.setValue(body);
//        }else {
//          String s = response.errorBody().toString();
//          Log.v("errir",s);
//        }
//      }
//      @Override
//      public void onFailure(Call<NewsModel> call, Throwable t) {
//        Log.d("xXx", t.getMessage());
//      }
//    });
//  }


}
