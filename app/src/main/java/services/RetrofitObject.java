package services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class that holds a single retrofit instance for whole application
 */
public final class RetrofitObject {

    public static final String BASE_URL = "http://192.168.0.34:8080/";

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
