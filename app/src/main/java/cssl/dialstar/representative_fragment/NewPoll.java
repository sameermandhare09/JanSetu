package cssl.dialstar.representative_fragment;

import android.app.Activity;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.user_adapter.EditProfileAdapter;
import cssl.dialstar.user_utils.Dialstar;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NewPoll extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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




    private TextInputLayout txtsubject,txtoption1,txtoption2,txtoption3,txtoption4,txtoption5;
    private EditText edsubject,edoption1,edoption2,edoption3,edoption4,edoption5;
    private TextInputLayout txtstate, txtconstituency,txtloksabhaconstituency;
    private Button btnsubmit,btnupdate;
    private EditText edname, edaddress, edmobilenumber,
            edemail, edconstituency,edpin, edaadhaarnumber,edvoterid,edcity,
            eddistrict,edstate,edloksabhaconstituency;
    String loksabhaconstituency="";
    Spinner spstate, spconstituency;
    SharedPreferences.Editor editor ;
    int pollstateid=0;
    int pollconsid=0,pollconsid1=0,constituencyIdforpopulation=0,stateIdforpopulation=0;
    String pollloksabhaconstituency="",pollloksabhaconstituency1="",loksabhaconstituencyforpopulation="";
    int flag=1;
    String calling;
    EditProfileAdapter customAdapter;
    JSONObject jsonObject;
    ArrayList<Dialstar> dialstarPojos1;
    ArrayList<Dialstar> dialstarPojos2;
    ArrayList<Dialstar> dialstarPojos3;
    int stateId,constituencyId;
    LinearLayout lnrloksabha,lnrvidhansabha,lnrpublish;
    int userid=0,pollid=0;
    String subject,option1,option2,option3,option4,option5;
    SpinnerDialog spinnerDialogconstituency,spinnerDialogstate,spinnerDialogloksabhaconstituency;


    String usertype=null;
    Config config;
    SharedPreferences mlaPref;
    Fragment fragment;

    TextView txtback;
    RadioButton rbloksabha,rbvidhansabha;
    private TextView txtpopulation;





    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NewPoll() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static NewPoll newInstance(String param1, String param2,String param3,
                                      String param4,String param5, String param6,
                                      String param7,int param8,int param9,int param10,String param11) {
        NewPoll fragment = new NewPoll();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        args.putString(ARG_PARAM5, param5);
        args.putString(ARG_PARAM6, param6);
        args.putString(ARG_PARAM7, param7);
        args.putInt(ARG_PARAM8, param8);
        args.putInt(ARG_PARAM9, param9);
        args.putInt(ARG_PARAM10, param10);
        args.putString(ARG_PARAM11, param11);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            calling = getArguments().getString(ARG_PARAM1);
            subject = getArguments().getString(ARG_PARAM2);
            option1 = getArguments().getString(ARG_PARAM3);
            option2 = getArguments().getString(ARG_PARAM4);
            option3 = getArguments().getString(ARG_PARAM5);
            option4 = getArguments().getString(ARG_PARAM6);
            option5 = getArguments().getString(ARG_PARAM7);
            pollid = getArguments().getInt(ARG_PARAM8);
            pollstateid = getArguments().getInt(ARG_PARAM9);
            pollconsid = getArguments().getInt(ARG_PARAM10);
            pollloksabhaconstituency = getArguments().getString(ARG_PARAM11);
            stateIdforpopulation = pollstateid;
            loksabhaconstituencyforpopulation = pollloksabhaconstituency;
            constituencyIdforpopulation = pollconsid;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_new_poll, container, false);
        mlaPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor= mlaPref.edit();
        constituencyId = mlaPref.getInt("constituencyid",0);
        stateId = mlaPref.getInt("stateid",0);
        loksabhaconstituency = mlaPref.getString("loksabhaconstituency","");


        txtsubject=(TextInputLayout)rootview.findViewById(R.id.txtsubject);
        txtoption1=(TextInputLayout)rootview.findViewById(R.id.txtoption1);
        txtoption2=(TextInputLayout)rootview.findViewById(R.id.txtoption2);
        txtoption3=(TextInputLayout)rootview.findViewById(R.id.txtoption3);
        txtoption4=(TextInputLayout)rootview.findViewById(R.id.txtoption4);
        txtoption5=(TextInputLayout)rootview.findViewById(R.id.txtoption5);
        txtback = rootview.findViewById(R.id.back);

        txtloksabhaconstituency = (TextInputLayout)rootview.findViewById(R.id.txtloksabhaconstituency);
        txtconstituency = (TextInputLayout)rootview.findViewById(R.id.txtconstituency);
        txtstate = (TextInputLayout)rootview.findViewById(R.id.txtstate);
        edconstituency=(EditText)rootview.findViewById(R.id.edconstituency);
        edloksabhaconstituency=(EditText)rootview.findViewById(R.id.edloksabhaconstituency);
        edstate = (EditText)rootview.findViewById(R.id.edstate);

        spstate = rootview.findViewById(R.id.spstate);
        spconstituency = rootview.findViewById(R.id.spconstituency);

        config = new Config();
        edsubject=(EditText)rootview.findViewById(R.id.edsubject);
        edoption1=(EditText)rootview.findViewById(R.id.edoption1);
        edoption2=(EditText)rootview.findViewById(R.id.edoption2);
        edoption3=(EditText)rootview.findViewById(R.id.edoption3);
        edoption4=(EditText)rootview.findViewById(R.id.edoption4);
        edoption5=(EditText)rootview.findViewById(R.id.edoption5);

        edsubject.addTextChangedListener(new MyTextWatcher(edsubject));
        edoption1.addTextChangedListener(new MyTextWatcher(edoption1));
        edoption2.addTextChangedListener(new MyTextWatcher(edoption2));


        rbloksabha = (RadioButton)rootview.findViewById(R.id.rbloksabha);
        rbvidhansabha = (RadioButton)rootview.findViewById(R.id.rbvidhansabha);

        btnsubmit = (Button) rootview.findViewById(R.id.btn_submit);
        btnupdate = (Button) rootview.findViewById(R.id.btn_update);

        lnrloksabha = (LinearLayout)rootview.findViewById(R.id.lnrloksabha);
        lnrvidhansabha = (LinearLayout)rootview.findViewById(R.id.lnrvidhansabha);
        lnrpublish = (LinearLayout)rootview.findViewById(R.id.lnrpublish);

        txtpopulation=rootview.findViewById(R.id.txtpopulation);

        dialstarPojos1 = new ArrayList<>();
        dialstarPojos2=new ArrayList<>();
        new HttpRequestTaskstate().execute();

        if(calling.equals("edit")){

            edsubject.setText(subject);
            edoption1.setText(option1);
            edoption2.setText(option2);
            edoption3.setText(option3);
            edoption4.setText(option4);
            edoption5.setText(option5);
            //btnupdate.setVisibility(View.VISIBLE);

            getJanhitPopulation(stateIdforpopulation,constituencyIdforpopulation,loksabhaconstituencyforpopulation);
        }else{
            constituencyIdforpopulation=constituencyId;
            loksabhaconstituencyforpopulation = loksabhaconstituency;
            stateIdforpopulation = stateId;

            getJanhitPopulation(stateIdforpopulation,constituencyIdforpopulation,loksabhaconstituencyforpopulation);
        }

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();

            }
        });

     /*   btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();

            }
        });*/
        txtback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment = new Polls();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }
        });

        if(calling.equals("edit")){
            if(pollconsid>0){
                lnrvidhansabha.setVisibility(View.VISIBLE);
                rbvidhansabha.setChecked(true);

            }else{
                lnrvidhansabha.setVisibility(View.GONE);
                rbloksabha.setChecked(true);
            }

        }else{
            rbvidhansabha.setChecked(true);
        }

        rbvidhansabha.setChecked(true);
        rbloksabha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnrvidhansabha.setVisibility(View.GONE);
                getJanhitPopulation(stateIdforpopulation,0,loksabhaconstituencyforpopulation);

            }
        });
        rbvidhansabha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnrvidhansabha.setVisibility(View.VISIBLE);
                getJanhitPopulation(stateIdforpopulation,constituencyIdforpopulation,loksabhaconstituencyforpopulation);

            }
        });

        return rootview;


    }
    private void submit() {

       /* if(lnrpublish.getVisibility()==View.GONE){

            pollconsid1=0;
            pollloksabhaconstituency1="";

        }else{

            if(lnrloksabha.getVisibility()==View.GONE){
                pollloksabhaconstituency1="";


            }else {
                pollloksabhaconstituency1=pollloksabhaconstituency;
            }

            if(lnrvidhansabha.getVisibility()==View.GONE){


                pollconsid1=0;

            }else{
                pollconsid1=pollconsid;
            }

        }
*/

       pollconsid1 = 0;
        pollloksabhaconstituency1=pollloksabhaconstituency;

        if(!validatesubject()){

        }
        else if(!validateoption1()){

        }

        else if(!validateoption2()){
            }
        else if (!validateState()) {
            return;
        }
        else if (!validateLokSabhaConstituency() ) {
            return;
        }
        else if (!validateConstituency() && lnrvidhansabha.getVisibility()==View.VISIBLE) {
            return;
        }
        else{
            //call adding polls web service

            subject = edsubject.getText().toString().trim();
            option1= edoption1.getText().toString().trim();
            option2= edoption2.getText().toString().trim();
            option3= edoption3.getText().toString().trim();
            option4= edoption4.getText().toString().trim();
            option5= edoption5.getText().toString().trim();

            userid = mlaPref.getInt("representativeid",0);
            usertype = mlaPref.getString("usertype","");


            jsonObject = new JSONObject();
            try {
                jsonObject.put("pollid",pollid);
                jsonObject.put("subject",subject);
                jsonObject.put("option1",option1);
                jsonObject.put("option2",option2);
                jsonObject.put("option3",option3);
                jsonObject.put("option4",option4);
                jsonObject.put("option5",option5);
                jsonObject.put("userid",userid);
                jsonObject.put("usertype",usertype);

                if(stateId>0){
                    jsonObject.put("stateid",pollstateid);
                }
                else
                {
                    jsonObject.put("stateid",0);
                }
                jsonObject.put("constituencyid",pollconsid1);//
                jsonObject.put("name",pollloksabhaconstituency1);
                Log.e("input json add poll",String.valueOf(jsonObject));
                new HttpRequestAddPoll(jsonObject.toString()).execute();
               // Log.d("input json add poll", String.valueOf(jsonObject));


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    private boolean validateConstituency() {

        if (edconstituency.getText().toString().trim().isEmpty()) {
            txtconstituency.setError(getResources().getString(R.string.Select_Constituency));
            return false;
        } else {
            txtconstituency.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateState() {
        if (edstate.getText().toString().trim().isEmpty()) {
            txtstate.setError(getResources().getString(R.string.Select_State));
            return false;
        } else {
            txtstate.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateLokSabhaConstituency() {

        if (edloksabhaconstituency.getText().toString().trim().isEmpty() && lnrpublish.getVisibility()==View.VISIBLE) {
            txtloksabhaconstituency.setError(getResources().getString(R.string.Select_Lok_Sabha_Constituency));
            return false;
        } else {
            txtloksabhaconstituency.setErrorEnabled(false);
        }

        return true;
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
                case R.id.edsubject:
                    validatesubject();
                    break;

                case R.id.edoption1:
                    validateoption1();
                    break;

                case R.id.edoption2:
                    validateoption2();
                    break;

            }

        }
    }
    private boolean validateoption2() {
        if (edoption2.getText().toString().trim().isEmpty()) {
            txtoption2.setError(getResources().getString(R.string.Please_enter_option2));
            return false;
        } else {
            txtoption2.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateoption1() {
        if (edoption1.getText().toString().trim().isEmpty()) {
            txtoption1.setError(getResources().getString(R.string.Please_enter_option1));
            return false;
        } else {
            txtoption1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatesubject() {
        if (edsubject.getText().toString().trim().isEmpty()) {
            txtsubject.setError(getResources().getString(R.string.Please_enter_subject));
            return false;
        } else {
            txtsubject.setErrorEnabled(false);
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


    private void getJanhitPopulation(int stateIdforpopulation, int constituencyIdforpopulation, String loksabhaconstituencyforpopulation) {

        JSONObject json = new JSONObject();
        json = new JSONObject();
        try {
            json.put("stateid",stateIdforpopulation);
            json.put("constituencyid",constituencyIdforpopulation);
            json.put("subject",loksabhaconstituencyforpopulation);
            //Log.d("login Json:", String.valueOf(json));
            new HttpRequestJanhitPopulation(json.toString()).execute();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    private class HttpRequestAddPoll extends AsyncTask<Void, Void,String > {
        String json;
        int pollid=0;
        Dialog progressDialog;
        public HttpRequestAddPoll(String json)
        {
            this.json=json;

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
           // final String url = config.getInsertNewPollsDetails();
            final String url = config.representativeremoteurl+"admin/app/insertNewPollsDetails";

            Log.d("add poll url",url);
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

                // int resp = response.body().;
                return resp;


            } catch (Exception e) {
                // Toast.makeText(LogIn_Now.this,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();

                return new String("Exception: " + e.getMessage());


            }

        }

        @Override
        protected void onPostExecute(String greeting) {



            String responceName = greeting;
           // Log.d("response of add poll",responceName);
            if(responceName.equalsIgnoreCase("0")){
                Toast.makeText(getContext(),getResources().getString(R.string.Sorry_poll_Not_created),Toast.LENGTH_LONG).show();

            }else if(!responceName.contains("timestamp"))
            {
                pollid=Integer.parseInt(responceName);
                //Log.e("responceName:- ",responceName+"Poll ID="+pollid);
                Toast.makeText(getActivity(), getResources().getString(R.string.Poll_Successfully_Added), Toast.LENGTH_SHORT).show();

                fragment = new Polls();
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

            }

            progressDialog.dismiss();

        }

    }

    //All state ...


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


                Log.i("ConstutuencyNameList url", url2);
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

                Dialstar dialstar1=new Dialstar();
                dialstar1.setConstituencyname("All Constituencies");
                dialstar1.setConstituencyid(0);
                dialstarPojos2.add(0,dialstar1);



                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos2.size();i++){
                    items.add(dialstarPojos2.get(i).getConstituencyname());


                }

                if(calling.equals("edit")){
                    if(flag ==1)
                    {
                        for (int i = 0; i < dialstarPojos2.size(); i++) {
                            if (pollconsid == dialstarPojos2.get(i).getConstituencyid()) {
                                edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                            }
                        }
                    }

                }else{
                    if(flag ==1)
                    {
                        for (int i = 0; i < dialstarPojos2.size(); i++) {
                            if (constituencyId == dialstarPojos2.get(i).getConstituencyid()) {
                                pollconsid=constituencyId;
                                edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                            }
                        }
                    }
                    else {
                        for (int i = 0; i < dialstarPojos2.size(); i++) {
                            if (pollconsid == dialstarPojos2.get(i).getConstituencyid()) {
                                edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                            }
                        }
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

                                pollconsid = dialstarPojos2.get(i).getConstituencyid();
                                constituencyIdforpopulation = pollconsid;
                                getJanhitPopulation(stateIdforpopulation,constituencyIdforpopulation,loksabhaconstituencyforpopulation);


                            }
                        }

                    }
                });

            }else{
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage("Internet problem...")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }

                        }).show();
            }

            progressDialog.dismiss();

        }
    }



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
               // final String url1 = config.getGetAllState() + "99" + " ";
                final String url1 = config.representativeremoteurl+"admin/app/getAllState/"+ "99" + " ";


               // Log.i("url", url1);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());


                Dialstar[] forNow1 = restTemplate.getForObject(url1, Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow1));


                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }


        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            //Log.i("result output", myPojo.size() + "");

            if(myPojo!=null){
                dialstarPojos1 = myPojo;



                Dialstar dp1 = new Dialstar();
                dp1.setStatename(" All States ");
                dp1.setStateid(0);
                // dialstarPojos1.add(0, dp);
                dialstarPojos1.add(0, dp1);


                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < dialstarPojos1.size(); i++) {
                    items.add(dialstarPojos1.get(i).getStatename());

                }

                if(calling.equals("edit")){
                    for(int i=0;i<dialstarPojos1.size();i++){
                        if(pollstateid == dialstarPojos1.get(i).getStateid()){
                            edstate.setText(dialstarPojos1.get(i).getStatename());
                           // stateIdforpopulation = pollconsid;
                            //getJanhitPopulation(stateIdforpopulation,0,"");

                            if (pollstateid > 0) {

                                lnrpublish.setVisibility(View.VISIBLE);
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("stateid", pollstateid);
                                    Log.d("sending Json :- ", String.valueOf(json));
                                   // new HttpRequestTaskReq(json.toString()).execute();
                                    new HttpRequestLokSabhaConstituencyByStateId(pollstateid).execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                lnrpublish.setVisibility(View.GONE);
                            }

                        }

                    }

                }else {
                    if (flag == 1) {
                        for (int i = 0; i < dialstarPojos1.size(); i++) {
                            if (stateId == dialstarPojos1.get(i).getStateid()) {
                                pollstateid = stateId;
                                edstate.setText(dialstarPojos1.get(i).getStatename());
                                if (stateId > 0) {

                                    lnrpublish.setVisibility(View.VISIBLE);
                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("stateid", stateId);
                                        Log.d("sending Json :- ", String.valueOf(json));
                                        //new HttpRequestTaskReq(json.toString()).execute();
                                        new HttpRequestLokSabhaConstituencyByStateId(stateId).execute();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{

                                    lnrpublish.setVisibility(View.GONE);
                                }

                            }

                        }
                    } else {
                        for (int i = 0; i < dialstarPojos1.size(); i++) {
                            if (pollstateid == dialstarPojos1.get(i).getStateid()) {
                                edstate.setText(dialstarPojos1.get(i).getStatename());
                                if (pollstateid > 0) {

                                    lnrpublish.setVisibility(View.VISIBLE);

                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("stateid", pollstateid);
                                        Log.d("sending Json :- ", String.valueOf(json));
                                       // new HttpRequestTaskReq(json.toString()).execute();
                                        new HttpRequestLokSabhaConstituencyByStateId(pollstateid).execute();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else{


                                    lnrpublish.setVisibility(View.GONE);
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

                                lnrpublish.setVisibility(View.VISIBLE);
                                pollstateid = dialstarPojos1.get(i).getStateid();
                                stateIdforpopulation = pollstateid;


                                getJanhitPopulation(stateIdforpopulation,0,"");
                                edloksabhaconstituency.setText("");
                                edconstituency.setText("");
                                if (pollstateid > 1) {




                                    JSONObject json = new JSONObject();
                                    try {
                                        json.put("stateid", pollstateid);
                                        Log.d("sending Json :- ", String.valueOf(json));
                                       // new HttpRequestTaskReq(json.toString()).execute();
                                        new HttpRequestLokSabhaConstituencyByStateId(pollstateid).execute();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    lnrpublish.setVisibility(View.GONE);
                                    //getJanhitPopulation(stateIdforpopulation,0,"");


                                    // spconstituency.setVisibility(View.GONE);
                                }


                            }
                        }

                    }
                });


            }else{
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage("Internet problem...")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();
            }

            progressDialog.dismiss();



        }

    }



    private class HttpRequestLokSabhaConstituencyByStateId extends AsyncTask<Void, Void, ArrayList<Dialstar>> {


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
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {

                final String url2 = config.representativeremoteurl+"admin/app/getLokSabhaConstituencyByStateId/"+stateid;


                // Log.i("LokSabhaConstituencyName url", url2);
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

            progressDialog.dismiss();
            if(myPojo!=null){

                dialstarPojos3 = new ArrayList<Dialstar>();

                dialstarPojos3 = myPojo;


                Dialstar dp1 = new Dialstar();
                dp1.setSubject("All Constituency");
                dialstarPojos3.add(0, dp1);

                ArrayList<String> items=new ArrayList<>();
                for(int i=0;i<dialstarPojos3.size();i++){
                    items.add(dialstarPojos3.get(i).getSubject());

                }


                if(calling.equals("edit")){
                    if(pollloksabhaconstituency.equalsIgnoreCase("")){
                        edloksabhaconstituency.setText(dialstarPojos3.get(0).getSubject());
                        pollloksabhaconstituency="";
                        lnrvidhansabha.setVisibility(View.GONE);
                    }else{
                        for (int i = 0; i < dialstarPojos3.size(); i++) {
                            if (pollloksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject())) {
                                edloksabhaconstituency.setText(dialstarPojos3.get(i).getSubject());
                                if(edloksabhaconstituency.getText().toString().trim().equalsIgnoreCase("")){
                                    pollloksabhaconstituency="";
                                    lnrvidhansabha.setVisibility(View.GONE);

                                }else{
                                    lnrvidhansabha.setVisibility(View.VISIBLE);
                                    new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(pollloksabhaconstituency).execute();
                                }

                            }else{

                            }
                        }

                    }


                }else {
                    if(flag==1){
                        for(int i=0;i<dialstarPojos3.size();i++){
                            if(loksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject()) ){
                                pollloksabhaconstituency = loksabhaconstituency;
                                edloksabhaconstituency.setText(dialstarPojos3.get(i).getSubject());
                                if(edloksabhaconstituency.getText().toString().trim().equalsIgnoreCase("All Constituency")){
                                    pollloksabhaconstituency="";
                                    lnrvidhansabha.setVisibility(View.GONE);

                                }else{
                                    lnrvidhansabha.setVisibility(View.VISIBLE);
                                    new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(loksabhaconstituency).execute();
                                }

                            }

                        }
                    }
                    else {
                        for (int i = 0; i < dialstarPojos3.size(); i++) {
                            if (pollloksabhaconstituency.equalsIgnoreCase(dialstarPojos3.get(i).getSubject())) {
                                edloksabhaconstituency.setText(dialstarPojos3.get(i).getSubject());
                                if(edloksabhaconstituency.getText().toString().trim().equalsIgnoreCase("All Constituency")){
                                    pollloksabhaconstituency="";
                                    lnrvidhansabha.setVisibility(View.GONE);

                                }else{
                                    lnrvidhansabha.setVisibility(View.VISIBLE);
                                    new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(pollloksabhaconstituency).execute();
                                }

                            }
                        }

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
                        pollloksabhaconstituency = edloksabhaconstituency.getText().toString().trim();

                        loksabhaconstituencyforpopulation = pollloksabhaconstituency;

                        if(pollloksabhaconstituency.equalsIgnoreCase("All Constituency")){
                            getJanhitPopulation(stateIdforpopulation,0,"");
                        }else{
                            getJanhitPopulation(stateIdforpopulation,0,loksabhaconstituencyforpopulation);
                        }


                        edconstituency.setText("");
                        if(edloksabhaconstituency.getText().toString().trim().equalsIgnoreCase("All Constituency")){
                            pollloksabhaconstituency="";
                            lnrvidhansabha.setVisibility(View.GONE);

                        }else{


                                lnrvidhansabha.setVisibility(View.VISIBLE);
                                new HttpRequestConstutuencyNameListByLoksabhaConstituencyName(pollloksabhaconstituency).execute();
                                rbvidhansabha.setChecked(true);


                        }


                    }
                });


            }else{
                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage("Internet problem...")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }

                        }).show();
            }




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

               // return new String("Exception: " + e.getMessage());
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
                        .setNegativeButton(getResources().getString(R.string.ok),null)
                        .show();
            }

            progressDialog.dismiss();
        }

    }


    // Constituency..

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
            //final String url = config.getGetAllConstituencyByStateId();
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
                        Dialstar dialstar=new Dialstar();
                        dialstar.setConstituencyname(constituencyname);
                        dialstar.setConstituencyid(constituencyid);
                        dialstarPojos2.add(dialstar);


                        //Log.i("constituency details",constituencyid+" "+constituencyname);
                    }
                    Dialstar dialstar1=new Dialstar();
                    dialstar1.setConstituencyname("All Constituencies");
                    dialstar1.setConstituencyid(0);
                    dialstarPojos2.add(0,dialstar1);



                    ArrayList<String> items=new ArrayList<>();
                    for(int i=0;i<dialstarPojos2.size();i++){
                        items.add(dialstarPojos2.get(i).getConstituencyname());


                    }

                    if(calling.equals("edit")){
                        if(flag ==1)
                        {
                            for (int i = 0; i < dialstarPojos2.size(); i++) {
                                if (pollconsid == dialstarPojos2.get(i).getConstituencyid()) {
                                    edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                                }
                            }
                        }

                    }else{
                        if(flag ==1)
                        {
                            for (int i = 0; i < dialstarPojos2.size(); i++) {
                                if (constituencyId == dialstarPojos2.get(i).getConstituencyid()) {
                                    edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                                }
                            }
                        }
                        else {
                            for (int i = 0; i < dialstarPojos2.size(); i++) {
                                if (pollconsid == dialstarPojos2.get(i).getConstituencyid()) {
                                    edconstituency.setText(dialstarPojos2.get(i).getConstituencyname());
                                }
                            }
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

                                    pollconsid = dialstarPojos2.get(i).getConstituencyid();


                                }
                            }

                        }
                    });


                    // Log.i("dialstarPojos2",dialstarPojos2+".. ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }else{

                new android.support.v7.app.AlertDialog.Builder(getContext())
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {


                                dialog.cancel();

                            }

                        }).show();

            }



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
