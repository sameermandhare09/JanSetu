package cssl.dialstar.user_activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cssl.dialstar.R;

public class ForgotPasswordUser extends AppCompatActivity {
    Button btnsubmit ;
    public TextInputLayout txtmobilenumber;
    public EditText edmobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        txtmobilenumber=(TextInputLayout) findViewById(R.id.txtmobilenumber);
        edmobilenumber=(EditText) findViewById(R.id.edmobilenumber);
        btnsubmit =(Button) findViewById(R.id.btnsubmit);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }


    public void submit(){
        if(!validatemobilenumber())
        {
            return;
        }
    }
    private boolean validatemobilenumber() {
        if (edmobilenumber.getText().toString().trim().isEmpty()) {
            txtmobilenumber.setError("Please enter valid mobile number or Email");
            return false;
        } else {
            txtmobilenumber.setErrorEnabled(false);
        }

        return true;
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
