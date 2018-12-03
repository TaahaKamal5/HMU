package com.something.ihfaz.hmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PresetMessages extends AppCompatActivity {

    RecyclerView rView;
    PresetMessageAdapter adapter;
    static String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preset_messages);

        rView = (RecyclerView) findViewById(R.id.rvMsg);
        adapter = new PresetMessageAdapter(this,address);
        rView.setLayoutManager(new GridLayoutManager(this, 1));
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setAdapter(adapter);
    }

    public static void setItemAddress(String s){
        address = s;
    }
}
