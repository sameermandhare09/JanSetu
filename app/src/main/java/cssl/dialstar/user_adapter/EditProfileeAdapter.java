package cssl.dialstar.user_adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.Dialstar;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_utils.Dialstar;
*/

/**
 * Created by cats on 29/12/17.
 */

public class EditProfileeAdapter extends ArrayAdapter<Dialstar> {
    LayoutInflater flater;
    //List<MyPojo> list;
    ArrayList<Dialstar> list;
    Activity context;

    //Fragment fragment;


    public EditProfileeAdapter(Activity context, int resouceId, int textviewId, ArrayList<Dialstar> list){

        super(context,resouceId,textviewId,list);
        this.context=context;
        flater = context.getLayoutInflater();

        this.list=list;
    }
  /*  public EditProfileAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<MyPojo> objects, LayoutInflater flater, List<MyPojo> list, Activity context1) {
        super(context, resource, textViewResourceId, objects);
        this.flater = flater;
        this.list = list;
        this.context = context1;
    }*/






    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row=flater.inflate(R.layout.customspinner, parent, false);
        TextView label=(TextView)row.findViewById(R.id.textView);



        if(list.size()>position){


         if(list.get(position).getCountryname()!=null && !list.get(position).getCountryname().isEmpty()) {
            label.setText(list.get(position).getCountryname());

        }
        else if(list.get(position).getStatename()!=null && !list.get(position).getStatename().isEmpty())
        {
            label.setText(list.get(position).getStatename());
        }

        else if(list.get(position).getCityname()!=null && !list.get(position).getCityname().isEmpty())
        {
            label.setText(list.get(position).getCityname());
        }


        else if(list.get(position).getPartyname()!=null && !list.get(position).getPartyname().isEmpty())
        {
            label.setText(list.get(position).getPartyname());
        }


        else if(list.get(position).getGrievancename()!=null && !list.get(position).getGrievancename().isEmpty())
        {
            label.setText(list.get(position).getGrievancename());
        }

        else if(list.get(position).getRepresentativename()!=null && !list.get(position).getRepresentativename().isEmpty())
        {
            label.setText(list.get(position).getRepresentativename());
        }
        else if(list.get(position).getMlaname()!=null && !list.get(position).getMlaname().isEmpty())
        {
            label.setText(list.get(position).getMlaname());
        }

        else if(list.get(position).getPincode()!=null && !list.get(position).getPincode().isEmpty())
        {
            label.setText(list.get(position).getPincode());
        }

        else if(list.get(position).getConstituencyname()!=null && !list.get(position).getConstituencyname().isEmpty())
        {
            label.setText(list.get(position).getConstituencyname());
        }

        else if(list.get(position).getWardname()!=null && !list.get(position).getWardname().isEmpty())
        {
            label.setText(list.get(position).getWardname());
        }

        }

        return row;
    }
}
