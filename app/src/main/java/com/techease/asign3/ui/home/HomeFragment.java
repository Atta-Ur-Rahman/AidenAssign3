package com.techease.asign3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.techease.asign3.R;
import com.techease.asign3.genrelUtills.GenrelUtils;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static String DATE_FORMAT_2 = "dd-MMM-yyyy";
    public static String TIME_FORMAT = "hh:mm a";

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView tvTime = root.findViewById(R.id.tvTime);
        tvTime.setText(GenrelUtils.getCurrentTime());

        final TextView tvDate = root.findViewById(R.id.tvDate);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                tvDate.setText(s);
            }
        });

        TIME_FORMAT = GenrelUtils.getSharedPreferences(getActivity()).getString("date_format", "hh:mm a");

        return root;
    }
}