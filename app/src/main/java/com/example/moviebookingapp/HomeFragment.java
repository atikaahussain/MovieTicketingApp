package com.example.moviebookingapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

//        helps to glue tabs buttons with the viewpage container
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Now Showing");
                    } else {
                        tab.setText("Coming Soon");
                    }
                }).attach();

        ImageView btnMenu = view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(v -> showPopupMenu(v));
        return view;
    }
    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        popup.getMenu().add("View Last Booking");

        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("View Last Booking")) {
                showLastBookingDialog();
                return true;
            }
            return false;
        });

        popup.show();
    }

    private void showLastBookingDialog() {
        String bookingInfo = TicketSummaryFragment.getLastBookingInfo(requireContext());

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_last_booking, null);

        TextView tvBookingDetails = dialogView.findViewById(R.id.tvBookingDetails);
        Button btnCloseDialog = dialogView.findViewById(R.id.btnCloseDialog);
        Button btnClearSharedPref = dialogView.findViewById(R.id.btnClearSharedPref);

        tvBookingDetails.setText(bookingInfo);

        AlertDialog customDialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnCloseDialog.setOnClickListener(v -> customDialog.dismiss());
        btnClearSharedPref.setOnClickListener(v -> {

            TicketSummaryFragment.clearLastBooking(requireContext());

            android.widget.Toast.makeText(requireContext(), "Booking history cleared!", android.widget.Toast.LENGTH_SHORT).show();

            customDialog.dismiss();
        });

        customDialog.show();
    }
}
