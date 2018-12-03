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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;
public class FeedActivity extends AppCompatActivity {
    private List<Item> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ItemAdapter iAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    int counter = 0;

    private void getItems() {

       /* Item i1 = new Item("CS 1200 Textbook Name Is Long", "Textbook for the class of called the life of an engineer", "R.drawable.test1", "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i1);

        Item i2 = new Item("Pink eraser", "Very pink", "R.drawable.test1", "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 4.90);
        itemList.add(i2);

        Item i3 = new Item("Blue eraser", "Tres chic", "R.drawable.test1", "RXR180022", "Well done", "At SU near the fountain my dude ok??", true, 5.95);
        itemList.add(i3);
        */

        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> list = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        list.add(document.getId());
                    }
                    final int lSize = list.size();

                    Log.d("tag", list.toString());

                    for(int c = 0; c< list.size(); c++){

                        final DocumentReference docRef = db.collection("items").document(list.get(c));
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d("tag", "DocumentSnapshot data: " + document.getData());

                                        Long l = new  Long(0);
                                        l = Long.parseLong(document.get("price").toString());
                                        Double a = (double)l;




                                        itemList.add(new Item(document.get("name").toString(), document.get("description").toString(), "R.drawable.test1", document.get("seller_netid").toString(), document.get("condition").toString(), document.get("location").toString(), (boolean)document.get("status"), a));

                                        if(counter == itemList.size() - 1)
                                            finishCreating();

                                        counter++;
                                    } else {
                                        Log.d("tag", "No such document");
                                    }
                                } else {
                                    Log.d("tag", "get failed with ", task.getException());
                                }
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
                            //toNewsFeed();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        getItems();
    }

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

    public void finishCreating(){
        iAdapter = new ItemAdapter(this, itemList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(iAdapter);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Menu menu = bottomNav.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);


        iAdapter.notifyDataSetChanged();

        // create sample data
    }
}
