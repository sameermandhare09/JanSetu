package cssl.dialstar.representative_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.Grievance;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;

/**
 * Created by sameer on 1/12/17.
 */

public class IssueViewAdapter extends RecyclerView.Adapter<IssueViewAdapter.MyViewHolder> {

    //ArrayList<IssueDataModel> issueDataModels;
    ArrayList<DialstarPojo> dialstarPojos;
    Context context;
    Fragment fragment = new Fragment();
    Config config=new Config();

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textViewIssueName;
        TextView textViewIssueNumber;
        ImageView imageViewIcon;
       // CircleImageView circleImageView;
        ImageView circleImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewIssueName = itemView.findViewById(R.id.textViewName);
            textViewIssueNumber = itemView.findViewById(R.id.textViewVersion);
            //imageViewIcon = itemView.findViewById(R.id.imageView);
            circleImageView = itemView.findViewById(R.id.imageView);

        }
    }

    public IssueViewAdapter(ArrayList<DialstarPojo>dataModels, Context context){
       // issueDataModels = dataModels;
        dialstarPojos = dataModels;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        TextView textViewIssueName = holder.textViewIssueName;
        TextView textViewIssueNumber = holder.textViewIssueNumber;
        //ImageView imageViewIcon = holder.imageViewIcon;
    //    CircleImageView circleImageView = holder.circleImageView;
       ImageView circleImageView = holder.circleImageView;

      /*  textViewIssueName.setText(issueDataModels.get(position).getName());
        textViewIssueNumber.setText(issueDataModels.get(position).getIssuenumber()+"");
*/

        textViewIssueName.setText(dialstarPojos.get(position).getGrievancename());
        textViewIssueNumber.setText(dialstarPojos.get(position).getCount()+"");

        Picasso.with(context)
                .load(config.representativeremoteurl1+"BJPJanhit/uploads/grievancecategory/"+dialstarPojos.get(position).getGrievancecategoryid()+".png")
              //  .load(config.getGrievancecategory()+dialstarPojos.get(position).getGrievancecategoryid()+".png")
                .error(R.drawable.noimage)
                .into(circleImageView);
       /* Log.d("imageurl",dialstarPojos.get(position).getFile());
        Log.d("createdimageurl","http://192.168.1.24/dialstar/uploads/grievancecategory/"+dialstarPojos.get(position).getGrievancecategoryid()+".png");
*/
     //  String icon_path=config.getGrievancecategory()+dialstarPojos.get(position).getGrievancecategoryid()+".png";
        String icon_path=config.representativeremoteurl1+"BJPJanhit/uploads/grievancecategory/"+dialstarPojos.get(position).getGrievancecategoryid()+".png";
        //Log.d("grievance icon",icon_path);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               int grievancecategoryid = dialstarPojos.get(position).getGrievancecategoryid();
              // Log.d("grievancecategoryid on issuew adapter",grievancecategoryid+"");
               fragment = Grievance.newInstance(grievancecategoryid,0,0,"");
               if (fragment != null) {
                   FragmentManager fragmentManager =  ((MlaHome)context).getSupportFragmentManager();
                   FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                   fragmentTransaction.replace(R.id.fragment_container, fragment);
                   fragmentTransaction.addToBackStack(null);

                   fragmentTransaction.commit();
                   //
               }


           }
       });

    }


    @Override
    public int getItemCount() {
        return dialstarPojos.size();
    }
}
