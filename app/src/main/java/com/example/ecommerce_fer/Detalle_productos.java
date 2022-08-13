package com.example.ecommerce_fer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Detalle_productos extends AppCompatActivity {


    private ArrayList<productos> list_detalles;
    private ProductoAdapter adapter;
    TextView nombre, detalle, precio, total;
    ImageView foto;
    Button btmas, btmenos, agregar, carrito;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_detalle_productos);

        nombre = (TextView) findViewById(R.id.tvNameDetalle);
        detalle = (TextView) findViewById(R.id.tvDescripcionDetalle);
        precio = (TextView) findViewById(R.id.tvPrecioDetalle);
        total = (TextView) findViewById(R.id.total);
        foto = (ImageView) findViewById(R.id.imgDetalle);
        btmas = (Button) findViewById(R.id.btnMas);
        btmenos = (Button) findViewById(R.id.btnMenos);
        agregar = (Button) findViewById(R.id.btnAgregar);
        carrito = (Button) findViewById(R.id.btnCarrito);


        preferences=getSharedPreferences("pedidos", Context.MODE_PRIVATE);
        validadCantidad("idproducto");

        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CarritoCompras.class);
                startActivity(i);

            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Producto Agregado Correctamente" , Toast.LENGTH_LONG).show();


            }
        });


        btmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cantidad = preferences.getString("1","0");
                int cant = Integer.parseInt(cantidad);

                    cant = cant + 1;

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("1",cant+"");
                editor.commit();
                total.setText("Cantidad: "+cant+"");
            }
        });

        btmenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cantidad = preferences.getString("1","0");
                int cant = Integer.parseInt(cantidad);
                if (cant > 0)
                cant = cant - 1;
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("1",cant+"");
                editor.commit();
                total.setText("Cantidad: "+cant+"");

            }
        });

        list_detalles = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.get("idproducto") != null) {
                Toast.makeText(getApplicationContext(), "Se mostrara el producto: " + extras.get("idproducto").toString(), Toast.LENGTH_LONG).show();
                GetProductos(extras.get("idproducto").toString());
            }

        }
    }
    private void validadCantidad(String idproducto) {
        String cantidad = preferences.getString(idproducto,"0");
        total.setText("Cantidad: "+cantidad);

    }


    private void GetProductos(String idproducto) {
        RequestQueue queque = Volley.newRequestQueue(this);
        String url = "https://fashionstylefdo.000webhostapp.com/ws.php?detalles=" + idproducto;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.optString("Status").equals("success")) {
                    String data = response.optString("Datos");
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject = jsonArray.getJSONObject(i);
                            nombre.setText(jsonObject.optString("nombre"));
                            detalle.setText(jsonObject.optString("descripcion"));
                            precio.setText("Precio: $"+jsonObject.optString("precio")+".00");
                            Picasso.get().load("https://fashionstylefdo.000webhostapp.com/images/" + jsonObject.optString("foto") + ".png").into(foto);

                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Error al cargar los productos", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error no se pudo establecer la comunicacion", Toast.LENGTH_LONG).show();
            }
        });

        queque.add(jsonObjectRequest);
    }

    }