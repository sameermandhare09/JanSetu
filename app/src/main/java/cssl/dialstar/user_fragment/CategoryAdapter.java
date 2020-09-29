package cssl.dialstar.user_fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.user_utils.CategoryPojo;

class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder>{
    Context context;
    ArrayList<CategoryPojo> imagelist;

    int selectedPosition=-1;
    int categoryid=0;

    public CategoryAdapter(Context demoRecyclerView, ArrayList<CategoryPojo> imagelist) {

        this.context = demoRecyclerView;
        this.imagelist = imagelist;

    }
    public CategoryAdapter(Context demoRecyclerView, ArrayList<CategoryPojo> imagelist,int selectedPosition) {

        this.context = demoRecyclerView;
        this.imagelist = imagelist;
        this.categoryid = selectedPosition;

    }
    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.categorylist,parent,false);
        MyViewHolder myViewHolder = new CategoryAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder, final int position) {
        TextView textView = holder.textView;
        ImageView imageView = holder.imageView;

        imageView.setImageResource(imagelist.get(position).getId());
        textView.setText(imagelist.get(position).getName());

        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.highlighted_text_user));
        else if(categoryid==imagelist.get(position).getCategoryid())
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.highlighted_text_user));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryid=0;
                selectedPosition=position;
                Add.id = imagelist.get(position).getCategoryid();
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return imagelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;




        public MyViewHolder(final View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.idtxtview);
            imageView = itemView.findViewById(R.id.idimageview);


     /*       itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // itemView.setBackgroundColor(context.getResources().getColor(R.color.highlighted_text_user));


                }
            });
*/


            //imageViewIcon = itemView.findViewById(R.id.imageView);

        }
    }


}
