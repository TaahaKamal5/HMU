package com.something.ihfaz.hmu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    }
}
