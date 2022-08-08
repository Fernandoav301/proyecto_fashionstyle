package com.example.ecommerce_fer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Menu_productos extends AppCompatActivity {

    private ListView listView;
    SmartImageView imageView;

    String URL_PRODUCTOS = "https://fashionstylefdo.000webhostapp.com/productos.php";
    String URL_IMAGENES = "https://fashionstylefdo.000webhostapp.com/images/";

    String nombre, descripcion, foto;
    int precio;

    private List<productos> sampleData;
    private ArrayList<productos> sampleList;
    private ImagenAdapter sampleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_productos);

        listView =findViewById(R.id.ListProductos);
        MostrarLista();
    }

    private void MostrarLista() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando datos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL_PRODUCTOS, new AsyncHttpResponseHandler () {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        final JSONArray jsonArray = new JSONArray(new String(responseBody));

                        sampleData = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObjectInside = jsonArray.getJSONObject(i);
                            nombre = jsonObjectInside.getString("nombre");
                            precio = jsonObjectInside.getInt("precio");
                            descripcion = jsonObjectInside.getString("descripcion");
                            foto = jsonObjectInside.getString("foto");
                            sampleData.add(new productos(nombre, precio, descripcion, foto));
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(Menu_productos.this, Detalle_productos.class);
                                startActivity(intent);
                            }

                        });

                        handlePostsList(sampleData);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error del Servidor..", Toast.LENGTH_LONG).show();
                    }
                }
            }


            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "Error del Servidor...", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                finish();

            }

        });
    }

    private void handlePostsList(final List<productos> sampleData) {
        this.sampleData = sampleData;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    sampleList = new ArrayList<productos>();

                    for (int i = 0; i < sampleData.size(); i++) {
                        sampleList.add(new productos(sampleData.get(i).getNombre(),
                                sampleData.get(i).getPrecio(), sampleData.get(i).getDescripcion(), sampleData.get(i).getFoto()));
                    }
                    sampleAdapter = new ImagenAdapter( Menu_productos.this, sampleList);
                    listView.setAdapter(sampleAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public class ImagenAdapter extends ArrayAdapter<productos> implements Filterable {

    private ArrayList<productos> arrayList;

    Context context;
    List<productos> sampleData = null;
    LayoutInflater layoutInflater;

    public ImagenAdapter(Context ctx, List<productos> sampleData) {
        super(ctx, R.layout.items_productos, sampleData);

        this.context = ctx;
        this.sampleData = sampleData;
        this.arrayList = new ArrayList<productos>();
        this.arrayList.addAll(sampleData);
    }

    public class ViewHolder {
        TextView tvnombre, tvprecio, tvdescripcion;
        SmartImageView imagen;
    }

    @Override
        public int getCount(){ return sampleData.size();}

    @Override
    public productos getItem(int position) {return  sampleData.get(position);}

    @Override
    public long getItemId(int position) {return sampleData.indexOf(getItem(position));}


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.items_productos, parent, false);

            holder = new ViewHolder();
            holder.tvnombre = convertView.findViewById(R.id.nombre);
            holder.tvprecio = convertView.findViewById(R.id.precio);
            holder.tvdescripcion = convertView.findViewById(R.id.descripcion);
            holder.imagen = convertView.findViewById(R.id.imagen);
            convertView.setTag(holder);

        } else {
                holder = (ViewHolder) convertView.getTag();
            }


        String urlfinal = URL_IMAGENES + sampleData.get(position).getFoto() + ".png";

            holder.imagen.setImageUrl(urlfinal);

            holder.tvnombre.setText(sampleData.get(position).getNombre());
            holder.tvdescripcion.setText(sampleData.get(position).getDescripcion());
            holder.tvprecio.setText(sampleData.get(position).getPrecio().toString());

            return convertView;
        }
    }
}