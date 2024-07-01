package com.example.proysw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proysw.modelo.dto.Producto;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout txtCodigo,txtNombre,txtPrecio,txtCantidad;
    private ListView listado;
    String url = "http://10.0.2.2/desarrollo/serviciosrest/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enlazarControles();
    }
    private void enlazarControles() {
        txtCodigo = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtCantidad = findViewById(R.id.txtCantidad);
        listado = findViewById(R.id.listado);

        listar(listado);
    }

    public void consultar(View v){
        RequestQueue rq  = Volley.newRequestQueue(this);

        String cod = txtCodigo.getEditText().getText().toString();
        if (cod.trim().isEmpty()){
            txtCodigo.setError("Codigo debe contener dato");
            return ;
        }

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url+"selectProducto.php?codigo="+cod,
                        null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("INFOX","si");
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                /*
                                Log.i("INFOX",data.getString("codigo"));
                                Log.i("INFOX",data.getString("nombre"));
                                Log.i("INFOX",data.getString("precio"));
                                Log.i("INFOX",data.getString("cantidad"));
                                 */
                                Producto p = new Producto();
                                p.setCodigo(Integer.parseInt(data.getString("codigo")));
                                p.setNombre(data.getString("nombre"));
                                p.setPrecio(Double.parseDouble(data.getString("precio")));
                                p.setCantidad(Integer.parseInt(data.getString("cantidad")));
                                mostrarProducto(p);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors that occur in the request
                    }
                });

        // 5
        rq.add(jsonArrayRequest);
    }

    public void listar(View v){
        RequestQueue rq  = Volley.newRequestQueue(this);
        List<Producto> listaProducto = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(Request.Method.GET,
                        url+"listarProductos.php",
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                Log.i("INFOX","si");
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject data = response.getJSONObject(i);
                                        Producto p = new Producto();
                                        p.setCodigo(Integer.parseInt(data.getString("codigo")));
                                        p.setNombre(data.getString("nombre"));
                                        p.setPrecio(Double.parseDouble(data.getString("precio")));
                                        p.setCantidad(Integer.parseInt(data.getString("cantidad")));
                                        listaProducto.add(p);
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                ArrayAdapter<Producto> adp =
                                        new ArrayAdapter<>(getBaseContext(),
                                                android.R.layout.simple_list_item_1,
                                                listaProducto);
                                listado.setAdapter(adp);


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle errors that occur in the request
                            }
                        });

        // 5
        rq.add(jsonArrayRequest);
    }
    private void mostrarProducto(Producto p) {
        txtCodigo.getEditText().setText(""+p.getCodigo());
        txtNombre.getEditText().setText(""+p.getNombre());
        txtPrecio.getEditText().setText(""+p.getPrecio());
        txtCantidad.getEditText().setText(""+p.getCantidad());
    }

}