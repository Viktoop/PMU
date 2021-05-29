package com.example.projekatcats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

public class FightActivity extends AppCompatActivity {

    private FightSurfaceView fightSurfaceView;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fight);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        layout = findViewById(R.id.fightlayout);
        Intent intent = getIntent();
        int idPlayer = intent.getIntExtra("idPlayer",0);
        int[] coord = new int[2];
        coord[0] = layout.getWidth()/4;
        coord[1] = layout.getHeight()/5*3;
        fightSurfaceView =  new FightSurfaceView(getApplicationContext(),idPlayer,coord);

        layout.addView(fightSurfaceView);
        fightSurfaceView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fightSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
