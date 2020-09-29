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
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;

public class NewGrievance extends Fragment {

    public static TextView txtnodata,txtecount;
    private RecyclerView recyclerView;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    ConfigUser config;
    View rootView;
    ArrayList<Dialstar> dialstar=new ArrayList<>();
    private cssl.dialstar.user_adapter.Grievanceadapter mAdapter;
    int userid = 0;
    String UserId = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

       rootView=inflater.inflate(R.layout.fragment_new_grievance, container, false);

        txtnodata = rootView.findViewById(R.id.txtview);
        txtecount = rootView.findViewById(R.id.txtviewcount);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);


        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = share.edit();
        config=new ConfigUser();

        userid = share.getInt("Userid", 0);
        UserId = String.valueOf(userid);

        if(getActivity()!=null && getView()!=null){
            new HttpRequestTask().execute();

        }else{

        }


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity()!=null && getView()!=null){
            new HttpRequestTask().execute();

        }else{

        }

    }
    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


        Dialog progressDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(getActivity()!=null ){
                progressDialog = new Dialog(getContext());
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.setContentView( R.layout.progressbar );
                progressDialog.show(); // Display Progress Dialog

            }


        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
               // String url=config.getGetUserNewGrievanceDetails()+userid;
           //     String url=config.getGetAllGrievanceDetailsForUser()+userid;
                String url=config.userremoteurl+"admin/app/getAllGrievanceDetailsForUser/"+userid;

                Log.d("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            if(myPojo!=null && getActivity()!=null){

                dialstar = myPojo;
                if(myPojo.size()==0){
                    txtnodata.setVisibility(View.VISIBLE);
                    txtecount.setText("Total Records: "+myPojo.size());
                    // txtrefresh.setVisibility(View.GONE);
                }


                Log.d("myPojo", String.valueOf(myPojo));
                mAdapter = new cssl.dialstar.user_adapter.Grievanceadapter(myPojo,getActivity(), 1);
                recyclerView.setAdapter(mAdapter);
                int size = myPojo.size();
                setcount(size);


            }else {
                new android.support.v7.app.AlertDialog.Builder(getActivity()).setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
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

    public void setcount(int size){
        txtecount.setText(getString(R.string.Total_Records)+size);
    }


}
