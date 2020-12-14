package com.mashreq.app.view.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.mashreq.app.BuildConfig;
import com.mashreq.app.R;
import com.mashreq.app.RoomCaching.DAO.NewsDAO.LatestNewsDAO.ILatestNewsDao;
import com.mashreq.app.RoomCaching.DAO.UserDAO.IUserDao;
import com.mashreq.app.RoomCaching.Database.AppDatabase;
import com.mashreq.app.databinding.FragmentHomeFragemtBinding;
import com.mashreq.app.databinding.FragmentMyAccountBinding;
import com.mashreq.app.model.modeldb.Data.Data;
import com.mashreq.app.view.actvivties.Login;
import com.mashreq.app.view.actvivties.ProfileActivity;
import com.mashreq.app.view.actvivties.splashScreen;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends Fragment implements View.OnClickListener {
    FragmentMyAccountBinding fragmentMyAccountBinding;

    public MyAccount() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentMyAccountBinding = FragmentMyAccountBinding.inflate(inflater, container, false);
        fillDate();
        return fragmentMyAccountBinding.getRoot();
    }

    private void fillDate() {
        fragmentMyAccountBinding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(getActivity(), Login.class);
                startActivity(mainIntent);
                Animatoo.animateSwipeLeft(getActivity()); //fire the slide left animation
            }
        });
        fragmentMyAccountBinding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(getActivity(), Login.class);
                startActivity(mainIntent);
                Animatoo.animateSwipeLeft(getActivity()); //fire the slide left animation
            }
        });
    }
    List<Data>list;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textViewHome = getActivity().findViewById(R.id.textHome);
        textViewHome.setText("حسابى");
        RelativeLayout rel = getActivity().findViewById(R.id.rel);
        rel.setVisibility(View.GONE);

        fragmentMyAccountBinding.avatar.setOnClickListener(this);

        fragmentMyAccountBinding.call.setOnClickListener(this);
        fragmentMyAccountBinding.share.setOnClickListener(this);
        fragmentMyAccountBinding.rate.setOnClickListener(this);
        fragmentMyAccountBinding.policy.setOnClickListener(this);
        fragmentMyAccountBinding.condattion.setOnClickListener(this);
        AppDatabase appDatabase=AppDatabase.getInstance(getContext());
        final IUserDao iUserDao=appDatabase.iUserDao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                list=iUserDao.getUser();
                if(list.size()>0){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(getActivity().getApplicationContext()).load(list.get(0).getImage()).into(
                                    fragmentMyAccountBinding.avatar
                            );
                            fragmentMyAccountBinding.username.setText(list.get(0).getName());
                        }
                    });
                    fragmentMyAccountBinding.register.setVisibility(View.GONE);
                    fragmentMyAccountBinding.login.setVisibility(View.GONE);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.avatar:
                startActivity(new Intent(getContext(), ProfileActivity.class));
                break;
            case R.id.call:
                call();
                break;
            case R.id.share:
                share();
                break;
            case R.id.rate:
                rate();
                break;
            case R.id.policy:
                openPolicy();
                break;
            case R.id.condattion:
                openPolicy();
                break;
        }
    }

    private void openPolicy(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        startActivity(browserIntent);
    }
    private void rate(){
        Uri uri = Uri.parse("market://details?id=$packageName");
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")));
        }
    }
    private void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
    private void call(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0123456789"));
        startActivity(intent);
    }
}
