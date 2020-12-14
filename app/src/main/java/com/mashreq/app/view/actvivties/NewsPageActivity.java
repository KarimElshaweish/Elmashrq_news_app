package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.adapter.LatesNewsAdapter;
import com.mashreq.app.adapter.ResourceNewsAdapter;
import com.mashreq.app.adapter.ResourcesAdapter;
import com.mashreq.app.databinding.ActivityNewsPageBinding;
import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.model.modeldb.ResourceNews.ResourceNews;
import com.mashreq.app.viewmodel.FollowOrUnFollowViewModel;
import com.mashreq.app.viewmodel.ResourceNewsViewModel;
import com.mashreq.app.viewmodel.ResourceViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.List;

import dagger.hilt.EntryPoint;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NewsPageActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityNewsPageBinding activity_news_page;
    ResourceNewsAdapter latesNewsAdapter;
    ResourceNewsViewModel resourceViewModel;
    FollowOrUnFollowViewModel followOrUnFollowViewModel;
    int followers;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_news_page=ActivityNewsPageBinding.inflate(getLayoutInflater());
        setContentView(activity_news_page.getRoot());
        activity_news_page.backButton.setOnClickListener(this);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String logo=intent.getStringExtra("logo");
        Boolean isFollow=intent.getBooleanExtra("is_follow",false);
        activity_news_page.followBtn.setOnClickListener(this);
        if(isFollow){
            activity_news_page.followBtn.setText("إالغاء المتابعة");
        }
        followers=intent.getIntExtra("followers",0);
        id=intent.getIntExtra("id",0);
        Glide.with(this).load(logo).into(activity_news_page.logo);

        activity_news_page.followers.setText( followers+" متابع ");
        activity_news_page.newsName.setText(name);
        activity_news_page.newsName2.setText(name);
        latesNewsAdapter=new ResourceNewsAdapter();

        activity_news_page.rv.setHasFixedSize(true);
        activity_news_page.rv.setLayoutManager(new LinearLayoutManager(this));
        activity_news_page.rv.setAdapter(latesNewsAdapter);
        getdata(String.valueOf(id));
    }
    private void getdata(String id){
        resourceViewModel= new ViewModelProvider(this).get(ResourceNewsViewModel.class);
        resourceViewModel.getNewsMutableLiveData(id);
        resourceViewModel.getNewsMutableLiveData().observe(this, new Observer<ResourceNews>() {
            @Override
            public void onChanged(ResourceNews news) {
                if(news!=null)
                    latesNewsAdapter.setnewsList(news.getData());
            }
        });
    }


    private void followOrUnFollow(String token,String sourceId){
        followOrUnFollowViewModel=new ViewModelProvider(this).get(FollowOrUnFollowViewModel.class);
        followOrUnFollowViewModel.FollowOrUnFollow(token,sourceId);
        followOrUnFollowViewModel.getMutableLiveData().observe(this, new Observer<ResourceDB>() {
            @Override
            public void onChanged(ResourceDB resourceDB) {
                String text=activity_news_page.followBtn.getText().toString();
                if(resourceDB.getData().size()!=0) {
                    if(!text.equals("إلغاء المتابعة")) {
                        MDToast mdToast = MDToast.makeText(getBaseContext(), resourceDB.getMessage(), MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
                        mdToast.show();
                        activity_news_page.followBtn.setText("إلغاء المتابعة");
                        followers++;
                        activity_news_page.followers.setText( followers+" متابع ");

                    }
                }
                    else {
                        if(!text.equals("متابعة")) {
                            MDToast mdToast = MDToast.makeText(getBaseContext(), resourceDB.getMessage(), MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
                            mdToast.show();
                            activity_news_page.followBtn.setText("متابعة");
                            followers--;
                            activity_news_page.followers.setText(followers + " متابع ");
                        }

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.followBtn:
                SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                String token =sharedPreferences.getString("token","");
                if(token.equals("")){
                    token="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDc2NWUxYjExYTU4MDE1ZTNkNTRjOTMyMjAyOTQxNzg3YWUzNWU2OTMxODhjZWRmMjQ3NmJlMTBjZjkwZmM5NTIyMzA5ZDhmYmQ3OGZkZjQiLCJpYXQiOjE2MDM4Nzk5MzAsIm5iZiI6MTYwMzg3OTkzMCwiZXhwIjoxNjM1NDE1OTMwLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.UEnM0DUoUCPTpFLTTPGWkqDlIzHARnQ9zHvDruNqngri27pHGNX85TnRhD4I0U6Rp9cnsCsYp6aNpgjxMeLyYXkIpz9YCG--36DwsgGxq0dYw7RvDNMYh9KuOGXW0G38c86On6vgvoczeLkAYk_OLPmppcXAXZu6I-We_olVnZVmQ4d6xrrCAl7gwHgm2eXrvRpOSrYpWneLGOWB4mFQQ3pSxqxWsjy4Tc6zQuAf4dxv8yzrEAGLtFa_Pjsq27r1xnII8wVD_xoDQx5Vh2-hcdKqVNqS3fzwc_hZEI5eHuBMH3dHeqfQRH9xtNsXmooegkgS-6-y3VGQBxS1LRvVoBJIiwE0x_TdyoNVnsCS4kGofq76dAF-96oEoOzocBcRLt-ceALOnE6VQsRPenXcGz1tTZiEk6lnrHy4pFhWmttefHwULGaqOHte7a4Z2im7kXUC45CFC03bFW2HXBsuGB_nYFotElAE8psbfTfGm6AbNlOndsPLABnN9NbmdOXTrzHXAzPDMiEHe0hWLqcUZWCqM1dyVHg7FSuwmSCA0L2MALNiqM-f9ACbykBRbF_RgA1aH-Ja8fptkf6tanqHU-E1GolrjjHk0-ERPq2xH1h4V2Wvi4gDTpWtjq4kO8EeytA6hA1Fn2MGvnUNkM3j3CuIEcdNCPvH6-dzC91SJDE";
                }
                token="Bearer "+token;
                followOrUnFollow(token,String.valueOf(id));
                break;
            case R.id.backButton:
                ActivityManager am=(ActivityManager)getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo>taskInfoList=am.getRunningTasks(10);
                if(taskInfoList.get(0).numActivities==1&&
                    taskInfoList.get(0).topActivity.getClassName().equals(this.getClass().getName())){
                    startActivity(new Intent(this,HomePage.class));
                }else{
                    finish();
                }
        }
    }
}
