package com.example.chadt.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.chadt.AuthorizationCode.AuthorizationActivity;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.R;

public class SettingsActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button logout = (Button) getView().findViewById(R.id.logout);
        TextView nickName = (TextView) getView().findViewById(R.id.YourNick);
        nickName.setText(GLOBALVALUES.name);
        logout.setOnClickListener(v -> {
            getActivity().finish();
            startActivity(new Intent(getActivity(), AuthorizationActivity.class));
        });
        SwitchCompat soundButton = (SwitchCompat) getView().findViewById(R.id.soundToggleButton);
        soundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GLOBALVALUES.Sounds = true;
                } else {
                    GLOBALVALUES.Sounds = false;
                }
            }
        });
        SwitchCompat vibrationButton = (SwitchCompat) getView().findViewById(R.id.vibrationToggleButton);
        vibrationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    GLOBALVALUES.Vibration = true;
                } else {
                    GLOBALVALUES.Vibration = false;
                }
            }
        });
    }


}