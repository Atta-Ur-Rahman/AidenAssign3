package com.techease.asign3.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.techease.asign3.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AiHomeFragment extends Fragment {


    View parentView;

    TextView tvAiden;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.fragment_ai_home, container, false);
        tvAiden=parentView.findViewById(R.id.tvAiden);



        return parentView;
    }


}
