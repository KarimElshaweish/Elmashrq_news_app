package com.mashreq.app.viewmodel;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.mashreq.app.R;
import com.mashreq.app.RealTimeActions.ISignUpServices;
import com.mashreq.app.model.modeldb.Signup.SignUpModel;
import com.mashreq.app.network.Constanturl;
import com.mashreq.app.network.RetrofitInterfaces;
import com.mashreq.app.repository.Repository;
import com.valdesekamdem.library.mdtoast.MDToast;

import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    MutableLiveData<SignUpModel>signUpModelMutableLiveData=new MutableLiveData<>();

    private Repository repository;

    @ViewModelInject
    public SignUpViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<SignUpModel> getSignUpModelMutableLiveData() {
        return signUpModelMutableLiveData;
    }


    ISignUpServices iSignUpServices;
   public void getResponse(String username, String email, String password, String mobile,String firebaseToken,
                                                  Context ctx
                                                  ,SpinKitView spinKitView){
       JsonObject jsonObject=new JsonObject();
       jsonObject.addProperty("name",username);
       jsonObject.addProperty("email",email);
       jsonObject.addProperty("password",password);
       jsonObject.addProperty("mobile",mobile);
       jsonObject.addProperty("fire_base",firebaseToken);

        iSignUpServices=(ISignUpServices)ctx;
        repository.signUp(jsonObject).subscribeOn(Schedulers.io())
        .subscribe(signUpModel -> {
            if(!signUpModel.getStatus().equals("error"))
                if (signUpModel.getData() == null) {
                    MDToast mdToastt = MDToast.makeText(ctx, signUpModel.getMessage(), MDToast.LENGTH_LONG, MDToast.TYPE_ERROR);
                    mdToastt.show();
                    iSignUpServices.onLoadingFailed();
                }
                else
                    signUpModelMutableLiveData.setValue(signUpModel);
            else{
                spinKitView.setVisibility(View.GONE);
                MDToast mdToastt = MDToast.makeText(ctx,ctx.getString(R.string.login_error),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
                mdToastt.show();
            }
        },error->Log.e("error",error.getMessage()));
    }

}
