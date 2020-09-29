package cssl.dialstar;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GridRecycler extends AppCompatActivity {

    private List<Recycler> transactionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_recycler);
        context = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        mAdapter = new RecyclerViewAdapter(transactionList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        prepareMovieData();
    }

    private void prepareMovieData() {
        Recycler transaction = new Recycler("Water","86");
        transactionList.add(transaction);

        transaction = new Recycler("Road","55");
        transactionList.add(transaction);

        transaction = new Recycler("Electricity","70");
        transactionList.add(transaction);
        mAdapter.notifyDataSetChanged();
    }
}
