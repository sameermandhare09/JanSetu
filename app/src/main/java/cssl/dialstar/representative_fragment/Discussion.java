package cssl.dialstar.representative_fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.ViewPagerAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_adapter.EditProfileAdapter;
import cssl.dialstar.user_utils.Dialstar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Discussion extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private ViewPager viewPager;
    PagerAdapter adapter;
    ArrayList<DialstarPojo> myPojoExtra;
    ImageView button;
    private TabLayout tabLayout;
    FloatingActionButton button1;
    int stateId,constituencyId,constituencyIddisc;
    int disccon=0;
    ArrayList<Dialstar> dialstarPojos1;
    ArrayList<Dialstar> dialstarPojos2;
    EditProfileAdapter customAdapter;
    EditText comment;
    String commentNow;
    public TextInputLayout InputEditText;
    public EditText textInputEditText;
    public TextView txtstate;
    public TextView txtconstituency;
    public Spinner spstate;
    public Spinner spconstituency;
    int representativeid=0,mlaid=0;
    public Button btn_save;
    int userId;
    int discussionid;
    String url;
    JSONObject jsonObject;
    SharedPreferences mlaPref;
    SharedPreferences.Editor editor ;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    Config config = new Config();
    int selectedposition;
    TextView textView;
    String usertype;
    TextView textGoBack;
    Fragment fragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.fragment_discussion1, container, false);


        textView = rootview.findViewById(R.id.txtview);
        textGoBack=(TextView)rootview.findViewById(R.id.txtback);
        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor= mlaPref.edit();



        representativeid = mlaPref.getInt("representativeid",0);
        constituencyId = mlaPref.getInt("constituencyid",0);
        stateId = mlaPref.getInt("stateid",0);
        //constituencyId = 4;
        usertype = mlaPref.getString("usertype","");
        mlaid = mlaPref.getInt("mlaid",0);

        //add the current discussion flag in shared pref
        editor.putInt("discussionfragflag",1);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2=new ArrayList<>();
        //  Toolbar toolbar = (Toolbar) rootview.findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        button=(ImageView) rootview.findViewById(R.id.button2);





        button1 = rootview.findViewById(R.id.btn_new_discussion);
       /* if(usertype.equalsIgnoreCase("mla")){
            button1.setVisibility(View.INVISIBLE);
        }*/
        comment=(EditText) rootview.findViewById(R.id.comment);
        viewPager = (ViewPager) rootview.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootview.findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);

        comment.setPadding(33,10,20,10);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                selectedposition=viewPager.getCurrentItem();

                discussionid=myPojoExtra.get(selectedposition).getDiscussionid();
                commentNow=comment.getText().toString().trim();
             /*   if(usertype.equalsIgnoreCase("representative")) {
                    userId = representativeid;
                } else if(usertype.equalsIgnoreCase("mla")) {
                    userId = mlaid;
                }*/
             userId = representativeid;

                //{"userid":"1","discussionid":"1","commenttext":"xyz"}
                jsonObject=new JSONObject();
                try {
                    if(usertype.equalsIgnoreCase("representative")){
                        // usertype = "representative";
                        jsonObject.put("usertype",usertype);
                    }
                    else if (usertype.equalsIgnoreCase("user")){

                        // usertype = "user";
                        jsonObject.put("usertype",usertype);
                    }
                    else if (usertype.equalsIgnoreCase("mla")){

                        // usertype = "user";
                        jsonObject.put("usertype",usertype);
                    }
                    jsonObject.put("userid",userId);
                    jsonObject.put("discussionid",discussionid);
                    jsonObject.put("commenttext",commentNow);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                new HttpRequestTask1(jsonObject.toString(),selectedposition).execute();
                Log.i("Viewpager selected", String.valueOf(selectedposition));


            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = new NewDiscussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }


            }

        });

      /*  if(usertype.equalsIgnoreCase("mla")){
            new HttpRequestTaskreq(mlaid,constituencyId,usertype).execute();
        }else {
            new HttpRequestTaskreq(representativeid, constituencyId,usertype).execute();
        }*/


        textGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
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
        new HttpRequestTaskreq(representativeid, constituencyId,usertype).execute();

        return rootview;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //add the current discussion flag in shared pref
        editor.putInt("discussionfragflag",0);

    }

    @Override
    public void onRefresh() {
        int selectedposition=viewPager.getCurrentItem();

            new HttpRequestTaskreq(selectedposition,representativeid,constituencyId,usertype).execute();


        //new HttpRequestTaskreq(selectedposition).execute();
        mSwipeRefreshLayout.setRefreshing(false);

    }


    private class HttpRequestTaskreq extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {

        Dialog progressDialog;

        int selectedposition=0;
        private boolean isCanceled;
        String json;

        int representativeid=0, constituencyId=0;
        String usertype="";
    /*    public HttpRequestTaskreq(int selectedposition) {
            this.selectedposition=selectedposition;

        }*/

     /*   public HttpRequestTaskreq(String s) {
            this.json = s;
        }*/

        public HttpRequestTaskreq(int representativeid, int constituencyId,String usertype) {
            this.representativeid = representativeid;
            this.constituencyId = constituencyId;
            this.usertype = usertype;
        }

        public HttpRequestTaskreq(int selectedposition, int representativeid, int constituencyId,String usertype) {
            this.selectedposition=selectedposition;
            this.representativeid = representativeid;
            this.constituencyId = constituencyId;
            this.usertype = usertype;

        }

        @Override
        protected void onPreExecute() {
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
            progressDialog.setButton(BUTTON_NEGATIVE, getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener(){
                // Set a click listener for progress dialog cancel button
                @Override
                public void onClick(DialogInterface dialog, int which){
                    // dismiss the progress dialog
                    progressDialog.dismiss();
                    // Tell the system about cancellation
                    isCanceled = true;
                }
            });
            progressDialog.show();
*/
        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {


            try {
                //final String url2 = config.getGetDiscussionDetails()+"/"+representativeid+"/"+constituencyId+"/"+usertype;
                final String url2 = config.representativeremoteurl+"admin/app/getDiscussionDetails"+"/"+representativeid+"/"+constituencyId+"/"+usertype;


                Log.i("GetDiscussionDetails", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);

            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {

            if(myPojo!=null){
                myPojoExtra=myPojo;


                Log.i("output in ongoing", myPojo.size() + "");
                Log.d("getNewGrievanceDetails", String.valueOf(myPojo));
                //   adapter = new ViewPagerAdapter(myPojo,Discussion.this);


                if (myPojo.size()==0){
                    textView.setVisibility(View.VISIBLE);
                    comment.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);


                }
                else {
                    textView.setVisibility(View.GONE);
                }



                adapter=new ViewPagerAdapter(getActivity(),myPojo);

                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(selectedposition);
                viewPager.setFocusable(false);

                mSwipeRefreshLayout.setOnRefreshListener(Discussion.this);


            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }

            progressDialog.dismiss();

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
            //    String url = config.getInsertDiscussionDetails();
                String url = config.representativeremoteurl+"admin/app/insertDiscussionDetails";

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
            // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
            mSwipeRefreshLayout.setOnRefreshListener(Discussion.this);
            progressDialog.dismiss();
            if (result.equalsIgnoreCase("Success")) {
/*
                if(usertype.equalsIgnoreCase("mla")){
                    new HttpRequestTaskreq(selectedposition, mlaid, constituencyId,usertype).execute();


                }else {
                    new HttpRequestTaskreq(selectedposition, representativeid, constituencyId,usertype).execute();

                }*/
                new HttpRequestTaskreq(selectedposition, representativeid, constituencyId,usertype).execute();

                comment.setText("");
            }
            else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(getResources().getString(R.string.no_internet));
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }

    }





    public class AddNewDiscussion extends AsyncTask<String, Void, String> //Using POST Method, send newly entered comment
    {
        Dialog progressDialog;

        String jsonstr;
        int selectedposition=0;

        public AddNewDiscussion(String jsonstr,int selectedposition) {
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
               // String url = config.getCreateNewRepresentativeDiscussion();
                String url = config.representativeremoteurl+"admin/app/createNewRepresentativeDiscussion/";

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
            // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
            mSwipeRefreshLayout.setOnRefreshListener(Discussion.this);
            progressDialog.dismiss();
            if (result.equalsIgnoreCase("Success")) {

                    new HttpRequestTaskreq(representativeid,constituencyId,usertype).execute();

                //new HttpRequestTaskreq(selectedposition).execute();
                comment.setText("");
            }
            else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(getResources().getString(R.string.no_internet));
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }

        }

    }


}