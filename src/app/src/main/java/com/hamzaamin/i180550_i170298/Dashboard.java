package com.hamzaamin.i180550_i170298;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.hamzaamin.i180550_i170298.databinding.ActivityDashboardBinding;
import com.hamzaamin.i180550_i170298.main.SectionsPagerAdapter;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding binding;

    TabLayout tabLayout;

    private int[] tabIcons = {
            R.drawable.ic_twotone_phone_24,
            R.drawable.ic_twotone_photo_camera,
            R.drawable.ic_twotone_chat_bubble,
            R.drawable.ic_twotone_people
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());


        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);


        tabLayout = binding.tabs;
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

}