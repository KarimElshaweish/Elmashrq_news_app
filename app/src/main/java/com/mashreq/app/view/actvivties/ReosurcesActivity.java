package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.mashreq.app.R;
import com.mashreq.app.adapter.ResourcesAdapter;
import com.mashreq.app.databinding.ActivityReosurcesBinding;
import com.mashreq.app.model.modeldb.Resource.Datum;
import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.viewmodel.FollowOrUnFollowViewModel;
import com.mashreq.app.viewmodel.ResourceViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReosurcesActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityReosurcesBinding reosurcesBinding;
    ResourceViewModel resourceViewModel;
    ResourcesAdapter iragAdapter;
    ResourcesAdapter otherAdapter;
    FollowOrUnFollowViewModel followOrUnFollowViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("rss",true);
        editor.apply();
        reosurcesBinding =ActivityReosurcesBinding.inflate(getLayoutInflater());
        followOrUnFollowViewModel=new ViewModelProvider(this).get(FollowOrUnFollowViewModel.class);
        setContentView(reosurcesBinding.getRoot());
        reosurcesBinding.backButton.setOnClickListener(this);
        reosurcesBinding.iragRV.setHasFixedSize(true);
        reosurcesBinding.iragRV.setLayoutManager(new LinearLayoutManager(this));
        iragAdapter=new ResourcesAdapter(followOrUnFollowViewModel,this);
        reosurcesBinding.iragRV.setAdapter(iragAdapter);
        otherAdapter=new ResourcesAdapter(followOrUnFollowViewModel,this);
        reosurcesBinding.otherRV.setHasFixedSize(true);
        reosurcesBinding.otherRV.setLayoutManager(new LinearLayoutManager(this));
        reosurcesBinding.otherRV.setAdapter(otherAdapter);
        String token=sharedPreferences.getString("token","");
        if(token.equals(""))
            token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDc2NWUxYjExYTU4MDE1ZTNkNTRjOTMyMjAyOTQxNzg3YWUzNWU2OTMxODhjZWRmMjQ3NmJlMTBjZjkwZmM5NTIyMzA5ZDhmYmQ3OGZkZjQiLCJpYXQiOjE2MDM4Nzk5MzAsIm5iZiI6MTYwMzg3OTkzMCwiZXhwIjoxNjM1NDE1OTMwLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.UEnM0DUoUCPTpFLTTPGWkqDlIzHARnQ9zHvDruNqngri27pHGNX85TnRhD4I0U6Rp9cnsCsYp6aNpgjxMeLyYXkIpz9YCG--36DwsgGxq0dYw7RvDNMYh9KuOGXW0G38c86On6vgvoczeLkAYk_OLPmppcXAXZu6I-We_olVnZVmQ4d6xrrCAl7gwHgm2eXrvRpOSrYpWneLGOWB4mFQQ3pSxqxWsjy4Tc6zQuAf4dxv8yzrEAGLtFa_Pjsq27r1xnII8wVD_xoDQx5Vh2-hcdKqVNqS3fzwc_hZEI5eHuBMH3dHeqfQRH9xtNsXmooegkgS-6-y3VGQBxS1LRvVoBJIiwE0x_TdyoNVnsCS4kGofq76dAF-96oEoOzocBcRLt-ceALOnE6VQsRPenXcGz1tTZiEk6lnrHy4pFhWmttefHwULGaqOHte7a4Z2im7kXUC45CFC03bFW2HXBsuGB_nYFotElAE8psbfTfGm6AbNlOndsPLABnN9NbmdOXTrzHXAzPDMiEHe0hWLqcUZWCqM1dyVHg7FSuwmSCA0L2MALNiqM-f9ACbykBRbF_RgA1aH-Ja8fptkf6tanqHU-E1GolrjjHk0-ERPq2xH1h4V2Wvi4gDTpWtjq4kO8EeytA6hA1Fn2MGvnUNkM3j3CuIEcdNCPvH6-dzC91SJDE";

        else
            token="Bearer "+token;
        getdata(token);
    }

    void getdata(String token){
        resourceViewModel= new ViewModelProvider(this).get(ResourceViewModel.class);
        resourceViewModel.getResources(token);
        resourceViewModel.getDbMutableLiveData().observe(this, new Observer<ResourceDB>() {
            @Override
            public void onChanged(ResourceDB resourceDB) {
                if(resourceDB.getData()!=null) {
                    List<Datum>iragList=new ArrayList<>();
                    List<Datum>othersList=new ArrayList<>();
                    for(int i=0;i<resourceDB.getData().size();i++){
                        Datum dt=resourceDB.getData().get(i);
                        if(dt.getCity()!=null&&dt.getCity().equals("العراق"))
                            iragList.add(dt);
                        else
                            othersList.add(dt);
                    }
                    iragAdapter.setNewList(iragList);
                    otherAdapter.setNewList(othersList);
                    System.out.println(resourceDB.getData().size());
                }
                else
                    System.out.println(resourceDB.getMessage());
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomePage.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                startActivity(new Intent(this,HomePage.class));
                break;
        }
    }
}
