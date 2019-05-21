package com.project.angelcanturamirez.appcontrolacceso;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    // Create a VideoView variable, a MediaPlayer variable, and an int to hold the current
    // video position.
    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;
    Button btn_ingresar;
    EditText user, pass;
    String id="";
    Boolean login_correcto=false;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        user = (EditText) findViewById(R.id.txtEmail);
        pass = (EditText) findViewById(R.id.txtPassword);

        btn_ingresar = findViewById(R.id.btLogInButton);

        //WebService
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        btn_ingresar.setOnClickListener(this);
        // Hook up the VideoView to our UI.
        videoBG = (VideoView) findViewById(R.id.videoView);

        // Build your video Uri
        Uri uri = Uri.parse("android.resource://" // First start with this,
                + getPackageName() // then retrieve your package name,
                + "/" // add a slash,
                + R.raw.sector); // and then finally add your video resource. Make sure it is stored
        // in the raw folder.

        // Set the new Uri to our VideoView
        videoBG.setVideoURI(uri);
        // Start the VideoView
        videoBG.start();

        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
        // check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });
    }

    /*================================ Important Section! ================================
    We must override onPause(), onResume(), and onDestroy() to properly handle our
    VideoView.
     */

    @Override
    protected void onPause() {
        super.onPause();
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(),"Datos incorrectos "+error.toString(), Toast.LENGTH_LONG).show();
        login_correcto = false;
    }

    @Override
    public void onResponse(JSONObject response) {

        JSONArray json=response.optJSONArray("Residentes");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            id = jsonObject.optString("idResidente");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        login_correcto = true;
        if (login_correcto) {
            Intent start = new Intent(getApplicationContext(), InicioActivity.class);
            start.putExtra("id", id);
            startActivity(start);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btLogInButton:
                if(!(user.getText().toString().equals("") && pass.getText().toString().equals(""))){
                    String url_edit= "http://"+getString(R.string.url)+"/appacceso/ConsultarResidenteCodigo.php?id="+user.getText().toString();
                    jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
                    requestQueue.add(jsonObjectRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "Datos vacios", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);//Se crea un alerta builder
        builder.setMessage("¿Seguro quieres salir?");//Mensaje de la alerta
        builder.setTitle("Confirmación");//Titulo de la alerta
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });//EN CASO DE QUE SI
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });//EN CASO DE QUE NO

        AlertDialog dialog = builder.create();//Se crea esa alerta
        dialog.show();//Se muestra esa alerta
    }
}

