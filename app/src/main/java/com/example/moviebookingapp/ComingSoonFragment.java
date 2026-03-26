package com.example.moviebookingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();

        movieList.add(new Movie("Dark Knight", "Action", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=EXeTwQWrcwY"));
        movieList.add(new Movie("Inception", "Sci-Fi", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=YoHD9XEInc0"));
        movieList.add(new Movie("Interstellar", "Sci-Fi", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=zSWdZVtXT7E"));

        MovieAdapter adapter = new MovieAdapter(getContext(), movieList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}