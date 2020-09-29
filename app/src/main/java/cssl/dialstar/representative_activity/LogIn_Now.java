package cssl.dialstar.representative_activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogIn_Now extends AppCompatActivity {

    public TextInputLayout txtmobilenumber,txtemail,txtpassword,txtconfirmpassword;
    public EditText edmobilenumber,edemail,edpassword;
    public TextView txtaccnt,txtfogotpass;
    public Button btnlogin;

    String mobilenumber,email;
    JSONObject json;
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor ;
    String firebaseId="";


    Config  config = new Config();

    int mlaid,partyid,representativeid,countryid,stateid,cityid,constituencyid=0,pinid=0,wardid=0;
    String name;
    String address;

    String pin="";
    String mobileno;
    String emailid;
    String area;
    String ward,usertype;

    String password="";

    String partyname="",answer="";

    String file="";
    String countryname="";
    String statename="",constituency="",typename="",districtname="",
            loksabhaconstituency="",gender="",dob="";
    String cityname="";
    private BroadcastReceiver mRegistrationBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in__now);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(getResources().getString(R.string.app_name));
        txtmobilenumber=(TextInputLayout) findViewById(R.id.txtmobilenumber);
        txtpassword=(TextInputLayout) findViewById(R.id.txtpassword);
        txtemail=(TextInputLayout) findViewById(R.id.txtemail);


        edmobilenumber=(EditText) findViewById(R.id.edmobilenumber);
        edemail=(EditText) findViewById(R.id.edemail);
        edpassword=(EditText) findViewById(R.id.edpassword);
        btnlogin=(Button) findViewById(R.id.btnlogin);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mlaPref.edit();

        edpassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == keyEvent.KEYCODE_ENTER){
                    submitform();
                }
                return false;
            }
        });

        edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));
        edpassword.addTextChangedListener(new MyTextWatcher(edpassword));
        edemail.addTextChangedListener(new MyTextWatcher(edemail));

        txtaccnt=(TextView)findViewById(R.id.txtaccnt);
        txtfogotpass = (TextView) findViewById(R.id.txtfogotpass);
        txtfogotpass.setVisibility(View.GONE);
        txtfogotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ForgotPasswordRepresentative.class);
                startActivity(intent);
                finish();

            }
        });

        txtaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(LogIn_Now.this,Personal_Details.class);
               // Intent i=new Intent(LogIn_Now.this,Political_Details.class);
                startActivity(i);
                finish();
            }
        });


        edpassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    // do your stuff here
                    submitform();

                }
                return false;
            }

        });

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
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitform();
            }
        });


    }
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        firebaseId = regId;
        editor.putString("firebaseId",regId);
        editor.commit();
        Log.e("Dial star firebase", "Firebase reg id: " + regId);


    }


    public void submitform(){
        mobilenumber=edmobilenumber.getText().toString().trim();
        password=edpassword.getText().toString().trim();
        email = edemail.getText().toString().trim();
   /*     if(!validatemobilenumber())
        {
            return;
        }*/

  /*      else if(!validateemail()){
            return;

        }
  */     if(!validatepassword())
        {
            return;
        }

        else
        {
            //call webservice here to create new_compaint account
        }


        json = new JSONObject();
        try {
            json.put("mobileno",mobilenumber);
            json.put("password",password);
            json.put("emailid",email);
            json.put("androidid",firebaseId);

            Log.d("login Json:", String.valueOf(json));
            new HttpRequestTask(json.toString(),mobilenumber,password).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }
      /*  Intent i=new Intent(LogIn_Now.this,MlaHome.class);
        startActivity(i);*/



    }
/*    private boolean validatemobilenumber() {
        if (edmobilenumber.getText().toString().trim().isEmpty()) {
            txtmobilenumber.setError("Please enter valid mobile number");
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
    }*/
/*    private boolean validateemail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!edemail.getText().toString().trim().matches(emailPattern) && edemail.getText().toString().trim().isEmpty() ) {
            txtemail.setError("Please enter valid email");
            return false;
        }
        else {
            txtemail.setErrorEnabled(false);
        }

        return true;
    }*/


    private boolean validatepassword() {
        if (edpassword.getText().toString().trim().isEmpty()) {
            txtpassword.setError(getResources().getString(R.string.valid_password));
            return false;
        } else {
            txtpassword.setErrorEnabled(false);
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
                        txtmobilenumber.setError(getResources().getString(R.string.ten_digit_mobile_number));
                    }

                    if(edmobilenumber.getText().toString().length()==10) {
                        edpassword.requestFocus();
                        txtmobilenumber.setErrorEnabled(false);

                    }
                    break;

                /*case R.id.edemail:
                    validateemail();
                    break;*/

                case R.id.edpassword:
                    validatepassword();
                    break;

            }

        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void,String > {
        String json;
        Dialog progressDialog;
        String mobilenumber;
        String password;

        public HttpRequestTask(String json)
        {
            this.json=json;
           // Log.d("login Json:", String.valueOf(json));


        }

        public HttpRequestTask(String json, String mobilenumber, String password) {
            this.json=json;
            this.mobilenumber=mobilenumber;
            this.password=password;
            //Log.d("login Json:", String.valueOf(json));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();




            progressDialog = new Dialog(LogIn_Now.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
         /*//   progressDialog.setMessage("You Login ..."); // Setting Message
            progressDialog.setMessage("Please wait");  // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);*/


        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
           // final String url = config.getMla_login();
            final String url = config.representativeremoteurl+"admin/app/mla_login/";

            Log.d("login url",url);
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

            if(greeting!=null)
            {
                String responceName = greeting;
                //Log.e(" login responce:- ",responceName);



                try {
                    JSONObject jsonObject = new JSONObject(responceName);
                    //Log.e("JSONObject:- ", String.valueOf(jsonObject));

                    mlaid = jsonObject.getInt("mlaid");
                    representativeid = jsonObject.getInt("representativeid");
                    usertype = jsonObject.getString("usertype");

                    answer = jsonObject.getString("answer");
                    /* answer=getString(R.string.Error);*/
                    // Log.e("mlaid:- ", String.valueOf(mlaid));
                    //Log.e("representativeid:- ", String.valueOf(representativeid));


                    if(answer.equalsIgnoreCase(getString(R.string.Error)))
                    {
                        // Toast.makeText(LogIn_Now.this,"No Internet Connection...",Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(LogIn_Now.this)
                                .setMessage(getResources().getString(R.string.valid_username_and_password))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {


                                        dialog.cancel();

                                    }

                                }).show();
                    }

                    else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp") && answer.equalsIgnoreCase("Active")){

                        //Toast.makeText(LogIn_Now.this,"MLA Login Successful...",Toast.LENGTH_SHORT).show();

                        //
                        mlaid = jsonObject.getInt("mlaid");
                        name = jsonObject.getString("name");
                        address = jsonObject.getString("address");
                        pin = jsonObject.getString("pincode");
                        mobileno = jsonObject.getString("mobileno");
                        emailid = jsonObject.getString("emailid");
                        area = jsonObject.getString("area");
                        ward = jsonObject.getString("ward");



                        partyname = jsonObject.getString("partyname");
                        file = jsonObject.getString("file");
                        countryname = jsonObject.getString("countryname");
                        statename = jsonObject.getString("statename");
                        cityname = jsonObject.getString("cityname");
                        partyid = jsonObject.getInt("partyid");
                        representativeid = jsonObject.getInt("representativeid");
                        countryid = jsonObject.getInt("countryid");
                        stateid = jsonObject.getInt("stateid");
                        cityid = jsonObject.getInt("cityid");
                        pinid = jsonObject.getInt("pinid");
                        constituencyid = jsonObject.getInt("constituencyid");
                        constituency = jsonObject.getString("constituencyname");
                        typename =  jsonObject.getString("constituencyname");
                        wardid = jsonObject.getInt("wardid");
                        districtname = jsonObject.getString("districtname");//
                        loksabhaconstituency = jsonObject.getString("subject");
                        gender = jsonObject.getString("type");
                        dob = jsonObject.getString("createddate");

                        editor.putString("gender",gender);
                        editor.putString("dob",dob);

                        editor.putString("loksabhaconstituency",loksabhaconstituency);
                        editor.putInt("mlaid", representativeid);
                        editor.putString("name",name);
                        editor.putString("address",address);
                        editor.putString("mobileno",mobileno);
                        editor.putString("pincode",pin);
                        editor.putString("emailid",emailid);
                        editor.putString("area",area);
                        editor.putString("ward",ward);
                        editor.putString("password",password);
                        editor.putString("partyname",partyname);
                        editor.putString("file",file);
                        editor.putInt("countryid",countryid);
                        editor.putInt("stateid",stateid);
                        editor.putInt("cityid",cityid);

                        editor.putString("countryname",countryname);
                        editor.putString("statename",statename);
                        editor.putString("cityname",cityname);
                        editor.putString("constituency",constituency);
                        editor.putInt("partyid",partyid);
                        editor.putInt("representativeid",representativeid);
                        editor.putString("usertype","mla");
                        // Log.d("partyid", String.valueOf(partyid));
                        editor.putInt("pinid",pinid);
                        editor.putInt("constituencyid",constituencyid);
                        editor.putInt("wardid",wardid);
                        editor.putString("answer",answer);
                        editor.putString("typename",usertype);
                        editor.putString("districtname",districtname);


                        editor.commit();

         /*           Log.d("mlaid", String.valueOf(mlaid));
                    Log.d("name",name);
                    Log.d("address",address);
                    Log.d("mobilenumber",mobilenumber);
                    Log.d("pincode",pincode);
                    Log.d("emailid",emailid);
                    Log.d("area",area);
                    Log.d("wardname",ward);
                    Log.d("password",password);
                    Log.d("partyname",partyname);
                    Log.d("file",file);
                    Log.d("countryname",countryname);
                    Log.d("statename",statename);
                    Log.d("cityname",cityname);
*/


                        Intent i=new Intent(LogIn_Now.this,MlaHome.class);
                        startActivity(i);
                        finish();






                    }else if(answer.equalsIgnoreCase("Inactive")){

                        new AlertDialog.Builder(LogIn_Now.this)
                                .setMessage(getResources().getString(R.string.Please_ask_to_Admin_active_your_account_))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {


                                        dialog.cancel();

                                    }

                                }).show();
                    }  else  if (mlaid==0 && representativeid ==0 && answer.equalsIgnoreCase("")) {
                        new AlertDialog.Builder(LogIn_Now.this)
                                .setMessage(getResources().getString(R.string.valid_username_and_password))
                                .setCancelable(false)
                                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which)
                                    {


                                        dialog.cancel();

                                    }

                                }).show();
                       // Toast.makeText(LogIn_Now.this,"Login Unsuccessful...",Toast.LENGTH_SHORT).show();
                    }

                    else {

                        //  Toast.makeText(LogIn_Now.this,"Representative Login Successful...",Toast.LENGTH_SHORT).show();

                        //
                        mlaid = jsonObject.getInt("mlaid");
                        name = jsonObject.getString("name");
                        address = jsonObject.getString("address");
                        pin = jsonObject.getString("pincode");
                        mobileno = jsonObject.getString("mobileno");
                        emailid = jsonObject.getString("emailid");
                        area = jsonObject.getString("area");
                        ward = jsonObject.getString("ward");

                        partyname = jsonObject.getString("partyname");
                        file = jsonObject.getString("file");
                        countryname = jsonObject.getString("countryname");
                        statename = jsonObject.getString("statename");
                        cityname = jsonObject.getString("cityname");
                        partyid = jsonObject.getInt("partyid");
                        representativeid = jsonObject.getInt("representativeid");

                        countryid = jsonObject.getInt("countryid");
                        stateid = jsonObject.getInt("stateid");

                        cityid = jsonObject.getInt("cityid");
                        pinid = jsonObject.getInt("pinid");

                        constituencyid = jsonObject.getInt("constituencyid");
                        constituency = jsonObject.getString("constituencyname");
                        wardid = jsonObject.getInt("wardid");
                        districtname = jsonObject.getString("districtname");
                        loksabhaconstituency = jsonObject.getString("subject");
                        gender = jsonObject.getString("type");
                        dob = jsonObject.getString("reopendate");

                        editor.putString("gender",gender);
                        editor.putString("dob",dob);

                        editor.putString("loksabhaconstituency",loksabhaconstituency);
                        editor.putInt("mlaid", mlaid);
                        editor.putString("name",name);
                        editor.putString("address",address);
                        editor.putString("mobileno",mobileno);
                        editor.putInt("pinid",pinid);
                        editor.putString("pincode",pin);
                        editor.putString("emailid",emailid);
                        editor.putString("area",area);

                        editor.putString("password",password);
                        editor.putString("partyname",partyname);
                        editor.putString("file",file);
                        editor.putString("countryname",countryname);
                        editor.putInt("countryid",countryid);



                        editor.putInt("partyid",partyid);

                        editor.putInt("stateid",stateid);
                        editor.putString("statename",statename);
                        editor.putInt("cityid",cityid);
                        editor.putString("cityname",cityname);
                        editor.putInt("representativeid",representativeid);
                        editor.putString("usertype","representative");
                        editor.putString("typename",usertype);

                        editor.putInt("constituencyid",constituencyid);
                        editor.putString("constituency",constituency);
                        editor.putInt("wardid",wardid);
                        editor.putString("ward",ward);
                        editor.putString("districtname",districtname);
                        editor.commit();



                        Intent i=new Intent(LogIn_Now.this,MlaHome.class);

                        startActivity(i);
                        finish();


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LogIn_Now.this,getResources().getString(R.string.no_internet),Toast.LENGTH_SHORT).show();

                }


            }else{
                new android.support.v7.app.AlertDialog.Builder(LogIn_Now.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
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
