package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {
    private TextView name, description, price, sellerName, condition, location, timePosted;
    private ImageView pictureID;

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
        Item item = (Item) intent.getParcelableExtra("itemObj");

        Log.v("TAGINSIDE","" + item.getCondition());

        name.setText(item.getName());
        description.setText("Description: " + item.getDescription());
        price.setText("Price: $" + item.getPrice());
        sellerName.setText("Seller Name: " + item.getSellerNETID());
        condition.setText("Condition: " + item.getCondition());
        location.setText("Location: " + item.getLocation());
        timePosted.setText("Time Posted");
        pictureID.setImageResource(item.getPicture());
    }
}
