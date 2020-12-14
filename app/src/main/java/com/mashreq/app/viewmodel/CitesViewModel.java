package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.Cities.CitesModel;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class CitesViewModel extends ViewModel {
    private MutableLiveData<CitesModel>citesViewModelMutableLiveData=new MutableLiveData<>();
    private Repository repository;



    @ViewModelInject
    public CitesViewModel(Repository repository) {
        this.repository = repository;
    }




    public MutableLiveData<CitesModel> getCitesViewModelMutableLiveData() {
        return citesViewModelMutableLiveData;
    }

    public void getCites(){

        repository.getCites().subscribeOn(Schedulers.io())
                .subscribe(result->citesViewModelMutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
    }

}
