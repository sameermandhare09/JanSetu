package cssl.dialstar.user_adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.Dialstar;
/*

import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by cats on 13/12/17.
 */

public class SurveyAdapter extends PagerAdapter {

    Context context;

    String reason="";
    String Pollid;
    String answer="";
    String user="";
    ArrayList<String> radio;
    SharedPreferences share;
    SharedPreferences.Editor edit;
    int userid=0;
    TextView txtpoll;
    TextView txtsubject;
    LinearLayout linear1;
    int   constituencyid=0;




    LayoutInflater inflater;
ArrayList<Dialstar> list;
    public SurveyAdapter(ArrayList<Dialstar> List, Context  context) {

        this.context = context;
        this.list=List;


    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {





        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = inflater.inflate(R.layout.survey_item, container,
                false);

        share = PreferenceManager.getDefaultSharedPreferences(context);
        edit=share.edit();
        constituencyid = share.getInt("constituencyid",0);

        //Log.i("message",reason);
        // Locate the TextViews in viewpager_item.xml
        txtpoll = (TextView) itemView.findViewById(R.id.poll);
        txtsubject = (TextView) itemView.findViewById(R.id.subject);
        linear1=(LinearLayout) itemView.findViewById(R.id.linear1);



      //  if(constituencyid == list.get(position).getConstituencyid()){
            String op1=list.get(position).getOption1();
            String op2=list.get(position).getOption2();
            String op3=list.get(position).getOption3();
            String op4=list.get(position).getOption4();
            String op5=list.get(position).getOption5();



            RadioButton rb;
            final RadioGroup rg = new RadioGroup(context);


            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    answer="";
                    View radioButton = radioGroup.findViewById(i);
                    int index = radioGroup.indexOfChild(radioButton);
                    answer=radioButton.getTag().toString();
                    //list.get(position).setAnswer(answer);
                   // Log.i("selectedindex",index+""+radioButton.getTag().toString());
                }
            });
            rg.setOrientation(RadioGroup.VERTICAL);

        Typeface typeface = ResourcesCompat.getFont(context, R.font.alegreyasans_regular);

        if(!op1.equalsIgnoreCase("")) {
                context.setTheme(R.style.Radiobutton);
                rb = new RadioButton(context);
                rb.setText(op1);
                rb.setTextSize(17);
                rb.setTag(op1);
                rb.setTypeface(typeface);
                rg.addView(rb);


            }
            if(!op2.equalsIgnoreCase("")) {

                rb = new RadioButton(context);
                rb.setText(op2);
                rb.setTag(op2);
                rb.setTextSize(17);
                rb.setTypeface(typeface);
                rg.addView(rb);

            }
            if(!op3.equalsIgnoreCase("")) {
                rb = new RadioButton(context);
                rb.setText(op3);
                rb.setTag(op3);
                rb.setTextSize(17);
                rb.setTypeface(typeface);
                rg.addView(rb);

            }
            if(!op4.equalsIgnoreCase("")) {
                rb = new RadioButton(context);
                rb.setText(op4);
                rb.setTag(op4);
                rb.setTextSize(17);
                rb.setTypeface(typeface);
                rg.addView(rb);

            }
            if(!op5.equalsIgnoreCase("")) {
                rb = new RadioButton(context);
                rb.setText(op5);
                rb.setTag(op5);
                rb.setTextSize(17);
                rb.setTypeface(typeface);
                rg.addView(rb);

            }




            // Capture position and set to the TextViews

            txtpoll.setText("Poll Id:-"+(position+1));
            txtsubject.setText(list.get(position).getSubject());

            linear1.addView(rg);
            // Add viewpager_item.xml to ViewPager
            ((ViewPager) container).addView(itemView);


        //}

        return itemView;
    }
    public String returnanswer()
    {
        return answer;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);

    }

}
