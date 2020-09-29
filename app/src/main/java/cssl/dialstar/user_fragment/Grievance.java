package cssl.dialstar.user_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_adapter.Grievanceadapter;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;

*/

public class Grievance extends Fragment   {

    ArrayList<Dialstar> dialstar=new ArrayList<>();
    private RecyclerView recyclerView;
    private cssl.dialstar.user_adapter.Grievanceadapter mAdapter;
    Context context;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    int userid = 0;
    String UserId = "";
    ConfigUser config;
    ArrayList<String> imagelist;
    TextView txtnodata,txteventtitle,txtrefresh;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Fragment fragment = new Fragment();
    String title="";


    public Grievance() {

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
        rootView = inflater.inflate(R.layout.fragment_grievance, container, false);
        //BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.navigationgrievance);


       // navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        fragment = new NewGrievance();
        //getSupportActionBar().setTitle("New Grievances");
        if (fragment != null) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);

            fragmentTransaction.commit();

            //
        }


     /*   mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);*/


        txtnodata = rootView.findViewById(R.id.txtview);

       // txtrefresh = rootView.findViewById(R.id.txtviewrefresh);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);




        //mAdapter = new Grievanceadapter(List,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = share.edit();


    /*    recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);*/
       /* prepareMovieData();*/
       config=new ConfigUser();



        userid = share.getInt("Userid", 0);
        UserId = String.valueOf(userid);
     // new HttpRequestTask().execute();


        return rootView;
    }



    public void onDestroyView() {
        super.onDestroyView();

        dialstar.clear();
    }


    }


































































