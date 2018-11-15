package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActiveSales extends AppCompatActivity {

    //SAMPLE DATA
//    int[] images = {R.drawable.pen_test, R.drawable.cs_book_test, R.drawable.calculator_test};
//    String[] itemName = {"Mont Blanc Pen", "CS 1200 Textbook", "TI Calculator"};
//    String[] msg = {"Where do you want to meet?", "What condition is it in?", "I'm here"};

    List<Item> itemList = new ArrayList<>();
    RecyclerView rView;
    ActiveItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_sales);

        rView = (RecyclerView) findViewById(R.id.rv);
        adapter = new ActiveItemAdapter(this, itemList);
        rView.setLayoutManager(new GridLayoutManager(this, 1));
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        sampleData();
    }

    public void sampleData() {
       /* Item i1 = new Item("CS 1200 Textbook Name Is Long", "Textbook for the class called the life of an engineer", R.drawable.cs_book_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i1);

        Item i2 = new Item("Mont Blanc Pen", "Very pink", R.drawable.pen_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i2);

        Item i3 = new Item("TI Calculator", "Tres chic", R.drawable.calculator_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i3);

        Item i4 = new Item("TI Calculator", "Tres chic", R.drawable.calculator_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i4);

        Item i5 = new Item("TI Calculator", "Tres chic", R.drawable.calculator_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i5);

        Item i6 = new Item("TI Calculator", "Tres chic", R.drawable.calculator_test, "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i6);

        adapter.notifyDataSetChanged();*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()){
                        case R.id.nav_explore:
                            toNewsFeed();
                            break;
                        case R.id.nav_chat:
                            //toActiveSales();
                            break;
                        case R.id.nav_trade:
                            toAddItems();
                            break;
                        case R.id.nav_history:
                            toTradeHistory();
                            break;
                        case R.id.nav_person:
                            toAccount();
                            break;
                    }

                    return true; //true means you want to select the item
                }
            };

    public void toNewsFeed(){
        Intent intentNF = new Intent(this, FeedActivity.class);
        startActivity(intentNF);
    }

    public void toActiveSales(){
        Intent intentAS = new Intent(this, ActiveSales.class);
        startActivity(intentAS);
    }

    public void toAddItems(){
        Intent intentAI = new Intent(this, AddItems.class);
        startActivity(intentAI);
    }

    public void toTradeHistory(){
        Intent intentTH = new Intent(this, TradeHistory.class);
        startActivity(intentTH);
    }

    public void toAccount(){
        Intent intentA = new Intent(this, Account.class);
        startActivity(intentA);
    }

    public void toChat(){
        Intent intentC = new Intent(this, Chat.class);
        startActivity(intentC);
    }
}
