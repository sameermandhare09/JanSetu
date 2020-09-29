package cssl.dialstar.user_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import cssl.dialstar.user_adapter.EventAdapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_adapter.EventAdapter;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/


public class Events extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static View.OnClickListener myOnClickListener;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ConfigUser config;
    TextView txtnodata,txteventtitle,txtrefresh;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    int userid=0,constituencyid=0;

    public Events() {
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

        View root;
        root= inflater.inflate(R.layout.fragment_events, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        new HttpRequestTaskreq(userid,constituencyid).execute();
        txtnodata = root.findViewById(R.id.txtview);
        txteventtitle = root.findViewById(R.id.eventtitle);
        txtrefresh = root.findViewById(R.id.txtviewrefresh);

        config=new ConfigUser();
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit=share.edit();
        userid=share.getInt("Userid",0);
        constituencyid = share.getInt("constituencyid",0);

        //constituencyid = 4;
        recyclerView=(RecyclerView)root.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);




        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return root;
    }

    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public  void onResume()
    {
        super.onResume();
        new HttpRequestTaskreq(userid,constituencyid).execute();
    }
    @Override
    public void onRefresh() {
        new HttpRequestTaskreq(userid,constituencyid).execute();
        mSwipeRefreshLayout.setRefreshing(false);

    }

    private class HttpRequestTaskreq extends AsyncTask<Void, Void, ArrayList<Dialstar>>
    {
        private boolean isCanceled;
        Dialog progressDialog;

        int userid, constituencyid;
        public HttpRequestTaskreq(int userid, int constituencyid) {
            this.userid = userid;
            this.constituencyid = constituencyid;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            isCanceled = false;    progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

          /*  progressDialog = new ProgressDialog(getActivity());
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
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try
            {
                //final String url2 = config.getGetAllEventDetailsForUser()+"/"+constituencyid;
                final String url2 = config.userremoteurl+"admin/app/getAllEventDetailsForUser"+"/"+constituencyid;

                Log.i("GetAllEventDetails url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Dialstar[] forNow2 = restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));
                return greeting1;
            } catch (Exception e)
            {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){

                Log.i("output in ongoing", myPojo.size() + "");
                Log.d("getNewGrievanceDetails", String.valueOf(myPojo));

                adapter = new EventAdapter(myPojo,getContext());
                recyclerView.setAdapter(adapter);



            }else {
                new android.support.v7.app.AlertDialog.Builder(getActivity()).setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }

            progressDialog.dismiss();

        }
    }
}
