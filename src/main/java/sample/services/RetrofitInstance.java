package sample.services;

import com.google.gson.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.LocalDateTime;


public class RetrofitInstance {

    private static Retrofit ourInstance = new Retrofit.Builder()
            .baseUrl("https://taraj.tk/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder()
                            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                    LocalDateTime.parse(json.getAsString()))
                            .create()
            ))
            .build();

    public static Retrofit getInstance() {
        return ourInstance;
    }

    private RetrofitInstance() {

    }
}
