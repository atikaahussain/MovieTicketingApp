package com.example.moviebookingapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private String movieType;  // ✅ Add this
    private OnMovieClickListener listener;

    public MovieAdapter(Context context, ArrayList<Movie> movieList, String movieType, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.movieType = movieType;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        holder.name.setText(movie.getName());
        holder.genre.setText(movie.getGenre());
        holder.image.setImageResource(movie.getImageResId());

        // Trailer button
        holder.btnTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            context.startActivity(intent);
        });

        holder.btnBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookSeatsClick(movie, movieType);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}