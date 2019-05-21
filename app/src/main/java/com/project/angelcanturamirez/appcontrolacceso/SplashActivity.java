package com.project.angelcanturamirez.appcontrolacceso;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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

    TextView textSplash;
    ImageView splash;
    ProgressBar progressBar;
    private Typeface fuente;

    private final int DURACION_PANTALLA=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        splash = (ImageView)findViewById(R.id.imgSplash);
        textSplash = (TextView) findViewById(R.id.txtSplash);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        this.fuente = Typeface.createFromAsset(getAssets(), "fuentes/Staatliches.ttf");
        textSplash.setTypeface(fuente);

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.fadein);
        splash.startAnimation(animation);
        textSplash.startAnimation(animation);
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