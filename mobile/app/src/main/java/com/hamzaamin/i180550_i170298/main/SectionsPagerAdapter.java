package com.hamzaamin.i180550_i170298.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hamzaamin.i180550_i170298.CallFragment;
//import com.abubakar.i180449_i180564.CameraFragment;
//import com.mhassanakbar.i180564_i180449.ChatFragment;
//import com.mhassanakbar.i180564_i180449.ContactFragment;
import com.hamzaamin.i180550_i170298.CameraFragment;
import com.hamzaamin.i180550_i170298.ChatFragment;
import com.hamzaamin.i180550_i170298.ContactFragment;
import com.hamzaamin.i180550_i170298.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(position == 3){
            return ContactFragment.newInstance(1);
        }
        if(position == 1){
            return CameraFragment.newInstance("1", "1");
        }
        if(position == 2) {
            return ChatFragment.newInstance(1);
        }
        if(position == 0) {
            return CallFragment.newInstance(1);
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 4;
    }
}