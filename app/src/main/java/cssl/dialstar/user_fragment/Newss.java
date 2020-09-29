package cssl.dialstar.user_fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.user_adapter.NewsAdapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
/*

import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_adapter.NewsAdapter;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/


public class Newss extends Fragment {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Dialstar> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ConfigUser config;


    public Newss() {
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
        rootView= inflater.inflate(R.layout.fragment_newss, container, false);
        new HttpRequestTaskreq().execute();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        config=new ConfigUser();

        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<Dialstar>();



        removedItems = new ArrayList<Integer>();

       /* adapter = new NewsAdapter(data,MainActivity.this);
        recyclerView.setAdapter(adapter);*/

        return  rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        data.clear();
    }
    private class HttpRequestTaskreq extends AsyncTask<Void, Void,
                ArrayList<Dialstar>> {

        Dialog progressDialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url2 =config.getGetAllNewsDetails();
                final String url2 =config.userremoteurl+"admin/app/getAllNewsDetails";



                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new
                        MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 =
                        restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new
                        ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            Log.i("output in ongoing", myPojo.size() + "");
            Log.d("getNewGrievanceDetails", String.valueOf(myPojo));
            adapter = new NewsAdapter(myPojo,getContext());
            recyclerView.setAdapter(adapter);
          /*  recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new
Intent(MainActivity.this,ScrollingActivity.class);
                    *//*String name=v.getTag().toString();
                    intent.putExtra("NewsName",name);
                    MainActivity.this.startActivity(intent);
*//*
                    startActivity(intent);
                }
            });*/

            progressDialog.dismiss();

        }
    }
}
