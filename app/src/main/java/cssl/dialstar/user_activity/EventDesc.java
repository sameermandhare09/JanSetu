package cssl.dialstar.user_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import cssl.dialstar.R;
import cssl.dialstar.user_fragment.Events;

/*
import user.dialstar.cssl.dialstaruser.R;
import user.dialstar.cssl.dialstaruser.user_fragment.Events;
*/

/**
 * Created by cats on 22/12/17.
 */
public class EventDesc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_desc);



        Bundle bundle=getIntent().getExtras();
        String name=bundle.getString("DescName");
        String desc=bundle.getString("Description");
        String startDate=bundle.getString("StartDate");
        String endDate=bundle.getString("EndDate");
        String create=bundle.getString("Created");
        String on=bundle.getString("On");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  toolbar.setTitle(name);

//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textViewName=(TextView)findViewById(R.id.EventName);
        TextView textViewStart=(TextView)findViewById(R.id.Start);
        TextView textViewDesp=(TextView)findViewById(R.id.desp);
        TextView textViewCreate=(TextView)findViewById(R.id.created);



        textViewDesp.setText(desc);
        textViewName.setText(name);
        textViewStart.setText(Html.fromHtml("Starting from <b>"+startDate+"</b> to <b> "+endDate+"</b>"));
        textViewCreate.setText(Html.fromHtml("Created by "+create+" on <b>"+on+"</b>"));


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

               /* Intent intent = new Intent(this, MlaHome.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();*/
                if (getFragmentManager().getBackStackEntryCount() == 0) {
                    EventDesc.this.finish();
                } else {
                    getFragmentManager().popBackStack();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
