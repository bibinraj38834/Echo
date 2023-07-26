package com.example.echo.models.Release;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumResponse {
    @SerializedName("releases")
    List<Releases> releases;

    public List<Releases> getReleases() {
        return releases;
    }
}
