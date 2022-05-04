package com.example.chadt.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.chadt.R;
import com.example.chadt.ReceivingTasks.MessageReceiver;
import com.example.chadt.SendingTasks.MessageSender;

public class AboutActivity extends Fragment {

    public ImageView CpedniyNikon;
    public ImageView CommonPlaceC;
    public ImageView GitHub;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.about_activity, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        CpedniyNikon = (ImageView) getView().findViewById(R.id.CpedniyNikon);
        GitHub = (ImageView) getView().findViewById(R.id.GitHub);
        CpedniyNikon.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/CpedniyNikon"));
            startActivity(browserIntent);
        });
        GitHub.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/CpedniyNikon/OnlineMessenger"));
            startActivity(browserIntent);
        });
    }

}