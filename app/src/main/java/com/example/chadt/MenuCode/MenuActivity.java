package com.example.chadt.MenuCode;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.example.chadt.Fragments.AboutActivity;
import com.example.chadt.Fragments.GeneralChatActivity;
import com.example.chadt.Fragments.SettingsActivity;
import com.example.chadt.GlobalVariables.GLOBALVALUES;
import com.example.chadt.MenuCode.ViewPageAdapter;
import com.example.chadt.R;
import com.example.chadt.SendingTasks.CommandSender;
import com.google.android.material.tabs.TabLayout;

public class MenuActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.white));
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
            getWindow().setStatusBarColor(0x00000000);
        }
        getSupportActionBar().hide();

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.addFragment(new SettingsActivity());
        viewPageAdapter.addFragment(new GeneralChatActivity());
        viewPageAdapter.addFragment(new AboutActivity());
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.settings);
        tabLayout.getTabAt(1).setIcon(R.drawable.general_chat);
        tabLayout.getTabAt(2).setIcon(R.drawable.about);

        tabLayout.setScrollPosition(1,0f,true);
        viewPager.setCurrentItem(1);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View focus = getCurrentFocus();
                if (focus != null) {
                    hiddenKeyboard(focus);
                }
            }
        });
    }
    private void hiddenKeyboard(View v) {
        InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
