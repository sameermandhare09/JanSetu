package cssl.dialstar.user_activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_adapter.EditProfileeAdapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.GPSTracker;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
*/

public class Register extends AppCompatActivity {
    SharedPreferences share;
    SharedPreferences.Editor edit;
    public TextInputLayout txtmobilenumber,txtaddress,txtemail,txtpassword,
            txtdistrict,txtstate,txtname,txtassociation,
            txtconfirmpassword,txtaadhaarnumber,txtvoterid,txtward,txtcity,
            txtpin,txtdob,txtmandal;//txtconstituency,txtloksabhaconstituency,
    public Button btnregister;
    public EditText edmobilenumber,edemail,edaddress,edpassword,
            edconfirmpassword,edaadhaarnumber,
            edvoterid,edward,edcity,edpin,
            edname,eddistrict,edstate,eddob,edmandal,edassociation;//edconstituency,edloksabhaconstituency,
    String mobilenumber,email,address,password,confirmpassword,aadhaarnumber,voterid,
            gender,dob,mandalName;
    ConfigUser config;
    Spinner sppincode,spconstituency,spward, spcity;
    TextView txtlogin,txtlocation;
    String androidid=null;
    ImageView imageViewlocation;
    String districtname="";
    String locationAddress,loksabhaconstituency;
    Double latitude=0.0, longitude=0.0;
    int locationflag=0;
    Button btnlocation;
    String state="";
    RadioGroup rbggender;
    RadioButton rbmale,rbfemale,rbother;

    String locationaddress="",locationcity="",locationstate="",locationcountry="",
            locationpostalCode="",locationknownName="";

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    ArrayList<DialstarPojo> dialstarPojos1;
    ArrayList<DialstarPojo> dialstarPojos2;
    ArrayList<DialstarPojo> dialstarPojos3;
    ArrayList<DialstarPojo> dialstarPojos4;
    ArrayList<DialstarPojo> dialstarPojos5;
    ArrayList<DialstarPojo> dialstarPojos6;
    ArrayList<DialstarPojo> dialstarPojos8;
    EditProfileeAdapter customAdapter;
    int countryid, cityid;
    int pincodeflag=0;
    int stateid=0,constituencyid=0,pinid=0,wardid=0;
   SpinnerDialog spinnerDialogcity,spinnerDialogpin,spinnerDialogdistrict,
           spinnerDialogconstituency,spinnerDialogstate,spinnerDialogloksabhaconstituency;

    String ward="",pin="",constituency="",city="",name="";
    String pincodetext="";
    String constituencytext="";
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_white);
        setSupportActionBar(toolbar);

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setLogo(R.drawable.logo);
       // toolbar.setTitle(getResources().getString(R.string.app_name));
        setTitle(getResources().getString(R.string.app_name));
        txtmobilenumber=(TextInputLayout) findViewById(R.id.txtmobilenumber);
        //txtaadhaarnumber=(TextInputLayout) findViewById(R.id.txtaadhaarnumber);
        txtvoterid=(TextInputLayout) findViewById(R.id.txtvoterid);
        txtpassword=(TextInputLayout) findViewById(R.id.txtpassword);
        txtemail=(TextInputLayout) findViewById(R.id.txtemail);
        txtward = (TextInputLayout) findViewById(R.id.txtward);
        txtaddress = (TextInputLayout) findViewById(R.id.txtaddress);
        txtconfirmpassword=(TextInputLayout) findViewById(R.id.txtconfirmpassword);
        txtcity =(TextInputLayout) findViewById(R.id.txtcity);
        txtpin=(TextInputLayout) findViewById(R.id.txtpin);

        txtdistrict=(TextInputLayout)findViewById(R.id.txtdistrict);
        txtstate=(TextInputLayout)findViewById(R.id.txtstate);
        txtname = (TextInputLayout) findViewById(R.id.txtname);//txtloksabhaconstituency



        /*txtloksabhaconstituency = (TextInputLayout) findViewById(R.id.txtloksabhaconstituency);
        txtconstituency=(TextInputLayout)findViewById(R.id.txtconstituency);
        */

        txtdob=(TextInputLayout)findViewById(R.id.txtdob);
        txtmandal=(TextInputLayout)findViewById(R.id.txtmandal);
        txtassociation = (TextInputLayout)findViewById(R.id.txtassociation);
        edassociation = (EditText)findViewById(R.id.edassociation);
        eddob = (EditText)findViewById(R.id.eddob);
        edmandal = (EditText)findViewById(R.id.edmandal);

        rbggender = (RadioGroup)findViewById(R.id.rbggender);
        rbmale = (RadioButton)findViewById(R.id.rbmale);
        rbfemale = (RadioButton)findViewById(R.id.rbfemale);
        rbother = (RadioButton)findViewById(R.id.rbother);
        edname = (EditText) findViewById(R.id.edname);
        eddistrict = (EditText)findViewById(R.id.eddistrict);
        edstate = (EditText)findViewById(R.id.edstate);
        edmobilenumber=(EditText) findViewById(R.id.edmobilenumber);
        //edaadhaarnumber=(EditText) findViewById(R.id.edaadhaarnumber);
        edvoterid=(EditText) findViewById(R.id.edvoterid);
        edemail=(EditText) findViewById(R.id.edemail);
        edward=(EditText) findViewById(R.id.edward);
        edpassword=(EditText) findViewById(R.id.edpassword);
        edaddress = (EditText) findViewById(R.id.edaddress);
        edconfirmpassword=(EditText) findViewById(R.id.edconfirmpassword);
        edcity=(EditText) findViewById(R.id.edcity);


//        edconstituency = (EditText)findViewById(R.id.edconstituency);//edloksabhaconstituency
//        edloksabhaconstituency = (EditText)findViewById(R.id.edloksabhaconstituency);//edloksabhaconstituency

        edpin=(EditText) findViewById(R.id.edpin);
        btnregister=(Button) findViewById(R.id.btnregister);

        //spcity = (Spinner) findViewById(R.id.spcity);

        //sppincode = findViewById(R.id.sppincode);
        spconstituency = findViewById(R.id.spconstituency);
        //spward = findViewById(R.id.spward);

        imageViewlocation = findViewById(R.id.imageViewlocation);


        edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));
        //edemail.addTextChangedListener(new MyTextWatcher(edemail));
        edaddress.addTextChangedListener(new MyTextWatcher(edaddress));
        edpassword.addTextChangedListener(new MyTextWatcher(edpassword));
        edconfirmpassword.addTextChangedListener(new MyTextWatcher(edconfirmpassword));
        edpin.addTextChangedListener(new MyTextWatcher(edpin));
        edname.addTextChangedListener(new MyTextWatcher(edname));



        config=new ConfigUser();
        txtlogin=(TextView)findViewById(R.id.txtlogin);
        //txtlocation=(TextView) findViewById(R.id.txtlocation);
        txtlogin.setPaintFlags(txtlogin.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Register.this,User_login.class);
                startActivity(intent);
                Register.this.finish();

            }
        });

        share = PreferenceManager.getDefaultSharedPreferences(this);
        edit = share.edit();
        androidid = share.getString("firebaseId",null);


        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2 = new ArrayList<>();
        dialstarPojos3 = new ArrayList<>();
        dialstarPojos4 = new ArrayList<>();
        dialstarPojos5 = new ArrayList<>();

        dialstarPojos6 = new ArrayList<>();
        dialstarPojos8 = new ArrayList<>();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();

        new HttpRequestStatebyCountryid(99).execute();

        gender = rbmale.getText().toString();
        rbggender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                View radioButton = rbggender.findViewById(checkedId);
                int index = rbggender.indexOfChild(radioButton);

                switch (index) {
                    case 0:
                        gender = rbmale.getText().toString();

                        break;
                    case 1:
                        gender = rbfemale.getText().toString();

                        break;
                    case 2:
                        gender = rbother.getText().toString();

                        break;

                }
            }
        });

        eddob.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                SimpleDateFormat dateFormatter = null,dateFormatter1 = null;
                dateFormatter=new SimpleDateFormat("dd-MM-yyyy",Locale.US);
                //dateFormatter1=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                final SimpleDateFormat DateFormatter = dateFormatter;
                final Calendar myCalendar= Calendar.getInstance();
                dob = eddob.getText().toString();

                Date dobDate = null;

                try {
                    dobDate = dateFormatter.parse(dob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(dobDate!=null){
                    myCalendar.setTime(dobDate);
                }

                DatePickerDialog dobDatePickerDialog ;
                dobDatePickerDialog =new DatePickerDialog(Register.this, R.style.UserDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()
                {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {


                        myCalendar.set(year, monthOfYear, dayOfMonth);
                        eddob.setText(DateFormatter.format(myCalendar.getTime()));
                        //txtviewfromdate.setText(DateFormatter.format(myCalendar.getTime()));
                    }

                },myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));


                dobDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dobDatePickerDialog.show();


            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                submitform();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(Register.this,User_login.class);
        startActivity(intent);
        Register.this.finish();
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

            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if(addresses.size()>0)
                {

                     locationaddress = addresses.get(0).getAddressLine(0);
                     locationcity = addresses.get(0).getLocality();
                     locationstate = addresses.get(0).getAdminArea();
                     locationcountry = addresses.get(0).getCountryName();
                     locationpostalCode = addresses.get(0).getPostalCode();
                     locationknownName = addresses.get(0).getFeatureName();

                    locationAddress=locationaddress+","+locationcity+","+locationstate+","+locationcountry+","+locationpostalCode+" ";
                    edaddress.setText(locationcity);
                    edpin.setText(locationpostalCode);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{

            gps.showSettingsAlert();
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


                Intent intent = new Intent(Register.this,User_login.class);
                startActivity(intent);
                Register.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", "");

        edit.putString("firebaseId",regId);
        edit.commit();
        //Log.e("Dial star firebase", "Firebase reg id: " + regId);


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submitform(){
        mobilenumber=edmobilenumber.getText().toString().trim();
        address = edaddress.getText().toString().trim();
        password=edpassword.getText().toString().trim();
        confirmpassword=edconfirmpassword.getText().toString().trim();
        email=edemail.getText().toString().trim();
        ward=edward.getText().toString().trim();
        pin= edpin.getText().toString().trim();

        city = edcity.getText().toString().trim();
        districtname = eddistrict.getText().toString().trim();
        name = edname.getText().toString().trim();

//        loksabhaconstituency = edloksabhaconstituency.getText().toString().trim();
//        constituency = edconstituency.getText().toString().trim();

        dob = eddob.getText().toString().trim();

        SimpleDateFormat dateFormatter = null,dateFormatter1 = null;
        dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //2018-05-30 16:15:28
        if(dob!=null){
            Date date = null;
            try {
                date = dateFormatter.parse(dob);
                dob = format.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        // aadhaarnumber=edaadhaarnumber.getText().toString().trim();
        voterid=edvoterid.getText().toString().trim();

        if(!validateusername())
        {
            return;
        }
        else  if(!validatename())
        {
            return;
        }else  if(!validateDob())
        {
            return;
        }
        else if(!validateaddress())
        {
            return;
        }else if(!validatepincode())
        {
            return;
        }
        else  if(!validateState())
        {
            return;
        }
        else if(!validateDistrict())
        {
            return;
        }else if(!validateCity())
        {
            return;
        }

       /* else if(!validateloksabhaconstituency())
        {
            return;
        }*/
      /*  else if(!validateconstituency())
        {
            return;
        }*/
      /*  else  if(!validateward())
        {
            return;
        }*/
        else if(!validatepassword())
        {
            return;
        }
        else if(!validateconfirmpassword())
        {
            return;
        }
        else if(!password.equals(confirmpassword))
        {
            new AlertDialog.Builder(Register.this)
                    .setMessage(getString(R.string.Password_does_not_match))
                    .setCancelable(false)
                    .setNegativeButton(getString(R.string.ok), null)
                    .show();
        }

        else if(password.equals(confirmpassword))

        {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mobileno", mobilenumber);
                jsonObject.put("name",name);
                jsonObject.put("emailid", email);
                jsonObject.put("address",address);
                jsonObject.put("password", password);
                jsonObject.put("aadharcardno", aadhaarnumber);
                jsonObject.put("voterid", voterid);
                jsonObject.put("androidid",androidid);
                jsonObject.put("stateid",stateid);
                jsonObject.put("cityid",cityid);
                jsonObject.put("pinid",pinid);
                jsonObject.put("pincode", "");
                jsonObject.put("constituencyid",constituencyid);
                jsonObject.put("constituency",constituency);
                jsonObject.put("wardid",wardid);
                jsonObject.put("ward",ward);
                jsonObject.put("districtname",districtname);
                jsonObject.put("subject",loksabhaconstituency);
                jsonObject.put("usertype",gender);
                jsonObject.put("createddate",dob);

                Log.d("register input json",jsonObject.toString());

               new HttpRequestTask(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else
        {
            //call webservice here to create new account
        }
    }





    private boolean validateusername() {
        if (edmobilenumber.getText().toString().trim().isEmpty()) {
            txtmobilenumber.setError(getString(R.string.valid_mobile_number));
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDob() {
        if (eddob.getText().toString().trim().isEmpty()) {
            txtdob.setError(getString(R.string.valid_dob));
            return false;
        } else {
            txtdob.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateaddress() {
        if (edaddress.getText().toString().trim().isEmpty()) {
            txtaddress.setError(getString(R.string.valid_address));
            return false;
        } else {
            txtaddress.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateState() {
        if (edstate.getText().toString().trim().isEmpty()) {
            txtstate.setError(getString(R.string.valid_State));
            return false;
        } else {
            txtstate.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity() {
        if (edcity.getText().toString().trim().isEmpty()) {
            txtcity.setError(getString(R.string.valid_City));
            return false;
        } else {
            txtcity.setErrorEnabled(false);
        }


        return true;
    }

    private boolean validateDistrict() {

        if (eddistrict.getText().toString().trim().isEmpty()) {
            txtdistrict.setError(getString(R.string.valid_District));
            return false;
        } else {
            txtdistrict.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatepincode() {
        if (edpin.getText().toString().trim().isEmpty()) {
            txtpin.setError(getString(R.string.valid_Pin_Code));
            return false;
        } else {
            txtpin.setErrorEnabled(false);
        }

        return true;
    }
    /*

    private boolean validateloksabhaconstituency() {
        if (edloksabhaconstituency.getText().toString().trim().isEmpty()) {
            txtloksabhaconstituency.setError(getString(R.string.valid_lok_sabha_constituency));
            return false;
        } else {
            txtloksabhaconstituency.setErrorEnabled(false);
        }

        return true;
    }


    */
    /*
    private boolean validateconstituency() {
        if (edconstituency.getText().toString().trim().isEmpty()) {
            txtconstituency.setError(getString(R.string.valid_vidhan_sabha_constituency));
            return false;
        } else {
            txtconstituency.setErrorEnabled(false);
        }

        return true;
    }


    */
    private boolean validateward() {
        if (edward.getText().toString().trim().isEmpty()) {
            txtward.setError(getString(R.string.valid_ward_name));
            return false;
        } else {
            txtward.setErrorEnabled(false);
        }

        return true;
    }



    private boolean validateemail() {
        if (edemail.getText().toString().trim().isEmpty()) {
            txtemail.setError("Please enter valid email");
            return false;
        } else {
            txtemail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatepassword() {
        if (edpassword.getText().toString().trim().isEmpty()) {
            txtpassword.setError(getString(R.string.valid_password));
            return false;
        } else {
            txtpassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatename() {
        if (edname.getText().toString().trim().isEmpty()) {
            txtname.setError(getString(R.string.valid_name));
            return false;
        } else {
            txtname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateconfirmpassword() {
        if (edconfirmpassword.getText().toString().trim().isEmpty()) {
            txtconfirmpassword.setError(getString(R.string.Re_enter_password));
            return false;
        } else {
            txtconfirmpassword.setErrorEnabled(false);
        }

        return true;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }


    public void onNothingSelected(AdapterView<?> parent) {

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
                case R.id.edmobilenumber:

                    if(edmobilenumber.getText().toString().length()<10) {
                        txtmobilenumber.setError(getString(R.string.ten_digit_mobile_number));
                    }

                    if(edmobilenumber.getText().toString().length()==10) {
                        edname.requestFocus();
                        txtmobilenumber.setErrorEnabled(false);
                    }
                    //validateusername();
                    break;
                case R.id.edaddress:
                    validateaddress();
                    break;

                case R.id.edemail:
                    validateemail();
                    break;
                case R.id.edpin:
                    validatepincode();

                    if(pincodeflag==0){
                        if(edpin.getText().length()==6){
                            new HttpRequestAllSearchingPincodeList(edpin.getText().toString()).execute();
                        }

                    }


                    break;
                case R.id.edname:
                    validatename();
                    break;

                case R.id.edpassword:
                    validatepassword();
                    break;

                case R.id.edconfirmpassword:
                    validateconfirmpassword();
                    break;
            }

        }
    }

    public class HttpRequestTask extends AsyncTask<String, Void, String> {
        Dialog progressDialog;

        String jsonstr;




        public HttpRequestTask(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.d("register input json",jsonstr);
        }


        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        protected String doInBackground(String... arg0) {

            try {
                //For POST
               // String url = config.getUser_create_new_account();
                String url = config.userremoteurl+"admin/app/user_create_new_account";

                Log.d("register user url",url);
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
                return new String( e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            //Log.i("Register result:", result);


            String status="";
            int userid=0;
             JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(result);
                status = jsonObject.getString("status");
                userid = jsonObject.getInt("userid");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(status.equalsIgnoreCase("Created"))
              {
                 // edit.putString("Name",name);
                  edit.putInt("Userid",userid);
                  edit.putString("name",name);
                  edit.putString("Address",address);
                  edit.putString("MobileNo",mobilenumber);
                  edit.putString("Emailid",email);
                  edit.putString("aadhaarnumber",aadhaarnumber);
                  edit.putString("voterid",voterid);
                  edit.putInt("stateid",stateid);
                  edit.putInt("Cityid",cityid);
                  edit.putString("Cityname",city);
                  edit.putInt("pinid",pinid);
                  edit.putString("pin",pin);
                  edit.putString("usertype","user");
                  edit.putString("ward",ward);
                  edit.putInt("constituencyid",constituencyid);
                  edit.putString("constituency",constituency);
                  edit.putInt("wardid",wardid);
                  edit.putString("districtname",districtname);
                  edit.putString("loksabhaconstituency",loksabhaconstituency);
                  edit.putString("gender",gender);
                  edit.putString("dob",dob);
                  edit.commit();


                   Intent i=new Intent(Register.this,UserDashboard.class);

                   startActivity(i);
                   Register.this.finish();

              }
              else if (status.equalsIgnoreCase("Already Exist")) {
                  new AlertDialog.Builder(Register.this).setTitle(getString(R.string.Already_Exists))
                          .setMessage(getString(R.string.already_exits))
                          .setCancelable(true)
                          .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                              public void onClick(DialogInterface dialog, int which) {
                                  dialog.cancel();
                              }
                          }).show();
              }
              else {
                  //give alert error in connection plz try again
                  new AlertDialog.Builder(Register.this).setTitle(getString(R.string.Error))
                          .setMessage(getString(R.string.no_internet))
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



    private class HttpRequestStatebyCountryid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        Dialog progressDialog;
        int countryId;
        public  HttpRequestStatebyCountryid(int pos) {
            countryId = pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url1 = config.getGetAllState()+countryId+" ";
                final String url1 = config.userremoteurl+"admin/app/getAllState/"+countryId+" ";


                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                DialstarPojo[] forNow1 = restTemplate.getForObject(url1,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
//            Log.i("result output", myPojo.size() + "");


            if(myPojo!=null) {
                dialstarPojos1 = myPojo;

                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < dialstarPojos1.size(); i++) {
                    items.add(dialstarPojos1.get(i).getStatename());

                }

                if(dialstarPojos3.size()>0){
                    for(int i=0;i<dialstarPojos1.size();i++){
                        if(dialstarPojos3.get(0).getStatename().equalsIgnoreCase(dialstarPojos1.get(i).getStatename())){

                            edstate.setText(dialstarPojos1.get(i).getStatename());
                            stateid = dialstarPojos1.get(i).getStateid();
                            new HttpRequestDistrictNameByStateId(stateid).execute();
                            new HttpRequestCityNameByStateid(stateid).execute();



                        }

                    }


                }

                if (locationflag == 1) {

                    for (int i = 0; i < dialstarPojos1.size(); i++) {
                        if (locationstate.equalsIgnoreCase(dialstarPojos1.get(i).getStatename())) {

                            stateid = dialstarPojos1.get(i).getStateid();
                            edstate.setText(dialstarPojos1.get(i).getStatename());
                            new HttpRequestDistrictNameByStateId(stateid).execute();
                            new HttpRequestCityNameByStateid(stateid).execute();
                            // new HttpRequestTask2(stateid).execute();
                        }


                    }

                }

                edstate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogstate.showSpinerDialog();
                    }
                });

                spinnerDialogstate = new SpinnerDialog(Register.this, items, getString(R.string.select_state), getString(R.string.Close));// With No Animation

                spinnerDialogstate.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edstate.setText(item);
                        for (int i = 0; i < dialstarPojos1.size(); i++) {
                            if (item.equalsIgnoreCase(dialstarPojos1.get(i).getStatename())) {

                                stateid = dialstarPojos1.get(i).getStateid();
                                new HttpRequestDistrictNameByStateId(stateid).execute();
                                new HttpRequestCityNameByStateid(stateid).execute();

                                districtname="";
                                loksabhaconstituency="";
                                constituencyid=0;
                                eddistrict.setText("");
                                edcity.setText("");
                                edpin.setText("");

//                                edloksabhaconstituency.setText("");
//                                edconstituency.setText("");


                            }
                        }

                    }
                });


            }else {
                new android.support.v7.app.AlertDialog.Builder(Register.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
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


    private class HttpRequestDistrictNameByStateId extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int stateId;
        Dialog progressDialog;
        public HttpRequestDistrictNameByStateId(int pos) {
            stateId = pos;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getDistrictNameByStateId/"+stateId+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            //Log.i("result output", myPojo.size() + "");


            dialstarPojos6 = myPojo;

            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos6.size();i++){
                items.add(dialstarPojos6.get(i).getName());

            }

            for(int i=0;i<dialstarPojos6.size();i++){
                if(dialstarPojos3.size()>0){
                    if(dialstarPojos3.get(0).getName()!=null && dialstarPojos6.get(i).getName()!=null ){
                        if(dialstarPojos3.get(0).getName().equalsIgnoreCase(dialstarPojos6.get(i).getName())){
                            eddistrict.setText(dialstarPojos3.get(0).getName());
                            districtname = eddistrict.getText().toString().trim();
//                            new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();

                        }
                    }
                    }



            }

            if(locationflag==1){
                for(int i=0;i<dialstarPojos6.size();i++){
                    if(locationcity.equalsIgnoreCase(dialstarPojos6.get(i).getName())){
                        eddistrict.setText(dialstarPojos6.get(i).getName());
                        districtname= eddistrict.getText().toString().trim();
                       // new HttpRequestCityNameByDistrictName(districtname).execute();
//                        new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();
                        //new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();



                    }

                }

            }
            eddistrict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogdistrict.showSpinerDialog();
                }
            });
            spinnerDialogdistrict=new SpinnerDialog(Register.this, items,getString(R.string.Select_District),getString(R.string.Close));// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogdistrict.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                    eddistrict.setText(item);
                    districtname = eddistrict.getText().toString().trim();
                   // new HttpRequestCityNameByDistrictName(districtname).execute();
//                    new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();
                   // new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();

                    loksabhaconstituency="";
                    constituencyid=0;
                    edcity.setText("");
                    edpin.setText("");

//                    edloksabhaconstituency.setText("");
//                    edconstituency.setText("");



                }
            });


            progressDialog.dismiss();

        }
    }

    /*

    private class HttpRequestConstutuencyNameListByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String districtname="";
        Dialog progressDialog;
        public HttpRequestConstutuencyNameListByDistrictName(String districtname) {


            this.districtname=districtname;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
           // Log.i("result output", myPojo.size() + "");


            dialstarPojos4 = myPojo;

        *//*    DialstarPojo dp=new DialstarPojo();
            dp.setCityname("--Please select city--");
            dp.setCityid(0);
            dialstarPojos2.add(0,dp);*//*

            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos4.size();i++){
                items.add(dialstarPojos4.get(i).getConstituencyname());


            }

            if(locationflag==1){
                for(int i=0;i<dialstarPojos4.size();i++){
                    if(locationcity.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){
                        edconstituency.setText(dialstarPojos4.get(i).getConstituencyname());
                        constituencyid = dialstarPojos4.get(i).getConstituencyid();




                    }

                }

            }
            edconstituency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogconstituency.showSpinerDialog();
                }
            });
            spinnerDialogconstituency=new SpinnerDialog(Register.this, items,getString(R.string.Select_Constituency),getString(R.string.Add),"constituency");// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    *//*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*//*

                    edconstituency.setText(item);
                    for(int i=0;i<dialstarPojos4.size();i++){
                        if(item.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){

                            constituencyid = dialstarPojos4.get(i).getConstituencyid();


                        }
                    }

                }
            });



            progressDialog.dismiss();

        }
    }

    */

    private class HttpRequestCityNameByStateid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        int stateId=0;
        Dialog progressDialog;
        public HttpRequestCityNameByStateid(int stateId) {


            this.stateId=stateId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
              //  final String url2 = config.userremoteurl+"admin/app/getCityNameByDistrictName/"+districtname+" ";
                final String url2 = config.userremoteurl+"admin/app/getAllCities/"+stateId+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos2 = myPojo;
            pincodeflag=1;



            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos2.size();i++){
                items.add(dialstarPojos2.get(i).getCityname());

            }


            for(int i=0;i<dialstarPojos2.size();i++){
                if(dialstarPojos3.size()>0){
                    if(dialstarPojos3.get(0).getCityname()!=null && dialstarPojos2.get(i).getCityname()!=null){
                        if(dialstarPojos3.get(0).getCityname().equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){

                            edcity.setText(dialstarPojos2.get(i).getCityname());
                            cityid = dialstarPojos2.get(i).getCityid();


                        }
                    }

                }


            }

            if(locationflag==1){
                for(int i=0;i<dialstarPojos2.size();i++){
                    if(locationcity.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){
                        edcity.setText(locationcity);
                        cityid = dialstarPojos2.get(i).getCityid();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("cityid",cityid);
                            new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();



                            pinid=0;
                            edpin.setText("");


                            // new HttpRequestPincode(jsonObject.toString()).execute();
                            // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }

                }

            }
            edcity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogcity.showSpinerDialog();
                }
            });
            spinnerDialogcity=new SpinnerDialog(Register.this, items,getString(R.string.Select_City),getString(R.string.Close));// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogcity.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                    edcity.setText(item);
                    for(int i=0;i<dialstarPojos2.size();i++){
                        if(item.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){

                            cityid = dialstarPojos2.get(i).getCityid();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("cityid",cityid);
                                new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();
                                // new HttpRequestPincode(jsonObject.toString()).execute();
                                // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }

                }
            });



            /*customCityAdapter = new CustomAdapter(Personal_Details.this,
                    R.layout.customspinner, R.id.title, dialstarPojos2);
            spcity.setAdapter(customCityAdapter);

            */



            progressDialog.dismiss();

        }
    }

/*

    private class HttpRequestCityNameByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String districtname="";
        ProgressDialog progressDialog;
        public HttpRequestCityNameByDistrictName(String districtname) {


            this.districtname=districtname;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getCityNameByDistrictName/"+districtname+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos2 = myPojo;
            pincodeflag=1;



            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos2.size();i++){
                items.add(dialstarPojos2.get(i).getCityname());

            }

            if(locationflag==1){
                for(int i=0;i<dialstarPojos2.size();i++){
                    if(locationcity.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){
                        edcity.setText(locationcity);
                        cityid = dialstarPojos2.get(i).getCityid();

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("cityid",cityid);
                            new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();
                            // new HttpRequestPincode(jsonObject.toString()).execute();
                            // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }






                    }

                }

            }
            edcity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogcity.showSpinerDialog();
                }
            });
            spinnerDialogcity=new SpinnerDialog(Register.this, items,"Select City","Close");// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogcity.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    */
/*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*//*


                    edcity.setText(item);
                    for(int i=0;i<dialstarPojos2.size();i++){
                        if(item.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){

                            cityid = dialstarPojos2.get(i).getCityid();
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("cityid",cityid);
                                new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();
                                // new HttpRequestPincode(jsonObject.toString()).execute();
                                // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        }
                    }

                }
            });



            */
/*customCityAdapter = new CustomAdapter(Personal_Details.this,
                    R.layout.customspinner, R.id.title, dialstarPojos2);
            spcity.setAdapter(customCityAdapter);

            *//*




            progressDialog.dismiss();

        }
    }
*/

    private class HttpRequestAllPincodeByCityId extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;

        public HttpRequestAllPincodeByCityId(String json) {
            this.json = json;
            Log.d("pincode i/p json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            //final String url = config.getGetAllPincodeByCityId();
            final String url = config.userremoteurl+"admin/app/getAllPincodeByCityId";

            Log.d("pincode url",url);
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

            dialstarPojos3=new ArrayList<>();


            String responceName = greeting;
            //Log.e("Pincode responce =", responceName + "" );
           // Log.e  ("Response size=", String.valueOf(responceName.length()));
            try {
                JSONArray jsonArray=new JSONArray(greeting);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    String pincode=jsonObject.getString("pincode");
                    int pinid=jsonObject.getInt("pinid");
                    DialstarPojo dialstar=new DialstarPojo();
                    dialstar.setPincode(pincode);
                    dialstar.setPinid(pinid);
                    dialstarPojos3.add(dialstar);

                }
              /*  DialstarPojo dialstar1=new DialstarPojo();
                dialstar1.setPincode("--Please select Pin--");
                dialstar1.setPinid(0);
                dialstarPojos3.add(0,dialstar1);*/


                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos3.size();i++){
                    items.add(dialstarPojos3.get(i).getPincode());

                }
                if(locationflag==1){
                    for(int i=0;i<dialstarPojos3.size();i++){
                        if(locationpostalCode.equalsIgnoreCase(dialstarPojos3.get(i).getPincode())){
                            edpin.setText(locationpostalCode);
                            pinid = dialstarPojos3.get(i).getPinid();

                            JSONObject jsonObject1 = new JSONObject();
                            try {
                                jsonObject1.put("pinid",pinid);
                                // new HttpRequestConstituencyByPincode(jsonObject1.toString()).execute();
                                //new HttpRequestWardNameByPincode(jsonObject1.toString()).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }

                }


                edpin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogpin.showSpinerDialog();
                    }
                });
                spinnerDialogpin=new SpinnerDialog(Register.this, items,getString(R.string.Select_Pin_Code),getString(R.string.Close));// With No Animation



                spinnerDialogpin.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        edpin.setText(item);
                        for(int i=0;i<dialstarPojos3.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos3.get(i).getPincode())){

                                pinid = dialstarPojos3.get(i).getPinid();

                                JSONObject jsonObject1 = new JSONObject();
                                try {
                                    jsonObject1.put("pinid",pinid);
                                    // new HttpRequestConstituencyByPincode(jsonObject1.toString()).execute();
                                    //new HttpRequestWardNameByPincode(jsonObject1.toString()).execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                    }
                });

                if(pinid==0){



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }




            progressDialog.dismiss();
        }
    }


    private class HttpRequestAllSearchingPincodeList extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {
        String pin;
        Dialog progressDialog;

        public HttpRequestAllSearchingPincodeList(String pincode) {
            this.pin = pincode;
            Log.d("PinCode i/p pin",pin);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getAllSearchingPincodeList/"+pin;


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DialstarPojo> greeting) {

            //  dialstarPojos3=new ArrayList<>();



            dialstarPojos3=greeting;
            Log.d("pincode o/p",dialstarPojos3+"");

            if(dialstarPojos3.size()>0) {




                dialstarPojos4.clear();

                pinid = dialstarPojos3.get(0).getPinid();

                new HttpRequestStatebyCountryid(99).execute();

                /*state = dialstarPojos3.get(0).getStatename();
                stateid = dialstarPojos3.get(0).getStateid();
                edstate.setText(state);
                eddistrict.setText(dialstarPojos3.get(0).getName());
                edcity.setText(dialstarPojos3.get(0).getCityname());
                cityid = dialstarPojos3.get(0).getCityid();

                districtname = dialstarPojos3.get(0).getName();
                new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();
                */



                /*final String subject = dialstarPojos3.get(0).getSubject();
                String constituencyname = dialstarPojos3.get(0).getConstituencyname();

                try {
                    JSONObject jsonObject = new JSONObject(subject);
                    JSONObject jsonObject_name = new JSONObject(constituencyname);

                    int constituancyid=0;
                    String constituancy="";

                    for(int i=0;i<jsonObject.length();i++){


                        DialstarPojo dialstarPojo = new DialstarPojo();


                        if(jsonObject_name.has("constituencyname"+(i+1)))
                            constituancy= jsonObject_name.getString("constituencyname"+(i+1));

                        if(jsonObject.has("constituencyid"+(i+1)))
                            constituancyid= jsonObject.getInt("constituencyid"+(i+1));

                        dialstarPojo.setConstituencyname(constituancy);
                        dialstarPojo.setConstituencyid(constituancyid);


                        if(!String.valueOf(constituancy).equalsIgnoreCase("null"))
                            dialstarPojos4.add(dialstarPojo);



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos4.size();i++){
                    items.add(dialstarPojos4.get(i).getConstituencyname());


                }


                edconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogconstituency=new SpinnerDialog(Register.this, items,"Select or Add Constituency","Add","constituency");// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    *//*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*//*

                        edconstituency.setText(item);
                        for(int i=0;i<dialstarPojos4.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){

                                constituencyid = dialstarPojos4.get(i).getConstituencyid();




                            }
                        }

                    }
                });

*/


            }else {
                new android.support.v7.app.AlertDialog.Builder(Register.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.Incorrect_PinCode))
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

/*


    private class HttpRequestLokSabhaConstituencyNameByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        String didtrictname="";
        Dialog progressDialog;
        public HttpRequestLokSabhaConstituencyNameByDistrictName(String districtname) {
            this.didtrictname= districtname;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {

                final String url2 = ConfigUser.userremoteurl+"admin/app/getLokSabhaConstituencyNameByDistrictName/"+didtrictname;


                Log.i("LokSabhaConstituencyName url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {



            dialstarPojos8 = myPojo;

            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos8.size();i++){
                items.add(dialstarPojos8.get(i).getSubject());

            }

            if(locationflag==1){
                for(int i=0;i<dialstarPojos8.size();i++){
                    if(locationcity.equalsIgnoreCase(dialstarPojos8.get(i).getSubject())){
                        edloksabhaconstituency.setText(dialstarPojos8.get(i).getSubject());

                        loksabhaconstituency= edloksabhaconstituency.getText().toString().trim();

                        new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituency).execute();


                    }

                }

            }
            edloksabhaconstituency.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogloksabhaconstituency.showSpinerDialog();
                }
            });
            spinnerDialogloksabhaconstituency=new SpinnerDialog(Register.this, items,getString(R.string.Select_Lok_Sabha_Constituency),getString(R.string.Close));// With No Animation


            spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {


                    edloksabhaconstituency.setText(item);
                    loksabhaconstituency = edloksabhaconstituency.getText().toString().trim();

                    new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituency).execute();


                    constituencyid=0;
                    edconstituency.setText("");


                }
            });


            progressDialog.dismiss();

        }
    }


    private class HttpRequestConstutuencyNameListByLoksabhaConstituencyName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String loksabhaconstituency="";
        Dialog progressDialog;
        public HttpRequestConstutuencyNameListByLoksabhaConstituencyName(String loksabhaconstituency) {


            this.loksabhaconstituency=loksabhaconstituency;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Register.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                // final String url2 = config.userremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+loksabhaconstituency+" ";

                final String url2 = ConfigUser.userremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+loksabhaconstituency+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            // Log.i("result output", myPojo.size() + "");

            if(myPojo!=null){

                dialstarPojos4.clear();

                dialstarPojos4 = myPojo;

        *//*    DialstarPojo dp=new DialstarPojo();
            dp.setCityname("--Please select city--");
            dp.setCityid(0);
            dialstarPojos2.add(0,dp);*//*

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos4.size();i++){
                    items.add(dialstarPojos4.get(i).getConstituencyname());


                }

                if(locationflag==1){
                    for(int i=0;i<dialstarPojos4.size();i++){
                        if(locationcity.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){
                            edconstituency.setText(dialstarPojos4.get(i).getConstituencyname());
                            constituencyid = dialstarPojos4.get(i).getConstituencyid();




                        }

                    }

                }
                edconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogconstituency=new SpinnerDialog(Register.this, items,getString(R.string.Select_Constituency),getString(R.string.Add),"constituency");// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    *//*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*//*

                        edconstituency.setText(item);
                        for(int i=0;i<dialstarPojos4.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){

                                constituencyid = dialstarPojos4.get(i).getConstituencyid();


                            }
                        }

                    }
                });



            }else {
                new android.support.v7.app.AlertDialog.Builder(Register.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
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



    */

    public  class SpinnerDialog {
        ArrayList<String> items;
        Activity context;
        String dTitle;
        String closeTitle = "Close";
        String calling = "";
        OnSpinerItemClick onSpinerItemClick;
        android.app.AlertDialog alertDialog;
        int pos;
        int style;

        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
        }

        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
            this.closeTitle = closeTitle;
        }
        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle, String calling) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
            this.style = style;
            this.closeTitle = closeTitle;
            this.calling = calling;
        }

        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
            this.style = style;
        }

        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, int style, String closeTitle) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
            this.style = style;
            this.closeTitle = closeTitle;
        }

        public void bindOnSpinerListener(OnSpinerItemClick onSpinerItemClick1) {
            this.onSpinerItemClick = onSpinerItemClick1;
        }

        public void showSpinerDialog() {
            android.app.AlertDialog.Builder adb = new android.app.AlertDialog.Builder(this.context);
            View v = this.context.getLayoutInflater().inflate(in.galaxyofandroid.spinerdialog.R.layout.dialog_layout, (ViewGroup)null);
            TextView rippleViewClose = (TextView)v.findViewById(in.galaxyofandroid.spinerdialog.R.id.close);
            TextView title = (TextView)v.findViewById(in.galaxyofandroid.spinerdialog.R.id.spinerTitle);
            rippleViewClose.setText(this.closeTitle);
            title.setText(this.dTitle);
            ListView listView = (ListView)v.findViewById(in.galaxyofandroid.spinerdialog.R.id.list);
            final EditText searchBox = (EditText)v.findViewById(in.galaxyofandroid.spinerdialog.R.id.searchBox);
            final ArrayAdapter<String> adapter = new ArrayAdapter(this.context, in.galaxyofandroid.spinerdialog.R.layout.items_view, this.items);
            listView.setAdapter(adapter);
            adb.setView(v);
            this.alertDialog = adb.create();
            this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
            this.alertDialog.setCancelable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView t = (TextView)view.findViewById(in.galaxyofandroid.spinerdialog.R.id.text1);

                    for(int j = 0; j <items.size(); ++j) {
                        if (t.getText().toString().equalsIgnoreCase(((String) items.get(j)).toString())) {
                            pos = j;
                        }
                    }

                    onSpinerItemClick.onClick(t.getText().toString(), pos);
                    alertDialog.dismiss();
                }
            });
            searchBox.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    if(calling.equalsIgnoreCase("pin")){
                        pincodetext=searchBox.getText().toString();
                        adapter.getFilter().filter(searchBox.getText().toString());
                    }else if(calling.equalsIgnoreCase("constituency")){
                        constituencytext=searchBox.getText().toString();
                        adapter.getFilter().filter(searchBox.getText().toString());
                    }else{
                        adapter.getFilter().filter(searchBox.getText().toString());
                    }

                }
            });
            rippleViewClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertDialog.dismiss();
                    if(calling.equalsIgnoreCase("pin")){
                        edpin.setText(pincodetext);
                        pinid=0;
                    }
                    else if(calling.equalsIgnoreCase("constituency")){
//                        edconstituency.setText(constituencytext);
                        constituencyid=0;
                    }
                }
            });
            this.alertDialog.show();
        }
    }


}
