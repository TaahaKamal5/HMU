package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {

    final static String[] msgList = {"Yes", "No", "Time for meet up: ", "Day for meet up: ", "I want this back at: ", "I'm at the location", "I'm on my way",
            "That sounds good", "Okay", "Can we negotiate the price", "Where are you?", "I'm interested in this item", "Is this item still available"
    };

    Item aItem;

    String itemNameHolder = "";
    Button bMsg;
    TextView heading, heading2, sellerName;
    ImageView itemPic;
    String itemId = "";
    boolean loading = false;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth user = FirebaseAuth.getInstance();

    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter iAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bMsg = (Button) findViewById(R.id.bMessage);
        heading = (TextView) findViewById(R.id.chatHeading);
        heading2 = (TextView) findViewById(R.id.chatHeading2);      //Item name
        sellerName = (TextView) findViewById(R.id.chatSellerName);
        itemPic = (ImageView) findViewById(R.id.itemPic);
        recyclerView = (RecyclerView) findViewById(R.id.chatRV);

        //TODO FOR TESTING PURPOSES ONLY

        Intent intent = getIntent();
        final Item item = intent.getParcelableExtra("itemObj");
        aItem = intent.getParcelableExtra("itemObj");

        //itemId = "silver0hMqeYLM5OSeUG3hB614VVNxbVm2";
        itemId = item.getName().toString() + item.getSellerNETID().toString();
        Log.d("itemIdatline68is", itemId);
        getMessages();
        //sampleData();


        //TESTING BACK-END
    }

    public void setHeading(String head){
        heading.setText(head);
        heading2.setText(head);
    }

    public void setSellerName(String sellerName){
        this.sellerName.setText(sellerName);
    }

    public void setPicture(int id){

    }


    public void getMessages(){
        loading=true;
        db.collection("items").document(itemId).collection("messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    final int lSize = list.size();

                    Log.d("Printed list msg", list.toString());

                    for(int c = 0; c< list.size(); c++){
                        Log.d(list.get(c).toString(), "Doc addy being tried..");
                        String temper = list.get(c).toString();
                        final DocumentReference docRef = db.collection("items").document(itemId).collection("messages").document(temper);
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                ArrayList<String> databaseData = new ArrayList<String>();

                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d("Line 120 ReadsL" + document.get("sender").toString(), document.get("message").toString());
                                        databaseData.add(document.get("sender").toString()+ "---" + document.get("message").toString());
                                    } else {
                                        Log.d("Filed Read", "No such document");
                                    }

                                    String out = "";

                                    if(!loading)
                                        finishCreate();

                                    for(int c = 0; c < databaseData.size(); c++){
                                        out = databaseData.get(c);
                                        String[] data = out.split("---");
                                        Log.d("OpeningNoah's", itemId);
                                        Log.d("Message Data", data[1] + "( sent by )" + data[0]);

                                        if(user.getUid().toString().equals(data[0]))
                                        {
                                            Log.d("Found True", user.getUid());
                                            messages.add(new ChatMessage(false, msgList[Integer.parseInt(data[1])]));
                                        }else{
                                            Log.d("Found False", user.getUid());
                                            Log.d("Vender id", aItem.getSellerNETID());
                                            messages.add(new ChatMessage(true, msgList[Integer.parseInt(data[1])]));
                                        }
                                    }

                                    if(databaseData.size() == 0)
                                        Log.d("Error", "Message size" + databaseData.size());

                                } else {
                                    Log.d("tag", "get failed with ", task.getException());
                                }
                            }
                        });

                        if(c==list.size()-1)
                            loading=false;
                    }



                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }


            }
        });


    }
/*
    public String getLastMessage(){
        if(messages.size() > 0){
            String out = messages.get(messages.size()-1);
            String[] data = out.split("---");
            return data[1];
        }else return "no messages";

    }

    public String getLastSender(){
        if(messages.size() > 0){
            String out = messages.get(messages.size()-1);
            String[] data = out.split("---");
            return data[0];
        }else return "no senders";
    }
*/
    public void pop(){
        messages.remove(messages.size()-1);
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
        PresetMessages.setItemAddress(itemId);
        Intent intentPM = new Intent(this, PresetMessages.class);
        startActivity(intentPM);
    }

    public void finishCreate(){

        for(int b = 0; b < messages.size(); b++){
            Log.d("Message Loaded27: ", messages.get(b).toString());
        }



        heading.setText(aItem.getName());
        heading2.setText(aItem.getName());
        sellerName.setText(aItem.getSellerNETID());
        itemPic.setImageResource(R.drawable.test1);

        bMsg.setOnClickListener(new View.OnClickListener() {    //TODO this happens when send button is pressed
            @Override
            public void onClick(View v) {
                toPresetMsg();
            }
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //itemId =  heading.getText().toString() + sellerName.getText().toString(); //TODO WHEN FRONT-END CONNECTED UN COMMENT

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        iAdapter = new MessageAdapter(this, messages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(iAdapter);
        iAdapter.notifyDataSetChanged();
    }
}
