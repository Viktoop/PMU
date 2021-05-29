package com.example.projekatcats;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VehicleDAO {
    @Query("SELECT * FROM vehicledb")
    List<VehicleDB> getAll();

    @Query("SELECT * FROM vehicledb WHERE id = :vid")
    VehicleDB getById(int vid);

    @Query("DELETE FROM vehicledb WHERE id = :vid ")
    void  deleteById(int vid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VehicleDB vehicleDB);
}
