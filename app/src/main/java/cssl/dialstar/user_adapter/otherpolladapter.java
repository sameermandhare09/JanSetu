package cssl.dialstar.user_adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.Dialstar;

public class otherpolladapter extends RecyclerView.Adapter<otherpolladapter.MyViewHolder> {
    public ArrayList<Dialstar> dialstarPojos;
    public Context context;
    public LinearLayout linear1;
    public LinearLayout linear2;
    public LinearLayout linear3;
   public ProgressBar progressBar;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtsubject;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtsubject=(TextView) itemView.findViewById(R.id.txtsubject);
            linear1=(LinearLayout) itemView.findViewById(R.id.linear1);
            linear2=(LinearLayout) itemView.findViewById(R.id.linear2);
            linear3=(LinearLayout) itemView.findViewById(R.id.linear3);
        }
    }

    public otherpolladapter(Context context, ArrayList<Dialstar> dialstarPojos) {
        this.context=context;
        this.dialstarPojos=dialstarPojos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.otherpolls, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
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


       holder.txtsubject.setText(Html.fromHtml(dialstarPojos.get(position).getSubject()));

        double totalPercentage = 0.0;

        String op1=dialstarPojos.get(position).getOption1();
        String op2=dialstarPojos.get(position).getOption2();
        String op3=dialstarPojos.get(position).getOption3();
        String op4=dialstarPojos.get(position).getOption4();
        String op5=dialstarPojos.get(position).getOption5();

        int op1count=dialstarPojos.get(position).getAckgrievancecount();
        int op2count=dialstarPojos.get(position).getAcknowledgeby();
        int op3count=dialstarPojos.get(position).getAdminid();
        int op4count=dialstarPojos.get(position).getBoothid();
        int op5count=dialstarPojos.get(position).getCategoryid();

        int totalcount=dialstarPojos.get(position).getCount();

        if(!op1.equalsIgnoreCase(""))
        {
            totalPercentage = (100.0*op1count)/totalcount;
            TextView t1 = new TextView(context);
            t1.setText(Html.fromHtml(op1));
            t1.setTextSize(16);
          //  t1.setAllCaps(true);

            t1.setTag(op1);
            linear1.addView(t1);
            progressBar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#7AC943"), PorterDuff.Mode.SRC_IN);
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 7, 0, 5);
            progressBar.setScaleY(1.3f);

            progressBar.setLayoutParams(buttonLayoutParams);
            progressBar.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
            linear3.addView(progressBar);
            TextView t2 = new TextView(context);
            t2.setText(Math.round(totalPercentage)+"%");
            t2.setTextSize(16);
            t2.setTag(totalPercentage);
            linear2.addView(t2);

        }
        if(!op2.equalsIgnoreCase(""))
        {
            totalPercentage = (100.0*op2count)/totalcount;
            TextView t3 = new TextView(context);
            t3.setText(op2);
           // t3.setAllCaps(true);
            t3.setTextSize(16);
            t3.setTag(op2);
            linear1.addView(t3);
            progressBar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.SRC_IN);
            //   progressBar.setProgressDrawable(context.getResources().getDrawable(R.color.nobar));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 9, 0, 5);
            progressBar.setLayoutParams(buttonLayoutParams);
            progressBar.setScaleY(1.3f);
            progressBar.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
            linear3.addView(progressBar);
            TextView t4 = new TextView(context);
            t4.setText(Math.round(totalPercentage)+"%");
            t4.setTextSize(16);
            t4.setTag(totalPercentage);
            linear2.addView(t4);
        }

        if(!op3.equalsIgnoreCase(""))
        {
            totalPercentage = (100.0*op3count)/totalcount;
            TextView t5 = new TextView(context);
            t5.setText(op3);
            //t5.setAllCaps(true);
            t5.setTextSize(16);
            t5.setTag(op2);
            linear1.addView(t5);
            progressBar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
            progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#BDCCD4"), PorterDuff.Mode.SRC_IN);
            //   progressBar.setProgressDrawable(context.getResources().getDrawable(R.color.nobar));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 9, 0, 5);
            progressBar.setLayoutParams(buttonLayoutParams);
            progressBar.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
            progressBar.setScaleY(1.3f);
            linear3.addView(progressBar);
            TextView t6 = new TextView(context);
            t6.setText(Math.round(totalPercentage)+"%");
            t6.setTextSize(16);
            t6.setTag(totalPercentage);
            linear2.addView(t6);
        }

        if(!op4.equalsIgnoreCase(""))
        {
            totalPercentage = (100.0*op4count)/totalcount;
            TextView t7 = new TextView(context);
            t7.setText(op4);
            //t7.setAllCaps(true);
            t7.setTextSize(16);
            t7.setTag(op4);
            linear1.addView(t7);
            progressBar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
           // progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#BDCCD4"), PorterDuff.Mode.SRC_IN);
            //   progressBar.setProgressDrawable(context.getResources().getDrawable(R.color.nobar));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 9, 0, 5);
            progressBar.setLayoutParams(buttonLayoutParams);
            progressBar.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
            progressBar.setScaleY(1.3f);
            linear3.addView(progressBar);
            TextView t8 = new TextView(context);
            t8.setText(Math.round(totalPercentage)+"%");
            t8.setTextSize(16);
            t8.setTag(totalPercentage);
            linear2.addView(t8);
        }

        if(!op5.equalsIgnoreCase(""))
        {
            totalPercentage = (100.0*op5count)/totalcount;
            TextView t9 = new TextView(context);
            t9.setText(op5);
            //t9.setAllCaps(true);
            t9.setTextSize(16);
            t9.setTag(op5);
            linear1.addView(t9);
            progressBar = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
       //     progressBar.getProgressDrawable().setColorFilter(Color.parseColor("#BDCCD4"), PorterDuff.Mode.SRC_IN);
            //   progressBar.setProgressDrawable(context.getResources().getDrawable(R.color.nobar));
            LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            buttonLayoutParams.setMargins(0, 9, 0, 5);
            progressBar.setLayoutParams(buttonLayoutParams);
            progressBar.setProgress(Integer.parseInt(String.valueOf(Math.round(totalPercentage))));
            progressBar.setScaleY(1.3f);
            linear3.addView(progressBar);
            TextView t10 = new TextView(context);
            t10.setText(Math.round(totalPercentage)+"%");
            t10.setTextSize(16);
            t10.setTag(totalPercentage);
            linear2.addView(t10);
        }

    }

    @Override
    public int getItemCount() {
        return dialstarPojos.size();
    }


}
