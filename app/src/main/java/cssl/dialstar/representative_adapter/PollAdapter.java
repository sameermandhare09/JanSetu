package cssl.dialstar.representative_adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.NewPoll;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//import cssl.dialstar.R;

/**
 * Created by catseye on 8/1/18.
 */

public class PollAdapter extends RecyclerView.Adapter<PollAdapter.MyViewHolder> {
    public RecyclerViewClickListener mListener;
    public Context context;
    JSONObject jsonObject;
    Config config;
    String p="";
    double totalPercentage = 0.0;
    int representativeid1=0;

    int mlaid, representativeid;
    int adminid=0,pollid=0;
    String closedremark=null,closedtype=null;


    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;


    int id=0;
    String usertype="";

    Fragment fragment = new Fragment();

    private ArrayList<DialstarPojo> dataSet;

    public PollAdapter(ArrayList<DialstarPojo> data, Context context)
    {
        this.dataSet = data;
        this.context=context;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textPollName;

        TextView textOption1;
        TextView textOption2;
        TextView textOption3;
        TextView textOption4;
        TextView textOption5;
        TextView textViewPublish;
        TextView textViewAnswer;
        TextView txtdate;
        LinearLayout lnroption1,lnroption2,lnroption3,lnroption4,lnroption5;
        ProgressBar progoption1,progoption2,progoption3,progoption4,progoption5;
        TextView txtperoption1,txtperoption2,txtperoption3,txtperoption4,txtperoption5;
        Button btnEdit;
        Button btnDelete;
        CardView card;
        LinearLayout linear1,lnrbutton;
        // TextView textViewEvent;
        private AdapterView.OnItemClickListener itemClickListener;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.textPollName=(TextView)itemView.findViewById(R.id.PollName);


            this.textOption1=(TextView)itemView.findViewById(R.id.Option1);
            this.textOption2=(TextView)itemView.findViewById(R.id.Option2);
            this.textOption3=(TextView)itemView.findViewById(R.id.Option3);
            this.textOption4=(TextView)itemView.findViewById(R.id.Option4);
            this.textOption5=(TextView)itemView.findViewById(R.id.Option5);
            this.card=(CardView)itemView.findViewById(R.id.pollcardview);
            this.textViewAnswer=(TextView)itemView.findViewById(R.id.txtviewanswer);
            this.txtdate=(TextView)itemView.findViewById(R.id.txtdate);
            this.linear1 = itemView.findViewById(R.id.lnrviewanswer);
            this.lnrbutton = itemView.findViewById(R.id.lnrbutton);
            this.textViewPublish=(TextView)itemView.findViewById(R.id.PublishBy);
            this.btnEdit = itemView.findViewById(R.id.btnpolledit);
            this.btnDelete = itemView.findViewById(R.id.btnpolldelete);

            this.lnroption1 = itemView.findViewById(R.id.lnroption1);
            this.lnroption2 = itemView.findViewById(R.id.lnroption2);
            this.lnroption3 = itemView.findViewById(R.id.lnroption3);
            this.lnroption4 = itemView.findViewById(R.id.lnroption4);
            this.lnroption5 = itemView.findViewById(R.id.lnroption5);

            this.progoption1 = itemView.findViewById(R.id.progoption1);
            this.progoption2 = itemView.findViewById(R.id.progoption2);
            this.progoption3 = itemView.findViewById(R.id.progoption3);
            this.progoption4 = itemView.findViewById(R.id.progoption4);
            this.progoption5 = itemView.findViewById(R.id.progoption5);
            // this.textViewEvent=(TextView)itemView.findViewById(R.id.EventViewName);

            this.txtperoption1 = itemView.findViewById(R.id.txtperoption1);
            this.txtperoption2 = itemView.findViewById(R.id.txtperoption2);
            this.txtperoption3 = itemView.findViewById(R.id.txtperoption3);
            this.txtperoption4 = itemView.findViewById(R.id.txtperoption4);
            this.txtperoption5 = itemView.findViewById(R.id.txtperoption5);

        }

        @Override
        public void onClick(View v)
        {
         /*   Intent intent = new Intent(context, EventDesc.class);
            context.startActivity(intent);*/
        }
        public void setItemClickListener(AdapterView.OnItemClickListener itemClickListener)
        {
            this.itemClickListener=itemClickListener;

        }

    }
    PollAdapter(RecyclerViewClickListener listener)
    {
        mListener=listener;
    }
    public interface RecyclerViewClickListener
    {

        void onClick(View view, int position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poll_card, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        mlaPref = PreferenceManager.getDefaultSharedPreferences(context);
        editor = mlaPref.edit();
        //  usertype = mlaPref.getString("usertype", "");
        representativeid = mlaPref.getInt("representativeid", representativeid);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        id=position+1;

        TextView textPollName=holder.textPollName;


        TextView textOption1=holder.textOption1;
        TextView textOption2=holder.textOption2;
        TextView textOption3=holder.textOption3;
        TextView textOption4=holder.textOption4;
        TextView textPublish=holder.textViewPublish;
        TextView textOption5=holder.textOption5;
        TextView textViewAnswer=holder.textViewAnswer;
        TextView txtdate=holder.txtdate;
        Button btnEdit=holder.btnEdit;
        Button btnDelete=holder.btnDelete;
        CardView card=holder.card;

        LinearLayout lnrbutton =holder.lnrbutton;


        LinearLayout lnroption1=holder.lnroption1,
                lnroption2=holder.lnroption2,
                lnroption3=holder.lnroption3,
                lnroption4=holder.lnroption4,
                lnroption5=holder.lnroption5;

        ProgressBar progoption1=holder.progoption1,
                progoption2=holder.progoption2,
                progoption3=holder.progoption3,
                progoption4=holder.progoption4,
                progoption5=holder.progoption5;

        TextView txtperoption1=holder.txtperoption1,
                txtperoption2=holder.txtperoption2,
                txtperoption3=holder.txtperoption3,
                txtperoption4=holder.txtperoption4,
                txtperoption5=holder.txtperoption5;

        Config config = new Config();

        LinearLayout linear1 = holder.linear1;
        String all="All State";

        p=dataSet.get(position).getConstituencyname();
        if(p == null)
        {
            textPublish.setText(Html.fromHtml("This Poll publish in <b>"+all+"</b>"));

        }
        else
        {
            textPublish.setText(Html.fromHtml("This Poll publish in <b>"+String.valueOf(p)+"</b>"));
        }

        //Log.i("Publish",String.valueOf(p));
        String createddate = dataSet.get(position).getCreateddate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");

        Date date;
        try {
            date = format.parse(createddate);
            createddate = dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

String op1="",op2="",op3="",op4="",op5="";
         op1=dataSet.get(position).getOption1();
         op2=dataSet.get(position).getOption2();
         op3=dataSet.get(position).getOption3();
         op4=dataSet.get(position).getOption4();
         op5=dataSet.get(position).getOption5();
        int op1count=dataSet.get(position).getOption1Count();
        int op2count=dataSet.get(position).getOption2Count();
        int op3count=dataSet.get(position).getOption3Count();
        int op4count=dataSet.get(position).getOption4Count();
        int op5count=dataSet.get(position).getOption5Count();
        representativeid1=dataSet.get(position).getRepresentativeid();
        txtdate.setText(createddate);
        textPollName.setText(id+"."+dataSet.get(position).getSubject());
        int totalcount=dataSet.get(position).getTotalcount();


        if(representativeid!=representativeid1)
        {
            lnrbutton.setVisibility(View.GONE);
        }


        // textOptions.setText("option. "+dataSet.get(position).getOption1());
        if(!op1.equalsIgnoreCase("")) {
            totalPercentage = (100.0 * op1count) / totalcount;
            lnroption1.setVisibility(View.VISIBLE);
            textOption1.setVisibility(View.VISIBLE);
            textOption1.setText(dataSet.get(position).getOption1());//"option1. "+
            txtperoption1.setText(Math.round(totalPercentage) + "%");
            progoption1.getProgressDrawable().setColorFilter(
                    Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
            progoption1.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
        }
        if(!op2.equalsIgnoreCase("")) {

            totalPercentage = (100.0 * op2count) / totalcount;
            lnroption2.setVisibility(View.VISIBLE);
            textOption2.setVisibility(View.VISIBLE);
            textOption2.setText(dataSet.get(position).getOption2());//"option2. "+
            txtperoption2.setText(Math.round(totalPercentage) + "%");
            progoption2.getProgressDrawable().setColorFilter(
                    Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN);
            progoption2.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
        }
        if(!op3.equalsIgnoreCase("")) {
            totalPercentage = (100.0 * op3count) / totalcount;
            lnroption3.setVisibility(View.VISIBLE);
            textOption3.setVisibility(View.VISIBLE);
            textOption3.setText(dataSet.get(position).getOption3());//"option3. "+
            txtperoption3.setText(Math.round(totalPercentage) + "%");
            progoption3.getProgressDrawable().setColorFilter(
                    Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);
            progoption3.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
        }
        if(!op4.equalsIgnoreCase("")) {
            totalPercentage = (100.0 * op4count) / totalcount;
            lnroption4.setVisibility(View.VISIBLE);
            textOption4.setVisibility(View.VISIBLE);
            textOption4.setText(dataSet.get(position).getOption4());//"option4. "+
            txtperoption4.setText(Math.round(totalPercentage) + "%");
            progoption4.getProgressDrawable().setColorFilter(
                    Color.LTGRAY, android.graphics.PorterDuff.Mode.SRC_IN);
            progoption4.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
        }
        if(!op5.equalsIgnoreCase("")) {
            totalPercentage = (100.0 * op5count) / totalcount;
            lnroption5.setVisibility(View.VISIBLE);
            textOption5.setVisibility(View.VISIBLE);
            textOption5.setText(dataSet.get(position).getOption5());//"option5. "+
            txtperoption5.setText(Math.round(totalPercentage) + "%");
            progoption5.getProgressDrawable().setColorFilter(
                    Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
            progoption5.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
        }
         usertype = mlaPref.getString("usertype","");
        String usertypeurl=dataSet.get(position).getUsertype();
        String representativename = null;
        int pollstateid=0;
        int pollconsid=0;
        pollstateid = dataSet.get(position).getStateid();
        pollconsid = dataSet.get(position).getConstituencyid();
        representativename = mlaPref.getString("name","");
        String representativenameurl = null;
        representativenameurl = dataSet.get(position).getRepresentativename();
        if(usertype.equals(usertypeurl)&&representativename.equalsIgnoreCase(representativenameurl)){//
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        }

      /*  if(usertype.equalsIgnoreCase("mla")){
            lnrbutton.setVisibility(View.GONE);

        }*/
        final int finalPollstateid = pollstateid;
        final int finalPollconsid = pollconsid;
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call edit polls fragment

                String subject = dataSet.get(position).getSubject();
                String option1 = dataSet.get(position).getOption1();
                String option2 = dataSet.get(position).getOption2();
                String option3 = dataSet.get(position).getOption3();
                String option4 = dataSet.get(position).getOption4();
                String option5 = dataSet.get(position).getOption5();
                int pollid = dataSet.get(position).getPollid();
                String pollloksabhaconstituency =  dataSet.get(position).getName();





                String calling = "edit";


                //calling,subject,option1,option2,option3,option4,option5,pollid
                fragment = NewPoll.newInstance(calling,subject,option1,option2,option3,option4,option5,pollid,finalPollstateid,finalPollconsid,pollloksabhaconstituency);
                if (fragment != null) {
                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);  fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //call delete web service


                final TextInputEditText textInputEditText = new TextInputEditText(context);
                textInputEditText.setHint(context.getResources().getString(R.string.Enter_Reason));


                final AlertDialog dialog = new AlertDialog.Builder(context)
                        .setView(textInputEditText)
                        .setTitle(context.getResources().getString(R.string.Reason))

                        .setPositiveButton(android.R.string.ok, null) //Set to null. We override the onclick
                        .setNegativeButton(android.R.string.cancel, null)
                        .create();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {

                    @Override
                    public void onShow(DialogInterface dialogInterface) {

                        Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View view) {
                                // TODO Do something

                                String topic = textInputEditText.getText().toString().trim();

                                if (textInputEditText.getText().toString().trim().isEmpty()){

                                    textInputEditText.setError(context.getResources().getString(R.string.Please_enter_Reason));

                                }


                                else {

                                    mlaPref = PreferenceManager.getDefaultSharedPreferences(context);
                                    adminid = mlaPref.getInt("representativeid",0);
                                    pollid = dataSet.get(position).getPollid();
                                    closedremark = textInputEditText.getText().toString().trim();
                                    closedtype = mlaPref.getString("usertype","");
                                    jsonObject=new JSONObject();
                                    try {
                                        jsonObject.put("adminid",adminid);
                                        jsonObject.put("pollid",pollid);
                                        jsonObject.put("closedremark",closedremark);
                                        jsonObject.put("closedtype",closedtype);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    new HttpRequestDeletePoll(jsonObject.toString(),position).execute();
                                    dialog.dismiss();
                                }
                                //Dismiss once everything is OK.
                                //dialog.dismiss();
                            }
                        });
                    }
                });
                dialog.show();


            }
        });



    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }





    private class HttpRequestDeletePoll extends AsyncTask<Void, Void,String > {
        Config config = new Config();
        String json;
        int pollid=0;
        int position=0;
        Dialog progressDialog;
        public HttpRequestDeletePoll(String json,int position)
        {
            this.json=json;
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
            // try {
   //         final String url = config.getClosedPolls();
            final String url = config.representativeremoteurl+"admin/app/closedPolls";

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

                return null;


            }

        }

        @Override
        protected void onPostExecute(String greeting) {

            if(greeting!=null){

                String responceName = greeting;
                if(!responceName.contains("timestamp"))
                {
                    pollid=Integer.parseInt(responceName);
                    Log.e("responceName:- ",responceName+"Poll ID="+pollid);
                    Toast.makeText(context, context.getResources().getString(R.string.Poll_Successfully_Deleted), Toast.LENGTH_SHORT).show();
                    dataSet.remove(position);
                    notifyDataSetChanged();

                }

            }else{
                new android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle(context.getString(R.string.Error))
                        .setMessage(context.getResources().getString(R.string.no_internet))
                        .setCancelable(false)
                        .setNegativeButton(context.getResources().getString(R.string.ok),null)
                        .show();
            }



            progressDialog.dismiss();

        }

    }


}