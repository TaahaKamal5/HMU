package com.something.ihfaz.hmu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

// NEED TO IMPLEMENT SAVE CHANGES AND DELETE BUTTON

public class AdminItemActivity extends AppCompatActivity {   // Activity that displays an item off the feed to make changes
    EditText name, description, price, condition, location;
    ImageView pictureID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_item);

        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);
        condition = (EditText) findViewById(R.id.condition);
        location = (EditText) findViewById(R.id.location);
        pictureID = (ImageView) findViewById(R.id.pictureID);

        Intent intent = getIntent();
        Item item = (Item) intent.getParcelableExtra("itemObj");    // get the item object from the call

        name.setText(item.getName());
        description.setText("" + item.getDescription());
        price.setText("" + item.getPrice());
        condition.setText("" + item.getCondition());
        location.setText("" + item.getLocation());
        pictureID.setImageResource(item.getPicture());
    }
}
