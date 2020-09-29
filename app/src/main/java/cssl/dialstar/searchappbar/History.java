package cssl.dialstar.searchappbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import cssl.dialstar.R;

public class History extends AppCompatActivity {
    LinearLayout layout1;
    TextView txt;
    ImageView image;
    String Name="";
    String Mobile="";
    String Address="";
    String File="";
    int postgrievance=0;
    int ackgrievance=0;
    int completedgrievance=0;
    int pendinggrievance=0;
    TextView username;
    TextView mobile;
    TextView address;
    TextView ack;
    TextView pending;
    TextView completed;
    TextView post;
    SharedPreferences share;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);




        share = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = share.edit();

        post=(TextView)findViewById(R.id.post);
        username=(TextView) findViewById(R.id.username);
        mobile=(TextView) findViewById(R.id.mobile);
        address=(TextView) findViewById(R.id.address);

        ack=(TextView) findViewById(R.id.ack);
        pending=(TextView) findViewById(R.id.pending);
        completed=(TextView) findViewById(R.id.completed);
        image=(ImageView)  findViewById(R.id.image);



        Bundle b=getIntent().getExtras();
        Name=b.getString("name");
        Mobile=b.getString("mobile");
        Address=b.getString("address");
        File=b.getString("file");
        toolbar.setTitle(Name);
        //setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

/*
if(postgrievance!=null)
{
    post.setText("Total Grievance Post:-"+postgrievance);
}
else
{
    post.setVisibility(View.GONE);
}
*/
        postgrievance=b.getInt("postgrievance");
         ackgrievance=b.getInt("ackgrievance");
         completedgrievance=b.getInt("completedgrievance");
         pendinggrievance=b.getInt("pendinggrievance");


        username.setText(Name);
        mobile.setText(Mobile);
        address.setText(Address);



        post.setText(String.valueOf(postgrievance));
        ack.setText(String.valueOf(ackgrievance));
        pending.setText(String.valueOf(pendinggrievance));
        completed.setText(String.valueOf(completedgrievance));


        Picasso.with(getApplicationContext())
                .load(File)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.noimage)
                .into(image);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

       /*         Intent intent = new Intent(this, UserDashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);*/
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
