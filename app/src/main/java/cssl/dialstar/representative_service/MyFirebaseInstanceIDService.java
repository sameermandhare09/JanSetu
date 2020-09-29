package cssl.dialstar.representative_service;

/**
 * Created by User on 22-Dec-17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*import com.example.user.card.util.ConfigUser;*/


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();


    SharedPreferences mlaPref;
    int userid=0;
    String usertype="";
    String androidid="";
    JSONObject json;
    SharedPreferences.Editor editor ;
    Config config;
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mlaPref.edit();
        config = new Config();
        // Saving reg id to shared preferences
        storeRegIdInPref(refreshedToken);

        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        androidid = token;
        editor.putString("firebaseId",token);
        editor.commit();


         usertype=mlaPref.getString("usertype","");

        if (usertype.equalsIgnoreCase("mla")){
             userid =mlaPref.getInt("mlaid",0);
        }
        else if (usertype.equalsIgnoreCase("representative")){
            userid =mlaPref.getInt("representativeid",0);
        }
        json = new JSONObject();
        try {
            json.put("userid",userid);
            json.put("usertype",usertype);
            json.put("androidid",androidid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new HttpFireBaseIdUpdate(json.toString()).execute();

    }

    private void storeRegIdInPref(String token) {
        SharedPreferences MlaRegipref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = MlaRegipref.edit();
        editor.putString("regId", token);
        editor.commit();

    }

    private class HttpFireBaseIdUpdate extends AsyncTask<Void, Void,String > {
        String json;
        ProgressDialog progressDialog;

        public HttpFireBaseIdUpdate(String json)
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
           // final String url = config.getUpdateAndroidId();
            final String url = config.representativeremoteurl+"admin/app/updateAndroidId";

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
           // Log.e("responceName:- ", responceName);
            if(responceName.equalsIgnoreCase("Success")){
                Toast.makeText(MyFirebaseInstanceIDService.this, "your Application id Successfully updated", Toast.LENGTH_SHORT).show();

            }
            else if(responceName.equalsIgnoreCase(getApplicationContext().getString(R.string.Error))){
                Toast.makeText(MyFirebaseInstanceIDService.this, "your Application id Not updated", Toast.LENGTH_SHORT).show();

            }


        }
        }

    }