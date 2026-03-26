package com.example.moviebookingapp;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public TextView name, genre;
    public Button btnBook;
    public ImageView btnTrailer;
    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.imgPoster);
        name = itemView.findViewById(R.id.txtMovieName);
        genre = itemView.findViewById(R.id.txtGenreDuration);
        btnTrailer = itemView.findViewById(R.id.btnTrailer);
        btnBook = itemView.findViewById(R.id.btnBookSeats);
    }

}

