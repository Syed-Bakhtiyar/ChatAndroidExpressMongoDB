package com.example.syed_bakhtiyar.chatappmongodb;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.syed_bakhtiyar.chatappmongodb.FragmentAdapters.ProfilePagerAdapter;

public class ProfileActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        viewPager = (ViewPager) findViewById(R.id.vp);

        viewPager.setAdapter(new ProfilePagerAdapter(getSupportFragmentManager()));


    }
}
