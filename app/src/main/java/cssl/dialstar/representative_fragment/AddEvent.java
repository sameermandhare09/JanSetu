package cssl.dialstar.representative_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_adapter.EditProfileAdapter;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import cssl.dialstar.R;

public class AddEvent extends Fragment {
    //File file;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    private static final String ARG_PARAM7 = "param7";
    private static final String ARG_PARAM8 = "param8";

    private static final String ARG_PARAM9 = "param9";
    private static final String ARG_PARAM10 = "param10";
    private static final String ARG_PARAM11 = "param11";

    private String mParam1;
    private String mParam2;
    TextView txtback;
    Fragment fragment;
    TextInputLayout txtvieweventname,txtviewfrom,txtviewto,txtviewtimefrom,txtviewtimeto;
    EditText edtdescription, edteventname,edtfrom,edtto,edttimefrom,edttimeto;
    Spinner spstate, spconstituency;
    Button btnstartdate, btnenddate, btnsave;
    String startdate="";
    String starttime="";
    String enddate="";
    String endtime="";

    RadioButton rbloksabha,rbvidhansabha;
    String enddatejson="",startdatejson="";

    private int mYear, mMonth, mDay, mHour, mMinute, mSec,day;
    SharedPreferences mlaPref;
    SharedPreferences.Editor editor ;
    int eventstateid=0;
    int eventconsid=0,eventconsid1=0;
    int representativeid = 0;
    JSONObject jsonObject;
    int stateId,constituencyId,constituencyIdforpopulation;
    String eventloksabhaconstituency="",loksabhaconstituency="",loksabhaconstituencyforpopulation="",name="";

    Config config=new Config();
    int flag=0;
    String eventname = "", eventstartdate = "", eventenddate = "";
    String description = "";
    int eventid = 0, userid = 0, editevent = 0;
    ArrayList<DialstarPojo> dialstarPojos1;
    ArrayList<DialstarPojo> dialstarPojos2;
    ArrayList<DialstarPojo> dialstarPojos3;
    EditProfileAdapter customAdapter;
    private OnFragmentInteractionListener mListener;
    private TextInputLayout txtname, txtaddress, txtmobilenumber,txtdistrict,
            txtemail,txtarea, txtward, txtpin,txtloksabhaconstituency,txtcity,
            txtconstituency,txtstate;
    private EditText edname, edaddress, edmobilenumber,
            edemail, edconstituency,edpin, edaadhaarnumber,edvoterid,edcity,
            eddistrict,edstate,edloksabhaconstituency;
    LinearLayout lnrloksabha,lnrvidhansabha;
    SpinnerDialog spinnerDialogconstituency,spinnerDialogstate,spinnerDialogloksabhaconstituency;
    private TextView txtpopulation;

    public AddEvent() {
        // Required empty public constructor
    }
  public static AddEvent newInstance(int param1, int param2,String param3,
                                     int param4, int param5,
                                     String param6,String param7,String param8,
                                     int param9,int param10,String eventloksabhaconstituency) {
        AddEvent fragment = new AddEvent();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
      args.putString(ARG_PARAM3, param3);
      args.putInt(ARG_PARAM4, param4);
      args.putInt(ARG_PARAM5, param5);
      args.putString(ARG_PARAM6, param6);
      args.putString(ARG_PARAM7, param7);
      args.putString(ARG_PARAM8, param8);
      args.putInt(ARG_PARAM9, param9);
      args.putInt(ARG_PARAM10, param10);
      args.putString(ARG_PARAM11, eventloksabhaconstituency);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*
            flag,editevent,eventname,eventstateid,
                        eventconsid,eventstartdate,eventenddate,description,eventid,userid
            */
            flag = getArguments().getInt(ARG_PARAM1);
            editevent = getArguments().getInt(ARG_PARAM2);
            eventname=getArguments().getString(ARG_PARAM3);
            eventstateid=getArguments().getInt(ARG_PARAM4);
            eventconsid=getArguments().getInt(ARG_PARAM5);
            eventstartdate=getArguments().getString(ARG_PARAM6);
            eventenddate=getArguments().getString(ARG_PARAM7);
             description=getArguments().getString(ARG_PARAM8);
            eventid=getArguments().getInt(ARG_PARAM9);
            userid=getArguments().getInt(ARG_PARAM10);
            eventloksabhaconstituency = getArguments().getString(ARG_PARAM11);

            constituencyIdforpopulation = eventconsid;
            loksabhaconstituencyforpopulation = eventloksabhaconstituency;

            Log.d("editevent",editevent+"");
            Log.d("flag",flag+"");
            Log.d("eventname",eventname+"");
            Log.d("eventstateid",eventstateid+"");
            Log.d("eventconsid",eventconsid+"");
            Log.d("eventstartdate",eventstartdate+"");
            Log.d("eventenddate",eventenddate+"");
            Log.d("description",description+"");
            Log.d("eventid",eventid+"");
            Log.d("userid",userid+"");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view= inflater.inflate(R.layout.fragment_add_event2, container, false);
         txtback = view.findViewById(R.id.back);
        txtvieweventname = (TextInputLayout) view.findViewById(R.id.txt_event_name);
        edteventname = (EditText)  view.findViewById(R.id.edeventname);
        txtviewfrom = (TextInputLayout) view.findViewById(R.id.txtview_start_date);
        edtfrom =  (EditText)  view.findViewById(R.id.edtxt_start_date);
        txtviewtimefrom = (TextInputLayout) view.findViewById(R.id.txtview_start_time);
        edttimefrom =  (EditText)  view.findViewById(R.id.edtxt_start_time);
        txtviewto = (TextInputLayout) view.findViewById(R.id.txtview_end_date);
        edtto =  (EditText)  view.findViewById(R.id.edtxt_end_date);
        txtviewtimeto = (TextInputLayout) view.findViewById(R.id.txtview_end_time);
        edttimeto =  (EditText)  view.findViewById(R.id.edtxt_end_time);
        spstate = view.findViewById(R.id.spstate);
        spconstituency = view.findViewById(R.id.spconstituency);
        edtdescription = (EditText) view.findViewById(R.id.ed_description);
        btnsave = (Button) view.findViewById(R.id.btn_save);
        txtpopulation=view.findViewById(R.id.txtpopulation);
        rbloksabha = (RadioButton)view.findViewById(R.id.rbloksabha);
        rbvidhansabha = (RadioButton)view.findViewById(R.id.rbvidhansabha);

        txtstate=(TextInputLayout)view.findViewById(R.id.txtstate);
        txtloksabhaconstituency=(TextInputLayout)view.findViewById(R.id.txtloksabhaconstituency);
        txtconstituency=(TextInputLayout)view.findViewById(R.id.txtconstituency);
        edconstituency=(EditText)view.findViewById(R.id.edconstituency);
        edloksabhaconstituency=(EditText)view.findViewById(R.id.edloksabhaconstituency);
        edstate = (EditText)view.findViewById(R.id.edstate);
        lnrloksabha = (LinearLayout)view.findViewById(R.id.lnrloksabha);
        lnrvidhansabha = (LinearLayout)view.findViewById(R.id.lnrvidhansabha);


        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor= mlaPref.edit();
        representativeid = mlaPref.getInt("representativeid", 0);
        //eventstateid=mlaPref.getInt("stateIdevent",0);
        //eventconsid=mlaPref.getInt("constituencyIdevent",0);
        constituencyId = mlaPref.getInt("constituencyid",0);


        stateId = mlaPref.getInt("stateid",0);
        loksabhaconstituency = mlaPref.getString("loksabhaconstituency","");


        name = mlaPref.getString("name", "");

        if (editevent == 1) {

            // toolbar.setTitle("Edit Event");
            edteventname.setText(eventname);
            edtdescription.setText(description);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
            SimpleDateFormat formatdate = new SimpleDateFormat("MMM dd yyyy");
            SimpleDateFormat formattime = new SimpleDateFormat("hh:mm a");

            Date date;
            try {
                date = dateFormat.parse(eventstartdate);
                String startdate = formatdate.format(date);
                edtfrom.setText(startdate);
                String starttime = formattime.format(date);
                edttimefrom.setText(starttime);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            Date date1;
            try {
                date1 = dateFormat.parse(eventenddate);
                String enddate = formatdate.format(date1);
                edtto.setText(enddate);
                String endtime = formattime.format(date1);
                edttimeto.setText(endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);





        }else{
            constituencyIdforpopulation=constituencyId;
            loksabhaconstituencyforpopulation = loksabhaconstituency;
            getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);

            /*JSONObject json = new JSONObject();
            json = new JSONObject();
            try {
                json.put("constituencyid",constituencyIdforpopulation);
                json.put("subject",loksabhaconstituencyforpopulation);
                //Log.d("login Json:", String.valueOf(json));
                new HttpRequestJanhitPopulation(json.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }


        new HttpRequestTaskstate().execute();


        edtfrom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                day = c.get(Calendar.DAY_OF_WEEK);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),

                        R.style.RepresentativeDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {




                                startdate =  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                               // getTime();

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");

                                Date date;
                                try {
                                    date = format.parse(startdate);
                                    startdate = dateFormat.format(date);
                                    Log.d("startdate",startdate);
                                    edtfrom.setText(startdate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                // edtfrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                // txtviewstartdate.setText("");
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                //datePickerDialog.setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }


        });



        edttimefrom.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // mSec = c.get(Calendar.SECOND);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        R.style.RepresentativeDatePickerDialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                starttime = " " + mHour + ":" + mMinute;
                                SimpleDateFormat format = new SimpleDateFormat(" HH:mm");
                                SimpleDateFormat dateFormat = new SimpleDateFormat(" hh:mm a");


                             /*   startdate = startdate+starttime;
                                startdatejson = startdate;*/
                                Log.d("startdate",starttime);
                               /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a");
*/
                                Date date;
                                try {
                                    date = format.parse(starttime);
                                    starttime = dateFormat.format(date);
                                    Log.d("startdate",starttime);
                                    edttimefrom.setText(starttime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // edtfrom.append(" " + mHour + ":" + mMinute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });

        edtto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                day = c.get(Calendar.DAY_OF_WEEK);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        R.style.RepresentativeDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {




                                enddate =  year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                               // getTime();

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy");

                                Date date;
                                try {
                                    date = format.parse(enddate);
                                    enddate = dateFormat.format(date);
                                    Log.d("enddate",enddate);
                                    edtto.setText(enddate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }




                                // edtfrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                // txtviewstartdate.setText("");
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }

        });
        edttimeto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                // mSec = c.get(Calendar.SECOND);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        R.style.RepresentativeDatePickerDialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                mHour = hourOfDay;
                                mMinute = minute;
                                endtime = " " + mHour + ":" + mMinute;

                             /*   enddate = enddate+endtime;
                                enddatejson = enddate;*/
                               // Log.d("enddate",enddate);
                                SimpleDateFormat format = new SimpleDateFormat(" HH:mm");
                                SimpleDateFormat dateFormat = new SimpleDateFormat(" hh:mm a");

                                Date date;
                                try {
                                    date = format.parse(endtime);
                                    endtime = dateFormat.format(date);
                                    Log.d("endtime",endtime);
                                    edttimeto.setText(endtime);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                // edtfrom.append(" " + mHour + ":" + mMinute);

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

            }
        });

         txtback.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                      fragment = new Event();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

             }
         });


         if(flag==1){
             rbvidhansabha.setChecked(true);
         }else{
             if(eventconsid>0){
                 lnrvidhansabha.setVisibility(View.VISIBLE);
                 rbvidhansabha.setChecked(true);

             }else{
                 lnrvidhansabha.setVisibility(View.GONE);
                 rbloksabha.setChecked(true);
             }
         }


         rbloksabha.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 lnrvidhansabha.setVisibility(View.GONE);
                 getJanhitPopulation(0,loksabhaconstituencyforpopulation);

          /*       JSONObject json = new JSONObject();
                 json = new JSONObject();
                 try {
                     json.put("constituencyid",0);
                     json.put("subject",loksabhaconstituencyforpopulation);
                     //Log.d("login Json:", String.valueOf(json));
                     new HttpRequestJanhitPopulation(json.toString()).execute();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }*/
                 //edloksabhaconstituency.setVisibility(View.VISIBLE);

             }
         });
         rbvidhansabha.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 lnrvidhansabha.setVisibility(View.VISIBLE);
                 getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);

             /*    JSONObject json = new JSONObject();

                 json = new JSONObject();
                 try {
                     json.put("constituencyid",constituencyIdforpopulation);
                     json.put("subject",loksabhaconstituencyforpopulation);
                     //Log.d("login Json:", String.valueOf(json));
                     new HttpRequestJanhitPopulation(json.toString()).execute();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }*/
                 //edloksabhaconstituency.setVisibility(View.GONE);
             }
         });
         btnsave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 submit();
             }
         });
        return view;
    }

    private void getJanhitPopulation(int constituencyIdforpopulation, String loksabhaconstituencyforpopulation) {

        JSONObject json = new JSONObject();
        json = new JSONObject();
        try {
            json.put("constituencyid",constituencyIdforpopulation);
            json.put("subject",loksabhaconstituencyforpopulation);
            //Log.d("login Json:", String.valueOf(json));
            new HttpRequestJanhitPopulation(json.toString()).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void submit() {

       /* if(lnrvidhansabha.getVisibility()==View.GONE){
            eventconsid1=0;

        }else {

            eventconsid1 = eventconsid;

        }*/
       eventconsid1=0;

        String eventname = edteventname.getText().toString().trim();


        String description = edtdescription.getText().toString().trim();
        String usertype = mlaPref.getString("usertype", "");
        String startdate = edtfrom.getText().toString().trim();

        String starttime = edttimefrom.getText().toString().trim();
        String startdatetime = startdate+" "+starttime;
        Log.d("startdatetime",startdatetime);
        eventloksabhaconstituency = edloksabhaconstituency.getText().toString().trim();



            if (!validateEventName()) {
                return;
            } else if (!validateStartDate()) {
                return;
            }
            else if (!validateStartTime()) {
                return;
            }else if (!validateEndDate()) {
                return;
            }
            else if (!validateEndTime()) {
                return;
            }else if (!validateState()) {
                return;
            }
            else if (!validateLokSabhaConstituency() ) {
                return;
            }
            else if (!validateConstituency() && lnrvidhansabha.getVisibility()==View.VISIBLE) {
                return;
            }
            else if (!validateDescription()) {
                return;
            } else {


                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy hh:mm a");

                Date date = null;
                try {
                    date = dateFormat.parse(startdatetime);
                    startdatetime = format.format(date);
                    Log.d("startdatetime formated",startdatetime);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String enddate = edtto.getText().toString().trim();
                String endtime = edttimeto.getText().toString().trim();
                String enddatetime = enddate+" "+endtime;
                Log.d("enddatetime ",enddatetime);
                // int userrepresentativeidid =representativeid;
                Date date1 = null;
                try {
                    date1 = dateFormat.parse(enddatetime);
                    enddatetime = format.format(date1);
                    Log.d("enddatetime formated",enddatetime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String eventstartdate = startdatetime;
                String eventenddate = enddatetime;

                if(date1.after(date)){

                    if (editevent == 1) {
                        jsonObject = new JSONObject();

                        try {

                            jsonObject.put("eventid", eventid);
                            jsonObject.put("eventname", eventname);
                            jsonObject.put("eventstartdate", eventstartdate);
                            jsonObject.put("eventenddate", eventenddate);
                            jsonObject.put("description", description);
                            jsonObject.put("userid", representativeid);
                            jsonObject.put("representativename",name);

                            if(stateId>0){
                                jsonObject.put("stateid",eventstateid);
                            }
                            else
                            {
                                jsonObject.put("stateid",0);
                            }
                            jsonObject.put("constituencyid",eventconsid1);
                            jsonObject.put("name",eventloksabhaconstituency);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("update event json", String.valueOf(jsonObject));
                        new HttpRequestUpdateEvent(jsonObject.toString()).execute();



                    } else if (editevent == 0) {
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("eventname", eventname);
                            jsonObject.put("eventstartdate", eventstartdate);
                            jsonObject.put("eventenddate", eventenddate);
                            jsonObject.put("description", description);
                            jsonObject.put("representativeid", representativeid);
                            jsonObject.put("name", eventloksabhaconstituency);
                            jsonObject.put("usertype", usertype);
                            //jsonObject.put("representativename",name);

                            if(stateId>0){
                                jsonObject.put("stateid",eventstateid);
                            }
                            else
                            {
                                jsonObject.put("stateid",0);
                            }
                            jsonObject.put("constituencyid",eventconsid1);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("add event json",jsonObject.toString());
                        new HttpRequestAddEvent(jsonObject.toString()).execute();
                        // Log.d("json", String.valueOf(jsonObject));

                    }



                }else{

                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                    alertDialog.setTitle("Error..!");
                    alertDialog.setMessage(getResources().getString(R.string.Please_enter_start_date_before_the_end_date));
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();

                }


            }



    }


    private boolean validateDescription() {
        if (edtdescription.getText().toString().isEmpty()) {
            //edtdescription.setError("Please enter Description");

            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Error..!");
            alertDialog.setMessage(getResources().getString(R.string.Please_enter_Description));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return false;
        }

        return true;
    }



    private boolean validateEndDate() {
        if (edtto.getText().toString().isEmpty()) {
            //  txtviewenddate.setError("Please enter End Date");
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getResources().getString(R.string.Error));
            alertDialog.setMessage(getResources().getString(R.string.Please_enter_End_Date));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return false;
        }

        return true;
    }

    private boolean validateStartDate() {

        if (edtfrom.getText().toString().isEmpty()) {
            // txtviewstartdate.setError("Please enter Start Date");
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getResources().getString(R.string.Error));
            alertDialog.setMessage(getResources().getString(R.string.Please_enter_Start_Date));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return false;
        }

        return true;
    }
    private boolean validateStartTime() {

        if (edttimefrom.getText().toString().isEmpty()) {
            // txtviewstartdate.setError("Please enter Start Date");
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getResources().getString(R.string.Error));
            alertDialog.setMessage(getResources().getString(R.string.Please_enter_Start_Time));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return false;
        }

        return true;
    }
    private boolean validateEndTime() {

        if (edttimeto.getText().toString().isEmpty()) {
            // txtviewstartdate.setError("Please enter Start Date");
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getResources().getString(R.string.Error));
            alertDialog.setMessage(getResources().getString(R.string.Please_enter_End_Time));
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
            return false;
        }

        return true;
    }

    private boolean validateEventName() {
        if (edteventname.getText().toString().trim().isEmpty()) {
            txtvieweventname.setError(getResources().getString(R.string.Please_enter_Event_Name));
            return false;
        } else {
            txtvieweventname.setErrorEnabled(false);
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

    private boolean validateConstituency() {

        if (edconstituency.getText().toString().trim().isEmpty()) {
            txtconstituency.setError(getResources().getString(R.string.valid_vidhan_sabha_constituency));
            return false;
        } else {
            txtconstituency.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLokSabhaConstituency() {

        if (edloksabhaconstituency.getText().toString().trim().isEmpty()) {
            txtloksabhaconstituency.setError(getResources().getString(R.string.valid_lok_sabha_constituency));
            return false;
        } else {
            txtloksabhaconstituency.setErrorEnabled(false);
        }

        return true;
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private class HttpRequestTaskstate extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {

        Dialog progressDialog;

        public HttpRequestTaskstate() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog
// Display Progress Dialog


        }

        @Override
        protected ArrayList<DialstarPojo> doInBackground(Void... params) {
            try {
               // final String url1 = config.getGetAllState()+99;
                final String url1 = config.representativeremoteurl+"admin/app/getAllState/"+99;

                Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                DialstarPojo[] forNow1 = restTemplate.getForObject(url1, DialstarPojo[].class);
                ArrayList<DialstarPojo> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(ArrayList<DialstarPojo> myPojo) {
            if(myPojo!=null){
                Log.i("result output", myPojo.size() + "");



                dialstarPojos1 = myPojo;

                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < dialstarPojos1.size(); i++) {
                    items.add(dialstarPojos1.get(i).getStatename());

                }


                //spstate.setSelection(0);
                if(flag==1){
                    for(int i=0;i<dialstarPojos1.size();i++){
                        if(stateId == dialstarPojos1.get(i).getStateid()){
                            edstate.setText(dialstarPojos1.get(i).getStatename());
                            if (stateId > 0) {

                                JSONObject json = new JSONObject();
                                try {
                                    json.put("stateid", stateId);
                                    eventstateid = stateId;
                                    Log.d("sending Json :- ", String.valueOf(json));
                                    //new HttpRequestTaskReq(json.toString()).execute();
                                    new HttpRequestLokSabhaConstituencyByStateId(stateId).execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    }
                }
                else {
                    for (int i = 0; i < dialstarPojos1.size(); i++) {
                        if (eventstateid == dialstarPojos1.get(i).getStateid()) {
                            edstate.setText(dialstarPojos1.get(i).getStatename());
                            if (eventstateid > 0) {

                                JSONObject json = new JSONObject();
                                try {
                                    json.put("stateid", eventstateid);
                                    Log.d("sending Json :- ", String.valueOf(json));
                                    //new HttpRequestTaskReq(json.toString()).execute();
                                    new HttpRequestLokSabhaConstituencyByStateId(eventstateid).execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

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

                                eventstateid = dialstarPojos1.get(i).getStateid();
                                edloksabhaconstituency.setText("");
                                edconstituency.setText("");
                                if (eventstateid > 0) {

                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("stateid", eventstateid);
                                        Log.d("sending Json :- ", String.valueOf(json));
                                        //new HttpRequestTaskReq(json.toString()).execute();
                                        new HttpRequestLokSabhaConstituencyByStateId(eventstateid).execute();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    // spconstituency.setVisibility(View.GONE);
                                }


                            }
                        }

                    }
                });


            }else{
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


    private class HttpRequestLokSabhaConstituencyByStateId extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


        int stateid;
        Dialog progressDialog;
        public HttpRequestLokSabhaConstituencyByStateId(int stateid) {
            this.stateid= stateid;

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

                final String url2 = config.representativeremoteurl+"admin/app/getLokSabhaConstituencyByStateId/"+stateid;


               // Log.i("LokSabhaConstituencyName url", url2);
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

            progressDialog.dismiss();
            if(myPojo!=null){

                dialstarPojos3 = new ArrayList<DialstarPojo>();

                dialstarPojos3 = myPojo;

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos3.size();i++){
                    items.add(dialstarPojos3.get(i).getSubject());

                }
                if(flag==1){
                    for(int i=0;i<dialstarPojos3.size();i++){
                        if(loksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject()) ){
                            edloksabhaconstituency.setText(dialstarPojos3.get(i).getSubject());
                            new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituency).execute();

                        }

                    }
                }
                else {
                    for (int i = 0; i < dialstarPojos3.size(); i++) {
                        if (eventloksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject())) {
                            edloksabhaconstituency.setText(dialstarPojos3.get(i).getSubject());

                            new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(eventloksabhaconstituency).execute();
                        }
                    }

                }

                edloksabhaconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogloksabhaconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogloksabhaconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Lok_Sabha_Constituency),getResources().getString(R.string.Close));// With No Animation


                spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {


                        edloksabhaconstituency.setText(item);
                        eventloksabhaconstituency = edloksabhaconstituency.getText().toString().trim();

                        loksabhaconstituencyforpopulation = eventloksabhaconstituency;
                        getJanhitPopulation(0,loksabhaconstituencyforpopulation);

                     /*   JSONObject json = new JSONObject();
                        json = new JSONObject();
                        try {
                            json.put("constituencyid",0);
                            json.put("subject",loksabhaconstituencyforpopulation);
                            //Log.d("login Json:", String.valueOf(json));
                            new HttpRequestJanhitPopulation(json.toString()).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        edconstituency.setText("");
                        new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(eventloksabhaconstituency).execute();


                    }
                });


            }else{
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




        }
    }


    private class HttpRequestConstutuencyNameListByLoksabhaConstituencyName extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {




        String loksabhaconstituency;

        Dialog progressDialog;
        public HttpRequestConstutuencyNameListByLoksabhaConstituencyName(String loksabhaconstituency) {


            this.loksabhaconstituency=loksabhaconstituency;
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

                // final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+loksabhaconstituency+" ";

                final String url2 = Config.representativeremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+loksabhaconstituency+" ";


                Log.i("ConstutuencyList url", url2);
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

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos2.size();i++){
                    items.add(dialstarPojos2.get(i).getConstituencyname());


                }

                if(flag==1){
                    for (int i = 0; i < dialstarPojos2.size(); i++) {
                        if (constituencyId == dialstarPojos2.get(i).getConstituencyid()) {
                            eventconsid = constituencyId;
                            edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                        }
                    }
                }else {
                    for (int i = 0; i < dialstarPojos2.size(); i++) {
                        if (eventconsid == dialstarPojos2.get(i).getConstituencyid()) {
                            edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                            //spconstituency.setSelection(i);
                        }
                    }
                }



                edconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Constituency),getResources().getString(R.string.Close));// With No Animation

                spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        edconstituency.setText(item);
                        for(int i=0;i<dialstarPojos2.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos2.get(i).getConstituencyname())){

                                eventconsid = dialstarPojos2.get(i).getConstituencyid();
                                constituencyIdforpopulation = eventconsid;
                               // loksabhaconstituencyforpopulation = eventloksabhaconstituency;


                                getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);

                         /*       JSONObject json = new JSONObject();
                            try {
                                json.put("constituencyid", constituencyIdforpopulation);
                                json.put("subject", loksabhaconstituencyforpopulation);
                                Log.d("sending Json :- ", String.valueOf(json));
                                //new HttpRequestTaskReq(json.toString()).execute();
                                new HttpRequestJanhitPopulation(json.toString()).execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
*/

                            }
                        }
                        if (eventconsid > 0) {
                            eventloksabhaconstituency = edloksabhaconstituency.getText().toString().trim();
                        } else {
                            // spconstituency.setVisibility(View.GONE);
                        }
                    }
                });



            }else{
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

            progressDialog.dismiss();

        }
    }




    private class HttpRequestJanhitPopulation extends AsyncTask<Void, Void,String > {
        String json;
        Dialog progressDialog;
        String mobilenumber;
        String password;

        public HttpRequestJanhitPopulation(String json)
        {
            this.json=json;
             Log.e("getpopulation count Json:", String.valueOf(json));


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
            // final String url = config.getMla_login();
            final String url = config.representativeremoteurl+"admin/app/getJanhitPopulation";

            Log.e("login url",url);
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

                return null;


            }

        }

        @Override
        protected void onPostExecute(String greeting) {


            if(greeting!=null){
                String responceName = greeting;
                Log.e(" login responce:- ",responceName);
                txtpopulation.setText(Html.fromHtml( ""+"<font color='black'><font size='22dp'>" + getResources().getString(R.string.Population) +responceName + "</font>"));

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

/*



    private class HttpRequestTaskforpopulation extends AsyncTask<Void, Void, String> {
        String json;
        ProgressDialog progressDialog;

        public HttpRequestTaskforpopulation(String json) {
            this.json = json;
            Log.e("JanhitPopulation_json",json);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please wait");  // Setting Message
            // progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(Void... params) {
            // try {
            //  final String url = config.getGetAllConstituencyByStateId();
            final String url = config.representativeremoteurl + "getJanhitPopulation";
            Log.e("getJanhitPopulation",url);
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
            progressDialog.dismiss();
            if (greeting != null) {


                String responceName = greeting;
                // Log.e("constituency Data =", responceName + "" );
                //Log.e  ("Response size=", String.valueOf(responceName.length()));

                try {
                    JSONObject jsonObject = new JSONObject(responceName);
//                        String constituencyname=jsonObject.getString("constituencyname");
                    int count = jsonObject.getInt("count");
                    txtpopulation.setText("Janhit Population : " + String.valueOf(count));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                        Log.i("constituency details",constituencyid+" "+constituencyname);

            }


        }
    }



*/


    private class HttpRequestTaskReq extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;

        public HttpRequestTaskReq(String json) {
            this.json = json;

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
          //  final String url = config.getGetAllConstituencyByStateId();
            final String url = config.representativeremoteurl+"admin/app/getAllConstituencyByStateId";

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
            progressDialog.dismiss();
            if(greeting!=null){
                dialstarPojos2=new ArrayList<>();


                String responceName = greeting;
                // Log.e("constituency Data =", responceName + "" );
                //Log.e  ("Response size=", String.valueOf(responceName.length()));
                try {
                    JSONArray jsonArray=new JSONArray(greeting);
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String constituencyname=jsonObject.getString("constituencyname");
                        int constituencyid=jsonObject.getInt("constituencyid");
                        DialstarPojo dialstar=new DialstarPojo();
                        dialstar.setConstituencyname(constituencyname);
                        dialstar.setConstituencyid(constituencyid);
                        dialstarPojos2.add(dialstar);


                        Log.i("constituency details",constituencyid+" "+constituencyname);
                    }

                    ArrayList<String> items=new ArrayList<>();
                    for(int i=0;i<dialstarPojos2.size();i++){
                        items.add(dialstarPojos2.get(i).getConstituencyname());


                    }

                    if(flag==1){
                        for (int i = 0; i < dialstarPojos2.size(); i++) {
                            if (constituencyId == dialstarPojos2.get(i).getConstituencyid()) {
                                eventconsid = constituencyId;
                                edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                            }
                        }
                    }else {
                        for (int i = 0; i < dialstarPojos2.size(); i++) {
                            if (eventconsid == dialstarPojos2.get(i).getConstituencyid()) {
                                edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                                //spconstituency.setSelection(i);
                            }
                        }
                    }



                    edconstituency.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            spinnerDialogconstituency.showSpinerDialog();
                        }
                    });
                    spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,getResources().getString(R.string.Select_Constituency),getResources().getString(R.string.Close));// With No Animation

                    spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                            edconstituency.setText(item);
                            for(int i=0;i<dialstarPojos2.size();i++){
                                if(item.equalsIgnoreCase(dialstarPojos2.get(i).getConstituencyname())){

                                    eventconsid = dialstarPojos2.get(i).getConstituencyid();


                                }
                            }

                        }
                    });


                    Log.i("dialstarPojos2",dialstarPojos2+".. ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{
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



        }
    }



    private class HttpRequestAddEvent extends AsyncTask<Void, Void, String> {
        String json;
        Dialog progressDialog;


        public HttpRequestAddEvent(String json) {
            this.json = json;
            Log.d("AddEvent json",json);

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
           // final String url = config.getAddNewEventDetails();
            final String url = config.representativeremoteurl+"admin/app/addNewEventDetails";
            Log.d("AddNewEvent url",url);
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
            //Log.e("responceName:- ", responceName);

            if(responceName.equalsIgnoreCase("success")) {
                edteventname.setHint(getResources().getString(R.string.Event_Name));
                edtdescription.setHint(getResources().getString(R.string.Description));
                edtto.setHint("to");
                edtfrom.setHint("from");
                Toast.makeText(getContext(), getResources().getString(R.string.Your_event_successfully_added), Toast.LENGTH_SHORT).show();
                fragment = new Event();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }else if (responceName.equalsIgnoreCase(getString(R.string.Error))) {

                Toast.makeText(getContext(), getResources().getString(R.string.Event_not_add_successfully), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

            }
            progressDialog.dismiss();
        }

    }


    public class HttpRequestUpdateEvent extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;

        public  HttpRequestUpdateEvent(String jsonstr) {
            this.jsonstr = jsonstr;
            Log.d("UpdateEvent  json",jsonstr);
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

                //String url = config.getUpdateUserEventDetails();
                String url = config.representativeremoteurl+"admin/app/updateUserEventDetails/";

                //Log.e("UpdateUserEventDetails", url);
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
            progressDialog.dismiss();
            if (result.equalsIgnoreCase("success")) {

                Toast.makeText(getContext(), getResources().getString(R.string.Event_updated_successfully), Toast.LENGTH_SHORT).show();
                fragment = new Event();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            } else if (result.equalsIgnoreCase("Error")) {

                Toast.makeText(getContext(), getResources().getString(R.string.Event_not_updated_successfully), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

            }


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
