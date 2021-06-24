package com.example.russianrulette;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class Logo extends Activity {
    private boolean isLoading = true;
    private int timeLoading = 5000;

    private TextView t1, t2;
    private ImageView img;

    private Animation fromLeft, fromRight, scale;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        init();
        loadAnimations();
        startAnimations();
        goNext();
    }

    private void startAnimations() {
        t1.startAnimation(fromLeft);
        t2.startAnimation(fromRight);
        img.startAnimation(scale);
    }

    private void loadAnimations() {
        fromLeft = AnimationUtils.loadAnimation(this, R.anim.from_left);
        fromRight = AnimationUtils.loadAnimation(this, R.anim.from_right);
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    private void init() {
        t1 = findViewById(R.id.text1);
        t2 = findViewById(R.id.text2);
        img = findViewById(R.id.imageView);
    }



    private void goNext() {
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (isLoading) {
                            try {
                                Thread.sleep(timeLoading);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            isLoading = false;
                            goMain();
                        }
                    }
                }
        ).start();
    }

    private void goMain() {
        Intent intent = new Intent(Logo.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLoading = false;
    }
}
