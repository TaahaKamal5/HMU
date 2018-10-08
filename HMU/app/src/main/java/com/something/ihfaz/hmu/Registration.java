package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Registration extends AppCompatActivity {

    EditText email;
    EditText name;
    EditText pass;
    EditText pass2;
    Button enter;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = (EditText) findViewById(R.id.etrEmail);
        name = (EditText) findViewById(R.id.etrName);
        pass = (EditText) findViewById(R.id.etrPass);
        pass2 = (EditText) findViewById(R.id.etrPass2);
        enter = (Button) findViewById(R.id.bEnter);
        user = (TextView) findViewById(R.id.tvUser);

        String text = "Already a user? Click here";
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);

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
