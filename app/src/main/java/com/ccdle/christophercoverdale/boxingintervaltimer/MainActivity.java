package com.ccdle.christophercoverdale.boxingintervaltimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, new Dashboard())
                .commit();
    }
}
