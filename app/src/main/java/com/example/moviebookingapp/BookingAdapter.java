package com.example.moviebookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private final Context context;
    private final ArrayList<Booking> bookingList;

    public BookingAdapter(Context context, ArrayList<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);

        holder.tvName.setText(booking.movieName);
        holder.tvDate.setText(booking.dateTime);
        holder.tvTickets.setText(booking.seatCount + " Tickets");

        // dynamic posters
        int posterResId = R.drawable.movie_poster_1;
        if (booking.movieName != null) {
            if (booking.movieName.equalsIgnoreCase("Inception")) {
                posterResId = R.drawable.movie_poster_2;
            } else if (booking.movieName.equalsIgnoreCase("Interstellar")) {
                posterResId = R.drawable.movie_poster_3;
            }
        }
        holder.ivPoster.setImageResource(posterResId);

        holder.btnCancel.setOnClickListener(v -> {
            if (isFutureBooking(booking.dateTime)) {
                showCancelDialog(booking, holder.getAdapterPosition());
            } else {
                Toast.makeText(context, "Cannot cancel past bookings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Inside BookingAdapter.java
    private boolean isFutureBooking(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return false;

        try {
            // MATCH THIS to your JSON format exactly
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

            // This converts your string "2026-05-15" into a Date object
            Date bookingDate = sdf.parse(dateStr);

            // This gets the current date/time on your Fedora system
            Date currentDate = new Date();

            // LOGIC CHECK:
            // If bookingDate is 2026-05-15 and today is 2026-05-01, this returns TRUE.
            return bookingDate != null && bookingDate.after(currentDate);

        } catch (Exception e) {
            // If it hits here, the date format in Firebase doesn't match "yyyy-MM-dd"
            e.printStackTrace();
            return false;
        }
    }

    private void showCancelDialog(Booking booking, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Cancel Booking")
                .setMessage("Are you sure you want to cancel this booking?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // curr user id
                    String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();

                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference("bookings")
                            .child(userId)
                            .child(booking.bookingId)
                            .removeValue() //firebase Delete command
                            .addOnSuccessListener(aVoid -> {

                                Toast.makeText(context, "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvTickets;
        ImageButton btnCancel;
        ImageView ivPoster;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBookingMovieName);
            tvDate = itemView.findViewById(R.id.tvBookingDate);
            tvTickets = itemView.findViewById(R.id.tvBookingTickets);
            btnCancel = itemView.findViewById(R.id.btnCancelBooking);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }
    }
}