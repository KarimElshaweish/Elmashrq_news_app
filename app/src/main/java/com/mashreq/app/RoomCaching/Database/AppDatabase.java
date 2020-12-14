package com.mashreq.app.RoomCaching.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mashreq.app.RoomCaching.DAO.NewsDAO.LatestNewsDAO.ILatestNewsDao;
import com.mashreq.app.RoomCaching.DAO.UserDAO.IUserDao;
import com.mashreq.app.model.modeldb.Data.Data;
import com.mashreq.app.model.modeldb.News.NewsDataModel;

@Database(entities = {Data.class, NewsDataModel.class},version =5)
public abstract class AppDatabase extends RoomDatabase {
    private static final String dbName="elmashraq";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context,AppDatabase.class,dbName)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract IUserDao iUserDao();
    public abstract ILatestNewsDao iLatestNewsDao();
}
