package com.example.projekatcats;


import android.content.Context;

import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

public class VehicleSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private int startX;
    private int startY;

    private int surfaceWidth;
    private int surfaceHeight;

    private VehicleSurfaceViewListener listener;

    public Vehicle vehicle;

    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Thread thread;

    private boolean firstrun;

    private boolean running;
    private Context context;
    private int idPlayer;

    public int[] coordinates;

    public VehicleSurfaceView(Context context, int idPlayer, @Nullable int[] coordinates) {
        super(context);
        setZOrderOnTop(true);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        this.idPlayer = idPlayer;
        this.context = context;

        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        listener = (VehicleSurfaceViewListener) context;
        firstrun = true;

        vehicle = new Vehicle(context, idPlayer, coordinates);
    }

    @Override
    public void run() {
        while (running) {
            if (surfaceHolder.getSurface().isValid()) {
                canvas = surfaceHolder.lockCanvas();
                vehicle.draw(canvas,true);
                if (firstrun) {
                    getVehicleParts(vehicle);
                    firstrun = false;
                }

                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                canvas.drawText("O",2,2,paint);
                canvas.drawText("Ox",vehicle.startX,vehicle.startY,paint);
                canvas.drawText("Op",vehicle.bottomWeaponSlot.x,vehicle.bottomWeaponSlot.y,paint);
                canvas.drawText("Od",vehicle.topWeaponSlot.x,vehicle.topWeaponSlot.y,paint);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void getVehicleParts(Vehicle vehicle) {
        VehicleDB vehicleDB = AppDatabase.getInstance(context).VehicleDao().getById(idPlayer);
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
            Weapon weapon = AllWeapons.weapons.get(vehicleDB.backWheel - 1);
            vehicle.frontWheelSlot.weapon = weapon;
            vehicle.frontWheelSlot.isEmpty = false;
            vehicle.health += weapon.health;
            vehicle.energy += weapon.energy;
            vehicle.totalenergy += weapon.energy;
        }
    }

    public void resume() {
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        running = false;
        VehicleDB vehicleDB = new VehicleDB(idPlayer,
                R.drawable.vehicle,
                vehicle.health,
                vehicle.power,
                vehicle.energy,
                vehicle.totalenergy);
        if (!vehicle.backWheelSlot.isEmpty)
            vehicleDB.setBackWheel(vehicle.backWheelSlot.weapon.idWeapon);
        if (!vehicle.frontWheelSlot.isEmpty)
            vehicleDB.setFrontWheel(vehicle.frontWheelSlot.weapon.idWeapon);
        if (!vehicle.topWeaponSlot.isEmpty)
            vehicleDB.setTopWeapon(vehicle.topWeaponSlot.weapon.idWeapon);
        if (!vehicle.bottomWeaponSlot.isEmpty)
            vehicleDB.setBottomWeapon(vehicle.bottomWeaponSlot.weapon.idWeapon);
        AppDatabase.getInstance(getContext()).VehicleDao().insert(vehicleDB);
        vehicleDB.id = 0;
        AppDatabase.getInstance(getContext()).VehicleDao().insert(vehicleDB);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
        resume();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public boolean addWeapon(int x, int y, Weapon weapon) {
        return vehicle.addWeapon(x, y, weapon);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            pause();
            vehicle.touchEvent((int) event.getX(), (int) event.getY());
            listener.updateParts();
            resume();
        }
        return true;
    }

    public interface VehicleSurfaceViewListener {
        void updateParts();
    }


}
