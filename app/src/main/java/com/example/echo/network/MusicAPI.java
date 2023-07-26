package com.example.echo.network;

import com.example.echo.models.Release.AlbumResponse;
import com.example.echo.models.Search.SearchResponse;
import com.example.echo.models.GenreResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MusicAPI {

    @GET("artist/{id}")
    Call<SearchResponse> getArtistInfo(@Path("id") String artistId);

    @GET("genre/{id}")
    Call<GenreResponse> getGenreInfo(@Path("id") String genreId);

    @GET("release")
    Call<AlbumResponse> getReleaseInfo(@Query("label") String label, @Query("limit") String limit, @Query("fmt") String format);
    @GET("annotation")
    Call<SearchResponse> getSearchInfo(@Query("query") String searchQuery, @Query("limit") String limit,@Query("fmt") String format);

}
