package com.something.ihfaz.hmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter iAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        iAdapter = new ItemAdapter(this, itemList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(iAdapter);

        sampledata();   // create sample data
    }

    public void sampledata() {
        Item i1 = new Item("CS 1200 Textbook Name Is Long", "Textbook for the class of called the life of an engineer", R.drawable.test1, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i1);

        Item i2 = new Item("Pink eraser", "Very pink", R.drawable.test1, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i2);

        Item i3 = new Item("Blue eraser", "Tres chic", R.drawable.test1, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i3);

        iAdapter.notifyDataSetChanged();
    }

}
