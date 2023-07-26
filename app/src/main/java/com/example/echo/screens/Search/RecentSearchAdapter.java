package com.example.echo.screens.Search;

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
import com.example.echo.models.Release.Releases;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ReleaseViewHolder> {
    private List<Releases> releaseList;
    private Context context;

    public RecentSearchAdapter(List<Releases> releases, Context context) {
        this.releaseList = releases;
        this.context = context;
    }

    @NonNull
    @Override
    public ReleaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular_songs, parent, false);
        return new ReleaseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReleaseViewHolder holder, int position) {
        Releases release = releaseList.get(position);
        holder.textViewReleaseTitle.setText(release.getTitle());
        holder.textViewReleaseDetails.setText(release.getDate());
        // adding a single image as image not available in the API response
        String imageName = "demo2.jpeg";
        try {
            // Open the image file from the assets folder
            InputStream inputStream = context.getAssets().open(imageName);

            // Load the image using BitmapFactory and set it in the ImageView
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            holder.popularSongImage.setImageBitmap(bitmap);

            // Close the InputStream after use
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return releaseList.size();
    }

    public static class ReleaseViewHolder extends RecyclerView.ViewHolder {
        TextView textViewReleaseTitle, textViewReleaseDetails;
        ImageView popularSongImage;

        public ReleaseViewHolder(View itemView) {
            super(itemView);
            textViewReleaseTitle = itemView.findViewById(R.id.popular_song_name);
            textViewReleaseDetails = itemView.findViewById(R.id.popular_song_details);
            popularSongImage = itemView.findViewById(R.id.popular_song_image);
        }
    }
}
