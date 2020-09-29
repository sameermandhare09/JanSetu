package cssl.dialstar;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import cssl.dialstar.representative_activity.LogIn_Now;
import cssl.dialstar.representative_activity.MainActivity;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DeviceOnline;
import cssl.dialstar.representative_util.NetworkChangeReceiver;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_activity.User_login;
import cssl.dialstar.user_utils.ConfigUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
//import user.dialstar.cssl.dialstaruser.user_activity.Tab_Activity;


public class SplashScreen extends AppCompatActivity {


    private static final String LOCALE_KEY = "localekey";
    private static final String HINDI_LOCALE = "hi";
    private static final String MARATHI_LOCALE = "mr";
    private static final String ENGLISH_LOCALE = "en_US";
    private Locale locale;
    private static final int TIME = 2 * 1000;// interest4 seconds
  //  private PrefManager prefManager;
  SharedPreferences share;
  SharedPreferences.Editor edit;
    String username,email;
    String firebaseId="";
    int userid=0;
    ConfigUser configUser;
    String usertype="",constituency="",districtname="",loksabhaconstituency,gender,dob;
    JSONObject json;
    int mlaid,partyid,representativeid,constituencyid=0;
    String name;
    String address;

    String pin;
    String mobileno;
    String emailid;
    String area;
    String ward;

    String password;

    String partyname;

    String file;
    String countryname;
    String statename;
    String cityname;
    TextView version;
    int versionCode=0;
    String versionName="";
    Config config = new Config();
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor ;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
  //  PrefManager prefManager;


    private static final String LOG_TAG = "CheckNetworkStatus";
    private NetworkChangeReceiver receiver;
    IntentFilter filter;
    private boolean isConnected = false;
    LinearLayout linearLayout ;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        linearLayout = (LinearLayout)findViewById(R.id.lnrsplash);
        version=(TextView) findViewById(R.id.version);
        share = PreferenceManager.getDefaultSharedPreferences(this);

        edit=share.edit();
        String localeString = share.getString(LOCALE_KEY, "");

        Resources resources = getResources();

        if(share.getString(LOCALE_KEY, "").equals(HINDI_LOCALE) ){
            linearLayout.setBackground(getDrawable(R.drawable.splashscreen1));

            locale = new Locale(HINDI_LOCALE);
            //edit.putString(LOCALE_KEY, ENGLISH_LOCALE);
        }else if(share.getString(LOCALE_KEY, "").equals(MARATHI_LOCALE)){
            linearLayout.setBackground(getDrawable(R.drawable.splashscreen1));

            locale = new Locale(MARATHI_LOCALE);
        }

        else {
            linearLayout.setBackground(getDrawable(R.drawable.splashscreen1));
            locale = new Locale(ENGLISH_LOCALE);
            // edit.putString(LOCALE_KEY, HINDI_LOCALE);
        }
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);

        getBaseContext().getResources().updateConfiguration(configuration,
                getBaseContext().getResources().getDisplayMetrics());
        //recreate();

        // version code and versoin name
          versionCode = BuildConfig.VERSION_CODE;
          versionName = BuildConfig.VERSION_NAME;

        Log.e("versionCode:-", String.valueOf(versionCode));
        Log.e("versionName:-", String.valueOf(versionName));
        version.setText("Version "+versionName+"."+versionCode);
        usertype=share.getString("usertype","");
       // Log.i("UserType",usertype);

        //Internet Broadcast receiver

         filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);


        receiver = new NetworkChangeReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent) {




                // super.onReceive(context, intent);

                Bundle results = getResultExtras(true);
                String state = results.getString("Connected");
                Log.i("Connected",String.valueOf(state));


               // login();

                JSONObject json = new JSONObject();
                try {
                    json.put("versionname", versionName);
                    json.put("versioncode", versionCode);

                    Log.d("sending Json :- ", String.valueOf(json));
                    new HttpRequestTaskReq(json.toString()).execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        };

        registerReceiver(receiver, filter);

       // receiver.setResult();


        //you are online
        if(DeviceOnline.isDeviceOnline(SplashScreen.this))
        {

            JSONObject json = new JSONObject();
            try {
                json.put("versionname", versionName);
                json.put("versioncode", versionCode);

                Log.d("sending Json :- ", String.valueOf(json));
                new HttpRequestTaskReq(json.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else
        {
            //you are offline.
            Toast.makeText(SplashScreen.this, " No internet Connection ", Toast.LENGTH_SHORT).show();


        }



        configUser=new ConfigUser();

        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mlaPref.edit();
       // Log.i("UserType",usertype);

        displayFirebaseRegId();


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



    }
/*

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        registerReceiver(receiver, filter);
    }
*/

    @Override
    protected void onDestroy() {
        Log.v(LOG_TAG, "onDestory");
        super.onDestroy();

        unregisterReceiver(receiver);

    }




    // new 24 april....
    public void login()

    {

        if(DeviceOnline.isDeviceOnline(this)) {


            if (usertype.equalsIgnoreCase("mla") || usertype.equalsIgnoreCase("mp")) {
                //mla_login

                if (DeviceOnline.isDeviceOnline(this)) {

                    username = share.getString("mobileno", "");
                    password = share.getString("password", "");
                    json = new JSONObject();
                    try {
                        json.put("mobileno", username);
                        json.put("password", password);
                        json.put("emailid", email);
                        json.put("androidid", firebaseId);
                        // Log.i("Mla json",json.toString());
                        new HttpRequestMlaTask(json.toString()).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    //you are offline.
                    Toast.makeText(this, " No internet Connection ", Toast.LENGTH_SHORT).show();


                }


            } else if (usertype.equalsIgnoreCase("user")) {
                //


                if (DeviceOnline.isDeviceOnline(this)) {

                    username = share.getString("MobileNo", "");
                    password = share.getString("Password", "");

                    //if (username.length() > 0 && password.length() > 0) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("emailid", email);
                            jsonObject.put("password", password);
                            jsonObject.put("mobileno", username);
                            jsonObject.put("androidid", firebaseId);
//                    json.put("androidid",firebaseId);
                            // Log.i("User json",jsonObject.toString());
                            new HttpRequestTask(jsonObject.toString(), username, password).execute();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                   // }


                } else {
                    //you are offline.
                    Toast.makeText(this, " No internet Connection ", Toast.LENGTH_SHORT).show();


                }

            } else if (usertype.equalsIgnoreCase("representative"))

            {

                if (DeviceOnline.isDeviceOnline(this)) {

                    username = share.getString("mobileno", "");
                    password = share.getString("password", "");
                    json = new JSONObject();
                    try {
                        json.put("mobileno", username);
                        json.put("password", password);
                        json.put("emailid", email);
                        json.put("androidid", firebaseId);
                        //new HttpRequestMlaTask(json.toString()).execute();
                        new HttpRequestMlaTask(json.toString()).execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    //you are offline.
                    Toast.makeText(this, " No internet Connection ", Toast.LENGTH_SHORT).show();

                }

            } else {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_in_right);

                    }
                }, TIME);
            }
        }else{

            //you are offline.
            Toast.makeText(this, " No internet Connection ", Toast.LENGTH_SHORT).show();


        }
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        firebaseId = regId;
        editor.putString("firebaseId",regId);
        editor.commit();
       // Log.e("Dial star firebase", "Firebase reg id: " + regId);


    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    private class HttpRequestMlaTask extends AsyncTask<Void, Void,String > {
        String json;


        public HttpRequestMlaTask(String json)
        {
            this.json=json;
            Log.d("login ip json ",json);

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            //final String url = config.getMla_login();
            final String url = config.representativeremoteurl+"admin/app/mla_login/";
           // Log.d("representative login url",url);

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


            String responceName = greeting;
           // Log.e("responceName:- ",responceName);



            try {
                JSONObject jsonObject = new JSONObject(responceName);


                representativeid = jsonObject.getInt("representativeid");
                usertype = jsonObject.getString("usertype");
                if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){

                   // Toast.makeText(SplashScreen.this,"MLA Login Successful...",Toast.LENGTH_SHORT).show();
                    //
                    mlaid = jsonObject.getInt("mlaid");
                    name = jsonObject.getString("name");
                    address = jsonObject.getString("address");
                    pin = jsonObject.getString("pincode");
                    mobileno = jsonObject.getString("mobileno");
                    emailid = jsonObject.getString("emailid");
                    area = jsonObject.getString("area");
                    ward = jsonObject.getString("ward");
                    password = jsonObject.getString("password");
                    partyname = jsonObject.getString("partyname");
                    file = jsonObject.getString("file");
                    countryname = jsonObject.getString("countryname");
                    statename = jsonObject.getString("statename");
                    cityname = jsonObject.getString("cityname");
                    partyid = jsonObject.getInt("partyid");
                    representativeid = jsonObject.getInt("representativeid");
                    usertype = jsonObject.getString("usertype");
                    constituency = jsonObject.getString("constituencyname");
                    constituencyid = jsonObject.getInt("constituencyid");
                    districtname = jsonObject.getString("districtname");
                    loksabhaconstituency = jsonObject.getString("subject");

                    gender = jsonObject.getString("type");
                    dob = jsonObject.getString("createddate");

                    editor.putString("gender",gender);
                    editor.putString("dob",dob);

                    //stoared in shared preference
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
                    editor.putString("countryname",countryname);
                    editor.putString("statename",statename);
                    editor.putString("cityname",cityname);
                    editor.putInt("partyid",partyid);
                    editor.putInt("representativeid",representativeid);
                    editor.putString("constituency",constituency);
                    editor.putInt("constituencyid",constituencyid);
                    editor.putString("usertype","mla");
                    editor.putString("typename",usertype);
                    editor.putString("districtname",districtname);
                    //Log.d("partyid", String.valueOf(partyid));

                    editor.commit();


                    Intent i=new Intent(SplashScreen.this,MlaHome.class);

                    startActivity(i);
                    finish();

                }else  if (representativeid ==0 ) {
                    Toast.makeText(SplashScreen.this,"Login Unsuccessful...",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SplashScreen.this, LogIn_Now.class);
                    startActivity(intent);
                    finish();
                } else {//if (usertype.equalsIgnoreCase("representative"))

                    // Toast.makeText(SplashScreen.this,"MLA Login Successful...",Toast.LENGTH_SHORT).show();
                    //
                    mlaid = jsonObject.getInt("mlaid");
                    name = jsonObject.getString("name");
                    address = jsonObject.getString("address");
                    pin = jsonObject.getString("pincode");
                    mobileno = jsonObject.getString("mobileno");
                    emailid = jsonObject.getString("emailid");
                    area = jsonObject.getString("area");
                    ward = jsonObject.getString("ward");
                    password = jsonObject.getString("password");
                    partyname = jsonObject.getString("partyname");
                    file = jsonObject.getString("file");
                    countryname = jsonObject.getString("countryname");
                    statename = jsonObject.getString("statename");
                    cityname = jsonObject.getString("cityname");
                    partyid = jsonObject.getInt("partyid");
                    representativeid = jsonObject.getInt("representativeid");
                    usertype = jsonObject.getString("usertype");
                    constituency = jsonObject.getString("constituencyname");
                    constituencyid = jsonObject.getInt("constituencyid");
                    districtname = jsonObject.getString("districtname");
                    loksabhaconstituency = jsonObject.getString("subject");

                    gender = jsonObject.getString("type");
                    dob = jsonObject.getString("reopendate");

                    editor.putString("gender",gender);
                    editor.putString("dob",dob);

                    //stoared in shared preference
                    editor.putString("loksabhaconstituency",loksabhaconstituency);
                    editor.putString("typename",usertype);
                    editor.putInt("mlaid", mlaid);
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
                    editor.putString("countryname",countryname);
                    editor.putString("statename",statename);
                    editor.putInt("constituencyid",constituencyid);
                    editor.putString("constituency",constituency);
                    editor.putString("cityname",cityname);
                    editor.putInt("partyid",partyid);
                    editor.putInt("representativeid",representativeid);
                    editor.putString("usertype","representative");
                    editor.putString("districtname",districtname);

                   // Log.d("partyid", String.valueOf(partyid));

                    editor.commit();


                    Intent i=new Intent(SplashScreen.this,MlaHome.class);

                    startActivity(i);
                    finish();


                }


            } catch (JSONException e) {
                e.printStackTrace();

Log.e(getString(R.string.Error)," No internet Connection ");
               // Toast.makeText(SplashScreen.this, " No internet Connection ", Toast.LENGTH_SHORT).show();
              //  login();

           /*     new AlertDialog.Builder(SplashScreen.this)
                        .setTitle("Error")
                        .setMessage("No Internet Connection...")
                        .setCancelable(true)
                        .setPositiveButton("Refresh", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                login();
                                dialogInterface.cancel();
                            }
                        }).show();*/

              /*  Intent intent=new Intent(SplashScreen.this, LogIn_Now.class);
                startActivity(intent);
                finish();*/

            }



           // progressDialog.dismiss();
        }

    }

    public class HttpRequestTask extends AsyncTask<String, Void, String> {

        String jsonstr;
        String mobileno;
        String password;

        public HttpRequestTask(String jsonstr,String mobileno,String password) {
            this.jsonstr = jsonstr;
            this.mobileno=mobileno;
            this.password=password;
        }


        protected void onPreExecute() {
            super.onPreExecute();


        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

               // String url =configUser.getUser_login();
                String url =configUser.userremoteurl+"admin/app/user_login";
                Log.d("user loging url",url);

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
                return new String(e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            Log.e("user Log_in rsult", result);
            JSONObject jsonObject;
            int userid=0;
            String name="";
            String emailid="";
            String password="";
            String file=" ";
            String countryname=" ";
            String statename=" ";
            String cityname=" ";
            String pin=" ",constituency="",districtname="",loksabhaconstituency="";

            try {
                jsonObject =new JSONObject(result);
                userid=jsonObject.getInt("userid");
                name=jsonObject.getString("name");
                emailid=jsonObject.getString("emailid");
                password=jsonObject.getString("password");
                file=jsonObject.getString("file");
                countryname=jsonObject.getString("countryname");
                statename=jsonObject.getString("statename");
                cityname=jsonObject.getString("cityname");
                pin=jsonObject.getString("pincode");
                constituency = jsonObject.getString("constituencyname");
                districtname = jsonObject.getString("districtname");
                loksabhaconstituency = jsonObject.getString("subject");
                gender = jsonObject.getString("usertype");
                dob = jsonObject.getString("createddate");

                edit.putString("gender",gender);
                edit.putString("dob",dob);
                edit.putInt("Userid",userid);
                edit.putString("MobileNo",mobileno);
                edit.putString("Password",password);
                edit.putString("name",name);
                edit.putString("Emailid",emailid);
                edit.putString("Password",password);
                edit.putString("Countryname",countryname);
                edit.putString("Statename",statename);
                edit.putString("pin",pin);
                edit.putString("constituency",constituency);
                edit.putString("Cityname",cityname);
                edit.putString("File",file);
                edit.putString("districtname",districtname);
                edit.putString("loksabhaconstituency",loksabhaconstituency);
                edit.commit();


                if(userid!=0)
                {

                    Intent i=new Intent(SplashScreen.this,UserDashboard.class);
                    finish();
                    startActivity(i);
                    finish();
                }
                else
                {
                    Intent i=new Intent(SplashScreen.this,User_login.class);

                    startActivity(i);
                    finish();
                }




            } catch (JSONException e) {
                e.printStackTrace();


                Toast.makeText(SplashScreen.this, " No internet Connection ", Toast.LENGTH_SHORT).show();
               // login();


            }


        }


    }

   /* @Override
    protected void onStop() {
        super.onStop();
    }*/
    public void stop(){
        super.onStop();
    }

    // version code check..

    private class HttpRequestTaskReq extends AsyncTask<Void, Void, String> {
        String json;


        public HttpRequestTaskReq(String json) {
            this.json = json;
            //Log.e("update json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            // final String url = config.getGetAllConstituencyByStateId();
            final String url = config.representativeremoteurl + "admin/app/updateAndroidVersion";

            Log.e("update url",url);
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

            String newfeatures="";
            String responceName = greeting;
            //Log.e("Responce udpate app",responceName.toString());
            try {
                JSONObject json = new JSONObject(greeting);
                if (json.has("newfeatures")) {
                     newfeatures=json.getString("newfeatures");
                }

                String status=json.getString("status");
                if(status.equalsIgnoreCase("No update"))
                {
                    login();


                }
                else if(status.equalsIgnoreCase(getString(R.string.Error)))
                {

                   login();
                }
                else if(status.equalsIgnoreCase("Please update version code"))
                {
                    AlertDialog dialog = new AlertDialog.Builder(SplashScreen.this)
                            .setTitle("New version available")
                            .setCancelable(false)
                            .setMessage("Please, update app to new version to continue reposting."+ "\n"
                                    +"New Features:-"+ "\n"+newfeatures)


                            .setPositiveButton("Update",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=cssl.dialstar")));
                                            SplashScreen.this.finish();
                                        }
                                    }).setNegativeButton("No Thanks",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                           login();

                                        }
                                    }).create();
                    dialog.show();

                }


            } catch (JSONException e) {
                e.printStackTrace();
               // login();
            }


            //{"newfeatures":"xyz","status":"Please update version code"}

        }
    }


    //Internet connection Broadcast receiver
 /*   public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            isNetworkAvailable(context);

            Intent intent1 = new Intent(getApplicationContext(),SplashScreen.class);

            //send broadcast
            context.sendBroadcast(intent1);

        }




        private boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if(!isConnected){
                              isConnected = true;
                               // login();

                            }
                            return true;
                        }
                    }
                }
            }

            isConnected = false;
            return false;
        }
    }*/
}
