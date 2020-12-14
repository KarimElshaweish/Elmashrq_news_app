package com.mashreq.app.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mashreq.app.R;
import com.mashreq.app.model.modeldb.Register.LoginModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;
import com.valdesekamdem.library.mdtoast.MDToast;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginModel>loginModelMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<LoginModel> getLoginModelMutableLiveData() {
        return loginModelMutableLiveData;
    }

    public void login(String email, String password, String token, Context _ctx){

        //adding parameters to the json object
        JsonObject parmsJsonObject = new JsonObject();
        parmsJsonObject.addProperty("email",email);
        parmsJsonObject.addProperty("password",password);
        parmsJsonObject.addProperty("fire_base",token);

        //login async
        repository.getLoginResponse(parmsJsonObject).subscribeOn(Schedulers.io())
        .subscribe(result->{
            loginModelMutableLiveData.postValue(result);
            if(result.getData()==null)
                makeToast(_ctx);
        },error->{
            String s = error.getMessage();
            Log.e("error",s);
            makeToast(_ctx);
        });
    }
    private void makeToast(Context _ctx){
        MDToast mdToastt = MDToast.makeText(_ctx,_ctx.getString(R.string.login_error),MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
        mdToastt.show();

    }
}
