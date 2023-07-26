package com.example.echo.screens.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.example.echo.R;
import com.example.echo.di.APIClient;
import com.example.echo.models.Release.AlbumResponse;
import com.example.echo.models.Release.Releases;
import com.example.echo.network.NetworkAPI;
import com.example.echo.screens.Search.SearchActivity;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private APIClient musicApiClient = new APIClient();
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MaterialButton searchButton;
    private MaterialProgressBar materialProgressBar;
    private PopularReleaseAdapter popularReleaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NetworkAPI.init();

        setContentView(R.layout.activity_home);
        toolbar = findViewById(R.id.toolbar);
        materialProgressBar = findViewById(R.id.loading);
        searchButton = findViewById(R.id.search_button);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Good Morning, Bibin");

        // Making an API call to get popular releases information
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor(this);
        builder.addInterceptor(chuckerInterceptor);
        String label = "47e718e1-7ee4-460c-b1cc-1192a841c6e5";

        fetchReleasesDetails(label);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchReleasesDetails(String label) {
        NetworkAPI.getReleasesInfo(label, new Callback<AlbumResponse>() {
            @Override
            public void onResponse(Call<AlbumResponse> call, Response<AlbumResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response here
                    AlbumResponse albumResponse = response.body();
                    if (albumResponse != null) {
                        List<Releases> releasesList = albumResponse.getReleases();
                        recyclerView = findViewById(R.id.rv_popular_songs);
                        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                        popularReleaseAdapter = new PopularReleaseAdapter(releasesList, HomeActivity.this);
                        recyclerView.setAdapter(popularReleaseAdapter);

                    }
                } else {
                    String errorMessage = "Error fetching data";
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            errorMessage = errorBody.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(HomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
                materialProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {

            }
        });
    }
}