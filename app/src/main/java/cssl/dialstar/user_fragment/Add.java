package cssl.dialstar.user_fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cssl.dialstar.R;
import cssl.dialstar.user_activity.Thanks;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_adapter.AddAdapter;
import cssl.dialstar.user_utils.CategoryPojo;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
import cssl.dialstar.user_utils.GPSTracker;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

public class Add extends Fragment  {
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    public static final int MEDIA_TYPE_VIDEO = 2;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public int PIC_CODE=0;
    private String userChoosenTask;
    int griveanceid=0;

    ArrayList<Dialstar> mypojo1;
    ArrayList<Dialstar> mypojo2;
    ArrayList<Dialstar> mypojo3;
    ArrayList<Dialstar> mypojo4;
    ArrayList<Dialstar> mypojo5;
    private Uri fileUri; // file url to store image/video
 VideoView vdo1;
    AddAdapter customAdapter;
    Fragment fragment;
    int partyid,representativeid,categoryid,flag=0;
     int grievanceid;

    ConfigUser config;

    private ImageView img11;
    private ImageView img22;
    private ImageView img33;
    private ImageView btnselectimage,btnselectvideo;
    private ImageView video;
    GridView gridcategory;

    File file1,file2,file3;
    File imgfile1,imgfile2,imgfile3;
    int flag1=0,flag2=0,typeid=0;
    VideoView videoview1,videoview2,videoview3;
    ImageView videoimg1,videoimg2,videoimg3;
    int clickcount=0,clickcount1=5,deleteimgclick=5;
    public static final int code = 1111;

    private LinearLayout linearimage,videolinear;
    private LinearLayout linearlayout,linearlayout1;
    RelativeLayout relativevideo1,relativevideo2,relativevideo3;
    ImageView imgdelete1,imgdelete2,imgdelete3;

    ImageView delete1,delete2,delete3;
    ImageView img1,img2,img3;
    int imageclick=0,imagedelete=0;



    public Spinner sprepresent,spnonelected,sprepresentativelist;


    public RadioButton polticalparty;
    public RadioButton elective;
    public RadioButton nonelective;
    int flagradiobutton=0;

    public TextView txtmla,txtrepresent,txtnonelected,txtrepresentativelist;
    LinearLayout lnrnonelected,lnrelected,lnrrepresentative;

    public Button submit;
    public TextView clear;
    public Spinner category;
    static  int id=0;
    int userid=0;
    String Name="";
    String description="",categoryname="",grievancename="",
            loksabhaconstituency="",representativename="",partyname="",address="";
    String name="";
    TextInputLayout txtname;

    ArrayList<String>imagelist = new ArrayList<String>();
    String imageurlarray[] = new String[3];


   // ArrayList<Bitmap> arrayList;

    Bitmap[] imagearray= new Bitmap[3];

    static final String appDirectoryName = "JanSetu";
    static final File imageRoot = new File(Environment.getExternalStorageDirectory().getPath(), appDirectoryName);


    static final File videoRoot = new File(Environment.getExternalStorageDirectory().getPath(), appDirectoryName);

    TextView txtback;
    ArrayList<String> file;
    ArrayList<CategoryPojo> categoryPojos ;
    CategoryAdapter categoryAdapter;
    String griveanceimage="";
    String grievancelocationAddress;
    Double latitude=0.0, longitude=0.0;
    int constituencyid=0;
    RecyclerView recyclercategory;
    RecyclerView.LayoutManager layoutManager;


    TextInputLayout txtcategory,txtpoliticalparty,
            txtrepresentativeparty,txtelectedrepresentative,txtrepresentativetype,
            txtrepresentative;
    public EditText edgrievancename,edmsg,edpoliticalparty,
            edrepresentativeparty,edelectedrepresentative,edrepresentativetype,
            edrepresentative;
    SpinnerDialog spinnerDialogcategory,spinnerDialogpoliticalparty,
            spinnerDialogrepresentativeparty,spinnerDialogrepresentative,
            spinnerDialogrepresentativetype,
            spinnerDialogelectedrepresentative;
    LinearLayout lnrcategory;


    TextView edcategory;
    SharedPreferences share;
    SharedPreferences.Editor edit;

    LinearLayout lrpoliticalparty,sublrpoliticalparty,lrelected,sublrelected,lrrepresentative,sublrrepresentative;

    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 5;


    public Add() {
        // Required empty public constructor
    }


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    //static ArrayList<String> ARG_PARAM4;
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";
    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";
    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";
    private static final String ARG_PARAM11 = "param11";
    private static final String ARG_PARAM12 = "param12";
    private static final String ARG_PARAM13 = "param13";
    private static final String ARG_PARAM14 = "param14";
    String electived="";

    public static Add newInstance(String param1, String param2,
                                  String param3,ArrayList<String> param4,
                                  String param5,String param6,String param7,
                                  int param8,int param9,int param10,
                                  int param11,int param12, String param13,int param14) {
        Add fragment = new Add();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putStringArrayList(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putInt(ARG_PARAM8, param8);
        args.putInt(ARG_PARAM9, param9);
        args.putInt(ARG_PARAM10, param10);
        args.putInt(ARG_PARAM11, param11);
        args.putInt(ARG_PARAM12, param12);
        args.putString(ARG_PARAM13, param13);
        args.putInt(ARG_PARAM14, param14);

        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //category,grievancename,description,files,acknowledgedby
            categoryname = getArguments().getString(ARG_PARAM1);
            grievancename = getArguments().getString(ARG_PARAM2);
            description = getArguments().getString(ARG_PARAM3);
            //files = getArguments().getString(ARG_PARAM4);
            imagelist = getArguments().getStringArrayList(ARG_PARAM4);
            Log.d("imagelist",imagelist+"");
            representativename = getArguments().getString(ARG_PARAM5);
            partyname = getArguments().getString(ARG_PARAM6);
            address = getArguments().getString(ARG_PARAM7);
            categoryid =getArguments().getInt(ARG_PARAM8);
            partyid =getArguments().getInt(ARG_PARAM9);
            representativeid =getArguments().getInt(ARG_PARAM10);
            grievanceid =getArguments().getInt(ARG_PARAM11);
            flag = getArguments().getInt(ARG_PARAM12);
            electived = getArguments().getString(ARG_PARAM13);
            typeid = getArguments().getInt(ARG_PARAM14);


        }

    }


    @Override
    public void onResume() {
        super.onResume();

        polticalparty.setActivated(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        imageRoot.mkdirs();
        videoRoot.mkdirs();
        id=0;
        View rootview;
        //arrayList=new ArrayList<Bitmap>();
        rootview= inflater.inflate(R.layout.fragment_add, container, false);

        mypojo1 = new ArrayList<Dialstar>();
        //imagelist = new ArrayList<String>();

        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit=share.edit();
        constituencyid = share.getInt("constituencyid",0);
        loksabhaconstituency = share.getString("loksabhaconstituency","");



        recyclercategory =(RecyclerView)rootview.findViewById(R.id.recyclercategory);
        layoutManager = new GridLayoutManager(getContext(),3);
        recyclercategory.setLayoutManager(layoutManager);


        relativevideo1 = (RelativeLayout)rootview.findViewById(R.id.relativevideo1);
        relativevideo2 = (RelativeLayout)rootview.findViewById(R.id.relativevideo2);
        relativevideo3 = (RelativeLayout)rootview.findViewById(R.id.relativevideo3);


        imgdelete1 = (ImageView) rootview.findViewById(R.id.imgdelete1);
        imgdelete2 = (ImageView) rootview.findViewById(R.id.imgdelete2);
        imgdelete3 = (ImageView) rootview.findViewById(R.id.imgdelete3);

        delete1 = (ImageView) rootview.findViewById(R.id.delete1);
        delete2 = (ImageView) rootview.findViewById(R.id.delete2);
        delete3 = (ImageView) rootview.findViewById(R.id.delete3);

        img1 = (ImageView) rootview.findViewById(R.id.img1);
        img2 = (ImageView) rootview.findViewById(R.id.img2);
        img3 = (ImageView) rootview.findViewById(R.id.img3);

        lnrcategory = (LinearLayout) rootview.findViewById(R.id.lnrcategory);


       // linearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        videoview1 = (VideoView)rootview.findViewById(R.id.videoview1);
        videoview2 = (VideoView)rootview.findViewById(R.id.videoview2);
        videoview3 = (VideoView)rootview.findViewById(R.id.videoview3);

        videoimg1 = (ImageView) rootview.findViewById(R.id.videoimg1);
        videoimg2 = (ImageView) rootview.findViewById(R.id.videoimg2);
        videoimg3 = (ImageView) rootview.findViewById(R.id.videoimg3);

        btnselectimage=(ImageView) rootview.findViewById(R.id.btnselectimage);
        btnselectvideo =(ImageView) rootview.findViewById(R.id.btnselectvideo);
        img11=(ImageView) rootview.findViewById(R.id.img1);
        img22=(ImageView) rootview.findViewById(R.id.img2);
     //   img3=(ImageView) rootview.findViewById(R.id.img3);
        edgrievancename=(EditText) rootview.findViewById(R.id.edgrievancename);
        edmsg=(EditText) rootview.findViewById(R.id.edmsg);
        txtback = rootview.findViewById(R.id.back);






        config=new ConfigUser();




       // edcategory = (EditText)rootview.findViewById(R.id.edcategory);
        edcategory = (TextView) rootview.findViewById(R.id.edcategory);
        edpoliticalparty = (EditText)rootview.findViewById(R.id.edpoliticalparty);
        edrepresentativeparty = (EditText)rootview.findViewById(R.id.edrepresentativeparty);//edelectedrepresentative
        edelectedrepresentative = (EditText)rootview.findViewById(R.id.edelectedrepresentative);//edelectedrepresentative
        edrepresentativetype = (EditText)rootview.findViewById(R.id.edrepresentativetype);
        edrepresentative = (EditText)rootview.findViewById(R.id.edrepresentative);

        txtcategory = (TextInputLayout)rootview.findViewById(R.id.txtcategory);
        txtpoliticalparty = (TextInputLayout)rootview.findViewById(R.id.txtpoliticalparty);
        txtrepresentativeparty = (TextInputLayout)rootview.findViewById(R.id.txtrepresentativeparty);//txtelectedrepresentative
        txtelectedrepresentative = (TextInputLayout)rootview.findViewById(R.id.txtelectedrepresentative);//txtrepresentativetype
        txtrepresentativetype = (TextInputLayout)rootview.findViewById(R.id.txtrepresentativetype);//
        txtrepresentative = (TextInputLayout)rootview.findViewById(R.id.txtrepresentative);//




        submit=(Button) rootview.findViewById(R.id.submit);
        clear=(TextView) rootview.findViewById(R.id.clear);


        sprepresent=(Spinner) rootview.findViewById(R.id.sprepresent);
        spnonelected =(Spinner) rootview.findViewById(R.id.spnonelected);
        sprepresentativelist =(Spinner) rootview.findViewById(R.id.sprepresentativelist);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        polticalparty=(RadioButton)  rootview.findViewById(R.id.politicalparty);
        elective=(RadioButton) rootview.findViewById(R.id.elective);
        nonelective=(RadioButton) rootview.findViewById(R.id.nonelective);

        txtrepresent=(TextView) rootview.findViewById(R.id.txtrepresent);
        txtnonelected=(TextView) rootview.findViewById(R.id.txtnonelected);
        txtrepresentativelist=(TextView) rootview.findViewById(R.id.txtrepresentativelist);

        linearimage=(LinearLayout) rootview.findViewById(R.id.linearimage);
        linearlayout=(LinearLayout) rootview.findViewById(R.id.linearlayout);
        linearlayout1=(LinearLayout) rootview.findViewById(R.id.linearlayout1);
        lnrelected=(LinearLayout) rootview.findViewById(R.id.lnrelected);
        lnrnonelected=(LinearLayout) rootview.findViewById(R.id.lnrnonelected);//

        //lrpoliticalparty,lrelected,lrrepresentative
        lrpoliticalparty=(LinearLayout) rootview.findViewById(R.id.lrpoliticalparty);//
        sublrpoliticalparty=(LinearLayout) rootview.findViewById(R.id.sublrpoliticalparty);//
        lrelected=(LinearLayout) rootview.findViewById(R.id.lrelected);//
        sublrelected=(LinearLayout) rootview.findViewById(R.id.sublrelected);//
        lrrepresentative=(LinearLayout) rootview.findViewById(R.id.lrrepresentative);//
        sublrrepresentative=(LinearLayout) rootview.findViewById(R.id.sublrrepresentative);//

        //lnrpoliticalparty=(LinearLayout) rootview.findViewById(R.id.lnrpoliticalparty);//lnrrepresentative
        lnrrepresentative=(LinearLayout) rootview.findViewById(R.id.lnrrepresentative);//lnrrepresentative


        txtname=(TextInputLayout) rootview.findViewById(R.id.txtgrievancename);
        //lnrpoliticalparty.setVisibility(View.GONE);
        lnrnonelected.setVisibility(View.VISIBLE);
        lnrelected.setVisibility(View.GONE);

        categoryPojos = new ArrayList<>();
        CategoryPojo categoryPojo1 = new CategoryPojo();
        categoryPojo1.setId(R.drawable.helth_icon);
        categoryPojo1.setName(getResources().getString(R.string.Health));
        categoryPojo1.setCategoryid(4);
        categoryPojos.add(categoryPojo1);

        CategoryPojo categoryPojo2 = new CategoryPojo();
        categoryPojo2.setId(R.drawable.education_icon);
        categoryPojo2.setName(getResources().getString(R.string.Education));
        categoryPojo2.setCategoryid(7);
        categoryPojos.add(categoryPojo2);

        CategoryPojo categoryPojo3 = new CategoryPojo();
        categoryPojo3.setId(R.drawable.unemployment_icon);
        categoryPojo3.setName(getResources().getString(R.string.Unemployment));
        categoryPojo3.setCategoryid(8);
        categoryPojos.add(categoryPojo3);

        CategoryPojo categoryPojo4 = new CategoryPojo();
        categoryPojo4.setId(R.drawable.infrastruture_icon);
        categoryPojo4.setName(getResources().getString(R.string.Infrastructure));
        categoryPojo4.setCategoryid(9);
        categoryPojos.add(categoryPojo4);

        CategoryPojo categoryPojo5 = new CategoryPojo();
        categoryPojo5.setId(R.drawable.women_empowerment_icon);
        categoryPojo5.setName(getResources().getString(R.string.Women_Empowerment));
        categoryPojo5.setCategoryid(10);
        categoryPojos.add(categoryPojo5);

        CategoryPojo categoryPojo6 = new CategoryPojo();
        categoryPojo6.setId(R.drawable.central_govement_icon);
        categoryPojo6.setName(getResources().getString(R.string.Central_Government_Schemes));
        categoryPojo6.setCategoryid(11);
        categoryPojos.add(categoryPojo6);

        CategoryPojo categoryPojo7 = new CategoryPojo();
        categoryPojo7.setId(R.drawable.state_goverment_icon);
        categoryPojo7.setName(getResources().getString(R.string.State_Government_Schemes));
        categoryPojo7.setCategoryid(12);
        categoryPojos.add(categoryPojo7);

        CategoryPojo categoryPojo8 = new CategoryPojo();
        categoryPojo8.setId(R.drawable.lawandorder_icon);
        categoryPojo8.setName(getResources().getString(R.string.Law_And_Order));
        categoryPojo8.setCategoryid(13);
        categoryPojos.add(categoryPojo8);

        CategoryPojo categoryPojo9 = new CategoryPojo();
        categoryPojo9.setId(R.drawable.other_icon);
        categoryPojo9.setName(getResources().getString(R.string.Others));
        categoryPojo9.setCategoryid(6);
        categoryPojos.add(categoryPojo9);

        CategoryPojo categoryPojo10 = new CategoryPojo();
        categoryPojo10.setId(R.drawable.electricity_icon);
        categoryPojo10.setName(getResources().getString(R.string.electricity));
        categoryPojo10.setCategoryid(2);
        categoryPojos.add(categoryPojo10);

        CategoryPojo categoryPojo11 = new CategoryPojo();
        categoryPojo11.setId(R.drawable.roads_icon);
        categoryPojo11.setName(getResources().getString(R.string.road));
        categoryPojo11.setCategoryid(3);
        categoryPojos.add(categoryPojo11);

        CategoryPojo categoryPojo12 = new CategoryPojo();
        categoryPojo12.setId(R.drawable.water_supply_icon);
        categoryPojo12.setName(getResources().getString(R.string.watersupply));
        categoryPojo12.setCategoryid(1);
        categoryPojos.add(categoryPojo12);

        CategoryPojo categoryPojo13 = new CategoryPojo();
        categoryPojo13.setId(R.drawable.traffic_icon);
        categoryPojo13.setName(getResources().getString(R.string.traffic));
        categoryPojo13.setCategoryid(5);
        categoryPojos.add(categoryPojo13);


        if(categoryid>0){
            id=categoryid;
            categoryAdapter = new CategoryAdapter(getContext(),categoryPojos,id);
        }else{
            categoryAdapter = new CategoryAdapter(getContext(),categoryPojos);
        }


        recyclercategory.setAdapter(categoryAdapter);




        lrpoliticalparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (sublrpoliticalparty.getVisibility() == View.VISIBLE) {
                    // Its visible
                    sublrpoliticalparty.setVisibility(View.GONE);


                } else {
                    // Either gone or invisible
                    sublrpoliticalparty.setVisibility(View.VISIBLE);

                    sublrelected.setVisibility(View.GONE);
                    sublrrepresentative.setVisibility(View.GONE);
                }

            }
        });

        lrelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sublrelected.getVisibility() == View.VISIBLE) {
                    // Its visible
                    sublrelected.setVisibility(View.GONE);



                } else {
                    // Either gone or invisible
                    sublrpoliticalparty.setVisibility(View.GONE);
                    new HttpRequestTask5().execute();
                    sublrelected.setVisibility(View.VISIBLE);
                    sublrrepresentative.setVisibility(View.GONE);
                }



            }
        });
        lrrepresentative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sublrrepresentative.getVisibility() == View.VISIBLE) {
                    // Its visible
                    sublrrepresentative.setVisibility(View.GONE);


                } else {
                    // Either gone or invisible
                    sublrpoliticalparty.setVisibility(View.GONE);
                    sublrelected.setVisibility(View.GONE);
                    sublrrepresentative.setVisibility(View.VISIBLE);
                    new HttpRequestMasterUser().execute();
                }



            }
        });



            edgrievancename.setText(grievancename);
           edmsg.setText(description);


            imagelist();
           if(typeid>0){
               //nonelective.callOnClick();
               nonelective.setChecked(true);
               // new HttpRequestNonElective().execute();
               new HttpRequestMasterUser().execute();
               sublrrepresentative.setVisibility(View.VISIBLE);


           }


        edgrievancename.addTextChangedListener(new MyTextWatcher(edgrievancename));
        edgrievancename.addTextChangedListener(new MyTextWatcher(edcategory));

        name=share.getString("name","");
       // Log.i("Name share",name);




        polticalparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flagradiobutton=1;
                  if(!view.isActivated()) {
                      lnrnonelected.setVisibility(View.VISIBLE);
                      lnrelected.setVisibility(View.GONE);
                      //lnrpoliticalparty.setVisibility(View.GONE);

                  }
                  else
                  {
                      lnrnonelected.setVisibility(View.GONE);
                      lnrelected.setVisibility(View.VISIBLE);

                      //lnrpoliticalparty.setVisibility(View.GONE);

                  }

            }
        });



        elective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagradiobutton=3;

                partyname="";
                partyid=0;
                if(!view.isActivated()) {

                    new HttpRequestTask5().execute();
                    lnrnonelected.setVisibility(View.GONE);
                    lnrelected.setVisibility(View.VISIBLE);
                    //lnrpoliticalparty.setVisibility(View.GONE);
                }
                else
                {
                    lnrnonelected.setVisibility(View.VISIBLE);
                    lnrelected.setVisibility(View.GONE);
                    //lnrpoliticalparty.setVisibility(View.GONE);
                }

            }
        });


        nonelective.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagradiobutton=2;

                partyname="";
                partyid=0;
                if(!view.isActivated()) {

                    //new HttpRequestNonElective().execute();
                    new HttpRequestMasterUser().execute();
                    lnrelected.setVisibility(View.GONE);
                    lnrnonelected.setVisibility(View.VISIBLE);

                }
                else
                {
                    lnrelected.setVisibility(View.VISIBLE);
                    lnrnonelected.setVisibility(View.GONE);
                }

            }
        });



        linearimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cameraIntent();
            }
        });

       /* btnselectimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.size()<=2 && imagelist.size()<=2) {
                    cameraIntent();
                }
                else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage("Sorry.. U can select only 3 images")
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok),null)
                            .show();
                }
            }
        });*/
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagearray[0]==null) {
                   imageclick=1;
                    cameraIntent();
                }
                else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.Remove_first_added_image))
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok),null)
                            .show();
                }
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagearray[1]==null) {
                    imageclick=2;
                    cameraIntent();
                }
                else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.Remove_first_added_image))
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok),null)
                            .show();
                }
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imagearray[2]==null) {
                    imageclick=3;
                    cameraIntent();
                }
                else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage(getResources().getString(R.string.Remove_first_added_image))
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok),null)
                            .show();
                }
            }
        });
        btnselectvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                                    5);

                        }
                    } else if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    5);

                        }
                    }else   if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    5);

                        }
                    }

                    else {
                        if(flag1==1){
                            if(videoimg1.getVisibility()==View.VISIBLE && videoimg2.getVisibility()==View.VISIBLE && videoimg3.getVisibility()==View.VISIBLE){
                                clickcount=1;
                                flag1=0;
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                                // clickcount = clickcount + 1;

                            }else {
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                                clickcount = flag1;
                            }


                        }else if(flag1==2){
                            if(videoimg1.getVisibility()==View.VISIBLE && videoimg2.getVisibility()==View.VISIBLE && videoimg3.getVisibility()==View.VISIBLE){
                                clickcount=1;
                                flag1=0;
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                                // clickcount = clickcount + 1;

                            }else {
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                                clickcount = flag1;
                            }
                        }
                        else if(flag1==3){
                            if(videoimg1.getVisibility()==View.VISIBLE && videoimg2.getVisibility()==View.VISIBLE && videoimg3.getVisibility()==View.VISIBLE){
                                clickcount=1;
                                flag1=0;
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                               // clickcount = clickcount + 1;

                            }else {
                                startActivityForResult(new Intent(
                                        MediaStore.ACTION_VIDEO_CAPTURE), code);
                                clickcount = flag1;
                            }
                        }  else if(clickcount >= 3){

                            //Toast.makeText(getContext(), "You can not upload more videos", Toast.LENGTH_SHORT).show();

                            new android.support.v7.app.AlertDialog.Builder(getActivity())
                                    .setMessage("Sorry.. U can select only 3 Videos")
                                    .setCancelable(false)
                                    .setNegativeButton(getString(R.string.ok),null)
                                    .show();
                        }
                        else {
                            startActivityForResult(new Intent(
                                    MediaStore.ACTION_VIDEO_CAPTURE), code);
                            clickcount = clickcount + 1;
                        }
                    }

            }
        });

        new HttpRequestTask().execute();
        new HttpRequestPoliticalParties().execute();
        //new HttpRequestNonElective().execute();
        new HttpRequestMasterUser().execute();
        getMyLocation();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                check();
      //submit();

            }
        });


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edgrievancename.setText("");
                edmsg.setText("");
                categoryid=0;
                partyid=0;
                representativeid=0;
                imagelist.clear();
                typeid=0;
                edpoliticalparty.setText("");
                edrepresentativeparty.setText("");
                edrepresentative.setText("");
                edrepresentativetype.setText("");
                edelectedrepresentative.setText("");

                sublrrepresentative.setVisibility(View.GONE);
                sublrelected.setVisibility(View.GONE);
                sublrpoliticalparty.setVisibility(View.GONE);
                for(int i=0;i<imagearray.length;i++){
                    imagearray[i]=null;

                }


                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Add.this).attach(Add.this).commit();



            }
        });

        if(flag==1){
            txtback.setVisibility(View.VISIBLE);
            txtback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new NewGrievance();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.mainFrame, fragment);
                        fragmentTransaction.commit();

                    }
                }
            });

        }
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.delete_this_Image))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                imagedelete=1;


                                if(imagearray[0]!=null){
                                    imagearray[0]=null;
                                    imgfile1.delete();

                                    delete1.setVisibility(View.GONE);

                                    img1.setImageDrawable(null);

                                }else{
                                    if(imageurlarray.length>0 && imageurlarray[0]!=null){
                                        String imageurl1 = imageurlarray[0];

                                        String filename=imageurl1.replace(ConfigUser.userremoteurl1+"BJPJanhit/uploads/grievancephoto/","");
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("name",filename);
                                            new HttpRequestPhotoDelete(jsonObject.toString(), imageurl1, 1).execute();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
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
        });

        delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.delete_this_Image))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                imagedelete=2;


                                if(imagearray[1]!=null){
                                    imagearray[1]=null;
                                    imgfile2.delete();

                                    delete2.setVisibility(View.GONE);
                                    img2.setImageDrawable(null);

                                }else{

                                    if(imageurlarray.length>1 && imageurlarray[1]!=null){

                                        String imageurl1 = imageurlarray[1];
                                        String filename=imageurl1.replace(ConfigUser.userremoteurl1+"BJPJanhit/uploads/grievancephoto/","");
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("name",filename);
                                            new HttpRequestPhotoDelete(jsonObject.toString(), imageurl1, 2).execute();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

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
        });

        delete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.delete_this_Image))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                imagedelete=3;


                                if(imagearray[2]!=null){
                                    imagearray[2]=null;
                                    imgfile3.delete();

                                    delete3.setVisibility(View.GONE);

                                    img3.setImageDrawable(null);

                                }else{
                                    if(imageurlarray.length>2 && imageurlarray[2]!=null){
                                        String imageurl1 = imageurlarray[2];
                                        String filename=imageurl1.replace(ConfigUser.userremoteurl1+"BJPJanhit/uploads/grievancephoto/","");
                                        JSONObject jsonObject = new JSONObject();
                                        try {
                                            jsonObject.put("name",filename);
                                            new HttpRequestPhotoDelete(jsonObject.toString(), imageurl1, 3).execute();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }


/*

                                flag2=3;

                                if(imagelist.size()>2){

                                    String imageurl1 = imagelist.get(2);
                                    String filename=imageurl1.replace("http://205.147.109.242:9090/dialstar_uat/uploads/grievancephoto/","");
                                   // Log.i("filename",filename);
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("name",filename);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    if(arrayList.size()==1){
                                        arrayList.remove(0);
                                        imagelist.remove(0);
                                    }else if(arrayList.size()==2){
                                        arrayList.remove(1);
                                        imagelist.remove(1);
                                    }else if(arrayList.size()==3){
                                        arrayList.remove(2);
                                        imagelist.remove(2);
                                    }
                                    *//*if(arrayList.size()!=0){
                                        arrayList.remove(2);
                                        imagelist.remove(2);
                                    }*//*else {

                                        new HttpRequestPhotoDelete(jsonObject.toString(), imageurl1, 2).execute();
                                    }

                                   // new HttpRequestPhotoDelete(jsonObject.toString(),imageurl1,2).execute();


                                }else {


                                 *//*   Log.e("filepath imgfile1", imgfile1.getAbsolutePath());
                                    Log.e("filepath imgfile2", imgfile2.getAbsolutePath());
                                    Log.e("filepath imgfile3", imgfile3.getAbsolutePath());*//*

                                    if (imgfile3.exists()) {

                                        //Log.i("filepath", "file exists");
                                        imgfile3.delete();
                                        if (arrayList.size() == 3){
                                            arrayList.remove(2);
                                            imagelist.remove(2);
                                        }


                                        else if (arrayList.size() == 2){
                                            arrayList.remove(1);
                                            imagelist.remove(1);
                                        }

                                        else if (arrayList.size() == 1){
                                            arrayList.remove(0);
                                            imagelist.remove(0);
                                        }


                                        if (arrayList.size() == 0)
                                            clickcount1 = 0;
                                        else
                                            clickcount1 = 2;


                                    }

                                }*/

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
        });


        imgdelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you want to delete this Video")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                    flag1=1;
                                    //Log.i("filepath",file1.getAbsolutePath());
                                    if(file1.exists()) {

                                       // Log.i("filepath","file exists");
                                        file1.delete();

                                    }


                                    relativevideo1.setVisibility(View.GONE);
                                    videoimg1.setVisibility(View.VISIBLE);

                                    dialog.cancel();
                                }

                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }
                        }).show();



            }
        });

        imgdelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Are you want to delete this Video")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                flag1=2;
                                file2.delete();
                                relativevideo2.setVisibility(View.GONE);
                                videoimg2.setVisibility(View.VISIBLE);

                                dialog.cancel();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }
                        }).show();



            }
        });

        imgdelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getContext())
                        .setMessage("Are you want to delete this Video")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                flag1=3;
                                file3.delete();
                                relativevideo3.setVisibility(View.GONE);
                                videoimg3.setVisibility(View.VISIBLE);

                                dialog.cancel();

                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }
                        }).show();





            }
        });


    /*    //clear the fragment back stack
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            //getActivity().getSupportFragmentManager().popBackStack();
        }*/

        return rootview;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {



            if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data,imageclick);
           // clickcount1 = clickcount1+1;




        }

        if (requestCode == code) {
            if (resultCode == RESULT_OK) {
          /*      Toast.makeText(getContext(), "Video Recorded", Toast.LENGTH_LONG)
                        .show();*/


                if(clickcount==1){
                    if (flag1==1){
                        flag1=0;
                        clickcount=3;
                    }
                    videoimg1.setVisibility(View.GONE);
                    videoview1.setVisibility(View.VISIBLE);
                    relativevideo1.setVisibility(View.VISIBLE);


                    final File destination = new File(videoRoot, System.currentTimeMillis() +".mp4");


                    AssetFileDescriptor videoAsset = null;
                    try {
                        videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                        FileInputStream fis = null;
                        fis = videoAsset.createInputStream();

                        FileOutputStream fos = new FileOutputStream(destination);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Uri videoUri = data.getData();

                   // Uri videoUri = destination.getAbsolutePath();

                    //String videopath = getRealPathFromURI(videoUri);
                    String videopath = destination.getAbsolutePath();

                    file1 = new File(String.valueOf(videopath));
                    Log.d("file1", String.valueOf(file1));
                    Log.d("videopath1",videopath);
                    videoview1.setVideoURI(Uri.parse(videopath));
                    // videoview1.setMediaController(new MediaController(getContext()));
                    videoview1.start();
                    videoview1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                    /*
                                     *  add media controller
                                     */
                                    MediaController mc;
                                    mc = new MediaController(getContext());;
                                    videoview1.setMediaController(mc);
                                    /*
                                     * and set its position on screen
                                     */
                                    mc.setAnchorView(videoview1);
                                }
                            });
                        }


                    });


                }else if(clickcount==2){
                    if (flag1==2){
                        flag1=0;
                        clickcount=3;
                    }
                    videoimg2.setVisibility(View.GONE);
                    videoview2.setVisibility(View.VISIBLE);
                    relativevideo2.setVisibility(View.VISIBLE);

                    final File destination = new File(videoRoot, System.currentTimeMillis() +".mp4");


                    AssetFileDescriptor videoAsset = null;
                    try {
                        videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                        FileInputStream fis = null;
                        fis = videoAsset.createInputStream();

                        FileOutputStream fos = new FileOutputStream(destination);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Uri videoUri = data.getData();

                    // Uri videoUri = destination.getAbsolutePath();

                    //String videopath = getRealPathFromURI(videoUri);
                    String videopath = destination.getAbsolutePath();

             /*       Uri videoUri = data.getData();
                    String videopath = getRealPathFromURI(videoUri);*/
                    file2 = new File(videopath);
                    Log.d("file1", String.valueOf(file2));
                    Log.d("videopath2",videopath);
                    videoview2.setVideoURI(Uri.parse(videopath));
                    // videoview2.setMediaController(new MediaController(getContext()));
                    videoview2.start();
                    videoview2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                    /*
                                     *  add media controller
                                     */
                                    MediaController mc;
                                    mc = new MediaController(getContext());;
                                    videoview2.setMediaController(mc);
                                    /*
                                     * and set its position on screen
                                     */
                                    mc.setAnchorView(videoview2);
                                }
                            });
                        }


                    });


                }else {
                    if (flag1==3){
                        flag1=0;
                        clickcount=3;
                    }
                    videoimg3.setVisibility(View.GONE);
                    videoview3.setVisibility(View.VISIBLE);
                    relativevideo3.setVisibility(View.VISIBLE);


                    final File destination = new File(videoRoot, System.currentTimeMillis() +".mp4");


                    AssetFileDescriptor videoAsset = null;
                    try {
                        videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                        FileInputStream fis = null;
                        fis = videoAsset.createInputStream();

                        FileOutputStream fos = new FileOutputStream(destination);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();



                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Uri videoUri = data.getData();

                    // Uri videoUri = destination.getAbsolutePath();

                    //String videopath = getRealPathFromURI(videoUri);
                    String videopath = destination.getAbsolutePath();


              /*      Uri videoUri = data.getData();
                    String videopath = getRealPathFromURI(videoUri);*/
                    file3 = new File(videopath);
                    Log.d("file1", String.valueOf(file3));
                    Log.d("videopath1",videopath);
                    videoview3.setVideoURI(Uri.parse(videopath));
                    // videoview3.setMediaController(new MediaController(getContext()));
                    videoview3.start();
                    videoview3.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                @Override
                                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                    /*
                                     *  add media controller
                                     */
                                    MediaController mc;
                                    mc = new MediaController(getContext());;
                                    videoview3.setMediaController(mc);
                                    /*
                                     * and set its position on screen
                                     */
                                    mc.setAnchorView(videoview3);
                                }
                            });
                        }


                    });

                }


            }
        }


    }


  /*  public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/

    public void imagelist(){


        //Log.d("imagelist in imagelist()",imagelist+"");
       // imagearray = imagelist.toArray(new Bitmap[imagelist.size()]);
        imageurlarray = imagelist.toArray(new String[imagelist.size()]);
        if(imageurlarray.length==3) {

            if(imageurlarray[0]!=null){
                String imageurl1 = imageurlarray[0];
                Picasso.with(getActivity()).load(imageurl1).into(img1);
                delete1.setVisibility(View.VISIBLE);
            }else{

                delete1.setVisibility(View.GONE);
                img1.setImageDrawable(null);
            }

            if(imageurlarray[1]!=null) {
                String imageurl2 = imageurlarray[1];
                Picasso.with(getActivity()).load(imageurl2).into(img2);
                delete2.setVisibility(View.VISIBLE);
            }else{
                delete2.setVisibility(View.GONE);
                img2.setImageDrawable(null);
            }
            if(imageurlarray[2]!=null){
                String imageurl3 = imageurlarray[2];
                Picasso.with(getActivity()).load(imageurl3).into(img3);
                delete3.setVisibility(View.VISIBLE);
            }else{
                delete3.setVisibility(View.GONE);
                img3.setImageDrawable(null);
            }

        }else if(imageurlarray.length==2) {

            if(imageurlarray[0]!=null){
                String imageurl1 = imageurlarray[0];
                Picasso.with(getActivity()).load(imageurl1).into(img1);
                delete1.setVisibility(View.VISIBLE);
            }else{

                delete1.setVisibility(View.GONE);
                img1.setImageDrawable(null);
            }

            if(imageurlarray[1]!=null) {
                String imageurl2 = imageurlarray[1];
                Picasso.with(getActivity()).load(imageurl2).into(img2);
                delete2.setVisibility(View.VISIBLE);
            }else{
                delete2.setVisibility(View.GONE);
                img2.setImageDrawable(null);
            }


        }else if(imageurlarray.length==1) {

            if(imageurlarray[0]!=null){
                String imageurl1 = imageurlarray[0];
                Picasso.with(getActivity()).load(imageurl1).into(img1);
                delete1.setVisibility(View.VISIBLE);
            }else{

                delete1.setVisibility(View.GONE);
                img1.setImageDrawable(null);
            }


        }



    }
    public void check()
    {
       if(name.equalsIgnoreCase("")){
           new android.support.v7.app.AlertDialog.Builder(getActivity())
                   .setMessage("Please Update Your Profile")
                   .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           ((UserDashboard)getActivity()).updateFragment();
                       }
                   })

                   .setCancelable(false)
                   .show();

       }
       else {
        /*   Intent intent = new Intent(getActivity(), Thanks.class);
           startActivity(intent);
           getActivity().finish();*/
           submit();
       }
    }
    public void submit()
    {
        Name = edgrievancename.getText().toString().trim();
        description=edmsg.getText().toString();
        userid=share.getInt("Userid",0);
        representativeid=1;


        Log.i("Name=",Name+"Dscription= "+description);
        if(!validatecategory())
        {

            return;

        }else if(id==0){

            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setMessage(getResources().getString(R.string.Please_select_category))
                    .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // ((UserDashboard)getActivity()).updateFragment();
                        }
                    })

                    .setCancelable(false)
                    .show();
            return;


        }


        else if(lnrelected.getVisibility()==View.VISIBLE  && !validatepoliticalparty()) {

                return;

        }
       else if(lnrelected.getVisibility()==View.VISIBLE && !validaterepresentative())
        {
            return;
        }
        else if(representativeid==0)
        {
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setMessage(getResources().getString(R.string.Please_select_representative))
                    .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                           // ((UserDashboard)getActivity()).updateFragment();
                        }
                    })

                    .setCancelable(false)
                    .show();
            return;
        }
         else {


            getMyLocation();
            if (latitude==0.0&&longitude==0.0)
            {
                getMyLocation();

                grievancelocationAddress = "No Address Found";
                if(flag==1){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        // jsonObject.put("userid", userid);
                        jsonObject.put("categoryid", id);
                        jsonObject.put("grievancename", grievancename);
                        jsonObject.put("description", description);
                        // jsonObject.put("file", " ");
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("partyid", partyid);
                        jsonObject.put("representativeid", representativeid);
                        // jsonObject.put("grievancename", grievancename);
                        jsonObject.put("grievanceid", grievanceid);
                        jsonObject.put("address", grievancelocationAddress);


                        Log.e("sending Json :- ", String.valueOf(jsonObject));
                        new HttpRequestUpdate(jsonObject.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("userid", userid);
                        jsonObject.put("categoryid", id);
                        jsonObject.put("name", Name);
                        jsonObject.put("description", description);
                        jsonObject.put("file", " ");
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("partyid", partyid);
                        jsonObject.put("representativeid", representativeid);
                        jsonObject.put("address", grievancelocationAddress);


                        Log.e("sending Json :- ", String.valueOf(jsonObject));
//                        new HttpRequestTaskSendGrievance(jsonObject.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }else{
                if(flag==1){
                    try {
                        JSONObject jsonObject = new JSONObject();
                        // jsonObject.put("userid", userid);
                        jsonObject.put("categoryid", id);
                        jsonObject.put("grievancename", grievancename);
                        jsonObject.put("description", description);
                        // jsonObject.put("file", " ");
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("partyid", partyid);
                        jsonObject.put("representativeid", representativeid);
                        // jsonObject.put("grievancename", grievancename);
                        jsonObject.put("grievanceid", grievanceid);
                        jsonObject.put("address", grievancelocationAddress);


                        Log.e("sending Json :- ", String.valueOf(jsonObject));
                        new HttpRequestUpdate(jsonObject.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("userid", userid);
                        jsonObject.put("categoryid", id);
                        jsonObject.put("name", Name);
                        jsonObject.put("description", description);
                        jsonObject.put("file", " ");
                        jsonObject.put("latitude", latitude);
                        jsonObject.put("longitude", longitude);
                        jsonObject.put("partyid", partyid);
                        jsonObject.put("representativeid", representativeid);
                        jsonObject.put("address", grievancelocationAddress);


                        Log.e("sending Json :- ", String.valueOf(jsonObject));
                        new HttpRequestTaskSendGrievance(jsonObject.toString()).execute();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }


    }
    private boolean validatename() {
        if (edgrievancename.getText().toString().trim().isEmpty()) {
            txtname.setError("Please enter valid name");
            return false;
        } else {
            txtname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatecategory() {
        if (edcategory.getText().toString().trim().isEmpty()) {
            txtcategory.setError("Please enter valid category");
            return false;
        } else {
            txtpoliticalparty.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatepoliticalparty() {
        if (edpoliticalparty.getText().toString().trim().isEmpty()) {
            txtpoliticalparty.setError("Please enter valid political party");
            return false;
        } else {
            txtpoliticalparty.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validaterepresentative() {
        if (sprepresent.getSelectedItemId() == 0) {
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setMessage(getResources().getString(R.string.Please_select_representative))
                    .setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // ((UserDashboard)getActivity()).updateFragment();
                        }
                    })

                    .setCancelable(false)
                    .show();
            //Toast.makeText(getContext(), "Please select representative", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;


    }

    public void onDestroyView() {
        super.onDestroyView();
        mypojo1.clear();
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
                case R.id.edgrievancename:
                    validatename();
                    break;


                case R.id.edcategory:
                    validatecategory();
                    break;
            }

        }
    }


    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
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
        GPSTracker gps = new GPSTracker(getActivity());
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

          try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getActivity(), Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if(addresses.size()>0)
                {

                    String address = addresses.get(0).getAddressLine(0);
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                     grievancelocationAddress=address;
                    //Log.e("Address",grievancelocationAddress);
                  /*  new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage(locationAddress)
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok), null)
                            .show();*/
                    if (String.valueOf(grievancelocationAddress).equalsIgnoreCase("null"))
                    {

                    }
                    else
                    {

                    }


                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }else{

            gps.showSettingsAlert();
        }



    }



    private void cameraIntent()
    {


        if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.CAMERA ) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M  ) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5);



            }
            else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivityForResult(intent, REQUEST_CAMERA);


            }


        }else  if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE ) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M  ) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        5);



            }
            else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivityForResult(intent, REQUEST_CAMERA);


            }


        }
        else {

           /* if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            5);

                }
            }else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivityForResult(intent, REQUEST_CAMERA);


            }*/


            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
            //Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            startActivityForResult(intent, REQUEST_CAMERA);

        }




    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);



        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else {

                }
                return;
        }
    }

    private void onCaptureImageResult(Intent data, int imageclick) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;


        //Bitmap thumbnail = BitmapFactory.decodeResource(data.getExtras().get("data"),options);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        //Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));




   /*     File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
*/

            final File destination = new File(imageRoot, System.currentTimeMillis() +".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        if(imageclick==1){
            if(delete1.getVisibility()==View.GONE){
                imgfile1 = destination;
                img1.setImageBitmap(thumbnail);
                delete1.setVisibility(View.VISIBLE);
                imagearray[0]=thumbnail;
                //arrayList.add(0,thumbnail);

                //imagelist.add(0, String.valueOf(thumbnail));

            }


        }else if(imageclick==2){
            if(delete2.getVisibility()==View.GONE){
                imgfile2 = destination;
                img2.setImageBitmap(thumbnail);
                delete2.setVisibility(View.VISIBLE);
                imagearray[1]=thumbnail;
                //arrayList.add(1,thumbnail);

                //imagelist.add(1, String.valueOf(thumbnail));
            }

        }else if(imageclick==3){
            if(delete3.getVisibility()==View.GONE){
                imgfile3 = destination;
                img3.setImageBitmap(thumbnail);
                delete3.setVisibility(View.VISIBLE);
                imagearray[2]=thumbnail;
                //arrayList.add(2,thumbnail);

               // imagelist.add(2, String.valueOf(thumbnail));
            }
        }
/*

           arrayList.add(thumbnail);
          */
/* ImageView img1=new ImageView(getActivity());
           img1.setImageBitmap(thumbnail);
           img1.setPadding(10,5,10,5);
           linearlayout.addView(img1);
           Log.i("Image",img1.toString());
*//*


          if(imagelist.size()==2){
              clickcount1=2;
          }else if(imagelist.size()==1){
              clickcount1=1;
          }else if(imagelist.size()==0){

              clickcount1=0;
          }


          if (clickcount1==0){

              if(delete1.getVisibility()==View.GONE){
                  imgfile1 = destination;
                  img1.setImageBitmap(thumbnail);
                  delete1.setVisibility(View.VISIBLE);

              }else if(delete2.getVisibility()==View.GONE){
                  imgfile2 = destination;
                  img2.setImageBitmap(thumbnail);
                  delete2.setVisibility(View.VISIBLE);
              }
              else{
                  imgfile3 = destination;
                  img3.setImageBitmap(thumbnail);
                  delete3.setVisibility(View.VISIBLE);
              }
              imagelist.add(0, String.valueOf(thumbnail));

          }
          else if(clickcount1==1){
              if(delete1.getVisibility()==View.GONE){
                  imgfile1 = destination;
                  img1.setImageBitmap(thumbnail);
                  delete1.setVisibility(View.VISIBLE);

              }else if(delete2.getVisibility()==View.GONE){
                  imgfile2 = destination;
                  img2.setImageBitmap(thumbnail);
                  delete2.setVisibility(View.VISIBLE);
              }
              else{
                  imgfile3 = destination;
                  img3.setImageBitmap(thumbnail);
                  delete3.setVisibility(View.VISIBLE);
              }
              imagelist.add(1, String.valueOf(thumbnail));
          }else if(clickcount1==2){
              if(delete1.getVisibility()==View.GONE){
                  imgfile1 = destination;
                  img1.setImageBitmap(thumbnail);
                  delete1.setVisibility(View.VISIBLE);

              }else if(delete2.getVisibility()==View.GONE){
                  imgfile2 = destination;
                  img2.setImageBitmap(thumbnail);
                  delete2.setVisibility(View.VISIBLE);


              }else{
                  imgfile3 = destination;
                  img3.setImageBitmap(thumbnail);
                  delete3.setVisibility(View.VISIBLE);
              }

              imagelist.add(2, String.valueOf(thumbnail));
          }
*/

    }


    public class HttpRequestPhotoDelete extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;
        String url;
        int imagedelete=0;


        public HttpRequestPhotoDelete(String jsonstr, String url,int deleteimgclick) {
            this.jsonstr = jsonstr;
            Log.d("image delete json",jsonstr);
            this.url = url;
            this.imagedelete = deleteimgclick;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

              //  String url = config.getDeleteUserGrievancePhoto();
                String url = config.userremoteurl+"admin/app/deleteUserGrievancePhoto/";

                Log.e("image deleted url",url);
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
                return new String("Exception: " + e.getMessage());

            }

        }


        @Override
        protected void onPostExecute(String result) {


            //Log.i("Image_Results:", result);
        /*    Toast.makeText(EditGrievance.this, "Photo deleted successfully", Toast.LENGTH_SHORT).show();
            EditGrievance editGrievance = new EditGrievance();
            editGrievance.imagelist();*/

            progressDialog.dismiss();
            if(result.equalsIgnoreCase("Success")){
               // imagelist.remove(url);
                Toast.makeText(getActivity(), "Photo deleted successfully", Toast.LENGTH_SHORT).show();
                //EditGrievance editGrievance = new EditGrievance();

                edgrievancename.setText(grievancename);
                edmsg.setText(description);


                if(imageurlarray.length>0){
                    if(imagedelete==1){
                        //imagelist.remove(0);
                        imageurlarray[0]=null;


                        Log.d("imagelist.size",imagelist.size()+"");


                        img1.setImageDrawable(null);
                        delete1.setVisibility(View.GONE);

                    }else if(imagedelete==2){
//                        imagelist.remove(1);
                        imageurlarray[1]=null;

                        //delete2.setVisibility(View.GONE);
                        // img1.setBackground();
                        Log.d("imagelist.size",imagelist.size()+"");
                        //img2.setImageDrawable(getResources().getDrawable(R.drawable.addphoto));
                        img2.setImageDrawable(null);
                        delete2.setVisibility(View.GONE);

                    }else if(imagedelete==3){
                       // imagelist.remove(2);
                        imageurlarray[2]=null;

                       // delete3.setVisibility(View.GONE);
                        // img1.setBackground();

                        Log.d("imagelist.size",imagelist.size()+"");
                       //img3.setImageDrawable(getResources().getDrawable(R.drawable.addphoto));
                        img3.setImageDrawable(null);
                        delete3.setVisibility(View.GONE);

                    }


                }

                //imagelist();


            }
            else if(result.equalsIgnoreCase(getString(R.string.Error))){
                Toast.makeText(getActivity(), "Photo not deleted successfully", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        AddAdapter customAdapter;
        Dialog progressDialog;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
               // final String url2 = config.getGetAllCategories();
                final String url2 = config.userremoteurl+"admin/app/getAllCategories";



                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){
                Log.i("output is all category", myPojo.size() + "");

                mypojo1 = myPojo;

                if(categoryid>0){
                    for(int i=0; i<myPojo.size();i++){
                        if(categoryid == myPojo.get(i).getGrievancecategoryid()){
                            edcategory.setText(myPojo.get(i).getGrievancename());
                            id = myPojo.get(i).getGrievancecategoryid();
                        }
                    }
                }

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<myPojo.size();i++){
                    items.add(myPojo.get(i).getGrievancename());

                }
                /*lnrcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogcategory.showSpinerDialog();
                    }
                });*/


                spinnerDialogcategory=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Category),getResources().getString(R.string.Close));// With No Animation

                spinnerDialogcategory.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        edcategory.setText(item);
                        for(int i=0;i<myPojo.size();i++){
                            if(item.equalsIgnoreCase(myPojo.get(i).getGrievancename())){

                                id = myPojo.get(i).getGrievancecategoryid();

                            }
                        }

                    }
                });








            }else{
                new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
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


    public class HttpRequestTaskSendGrievance extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;

        public HttpRequestTaskSendGrievance(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.d("Add grievance input",jsonstr);
        }

        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

                //String url = config.getUser_add_new_grievance();
                String url = config.userremoteurl+"admin/app/user_add_new_grievance";
                Log.d("add grievance url",url);

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
                return new String("Exception: " + e.getMessage());

            }

        }


        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(String result) {
          progressDialog.dismiss();
            Log.i("Griveance_Id:", result);

         if(result!=null) {

             griveanceid = Integer.parseInt(result);

             if (imagearray.length > 0) {
                 for (int i = 0; i <imagearray.length; i++) {
                     if(imagearray[i]!=null){
                         Bitmap bm = imagearray[i];
                         //convert it into base 64
                         ByteArrayOutputStream baos = new ByteArrayOutputStream();
                         bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                         byte[] b = baos.toByteArray();
                         griveanceimage = Base64.encodeToString(b, 0);
                         Log.i("Image", griveanceimage);
                         //base64str=
                         //form a jsonobject which contain

                         JSONObject jsonObject = new JSONObject();
                         try {
                             jsonObject.put("grievanceid", griveanceid);
                             jsonObject.put("file", griveanceimage);
                             jsonObject.put("userid", userid);
                             new HttpRequestTask2(jsonObject.toString()).execute();
                         } catch (JSONException e) {
                             e.printStackTrace();

                             return;
                         }

                     }

                 }

             }

             edgrievancename.setText("");
             edmsg.setText("");
             categoryid=0;
             imagelist.clear();
             partyid=0;
             representativeid=0;
             typeid=0;
             nonelective.setChecked(true);
             edpoliticalparty.setText("");
             edrepresentativeparty.setText("");
             edrepresentative.setText("");
             edrepresentativetype.setText("");
             edelectedrepresentative.setText("");

           /*  FragmentTransaction ft = getFragmentManager().beginTransaction();
             ft.detach(Add.this).attach(Add.this).commit();
             new android.support.v7.app.AlertDialog.Builder(getActivity())
                     .setMessage("Grievance added successfully")
                     .setCancelable(false)
                     .setNegativeButton(getString(R.string.ok),null)
                     .show();
             */

           if(getActivity()!=null && getView()!=null){
               Intent intent = new Intent(getActivity(), Thanks.class);
               startActivity(intent);
               getActivity().finish();
           }


         }
         else
         {
             new android.support.v7.app.AlertDialog.Builder(getActivity())
                     .setMessage("Error in Server Connection")
                     .setCancelable(false)
                     .setNegativeButton(getString(R.string.ok),null)
                     .show();

         }

        }
    }



    public class HttpRequestUpdate extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;

        public HttpRequestUpdate(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.d("update grievance input",jsonstr);
        }

        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

               // String url = config.getUpdateSelectedGrievance();
                String url = config.userremoteurl+"admin/app/updateSelectedGrievance/";
                Log.d("update grievance",url);

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
                return new String("Exception: " + e.getMessage());

            }

        }


        @SuppressLint("WrongThread")
        @Override
        protected void onPostExecute(String result) {
            progressDialog.dismiss();
            Log.i("Griveance_Id:", result);

            if(!result.contains("timestamp")) {

                //griveanceid = Integer.parseInt(result);

                if (imagearray.length > 0) {
                    for (int i = 0; i < imagearray.length; i++) {
                        if(imagearray[i]!=null){
                            Bitmap bm = imagearray[i];
                            //convert it into base 64
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                            byte[] b = baos.toByteArray();
                            griveanceimage = Base64.encodeToString(b, 0);
                            Log.i("Image", griveanceimage);
                            //base64str=
                            //form a jsonobject which contain

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("grievanceid", grievanceid);
                                jsonObject.put("file", griveanceimage);
                                jsonObject.put("userid", userid);
                                new HttpRequestTask7(jsonObject.toString()).execute();

                            } catch (JSONException e) {
                                e.printStackTrace();

                                return;
                            }


                        }

                    }

                }
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setMessage(getResources().getString(R.string.Grievance_updated_successfully))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();

                fragment = new NewGrievance();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);
                    fragmentTransaction.commit();

                }



            }
            else
            {
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setMessage("Error in Server Connection")
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();

            }

        }
    }


    public class HttpRequestTask7 extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;

        public HttpRequestTask7(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.d("update json",jsonstr);
        }

        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

                //String url = config.getUploadAllImages();
                String url = config.userremoteurl+"admin/app/uploadAllImages";

                Log.d("image upload url",url);
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
                return new String("Exception: " + e.getMessage());

            }

        }


        @Override
        protected void onPostExecute(String result) {

            Log.i("Image_Results:", result);
            if(result==null){
         /*       new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();*/

            }else {
               /* if(result.equalsIgnoreCase("Success")){
                    Toast.makeText(getActivity(), "Successfully upload all images", Toast.LENGTH_SHORT).show();

                }else if(result.equalsIgnoreCase("timeout")){
                    Toast.makeText(getActivity(), "Connection problem for uploading images", Toast.LENGTH_SHORT).show();

                }*/

            }

            progressDialog.dismiss();

        }
    }


    public class HttpRequestTask2 extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String jsonstr;

        public HttpRequestTask2(String jsonstr) {
            this.jsonstr = jsonstr;
        }

        protected void onPreExecute() {
            super.onPreExecute();

          /*  progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait");  // Setting Message
           // progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);*/

        }

        protected String doInBackground(String... arg0) {


            try {

                //For POST

               // String url = config.getUploadAllImages();
                String url = config.userremoteurl+"admin/app/uploadAllImages";

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
                return new String("Exception: " + e.getMessage());

            }

        }


        @Override
        protected void onPostExecute(String result) {
            if(result!=null){

                Log.i("Image_Results:", result);
                for(int i=0;i<imagearray.length;i++){
                    imagearray[i]=null;

                }


            }else{
            /*    new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection...")
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();*/
            }

           // progressDialog.dismiss();




        }
    }

    private class HttpRequestPoliticalParties extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        AddAdapter customAdapter;
        Dialog progressDialog;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
           //     final String url2 = config.getGetPoliticalParties();
                    final String url2 = config.userremoteurl+"admin/app/getPoliticalParties";



                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){

                Log.i("Political Party", myPojo.size() + "");

                if(partyid>0){
                    edpoliticalparty.setText(partyname);

                }

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<myPojo.size();i++){
                    items.add(myPojo.get(i).getPartyname());

                }

                edpoliticalparty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edrepresentativeparty.setText("");
                        spinnerDialogpoliticalparty.showSpinerDialog();
                    }
                });
                spinnerDialogpoliticalparty =new  SpinnerDialog(getActivity(), items, "Select Political Party ", "Close");

                spinnerDialogpoliticalparty.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edpoliticalparty.setText(item);
                        for(int i=0;i<myPojo.size();i++){
                            if(item.equalsIgnoreCase(myPojo.get(i).getPartyname())){
                                partyid = myPojo.get(i).getPartyid();
                                //new HttpRequestTask4(partyid,latitude,longitude).execute();
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("constituencyid",constituencyid);
                                    jsonObject.put("partyid",partyid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Log.e("input json ",jsonObject.toString());
                                new HttpRequestAllMpMlaRepresentativeListByPartyId(jsonObject.toString()).execute();


                            }

                        }

                    }
                });


            }else {
              /*  new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();*/
            }

            progressDialog.dismiss();

        }
    }



    private class HttpRequestAllMpMlaRepresentativeListByPartyId extends AsyncTask<Void, Void, String> {

        AddAdapter customAdapter;
        Dialog progressDialog;

        String json;


        public HttpRequestAllMpMlaRepresentativeListByPartyId(String json){
            this.json = json;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
;
        }

        @Override
        protected String doInBackground(Void... params) {


            final String url2 = config.userremoteurl+"admin/app/getAllMpMlaRepresentativeListByPartyId";



            Log.e("url", url2);


            try {

//For POST

                MediaType JSON = MediaType.parse("application/json");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(url2)
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

           // return null;
        }
        protected void onPostExecute(final String myPojo) {

            if(myPojo!=null){

                mypojo2 = new ArrayList<>();
                mypojo2.clear();
                Log.e("representative by Party", myPojo );


                try {
                    JSONArray jsonArray = new JSONArray(myPojo);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);

                        String representativename=jsonObject.getString("representativename");
                        int representativeid =jsonObject.getInt("representativeid");

                        Dialstar dialstar=new Dialstar();
                        dialstar.setRepresentativename(representativename);
                        dialstar.setRepresentativeid(representativeid);
                        mypojo2.add(dialstar);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < mypojo2.size(); i++) {
                    items.add(mypojo2.get(i).getRepresentativename());

                }

                edrepresentativeparty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogrepresentativeparty.showSpinerDialog();
                    }
                });



                spinnerDialogrepresentativeparty = new  SpinnerDialog(getActivity(), items, "Select Representative ", "Close");// With No Animation


                spinnerDialogrepresentativeparty.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edrepresentativeparty.setText(item);
                        for (int i = 0; i < mypojo2.size(); i++) {
                            if (item.equalsIgnoreCase(mypojo2.get(i).getRepresentativename())) {

                                representativeid = mypojo2.get(i).getRepresentativeid();

                            }
                        }

                    }
                });



            }else {
                /*new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection")
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();*/
            }

            progressDialog.dismiss();

        }
    }


    private class HttpRequestTask5 extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        Dialog progressDialog;



        public HttpRequestTask5() {

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url1 = config.getGetRepresentativeList();
                //final String url1 = config.userremoteurl+"admin/app/getAllElectiveMembersList"+"/"+loksabhaconstituency;
                final String url1 = config.userremoteurl+"admin/app/getAllElectiveMembersListForNew"+"/"+loksabhaconstituency;


                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow1 = restTemplate.getForObject(url1, Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){
                Log.i("result output", myPojo.size() + "");


                mypojo4 = myPojo;


                if(representativeid>0){
                    for(int i=0;i<myPojo.size();i++){
                        if(representativeid==myPojo.get(i).getElectiveid()){
                            edelectedrepresentative.setText(myPojo.get(i).getElectivename());

                        }

                    }

                }

                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < myPojo.size(); i++) {
                    items.add(myPojo.get(i).getElectivename());

                }


                edelectedrepresentative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogelectedrepresentative.showSpinerDialog();
                    }
                });

                spinnerDialogelectedrepresentative = new SpinnerDialog(getActivity(), items, "Select Representative ", "Close");
                spinnerDialogelectedrepresentative.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edelectedrepresentative.setText(item);
                        for (int i = 0; i < myPojo.size(); i++) {
                            if (item.equalsIgnoreCase(myPojo.get(i).getElectivename())) {

                                representativeid = myPojo.get(i).getElectiveid();

                            }
                        }

                    }
                });


                Dialstar dp = new Dialstar();
                dp.setElectivename("--Select Representative--");
                dp.setElectiveid(0);
                mypojo4.add(0, dp);
                customAdapter = new AddAdapter(getActivity(),R.layout.customspinner, R.id.title, myPojo);
                sprepresent.setAdapter(customAdapter);
                // spmla.setSelection(mlaid);

                if(representativeid>0){
                    for(int i=0;i< myPojo.size();i++) {
                        if (representativeid == myPojo.get(i).getElectiveid()) {
                            sprepresent.setSelection(i);
                        }
                    }

                }else{
                    sprepresent.setSelection(0);
                }

                AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




                        representativeid = mypojo4.get(i).getElectiveid();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                };

                sprepresent.setOnItemSelectedListener(onItemSelectedListener);
            }else{
      /*          new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection...")
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();*/
            }

            progressDialog.dismiss();

        }

    }


    private class HttpRequestTask4 extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        Dialog progressDialog;
        int partyId;
        double latitude;
        double longitude;



        public HttpRequestTask4(int pos, Double latitude, Double longitude) {

            partyId = pos;
            this.latitude=latitude;
            this.longitude=longitude;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                // final String url1 = config.getSelectedRepresentativeList()+partyId+"/"+latitude+"/"+longitude;
                final String url1 = config.userremoteurl+"admin/app/selectedRepresentativeList/"+partyId+"/"+latitude+"/"+longitude;


                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow1 = restTemplate.getForObject(url1, Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            Log.i("result output", myPojo.size() + "");


            mypojo2 = myPojo;




            ArrayList<String> items = new ArrayList<>();
            for (int i = 0; i < myPojo.size(); i++) {
                items.add(myPojo.get(i).getRepresentativename());

            }

            edrepresentativeparty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialogrepresentativeparty.showSpinerDialog();
                }
            });



            spinnerDialogrepresentativeparty = new  SpinnerDialog(getActivity(), items, "Select Representative ", "Close");// With No Animation


            spinnerDialogrepresentativeparty.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    edrepresentativeparty.setText(item);
                    for (int i = 0; i < myPojo.size(); i++) {
                        if (item.equalsIgnoreCase(myPojo.get(i).getRepresentativename())) {

                            representativeid = myPojo.get(i).getRepresentativeid();

                        }
                    }

                }
            });


            progressDialog.dismiss();

        }

    }



    private class HttpRequestMasterUser extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url = config.getGetUserTypeMaster();
                final String url = config.userremoteurl+"admin/app/getUserTypeMaster";



                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
/*
                MyPojo[] forNow = restTemplate.getForObject(url, MyPojo[].class);
                ArrayList<MyPojo> greeting = new ArrayList(Arrays.asList(forNow));*/


                Dialstar[] forNow = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting = new ArrayList(Arrays.asList(forNow));
                return greeting;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
                //Toast.makeText(Personal_Details.this, "Sever connection failed.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){

                //Log.i("result usertype", myPojo+ "");
                mypojo3 = myPojo;





                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < myPojo.size(); i++) {
                    items.add(myPojo.get(i).getTypename());

                }


                if(typeid>0){
                    for(int i=0;i<myPojo.size();i++){

                        if(typeid==myPojo.get(i).getTypeid()){
                            edrepresentativetype.setText(myPojo.get(i).getTypename());
                            new HttpRequestRepresenataivelist(typeid,constituencyid).execute();


                        }

                    }
                }

                edrepresentativetype.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edrepresentative.setText("");
                        spinnerDialogrepresentativetype.showSpinerDialog();
                    }
                });


                spinnerDialogrepresentativetype = new SpinnerDialog(getActivity(), items, "Select Representative Type ", "Close");// With No Animation



                spinnerDialogrepresentativetype.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edrepresentativetype.setText(item);
                        for (int i = 0; i < myPojo.size(); i++) {
                            if (item.equalsIgnoreCase(myPojo.get(i).getTypename())) {

                                typeid = myPojo.get(i).getTypeid();

                                new HttpRequestRepresenataivelist(typeid,constituencyid).execute();

                            }
                        }

                    }
                });


            }else {
                /*new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection...")
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();*/
            }

        }

    }



    private class HttpRequestRepresenataivelist extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        Dialog progressDialog;


        int typeid=0;
        int constituencyid=0;

        public HttpRequestRepresenataivelist(int typeid,int constituencyid) {
            this.typeid=typeid;
            this.constituencyid = constituencyid;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url1 = config.getGetRepresentativeList();
                final String url1 = config.userremoteurl+"admin/app/getMlaList/"+typeid+"/"+constituencyid;



                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow1 = restTemplate.getForObject(url1, Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            Log.i("result output", myPojo.size() + "");


            mypojo5 = myPojo;

            ArrayList<String> items = new ArrayList<>();
            for (int i = 0; i < myPojo.size(); i++) {
                items.add(myPojo.get(i).getMlaname());

            }
            if(representativeid>0){
                for(int i=0;i<myPojo.size();i++){
                    if(representativeid==myPojo.get(i).getMlaid()){

                        edrepresentative.setText(myPojo.get(i).getMlaname());
                    }

                }

            }

            edrepresentative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerDialogrepresentative.showSpinerDialog();

                }
            });

            spinnerDialogrepresentative = new SpinnerDialog(getActivity(), items, "Select Representative ", "Close");// With No Animation

            spinnerDialogrepresentative.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    edrepresentative.setText(item);
                    for (int i = 0; i < myPojo.size(); i++) {
                        if (item.equalsIgnoreCase(myPojo.get(i).getMlaname())) {

                            representativeid = myPojo.get(i).getMlaid();

                        }
                    }

                }
            });



            Dialstar dp = new Dialstar();
            dp.setMlaname("--Select Representative--");
            dp.setMlaid(0);
            mypojo5.add(0, dp);
            customAdapter = new AddAdapter(getActivity(),R.layout.customspinner, R.id.title, myPojo);
            sprepresentativelist.setAdapter(customAdapter);
            // spmla.setSelection(mlaid);

            if(representativeid>0){
                for(int i=0;i< myPojo.size();i++) {
                    if (representativeid == myPojo.get(i).getMlaid()) {
                        sprepresentativelist.setSelection(i);
                    }
                }

            }else{
                sprepresentativelist.setSelection(0);
            }

            AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {




                    representativeid = mypojo5.get(i).getMlaid();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            };

            sprepresentativelist.setOnItemSelectedListener(onItemSelectedListener);
            progressDialog.dismiss();

        }

    }

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
        public SpinnerDialog(Activity activity, ArrayList<String> items, String dialogTitle, String closeTitle, String calling) {
            this.items = items;
            this.context = activity;
            this.dTitle = dialogTitle;
            this.style = style;
            this.closeTitle = closeTitle;
            this.calling = calling;
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

                    adapter.getFilter().filter(searchBox.getText().toString());
                }
            });
            rippleViewClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    alertDialog.dismiss();

                }
            });

            this.alertDialog.show();
        }
    }

}




