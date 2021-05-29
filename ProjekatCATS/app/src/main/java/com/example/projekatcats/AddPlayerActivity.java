package com.example.projekatcats;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class AddPlayerActivity extends AppCompatActivity {

    ImageButton okButton;
    EditText nameET;
    EditText nicknameET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        okButton = findViewById(R.id.okbutton);
        nameET = findViewById(R.id.nameedittext);
        nicknameET = findViewById(R.id.nicknameedittext);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.getInstance(getApplicationContext()).PlayerDao().insert(new Player(nameET.getText().toString(),nicknameET.getText().toString()));
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
