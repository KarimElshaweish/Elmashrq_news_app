package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.ForgetPassword.ForgetPasswordModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordViewModel extends ViewModel {

    private MutableLiveData<ForgetPasswordModel>forgetPasswordModelMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public ForgetPasswordViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ForgetPasswordModel> getForgetPasswordModelMutableLiveData() {
        return forgetPasswordModelMutableLiveData;
    }

    public void activeCode(String email){

            repository.getSigninResponse(email).subscribeOn(Schedulers.io())
            .subscribe(result->forgetPasswordModelMutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));

    }

}
