package cssl.dialstar.user_adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.ItemClickListener;
import cssl.dialstar.R;
import cssl.dialstar.user_activity.ScrollingActivity;
import cssl.dialstar.user_fragment.Newss;
import cssl.dialstar.user_utils.ConfigUser;
import cssl.dialstar.user_utils.Dialstar;
/*
import user.dialstar.cssl.dialstaruser.ItemClickListener;
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_activity.ScrollingActivity;
import user.dialstar.cssl.dialstaruser.user_fragment.Newss;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by cats on 21/12/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    public RecyclerViewClickListener mListener;
    NewsAdapter(RecyclerViewClickListener listener)
    {
        mListener = listener;
    }

    public interface RecyclerViewClickListener
    {

        void onClick(View view, int position);
    }



    public Context context;

    ConfigUser config;

    private ArrayList<Dialstar> dataSet;


    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ItemClickListener itemClickListener;
        TextView textViewName;
        TextView textViewVersion;
        ImageView imageViewIcon;
        Button button1;
        CardView card;


        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(context, ScrollingActivity.class);
            context.startActivity(intent);
//itemClickListener.onClick(v,getAdapterPosition(),false);
        }

        public boolean onLongClick(View v)
        {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener=itemClickListener;

        }
        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView)
                    itemView.findViewById(R.id.textViewName);
            this.textViewVersion = (TextView)
                    itemView.findViewById(R.id.textViewVersion);
            this.imageViewIcon = (ImageView)
                    itemView.findViewById(R.id.imageView);

            this.card=(CardView)itemView.findViewById(R.id.info);
        }


    }

    public NewsAdapter(ArrayList<Dialstar> data, Context context) {
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);

       // myOnClickListener
        view.setOnClickListener(Newss.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;


    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int
            listPosition) {

        TextView textViewName = holder.textViewName;
        TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;

        final int news=dataSet.get(listPosition).getNewsid();

        textViewName.setText(dataSet.get(listPosition).getNewtitle());
        textViewVersion.setText(dataSet.get(listPosition).getNewtext());

        config=new ConfigUser();

        // imageView.setImageResource(dataSet.get(listPosition).getImage());
      //  Picasso.with(context).load(config.getNewsphoto()+news+".png").into(imageView);
        Picasso.with(context).load(config.userremoteurl1+"BJPJanhit/uploads/newsphoto/"+news+".png").into(imageView);

        holder.card.setTag(dataSet.get(listPosition).getName());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                Date date2 = null;



                Intent intent=new Intent(context,ScrollingActivity.class);
                String name=dataSet.get(listPosition).getNewtitle();
                intent.putExtra("News",news);
                intent.putExtra("NewsName",name);
                String desc=dataSet.get(listPosition).getNewtext();
                intent.putExtra("Description",desc);
                String date1=dataSet.get(listPosition).getCreateddate();
                Date date;
                String dtStart=date1;//"2017-11-23 15:10:25.0";
                SimpleDateFormat format = new
                        SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new
                        SimpleDateFormat("dd-MMM-yyyy hh:mm a");

                try {
                    date = format.parse(dtStart);
                    String dateTime = dateFormat.format(date);
                   // Log.d("dateTime",dateTime);

                    intent.putExtra("Date",dateTime);
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
