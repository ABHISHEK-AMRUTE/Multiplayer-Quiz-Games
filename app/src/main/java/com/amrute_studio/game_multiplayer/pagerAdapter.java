package com.amrute_studio.game_multiplayer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class pagerAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;
    Home_fragment objectHomeFragment;
    profile_fragment objectJoinRooms;

    //Constructor to the class
    public pagerAdapter(FragmentManager fm, int tabCount) {
        super(fm,tabCount);
        //Initializing tab count
        objectHomeFragment = new Home_fragment();
        objectJoinRooms = new profile_fragment();
        this.tabCount= tabCount;
    }



    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:

                return objectJoinRooms;
            case 1:

                return objectHomeFragment;

            default:
                return objectJoinRooms;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}