package com.project.angelcanturamirez.appcontrolacceso;

import android.content.SharedPreferences;
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
import com.project.angelcanturamirez.appcontrolacceso.RecyclerViews.RecyclerViewMensajes;
import com.project.angelcanturamirez.appcontrolacceso.RecyclerViews.RecyclerViewServicios;
import com.project.angelcanturamirez.appcontrolacceso.entidades.MensajeModelo;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ServicioModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiciosActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<ServicioModelo> listaServicios;
    RecyclerView recyclerView;

    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        listaServicios = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerServicios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        RecyclerViewServicios adaptador = new RecyclerViewServicios(listaServicios);
        recyclerView.setAdapter(adaptador);

        //Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);
        id = sharedPreferences.getString("idResidente", "No hay datos");

        String url_edit= "http://"+getString(R.string.url)+"/appacceso/servicios.php";
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, ""+error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        ServicioModelo servicio= null;
        JSONArray json=response.optJSONArray("Servicios");

        try {
            for(int x=0;x<json.length();x++){
                servicio=new ServicioModelo();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(x);
                servicio.setServicio(jsonObject.optString("Servicio"));
                servicio.setTelefono(jsonObject.optString("Telefono"));
                listaServicios.add(servicio);
            }

            RecyclerViewServicios adaptador=new RecyclerViewServicios(listaServicios);
            recyclerView.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Seleccion: "+listaServicios.get(recyclerView.getChildAdapterPosition(v)).getTelefono(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //METODO PARA PONER BOTON DE REGRESAR EN LA PARTE DE LA BARRA
    private void setupActionBar(){
        ActionBar regresar = getSupportActionBar();//SE INSTANCIA LA ACCION DE LA BARRA
        if (regresar != null){//MIENTRAS SEA DIFERENTE DE NULO
            regresar.setDisplayHomeAsUpEnabled(true);//SE MANTIENE HABILITADO
        }
    }
}
