package com.something.ihfaz.hmu;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email;
    EditText pass;
    Button login;
    TextView reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.etEmail);
        pass = (EditText) findViewById(R.id.etPass);
        login = (Button) findViewById(R.id.bLogin);
        reg = (TextView) findViewById(R.id.tvRegister);

        String text = "Not registered yet? Register here\n Forgot your password?";
        SpannableStringBuilder ssb = new SpannableStringBuilder(text);

        ClickableSpan cs1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toNewReg();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        };

        ClickableSpan cs2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                toForgotPass();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setFakeBoldText(true);
            }
        };

        ssb.setSpan(cs1, 20, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(cs2, 34, 56, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        reg.setText(ssb);
        reg.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void toNewReg(){
        // Intent intentNR = new Intent(this, Registration.class);
        // startActivity(intentNR);
        Intent intentNR = new Intent(this, CurrentlySellingActivity.class);
        startActivity(intentNR);
    }

    public void toForgotPass(){
        Intent intentFP = new Intent(this, Email.class);
        startActivity(intentFP);
    }
}
