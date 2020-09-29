package cssl.dialstar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by cats on 20/11/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Recycler> infoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView count;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count=(TextView) view.findViewById(R.id.count);
        }
    }


    public RecyclerViewAdapter(List<Recycler> infoList) {
        this.infoList = infoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Recycler transaction = infoList.get(position);
        holder.title.setText(transaction.getTitle());
        holder.count.setText(transaction.getCount());



    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }
}