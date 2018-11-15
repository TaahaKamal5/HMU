package com.something.ihfaz.hmu;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemActivity extends AppCompatActivity {   // Activity that displays an item off the feed
    private TextView name, description, price, sellerName, condition, location, timePosted;
    private ImageView pictureID;
    Dialog sellerPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        sellerPopup = new Dialog(this);

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

        String sellerNET = item.getSellerNETID();
        SpannableStringBuilder seller = new SpannableStringBuilder("Seller: " + sellerNET);
        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                // Toast.makeText(getApplicationContext(),"Show seller info",Toast.LENGTH_SHORT).show();
                showSeller();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        };
        seller.setSpan(cs1, 7, 8 + sellerNET.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sellerName.setText(seller);
        sellerName.setClickable(true);
        sellerName.setMovementMethod(LinkMovementMethod.getInstance());

        condition.setText("Condition: " + item.getCondition());
        location.setText("Location: " + item.getLocation());
        timePosted.setText("Time Posted: " + item.printTimePosted());
        pictureID.setImageResource(item.getPicture());
    }

    public void showSeller() {
        // sellerPopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sellerPopup.setContentView(R.layout.seller_popup);
        TextView name;
        ImageView image;
        name = (TextView) sellerPopup.findViewById(R.id.name);
        image = (ImageView) sellerPopup.findViewById(R.id.profpic);
        name.setText("Pull name from database");
        image.setImageResource(R.drawable.test1);
        // name.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.test1, 0, 0);    // set prof picture
        sellerPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sellerPopup.show();
    }
}
