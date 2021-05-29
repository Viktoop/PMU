package com.example.projekatcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GarageActivity extends AppCompatActivity implements SettingsDialog.SettingsDialogListener{

    public MediaPlayer musicPlayer;
    private int song;
    private int idPlayer;
    private boolean musicplaying;

    private boolean cantouch;

    private int newboxes;

    private VehicleImageSurfaceView vehicleSurfaceView;

    private ImageButton settingsButton;
    private ImageButton resultsButton;
    private ImageButton boxButton;
    private ImageButton fightButton;
    private LinearLayout vehicleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);

        Intent intent = getIntent();
        idPlayer = intent.getIntExtra("idPlayer",0);

        settingsButton = findViewById(R.id.settingsbutton);
        resultsButton = findViewById(R.id.resultsbutton);
        boxButton = findViewById(R.id.boxbutton);
        vehicleButton = findViewById(R.id.vehiclebutton);
        fightButton = findViewById(R.id.fightbuttongarage);


        settingsButton.setOnClickListener(clickListener);
        resultsButton.setOnClickListener(clickListener);
        boxButton.setOnClickListener(clickListener);
        vehicleButton.setOnClickListener(clickListener);
        fightButton.setOnClickListener(clickListener);



        cantouch = true;

        newboxes = 0;

        musicplaying = true;
        startMusic();
        musicPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if(song == 1){
                    setsong(2);
                    song = 0;
                } else {
                    setsong(1);
                    song=1;
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        int[] cord = new int[2];
        cord[0] = (int)vehicleButton.getX();
        cord[1] = (int)vehicleButton.getY();
        int x = vehicleButton.getWidth();
        int y = vehicleButton.getHeight();
        vehicleSurfaceView = new VehicleImageSurfaceView(this, idPlayer,null);

        vehicleButton.addView(vehicleSurfaceView);
    }

    private void startMusic(){
        song = 1;
        if(musicPlayer == null){
            musicPlayer = MediaPlayer.create(this, R.raw.beneaththeveil);
        }
        musicPlayer.start();
    }

    private void setsong(int num){
        if(num == 1){
            musicPlayer = MediaPlayer.create(this, R.raw.beneaththeveil);
        } else {
            musicPlayer = MediaPlayer.create(this, R.raw.hotlunchmonksonthemoon);
        }
        musicPlayer.start();
    }

    public void stopMusic(){
        if(musicPlayer != null){
            musicPlayer.release();
            musicPlayer = null;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.settingsbutton:
                    openSettingsDialog();
                    break;

                case R.id.resultsbutton:
                    openResultsDialog();
                    break;

                case R.id.boxbutton:
                    if(newboxes == 0) {
                        Toast.makeText(GarageActivity.this,"No new boxes", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case R.id.vehiclebutton:
                    Intent intent = new Intent(GarageActivity.this, VehicleActivity.class);
                    intent.putExtra("idPlayer",idPlayer);
                    startActivity(intent);
                    break;

                case R.id.fightbuttongarage:
                    Intent fintent = new Intent(GarageActivity.this, FightActivity.class);
                    fintent.putExtra("idPlayer",idPlayer);
                    startActivity(fintent);
                    break;

                default:
                    break;
            }
        }
    };

    private void openSettingsDialog(){
        SettingsDialog settingsDialog = new SettingsDialog(GarageActivity.this,musicplaying,cantouch);
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        settingsDialog.show();
    }

    private void openResultsDialog(){

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMusic();
        System.out.println("resuming");
        vehicleSurfaceView = new VehicleImageSurfaceView(this, idPlayer,null);
        vehicleButton.addView(vehicleSurfaceView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMusic();
    }

    @Override
    public void switchMusic() {
        if(musicplaying) {
            stopMusic();
            musicplaying = false;
        } else {
            startMusic();
            musicplaying = true;
        }
    }

    @Override
    public void toggleTouch() {
        cantouch = !cantouch;
    }
}
