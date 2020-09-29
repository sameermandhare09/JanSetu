package cssl.dialstar.representative_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.user_adapter.EditProfileAdapter;
import cssl.dialstar.user_utils.Dialstar;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.DialogInterface.BUTTON_NEGATIVE;

public class NewDiscussion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Config config = new Config();
    TextView textGoBack;
    Fragment fragment;
    JSONObject jsonObject;
    public Spinner spstate;
    public Spinner spconstituency;
    public Button btn_save;
    TextInputLayout txttopicname;
    EditText edttopicname;
    int representativeid=0,mlaid=0;
    int stateId,constituencyId,constituencyIddisc,stateIddisc,constituencyIddisc1,constituencyIdforpopulation;
    private TextView txtpopulation;

    String loksabhaconstituencydisc="",loksabhaconstituency="",loksabhaconstituencyforpopulation="";


    ArrayList<Dialstar> dialstarPojos1;
    ArrayList<Dialstar> dialstarPojos2;



    ArrayList<DialstarPojo> dialstarPojos3;
    EditProfileAdapter customAdapter;
    private OnFragmentInteractionListener mListener;
    private TextInputLayout txtname, txtaddress, txtmobilenumber,txtdistrict,
            txtemail,txtarea, txtward, txtpin,txtstate,txtloksabhaconstituency,txtcity,
            txtconstituency;
    private EditText edname, edaddress, edmobilenumber,
            edemail, edconstituency,edpin, edaadhaarnumber,edvoterid,edcity,
            eddistrict,edstate,edloksabhaconstituency;
    LinearLayout lnrloksabha,lnrvidhansabha;

    RadioButton rbloksabha,rbvidhansabha;
    SharedPreferences mlaPref;
    SharedPreferences.Editor editor ;
    String usertype;
    SpinnerDialog spinnerDialogconstituency,spinnerDialogstate,spinnerDialogloksabhaconstituency;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public NewDiscussion() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static NewDiscussion newInstance(String param1, String param2) {
        NewDiscussion fragment = new NewDiscussion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_new_discussion, container, false);
        textGoBack = (TextView)rootview.findViewById(R.id.txtback);
        txttopicname =(TextInputLayout)rootview.findViewById(R.id.txt_topic_name);
        edttopicname = (EditText)rootview.findViewById(R.id.topicname);
        spstate = (Spinner)rootview.findViewById(R.id.spstate);
        txtpopulation=rootview.findViewById(R.id.txtpopulation);
        spconstituency = (Spinner)rootview.findViewById(R.id.spconstituency);
        btn_save = (Button)rootview.findViewById(R.id.btn_save);





        rbloksabha = (RadioButton)rootview.findViewById(R.id.rbloksabha);
        rbvidhansabha = (RadioButton)rootview.findViewById(R.id.rbvidhansabha);

        txtstate=(TextInputLayout)rootview.findViewById(R.id.txtstate);
        txtloksabhaconstituency=(TextInputLayout)rootview.findViewById(R.id.txtloksabhaconstituency);
        txtconstituency=(TextInputLayout)rootview.findViewById(R.id.txtconstituency);
        edconstituency=(EditText)rootview.findViewById(R.id.edconstituency);
        edloksabhaconstituency=(EditText)rootview.findViewById(R.id.edloksabhaconstituency);
        edstate = (EditText)rootview.findViewById(R.id.edstate);
        lnrloksabha = (LinearLayout)rootview.findViewById(R.id.lnrloksabha);
        lnrvidhansabha = (LinearLayout)rootview.findViewById(R.id.lnrvidhansabha);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor= mlaPref.edit();
        representativeid = mlaPref.getInt("representativeid",0);
        constituencyId = mlaPref.getInt("constituencyid",0);
        stateId = mlaPref.getInt("stateid",0);
        loksabhaconstituency = mlaPref.getString("loksabhaconstituency","");
        loksabhaconstituencyforpopulation = loksabhaconstituency;
        constituencyIdforpopulation = constituencyId;
        //constituencyId = 4;
        usertype = mlaPref.getString("usertype","");
        mlaid = mlaPref.getInt("mlaid",0);


        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2=new ArrayList<>();

        new HttpRequestTaskstate().execute();

        getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);


        rbvidhansabha.setChecked(true);
        rbloksabha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lnrvidhansabha.setVisibility(View.GONE);
                getJanhitPopulation(0,loksabhaconstituencyforpopulation);
                //edloksabhaconstituency.setVisibility(View.VISIBLE);

            }
        });
        rbvidhansabha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnrvidhansabha.setVisibility(View.VISIBLE);
                getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);
                //edloksabhaconstituency.setVisibility(View.GONE);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(lnrvidhansabha.getVisibility()==View.GONE){
                    constituencyIddisc1=0;

                }else {

                    constituencyIddisc1 = constituencyIddisc;

                }*/
                constituencyIddisc1=0;
                String topic = edttopicname.getText().toString().trim();
                if(!validattopic())
                {
                    return;
                }
                else if (!validateConstituency()) {
                    return;
                }
                else if (!validateCountry()) {
                    return;
                }
                else {
                    jsonObject = new JSONObject();
                    try {

                        jsonObject.put("topic", topic);
                        jsonObject.put("representativeid", representativeid);
                        jsonObject.put("stateid", stateIddisc);
                        jsonObject.put("constituencyid", constituencyIddisc1);
                        jsonObject.put("name", loksabhaconstituencydisc);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("add discussion json",jsonObject.toString());
                    new AddNewDiscussion(jsonObject.toString()).execute();
                }

                }
        });
        textGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new Discussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }


           /*     FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    //super.onBackPressed();
                }
*/
            }
        });

        return rootview ;
    }


    public class MyTextWatcher implements TextWatcher {

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

            }

        }
    }
    private boolean validattopic() {
        if (edttopicname.getText().toString().trim().isEmpty()) {
            txttopicname.setError(getResources().getString(R.string.Please_enter_a_valid_topic));
            return false;
        }

        return true;
    }


    private boolean validateCountry() {
        if (spstate.getSelectedItemId() == 0) {
            new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.Error))
                    .setMessage(" please select state ")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //Toast.makeText(getContext(), "Please select Country", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validateConstituency() {
        if (spconstituency.getSelectedItemId() == 0) {
            new android.support.v7.app.AlertDialog.Builder(getContext())
                    .setTitle(getString(R.string.Error))
                    .setMessage(" please select Constituency ")
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            //Toast.makeText(getContext(), "Please select Country", Toast.LENGTH_SHORT).show();
            return false;
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

    // All state..

    private class HttpRequestTaskstate extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

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

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url1 = config.getGetAllState() + "99" + " ";
                final String url1 = config.representativeremoteurl+"admin/app/getAllState/"+ "99" + " ";


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


        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            //Log.i("result output", myPojo.size() + "");

            if(myPojo!=null){

                dialstarPojos1 = myPojo;


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


                for (int i=0;i<dialstarPojos1.size();i++){
                    if(stateId == dialstarPojos1.get(i).getStateid()){
                        edstate.setText(dialstarPojos1.get(i).getStatename());
                        stateIddisc = stateId;

                        new HttpRequestLokSabhaConstituencyByStateId(stateIddisc).execute();


                    }
                }
                spinnerDialogstate.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                        edstate.setText(item);
                        for (int i = 0; i < dialstarPojos1.size(); i++) {
                            if (item.equalsIgnoreCase(dialstarPojos1.get(i).getStatename())) {

                                stateId = dialstarPojos1.get(i).getStateid();
                                if (stateId > 0) {

                                    JSONObject json = new JSONObject();
                                    try {
                                        stateIddisc=stateId;
                                        json.put("stateid", stateIddisc);
                                        Log.d("sending Json :- ", String.valueOf(json));
                                        //new HttpRequestTaskReq(json.toString()).execute();
                                        edloksabhaconstituency.setText("");
                                        edconstituency.setText("");
                                        new HttpRequestLokSabhaConstituencyByStateId(stateIddisc).execute();
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




                Dialstar dp = new Dialstar();
                Dialstar dp1 = new Dialstar();

                dp.setCityname("--Select State--");
                dp.setCityid(0);
                dp1.setCityname("--ALL--");
                dp1.setCityid(1);
                dialstarPojos1.add(0, dp);
                dialstarPojos1.add(1, dp1);
                customAdapter = new EditProfileAdapter(getActivity(), R.layout.customspinner, R.id.title, myPojo);
                spstate.setAdapter(customAdapter);
                int pos = 0;
                for (int i=0;i<dialstarPojos1.size();i++){
                    if(stateId == dialstarPojos1.get(i).getStateid()){
                        pos = i;

                    }
                }
                spstate.setSelection(pos);

                AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        //  Toast.makeText(adapterView.getContext(), "Position:- " + adapterView.getItemIdAtPosition(i), Toast.LENGTH_SHORT).show();
                        //Log.d("select item position:-", String.valueOf(myPojo.get(pos)));


                        stateId = dialstarPojos1.get(i).getStateid();
                        //Toast.makeText(adapterView.getContext(), "stateIdv:- " + stateId, Toast.LENGTH_SHORT).show();
                        JSONObject json = new JSONObject();
                        try {
                            json.put("stateid", stateId);
                            Log.d("sending Json :- ", String.valueOf(json));
                            // new HttpRequestTaskConstituency(json.toString()).execute();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                };

                spstate.setOnItemSelectedListener(onItemSelectedListener);


            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage("No Internet Connection...")
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
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

                for(int i=0;i<dialstarPojos3.size();i++){
                    if(loksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject())){

                        loksabhaconstituencydisc = loksabhaconstituency;

                        edloksabhaconstituency.setText(loksabhaconstituencydisc);
                        new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituencydisc).execute();


                    }

                }

                edloksabhaconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogloksabhaconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogloksabhaconstituency=new SpinnerDialog(getActivity(), items,"Select Lok Sabha Constituency","Close");// With No Animation


                spinnerDialogloksabhaconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {


                        edloksabhaconstituency.setText(item);
                        loksabhaconstituencydisc = edloksabhaconstituency.getText().toString().trim();
                        loksabhaconstituencyforpopulation = loksabhaconstituencydisc;

                        getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);
                        edconstituency.setText("");
                        new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituencydisc).execute();


                    }
                });


            }else{
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage("Internet problem...")
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



    private class HttpRequestConstutuencyNameListByLoksabhaConstituencyName extends AsyncTask<Void, Void, ArrayList<Dialstar>> {




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
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {

                // final String url2 = config.representativeremoteurl+"admin/app/getConstutuencyNameListByDistrictName/"+loksabhaconstituency+" ";

                final String url2 = Config.representativeremoteurl+"admin/app/getVidhanSabhaConstituencyNameByLokSabhaConstituencyNAme/"+loksabhaconstituency+" ";


                Log.i("ConstutuencyNameListurl", url2);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow2 = restTemplate.getForObject(url2,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                return greeting1;
            } catch (Exception e) {
                // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            if(myPojo!=null){
                Log.i("result output", myPojo.size() + "");


                dialstarPojos2 = myPojo;

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos2.size();i++){
                    items.add(dialstarPojos2.get(i).getConstituencyname());


                }

                for(int i=0;i<dialstarPojos2.size();i++){
                    if(constituencyId==dialstarPojos2.get(i).getConstituencyid()){
                        edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());

                        constituencyIddisc=constituencyId;
                    }
                }



                edconstituency.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        spinnerDialogconstituency.showSpinerDialog();
                    }
                });
                spinnerDialogconstituency=new SpinnerDialog(getActivity(), items,"Select  Constituency","Close");// With No Animation

                spinnerDialogconstituency.bindOnSpinerListener(new OnSpinerItemClick() {
                    @Override
                    public void onClick(String item, int position) {
                    /*    Toast.makeText(Personal_Details.this, item + "  " + position+"", Toast.LENGTH_SHORT).show();
                        selectedItems.setText(item + " Position: " + position);*/

                        edconstituency.setText(item);
                        for(int i=0;i<dialstarPojos2.size();i++){
                            if(item.equalsIgnoreCase(dialstarPojos2.get(i).getConstituencyname())){

                                constituencyIddisc = dialstarPojos2.get(i).getConstituencyid();
                                constituencyIdforpopulation = constituencyIddisc;
                                getJanhitPopulation(constituencyIdforpopulation,loksabhaconstituencyforpopulation);

                            }
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




    public class AddNewDiscussion extends AsyncTask<String, Void, String> //Using POST Method, send newly entered comment
    {
        ProgressDialog progressDialog;

        String jsonstr;

        public AddNewDiscussion(String jsonstr) {
            this.jsonstr = jsonstr;

        }


        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait");  // Setting Message
            //progressDialog.setTitle("Please wait"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show(); // Display Progress Dialog
            progressDialog.setCancelable(false);
            progressDialog.setButton(BUTTON_NEGATIVE, getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        protected String doInBackground(String... arg0) {

            try {
                //For POST
             //   String url = config.getCreateNewRepresentativeDiscussion();
                String url = config.representativeremoteurl+"admin/app/createNewRepresentativeDiscussion/";

                Log.e("add discussion url",url);
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
                String resp = response.message().toString();

                String respbody = response.body().string().toString();


                return respbody;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());

            }

        }

        @Override
        protected void onPostExecute(String result) {

            //Log.i("Discussion Result", result.trim());
            // mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

            progressDialog.dismiss();
            if (result.equalsIgnoreCase("Success")) {


                Toast.makeText(getContext(), getResources().getString(R.string.Discussion_successfully_added), Toast.LENGTH_SHORT).show();
                fragment = new Discussion();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            }
            else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(getResources().getString(R.string.no_internet));
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
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

//                return new String("Exception: " + e.getMessage());

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




}
