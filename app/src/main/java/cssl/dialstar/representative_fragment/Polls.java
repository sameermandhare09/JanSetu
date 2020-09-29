package cssl.dialstar.representative_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import cssl.dialstar.representative_adapter.PollAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;


public class Polls extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View.OnClickListener myOnClickListener;
    RecyclerView.Adapter adapter;
    Config config;
    Fragment fragment;
    FloatingActionButton btnAdd;
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;
    String usertype;
    TextView txtnodata,txtpolltitle;
    TextView textGoBack;
    int represntativeid=0,constituencyId=0,mlaid=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_polls, container, false);

        txtnodata=rootview.findViewById(R.id.txtview);
        txtpolltitle = rootview.findViewById(R.id.polltitle);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefreshLayoutPoll);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        textGoBack=(TextView)rootview.findViewById(R.id.txtback);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = mlaPref.edit();
        mlaid = mlaPref.getInt("mlaid",0);
        represntativeid = mlaPref.getInt("representativeid",0);
        constituencyId = mlaPref.getInt("constituencyid",0);
       // constituencyId = 9;




        recyclerView = rootview.findViewById(R.id.poll_recycler_view);
        recyclerView.setHasFixedSize(true);

        config = new Config();


        btnAdd = (FloatingActionButton) rootview.findViewById(R.id.btnaddpoll);

        usertype = mlaPref.getString("usertype", "");
      /*  if(usertype.equalsIgnoreCase("mla")){
            btnAdd.setVisibility(View.GONE);
            new HttpRequestPolls(mlaid,constituencyId,usertype).execute();

        }else {
            new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();
        }
*/
        new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String calling = "add";

                String subject="",option1="",option2="",option3="",option4="",option5="";
                int stateId=0,constituencyId=0;
                int userid=0,pollid=0;
                String pollloksabhaconstituency ="";

                fragment = NewPoll.newInstance(calling,subject,option1,option2,option3,option4,option5,
                        pollid,stateId,constituencyId,pollloksabhaconstituency);

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

                if(usertype.equalsIgnoreCase("mla")){
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


      /*  if(usertype.equalsIgnoreCase("mla")){
            btnAdd.setVisibility(View.GONE);
            new HttpRequestPolls(mlaid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }else {
            new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }*/
        new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
     /*   if(usertype.equalsIgnoreCase("mla")){
            btnAdd.setVisibility(View.GONE);
            new HttpRequestPolls(mlaid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }else {
            new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();
            mSwipeRefreshLayout.setRefreshing(false);
        }*/

        new HttpRequestPolls(represntativeid,constituencyId,usertype).execute();
        mSwipeRefreshLayout.setRefreshing(false);

    }


    private class HttpRequestPolls extends AsyncTask<Void, Void, ArrayList<DialstarPojo>>
    {
        private boolean isCanceled;
        Dialog progressDialog;
        ArrayList<DialstarPojo> response=new ArrayList<>();
        String usertype="";

        int represntativeid=0, constituencyId=0;
        public HttpRequestPolls(int represntativeid, int constituencyId, String usertype) {
            this.represntativeid = represntativeid;
            this.constituencyId = constituencyId;
            this.usertype = usertype;
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
/* progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");  // Setting Message
           // progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.setCancelable(false);
            progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener(){
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
//                final String url2 = config.getGetAllPollsDetails()+"/"+represntativeid+"/"+constituencyId+"/"+usertype;//call poll web service
                final String url2 = config.representativeremoteurl+"admin/app/getAllPollsDetails"+"/"+represntativeid+"/"+constituencyId+"/"+usertype;//call poll web service

                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                response = new ArrayList(Arrays.asList(forNow2));
                return response;
            } catch (Exception e)
            {
                Log.e("MainActivity", e.getMessage(), e);
                return response;
            }


        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {


           // Log.i("Pojo size", myPojo.size() + "");
           // Log.d("getNewGrievanceDetails", String.valueOf(myPojo));

            if(myPojo.size()==0){
                txtnodata.setVisibility(View.VISIBLE);

            }

            txtpolltitle.setText(getResources().getString(R.string.polls)+"  ("+String.valueOf( myPojo.size())+")");
            adapter = new PollAdapter(myPojo,getActivity());
            recyclerView.setAdapter(adapter);

            progressDialog.dismiss();

        }
    }

}
