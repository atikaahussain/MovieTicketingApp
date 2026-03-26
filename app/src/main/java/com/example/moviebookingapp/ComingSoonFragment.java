package com.example.moviebookingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ComingSoonFragment extends Fragment implements OnMovieClickListener {

    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_coming_soon, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();

        movieList.add(new Movie("Avengers: Secret Wars", "Action", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=example1"));
        movieList.add(new Movie("Deadpool 3", "Action/Comedy", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=example2"));
        movieList.add(new Movie("Dune Part 3", "Sci-Fi", R.drawable.movie_poster_1, "https://www.youtube.com/watch?v=example3"));

        // ✅ Pass "COMING_SOON" and "this"
        MovieAdapter adapter = new MovieAdapter(getContext(), movieList, "COMING_SOON", this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // ✅ Implement the interface method
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