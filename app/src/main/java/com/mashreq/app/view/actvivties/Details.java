package com.mashreq.app.view.actvivties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mashreq.app.R;
import com.mashreq.app.adapter.RelatedNewsAdapter;
import com.mashreq.app.databinding.ActivityDetailsBinding;
import com.mashreq.app.model.modeldb.SingleNew.New;
import com.mashreq.app.model.modeldb.SingleNew.SingleNewsModel;
import com.mashreq.app.viewmodel.SingleNewsViewModel;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.http.Url;
@AndroidEntryPoint
public class Details extends AppCompatActivity implements View.OnClickListener {

    ActivityDetailsBinding activityDetailsBinding;
    Intent intent;
    String link;
    RelatedNewsAdapter relatedNewsAdapter;
    SingleNewsViewModel singleNewsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDetailsBinding=ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(activityDetailsBinding.getRoot());
        singleNewsViewModel= new ViewModelProvider(this).get(SingleNewsViewModel.class);
        String id=getIntent().getStringExtra("id");
        if(id!=null)
            getNewsDetails(Integer.parseInt(id));
        activityDetailsBinding.backButton.setOnClickListener(this);
        activityDetailsBinding.share.setOnClickListener(this);
        activityDetailsBinding.shareWhatsApp.setOnClickListener(this);
        activityDetailsBinding.ShareFaceBook.setOnClickListener(this);
        activityDetailsBinding.ShareTwitter.setOnClickListener(this);
        activityDetailsBinding.openUrlText.setOnClickListener(this);
        activityDetailsBinding.relatedNewsRV.setHasFixedSize(true);
        activityDetailsBinding.relatedNewsRV.setLayoutManager(new LinearLayoutManager(this));

        relatedNewsAdapter=new RelatedNewsAdapter();
        activityDetailsBinding.relatedNewsRV.setAdapter(relatedNewsAdapter);

       // activityDetailsBinding.relatedNewsRV.setNestedScrollingEnabled(false);
//        intent=getIntent();
//        String newsName=intent.getStringExtra("newsName");
//        link=intent.getStringExtra("link");
//        activityDetailsBinding.newsName.setText(newsName);
//        activityDetailsBinding.content.setText(intent.getStringExtra("title"));
//         activityDetailsBinding.fullContent.setText(intent.getStringExtra("content"));
//        Glide.with(this).load(intent.getStringExtra("image")).into(activityDetailsBinding.newsImage);
    }

    private void getNewsDetails(int id) {
        singleNewsViewModel.getSingleNews(id);
        singleNewsViewModel.getMutableLiveData().observe(this, new Observer<SingleNewsModel>() {
            @Override
            public void onChanged(SingleNewsModel singleNewsModel) {
                if(singleNewsModel.getData()!=null){
                    New news=singleNewsModel.getData().getNew();
                    relatedNewsAdapter.setNewsRelatedList(singleNewsModel.getData().getRelatedNews());
                    setData(news);
                }
            }
        });
    }

    void setData(New news){
        activityDetailsBinding.content.setText(news.getContent());
        activityDetailsBinding.title.setText(news.getTitle());
        Glide.with(this).load(news.getImage()).into(activityDetailsBinding.newsImage);
        activityDetailsBinding.newsName.setText(news.getSource());
        Glide.with(this).load(news.getSourceLogo()).into(activityDetailsBinding.sourceLogo);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backButton:
                finish();
                break;
            case R.id.share:
                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT,link);
                startActivity(sendIntent);
                break;
            case R.id.openUrlText:
                Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(browserIntent);
                break;
            case R.id.ShareTwitter:
                shareTwitter(link);
                break;
            case R.id.ShareFaceBook:
                shareFaceBook();
                break;
            case R.id.shareWhatsApp:
                shareWhatsApp();
                break;
        }
    }


    private void shareTwitter(String message) {
        Intent tweetIntent = new Intent(Intent.ACTION_SEND);
        tweetIntent.putExtra(Intent.EXTRA_TEXT, "This is a Test.");
        tweetIntent.setType("text/plain");

        PackageManager packManager = getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;
        for (ResolveInfo resolveInfo : resolvedInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (resolved) {
            startActivity(tweetIntent);
        } else {
            Intent i = new Intent();
            i.putExtra(Intent.EXTRA_TEXT, message);
            i.setAction(Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
            startActivity(i);
            Toast.makeText(this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
        }
    }

    String TAG="Error";
    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.wtf(TAG, "UTF-8 should always be supported", e);
            return "";
        }
    }

    private void shareWhatsApp(){
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");


        waIntent.setPackage("com.whatsapp");

        if (waIntent != null) {
            waIntent.putExtra(Intent.EXTRA_TEXT, link);//
            startActivity(Intent.createChooser(waIntent, "Share with"));
        } else {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
        }
    }


    private void shareFaceBook(){

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        // intent.putExtra(Intent.EXTRA_SUBJECT, "Foo bar"); // NB: has no effect!
        intent.putExtra(Intent.EXTRA_TEXT, link);

        // See if official Facebook app is found
        boolean facebookAppFound = false;
        List<ResolveInfo> matches = getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo info : matches) {
            if (info.activityInfo.packageName.toLowerCase().startsWith("com.facebook.katana")) {
                intent.setPackage(info.activityInfo.packageName);
                facebookAppFound = true;
                break;
            }
        }

        // As fallback, launch sharer.php in a browser
        if (!facebookAppFound) {
            String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + link;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
        }

        startActivity(intent);
    }
}
