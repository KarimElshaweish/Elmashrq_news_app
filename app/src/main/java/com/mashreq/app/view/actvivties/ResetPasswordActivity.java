package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.gson.JsonObject;
import com.mashreq.app.R;
import com.mashreq.app.databinding.ActivityResetPasswordBinding;
import com.mashreq.app.model.modeldb.Activation.ActivationModel;
import com.mashreq.app.viewmodel.ChangePasswordViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityResetPasswordBinding activity_reset_password;
    ChangePasswordViewModel changePasswordViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_reset_password=ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(activity_reset_password.getRoot());
        activity_reset_password.backButton.setOnClickListener(this);
        activity_reset_password.confirmNewPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
            case R.id.confim_new_password_button:
                validation();
                break;
        }
    }

    private void validation() {
        String newPassword=activity_reset_password.newPassword.getText().toString().trim();
        String confirmNewPassword=activity_reset_password.confirmNewPassword.getText().toString().trim();
        if(!newPassword.equals(confirmNewPassword)){
            MDToast mdToast= MDToast.makeText(this,getString(R.string.please_make_sure_2password),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
            mdToast.show();
        }else{
            activity_reset_password.spinKit.setVisibility(View.VISIBLE);
            changePassword(newPassword,confirmNewPassword);
        }
    }

    private void changePassword(String newPassword, String confirmNewPassword) {
        String token=getIntent().getStringExtra("token");
        JsonObject data=new JsonObject();
        data.addProperty("password",newPassword);
        data.addProperty("password_confirm",confirmNewPassword);
        changePasswordViewModel= new ViewModelProvider(this).get(ChangePasswordViewModel.class);
        changePasswordViewModel.changePassword(data,token);
        changePasswordViewModel.getMutableLiveData().observe(this, new Observer<ActivationModel>() {
            @Override
            public void onChanged(ActivationModel activationModel) {
                activity_reset_password.spinKit.setVisibility(View.GONE);
                if(activationModel.getStatus()==1){
                    MDToast mdToast=MDToast.makeText(ResetPasswordActivity.this,getString(R.string.done),MDToast.LENGTH_SHORT,MDToast.TYPE_SUCCESS);
                    mdToast.show();
                    startActivity(new Intent(ResetPasswordActivity.this,Login.class));
                }else{
                    MDToast mdToast=MDToast.makeText(ResetPasswordActivity.this,getString(R.string.failed),MDToast.LENGTH_SHORT,MDToast.TYPE_ERROR);
                    mdToast.show();
                }
            }
        });
    }

    public void confirmOnclick(View view) {
        validation();
    }
}
