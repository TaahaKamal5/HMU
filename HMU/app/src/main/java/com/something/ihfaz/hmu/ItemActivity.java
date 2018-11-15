package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;


public class ItemActivity extends AppCompatActivity {   // Activity that displays an item off the feed


    private TextView name, description, price, sellerName, condition, location, timePosted;
    private ImageView pictureID;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        Intent intent = getIntent();
        Item item = (Item) intent.getParcelableExtra("itemObj");    // get the item object from the call

        name.setText(item.getName());
        description.setText("Description: " + item.getDescription());
        price.setText("Price: $" + item.getPrice());
        sellerName.setText("Seller Name: " + item.getSellerNETID());
        condition.setText("Condition: " + item.getCondition());
        location.setText("Location: " + item.getLocation());
        timePosted.setText("Time Posted");
       // pictureID.setImageResource(item.getPicture());
    }
}
