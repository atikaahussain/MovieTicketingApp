package com.example.moviebookingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyBookingsFragment extends Fragment {
    private RecyclerView rv;
    private BookingAdapter adapter;
    private ArrayList<Booking> list;
    private TextView tvStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_bookings, container, false);
        rv = v.findViewById(R.id.rvMyBookings);
        tvStatus = v.findViewById(R.id.tvBookingsStatus);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        adapter = new BookingAdapter(getContext(), list);
        rv.setAdapter(adapter);

        loadBookings();
        return v;
    }

    private void loadBookings() {
        if (tvStatus != null) tvStatus.setText("Loading from Cloud...");

        String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        com.google.firebase.database.DatabaseReference dbRef =
                com.google.firebase.database.FirebaseDatabase.getInstance().getReference("bookings").child(userId);

        dbRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                list.clear();
                for (com.google.firebase.database.DataSnapshot data : snapshot.getChildren()) {
                    Booking b = data.getValue(Booking.class);
                    if (b != null) {
                        list.add(b);
                    }
                }
                adapter.notifyDataSetChanged();

                if (tvStatus != null) {
                    tvStatus.setText(list.isEmpty() ? "No bookings found." : "Bookings found: " + list.size());
                }
            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Toast.makeText(getContext(), "Fetch failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}