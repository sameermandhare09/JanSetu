package cssl.dialstar.user_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cssl.dialstar.R;


public class Thanks extends AppCompatActivity {


     public Button btnhome;
    TextView txtref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        //txtref

        btnhome=(Button) findViewById(R.id.btnhome);

        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Thanks.this,UserDashboard.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(Thanks.this,UserDashboard.class);
        startActivity(intent);
        finish();
    }
}
