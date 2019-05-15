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
import com.project.angelcanturamirez.appcontrolacceso.entidades.InvitadoModelo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class InvitadosActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject>{

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    ArrayList<InvitadoModelo> listaInvitados;
    RecyclerView recyclerView;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitados);

        listaInvitados = new ArrayList<>();

        recyclerView=(RecyclerView) findViewById(R.id.recyclerEdificio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        RecyclerViewAdaptador adaptador = new RecyclerViewAdaptador(listaInvitados);
        recyclerView.setAdapter(adaptador);

        //Recibir extra
        id = getIntent().getStringExtra("id");

        String url_edit= "http://"+getString(R.string.url)+"/appacceso/ConsultarInvitados.php?id="+id;
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
        InvitadoModelo invitado= null;
        JSONArray json=response.optJSONArray("Invitados");



        try {
            for(int x=0;x<json.length();x++){
                invitado=new InvitadoModelo();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(x);
                invitado.setNombre(jsonObject.optString("Nombre"));
                invitado.setFecha(jsonObject.optString("Fecha"));
                invitado.setCaducidad(jsonObject.optString("Caducidad"));
                listaInvitados.add(invitado);
            }

            RecyclerViewAdaptador adaptador=new RecyclerViewAdaptador(listaInvitados);
            recyclerView.setAdapter(adaptador);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     Toast.makeText(getApplicationContext(), "Seleccion: "+listaInvitados.get(recyclerView.getChildAdapterPosition(view)).getNombre(), Toast.LENGTH_LONG).show();
                     //Intent abrir = new Intent(getApplicationContext(), EdificioDetallesActivity.class);
                    //abrir.putExtra("titulo", listaEdificios.get(recyclerView.getChildAdapterPosition(view)).getNombre());
                    //abrir.putExtra("descripcion", listaEdificios.get(recyclerView.getChildAdapterPosition(view)).getDescripcion_larga());
                    //startActivity(abrir);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
