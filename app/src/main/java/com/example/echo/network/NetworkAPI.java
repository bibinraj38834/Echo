package com.example.echo.network;

import com.example.echo.di.APIClient;
import com.example.echo.models.Release.AlbumResponse;
import com.example.echo.models.Search.SearchResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class NetworkAPI {

    private static final String TAG = "NetworkAPI";

    private static final int TIMEOUT_SECONDS = 30;
    private static OkHttpClient okHttpClient;
    private static MusicAPI musicAPI;

    public static void init() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = APIClient.getRetrofitInstance();
        musicAPI = retrofit.create(MusicAPI.class);
    }

    public static void getReleasesInfo( String label,Callback<AlbumResponse> callback) {
        Call<AlbumResponse> call = musicAPI.getReleaseInfo(label, "4","json");
        call.enqueue(callback);
    }

    public static void getSearchInfo( String label,Callback<SearchResponse> callback) {
        Call<SearchResponse> call = musicAPI.getSearchInfo(label, "15", "json");
        call.enqueue(callback);
    }


}

