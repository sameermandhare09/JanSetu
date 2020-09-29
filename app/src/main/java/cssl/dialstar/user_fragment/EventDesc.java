package cssl.dialstar.user_fragment;

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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.ConfigUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EventDesc extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM16 = "param16";

    Fragment fragment;
    ConfigUser config = new ConfigUser();
    TextView btnjoin;
    Button btnalreadyjoin;
    int userid=0;
    int eventid=0;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    //TODO: Rename and change types of parameters
    private String mParam1,mParam2,mParam3,mParam4,mParam5,mParam6;
    String name="default";
    String desc="default";
    String startDate="default";
    String endDate="default";
    String create="default";
    String on="default";
    public static boolean flag1=false;
    TextView textGoBack,textViewName,textViewStart,textViewEnd,textViewDesp,textViewCreate;
    public EventDesc()
    {
    }
    public static EventDesc newInstance(String param1, String param2,String param3,String param4,String param5,String param6,int param7) {
        EventDesc fragment = new EventDesc();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putInt(ARG_PARAM7, param7);
        flag1=false;

        fragment.setArguments(args);
        return fragment;
    }

    public static EventDesc newInstance(int param16) {

        EventDesc fragment = new EventDesc();
        Bundle args = new Bundle();
        flag1=true;

        args.putInt(ARG_PARAM16, param16);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (flag1 == true) {

                //event id get from arguments
                eventid = getArguments().getInt(ARG_PARAM16);

                //call webservice to get all details from event id


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("eventid",eventid);
                    Log.e("json ",jsonObject.toString());
                    new HttpRequestAllEventDetailsBYEventId(jsonObject.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            } else{
                name = getArguments().getString(ARG_PARAM1);
            desc = getArguments().getString(ARG_PARAM2);
            startDate = getArguments().getString(ARG_PARAM3);
            endDate = getArguments().getString(ARG_PARAM4);
            create = getArguments().getString(ARG_PARAM5);
            on = getArguments().getString(ARG_PARAM6);
            eventid = getArguments().getInt(ARG_PARAM7);
        }
           // Log.d("evet desc eventid", eventid+"");

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_event_desc, container, false);

        share= PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit=share.edit();
        userid=share.getInt("Userid",0);




         textGoBack=(TextView)view.findViewById(R.id.back);
         textViewName=(TextView)view.findViewById(R.id.EventName);
         textViewStart=(TextView)view.findViewById(R.id.Start);
         textViewEnd=(TextView)view.findViewById(R.id.end);
         textViewDesp=(TextView)view.findViewById(R.id.desp);
         textViewCreate=(TextView)view.findViewById(R.id.created);
       // Button btnjoin = (Button)view.findViewById(R.id.btnjoin);
        btnjoin = (TextView) view.findViewById(R.id.btnjoin);
        btnalreadyjoin = (Button)view.findViewById(R.id.btnalreadyjoin);

        if(flag1==true){
            //new HttpRequestEventDetails(eventid).execute();
        }

        if (flag1==false) {

            textViewDesp.setText(desc);
            textViewName.setText(name);
            textViewStart.setText(Html.fromHtml(startDate));
            textViewEnd.setText(Html.fromHtml(endDate));
            textViewCreate.setText(Html.fromHtml(getString(R.string.CREATED_BY)+" " + "<font color='black'><font size='16dp'>" + create + "</font>"));
        }
        textGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    fragment = new Events();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.mainFrame, fragment);
                        fragmentTransaction.commit();

                    }



            }
        });


        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity(), "userid: "+userid+" eventid: "+eventid, Toast.LENGTH_SHORT).show();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("userid",userid);
                    jsonObject.put("eventid",eventid);
                    new HttpRequestEventJoin(jsonObject.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }


    public class HttpRequestEventJoin extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;


        public HttpRequestEventJoin(String jsonstr) {
            this.jsonstr = jsonstr;

            Log.d("user login input json",jsonstr);
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
                //String url =config.getUserJoinEvent();
                String url =config.userremoteurl+"admin/app/userJoinEvent";

                //"http://182.18.163.201:8099/admin/app/user_login";
                Log.d("Event Join url",url);
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
                return  e.getMessage().toString();
                // Toast.makeText(User_login.this, "Please check Intenet connection", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Log.i("event join result:", result);
            if(result.equalsIgnoreCase("Success") ){
                Toast.makeText(getActivity(), getString(R.string.event_registered), Toast.LENGTH_SHORT).show();
                btnjoin.setVisibility(View.GONE);
            }else  if(result.equalsIgnoreCase("Already Exist") ){
                btnjoin.setVisibility(View.GONE);
                btnalreadyjoin.setVisibility(View.VISIBLE);
               // Toast.makeText(getActivity(), "Sorry you already registered this event", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), getString(R.string.network_problem), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public class HttpRequestAllEventDetailsBYEventId extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;


        public HttpRequestAllEventDetailsBYEventId(String jsonstr) {
            this.jsonstr = jsonstr;


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
                //String url =config.getUserJoinEvent();
                String url =config.userremoteurl+"admin/app/getAllEventDetailsBYEventId";

                //"http://182.18.163.201:8099/admin/app/user_login";
                Log.e("EventDetail EventId url",url);
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
                return  e.getMessage().toString();
                // Toast.makeText(User_login.this, "Please check Intenet connection", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            if(result!=null){

                Log.e("EventDetail result:", result);
                try {
                    JSONArray  jsonArray = new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        desc = jsonObject.getString("description");
                        name =  jsonObject.getString("eventname");
                        startDate =  jsonObject.getString("eventstartdate");
                        endDate =  jsonObject.getString("eventenddate");
                        create = jsonObject.getString("representativename");
                        Date date;


                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");

                        try {
                            date = format.parse(startDate);//converting given string to date,parsing
                            String dateTime = dateFormat.format(date); //date to new format and convert to String,formatting
                            Log.d("dateTime",dateTime);
                            startDate=dateTime;

                            date = format.parse(endDate);
                            dateTime = dateFormat.format(date);
                            Log.d("dateTime",dateTime);
                            endDate=dateTime;




                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }


                        textViewDesp.setText(desc);
                        textViewName.setText(name);
                        textViewStart.setText(Html.fromHtml(startDate));
                        textViewEnd.setText(Html.fromHtml(endDate));
                        textViewCreate.setText(Html.fromHtml(getString(R.string.CREATED_BY) + "<font color='black'><font size='16dp'>" + create + "</font>"));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }




        }
    }


}
