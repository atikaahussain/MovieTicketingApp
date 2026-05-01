package com.example.moviebookingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

public class NowShowingFragment extends Fragment implements OnMovieClickListener {

    RecyclerView recyclerView;
    ArrayList<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_showing, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = loadMoviesFromJson("now_showing");

        MovieAdapter adapter = new MovieAdapter(getContext(), movieList, "NOW_SHOWING", this);
        recyclerView.setAdapter(adapter);

        return view;
    }
    public ArrayList<Movie> loadMoviesFromJson(String category) {
        ArrayList<Movie> list = new ArrayList<>();
        try {
            // Open the movies.json
            InputStream is = requireContext().getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON into a Map using GSON
            Gson gson = new Gson();
            Map<String, ArrayList<Movie>> map = gson.fromJson(json,
                    new TypeToken<Map<String, ArrayList<Movie>>>(){}.getType());

            if (map != null && map.containsKey(category)) {
                list = map.get(category);
                for (Movie m : list) {
                    m.convertImageNameToId(requireContext());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void onBookSeatsClick(Movie movie, String movieType) {
        SeatSelectionFragment fragment = SeatSelectionFragment.newInstance(
                movie.getName(),
                movieType,
                movie.getTrailerUrl(),
                movie.getDate()
        );

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}