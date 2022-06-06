package com.tanzim.hotelsafety.hotel_user.profile.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tanzim.hotelsafety.R;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Information;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Profile_Employee;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Profile_Facilities;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Profile_License;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Profile_Other_Info;
import com.tanzim.hotelsafety.hotel_user.profile.pages.Hotel_Profile_Owner;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.profile_title, R.string.hotel_license, R.string.hotel_owner_info, R.string.hotel_facilities_employee,
    R.string.hotel_employee_list, R.string.hotel_others_information};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position){
            case 0:
                return new Hotel_Information();
            case 1:
                return new Hotel_Profile_License();
            case 2:
                return new Hotel_Profile_Owner();
            case 3:
                return new Hotel_Profile_Facilities();
            case 4:
                return new Hotel_Profile_Employee();
            case 5:
                return new Hotel_Profile_Other_Info();
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 6;
    }
}