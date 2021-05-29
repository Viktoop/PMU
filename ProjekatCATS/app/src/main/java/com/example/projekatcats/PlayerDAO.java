package com.example.projekatcats;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDAO {
    @Query("SELECT * FROM player")
    List<Player> getAll();

    @Query("SELECT * FROM player WHERE uid IN (:userIds)")
    List<Player> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM player WHERE uid IN (:id)")
    Player findById(int id);

    @Insert
    void insert(Player player);

    @Delete
    void delete(Player players);
}