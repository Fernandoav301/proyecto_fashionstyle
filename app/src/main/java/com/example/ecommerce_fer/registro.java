package com.example.ecommerce_fer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class registro extends AppCompatActivity {

    EditText nombre, email, pass;
    Button regresarlogin, guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        regresarlogin = (Button)findViewById(R.id.btn_regresar);
        guardar = (Button)findViewById(R.id.bt_guardar);
        nombre = (EditText)findViewById(R.id.ed_usuario);
        email = (EditText)findViewById(R.id.ed_correo);
        pass = (EditText)findViewById(R.id.ed_nuevopass);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                InsertarUsuario();
                startActivity(i);


            }



        });

        regresarlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);


            }
        });
    }
    private void InsertarUsuario() {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url= "https://fashionstylefdo.000webhostapp.com/ws.php?nombre="+nombre.getText().toString()+"&email="+email.getText().toString()+"&pass="+pass.getText().toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.optString("Status").equals("success")){
                    Toast.makeText(getApplicationContext()," "+response.optString("Mensaje"),Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(),"Server:"+response.optString("Mensaje"),Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error no se pudo establecer la comunicacion"+error, Toast.LENGTH_LONG).show();
            }
        });

        queque.add(jsonObjectRequest);
    }
}