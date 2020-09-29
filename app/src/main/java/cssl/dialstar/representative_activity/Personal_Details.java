package cssl.dialstar.representative_activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cssl.dialstar.CustomAdapter;
import cssl.dialstar.R;
import cssl.dialstar.Utility;
import cssl.dialstar.representative_adapter.EditProfileAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_utils.GPSTracker;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Personal_Details extends AppCompatActivity {



    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor ;
    private TextInputLayout txtname,txtaddress,txtdistrict,txtstate,
            txtmobilenumber,txtemail,txtpin,txtward,txtcity,txtpoliticalparty
            ,txtpassword,txtconfirmpassword,txtdob,txtmandal;//txtconstituency,txtloksabhaconstituency,

    public EditText edname,edaddress,edmobilenumber,edemail,edpin,edward,
            edcity,eddistrict,edmandal,
            edstate,edarea,edpassword,edconfirmpassword,edother,edpoliticalparty,eddob;//,edloksabhaconstituency,edconstituency
    private Button btnsave;
    private Spinner sppincode,spconstituency,spward,spcity,
            spmasteruser,sptounder,sppoliticalparty;
    String address="",name="",country,state,city,
            email="",picture="",mobilenumber="",password="",confirmpassword="",
            constituency="",pin="",ward="",districtname="",partyName="",dob="",mandalName="";
    int typeid=0,countryId=0,stateId=0,cityId=0,mlaid=0,constituencyid=0,pinid=0,wardid=0,partyId=0;
    Config config = new Config();
    String usertype = "";
    String pincodetext="";
    String constituencytext="",loksabhaconstituency;
    Button btnlocation;
    //String startdate="";

    private int mYear, mMonth, mDay, mHour, mMinute, mSec,day;
    Bitmap bm=null;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Button btnSelect;
    private ImageView ivImage;
    private String userChoosenTask;
    File destination = null;
    Dialog progressDialog;
    //LinearLayout linearLayout;
    String locationAddress;
    Double latitude=0.0, longitude=0.0;
    int locationflag=0;
    TextView txtlocation;
    EditProfileAdapter customAdapter;
    JSONObject json;
    RadioGroup rbggender;
    RadioButton rbmale,rbfemale,rbother;

    String locationaddress="",locationcity="",locationstate="",locationcountry="",
            locationpostalCode="",locationknownName="",androidid="",gender="";



    /*ArrayList<MyPojo> mypojo;
    ArrayList<MyPojo> mypojo1;
    ArrayList<MyPojo> mypojo2;*/

    int   pincodeflag=0;
    ArrayList<DialstarPojo> dialstarPojos;
    ArrayList<DialstarPojo> dialstarPojos1;
    ArrayList<DialstarPojo> dialstarPojos2;
    ArrayList<DialstarPojo>  dialstarPojosPost;
    ArrayList<DialstarPojo> dialstarPojosCountry;
    ArrayList<DialstarPojo> dialstarPojos3;
    ArrayList<DialstarPojo> dialstarPojos4;
    ArrayList<DialstarPojo> dialstarPojos5;
    ArrayList<DialstarPojo> dialstarPojos6;
    ArrayList<DialstarPojo> dialstarPojos7;
    ArrayList<DialstarPojo> dialstarPojos8;
    CustomAdapter customCountryAdapter;

    RadioGroup radioGroup;
    SpinnerDialog spinnerDialogcity,spinnerDialogpin,spinnerDialogconstituency,
            spinnerDialogdistrict,spinnerDialogloksabhaconstituency,spinnerDialogstate,
            spinnerDialogpoliticalpatry;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo_white);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        setTitle(getResources().getString(R.string.app_name));


       // linearLayout = (LinearLayout) findViewById(R.id.lnrtounder);

        spmasteruser = (Spinner) findViewById(R.id.spmastertype);
      //  sptounder = (Spinner) findViewById(R.id.sptounder);



        btnsave=(Button) findViewById(R.id.btnsave);
        btnSelect=(Button)findViewById(R.id.btnSelectPhoto);

        txtaddress=(TextInputLayout)findViewById(R.id.txtaddress);
        txtname=(TextInputLayout)findViewById(R.id.txtname);
        txtward = (TextInputLayout)findViewById(R.id.txtward);
       // txtlocation=(TextView) findViewById(R.id.txtlocation);

        txtemail=(TextInputLayout)findViewById(R.id.txtemail);
        txtpin=(TextInputLayout)findViewById(R.id.txtpin);
        txtcity=(TextInputLayout)findViewById(R.id.txtcity);
//        txtconstituency=(TextInputLayout)findViewById(R.id.txtconstituency);
//        txtloksabhaconstituency=(TextInputLayout)findViewById(R.id.txtloksabhaconstituency);
        txtdistrict=(TextInputLayout)findViewById(R.id.txtdistrict);
        txtstate=(TextInputLayout)findViewById(R.id.txtstate);
        txtpoliticalparty=(TextInputLayout)findViewById(R.id.txtpoliticalparty);

        eddistrict = (EditText)findViewById(R.id.eddistrict);
        edstate = (EditText)findViewById(R.id.edstate);
        edname=(EditText)findViewById(R.id.edname);
        edpin=(EditText)findViewById(R.id.edpin);
        edaddress=(EditText)findViewById(R.id.edaddress);
        edward = (EditText)findViewById(R.id.edward);
//        edconstituency = (EditText)findViewById(R.id.edconstituency);
//        edloksabhaconstituency = (EditText)findViewById(R.id.edloksabhaconstituency);
        edpoliticalparty = (EditText)findViewById(R.id.edpoliticalparty);




        edemail=(EditText)findViewById(R.id.edemail);
        edcity=(EditText)findViewById(R.id.edcity);

        edname.addTextChangedListener(new MyTextWatcher(edname));
        edaddress.addTextChangedListener(new MyTextWatcher(edaddress));
      //  edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));
        edpin.addTextChangedListener(new MyTextWatcher(edpin));



        txtward=(TextInputLayout)findViewById(R.id.txtward);
        txtpassword=(TextInputLayout)findViewById(R.id.txtpassword);
        txtconfirmpassword=(TextInputLayout)findViewById(R.id.txtconfirmpassword);
        txtmobilenumber=(TextInputLayout)findViewById(R.id.txtmobilenumber);
        txtdob=(TextInputLayout)findViewById(R.id.txtdob);
        txtmandal=(TextInputLayout)findViewById(R.id.txtmandal);

        eddob = (EditText)findViewById(R.id.eddob);
        edmandal = (EditText)findViewById(R.id.edmandal);


        //edward=(EditText)findViewById(R.id.edward);
        edother = (EditText) findViewById(R.id.edother);
        edpassword=(EditText)findViewById(R.id.edpassword);
        edconfirmpassword=(EditText)findViewById(R.id.edconfirmpassword);
        edmobilenumber=(EditText)findViewById(R.id.edmobilenumber);

        sppoliticalparty=(Spinner)findViewById(R.id.sppoliticalparty);


        rbggender = (RadioGroup)findViewById(R.id.rbggender);
        rbmale = (RadioButton)findViewById(R.id.rbmale);
        rbfemale = (RadioButton)findViewById(R.id.rbfemale);
        rbother = (RadioButton)findViewById(R.id.rbother);



//        edward.addTextChangedListener(new MyTextWatcher(edward));

        edpassword.addTextChangedListener(new MyTextWatcher(edpassword));
        edconfirmpassword.addTextChangedListener(new MyTextWatcher(edconfirmpassword));
        edmobilenumber.addTextChangedListener(new MyTextWatcher(edmobilenumber));

        mlaPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mlaPref.edit();
        androidid = mlaPref.getString("firebaseId",null);




        dialstarPojos = new ArrayList<>();
        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2= new ArrayList<>();
        dialstarPojosPost=new ArrayList<>();
        dialstarPojosCountry=new ArrayList<>();
        dialstarPojos3 = new ArrayList<>();
        dialstarPojos4 = new ArrayList<>();
        dialstarPojos5 = new ArrayList<>();
        dialstarPojos6 = new ArrayList<>();
        dialstarPojos7 = new ArrayList<>();
        dialstarPojos8 = new ArrayList<>();

        new HttpRequestMasterUser().execute();
        new HttpRequestStatebyCountryid(99).execute();
        new HttpRequestTaskParties().execute();



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
                dobDatePickerDialog =new DatePickerDialog(Personal_Details.this, R.style.RepresentativeDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()
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
        btnsave.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                submitform();


            }
        });


        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.ivImage);


 }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

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
                    //Log.e("Address",locationAddress);
                  /*  new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setMessage(locationAddress)
                            .setCancelable(false)
                            .setNegativeButton(getString(R.string.ok), null)
                            .show();*/
                    if (String.valueOf(locationAddress).equalsIgnoreCase("null"))
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




    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo")) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(Personal_Details.this, new String[]{Manifest.permission.CAMERA}, requestCode);
                        }
                        else
                        {
                            cameraIntent();
                            //picture = convertToBase64(destination.getPath().toString());
                        }


                    }
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                   // picture = convertToBase64(destination.getPath().toString());
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { getResources().getString(R.string.Add_Photo), getResources().getString(R.string.Choose_from_Library),
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Personal_Details.this);
        builder.setTitle(getResources().getString(R.string.Add_Photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Personal_Details.this);

                if (items[item].equals(getResources().getString(R.string.Add_Photo))) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals( getResources().getString(R.string.Choose_from_Library))) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals( getResources().getString(R.string.Cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

         destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

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
        bm=thumbnail;
        ivImage.setImageBitmap(thumbnail);



    }


    private void onSelectFromGalleryResult(Intent data) {


        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);


    }



 @RequiresApi(api = Build.VERSION_CODES.N)
 public void submitform()
 {

     int countryposition=0,stateposition=0,cityposition=0;

    // mobilenumber= edmobilenumber.getText().toString().trim();
     name=edname.getText().toString().trim();
     address=edaddress.getText().toString().trim();
     pin= edpin.getText().toString().trim();

     districtname = eddistrict.getText().toString().trim();
     password=edpassword.getText().toString().trim();
     mobilenumber= edmobilenumber.getText().toString().trim();
     confirmpassword=edconfirmpassword.getText().toString().trim();
     city = edcity.getText().toString().trim();

//     loksabhaconstituency = edloksabhaconstituency.getText().toString().trim();
//     constituency = edconstituency.getText().toString().trim();

     email = edemail.getText().toString().trim();
     ward = edward.getText().toString().trim();
     dob = eddob.getText().toString().trim();
     usertype = "representative";


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



      if(!validatename())
        {
         return;
        }
      else if(!validateaddress())
      {
          return;
      }
      else if(!validatepincode())
      {
          return;
      }

      else if(!validateState())
      {
          return;
      }
      else if(!validateDistrict())
      {
          return;
      }
      else if(!validateCity())
      {
          return;
      } /*else if(!validateloksabhaconstituency())
      {
          return;
      }
      else if(!validateconstituency())
      {
          return;
      }*/
    /*  else if(!validateward())
      {
          return;
      }*/
      /*else if(!validateUserType())
      {
          return;
      }else if(typeid>2){
          if(!validateTounder()){

          }
          return;

      }else if(!validateParty()){
          return;

      } */else if(!validateDob()){
          return;

      } else if(!validatemobilenumber())
      {
          return;
      }else if(!validatepassword())
      {
          return;
      }
      else if(!validateconfirmpassword())
      {
          return;
      } else if(!password.equals(confirmpassword))
      {
          new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                  .setMessage(getResources().getString(R.string.Password_does_not_match))
                  .setCancelable(false)
                  .setNegativeButton(getString(R.string.ok), null)
                  .show();
      }/*else if(linearLayout.getVisibility()==View.VISIBLE && !validateMlaid())
      {


          return;

      }*/


     else if(password.equals(confirmpassword)){



         getMyLocation();
         // Toast.makeText(this, "latitude"+latitude, Toast.LENGTH_SHORT).show();
         // Toast.makeText(this, "longitude"+longitude, Toast.LENGTH_SHORT).show();

       /*  if (latitude==0.0&&longitude==0.0)
         {
             getMyLocation();
         }*/

/*

             if (usertype.equalsIgnoreCase("mla")) {
                 mlaid=0;


                 json = new JSONObject();
                 try {
                     json.put("name", name);
                     json.put("address", address);
                     json.put("countryid", 99);
                     json.put("stateid", stateId);
                     json.put("cityid", cityId);
                     json.put("pincode", pin);
                     json.put("mobileno", mobilenumber);
                     json.put("emailid", email);
                     json.put("area", city);
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
                     json.put("subject",loksabhaconstituency);
                     json.put("type",gender);
                     json.put("createddate",dob);


                     Log.e("Mla Input Json:- ", String.valueOf(json));


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
                     json.put("countryid", 99);
                     json.put("stateid", stateId);
                     json.put("cityid", cityId);
                     json.put("pincode", pin);
                     json.put("mobileno", mobilenumber);
                     json.put("emailid", email);
                     json.put("area", city);
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
                     json.put("subject",loksabhaconstituency);
                     json.put("type",gender);
                     json.put("createddate",dob);



                     Log.e("MP Input Json:- ", String.valueOf(json));

                     new HttpRequestRepresentiveRegister(json.toString()).execute();


                 } catch (JSONException e) {
                     e.printStackTrace();
                 }


             }


             else  {//if (usertype.equalsIgnoreCase("representative"))
*/

                 json = new JSONObject();
                 try {
                     json.put("name", name);
                     json.put("address", address);
                     json.put("countryid", 99);
                     json.put("stateid", stateId);
                     json.put("cityid", cityId);
                     json.put("pincode", "");
                     json.put("pinid",pinid);
                     json.put("mobileno", mobilenumber);
                     json.put("emailid", email);
                     json.put("area", city);
                     json.put("partyid", 2);
                     json.put("partyname", "BJP");
                     json.put("password", password);

                     json.put("ward", ward);
                     json.put("file", picture);
                     json.put("mlaid", 1);
                     json.put("latitude", latitude);
                     json.put("longitude", longitude);
                     json.put("androidid", androidid);
                     json.put("usertype", "representative");
                     json.put("typeid",5);
                     json.put("aadharcardno", "");
                     json.put("voterid", "");
                     json.put("districtname",districtname);
                     json.put("constituencyname",constituency);
                     json.put("constituencyid",constituencyid);
                     json.put("wardid",wardid);
                     json.put("subject",loksabhaconstituency);
                     json.put("type",gender);
                     json.put("createddate",dob);

                     Log.e("Representative i/p Json", String.valueOf(json));


                    new HttpRequestRepresentiveRegister(json.toString()).execute();

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }

             }
      //   }


 }

    private boolean validateUserType() {
        if(spmasteruser.getSelectedItemId()==0){
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.Error))
                    .setMessage(getResources().getString(R.string.Please_select_Master_User))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
           // Toast.makeText(this, "Please select Master User", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateTounder() {
        if(mlaid==1000){
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.Error))
                    .setMessage("Please select MP or MLA")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            // Toast.makeText(this, "Please select Master User", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean validateParty() {
        if (edpoliticalparty.getText().toString().trim().isEmpty()) {
            txtpoliticalparty.setError("Please enter valid political party");
            return false;
        } else {
            txtpoliticalparty.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDob() {
        if (eddob.getText().toString().trim().isEmpty()) {
            txtdob.setError(getResources().getString(R.string.valid_dob));
            return false;
        } else {
            txtdob.setErrorEnabled(false);
        }

        return true;
    }
/*    private boolean validateMlaid() {
        if(sptounder.getSelectedItemId()==0){
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.Error))
                    .setMessage("Please select MLA or MP")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //Toast.makeText(this, "Please select MLA ", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }*/

    private boolean validateCity() {

        if (edcity.getText().toString().trim().isEmpty()) {
            txtcity.setError(getResources().getString(R.string.valid_City));
            return false;
        } else {
            txtcity.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateDistrict() {

        if (eddistrict.getText().toString().trim().isEmpty()) {
            txtdistrict.setError(getResources().getString(R.string.valid_District));
            return false;
        } else {
            txtdistrict.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validatepincode() {
        if (edpin.getText().toString().trim().isEmpty()) {
            txtpin.setError(getResources().getString(R.string.valid_Pin_Code));
            return false;
        } else {
            txtpin.setErrorEnabled(false);
        }

        return true;
    }
    /*

    private boolean validateconstituency() {
        if (edconstituency.getText().toString().trim().isEmpty()) {
            txtconstituency.setError(getResources().getString(R.string.valid_vidhan_sabha_constituency));
            return false;
        } else {
            txtconstituency.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateloksabhaconstituency() {
        if (edloksabhaconstituency.getText().toString().trim().isEmpty()) {
            txtloksabhaconstituency.setError(getResources().getString(R.string.valid_lok_sabha_constituency));
            return false;
        } else {
            txtloksabhaconstituency.setErrorEnabled(false);
        }

        return true;
    }

    */

    private boolean validatepassword() {
        if (edpassword.getText().toString().trim().isEmpty()) {
            txtpassword.setError(getResources().getString(R.string.valid_password));
            return false;
        } else {
            txtpassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateconfirmpassword() {
        if (edconfirmpassword.getText().toString().trim().isEmpty()) {
            txtconfirmpassword.setError(getResources().getString(R.string.correct_current_password));
            return false;
        } else {
            txtconfirmpassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatemobilenumber() {
        if (edmobilenumber.getText().toString().trim().isEmpty()) {
            txtmobilenumber.setError(getResources().getString(R.string.valid_mobile_number));
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateward() {

        if (edward.getText().toString().trim().isEmpty()) {
            txtward.setError(getResources().getString(R.string.valid_ward_name));
            return false;
        } else {
            txtward.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateState() {
        if (edstate.getText().toString().trim().isEmpty()) {
            txtstate.setError(getResources().getString(R.string.valid_State));
            return false;
        } else {
            txtstate.setErrorEnabled(false);
        }

        return true;
    }



    private boolean validatename() {
        if (edname.getText().toString().trim().isEmpty()) {
            txtname.setError(getResources().getString(R.string.valid_name));
            return false;
        } else {
            txtname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateemail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!edemail.getText().toString().trim().matches(emailPattern)  ) {
            txtemail.setError("Please enter valid email");
            return false;
        }
        else {
            txtemail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateaddress() {
        if (edaddress.getText().toString().trim().isEmpty()) {
            txtaddress.setError(getResources().getString(R.string.valid_address));
            return false;
        } else {
            txtaddress.setErrorEnabled(false);
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


                case R.id.edname:
                    validatename();
                    break;

                case R.id.edaddress:
                    validateaddress();
                    break;
                case R.id.edpassword:
                    validatepassword();
                    break;

                case R.id.edconfirmpassword:
                    validateconfirmpassword();
                    break;
                case R.id.edmobilenumber:
                    if(edmobilenumber.getText().toString().length()<10) {
                        txtmobilenumber.setError(getResources().getString(R.string.ten_digit_mobile_number));
                    }

                    if(edmobilenumber.getText().toString().length()==10) {
                        edpassword.requestFocus();
                        txtmobilenumber.setErrorEnabled(false);

                    }
                    //validatemobilenumber();
                    break;

                case R.id.edpin:
                    validatepincode();

                    if(pincodeflag==0){
                        if(edpin.getText().length()==6){
                            new HttpRequestAllSearchingPincodeList(edpin.getText().toString()).execute();
                        }

                    }


                    break;
            }

        }
    }

    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

    private String convertToBase64(Bitmap bm)

    {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG,100, baos);

        byte [] b=baos.toByteArray();

        String temp=null;

        try{

            System.gc();

            temp= Base64.encodeToString(b, Base64.DEFAULT);

        }catch(Exception e){

            e.printStackTrace();

        }catch(OutOfMemoryError e){

            baos=new  ByteArrayOutputStream();

            bm.compress(Bitmap.CompressFormat.JPEG,50, baos);
            b=baos.toByteArray();

            temp=Base64.encodeToString(b, Base64.DEFAULT);

           // Log.e("EWN", "Out of memory error catched");

        }

        return temp;

    }
    public String resizeBase64Image(String base64image){
        byte [] encodeByte=Base64.decode(base64image.getBytes(),Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


        if(image.getHeight() <= 400 && image.getWidth() <= 400){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 400, 400, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    private class HttpRequestMasterUser extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
   /*         progressDialog = new ProgressDialog(Personal_Details.this);
            progressDialog.setMessage("Loading Member...."); // Setting Message
            progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
*/
        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //final String url = config.getGetUserTypeMaster();
                final String url = config.representativeremoteurl+"admin/app/getUserTypeMaster";



                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
/*
                MyPojo[] forNow = restTemplate.getForObject(url, MyPojo[].class);
                ArrayList<MyPojo> greeting = new ArrayList(Arrays.asList(forNow));*/


                DialstarPojo[] forNow = restTemplate.getForObject(url,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting = new ArrayList(Arrays.asList(forNow));
                return greeting;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
                //Toast.makeText(Personal_Details.this, "Sever connection failed.", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        protected void onPostExecute(final ArrayList<DialstarPojo> myPojo) {
//            Log.i("pojo size", myPojo.size() + "");
            dialstarPojosPost = new ArrayList<>();
            dialstarPojosPost = myPojo;


            if (myPojo!=null) {


            DialstarPojo dp = new DialstarPojo();
            dp.setTypename("--Select Post--");
            dp.setTypeid(0);
            dialstarPojosPost.add(0, dp);

            customCountryAdapter = new CustomAdapter(Personal_Details.this,
                    R.layout.customspinnerrepresregister, R.id.title, dialstarPojosPost);
            spmasteruser.setAdapter(customCountryAdapter);


            AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    // Toast.makeText(adapterView.getContext(), "id:- "+adapterView.getItemIdAtPosition(i), Toast.LENGTH_SHORT).show();
                    //Log.d("select item position:-", String.valueOf(myPojo.get(pos)));

                    //linearLayout.setVisibility(View.GONE);
                    // usertypeId = (int) adapterView.getItemIdAtPosition(i);
                    // usertype = myPojo.get(usertypeId).getTypename();
                    // usertypeId = myPojo.get(i).getUserid();

                    typeid = myPojo.get(i).getTypeid();
                    usertype = myPojo.get(i).getTypename();
                 /*   if (typeid > 2) {
                        usertype = "representative";

                    } else {
                        usertype = myPojo.get(i).getTypename();

                    }*/

                 /*   if (typeid != 0 && typeid != 1 && typeid != 2) {
                        //sptounder.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        if (dialstarPojos.size() < 3) {
                            new HttpRequesttounder(loksabhaconstituency).execute();
                        }

                    }*/


                    // Log.d("selected master user:-", String.valueOf(usertypeId));
                    // new HttpRequestTaskSendGrievance(countryId).execute();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            };


            spmasteruser.setOnItemSelectedListener(onItemSelectedListener);
            // progressDialog.dismiss();
        }else {
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }

        }

    }
/*


    private class HttpRequesttounder extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String loksabhaconstituency="";
        HttpRequesttounder(String loksabhaconstituency){
            this.loksabhaconstituency = loksabhaconstituency;
        }

        ProgressDialog progressDialog1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog1 = new ProgressDialog(Personal_Details.this);
            progressDialog.setMessage("Please wait"); // Setting Message
           // progressDialog1.setTitle("Please wait"); // Setting Title
            progressDialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog1.show(); // Display Progress Dialog
            progressDialog1.setCancelable(false);

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
             //   final String url3 =config.getGetMlaList();
                //final String url3 =config.representativeremoteurl+"admin/app/getselectedMlaListByCityid/"+cityid;
                final String url3 =config.representativeremoteurl+"admin/app/getAllMlaListByConstituencyId/"+loksabhaconstituency;


                Log.i("url", url3);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow3 = restTemplate.getForObject(url3,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow3));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(final ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");
            progressDialog1.dismiss();


            dialstarPojos = myPojo;

            DialstarPojo dp=new DialstarPojo();
            DialstarPojo dp1=new DialstarPojo();

            dp.setMlaname("--Select MP or MLA--");
            dp.setMlaid(1000);
            dp1.setMlaname("Others");
            dp1.setMlaid(0);
            dialstarPojos.add(0,dp);
            dialstarPojos.add(1,dp1);
            EditProfileAdapter customAdapter;
            customAdapter = new EditProfileAdapter(Personal_Details.this,
                    R.layout.customspinnerrepresregister, R.id.title, myPojo);
            sptounder.setAdapter(customAdapter);
            sptounder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    mlaid = myPojo.get(i).getRepresentativeid();
                    //Toast.makeText(Personal_Details.this, "mlaid" + mlaid, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
           });



        }

    }

*/

    private class HttpRequestTaskParties extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
 /*           progressDialog = new ProgressDialog(Personal_Details.this);
            progressDialog.setMessage("Please wait");  // Setting Message
            //
            // progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);*/

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
        protected void onPostExecute(final ArrayList<DialstarPojo> myPojo) {
            //Log.i("result output", myPojo.size() + "");

           // progressDialog.dismiss();

            if(myPojo!=null){
                EditProfileAdapter customAdapter;
                dialstarPojos7 = myPojo;
                for (DialstarPojo mp3 : myPojo) {
                    // Log.e("Partyid",mp3.getPartyid()+"Partyname: "+ mp3.getPartyname());

                }


                DialstarPojo dp1=new DialstarPojo();
                dp1.setPartyname("Others");
                dp1.setPartyid(0);

                dialstarPojos7.add(0,dp1);



                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos7.size();i++){
                    items.add(dialstarPojos7.get(i).getPartyname());

                }

                edpoliticalparty.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogpoliticalpatry.showSpinerDialog();
                    }
                });
                spinnerDialogpoliticalpatry=new SpinnerDialog(Personal_Details.this, items,"Select Political Party","Close");// With No Animation

                spinnerDialogpoliticalpatry.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {


                        edpoliticalparty.setText(item);
                        for(int i=0;i<dialstarPojos7.size();i++){

                            if(edpoliticalparty.getText().toString().equalsIgnoreCase(dialstarPojos7.get(i).getPartyname())){


                                partyId = dialstarPojos7.get(i).getPartyid();
                            }
                        }



                    }
                });



            }else{
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }

/*


            customAdapter = new EditProfileAdapter(Personal_Details.this,
                    R.layout.customspinnerrepresregister, R.id.title, myPojo);
            sppoliticalparty.setAdapter(customAdapter);
            sppoliticalparty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    partyId = myPojo.get(i).getPartyid();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

*/

        }
    }



    private class HttpRequestStatebyCountryid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int countryId;
        public  HttpRequestStatebyCountryid(int pos) {
            countryId = pos;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
              //  final String url1 = config.getGetAllState()+countryId+" ";
                final String url1 = config.representativeremoteurl+"admin/app/getAllState/"+countryId+" ";


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
                            stateId = dialstarPojos1.get(i).getStateid();
                            new HttpRequestDistrictNameByStateId(stateId).execute();
                            new HttpRequestCityNameByStateid(stateId).execute();



                        }

                    }


                }

                if (locationflag == 1) {

                    for (int i = 0; i < dialstarPojos1.size(); i++) {
                        if (locationstate.equalsIgnoreCase(dialstarPojos1.get(i).getStatename())) {

                            stateId = dialstarPojos1.get(i).getStateid();
                            edstate.setText(dialstarPojos1.get(i).getStatename());
                            new HttpRequestDistrictNameByStateId(stateId).execute();
                            new HttpRequestCityNameByStateid(stateId).execute();
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

                spinnerDialogstate = new SpinnerDialog(Personal_Details.this, items, "Select State ", "Close");// With No Animation

                spinnerDialogstate.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edstate.setText(item);
                        for (int i = 0; i < dialstarPojos1.size(); i++) {
                            if (item.equalsIgnoreCase(dialstarPojos1.get(i).getStatename())) {

                                stateId = dialstarPojos1.get(i).getStateid();
                                new HttpRequestDistrictNameByStateId(stateId).execute();
                                new HttpRequestCityNameByStateid(stateId).execute();

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
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
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
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            //final String url = config.getGetAllPincodeByCityId();
            final String url = config.representativeremoteurl+"admin/app/getAllPincodeByCityId";

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
            //Log.e  ("Response size=", String.valueOf(responceName.length()));
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
                spinnerDialogpin=new SpinnerDialog(Personal_Details.this, items,"Select  Pin Code ","Close");// With No Animation



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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    }
                });

                if(pinid==0){

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("cityid",cityId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            } catch (JSONException e) {
                e.printStackTrace();
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
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {

                final String url2 = config.representativeremoteurl+"admin/app/getDistrictNameByStateId/"+stateId+" ";


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



            dialstarPojos6 = myPojo;

            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos6.size();i++){
                items.add(dialstarPojos6.get(i).getName());

            }


                for(int i=0;i<dialstarPojos6.size();i++){
                    if(dialstarPojos3.size()>0  ){
                        String district = "",district1 = "";
                        district = dialstarPojos3.get(0).getName();
                        district1 = dialstarPojos6.get(i).getName();


                        if(district!=null && district1!=null){
                            if(district.equalsIgnoreCase(district1)){
                                eddistrict.setText(dialstarPojos3.get(0).getName());
                                districtname = eddistrict.getText().toString().trim();
//                                new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();

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




                    }

                }

            }



            eddistrict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogdistrict.showSpinerDialog();
                }
            });
            spinnerDialogdistrict=new SpinnerDialog(Personal_Details.this, items,"Select District","Close");// With No Animation


            spinnerDialogdistrict.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {


                    eddistrict.setText(item);
                    districtname = eddistrict.getText().toString().trim();
                   // new HttpRequestCityNameByDistrictName(districtname).execute();
//                    new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();

                    // new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();
                    loksabhaconstituency="";
                    constituencyid=0;
                    edpin.setText("");
//                    edloksabhaconstituency.setText("");
//                    edconstituency.setText("");


                }
            });


            progressDialog.dismiss();

        }
    }


    private class HttpRequestCityNameByStateid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        int stateId=0;
        Dialog progressDialog;
        public HttpRequestCityNameByStateid(int stateId) {


            this.stateId=stateId;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                //final String url2 = config.representativeremoteurl+"admin/app/getCityNameByDistrictName/"+districtname+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getAllCities/"+stateId+" ";


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
            if(myPojo!=null){
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
                                cityId = dialstarPojos2.get(i).getCityid();


                            }

                        }


                    }

                }

                if(locationflag==1){
                    for(int i=0;i<dialstarPojos2.size();i++){
                        if(locationcity.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){
                            edcity.setText(locationcity);
                            cityId = dialstarPojos2.get(i).getCityid();

                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("cityid",cityId);

                                new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();

                               pinid=0;
                                edpin.setText("");

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
                spinnerDialogcity=new SpinnerDialog(Personal_Details.this, items,"Select City","Close");// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogcity.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        edcity.setText(item);
                        for(int i=0;i<dialstarPojos2.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos2.get(i).getCityname())){

                                cityId = dialstarPojos2.get(i).getCityid();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("cityid",cityId);
                                    new HttpRequestAllPincodeByCityId(jsonObject.toString()).execute();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }

                    }
                });


            }else {

                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();

            }


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
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {

                final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";


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


            dialstarPojos4 = myPojo;


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
            spinnerDialogconstituency=new SpinnerDialog(Personal_Details.this, items,"Select or Add Constituency","Add","constituency");// With No Animation

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
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getAllSearchingPincodeList/"+pin;


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

                //state = dialstarPojos3.get(0).getStatename();
               // stateId = dialstarPojos3.get(0).getStateid();
               // edstate.setText(state);
         /*       eddistrict.setText(dialstarPojos3.get(0).getName());
                edcity.setText(dialstarPojos3.get(0).getCityname());
                cityId = dialstarPojos3.get(0).getCityid();
                districtname = dialstarPojos3.get(0).getName();*/




              //  new HttpRequestLokSabhaConstituencyNameByDistrictName(districtname).execute();





            }else {
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.valid_Pin_Code))
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


    private class HttpRequestRepresentiveRegister extends AsyncTask<Void, Void, String>
    {
        String json;
        Dialog progressDialog;

        public HttpRequestRepresentiveRegister(String json) {
            this.json = json;
            //Log.d("Representive Regi json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            //     final String url = config.getRepresentativeCreateProfile();
            final String url = config.representativeremoteurl+"admin/app/representativeCreateProfile";

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
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();


                // Toast.makeText(Political_Details.this, "Server Not Connected", Toast.LENGTH_SHORT).show();

            }
            else if(responceName.equals("Already Exist")){
                new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
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
                editor.putInt("partyid", 2);
                editor.putString("pincode", pin);
                editor.putString("emailid", email);
                editor.putString("area", city);
                editor.putString("password", password);
                editor.putString("partyname", "BJP");
                editor.putInt("mlaid", mlaid);
                editor.putString("ward", ward);
                editor.putString("file", picture);
                editor.putInt("representativeid",representativeid);
                //editor.putString("usertype",usertype);
                editor.putInt("pinid",pinid);
                editor.putInt("constituencyid",constituencyid);
                editor.putInt("wardid",wardid);
                editor.putInt("typeid",typeid);
                editor.putString("constituency",constituency);
                editor.putString("districtname",districtname);
                editor.putString("loksabhaconstituency",loksabhaconstituency);
                editor.putString("gender",gender);
                editor.putString("dob",dob);
                if(!usertype.equalsIgnoreCase("mla") && !usertype.equalsIgnoreCase("mp")){


                    editor.putString("usertype","representative");
                    editor.putString("typename",usertype);

                }

                //editor.putString("image",picture);
                editor.commit();

                if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){

                    new android.support.v7.app.AlertDialog.Builder(Personal_Details.this)
                            .setMessage(getResources().getString(R.string.Your_account_has_been_created_successfully))
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which)
                                {

                                    Intent i = new Intent(Personal_Details.this, LogIn_Now.class);
                                    startActivity(i);
                                    finish();


                                    dialog.cancel();

                                }

                            }).show();

                }else{
                    Intent i = new Intent(Personal_Details.this, MlaHome.class);
                    startActivity(i);
                    finish();
                }

            }




            progressDialog.dismiss();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent( Personal_Details .this,LogIn_Now.class);

        startActivity(i);

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                Intent i=new Intent( Personal_Details .this,LogIn_Now.class);
                startActivity(i);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {

                final String url2 = config.representativeremoteurl+"admin/app/getLokSabhaConstituencyNameByDistrictName/"+didtrictname;


                Log.i("LokSabhaConstituen url", url2);
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
            spinnerDialogloksabhaconstituency=new SpinnerDialog(Personal_Details.this, items,"Select Lok Sabha Constituency","Close");// With No Animation


            spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {


                    edloksabhaconstituency.setText(item);
                    loksabhaconstituency = edloksabhaconstituency.getText().toString().trim();

                    new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituency).execute();

                   // edcity.setText("");
                    //edpin.setText("");
                    //edloksabhaconstituency.setText("");
                    constituencyid=0;
                    edconstituency.setText("");


                }
            });


            progressDialog.dismiss();

        }
    }



    */

    /*


    private class HttpRequestConstutuencyNameListByLoksabhaConstituencyName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {




        String loksabhaconstituency;
        String districtname="";
        Dialog progressDialog;
        public HttpRequestConstutuencyNameListByLoksabhaConstituencyName(String loksabhaconstituency) {


            this.loksabhaconstituency=loksabhaconstituency;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(Personal_Details.this);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {

                // final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+loksabhaconstituency+" ";

                final String url2 = Config.representativeremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+loksabhaconstituency+" ";


                Log.i("ConstutuencyName url", url2);
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


            dialstarPojos4 = myPojo;


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
            spinnerDialogconstituency=new SpinnerDialog(Personal_Details.this, items,"Select or Add Constituency","Add","constituency");// With No Animation

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
//                    else if(calling.equalsIgnoreCase("constituency")){
//                        edconstituency.setText(constituencytext);
//                        constituencyid=0;
//                    }

                }
            });



            this.alertDialog.show();
        }



    }



}


