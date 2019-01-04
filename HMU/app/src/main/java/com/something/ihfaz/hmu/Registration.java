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
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private FirebaseAuth mAuth;
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
        mAuth = FirebaseAuth.getInstance();

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
                if(!isEditTextFilled(email.getText().toString(), name.getText().toString(), pass.getText().toString(), pass2.getText().toString())) {
                    Toast.makeText(Registration.this, "Error: All the information must be entered!", Toast.LENGTH_SHORT).show();
                }

                else if(!pass.getText().toString().equals(pass2.getText().toString())) {
                    Toast.makeText(Registration.this, "Error: The passwords must match!", Toast.LENGTH_SHORT).show();
                }

                else {
                    String emailHolder = email.getText().toString();
                    String passHolder = pass.getText().toString();

                    if(emailHolder.substring(emailHolder.length() - 13).equals("utdallas.edu") || emailHolder.substring(emailHolder.length() - 13).equals("utesting.edu"))
                    {
                        mAuth.createUserWithEmailAndPassword(emailHolder, passHolder);
                        toLogin();
                    }else{
                        Toast.makeText(Registration.this, "Error: must be a UTDallas email", Toast.LENGTH_SHORT).show();
                    }
                }
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

    public boolean isEditTextFilled(String... args) {
        for(String s: args){
            if(s.equals("")){
                return false;
            }
        }
        return true;
    }
}
