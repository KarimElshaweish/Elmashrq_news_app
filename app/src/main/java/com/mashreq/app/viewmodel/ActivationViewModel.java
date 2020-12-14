package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.Activation.ActivationModel;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class ActivationViewModel extends ViewModel {
    private Repository repository;
    @ViewModelInject
    public ActivationViewModel(Repository repository) {
        this.repository = repository;
    }
    private MutableLiveData<ActivationModel>activationModelMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<ActivationModel> getActivationModelMutableLiveData() {
        return activationModelMutableLiveData;
    }
    public void getResponse(String token, String code){
        getResponseMethod(token,code);
    }
    private void getResponseMethod(String token, String code) {
      JsonObject object =new JsonObject();
      object.addProperty("code",code);
      repository.activateAccount(object,"Bearer "+token).subscribeOn(Schedulers.io())
              .subscribe(result->activationModelMutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
    }
}
