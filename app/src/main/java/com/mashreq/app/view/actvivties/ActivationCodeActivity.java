package com.mashreq.app.view.actvivties;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityActivationCodeBinding;
import com.mashreq.app.model.modeldb.Activation.ActivationModel;
import com.mashreq.app.viewmodel.ActivationViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivationCodeActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityActivationCodeBinding activityActivationCodeBinding;
    ActivationViewModel activationViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityActivationCodeBinding=ActivityActivationCodeBinding.inflate(getLayoutInflater());
        setContentView(activityActivationCodeBinding.getRoot());
        activityActivationCodeBinding.changePasswordButton.setOnClickListener(this);
        activityActivationCodeBinding.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.changePasswordButton:
                validation();
                break;
            case R.id.backButton:
                finish();
                break;
        }
    }
    private void  validation(){
        if(activityActivationCodeBinding.activationCodeText.getText().toString().equals("")) {
            MDToast mdToastt = MDToast.makeText(this, getString(R.string.please_enter_actication_code), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
            mdToastt.show();
        }else{
            activityActivationCodeBinding.spinKit.setVisibility(View.VISIBLE);
            acivateCodeMethod();

        }
    }



    private void acivateCodeMethod(){
        activationViewModel= new ViewModelProvider(this).get(ActivationViewModel.class);
        final String token=getIntent().getStringExtra("token");
        if(token!=null) {
            String code=activityActivationCodeBinding.activationCodeText.getText().toString();
            activationViewModel.getResponse(token,code);
            activationViewModel.getActivationModelMutableLiveData().observe(this, new Observer<ActivationModel>() {
                @Override
                public void onChanged(ActivationModel activationModel) {
                    activityActivationCodeBinding.spinKit.setVisibility(View.GONE);
                    activityActivationCodeBinding.spinKit.setVisibility(View.GONE);
                    MDToast mdToast = MDToast.makeText(ActivationCodeActivity.this, activationModel.getMessage(), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    int type=getIntent().getIntExtra("id",0);
                    Intent intent;
                    if(type==1){
                        intent=new Intent(ActivationCodeActivity.this,ResetPasswordActivity.class);
                    }else{
                        intent=new Intent(ActivationCodeActivity.this,HomePage.class);
                    }
                    intent.putExtra("token",token);
                    Animatoo.animateSlideLeft(ActivationCodeActivity.this);
                    startActivity(intent);
                }

            });
        }else{
            activityActivationCodeBinding.spinKit.setVisibility(View.GONE);
            MDToast mdToast = MDToast.makeText(ActivationCodeActivity.this, "حدث خطأ",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
            mdToast.show();
        }
    }
}
