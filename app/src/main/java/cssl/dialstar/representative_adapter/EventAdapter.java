package cssl.dialstar.representative_adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.Event;
import cssl.dialstar.representative_fragment.EventDetails;
import cssl.dialstar.representative_util.DialstarPojo;

/**
 * Created by catseye on 22/12/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    public RecyclerViewClickListener mListener;
    public Context context;
    String p="";
    private ArrayList<DialstarPojo> dataSet;
    int id=0;
    int eventid=0;
    SharedPreferences mlaPref; // 0 - for private mode
    SharedPreferences.Editor editor;
    String emailid, usertype="";
    String name = "";
    EventDetails fragment = new EventDetails();

    EventAdapter(RecyclerViewClickListener listener)
    {
        mListener=listener;
    }

    public interface RecyclerViewClickListener
    {

        void onClick(View view, int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card1, parent, false);
        view.setOnClickListener(Event.myOnClickListener);

        mlaPref = PreferenceManager.getDefaultSharedPreferences(context);
        usertype = mlaPref.getString("usertype", "");


        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {
        id=position+1;
        TextView textName=holder.textViewName;
        final TextView textStart=holder.textViewStart;
        TextView textCreate=holder.textViewCreate;
        TextView textPublish=holder.textViewPublish;
        CardView card=holder.card;
        final LinearLayout linear=holder.linear1;
        Button btnEdit=holder.btnEdit;
        Button btnDelete=holder.btnDelete;
        RelativeLayout relativeLayout = holder.relativeLayout;
        TextView textEnd = holder.textViewEnd;
        RelativeLayout btnviewdetails = holder.btnviewdetails;

        String representativename = null;
        representativename = mlaPref.getString("name","");
        String representativenameurl = null;
        representativenameurl = dataSet.get(position).getRepresentativename();

        if(usertype.equalsIgnoreCase("mla")){
            relativeLayout.setVisibility(View.GONE);
        }
        else if(usertype.equalsIgnoreCase("representative")&&representativename.equalsIgnoreCase(representativenameurl)){
            //relativeLayout.setVisibility(View.VISIBLE);
        }

        btnviewdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String name=dataSet.get(position).getEventname();
                String desc=dataSet.get(position).getDescription();
                String startDate=dataSet.get(position).getEventstartdate();
                String endDate=dataSet.get(position).getEventenddate();
                String created=dataSet.get(position).getRepresentativename();
                String on=dataSet.get(position).getCreateddate();
                eventid = dataSet.get(position).getEventid();
                String  usertype = dataSet.get(position).getUsertype();

                int userid=0;
                int eventstateid=0,eventcount=0;
                int eventconsid=0;
                int representativeid=0;
                int adminid=0;
                String eventloksabhaconstituency="";



                representativeid=dataSet.get(position).getRepresentativeid();
                adminid=dataSet.get(position).getAdminid();
                userid=dataSet.get(position).getUserid();
                eventstateid = dataSet.get(position).getStateid();
                eventconsid = dataSet.get(position).getConstituencyid();
                eventcount = dataSet.get(position).getCount();
                eventloksabhaconstituency = dataSet.get(position).getSubject();


                Date date;
                String dtStart=startDate;//"2017-11-23 15:10:25.0";
                String dtEnd=endDate;
                String dtOn = on;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

                try {
                    date = format.parse(dtStart);//converting given string to date,parsing
                    String dateTime = dateFormat.format(date); //date to new format and convert to String,formatting
                    Log.d("dateTime",dateTime);
                   startDate = dateTime;

                    date = format.parse(dtEnd);
                    dateTime = dateFormat.format(date);
                    Log.d("dateTime",dateTime);
                    endDate = dateTime;


                    date = format.parse(dtOn);
                    dateTime = dateFormat.format(date);
                    on = dateTime;



                }
                catch (ParseException e) {
                    e.printStackTrace();
                }


                fragment = EventDetails.newInstance(name,desc,startDate,endDate,
                        created,on,eventid,userid,eventstateid,eventconsid,usertype,representativeid,adminid,eventcount,eventloksabhaconstituency);
                if (fragment != null) {
                    FragmentManager fragmentManager = ((MlaHome)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //
                }

            }
        });




        textName.setText(dataSet.get(position).getEventname());

        Date date;
        String dtStart=dataSet.get(position).getEventstartdate();//"2017-11-23 15:10:25.0";
        String dtEnd=dataSet.get(position).getEventenddate();
        String create=dataSet.get(position).getCreateddate();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");

        try {
            date = format.parse(dtStart);//converting given string to date,parsing
            String dateTime = dateFormat.format(date); //date to new format and convert to String,formatting
            //Log.d("dateTime",dateTime);
            dtStart=dateTime;

            date = format.parse(dtEnd);
            dateTime = dateFormat.format(date);
            // Log.d("dateTime",dateTime);
            dtEnd=dateTime;

            date = format.parse(create);
            dateTime = dateFormat.format(date);
            create=dateTime;
            // Log.d("dateTime",dateTime);



        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        textStart.setText(Html.fromHtml(dtStart));
        textEnd.setText(dtEnd);
        //textCreate.setText(Html.fromHtml("Created by "+dataSet.get(position).getRepresentativename()+" on <b>"+create+"</b>"));


            String usertype="";
            usertype = dataSet.get(position).getUsertype();
            if(usertype.equalsIgnoreCase("admin")){

               // textCreate.setText(Html.fromHtml("CREATED BY: "+usertype));
                textCreate.setText(Html.fromHtml(context.getResources().getString(R.string.CREATED_BY)+"  "+"<font color='black'><font size='16dp'>"+usertype+"</font>"));

            }else {

                name = dataSet.get(position).getRepresentativename();

               // textCreate.setText(Html.fromHtml("CREATED BY: "+name));
                textCreate.setText(Html.fromHtml(context.getResources().getString(R.string.CREATED_BY)+"  "+"<font color='black'><font size='16dp'>"+name+"</font>"));









            }




        p=dataSet.get(position).getConstituencyname();
        if(p == null)
        {
           // textPublish.setText(Html.fromHtml("This Event publish in <b>"+all+"</b>"));

        }
        else
        {
//            textPublish.setText(Html.fromHtml("This Event publish in <b>"+String.valueOf(p)+"</b>"));
        }


        holder.card.setTag(dataSet.get(position).getEventid());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView textViewName;
        TextView textViewStart;
        TextView textViewCreate;
        TextView textViewPublish;
        TextView textViewEnd;
        CardView card;
        LinearLayout linear1;
        Button btnEdit;
        Button btnDelete;
        RelativeLayout relativeLayout;
        RelativeLayout btnviewdetails;
        // TextView textViewEvent;
        private AdapterView.OnItemClickListener itemClickListener;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.textViewName=(TextView)itemView.findViewById(R.id.EventName);
            this.textViewStart=(TextView)itemView.findViewById(R.id.Starting);
            this.card=(CardView)itemView.findViewById(R.id.info);
            this.textViewCreate=(TextView)itemView.findViewById(R.id.CreatedBy);
            this.btnEdit = itemView.findViewById(R.id.btneventedit);
            this.btnDelete = itemView.findViewById(R.id.btneventdelete);
            this.textViewPublish=(TextView)itemView.findViewById(R.id.PublishBy);
            this.relativeLayout = itemView.findViewById(R.id.relativebuttons);
            this.textViewEnd = (TextView)itemView.findViewById(R.id.end);
            this.btnviewdetails = (RelativeLayout) itemView.findViewById(R.id.btnviewdetails);
            // this.textViewEvent=(TextView)itemView.findViewById(R.id.EventViewName);

        }


        @Override
        public void onClick(View v)
        {

        }


    }
    public EventAdapter(ArrayList<DialstarPojo> data, Context context)
    {
        this.dataSet = data;
        this.context=context;
    }





}