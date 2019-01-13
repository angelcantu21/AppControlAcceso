package com.project.angelcanturamirez.appcontrolacceso;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.angelcanturamirez.appcontrolacceso.Configuraciones.AcercaActivity;
import com.project.angelcanturamirez.appcontrolacceso.Configuraciones.PagosActivity;

public class DetallesActivity extends AppCompatActivity implements View.OnClickListener {

    Button acerca, pagos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        acerca = (Button) findViewById(R.id.btnAcerca);
        pagos = (Button) findViewById(R.id.btnPagos);

        acerca.setOnClickListener(this);
        pagos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAcerca:
                Intent intentA = new Intent(this, AcercaActivity.class);
                startActivity(intentA);
                break;
            case R.id.btnPagos:
                Intent intentP = new Intent(this, PagosActivity.class);
                startActivity(intentP);
                break;
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
