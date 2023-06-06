package com.bilan.postsApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;


import android.os.Bundle;

import static com.bilan.postsApp.R.id.nav_host_fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Navigation.findNavController(this, nav_host_fragment);
    }
}