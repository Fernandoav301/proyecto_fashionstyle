package com.example.ecommerce_fer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductoAdapter extends ArrayAdapter<productos> {
    public ProductoAdapter(@NonNull Context context, int resource, @NonNull List<productos> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.items_productos,null);
        }
        TextView nombre = (TextView) result.findViewById(R.id.nombreProducto);
        ImageView foto = (ImageView) result.findViewById(R.id.imagenProducto);
        TextView descripcion = (TextView) result.findViewById(R.id.descripcion);
        TextView precio = (TextView) result.findViewById(R.id.precioProducto);



        productos cate_row = getItem(position);
        nombre.setText(cate_row.getNombre());
        descripcion.setText(cate_row.getDescripcion());
        precio.setText(cate_row.getPrecio());
        Picasso.get().load("https://fashionstylefdo.000webhostapp.com/images/"+cate_row.getFoto()+".png").into(foto);

        return result;

    }
}

