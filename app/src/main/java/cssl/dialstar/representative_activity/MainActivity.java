package cssl.dialstar.representative_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cssl.dialstar.R;
import cssl.dialstar.user_activity.User_login;

//import cssl.dialstar.R;

public class MainActivity extends AppCompatActivity {
    LinearLayout btnMlaLogin,btnUserLogin;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        btnMlaLogin = (LinearLayout) findViewById(R.id.btn_mla_login);
        btnUserLogin = (LinearLayout) findViewById(R.id.btn_user_login);
        imageView = (ImageView) findViewById(R.id.imageView_Main_logo);

        //imageView.setImageResource(R.drawable.profile_image);

        btnMlaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this,LogIn_Now.class);
                startActivity(intent);

            }
        });

        btnUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(MainActivity.this,User_login.class);
                startActivity(intent);


            }
        });
    }
}
