package com.mashreq.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mashreq.app.LoadingDialog.LoadingDailog;
import com.mashreq.app.R;
import com.mashreq.app.databinding.ResourceCustomeBinding;
import com.mashreq.app.model.modeldb.Resource.Datum;
import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.view.actvivties.NewsPageActivity;
import com.mashreq.app.viewmodel.FollowOrUnFollowViewModel;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.List;

public class ResourcesAdapter extends RecyclerView.Adapter<ResourcesAdapter.ViewHolder> {
    List<Datum>list;
    Context _ctx;
    FollowOrUnFollowViewModel followOrUnFollowViewModel;

    LoadingDailog loadingDailog;
    public ResourcesAdapter(FollowOrUnFollowViewModel followOrUnFollowViewModel,Context context) {
        this.followOrUnFollowViewModel = followOrUnFollowViewModel;
        this._ctx=context;
        loadingDailog=new LoadingDailog(_ctx);
    }

    public ResourcesAdapter(Context context) {
        _ctx=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ResourceCustomeBinding resourceCustomeBinding=
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.resource_custome,parent,false);
        return new ViewHolder(resourceCustomeBinding);
    }
    String token;
    Boolean follow;
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        if(list!=null && list.size()>0){
            final Datum dt=list.get(position);
            Glide.with(_ctx).load(dt.getLogo()).into(holder.resourceCustomeBinding.logo);
            String std="عدد المتابعين";
            std+=" "+dt.getNumberOfFollow()+" ";
            std+="متابع";
            follow=dt.getIsFollow();
            holder.resourceCustomeBinding.followers.setText(std);
            holder.resourceCustomeBinding.title.setText(dt.getName());
            holder.resourceCustomeBinding.rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(_ctx, NewsPageActivity.class);
                    intent.putExtra("followers",dt.getNumberOfFollow());
                    intent.putExtra("logo",dt.getLogo());
                    intent.putExtra("name",dt.getName());
                    intent.putExtra("id",dt.getId());
                    intent.putExtra("is_follow",follow);
                    _ctx.startActivity(intent);
                }
            });
            if(dt.getIsFollow())
                holder.resourceCustomeBinding.followBtn.setText("إلغاء المتابعة");
            else
                holder.resourceCustomeBinding.followBtn.setText("متابعة");

            SharedPreferences sharedPreferences=_ctx.getSharedPreferences("data",Context.MODE_PRIVATE);

            token=sharedPreferences.getString("token","");
            if(token.equals(""))
                token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDc2NWUxYjExYTU4MDE1ZTNkNTRjOTMyMjAyOTQxNzg3YWUzNWU2OTMxODhjZWRmMjQ3NmJlMTBjZjkwZmM5NTIyMzA5ZDhmYmQ3OGZkZjQiLCJpYXQiOjE2MDM4Nzk5MzAsIm5iZiI6MTYwMzg3OTkzMCwiZXhwIjoxNjM1NDE1OTMwLCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.UEnM0DUoUCPTpFLTTPGWkqDlIzHARnQ9zHvDruNqngri27pHGNX85TnRhD4I0U6Rp9cnsCsYp6aNpgjxMeLyYXkIpz9YCG--36DwsgGxq0dYw7RvDNMYh9KuOGXW0G38c86On6vgvoczeLkAYk_OLPmppcXAXZu6I-We_olVnZVmQ4d6xrrCAl7gwHgm2eXrvRpOSrYpWneLGOWB4mFQQ3pSxqxWsjy4Tc6zQuAf4dxv8yzrEAGLtFa_Pjsq27r1xnII8wVD_xoDQx5Vh2-hcdKqVNqS3fzwc_hZEI5eHuBMH3dHeqfQRH9xtNsXmooegkgS-6-y3VGQBxS1LRvVoBJIiwE0x_TdyoNVnsCS4kGofq76dAF-96oEoOzocBcRLt-ceALOnE6VQsRPenXcGz1tTZiEk6lnrHy4pFhWmttefHwULGaqOHte7a4Z2im7kXUC45CFC03bFW2HXBsuGB_nYFotElAE8psbfTfGm6AbNlOndsPLABnN9NbmdOXTrzHXAzPDMiEHe0hWLqcUZWCqM1dyVHg7FSuwmSCA0L2MALNiqM-f9ACbykBRbF_RgA1aH-Ja8fptkf6tanqHU-E1GolrjjHk0-ERPq2xH1h4V2Wvi4gDTpWtjq4kO8EeytA6hA1Fn2MGvnUNkM3j3CuIEcdNCPvH6-dzC91SJDE";
            else
                token="Bearer "+token;
            holder.resourceCustomeBinding.followBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followOrUnFollow(token, String.valueOf(dt.getId()),holder.resourceCustomeBinding.followBtn);
                }
            });
        }
    }

    private void followOrUnFollow(String token, String sourceId, final Button followBtn){
        loadingDailog.showDialog();
        followOrUnFollowViewModel.FollowOrUnFollow(token,sourceId);
        followOrUnFollowViewModel.getMutableLiveData().observe((LifecycleOwner) _ctx, new Observer<ResourceDB>() {
            @Override
            public void onChanged(ResourceDB resourceDB) {
                String text=followBtn.getText().toString();
                if(resourceDB.getData().size()!=0) {
                    if(!text.equals("إلغاء المتابعة")) {
                        MDToast mdToast = MDToast.makeText(_ctx, resourceDB.getMessage(), MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
                        mdToast.show();
                        followBtn.setText("إلغاء المتابعة");
                        follow=true;
                    }else{
                        MDToast mdToast = MDToast.makeText(_ctx, resourceDB.getMessage(), MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
                        mdToast.show();
                        followBtn.setText("متابعة");
                        follow=false;
                    }
                }
                loadingDailog.dismissDialog();
            }
        });
    }
    public void setNewList(List<Datum>list){
        this.list=list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(list==null)
            return 0;
        else
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ResourceCustomeBinding resourceCustomeBinding;
        public ViewHolder(@NonNull ResourceCustomeBinding itemView) {
            super(itemView.getRoot());
            this.resourceCustomeBinding=itemView;
        }
    }
}
