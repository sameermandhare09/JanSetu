package cssl.dialstar.searchappbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.support.v4.content.ContextCompat.startActivity;


/**
 * Created by cats on 10/1/18.
 */


public class searchadapter extends RecyclerView.Adapter<searchadapter.MyViewHolder> {
    Context context;
    private ArrayList<Dialstar> list;
    public ImageView Image;
    String Name="";
    String MobileNumber="";
    String Address="";
    String file="";
    int Userid=0;
    int Mlaid=0;
    int Representativeid=0;
    Config config = new Config();
    String File="";

    int postgrievancecount=0;
    int ackgrievancecount=0;
    int completedgrievancecount=0;
    int pendinggrievancecount=0;
    SharedPreferences share;
    SharedPreferences.Editor edit;

    ArrayList<String> data=new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Name,Address,MobileNumber,Profilename;


        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.username);
            Address = (TextView) view.findViewById(R.id.address);
            MobileNumber = (TextView) view.findViewById(R.id.mobile);
            Image=(ImageView)view.findViewById(R.id.image);
           Profilename=(TextView)view.findViewById(R.id.profilename);


        }
    }

    public searchadapter(ArrayList<Dialstar> List, Context  context ) {
        this.list =List;
        this.context = context;
    }
    @Override
    public searchadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_item, parent, false);

        share = PreferenceManager.getDefaultSharedPreferences(context);
        edit = share.edit();
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(searchadapter.MyViewHolder holder, final int position) {
        holder.Name.setText("Name:-"+list.get(position).getName());
        holder.MobileNumber.setText("Mobile Number:-"+list.get(position).getMobileno());
        holder.Address.setText("Address:-"+list.get(position).getAddress());



        final int mlaid = list.get(position).getMlaid();
        final int representativeid = (list.get(position).getRepresentativeid());
        final int userid = (list.get(position).getUserid());

for(int i=0;i<list.size();i++)
{
     file=list.get(position).getFile();
    Picasso.with(context)
            .load(file)
            .memoryPolicy(MemoryPolicy.NO_CACHE)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .placeholder(R.drawable.noimage)
            .into(Image);

}

        if(mlaid > 0 && representativeid==0 && userid==0)
        {
            holder.Profilename.setText("Registered by:-"+"MLA");
        }
        if(mlaid == 0 && representativeid > 0 && userid==0)
        {
            holder.Profilename.setText("Registered by:-"+"Representative");
        }
        if(mlaid == 0 && representativeid ==0  && userid > 0)
        {
            holder.Profilename.setText("Registered by:-"+"User");
        }

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Userid=list.get(position).getUserid();
        Mlaid=list.get(position).getMlaid();
        Representativeid=list.get(position).getRepresentativeid();
        JSONObject jsonObject = new JSONObject();
       // String usertype = list.get(position).getUsertype();

        //Log.e("Userid", String.valueOf(Userid));
       // Log.e("Mlaid", String.valueOf(Mlaid));
       // Log.e("Representativeid", String.valueOf(Representativeid));
       // Log.e("usertype",usertype);


        if(Mlaid>0){
            try {
                jsonObject.put("userid",Mlaid);
                jsonObject.put("usertype", "mla");
                new HttpRequestTask2(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(Representativeid>0){
            try {
                jsonObject.put("userid",Representativeid);
                jsonObject.put("usertype", "representative");
                new HttpRequestTask2(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if(Userid>0){
            try {
                jsonObject.put("userid",Userid);
                jsonObject.put("usertype", "user");
                new HttpRequestTask2(jsonObject.toString()).execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        Name=list.get(position).getName();
        MobileNumber=list.get(position).getMobileno();
        Address=list.get(position).getAddress();

        for(int i=0;i<list.size();i++)
        {
            File =list.get(position).getFile();
            Picasso.with(context)
                    .load(File)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .placeholder(R.drawable.noimage)
                    .into(Image);

        }



    /*    Intent j=new Intent(context, History.class);
        j.putExtra("name",Name);
        j.putExtra("mobile",MobileNumber);
        j.putExtra("address",Address);
        j.putExtra("file",File);
        j.putExtra("postgrievance",postgrievancecount);
        j.putExtra("ackgrievance",ackgrievancecount);
        j.putExtra("completedgrievance",completedgrievancecount);
        j.putExtra("pendinggrievance",pendinggrievancecount);


        ((Activity) context).startActivityForResult(j, 1);*/


    }
});
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // History....

    private class HttpRequestTask2 extends AsyncTask<String, Void, String> {
        String jsonstr;

        public HttpRequestTask2(String jsonstr) {
            this.jsonstr = jsonstr;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... arg0) {

            try {

                //String url = config.getGetAllGrievaneCount();
                String url = config.representativeremoteurl+"admin/app/getAllGrievaneCount/";

                //"http://182.18.163.201:8099/admin/app/getAllGrievaneCount";
                Log.i("url", url);
                MediaType JSON = MediaType.parse("application/json");

                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(JSON, jsonstr);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("content-type", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                String resp = response.message().toString();
                String respbody = response.body().string().toString();
                return respbody;
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
           // Log.i("History :", result);
/*
            {"ackgrievancecount":0,"pendinggrievancecount":1,"postgrievancecount":1,"completedgrievancecount":0}
*/
JSONObject jsonObject;
            try {
               jsonObject=new JSONObject(result);
               postgrievancecount=jsonObject.getInt("postgrievancecount");
               ackgrievancecount=jsonObject.getInt("ackgrievancecount");
               pendinggrievancecount=jsonObject.getInt("pendinggrievancecount");
               completedgrievancecount=jsonObject.getInt("completedgrievancecount");
               //Log.i("post",postgrievancecount+","+ackgrievancecount+","+pendinggrievancecount+","+completedgrievancecount);


             /*  Intent intent=new Intent(context,History.class);
                intent.putExtra("postgrievance",postgrievancecount);
                intent.putExtra("ackgrievance",ackgrievancecount);
                intent.putExtra("completedgrievance",completedgrievancecount);
                intent.putExtra("pendinggrievance",pendinggrievancecount);
                context.startActivity(intent);


*/


                Intent j=new Intent(context, History.class);
                j.putExtra("name",Name);
                j.putExtra("mobile",MobileNumber);
                j.putExtra("address",Address);
                j.putExtra("file",File);
                j.putExtra("postgrievance",postgrievancecount);
                j.putExtra("ackgrievance",ackgrievancecount);
                j.putExtra("completedgrievance",completedgrievancecount);
                j.putExtra("pendinggrievance",pendinggrievancecount);


                //((Activity) context).startActivityForResult(j, 1);
                startActivity(context,j,null);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }




}





