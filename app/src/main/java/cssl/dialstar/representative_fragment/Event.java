package cssl.dialstar.representative_fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.EventAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;


public class Event extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    Config config;
    Fragment fragment;
   // Button btnAdd;
    FloatingActionButton btnAdd;
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;
    String usertype="";
    int representativeid=0, constituencyId=0,mlaid=0;
    TextView txtnodata,txteventtitle,txtrefresh;
    String loksabhaconstituency="";
    TextView textGoBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_event, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);



        recyclerView = rootview.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        txtnodata = rootview.findViewById(R.id.txtview);
        txteventtitle = rootview.findViewById(R.id.eventtitle);
        txtrefresh = rootview.findViewById(R.id.txtviewrefresh);
        textGoBack=(TextView)rootview.findViewById(R.id.txtback);

        config = new Config();

        btnAdd = rootview.findViewById(R.id.btn_new_event);
        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = mlaPref.edit();

        usertype = mlaPref.getString("usertype", "");
        representativeid = mlaPref.getInt("representativeid",0);
        constituencyId = mlaPref.getInt("constituencyid",0);
        loksabhaconstituency = mlaPref.getString("loksabhaconstituency","");

        mlaid = mlaPref.getInt("mlaid",0);

       // constituencyId = 4;


      /*  if(usertype.equalsIgnoreCase("mla")){
            btnAdd.setVisibility(View.GONE);
            new HttpRequestTaskreq(mlaid,constituencyId,usertype).execute();
        }else{
            new HttpRequestTaskreq(representativeid,constituencyId,usertype).execute();
        }
*/
        new HttpRequestTaskreq(representativeid,constituencyId,usertype,loksabhaconstituency).execute();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eventname="",eventstartdate="",eventenddate="";
                String description="";
                int eventid=0,userid=0;

                int editevent = 0,flag=1,eventstateid=0,eventconsid=0;
                fragment =AddEvent.newInstance(flag,editevent,eventname="",eventstateid=0,
                        eventconsid=0,eventstartdate="",eventenddate="",description="",eventid=0,userid=0,"");
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });


        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        textGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usertype.equalsIgnoreCase("mla")||
                        usertype.equalsIgnoreCase("mp")){
                    fragment = new Dashboard();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }
                else {
                    fragment = new IssueView();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }

/*
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }*/

            }
        });
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();


     /*   if(usertype.equalsIgnoreCase("mla")){

            new HttpRequestTaskreq(mlaid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }else{
            new HttpRequestTaskreq(representativeid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }
*/

        new HttpRequestTaskreq(representativeid,constituencyId,usertype,loksabhaconstituency).execute();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
/*
        if(usertype.equalsIgnoreCase("mla")){

            new HttpRequestTaskreq(mlaid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }else{
            new HttpRequestTaskreq(representativeid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }

*/

        new HttpRequestTaskreq(representativeid,constituencyId,usertype,loksabhaconstituency).execute();
        mSwipeRefreshLayout.setRefreshing(false);

    }


    private class HttpRequestTaskreq extends AsyncTask<Void, Void, ArrayList<DialstarPojo>>
    {
        private boolean isCanceled;
        Dialog progressDialog;
        int representativeid=0,  constituencyId=0;
        String usertype="";

        String loksabhaconstituency;
        public HttpRequestTaskreq(int representativeid, int constituencyId,String usertype,String loksabhaconstituency) {
            this.representativeid = representativeid;
            this.constituencyId = constituencyId;
            this.usertype = usertype;
            this.loksabhaconstituency = loksabhaconstituency;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            isCanceled = false;
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

      /*      progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
                // Set a click listener for progress dialog cancel button
                @Override
                public void onClick(DialogInterface dialog, int which){
                    // dismiss the progress dialog
                    progressDialog.dismiss();
                    // Tell the system about cancellation
                    isCanceled = true;
                }
            });
            progressDialog.show(); // Display Progress Dialog*/
        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try
            {
                final String url2 = config.representativeremoteurl+"admin/app/getAllEventDetails"+"/"+representativeid+"/"+constituencyId+"/"+usertype;
                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));
                return greeting1;
            } catch (Exception e)
            {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            if(myPojo!=null){
                Log.i("event details pojo size", myPojo.size() + "");
                Log.d("Event Details Pojo", String.valueOf(myPojo));

                if(myPojo.size()==0){
                    txtnodata.setVisibility(View.VISIBLE);
                    txtrefresh.setVisibility(View.GONE);
                    txteventtitle.setVisibility(View.VISIBLE);
                    txteventtitle.setText(getResources().getString(R.string.events)+"("+myPojo.size()+")");
                }else{
                    txteventtitle.setVisibility(View.VISIBLE);
                    txteventtitle.setText(getResources().getString(R.string.events)+"("+myPojo.size()+")");
                }
                adapter = new EventAdapter(myPojo,getActivity());
                recyclerView.setAdapter(adapter);

            }else{
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

            }

            progressDialog.dismiss();

        }
    }
}
