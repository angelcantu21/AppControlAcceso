package com.project.angelcanturamirez.appcontrolacceso;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.json.JSONObject;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private CalendarView caducidad;
    private CheckBox check;
    private EditText carro, placas, nombre, empresa;
    private TextInputLayout carroInput, placasInput, empresaInput;
    private Button gen_btn, reg_btn;
    private ImageView image;
    private String fecha_caducidad, checkActivo, url, id;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //check
        check = (CheckBox) findViewById(R.id.checkCarro);

        //edittext
        nombre = (EditText) findViewById(R.id.txtNombre);
        carro = (EditText) findViewById(R.id.txtCarro);
        placas = (EditText) findViewById(R.id.txtPlacas);
        empresa = (EditText)findViewById(R.id.txtEmpresa);

        //inputs
        carroInput = (TextInputLayout) findViewById(R.id.txtCarroInput);
        placasInput = (TextInputLayout) findViewById(R.id.txtPlacasInput);
        empresaInput = (TextInputLayout) findViewById(R.id.txtEmpresaInput);

        //botones
        gen_btn = (Button) findViewById(R.id.btnQR);
        reg_btn = (Button) findViewById(R.id.btnRegistrar);

        //imagenview
        image = (ImageView) findViewById(R.id.image);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Recibir extra
        id = getIntent().getStringExtra("id");

        //CalendarView
        caducidad = (CalendarView) findViewById(R.id.calendarioCaducidad);

        //Listeners
        gen_btn.setOnClickListener(this);
        reg_btn.setOnClickListener(this);

        image.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View arg0) {

                //convertir imagen a bitmap
                image.buildDrawingCache();
                Bitmap bmap = image.getDrawingCache();

                //guardar imagen
                Save savefile = new Save();
                savefile.SaveImage(getApplicationContext(), bmap);
                return true;
            }
        });

        caducidad.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha_caducidad = year + "-" + month + "-" + dayOfMonth;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnQR:
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode("Prueba QR", BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
                break;
            case R.id.btnRegistrar:
                //convertir imagen a bitmap
                image.buildDrawingCache();
                Bitmap bmap = image.getDrawingCache();

                //guardar imagen
                Save savefile = new Save();
                savefile.SaveImage(getApplicationContext(), bmap);

                if (check.isChecked()){
                    checkActivo = "true";
                        url = "http://"+getString(R.string.url)+"/appacceso/RegistrarInvitados.php?" +
                            "Nombre="+ nombre.getText().toString() +
                            "&Caducidad="+ fecha_caducidad +
                            "&FotoID=id.png" +
                            "&Selfie=selfie.png" +
                            "&Vehiculo=" + carro.getText().toString() +
                            "&Placas=" + placas.getText().toString() +
                            "&Empresa=" + empresa.getText().toString() +
                            "&GeneradorQR=9373isjsue" +
                            "&FkResidente="+id+
                            "&check="+checkActivo;
                }else{
                    checkActivo = "false";
                    url = "http://"+getString(R.string.url)+"/appacceso/RegistrarInvitados.php?" +
                            "Nombre="+ nombre.getText().toString() +
                            "&Caducidad="+ fecha_caducidad +
                            "&FotoID=id.png" +
                            "&Selfie=selfie.png" +
                            "&Vehiculo=no" +
                            "&Placas=no" +
                            "&Empresa=no" +
                            "&GeneradorQR=9373isjsue" +
                            "&FkResidente="+id+
                            "&check="+checkActivo;
                }

                url = url.replace(" ","%20");
                jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
                requestQueue.add(jsonObjectRequest);
                //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast toast= Toast.makeText(this,"Error"+error.toString(),Toast.LENGTH_LONG);
        toast.show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(getApplicationContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();
    }

    public void loguearCheckbox(View v) {
        if (check.isChecked()){
            carroInput.setVisibility(View.VISIBLE);
            placasInput.setVisibility(View.VISIBLE);
            empresaInput.setVisibility(View.VISIBLE);
            carro.setVisibility(View.VISIBLE);
            placas.setVisibility(View.VISIBLE);
            empresa.setVisibility(View.VISIBLE);
            //Toast.makeText(this, "Marcado", Toast.LENGTH_LONG).show();
        }else{
            carroInput.setVisibility(View.GONE);
            placasInput.setVisibility(View.GONE);
            empresaInput.setVisibility(View.GONE);
            carro.setVisibility(View.GONE);
            placas.setVisibility(View.GONE);
            empresa.setVisibility(View.GONE);
            //Toast.makeText(this, "No marcado", Toast.LENGTH_LONG).show();
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
