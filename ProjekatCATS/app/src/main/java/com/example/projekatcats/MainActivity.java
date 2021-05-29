package com.example.projekatcats;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private List<Player> players;
    private int currentPlayer;
    private TextView playerNameTV;
    private int idPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.players = AppDatabase.getInstance(getApplicationContext()).PlayerDao().getAll();

        AllWeapons allWeapons = new AllWeapons(getApplicationContext());
        playerNameTV = findViewById(R.id.playerNameTextView);
        if(!players.isEmpty()){
            currentPlayer = players.size()-1;
            playerNameTV.setText(players.get(currentPlayer).getNickname());
            idPlayer = players.get(currentPlayer).uid;
        } else {
            playerNameTV.setText("");
        }

        ImageButton previousbutton = findViewById(R.id.previousbutton);
        ImageButton nextbutton = findViewById(R.id.nextbutton);
        ImageButton addbutton = findViewById(R.id.addbutton);
        ImageButton playbutton = findViewById(R.id.playbutton);

        previousbutton.setOnClickListener(clickListener);
        nextbutton.setOnClickListener(clickListener);
        playbutton.setOnClickListener(clickListener);
        addbutton.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.addbutton:
                    Intent intent = new Intent(MainActivity.this, AddPlayerActivity.class);
                    startActivityForResult(intent, 1);
                    break;

                case R.id.nextbutton:
                    if(!players.isEmpty()){
                        currentPlayer = (currentPlayer + 1) % players.size();
                        playerNameTV.setText(players.get(currentPlayer).getNickname());
                        idPlayer = players.get(currentPlayer).uid;
                    }
                    break;

                case R.id.previousbutton:
                    if(!players.isEmpty()){
                        currentPlayer = (currentPlayer-1);
                        if(currentPlayer < 0) currentPlayer = players.size()-1;
                        playerNameTV.setText(players.get(currentPlayer).getNickname());
                        idPlayer = players.get(currentPlayer).uid;
                    }
                    break;

                case R.id.playbutton:
                    Intent garageIntent = new Intent(MainActivity.this, GarageActivity.class);
                    garageIntent.putExtra("idPlayer",idPlayer);
                    startActivity(garageIntent);
                    break;


                default:
                     break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.players = AppDatabase.getInstance(getApplicationContext()).PlayerDao().getAll();
                currentPlayer = players.size()-1;
                playerNameTV.setText(players.get(currentPlayer).getNickname());
            }
        }
    }
}
