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


public class AddAdapter extends ArrayAdapter<Dialstar> {

    LayoutInflater flater;
    ArrayList<Dialstar> list;
    Activity context;

    public AddAdapter(Activity context, int resouceId, int textviewId,  ArrayList<Dialstar> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();

        this.context=context;
        this.list=list;
    }



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
        TextView label=(TextView)row.findViewById(R.id.textView123);
     //   label.setText(list.get(position).getGrievancename());


        /*if(list.get(position).getGrievancename()!=null) {
            label.setText(list.get(position).getGrievancename());
        }
        else*/ if(list.get(position).getPartyname()!=null && !list.get(position).getPartyname().isEmpty())
        {
            label.setText(list.get(position).getPartyname());
        }

        else if(list.get(position).getMlaname()!=null && !list.get(position).getMlaname().isEmpty())
        {
            label.setText(list.get(position).getMlaname());
        }


       else if(list.get(position).getRepresentativename()!=null && !list.get(position).getRepresentativename().isEmpty())
        {
            label.setText(list.get(position).getRepresentativename());
        }

        else if(list.get(position).getElectivename()!=null && !list.get(position).getElectivename().isEmpty())
        {
            label.setText(list.get(position).getElectivename());
        }
        else if(list.get(position).getNonelectivename()!=null && !list.get(position).getNonelectivename().isEmpty())
        {
            label.setText(list.get(position).getNonelectivename());
        }
        else if(list.get(position).getTypename()!=null && !list.get(position).getTypename().isEmpty())
        {
            label.setText(list.get(position).getTypename());
        }
        else
        {

        }


        return row;
    }
}