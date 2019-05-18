package sample.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit ourInstance = new Retrofit.Builder()
            .baseUrl("https://taraj.tk/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static  Retrofit getInstance() {
        return ourInstance;
    }

    private RetrofitInstance() {

    }
}
