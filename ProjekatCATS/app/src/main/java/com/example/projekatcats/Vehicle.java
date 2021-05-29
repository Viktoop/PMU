package com.example.projekatcats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.annotation.Nullable;

public class Vehicle {
    private Context context;

    public Slot backWheelSlot;
    public Slot frontWheelSlot;
    public Slot topWeaponSlot;
    public Slot bottomWeaponSlot;

    public int startX;
    public int startY;


    public Bitmap vehicleBitmap;

    public boolean firstDraw;

    public int idPlayer;

    public int health;
    public int power;
    public int energy;
    public int totalenergy;

    public Vehicle(Context context, int idPlayer, int[] coord) {
        this.context = context;
        vehicleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.vehicle);
        firstDraw = true;
        health = 200;
        power = 0;
        energy = 6;
        totalenergy = 6;

        startX = coord[0]-vehicleBitmap.getWidth()/2;
        startY = coord[1]-vehicleBitmap.getHeight()/2;

        setSlots();
        this.idPlayer = idPlayer;
    }

    public void draw(Canvas canvas, boolean vehiclebackground) {


        if (vehiclebackground) {
            Bitmap background = BitmapFactory.decodeResource(context.getResources(), R.drawable.vehiclebackpart);
            canvas.drawBitmap(background, 130, 50, null);
        }

        canvas.drawBitmap(vehicleBitmap, startX, startY, null);

        backWheelSlot.draw(canvas);
        frontWheelSlot.draw(canvas);
        topWeaponSlot.draw(canvas);
        bottomWeaponSlot.draw(canvas);
    }


    public boolean addWeapon(int x, int y, Weapon weapon) {
        if (weapon.idWeapon == 6 || weapon.idWeapon == 7) {
            if (backWheelSlot.isInRegion(x, y)) {
                backWheelSlot.isEmpty = false;
                backWheelSlot.weapon = weapon;
                health += weapon.health;
                totalenergy += weapon.energy;
                energy += weapon.energy;
                return true;
            }
            if (frontWheelSlot.isInRegion(x, y)) {
                frontWheelSlot.isEmpty = false;
                frontWheelSlot.weapon = weapon;
                health += weapon.health;
                totalenergy += weapon.energy;
                energy += weapon.energy;
                return true;
            }
        } else {
            if (topWeaponSlot.isInRegion(x, y)) {
                if (energy - weapon.energy >= 0) {
                    topWeaponSlot.isEmpty = false;
                    topWeaponSlot.weapon = weapon;
                    power += weapon.power;
                    health += weapon.health;
                    energy -= weapon.energy;
                    return true;
                }
            }
            if (bottomWeaponSlot.isInRegion(x, y)) {
                if (energy - weapon.energy >= 0) {
                    bottomWeaponSlot.isEmpty = false;
                    bottomWeaponSlot.weapon = weapon;
                    power += weapon.power;
                    health += weapon.health;
                    energy -= weapon.energy;
                    return true;
                }
            }
        }
        return false;
    }

    public void setSlots() {
        backWheelSlot = new Slot(90, 220);
        frontWheelSlot = new Slot(480, 220);
        topWeaponSlot = new Slot(200, 90);
        bottomWeaponSlot = new Slot(350, 160);
    }

    public void touchEvent(int x, int y) {
        boolean update = false;
        if ((x > backWheelSlot.x - 50 && x < backWheelSlot.x + 50) && (y > backWheelSlot.y - 50 && y < backWheelSlot.y + 50)) {
            backWheelSlot.isEmpty = true;
            health -= backWheelSlot.weapon.health;
            energy -= backWheelSlot.weapon.energy;
            totalenergy -= backWheelSlot.weapon.energy;
            backWheelSlot.weapon = null;
            update = true;
        }
        if ((x > frontWheelSlot.x - 50 && x < frontWheelSlot.x + 50) && (y > frontWheelSlot.y - 50 && y < frontWheelSlot.y + 50)) {
            frontWheelSlot.isEmpty = true;
            health -= frontWheelSlot.weapon.health;
            energy -= frontWheelSlot.weapon.energy;
            totalenergy -= frontWheelSlot.weapon.energy;
            frontWheelSlot.weapon = null;
            update = true;
        }
        if ((x > topWeaponSlot.x - 50 && x < topWeaponSlot.x + 50) && (y > topWeaponSlot.y - 50 && y < topWeaponSlot.y + 50)) {
            topWeaponSlot.isEmpty = true;
            power -= topWeaponSlot.weapon.power;
            health -= topWeaponSlot.weapon.health;
            energy += topWeaponSlot.weapon.energy;
            topWeaponSlot.weapon = null;
            update = true;
        }
        if ((x > bottomWeaponSlot.x - 50 && x < bottomWeaponSlot.x + 50) && (y > bottomWeaponSlot.y - 50 && y < bottomWeaponSlot.y + 50)) {
            bottomWeaponSlot.isEmpty = true;
            power -= bottomWeaponSlot.weapon.power;
            health -= bottomWeaponSlot.weapon.health;
            energy += bottomWeaponSlot.weapon.energy;
            bottomWeaponSlot.weapon = null;
            update = true;
        }
        if (update) {
            VehicleDB newVDB = new VehicleDB(idPlayer, R.drawable.vehicle, health, power, energy, totalenergy);
            AppDatabase.getInstance(context).VehicleDao().insert(newVDB);
        }
    }

    public void update(int x){
        topWeaponSlot.x += x;
        bottomWeaponSlot.x += x;
        backWheelSlot.x += x;
        frontWheelSlot.x += x;
    }


    public class Slot {
        public int x;
        public int y;
        public boolean isEmpty;
        public Weapon weapon;


        public Slot(int x, int y) {
            this.x =  startX + x;
            this.y =  startY + y;
            isEmpty = true;
        }

        public boolean isInRegion(int x, int y) {
            if (((x > this.x - 50 && x < this.x + 50) && (y > this.y - 50 && y < this.y + 50)) && isEmpty) {
                return true;
            }

            return false;
        }

        public void draw(Canvas canvas) {
            if (!isEmpty) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), weapon.imageId);
                canvas.drawBitmap(bitmap,  x - weapon.x,  y - weapon.y, null);
            }
        }
    }
}
