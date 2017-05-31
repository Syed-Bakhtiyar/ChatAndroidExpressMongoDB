package com.example.syed_bakhtiyar.chatappmongodb.FragmentAdapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.syed_bakhtiyar.chatappmongodb.Fragments.Logo;
import com.example.syed_bakhtiyar.chatappmongodb.Fragments.UsersList;

/**
 * Created by Syed_Bakhtiyar on 5/30/2017.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {
    public ProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){

            case 0:

                return "All Users";

            case 1:

                return "Develop on";

            default:

                return null;
        }

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:

                return new UsersList();

            case 1:

                return new Logo();

            default:

                return null;
        }



    }

    @Override
    public int getCount() {
        return 2;
    }
}
