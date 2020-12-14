package com.mashreq.app.RoomCaching.DAO.NewsDAO.LatestNewsDAO;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mashreq.app.model.modeldb.ElmashrqNews.LatestNews;
import com.mashreq.app.model.modeldb.News.NewsDataModel;

import java.util.List;

@Dao
public interface ILatestNewsDao {
    @Insert
    void addLatestNews(List<NewsDataModel>newsDataModelMutableLiveData);

    @Query("select * from news_data")
    List<NewsDataModel>getAllNews();


}
