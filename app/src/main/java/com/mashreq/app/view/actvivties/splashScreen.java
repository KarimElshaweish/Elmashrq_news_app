package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.mashreq.app.RoomCaching.DAO.UserDAO.IUserDao;
import com.mashreq.app.RoomCaching.Database.AppDatabase;
import com.mashreq.app.databinding.ActivitySplashScreenBinding;


public class splashScreen extends AppCompatActivity {
    ActivitySplashScreenBinding activitySplashScreenBinding;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySplashScreenBinding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(activitySplashScreenBinding.getRoot());
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                AppDatabase userDatabase=AppDatabase.getInstance(getApplicationContext());
                final IUserDao iUserDao=userDatabase.iUserDao();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int count=iUserDao.getUser().size();
                        if(count>0){
                            /* Create an Intent that will start the Menu-Activity. */
                            Intent mainIntent = new Intent(splashScreen.this, HomePage.class);
                            startActivity(mainIntent);
                            Animatoo.animateSwipeRight(splashScreen.this); //fire the slide left animation
                            finish();
                        }else{
                            SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
                            boolean rss=sharedPreferences.getBoolean("rss",false);
                            /* Create an Intent that will start the Menu-Activity. */
                            Intent mainIntent;
                            if(!rss){
                                 mainIntent = new Intent(splashScreen.this, ReosurcesActivity.class);
                            }else{
                                mainIntent=new Intent(splashScreen.this,HomePage.class);
                            }
                            startActivity(mainIntent);
                            Animatoo.animateSwipeRight(splashScreen.this); //fire the slide left animation
                            finish();
                        }
                    }
                }).start();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    }

