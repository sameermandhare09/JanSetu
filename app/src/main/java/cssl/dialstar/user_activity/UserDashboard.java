package cssl.dialstar.user_activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import cssl.dialstar.representative_util.BadgeDrawable;
import cssl.dialstar.representative_util.DatabaseHelper;
import cssl.dialstar.representative_util.MyDividerItemDecoration;
import cssl.dialstar.representative_util.Note;
import cssl.dialstar.user_fragment.Add;
import cssl.dialstar.user_fragment.Discussion;
import cssl.dialstar.user_fragment.EditProfilee;
import cssl.dialstar.user_fragment.EventDesc;
import cssl.dialstar.user_fragment.Events;
import cssl.dialstar.user_fragment.Grievance;
import cssl.dialstar.user_fragment.GrievanceDetails;
import cssl.dialstar.user_fragment.Newss;
import cssl.dialstar.user_fragment.Survey;
import cssl.dialstar.user_utils.ConfigUser;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_fragment.EditProfilee;
import user.dialstar.cssl.dialstaruser.user_fragment.Home;
import user.dialstar.cssl.dialstaruser.user_utils.Config;*/

public class UserDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment;
    //String title = "";
    SharedPreferences share;
    SharedPreferences.Editor edit;
    Toolbar toolbar;

    private static final String LOCALE_KEY = "localekey";
    private static final String HINDI_LOCALE = "hi";
    private static final String MARATHI_LOCALE = "mr";
    private static final String ENGLISH_LOCALE = "en_US";
    private Locale locale ;
    String localLag ;

    Bitmap bm1 = null;
    String name="",usertype="";
    TextView name1;
    TextView mobilenumber1;
    private ImageView imageView;



    String mobileno,notificationtype=null;

    int userid=0;

    private ViewPager viewPager;
    static TabLayout tabLayout;
    ConfigUser config;
    String file;

    private List<Note> notesList = new ArrayList<>();
    private List<Note> notesListdisply = new ArrayList<>();
    private List<Note> notesList1 = new ArrayList<>();
    private ArrayList<Note> notesListunread = new ArrayList<>();
    private DatabaseHelper db;
    int grievanceid=0,eventid=0,discussionid=0;
    LayerDrawable icon;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

       // notificationtype = "";
        // grievanceid = getIntent().getIntExtra("grievanceid",0);
        grievanceid = intent.getIntExtra("grievanceid",0);//bundle.getInt("grievanceid");
        notificationtype = intent.getStringExtra("type");
        eventid = intent.getIntExtra("eventid",0);
        discussionid = intent.getIntExtra("discussionid",0);
        //Log.e("grievance id=",grievanceid+"");
        if(notificationtype!=null){
            if(notificationtype.equalsIgnoreCase("Grievance") && grievanceid>0){
                fragment =  GrievanceDetails.newInstance(grievanceid);
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
                tabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(notificationtype.equalsIgnoreCase("Event") && eventid>0){

                tabLayout.getTabAt(3).select();
                int i= tabLayout.getSelectedTabPosition();
                fragment =  EventDesc.newInstance(eventid);
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }else if(notificationtype.equalsIgnoreCase("Discussion") && discussionid>0){

                tabLayout.getTabAt(2).select();
                //int i= tabLayout.getSelectedTabPosition();
                fragment =  new Discussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    //   fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        share= PreferenceManager.getDefaultSharedPreferences(this);
        edit=share.edit();


         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setLogo(R.drawable.logo_white);
        setTitle(getResources().getString(R.string.app_name));

              DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //toolbar.setTitle(getString(R.string.app_name));




        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));



       // db = new DatabaseHelper(this);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        name1 = (TextView)hView.findViewById(R.id.name1);
        mobilenumber1=(TextView)hView.findViewById(R.id.mobilenumber1);
        imageView=(ImageView)hView.findViewById(R.id.imageView);






        // toolbar.setTitle(R.string.app_name);


        name = share.getString("name","");
        usertype = share.getString("usertype","");

        name1.setText(name);
        mobileno = share.getString("MobileNo", "");
        mobilenumber1.setText(mobileno);
        userid=share.getInt("Userid",0);
        file = share.getString("File", "");
        String firebaseid= share.getString("firebaseId","");
        Log.e("firebaseId",firebaseid);
        config = new ConfigUser();

       /* viewPager=(ViewPager) findViewById(R.id.viewPager2);
        setupViewPager(viewPager);*/
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(Html.fromHtml("<b>"+getString(R.string.add_Grievance)+"</b>")));
        tabLayout.addTab(tabLayout.newTab().setText(Html.fromHtml("<b>"+getString(R.string.poll)+"</b>")));
        tabLayout.addTab(tabLayout.newTab().setText(Html.fromHtml("<b>"+getString(R.string.discussion)+"</b>")));
        tabLayout.addTab(tabLayout.newTab().setText(Html.fromHtml("<b>"+getString(R.string.events)+"</b>")));
       /* tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);*/
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
       // tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.holo_blue_light));
        //tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.holo_blue_light)));

        //clear notifications
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        //clear the fragment back stack
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fm = getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //getActivity().getSupportFragmentManager().popBackStack();
        }

        Bundle bundle  = getIntent().getExtras();
        if(bundle!=null) {
            grievanceid = bundle.getInt("grievanceid");
            String message = bundle.getString("message");
            eventid = bundle.getInt("eventid");
            discussionid = bundle.getInt("discussionid");
           // Log.i("Grievance ID",grievanceid+""+message);


            //Toast.makeText(this, "grievanceid" + grievanceid + "", Toast.LENGTH_SHORT).show();
        }


        fragment=new Add();

        replaceFragment(fragment);



        //tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

              setCurrentTabFragment(tab.getPosition());

             tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.holo_blue_light));
            //    tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4081"));

                tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.holo_blue_light));


            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
                tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.holo_blue_light));
                //tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF4081"));

                tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.holo_blue_light));

            }
        });



        refreshProfilePic();
      /*  Picasso.with(getApplicationContext())
                //.load(config.getUserprofilephoto()+userid+".png")
                .load(config.userremoteurl1+"dialstar_uat/uploads/userprofilephoto/"+ userid + ".png")

                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)

                .placeholder(R.drawable.profile_image)
                .into(imageView);*/

        if(grievanceid>0) {
            fragment = GrievanceDetails.newInstance(grievanceid);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));
            if (fragment != null) {
                //FragmentManager fragmentManager = fragment.getFragmentManager();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        }else if(eventid>0){

            tabLayout.getTabAt(3).select();
            int i= tabLayout.getSelectedTabPosition();
            fragment =  EventDesc.newInstance(eventid);
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrame, fragment);
                //   fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //
            }

        }else if(discussionid>0){

            tabLayout.getTabAt(2).select();

      /*      tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));*/
            fragment =  new Discussion();
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainFrame, fragment);
                //   fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                //
            }

        } else {
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.mainFrame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                getSupportActionBar();

            }
        }

      /*  for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
*/


        Fragment fragment=getVisibleFragment();
        if (fragment instanceof EventDesc){

            tabLayout.getTabAt(3).select();
            // ((Discussion) fragment).onRefresh();


        }

    }

    //Must unregister onPause()
    @Override
    protected void onPause() {
        super.onPause();
        this.unregisterReceiver(mMessageReceiver);

    }

    //register your activity onResume()
    @Override
    public void onResume() {
        super.onResume();
        this.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));


        //updateMyActivity(UserDashboard.this,"");



    }


    public static void setEventTabposition(){


            tabLayout.getTabAt(3).select();
            // ((Discussion) fragment).onRefresh();

    }

    public static void setDiscussionTabposition(){


        tabLayout.getTabAt(2).select();
        // ((Discussion) fragment).onRefresh();

    }

    public static void setBlankTab(){
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#000000")));


    }
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
            }else {
                invalidateOptionsMenu();
            }

            //do other stuff here
        }
    };

/*

    //refresh the discussion at this broadcast receiver(message receiving receiver)
    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mDiscussionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("discussion");
            // updateMyActivity(UserDashboard.this,message);

         //refresh the Discussion fragment

            int tabposision = tabLayout.getSelectedTabPosition();
            if(tabposision==2){

                Fragment fragment=getVisibleFragment();
                 if (fragment instanceof Discussion){
                    ((Discussion) fragment).onRefresh();


                }

            }




        }
    };*/
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = UserDashboard.this.getSupportFragmentManager();
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


    public boolean onCreateOptionsMenu(Menu menu) {

        notesList.clear();
        notesListdisply.clear();
        notesList1.clear();
        notesListunread.clear();


        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);


        if(share.getString(LOCALE_KEY, "").equals(HINDI_LOCALE)||share.getString(LOCALE_KEY, "").equals(MARATHI_LOCALE)) {

            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.translate_white));
        }
        else {

            menu.getItem(1).setIcon(getResources().getDrawable(R.drawable.translate_black));
        }


        MenuItem itemCart = menu.findItem(R.id.notification);
        //MenuItem itemCart1 = menu.findItem(R.id.exit);

        icon = (LayerDrawable) itemCart.getIcon();
        db = new DatabaseHelper(this);
        notesList1.addAll(db.getAllNotes());
        notesList1.addAll(db.getAllEvents());
        notesList1.addAll(db.getAllDiscussion());
        for(int i=0;i<notesList1.size();i++){
            if(usertype.equalsIgnoreCase(notesList1.get(i).getUsertype())&&
                    name.equalsIgnoreCase(notesList1.get(i).getUsername())){


                //if(notesList.size()<10){
                    notesList.add(notesList1.get(i));
               // }


            }

        }
        Collections.sort(notesList, new Sortbyroll());
        Log.e("sorted noteslist",notesList.toString());
        if(notesList.size()>0){

            for(int i=0;i<notesList.size();i++){
                if(notesList.get(i).getRead()==0){
                    notesListunread.add(notesList.get(i));
                }
                //Log.e("read is "+i+" ",notesList.get(i).getRead()+"");
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

        switch (item.getItemId()) {
            case R.id.exit:

                //final Dialog dialog = new Dialog(UserDashboard.this);
                final AlertDialog.Builder dialog;
                dialog = new   AlertDialog.Builder(UserDashboard.this);
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
                        edit.putString(LOCALE_KEY, ENGLISH_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_black));
                        edit.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        UserDashboard.this.setContentView(R.layout.activity_user_dashboard);


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
                        edit.putString(LOCALE_KEY, HINDI_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_white));
                        edit.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        UserDashboard.this.setContentView(R.layout.activity_user_dashboard);


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
                        edit.putString(LOCALE_KEY,MARATHI_LOCALE);

                        item.setIcon(getResources().getDrawable(R.drawable.translate_white));
                        edit.apply();

                        Configuration configuration = resources.getConfiguration();
                        configuration.setLocale(locale);

                        getBaseContext().getResources().updateConfiguration(configuration,
                                getBaseContext().getResources().getDisplayMetrics());
                        UserDashboard.this.setContentView(R.layout.activity_user_dashboard);


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




/*


                Resources resources = getResources();

                if(share.getString(LOCALE_KEY, ENGLISH_LOCALE).equals(HINDI_LOCALE)){
                    locale = new Locale(ENGLISH_LOCALE);
                    localLag = "en_US";
                    edit.putString(LOCALE_KEY, ENGLISH_LOCALE);

                    item.setIcon(getResources().getDrawable(R.drawable.translate_black));


                } else {
                    locale = new Locale(HINDI_LOCALE);
                    localLag = "hi";
                    edit.putString(LOCALE_KEY, HINDI_LOCALE);

                    item.setIcon(getResources().getDrawable(R.drawable.translate_white));

                }


                edit.apply();

              Configuration configuration = resources.getConfiguration();
                configuration.setLocale(locale);

                getBaseContext().getResources().updateConfiguration(configuration,
                        getBaseContext().getResources().getDisplayMetrics());
                this.setContentView(R.layout.activity_user_dashboard);

                //recreate();



                finish();
               startActivity(getIntent());

*/
                return true;

                
            case R.id.notification:
                // inflate the layout of the popup window


                db.updateNote(notesListunread);
                setBadgeCount(this, icon, "0");

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.content_main, null);
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true; // lets taps outside the popup also dismiss it
                final  PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
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
                popupWindow.showAtLocation(mainLayout, Gravity.NO_GRAVITY, 500, 170);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View popupView, MotionEvent event) {
                        popupWindow.dismiss();
                        notesList1.clear();
                        notesListdisply.clear();
                        notesList.clear();
                        notesListunread.clear();
                        return true;
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {

                  /*      notesList1.clear();
                        notesList.clear();
                        notesListunread.clear();*/
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCurrentTabFragment(int tabPosition){
        switch (tabPosition)
        {
            case 0 :
                fragment=new Add();
                replaceFragment(fragment);
                break;
            case 1 :
                fragment=new Survey();
                replaceFragment(fragment);
                break;
            case 2 :
                fragment=new Discussion();
                replaceFragment(fragment);
                break;
            case 3 :
                fragment=new Events();
                replaceFragment(fragment);
                break;

        }
    }
    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }
    private void setupViewPager(ViewPager viewPager) {
       ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Add(), "Add Grievance");
        adapter.addFragment(new Survey(), "Poll");
        adapter.addFragment(new Discussion(), "Discussion");
        adapter.addFragment(new Newss(), "News");
        adapter.addFragment(new Events(), "Events");
        viewPager
                .setAdapter(adapter);

    }
    public void updateFragment()
    {
        fragment =new EditProfilee();
        if (fragment != null) {
            tabLayout.setSelected(false);

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.mainFrame, fragment);
            fragmentTransaction.commit();
            getSupportActionBar();
            getSupportActionBar();
                   // getActionBar().setTitle(R.string.app_name);
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        Fragment fragment = null;
       /* if (id == R.id.nav_tab_activity) {

            fragment = new Add();
        } */   if (id == R.id.nav_my_grievance) {
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));


            fragment=new Grievance();
           // title = "JanSetu";
           // setTitle(title);
            setTitle(getString(R.string.app_name));

        }


        else if (id == R.id.nav_my_edit_profile) {
           // tabLayout.setSelected(false);
            tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
            tabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));

            fragment=new EditProfilee();


            setTitle(getString(R.string.app_name));

        }else if (id == R.id.nav_log_out) {
            new AlertDialog.Builder(UserDashboard.this)
                    .setMessage(getString(R.string.logout))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("usertype",usertype);
                                jsonObject.put("userid",userid);
                                Log.e("UserLogout",jsonObject.toString());
                                new HttpRequestAppUserLogout(jsonObject.toString()).execute();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            dialog.cancel();
                         /*   edit.clear();
                            edit.commit();
                            Intent intent=new Intent(UserDashboard.this,User_login.class);
                            finish();
                            startActivity(intent);
                            dialog.cancel();*/

                        }
                    })

                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.cancel();

                        }
                    }).show();

        }
        if (fragment != null) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.mainFrame, fragment);
            fragmentTransaction.commit();
            //getSupportActionBar().setTitle(title);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            exit();
        }

    }


    public void refreshProfilePic(){
        String imgaepath =config.userremoteurl1+"BJPJanhit/uploads/userprofilephoto/"+ userid + ".png" ;

        Log.e("imagepath",imgaepath);
        Picasso.with(getApplicationContext())

                //.load(config.getUserprofilephoto()+userid+".png")
                .load(imgaepath)

                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)

                .placeholder(R.drawable.profile_image)
                .into(imageView);
        name = share.getString("name","");
        name1.setText(name);

    }
    private void exit() {

        AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);



        alertDlg.setMessage(getResources().getString(R.string.want_exit)+" ?");

        alertDlg.setCancelable(false); // We avoid that the dialong can be cancelled, forcing the user to choose one of the options



        alertDlg.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {



                    public void onClick(DialogInterface dialog, int id) {

                      //  UserDashboard.super.onBackPressed();
                      /*  SplashScreen splashScreen = new SplashScreen();
                        splashScreen.stop();*/

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

        alertDlg.create().show();
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

            progressDialog = new Dialog(UserDashboard.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog



        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            // final String url = config.getMla_login();
            final String url = config.userremoteurl+"admin/app/appUserLogout/";

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

                    localLag = share.getString(LOCALE_KEY, "");
                    edit.clear();
                    edit.commit();



                     //edit=share.edit();
                    if(ENGLISH_LOCALE.equalsIgnoreCase(localLag)){
                        //locale = new Locale(ENGLISH_LOCALE);
                        edit.putString(LOCALE_KEY, ENGLISH_LOCALE);



                    } else  if(HINDI_LOCALE.equalsIgnoreCase(localLag) ){
                       // locale = new Locale(HINDI_LOCALE);
                        edit.putString(LOCALE_KEY, HINDI_LOCALE);

                    } else  if(MARATHI_LOCALE.equalsIgnoreCase(localLag) ){
                        // locale = new Locale(HINDI_LOCALE);
                        edit.putString(LOCALE_KEY, MARATHI_LOCALE);

                    }

                    edit.apply();
                    Intent intent=new Intent(UserDashboard.this,User_login.class);
                    finish();
                    startActivity(intent);
                    UserDashboard.this.finish();
                }else{

                    final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(UserDashboard.this).create();
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
