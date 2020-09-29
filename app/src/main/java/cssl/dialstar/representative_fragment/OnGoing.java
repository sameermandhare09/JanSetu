package cssl.dialstar.representative_fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_adapter.ComplaintAdapter;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import cssl.dialstar.representative_util.HeadingData;


@SuppressLint("ValidFragment")
public class OnGoing extends Fragment {
    /*ExpandableListView expandableListView;
    MyExpandableListAdapter myExpandableListAdapter ;*/

    TextView txtnodata;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager  layoutManager;
    RecyclerView recyclerView;
    ArrayList<HeadingData> headingData = new ArrayList<>();
   // HashMap<HeadingData,List<InfoData>> infoData;
   // Spinner spinner_category,spinner_representative;
    int grievancecategoryid=0;
    int representativeid=0;
    int mlaid,userid;
    int fragId = 2;
    SharedPreferences mlaPref;
    String usertype="",usertype1="";
    ArrayList<DialstarPojo> dialstarPojos1,dialstarPojos,dialstarPojos2;
   // TextView textView;
    Config config = new Config();
    TextView txtback,txtcount,txtcategory,txtrepresentative;
    ImageView imagecategoryicon;
    Fragment fragment = new Fragment();
    String grievancename;
    int flag=0;
    String representative="";
    @SuppressLint("ValidFragment")
    public  OnGoing(int x,int y,String representative,int flag){

        grievancecategoryid = x;
        representativeid = y;
        this.flag = flag;
        this.representative = representative;
        //grievancename=a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_on_going, container, false);

        recyclerView = layout.findViewById(R.id.new_complaint_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        mlaPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mlaid = mlaPref.getInt("mlaid",mlaid);
        usertype=mlaPref.getString("usertype","");
        txtnodata = layout.findViewById(R.id.txtview);
        txtnodata = layout.findViewById(R.id.txtview);
        txtback = layout.findViewById(R.id.txtback);
        txtcategory = layout.findViewById(R.id.txtcategory);
        txtrepresentative = layout.findViewById(R.id.txtrepresentative);
        txtcount = layout.findViewById(R.id.txtcount);
        imagecategoryicon = layout.findViewById(R.id.imagecategoryicon);

        if(flag==1){
            userid = representativeid;
            usertype1 = "representative";
            txtcategory.setVisibility(View.GONE);
            imagecategoryicon.setVisibility(View.GONE);
            txtrepresentative.setVisibility(View.VISIBLE);
            txtrepresentative.setText(representative);
            new HttpRequestTaskreq().execute();
        }else {
            if (usertype.equalsIgnoreCase("representative")) {
                userid = mlaPref.getInt("representativeid", 0);
                usertype1 = usertype;
                new HttpRequestTaskreq().execute();

            } else if (usertype.equalsIgnoreCase("mla")) {
                userid = mlaid;
                usertype1 = usertype;
                new HttpRequestTaskreq().execute();

            }

        }
        if(grievancecategoryid==1){
            txtcategory.setText("Water Supply");
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.water_supply_icon));
        }else  if(grievancecategoryid==2){
            txtcategory.setText("Electricity");
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.electricity_icon));
        }else  if(grievancecategoryid==3){
            txtcategory.setText("Roads");
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.roads_icon));
        }else  if(grievancecategoryid==4){
            txtcategory.setText(getResources().getString(R.string.Health));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.helth_icon));
        }else  if(grievancecategoryid==5){
            txtcategory.setText("Traffic");
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.traffic_icon));
        }else if(grievancecategoryid==6){
            txtcategory.setText("Other");
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
        }else  if(grievancecategoryid==7){
            txtcategory.setText(getResources().getString(R.string.Education));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.education_icon));
        }else  if(grievancecategoryid==8){
            txtcategory.setText(getResources().getString(R.string.Unemployment));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.unemployment_icon));
        }else  if(grievancecategoryid==9){
            txtcategory.setText(getResources().getString(R.string.Infrastructure));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.infrastruture_icon));
        }else  if(grievancecategoryid==10){
            txtcategory.setText(getResources().getString(R.string.Women_Empowerment));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.women_empowerment_icon));
        }else if(grievancecategoryid==11){
            txtcategory.setText(getResources().getString(R.string.Central_Government_Schemes));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.central_govement_icon));
        }else  if(grievancecategoryid==12){
            txtcategory.setText(getResources().getString(R.string.State_Government_Schemes));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.state_goverment_icon));
        }else  if(grievancecategoryid==13){
            txtcategory.setText(getResources().getString(R.string.Law_And_Order));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.lawandorder_icon));
        }else if(grievancecategoryid==14){
            txtcategory.setText(getResources().getString(R.string.Others));
            imagecategoryicon.setImageDrawable(getResources().getDrawable(R.drawable.other_icon));
        }

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

            }
        });

        return  layout;
    }


    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){

            String responceName = data.getStringExtra("responceName");
            int pos1= data.getIntExtra("position",0);
            if(responceName.equalsIgnoreCase("Success")){

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();


            }
        }
        Log.d("MyAdapter", "onActivityResult");
    }
    @Override
    public void onResume() {
        super.onResume();
        new HttpRequestTaskreq().execute();

    }

    private class HttpRequestTaskreq extends AsyncTask<Void, Void, ArrayList<DialstarPojo>> {


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
                //final String url2 = config.getGetOnGoingGrievanceDetails()+userid+"/"+usertype1+"/"+grievancecategoryid;
                final String url2 = config.representativeremoteurl+"admin/app/getOnGoingGrievanceDetails/"+userid+"/"+usertype1+"/"+grievancecategoryid;


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
                Log.i("output in ongoing", myPojo.size() + "");
                //Log.d("getNewGrievanceDetails", String.valueOf(myPojo));
                dialstarPojos = myPojo;

                dialstarPojos2 = new ArrayList<>();
                for(int i =0;i<myPojo.size();i++){
                    if (grievancecategoryid == 0 && representativeid == 0){

                        dialstarPojos2.add(myPojo.get(i));
                    }

                    else if (grievancecategoryid == myPojo.get(i).getGrievancecategoryid() && representativeid == 0){

                        dialstarPojos2.add(myPojo.get(i));
                    }
                    else if (grievancecategoryid == 0 && representativeid == myPojo.get(i).getRepresentativeid()){

                        dialstarPojos2.add(myPojo.get(i));
                    }
                    else if(grievancecategoryid == myPojo.get(i).getGrievancecategoryid() && representativeid == myPojo.get(i).getRepresentativeid()){
                        // Log.d("selectgrievancid:-", String.valueOf(grievancecategoryid));

                        dialstarPojos2.add(myPojo.get(i));
                    }

                }

                int size = myPojo.size();
                txtcount.setText(String.valueOf(size));
                Grievance.setTabCount(1,size);


                /* Log.d("dialstarPojos2:-", String.valueOf(dialstarPojos2));*/
                adapter = new ComplaintAdapter(dialstarPojos2,getContext(),fragId,grievancecategoryid,representativeid);
                recyclerView.setAdapter(adapter);


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
