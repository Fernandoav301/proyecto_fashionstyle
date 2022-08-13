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

public class CategoriaAdapter extends ArrayAdapter<categorias> {
    public CategoriaAdapter(@NonNull Context context, int resource, @NonNull List<categorias> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View result = convertView;
        if (result == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            result = inflater.inflate(R.layout.items_menu_categorias,null);
        }
        TextView name = (TextView) result.findViewById(R.id.txtcategoria);
        ImageView image = (ImageView) result.findViewById(R.id.img);

        categorias cate_row = getItem(position);
        name.setText(cate_row.getName());
        Picasso.get().load("https://fashionstylefdo.000webhostapp.com/images/"+cate_row.getImagen()+".png").into(image);

        return result;

    }
}
