package com.example.dzire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class contactus extends AppCompatActivity implements View.OnClickListener {
    Button mapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mapp = (Button) findViewById(R.id.buttmyloc);
        mapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=an+Pancham Icon,Near D-Mart, Kalyan Nagar, Diwalipura+vadodara"));
        startActivity(intent);

    }
}
