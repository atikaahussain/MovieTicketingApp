package com.example.moviebookingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NowShowingFragment extends Fragment implements OnMovieClickListener {

    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
//        to show in vertical scrolling list we used liner layout
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();

        movieList.add(new Movie("Dark Knight", "Action", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=EXeTwQWrcwY"));
        movieList.add(new Movie("Inception", "Sci-Fi", R.drawable.movie_poster_2, "https://www.youtube.com/watch?v=YoHD9XEInc0"));
        movieList.add(new Movie("Interstellar", "Sci-Fi", R.drawable.movie_poster_3, "https://www.youtube.com/watch?v=zSWdZVtXT7E"));

        MovieAdapter adapter = new MovieAdapter(getContext(), movieList, "NOW_SHOWING", this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onBookSeatsClick(Movie movie, String movieType) {
        // Create the SeatSelectionFragment
        SeatSelectionFragment fragment = SeatSelectionFragment.newInstance(
                movie.getName(),
                movieType,
                movie.getTrailerUrl()
        );

        // Navigate to SeatSelectionFragment
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}