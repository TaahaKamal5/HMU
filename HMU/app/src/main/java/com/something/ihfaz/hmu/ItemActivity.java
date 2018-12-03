package com.something.ihfaz.hmu;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class ItemActivity extends AppCompatActivity {   // Activity that displays an item off the feed


    public TextView name, description, price, sellerName, condition, location, timePosted;
    private ImageView pictureID;
    private Context cont;
    Button talk;



    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth user = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        final Item item = (Item) intent.getParcelableExtra("itemObj");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        price = (TextView) findViewById(R.id.price);
        sellerName = (TextView) findViewById(R.id.sellerName);
        condition = (TextView) findViewById(R.id.condition);
        location = (TextView) findViewById(R.id.location);
        timePosted = (TextView) findViewById(R.id.timePosted);
        pictureID = (ImageView) findViewById(R.id.pictureID);
        talk = (Button) findViewById(R.id.talk);

           // get the item object from the call

        name.setText(item.getName());
        description.setText("Description: " + item.getDescription());
        price.setText("Price: $" + item.getPrice());
        sellerName.setText("Seller Name: " + item.getSellerNETID());
        condition.setText("Condition: " + item.getCondition());
        location.setText("Location: " + item.getLocation());
        timePosted.setText("Time Posted");
        pictureID.setImageResource(R.drawable.test1);

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Log.d("Talk", "Button pressed!");

                final DocumentReference document = db.collection("items").document(item.getName() + item.getSellerNETID());

                document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnap = task.getResult();
                            if (documentSnap.exists()) {
                                Log.d("SnapLoad", "DocumentSnapshot data: " + documentSnap.getData());

                                Long l = new Long(0);
                                l = Long.parseLong(documentSnap.get("price").toString());
                                Double a = (double) l;


                                Map<String, Object> overWriteItem = new HashMap<>();
                                overWriteItem.put("itemId", documentSnap.getId());
                                overWriteItem.put("name", documentSnap.get("name"));
                                overWriteItem.put("description", documentSnap.get("description").toString());
                                overWriteItem.put("status", documentSnap.get("status"));
                                overWriteItem.put("keywords", documentSnap.get("keywords").toString());
                                overWriteItem.put("location", documentSnap.get("location").toString());


                                //TODO FIX DATE POSTED IT IS NOT RIGHT I THINK 11/1/18
                                //item.put("time posted", new Timestamp(0, 0));

                                overWriteItem.put("condition", documentSnap.get("condition"));


                                overWriteItem.put("seller_netid", documentSnap.get("seller_netid"));
                                overWriteItem.put("buyer", user.getUid());

                                //TODO ADD GEO LOCATION 11/1/18

                                overWriteItem.put("price", documentSnap.get("price"));


                                //TODO REVIEW HOW TO HANDLE THE NAMING OF ITEMS

                                document.set(overWriteItem); //Overwriting old item

                                toActiveSales();
                                Log.d("Replaced" + document, "with" + overWriteItem.toString());
                            } else {
                                Log.d("No Doc", "No such document");
                            }


                        }
                    }
                });
        };


    });
    }



    public void toActiveSales(){

        Intent intentAS = new Intent(this, ActiveSales.class);
        startActivity(intentAS);
    }
}

//               if(ActiveSales.itemList.size() == 0) {
//                  ActiveSales.createActiveSales(item);
//                   //Toast.makeText(v.getContext(), "Go to Chat for that person", Toast.LENGTH_SHORT).show();
//             } else{
//                   for(int i = 0; i < ActiveSales.itemList.size(); i++){
//                       if(item.equals(ActiveSales.itemList.get(i))){
//                           Toast.makeText(v.getContext(), "Go to Chat for that person", Toast.LENGTH_SHORT).show();
//                           return;
//                      }
//                      else {
//                          ActiveSales.createActiveSales(item);
//                          //Toast.makeText(v.getContext(), "Go to Chat for that person", Toast.LENGTH_SHORT).show();
//                           return;
//                       }
//                   }