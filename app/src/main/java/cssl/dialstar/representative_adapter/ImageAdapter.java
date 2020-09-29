package cssl.dialstar.representative_adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.fullscreenimage.PhotoFullPopupWindow;
import cssl.dialstar.user_activity.ImageModel;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder>  {

    Context context;
    ArrayList<ImageModel> imageModelArrayList;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageModelArrayList) {


        this.context = context;
        this.imageModelArrayList = imageModelArrayList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagecardview,parent,false);
       MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ImageView imageView;
        imageView = holder.imageView;
        Picasso.with(context).invalidate(imageModelArrayList.get(position).getImage());
        Picasso.with(context)
                .load(imageModelArrayList.get(position).getImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imageurl = imageModelArrayList.get(position).getImage();
                new PhotoFullPopupWindow(context, R.layout.popup_photo_full, view, imageurl, null);


  /*              String imageurl = imageModelArrayList.get(position).getImage();
                Intent intent = new Intent(context, FullImage.class);
                intent.putExtra("ImageKey",imageurl);

                context.startActivity(intent);*/


            }
        });


    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.cardimage);
        }
    }

}
