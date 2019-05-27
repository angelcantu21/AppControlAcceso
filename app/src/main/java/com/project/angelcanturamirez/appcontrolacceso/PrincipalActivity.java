package com.project.angelcanturamirez.appcontrolacceso;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.angelcanturamirez.appcontrolacceso.Configuraciones.AcercaActivity;
import com.project.angelcanturamirez.appcontrolacceso.entidades.ResidenteModelo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, Response.ErrorListener, Response.Listener<JSONObject> {

    Typeface fuente;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    TextView txt_status, txt_registro, txt_invitados, txt_mensajes, txt_incidencias, txt_configuracion, txt_panico, txt_usuarioNombre, txt_apartamento;
    CardView cardRegistrar, cardStatus, cardConfiguracion, cardMensajes, cardInvitados, cardBoton, cardIncidencias, cardCerrarSesion;
    Intent i ;
    String PATH_FUENTE = "fuentes/Questrial.ttf";
    String id_residente="";
    SharedPreferences sharedPreferences;
    ProgressDialog loading;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View header = navigationView.getHeaderView(0);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Textview
        txt_status = (TextView) findViewById(R.id.txtStatus);
        txt_registro = (TextView) findViewById(R.id.TituloRegistrar);
        txt_invitados = (TextView) findViewById(R.id.TituloInvitados);
        txt_mensajes = (TextView) findViewById(R.id.TituloMensajes);
        txt_incidencias = (TextView) findViewById(R.id.TituloIncidencias);
        txt_configuracion = (TextView) findViewById(R.id.TituloConfiguracion);
        txt_panico = (TextView) findViewById(R.id.TituloPanico);
        txt_usuarioNombre = (TextView) header.findViewById(R.id.txtNombreUsuario);
        txt_apartamento = (TextView) header.findViewById(R.id.txtApartamento);

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
        cardCerrarSesion = (CardView) findViewById(R.id.card_cerrar_sesion);

        //Listeners
        cardRegistrar.setOnClickListener(this);
        cardStatus.setOnClickListener(this);
        cardConfiguracion.setOnClickListener(this);
        cardMensajes.setOnClickListener(this);
        cardInvitados.setOnClickListener(this);
        cardBoton.setOnClickListener(this);
        cardIncidencias.setOnClickListener(this);
        cardCerrarSesion.setOnClickListener(this);

        //WebService datos
        sharedPreferences = getSharedPreferences("LoginSp", MODE_PRIVATE);
        id_residente = sharedPreferences.getString("idResidente", "No hay datos");

        //Mensaje de espera
        loading = ProgressDialog.show(this,"Cargando informacion","Espere por favor...",false,false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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

    @Override
    protected void onPostResume() {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url_edit= "http://"+getString(R.string.url)+"/appacceso/ConsultarResidentes.php?id="+id_residente;
        jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url_edit,null,this,this);
        requestQueue.add(jsonObjectRequest);
        super.onPostResume();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inicio) {
            startActivity(getIntent());
            finish();
        } else if (id == R.id.nav_camera) {
            i= new Intent(this, CamaraActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_servicios) {
            i = new Intent(this, ServiciosActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            sharedPreferences.edit().putBoolean("Logeado", false).apply();
            i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_creditos) {
            i = new Intent(this, CreditosActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_acerca) {
            i = new Intent(this, AcercaActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            case R.id.card_cerrar_sesion:
                sharedPreferences.edit().putBoolean("Logeado", false).apply();
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
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
            txt_apartamento.setText("Departamento - "+residente.getDepartamento());
            loading.dismiss();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}