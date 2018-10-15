package com.something.ihfaz.hmu;

import android.content.Intent;
import android.nfc.Tag;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText pass;
    EditText pass2;
    Button enter;
    TextView user;

    FirebaseFirestore db = FirebaseFirestore.getInstance();  //creating instance of firestore

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = (EditText) findViewById(R.id.etrEmail); //adding listeners from 31 to line 36
        name = (EditText) findViewById(R.id.etrName);
        pass = (EditText) findViewById(R.id.etrPass);
        pass2 = (EditText) findViewById(R.id.etrPass2);
        enter = (Button) findViewById(R.id.bEnter);     //!button to submit form!
        user = (TextView) findViewById(R.id.tvUser);    //this is making the button for people who are already registered

        String text = "Already a user? Click here";                         //same here
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);      //displaying the text

        //submitting contents of email, name, pass, and pass2 when "enter" button is pressed

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> user = new HashMap<>();
                //TODO put netid here
                user.put("email", email.getText().toString());
                user.put("display-name", name.getText().toString());
                //TODO put password verify here
                user.put("password", pass.getText().toString());


                // adding the new user doc

                 db.collection("users").document("newDocumentId")
                         .set(user);
            }
        });







        //Text at bottom that goes back to login screen

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toLogin();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        };

        ssb.setSpan(cs1, 16, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        user.setText(ssb);
        user.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void toLogin(){
        Intent intentL = new Intent(this, Login.class);
        startActivity(intentL);
    }
}
