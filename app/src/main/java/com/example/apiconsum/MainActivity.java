package com.example.apiconsum;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apiconsum.interfaces.ProductoAPI;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText edtCodigo;
    TextView tvNombrecli;
    TextView tvDoccli;
    TextView tnNompaq;
    TextView tvCantd;
    TextView tvTotal;
    TextView tvNomus;
    TextView tvFech;
    Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCodigo=findViewById(R.id.edtCodigo);
        tvNombrecli=findViewById(R.id.tvNombrecli);
        tvDoccli=findViewById(R.id.tvDoccli);
        tnNompaq=findViewById(R.id.tvNombrepaq);
        tvCantd=findViewById(R.id.tvCantd);
        tvTotal=findViewById(R.id.tvTotal);
        tvNomus=findViewById(R.id.tvNomus);
        tvFech=findViewById(R.id.tvFech);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find(edtCodigo.getText().toString());
            }
        });


    }

    private void find(String codigo){
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://192.168.0.8/API/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        ProductoAPI productoAPI=retrofit.create(ProductoAPI.class);
        Call<Producto> call=productoAPI.find(codigo);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                try {

                    if(response.isSuccessful()){
                        Producto p=response.body();
                        tvNombrecli.setText(p.getnombre_cliente());
                        tvDoccli.setText(p.getdoc_cliente());
                        tnNompaq.setText(p.getnombre_paquete());
                        tvCantd.setText(p.getcantidad_dias());
                        tvTotal.setText(p.gettotal());
                        tvNomus.setText(p.getnombre_usuario());
                        tvFech.setText(p.getfecha());
                    }

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Errod de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

}