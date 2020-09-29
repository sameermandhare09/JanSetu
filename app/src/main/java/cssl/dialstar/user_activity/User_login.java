package cssl.dialstar.user_activity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.user_utils.ConfigUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
*/

public class User_login extends AppCompatActivity {

    SharedPreferences share;
    SharedPreferences.Editor edit;
    public TextInputLayout txtusername, txtpassword,txtemail;
    public EditText edmobilenumber=null, epassword,edemail;
    public Button btnlogin;
    String username="",androidid="", password="",email="";
    public TextView txtaccnt,txtfogotpass;
    ConfigUser config;

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.logo_white);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setLogo(R.drawable.logo);
//        toolbar.setBackgroundColor(getResources().getColor(R.color.btnblue));
       // toolbar.setTitle(getResources().getString(R.string.app_name));
        setTitle(getResources().getString(R.string.app_name));

        share = PreferenceManager.getDefaultSharedPreferences(this);
        edit = share.edit();
        txtusername = (TextInputLayout) findViewById(R.id.txtusername);
        txtpassword = (TextInputLayout) findViewById(R.id.txtpassword);
        //  txtemail=(TextInputLayout) findViewById(R.id.txtemail);

        edmobilenumber = (EditText) findViewById(R.id.edusername);
        epassword = (EditText) findViewById(R.id.edpassword);
        edemail=(EditText) findViewById(R.id.edemail);

        btnlogin = (Button) findViewById(R.id.btnlogin);
        config = new ConfigUser();

        edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));
        epassword.addTextChangedListener(new MyTextWatcher(epassword));


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


        txtaccnt = (TextView) findViewById(R.id.txtaccnt);
        txtfogotpass = (TextView) findViewById(R.id.txtfogotpass);
        txtfogotpass.setVisibility(View.GONE);
        txtfogotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),ForgotPasswordUser.class);
                startActivity(intent);
                finish();
            }
        });

        txtaccnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(User_login.this, Register.class);
                startActivity(i);
                finish();


            }
        });
        epassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == keyEvent.KEYCODE_ENTER) {
                    submitform();
                }
                return false;
            }
        });
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        androidid = pref.getString("regId", "");

        edit.putString("firebaseId",androidid);
        edit.commit();


        // Log.e("Dial star firebase", "Firebase reg id: " + androidid);


    }

    public void submitform(){
        username=edmobilenumber.getText().toString().trim();
        email=edemail.getText().toString().trim();
        password=epassword.getText().toString().trim();






       /* if(!validateName())
        {
            return;
        }*/
       /* else if (!validateemail())
        {
            return;
        }*/
        if(!validatepassword())
        {
            return;
        }
        else
        {

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("emailid",email);
                jsonObject.put("password", password);
                jsonObject.put("mobileno", username);
                jsonObject.put("androidid", androidid);


                new HttpRequestTask(jsonObject.toString(),username,password).execute();

            } catch (JSONException e) {
                e.printStackTrace();
            }



           /* Intent i=new Intent(User_login.this,Tab_Activity.class);
            startActivity(i);*/
            //call webservice here to authenticate user and password

        }
    }

    private boolean validateName() {

        if (edmobilenumber.getText().toString().trim().isEmpty() ) {

            txtusername.setError(getString(R.string.valid_mobile_number));
            return false;
        }
        else {
            txtusername.setErrorEnabled(false);

        }

        return true;
    }

   /* private boolean validateemail()
    {
        if(edemail.getText().toString().trim().isEmpty())
        {
            txtemail.setError("please enter a valid email");
        }
        else
        {
            txtemail.setErrorEnabled(false);
        }
        return true;
    }*/
    private boolean validatepassword() {
        if (epassword.getText().toString().trim().isEmpty()) {
            txtpassword.setError(getString(R.string.valid_password));
            return false;
        } else {
            txtpassword.setErrorEnabled(false);
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
                case R.id.edusername:

                    if(edmobilenumber.getText().toString().length()<10) {
                        txtusername.setError(getString(R.string.ten_digit_mobile_number));
                    }

                    if(edmobilenumber.getText().toString().length()==10) {
                        epassword.requestFocus();
                        txtusername.setErrorEnabled(false);
                    }
                    //validateName();
                    break;

                case R.id.edpassword:
                    validatepassword();
                    break;
            }

        }
    }

    public class HttpRequestTask extends AsyncTask<String, Void, String> {
         Dialog progressDialog;
        String jsonstr;
        String mobileno;
        String password;

        public HttpRequestTask(String jsonstr,String mobileno,String password) {
            this.jsonstr = jsonstr;
            this.mobileno=mobileno;
            this.password=password;
            Log.d("user login input json",jsonstr);
        }


        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new Dialog(User_login.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST


               // String url =config.getUser_login();
                String url =config.userremoteurl+"admin/app/user_login";

                //"http://182.18.163.201:8099/admin/app/user_login";
                Log.d("User Login url",url);
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
            progressDialog.dismiss();
            Log.i("Log_in result:", result);
           /* if(result.equalsIgnoreCase("connect timed out")){
                Toast.makeText(User_login.this, "Please check Intenet connection", Toast.LENGTH_SHORT).show();
            }*/


            JSONObject jsonObject;
             int userid=0;
            int countryid=0;
            int stateid=0;
            int cityid=0;
            String name="";
            String emailid="";
            String password="";
            String file=" ";
            String countryname=" ";
            String statename=" ";
            String cityname=" ";
            String address=" ";
            String mobilenumber="";
            String pin="";
            String aadhaarnumber="";
            String voterid="";
            String usertype="",ward="",constituency="",districtname="",loksabhaconstituency,gender,dob;
            int pinid,constituencyid,wardid;
            try {
                jsonObject =new JSONObject(result);
                userid=jsonObject.getInt("userid");
                name=jsonObject.getString("name");
                emailid=jsonObject.getString("emailid");
                password=jsonObject.getString("password");
                file=jsonObject.getString("file");
                countryid=jsonObject.getInt("countryid");
                stateid=jsonObject.getInt("stateid");
                cityid=jsonObject.getInt("cityid");
                address=jsonObject.getString("address");
                mobilenumber=jsonObject.getString("mobileno");
                cityname =jsonObject.getString("cityname");
                aadhaarnumber=jsonObject.getString("aadharcardno");
                voterid=jsonObject.getString("voterid");
                ward=jsonObject.getString("ward");
                pin=jsonObject.getString("pincode");
                pinid = jsonObject.getInt("pinid");
                constituencyid = jsonObject.getInt("constituencyid");
                wardid = jsonObject.getInt("wardid");
                constituency=jsonObject.getString("constituencyname");
                districtname = jsonObject.getString("districtname");
                loksabhaconstituency = jsonObject.getString("subject");
                gender = jsonObject.getString("usertype");
                dob = jsonObject.getString("createddate");
                //usertype = jsonObject.getString("usertype");




                if(userid!=0)
                {


                    edit.putString("loksabhaconstituency",loksabhaconstituency);
                    edit.putInt("Userid",userid);
                    edit.putString("Address",address);
                    edit.putString("MobileNo",mobilenumber);
                    edit.putString("Password",password);
                    edit.putString("name",name);
                    edit.putString("Emailid",emailid);
                    edit.putInt("Countryid",countryid);
                    edit.putInt("stateid",stateid);
                    edit.putInt("Cityid",cityid);
                    edit.putString("Cityname",cityname);
                    edit.putString("pin",pin);
                    edit.putString("constituency",constituency);
                    edit.putString("File",file);
                    edit.putString("aadhaarnumber",aadhaarnumber);
                    edit.putString("voterid",voterid);
                    edit.putString("usertype","user");
                    edit.putInt("pinid",pinid);
                    edit.putInt("constituencyid",constituencyid);
                    edit.putInt("wardid",wardid);
                    edit.putString("ward",ward);
                    edit.putString("districtname",districtname);
                    edit.putString("gender",gender);
                    edit.putString("dob",dob);
                    edit.commit();
                    Intent i=new Intent(User_login.this,UserDashboard.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    new AlertDialog.Builder(User_login.this).setTitle("Invalid Login")
                            .setMessage(getString(R.string.valid_username_and_password))
                            .setCancelable(true)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            }).show();
                }




            } catch (JSONException e) {
                e.printStackTrace();
                new AlertDialog.Builder(User_login.this).setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
                //Toast.makeText(User_login.this,"No Internet Connection...",Toast.LENGTH_SHORT).show();

            }


        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


                User_login.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
