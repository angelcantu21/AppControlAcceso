package com.project.angelcanturamirez.appcontrolacceso;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.angelcanturamirez.appcontrolacceso.entidades.InvitadoModelo;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ResidenteModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EstatusActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject>{

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    Switch disponible, ausente, no_disponible;
    TextView texto;
    Button btnGuardar;
    String accion = "", url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatus);

        //Switch
        disponible = (Switch) findViewById(R.id.switchDisponible);
        ausente = (Switch) findViewById(R.id.switchAusente);
        no_disponible = (Switch) findViewById(R.id.switchNoDisponible);

        //TextView
        texto = (TextView) findViewById(R.id.txtStado);

        //Button
        btnGuardar = (Button) findViewById(R.id.btnGuardarEstado);
        btnGuardar.setOnClickListener(this);

        //WebService
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://10.0.0.9/appacceso/ConsultarResidentes.php?id=1";
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);

        //Bloquear Switches
        disponible.setEnabled(false);
        ausente.setEnabled(false);
        no_disponible.setEnabled(false);
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
        Toast.makeText(getApplicationContext(), ""+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        if(accion == "guardar"){
            Toast.makeText(this, "Guardado", Toast.LENGTH_LONG).show();
        }else {

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
                    disponible.setChecked(true);
                    disponible.setEnabled(true);
                } else if (residente.getAusente().equals("1")) {
                    ausente.setChecked(true);
                    ausente.setEnabled(true);
                } else if (residente.getMolestar().equals("1")) {
                    no_disponible.setChecked(true);
                    no_disponible.setEnabled(true);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardarEstado:

                if (disponible.isChecked() || ausente.isChecked() || no_disponible.isChecked()){

                    if (disponible.isChecked()) {
                        url= "http://10.0.0.9/appacceso/GuardarEstado.php?" +
                                "disponible=1" +
                                "&ausente=0" +
                                "&molestar=0" +
                                "&id=1";
                    } else if (ausente.isChecked()) {
                        url= "http://10.0.0.9/appacceso/GuardarEstado.php?" +
                                "disponible=0" +
                                "&ausente=1" +
                                "&molestar=0" +
                                "&id=1";
                    } else if (no_disponible.isChecked()) {
                        url= "http://10.0.0.9/appacceso/GuardarEstado.php?" +
                                "disponible=0" +
                                "&ausente=0" +
                                "&molestar=1" +
                                "&id=1";
                    }

                    jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
                    requestQueue.add(jsonObjectRequest);
                    accion = "guardar";
                }else{
                    Toast.makeText(this, "Elige al menos un estado", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }

    public void verificarSwitch(View v) {
        if (disponible.isChecked()) {
            //Deshabilitar
            ausente.setChecked(false);
            no_disponible.setChecked(false);
            //Bloquear
            ausente.setEnabled(false);
            no_disponible.setEnabled(false);

        } else if (ausente.isChecked()) {
            //Deshabilitar
            disponible.setChecked(false);
            no_disponible.setChecked(false);
            //Bloquear
            disponible.setEnabled(false);
            no_disponible.setEnabled(false);

        } else if (no_disponible.isChecked()) {
            //Deshabilitar
            disponible.setChecked(false);
            ausente.setChecked(false);
            //Bloquear
            disponible.setEnabled(false);
            ausente.setEnabled(false);

        }else{
            disponible.setEnabled(true);
            ausente.setEnabled(true);
            no_disponible.setEnabled(true);
        }
    }
}
