package cssl.dialstar.user_fragment;

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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cssl.dialstar.R;
import cssl.dialstar.user_activity.ImageModel;
import cssl.dialstar.user_adapter.AddAdapter;
import cssl.dialstar.user_adapter.SlidingImage_Adapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GrievanceDetails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    //static ArrayList<String> ARG_PARAM4;
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
    private static final String ARG_PARAM17 = "param17";
    private static final String ARG_PARAM18 = "param18";
    private static final String ARG_PARAM19 = "param19";
    int categoryid=0,partyid=0,representativeid=0,grievanceid=0,flag=1;
    int acknowledge=0,closed=0, typeid=0;
    String closedate = "",acknowledgedate="",electived="",createddate="";
    double latitude=0.0,longitude=0.0;
    TextView txtcategory,txtsubjectname,txtsubjectdetails,
            txtrepresentativename,txtpartyname,txtback,
            txtAcknowledgedate,txtClosedate;
    LinearLayout ackll,closell;


    Button btnedit,btnreminder;
    ConfigUser config;

    RadioButton elective;
    Spinner sppoliticalparty,sprepresent;
    TextView txtpolticalparty;

    ArrayList<Dialstar> mypojo1;
    ArrayList<Dialstar> mypojo2;
    AddAdapter customAdapter;

    SharedPreferences share;


    String username="";

    RadioButton polticalparty;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String category="",grievancename="",description="",representativename="",address="",partyname="";
    String files ;
    Fragment fragment;
    ArrayList<String>imagelist;
    ArrayList<ImageModel> imageModelArrayList;
    private ViewPager viewPager1;
    private OnFragmentInteractionListener mListener;

    int currentPage = 0;
    int NUM_PAGES = 0;
    public static boolean flag1=false;
    public GrievanceDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GrievanceDetails newInstance(String param1, String param2,
                                               String param3,ArrayList<String> param4,
                                               String param5,String param6,String param7,
                                               int param8,int param9,int param10,
                                               int param11,int param12,int param13,
                                               String param14,String param15,String param16,int param18,String param19) {
        GrievanceDetails fragment = new GrievanceDetails();
        Bundle args = new Bundle();
        flag1=false;
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putStringArrayList(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putInt(ARG_PARAM8, param8);
        args.putInt(ARG_PARAM9, param9);
        args.putInt(ARG_PARAM10, param10);
        args.putInt(ARG_PARAM11, param11);
        args.putInt(ARG_PARAM12, param12);
        args.putInt(ARG_PARAM13, param13);
        args.putString(ARG_PARAM14, param14);
        args.putString(ARG_PARAM15, param15);
        args.putString(ARG_PARAM17, param16);
        args.putInt(ARG_PARAM18, param18);
        args.putString(ARG_PARAM19, param19);


        fragment.setArguments(args);
        return fragment;
    }

    public static GrievanceDetails newInstance(int param16) {

        GrievanceDetails fragment = new GrievanceDetails();
        Bundle args = new Bundle();
        flag1=true;

        args.putInt(ARG_PARAM16, param16);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        share= PreferenceManager.getDefaultSharedPreferences(getContext());

        username = share.getString("name","");
        if (getArguments() != null) {
            if(flag1==true){

                //grievance id get from arguments
                grievanceid = getArguments().getInt(ARG_PARAM16);

                //call webservice to get all details from grievance id


            }else {
                //category,grievancename,description,files,acknowledgedby
                category = getArguments().getString(ARG_PARAM1);
                grievancename = getArguments().getString(ARG_PARAM2);
                description = getArguments().getString(ARG_PARAM3);

                imagelist = getArguments().getStringArrayList(ARG_PARAM4);
                representativename = getArguments().getString(ARG_PARAM5);
                partyname = getArguments().getString(ARG_PARAM6);
                address = getArguments().getString(ARG_PARAM7);
                categoryid = getArguments().getInt(ARG_PARAM8);
                partyid = getArguments().getInt(ARG_PARAM9);
                representativeid = getArguments().getInt(ARG_PARAM10);
                grievanceid = getArguments().getInt(ARG_PARAM11);
                acknowledge = getArguments().getInt(ARG_PARAM12);
                closed = getArguments().getInt(ARG_PARAM13);
                acknowledgedate = getArguments().getString(ARG_PARAM14);
                closedate = getArguments().getString(ARG_PARAM15);
                electived = getArguments().getString(ARG_PARAM17);
                typeid = getArguments().getInt(ARG_PARAM18);
                createddate = getArguments().getString(ARG_PARAM19);

                config = new ConfigUser();
//            Log.e("acknowledgedate",acknowledgedate);
                //  Log.e("closedate",closedate);
            }
        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_grievance_details, container, false);

        txtcategory = view.findViewById(R.id.txtcategoryname);
        txtsubjectname = view.findViewById(R.id.txtsubject);
        txtsubjectdetails = view.findViewById(R.id.txtsubjectdetails);
        txtrepresentativename = view.findViewById(R.id.txtrepresentativename);
        txtpartyname = view.findViewById(R.id.txtpartyname);
        txtback = view.findViewById(R.id.back);
        txtAcknowledgedate = view.findViewById(R.id.txtAcknowledgedate);
        txtClosedate = view.findViewById(R.id.txtClosedate);
        btnedit = view.findViewById(R.id.btnedit);
        btnreminder = view.findViewById(R.id.btnreminder);
        ackll=view.findViewById(R.id.ackll);
        closell=view.findViewById(R.id.closell);




        viewPager1 = (ViewPager) view.findViewById(R.id.viewPager1);
        final View alertLayout = inflater.inflate(R.layout.resend, null);
        polticalparty=(RadioButton)  alertLayout.findViewById(R.id.politicalparty);;
        elective=(RadioButton) alertLayout.findViewById(R.id.elective);
        sppoliticalparty=(Spinner) alertLayout.findViewById(R.id.sppoliticalparty);;
        sprepresent=(Spinner) alertLayout.findViewById(R.id.sprepresent);
        txtpolticalparty=(TextView) alertLayout.findViewById(R.id.txtpolticalparty);

        if(flag1==true){
            new HttpRequestGrievanceDetails(grievanceid).execute();
        }

        if (flag1==false){
            setData();

            if(acknowledge==0){

                new HttpRequestGrievanceDuration(categoryid,"new").execute();
            }else if(acknowledge==1 && closed==0){

                new HttpRequestGrievanceDuration(categoryid,"acknowledged").execute();
            }

        }


        if(acknowledge==1 && closed==1){
            ackll.setVisibility(View.VISIBLE);
            closell.setVisibility(View.VISIBLE);

            txtAcknowledgedate.setVisibility(View.VISIBLE);
            txtAcknowledgedate.setText(acknowledgedate);
            txtClosedate.setVisibility(View.VISIBLE);
            txtClosedate.setText(closedate);
        }else if (acknowledge==1){
           ackll.setVisibility(View.VISIBLE);
           txtAcknowledgedate.setVisibility(View.VISIBLE);
           txtAcknowledgedate.setText(acknowledgedate);
       }






        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag1==true){
                    fragment = new Add();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.mainFrame, fragment);
                        fragmentTransaction.commit();

                    }

                }else {
                    fragment = new NewGrievance();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.mainFrame, fragment);
                        fragmentTransaction.commit();

                    }

                }

            }
        });



        return  view;
    }


    public void setData(){


        txtcategory.setText(category);
        txtsubjectname.setText(grievancename);
        txtsubjectdetails.setText(description);
        txtrepresentativename.setText(representativename+",");

        if(partyid==0){
            txtpartyname.setText("Other"+", "+address);
        }else {
            txtpartyname.setText(partyname+", "+address);
        }





        imageModelArrayList = new ArrayList<>();
        ArrayList<ImageModel> list = new ArrayList<>();
        imageModelArrayList = list;
        for (int i = 0;i<imagelist.size();i++){

            ImageModel imageModel = new ImageModel();
            imageModel.setImage(imagelist.get(i));
            imageModelArrayList.add(imageModel);
          //  Log.d("imageMOdel",imageModel+"");

        }
        Log.d("imageModelArrayList",imageModelArrayList+"");



        viewPager1.setAdapter(new SlidingImage_Adapter(getActivity(),imageModelArrayList));


       // indicator.setViewPager(viewPager1);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        //indicator.setRadius(5 * density);

        NUM_PAGES =imageModelArrayList.size();


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager1.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2000, 2000);



        if(acknowledge==1 || closed==1){
            btnedit.setVisibility(View.GONE);

        }
        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fragment = Add.newInstance(category,grievancename,description,imagelist,representativename,partyname,address,categoryid,partyid,representativeid,grievanceid,flag,electived,typeid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();

                }
            }
        });


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





    private class HttpRequestGrievanceDetails extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


        Dialog progressDialog;
        int grievanceid;

        public HttpRequestGrievanceDetails(int grievanceid) {
            this.grievanceid = grievanceid;
        }





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
                // String url=config.getGetUserNewGrievanceDetails()+userid;
                //     String url=config.getGetAllGrievanceDetailsForUser()+userid;
                String url=config.userremoteurl+"admin/app/getAllGrievanceDetailsByGrievanceId/"+grievanceid;

                Log.d("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            Log.d("result of grievance",myPojo+"");



            if(myPojo.size()>0) {
                category = myPojo.get(0).getCategoryname();
                grievancename = myPojo.get(0).getGrievancename();
                description= myPojo.get(0).getDescription();

                imagelist = new ArrayList<>();
                imagelist.clear();
                String file = myPojo.get(0).getFile();
                String file1="";
                try {
                    JSONObject jsonObject = new JSONObject(file);

                    int sizeobj=jsonObject.length();
                    for (int i=0;i<sizeobj;i++)
                    {
                        file1=jsonObject.getString("file"+(i+1));
                        imagelist.add(file1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                representativename= myPojo.get(0).getRepresentativename();
                partyname= myPojo.get(0).getPartyname();
                address= myPojo.get(0).getAddress();
                categoryid= myPojo.get(0).getCategoryid();
                partyid= myPojo.get(0).getPartyid();
                representativeid= myPojo.get(0).getRepresentativeid();
                grievanceid= myPojo.get(0).getGrievanceid();
                acknowledge= myPojo.get(0).getIsacknowledged();
                closed= myPojo.get(0).getIsclosed();
                acknowledgedate= myPojo.get(0).getAcknowledgedate();
                closedate= myPojo.get(0).getCloseddate();

                setData();

            }
            else{

            }
            progressDialog.dismiss();

        }
    }




    private class HttpRequestGrievanceDuration extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


        Dialog progressDialog;
        int categoryid;
        String type;

        public HttpRequestGrievanceDuration(int categoryid,String type) {
            this.categoryid = categoryid;
            this.type = type;
        }





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
                // String url=config.getGetUserNewGrievanceDetails()+userid;
                //     String url=config.getGetAllGrievanceDetailsForUser()+userid;
                String url=config.userremoteurl+"admin/app/sendGrievanceDuration/"+categoryid+"/"+type;

               // String url="http://192.168.0.126:8099/admin/app/sendGrievanceDuration/"+categoryid+"/"+type;

                //String url=config.userremoteurl+"admin/app/getUserTypeMaster";
                Log.e("GrievanceDuration url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar forNow2 = restTemplate.getForObject(url,Dialstar.class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);


            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){


                Log.e("resultGrievanceDuration",myPojo+"");
                for (int i=0;i<myPojo.size();i++){

                    int remiderday =0;
                    if(myPojo.get(i).getOption1()!=null && !myPojo.get(i).getOption1().equalsIgnoreCase("")){
                        remiderday = Integer.parseInt(myPojo.get(i).getOption1());
                    }

                        Log.e("remider day",remiderday+"");

                        if(acknowledge==1&&closed==0){

                            Date todaysdate = new Date();
                            Date acknowledgedated;
                            SimpleDateFormat format = new SimpleDateFormat("MMM dd,yyyy");

                            try {
                                acknowledgedated = format.parse(acknowledgedate);//converting given string to date,parsing
                                Calendar cal = Calendar.getInstance();
                                cal.setTime( acknowledgedated);
                                cal.add(Calendar.DATE, remiderday); // add 10 days
                                Date reminderdare = cal.getTime();
                                if(todaysdate.after(reminderdare)){
                                    btnreminder.setVisibility(View.VISIBLE);

                                }else {
                                    btnreminder.setVisibility(View.GONE);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Date todaysdate = new Date();
                            Date createdate;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {
                                createdate = format.parse(createddate);//converting given string to date,parsing
                                Calendar cal = Calendar.getInstance();
                                cal.setTime( createdate);
                                cal.add(Calendar.DATE, remiderday); // add 5 days
                                Date reminderdare = cal.getTime();
                                if(todaysdate.after(reminderdare)){
                                    btnreminder.setVisibility(View.VISIBLE);

                                }else {
                                    btnreminder.setVisibility(View.GONE);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                        }



                    btnreminder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                    .setTitle("Alert")
                                    .setMessage(getString(R.string.reminder_grievance)+representativename+". ")
                                    .setCancelable(true)
                                    .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("grievancecategoryid",categoryid);
                                                jsonObject.put("grievanceid",grievanceid);
                                                jsonObject.put("representativeid",representativeid);
                                                jsonObject.put("username",username);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }



                                            new HttpRequestSendNotificationToRepresentative(String.valueOf(jsonObject)).execute();
                                        }
                                    })
                                    .show();

                        }
                    });


                }
            }else {

                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }



            progressDialog.dismiss();

        }
    }



    private class HttpRequestSendNotificationToRepresentative extends AsyncTask<Void, Void, String> {


        Dialog progressDialog;
        String json;

        public HttpRequestSendNotificationToRepresentative(String json) {
            this.json = json;
            Log.e("SendNotificationToRepresentative json",json);
        }


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
                // String url=config.getGetUserNewGrievanceDetails()+userid;
                //     String url=config.getGetAllGrievanceDetailsForUser()+userid;
                String url=config.userremoteurl+"admin/app/reSendNotificationToRepresentative/";


               // String url="http://192.168.0.126:8099/admin/app/reSendNotificationToRepresentative/";

                //String url=config.userremoteurl+"admin/app/getUserTypeMaster";
                Log.e("SendNotificationToRepresentative url", url);
                MediaType JSON = MediaType.parse("application/json");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, json);
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
                Log.e("MainActivity", e.getMessage(), e);


            }

            return null;
        }
        protected void onPostExecute(String  resp) {
            if(resp!=null){


                Log.e("resultGrievanceDuration",resp+"");
                if(resp.equalsIgnoreCase("Success")){
                    Toast.makeText(getContext(), getString(R.string.reminder_grievance_successfully), Toast.LENGTH_LONG).show();

                }

            }else {

                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }



            progressDialog.dismiss();

        }
    }


}
