package com.example.projekatcats;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class VehicleActivity extends AppCompatActivity implements  VehicleSurfaceView.VehicleSurfaceViewListener{


    private LinearLayout myLayout = null;

    private Weapon draggedWeapon;

    private LinearLayout vehicleBox;
    private int idPlayer;

    private VehicleSurfaceView vehicleSurfaceView;

    private TextView powertextwview;
    private TextView healthtextview;
    private TextView energytextview;
    private ImageView weaponimageview;
    private TextView weapontextview;

    private TextView vehiclepowertextview;
    private TextView vehiclehealthtextview;
    private TextView vehicleenergytextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle);

        Intent intent = getIntent();
        idPlayer = intent.getIntExtra("idPlayer",0);
        vehicleBox = findViewById(R.id.vehiclebox);

        vehiclepowertextview = findViewById(R.id.vehiclepowertextview);
        vehiclehealthtextview = findViewById(R.id.vehiclehealthtextview);
        vehicleenergytextview = findViewById(R.id.vehicleenergytextview);


        vehicleBox.setOnDragListener(dragListener);

        myLayout = findViewById(R.id.weapons);
        powertextwview = findViewById(R.id.powertextview);
        healthtextview = findViewById(R.id.healthtextview);
        energytextview = findViewById(R.id.energytextview);
        weaponimageview = findViewById(R.id.weaponimageview);
        weapontextview = findViewById(R.id.weapontextwiew);

        addWeapons();

        powertextwview.setText("" + AllWeapons.weapons.get(0).power);
        healthtextview.setText("" + AllWeapons.weapons.get(0).health);
        energytextview.setText("" + AllWeapons.weapons.get(0).energy);
        weaponimageview.setBackgroundResource(AllWeapons.weapons.get(0).imageId);
        weapontextview.setText("" + AllWeapons.weapons.get(0).name);


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int[] coord = new int[2];
        coord[0] = vehicleBox.getWidth()/2;
        coord[1] = vehicleBox.getHeight()/2;
        vehicleSurfaceView = new VehicleSurfaceView(this,idPlayer,coord);
        vehicleBox.addView(vehicleSurfaceView);

        vehiclepowertextview.setText(""+vehicleSurfaceView.vehicle.power);
        vehiclehealthtextview.setText(""+vehicleSurfaceView.vehicle.health);
        vehicleenergytextview.setText(""+vehicleSurfaceView.vehicle.energy +"/" + vehicleSurfaceView.vehicle.totalenergy);
    }

    public void addWeapons() {
        for (int i = 0; i < AllWeapons.weapons.size(); i++) {
            LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setMargins(0, 0, 30, 0);
            AllWeapons.weapons.get(i).setLayoutParams(layout);
            AllWeapons.weapons.get(i).setBackgroundResource(AllWeapons.weapons.get(i).imageId);
            AllWeapons.weapons.get(i).setOnClickListener(clickListener);
            AllWeapons.weapons.get(i).setOnLongClickListener(longClickListener);
            myLayout.addView(AllWeapons.weapons.get(i));
        }
    }

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            powertextwview.setText("" + ((Weapon) v).power);
            healthtextview.setText("" + ((Weapon) v).health);
            energytextview.setText("" + ((Weapon) v).energy);
            weaponimageview.setBackgroundResource(((Weapon) v).imageId);
            weapontextview.setText("" + ((Weapon) v).name);
        }
    };

    @TargetApi(Build.VERSION_CODES.N)
    public View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDragAndDrop(clipData, shadowBuilder, v, 0);
            draggedWeapon = (Weapon) v;
            return true;
        }
    };

    public View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            int action = event.getAction();

            if (action == DragEvent.ACTION_DROP) {
                if(vehicleSurfaceView.addWeapon((int) event.getX(), (int) event.getY(), draggedWeapon)){
                    vehiclepowertextview.setText(""+vehicleSurfaceView.vehicle.power);
                    vehiclehealthtextview.setText(""+vehicleSurfaceView.vehicle.health);
                    vehicleenergytextview.setText(""+vehicleSurfaceView.vehicle.energy +"/" + vehicleSurfaceView.vehicle.totalenergy);
                }
            }
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    protected void onPause() {
        super.onPause();
        vehicleSurfaceView.pause();
        for (int i = 0; i < AllWeapons.weapons.size(); i++) {
            myLayout.removeView(AllWeapons.weapons.get(i));
        }
    }

    @Override
    public void updateParts() {
        vehiclepowertextview.setText(""+vehicleSurfaceView.vehicle.power);
        vehiclehealthtextview.setText(""+vehicleSurfaceView.vehicle.health);
        vehicleenergytextview.setText(""+vehicleSurfaceView.vehicle.energy +"/" + vehicleSurfaceView.vehicle.totalenergy);
    }
}
