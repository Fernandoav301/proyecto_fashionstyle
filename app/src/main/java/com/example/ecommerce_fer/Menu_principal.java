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

public class Menu_principal extends AppCompatActivity {
    private ListView listView;
    SmartImageView imageView;

    String URL_CATEGORIAS = "https://fashionstylefdo.000webhostapp.com/categorias.php";
    String URL_IMAGENES = "https://fashionstylefdo.000webhostapp.com/images/";

    String categoria, fotoca;

    private List<categorias> sampleData2;
    private ArrayList<categorias> sampleList2;
    private Menu_principal.ImagenAdapter2 sampleAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_productos);

        listView = findViewById(R.id.ListProductos);
        MostrarLista2();
    }

    private void MostrarLista2() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("cargando datos...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL_CATEGORIAS, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    progressDialog.dismiss();
                    try {
                        final JSONArray jsonArray = new JSONArray(new String(responseBody));

                        sampleData2 = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObjectInside = jsonArray.getJSONObject(i);
                            categoria = jsonObjectInside.getString("name");
                            fotoca = jsonObjectInside.getString("fotomenu");
                            sampleData2.add(new categorias(categoria, fotoca));
                        }

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                Intent intent = new Intent(Menu_principal.this, Menu_productos.class);
                                startActivity(intent);
                            }

                        });

                        handlePostsList(sampleData2);
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

    private void handlePostsList(final List<categorias> sampleData2) {
        this.sampleData2 = sampleData2;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    sampleList2 = new ArrayList<categorias>();

                    for (int i = 0; i < sampleData2.size(); i++) {
                        sampleList2.add(new categorias(sampleData2.get(i).getCategoria(),
                                sampleData2.get(i).getFtcategoria()));
                    }
                    sampleAdapter2 = new ImagenAdapter2(Menu_principal.this, sampleList2);
                    listView.setAdapter(sampleAdapter2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public class ImagenAdapter2 extends ArrayAdapter<categorias> implements Filterable {

        private ArrayList<categorias> arrayList2;

        Context context;
        List<categorias> sampleData2 = null;
        LayoutInflater layoutInflater;

        public ImagenAdapter2(Context ctx, List<categorias> sampleData2) {
            super(ctx, R.layout.items_menu, sampleData2);

            this.context = ctx;
            this.sampleData2 = sampleData2;
            this.arrayList2 = new ArrayList<categorias>();
            this.arrayList2.addAll(sampleData2);
        }

        public class ViewHolder {
            TextView tvcategoria;
            SmartImageView imagen2;
        }

        @Override
        public int getCount(){ return sampleData2.size();}

        @Override
        public categorias getItem(int position) {return  sampleData2.get(position);}

        @Override
        public long getItemId(int position) {return sampleData2.indexOf(getItem(position));}


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Menu_principal.ImagenAdapter2.ViewHolder holder;

            layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.items_menu, parent, false);

                holder = new Menu_principal.ImagenAdapter2.ViewHolder();
                holder.tvcategoria = convertView.findViewById(R.id.catego);
                holder.imagen2 = convertView.findViewById(R.id.ftcate);
                convertView.setTag(holder);

            } else {
                holder = (Menu_principal.ImagenAdapter2.ViewHolder) convertView.getTag();
            }


            String urlfinal = URL_IMAGENES + sampleData2.get(position).getFtcategoria() + ".png";

            holder.imagen2.setImageUrl(urlfinal);

            holder.tvcategoria.setText(sampleData2.get(position).getCategoria());

            return convertView;
        }
    }


}
