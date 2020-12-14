package com.mashreq.app.repository;

import com.google.gson.JsonObject;
import com.mashreq.app.model.modeldb.Activation.ActivationModel;
import com.mashreq.app.model.modeldb.AddNews.AddNewsDB;
import com.mashreq.app.model.modeldb.CitesNews.CitesNews;
import com.mashreq.app.model.modeldb.Cities.CitesModel;
import com.mashreq.app.model.modeldb.ElmashrqManualData.ElmashrqNews;
import com.mashreq.app.model.modeldb.ElmashrqNews.News;
import com.mashreq.app.model.modeldb.ForgetPassword.ForgetPasswordModel;
import com.mashreq.app.model.modeldb.LiveStreamDB.LiveSteamDB;
import com.mashreq.app.model.modeldb.Register.LoginModel;
import com.mashreq.app.model.modeldb.Report.ReportsModel;
import com.mashreq.app.model.modeldb.Resource.ResourceDB;
import com.mashreq.app.model.modeldb.ResourceNews.ResourceNews;
import com.mashreq.app.model.modeldb.Signup.SignUpModel;
import com.mashreq.app.model.modeldb.SingleNew.SingleNewsModel;
import com.mashreq.app.model.modeldb.export.ExportModel;
import com.mashreq.app.network.RetrofitInterfaces;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;

public class Repository {
    private RetrofitInterfaces retrofitService;

    @Inject
    public Repository(RetrofitInterfaces retrofitService) {
        this.retrofitService = retrofitService;
    }

    public Observable<CitesNews>getCitesNews(String id){
        return retrofitService.getCityNews(id);
    }

    public Observable<ActivationModel>activateAccount(JsonObject jsonObject,String token){
        return retrofitService.activateCode(jsonObject,token);
    }

    public Observable<AddNewsDB>addNews(String token,String cityId, String catID, String  title, String content , MultipartBody.Part image,
                                        String video, String date){
        return retrofitService.addNews(catID,
                title,
                content,
                image,
                date,
                null,
                "Bearer "+token);
    }


    public Observable<ExportModel>getAllExports(){
        return retrofitService.getAllExports();
    }

    public Observable<ActivationModel> chnagePasswordAPI(JsonObject data, String token) {
        return retrofitService.changePassword(data,token);
    }


    public Observable<CitesModel>getCites(){
        return retrofitService.getCites();
    }

    public Observable<ElmashrqNews> getRepsonseFromApi() {
        return retrofitService.getHomeElmashraqNews();
    }


    public Observable<ResourceDB> getResponse(JsonObject data, String token) {
        return retrofitService.followOrUnFollow(data, token) ;
    }

    public Observable<ForgetPasswordModel> getSigninResponse(String email) {
        return retrofitService.forgetPassword(email);
    }

    public Observable<News> getListResult(int type, int catId) {
        return retrofitService.getHomeNews();
    }

    public Observable<LiveSteamDB> getLiveSteam() {
        return retrofitService.getLive();
    }

    public Observable<LoginModel> getLoginResponse(JsonObject parmsJsonObject) {
        return retrofitService.login(parmsJsonObject);
    }

    public Observable<ReportsModel> getImportantNews() {
        return retrofitService.getImportantNews();
    }

    public Observable<ResourceNews> getResourceNews(String id) {
        return retrofitService.getResourceNews(id);
    }

    public Observable<ResourceDB> getResources(String token) {
        return retrofitService.getResources(token);
    }

    public Observable<SignUpModel> signUp(JsonObject jsonObject) {
        return retrofitService.signup(jsonObject);
    }

    public Observable<SingleNewsModel> getSingleNews(int id) {
        return retrofitService.getSingleNews(id);
    }
}
