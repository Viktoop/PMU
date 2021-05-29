package com.example.projekatcats;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
public class SettingsDialog extends Dialog {

    private SettingsDialogListener listener;

    private ImageButton touchButton;
    private boolean cantouch;

    private ImageButton musicButton;
    private boolean musicplaying;


    public SettingsDialog(@NonNull Context context, boolean musicplaying, boolean cantouch) {
        super(context);
        this.cantouch = cantouch;
        this.musicplaying = musicplaying;
        listener = (SettingsDialogListener) context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_settings);

        touchButton = findViewById(R.id.touchbutton);
        musicButton = findViewById(R.id.musicbutton);

        if(cantouch){
            touchButton.setImageResource(R.drawable.touchon);
        } else {
            touchButton.setImageResource(R.drawable.touchoff);
        }

        if(musicplaying) {
            musicButton.setImageResource(R.drawable.soundon);
        }
        else {
            musicButton.setImageResource(R.drawable.soundoff);
        }


        musicButton.setOnClickListener(clicklistener);
        touchButton.setOnClickListener(clicklistener);

    }

    private View.OnClickListener clicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.musicbutton:
                    listener.switchMusic();

                    if(musicplaying) {
                        musicButton.setImageResource(R.drawable.soundoff);
                        musicplaying = false;
                    }
                    else {
                        musicButton.setImageResource(R.drawable.soundon);
                        musicplaying = true;
                    }
                    break;

                case R.id.touchbutton:
                    listener.toggleTouch();

                    if(cantouch) {
                        touchButton.setImageResource(R.drawable.touchoff);
                        cantouch = false;
                    }
                    else {
                        touchButton.setImageResource(R.drawable.touchon);
                        cantouch = true;
                    }
                    break;

                default:
                    break;
            }
        }
    };


    public interface SettingsDialogListener{
        void switchMusic();
        void toggleTouch();
    }
}
