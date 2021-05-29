package com.example.projekatcats;

import android.content.Context;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatImageView;

public class Weapon extends AppCompatImageView {

    public String name;
    public int power;
    public int health;
    public int energy;
    public int imageId;

    public int idWeapon;

    public int x;
    public int y;

    public Weapon(Context context,String name, int power, int health, int energy, int imageId, int x, int y, int id) {
        super(context);
        this.name = name;
        this.power = power;
        this.health = health;
        this.energy = energy;
        this.imageId = imageId;

        this.x = x;
        this.y = y;

        this.idWeapon = id;
    }
}
