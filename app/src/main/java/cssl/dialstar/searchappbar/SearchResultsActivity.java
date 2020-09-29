package cssl.dialstar.searchappbar;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

import cssl.dialstar.R;
import cssl.dialstar.representative_util.Config;

public class SearchResultsActivity extends AppCompatActivity {
    private TextView txtQuery, txtcounter;
    ArrayList<Dialstar> dialstarPojos = new ArrayList<>();
    private RecyclerView recyclerView;
    private searchadapter mAdapter;
    String query1 = "";
    SharedPreferences share;
    SharedPreferences.Editor edit;
    Config config;
    int userid = 0;
    int counter = 0;
    String postgrievancecount="";
    String ackgrievancecount="";
    String completedgrievancecount="";
    String pendinggrievancecount="";
    ArrayList<String> data=new ArrayList<>();
    Bundle bundle;
    String query="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ActionBar actionBar = getActionBar();
        config = new Config();

        bundle=getIntent().getExtras();
        query=bundle.getString("query");
        new HttpRequestTask(query).execute();


        // actionBar.setTitle("hii");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        txtQuery = (TextView) findViewById(R.id.txtQuery);
        txtcounter = (TextView) findViewById(R.id.txtcounter);

        txtQuery.setText("Result For:- " + query);

        share = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        edit = share.edit();
        userid = share.getInt("Userid", 0);
       // handleIntent(getIntent());

    }


    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<Dialstar>> {

        ProgressDialog progressDialog;
        String searchstr = "";

        public HttpRequestTask(String query) {
            this.searchstr = query;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

          /*  progressDialog = new Dialog();
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.setContentView( R.layout.progressbar );
            progressDialog.show(); // Display Progress Dialog

*/

        }

        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                //final String url = config.getGetSearchingRepresentativeDetails() + searchstr;
                final String url = config.representativeremoteurl+"admin/app/getSearchingRepresentativeDetails/" + searchstr;


               // Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                Dialstar[] forNow = restTemplate.getForObject(url, Dialstar[].class);
                ArrayList<Dialstar> greeting = new ArrayList(Arrays.asList(forNow));

                return greeting;
            } catch (Exception e) {
                //Log.e("MainActivity", e.getMessage(), e);
            }


            return null;
        }

        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            //Log.i("Search result output", myPojo.size() + "");
            dialstarPojos = myPojo;
           // Log.d("myPojo", String.valueOf(myPojo));
            mAdapter = new searchadapter(myPojo, SearchResultsActivity.this);
            recyclerView.setAdapter(mAdapter);
            counter = myPojo.size();
            txtcounter.setText(String.valueOf("Result Found:-" + counter));


            progressDialog.dismiss();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_action, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQuery(query, false);
        searchView.setIconified(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new HttpRequestTask(query).execute();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
     //   searchAutoComplete.setHintTextColor(Color.GRAY);
      //  searchAutoComplete.setTextColor(Color.BLACK);
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


/* private class HttpRequestTaskSendGrievance extends AsyncTask<Void, Void, ArrayList<Dialstar>> {
        String searchstr1="";
        public HttpRequestTaskSendGrievance(String query1) {
            this.searchstr1=query1;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList<Dialstar> doInBackground(Void... params) {
            try {
                final String url = "http://182.18.163.201:8099/admin/app/getSearchingRepresentativeDetails/"+searchstr1;
                Log.i("url", url);
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Dialstar[] forNow = restTemplate.getForObject(url, Dialstar[].class);
                ArrayList<Dialstar> greeting = new ArrayList(Arrays.asList(forNow));
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }
            return null;
        }
        protected void onPostExecute(final ArrayList<Dialstar> myPojo) {
            Log.i("Search result output",myPojo.size() + "");
            dialstarPojos = myPojo;
            Log.d("myPojo", String.valueOf(myPojo));
            mAdapter = new searchadapter(myPojo,getBaseContext());
            recyclerView.setAdapter(mAdapter);
        }
    }
}*/

