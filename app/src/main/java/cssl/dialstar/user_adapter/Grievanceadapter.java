package cssl.dialstar.user_adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_fragment.GrievanceDetails;
import cssl.dialstar.user_fragment.NewGrievance;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_activity.Grievance_history;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by cats on 13/12/17.
 */

public class Grievanceadapter  extends RecyclerView.Adapter<Grievanceadapter.MyViewHolder>{

 private ArrayList<Dialstar> list;
    Context context;
    String file,file1;
    int grievanceid;
    int grievancecategoryid = 0;
    int representativeid=0;
    int pos=0;
    String grievancename,category;
    String username,address;
    int fragid;

    android.support.v4.app.Fragment fragment;
    NewGrievance newGrievance = new NewGrievance();

    int isacknowledged;

    ArrayList<String>files = new ArrayList<>();
    // ArrayList<Integer> integers=new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Time,category,name;



/*            Time = (TextView) view.findViewById(R.id.date);
            category = (TextView) view.findViewById(R.id.category);
            name = (TextView) view.findViewById(R.id.name);*/

            TextView txtviewcomplaintname;
            TextView txtviewcatgoryname;
            TextView txtviewcomplaintdate;
            TextView txtviewcomplaintassignedto;
            TextView txtviewcomplaintcreatedby;
            TextView txtviewcomplaintdescript;
            TextView txtviewcomplaintacknowledge;
            TextView txtviewcomplaintacknowledgeby;
            TextView txtviewcomplaintclose;
            TextView txtviewcomplaintcloseby;
            ImageView img1,img2,img3;
        ImageView img11,img22,img33;
        TextView txtstatussolved;
        TextView txtstatuspending;
        TextView txtstatusacknowledge;

        Button btnEdit;
        Button btnDelete;
        TextView btnviewdetails;


            TextView txtviewaddress;
            RelativeLayout relativeLayout;
            LinearLayout linearLayoutacknowledg,linearLayoutclosed,linearLayoutacknowledg1,linearLayoutclosed1;


           public MyViewHolder(View itemView) {
                super(itemView);

                txtviewcomplaintname = itemView.findViewById(R.id.txtView_complaint_name);
                txtviewcatgoryname = itemView.findViewById(R.id.txtview_category_name);
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
               txtstatussolved = itemView.findViewById(R.id.txtstatusolved);
               txtstatuspending = itemView.findViewById(R.id.txtstatuspending);
               txtstatusacknowledge = itemView.findViewById(R.id.txtstatusAcknowledge);
                 btnEdit = itemView.findViewById(R.id.btngrievanceedit);
                btnDelete = itemView.findViewById(R.id.btngrievancedelete);
               btnviewdetails = itemView.findViewById(R.id.btnviewdetails);
               relativeLayout = itemView.findViewById(R.id.relativebuttons);
               img1=itemView.findViewById(R.id.img1);
               img2=itemView.findViewById(R.id.img2);
               img3=itemView.findViewById(R.id.img3);
               img11=itemView.findViewById(R.id.img11);
               img22=itemView.findViewById(R.id.img22);
               img33=itemView.findViewById(R.id.img33);


               /*   linearLayoutacknowledg1 = itemView.findViewById(R.id.lnr_acknowledge1);*/
          /*  linearLayoutclosed1 = itemView.findViewById(R.id.lnr_close1);*/




        }
    }


    public Grievanceadapter(ArrayList<Dialstar> List, Context  context, int fragid ) {
        this.list =List;
       // Log.d("list in adapter", String.valueOf(list));
        this.context = context;
        this.fragid = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     /*   View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grievance_item, parent, false);

*/
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.heading_row,parent,false);


        MyViewHolder myViewHolder =new  MyViewHolder(itemView);
        return myViewHolder;

        //return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        //Dialstar recycle = List.get(position);

/*

        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#F4F4F4"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
*/


      String time=list.get(position).getCreateddate();


        TextView txtviewcomplaintname = holder.txtviewcomplaintname;
        TextView txtviewcatgoryname = holder.txtviewcatgoryname;
        TextView txtviewcomplaintdate = holder.txtviewcomplaintdate;
        TextView txtviewcomplaintassignedto = holder.txtviewcomplaintassignedto;
        TextView txtviewcomplaintcreatedby = holder.txtviewcomplaintcreatedby;
        TextView txtviewcomplaintdescript = holder.txtviewcomplaintdescript;
        TextView txtviewcomplaintacknowledge = holder.txtviewcomplaintacknowledge;
        TextView txtviewcomplaintclose = holder.txtviewcomplaintclose;
        TextView txtviewcomplaintacknowledgeby = holder.txtviewcomplaintacknowledgeby;
        TextView txtstatussolved = holder.txtstatussolved;
        TextView txtstatuspending = holder.txtstatuspending;
        TextView txtstatusacknowledge = holder.txtstatusacknowledge;
        ImageView img1=holder.img1;
        ImageView img2=holder.img2;
        ImageView img3=holder.img3;
        ImageView img11=holder.img11;
        ImageView img22=holder.img22;
        ImageView img33=holder.img33;
        TextView txtviewcomplaintcloseby = holder.txtviewcomplaintcloseby;
        TextView txtviewaddress = holder.txtviewaddress;
        LinearLayout linearLayoutacknowledg = holder.linearLayoutacknowledg;
        LinearLayout linearLayoutclosed = holder.linearLayoutclosed;
        Button btnEdit=holder.btnEdit;
        Button btnDelete=holder.btnDelete;
        TextView btnviewdetails=holder.btnviewdetails;
        RelativeLayout relativeLayout= holder.relativeLayout;



        if(fragid==1){
            relativeLayout.setVisibility(View.VISIBLE);

        }
        else if(fragid==2){
            relativeLayout.setVisibility(View.GONE);

        }
        else if(fragid==3){
            relativeLayout.setVisibility(View.GONE);

        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grievanceid = list.get(position).getGrievanceid();
                new AlertDialog.Builder(context)
                        .setMessage(context.getResources().getString(R.string.delete_grievance))
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {

                                new HttpRequestDeleteGrievance(position).execute();

                                dialog.cancel();

                            }
                        })

                        .setNegativeButton(context.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                dialog.cancel();

                            }
                        }).show();



            }
        });


        Date date;
        //String dtStart1=time;//"2017-11-23 15:10:25.0";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("MMM dd, yyyy ");

        final int acknowledged = list.get(position).getAcknowledgeby();
        if(acknowledged > 0){

            linearLayoutacknowledg.setVisibility(View.GONE);
             /*linearLayoutacknowledg1.setVisibility(View.VISIBLE);*/

            String usertype = "";
            usertype = list.get(position).getRepresentativename();
         /*   if(usertype.equalsIgnoreCase("mla")){
                String mlaname =list.get(position).getMlaname();
                txtviewcomplaintacknowledgeby.setText(mlaname);

            }*/
           /*  if (usertype.equalsIgnoreCase("representative")){
                String representative = list.get(position).getRepresentativename();
                txtviewcomplaintacknowledgeby.setText(usertype);
            }*/


            String representative = list.get(position).getRepresentativename();
            txtviewcomplaintacknowledgeby.setText(usertype);

            String acknowledgedate = list.get(position).getAcknowledgedate();
            try {
                date = format.parse(acknowledgedate);//converting given string to date,parsing
                String dateTime = dateFormat1.format(date);
                acknowledgedate=dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtviewcomplaintacknowledge.setText(acknowledgedate);

        }



        int close = list.get(position).getIsclosed();
        int acknowledge = list.get(position).getIsacknowledged();

        if(acknowledge==0 && close==0) {
            img1.setVisibility(View.VISIBLE);
            txtstatuspending.setTextColor(context.getResources().getColor(R.color.holo_green_light));
            }
            else
        {
            img11.setVisibility(View.VISIBLE);

        }
         if(acknowledge==1 && close==0){
            img2.setVisibility(View.VISIBLE);
            txtstatusacknowledge.setTextColor(context.getResources().getColor(R.color.holo_green_light));
        }
        else
         {
             img22.setVisibility(View.VISIBLE);

         }
         if(close==1 && acknowledge==1){
            img3.setVisibility(View.VISIBLE);
            linearLayoutacknowledg.setVisibility(View.GONE);
            txtstatussolved.setTextColor(context.getResources().getColor(R.color.holo_green_light));


            String usertype = "";



            String representative1 = list.get(position).getRepresentativename();
            txtviewcomplaintacknowledgeby.setText(representative1);
            String acknowledgedate = list.get(position).getAcknowledgedate();
            try {
                date = format.parse(acknowledgedate);//converting given string to date,parsing
                String dateTime = dateFormat1.format(date);
                acknowledgedate = dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtviewcomplaintacknowledge.setText(acknowledgedate);


            linearLayoutclosed.setVisibility(View.GONE);


            String representative = list.get(position).getRepresentativename();
            txtviewcomplaintcloseby.setText(representative);

            String closeddate = list.get(position).getCloseddate();
            try {
                date = format.parse(closeddate);//converting given string to date,parsing
                String dateTime = dateFormat1.format(date);
                closeddate=dateTime;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtviewcomplaintclose.setText(closeddate);

        }
        else
         {
             img33.setVisibility(View.VISIBLE);

         }





        String createddate = list.get(position).getCreateddate();
        try {
            date = format.parse(createddate);//converting given string to date,parsing
            String dateTime = dateFormat1.format(date);
            createddate=dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }


        grievancename = list.get(position).getGrievancename();
        category = list.get(position).getCategoryname();

        txtviewcomplaintname.setText(grievancename);
        txtviewcatgoryname.setText(category);
        txtviewcomplaintdate.setText(createddate);
        txtviewcomplaintassignedto.setText(list.get(position).getRepresentativename());
        txtviewcomplaintcreatedby.setText(list.get(position).getUsername());
        txtviewcomplaintdescript.setText(list.get(position).getDescription());
        txtviewaddress.setText(context.getResources().getString(R.string.Sent_from) + list.get(position).getAddress());


        btnviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = list.get(position).getCategoryname();

                 String description =list.get(position).getDescription();
                 String elective = list.get(position).getElectivename();
                 int typeid = list.get(position).getTypeid();

                String representativename = list.get(position).getRepresentativename();
                String partyname = list.get(position).getPartyname();
                String address = list.get(position).getAddress();




                int categoryid=0,partyid=0,representativeid=0,grievanceid=0;
                String closedate = "",acknowledgedate="",createddate="";



                grievancename = list.get(position).getGrievancename();
                categoryid=list.get(position).getCategoryid();
                partyid=list.get(position).getPartyid();
                representativeid=list.get(position).getRepresentativeid();

                int acknowledge=0,closed=0;
                acknowledge = list.get(position).getIsacknowledged();
                closed = list.get(position).getIsclosed();
                closedate = list.get(position).getCloseddate();
                acknowledgedate = list.get(position).getAcknowledgedate();
                createddate = list.get(position).getCreateddate();

                grievanceid = list.get(position).getGrievanceid();

                files.clear();
                file = list.get(position).getFile();
                try {
                    JSONObject jsonObject = new JSONObject(file);

                    int sizeobj=jsonObject.length();
                    for (int i=0;i<sizeobj;i++)
                    {
                        file1=jsonObject.getString("file"+(i+1));
                        files.add(file1);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Date date;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                if(acknowledgedate!=null) {
                    try {
                        date = format.parse(acknowledgedate);//converting given string to date,parsing
                        String dateTime = dateFormat.format(date);
                        acknowledgedate = dateTime;

              /*          date = format.parse(closeddate);//converting given string to date,parsing
                        String dateTime1 = dateFormat.format(date);
                        closeddate = dateTime1;*/

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
                if(closedate!=null) {
                    try {
                      /*  date = format.parse(acknwoldgeddate);//converting given string to date,parsing
                        String dateTime = dateFormat.format(date);
                        acknwoldgeddate = dateTime;*/

                        date = format.parse(closedate);//converting given string to date,parsing
                        String dateTime1 = dateFormat.format(date);
                        closedate = dateTime1;

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                fragment = GrievanceDetails.newInstance(category,grievancename,description,
                        files,representativename,partyname,address,categoryid,partyid,
                        representativeid,grievanceid,acknowledge,closed,acknowledgedate,closedate,elective,typeid,createddate);
                if (fragment != null) {
                    //FragmentManager fragmentManager = fragment.getFragmentManager();
                    FragmentManager fragmentManager = ((UserDashboard)context).getSupportFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.mainFrame, fragment);  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }



            }
        });


    }
    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }






    private class HttpRequestDeleteGrievance extends AsyncTask<Void, Void, String> {


        ConfigUser config = new ConfigUser();
        Dialog progressDialog;
        int position=0;

        public HttpRequestDeleteGrievance(int position) {
            this.position=position;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new Dialog(context);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog


        }

        @Override
        protected String doInBackground(Void... params) {
            try {
           //     String url=config.getDeleteUserGrievance()+grievanceid;
                String url=config.userremoteurl+"admin/app/deleteUserGrievance/"+grievanceid;


                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());



                //Dialstar[] forNow2 = restTemplate.getForObject(url,Dialstar[].class);
               // ArrayList<Dialstar> greeting1 = new ArrayList(Arrays.asList(forNow2));

                String result = restTemplate.getForObject(url,String.class);

                return result;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }

            return "0";
        }
        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            int isdelete=0;
            isdelete=Integer.parseInt(result);


      if(isdelete >0){
          Toast.makeText(context, context.getResources().getString(R.string.delete_grievance_successfully), Toast.LENGTH_SHORT).show();

          list.remove(position);
          notifyDataSetChanged();
          newGrievance.setcount(list.size());

      }




        }
    }




}
