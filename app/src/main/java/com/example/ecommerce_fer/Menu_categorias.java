package com.example.ecommerce_fer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Menu_categorias extends AppCompatActivity {


    private ListView listacategorias;
    private ArrayList<categorias> list_categorias;
    private CategoriaAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_categorias);
        listacategorias = (ListView) findViewById(R.id.ListMenu);
        list_categorias = new ArrayList<>();

        GetCategos();

    }
    private void GetCategos() {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url= "https://fashionstylefdo.000webhostapp.com/ws.php?categorias";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(response.optString("Status").equals("success")){
                    String data= response.optString("Datos");
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i=0;i<jsonArray.length(); i++){
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = jsonArray.getJSONObject(i);
                            list_categorias.add(new categorias(jsonObject.optString("id"),jsonObject.optString("name"),jsonObject.optString("imagen")));
                        }
                       LlenarLista();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error"+ e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else
                    Toast.makeText(getApplicationContext(),"Error al cargar categorias",Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error no se pudo establecer la comunicacion", Toast.LENGTH_LONG).show();
            }
        });

        queque.add(jsonObjectRequest);
    }

    private void LlenarLista() {
        adapter = new CategoriaAdapter(this,R.layout.items_menu_categorias,list_categorias);
        listacategorias.setAdapter(adapter);
        listacategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(Menu_categorias.this,Menu_productos.class);
                intent.putExtra("idCategoria",list_categorias.get(i).getId());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Seleccionaste: "+list_categorias.get(i).getName(),Toast.LENGTH_LONG).show();
            }
        });
    }
}