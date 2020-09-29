package cssl.dialstar.user_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cssl.dialstar.R;
import cssl.dialstar.user_fragment.News;
import cssl.dialstar.user_utils.ConfigUser;
/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_fragment.News;
import user.dialstar.cssl.dialstaruser.user_utils.Config;
*/


public class ScrollingActivity extends AppCompatActivity {
    public Context context;
    ConfigUser config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("NewsName");
        String desp=bundle.getString("Description");
        String date=bundle.getString("Date");
        int news=bundle.getInt("News");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ImageView imageView=(ImageView)findViewById(R.id.imageView) ;
        TextView scrollData=(TextView) findViewById(R.id.scrollData);
        TextView dateView=(TextView)findViewById(R.id.dateView);
        ViewPager mPager;
        config=new ConfigUser();
        scrollData.setText(desp);
        dateView.setText(date);
        //Picasso.with(context).load(config.getNewsphoto()+news+".png").into(imageView);
        Picasso.with(context).load(config.userremoteurl1+"BJPJanhit/uploads/newsphoto/"+news+".png").into(imageView);

        //getsu


        /*
        FloatingActionButton fab = (FloatingActionButton)
findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

