package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityForgetPassBinding;
import com.mashreq.app.model.modeldb.ForgetPassword.ForgetPasswordModel;
import com.mashreq.app.viewmodel.ForgetPasswordViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ForgetPass extends AppCompatActivity implements View.OnClickListener {

    ForgetPasswordViewModel forgetPasswordViewModel;
    ActivityForgetPassBinding activityForgetPassBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //databinding imp
        activityForgetPassBinding=ActivityForgetPassBinding.inflate(getLayoutInflater());
        setContentView(activityForgetPassBinding.getRoot());

        forgetPasswordViewModel= new ViewModelProvider(this).get(ForgetPasswordViewModel.class);

        defineButtonClick();
    }

    //this function to init the onclick function
    private void defineButtonClick(){
        activityForgetPassBinding.backButton.setOnClickListener(this);
        activityForgetPassBinding.changePasswordButton.setOnClickListener(this);
    }

    private void forgetPassword(String email){
        if(email!=null){
           activityForgetPassBinding.spinKit.setVisibility(View.VISIBLE);
            forgetPasswordViewModel.activeCode(email);
            forgetPasswordViewModel.getForgetPasswordModelMutableLiveData().observe(this, new Observer<ForgetPasswordModel>() {
                @Override
                public void onChanged(ForgetPasswordModel forgetPasswordModel) {
                    if(forgetPasswordModel!=null){
                        MDToast mdToast = MDToast.makeText(ForgetPass.this, forgetPasswordModel.getMessage(),MDToast.LENGTH_LONG,MDToast.TYPE_INFO);
                        mdToast.show();
                        activityForgetPassBinding.spinKit.setVisibility(View.GONE);
                        Intent intent=new Intent(ForgetPass.this,ActivationCodeActivity.class);
                        intent.putExtra("token",forgetPasswordModel.getData().getToken());
                        intent.putExtra("id",1);
                        startActivity(intent);
                    }else{
                        MDToast mdToast = MDToast.makeText(ForgetPass.this, getString(R.string.some_thing_wrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
                        mdToast.show();
                    }
                }
            });
        }else{
            MDToast mdToast = MDToast.makeText(this, getString(R.string.please_enter_your_email),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
            mdToast.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changePasswordButton:
                forgetPassword(activityForgetPassBinding.emailText.getText().toString());
                break;
                case R.id.backButton:
                    finish();
                    break;
        }
    }
}
