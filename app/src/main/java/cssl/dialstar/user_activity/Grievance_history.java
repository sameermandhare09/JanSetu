package cssl.dialstar.user_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


import cssl.dialstar.R;
import cssl.dialstar.user_adapter.SlidingImage_Adapter;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_adapter.SlidingImage_Adapter;
*/


public class Grievance_history extends AppCompatActivity {

private ViewPager viewPager1;
    ArrayList<String> file;
    ArrayList<ImageModel> imageModelArrayList;
    LinearLayout layout1;
    TextView txt;
    int currentPage = 0;
    int NUM_PAGES = 0;
    String desc="";
    String rname="";
    String mname="";
    String rnamec="";
    String mnamec="";
    String rnamep="";
    String mnamep="";
    String ackdate="";
    String pending="";
    String completed="";
    String New="";

    TextView txtdesc=null;

    String address=null;
    String mobilenumber=null;
    String email="";
    String acknwoldgeddate=null;
    String acknowledgedby=null;
    String closedby=null;
    String username=null;
    String createddate=null;
    String closeddate=null;
    String description=null;
    String grievancename=null;




    TextView txtviewusername,txtviewmobileno,txtviewemailid,txtviewcreateddate;
    TextView txtviewcomplaintname;
    TextView txtviewcomplaintdate;
    TextView txtviewcomplaintassignedto;
    TextView txtviewcomplaintcreatedby;
    TextView txtviewcomplaintdescript;
    TextView txtviewcomplaintacknowledge;
    TextView txtviewcomplaintacknowledgeby;
    TextView txtviewcomplaintclose;
    TextView txtviewcomplaintcloseby;
    TextView txtviewaddress;
    LinearLayout linearLayoutacknowledg,linearLayoutclosed,linearLayoutacknowledg1,linearLayoutclosed1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_grievance_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




      /*  viewPager1=(ViewPager) findViewById(R.id.viewPager1);*/
     /* layout1=(LinearLayout) findViewById(R.id.layout1);*/
        txtdesc=(TextView) findViewById(R.id.txtView_description);
        txtviewcomplaintname = findViewById(R.id.txtView_complaint_name);
        txtviewcomplaintdate = findViewById(R.id.txtView_complaint_date);
        txtviewcomplaintassignedto = findViewById(R.id.txtView_assigned_to);
        txtviewcomplaintcreatedby = findViewById(R.id.txtView_created_by);
        txtviewcomplaintdescript=findViewById(R.id.txtView_descript);
        txtviewcomplaintacknowledge = findViewById(R.id.txtView_acknowledgedate);
        linearLayoutacknowledg = findViewById(R.id.lnr_acknowledge);
        txtviewcomplaintacknowledgeby=findViewById(R.id.txtView_acknowledgeby);
        linearLayoutclosed = findViewById(R.id.lnr_close);
        txtviewcomplaintclose=findViewById(R.id.txtView_Closedate);
        txtviewcomplaintcloseby = findViewById(R.id.txtView_Closeby);
        txtviewaddress = findViewById(R.id.txtView_address);

       // txtviewcreateddate = (TextView) findViewById(R.id.txtView_complaint_date);
        txtviewemailid= (TextView) findViewById(R.id.txtView_user_emailid);
        txtviewmobileno = (TextView) findViewById(R.id.txtView_user_mobile);
        //txtviewusername = (TextView) findViewById(R.id.txtView_created_by);
        //txtviewaddress = (TextView) findViewById(R.id.txtView_address);
      //  txt=(TextView) findViewById(R.id.txt);
     /*   TextView txt1=new TextView(getApplicationContext());
        TextView txt2=new TextView(getApplicationContext());
        TextView txt3=new TextView(getApplicationContext());
        TextView txt4=new TextView(getApplicationContext());
        TextView txt5=new TextView(getApplicationContext());
        TextView txt6=new TextView(getApplicationContext());
*/
        Bundle b=getIntent().getExtras();
        /*

            intent.putExtra("name",Name);
                intent.putExtra("mobilenumber",mobilenumber);
                intent.putExtra("email",email);
                intent.putExtra("address",address);
                intent.putExtra("createddate",createddate);
                intent.putExtra("acknowledgedby",acknowledgedby);
                intent.putExtra("acknwoldgeddate",acknwoldgeddate);
                intent.putExtra("closedby",closedby);
                intent.putExtra("closeddate",closeddate);
        */

        file=new ArrayList<>();
        file=b.getStringArrayList("images");
        Log.d("file", String.valueOf(file));


        grievancename =  b.getString("grievancename");
        username =  b.getString("username");
        createddate =  b.getString("createddate");
        acknowledgedby =  b.getString("acknowledgedby");
        acknwoldgeddate =  b.getString("acknwoldgeddate");
        closedby =  b.getString("closedby");
        closeddate =  b.getString("closeddate");
        address =  b.getString("address");
        description=  b.getString("description");
        email =  b.getString("email");
        mobilenumber =  b.getString("mobilenumber");

        toolbar.setTitle(grievancename);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

     /*   desc=b.getString("desc");
        rname=b.getString("representativename");
        username=b.getString("name");
        mobilenumber=b.getString("mobilenumber");
        address=b.getString("address");
        email=b.getString("email");
        mname=b.getString("mla");
        rnamep=b.getString("representativenamep");
        mnamep=b.getString("mlap");
        rnamec=b.getString("representativenamec");
        mnamec=b.getString("mlac");
        ackdate=b.getString("acknowledgedate");
        pending=b.getString("pen");
        completed=b.getString("com");
        createddate=b.getString("createddate");
        closeddate=b.getString("closedate");
        New=b.getString("New");
        String representativeby = "";
        String mlaby = "";
*/
/*
        txtviewusername.setText(username);
        txtviewmobileno.setText(mobilenumber);
        txtviewemailid.setText(email);
        txtviewcreateddate.setText(createddate);
        txtviewaddress.setText(address);

        */




        txtviewemailid.setText("Not provide");
        if(!email.isEmpty()) {
            txtviewemailid.setText(email);
        }
        if(acknwoldgeddate==null){
            linearLayoutacknowledg.setVisibility(View.GONE);
        }
        if(closeddate==null){
            linearLayoutclosed.setVisibility(View.GONE);
        }


        txtdesc.setText(description);
        txtviewcomplaintcreatedby.setText(username);
        txtviewcomplaintdate.setText(createddate);
        txtviewmobileno.setText(mobilenumber);
        txtviewcomplaintacknowledgeby.setText(acknowledgedby);
        txtviewcomplaintacknowledge.setText(acknwoldgeddate);
        txtviewcomplaintcloseby.setText(closedby);
        txtviewcomplaintclose.setText(closeddate);
        txtviewaddress.setText(address);



      //**  txt1.setText("Description:-"+desc);*//**//**//**//**//**//**//**//*
        /*if(rname!=null){
                txt2.setText("Assigned to:- " + rname + " on " + createddate);
            }
        if(mname!=null) {

            txt3.setText("Assigned to:- " + mname + " on " + createddate);
        }
        if(rnamep!=null){
            txt2.setText("Acknowledge by:- " + rnamep + " on " + ackdate);
        }
        if(mnamep!=null){
            txt3.setText("Acknowledge by:- " + mnamep + " on " + ackdate);
        }
        if(rnamec!=null){
            txt2.setText("Closed by:- " + rnamec + " on " + closeddate);
        }
        if(mnamec!=null){
            txt3.setText("Closed by:- " + mnamec + " on " + closeddate);
        }
        txt4.setText(pending);
        txt5.setText(completed);
        txt6.setText(New);
*/
     /*   layout1.addView(txt1);
        layout1.addView(txt2);

        layout1.addView(txt3);
        layout1.addView(txt4);
        layout1.addView(txt5);
        layout1.addView(txt6);
*/


        imageModelArrayList = new ArrayList<>();
        ArrayList<ImageModel> list = new ArrayList<>();
        imageModelArrayList = list;
        for (int i = 0;i<file.size();i++){

            ImageModel imageModel = new ImageModel();
            imageModel.setImage(file.get(i));
            imageModelArrayList.add(imageModel);

        }


        Log.d("images in imagemodel", String.valueOf(imageModelArrayList));
      /*   Bundle m=getIntent().getExtras();
        String represent=m.getString("representativename");
        TextView txt=new TextView(this);
        txt.setText(represent);
        layout1.addView(txt);
*/
        init();
    }

    private void init() {
        viewPager1 = (ViewPager) findViewById(R.id.viewPager1);
        viewPager1.setAdapter(new SlidingImage_Adapter(this,imageModelArrayList));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(viewPager1);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

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

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
/*
                Intent intent = new Intent();
                intent.putExtra("responceName",responceName);
                intent.putExtra("grievanceid",grievanceid);
                intent.putExtra("position",position);
//
                setResult(Activity.RESULT_OK,intent);*/
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    }

