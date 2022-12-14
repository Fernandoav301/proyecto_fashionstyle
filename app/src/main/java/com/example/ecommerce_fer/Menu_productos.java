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

public class Menu_productos extends AppCompatActivity {

    private ListView listaproductos;
    private ArrayList<productos> list_productos;
    private ProductoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_menu_productos);



        listaproductos = (ListView) findViewById(R.id.ListProductos);
        list_productos = new ArrayList<>();
        Bundle extras=getIntent().getExtras();
        if (extras != null){
            if (extras.get("idCategoria")!=null){
                Toast.makeText(getApplicationContext(), "Se mostraran los productos de la categoria: "+extras.get("idCategoria").toString(),Toast.LENGTH_LONG).show();
                GetProductos(extras.get("idCategoria").toString());            
            }
            
        }
    }

    private void GetProductos(String idCategoria) {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url= "https://fashionstylefdo.000webhostapp.com/ws.php?productos="+idCategoria;
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
                            list_productos.add(new productos(jsonObject.optString("idproducto"),jsonObject.optString("nombre"),jsonObject.optString("descripcion"),jsonObject.optString("foto"),"Precio: $"+jsonObject.optString("precio")+".00"));
                        }
                        LlenarProductos();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),"Error"+ e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }else
                    Toast.makeText(getApplicationContext(),"Error al cargar los productos",Toast.LENGTH_LONG).show();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error no se pudo establecer la comunicacion", Toast.LENGTH_LONG).show();
            }
        });

        queque.add(jsonObjectRequest);
    }

    private void LlenarProductos() {
        adapter = new ProductoAdapter(this,R.layout.items_productos,list_productos);
        listaproductos.setAdapter(adapter);
        listaproductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(Menu_productos.this,Detalle_productos.class);
                intent.putExtra("idproducto",list_productos.get(i).getIdProducto());
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Seleccionaste: "+list_productos.get(i).getNombre(),Toast.LENGTH_LONG).show();
            }
        });
    }

}
