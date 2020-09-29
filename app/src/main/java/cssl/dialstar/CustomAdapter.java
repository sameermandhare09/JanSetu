package cssl.dialstar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.representative_util.DialstarPojo;


public class CustomAdapter extends ArrayAdapter<DialstarPojo> {

    LayoutInflater flater;
    ArrayList<DialstarPojo> list;
    Context context;



    public CustomAdapter(Context context, int resouceId, int textviewId, ArrayList<DialstarPojo> list){
        super(context,resouceId,textviewId,list);


        this.context=context;


        this.list=list;
    }



    public void datachanged(ArrayList<DialstarPojo> list)
    {
        this.list=list;
        notifyDataSetChanged();

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

@Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
       // this.list=dialstarPojos;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View row=LayoutInflater.from(parent.getContext()).inflate(R.layout.customspinner, parent,false);
        TextView label=(TextView)row.findViewById(R.id.textView123);


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

        else if(list.get(position).getTypename()!=null && !list.get(position).getTypename().isEmpty())
        {
            label.setText(list.get(position).getTypename());
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




        return row;
    }
}