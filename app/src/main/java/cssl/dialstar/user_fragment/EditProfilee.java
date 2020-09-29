package cssl.dialstar.user_fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_adapter.EditProfileeAdapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Utility;
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


public class EditProfilee extends Fragment {

    private TextInputLayout txtname, txtaddress, txtmobilenumber,
            txtward,txtemail, txtpin,txtaadhaarnumber,txtvoterid,txtcity,txtdob,
            txtdistrict,txtstate,txtmandal,txtassociation;//,txtconstituency,txtloksabhaconstituency
    private EditText edname, edaddress, edmobilenumber, edemail,edcity,
            eddistrict,edstate,eddob,edmandal,edassociation,
            edward,edpin, edaadhaarnumber,edvoterid;//,edconstituency,edloksabhaconstituency
    private Button btnsave,btnchngpassword;
    private Spinner spcountry, spcity, spstate,sppincode,spconstituency,spward;
    JSONObject json;
    int userid=0;
    Fragment fragment = new Fragment();


    SharedPreferences share;
    SharedPreferences.Editor edit;
    SpinnerDialog spinnerDialogcity,spinnerDialogpin,spinnerDialogconstituency,
            spinnerDialogdistrict,spinnerDialogstate,spinnerDialogloksabhaconstituency;

    int pincodeflag=0;
    RadioGroup rbggender;
    RadioButton rbmale,rbfemale,rbother;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
   // private Button btnSelect;
    private ImageView ivImage,btnSelect;
    private String userChoosenTask;
    Bitmap bm1 = null;

    String name="",password="",mobileno="",emailid="",ward="",file="",city="",state="",
            address="",aadhaarnumber="",voterid="",mandalName="",pincodetext="",
            constituencytext="",gender="",dob="",districtname="",encodedimage = "",
            pin="",constituency="",loksabhaconstituency="",aadharcardno="";
    int countryid=99,country=0, stateid=0,cityid=0,pinid=0,constituencyid=0,wardid=0;

    ConfigUser config;

    ArrayList<DialstarPojo> dialstarPojos1;
    ArrayList<DialstarPojo> dialstarPojos2;
    ArrayList<DialstarPojo> dialstarPojos3;
    ArrayList<DialstarPojo> dialstarPojos4;
    ArrayList<DialstarPojo> dialstarPojos5;
    ArrayList<DialstarPojo> dialstarPojos6;
    ArrayList<DialstarPojo> dialstarPojos7;

    EditProfileeAdapter customAdapter;
    Context context;


    public EditProfilee() {
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

        View rootview;
    rootview= inflater.inflate(R.layout.fragment_edit_profilee, container, false);



        //spcountry = (Spinner) rootview.findViewById(R.id.spcountry);
        spstate = (Spinner) rootview.findViewById(R.id.spstate);
        //spcity = (Spinner) rootview.findViewById(R.id.spcity);
       // sppincode = rootview.findViewById(R.id.sppincode);
       //
        // spconstituency = rootview.findViewById(R.id.spconstituency);
        //spward = rootview.findViewById(R.id.spward);

        btnsave = (Button) rootview.findViewById(R.id.btnsave);
        btnchngpassword= (Button) rootview.findViewById(R.id.btnchngpassword);
        btnSelect = (ImageView) rootview.findViewById(R.id.btnSelectPhoto);

        txtaddress = (TextInputLayout) rootview.findViewById(R.id.txtaddress);
       // txtaadhaarnumber=(TextInputLayout) rootview.findViewById(R.id.txtaadhaarnumber);
        txtvoterid=(TextInputLayout) rootview.findViewById(R.id.txtvoterid);

        txtmobilenumber = (TextInputLayout) rootview.findViewById(R.id.txtmobilenumber);
        txtward = (TextInputLayout) rootview.findViewById(R.id.txtward);
        txtemail = (TextInputLayout) rootview.findViewById(R.id.txtemail);
        txtpin = (TextInputLayout) rootview.findViewById(R.id.txtpin);
        txtcity = (TextInputLayout) rootview.findViewById(R.id.txtcity);

        txtstate = (TextInputLayout) rootview.findViewById(R.id.txtstate);
        txtdistrict = (TextInputLayout) rootview.findViewById(R.id.txtdistrict);
        txtname = (TextInputLayout) rootview.findViewById(R.id.txtname);
        txtmandal = (TextInputLayout) rootview.findViewById(R.id.txtmandal);
        txtassociation = (TextInputLayout) rootview.findViewById(R.id.txtassociation);


//        txtloksabhaconstituency = (TextInputLayout) rootview.findViewById(R.id.txtloksabhaconstituency);
//        txtconstituency = (TextInputLayout) rootview.findViewById(R.id.txtconstituency);


        rbggender = (RadioGroup) rootview.findViewById(R.id.rbggender);
        rbmale = (RadioButton) rootview.findViewById(R.id.rbmale);
        rbfemale = (RadioButton) rootview.findViewById(R.id.rbfemale);
        rbother = (RadioButton) rootview.findViewById(R.id.rbother);
        txtdob=(TextInputLayout)rootview.findViewById(R.id.txtdob);
        eddob = (EditText)rootview.findViewById(R.id.eddob);
        edname = (EditText) rootview.findViewById(R.id.edname);
        edstate=(EditText) rootview.findViewById(R.id.edstate);
        eddistrict=(EditText) rootview.findViewById(R.id.eddistrict);
        edvoterid=(EditText) rootview.findViewById(R.id.edvoterid);
        edward = (EditText) rootview.findViewById(R.id.edward);
        edpin = (EditText) rootview.findViewById(R.id.edpin);
        edaddress = (EditText) rootview.findViewById(R.id.edaddress);
        edmobilenumber = (EditText) rootview.findViewById(R.id.edmobilenumber);
        edemail = (EditText) rootview.findViewById(R.id.edemail);
        edcity = (EditText) rootview.findViewById(R.id.edcity);
        edmandal = (EditText) rootview.findViewById(R.id.edmandal);
        edassociation = (EditText) rootview.findViewById(R.id.edassociation);

//        edconstituency = (EditText) rootview.findViewById(R.id.edconstituency);
//        edloksabhaconstituency = (EditText) rootview.findViewById(R.id.edloksabhaconstituency);

        edname.addTextChangedListener(new EditProfilee.MyTextWatcher(edname));
        edaddress.addTextChangedListener(new EditProfilee.MyTextWatcher(edaddress));
        edmobilenumber.addTextChangedListener(new EditProfilee.MyTextWatcher(edmobilenumber));
        edpin.addTextChangedListener(new EditProfilee.MyTextWatcher(edpin));
        voterid=edvoterid.getText().toString().trim();
//        aadhaarnumber=edaadhaarnumber.getText().toString().trim();

        ivImage = (ImageView) rootview.findViewById(R.id.ivImage);

        config=new ConfigUser();
        //get the data from sharedpref
        share = PreferenceManager.getDefaultSharedPreferences(getContext());
        edit = share.edit();
        userid=share.getInt("Userid",0);
        name = share.getString("name","");
        address = share.getString("Address","");
        mobileno = share.getString("MobileNo", "");
        emailid = share.getString("Emailid", "");
        file = share.getString("File", "");
        voterid=share.getString("voterid","");
       // aadhaarnumber=share.getString("aadhaarnumber","");
         country=share.getInt("Countryid",0);
         stateid=share.getInt("stateid",0);
         cityid=share.getInt("Cityid",0);
         pinid = share.getInt("pinid",0);
         constituencyid = share.getInt("constituencyid",0);
         wardid = share.getInt("wardid",0);
         ward = share.getString("ward","");
         city =  share.getString("Cityname","");
         pin =  share.getString("pin","");
         password  =  share.getString("Password","");
         constituency =  share.getString("constituency","");
         districtname = share.getString("districtname","");
         loksabhaconstituency = share.getString("loksabhaconstituency","");
         dob = share.getString("dob","");
        gender = share.getString("gender","");



         if(!name.equalsIgnoreCase(null))
             edname.setText(name);
         else
             edname.setText("");

        if(!address.equalsIgnoreCase(null))
            edaddress.setText(address);

        else
            edaddress.setText("");
//        edpin.setText(pincode);

        if(!mobileno.equalsIgnoreCase(null))
            edmobilenumber.setText(mobileno);

        else
            edmobilenumber.setText("");

        if(!city.equalsIgnoreCase(null))
            edcity.setText(city);
        else
            edcity.setText("");
        if(!pin.equalsIgnoreCase(null))
            edpin.setText(pin);
        else
            edpin.setText("");
        if(!districtname.equalsIgnoreCase(null))
            eddistrict.setText(districtname);
        else
            eddistrict.setText("");
        /*if(!loksabhaconstituency.equalsIgnoreCase(null))
            edloksabhaconstituency.setText(loksabhaconstituency);
        else
            edloksabhaconstituency.setText("");

        if(!constituency.equalsIgnoreCase(null))
            edconstituency.setText(constituency);
        else
            edconstituency.setText("");
*/
        if(!emailid.equalsIgnoreCase(null))
            edemail.setText(emailid);
        else
            edemail.setText("");
        if(!ward.equalsIgnoreCase(null))
            edward.setText(ward);
        else
            edward.setText("");
        if(!voterid.equalsIgnoreCase(null))
            edvoterid.setText(voterid);
        else
            edvoterid.setText("");
        if(!dob.equalsIgnoreCase("null")){
            SimpleDateFormat dateFormatter = null,dateFormatter1 = null;
            dateFormatter=new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            //2018-05-30 16:15:28
            if(!dob.equalsIgnoreCase("null")){
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





        refreshProfilePic();

       /* Picasso.with(getContext())
                .load(config.getUserprofilephoto()+userid+".png")//"http://182.18.163.201/dialstar/uploads/mlaprofilephoto/"+mlaid+".png"
                .into(ivImage);*/

       Activity activity = getActivity();
       if(activity !=null){
           new HttpRequestStatebyCountryid(99).execute();
       }else{

       }


       // new EditProfilee.HttpRequestTask().execute();


        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2 = new ArrayList<>();
        dialstarPojos3 = new ArrayList<>();
        dialstarPojos4 = new ArrayList<>();
        dialstarPojos5 = new ArrayList<>();
        dialstarPojos6 = new ArrayList<>();
        dialstarPojos7 = new ArrayList<>();

        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


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
                dobDatePickerDialog =new DatePickerDialog(getContext(), R.style.UserDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener()
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


        btnchngpassword.setOnClickListener(new View.OnClickListener() {
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
                dialog.setTitle(getString(R.string.Change_Password));
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
                                    .setMessage(getString(R.string.Password_does_not_match))
                                    .setCancelable(false)
                                    .setNegativeButton(getString(R.string.ok), null)
                                    .show();
                        }
                        else if(passwordnew1.equals(passwordnew2)){
                            Toast.makeText(getContext(), getString(R.string.Password_successfully_updated), Toast.LENGTH_SHORT).show();

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
                    txtpasswordcurrent.setError(getString(R.string.valid_password));

                    return false;
                }else if(!password.equalsIgnoreCase(editpasswordcurrent.getText().toString().trim())){
                    txtpasswordcurrent.setError(getString(R.string.correct_current_password));
                    return false;
                }
                else {
                    txtpasswordcurrent.setErrorEnabled(false);
                }

                return true;
            }


            private boolean validatepasswordnew1() {
                if (editpasswordnew1.getText().toString().trim().isEmpty()) {
                    txtpasswordnew1.setError(getString(R.string.valid_password));
                    return false;
                } else {
                    txtpasswordnew1.setErrorEnabled(false);
                }

                return true;
            }
            private boolean validatepasswordnew2() {
                if (editpasswordnew2.getText().toString().trim().isEmpty()) {
                    txtpasswordnew2.setError(getString(R.string.valid_password));
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
            @Override
            public void onClick(View view) {
                submitform();

            }
        });




        return rootview;
    }


    public void refreshProfilePic() {


        Picasso.with(getContext())
                .load(config.userremoteurl1+"BJPJanhit/uploads/userprofilephoto/"+ userid + ".png")
             //   .load(config.getUserprofilephoto() + userid + ".png")

                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)

                .placeholder(R.drawable.addphoto)
                .into(ivImage);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals(getString(R.string.Take_Photo))) {

                       // cameraIntent();
                        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, requestCode);
                        } else {
                            cameraIntent();
                        }


                    } else if (userChoosenTask.equals(getString(R.string.Choose_from_Library)))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.Take_Photo), getString(R.string.Choose_from_Library),
                getString(R.string.Cancel)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.Add_Photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(getContext());

                if (items[item].equals(getString(R.string.Take_Photo))) {
                    userChoosenTask = getString(R.string.Take_Photo);
                    if (result)
                        cameraIntent();

                } else if (items[item].equals(getString(R.string.Choose_from_Library))) {
                    userChoosenTask = getString(R.string.Choose_from_Library);
                    if (result)
                        galleryIntent();

                } else if (items[item].equals(getString(R.string.Cancel))) {
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

       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);*/
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

        name = edname.getText().toString().trim();

        address = edaddress.getText().toString().trim();
        mobileno = edmobilenumber.getText().toString().trim();

        pin = edpin.getText().toString().trim();
        emailid = edemail.getText().toString().trim();
       // aadhaarnumber=edaadhaarnumber.getText().toString().trim();
        voterid=edvoterid.getText().toString().trim();
        ward = edward.getText().toString().trim();
        city = edcity.getText().toString().trim();

        districtname = eddistrict.getText().toString().trim();

//        loksabhaconstituency = edloksabhaconstituency.getText().toString().trim();
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
        }else {
            validateDob();
        }


        if (!validatemobilenumber()) {
            return;
        } else if (!validatename()) {
            return;
        } else if (!validateDob()) {
            return;
        }  else if (!validateaddress()) {
            return;
        } else if (!validatepincode()) {
            return;

        }
     /*   else if(!validateward())
        {
            return;
        }*/

        else if(!validateState()) {
            return;
        }
        else  if(!validateState())
        {
            return;
        }

        else if(!validateDistrict())
        {
            return;
        }
        else if(!validateCity()) {
            return;
        }
       /* else if(!validateloksabhaconstituency()) {
            return;
        }
        else if(!validateconstituency()) {
            return;
        }*/else
         {

            json=new JSONObject();
            if(bm1!=null)
                encodedimage = convertToBase64(bm1);
            try {
                json.put("name",name);
                json.put("address",address);
                json.put("userid",userid);

                json.put("countryid",countryid);
                json.put("stateid",stateid);
                json.put("cityid",cityid);
               // json.put("pincode",pin);
                json.put("mobileno",mobileno);
                json.put("emailid",emailid);
                json.put("file", encodedimage);
                json.put("aadharcardno",aadhaarnumber);
                json.put("voterid",voterid);
                json.put("pincode","");
                json.put("pinid",pinid);
                json.put("constituencyid",constituencyid);
                json.put("wardid",wardid);
                json.put("ward",ward);
                json.put("cityname","");
                json.put("constituencyname",constituency);
                json.put("districtname",districtname);
                json.put("subject",loksabhaconstituency);
                json.put("usertype",gender);
                json.put("createddate",dob);
                json.put("password", password);


                /*  edit.putInt("pinid",pinid);
                edit.putString("pincode",pincode);
                edit.putInt("constituencyid",constituencyid);
                edit.putInt("wardid",wardid);*/

                Log.d("sending Json :- ", String.valueOf(json));
                new HttpRequestTaskReq(json.toString()).execute();



            } catch (JSONException e) {
                e.printStackTrace();
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
/*
                case R.id.edemail:
                    validateemail();
                    break;*/


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


    private boolean validateward() {
        if (edward.getText().toString().trim().isEmpty()) {
            txtward.setError(getString(R.string.valid_ward_name));
            return false;
        } else {
            txtward.setErrorEnabled(false);
        }
       /* if(spward.getSelectedItemId()==0){
            new android.support.v7.app.AlertDialog.Builder(this)
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
            txtmobilenumber.setError(getString(R.string.valid_mobile_number));
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatename() {
        if (edname.getText().toString().trim().isEmpty()) {
            txtname.setError(getString(R.string.valid_name));
            return false;
        } else {
            txtname.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateaddress() {
        if (edaddress.getText().toString().trim().isEmpty()) {
            txtaddress.setError(getString(R.string.valid_address));
            return false;
        } else {
            txtaddress.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateState() {
        if (edstate.getText().toString().trim().isEmpty()) {
            txtstate.setError(getString(R.string.valid_State));
            return false;
        } else {
            txtstate.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateCity() {
        if (edcity.getText().toString().trim().isEmpty()) {
            txtcity.setError(getString(R.string.valid_City));
            return false;
        } else {
            txtcity.setErrorEnabled(false);
        }


        return true;
    }

    private boolean validateDistrict() {

        if (eddistrict.getText().toString().trim().isEmpty()) {
            txtdistrict.setError(getString(R.string.valid_District));
            return false;
        } else {
            txtdistrict.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDob() {
        if (eddob.getText().toString().trim().isEmpty()) {
            txtdob.setError(getString(R.string.valid_dob));
            return false;
        } else {
            txtdob.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatepincode() {
        if (edpin.getText().toString().trim().isEmpty()) {
            txtpin.setError(getString(R.string.valid_Pin_Code));
            return false;
        } else {
            txtpin.setErrorEnabled(false);
        }

        return true;
    }


    /*
    private boolean validateloksabhaconstituency() {
        if (edloksabhaconstituency.getText().toString().trim().isEmpty()) {
            txtloksabhaconstituency.setError(getString(R.string.valid_lok_sabha_constituency));
            return false;
        } else {
            txtloksabhaconstituency.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateconstituency() {
        if (edconstituency.getText().toString().trim().isEmpty()) {
            txtconstituency.setError(getString(R.string.valid_vidhan_sabha_constituency));
            return false;
        } else {
            txtconstituency.setErrorEnabled(false);
        }

        return true;
    }

*/

    private class HttpRequestStatebyCountryid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        Dialog progressDialog;
        int countryId;
        public  HttpRequestStatebyCountryid(int pos) {
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
                //  final String url1 = config.getGetAllState()+countryId+" ";
                final String url1 = config.userremoteurl+"admin/app/getAllState/"+countryId+" ";


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


            if(myPojo!=null && getActivity()!=null) {
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

                spinnerDialogstate = new SpinnerDialog(getActivity(), items, getString(R.string.Select_State), getString(R.string.Close));// With No Animation

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
//                                edloksabhaconstituency.setText("");
//                                edconstituency.setText("");

                            }
                        }

                    }
                });


            }else {
              /*  new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setTitle(getString(R.string.Error))
                        .setMessage("Internet connection problem... ")
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



    private class HttpRequestDistrictNameByStateId extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int stateId;
        Dialog progressDialog;
        public HttpRequestDistrictNameByStateId(int pos) {
            stateId = pos;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(getActivity()!=null){
                progressDialog = new Dialog(getContext());
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.setContentView( R.layout.progressbar );
                progressDialog.show(); // Display Progress Dialog
            }
            else{

            }

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getDistrictNameByStateId/"+stateId+" ";


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


                dialstarPojos6 = myPojo;

                if(districtname!=null){
                    eddistrict.setText(districtname);
                    //new HttpRequestCityNameByDistrictName(districtname).execute();
//                    new HttpRequestLoksabhaConstutuencyNameListByDistrictName(districtname).execute();



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
                spinnerDialogdistrict=new SpinnerDialog(getActivity(), items,getString(R.string.Select_District),getString(R.string.Close));// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogdistrict.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        eddistrict.setText(item);
                        districtname = eddistrict.getText().toString().trim();
                        // new HttpRequestCityNameByDistrictName(districtname).execute();
//                        new HttpRequestLoksabhaConstutuencyNameListByDistrictName(districtname).execute();


                        loksabhaconstituency="";
                        constituencyid=0;
                        edcity.setText("");
                        edpin.setText("");


//                        edloksabhaconstituency.setText("");
//                        edconstituency.setText("");


                        //new HttpRequestConstutuencyNameListByDistrictName(districtname).execute();



                    }
                });



            }else{

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
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";


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

        */
/*    DialstarPojo dp=new DialstarPojo();
            dp.setCityname("--Please select city--");
            dp.setCityid(0);
            dialstarPojos2.add(0,dp);*//*


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
            spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getString(R.string.Select_Constituency),getString(R.string.Add),"constituency");// With No Animation

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
    private class HttpRequestCityNameByStateid extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int stateId=0;
        ProgressDialog progressDialog;
        public HttpRequestCityNameByStateid(int stateId) {


            this.stateId=stateId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(getActivity()!=null){

                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Please wait");  // Setting Message
                //progressDialog.setTitle("Please wait"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog
                progressDialog.setCancelable(false);
            }else{

            }

        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
                //  final String url2 = config.getGetAllCities()+stateId+" ";
                final String url2 = config.userremoteurl+"admin/app/getAllCities/"+stateId+" ";


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



                edcity.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogcity.showSpinerDialog();
                    }
                });
                spinnerDialogcity=new SpinnerDialog(getActivity(), items,getString(R.string.Select_City),getString(R.string.Close));// With No Animation

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

                                    edpin.setText("");
                                    pinid=0;
                                    // new HttpRequestPincode(jsonObject.toString()).execute();
                                    // new HttpRequestConstituencyByCityid(jsonObject.toString()).execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }



                            }
                        }

                    }
                });



            }

            progressDialog.dismiss();

        }
    }
/*



    private class HttpRequestCityNameByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String districtname="";
        ProgressDialog progressDialog;
        public HttpRequestCityNameByDistrictName(String districtname) {


            this.districtname=districtname;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
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
                final String url2 = config.userremoteurl+"admin/app/getCityNameByDistrictName/"+districtname+" ";


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



            ArrayList<String> items=new ArrayList<>();
            for(int i=0;i<dialstarPojos2.size();i++){
                items.add(dialstarPojos2.get(i).getCityname());

            }
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



            */
/*customCityAdapter = new CustomAdapter(Personal_Details.this,
                    R.layout.customspinner, R.id.title, dialstarPojos2);
            spcity.setAdapter(customCityAdapter);

            *//*




            progressDialog.dismiss();

        }
    }


*/
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
            final String url = config.userremoteurl+"admin/app/getAllPincodeByCityId";

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
                spinnerDialogpin=new SpinnerDialog(getActivity(), items,getString(R.string.Select_Pin_Code),getString(R.string.Close));// With No Animation



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

                if(pinid==0){



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }




            progressDialog.dismiss();
        }
    }


    private class HttpRequestTaskReq extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;

        public HttpRequestTaskReq(String json)
        {
            this.json=json;
            Log.d("input jsonupdateprofile",json);

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
            //final String url =config.getUpdateUserProfile();
            final String url =config.userremoteurl+"admin/app/updateUserProfile";

            Log.d("UpdateUserProfile url",url);
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
                return new String(e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            String responceName = greeting;
            //Log.e("responceName =",responceName+"");

            try {
                JSONObject jsonObject=new JSONObject(greeting);
                if(jsonObject.has("aadharcardno"))
                    aadharcardno=jsonObject.getString("aadharcardno");
                if (jsonObject.has("voterid"))
                    voterid=jsonObject.getString("voterid");



            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(responceName.equals(getString(R.string.Error))){
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

            }else if(responceName.equals("timeout")){
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

            }
            else if (responceName.equals("Success")){


/*
                {"name":"Asif","userid":1,"address":"omkar niwas","countryid":99,"stateid":12,"cityid":14,"mobileno":"9769547677","emailid":"asif.pha1@gmail.com","file":"","aadharcardno":"121212121212","voterid":"1212","pincode":"421306"}
*/

                //save all details in shared preference
                edit.putString("name",name);
                edit.putString("Address",address);
                edit.putInt("Countryid", countryid);
                edit.putInt("stateid",stateid);
                edit.putInt("Cityid",cityid);
                edit.putString("MobileNo",mobileno);
                edit.putString("Password",password);
                edit.putString("Emailid",emailid);
                //edit.putString("aadhaarnumber",aadhaarnumber);
                edit.putString("voterid",voterid);
                edit.putString("password",password);
                edit.putString("File",encodedimage);
                edit.putInt("pinid",pinid);
                edit.putInt("constituencyid",constituencyid);
                edit.putString("pin",pin);
                edit.putString("constituency",constituency);
                edit.putString("Cityname",city);
                edit.putInt("wardid",wardid);
                edit.putString("usertype","user");
                edit.putString("ward",ward);
                edit.putString("districtname",districtname);
                edit.putString("loksabhaconstituency",loksabhaconstituency);
                edit.putString("gender",gender);
                edit.putString("dob",dob);

                edit.commit();

                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setMessage(getString(R.string.successfully_updated))
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();
                //Toast.makeText(getActivity()," successfully updated",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

                ((UserDashboard)getActivity()).refreshProfilePic();
            /*    fragment = new Add();
                if (fragment != null) {
                    FragmentManager fragmentManager = ((UserDashboard)getActivity()).getSupportFragmentManager();//getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.viewPager, fragment);
                    fragmentTransaction.commit();
                    //
                }*/
             /*   Intent i=new Intent(getActivity(),UserDashboard.class);
                startActivity(i);*/
            }


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
                final String url2 = config.userremoteurl+"admin/app/getAllSearchingPincodeList/"+pin;


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

        @Override
        protected void onPostExecute(ArrayList<DialstarPojo> greeting) {

            //  dialstarPojos3=new ArrayList<>();

            Activity activity = getActivity();
            if(greeting!=null && activity!=null){
                dialstarPojos3=greeting;
                Log.d("pincode o/p",dialstarPojos3+"");

                if(dialstarPojos3.size()>0) {




                    dialstarPojos4.clear();

                    pinid = dialstarPojos3.get(0).getPinid();
                    state = dialstarPojos3.get(0).getStatename();
                    stateid = dialstarPojos3.get(0).getStateid();
                    edstate.setText(state);
                    eddistrict.setText(dialstarPojos3.get(0).getName());
                    edcity.setText(dialstarPojos3.get(0).getCityname());
                    cityid = dialstarPojos3.get(0).getCityid();
                    final String subject = dialstarPojos3.get(0).getSubject();
                    String constituencyname = dialstarPojos3.get(0).getConstituencyname();

                    try {
                        JSONObject jsonObject = new JSONObject(subject);
                        JSONObject jsonObject_name = new JSONObject(constituencyname);

                        int constituancyid=0;
                        String constituancy="";

                        for(int i=0;i<jsonObject.length();i++){


                            DialstarPojo dialstarPojo = new DialstarPojo();


                            if(jsonObject_name.has("constituencyname"+(i+1)))
                                constituancy= jsonObject_name.getString("constituencyname"+(i+1));

                            if(jsonObject.has("constituencyid"+(i+1)))
                                constituancyid= jsonObject.getInt("constituencyid"+(i+1));

                            dialstarPojo.setConstituencyname(constituancy);
                            dialstarPojo.setConstituencyid(constituancyid);


                            if(!String.valueOf(constituancy).equalsIgnoreCase("null"))
                                dialstarPojos4.add(dialstarPojo);



                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    ArrayList<String> items=new ArrayList<>();
                    for(int i=0;i<dialstarPojos4.size();i++){
                        items.add(dialstarPojos4.get(i).getConstituencyname());


                    }
/*

                    edconstituency.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            spinnerDialogconstituency.showSpinerDialog();
                        }
                    });

                    */
                    spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getString(R.string.Select_Constituency),getString(R.string.Add),"constituency");// With No Animation

                    // spinnerDialogcity.setCancelable(true);
                    spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

//                            edconstituency.setText(item);
                            for(int i=0;i<dialstarPojos4.size();i++){
                                if(item.equalsIgnoreCase(dialstarPojos4.get(i).getConstituencyname())){

                                    constituencyid = dialstarPojos4.get(i).getConstituencyid();




                                }
                            }

                        }
                    });




                }else {
                    new android.support.v7.app.AlertDialog.Builder(getActivity())
                            .setTitle(getString(R.string.Error))
                            .setMessage(getString(R.string.Incorrect_PinCode))
                            .setCancelable(true)
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
            }else if(getActivity()!=null){
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getString(R.string.no_internet))
                        .setCancelable(true)
                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).show();
            }else{

            }



            progressDialog.dismiss();
        }
    }

/*


    private class HttpRequestLoksabhaConstutuencyNameListByDistrictName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {



        String districtname="";
        ProgressDialog progressDialog;

        public HttpRequestLoksabhaConstutuencyNameListByDistrictName(String districtname) {


            this.districtname=districtname;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
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
                // final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+districtname+" ";

                final String url2 = config.userremoteurl+"admin/app/getLokSabhaConstituencyNameByDistrictName/"+districtname;



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
                spinnerDialogloksabhaconstituency=new SpinnerDialog(getActivity(), items,getString(R.string.Select_Lok_Sabha_Constituency),getString(R.string.Close));// With No Animation

                // spinnerDialogcity.setCancelable(true);
                spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    *//*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
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
                        .setMessage(getString(R.string.no_internet))
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
                final String url2 = config.userremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+constituency+" ";


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
            spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getString(R.string.Select_Constituency),getString(R.string.Add),"constituency");// With No Animation

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
                    else if(calling.equalsIgnoreCase("constituency")){
//                        edconstituency.setText(constituencytext);
                        constituencyid=0;
                    }
                }
            });
            this.alertDialog.show();
        }
    }


}
