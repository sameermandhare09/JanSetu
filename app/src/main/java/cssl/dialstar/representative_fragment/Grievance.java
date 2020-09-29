package cssl.dialstar.representative_fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cssl.dialstar.R;


public class Grievance extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";



    public static  TabLayout tabLayout;
    private ViewPager viewPager;
    int grievancecategoryid = 0;
    String representative = "";
    int userid=0,representativeid=0;
    int flag=0;
    Typeface typeface=null;

    TextView txtnewcomplaintcount,txtongoingcount;
    LayerDrawable icon;
    private OnFragmentInteractionListener mListener;

    public Grievance() {

    }

    public static Grievance newInstance(int param1, int param2,int flag,String representative) {
        Grievance fragment = new Grievance();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, flag);
        args.putString(ARG_PARAM4, representative);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           grievancecategoryid = getArguments().getInt(ARG_PARAM1);
           representativeid = getArguments().getInt(ARG_PARAM2);
           flag = getArguments().getInt(ARG_PARAM3);
            representative = getArguments().getString(ARG_PARAM4);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView;
         rootView=inflater.inflate(R.layout.fragment_grievance2, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        typeface = ResourcesCompat.getFont(getContext(), R.font.alegreyasans_regular);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setCustomView(R.layout.badged_tab);
        tabLayout.getTabAt(1).setCustomView(R.layout.badged_tab);

        //tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.holo_blue_light));
      //  setTabCount(0,1);
      //  setTabCount(1,2);
           //tabLayout.getTabAt(0).setText("New");
        //tabLayout.getTabAt(1).setText("ON GOING");
        tabLayout.getTabAt(2).setText(getResources().getString(R.string.COMPLETED));

        TextView tv = (TextView)LayoutInflater.from(getContext()).inflate(R.layout.customtabs,null);
        tv.setTypeface(typeface);
        tabLayout.getTabAt(2).setCustomView(tv);





    /*    tabLayout.getTabAt(0).setCustomView(R.layout.badged_tab);
        if(tabLayout.getTabAt(0) != null && tabLayout.getTabAt(0).getCustomView() != null) {
            txtnewcomplaintcount = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.badge);
            TextView b1 = (TextView) tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tvtab1);
            if(b1 != null) {
                b1.setText("NEW");
            }
            if(txtnewcomplaintcount != null) {
                txtnewcomplaintcount.setText("5");
            }
            View v = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.badgeCotainer);
            if(v != null) {
                v.setVisibility(View.VISIBLE);
            }
        }


        tabLayout.getTabAt(1).setCustomView(R.layout.badged_tab);
        if(tabLayout.getTabAt(1) != null && tabLayout.getTabAt(1).getCustomView() != null) {
            txtongoingcount = (TextView) tabLayout.getTabAt(1).getCustomView().findViewById(R.id.badge);
            TextView b1 = (TextView) tabLayout.getTabAt(1).getCustomView().findViewById(R.id.tvtab1);
            if(b1 != null) {
                b1.setText("ON GOING");
            }
            if(txtongoingcount != null) {
                txtongoingcount.setText("1");
            }
            View v = tabLayout.getTabAt(1).getCustomView().findViewById(R.id.badgeCotainer);
            if(v != null) {
                v.setVisibility(View.VISIBLE);
            }
        }
*/
        return rootView;
    }

    public static  void setTabCount(int tab_count,int item_count){
      //  tabLayout.getTabAt(tab_count).setCustomView(R.layout.badged_tab);
        if(tabLayout.getTabAt(tab_count)!=null){

        }
        if(tabLayout.getTabAt(tab_count) != null && tabLayout.getTabAt(tab_count).getCustomView() != null) {
            TextView txtongoingcount = (TextView) tabLayout.getTabAt(tab_count).getCustomView().findViewById(R.id.badge);
            TextView b1 = (TextView) tabLayout.getTabAt(tab_count).getCustomView().findViewById(R.id.tvtab1);
            if(b1 != null) {
                if(tab_count==0)
                    b1.setText(tabLayout.getResources().getString(R.string.NEW));
                    else
                b1.setText(tabLayout.getResources().getString(R.string.ON_GOING));
            }
            if(txtongoingcount != null) {
                txtongoingcount.setText(item_count+"");
            }
            View v = tabLayout.getTabAt(tab_count).getCustomView().findViewById(R.id.badgeCotainer);
            if(v != null) {
                if(item_count>0){
                    v.setVisibility(View.VISIBLE);
                }else {
                    v.setVisibility(View.GONE);
                }

            }
        }



    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new NewComplaint(grievancecategoryid,representativeid,representative,flag), "NEW");
        adapter.addFragment(new OnGoing(grievancecategoryid,representativeid,representative,flag), "ON GOING");
        adapter.addFragment(new Completed(grievancecategoryid,representativeid,representative,flag), "COMPLETED");

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
          //  mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
