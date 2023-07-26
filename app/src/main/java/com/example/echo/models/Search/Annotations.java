package com.example.echo.models.Search;

import com.google.gson.annotations.SerializedName;

public class Annotations {

    @SerializedName("score")
    String score;

    @SerializedName("name")
    String name;

    @SerializedName("text")
    String text;

    public String getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
