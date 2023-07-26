package com.example.echo.screens.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.echo.R;
import com.example.echo.models.Release.AlbumResponse;
import com.example.echo.models.Release.Releases;
import com.example.echo.network.NetworkAPI;
import com.example.echo.screens.HomeScreen.PopularReleaseAdapter;

import java.io.IOException;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private SearchView songSearchView;
    private MaterialProgressBar materialProgressBar;
    private RecyclerView recyclerView;
    private RecentSearchAdapter recentSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        materialProgressBar = findViewById(R.id.loading);
        songSearchView = findViewById(R.id.song_search);
        String label = "d01b43fc-9098-4105-a619-983b6146a777";

        fetchReleasesDetails(label);
        songSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSearchResultActivity(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // We can implement auto suggestion here
                return false;
            }
        });
    }

    private void startSearchResultActivity(String query) {

        Intent intent = new Intent(this, SearchResultActivity.class);
        // Pass the search query as an extra with the intent
        intent.putExtra("SEARCH_QUERY", query);

        // Start the SearchResultActivity
        startActivity(intent);
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
                        recyclerView = findViewById(R.id.rv_recent_songs);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                        recentSearchAdapter = new RecentSearchAdapter(releasesList, SearchActivity.this);
                        recyclerView.setAdapter(recentSearchAdapter);

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
                    Toast.makeText(SearchActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
                materialProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AlbumResponse> call, Throwable t) {

            }
        });
    }
}