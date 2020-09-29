package cssl.dialstar.representative_adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.Grievance_desc;
import cssl.dialstar.representative_util.DialstarPojo;

/**
 * Created by catseye on 8/12/17.
 */

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.MyViewHolder> {

    ArrayList<DialstarPojo> headingData,clientdata;
    Context context;
    String file,file1;
    int fragId,grievanceid;
    int grievancecategoryid = 0;
    int representativeid=0;
    int pos=0;
    String grievancename,category;
    String username,address;
    SharedPreferences Mlapref;
    String usertype="";

    android.support.v4.app.Fragment fragment = new android.support.v4.app.Fragment();
     int isacknowledged;

    ArrayList<String>files = new ArrayList<>();
   // ArrayList<Integer> integers=new ArrayList<>();



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtviewcomplaintname;
       // TextView txtviewcatgoryname;
        TextView txtviewcomplaintdate;
        TextView txtviewcomplaintassignedto;
        TextView txtviewcomplaintcreatedby;
        TextView txtviewcomplaintdescript;
        TextView txtviewcomplaintacknowledge;
        TextView txtviewcomplaintacknowledgeby;
        TextView txtviewcomplaintclose;
        TextView txtviewcomplaintcloseby;
        TextView txtassignedto;
        RelativeLayout btnviewdetails;

        TextView txtviewaddress;
        LinearLayout linearLayoutacknowledg,linearLayoutclosed,linearLayoutacknowledg1,linearLayoutclosed1;


        String usertype = null;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtviewcomplaintname = itemView.findViewById(R.id.txtView_complaint_name);
           // txtviewcatgoryname = itemView.findViewById(R.id.txtcategory);
            txtviewcomplaintdate = itemView.findViewById(R.id.txtView_complaint_date);
            txtviewcomplaintassignedto = itemView.findViewById(R.id.txtView_assigned_to);
            txtviewcomplaintcreatedby = itemView.findViewById(R.id.txtView_created_by);
            txtviewcomplaintdescript=itemView.findViewById(R.id.txtView_descript);
            txtviewcomplaintacknowledge = itemView.findViewById(R.id.txtView_acknowledgedate);
            linearLayoutacknowledg = itemView.findViewById(R.id.lnr_acknowledge);
            txtviewcomplaintacknowledgeby=itemView.findViewById(R.id.txtView_acknowledgeby);
            linearLayoutclosed = itemView.findViewById(R.id.lnr_close);
            txtviewcomplaintclose=itemView.findViewById(R.id.txtView_Closedate);
            txtviewcomplaintcloseby = itemView.findViewById(R.id.txtView_Closeby);
            txtviewaddress = itemView.findViewById(R.id.txtView_address);
            btnviewdetails = itemView.findViewById(R.id.btnviewdetails);
            txtassignedto = itemView.findViewById(R.id.txtassignedto);
         /*   linearLayoutacknowledg1 = itemView.findViewById(R.id.lnr_acknowledge1);*/
          /*  linearLayoutclosed1 = itemView.findViewById(R.id.lnr_close1);*/




        }
    }


  public ComplaintAdapter(){
      this.clientdata=headingData;
  }
    public ComplaintAdapter(ArrayList<DialstarPojo> headingData, Context activity,int fragId,int x,int y) {

        this.headingData = headingData;
        //this.clientdata=headingData;
        context = activity;
        this.fragId=fragId;
        grievancecategoryid=x;
        representativeid=y;
        //grievancename=a;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_row,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        Mlapref = PreferenceManager.getDefaultSharedPreferences(context);
        usertype = Mlapref.getString("usertype", "");

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        TextView txtviewcomplaintname = holder.txtviewcomplaintname;
        //TextView txtviewcatgoryname = holder.txtviewcatgoryname;
        TextView txtviewcomplaintdate = holder.txtviewcomplaintdate;
        TextView txtviewcomplaintassignedto = holder.txtviewcomplaintassignedto;
        TextView txtviewcomplaintcreatedby = holder.txtviewcomplaintcreatedby;
        TextView txtviewcomplaintdescript = holder.txtviewcomplaintdescript;
        TextView txtviewcomplaintacknowledge = holder.txtviewcomplaintacknowledge;
        TextView txtviewcomplaintclose = holder.txtviewcomplaintclose;
        TextView txtviewcomplaintacknowledgeby = holder.txtviewcomplaintacknowledgeby;
        TextView txtviewcomplaintcloseby = holder.txtviewcomplaintcloseby;
        TextView txtviewaddress = holder.txtviewaddress;
        TextView txtassignedto = holder.txtassignedto;
        LinearLayout linearLayoutacknowledg = holder.linearLayoutacknowledg;
        LinearLayout linearLayoutclosed = holder.linearLayoutclosed;
        RelativeLayout btnviewdetails = holder.btnviewdetails;

      /*  LinearLayout linearLayoutacknowledg1 = holder.linearLayoutacknowledg1;*/
/*        LinearLayout linearLayoutclosed1 = holder.linearLayoutclosed1;*/


        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        int acknowledged = headingData.get(position).getAcknowledgeby();


       /* if(usertype.equalsIgnoreCase("mla")){
            txtassignedto.setVisibility(View.VISIBLE);
            txtassignedto.setText(Html.fromHtml("Assigned to: "+headingData.get(position).getRepresentativename()));
        }*/
        txtassignedto.setVisibility(View.VISIBLE);
        txtassignedto.setText(Html.fromHtml(context.getResources().getString(R.string.Assigned_to)+headingData.get(position).getRepresentativename()));
        if(acknowledged > 0){
            String usertype = "";
            linearLayoutacknowledg.setVisibility(View.VISIBLE);
        /*    linearLayoutacknowledg1.setVisibility(View.VISIBLE);*/

          /*  usertype = headingData.get(position).getAcknowledgetype();
            if(usertype.equalsIgnoreCase("mla")){
                String mlaname =headingData.get(position).getMlaname();
                txtviewcomplaintacknowledgeby.setText(mlaname);

            }
            else if (usertype.equalsIgnoreCase("representative")){
                String representative = headingData.get(position).getRepresentativename();
                txtviewcomplaintacknowledgeby.setText(representative);
            }*/
            String representative="";

         /* if (headingData.get(position).getAcknowledgetype().equalsIgnoreCase("mla") || headingData.get(position).getAcknowledgetype().equalsIgnoreCase("mp") )
          {
              representative = headingData.get(position).getOption1();
          }else{

          }*/
            representative = headingData.get(position).getOption1();
            // representative = headingData.get(position).getRepresentativename();
            txtviewcomplaintacknowledgeby.setText(representative);

            String acknowledgedate = headingData.get(position).getAcknowledgedate();
            try {
                date = format.parse(acknowledgedate);//converting given string to date,parsing
                String dateTime = dateFormat.format(date);
                acknowledgedate=dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
           txtviewcomplaintacknowledge.setText(acknowledgedate);

        }

        int close = headingData.get(position).getClosedby();
        if(close>0){

            String usertype = "";
            linearLayoutacknowledg.setVisibility(View.VISIBLE);



            usertype = headingData.get(position).getAcknowledgetype();

            if(!String.valueOf(usertype).equalsIgnoreCase("null")) {
              /*  if (usertype.equalsIgnoreCase("mla")) {
                    String mlaname1 = headingData.get(position).getMlaname();
                    txtviewcomplaintacknowledgeby.setText(mlaname1);

                } else if (usertype.equalsIgnoreCase("representative")) {
                    String representative1 = headingData.get(position).getRepresentativename();
                    txtviewcomplaintacknowledgeby.setText(representative1);
                }*/
                String representative1 = headingData.get(position).getOption1();
                txtviewcomplaintacknowledgeby.setText(representative1);
                String acknowledgedate = headingData.get(position).getAcknowledgedate();
                try {
                    date = format.parse(acknowledgedate);//converting given string to date,parsing
                    String dateTime = dateFormat.format(date);
                    acknowledgedate = dateTime;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                txtviewcomplaintacknowledge.setText(acknowledgedate);

            }
                linearLayoutclosed.setVisibility(View.VISIBLE);
          /*  linearLayoutclosed1.setVisibility(View.VISIBLE);*/
                usertype = headingData.get(position).getClosedtype();
           /*     if(usertype.equalsIgnoreCase("mla")){
                    String mlaname =headingData.get(position).getMlaname();
                    txtviewcomplaintcloseby.setText(mlaname);



                }
                else if (usertype.equalsIgnoreCase("representative")){
                    String representative = headingData.get(position).getRepresentativename();
                    txtviewcomplaintcloseby.setText(representative);
                }
*/

            String representative = headingData.get(position).getOption2();
            txtviewcomplaintcloseby.setText(representative);

            String closeddate = headingData.get(position).getCloseddate();
            try {
                date = format.parse(closeddate);//converting given string to date,parsing
                String dateTime = dateFormat.format(date);
                closeddate=dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtviewcomplaintclose.setText(closeddate);

        }



        String dtStart = headingData.get(position).getCreateddate();
        try {
            date = format.parse(dtStart);//converting given string to date,parsing
            String dateTime = dateFormat.format(date);
            dtStart=dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
         //date to new format and convert to String,formatting
        //Log.d("dateTime",dateTime);



        //txtviewcomplaintname.setText(headingData.get(position).getCategoryname());

        grievancename = headingData.get(position).getGrievancename();
        category = headingData.get(position).getCategoryname();

        txtviewcomplaintname.setText(grievancename);
        //txtviewcatgoryname.setText(category);
        txtviewcomplaintdate.setText(dtStart);
        txtviewcomplaintassignedto.setText(headingData.get(position).getRepresentativename());
        txtviewcomplaintcreatedby.setText(headingData.get(position).getUsername());
        txtviewcomplaintdescript.setText(headingData.get(position).getDescription());
        //txtviewaddress.setText("Not Mention");
      /*  address=headingData.get(position).getAddress();
        txtviewaddress.setText("Not Mention");
        if(!address.isEmpty()) {*/
            txtviewaddress.setText(context.getResources().getString(R.string.Sent_from) + headingData.get(position).getAddress());
        //}



        final String finalDtStart = dtStart;

        btnviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                grievanceid = headingData.get(position).getGrievanceid();
                String username = null,closedby,closeddate,acknowledgedby,acknwoldgeddate,mobileno = null,emailid = null,address=null;

                username=headingData.get(position).getUsername();
                mobileno= headingData.get(position).getMobileno();
                emailid=headingData.get(position).getEmailid();
                address = headingData.get(position).getAddress();

                // acknowledgedby = headingData.get(position).getAcknowledgetype();
                String createddate = headingData.get(position).getCreateddate();
                acknwoldgeddate = headingData.get(position).getAcknowledgedate();

                // closedby = headingData.get(position).getClosedtype();
                closeddate = headingData.get(position).getCloseddate();
                grievancename = headingData.get(position).getGrievancename();



                Date date;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                if(acknwoldgeddate!=null) {
                    try {
                        date = format.parse(acknwoldgeddate);//converting given string to date,parsing
                        String dateTime = dateFormat.format(date);
                       // acknwoldgeddate = dateTime;



                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if (closeddate!=null){
                    try {


                        date = format.parse(closeddate);//converting given string to date,parsing
                        String dateTime1 = dateFormat.format(date);
                       // closeddate = dateTime1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                files.clear();
                final String description =headingData.get(position).getDescription();
                //Log.d("description", description);
                file = headingData.get(position).getFile();
                try {
                    JSONObject jsonObject = new JSONObject(file);

                    int sizeobj=jsonObject.length();
                    for (int i=0;i<sizeobj;i++)
                    {
                        if(jsonObject.has("file"+(i+1))) {
                            file1 = jsonObject.getString("file" + (i + 1));
                            files.add(file1);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // pos=position;
                //String grievancename;
                // grievancename = headingData.get(position).getGrievancename();

               // Log.d("grievanceid", String.valueOf(grievanceid));

               // Intent intent = new Intent(context, ComplaintDetail.class);
               // Grievance.setTabCount(0,1);
                String usertype = "";
                usertype = headingData.get(position).getAcknowledgetype();
                String citizenAddress="",citizengender="",citizenimage="";
                citizenAddress = headingData.get(position).getOption1()+", "+headingData.get(position).getCityname()+", "+
                        headingData.get(position).getDistrictname()+", "+headingData.get(position).getStatename()+", "+headingData.get(position).getCountryname();
                citizengender = headingData.get(position).getOption3();
                citizenimage =  headingData.get(position).getOption2();

                if(usertype!=null) {

                                      String representative = headingData.get(position).getOption1();
                    String representative1 = headingData.get(position).getOption2();
                    int representativeid=0;
                    representativeid=headingData.get(position).getRepresentativeid();
                    fragment =Grievance_desc.newInstance(representative,representative1,description,
                            files,fragId,grievanceid,grievancecategoryid,
                            representativeid,position,grievancename,createddate,acknwoldgeddate,closeddate,
                            username,mobileno,emailid,address,citizenAddress,citizengender,citizenimage);
                    //fragment = new Grievance_desc();
                    if (fragment != null) {
                        FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }

                }
                else
                {
                    String representative = headingData.get(position).getRepresentativename();
                    int representativeid=0;
                    representativeid=headingData.get(position).getRepresentativeid();


                    fragment =Grievance_desc.newInstance(usertype,usertype,description,files,fragId,grievanceid,grievancecategoryid,
                            representativeid,position,grievancename,finalDtStart,acknwoldgeddate,closeddate,
                            username,mobileno,emailid,address,citizenAddress,citizengender,citizenimage);
                    //fragment = new Grievance_desc();
                    if (fragment != null) {
                        FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        //
                    }
                }







            }
        });

    }

/*
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MyAdapter", "onActivityResult");
    }*/
    @Override
    public int getItemCount() {
        return headingData.size();
    }

}
