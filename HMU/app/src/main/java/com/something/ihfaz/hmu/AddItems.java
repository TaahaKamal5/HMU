package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddItems extends AppCompatActivity {

    Button borrow, sell, submit;
    EditText name, description, price, condition, location, keywords;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //Buttons
        borrow = findViewById(R.id.button_borrow);
        sell = findViewById(R.id.button_sell);
        submit = findViewById(R.id.button_submit);

        //Text fields
        name = findViewById(R.id.addName);
        description = findViewById(R.id.addDesc);
        price = findViewById(R.id.addPrice);
        condition = findViewById(R.id.addCond);
        location = findViewById(R.id.addLoc);
        keywords = findViewById(R.id.addKeywords);

        borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This button is for borrowing");
                //what the button is supposed to do
            }
        });


        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("This button is for selling");
                //what the button is supposed to do
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.setLoggingEnabled(true);

                //Log.i("SUBMIT BUTTOM PRESSED SUBMIT BUTTON PRESSED WOWOWOWOWOWOOWOWOWOWOOWOOWOWO", "WOWOWOOWWOWOOWO");
                Map<String, Object> item = new HashMap<>();
                String itemName = name.getText().toString();
                item.put("name", name.getText().toString());
                item.put("description", description.getText().toString());
                item.put("status", false);
                item.put("keywords", keywords.getText().toString());

                //TODO FIX DATE POSTED IT IS NOT RIGHT I THINK 11/1/18
                //item.put("time posted", new Timestamp(0, 0));

                item.put("condition", condition.getText().toString());

                //TODO FIX THIS ONE FIRST UID THAT IS

                //.put("vendor UID", user.getUid());

                //TODO ADD GEO LOCATION 11/1/18

                item.put("price", price.getText().toString());


                //TODO REVIEW HOW TO HANDLE THE NAMING OF ITEMS

                db.collection("items").document(itemName).set(item);

                //  Intent intentL = new Intent(this, FeedActivity.class);
                // startActivity(intentL);




                //what the button is supposed to do
                toNewsFeed();
            }
        });
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
                            toActiveSales();
                            break;
                        case R.id.nav_trade:
                            //toAddItems();
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
}
