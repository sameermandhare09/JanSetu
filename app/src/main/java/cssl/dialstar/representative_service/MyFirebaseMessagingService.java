package cssl.dialstar.representative_service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MainActivity;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_util.DatabaseHelper;
import cssl.dialstar.representative_util.Note;
import cssl.dialstar.representative_util.NotificationUtils;
import cssl.dialstar.user_activity.UserDashboard;

//import com.google.user.card.app.NotificationUtils;

/**
 * Created by catseye on 23/12/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    SharedPreferences share;
    SharedPreferences.Editor edit;

    String usertype="",username="";
    String message="",type="";
    int grievanceid=0,id=0,eventid=0,discussionid=0;

    private List<Note> notesList = new ArrayList<>();
    private List<Note> notesList1 = new ArrayList<>();
    private ArrayList<Note> notesListunread = new ArrayList<>();


    private DatabaseHelper db;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log.e(TAG, "From: " + remoteMessage.getFrom());
        share = PreferenceManager.getDefaultSharedPreferences(this);
        edit = share.edit();
        usertype=share.getString("usertype","");
        username=share.getString("name","");
        db = new DatabaseHelper(this);


        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
           // Intent resultIntent = new Intent(getApplicationContext(),MlaHome.class);
           // resultIntent.putExtra("message", remoteMessage.getNotification().getBody());
            Map<String, String> params = remoteMessage.getData();
            JSONObject json = new JSONObject(params);
            try {
                String title = json.getString("title");
                message = json.getString("message");
                type = json.getString("type");
                id= json.getInt("id");
                //grievanceid = json.getInt("grievanceid");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            //showNotificationMessage(getApplicationContext(), "JanSetu", remoteMessage.getNotification().getBody(), "");

            showNotificationMessage(getApplicationContext(), "JanSetu", message, "");

            //handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            //Log.e(TAG, "Data Payload: " + remoteMessage.getData());
            Map<String, String> params = remoteMessage.getData();


            try {
                Random rm=new Random();
                int notificationid=rm.nextInt();
                JSONObject json = new JSONObject(params);

                String title = json.getString("title");
                message = json.getString("message");
                type = json.getString("type");
                id= json.getInt("id");


                if(type.equalsIgnoreCase("Discussion"))
                {
                    if(message.equalsIgnoreCase("Discussion")){
                        updateMyActivity(getApplicationContext(),message);

                    }
                    else
                        {
                        discussionid = id;

                        //create database

                        // inserting note in db and getting
                        // newly inserted note id
                        long id = db.insertDiscussion(message,discussionid,usertype,username,0);
                        Log.e("insert Event id",id+"");

                        // call notification counter broadcaster
                        updateMyActivity(getApplicationContext(),message);
                        showNotification(notificationid,discussionid,0,0);
                    }



                }
                else if(type.equalsIgnoreCase("Event")){
                    eventid = id;

                    //create database

                    // inserting note in db and getting
                    // newly inserted note id
                    long id = db.insertEvent(message,eventid,usertype,username,0);
                    Log.e("insert Event id",id+"");

                    // call notification counter broadcaster
                    updateMyActivity(getApplicationContext(),message);
                    showNotification(notificationid,0,eventid,0);

                }


                else if(type.equalsIgnoreCase("Grievance")){
                    grievanceid = id;


                    // inserting note in db and getting
                    // newly inserted note id
                    long id = db.insertNote(message,grievanceid,usertype,username,0);
                    //Log.e("insert message id",id+"");

                    // call notification counter broadcaster
                    updateMyActivity(getApplicationContext(),message);
                    showNotification(notificationid,0,0,grievanceid);


                }

              //  handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    private void showNotificationMessage(Context context, String title, String message, String timeStamp) {
        Intent resultIntent;// = new Intent(getApplicationContext(),MainActivity.class);
        if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp"))
        {

            resultIntent = new Intent(getApplicationContext(),MlaHome.class);

        }

        else if(usertype.equalsIgnoreCase("user")) {

           resultIntent = new Intent(getApplicationContext(),UserDashboard.class);
        }
        else if(usertype.equalsIgnoreCase("representative"))
        {

            resultIntent = new Intent(getApplicationContext(),MlaHome.class);

        }
        else
        {

            resultIntent = new Intent(getApplicationContext(),MainActivity.class);
        }


        resultIntent.putExtra("message", message);
        resultIntent.putExtra("grievanceid", grievanceid);


        notificationUtils = new NotificationUtils(context);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notificationUtils.showNotificationMessage(title, message, timeStamp, resultIntent);
    }



    //Notification counter Broadcaster
    // This function will create an intent. This intent must take as parameter the "unique_name" that you registered your activity with
    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("unique_name");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }


    //Notification Discussion Broadcaster
    // This function will create an intent. This intent must take as parameter the "discussion" that you registered your activity with
    static void refreshDiscussion(Context context, String message) {

        Intent intent = new Intent("discussion");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }


    public void showNotification(int notificationid ,int discussionid,int eventid,int grievanceid){



        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;


        Intent resultIntent;

        if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp"))
        {

            resultIntent = new Intent(getApplicationContext(),MlaHome.class);

        }


        else if(usertype.equalsIgnoreCase("representative"))
        {

            resultIntent = new Intent(getApplicationContext(),MlaHome.class);

        } else if(usertype.equalsIgnoreCase("user")) {



            resultIntent = new Intent(getApplicationContext(),UserDashboard.class);

        }
        else
        {

            resultIntent = new Intent(getApplicationContext(),MainActivity.class);
        }
        resultIntent.putExtra("type", type);
        resultIntent.putExtra("message", message);
        resultIntent.putExtra("discussionid", discussionid);
        resultIntent.putExtra("eventid", eventid);
        resultIntent.putExtra("grievanceid", grievanceid);

  /*      if(type.equalsIgnoreCase("Grievance")){
            grievanceid = id;
            resultIntent.putExtra("grievanceid", grievanceid);
        }else if(type.equalsIgnoreCase("Event")){
            eventid = id;
            resultIntent.putExtra("eventid", eventid);
        }else if(type.equalsIgnoreCase("Discussion")){
            discussionid = id;

            resultIntent.putExtra("discussionid", discussionid);
        }
*/


        resultIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message

            PendingIntent piResult = PendingIntent.getActivity(getApplicationContext(),notificationid,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

            // PendingIntent piResult = PendingIntent.getActivity(this, 0, resultIntent,0);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine(message);
            Notification notification;
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);

            notification = mBuilder
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("JanSetu").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("JanSetu")
                    .setContentIntent(piResult)
                    .setStyle(inboxStyle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(channelId)
                    .setContentText(message)
                    .build();
            //notification.flags |= Notification.FLAG_AUTO_CANCEL;
            //  notification.setLatestEventInfo(getApplicationContext(), "JanSetu", message, pendingNotificationIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }


            notificationManager.notify(notificationid, notification);
        } else {
            // app is in background, show the notification in notification tray


            PendingIntent piResult = PendingIntent.getActivity(getApplicationContext(),notificationid,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            inboxStyle.addLine(message);
            Notification notification;
            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    this);
            notification = mBuilder
                    .setSmallIcon(R.mipmap.ic_launcher).setTicker("JanSetu").setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle("JanSetu")
                    .setContentIntent(piResult)
                    .setStyle(inboxStyle)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setChannelId(channelId)
                    .setContentText(message)
                    .build();
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, importance);
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(notificationid, notification);
            // check for image attachment

        }

    }
}
