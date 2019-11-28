package com.techease.asign3.ui.settings;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.techease.asign3.MainActivity;
import com.techease.asign3.R;
import com.techease.asign3.genrelUtills.GenrelUtils;
import com.techease.asign3.ui.home.HomeFragment;

public class BolSetFragment extends Fragment {

    private BolSetViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = ViewModelProviders.of(this).get(BolSetViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bol_set, container, false);
        SwitchCompat aSwitch = root.findViewById(R.id.switch_change_hour);
        SwitchCompat aSwitchScreen = root.findViewById(R.id.switch_change_screen_orientation);
        SwitchCompat switchCompatNightMode = root.findViewById(R.id.switch_change_screen_mode);


        if (HomeFragment.TIME_FORMAT.equals("HH:mm a")) {
            aSwitch.setChecked(true);
        }

            aSwitchScreen.setChecked(GenrelUtils.getSharedPreferences(getActivity()).getBoolean("screen", false));
        switchCompatNightMode.setChecked(GenrelUtils.getSharedPreferences(getActivity()).getBoolean("night_mode", false));


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    HomeFragment.TIME_FORMAT = "HH:mm a";
                    GenrelUtils.putStringValueInEditor(getActivity(), "date_format", "HH:mm a");
                } else {
                    HomeFragment.TIME_FORMAT = "hh:mm a";
                    GenrelUtils.putStringValueInEditor(getActivity(), "date_format", "hh:mm a");

                }
            }
        });


        aSwitchScreen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GenrelUtils.putBooleanValueInEditor(getActivity(), "screen", isChecked);
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

                } else {
                    GenrelUtils.putBooleanValueInEditor(getActivity(), "screen", isChecked);
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));

                }
            }
        });


        switchCompatNightMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GenrelUtils.putBooleanValueInEditor(getActivity(), "night_mode", isChecked);
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));

                } else {
                    GenrelUtils.putBooleanValueInEditor(getActivity(), "night_mode", isChecked);
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(), MainActivity.class));


                }
            }
        });
        return root;
    }
}