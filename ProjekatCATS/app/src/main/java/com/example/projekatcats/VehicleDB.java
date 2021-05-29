package com.example.projekatcats;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VehicleDB {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "imageId")
    public int imageId;

    @ColumnInfo(name = "topWeapon")
    public int topWeapon;

    @ColumnInfo(name = "bottomWeapon")
    public int bottomWeapon;

    @ColumnInfo(name = "backWheel")
    public int backWheel;

    @ColumnInfo(name = "frontWheel")
    public int frontWheel;

    @ColumnInfo(name = "health")
    public int health;

    @ColumnInfo(name = "power")
    public int power;

    @ColumnInfo(name = "energy")
    public int energy;

    @ColumnInfo(name = "totalenergy")
    public int totalenergy;

    public VehicleDB(int id, int imageId, int health, int power, int energy,int totalenergy) {
        this.id = id;
        this.imageId = imageId;
        this.health = health;
        this.power = power;
        this.energy = energy;
        this.totalenergy = totalenergy;
    }

    public void setTopWeapon(int topWeapon) {
        this.topWeapon = topWeapon;
    }

    public void setBottomWeapon(int bottomWeapon) {
        this.bottomWeapon = bottomWeapon;
    }

    public void setBackWheel(int backWheel) {
        this.backWheel = backWheel;
    }

    public void setFrontWheel(int frontWheel) {
        this.frontWheel = frontWheel;
    }
}
