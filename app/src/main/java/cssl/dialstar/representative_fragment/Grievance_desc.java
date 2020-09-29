package cssl.dialstar.representative_fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.ImageAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.user_activity.ImageModel;
import cssl.dialstar.user_adapter.AddAdapter;
import cssl.dialstar.user_adapter.SlidingImage_Adapter;
import cssl.dialstar.user_utils.Dialstar;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.DialogInterface.BUTTON_NEGATIVE;


public class Grievance_desc extends Fragment {
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
    private static final String ARG_PARAM11= "param11";
    private static final String ARG_PARAM12= "param12";
    private static final String ARG_PARAM13 = "param13";
    private static final String ARG_PARAM14 = "param14";
    private static final String ARG_PARAM15= "param15";
    private static final String ARG_PARAM16= "param16";
    private static final String ARG_PARAM17= "param17";
    private static final String ARG_PARAM18= "param18";
    private static final String ARG_PARAM19= "param19";
    private static final String ARG_PARAM20= "param20";
    private static final String ARG_PARAM21= "param21";

    private String mParam1;
    private String mParam2;
    Config config = new Config();
    TextView txtback,txtcreateddate,txtusername,txtcitizenname,txtusermobile,txtuseremail,txtgrivanceaddress,txtusergender,txtuseraddress,txtsubject,txtdetails;
    String username = null,closedby="",closeddate="",description="",acknowledgedby="",acknwoldgeddate="",mobileno = null,emailid = null,address=null;
    String citizenAddress="",citizengender="",citizenimage="";
    int grievancecategoryid = 0;

    int representativeidfwrd=0,representativeid=0,representativeid1=0,mlaid=0;
    int fragId,grievanceid,position;
    String usertype="",representativename="",name="";
    int pos=0;
    Spinner spusertype,sprepresentative;
    SharedPreferences Mlapref;
    int isacknowledged=0,isclosed=0;
    int userid=0,userid1=0;
    String grievancename,category,createddate;
    ArrayList<String>imagelist;
    ArrayList<ImageModel> imageModelArrayList;
    private ViewPager viewPager1;
    int currentPage = 0;
    int NUM_PAGES = 0;
    Button button,btnforward,btnmailto;
    String type="";
    ArrayList<Dialstar> mypojo3;
    ArrayList<Dialstar> mypojo5;
    AddAdapter customAdapter;
    int typeid=0,constituencyid=0;
    TextInputLayout txtemail;
    EditText edemail;
    LinearLayout lnrcitizendetails,lnrcitizen,lnrname,lnremail,lnrgrievadd,lnr1,lnrgender,lnradd;
    RecyclerView imgerecycler;
    RecyclerView.LayoutManager layoutManager;

    Fragment fragment = new Fragment();

    ArrayList<String> files = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    public static boolean flag=false;
    TextView txtviewcomplaintacknowledge;
    TextView txtviewcomplaintacknowledgeby;
    TextView txtviewcomplaintclose;
    TextView txtviewcomplaintcloseby;
    TextView txtunderlineackby,txtunderlineackdate,txtunderlinecloseby,txtunderlineclosedate;
    LinearLayout linearLayoutacknowledg,linearLayoutclosed,linearLayoutacknowledgdate,linearLayoutcloseddate;


    public Grievance_desc() {
        // Required empty public constructor
    }

   public static Grievance_desc newInstance(String param1, String param2,String param3, ArrayList<String> param4,
                                            int param5, int param6,int param7, int param8,
                                            int param9, String param10,String param11, String param12,
                                            String param13, String param14,String param15, String param16,
                                            String param17,String param19,String param20,String param21
                                            ) {
        Grievance_desc fragment = new Grievance_desc();
        Bundle args = new Bundle();
       flag=false;
        args.putString(ARG_PARAM1, param1);
       args.putString(ARG_PARAM2, param2);
       args.putString(ARG_PARAM3, param3);
       args.putStringArrayList(ARG_PARAM4, param4);
       args.putInt(ARG_PARAM5, param5);
       args.putInt(ARG_PARAM6, param6);
       args.putInt(ARG_PARAM7, param7);
       args.putInt(ARG_PARAM8, param8);
       args.putInt(ARG_PARAM9, param9);
       args.putString(ARG_PARAM10, param10);
       args.putString(ARG_PARAM11, param11);
       args.putString(ARG_PARAM12, param12);
       args.putString(ARG_PARAM13, param13);
       args.putString(ARG_PARAM14, param14);
       args.putString(ARG_PARAM15, param15);
       args.putString(ARG_PARAM16, param16);
       args.putString(ARG_PARAM17, param17);
       args.putString(ARG_PARAM19, param19);
       args.putString(ARG_PARAM20, param20);
       args.putString(ARG_PARAM21, param21);

       fragment.setArguments(args);
        return fragment;
    }
    public static Grievance_desc newInstance(int param18) {
        Grievance_desc fragment = new Grievance_desc();
        Bundle args = new Bundle();
        flag=true;
        args.putInt(ARG_PARAM18, param18);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            if(flag==true)
            {

                //grievance id get from arguments
                grievanceid = getArguments().getInt(ARG_PARAM18);
                //fragId=1;
            //call webservice to get all details from grievance id
            }else {

                acknowledgedby = getArguments().getString(ARG_PARAM1);
                closedby = getArguments().getString(ARG_PARAM2);
                description = getArguments().getString(ARG_PARAM3);
                files = getArguments().getStringArrayList(ARG_PARAM4);
                fragId = getArguments().getInt(ARG_PARAM5);
                grievanceid = getArguments().getInt(ARG_PARAM6);
                grievancecategoryid = getArguments().getInt(ARG_PARAM7);
                representativeid1 = getArguments().getInt(ARG_PARAM8);
                position = getArguments().getInt(ARG_PARAM9);
                grievancename = getArguments().getString(ARG_PARAM10);
                createddate = getArguments().getString(ARG_PARAM11);
                acknwoldgeddate = getArguments().getString(ARG_PARAM12);
                closeddate = getArguments().getString(ARG_PARAM13);
                username = getArguments().getString(ARG_PARAM14);
                mobileno = getArguments().getString(ARG_PARAM15);
                emailid = getArguments().getString(ARG_PARAM16);
                address = getArguments().getString(ARG_PARAM17);

                citizenAddress =  getArguments().getString(ARG_PARAM19);
                citizengender = getArguments().getString(ARG_PARAM20);
                citizenimage =  getArguments().getString(ARG_PARAM21);
                imagelist = files;

            }



        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_grievance_desc, container, false);

        txtback = rootview.findViewById(R.id.txtback);
        txtcreateddate = rootview.findViewById(R.id.txtcreateddate);
        txtusername = rootview.findViewById(R.id.txtusername);
         txtsubject=rootview.findViewById(R.id.txtsubject);
        txtdetails=rootview.findViewById(R.id.txtsubjectdetails);
        button = (Button) rootview.findViewById(R.id.btn_acknowledge);
        btnforward= (Button) rootview.findViewById(R.id.btn_forward);
        btnmailto = (Button) rootview.findViewById(R.id.btn_mail_to);
        txtviewcomplaintacknowledge = rootview.findViewById(R.id.txtView_acknowledgedate);
        linearLayoutacknowledg = rootview.findViewById(R.id.lnr_acknowledgeby);
        txtviewcomplaintacknowledgeby=rootview.findViewById(R.id.txtView_acknowledgeby);
        linearLayoutclosed = rootview.findViewById(R.id.lnr_close);
        linearLayoutacknowledgdate =rootview.findViewById(R.id.lnr_acknowledgedate);
        linearLayoutcloseddate = rootview.findViewById(R.id.lnr_closedate);
       // lnrcitizendetails =  rootview.findViewById(R.id.lnrcitizendetails);
        lnrcitizen =  rootview.findViewById(R.id.lnrcitizen);

        imgerecycler =   rootview.findViewById(R.id.imgerecycler);
        layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        imgerecycler.setLayoutManager(layoutManager);

        txtviewcomplaintclose=rootview.findViewById(R.id.txtView_Closedate);
        txtviewcomplaintcloseby = rootview.findViewById(R.id.txtView_Closeby);

        txtunderlineackby=rootview.findViewById(R.id.txtunderlineackby);
        txtunderlineackdate=rootview.findViewById(R.id.txtunderlineackdate);
        txtunderlinecloseby=rootview.findViewById(R.id.txtunderlinecloseby);
        txtunderlineclosedate=rootview.findViewById(R.id.txtunderlineclosedate);
        viewPager1 = (ViewPager) rootview.findViewById(R.id.viewPager1);

        Mlapref = PreferenceManager.getDefaultSharedPreferences(getContext());
        mlaid = Mlapref.getInt("mlaid",0);
        usertype=Mlapref.getString("usertype","");
        representativename = Mlapref.getString("name","");
        representativeid = Mlapref.getInt("representativeid",0);
        constituencyid = Mlapref.getInt("constituencyid",0);

        lnrcitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // lnrcitizendetails.setVisibility(View.VISIBLE);

                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(getActivity());
                View sheetView = getActivity().getLayoutInflater().inflate(R.layout.usrinfobottomsheetdialog, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.setTitle(username);

                txtusermobile = sheetView.findViewById(R.id.txtusermobile);
                txtuseremail = sheetView.findViewById(R.id.txtuseremail);
                txtgrivanceaddress = sheetView.findViewById(R.id.txtgrivanceaddress);
                txtuseraddress = sheetView.findViewById(R.id.txtuseraddress);
                txtusergender = sheetView.findViewById(R.id.txtusergender);
                txtcitizenname = sheetView.findViewById(R.id.txtcitizenname);
                lnrname = sheetView.findViewById(R.id.lnrname);
                lnremail = sheetView.findViewById(R.id.lnremail);
                lnrgrievadd = sheetView.findViewById(R.id.lnrgrievadd);
                lnr1 = sheetView.findViewById(R.id.lnr1);
                lnrgender = sheetView.findViewById(R.id.lnrgender);
                lnradd = sheetView.findViewById(R.id.lnradd);

                CircleImageView citizenprofilepic = sheetView.findViewById(R.id.profilepic);
               // lnr1.setAlpha((float) 0.0);

                String imagepath = config.representativeremoteurl1+"BJPJanhit/uploads"+citizenimage;
               Log.e("citizenimage",imagepath);
                Picasso.with(getContext())

                        .load(imagepath)
                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                        .networkPolicy(NetworkPolicy.NO_CACHE)
                        .placeholder(R.drawable.noimage)
                        .into(citizenprofilepic);

                txtcitizenname.setText(username);
                if(mobileno!=null && !mobileno.isEmpty()){
                    txtusermobile.setText(mobileno);
                }else{
                    lnrname.setVisibility(View.GONE);
                }

                if(citizengender!=null && !citizengender.isEmpty()){
                  txtusergender.setText(citizengender);
                }else{
                    lnrgender.setVisibility(View.GONE);
                }
                if(citizenAddress!=null && !citizenAddress.isEmpty()){

                    txtuseraddress.setText(citizenAddress);
                }else{
                    lnradd.setVisibility(View.GONE);
                }

                if(emailid!=null && !emailid.isEmpty()){
                    txtuseremail.setText(emailid);
                }else{
                    lnremail.setVisibility(View.GONE);
                }

                if(address!=null && !address.isEmpty()){
                    txtgrivanceaddress.setText(address);

                }else{
                    lnrgrievadd.setVisibility(View.GONE);
                }



               // mBottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                mBottomSheetDialog.show();
            }
        });

        //get the forwarded list
        forardedList();

        if(flag==true){
            new HttpRequestGrievanceDetails(grievanceid).execute();

        }
        if (flag==false){
            if(fragId == 1){


                button.setText(getResources().getString(R.string.Acknowledge));
                type = "New";
                Drawable icon = getContext().getDrawable(R.drawable.acknowlged);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);

            }
            else if(fragId == 2)
            {

                button.setText(getResources().getString(R.string.Close));
                type = "OnGoing";
                button.setPadding(15,0,15,0);
                Drawable icon = getContext().getDrawable(R.drawable.closed);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);

            }
            else if(fragId == 3)
            {
                button.setVisibility(View.INVISIBLE);
            }

            setdata();
        }




        userid1 = representativeid;




        userid=representativeid;
        if(userid1==representativeid1){
            if(fragId == 1){

                button.setVisibility(View.VISIBLE);
                btnforward.setVisibility(View.VISIBLE);
                button.setText(getResources().getString(R.string.Acknowledge));
                type = "New";
                Drawable icon = getContext().getDrawable(R.drawable.acknowlged);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);


            }
            else if(fragId == 2)
            {

                button.setVisibility(View.VISIBLE);
                btnforward.setVisibility(View.VISIBLE);
                button.setText(getResources().getString(R.string.Close));
                type = "OnGoing";
                button.setPadding(15,0,15,0);
                Drawable icon = getContext().getDrawable(R.drawable.closed);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);

            }
            else if(fragId == 3)
            {
                button.setVisibility(View.INVISIBLE);
                btnforward.setVisibility(View.INVISIBLE);
            }

        }else{
            button.setVisibility(View.INVISIBLE);
            btnforward.setVisibility(View.INVISIBLE);
        }


        btnforward.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


                LayoutInflater inflater = getLayoutInflater();//
                View alertLayout = inflater.inflate(R.layout.forwardalertdialog, null);
                 spusertype = (Spinner)alertLayout.findViewById(R.id.spusertype);
                 sprepresentative = (Spinner)alertLayout.findViewById(R.id.sprepresentative);
                 Button btncancel = (Button)alertLayout.findViewById(R.id.btncancel);
                 Button btnforward = (Button)alertLayout.findViewById(R.id.btnforward);

                 new HttpRequestMasterUser().execute();

                final Dialog dialog = new Dialog(getContext(), R.style.Theme_Dialog);
                getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

                dialog.setContentView(alertLayout);
                dialog.setTitle(getResources().getString(R.string.Forward_This_Grievance_To));
                dialog.setCancelable(false);



                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });
                btnforward.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(typeid==0){
                            Toast.makeText(getContext(), getResources().getString(R.string.Please_select_user_type), Toast.LENGTH_SHORT).show();

                        }else if(representativeidfwrd==0){
                            Toast.makeText(getContext(), getResources().getString(R.string.Please_select_sender_representative), Toast.LENGTH_SHORT).show();

                        }else {


                            dialog.dismiss();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("grievanceid",grievanceid);
                                jsonObject.put("mlaid",representativeid);
                                jsonObject.put("representativename",representativename);
                                jsonObject.put("representativeid",representativeidfwrd);
                                jsonObject.put("newtext","");
                                jsonObject.put("categoryid",grievancecategoryid);
                                new HttpRequestForward(String.valueOf(jsonObject)).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                });

                dialog.create();
                dialog.show();

            }

        });

        btnmailto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();//
                View alertLayout = inflater.inflate(R.layout.mailtoalertdialog, null);
                 txtemail = (TextInputLayout)alertLayout.findViewById(R.id.txtemail);
                 edemail = (EditText)alertLayout.findViewById(R.id.edemail);
                 Button btnsend = (Button)alertLayout.findViewById(R.id.btnsend);
                Button btncancel = (Button)alertLayout.findViewById(R.id.btncancel);


                final Dialog dialog = new Dialog(getContext(), R.style.Theme_Dialog);
                getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

                dialog.setContentView(alertLayout);

                dialog.setCancelable(false);


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnsend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (!edemail.getText().toString().trim().matches(emailPattern)  ) {
                            txtemail.setError(getResources().getString(R.string.Please_enter_valid_email));

                        }
                       else {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("grievanceid",grievanceid);
                                jsonObject.put("representativeid",representativeid);
                                jsonObject.put("option1",edemail.getText().toString().trim());

                                new HttpTaskSendmail(String.valueOf(jsonObject)).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            dialog.dismiss();
                        }

                    }
                });

                dialog.show();



            }

        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json;
                json = new JSONObject();

                try {
                    json.put("grievanceid",grievanceid);
                    json.put("userid",userid);
                    json.put("usertype",usertype);
                    json.put("type",type);
                    Log.d("json", String.valueOf(json));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("updateGrievance json",json.toString());
                new HttpRequestTask(json.toString()).execute();

            }
        });


        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();

                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")) {
                        fragment = new Dashboard();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }

                }
            }
        });

        return rootview;
    }

    private void forardedList() {
        JSONObject jsonObject =new JSONObject();

        try {
            jsonObject.put("representativeid",representativeid);
            jsonObject.put("grievanceid",grievanceid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new HttpRequestForwardedList(jsonObject.toString()).execute();

    }

    private boolean validateemail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!edemail.getText().toString().trim().matches(emailPattern)  ) {
            txtemail.setError(getString(R.string.Please_enter_valid_email));
            return false;

        }
        else {
            txtemail.setErrorEnabled(false);
        }

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setdata(){

        Date date,date1,date2;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            date = format.parse(createddate);
            createddate = dateFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }



        if( acknwoldgeddate != null && !acknwoldgeddate.equalsIgnoreCase("")){//!String.valueOf(acknwoldgeddate).equalsIgnoreCase("null")
            try {
                date1 = format.parse(acknwoldgeddate);
                acknwoldgeddate = dateFormat.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            linearLayoutacknowledg.setVisibility(View.VISIBLE);
            linearLayoutacknowledgdate.setVisibility(View.VISIBLE);
            txtunderlineackby.setVisibility(View.VISIBLE);
            txtunderlineackdate.setVisibility(View.VISIBLE);


        }else{
            linearLayoutacknowledg.setVisibility(View.GONE);
            linearLayoutacknowledgdate.setVisibility(View.GONE);
            txtunderlineackby.setVisibility(View.GONE);
            txtunderlineackdate.setVisibility(View.GONE);
        }
        if(closeddate != null && !closeddate.equalsIgnoreCase("")){
            try {
                date2 = format.parse(closeddate);
                closeddate = dateFormat.format(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            linearLayoutclosed.setVisibility(View.VISIBLE);
            linearLayoutcloseddate.setVisibility(View.VISIBLE);
            txtunderlinecloseby.setVisibility(View.VISIBLE);
            txtunderlineclosedate.setVisibility(View.VISIBLE);

        }else{

            linearLayoutclosed.setVisibility(View.GONE);
            linearLayoutcloseddate.setVisibility(View.GONE);
            txtunderlinecloseby.setVisibility(View.GONE);
            txtunderlineclosedate.setVisibility(View.GONE);

        }



        SpannableString content = new SpannableString(username);
        content.setSpan(new UnderlineSpan(), 0, username.length(), 0);

        txtcreateddate.setText(getResources().getString(R.string.POSTED_ON)+createddate);
        txtusername.setText(content);

        txtsubject.setText(grievancename);
        txtdetails.setText(description);

            txtviewcomplaintacknowledgeby.setText(acknowledgedby);
            txtviewcomplaintacknowledge.setText(acknwoldgeddate);

            txtviewcomplaintcloseby.setText(closedby);
            txtviewcomplaintclose.setText(closeddate);




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

        ImageAdapter imageAdapter = new ImageAdapter(getContext(),imageModelArrayList);

        imgerecycler.setAdapter(imageAdapter);



        viewPager1.setAdapter(new SlidingImage_Adapter(getActivity(),imageModelArrayList));



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

        if(flag==true){
            if(isacknowledged==0 && isclosed==0){
                button.setText(getResources().getString(R.string.Acknowledge));
                //btnforward.setVisibility(View.VISIBLE);
                type = "New";
                Drawable icon = getContext().getDrawable(R.drawable.acknowlged);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);

            }
            else if(isacknowledged==1 && isclosed==0){
                button.setText(getResources().getString(R.string.Close));
               // btnforward.setVisibility(View.VISIBLE);
                type = "OnGoing";
                button.setPadding(15,0,15,0);
                Drawable icon = getContext().getDrawable(R.drawable.closed);
                button.setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);

            }
            else if(isacknowledged==1 && isclosed==1){
                button.setVisibility(View.INVISIBLE);
            }
        }

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


    private class HttpRequestTask extends AsyncTask<Void, Void,String > {
        String json;
        ProgressDialog progressDialog;



        public HttpRequestTask(String json)
        {
            this.json=json;

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
         //   final String url = config.getUpdateGrievance();
            final String url = config.representativeremoteurl+"admin/app/updateGrievance";
            Log.e("updateGrievance url",url);

            try {

//For POST

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
                // Toast.makeText(LogIn_Now.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();

                return new String("Exception: " + e.getMessage());


            }

        }


        @Override
        protected void onPostExecute(String greeting) {



            if(greeting!=null){

                String responceName = greeting;
                Log.e("responceName:- ",responceName);


                if(responceName.equalsIgnoreCase("Success")){
                    if(type.equalsIgnoreCase("New")){
                        Toast.makeText(getContext(), getResources().getString(R.string.Acknowledge_Success), Toast.LENGTH_SHORT).show();


                    }else if (type.equalsIgnoreCase("OnGoing")){
                        Toast.makeText(getContext(), getResources().getString(R.string.Successfully_Close), Toast.LENGTH_SHORT).show();

                    }

                }
                else{
                    Toast.makeText(getContext(), getResources().getString(R.string.Connection_Problem), Toast.LENGTH_SHORT).show();
                }



                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")) {
                        fragment = new Dashboard();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


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
                String url=config.representativeremoteurl+"admin/app/getAllGrievanceDetailsByGrievanceId/"+grievanceid;

                Log.d("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {

            if(myPojo!=null){
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

                    address= myPojo.get(0).getAddress();

                    representativeid1= myPojo.get(0).getRepresentativeid();
                    grievanceid= myPojo.get(0).getGrievanceid();
                    acknwoldgeddate= myPojo.get(0).getAcknowledgedate();
                    closeddate= myPojo.get(0).getCloseddate();
                    createddate=myPojo.get(0).getCreateddate();
                    username=myPojo.get(0).getUsername();
                    mobileno=myPojo.get(0).getMobileno();
                    emailid=myPojo.get(0).getEmailid();
                    address=myPojo.get(0).getAddress();
                    acknowledgedby=myPojo.get(0).getOption1();
                    closedby=myPojo.get(0).getOption2();
                    isacknowledged = myPojo.get(0).getIsacknowledged();
                    isclosed =myPojo.get(0).getIsclosed();
                    citizenAddress = myPojo.get(0).getOption1()+", "+
                            myPojo.get(0).getCityname()+", "+
                            myPojo.get(0).getDistrictname()+", "+
                            myPojo.get(0).getStatename()+", "+
                            myPojo.get(0).getCountryname();
                    citizengender = myPojo.get(0).getOption3();
                    citizenimage =  myPojo.get(0).getOption2();

                    if(userid1==representativeid1){
                        button.setVisibility(View.VISIBLE);
                        btnforward.setVisibility(View.VISIBLE);
                    }else{
                        button.setVisibility(View.INVISIBLE);
                        btnforward.setVisibility(View.INVISIBLE);
                    }

                    setdata();

                }
                else{

                }

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

    private class HttpRequestMasterUser extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*         progressDialog = new ProgressDialog(Personal_Details.this);
            progressDialog.setMessage("Loading Member...."); // Setting Message
            progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
*/
        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url = config.getGetUserTypeMaster();
                final String url = config.representativeremoteurl+"admin/app/getUserTypeMaster";



                Log.i("TypeMaster url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
/*
                MyPojo[] forNow = restTemplate.getForObject(url, MyPojo[].class);
                ArrayList<MyPojo> greeting = new ArrayList(Arrays.asList(forNow));*/


                Dialstar[] forNow = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting = new ArrayList(Arrays.asList(forNow));
                return greeting;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
                //Toast.makeText(Personal_Details.this, "Sever connection failed.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){

                //Log.i("result usertype", myPojo+ "");
                mypojo3 = myPojo;


                Dialstar dp=new Dialstar();
                dp.setTypename(getResources().getString(R.string.Select_Representative_Type));
                dp.setTypeid(0);
                mypojo3.add(0,dp);

                customAdapter = new AddAdapter(getActivity(),R.layout.customspinner, R.id.title, mypojo3);
                spusertype.setAdapter(customAdapter);


                AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        typeid = mypojo3.get(i).getTypeid();


                        new HttpRequestRepresenataivelist(typeid,constituencyid).execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                };


                spusertype.setOnItemSelectedListener(onItemSelectedListener);

            }else {
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }

        }

    }


    private class HttpRequestRepresenataivelist extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        Dialog progressDialog;


        int typeid=0;
        int constituencyid=0;

        public HttpRequestRepresenataivelist(int typeid,int constituencyid) {
            this.typeid=typeid;
            this.constituencyid = constituencyid;

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
                //final String url1 = config.getGetRepresentativeList();
                final String url1 = config.representativeremoteurl+"admin/app/getMlaList/"+typeid+"/"+constituencyid;



                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow1 = restTemplate.getForObject(url1, Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            Log.i("result output", myPojo.size() + "");


            mypojo5 = myPojo;


            Dialstar dp = new Dialstar();
            dp.setMlaname(getResources().getString(R.string.Select_Representative));
            dp.setMlaid(0);
            mypojo5.add(0, dp);
            for(int i=0;i<mypojo5.size();i++){

                if(representativename.equalsIgnoreCase(mypojo5.get(i).getMlaname())){
                    mypojo5.remove(i);

                }

            }
            customAdapter = new AddAdapter(getActivity(),R.layout.customspinner, R.id.title, mypojo5);
            sprepresentative.setAdapter(customAdapter);
            // spmla.setSelection(mlaid);



            AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




                    representativeidfwrd = mypojo5.get(i).getMlaid();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            };

            sprepresentative.setOnItemSelectedListener(onItemSelectedListener);
            progressDialog.dismiss();

        }

    }


    private class HttpRequestForward extends AsyncTask<Void, Void,String > {
        String json;
        Dialog progressDialog;
        String mobilenumber;
        String password;

        public HttpRequestForward(String json)
        {
            this.json=json;
             Log.e("login Json:", String.valueOf(json));


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
            // try {
            // final String url = config.getMla_login();
           // final String url = "http://192.168.0.126:8099/admin/app/sendGrievance";
            final String url=config.representativeremoteurl+"admin/app/sendGrievance";

            Log.e("forward url",url);
            try {

//For POST

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
                // Toast.makeText(LogIn_Now.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();

                return new String("Exception: " + e.getMessage());


            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            if(greeting!=null){
                String responceName = greeting;
                Log.e(" forward responce:- ",responceName);


                Toast.makeText(getContext(), getResources().getString(R.string.Forward_successfully), Toast.LENGTH_SHORT).show();


                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();

                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")) {
                        fragment = new Dashboard();
                        if (fragment != null) {
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }

                }


            }else {
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
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


    private class HttpRequestForwardedList extends AsyncTask<Void, Void,String > {
        String json;
        Dialog progressDialog;


        public HttpRequestForwardedList(String json)
        {
            this.json=json;
            Log.e("forwarded list Json:", String.valueOf(json));


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
            // try {
            // final String url = config.getMla_login();
            // final String url = "http://192.168.0.126:8099/admin/app/sendGrievance";
            final String url=config.representativeremoteurl+"admin/app/getAllSendGrievanceHistory";

            Log.e("forwardlist url",url);
            try {

//For POST

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
                // Toast.makeText(LogIn_Now.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();

                return null;


            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            if(greeting!=null) {
                Log.e("responce",greeting);
            }else
             {
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
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

    public class HttpTaskSendmail extends AsyncTask<String, Void, String> //Using POST Method, send newly entered comment
    {
        ProgressDialog progressDialog;

        String jsonstr;

        public HttpTaskSendmail(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.e("mail json",jsonstr);

        }


        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            progressDialog.setButton(BUTTON_NEGATIVE, getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        protected String doInBackground(String... arg0) {

            try {
                //For POST
                //   String url = config.getCreateNewRepresentativeDiscussion();
                String url = config.representativeremoteurl+"admin/app/sendMail";
                Log.e("mail url",url);

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
            Log.e("mail result",result);

            //Log.i("Discussion Result", result.trim());
            // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            progressDialog.dismiss();
            Toast.makeText(getContext(), getResources().getString(R.string.Email_successfully_send), Toast.LENGTH_SHORT).show();


        }

    }



}
