package cssl.dialstar.representative_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.IssueViewAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class IssueView extends Fragment {

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager  layoutManager;
    RecyclerView recyclerView;
    //ArrayList<IssueDataModel> issueDataModels = new ArrayList<>();
    Config config = new Config();
    ArrayList<DialstarPojo> myPojo;
    SharedPreferences mlaPref;
    SharedPreferences.Editor editor ;
    RelativeLayout relativeevents,relativepoll,relativediscussion;

    String usertype="";
    int mlaid;
    int userid=0,representativeid=0, constituencyId=0;
    TextView textView,txteventcount,txtpollcount,txtdiscussioncount,txtdashboard;
    LinearLayout linearLayout;
    Fragment fragment = new Fragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview;
        rootview = inflater.inflate(R.layout.fragment_issue_view, container, false);
        recyclerView = rootview.findViewById(R.id.my_recycler_view);
        //recyclerView.setHasFixedSize(true);
        linearLayout = rootview.findViewById(R.id.lnr_Issue);

        layoutManager = new GridLayoutManager(getContext(),3);

        recyclerView.setLayoutManager(layoutManager);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        mlaid = mlaPref.getInt("mlaid",mlaid);
        usertype=mlaPref.getString("usertype","");
        representativeid=mlaPref.getInt("representativeid",0);
        constituencyId = mlaPref.getInt("constituencyid",0);
        Log.d("mlaidInIssueView", String.valueOf(mlaid));

        textView = rootview.findViewById(R.id.txtview_data_not);
        txtdashboard = rootview.findViewById(R.id.txtdashboard);
        relativeevents = rootview.findViewById(R.id.relativeevents);
        relativepoll =rootview.findViewById(R.id.relativepolls);
        relativediscussion = rootview.findViewById(R.id.relativediscussion);
        txteventcount = rootview.findViewById(R.id.txtcountevents);
        txtpollcount = rootview.findViewById(R.id.txtcountpoll);
        txtdiscussioncount = rootview.findViewById(R.id.txtcountdiscussion);

        myPojo=new ArrayList<DialstarPojo>();

        if(usertype.equalsIgnoreCase("mla"))
        {
            userid=mlaid;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("representativeid",mlaid);
                jsonObject.put("usertype",usertype);
                jsonObject.put("constituencyid",constituencyId);

                new HttpRequestgetcount(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            txtdashboard.setVisibility(View.GONE);

        }

        else
        { //representative issue view url
            userid=representativeid;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("representativeid",representativeid);
                jsonObject.put("usertype",usertype);
                jsonObject.put("constituencyid",constituencyId);
                new HttpRequestgetcount(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
           // new HttpRequestRepresentative().execute();
        }

        new HttpRequestMla().execute();


        relativeevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usertype.equalsIgnoreCase("mla")){
                    fragment = new Event();
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
                    fragment = new Event();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                       fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }


            }
        });

        relativepoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(usertype.equalsIgnoreCase("mla")){
                    fragment = new Polls();
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
                    fragment = new Polls();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }

            }
        });

        relativediscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usertype.equalsIgnoreCase("mla")){
                    fragment = new Discussion();
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
                    fragment = new Discussion();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                         fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }

            }
        });



        //clear the fragment back stack
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //getActivity().getSupportFragmentManager().popBackStack();
        }

        return rootview;



    }

    private class HttpRequestMla extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {
        Dialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

          /*  progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);*/

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                 String url3="";
                   //url3=config.getMla_Issueview()+userid+"/"+usertype;

                url3=config.representativeremoteurl+"admin/app/mla_Issueview/"+userid+"/"+usertype;




                Log.i("url", url3);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                DialstarPojo[] forNow3 = restTemplate.getForObject(url3,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow3));

                return greeting1;
            } catch (Exception e) {
              //  Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {

            if(myPojo!=null){

                if(myPojo.size()==0){
                    textView.setVisibility(View.VISIBLE);

                }

                Log.d("Issue View Pojo", String.valueOf(myPojo));
                adapter = new IssueViewAdapter(myPojo,getActivity());
                recyclerView.setAdapter(adapter);

                // adapter.notifyDataSetChanged();

            }else {
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

    public class HttpRequestgetcount extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;
        String mobileno;
        String password;

        public HttpRequestgetcount(String jsonstr) {
            this.jsonstr = jsonstr;

            Log.d("Requestgetcount json",jsonstr);
        }


        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
          /*  progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);*/

        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST


               // String url =config.getGetEventPollsDiscussionCount();
                String url =config.representativeremoteurl+"admin/app/getEventPollsDiscussionCount";

                //"http://182.18.163.201:8099/admin/app/user_login";
                Log.d("GetEventPollsCount url",url);
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
                String resp = response.body().string().toString();


                return resp;
            } catch (Exception e) {
                return new String( e.getMessage());
                // Toast.makeText(User_login.this, "Please check Intenet connection", Toast.LENGTH_SHORT).show();

            }

        }


        @Override
        protected void onPostExecute(String result) {
            Log.i("GetEventCount result:", result);

            try {
               // JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = new JSONArray(result);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int eventcount = jsonObject.getInt("totaleventcount");
                    int pollcount = jsonObject.getInt("totalpollscount");
                    int discussioncount = jsonObject.getInt("totaldiscussioncount");

                    Log.d("eventcount",eventcount+"");
                    Log.d("pollcount",pollcount+"");
                    Log.d("discussioncount",discussioncount+"");
                    txteventcount.setText("("+eventcount+")");
                    txtpollcount.setText("("+pollcount+")");
                    txtdiscussioncount.setText("("+discussioncount+")");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();

        }


    }




}
