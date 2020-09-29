package cssl.dialstar.representative_activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.NotesAdapter;
import cssl.dialstar.representative_fragment.Dashboard;
import cssl.dialstar.representative_fragment.Discussion;
import cssl.dialstar.representative_fragment.EditProfile;
import cssl.dialstar.representative_fragment.EventDetails;
import cssl.dialstar.representative_fragment.Grievance_desc;
import cssl.dialstar.representative_fragment.IssueView;
import cssl.dialstar.representative_util.BadgeDrawable;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DatabaseHelper;
import cssl.dialstar.representative_util.MyDividerItemDecoration;
import cssl.dialstar.representative_util.Note;
import cssl.dialstar.representative_util.NotificationUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import cssl.dialstar.R;

public class MlaHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    Fragment fragment = new Fragment();
    ImageView imageView;
    TextView textViewUserName, textViewMailid,txtViewuserType;
    TextView txtCount;
    String name="",typename="";
    String file;
    String emailid, usertype,mobileno;
    String message="activity",notificationtype=null;
    private PendingIntent pendingIntentam;
    private PendingIntent pentdingIntentpm;
    int count=0;
    LayerDrawable icon;



    private List<Note> notesList = new ArrayList<>();
    private List<Note> notesListdisply = new ArrayList<>();
    private List<Note> notesList1 = new ArrayList<>();
    private ArrayList<Note> notesListunread = new ArrayList<>();
    private DatabaseHelper db;
    Config config = new Config();
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;

    private BroadcastReceiver broadcastReceiver;

    private static final String LOCALE_KEY = "localekey";
    private static final String HINDI_LOCALE = "hi";
    private static final String MARATHI_LOCALE = "mr";
    private static final String ENGLISH_LOCALE = "en_US";
    private Locale locale ;
    String localLag ;

    int mlaid, representativeid,grievanceid=0,eventid=0,discussionid=0;
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

       // grievanceid = getIntent().getIntExtra("grievanceid",0);
        notificationtype = intent.getStringExtra("type");
        grievanceid = intent.getIntExtra("grievanceid",0);//bundle.getInt("grievanceid");
         message = intent.getStringExtra("message");
        eventid = intent.getIntExtra("eventid",0);
        discussionid = intent.getIntExtra("discussionid",0);
         //Log.e("grievance id=",grievanceid+""+message);

        if(notificationtype!=null){
            if(notificationtype.equalsIgnoreCase("Grievance") && grievanceid>0){
                fragment =  Grievance_desc.newInstance(grievanceid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(notificationtype.equalsIgnoreCase("Event") && eventid>0){
                fragment =  EventDetails.newInstance(eventid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(notificationtype.equalsIgnoreCase("Discussion") && discussionid>0){
                fragment =  new Discussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }


        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mla_home);



        //clear notifications
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo_white);
       // toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.notifications));

        //setTitle(getResources().getString(R.string.app_name));
        //Typeface typeface = ResourcesCompat.getFont(this, R.font.alegreyasans_regular);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu menuNav = navigationView.getMenu();


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));



        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_mla_home);

        Bundle bundle  = getIntent().getExtras();
        if(bundle!=null) {
            grievanceid = bundle.getInt("grievanceid");
            eventid = bundle.getInt("eventid");
            discussionid = bundle.getInt("discussionid",0);

            String message = bundle.getString("message");
           // Log.i("Grievance ID",grievanceid+""+message);


           //

            //Toast.makeText(this, "grievanceid" + grievanceid + "", Toast.LENGTH_SHORT).show();
        }

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


        imageView = (ImageView) navHeaderView.findViewById(R.id.imageView1);
        //imageView.setImageResource(R.drawable.profile_image);
        textViewUserName = navHeaderView.findViewById(R.id.txtView_userName);
        txtViewuserType= navHeaderView.findViewById(R.id.txtView_userType);
        textViewMailid = navHeaderView.findViewById(R.id.textView_mailid);


        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);

        editor = mlaPref.edit();
        mlaid = mlaPref.getInt("mlaid", 0);
        name = mlaPref.getString("name", "");
        typename = mlaPref.getString("typename", "");
       // file = mlaPref.getString("file", file);
        usertype = mlaPref.getString("usertype", "");

        representativeid = mlaPref.getInt("representativeid", representativeid);
        emailid = mlaPref.getString("emailid", emailid);
        mobileno = mlaPref.getString("mobileno",mobileno);



        textViewUserName.setText(name);
        textViewMailid.setText(mobileno);
        txtViewuserType.setText(typename);
//        Log.d("file",file);




        refreshProfilePic();

        if (usertype.equalsIgnoreCase("representative")) {
            if(grievanceid>0){
                fragment =  Grievance_desc.newInstance(grievanceid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(eventid>0){
                fragment =  EventDetails.newInstance(eventid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else{
                fragment = new IssueView();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }

        } else if (usertype.equalsIgnoreCase("mla") || usertype.equalsIgnoreCase("mp")) {
            if(grievanceid>0){
                fragment = Grievance_desc.newInstance(grievanceid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(eventid>0){
                fragment =  EventDetails.newInstance(eventid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else{
                fragment = new Dashboard();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }
        }



   /*     // register notification broadcast receiver
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
        this.registerReceiver(mDiscussionReceiver, new IntentFilter("discussion"));

*/

    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mMessageReceiver);

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
    }

    //register your activity onResume()
    @Override
    public void onResume() {
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


        // register notification broadcast receiver
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));


    }


    // notification broadcast receiver
    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");
            // updateMyActivity(UserDashboard.this,message);

            if(message.equalsIgnoreCase("Discussion")){



                    Fragment fragment=getVisibleFragment();
                    if (fragment instanceof Discussion){
                        ((Discussion) fragment).onRefresh();


                    }




            }else{
                invalidateOptionsMenu();
            }

            //do other stuff here
        }
    };



    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MlaHome.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public  boolean onCreateOptionsMenu(Menu menu) {

        notesList1.clear();
        notesList.clear();
        notesListdisply.clear();
        notesListunread.clear();

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        if(mlaPref.getString(LOCALE_KEY, "").equals(HINDI_LOCALE)||mlaPref.getString(LOCALE_KEY, "").equals(MARATHI_LOCALE)) {

            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.translate_white));
        }
        else {

            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.translate_black));
        }


        MenuItem itemCart = menu.findItem(R.id.notification);
         icon = (LayerDrawable) itemCart.getIcon();
        db = new DatabaseHelper(this);
        notesList1.addAll(db.getAllNotes());
        notesList1.addAll(db.getAllEvents());
        notesList1.addAll(db.getAllDiscussion());
        for(int i=0;i<notesList1.size();i++){
            if(usertype.equalsIgnoreCase(notesList1.get(i).getUsertype())&&
                    name.equalsIgnoreCase(notesList1.get(i).getUsername())){

               // if(notesList.size()<10){
                    notesList.add(notesList1.get(i));
                //}


            }

        }



        /*List<Date> dateList = new ArrayList<>();
        for (int i = 0; i < notesList.size(); i++) {
            Date date = new Date(notesList.get(i).getTimestamp());
            dateList.add(date);
        }
        Collections.sort(dateList, new Comparator<Date>() {
            @Override
            public int compare(Date o1, Date o2) {
                return o1.compareTo(o2);
            }
        });


        Log.e("date ",dateList.toString());*/
        Collections.sort(notesList, new Sortbyroll());
        Log.e("sorted noteslist",notesList.toString());
        if(notesList.size()>0){
            for(int i=0;i<notesList.size();i++){
                if(notesList.get(i).getRead()==0){
                    notesListunread.add(notesList.get(i));
                }
               // Log.e("read is "+i+" ",notesList.get(i).getRead()+"");
            }
        }


        if(notesList.size()>10){
            for(int i=0;i<10;i++){

                notesListdisply.add(notesList.get(i));

            }
        }else {
            notesListdisply.addAll(notesList);
        }


        int notificationcount = notesListunread.size();

        if(notificationcount>0){
            setBadgeCount(this, icon, notificationcount+"");
        }

        return true;
    }

    class Sortbyroll implements Comparator<Note>
    {
        // Used for sorting in ascending order of
        // roll number


        @Override
        public int compare(Note o1, Note o2) {
            Date date1 = new Date(o1.getTimestamp());
            Date date2 = new Date(o2.getTimestamp());

            return date2.compareTo(date1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public boolean onOptionsItemSelected(final MenuItem item) {
        Fragment fragment = null;

        //notesList.addAll(db.getAllNotes());

        switch (item.getItemId()) {

            case R.id.exit:
               // exit();

                final AlertDialog.Builder dialog;
                dialog = new   AlertDialog.Builder(MlaHome.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialogLayout = layoutInflater.inflate(R.layout.languagealert, null);
                dialog.setTitle(getResources().getString(R.string.select_language));
                TextView txtenglish = dialogLayout.findViewById(R.id.txtenglish);
                TextView txthindi = dialogLayout.findViewById(R.id.txthindi);
                TextView txtmarathi = dialogLayout.findViewById(R.id.txtmarathi);


                txtenglish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources resources = getResources();
                        locale = new Locale(ENGLISH_LOCALE);
                        localLag = "en_US";
                        editor.putString(LOCALE_KEY, ENGLISH_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_black));
                        editor.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        MlaHome.this.setContentView(R.layout.activity_user_dashboard);


                        finish();
                        startActivity(getIntent());


                    }
                });
                txthindi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources resources = getResources();
                        locale = new Locale(HINDI_LOCALE);
                        localLag = "hi";
                        editor.putString(LOCALE_KEY, HINDI_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_white));
                        editor.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        MlaHome.this.setContentView(R.layout.activity_user_dashboard);


                        finish();
                        startActivity(getIntent());

                    }
                });
                txtmarathi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Resources resources = getResources();
                        locale = new Locale(MARATHI_LOCALE);
                        localLag = "mr";
                        editor.putString(LOCALE_KEY,MARATHI_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_white));
                        editor.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        MlaHome.this.setContentView(R.layout.activity_user_dashboard);


                        finish();
                        startActivity(getIntent());

                    }
                });


                dialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.setView(dialogLayout);
                dialog.setCancelable(true);
                dialog.show();





               /* Resources resources = getResources();

                if(mlaPref.getString(LOCALE_KEY, ENGLISH_LOCALE).equals(HINDI_LOCALE)){
                    locale = new Locale(ENGLISH_LOCALE);
                    localLag = "en_US";
                    editor.putString(LOCALE_KEY, ENGLISH_LOCALE);

                    item.setIcon(getResources().getDrawable(R.drawable.translate_black));


                } else {
                    locale = new Locale(HINDI_LOCALE);
                    localLag = "hi";
                    editor.putString(LOCALE_KEY, HINDI_LOCALE);

                    item.setIcon(getResources().getDrawable(R.drawable.translate_white));

                }


                editor.apply();

                Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);

                getBaseContext().getResources().updateConfiguration(configuration,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_mla_home);

                //recreate();



                finish();
                startActivity(getIntent());

*/
                return true;
            case R.id.notification:
                db.updateNote(notesListunread);
                setBadgeCount(this, icon, "0");

                //invalidateOptionsMenu();

                // inflate the layout of the popup window

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.content_main, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                LinearLayout mainLayout = (LinearLayout)
                        findViewById(R.id.activity_main_layout);

                RecyclerView recyclerView;
                NotesAdapter mAdapter;
                TextView textView;
                recyclerView = (RecyclerView) popupView.findViewById(R.id.recycler_view2);
                textView  =(TextView)popupView.findViewById(R.id.empty_notes_view);

                if(notesList.size()>0){
                    textView.setVisibility(View.GONE);

                }



                mAdapter = new NotesAdapter(this, notesListdisply,popupWindow);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
                recyclerView.setAdapter(mAdapter);

                //popupWindow.setBackgroundDrawable();
                popupWindow.showAtLocation(mainLayout, Gravity.NO_GRAVITY, 500, 150);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        notesList1.clear();
                        notesList.clear();
                        notesListunread.clear();
                        return true;
                    }
                });
               popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {

                   /* notesList1.clear();
                    notesList.clear();
                    notesListunread.clear();*/
                }
            });


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);


    }




    public void refreshProfilePic()
    {

        name = mlaPref.getString("name", "");
        textViewUserName.setText(name);
        if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
            Picasso.with(getApplicationContext())
                    //.load(config.getMlaprofilephoto()+mlaid+".png")
                    .load(config.representativeremoteurl1+"BJPJanhit/uploads/representativeprofilephoto/"+ mlaid + ".png")//"http://192.168.1.24/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)

                    .placeholder(R.drawable.profile_image)
                    .into(imageView);

           // Log.d("filePicturePath",config.getMlaprofilephoto()+mlaid+".png");
        }
        //Picasso.with(getApplicationContext()).invalidate(config.getMlaprofilephoto()+mlaid+".png");

        else if(!usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
            Picasso.with(getApplicationContext())
                   // .load(config.getRepresentativeprofilephoto()+representativeid+".png")
                    .load(config.representativeremoteurl1+"BJPJanhit/uploads/representativeprofilephoto/"+representativeid+".png")

                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)

                    .placeholder(R.drawable.profile_image)
                    .into(imageView);
            //Log.d("filePicturePath",config.getRepresentativeprofilephoto()+representativeid+".png");
        }

    }

    @Override
    public void onBackPressed() {

    FragmentManager fragmentManager = getSupportFragmentManager();


        if(grievanceid>0){
            grievanceid=0;


            if (fragmentManager.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
               // fragmentManager.popBackStack();

                Fragment fragment=getVisibleFragment();
                if (fragment instanceof Discussion){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                             fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else  if(fragment instanceof EventDetails){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else  if(fragment instanceof Grievance_desc){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else{
                    getSupportFragmentManager().popBackStack();
                }


            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                //super.onBackPressed();
                if (usertype.equalsIgnoreCase("representative")) {
                    fragment = new IssueView();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        // fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }


                } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                    fragment = new Dashboard();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }

                }

            }

        }else if(eventid>0){
            eventid=0;


            if (fragmentManager.getBackStackEntryCount() > 0) {
                Log.i("MainActivity", "popping backstack");
               // fragmentManager.popBackStack();

                Fragment fragment=getVisibleFragment();
                if (fragment instanceof Discussion){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else  if(fragment instanceof EventDetails){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else  if(fragment instanceof Grievance_desc){

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }

                    }


                }else{
                    getSupportFragmentManager().popBackStack();
                }



            } else {
                Log.i("MainActivity", "nothing on backstack, calling super");
                //super.onBackPressed();
                if (usertype.equalsIgnoreCase("representative")) {
                    fragment = new IssueView();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }


                } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                    fragment = new Dashboard();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }

                }

            }

        }
        else{
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {

                getSupportFragmentManager().popBackStack();


            } else {
                exit();
            }
        }




    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

            if(grievanceid>0){
                grievanceid=0;
                FragmentManager fragmentManager = getSupportFragmentManager();

                    if (usertype.equalsIgnoreCase("representative")) {
                        fragment = new IssueView();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }


                    } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                        fragment = new Dashboard();
                        if (fragment != null) {

                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment_container, fragment);
                            //   fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                            //
                        }



                }

            }else if(eventid>0){
                eventid=0;
                FragmentManager fragmentManager = getSupportFragmentManager();
                //fragmentManager.popBackStack();
                if (usertype.equalsIgnoreCase("representative")) {
                    fragment = new IssueView();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }


                } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                    fragment = new Dashboard();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }



                }

            } else if(discussionid>0){
                discussionid=0;
                FragmentManager fragmentManager = getSupportFragmentManager();

                if (usertype.equalsIgnoreCase("representative")) {
                    fragment = new IssueView();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }


                } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                    fragment = new Dashboard();
                    if (fragment != null) {

                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        //   fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }



                }

            }
            else {


                if (usertype.equalsIgnoreCase("representative")) {
                    fragment = new IssueView();
                } else if (usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")) {
                    fragment = new Dashboard();
                }

            }

        } else if (id == R.id.nav_editProfile) {

            fragment = new EditProfile();

        }
       /* else if (id == R.id.nav_shareapp) {

            Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
            String app_url = " https://play.google.com/store/apps/details?id=cssl.dialstar";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));

        }*/

        else if (id == R.id.nav_logOut) {

            new AlertDialog.Builder(this)

                    .setMessage(getResources().getString(R.string.logout))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("usertype",typename);
                                jsonObject.put("userid",representativeid);
                                Log.e("UserLogout",jsonObject.toString());
                                new HttpRequestAppUserLogout(jsonObject.toString()).execute();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            dialog.cancel();


                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();
                        }
                    }).show();

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            //
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        drawer.closeDrawer(GravityCompat.START);
        drawer.computeScroll();
        return true;
    }



    private void exit() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);



        alertDlg.setMessage(getResources().getString(R.string.want_exit));

        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the user to choose one of the options



        alertDlg.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {



                    public void onClick(DialogInterface dialog, int id) {

                        finish();


                    }

                }

        );

        alertDlg.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {

                // We do nothing

            }

        });

        alertDlg.show();
    }

    private class HttpRequestAppUserLogout extends AsyncTask<Void, Void,String > {
        String json;
        Dialog progressDialog;


        public HttpRequestAppUserLogout(String json)
        {
            this.json=json;
            // Log.d("login Json:", String.valueOf(json));


        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(MlaHome.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog



        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            // final String url = config.getMla_login();
            final String url = config.representativeremoteurl+"admin/app/appUserLogout/";

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


            if(greeting!=null){
                Log.e(" login responce:- ",greeting);
                if(greeting.equalsIgnoreCase("Success")){
                    String lang = mlaPref.getString(LOCALE_KEY, "");
                    editor.clear();
                    editor.commit();

                    editor.putString(LOCALE_KEY,lang);
                    editor.commit();

                    Intent intent=new Intent(MlaHome.this, LogIn_Now.class);
                    startActivity(intent);
                    MlaHome.this.finish();
                }else{

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MlaHome.this).create();
                    alertDialog.setTitle("Error..!");
                    alertDialog.setMessage(getResources().getString(R.string.Logout_unsuccessful));
                    alertDialog.setButton(android.app.AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }




            }



            progressDialog.dismiss();
        }

    }

}
