package com.example.echo.models.Search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("annotations")
    List<Annotations> annotations;

    public List<Annotations> getAnnotations() {
        return annotations;
    }
}
