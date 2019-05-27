package com.project.angelcanturamirez.appcontrolacceso;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrLoginActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView escaner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EscanearQR();
    }

    public void EscanearQR()
    {
        escaner=new ZXingScannerView(this);
        setContentView(escaner);
        escaner.setResultHandler(this);
        escaner.startCamera();
    }

    @Override
    protected void onStop() {
        super.onStop();
        escaner.stopCamera();
        onBackPressed();
    }

    //METODO PARA PONER BOTON DE REGRESAR EN LA PARTE DE LA BARRA
    private void setupActionBar(){
        ActionBar regresar = getSupportActionBar();//SE INSTANCIA LA ACCION DE LA BARRA
        if (regresar != null){//MIENTRAS SEA DIFERENTE DE NULO
            regresar.setDisplayHomeAsUpEnabled(true);//SE MANTIENE HABILITADO
        }
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Resultado de QR");
        builder.setMessage(result.getText());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        escaner.resumeCameraPreview(this); }
}
