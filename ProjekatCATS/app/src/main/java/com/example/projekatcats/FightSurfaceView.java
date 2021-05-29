package com.example.projekatcats;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

import java.util.LinkedList;
import java.util.List;

public class FightSurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    public int idPlayer;
    public Player player;

    public Thread mThread;
    public boolean running;
    public Canvas canvas;
    public SurfaceHolder surfaceHolder;

    public List<VehicleDB> allVehicles;
    public Vehicle playerVehicle;
    public Vehicle enemyVehicle;

    public int playerVehicleX;
    public int playerVehicleY;

    public Context context;
    public Bitmap background;

    public boolean fistRun;


    public FightSurfaceView(Context context, int idPlayer, int[] coordinates) {
        super(context);
        this.idPlayer = idPlayer;
        fistRun = true;
        this.context = context;
        this.player = AppDatabase.getInstance(context).PlayerDao().findById(idPlayer);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.backfight);
        background = Bitmap.createScaledBitmap(background, coordinates[0]*4, coordinates[1]*5/3, false);
        playerVehicle = new Vehicle(context,idPlayer,coordinates);
        enemyVehicle = new Vehicle(context,0,coordinates);

        getVehicleParts(playerVehicle);
        getVehicleParts(enemyVehicle);

        surfaceHolder = getHolder();

    }

    @Override
    public void run() {
        for (int i = 4; i>0; i--){
            prepdraw(i);
            control(1000);
        }
        while (running) {
            draw();
            update();
            control(10);
        }
    }

    public void prepdraw(int i){
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            canvas.drawBitmap(background, 0, 0, null);

            playerVehicle.draw(canvas,false);

            Paint paint = new Paint();
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.godzilla);
            paint.setColor(Color.WHITE);
            paint.setTextSize(300);
            paint.setTypeface(typeface);
            canvas.drawText(""+i,canvas.getWidth()/2,canvas.getHeight()/2,paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @TargetApi(20)
    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawBitmap(background, 0, 0, null);
            drawTopBar(canvas);

            playerVehicle.draw(canvas,false);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void drawTopBar(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);

        paint.setStrokeWidth(10);
        paint.setTextSize(100);
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.godzilla);
        paint.setTypeface(typeface);
        canvas.drawRect(50,70,canvas.getWidth()/2-300,150,paint);
        canvas.drawText(player.getName(),100,250,paint);


        canvas.drawRect(canvas.getWidth()/2+300,70,canvas.getWidth()-50,150,paint);
        canvas.drawText("enemy",canvas.getWidth()-350,250,paint);

        paint.setColor(Color.rgb(153, 0, 0));
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(canvas.getWidth()/2,50,canvas.getWidth()/2,250,paint);
    }

    public void update() {
        playerVehicle.startX+=5;
        playerVehicle.update(5);
    }

    public void control(int ms) {
        try {
            mThread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        running = false;
        try {
            mThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        running = true;
        mThread = new Thread(this);
        mThread.start();
    }

    public void getVehicleParts(Vehicle vehicle) {
        VehicleDB vehicleDB = AppDatabase.getInstance(getContext()).VehicleDao().getById(vehicle.idPlayer);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
