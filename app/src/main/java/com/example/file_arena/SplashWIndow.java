package com.example.file_arena;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashWIndow extends AppCompatActivity {
    public static int SPLASH_TIME_OUT = 2000;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_window);
        img = findViewById(R.id.splashimg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homepage = new Intent(SplashWIndow.this, HomePage.class);
                startActivity(homepage);
                finish();
            }
        }, SPLASH_TIME_OUT);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splash_screen);
        img.startAnimation(myanim);

    }
}
