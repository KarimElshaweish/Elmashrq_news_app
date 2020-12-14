package com.mashreq.app.RoomCaching.DAO.UserDAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mashreq.app.model.modeldb.Data.Data;

import java.util.List;

@Dao
public interface IUserDao {
    @Insert
    void registerUser(Data user);

    @Query("select * from user_data")
    List<Data> getUser();

}
