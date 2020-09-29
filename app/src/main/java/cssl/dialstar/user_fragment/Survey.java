package cssl.dialstar.user_fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_adapter.SurveyAdapter;
import cssl.dialstar.user_adapter.otherpolladapter;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class Survey extends Fragment {
    ViewPager viewPager;
    SurveyAdapter adapter;
    String [] otionname;
    int userid = 0,constituencyid=0;
    EditText edmssg;
    String UserId = "";
    TextView txtno,txtcounter,txtothercount;
    ConfigUser config;
    int counter=0;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    String name="";
    ArrayList<Dialstar> dialstar=new ArrayList<>();
    ArrayList<Dialstar> dialstar1=new ArrayList<>();
    public RecyclerView recyclerView;
    public otherpolladapter other;
    private ImageView submit;

    String reason="";
    LinearLayout lnr1,linear3;
    RelativeLayout relativeLayout;
    TabLayout tabLayout;
    public Survey() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        rootView= inflater.inflate(R.layout.fragment_survey, container, false);
       // LinearLayout mLinearLayout = (LinearLayout) rootView.findViewById(R.id.linear1);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        recyclerView=(RecyclerView)rootView. findViewById(R.id.recycler_view);
        relativeLayout=(RelativeLayout) rootView.findViewById(R.id.relative1) ;
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabDots);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        other=new otherpolladapter(getContext(),dialstar);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(other);
        new HttpRequestTask1().execute();
        // Pass results to SurveyAdapter Class
        share = PreferenceManager.getDefaultSharedPreferences(getActivity());
        edit = share.edit();
        config=new ConfigUser();
        userid = share.getInt("Userid", 0);
        UserId = String.valueOf(userid);
        constituencyid = share.getInt("constituencyid",0);
        //constituencyid = 9;
        txtno=(TextView) rootView.findViewById(R.id.txtno);
        txtno.setVisibility(View.GONE);
        txtcounter=(TextView) rootView.findViewById(R.id.txtcounter);
        txtothercount=(TextView) rootView.findViewById(R.id.txtothercount);
        lnr1=(LinearLayout) rootView.findViewById(R.id.lnr1);
        linear3=(LinearLayout) rootView.findViewById(R.id.linear3);
        tabLayout.setupWithViewPager(viewPager, true);
        new HttpRequestTask(userid,constituencyid).execute();
            submit = (ImageView) rootView.findViewById(R.id.submit);
            edmssg = (EditText) rootView.findViewById(R.id.edmssg);
            name=share.getString("name","");
      //  edmssg.setId(dialstar.get(position).getPollid());
       // submit.setTag(dialstar.get(position).getPollid());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

        return  rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        new HttpRequestTask(userid,constituencyid).execute();
    }

    private int getItemofviewpager(int i) {
        return viewPager.getCurrentItem() + i;
    }
    public void check()
    {
        if(name.equalsIgnoreCase("")){
            new android.support.v7.app.AlertDialog.Builder(getActivity())
                    .setMessage("Please Update Your Profile")
                    .setNegativeButton(getString(R.string.ok),null)
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
            submitform();
        }
    }
    public void submitform()
    {
        int selectedposition=viewPager.getCurrentItem();
        int pollid=dialstar1.get(selectedposition).getPollid();
        String answer=adapter.returnanswer();
       // Log.i("PolllId",String.valueOf(pollid));
        reason=edmssg.getText().toString();
        //Log.i("answer",reason);
        //Log.i("edmsg",reason);
        userid=share.getInt("Userid",0);
        if(!answer.equalsIgnoreCase(""))
        {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pollid", pollid);
                jsonObject.put("answer", answer );
                jsonObject.put("reason", reason);
                jsonObject.put("usertype","user");
                jsonObject.put("userid",userid);
                Log.e("poll ans json",jsonObject.toString());
                new HttpSubmitTask(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
            new AlertDialog.Builder(getActivity()).setTitle(getResources().getString(R.string.Error))
                    .setMessage(getResources().getString(R.string.Please_Select_your_answer))
                    .setCancelable(true)
                    .setPositiveButton(getString(R.string.ok),null).show();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(Survey.this).attach(Survey.this).commit();
        edmssg.setText("");
    }
    public void onDestroyView() {
        super.onDestroyView();
        dialstar.clear();
    }
    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Dialstar>> {
        Dialog progressDialog;
        int userid=0,  constituencyid=0;

        public HttpRequestTask(int userid, int constituencyid) {
            this.userid = userid;
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
             //   String url=config.getGetPollsDetailsList()+UserId+"/"+constituencyid;
                String url=config.userremoteurl+"admin/app/getPollsDetailsList/"+UserId+"/"+constituencyid;

                Log.e("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
                ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));
                return greeting1;
            } catch (Exception e) {
               // Log.e("MainActivity", e.getMessage(), e);
                return null;
            }

        }
        protected void onPostExecute(ArrayList<Dialstar> myPojo) {
            progressDialog.dismiss();
            if(myPojo!=null){

                dialstar = myPojo;
                // Log.d("myPojo", String.valueOf(myPojo));

                counter=myPojo.size();
                dialstar1.clear();
                //txtcounter.setText(String.valueOf("Poll Counter:-"+counter));
                if(myPojo.size()==0)
                {
                    lnr1.setVisibility(View.INVISIBLE);
                    txtno.setVisibility(View.VISIBLE);
                    linear3.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.GONE);
                    txtcounter.setVisibility(View.INVISIBLE);
                    viewPager.setVisibility(View.GONE);
                }
                else
                {
                    Dialstar dialstar = new Dialstar();


                    for (int i=0;i<myPojo.size();i++){
                        if(constituencyid==myPojo.get(i).getConstituencyid()||myPojo.get(i).getConstituencyid()==0){
                            dialstar1.add(myPojo.get(i));

                        }
                    }
                    adapter = new SurveyAdapter(dialstar1,getActivity());
                    viewPager.setAdapter(adapter);
                    viewPager.setVisibility(View.VISIBLE);
                }

            }else{
                new android.support.v7.app.AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.Error))
                        .setMessage(getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.ok),null)
                        .show();
            }

        }
    }
    public class HttpSubmitTask extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;
        public HttpSubmitTask(String jsonstr) {
            this.jsonstr = jsonstr;
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
              //  String url =config.getUserAnswers();
                String url =config.userremoteurl+"admin/app/userAnswers";

                Log.e("url",url);
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
            progressDialog.dismiss();
           // Log.i("User_Answer:", result);
            if(result.equalsIgnoreCase("Success")) {
                Toast.makeText(getContext(), getResources().getString(R.string.answer_successfully_saved), Toast.LENGTH_SHORT).show();
                refresh();
            }
        }
    }
    public void refresh(){
        new HttpRequestTask(userid,constituencyid).execute();

    }
    public class HttpRequestTask1 extends AsyncTask<String, Void, String> {
        Dialog progressDialog;
        String jsonstr;
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(getContext());
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

        }
        protected String doInBackground(String... arg0) {
            try {
                String url ="http://205.147.109.242:9980/admin/app/getOtherPolls";

                MediaType JSON = MediaType.parse("application/json");
                OkHttpClient client = new OkHttpClient();
                //  RequestBody body = RequestBody.create(JSON,jsonstr);
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                String resp = response.body().string().toString();
                return resp;

            } catch (Exception e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String result) {

            if(result!=null){
                String option1 = "",option2="",option3="",option4="",option5="";
                int option1Count = 0,option2Count=0,option3Count=0,option4Count=0,option5Count=0;
                int totalcount=0;
                try {
                    JSONArray jsonArray=new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        if(jsonObject.has("option1"))
                        {
                            option1=jsonObject.getString("option1");
                            //Log.i("Option1",option1.toString());
                        }
                        if(jsonObject.has("option2"))
                        {
                            option2=jsonObject.getString("option2");
                            //Log.i("Option2",option2.toString());
                        }
                        if(jsonObject.has("option3"))
                        {
                            option3=jsonObject.getString("option3");
                            //Log.i("Option3",option3.toString());
                        }
                        if(jsonObject.has("option4"))
                        {
                            option4=jsonObject.getString("option4");
                            //Log.i("Option4",option4.toString());
                        }
                        if(jsonObject.has("option5"))
                        {
                            option5=jsonObject.getString("option5");
                            //Log.i("Option5",option5.toString());
                        }
                        option1Count=jsonObject.getInt("option1Count");
                        option2Count=jsonObject.getInt("option2Count");
                        option3Count=jsonObject.getInt("option3Count");
                        option4Count=jsonObject.getInt("option4Count");
                        option5Count=jsonObject.getInt("option5Count");
                        totalcount=jsonObject.getInt("totalcount");
                        String sub=jsonObject.getString("subject");

                        Dialstar data=new Dialstar();
                        data.setSubject(sub);
                        data.setOption1(option1);
                        data.setOption2(option2);
                        data.setOption3(option3);
                        data.setOption4(option4);
                        data.setOption5(option5);
                        data.setAckgrievancecount(option1Count);
                        data.setAcknowledgeby(option2Count);
                        data.setAdminid(option3Count);
                        data.setBoothid(option4Count);
                        data.setCategoryid(option5Count);
                        data.setCount(totalcount);

                        dialstar.add(data);
                        String Result= String.valueOf(dialstar.size());
                        //Log.i("Rsultlength",Result);
                        Activity activity = getActivity();
                        if(!Result.equalsIgnoreCase(" ") && activity != null)
                        {
                            txtothercount.setText(Html.fromHtml(getString(R.string.OTHER_POLLS)+" ("+Result+")"));
                        }else{

                        }
                    }
                    other.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{

            }
           // Log.i("New Request", result);
                        progressDialog.dismiss();
        }
    }
}
