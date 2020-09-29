package cssl.dialstar.user_fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cssl.dialstar.R;
/*

import user.dialstar.cssl.dialstaruser.R;

*/

public class News extends Fragment {

    private ViewPager viewPager1;
    TabLayout tabLayout1;
    Fragment fragment;




    public News() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView;
        rootView= inflater.inflate(R.layout.fragment_news, container, false);

       // viewPager1=(ViewPager) rootView.findViewById(R.id.viewPager1);
       // setupViewPager(viewPager1);

      /*  tabLayout1 = (TabLayout) rootView.findViewById(R.id.tabs1);
        tabLayout1.setupWithViewPager(viewPager1);*/

        return rootView;

    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());//new Addition
        adapter.addFragment(new Newss(),"News");
        adapter.addFragment(new Discussion(), "Discussion");
        adapter.addFragment(new Events(), "Events");
        viewPager.setAdapter(adapter);


    }
    public void onDestroyView() {
        super.onDestroyView();

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
