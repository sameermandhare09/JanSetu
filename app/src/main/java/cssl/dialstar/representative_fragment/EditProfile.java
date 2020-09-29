package cssl.dialstar.representative_fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
import java.util.Locale;

import cssl.dialstar.R;
import cssl.dialstar.Utility;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_adapter.EditProfileAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_activity.UserDashboard;
import user.dialstar.cssl.dialstaruser.user_adapter.EditProfileeAdapter;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
import user.dialstar.cssl.dialstaruser.user_utils.Utility;
*/


public class EditProfile extends Fragment {

    private TextInputLayout txtname, txtaddress, txtmobilenumber,txtdistrict,
            txtemail,txtarea, txtward, txtpin,txtcity,
            txtstate,txtdob,txtmandal;//txtloksabhaconstituency,txtconstituency,
    private EditText edname, edaddress, edmobilenumber,
            edemail, edpin, edaadhaarnumber,edvoterid,edcity,eddob,
            eddistrict,edstate,edmandal;//edconstituency,edloksabhaconstituency,
    private Button btnsave,btnchangpass;
    private Spinner sppincode,spconstituency,spward,spcountry, spcity,sppoliticalparty;
    String mobilenumber, pin;
    JSONObject json;
    String encodedimage = "",districtname="";

    Fragment fragment = new Fragment();
    int userid=0;


    SharedPreferences mlaPref;
    SharedPreferences.Editor editor ;
/*

    SharedPreferences share;
    SharedPreferences.Editor edit;
*/



    EditText edarea,edother,edward;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    static final int MY_PERMISSIONS_REQUEST_CAMERA = 5;
    //private Button btnSelect;
    private ImageView ivImage,btnSelect;
    private String userChoosenTask;
    Bitmap bm1 = null;
    String name,androidid;
    String address,aadhaarnumber,voterid,area,ward,partyname,usertype;


    String mobileno;
    String emailid;

    String password="";

    Config config;
    String file;
    TextView txtback;


    int pincodeflag=0,fragstartflag=0;
    String state="";
    RadioGroup rbggender;
    RadioButton rbmale,rbfemale,rbother;

    ArrayList<DialstarPojo> dialstarPojos;
    ArrayList<DialstarPojo> dialstarPojos1;
    ArrayList<DialstarPojo> dialstarPojos2;
    ArrayList<DialstarPojo> dialstarPojos3;

    ArrayList<DialstarPojo> dialstarPojos4;
    ArrayList<DialstarPojo> dialstarPojos5;
    ArrayList<DialstarPojo> dialstarPojos6;
    ArrayList<DialstarPojo> dialstarPojos7;
    EditProfileAdapter customAdapter;
    EditProfileAdapter customPincodeAdapter;
    EditProfileAdapter customConstitutionAdapter;
    EditProfileAdapter customWardAdapter;
    Context context;
    String pincodetext="";
    String constituencytext="",constituency="",loksabhaconstituency="",gender="",dob="",mandalName="";
    int countryid, cityid,stateid,partyid,mlaid,representativeid,constituencyid=0,pinid=0,wardid=0;

    SpinnerDialog spinnerDialogcity,spinnerDialogpin,
            spinnerDialogdistrict,spinnerDialogconstituency,spinnerDialogstate,spinnerDialogloksabhaconstituency;

    public EditProfile() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview;
    rootview= inflater.inflate(R.layout.activity_edit_profile2, container, false);



        //spcity = (Spinner) rootview.findViewById(R.id.spcity);
        sppoliticalparty=(Spinner)rootview.findViewById(R.id.sppoliticalparty);

        //sppincode = rootview.findViewById(R.id.sppincode);
        //spconstituency = rootview.findViewById(R.id.spconstituency);
        //spward = rootview.findViewById(R.id.spward);
        txtback = rootview.findViewById(R.id.back);

        config = new Config();

        btnsave=(Button)rootview.findViewById(R.id.btnsave);
        btnchangpass=(Button)rootview.findViewById(R.id.btnchangpass);
        btnSelect=(ImageView) rootview.findViewById(R.id.btnSelectPhoto);

        txtaddress=(TextInputLayout)rootview.findViewById(R.id.txtaddress);
        txtname=(TextInputLayout)rootview.findViewById(R.id.txtname);
        txtmobilenumber=(TextInputLayout)rootview.findViewById(R.id.txtmobilenumber);
        txtemail=(TextInputLayout)rootview.findViewById(R.id.txtemail);
        txtpin=(TextInputLayout)rootview.findViewById(R.id.txtpin);
        txtarea=(TextInputLayout)rootview.findViewById(R.id.txtarea);
        txtward=(TextInputLayout)rootview.findViewById(R.id.txtward);
        txtcity=(TextInputLayout)rootview.findViewById(R.id.txtcity);

        txtdistrict=(TextInputLayout)rootview.findViewById(R.id.txtdistrict);//
        txtstate=(TextInputLayout)rootview.findViewById(R.id.txtstate);

//        txtloksabhaconstituency=(TextInputLayout)rootview.findViewById(R.id.txtloksabhaconstituency);
//        txtconstituency=(TextInputLayout)rootview.findViewById(R.id.txtconstituency);

//        edconstituency=(EditText)rootview.findViewById(R.id.edconstituency);
//        edloksabhaconstituency=(EditText)rootview.findViewById(R.id.edloksabhaconstituency);



        edstate = (EditText)rootview.findViewById(R.id.edstate);
        eddistrict = (EditText)rootview.findViewById(R.id.eddistrict);
        edname=(EditText)rootview.findViewById(R.id.edname);
        edpin=(EditText)rootview.findViewById(R.id.edpin);
        edaddress=(EditText)rootview.findViewById(R.id.edaddress);
        edmobilenumber=(EditText)rootview.findViewById(R.id.edmobilenumber);

        edemail=(EditText)rootview.findViewById(R.id.edemail);
        edarea=(EditText)rootview.findViewById(R.id.edarea);
        edward=(EditText)rootview.findViewById(R.id.edward);
        edother = (EditText) rootview.findViewById(R.id.edother);
        edcity = (EditText) rootview.findViewById(R.id.edcity);
        rbggender = (RadioGroup) rootview.findViewById(R.id.rbggender);
        rbmale = (RadioButton) rootview.findViewById(R.id.rbmale);
        rbfemale = (RadioButton) rootview.findViewById(R.id.rbfemale);
        rbother = (RadioButton) rootview.findViewById(R.id.rbother);
        txtdob=(TextInputLayout)rootview.findViewById(R.id.txtdob);
        txtmandal=(TextInputLayout)rootview.findViewById(R.id.txtmandal);
        eddob = (EditText)rootview.findViewById(R.id.eddob);
        edmandal = (EditText)rootview.findViewById(R.id.edmandal);


        edname.addTextChangedListener(new EditProfile.MyTextWatcher(edname));
        edaddress.addTextChangedListener(new EditProfile.MyTextWatcher(edaddress));
        edmobilenumber.addTextChangedListener(new EditProfile.MyTextWatcher(edmobilenumber));
        edpin.addTextChangedListener(new EditProfile.MyTextWatcher(edpin));
//        edconstituency.addTextChangedListener(new EditProfile.MyTextWatcher(edconstituency));

        edarea.addTextChangedListener(new EditProfile.MyTextWatcher(edarea));
//        edward.addTextChangedListener(new EditProfile.MyTextWatcher(edward));
        sppoliticalparty.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);

        ivImage = (ImageView)rootview.findViewById(R.id.ivImage);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor= mlaPref.edit();
        androidid = mlaPref.getString("androidid","");

        name = mlaPref.getString("name","");
        address = mlaPref.getString("address","");
        countryid = mlaPref.getInt("countryid",0);
        stateid = mlaPref.getInt("stateid",0);
        cityid = mlaPref.getInt("cityid",0);

        mobileno = mlaPref.getString("mobileno","");
        emailid = mlaPref.getString("emailid","");
        file = mlaPref.getString("file","");
        area = mlaPref.getString("area","");
        ward = mlaPref.getString("ward","");
        partyid = mlaPref.getInt("partyid",0);
        mlaid = mlaPref.getInt("mlaid",0);
        partyname = mlaPref.getString("partyname","");
        usertype = mlaPref.getString("usertype","");
        representativeid = mlaPref.getInt("representativeid",0);
        pinid = mlaPref.getInt("pinid",0);
        pin = mlaPref.getString("pincode","");
        constituencyid = mlaPref.getInt("constituencyid",0);
        constituency =  mlaPref.getString("constituency","");
        wardid = mlaPref.getInt("wardid",0);
        districtname = mlaPref.getString("districtname","");
        loksabhaconstituency = mlaPref.getString("loksabhaconstituency","");
        gender = mlaPref.getString("gender","");
        dob = mlaPref.getString("dob","");
        password = mlaPref.getString("password","");


        edname.setText(name);
        edaddress.setText(address);
        //edpin.setText(pin);
        edmobilenumber.setText(mobileno);
        edemail.setText(emailid);
        edward.setText(ward);
        eddistrict.setText(districtname);

//        edloksabhaconstituency.setText(loksabhaconstituency);
//        edconstituency.setText(constituency);

        //eddob.setText(dob);

        if(!dob.equalsIgnoreCase("null")){
            SimpleDateFormat dateFormatter = null,dateFormatter1 = null;
            dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //2018-05-30 16:15:28
            if(dob!=null){
                Date date = null;
                try {
                    date = format.parse(dob);
                    dob = dateFormatter.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            eddob.setText(dob);
        }else{
            eddob.setText("");
        }





        if(gender!=null){
            if(gender.equalsIgnoreCase(rbmale.getText().toString())){
                rbmale.setChecked(true);

            }else if(gender.equalsIgnoreCase(rbfemale.getText().toString())){
                rbfemale.setChecked(true);
            }else if(gender.equalsIgnoreCase(rbother.getText().toString())){
                rbother.setChecked(true);
            }
        }



        //edconstituency.setText(constituency);

       /* if(cityid>0){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cityid",cityid);
                new HttpRequestPincode(jsonObject.toString()).execute();
                //new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }*/

        if(usertype.equalsIgnoreCase("mla")) {
            Picasso.with(context)
                 //   .load(config.getMlaprofilephoto() + mlaid + ".png")//"http://192.168.1.24/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                    .load(config.representativeremoteurl1+"BJPJanhit/uploads/representativeprofilephoto/"+ mlaid + ".png")//"http://192.168.1.24/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)

                    .placeholder(R.drawable.addphoto)

                    .into(ivImage);
       //     Log.d("filePicturePath",config.getMlaprofilephoto()+mlaid+".png");
        }
        else if(!usertype.equalsIgnoreCase("mla")){
            Picasso.with(context)
                   // .load(config.getRepresentativeprofilephoto() + representativeid + ".png")//"http://192.168.1.24/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                    .load(config.representativeremoteurl1+"BJPJanhit/uploads/representativeprofilephoto/"+ representativeid + ".png")//"http://192.168.1.24/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)

                    .placeholder(R.drawable.addphoto)
                    .into(ivImage);

          //  Log.d("filePicturePath",config.getRepresentativeprofilephoto()+representativeid+".png");
        }
        edarea.setText(area);
       // edward.setText(ward);
        if(partyid==0)
            edother.setText(partyname);

        //new EditProfile.HttpRequestTask().execute();
        new EditProfile.HttpRequestTask1(99).execute();
        new EditProfile.HttpRequestTask3().execute();
        dialstarPojos = new ArrayList<>();
        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2 = new ArrayList<>();
        dialstarPojos3 = new ArrayList<>();
        dialstarPojos4 = new ArrayList<>();
         dialstarPojos5 = new ArrayList<>();
        dialstarPojos6 = new ArrayList<>();
        dialstarPojos7 = new ArrayList<>();


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
                dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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
                dobDatePickerDialog =new DatePickerDialog(getContext(), R.style.RepresentativeDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()
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

        btnchangpass.setOnClickListener(new View.OnClickListener() {
            EditText editpasswordcurrent;
            TextInputLayout txtpasswordcurrent;
            EditText editpasswordnew1;
            TextInputLayout txtpasswordnew1;
            EditText editpasswordnew2;
            TextInputLayout txtpasswordnew2;
            Button btncancel,btnsave;
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getContext());
                dialog.setTitle("Change Password");
                dialog.setContentView(R.layout.change_passaword);
                editpasswordcurrent=(EditText) dialog.findViewById(R.id.edpasswordcurrent);
                editpasswordnew1=(EditText) dialog.findViewById(R.id.edpasswordnew1);
                editpasswordnew2=(EditText) dialog.findViewById(R.id.edpasswordnew2);
                txtpasswordcurrent = dialog.findViewById(R.id.txtpasswordcurrent);
                txtpasswordnew1 = dialog.findViewById(R.id.txtpasswordnew1);
                txtpasswordnew2 = dialog.findViewById(R.id.txtpasswordnew2);
                btncancel= dialog.findViewById(R.id.btncancel);
                btnsave = dialog.findViewById(R.id.btnsave);


                editpasswordcurrent.addTextChangedListener(new MyTextWatcher(editpasswordcurrent));
                editpasswordnew1.addTextChangedListener(new MyTextWatcher(editpasswordnew1));
                editpasswordnew2.addTextChangedListener(new MyTextWatcher(editpasswordnew2));
                // editpasswordcurrent.setText(password);


                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String passwordnew1,passwordnew2;
                        passwordnew1 = editpasswordnew1.getText().toString().trim();
                        passwordnew2 = editpasswordnew2.getText().toString().trim();


                        if(!validatepasswordcurrent())
                        {
                            return;
                        }
                        else if(!validatepasswordnew1())
                        {
                            return;
                        }

                        else if(!validatepasswordnew2())
                        {
                            return;
                        }
                        else if(!passwordnew1.equals(passwordnew2))
                        {
                            new android.support.v7.app.AlertDialog.Builder(getContext())
                                    .setMessage("Password does not match")
                                    .setCancelable(false)
                                    .setNegativeButton(getString(R.string.ok), null)
                                    .show();
                        }
                        else if(passwordnew1.equals(passwordnew2)){
                            Toast.makeText(getContext(), "Password successfully updated", Toast.LENGTH_SHORT).show();

                            password=passwordnew2;
                            dialog.dismiss();
                        }

                    }
                });
                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }

            private boolean validatepasswordcurrent() {
                if (editpasswordcurrent.getText().toString().trim().isEmpty() ) {
                    txtpasswordcurrent.setError("Please enter valid password");

                    return false;
                }else if(!password.equalsIgnoreCase(editpasswordcurrent.getText().toString().trim())){
                    txtpasswordcurrent.setError("Please enter correct current password");
                    return false;
                }
                else {
                    txtpasswordcurrent.setErrorEnabled(false);
                }

                return true;
            }


            private boolean validatepasswordnew1() {
                if (editpasswordnew1.getText().toString().trim().isEmpty()) {
                    txtpasswordnew1.setError("Please enter valid password");
                    return false;
                } else {
                    txtpasswordnew1.setErrorEnabled(false);
                }

                return true;
            }
            private boolean validatepasswordnew2() {
                if (editpasswordnew2.getText().toString().trim().isEmpty()) {
                    txtpasswordnew2.setError("Please enter valid password");
                    return false;
                } else {
                    txtpasswordnew2.setErrorEnabled(false);
                }

                return true;
            }



            class MyTextWatcher implements TextWatcher {

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
                        case R.id.edpasswordcurrent:
                            validatepasswordcurrent();
                            break;

                        case R.id.edpasswordnew1:
                            validatepasswordnew1();
                            break;

                        case R.id.edpasswordnew2:
                            validatepasswordnew2();
                            break;

                    }

                }
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


        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usertype.equalsIgnoreCase("mla")){
                    fragment = new Dashboard();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }
                else {
                    fragment = new IssueView();
                    if (fragment != null) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }

               /*  FragmentManager fm = getFragmentManager();
                 if (fm.getBackStackEntryCount() > 0) {
                     Log.i("MainActivity", "popping backstack");
                     fm.popBackStack();
                 } else {
                     Log.i("MainActivity", "nothing on backstack, calling super");
                     //super.onBackPressed();
                 }*/
            }
        });

        return rootview;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(getResources().getString(R.string.Take_Photo))) {
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, requestCode);
                        } else {
                            cameraIntent();
                        }


                    } else if (userChoosenTask.equals(getResources().getString(R.string.Choose_from_Library)))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {getResources().getString(R.string.Take_Photo), getResources().getString(R.string.Choose_from_Library),
                getResources().getString(R.string.Cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.Add_Photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getContext());

                if (items[item].equals(getResources().getString(R.string.Take_Photo))) {
                    userChoosenTask =getResources().getString(R.string.Take_Photo);
                    if (result)
                        cameraIntent();

                } else if (items[item].equals(getResources().getString(R.string.Choose_from_Library))) {
                    userChoosenTask = getResources().getString(R.string.Choose_from_Library);
                    if (result)
                        galleryIntent();

                } else if (items[item].equals(getResources().getString(R.string.Cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {


        if (android.support.v4.content.ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA},
                        5);

            }
        }else {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }


/*
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


  */  @Override
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

        File destination = new File(Environment.getExternalStorageDirectory(),
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

        ivImage.setImageBitmap(thumbnail);
        encodeImage(thumbnail);
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ivImage.setImageBitmap(bm);
        encodeImage(bm);
    }


    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] b = baos.toByteArray();
        encodedimage = Base64.encodeToString(b, 0);
        Log.i("Image", encodedimage);
        return encodedimage;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void submitform() {



        mobileno=edmobilenumber.getText().toString().trim();
        name=edname.getText().toString().trim();
        address=edaddress.getText().toString().trim();
         pin=edpin.getText().toString().trim();

        emailid = edemail.getText().toString().trim();
        area = edarea.getText().toString().trim();
        ward = edward.getText().toString().trim();
        partyname = edother.getText().toString().trim();
        districtname = eddistrict.getText().toString().trim();

//        constituency = edconstituency.getText().toString().trim();
//        constituency = edconstituency.getText().toString().trim();

        dob = eddob.getText().toString().trim();


        SimpleDateFormat dateFormatter = null,dateFormatter1 = null;
        dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //2018-05-30 16:15:28
        if(!dob.equalsIgnoreCase("null")){
            Date date = null;
            try {
                date = dateFormatter.parse(dob);
                dob = format.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            validateDob();
        }


        if(!validatemobilenumber())
        {
            return;
        }
        else if(!validatename())
        {
            return;
        }
        else if(!validateaddress())
        {
            return;
        }
      /*  else if(!validatepin())
        {
            return;
        }*/
        else if(!validatearea())
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
        }
        if(!validatepincode())
        {
            return;
        }
        /*
        else if(!validateloksabhaconstituency())
        {
            return;
        }else if(!validateconstituency())
        {
            return;
        }

        */
        else if(!validateDob())
        {
            return;
        }

        /*if(!validateward())
        {
            return;
        }*/

      /*  else if(!validateward())
        {
            return;
        }*/
        else
        {

            json=new JSONObject();

            if(bm1!=null)
                encodedimage = convertToBase64(bm1);

            if(usertype.equalsIgnoreCase("mla")||usertype.equalsIgnoreCase("mp")){
                try {
                    json.put("name",name);
                    json.put("address",address);
                    json.put("countryid", 99);
                    json.put("stateid",stateid);
                    json.put("cityid",cityid);
                    json.put("pincode",pin);
                    json.put("mobileno",mobileno);
                    json.put("emailid",emailid);
                    json.put("area",area);
                    json.put("partyid",2);
                    json.put("password",password);
                    json.put("partyname","BJP");
                    json.put("ward",ward);
                    json.put("file", encodedimage);
                    json.put("androidid",androidid);
                    json.put("usertype",usertype);
                    json.put("representativeid",representativeid);
                    json.put("mlaid",0);
                    json.put("aadharcardno","");
                    json.put("voterid","");
                    json.put("latitude",0.0);
                    json.put("longitude",0.0);
                    json.put("pinid",pinid);
                    json.put("constituencyid",constituencyid);
                    json.put("constituencyname",constituency);
                    json.put("wardid",wardid);
                    json.put("districtname",districtname);
                    json.put("subject",loksabhaconstituency);
                    json.put("type",gender);
                    json.put("createddate",dob);






                   // json.put("androidid","");




                    new HttpRequestRepresentativeUpdate(json.toString()).execute();



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }


            if(!usertype.equalsIgnoreCase("mla")&&!usertype.equalsIgnoreCase("mp")){
                try {
                    json.put("name",name);
                    json.put("address",address);
                    json.put("countryid", 99);
                    json.put("stateid",stateid);
                    json.put("cityid",cityid);
                    json.put("pincode",pin);
                    json.put("mobileno",mobileno);
                    json.put("emailid",emailid);
                    json.put("area",area);
                    json.put("partyid",2);
                    json.put("password",password);
                    json.put("partyname","BJP");
                    json.put("ward",ward);
                    json.put("file", encodedimage);

                    json.put("androidid",androidid);
                    json.put("usertype",usertype);
                    json.put("mlaid",1);
                    json.put("representativeid",representativeid);
                    json.put("aadharcardno","");
                    json.put("voterid","");
                    json.put("latitude",0.0);
                    json.put("longitude",0.0);
                    json.put("pinid",pinid);
                    json.put("constituencyid",constituencyid);
                    json.put("constituencyname",constituency);
                    json.put("wardid",wardid);
                    json.put("districtname",districtname);
                    json.put("subject",loksabhaconstituency);
                    json.put("type",gender);
                    json.put("createddate",dob);

                    //json.put("androidid","");

                    Log.d("sending Json :- ", String.valueOf(json));
                    new HttpRequestRepresentativeUpdate(json.toString()).execute();



                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }
 }


    private String convertToBase64(Bitmap bm)

    {

        // Bitmap bm = BitmapFactory.decodeFile(imagePath);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] byteArrayImage = baos.toByteArray();

        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

        return encodedImage;

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
                    validatemobilenumber();
                    break;

                case R.id.edname:
                    validatename();
                    break;

                case R.id.edaddress:
                    validateaddress();

                    break;


                case R.id.edpin:
                    validatepincode();

                    if(pincodeflag==0&&fragstartflag!=0){
                        if(edpin.getText().length()==6){
                            new HttpRequestAllSearchingPincodeList(edpin.getText().toString()).execute();
                        }

                    }

                    break;

          /*
                case R.id.edloksabhaconstituency:
                    validateloksabhaconstituency();
                    break;

                case R.id.edconstituency:
                    validateconstituency();
                    break;

                   */


            }

        }
    }


/*    private boolean validatepin() {
        if (edpin.getText().toString().trim().isEmpty()) {
            txtpin.setError("Please enter valid pin number");
            return false;
        } else {
            txtpin.setErrorEnabled(false);
        }

        return true;
    }*/
    private boolean validatearea() {
        if (edarea.getText().toString().trim().isEmpty()) {
            txtarea.setError(getResources().getString(R.string.Please_enter_valid_area));
            return false;
        } else {
            txtarea.setErrorEnabled(false);
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
    private boolean validateState() {

        if (edstate.getText().toString().trim().isEmpty()) {
            txtstate.setError(getResources().getString(R.string.valid_State));
            return false;
        } else {
            txtstate.setErrorEnabled(false);
        }

     /*   if(spstate.getSelectedItemId()==0){

            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.Error))
                    .setMessage(getResources().getString(R.string.select_state))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //Toast.makeText(this, "Please select State", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;
    }

    private boolean validateCity() {


        if (edcity.getText().toString().trim().isEmpty()) {
            txtcity.setError(getResources().getString(R.string.valid_City));
            return false;
        } else {
            txtcity.setErrorEnabled(false);
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

    */

    /*

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

    private boolean validateward() {
        if (edward.getText().toString().trim().isEmpty()) {
            txtward.setError(getResources().getString(R.string.valid_ward_name));
            return false;
        } else {
            txtward.setErrorEnabled(false);
        }
  /*      if(spward.getSelectedItemId()==0){
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setTitle(getString(R.string.Error))
                    .setMessage(getResources().getString(R.string.select_ward))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //Toast.makeText(getContext(), "Please select Country", Toast.LENGTH_SHORT).show();
            return false;
        }*/
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

    private boolean validateDistrict() {

        if (eddistrict.getText().toString().trim().isEmpty()) {
            txtdistrict.setError(getResources().getString(R.string.valid_District));
            return false;
        } else {
            txtdistrict.setErrorEnabled(false);
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

    private boolean validateaddress() {
        if (edaddress.getText().toString().trim().isEmpty()) {
            txtaddress.setError(getResources().getString(R.string.valid_address));
            return false;
        } else {
            txtaddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateemail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!edemail.getText().toString().trim().matches(emailPattern)) {
            txtemail.setError("Please enter valid email");
            return false;
        } else {
            txtemail.setErrorEnabled(false);
        }

        return true;
    }



    private class HttpRequestTask1 extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {

        Dialog progressDialog;

        int countryId;
        public HttpRequestTask1(int pos) {

            countryId = pos;
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //final String url1 = config.getGetAllState()+countryId+" ";
                  final String url1 = config.representativeremoteurl+"admin/app/getAllState/"+countryId+" ";

                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

               /* MyPojo[] forNow1 = restTemplatinte.getForObject(url1, MyPojo[].class);
                ArrayList<MyPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));*/

                DialstarPojo[] forNow1 = restTemplate.getForObject(url1,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            if(myPojo!=null){

                Log.i("result output", myPojo.size() + "");


                dialstarPojos1.clear();


                    dialstarPojos1 = myPojo;



                    if(stateid>0){
                        for(int i=0;i<dialstarPojos1.size();i++){
                            if(stateid==dialstarPojos1.get(i).getStateid()){

                                edstate.setText(dialstarPojos1.get(i).getStatename());
                                new HttpRequestDistrictNameByStateId(stateid).execute();
                                new HttpRequestCityNameByStateid(stateid).execute();



                            }

                        }

                    }

                    ArrayList<String> items = new ArrayList<>();
                    for (int i = 0; i < dialstarPojos1.size(); i++) {
                        items.add(dialstarPojos1.get(i).getStatename());

                    }


                    edstate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            spinnerDialogstate.showSpinerDialog();
                        }
                    });

                    spinnerDialogstate = new SpinnerDialog(getActivity(), items, "Select State ", "Close");// With No Animation

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
//                                    edloksabhaconstituency.setText("");
//                                    edconstituency.setText("");


                                }
                            }

                        }
                    });



            }else {
                new android.support.v7.app.AlertDialog.Builder(getContext())
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


    private class HttpRequestDistrictNameByStateId extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int stateId;
        Dialog progressDialog;
        public HttpRequestDistrictNameByStateId(int pos) {
            stateId = pos;

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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getDistrictNameByStateId/"+stateId+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos6 = myPojo;


            if(districtname!=null){
                eddistrict.setText(districtname);
                //new HttpRequestCityNameByDistrictName(districtname).execute();
//                new HttpRequestLoksabhaConstutuencyNameListByDistrictName(districtname).execute();

                //new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();



            }

            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos6.size();i++){
                items.add(dialstarPojos6.get(i).getName());

            }

            eddistrict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogdistrict.showSpinerDialog();
                }
            });
            spinnerDialogdistrict=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_District),getResources().getString(R.string.Close));// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogdistrict.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                    eddistrict.setText(item);
                    districtname = eddistrict.getText().toString().trim();
                   // new HttpRequestCityNameByDistrictName(districtname).execute();
//                    new HttpRequestLoksabhaConstutuencyNameListByDistrictName(districtname).execute();

                    // new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();

                    loksabhaconstituency="";
                    constituencyid=0;

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
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos4 = myPojo;


            if(constituencyid>0){
                for(int i=0;i<dialstarPojos4.size();i++){
                    if(constituencyid==dialstarPojos4.get(i).getConstituencyid()){

                        edconstituency.setText(dialstarPojos4.get(i).getConstituencyname());



                    }

                }

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
            spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Constituency),getResources().getString(R.string.Add),"constituency");// With No Animation

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
/*

    private class HttpRequestCityNameByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>>
    {



        String districtname="";
        ProgressDialog progressDialog;
        public HttpRequestCityNameByDistrictName(String districtname) {


            this.districtname=districtname;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
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
                final String url2 = config.representativeremoteurl+"admin/app/getCityNameByDistrictName/"+districtname+" ";


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

            if(cityid>0){

                for(int i=0;i<dialstarPojos2.size();i++){
                    if(cityid==dialstarPojos2.get(i).getCityid()){
                        edcity.setText(dialstarPojos2.get(i).getCityname());
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



            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos2.size();i++){
                items.add(dialstarPojos2.get(i).getCityname());

            }

                       edcity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogcity.showSpinerDialog();
                }
            });
            spinnerDialogcity=new SpinnerDialog(getActivity(), items,"Select City","Close");// With No Animation

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



            progressDialog.dismiss();

        }
    }

*/


    private class HttpRequestCityNameByStateid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>>
    {



        int stateId=0;
        Dialog progressDialog;
        public HttpRequestCityNameByStateid(int stateId) {


            this.stateId=stateId;
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
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
            Log.i("result output", myPojo.size() + "");


            dialstarPojos2 = myPojo;
            pincodeflag=1;

            if(cityid>0){

                for(int i=0;i<dialstarPojos2.size();i++){
                    if(cityid==dialstarPojos2.get(i).getCityid()){
                        edcity.setText(dialstarPojos2.get(i).getCityname());
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



            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos2.size();i++){
                items.add(dialstarPojos2.get(i).getCityname());

            }

            edcity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    spinnerDialogcity.showSpinerDialog();
                }
            });
            spinnerDialogcity=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_City),getResources().getString(R.string.Close));// With No Animation

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


                                pinid=0;
                                edpin.setText("");
                                // new HttpRequestPincode(jsonObject.toString()).execute();
                                // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                            edpin.setText("");


                        }
                    }

                }
            });



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

            progressDialog = new Dialog(getContext());
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
              /*  DialstarPojo dialstar1=new DialstarPojo();
                dialstar1.setPincode("--Please select Pin--");
                dialstar1.setPinid(0);
                dialstarPojos3.add(0,dialstar1);*/

                if(pinid>0){

                    for(int i=0;i<dialstarPojos3.size();i++){
                        if(pinid==dialstarPojos3.get(i).getPinid()){
                            edpin.setText(dialstarPojos3.get(i).getPincode());

                        }

                    }

                }


                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos3.size();i++){
                    items.add(dialstarPojos3.get(i).getPincode());

                }


                edpin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        spinnerDialogpin.showSpinerDialog();
                    }
                });
                spinnerDialogpin=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Pin_Code),getResources().getString(R.string.Close));// With No Animation



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
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getAllSearchingPincodeList/"+pin+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DialstarPojo> greeting) {

            //  dialstarPojos3=new ArrayList<>();



            dialstarPojos3=greeting;
            Log.d("pincode o/p",dialstarPojos3+"");

            if(dialstarPojos3.size()>0) {
                state = dialstarPojos1.get(1).getStatename();
                edstate.setText(state);
                eddistrict.setText(dialstarPojos3.get(1).getName());
                edcity.setText(dialstarPojos3.get(1).getCityname());
                // edconstituency.setText(dialstarPojos3.get(0).getCityname());
            }else {
                new android.support.v7.app.AlertDialog.Builder(getContext())
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

    private class HttpRequestTask3 extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
               // final String url3 = config.getGetPoliticalParties();
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
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            // Log.i("result output", myPojo.size() + "");

            if(myPojo!=null){
                dialstarPojos3 = myPojo;

                DialstarPojo dp=new DialstarPojo();
                dp.setPartyname("--Select Party--");
                dp.setPartyid(100);
                dialstarPojos3.add(0,dp);
                DialstarPojo dp1=new DialstarPojo();
                dp1.setPartyname("Others");
                dp1.setPartyid(0);
                dialstarPojos3.add(1,dp1);


                customAdapter = new EditProfileAdapter(getActivity(),
                        R.layout.customspinner, R.id.title, myPojo);
                sppoliticalparty.setAdapter(customAdapter);


                if(partyid!=100){
                    for(int i=0;i<dialstarPojos3.size();i++){

                        if(partyid==dialstarPojos3.get(i).getPartyid()){

                            sppoliticalparty.setSelection(i);
                        }
                    }

                }else {
                    sppoliticalparty.setSelection(0);
                }
                AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        // Toast.makeText(adapterView.getContext(), "Position:- "+adapterView.getItemIdAtPosition(i), Toast.LENGTH_SHORT).show();
                        //Log.d("select item position:-", String.valueOf(myPojo.get(pos)));


                        partyid = dialstarPojos3.get(i).getPartyid();
                        if(partyid != 0){

                            partyname = dialstarPojos3.get(i).getPartyname();

                        }

                        //partyname = edother.getText().toString().trim();

                        // new EditProfile.HttpRequestTask2(stateId).execute();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                };

                sppoliticalparty.setOnItemSelectedListener(onItemSelectedListener);

            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }


            progressDialog.dismiss();

        }
    }


    private class HttpRequestRepresentativeUpdate extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;

        public HttpRequestRepresentativeUpdate(String json)
        {
            this.json=json;
            Log.d("sending Json :- ", json);

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
        protected String doInBackground(Void... params) {
            // try {
           // final String url =config.getUpdateRepresentativeProfile();
            final String url =config.representativeremoteurl+"admin/app/updateRepresentativeProfile";

            Log.d("update url",url);

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
                return new String(  e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            String responceName = greeting;
            Log.e("responceName =",responceName+"");



            progressDialog.dismiss();
            if(responceName.equals(getString(R.string.Error))){

                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.Not_updated))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

               // Toast.makeText(getContext()," Not updated",Toast.LENGTH_LONG).show();
            }else if(responceName.equals("timeout")){
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

            }
            else if (responceName.equals("Success")){


                editor.putString("name",name);
                editor.putString("address",address);
                editor.putInt("countryid", countryid);
                editor.putInt("stateid",stateid);
                editor.putInt("cityid",cityid);
                editor.putString("mobilenumber",mobileno);
                editor.putInt("partyid",2);
                editor.putString("pincode",pin);
                editor.putString("emailid",emailid);
                editor.putString("area",area);
                editor.putString("password",password);
                editor.putString("partyname","BJP");
                editor.putInt("mlaid", mlaid);
                editor.putString("ward",ward);
                editor.putInt("pinid",pinid);
                editor.putInt("constituencyid",constituencyid);
                editor.putString("constituency",constituency);
                editor.putInt("wardid",wardid);
                editor.putString("districtname",districtname);
                editor.putString("loksabhaconstituency",loksabhaconstituency);
                editor.putString("gender",gender);
                editor.putString("dob",dob);
               // editor.putString("file",encodedimage);
                editor.commit();


                //Toast.makeText(cssl.dialstar.representative_activity.EditProfile.this," successfully updated",Toast.LENGTH_LONG).show();

                Toast.makeText(getContext(),getResources().getString(R.string.successfully_updated),Toast.LENGTH_LONG).show();

                ((MlaHome)getActivity()).refreshProfilePic();

                fragment = new IssueView();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }



        }


        }

    }
/*

    private class HttpRequestLoksabhaConstutuencyNameListByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String districtname="";
        Dialog progressDialog;
        public HttpRequestLoksabhaConstutuencyNameListByDistrictName(String districtname) {


            this.districtname=districtname;
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                // final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";

                final String url2 = config.representativeremoteurl+"admin/app/getLokSabhaConstituencyNameByDistrictName/"+districtname;



                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            if(myPojo!=null){
                Log.i("result output", myPojo.size() + "");


                dialstarPojos7 = myPojo;


                if(loksabhaconstituency!=null){
                    for(int i=0;i<dialstarPojos7.size();i++){
                        if(loksabhaconstituency.equalsIgnoreCase(dialstarPojos7.get(i).getSubject())){

                            edloksabhaconstituency.setText(dialstarPojos7.get(i).getSubject());


                            new HttpRequestConstutuencyNameListByLoksabhaConstituency(loksabhaconstituency).execute();



                        }

                    }

                }



                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos7.size();i++){
                    items.add(dialstarPojos7.get(i).getSubject());


                }


                edloksabhaconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogloksabhaconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogloksabhaconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Lok_Sabha_Constituency),getResources().getString(R.string.Close));// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    */
/*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*//*


                        edloksabhaconstituency.setText(item);
                        for(int i=0;i<dialstarPojos7.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos7.get(i).getSubject())){

                                loksabhaconstituency = dialstarPojos7.get(i).getSubject();
                                new HttpRequestConstutuencyNameListByLoksabhaConstituency(loksabhaconstituency).execute();

                                constituencyid=0;
                                edconstituency.setText("");

                            }
                        }

                    }
                });




            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
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


    private class HttpRequestConstutuencyNameListByLoksabhaConstituency extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String constituency="";
        Dialog progressDialog;
        public HttpRequestConstutuencyNameListByLoksabhaConstituency(String constituency) {


            this.constituency=constituency;
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
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.representativeremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+constituency+" ";


                Log.i("url", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow2 = restTemplate.getForObject(url2,DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            Log.i("result output", myPojo.size() + "");


            dialstarPojos4 = myPojo;


            if(constituencyid>0){
                for(int i=0;i<dialstarPojos4.size();i++){
                    if(constituencyid==dialstarPojos4.get(i).getConstituencyid()){

                        edconstituency.setText(dialstarPojos4.get(i).getConstituencyname());



                    }

                }

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
            spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Constituency),getResources().getString(R.string.Add),"constituency");// With No Animation

            // spinnerDialogcity.setCancelable(true);
            spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                @Override
                public void onClick(String item, int position) {
                    */
/*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
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
        String closeTitle = getResources().getString(R.string.Close);
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
                    else if(calling.equalsIgnoreCase("constituency")){
                        //edconstituency.setText(constituencytext);
                        constituencyid=0;
                    }
                }
            });
            this.alertDialog.show();
        }
    }


}
