package com.mashreq.app.viewmodel;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mashreq.app.model.modeldb.export.ExportModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.content.PermissionChecker.checkSelfPermission;

public class AllExportsViewModel extends ViewModel {


    private Repository repository;

    @ViewModelInject
    public AllExportsViewModel(Repository repository) {
        this.repository = repository;
    }

    MutableLiveData<ExportModel>exportModelMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<ExportModel> getExportModelMutableLiveData() {
        return exportModelMutableLiveData;
    }

    public void getResponse(){

       repository.getAllExports().subscribeOn(Schedulers.io())
       .subscribe(result->exportModelMutableLiveData.postValue(result),error->Log.e("error",error.getMessage()));
    }

}
