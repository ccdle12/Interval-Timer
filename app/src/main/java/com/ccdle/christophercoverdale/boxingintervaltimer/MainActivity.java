package com.ccdle.christophercoverdale.boxingintervaltimer;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ccdle.christophercoverdale.boxingintervaltimer.Dashboard.Dashboard;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_holder, new Dashboard())
                .commit();
    }
}
