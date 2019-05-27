package com.project.angelcanturamirez.appcontrolacceso;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView splash;
    ProgressBar progressBar;

    private final int DURACION_PANTALLA=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        splash = (ImageView)findViewById(R.id.imgSplash);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        progressBar.getIndeterminateDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fade_in_indicaciones);
        splash.startAnimation(animation);
        progressBar.startAnimation(animation);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(getApplicationContext(), IndicaccionesActivity.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_PANTALLA);

    }
}