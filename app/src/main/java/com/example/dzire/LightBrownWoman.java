package com.example.dzire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class LightBrownWoman extends AppCompatActivity {
    ImageButton UrlOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_brown_woman);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        UrlOpen = (ImageButton) findViewById(R.id.flip);

        UrlOpen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent Getintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.amazon.in/s?i=apparel&bbn=1968542031&rh=n%3A1571271031%2Cn%3A1953602031%2Cn%3A11400137031%2Cn%3A1968542031%2Cp_n_size_two_browse-vebin%3A1975320031%7C1975321031%7C1975325031%7C1975326031%7C1975327031%7C1975328031%7C1975329031%7C1975331031&dc&fst=as%3Aoff&qid=1593094960&rnid=1974754031&ref=sr_nr_p_n_size_two_browse-vebin_7"));
                startActivity(Getintent);

            }
        });
    }

}