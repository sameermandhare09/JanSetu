package cssl.dialstar.user_fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;

public class CompleteGrievance extends Fragment {


    TextView txtnodata,txtecount,txtviewfromdate,txtviewtodate;
    private RecyclerView recyclerView;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    ConfigUser config;
    ArrayList<Dialstar> dialstar=new ArrayList<>();
    ArrayList<Dialstar> dialstar1=new ArrayList<>();
    private cssl.dialstar.user_adapter.Grievanceadapter mAdapter;
    int userid = 0;
    String UserId = "";
    ImageView imageViewfilter;
    private int mYear, mMonth, mDay, mHour, mMinute,mSec;
    String fromdate,todate;
    Date closeddate1 = null,fromdate1 = null,todate1 = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    RelativeLayout relativeLayout ;
    Date dfromdate2=null,dtodate2=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_complete_grievance, container, false);;

        txtnodata = rootView.findViewById(R.id.txtview);
        txtecount = rootView.findViewById(R.id.txtviewcount);
        imageViewfilter = rootView.findViewById(R.id.imgeviewfilter);
        txtviewfromdate = rootView.findViewById(R.id.txtviewfromdate);
        txtviewtodate = rootView.findViewById(R.id.txtviewtodate);
        imageViewfilter.setImageResource(R.drawable.ic_filter_list_black_24dp);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        relativeLayout = rootView.findViewById(R.id.relativefilterdates);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = share.edit();
        config=new ConfigUser();

        userid = share.getInt("Userid", 0);
        UserId = String.valueOf(userid);
        imageViewfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final TextView textViewfromdate = new TextView(getContext());
                textViewfromdate.setText("From Date: ");
                textViewfromdate.setPadding(10,10,10,10);
                TextView textViewtodate = new TextView(getContext());
                textViewtodate.setText("To Date: ");
                textViewtodate.setPadding(10,10,10,10);
                final TextView textViewfromdate1 = new TextView(getContext());
                textViewfromdate1.setPadding(10,10,10,10);
                textViewfromdate1.setBackgroundResource(R.drawable.chatedittext);
                final TextView textViewtodate1 = new TextView(getContext());
                textViewtodate1.setPadding(10,10,10,10);
                textViewtodate1.setBackgroundResource(R.drawable.chatedittext);


                String sfromdate3 = null,stodate3 = null;

                String sfromdate2 = txtviewfromdate.getText().toString();
                try {
                     dfromdate2 = dateFormat.parse(sfromdate2);
                    sfromdate3 = simpleDateFormat.format(dfromdate2);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String stodate2 = txtviewtodate.getText().toString();
                try {
                     dtodate2 = dateFormat.parse(stodate2);
                    stodate3 = simpleDateFormat.format(dtodate2);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textViewfromdate1.setText(sfromdate3);
                textViewtodate1.setText(stodate3);

                textViewfromdate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        Date dfromdate3=null;
                        String sfromdate3="";

                        String selecteddate=textViewfromdate1.getText().toString();
                        try {
                            dfromdate3 = simpleDateFormat.parse(selecteddate);


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dfromdate2!=null)
                        {
                            c.setTime(dfromdate2);
                        }
                        if(dfromdate3!=null)
                        {
                            c.setTime(dfromdate3);
                        }
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view,int year ,
                                                          int monthOfYear,int dayOfMonth ) {




                                        textViewfromdate1.setText(dayOfMonth+ "-" + (monthOfYear + 1)+ "-"+year);

                                       c.set(Calendar.YEAR,year);
                                        c.set(Calendar.MONTH,(monthOfYear + 1));
                                        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                                        mYear = year;
                                        mMonth =(monthOfYear + 1);
                                        mDay = dayOfMonth;
                                        // txtviewstartdate.setText("");
                                    }
                                }, c
                                .get(Calendar.YEAR), c.get(Calendar.MONTH),
                                c.get(Calendar.DAY_OF_MONTH) );
                        datePickerDialog.show();

                    }


                });


                textViewtodate1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();
                        Date dtodate3=null;
                        String stodate3="";

                        String selecteddate=textViewtodate1.getText().toString();
                        try {
                            dtodate3 = simpleDateFormat.parse(selecteddate);


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dtodate2!=null)
                        {
                            c.setTime(dtodate2);
                        }
                        if(dtodate3!=null)
                        {
                            c.setTime(dtodate3);
                        }
                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view,int year ,
                                                          int monthOfYear,int dayOfMonth ) {

                                        textViewtodate1.setText( dayOfMonth+ "-" + (monthOfYear + 1)+ "-"+year);

                                       /* mYear = year;
                                        mMonth =(monthOfYear + 1);
                                        mDay = dayOfMonth;*/
                                    }
                                }, mYear , mMonth,mDay );
                        datePickerDialog.show();

                    }
                                    });

                final LinearLayout linearLayout  = new LinearLayout(getContext());
                linearLayout.setPadding(20,10,20,10);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(textViewfromdate);
                linearLayout.addView(textViewfromdate1);

                linearLayout.addView(textViewtodate);
                linearLayout.addView(textViewtodate1);
                //linearLayout.addView(btnsearch);



                final AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setView(linearLayout)
                        .setTitle("Add Filter ")
                        .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                        .setNegativeButton(android.R.string.cancel, null)
                        .setCancelable(false)
                        .create();



               /* dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialogInterface) {


                    }
                });*/
                dialog.show();
                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something



                        txtviewtodate.setText("To: "+todate);

                        fromdate = textViewfromdate1.getText().toString().trim();


                        if (textViewfromdate1.getText().toString().trim().isEmpty()){

                            textViewfromdate1.setError("Please enter start date");

                        }

                        todate = textViewtodate1.getText().toString().trim();
                        if (textViewtodate1.getText().toString().trim().isEmpty()){

                            textViewtodate1.setError("Please enter end date");

                        }

                        else {
                            dialstar1.clear();
                            for (int i = 0; i < dialstar.size(); i++) {
                                String cLoseddate = dialstar.get(i).getCloseddate();

                                try {
                                    closeddate1 = format.parse(cLoseddate);
                                    fromdate1 = simpleDateFormat.parse(fromdate);
                                    todate1 = simpleDateFormat.parse(todate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (fromdate1 != null) {

                                    if (fromdate1.equals(todate1) || fromdate1.before(todate1)) {
                                        if (closeddate1.equals(fromdate1) || closeddate1.after(fromdate1)) {
                                            if (closeddate1.equals(todate1) || closeddate1.before(todate1)) {
                                                Dialstar dialstarobj = new Dialstar();
                                                dialstarobj = dialstar.get(i);
                                                dialstar1.add(dialstarobj);

                                                String fromdate = dateFormat.format(fromdate1);
                                                String todate = dateFormat.format(todate1);
                                                relativeLayout.setVisibility(View.VISIBLE);
                                                txtviewfromdate.setText(fromdate);
                                                txtviewtodate.setText(todate);

                                                if (dialstar1.size() == 0) {
                                                    txtnodata.setVisibility(View.VISIBLE);
                                                } else if (dialstar1.size() > 0) {
                                                    txtnodata.setVisibility(View.GONE);
                                                }
                                                txtecount.setText("Total Records: " + dialstar1.size());
                                                mAdapter = new cssl.dialstar.user_adapter.Grievanceadapter(dialstar1, getContext(), 3);
                                                recyclerView.setAdapter(mAdapter);
                                                dialog.dismiss();

                                            }else{
                                                String fromdate = dateFormat.format(fromdate1);
                                                String todate = dateFormat.format(todate1);
                                                relativeLayout.setVisibility(View.VISIBLE);
                                                txtviewfromdate.setText(fromdate);
                                                txtviewtodate.setText(todate);
                                                dialstar1.clear();
                                                mAdapter = new cssl.dialstar.user_adapter.Grievanceadapter(dialstar1, getContext(), 3);
                                                recyclerView.setAdapter(mAdapter);
                                                txtecount.setText("Total Records: " + dialstar1.size());
                                                dialog.dismiss();
                                            }
                                        }else {
                                            String fromdate = dateFormat.format(fromdate1);
                                            String todate = dateFormat.format(todate1);
                                            relativeLayout.setVisibility(View.VISIBLE);
                                            txtviewfromdate.setText(fromdate);
                                            txtviewtodate.setText(todate);
                                            dialstar1.clear();
                                            mAdapter = new cssl.dialstar.user_adapter.Grievanceadapter(dialstar1, getContext(), 3);
                                            recyclerView.setAdapter(mAdapter);
                                            txtecount.setText("Total Records: " + dialstar1.size());
                                            dialog.dismiss();
                                        }




                                    }else{
                                            Toast.makeText(getContext(),"Enter from date and to date correct",Toast.LENGTH_SHORT).show();

                                           }


                                }



                            }
                        }
                    }
                });

            }
        });


        new HttpRequestTask().execute();



        return rootView;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


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
            //    String url=config.getGetUserCompletedGrievance()+userid;
                String url=config.userremoteurl+"admin/app/getUserCompletedGrievance/"+userid;


                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {

            dialstar = myPojo;
            if(myPojo.size()==0){
                txtnodata.setVisibility(View.VISIBLE);
                txtecount.setText("Total Records: "+myPojo.size());
                // txtrefresh.setVisibility(View.GONE);
            }

            Log.d("myPojo", String.valueOf(myPojo));
            txtecount.setText("Total Records: "+myPojo.size());
            mAdapter = new cssl.dialstar.user_adapter.Grievanceadapter(myPojo,getContext(),3);
            recyclerView.setAdapter(mAdapter);
            progressDialog.dismiss();

        }
    }

}
