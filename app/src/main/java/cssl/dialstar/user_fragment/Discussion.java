package cssl.dialstar.user_fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.user_adapter.ViewPagerAdapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.DialogInterface.BUTTON_NEGATIVE;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_adapter.ViewPagerAdapter;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/


public class Discussion extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private TabLayout tabLayout;
    public ViewPager viewPager;

    PagerAdapter adapter;
    ArrayList<Dialstar> myPojoExtra;
    ImageView button;
    EditText comment;
    String commentNow;
    String userId;
    int discussionid;
    String url;
    View rootView;
    LinearLayout lnrdesc;
    TextView txtno;
    ConfigUser config;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    int userid=0,constituencyid=0;


    SharedPreferences share;
    SharedPreferences.Editor edit;
    String usertype;


    public Discussion() {
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

        rootView= inflater.inflate(R.layout.fragment_discussion, container, false);

         mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
         mSwipeRefreshLayout.setOnRefreshListener(this);

        button=(ImageView) rootView.findViewById(R.id.button2);
        comment=(EditText) rootView.findViewById(R.id.comment);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);


        txtno=(TextView) rootView.findViewById(R.id.txtno);
       txtno.setVisibility(View.GONE);
        lnrdesc=(LinearLayout) rootView.findViewById(R.id.lnrdesc);

        config=new ConfigUser();
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit=share.edit();
        userid=share.getInt("Userid",0);
        constituencyid = share.getInt("constituencyid",0);
       // constituencyid = 4;
        usertype = share.getString("usertype","");



        comment.setPadding(33,10,20,10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int selectedposition=viewPager.getCurrentItem();

                discussionid=myPojoExtra.get(selectedposition).getDiscussionid();
                commentNow=comment.getText().toString().trim();
               // userId="1";

                //{"userid":"1","discussionid":"1","commenttext":"xyz"}
                JSONObject jsonObject=new JSONObject();
                try {
                    if (usertype.equalsIgnoreCase("user")){

                        // usertype = "user";
                        jsonObject.put("usertype",usertype);
                    }
                    else if(usertype.equalsIgnoreCase("representative")){
                        // usertype = "representative";
                        jsonObject.put("usertype",usertype);
                    }

                    jsonObject.put("userid",userid);
                    jsonObject.put("discussionid",discussionid);
                    jsonObject.put("commenttext",commentNow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e("submit discussion",jsonObject.toString());
                new HttpRequestTask1(jsonObject.toString(),selectedposition).execute();
                Log.e("Viewpager selected", String.valueOf(selectedposition));


            }
        });

        new HttpRequestTaskreq(userid,constituencyid).execute();


        return rootView;
    }

    private int getItemofviewpager(int i) {
        return viewPager.getCurrentItem() + i;
    }
    public void onDestroyView() {
        super.onDestroyView();
       // myPojoExtra.clear();
    }



    @Override
    public void onRefresh() {
    /*    int selectedposition=0;
        if(viewPager!=null)
        {
            selectedposition=viewPager.getCurrentItem();
        }
        else
        {
            selectedposition=0;
        }



        new HttpRequestTaskreq(selectedposition,userid,constituencyid).execute();
        mSwipeRefreshLayout.setRefreshing(false);
       */

        int selectedposition=viewPager.getCurrentItem();

        new HttpRequestTaskreq(selectedposition,userid,constituencyid).execute();
        mSwipeRefreshLayout.setRefreshing(false);


    }

     public void refreshDiscussion(){
         int selectedposition=viewPager.getCurrentItem();

         new HttpRequestTaskreq(selectedposition,userid,constituencyid).execute();
         mSwipeRefreshLayout.setRefreshing(false);


    }
    private class HttpRequestTaskreq extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        Dialog progressDialog;

        int selectedposition=0;
        private boolean isCanceled;
        int userid, constituencyid;

      /*  public HttpRequestTaskreq(int selectedposition) {
            this.selectedposition=selectedposition;

        }*/

        public HttpRequestTaskreq(int userid, int constituencyid) {
            this.userid =  userid;
            this.constituencyid = constituencyid;
        }

        public HttpRequestTaskreq(int selectedposition, int userid, int constituencyid) {
            this.selectedposition=selectedposition;
            this.userid =  userid;
            this.constituencyid = constituencyid;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isCanceled = false;
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
 /*progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.setCancelable(false);
            progressDialog.setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener(){
                // Set a click listener for progress dialog cancel button
                @Override
                public void onClick(DialogInterface dialog, int which){
                    // dismiss the progress dialog
                    progressDialog.dismiss();
                    // Tell the system about cancellation
                    isCanceled = true;
                }
            });
            progressDialog.show();*/

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
             //   final String url2 = config.getGetDiscussionDetails()+"/"+constituencyid;
                final String url2 = config.userremoteurl+"admin/app/getDiscussionDetailsForUser"+"/"+constituencyid;



                Log.i("user discussion url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            progressDialog.dismiss();
            if(myPojo!=null){
                myPojoExtra=myPojo;

                Log.i("output in ongoing", myPojo.size() + "");
                Log.d("getNewGrievanceDetails", String.valueOf(myPojo));
                //   adapter = new ViewPagerAdapter(myPojo,Discussion.this);


                adapter=new ViewPagerAdapter(getActivity(),myPojo);

                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(selectedposition);
                // mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

                mSwipeRefreshLayout.setOnRefreshListener(Discussion.this);




                if(myPojo.size()==0)
                {
                    txtno.setVisibility(View.VISIBLE);
                    lnrdesc.setVisibility(View.INVISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                }else{


                }




            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity()).setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }

        }
    }


    public class HttpRequestTask1 extends AsyncTask<String, Void, String> //Using POST Method, send newly entered comment
    {
        Dialog progressDialog;

        String jsonstr;
        int selectedposition=0;

        public HttpRequestTask1(String jsonstr, int selectedposition) {
            this.jsonstr = jsonstr;
            this.selectedposition=selectedposition;

        }


        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        protected String doInBackground(String... arg0) {

            try {
                //For POST
                //String url = config.getInsertDiscussionDetails();
                String url = config.userremoteurl+"admin/app/insertDiscussionDetails";
                Log.e("insert Discussion url",url);

                MediaType JSON = MediaType.parse("application/json");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, jsonstr);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                String resp = response.message().toString();

                String respbody = response.body().string().toString();


                return respbody;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("Discussion Result", result.trim());
            //
            // mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
            mSwipeRefreshLayout.setOnRefreshListener(Discussion.this);
            progressDialog.dismiss();
            if (result.equalsIgnoreCase("Success")) {
                new HttpRequestTaskreq(selectedposition,userid,constituencyid).execute();
                comment.setText("");
            }
            else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Error in connection. Please try again! ");
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        }

    }





}

