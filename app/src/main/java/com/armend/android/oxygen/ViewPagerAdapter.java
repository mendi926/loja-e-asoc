package com.armend.android.oxygen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 *  * Created by hp1 on 21-01-2015.
 *  
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created



    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) // if the position is 0 we are returning the First tab
        {

            case 1:
                TabA tabA = new TabA();
                return tabA;

            case 2:
                TabB tabB = new TabB();
                return tabB;
            case 3:
                TabC tabC = new TabC();
                return tabC;
            case 4:
                TabD tabD = new TabD();
                return tabD;

            default:
                TabRez tabRez = new TabRez();
                return tabRez;

        }


    }

          // This method return the titles for the Tabs in the Tab Strip

           @Override
            public CharSequence getPageTitle(int position) {
            return Titles[position];
            }

            // This method return the Number of tabs for the tabs Strip

            @Override
        public int getCount() {
            return NumbOfTabs;
            }
}