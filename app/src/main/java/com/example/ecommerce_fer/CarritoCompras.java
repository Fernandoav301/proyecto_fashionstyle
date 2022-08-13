package com.example.ecommerce_fer;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CarritoCompras extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        listView=(ListView) findViewById(R.id.ListCarrito);
        String[] nombre= {"CHALECO", "SKY JEANS", "PLAYERA DE OSO", "TOP TIRANTES", "COLLAR URBANO"};
        String[] precio= {"844.00", "1,028.00", "370.00", "828.00", "717.00"};
        String[] cantidad= {"2", "3", "2", "4", "3"};
        Integer[] idimage= {R.mipmap.caballero5,R.mipmap.dama6,R.mipmap.nino4,R.mipmap.nino6,R.mipmap.accesorio4};
        CarritoAdapter adapter = new CarritoAdapter( this,nombre,precio,cantidad, idimage);
        listView.setAdapter(adapter);


    }
}
