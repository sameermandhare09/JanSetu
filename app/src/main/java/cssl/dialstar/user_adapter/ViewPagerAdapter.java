package cssl.dialstar.user_adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.Dialstar;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by cats on 21/12/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    public Context context;

    private ArrayList<Dialstar> dataSet;
    LayoutInflater inflater;
    NestedScrollView scrollview;
    SharedPreferences share;

    String namesharepref=null;

    String name=null;



    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);

    }
    public ViewPagerAdapter(Context context, ArrayList<Dialstar> data) {
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        TextView txttopic,txtcreatedby,txtcreateddate;
        final LinearLayout linear1;





        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.discussion_header, container,
                false);

        // Locate the TextViews in viewpager_item.xml

        share = PreferenceManager.getDefaultSharedPreferences(context);


        namesharepref = share.getString("name","");
        scrollview=(NestedScrollView) itemView.findViewById(R.id.scrollview);
        //scrollview.fullScroll(View.SCROLL_INDICATOR_BOTTOM);

        scrollview.scrollTo(0, scrollview.getBottom());

        scrollview.post(new Runnable() {
            @Override
            public void run() {
                //scrollview.fullScroll(View.FOCUS_DOWN);
                scrollview.scrollTo(0, scrollview.getBottom());

            }
        });
       /* scrollview.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(scrollview.FOCUS_DOWN);
            }
        },1);*/

        txttopic = (TextView) itemView.findViewById(R.id.topic); //text view with ID topic has to be replaced

        txttopic.setText((position+1)+"."+dataSet.get(position).getTopic()); //Extract name of topic from JSON File



        txtcreatedby = (TextView)itemView.findViewById(R.id.txtcreatedby);
        txtcreatedby.setText(context.getResources().getString(R.string.BY)+" "+dataSet.get(position).getRepresentativename());

        txtcreateddate = (TextView)itemView.findViewById(R.id.txtcreateddate);
        String createdate = dataSet.get(position).getCreateddate();
        Date date2;
        String dtStart2=createdate;//"2017-11-23 15:10:25.0";
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMM dd, yyyy");
        try {
            date2 = format2.parse(dtStart2);//converting given string to date,parsing
            String dateTime2 = dateFormat2.format(date2); //date to new format and convert to String,formatting
           // Log.d("dateTime",dateTime2);

            createdate=dateTime2;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


        txtcreateddate.setText(createdate);
        String jsonoptions=dataSet.get(position).getOption1(); //object of datatype String which will be used for extracting required data
        ArrayList<String> optionstr=new ArrayList<>();//object of Array List which will have the required data

        linear1=(LinearLayout) itemView.findViewById(R.id.layout2);

        try {
            JSONArray jsonArray=new JSONArray(jsonoptions);//Json array will have data to be converted in Strings
            for (int i=0;i<jsonArray.length();i++) //every object will be taken one by one to convert required elements from JSON to String
            {
                JSONObject jsonObject=jsonArray.getJSONObject(i); //accessing element of 1st object
                int representativeid =  jsonObject.getInt("representativeid");
                int userid =  jsonObject.getInt("userid");
                int mlaid = jsonObject.getInt("mlaid");

                if(representativeid>0){
                    name=jsonObject.getString("representativename");
                }else   if(userid>0){
                    name=jsonObject.getString("name");
                }else if(mlaid>0){

                    name=jsonObject.getString("mlaname");
                }


               // String name=jsonObject.getString("name");
                String date1=jsonObject.getString("createddate");

                Date date;
                String dtStart=date1;//"2017-11-23 15:10:25.0";
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

                try {
                    date = format.parse(dtStart);//converting given string to date,parsing
                    String dateTime = dateFormat.format(date); //date to new format and convert to String,formatting
                   // Log.d("dateTime",dateTime);

                    date1=dateTime;
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }

                String comment=jsonObject.getString("commenttext");
                Typeface typeface = ResourcesCompat.getFont(context, R.font.alegreyasans_regular);



                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setPadding(20,20,20,20);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,10,0,10);
                linearLayout.setLayoutParams(layoutParams);

                linearLayout.setBackground(context.getResources().getDrawable(R.drawable.discussionbackground));

                TextView txt=new TextView(context);
                txt.setText(comment);
                txt.setTextColor(context.getResources().getColor(R.color.black));
                txt.setTypeface(typeface);
                txt.setTextSize(15);
                txt.setPadding(0,0,0,5);
                linearLayout.addView(txt);

                RelativeLayout relativeLayout = new RelativeLayout(context);

                // Defining the RelativeLayout layout parameters.
                // In this case I want to fill its parent
             /*   RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.FILL_PARENT,
                        RelativeLayout.LayoutParams.FILL_PARENT);
*/


                // Creating a new TextView
                TextView tv = new TextView(context);
                tv.setText(context.getResources().getString(R.string.BY)+" "+name);
                tv.setTypeface(typeface);
                // Defining the layout parameters of the TextView
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);// Setting the parameters on the TextView
                tv.setLayoutParams(lp);
                relativeLayout.addView(tv);


                TextView txt2=new TextView(context);
                txt2.setText(date1);
                txt2.setTypeface(typeface);
                RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                txt2.setLayoutParams(lp1);
                // Adding the TextView to the RelativeLayout as a child
                relativeLayout.addView(txt2);




                linearLayout.addView(relativeLayout);
                linear1.addView(linearLayout);
                linear1.setPadding(0,10,0,10);

              /*  scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        scrollview.post(new Runnable() {
                            public void run() {
                                scrollview.fullScroll(View.FOCUS_DOWN);
                            }
                        });
                    }
                });*/
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }



        ((ViewPager) container).addView(itemView);
       /* scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scrollview.post(new Runnable() {
                    public void run() {
                        scrollview.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });*/
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }
}
