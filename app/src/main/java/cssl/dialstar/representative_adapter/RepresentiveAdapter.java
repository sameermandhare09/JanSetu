package cssl.dialstar.representative_adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.representative_activity.MlaHome;
import cssl.dialstar.representative_fragment.Grievance;
import cssl.dialstar.representative_util.Config;
import cssl.dialstar.representative_util.DialstarPojo;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sameer on 2/12/17.
 */

public class RepresentiveAdapter extends RecyclerView.Adapter<RepresentiveAdapter.MyViewHolder>  {



    Context context;
    ArrayList<DialstarPojo>dialstarPojos;
    Config config = new Config();


    int representativeid = 0;
    String representative="";
    Fragment fragment = new Fragment();

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtviewrepname;
        TextView txtviewrepwardno;
        TextView txtviewissueno;
       // ImageView imgviewrepimage;
       CircleImageView imgviewrepimage;

        public MyViewHolder(View itemView) {
            super(itemView);

            txtviewrepname = itemView.findViewById(R.id.textViewRepName);
            txtviewrepwardno = itemView.findViewById(R.id.textViewWardNo);
            txtviewissueno = itemView.findViewById(R.id.textViewIssue);
            imgviewrepimage = itemView.findViewById(R.id.imageViewRepImage);

        }
    }
    public RepresentiveAdapter(ArrayList<DialstarPojo> representativeDataModels,Context context){

        dialstarPojos = representativeDataModels;
        this.context = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reprentative,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TextView txtviewName = holder.txtviewrepname;
        TextView txtviewWardNo = holder.txtviewrepwardno;
        TextView txtviewissueNo = holder.txtviewissueno;
        CircleImageView imageViewRepPro = holder.imgviewrepimage;


        txtviewName.setText(dialstarPojos.get(position).getName());
        txtviewWardNo.setText(context.getResources().getString(R.string.Ward_Name)+": "+dialstarPojos.get(position).getWard()+"");
        txtviewissueNo.setText(context.getResources().getString(R.string.Total_Issues)+dialstarPojos.get(position).getCount()+"");


        Picasso.with(context)
               // .load(config.getRepresentativeprofilephoto()+dialstarPojos.get(position).getRepresentativeid()+".png")
                .load(config.representativeremoteurl1+"BJPJanhit/uploads/representativeprofilephoto/"+dialstarPojos.get(position).getRepresentativeid()+".png")

                .error(R.drawable.noimage)
                .into(imageViewRepPro);
        //Log.d("Representativeimageurl",dialstarPojos.get(position).getFile());
       // Log.d("RepresentativeCreated",config.getRepresentativeprofilephoto()+dialstarPojos.get(position).getRepresentativeid()+".png");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
           /* Representative  representative = new Representative();
            Dashboard dashboard = new Dashboard();
            MlaHome mlaHome = new MlaHome();*/
            @Override
            public void onClick(View view) {
               // Toast.makeText(context,"pos:- "+position,Toast.LENGTH_SHORT).show();
                 representativeid = dialstarPojos.get(position).getRepresentativeid();
                 representative = dialstarPojos.get(position).getName();

               // Log.d("representative name",representative);

                fragment = Grievance.newInstance(0,representativeid,1,representative);
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
