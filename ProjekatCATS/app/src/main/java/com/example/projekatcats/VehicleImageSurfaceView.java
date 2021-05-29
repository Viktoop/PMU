package com.example.projekatcats;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.LinkedList;
import java.util.List;

public class VehicleImageSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Vehicle vehicle;

    private int surfaceWidth;
    private int surfaceHeight;

    private Context context;
    private int idPlayer;

    int[] coord;


    public VehicleImageSurfaceView(Context context, int idPlayer, int[]coord) {
        super(context);
        setZOrderOnTop(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.context = context;
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        this.coord = coord;
        this.idPlayer = idPlayer;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            int[] coord = new int[2];
            coord[0] = this.getWidth()/2;
            coord[1] = this.getHeight()/2;
            VehicleDB vehicleDB = AppDatabase.getInstance(context).VehicleDao().getById(idPlayer);
            if (vehicleDB == null) {
                VehicleDB newVDB = new VehicleDB(idPlayer, R.drawable.vehicle, 200, 0, 6, 6);
                AppDatabase.getInstance(context).VehicleDao().insert(newVDB);
                vehicle = new Vehicle(context, idPlayer,coord);
            } else {
                vehicle = new Vehicle(context, idPlayer,coord);
                if (vehicleDB.topWeapon != 0) {
                    Weapon weapon = AllWeapons.weapons.get(vehicleDB.topWeapon-1);
                    vehicle.topWeaponSlot.weapon = weapon;
                    vehicle.topWeaponSlot.isEmpty = false;
                    vehicle.power += weapon.power;
                    vehicle.energy -= weapon.energy;
                    vehicle.health += weapon.health;
                }
                if (vehicleDB.bottomWeapon != 0) {
                    Weapon weapon = AllWeapons.weapons.get(vehicleDB.bottomWeapon-1);
                    vehicle.bottomWeaponSlot.weapon = weapon;
                    vehicle.bottomWeaponSlot.isEmpty = false;
                    vehicle.power += weapon.power;
                    vehicle.energy -= weapon.energy;
                    vehicle.health += weapon.health;
                }
                if (vehicleDB.backWheel != 0) {
                    Weapon weapon = AllWeapons.weapons.get(vehicleDB.backWheel-1);
                    vehicle.backWheelSlot.weapon = weapon;
                    vehicle.backWheelSlot.isEmpty = false;
                    vehicle.health += weapon.health;
                    vehicle.energy += weapon.energy;
                    vehicle.totalenergy += weapon.energy;
                }
                if (vehicleDB.frontWheel != 0) {
                    Weapon weapon = AllWeapons.weapons.get(vehicleDB.backWheel-1);
                    vehicle.frontWheelSlot.weapon = weapon;
                    vehicle.frontWheelSlot.isEmpty = false;
                    vehicle.health += weapon.health;
                    vehicle.energy += weapon.energy;
                    vehicle.totalenergy += weapon.energy;
                }
            }
            vehicle.draw(canvas,true);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
