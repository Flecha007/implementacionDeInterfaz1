package com.example.apiconsum.interfaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductoAPI {

    @GET("API/post.php?idventa={id}")
    public Call<Producto> find(@Path("id") String id);

}
