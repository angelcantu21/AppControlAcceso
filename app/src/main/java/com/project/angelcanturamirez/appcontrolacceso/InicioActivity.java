package com.project.angelcanturamirez.appcontrolacceso;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    TextView txt_status;
    CardView cardRegistrar, cardStatus, cardConfiguracion, cardMensajes, cardInvitados, cardBoton, cardIncidencias;
    Intent i ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        //Textview
        txt_status = (TextView) findViewById(R.id.txtStatus);

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

        //WebService
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://10.0.0.9/appacceso/ConsultarResidentes.php?id=1";
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bankcardId:
                i = new Intent(this, RegistrarActivity.class);
                startActivity(i);
                break;
            case R.id.card_status:
                i = new Intent(this, EstatusActivity.class);
                startActivity(i);
                break;
            case R.id.card_configuracion:
                i = new Intent(this, DetallesActivity.class);
                startActivity(i);
                break;
            case R.id.card_mensajes:
                i = new Intent(this, MensajesActivity.class);
                startActivity(i);
                break;
            case R.id.card_invitados:
                i = new Intent(this, InvitadosActivity.class);
                startActivity(i);
                break;
            case R.id.card_llamada:
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:+528117244270"));
                startActivity(intent);
                break;
            case R.id.card_incidencias:
                i = new Intent(this, IncidenciasActivity.class);
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
                residente.setDisponible(jsonObject.optString("Disponible"));
                residente.setAusente(jsonObject.optString("Ausente"));
                residente.setMolestar(jsonObject.optString("No_molestar"));
            }

            if (residente.getDisponible().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#26E300"));
                txt_status.setText("Disponible");
            } else if (residente.getAusente().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#EAD500"));
                txt_status.setText("Ausente");
            } else if (residente.getMolestar().equals("1")) {
                txt_status.setTextColor(Color.parseColor("#FF0000"));
                txt_status.setText("No molestar");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
