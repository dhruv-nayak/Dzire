package com.example.dzire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Types extends AppCompatActivity implements View.OnClickListener {
    ImageButton typeA, typeB, typeC, typeD;
    public static String opti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        typeA = (ImageButton) findViewById(R.id.ta);
        typeB = (ImageButton) findViewById(R.id.tb);
        typeC = (ImageButton) findViewById(R.id.tc);
        typeD = (ImageButton) findViewById(R.id.td);





        typeA.setOnClickListener(this);
        typeB.setOnClickListener(this);
        typeC.setOnClickListener(this);
        typeD.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ta:
                if (gender.opt == "a") {
                    startActivity(new Intent(Types.this, MaleBrown.class));
                    opti = "a";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else if (gender.opt == "b") {
                    startActivity(new Intent(Types.this, BrownWoman.class));
                    opti = "aa";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                }
                break;
            case R.id.tb:
                if (gender.opt == "a" ) {
                    startActivity(new Intent(Types.this, MaleLightBrown.class));
                    opti = "b";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else if (gender.opt == "b") {
                    startActivity(new Intent(Types.this, LightBrownWoman.class));
                    opti = "bb";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                }
                break;
            case R.id.tc:
                if (gender.opt == "a") {
                    startActivity(new Intent(Types.this, MaleBlack.class));
                    opti = "c";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else if (gender.opt == "b") {
                    startActivity(new Intent(Types.this, BlackWoman.class));
                    opti = "cc";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                }
                break;
                case R.id.td:
                if (gender.opt == "a") {
                    startActivity(new Intent(Types.this, MaleFair.class));
                    opti = "d";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else if (gender.opt == "b") {
                    startActivity(new Intent(Types.this, FairWoman.class));
                    opti = "dd";
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                }
                break;

        }
    }
    public void onBackPressed() {

        startActivity(new Intent(Types.this,gender.class));

    }
}