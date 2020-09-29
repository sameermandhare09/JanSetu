package cssl.dialstar.representative_adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cssl.dialstar.representative_fragment.IssueView;
import cssl.dialstar.representative_fragment.Representative;

/**
 * Created by sameer on 30/11/17.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                IssueView tab1 = new IssueView();
                return tab1;
            case 1:
                Representative tab2 = new Representative();
                return tab2;
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 0;
    }
}
