package cssl.dialstar.representative_activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.EditProfileAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.representative_util.GPSTracker;
import cssl.dialstar.representative_util.NotificationUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Political_Details extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    JSONObject json;
    ProgressDialog pDialog;
    Config config = new Config();
    String usertype="";
    String androidid=null,districtname="";
    int typeid=0;


    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor ;
    static final  int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=1 ;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private TextInputLayout txtarea,txtward,txtpassword,txtmobilenumber,txtconfirmpassword;
    private EditText edarea,edward,edpassword,edmobilenumber,edconfirmpassword,edother;
    private Button btnsave;
    private Spinner sppoliticalparty,spmla;
    ArrayList<DialstarPojo> dialstarPojos3;
    Dialog progressDialog;
    //TextView txtmla;

    String address="",name="",email="",picture="";
    String area="",ward="",password="",confirmpassword="",
            partyName="",mobilenumber="",pin="",constituency="",cityname="";
    int countryId=0,stateId=0,cityId=0,partyId=0,mlaid=0,constituencyid=0,pinid=0,wardid=0;
    double latitude=0,longitude=0;





    Context context;
    EditProfileAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_political__details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mlaPref.edit();
        androidid = mlaPref.getString("firebaseId",null);



        txtarea=(TextInputLayout)findViewById(R.id.txtarea);
        txtward=(TextInputLayout)findViewById(R.id.txtward);
        txtpassword=(TextInputLayout)findViewById(R.id.txtpassword);
        txtconfirmpassword=(TextInputLayout)findViewById(R.id.txtconfirmpassword);
        txtmobilenumber=(TextInputLayout)findViewById(R.id.txtmobilenumber);
       // txtmla = (TextView) findViewById(R.id.txtviewmla);
       // spmla = (Spinner) findViewById(R.id.spmla);

        edarea=(EditText)findViewById(R.id.edarea);
        //edward=(EditText)findViewById(R.id.edward);
        edother = (EditText) findViewById(R.id.edother);
        edpassword=(EditText)findViewById(R.id.edpassword);
        edconfirmpassword=(EditText)findViewById(R.id.edconfirmpassword);
        edmobilenumber=(EditText)findViewById(R.id.edmobilenumber);

        btnsave=(Button)findViewById(R.id.btnsave);

        sppoliticalparty=(Spinner)findViewById(R.id.sppoliticalparty);

        edarea.addTextChangedListener(new MyTextWatcher(edarea));
//        edward.addTextChangedListener(new MyTextWatcher(edward));
        edother.addTextChangedListener(new MyTextWatcher(edother));
        edpassword.addTextChangedListener(new MyTextWatcher(edpassword));
        edconfirmpassword.addTextChangedListener(new MyTextWatcher(edconfirmpassword));
        edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));






        Bundle bundle = getIntent().getExtras();
         name = bundle.getString("Name");
         address = bundle.getString("Address");
         countryId = bundle.getInt("Country");
         stateId = bundle.getInt("State");
         cityId = bundle.getInt("City");
         pin = bundle.getString("PinCode");
        // mobilenumber = bundle.getString("MobileNo");
         email = bundle.getString("Email");
         picture = bundle.getString("Picture");
         usertype = bundle.getString("usertype");
         mlaid = bundle.getInt("mlaid");
         pinid = bundle.getInt("pinid");
         constituency = bundle.getString("constituency");
         constituencyid = bundle.getInt("constituencyid");
         wardid = bundle.getInt("wardid");
         typeid = bundle.getInt("typeid");
         ward =  bundle.getString("ward");
        districtname = bundle.getString("districtname");



        //Toast.makeText(this , "usertype "+usertype, Toast.LENGTH_SHORT).show();
   /*     if(usertype.equalsIgnoreCase("Representative")){
            txtmla.setVisibility(View.VISIBLE);
            spmla.setVisibility(View.VISIBLE);


            new HttpRequestMLA().execute();


        }

*/

        sppoliticalparty.setOnItemSelectedListener(this);

        if(sppoliticalparty.getSelectedItemPosition()==0){
            edother.setVisibility(View.VISIBLE);
        }else
        {
            edother.setVisibility(View.GONE);
        }

        new HttpRequestTaskParties().execute();

       /* ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,political);

        sppoliticalparty.setAdapter(aa);
*/
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitform();

            }
        });

        edconfirmpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    submitform();

                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        //Toast.makeText(getApplicationContext(),political[position] ,Toast.LENGTH_LONG).show();
       //partyName = String.valueOf(arg0.getItemAtPosition(position));




    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }



    public void submitform()
    {
      /*  Bundle bundle = getIntent().getExtras();
        name = bundle.getString("Name");
        address = bundle.getString("Address");
        countryId = bundle.getInt("Country");
        stateId = bundle.getInt("State");
        cityId = bundle.getInt("City");
        pin = bundle.getString("PinCode");
        mobilenumber = bundle.getString("MobileNo");
        email = bundle.getString("Email");
        picture = bundle.getString("Picture");*/
        area=edarea.getText().toString().trim();
        //ward=edward.getText().toString().trim();
        partyId = (int) sppoliticalparty.getSelectedItemId();
        password=edpassword.getText().toString().trim();
        mobilenumber= edmobilenumber.getText().toString().trim();

        if(partyId!=0){

            partyName = dialstarPojos3.get(partyId).getPartyname();
            edother.setVisibility(View.GONE);


        }
        else{
            edother.setVisibility(View.VISIBLE);
            partyName = edother.getText().toString().trim();
        }



        confirmpassword=edconfirmpassword.getText().toString().trim();



       if(!validatepassword())
        {
            return;
        }
        else if(!validateconfirmpassword())
        {
            return;
        }
        else if(!validatemobilenumber())
        {
            return;
        }

        else if(!password.equals(confirmpassword))
        {
            new AlertDialog.Builder(Political_Details.this)
                    .setMessage("Password does not match")
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.ok), null)
                    .show();
        }

        else if(password.equals(confirmpassword)){



            getMyLocation();
       // Toast.makeText(this, "latitude"+latitude, Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "longitude"+longitude, Toast.LENGTH_SHORT).show();

            if (latitude==0.0&&longitude==0.0)
            {
                getMyLocation();
            }

            if(latitude!=0.0&&longitude!=0.0) {
                if (usertype.equalsIgnoreCase("mla")) {
                    mlaid=0;

                    json = new JSONObject();
                    try {
                        json.put("name", name);
                        json.put("address", address);
                        json.put("countryid", countryId);
                        json.put("stateid", stateId);
                        json.put("cityid", cityId);
                        json.put("pincode", pin);
                        json.put("mobileno", mobilenumber);
                        json.put("emailid", email);
                        json.put("area", area);
                        json.put("partyid", partyId);
                        json.put("password", password);
                        json.put("partyname", partyName);
                        json.put("ward", ward);
                        json.put("file", picture);
                        json.put("latitude", latitude);
                        json.put("longitude", longitude);
                        json.put("androidid", androidid);
                        json.put("usertype", usertype);
                        json.put("aadharcardno", "");
                        json.put("voterid", "");
                        json.put("pinid",pinid);
                        json.put("districtname",districtname);
                        json.put("constituencyname",constituency);
                        json.put("constituencyid",constituencyid);
                        json.put("wardid",wardid);
                        json.put("typeid",typeid);
                        json.put("mlaid", mlaid);

                        Log.d("Mla Input Json:- ", String.valueOf(json));
       /*             Log.d("Mla latitude", String.valueOf(latitude));
                    Log.d("Mla longitude", String.valueOf(longitude));
                    Log.d("Mla androidid", androidid);*/

                       // new HttpRequestMlARegister(json.toString()).execute();

                        new HttpRequestRepresentiveRegister(json.toString()).execute();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }else if (usertype.equalsIgnoreCase("mp")) {
                    mlaid=0;

                    json = new JSONObject();
                    try {
                        json.put("name", name);
                        json.put("address", address);
                        json.put("countryid", countryId);
                        json.put("stateid", stateId);
                        json.put("cityid", cityId);
                        json.put("pincode", pin);
                        json.put("mobileno", mobilenumber);
                        json.put("emailid", email);
                        json.put("area", area);
                        json.put("partyid", partyId);
                        json.put("password", password);
                        json.put("partyname", partyName);
                        json.put("ward", ward);
                        json.put("file", picture);
                        json.put("latitude", latitude);
                        json.put("longitude", longitude);
                        json.put("androidid", androidid);
                        json.put("usertype", usertype);
                        json.put("typeid",typeid);
                        json.put("aadharcardno", "");
                        json.put("voterid", "");
                        json.put("pinid",pinid);
                        json.put("mlaid", mlaid);
                        json.put("districtname",districtname);
                        json.put("constituencyname",constituency);
                        json.put("constituencyid",constituencyid);
                        json.put("wardid",wardid);
                        Log.d("MP Input Json:- ", String.valueOf(json));
       /*             Log.d("Mla latitude", String.valueOf(latitude));
                    Log.d("Mla longitude", String.valueOf(longitude));
                    Log.d("Mla androidid", androidid);*/

                       // new HttpRequestMlARegister(json.toString()).execute();
                        new HttpRequestRepresentiveRegister(json.toString()).execute();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                else if (usertype.equalsIgnoreCase("representative")) {

                    json = new JSONObject();
                    try {
                        json.put("name", name);
                        json.put("address", address);
                        json.put("countryid", countryId);
                        json.put("stateid", stateId);
                        json.put("cityid", cityId);
                        json.put("pincode", "");
                        json.put("pinid",pinid);
                        json.put("mobileno", mobilenumber);
                        json.put("emailid", email);
                        json.put("area", area);
                        json.put("partyid", partyId);
                        json.put("partyname", partyName);
                        json.put("password", password);

                        json.put("ward", ward);
                        json.put("file", picture);
                        json.put("mlaid", mlaid);
                        json.put("latitude", latitude);
                        json.put("longitude", longitude);
                        json.put("androidid", androidid);
                        json.put("usertype", usertype);
                        json.put("typeid",typeid);
                        json.put("aadharcardno", "");
                        json.put("voterid", "");
                        json.put("districtname",districtname);
                        json.put("constituencyname",constituency);
                        json.put("constituencyid",constituencyid);
                        json.put("wardid",wardid);

                        Log.d("Representative i/p Json", String.valueOf(json));
                 /*   Log.d("Repretive latitude", String.valueOf(latitude));
                    Log.d("Repretive longitude", String.valueOf(longitude));
                    Log.d("Repretive androidid", String.valueOf(androidid));
*/
                        new HttpRequestRepresentiveRegister(json.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }else {

                checkGpsIsEnable();
            }

        }


    }

    private boolean validateMla() {
        if(spmla.getSelectedItemId()==0){
            Toast.makeText(this, "Please select MLA or MP", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

            return;
        }
        else
        {
            checkGpsIsEnable();
        }

    }

    public void checkGpsIsEnable()
    {
        GPSTracker gps = new GPSTracker(this);
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
/*
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setMessage("Your lat long is="+latitude+","+longitude)
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.ok), null)
                    .show();*/
            Log.i("Current : " ,latitude + ", " + longitude);

         /*   lat=latitude.toString();
            log=longitude.toString();

            Log.i("latlog",lat+log);

*/


            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if(addresses.size()>0)
                {

                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    String locationAddress=address+","+city+","+state+","+country+","+postalCode+" ";
                   // Log.e("Address",locationAddress);
/*
                    new android.support.v7.app.AlertDialog.Builder(this)
                            .setMessage(locationAddress)
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok), null)
                            .show();
*/
                    if (String.valueOf(locationAddress).equalsIgnoreCase("null"))
                    {

                    }
                    else
                    {

                    }


                }

            } catch (IOException e) {
                e.printStackTrace();/*   Log.d("Repretive latitude", String.valueOf(latitude));
                    Log.d("Repretive longitude", String.valueOf(longitude));
                    Log.d("Repretive androidid", String.valueOf(androidid));
*/
            }


        }else{

            gps.showSettingsAlert();
        }



    }
    private boolean validateparty() {
        if(sppoliticalparty.getSelectedItemId()==0){


            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.Error))
                    .setMessage("Please select Political Party ")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
           // Toast.makeText(this, "Please select Political Party", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validatearea() {
        if (edarea.getText().toString().trim().isEmpty()) {
            txtarea.setError("Please enter valid area");
            return false;
        } else {
            txtarea.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateward() {
        if (edward.getText().toString().trim().isEmpty()) {
            txtward.setError("Please enter valid email");
            return false;
        } else {
            txtward.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatepassword() {
        if (edpassword.getText().toString().trim().isEmpty()) {
            txtpassword.setError("Please enter valid password");
            return false;
        } else {
            txtpassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateconfirmpassword() {
        if (edconfirmpassword.getText().toString().trim().isEmpty()) {
            txtconfirmpassword.setError("Please Re-enter password");
            return false;
        } else {
            txtconfirmpassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatemobilenumber() {
        if (edmobilenumber.getText().toString().trim().isEmpty()) {
            txtmobilenumber.setError("Please enter valid mobile number");
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edarea:
                    validatearea();
                    break;

              /*  case R.id.edward:
                    validateward();
                    break;*/

                case R.id.edpassword:
                    validatepassword();
                    break;

                case R.id.edconfirmpassword:
                    validateconfirmpassword();
                    break;
                case R.id.edmobilenumber:
                    validatemobilenumber();
                    break;
            }

        }
    }


    private class HttpRequestTaskParties extends AsyncTask<Void, Void, ArrayList<DialstarPojo>>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Political_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //final String url3 =config.getGetPoliticalParties();
                final String url3 = config.representativeremoteurl+"admin/app/getPoliticalParties";


                Log.i("url", url3);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

         /*       MyPojo[] forNow1 = restTemplate.getForObject(url2, MyPojo[].class);
                ArrayList<MyPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));*/

                DialstarPojo[] forNow3 = restTemplate.getForObject(url3,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow3));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos3 = myPojo;
            for (DialstarPojo mp3 : myPojo) {
               // Log.e("Partyid",mp3.getPartyid()+"Partyname: "+ mp3.getPartyname());

            }

            DialstarPojo dp=new DialstarPojo();
            dp.setCityname("--Select Party--");
            dp.setCityid(0);
            dialstarPojos3.add(0,dp);
            customAdapter = new EditProfileAdapter(Political_Details.this,
                    R.layout.customspinnerrepresregister, R.id.title, myPojo);
            sppoliticalparty.setAdapter(customAdapter);
            progressDialog.dismiss();

        }
    }

    private class HttpRequestMlARegister extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;

        public HttpRequestMlARegister(String json) {
            this.json = json;


            Log.d("Mla Register json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Political_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
           // final String url = config.getMlaCreateProfile();
            final String url = config.representativeremoteurl+"admin/app/mlaCreateProfile/";

            Log.d("MLa Register url",url);
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
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String greeting) {

            int mlaid = 0;
            String responceName = "";
            //Log.e("mla register response", greeting + "");
            try {
                JSONObject json = new JSONObject(greeting);
                mlaid = json.getInt("mlaid");
                responceName = json.getString("name");
              //  Log.d("MLAid:- ", String.valueOf(mlaid));
               // Log.d("responceName:- ", responceName);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (responceName.equals(getString(R.string.Error))) {
                new android.support.v7.app.AlertDialog.Builder(Political_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage("Server Not Connected ")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
               // Toast.makeText(Political_Details.this, "Server Not Connected", Toast.LENGTH_SHORT).show();

            }
            else if(responceName.equals("Already Exist")){
                new android.support.v7.app.AlertDialog.Builder(Political_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage("This mobile number already registered please add another number. ")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
               // Toast.makeText(Political_Details.this, "User Has Already Exist", Toast.LENGTH_SHORT).show();

            } else if (responceName.equals("Created") && mlaid > 0) {
                new AlertDialog.Builder(Political_Details.this)
                        .setMessage("Your account has been created successfully.")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                Intent i = new Intent(Political_Details.this, LogIn_Now.class);
                                startActivity(i);
                                finish();


                                dialog.cancel();

                            }

                        }).show();

                //save all details in shared preference
/*


                editor.putString("name", name);
                editor.putString("address", address);
             //   editor.putInt("countryid", countryId);
                editor.putInt("stateid", stateId);
                editor.putInt("cityid", cityId);
                editor.putString("mobileno", mobilenumber);
                editor.putInt("partyid", partyId);
                editor.putString("pincode", pin);
                editor.putString("emailid", email);
                editor.putString("area", area);
                editor.putString("password", password);
                editor.putString("partyname", partyName);
                editor.putInt("mlaid", mlaid);
                editor.putString("ward", ward);
                editor.putString("picture", picture);
                editor.putString("usertype",usertype);
                editor.putString("image",picture);
                editor.putInt("pinid",pinid);
                editor.putInt("constituencyid",constituencyid);
                editor.putInt("wardid",wardid);
                editor.commit();

*/

               // Toast.makeText(getApplicationContext(), "MLA id from shared pref- " + mlaidpref, Toast.LENGTH_SHORT).show();

               // Toast.makeText(getApplicationContext(), "MLA id- " + mlaid + " created successfully", Toast.LENGTH_SHORT).show();
           /*    if(usertype.equalsIgnoreCase("mla"))
                   Toast.makeText(getApplicationContext(), "MLA  " + name + " Welcome In Dialstar", Toast.LENGTH_LONG).show();

               else
                   Toast.makeText(getApplicationContext(), "M.P.  " + name + " Welcome In Dialstar", Toast.LENGTH_LONG).show();
*/
                //intent to mlahome

            }



            progressDialog.dismiss();
        }


    }


    private class HttpRequestRepresentiveRegister extends AsyncTask<Void, Void, String>
    {
        String json;
        Dialog progressDialog;

        public HttpRequestRepresentiveRegister(String json) {
            this.json = json;
            Log.d("Representive Regi json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Political_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
       //     final String url = config.getRepresentativeCreateProfile();
            final String url = config.representativeremoteurl+"admin/app/representativeCreateProfile/";

            Log.d("RepresentiveRegisterurl",url);
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
                return new String("Exception: " + e.getMessage());


            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            int representativeid=0;
            String responceName = "";
            //Log.e("Representive response", greeting + "");
            try {

                JSONObject json = new JSONObject(greeting);
                representativeid = json.getInt("representativeid");
                responceName = json.getString("name");
                Log.d("representativeid:- ", String.valueOf(representativeid));
                Log.d("responceName:- ", responceName);

            } catch (JSONException e) {
                e.printStackTrace();
            }




            if (responceName.equals(getString(R.string.Error))) {
                new android.support.v7.app.AlertDialog.Builder(Political_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage("Server Not Connected")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();


               // Toast.makeText(Political_Details.this, "Server Not Connected", Toast.LENGTH_SHORT).show();

            }
            else if(responceName.equals("Already Exist")){
                new android.support.v7.app.AlertDialog.Builder(Political_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage("This mobile number already registered please add another number. ")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
                //Toast.makeText(Political_Details.this, "User Has Already Exist", Toast.LENGTH_SHORT).show();

            }
            else if (responceName.equals("Created") && representativeid > 0) {

                //save all details in shared preference


                editor.putString("name", name);
                editor.putString("address", address);
                editor.putInt("countryid", countryId);
                editor.putInt("stateid", stateId);
                editor.putInt("cityid", cityId);
                editor.putString("mobileno", mobilenumber);
                editor.putInt("partyid", partyId);
                editor.putString("pincode", pin);
                editor.putString("emailid", email);
                editor.putString("area", area);
                editor.putString("password", password);
                editor.putString("partyname", partyName);
                editor.putInt("mlaid", mlaid);
                editor.putString("ward", ward);
                editor.putString("file", picture);
                editor.putInt("representativeid",representativeid);
                editor.putString("usertype",usertype);
                editor.putInt("pinid",pinid);
                editor.putInt("constituencyid",constituencyid);
                editor.putInt("wardid",wardid);
                editor.putInt("typeid",typeid);
                editor.putString("constituency",constituency);
                editor.putString("districtname",districtname);

                //editor.putString("image",picture);
                editor.commit();

                int representativeidpref = mlaPref.getInt("representativeid", representativeid);
                String representative = mlaPref.getString("name","");

                if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){

                    new AlertDialog.Builder(Political_Details.this)
                            .setMessage("Your account has been created successfully.")
                            .setCancelable(false)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    Intent i = new Intent(Political_Details.this, LogIn_Now.class);
                                    startActivity(i);
                                    finish();


                                    dialog.cancel();

                                }

                            }).show();

                }else{
                    Intent i = new Intent(Political_Details.this, MlaHome.class);
                    startActivity(i);
                    finish();
                }

            }




            progressDialog.dismiss();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

       /*         Intent intent = new Intent(this, UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
