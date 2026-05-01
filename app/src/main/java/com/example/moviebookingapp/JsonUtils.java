package com.example.moviebookingapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;
import android.content.Context;

public class JsonUtils {

    public static ArrayList<Movie> loadMoviesFromJson(Context context, String category) {
        ArrayList<Movie> list = new ArrayList<>();
        try {
            // Open the movies.json file from the assets folder
            InputStream is = context.getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            Gson gson = new Gson();
            // Parse JSON into a Map where keys are categories (now_showing/coming_soon)
            Map<String, ArrayList<Movie>> map = gson.fromJson(json,
                    new TypeToken<Map<String, ArrayList<Movie>>>(){}.getType());

            if (map != null && map.containsKey(category)) {
                list = map.get(category);
                // Convert string names to actual drawable Resource IDs
                for (Movie m : list) {
                    m.convertImageNameToId(context);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}