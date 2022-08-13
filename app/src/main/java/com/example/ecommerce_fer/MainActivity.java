package com.example.ecommerce_fer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button loguear,registrar;
    EditText contraseña,usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loguear = (Button)findViewById(R.id.bt_login);
        registrar = (Button)findViewById(R.id.btn_registro);
        usuario = (EditText)findViewById(R.id.user);
        contraseña = (EditText)findViewById(R.id.contra);



        loguear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logueate();
                Intent i= new Intent(getApplicationContext(), Menu_categorias.class);
                startActivity(i);


            }


        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),registro.class);
                startActivity(i);


            }
        });
    }
    private void logueate() {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url= "https://fashionstylefdo.000webhostapp.com/ws.php?logeate=1&email"+usuario.getText().toString()+"&pass="+contraseña.getText().toString();
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