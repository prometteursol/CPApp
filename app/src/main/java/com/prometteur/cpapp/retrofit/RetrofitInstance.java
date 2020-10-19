package com.prometteur.cpapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.prometteur.cpapp.utils.Preferences;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.prometteur.cpapp.retrofit.ApiInterface.BASE_URL;
import static com.prometteur.cpapp.utils.Constants.USERID;
import static com.prometteur.cpapp.utils.Constants.USERIDVAL;
import static com.prometteur.cpapp.utils.Constants.USERSESSION;
import static com.prometteur.cpapp.utils.Constants.USERSESSIONVAL;


public class RetrofitInstance {
   public static Retrofit retrofit;
    public RetrofitInstance()
    {

    }


    //Create a new Interceptor.
    public static Interceptor headerAuthorizationInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            Headers headers = request.headers().newBuilder().add("user_session", USERSESSIONVAL).add("user_session_id",USERIDVAL).build();
            request = request.newBuilder().headers(headers).build();

            return chain.proceed(request);
        }
    };



    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(45, TimeUnit.SECONDS)
                .writeTimeout(45, TimeUnit.SECONDS)
                .addInterceptor(headerAuthorizationInterceptor)
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }



}
