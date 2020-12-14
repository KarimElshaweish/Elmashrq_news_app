package com.mashreq.app.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.LiveStreamDB.LiveSteamDB;
import com.mashreq.app.model.modeldb.LiveStreamDB.LiveSteamDatum;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveStreamViewModel extends ViewModel {
    MutableLiveData<LiveSteamDB>liveSteamDBMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public LiveStreamViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<LiveSteamDB> getLiveSteamDBMutableLiveData() {
        return liveSteamDBMutableLiveData;
    }

    public void getLiveSteam(){
        if(liveSteamDBMutableLiveData==null)
            liveSteamDBMutableLiveData=new MutableLiveData<>();

        repository.getLiveSteam().subscribeOn(Schedulers.io())
        .subscribe(result->liveSteamDBMutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
    }

}
