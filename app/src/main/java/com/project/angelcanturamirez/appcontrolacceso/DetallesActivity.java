package com.project.angelcanturamirez.appcontrolacceso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.angelcanturamirez.appcontrolacceso.Configuraciones.AcercaActivity;
import com.project.angelcanturamirez.appcontrolacceso.Configuraciones.PagosActivity;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ResidenteModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetallesActivity extends AppCompatActivity implements View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

    String id;
    ProgressDialog loading;
    CircleImageView fotoUsuario;
    EditText nombre, apellido, departamentos, codigo;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        loading = ProgressDialog.show(this,"Cargando informacion","Espere por favor...",false,false);

        //Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);
        id = sharedPreferences.getString("idResidente", "No hay datos");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        nombre = findViewById(R.id.txtNombreUsuarioDetalles);
        apellido = findViewById(R.id.txtApellidoUsuarioDetalles);
        departamentos = findViewById(R.id.txtDepartamentoUsuarioDetalles);
        codigo = findViewById(R.id.txtCodigoUsuarioDetalles);

        fotoUsuario = findViewById(R.id.imgUsuarioDetalles);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://"+getString(R.string.url)+"/appacceso/ConsultarResidentes.php?id="+id;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu_detalles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_acerca) {
            Intent intentA = new Intent(this, AcercaActivity.class);
            startActivity(intentA);
            //Toast.makeText(getApplicationContext(), "Acerca de", Toast.LENGTH_LONG).show();
            //return true;
        }else if(id == R.id.action_mensualidades){
            Intent intentP = new Intent(this, PagosActivity.class);
            startActivity(intentP);
            //Toast.makeText(getApplicationContext(), "Mensualidades", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
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
        Log.i("Error",error.toString());
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
                residente.setDepartamento(jsonObject.optString("Departamento"));
                residente.setCodigo(jsonObject.optString("Codigo_Acceso"));
            }

            nombre.setText("Nombre: "+residente.getNombre());
            apellido.setText("Apellidos: "+residente.getApellidos());
            departamentos.setText("Departamento "+residente.getDepartamento());
            codigo.setText("Codigo de Acceso: "+residente.getCodigo());
            cargarWebService();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void cargarWebService(){
        String url = "http://"+getString(R.string.url)+"/appacceso/uploads/"+id+".jpg";
        url = url.replace(" ","%20");

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                fotoUsuario.setImageBitmap(response);
                loading.dismiss();
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al cargar la imagen"+error, Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(imageRequest);
    }
}
