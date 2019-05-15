package com.project.angelcanturamirez.appcontrolacceso;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.angelcanturamirez.appcontrolacceso.RecyclerViews.RecyclerViewAdaptador;
import com.project.angelcanturamirez.appcontrolacceso.RecyclerViews.RecyclerViewMensajes;
import com.project.angelcanturamirez.appcontrolacceso.entidades.InvitadoModelo;
import com.project.angelcanturamirez.appcontrolacceso.entidades.MensajeModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MensajesActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<MensajeModelo> listaMensajes;
    RecyclerView recyclerView;

    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);

        listaMensajes = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerMensajes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        RecyclerViewMensajes adaptador = new RecyclerViewMensajes(listaMensajes);
        recyclerView.setAdapter(adaptador);

        //Recibir extra
        id = getIntent().getStringExtra("id");

        String url_edit= getString(R.string.url)+"/appacceso/mensajes.php?id=1";
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    //METODO PARA PONER BOTON DE REGRESAR EN LA PARTE DE LA BARRA
    private void setupActionBar(){
        ActionBar regresar = getSupportActionBar();//SE INSTANCIA LA ACCION DE LA BARRA
        if (regresar != null){//MIENTRAS SEA DIFERENTE DE NULO
            regresar.setDisplayHomeAsUpEnabled(true);//SE MANTIENE HABILITADO
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, ""+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        MensajeModelo mensaje= null;
        JSONArray json=response.optJSONArray("Mensajes");



        try {
            for(int x=0;x<json.length();x++){
                mensaje=new MensajeModelo();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(x);
                mensaje.setAsunto(jsonObject.optString("Asunto"));
                mensaje.setMensaje(jsonObject.optString("Texto"));
                listaMensajes.add(mensaje);
            }

            RecyclerViewMensajes adaptador=new RecyclerViewMensajes(listaMensajes);
            recyclerView.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Seleccion: "+listaMensajes.get(recyclerView.getChildAdapterPosition(view)).getAsunto(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
