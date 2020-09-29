package cssl.dialstar.representative_fragment;

import android.app.Dialog;
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
import cssl.dialstar.representative_adapter.RepresentiveAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Representative extends Fragment {


    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager  layoutManager;
    RecyclerView recyclerView;
   // ArrayList<RepresentativeDataModel> representativeDataModels = new ArrayList<>();
    Config config = new Config();
    ArrayList<DialstarPojo> myPojo;

    Fragment fragment = new Fragment();
    SharedPreferences Mlapref;
    int mlaid=0,constituencyId=0;
    TextView textView;
    TextView txteventcount,txtpollcount,txtdiscussioncount,txtdashboard;
    RelativeLayout relativeevents,relativepoll,relativediscussion;


/*    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment fragment = new_compaint Fragment();*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_representative, container, false);

        recyclerView = layout.findViewById(R.id.representive_recycler_view);

        textView = layout.findViewById(R.id.txtview_data_not_rep);
        relativeevents = layout.findViewById(R.id.relativeevents);
        relativepoll =layout.findViewById(R.id.relativepolls);
        relativediscussion = layout.findViewById(R.id.relativediscussion);
        txteventcount = layout.findViewById(R.id.txtcountevents);
        txtpollcount = layout.findViewById(R.id.txtcountpoll);
        txtdiscussioncount = layout.findViewById(R.id.txtcountdiscussion);


        layoutManager  = new GridLayoutManager(getContext(),3);
        //layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,true);
        recyclerView.setLayoutManager(layoutManager);

        Mlapref = PreferenceManager.getDefaultSharedPreferences(getContext());
        mlaid = Mlapref.getInt("mlaid",mlaid);
        constituencyId = Mlapref.getInt("constituencyid",0);
        Log.d("mlaidInRepresentative", String.valueOf(mlaid));
        myPojo=new ArrayList<DialstarPojo>();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("representativeid",mlaid);
             jsonObject.put("usertype","mla");
            jsonObject.put("constituencyid",constituencyId);

            new HttpRequestgetcount(jsonObject.toString()).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        new HttpRequestTask().execute();

        relativeevents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Event();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }
        });

        relativepoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Polls();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }
        });

        relativediscussion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new Discussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }
        });


        return layout;


    }

    @Override
    public void onResume() {
        super.onResume();
        new HttpRequestTask().execute();
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //final String url3 =config.getRepresentative_issueview()+mlaid+" ";
                final String url3 =config.representativeremoteurl+"admin/app/representative_issueview/"+mlaid+" ";

                Log.i("getRepresentative url", url3);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

         /*       MyPojo[] forNow1 = restTemplate.getForObject(url2, MyPojo[].class);
                ArrayList<MyPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));*/

                DialstarPojo[] forNow3 = restTemplate.getForObject(url3,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow3));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {

            if(myPojo!=null){

                int size =myPojo.size();

                if(myPojo.size()==1){
                    textView.setVisibility(View.VISIBLE);

                }else{
                    //textView.setVisibility(View.GONE);
                    for(int i=0;i<myPojo.size();i++){
                        if(mlaid==myPojo.get(i).getRepresentativeid()){
                            myPojo.remove(i);

                        }
                    }
                    adapter = new RepresentiveAdapter(myPojo,getActivity());
                    recyclerView.setAdapter(adapter);

                }

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
        //ProgressDialog progressDialog;
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
            //Log.i("GetEventCount result:", result);

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
