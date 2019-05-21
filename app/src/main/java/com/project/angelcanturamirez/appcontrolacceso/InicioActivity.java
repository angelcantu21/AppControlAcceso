package com.project.angelcanturamirez.appcontrolacceso;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ResidenteModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InicioActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject>{

    Typeface fuente;

    int REQUEST_CODE = 1;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    TextView txt_status, txt_registro, txt_invitados, txt_mensajes, txt_incidencias, txt_configuracion, txt_panico, txt_usuarioNombre;
    CardView cardRegistrar, cardStatus, cardConfiguracion, cardMensajes, cardInvitados, cardBoton, cardIncidencias;
    Intent i ;
    String PATH_FUENTE = "fuentes/Questrial.ttf";
    String id ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Textview
        txt_status = (TextView) findViewById(R.id.txtStatus);
        txt_registro = (TextView) findViewById(R.id.TituloRegistrar);
        txt_invitados = (TextView) findViewById(R.id.TituloInvitados);
        txt_mensajes = (TextView) findViewById(R.id.TituloMensajes);
        txt_incidencias = (TextView) findViewById(R.id.TituloIncidencias);
        txt_configuracion = (TextView) findViewById(R.id.TituloConfiguracion);
        txt_panico = (TextView) findViewById(R.id.TituloPanico);
        txt_usuarioNombre = (TextView) findViewById(R.id.txtNombreUsuario);

        //Cargar fuentes
        this.fuente = Typeface.createFromAsset(getAssets(), PATH_FUENTE);
        txt_status.setTypeface(fuente);
        txt_registro.setTypeface(fuente);
        txt_invitados.setTypeface(fuente);
        txt_mensajes.setTypeface(fuente);
        txt_incidencias.setTypeface(fuente);
        txt_configuracion.setTypeface(fuente);
        txt_panico.setTypeface(fuente);

        //Card
        cardRegistrar = (CardView) findViewById(R.id.bankcardId);
        cardStatus = (CardView) findViewById(R.id.card_status);
        cardConfiguracion = (CardView) findViewById(R.id.card_configuracion);
        cardMensajes = (CardView) findViewById(R.id.card_mensajes);
        cardInvitados = (CardView) findViewById(R.id.card_invitados);
        cardBoton = (CardView) findViewById(R.id.card_llamada);
        cardIncidencias = (CardView) findViewById(R.id.card_incidencias);

        //Listeners
        cardRegistrar.setOnClickListener(this);
        cardStatus.setOnClickListener(this);
        cardConfiguracion.setOnClickListener(this);
        cardMensajes.setOnClickListener(this);
        cardInvitados.setOnClickListener(this);
        cardBoton.setOnClickListener(this);
        cardIncidencias.setOnClickListener(this);

        //WebService datos
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void onPostResume() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://"+getString(R.string.url)+"/appacceso/ConsultarResidentes.php?id="+id;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
        super.onPostResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // TODO Auto-generated method stub
        if ((requestCode == REQUEST_CODE && (resultCode == RESULT_OK))){
            id = data.getDataString();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bankcardId:
                i = new Intent(this, RegistrarActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.card_status:
                i = new Intent(this, EstatusActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.card_configuracion:
                i = new Intent(this, DetallesActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.card_mensajes:
                i = new Intent(this, MensajesActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.card_invitados:
                i = new Intent(this, InvitadosActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
            case R.id.card_llamada:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+528117244270"));
                startActivity(intent);
                break;
            case R.id.card_incidencias:
                i = new Intent(this, IncidenciasActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), ""+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        ResidenteModelo residente = null;
        JSONArray json = response.optJSONArray("Residentes");

        try {
            for (int x = 0; x < json.length(); x++) {
                residente = new ResidenteModelo();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(x);
                residente.setNombre(jsonObject.optString("Nombre"));
                residente.setApellidos(jsonObject.optString("Apellidos"));
                residente.setDisponible(jsonObject.optString("Disponible"));
                residente.setAusente(jsonObject.optString("Ausente"));
                residente.setMolestar(jsonObject.optString("No_molestar"));
            }

            if (residente.getDisponible().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#26E300"));
                txt_status.setText("Estado: Disponible");
            } else if (residente.getAusente().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#EAD500"));
                txt_status.setText("Estado: Ausente");
            } else if (residente.getMolestar().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#FF0000"));
                txt_status.setText("Estado: No molestar");
            }

            txt_usuarioNombre.setText(residente.getNombre()+" "+residente.getApellidos());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
/*
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
        dialog.show();//Se muestra esa alerta*/
    }
}
