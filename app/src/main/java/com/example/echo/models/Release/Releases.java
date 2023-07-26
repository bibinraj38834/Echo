package com.example.echo.models.Release;

import com.google.gson.annotations.SerializedName;

public class Releases {
    @SerializedName("title")
    String title;

    @SerializedName("date")
    String date;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }
}
