package com.example.dzire;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import android.os.Bundle;

import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {

        Button buttonpos,buttonex,buttcont,buttabu,buttser,manual;

        public static String kya;





        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                
                setContentView(R.layout.activity_main);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);



                buttonex = (Button) findViewById(R.id.buttonex);
                buttonpos = (Button) findViewById(R.id.buttonpos);
                /*buttoni1 = (Button) findViewById(R.id.buttoni1);*/


                buttabu = (Button) findViewById(R.id.btnabt);
                buttcont = (Button) findViewById(R.id.btncon);

                buttser = (Button) findViewById(R.id.btnser);
                manual = (Button) findViewById(R.id.manual);



                buttonex.setOnClickListener(this);
                buttonpos.setOnClickListener(this);




                buttser.setOnClickListener(this);
                manual.setOnClickListener(this);


                buttabu.setOnClickListener(this);
                buttcont.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


                switch (v.getId()) {

                        case R.id.buttonex:
                                startActivity(new Intent(MainActivity.this, upload.class));
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                                kya = "a";
                                break;
                        case R.id.buttonpos:
                                startActivity(new Intent(MainActivity.this, skinmodel.class));
                            overridePendingTransition(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
                                kya = "b";
                                break;
                    case R.id.manual:
                            startActivity(new Intent(MainActivity.this, gender.class));
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                        kya ="c";

                        break;
                    case R.id.btnabt:
                        startActivity(new Intent(MainActivity.this, about.class));
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                        break;
                    case R.id.btnser:
                        startActivity(new Intent(MainActivity.this, services.class));
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                        break;
                    case R.id.btncon:
                        startActivity(new Intent(MainActivity.this, contactus.class));
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                        break;
                        /*case R.id.buttoni2:
                                Toast.makeText(this,"Click Image From Camera With Proper Light", Toast.LENGTH_LONG).show();
                                break;*/


                }

        }


        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {

        }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure?"+"\n\n"+"There is Lot to Explore!")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();



    }

}

















