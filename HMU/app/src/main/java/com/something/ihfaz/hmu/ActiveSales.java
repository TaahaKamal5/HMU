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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActiveSales extends AppCompatActivity {

    //SAMPLE DATA
//    int[] images = {R.drawable.pen_test, R.drawable.cs_book_test, R.drawable.calculator_test};
//    String[] itemName = {"Mont Blanc Pen", "CS 1200 Textbook", "TI Calculator"};
//    String[] msg = {"Where do you want to meet?", "What condition is it in?", "I'm here"};
    boolean loading = false;
    int counter = 0;                            //TODO ik this global variable is gross.... leave me alone lol just dont name anything counter...
    List<Item> itemList = new ArrayList<>();
    RecyclerView rView;
    ActiveItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_sales);

        loadData();

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
    }

    public void loadData() {

        loading = true;

        final FirebaseFirestore firebase = FirebaseFirestore.getInstance();
        final FirebaseAuth user = FirebaseAuth.getInstance();


        //Loading any items the current user is selling...

        firebase.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()  {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();                          //Item doc addys stored in "list" arraylist
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }

                    final int listSize = list.size();

                    Log.d("tag", list.toString());

                    for(int c = 0; c < list.size(); c++){

                        final DocumentReference docRef = firebase.collection("items").document(list.get(c));
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        Log.d("DocSnap", "DocumentSnapshot data: " + document.getData());

                                        Long l = new  Long(0);
                                        l = Long.parseLong(document.get("price").toString());
                                        Double a = (double)l;

                                        Log.d("User signed in:", user.getUid());
                                        Log.d("Sale author:", document.get("seller_netid").toString());
                                        Log.d("Test result", ""+document.get("seller_netid").toString().equals(user.getUid()));
                                        //Checks if the current document snapshot is from a document the user is selling
                                        if(document.get("seller_netid").toString().equals(user.getUid()))
                                            itemList.add(new Item(document.get("name").toString(), document.get("description").toString(), "R.drawable.test1", document.get("seller_netid").toString(), document.get("condition").toString(), document.get("location").toString(), (boolean)document.get("status"), Double.parseDouble(document.get("price").toString())));

                                        Log.d("Counter", counter+ "");
                                        Log.d("listSize - 1", listSize-1+"");


                                        counter++;

                                        if(counter == listSize-1)
                                            finishCreate();

                                    } else {
                                        Log.d("DocRIP", "No such document");
                                    }

                                } else {
                                    Log.d("tag", "get failed with ", task.getException());
                                }
                                Log.d("ACD Displaying", itemList.size()+" items");

                            }
                        });

                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
            }
        });


        //Loading any items the user selected to message....

        firebase.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()  {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();                          //Item doc addys stored in "list" arraylist
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }

                    final int listSize = list.size();

                    Log.d("tag", list.toString());

                    for(int c = 0; c < list.size(); c++){

                        final DocumentReference docRef = firebase.collection("items").document(list.get(c));
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task){
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {

                                        Log.d("DocSnap", "DocumentSnapshot data: " + document.getData());

                                        Long l = new  Long(0);
                                        l = Long.parseLong(document.get("price").toString());
                                        Double a = (double)l;

                                        Log.d("User signed in:", user.getUid());
                                        Log.d("Sale author:", document.get("seller_netid").toString());
                                        Log.d("Test result", ""+document.get("seller_netid").toString().equals(user.getUid()));
                                        //Checks if the current document snapshot is from a document the user is selling
                                        Long la = new  Long(0);
                                        la = Long.parseLong(document.get("price").toString());
                                        Double aa = (double)la;

                                        if(document.get("buyer").toString().equals(user.getUid()))
                                            itemList.add(new Item(document.get("name").toString(), document.get("description").toString(), "R.drawable.test1", document.get("seller_netid").toString(), document.get("condition").toString(), document.get("location").toString(), (boolean)document.get("status"), aa));

                                        Log.d("Counter", counter+ "");
                                        Log.d("listSize - 1", listSize-1+"");


                                        counter++;

                                        if(counter == listSize-1)
                                            finishCreate();

                                    } else {
                                        Log.d("DocRIP", "No such document");
                                    }

                                } else {
                                    Log.d("tag", "get failed with ", task.getException());
                                }
                                Log.d("ACD Displaying", itemList.size()+" items");

                            }
                        });

                    }
                } else {
                    Log.d("tag", "Error getting documents: ", task.getException());
                }
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

    public void toChat(View view){
        Log.d("Entering", "Messages");
        Intent intentC = new Intent(this, Chat.class);
        startActivity(intentC);
    }

    public void finishCreate(){

        rView = (RecyclerView) findViewById(R.id.rv);
        Log.d("Being adapted", itemList.size()+" items");
        adapter = new ActiveItemAdapter(this, itemList);
        rView.setLayoutManager(new GridLayoutManager(this, 1));
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setAdapter(adapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        adapter.notifyDataSetChanged();
    }
}
