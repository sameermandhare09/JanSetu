package cssl.dialstar.representative_fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cssl.dialstar.R;

public class Dashboard extends Fragment  {
    private ViewPager viewPager;
    TabLayout tabLayout;
    int mlaid,representativeid;
    SharedPreferences mlaPref;
    Typeface typeface=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview ;
        rootview = inflater.inflate(R.layout.fragment_dashboard, container, false);

        viewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        typeface = ResourcesCompat.getFont(getContext(), R.font.alegreyasans_regular);


        tabLayout = (TabLayout) rootview.findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);


        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            //noinspection ConstantConditions
            TextView tv = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.customtabs,null);
            tv.setTypeface(typeface);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        representativeid = mlaPref.getInt("representativeid",representativeid);
        mlaid = mlaPref.getInt("mlaid",mlaid);
        Log.d("mlaid", String.valueOf(mlaid));


       /* FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        */


           //clear the fragment back stack
           if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
               FragmentManager fm = getActivity().getSupportFragmentManager();
               fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
               //getActivity().getSupportFragmentManager().popBackStack();
           }



      /*  for (Fragment fragment:getFragmentManager().getFragments()) {
            getFragmentManager().beginTransaction().remove(fragment).commit();
        }*/
           return rootview;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new IssueView(), getResources().getString(R.string.Issue_View));
            adapter.addFragment(new Representative(), getResources().getString(R.string.Representative));

        viewPager.setAdapter(adapter);
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
