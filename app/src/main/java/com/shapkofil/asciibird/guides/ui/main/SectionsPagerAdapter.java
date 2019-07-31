package com.shapkofil.asciibird.guides.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.shapkofil.asciibird.R;
import com.shapkofil.asciibird.guides.UserGuideActivity;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_overview,R.string.tab_inputs, R.string.tab_bit_display, R.string.tab_mail_box};
    private final Context mContext;

    private Fragment[] Tabs = new Fragment[] {};

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        Tabs = new Fragment[]
                {
                        OverviewFragment.newInstance(0),
                        InputFragment.newInstance(1),
                        BitDisplayFragment.newInstance(2),
                        MailBoxFragment.newInstance(3)
                };
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return Tabs[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }
}