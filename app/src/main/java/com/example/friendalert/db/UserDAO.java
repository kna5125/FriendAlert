package com.example.friendalert.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM users WHERE preferedContact = :preferedContact " +
            "ORDER BY phoneNumber COLLATE NOCASE, userID")
    LiveData<List<User>> getPrefered(boolean preferedContact);

    @Query("SELECT * FROM users ORDER BY phoneNumber COLLATE NOCASE, userID")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM users WHERE userID = :userID")
    User getById(int userID);

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User... user);

    @Query("DELETE FROM users WHERE userID = :userID")
    void delete(int userID);
}
