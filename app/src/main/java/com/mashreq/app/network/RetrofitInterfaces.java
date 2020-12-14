package com.mashreq.app.network;


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

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitInterfaces {
  @Headers({
          "Accept: application/json",
          "Content-Type:application/json"
  })
  @POST("Auth_general/login")
  Observable<LoginModel> login(@Body JsonObject data);




  @Headers("Content-Type:application/json")
  @GET("News/home")
  Observable<News>getHomeNews();
  @Headers("Content-Type:application/json")
  @GET("News/news_by_source")
  Observable<ResourceNews>getResourceNews(@Query("source_id")String sourceID);

  @Headers("Content-Type:application/json")
  @POST("News/like_new")
  Observable<News>likeOrUnlike(@Query("new_id")String newsID,@Header("Authorization")String token);

  @Headers("Content-Type:application/json")
  @GET("News/news_by_city")
  Observable<CitesNews> getCityNews(@Query("city_id")String cityID);


  @Headers("Content-Type:application/json")
  @POST("Resource/add_resource")
  Observable<ResourceDB>followOrUnFollow(@Body JsonObject data ,@Header("Authorization")String token);

  @Headers("Content-Type:application/json")
  @GET("News/filter_news")
  Observable<ElmashrqNews>getHomeElmashraqNews();

  @Headers({"Content-Type:application/json",
  "type:3"})
  @GET("News/filter_news")
  Observable<ReportsModel>getImportantNews();


  @Headers("Content-Type:application/json")
  @POST("Auth_general/forget_password")
  Observable<ForgetPasswordModel>forgetPassword(@Query("email")String email);


  @Headers({"Accept: application/json"})
  @POST("Auth_general/register")
  Observable<SignUpModel>signup(@Body JsonObject data);

  @Headers("Content-Type:application/json")
  @POST("Auth_private/check_active_code")
  Observable<ActivationModel>activateCodeSignup(@Body JsonObject data, @Header("Authorization")String token);
  @Headers("Content-Type:application/json")
  @POST("Auth_private/check_password_code")
  Observable<ActivationModel>activateCode(@Body JsonObject data, @Header("Authorization")String token);
  @Headers({"Accept: application/json"})
  @GET("News/get_cites")
  Observable<CitesModel>getCites();

  @Headers({"Accept: application/json"})
  @GET("News/get_exports")
  Observable<ExportModel>getAllExports();







  @Headers({"Accept: application/json"})
  @POST("Auth_private/reset_password")
  Observable<ActivationModel>changePassword(@Body JsonObject data,@Header("Authorization")String token);


  @Headers({"Accept: application/json"})
  @GET("Live_stream/Live_by_cat/6")
  Observable<LiveSteamDB>getLive();

  @Multipart
  @POST("News/add_news")
  Observable<AddNewsDB>addNews(
         // @Query("city_id") String city_id,
          @Query("cat_id")String cat_id,
          @Query("title") String title,
          @Query("content_new")String content_new,
          @Part MultipartBody.Part image ,
          @Query("date")String date,
          @Part MultipartBody.Part video,
          @Header("Authorization")String token);



  @Headers({"Accept: application/json"})
  @GET("Resource/get_resources")
  Observable<ResourceDB>getResources(@Header("Authorization")String token);

  @Headers({"Accept: application/json"})
  @GET("News/single_new/{id}")
  Observable<SingleNewsModel>getSingleNews(@Path("id")int id);

}
