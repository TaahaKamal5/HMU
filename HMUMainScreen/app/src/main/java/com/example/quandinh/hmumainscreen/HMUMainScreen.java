package com.example.quandinh.hmumainscreen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class HMUMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hmu_main_screen);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selected_fragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_person:
                            //what we're gonna do with this fragment
                            selected_fragment = new PersonalFragment();
                            break;
                        case R.id.nav_explore:
                            //what we're gonna do with this fragment
                            selected_fragment = new ExploreFragment();
                            break;
                        case R.id.nav_trade:
                            //what we're gonna do with this fragment
                            selected_fragment = new TradeFragment();
                            break;
                        case R.id.nav_chat:
                            //what we're gonna do with this fragment
                            selected_fragment = new ChatFragment();
                            break;
                        case R.id.nav_history:
                            //what we're gonna do with this fragment
                            selected_fragment = new HistoryFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selected_fragment).commit();

                    return true; //true means you want to select the item
                }
            };
}
