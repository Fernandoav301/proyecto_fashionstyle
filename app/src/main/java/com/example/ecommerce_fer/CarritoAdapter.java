package com.example.ecommerce_fer;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarritoAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] nombres;
    private final String[] precios;
    private final String[] cantidades;
    private final Integer[] idmage;

    public CarritoAdapter(Activity context, String[] nombres, String[] precios, String[] cantidades, Integer[] idimage) {
        super(context, R.layout.item_carrito, nombres);
        this.context = context;
        this.nombres = nombres;
        this.precios = precios;
        this.cantidades = cantidades;
        this.idmage = idimage;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.item_carrito, null);
        TextView _nombre = (TextView) rowView.findViewById(R.id.nombre);
        TextView _precios = (TextView) rowView.findViewById(R.id.precio);
        TextView _cantidades = (TextView) rowView.findViewById(R.id.cantidad);

        ImageView _imagen = (ImageView) rowView.findViewById(R.id.Logo);
        _nombre.setText(nombres[position]);
        _precios.setText(precios[position]);
        _cantidades.setText(cantidades[position]);
        _imagen.setImageResource(idmage[position]);
        return rowView;

    }

}
