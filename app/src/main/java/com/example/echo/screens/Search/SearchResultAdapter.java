package com.example.echo.screens.Search;

// CustomAdapter.java

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.echo.R;
import com.example.echo.models.Search.Annotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {

    private List<Annotations> dataList;
    private Context context;

    public SearchResultAdapter(List<Annotations> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.ratings.setText(dataList.get(position).getScore());
        holder.songDetails.setText(dataList.get(position).getText());
        holder.songName.setText(dataList.get(position).getName());

        // adding a single image as image not available in the API response
        String imageName = "demo.jpeg";
        try {
            // Open the image file from the assets folder
            InputStream inputStream = context.getAssets().open(imageName);

            // Load the image using BitmapFactory and set it in the ImageView
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.songImage.setImageBitmap(bitmap);

            // Close the InputStream after use
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ratings, songName, songDetails;
        ImageView songImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ratings = itemView.findViewById(R.id.ratings);
            songDetails = itemView.findViewById(R.id.song_details);
            songName = itemView.findViewById(R.id.song_name);
            songImage = itemView.findViewById(R.id.result_image);

        }
    }
}

