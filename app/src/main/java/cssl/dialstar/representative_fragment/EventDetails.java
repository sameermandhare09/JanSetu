package cssl.dialstar.representative_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EventDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";
    private static final String ARG_PARAM11 = "param11";
    private static final String ARG_PARAM12 = "param12";
    private static final String ARG_PARAM13 = "param13";
    private static final String ARG_PARAM14 = "param14";
    private static final String ARG_PARAM15 = "param15";
    private static final String ARG_PARAM16 = "param16";



    Fragment fragment;
    Config config = new Config();
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;


    int eventid=0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String name="default";
    String desc="default";
    String startDate="default";
    String endDate="default";
    String create="default";
    String on="default";
    String usertype="",usertype1="";
    Button btnEdit;
    Button btnDelete;
    int userid=0;
    int adminid=0,eventcount=0;
    int userid1=0;
    int eventstateid=0;
    int eventconsid=0;
    String eventloksabhaconstituency="";
    int mlaid, representativeid;
    LinearLayout lnrbuttons;

    TextView textGoBack,textViewName,textViewStart,
            textViewEnd,textViewDesp,textViewCreate,txteventcount;

    public static boolean flag1=false;
    private OnFragmentInteractionListener mListener;

    public EventDetails() {
        // Required empty public constructor
    }
 public static EventDetails newInstance(String param1, String param2, String param3,
                                        String param4, String param5, String param6,
                                        int param7, int param8, int param9, int param10,
                                        String usertype, int representativeid, int adminid,
                                        int eventcount,String eventloksabhaconstituency) {
        EventDetails fragment = new EventDetails();
        Bundle args = new Bundle();
     args.putString(ARG_PARAM1, param1);
     args.putString(ARG_PARAM2, param2);
     args.putString(ARG_PARAM3, param3);
     args.putString(ARG_PARAM4, param4);
     args.putString(ARG_PARAM5, param5);
     args.putString(ARG_PARAM6, param6);
     args.putInt(ARG_PARAM7, param7);
     args.putInt(ARG_PARAM8, param8);
     args.putInt(ARG_PARAM9, param9);
     args.putInt(ARG_PARAM10, param10);
     args.putString(ARG_PARAM11, usertype);
     args.putInt(ARG_PARAM12,representativeid);
     args.putInt(ARG_PARAM13,adminid);
     args.putInt(ARG_PARAM14, eventcount);
     args.putString(ARG_PARAM15, eventloksabhaconstituency);
     flag1=false;

        fragment.setArguments(args);
        return fragment;
    }

    public static EventDetails newInstance(int param16) {

        EventDetails fragment = new EventDetails();
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



            }else{
                name = getArguments().getString(ARG_PARAM1);
                desc = getArguments().getString(ARG_PARAM2);
                startDate = getArguments().getString(ARG_PARAM3);
                endDate = getArguments().getString(ARG_PARAM4);
                create = getArguments().getString(ARG_PARAM5);
                on = getArguments().getString(ARG_PARAM6);
                eventid = getArguments().getInt(ARG_PARAM7);
                userid =  getArguments().getInt(ARG_PARAM8);
                eventstateid =  getArguments().getInt(ARG_PARAM9);
                eventconsid =  getArguments().getInt(ARG_PARAM10);
                usertype1 = getArguments().getString(ARG_PARAM11);
                userid1=getArguments().getInt(ARG_PARAM12);
                adminid=getArguments().getInt(ARG_PARAM13);
                eventcount=getArguments().getInt(ARG_PARAM14);
                eventloksabhaconstituency = getArguments().getString(ARG_PARAM15);

            }

            mlaPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
            editor = mlaPref.edit();
          //  usertype = mlaPref.getString("usertype", "");
            representativeid = mlaPref.getInt("representativeid", representativeid);
            mlaid = mlaPref.getInt("mlaid", mlaid);

            Log.d("Event detail in eventid",eventid+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        usertype =  usertype = mlaPref.getString("usertype", "");
         textGoBack=(TextView)view.findViewById(R.id.back);
         textViewName=(TextView)view.findViewById(R.id.EventName);
         textViewStart=(TextView)view.findViewById(R.id.Start);
         textViewEnd=(TextView)view.findViewById(R.id.end);
         textViewDesp=(TextView)view.findViewById(R.id.desp);
         textViewCreate=(TextView)view.findViewById(R.id.created);
         txteventcount=(TextView)view.findViewById(R.id.txteventcount);

        lnrbuttons = view.findViewById(R.id.lnrbuttons);

        btnDelete = view.findViewById(R.id.btneventdelete);
        btnEdit = view.findViewById(R.id.btneventedit);
        textViewDesp.setText(desc);
        textViewName.setText(name);
        textViewStart.setText(Html.fromHtml(startDate));
        textViewEnd.setText(Html.fromHtml(endDate));
        txteventcount.setText(Html.fromHtml(getResources().getString(R.string.Interested_citizen_for_this_event)+eventcount));

        if(!usertype.equalsIgnoreCase(usertype1)){
            lnrbuttons.setVisibility(View.GONE);

        }
        else if(userid1!=representativeid)
        {
            lnrbuttons.setVisibility(View.GONE);

        }/*else if(userid1!=mlaid)
        {
            lnrbuttons.setVisibility(View.GONE);

        }*/
     /*   else if(mlaid!=adminid)
        {
            lnrbuttons.setVisibility(View.GONE);
        }
        else if(mlaid==adminid)
        {
            lnrbuttons.setVisibility(View.VISIBLE);
        }*/

        textGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                   // Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    FragmentManager fragmentManager = getFragmentManager();

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }



                    }
                   // Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.Are_you_want_to_delete_this_Event))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                new HttpRequestDeleteGrievance().execute();

                                dialog.cancel();

                            }
                        })

                        .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }
                        }).show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String eventname="",eventstartdate="",eventenddate="",eventpublish="";
                String description="";


                eventname = name ;
                description =desc ;
                eventstartdate = startDate ;
                eventenddate = endDate;
                create = getArguments().getString(ARG_PARAM5);
                on = getArguments().getString(ARG_PARAM6);



                int editevent = 1,flag=0;
                fragment =AddEvent.newInstance(flag,editevent,eventname,eventstateid,
                        eventconsid,eventstartdate,eventenddate,description,eventid,userid,eventloksabhaconstituency);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private class HttpRequestDeleteGrievance extends AsyncTask<Void, Void, String> {


        Config config = new Config();
        Dialog progressDialog;
        int position=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                //String url=config.getDeleteUserEventDetails()+eventid;
                String url=config.representativeremoteurl+"admin/app/deleteUserEventDetails/"+eventid;


                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                //Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                // ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                String result = restTemplate.getForObject(url,String.class);

                return result;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return "0";
        }
        @Override
        protected void onPostExecute(String result) {
            Log.d("Delete event",result);

            progressDialog.dismiss();
            int isdelete=0;
            isdelete=Integer.parseInt(result);


            if(isdelete >0){
                Toast.makeText(getContext(), getResources().getString(R.string.Event_Successfully_Deleted), Toast.LENGTH_SHORT).show();

         /*       dataSet.remove(position);
                notifyDataSetChanged();*/

                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                   // Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    //Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }

            }else{
                Toast.makeText(getContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

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
                String url =config.representativeremoteurl+"admin/app/getAllEventDetailsBYEventId";

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
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        desc = jsonObject.getString("description");
                        name =  jsonObject.getString("eventname");
                        startDate =  jsonObject.getString("eventstartdate");
                        endDate =  jsonObject.getString("eventenddate");
                        create = jsonObject.getString("representativename");
                        eventcount = jsonObject.getInt("count");

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
                        txteventcount.setText(Html.fromHtml(getResources().getString(R.string.Interested_citizen_for_this_event)+eventcount));
                        //textViewCreate.setText(Html.fromHtml("CREATED BY: " + "<font color='black'><font size='16dp'>" + create + "</font>"));


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }




        }
    }



}
