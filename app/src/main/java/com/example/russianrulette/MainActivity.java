package com.example.russianrulette;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements View.OnClickListener {
    private SoundPool sounds;

    private int soundShot, soundShotFalse, soundRollet;
    private boolean isBlood;

    private int currentBulletPosition, currentPos = 1;

    private Button shot, rollet;
    private ImageView blood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        startGame();
        createSoundPool();
        loadSounds();
    }

    private void init() {
        shot = findViewById(R.id.shot);
        rollet = findViewById(R.id.rollet);

        blood = findViewById(R.id.blood);

        shot.setOnClickListener(this);
        rollet.setOnClickListener(this);
    }

    private void loadSounds() {
        soundShot = sounds.load(this, R.raw.revolver_shot, 1);
        soundShotFalse = sounds.load(this, R.raw.gun_false, 1);
        soundRollet = sounds.load(this, R.raw.revolver_baraban, 1);
    }

    private void createSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            createNewSoundPool();
        } else {
            createOldSoundPool();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        sounds = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        sounds = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
    }

    private void startGame() {
        currentBulletPosition = getRandom(1, 6);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shot: {
                if (currentPos == currentBulletPosition) {
                    shotFunc();
                } else {
                    falseShotFunc();
                }
                break;
            }

            case R.id.rollet: {
                rollerFunc();
                break;
            }
        }
    }

    private void rollerFunc() {
        currentPos = getRandom(1, 6);
        sounds.play(soundRollet, 1, 1, 1, 0, 1);
        if (isBlood) {
            blood.setVisibility(View.INVISIBLE);
        }

    }

    private void falseShotFunc() {
        sounds.play(soundShotFalse, 1, 1, 1, 0, 1);
    }

    private void shotFunc() {
        sounds.play(soundShot, 1, 1, 1, 0, 1);
        isBlood = true;
        blood.setVisibility(View.VISIBLE);
        startGame();
    }

    private int getRandom(int min, int max) {
        return (int) Math.floor((Math.random() * (max - min + 1)) + min);
    }
}