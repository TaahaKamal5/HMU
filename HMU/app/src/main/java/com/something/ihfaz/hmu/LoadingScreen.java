package com.something.ihfaz.hmu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class LoadingScreen extends AppCompatActivity {

    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        logo = (TextView) findViewById(R.id.tempLogo);

        Animation loadTransition = AnimationUtils.loadAnimation(this, R.anim.transition);
        logo.startAnimation(loadTransition);

        final Intent intent = new Intent(this, Login.class);

        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
