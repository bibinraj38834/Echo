package com.example.echo.screens.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.echo.R;
import com.example.echo.models.Search.Annotations;
import com.example.echo.models.Search.SearchResponse;
import com.example.echo.network.NetworkAPI;

import java.io.IOException;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultActivity extends AppCompatActivity {

    private MaterialProgressBar materialProgressBar;
    private SearchResultAdapter searchResultsAdapter;
    private RecyclerView recyclerView;
    private TextView songNameHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        materialProgressBar = findViewById(R.id.loading);
        songNameHint = findViewById(R.id.song_result_hint);
        String searchQuery = getIntent().getStringExtra("SEARCH_QUERY");
        String searchWithQuote = "\"" + searchQuery + "\"";
        songNameHint.setText(searchWithQuote);
        fetchSearchDetails(searchQuery);
    }

    private void fetchSearchDetails(String label) {
        NetworkAPI.getSearchInfo(label, new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    // Handle the successful response here
                    SearchResponse searchResponse = response.body();
                    if (searchResponse != null) {
                        recyclerView = findViewById(R.id.rv_search_result);
                        recyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                        List<Annotations> annotationsList = searchResponse.getAnnotations();
                        searchResultsAdapter = new SearchResultAdapter(annotationsList, SearchResultActivity.this);
                        recyclerView.setAdapter(searchResultsAdapter);

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
                    Toast.makeText(SearchResultActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
                materialProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }
}