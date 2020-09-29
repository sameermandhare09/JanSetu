package cssl.dialstar.user_adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.ItemClickListener;
import cssl.dialstar.R;
import cssl.dialstar.user_activity.UserDashboard;
import cssl.dialstar.user_fragment.EventDesc;
import cssl.dialstar.user_fragment.Events;
import cssl.dialstar.user_utils.Dialstar;
//import cssl.dialstar.user_fragment.EventDesc;

//import android.support.v4.app.Fragment;

/*
import user.dialstar.cssl.dialstaruser.ItemClickListener;
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_activity.EventDesc;
import user.dialstar.cssl.dialstaruser.user_fragment.Events;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by User on 21-Dec-17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {


    android.support.v4.app.Fragment fragment;


    public RecyclerViewClickListener mListener;
    public Context context;
    private ArrayList<Dialstar> dataSet;
    int id=0;

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
                .inflate(R.layout.event_card, parent, false);

        view.setOnClickListener(Events.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position)
    {


    /*    if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#F4F4F4"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }*/
        id=position+1;
        TextView textName=holder.textViewName;
        final TextView textStart=holder.textViewStart;
        TextView textCreate=holder.textViewCreate;
        TextView textEnd = holder.textViewEnd;
       // TextView textViewEvent=holder.textViewEvent;
        CardView card=holder.card;
        final LinearLayout linear=holder.linear1;
        TextView btnviewdetails = holder.btnviewdetails;



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
            Log.d("dateTime",dateTime);
            dtStart=dateTime;

            date = format.parse(dtEnd);
            dateTime = dateFormat.format(date);
            Log.d("dateTime",dateTime);
            dtEnd=dateTime;

            date = format.parse(create);
            dateTime = dateFormat.format(date);
            Log.d("dateTime",dateTime);
            create=dateTime;


        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        textStart.setText(dtStart);
        textEnd.setText(dtEnd);
        textCreate.setText(Html.fromHtml("<font color='black'><font size='16dp'>"+context.getResources().getString(R.string.CREATED_BY)+"    "+dataSet.get(position).getRepresentativename()+"</font>"));
        holder.card.setTag(dataSet.get(position).getEventid());

         btnviewdetails.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name=dataSet.get(position).getEventname();
                 String desc=dataSet.get(position).getDescription();
                 String startDate=dataSet.get(position).getEventstartdate();
                 String endDate=dataSet.get(position).getEventenddate();
                 String created=dataSet.get(position).getRepresentativename();
                 String on=dataSet.get(position).getCreateddate();
                 int eventid = dataSet.get(position).getEventid();
                 String startDatedateTime,endDatedateTime,ondateTime;

                 Date date;
                 String dtStart=startDate;//"2017-11-23 15:10:25.0";
                 String dtEnd=endDate;
                 String dtOn = on;
                 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");

                 try {
                     date = format.parse(dtStart);//converting given string to date,parsing
                     startDatedateTime = dateFormat.format(date); //date to new format and convert to String,formatting
                     Log.d("dateTime",startDatedateTime);


                     date = format.parse(dtEnd);
                     endDatedateTime = dateFormat.format(date);
                     Log.d("dateTime",endDatedateTime);



                     date = format.parse(dtOn);
                     ondateTime = dateFormat.format(date);

                     fragment =EventDesc.newInstance(name,desc,startDatedateTime,endDatedateTime,created,ondateTime,eventid);
                     // eventDesc.setArguments(bundle);

                     // fragment = new cssl.dialstar.user_fragment.EventDesc();
                     if (fragment != null) {
                         //FragmentManager fragmentManager = fragment.getFragmentManager();
                         FragmentManager fragmentManager = ((UserDashboard)context).getSupportFragmentManager();

                         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                         fragmentTransaction.replace(R.id.mainFrame, fragment);
                         fragmentTransaction.addToBackStack(null);
                         fragmentTransaction.commit();

                     }

                 }
                 catch (ParseException e) {
                     e.printStackTrace();
                 }


             }
         });
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
        CardView card;
        LinearLayout linear1;
        TextView btnviewdetails;
        TextView textViewEnd;
        android.support.v4.app.Fragment fragment;
       // TextView textViewEvent;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            this.textViewName=(TextView)itemView.findViewById(R.id.EventName);
            this.textViewStart=(TextView)itemView.findViewById(R.id.Starting);
            this.card=(CardView)itemView.findViewById(R.id.info);
            this.textViewCreate=(TextView)itemView.findViewById(R.id.CreatedBy);
            this.btnviewdetails = (TextView) itemView.findViewById(R.id.btnviewdetails);
            this.textViewEnd = (TextView)itemView.findViewById(R.id.end);
           // this.textViewEvent=(TextView)itemView.findViewById(R.id.EventViewName);

        }

        @Override
        public void onClick(View v)
        {

       /*     Intent intent = new Intent(context, EventDesc.class);
            context.startActivity(intent);*/
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener=itemClickListener;

        }

    }
    public EventAdapter(ArrayList<Dialstar> data, Context context)
    {
        this.dataSet = data;
        this.context=context;
    }



}
