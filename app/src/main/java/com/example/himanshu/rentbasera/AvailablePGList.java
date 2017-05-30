package com.example.himanshu.rentbasera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.himanshu.rentbasera.R;

import java.util.ArrayList;
import java.util.List;

public class AvailablePGList extends AppCompatActivity {
    private List<PG> pgList = new ArrayList<>();

    private RecyclerView recyclerView;
    private PGAdapter pgAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_pglist);

         Bundle bundle= getIntent().getBundleExtra("bundle");
         // recyclerView.setAdapter(null);
       List<PG> pgList=(List<PG>)bundle.getSerializable("pgList");
          // Log.d("yyy----",pgList.get(1).getPgname());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pgAdapter = new PGAdapter(pgList,getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(pgAdapter);
          pgAdapter.notifyDataSetChanged();
    }
    }
