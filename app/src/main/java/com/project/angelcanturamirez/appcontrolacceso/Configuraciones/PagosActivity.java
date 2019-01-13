package com.project.angelcanturamirez.appcontrolacceso.Configuraciones;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.project.angelcanturamirez.appcontrolacceso.R;

import java.math.BigDecimal;

public class PagosActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    EditText txtMonto;
    Button btnPagar;

    String monto = "";

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        //Iniciar servicio de paypal
        //Intent intent = new Intent(this, PayPalService.class);
        //intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        //startActivity(intent);

        btnPagar = (Button) findViewById(R.id.btnPagarRenta);
        txtMonto = (EditText) findViewById(R.id.txtMonto);

        btnPagar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPagarRenta:
                procesarPago();
                break;
        }
    }

    private void procesarPago(){
        //Recibir el monto del control editext
        monto = txtMonto.getText().toString();

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(monto)), "MXN", "Pagado por Angel Cantu", PayPalPayment
                .PAYMENT_INTENT_SALE);

        //Enviar parametros
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PAYPAL_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null){
                    Toast.makeText(this, "PAGO EXITOSO", Toast.LENGTH_LONG).show();
                }
            }else if(resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this, "Transaccion cancelada", Toast.LENGTH_LONG).show();
            }else if(resultCode == PaymentActivity.RESULT_EXTRAS_INVALID){
                Toast.makeText(this, "Invalida", Toast.LENGTH_LONG).show();
            }
        }
    }
}
