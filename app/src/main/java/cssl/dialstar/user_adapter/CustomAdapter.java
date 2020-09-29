package cssl.dialstar.user_adapter;

import android.content.Context;
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
 * Created by cats on 23/12/17.
 */

public class CustomAdapter extends ArrayAdapter<Dialstar> {
    LayoutInflater flater;
    ArrayList<Dialstar> list;
    Context context;

    public CustomAdapter(Context context, int resouceId, int textviewId, ArrayList<Dialstar> list){
        super(context,resouceId,textviewId,list);


        this.context=context;


        this.list=list;
    }

    public void datachanged(ArrayList<Dialstar> list)
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
        TextView label=(TextView)row.findViewById(R.id.textView);


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


        return row;
    }

}
