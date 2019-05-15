package com.project.angelcanturamirez.appcontrolacceso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private CalendarView caducidad;
    private CheckBox check;
    private EditText carro, placas, nombre, empresa;
    private TextInputLayout carroInput, placasInput, empresaInput;
    private Button gen_btn, reg_btn, foto_btn;
    private ImageView image, foto;
    Drawable imagen_antigua;
    private String fecha_caducidad, checkActivo, url, id;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="http://10.0.0.9/appacceso/upload.php";

    private String KEY_IMAGEN = "foto";
    private String KEY_NOMBRE = "nombre";

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
        foto_btn = (Button) findViewById(R.id.btnCargarFoto);

        //imagenview
        image = (ImageView) findViewById(R.id.image);
        foto = (ImageView) findViewById(R.id.fotoSelfie);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //Recibir extra
        id = getIntent().getStringExtra("id");

        imagen_antigua = foto.getDrawable();


        //CalendarView
        caducidad = (CalendarView) findViewById(R.id.calendarioCaducidad);

        //Listeners
        gen_btn.setOnClickListener(this);
        reg_btn.setOnClickListener(this);
        foto_btn.setOnClickListener(this);

        reg_btn.setEnabled(false);
        gen_btn.setEnabled(false);
/*
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
*/
        foto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (foto.getDrawable() == imagen_antigua)
                {

                    Toast.makeText(getApplicationContext(), "No has elegido una nueva imagen!",
                            Toast.LENGTH_LONG).show();
                }else{
                    uploadImage();
                }
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
                    BitMatrix bitMatrix = multiFormatWriter.encode(nombre.getText().toString()+id, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
                reg_btn.setEnabled(true);
                break;
            case R.id.btnCargarFoto:
                showFileChooser();
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
                            "&FotoID=" + nombre.getText().toString()+
                            "&Selfie=" + nombre.getText().toString()+ ".png" +
                            "&Vehiculo=" + carro.getText().toString() +
                            "&Placas=" + placas.getText().toString() +
                            "&Empresa=" + empresa.getText().toString() +
                            "&GeneradorQR=" +nombre.getText().toString()+id+
                            "&FkResidente="+id+
                            "&check="+checkActivo;
                }else{
                    checkActivo = "false";
                    url = "http://"+getString(R.string.url)+"/appacceso/RegistrarInvitados.php?" +
                            "Nombre="+ nombre.getText().toString() +
                            "&Caducidad="+ fecha_caducidad +
                            "&FotoID=" + nombre.getText().toString()+
                            "&Selfie=" +nombre.getText().toString()+".png"+
                            "&Vehiculo=no" +
                            "&Placas=no" +
                            "&Empresa=no" +
                            "&GeneradorQR=" +nombre.getText().toString()+id+
                            "&FkResidente="+id+
                            "&check="+checkActivo;
                }

                url = url.replace(" ","%20");
                jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
                requestQueue.add(jsonObjectRequest);
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
        //Toast.makeText(getApplicationContext(), ""+response.toString(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Registrado", Toast.LENGTH_LONG).show();
        finish();
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
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Cómo obtener el mapa de bits de la Galería
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Configuración del mapa de bits en ImageView
                foto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage(){
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        gen_btn.setEnabled(true);
                        reg_btn.setEnabled(true);
                        foto.setEnabled(false);
                        Toast.makeText(RegistrarActivity.this, "Se subio la imagen" , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(RegistrarActivity.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                String name = nombre.getText().toString().trim();

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_NOMBRE, name);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }
}
