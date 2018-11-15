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
import android.widget.ImageView;
import android.widget.TextView;

public class Chat extends AppCompatActivity {

    Button bMsg;
    TextView heading, heading2, sellerName;
    ImageView itemPic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bMsg = (Button) findViewById(R.id.bMessage);
        heading = (TextView) findViewById(R.id.chatHeading);
        heading2 = (TextView) findViewById(R.id.chatHeading2);
        sellerName = (TextView) findViewById(R.id.chatSellerName);
        itemPic = (ImageView) findViewById(R.id.itemPic);

        bMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toPresetMsg();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
    }

    public void setHeading(String head){
        heading.setText(head);
        heading2.setText(head);
    }

    public void setSellerName(String sellerName){
        this.sellerName.setText(sellerName);
    }

    public void setPicture(int id){
        itemPic.setImageResource(id);
    }

    public String getLastMessage(){
        //TODO return the last message sent
        return "Last message";
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

    public void toPresetMsg() {
        Intent intentPM = new Intent(this, PresetMessages.class);
        startActivity(intentPM);
    }
}
