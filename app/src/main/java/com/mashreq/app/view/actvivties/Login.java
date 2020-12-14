package com.mashreq.app.view.actvivties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.mashreq.app.R;
import com.mashreq.app.RealTimeActions.ISignUpServices;
import com.mashreq.app.RoomCaching.DAO.UserDAO.IUserDao;
import com.mashreq.app.RoomCaching.Database.AppDatabase;
import com.mashreq.app.databinding.ActivityLoginBinding;
import com.mashreq.app.model.modeldb.Register.LoginModel;
import com.mashreq.app.model.modeldb.Signup.SignUpModel;
import com.mashreq.app.viewmodel.LoginViewModel;
import com.mashreq.app.viewmodel.SignUpViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Login extends AppCompatActivity implements View.OnClickListener, ISignUpServices {
    ActivityLoginBinding activityLoginBinding;
    LoginViewModel loginViewModel;
    SignUpViewModel signUpViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        activityLoginBinding.loginButton.setOnClickListener(this);
        activityLoginBinding.newAcount.setOnClickListener(this);
        activityLoginBinding.login.setOnClickListener(this);
        activityLoginBinding.newAcountOhter.setOnClickListener(this);
        activityLoginBinding.forgetPass.setOnClickListener(this);
        activityLoginBinding.login.setTextColor(getResources().getColor(R.color.colorAccent));
        activityLoginBinding.newAcount.setTextColor(getResources().getColor(R.color.color_not_activ));
        activityLoginBinding.login.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
        activityLoginBinding.newAcount.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
        activityLoginBinding.registerForm.setVisibility(View.GONE);

    }

    Boolean login=true;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           case  R.id.login_Button:
               /* Create an Intent that will start the Menu-Activity. */
               if(login)
                   validation();
               else
                   validation(true);
               break;
            case R.id.login:
                login=true;
                activityLoginBinding.phoneNumber.setVisibility(View.GONE);
                activityLoginBinding.phoneNumberTitle.setVisibility(View.GONE);
                activityLoginBinding.havingAccountCheck.setVisibility(View.VISIBLE);
                activityLoginBinding.checkboxAcceptTerms.setVisibility(View.GONE);
                activityLoginBinding.forgetPass.setVisibility(View.VISIBLE);
                activityLoginBinding.registerForm.setVisibility(View.GONE);
                activityLoginBinding.login.setTextColor(getResources().getColor(R.color.colorAccent));
                activityLoginBinding.newAcount.setTextColor(getResources().getColor(R.color.color_not_activ));
                activityLoginBinding.login.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
                activityLoginBinding.newAcount.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                break;
                case R.id.new_acount:
                case R.id.new_acount_ohter:
                    login=false;
                    activityLoginBinding.phoneNumber.setVisibility(View.VISIBLE);
                    activityLoginBinding.phoneNumberTitle.setVisibility(View.VISIBLE);
                    activityLoginBinding.havingAccountCheck.setVisibility(View.GONE);
                    activityLoginBinding.checkboxAcceptTerms.setVisibility(View.VISIBLE);
                    activityLoginBinding.forgetPass.setVisibility(View.GONE);
                    activityLoginBinding.registerForm.setVisibility(View.VISIBLE);
                    activityLoginBinding.newAcount.setTextColor(getResources().getColor(R.color.colorAccent));
                    activityLoginBinding.login.setTextColor(getResources().getColor(R.color.color_not_activ));
                    activityLoginBinding.newAcount.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.diver);
                    activityLoginBinding.login.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
                    break;

            case R.id.forget_pass:
                /* Create an Intent that will start the Menu-Activity. */
                Intent forget = new Intent(Login.this, ForgetPass.class);
                startActivity(forget);
                Animatoo.animateSwipeRight(Login.this); //fire the slide left animation
                 //   forgetPassword(activityLoginBinding.email.getText().toString());
                break;
        }
    }

    private void validation() {
        if(activityLoginBinding.email.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_email_enter),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else if(activityLoginBinding.password.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_email_enter),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else{
            activityLoginBinding.spinKit.setVisibility(View.VISIBLE);
            sigInMethod(activityLoginBinding.email.getText().toString(),activityLoginBinding.password.getText().toString());

        }
    }
     String refreshedToken;

    private void validation(Boolean signup) {
        if(activityLoginBinding.email.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_validation),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else if(activityLoginBinding.password.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_validation),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else if (activityLoginBinding.username.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_validation),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else if(activityLoginBinding.phoneNumber.getText().toString().equals(""))
            MDToast.makeText(this, getString(R.string.error_validation),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else if(!activityLoginBinding.checkboxAcceptTerms.isChecked())
            MDToast.makeText(this, getString(R.string.please_confirm_terms),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        else{
            activityLoginBinding.spinKit.setVisibility(View.VISIBLE);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Login.this,  new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    refreshedToken = instanceIdResult.getToken();
                    Log.e("Token",refreshedToken);
                }
            }).addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    signUpMethod(activityLoginBinding.username.getText().toString(),
                            activityLoginBinding.email.getText().toString(),
                            activityLoginBinding.password.getText().toString(),
                            activityLoginBinding.phoneNumber.getText().toString(),refreshedToken);
                }
            });

        }
    }

    private void signUpMethod(String username,String email,String password,String phoneNumber,String firebaseToken){
        signUpViewModel=new ViewModelProvider(this).get(SignUpViewModel.class);
        signUpViewModel.getResponse(username,email,password,phoneNumber,firebaseToken,this,activityLoginBinding.spinKit)
                ;
        signUpViewModel.getSignUpModelMutableLiveData().observe(this, new Observer<SignUpModel>() {
                    @Override
                    public void onChanged(SignUpModel signUpModel) {
                        if(signUpModel.getData()!=null) {
                            activityLoginBinding.spinKit.setVisibility(View.GONE);
                            MDToast mdToast = MDToast.makeText(Login.this, signUpModel.getMessage(), MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                            mdToast.show();
                            Intent intent=new Intent(Login.this,ActivationCodeActivity.class);
                            intent.putExtra("email",signUpModel.getData().getEmail());
                            intent.putExtra("type",2);
                            intent.putExtra("token",signUpModel.getData().getToken());
                            startActivity(intent);
                            Animatoo.animateSlideLeft(Login.this);
                        }
                    }
                });
    }
    private void sigInMethod(final String email, final String password) {
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( Login.this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                refreshedToken = instanceIdResult.getToken();
                Log.e("Token",refreshedToken);
            }
        }).addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                loginViewModel.login(email,password,refreshedToken,Login.this);
                loginViewModel.getLoginModelMutableLiveData().observe(Login.this, new Observer<LoginModel>() {
                    @Override
                    public void onChanged(final LoginModel loginModel) {
                        if(loginModel.getData()!=null){

                            SharedPreferences sharedPreferences=getSharedPreferences("data",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putString("token",loginModel.getData().getToken());
                            editor.apply();
                            editor.commit();
                            AppDatabase userDatabase=AppDatabase.getInstance(getApplicationContext());
                            final IUserDao iUserDao=userDatabase.iUserDao();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    iUserDao.registerUser(loginModel.getData());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            activityLoginBinding.spinKit.setVisibility(View.GONE);
                                            MDToast mdToast = MDToast.makeText(Login.this, "تم التسجيل بنجاح",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                                            mdToast.show();
                                            Intent mainIntent = new Intent(Login.this, HomePage.class);
                                            startActivity(mainIntent);
                                            Animatoo.animateSwipeRight(Login.this);
                                        }
                                    });
                                }
                            }).start();
                            //fire the slide left animation
                        }else{
                            activityLoginBinding.spinKit.setVisibility(View.GONE);
                            MDToast mdToast = MDToast.makeText(Login.this, "حدثت مشكلة اثناء التسجيل برجاء التأكد من اللبيانات والمحاولة مرة اخرى",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR);
                            mdToast.show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onLoadingFailed() {
        activityLoginBinding.spinKit.setVisibility(View.GONE);
    }
}
