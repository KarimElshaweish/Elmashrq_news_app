package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.Activation.ActivationModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import java.util.logging.Logger;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends ViewModel {
    MutableLiveData<ActivationModel>mutableLiveData=new MutableLiveData<>();
    private Repository repository;

    @ViewModelInject
    public ChangePasswordViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ActivationModel> getMutableLiveData() {
        return mutableLiveData;
    }

    public  void changePassword(JsonObject data, String token){


        repository.chnagePasswordAPI(data,"Bearer "+token).subscribeOn(Schedulers.io())
        .subscribe(result->mutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
    }

}
