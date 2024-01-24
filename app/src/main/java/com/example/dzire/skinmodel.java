package com.example.dzire;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.List;

public class skinmodel extends AppCompatActivity implements View.OnClickListener{

    FirebaseAutoMLRemoteModel remoteModel;
    FirebaseVisionImageLabeler labeler; //For running the image labeler
    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder optionsBuilder; // Which option is use to run the labeler local or remotely
    ProgressDialog progressDialog; //Show the progress dialog while model is downloading...
    FirebaseModelDownloadConditions conditions; //Conditions to download the model
    FirebaseVisionImage image; // preparing the input image
    TextView textView; // Displaying the label for the input image
    Button button,next, info; // To select the image from device
    Dialog myDialog;


    ImageView imageView; //To display the selected image
    private FirebaseAutoMLLocalModel localModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skinmodel);
        myDialog = new Dialog(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        textView = findViewById(R.id.text);
        button  = findViewById(R.id.info);


        button = findViewById(R.id.selectImage);
         next = (Button) findViewById(R.id.next);

        imageView = findViewById(R.id.image);
        next.setOnClickListener(this);
        findViewById(R.id.next).setEnabled(false);

        progressDialog = new ProgressDialog(skinmodel.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(skinmodel.this);
                //   fromRemoteModel();
            }
        });
    }
    public void ShowPopup(View v){
        TextView close;


        myDialog.setContentView(R.layout.popup);
        close = (TextView) myDialog.findViewById(R.id.close);
        
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                

            }
        });
        myDialog.show();

    }


    private void setLabelerFromLocalModel(Uri uri) {
        localModel = new FirebaseAutoMLLocalModel.Builder()
                .setAssetFilePath("DzireByDhruv/manifest.json")
                .build();
        try {
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options =
                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
                            .setConfidenceThreshold((float) 0.6)
                            .build();
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
            image = FirebaseVisionImage.fromFilePath(skinmodel.this, uri);
            processImageLabeler(labeler, image);
        } catch (FirebaseMLException | IOException e) {
            // ...
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    next.setEnabled(true);



                    if (result != null) {



                        Uri uri = result.getUri(); //path of image in phone
                        imageView.setImageURI(uri); //set image in imageview
                        textView.setText(""); //so that previous text don't get append with new one
                        setLabelerFromLocalModel(uri);

                        // setLabelerFromRemoteLabel(uri);
                    } else
                        progressDialog.cancel();





                } else
                    Toast.makeText(skinmodel.this, "Something went wrong Please Select Another Image! " , Toast.LENGTH_SHORT).show();


                progressDialog.cancel();
            }








    }

    private void setLabelerFromRemoteLabel(final Uri uri) {
        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnCompleteListener( new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isComplete()) {
                            optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel);
                            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = optionsBuilder
                                    .setConfidenceThreshold(0.0f)
                                    .build();
                            try {
                                labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
                                image = FirebaseVisionImage.fromFilePath(skinmodel.this, uri);
                                processImageLabeler(labeler, image);
                            } catch (FirebaseMLException | IOException exception) {
                                Log.e("TAG", "onSuccess: " + exception);
                                Toast.makeText(skinmodel.this, "Ml exeception", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(skinmodel.this, "Not downloaded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "onFailure: "+e );
                Toast.makeText(skinmodel.this, "err"+e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void processImageLabeler(FirebaseVisionImageLabeler labeler, FirebaseVisionImage image) {
        labeler.processImage(image).addOnCompleteListener(new OnCompleteListener<List<FirebaseVisionImageLabel>>() {

            @Override
            public void onComplete(@NonNull Task<List<FirebaseVisionImageLabel>> task) {
                progressDialog.cancel();
                for (FirebaseVisionImageLabel label : task.getResult()) {
                    String eachlabel = label.getText().toUpperCase();
                    float confidence = label.getConfidence();
                    textView.append("Your Skin Tone Is"+"\n\n"+eachlabel +("") + "\n\n");
                }



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OnFail", "" + e);
                Toast.makeText(skinmodel.this, "Something went wrong! " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fromRemoteModel() {
        progressDialog.show();                                         /* model name*/
        remoteModel = new FirebaseAutoMLRemoteModel.Builder("Dzire_By_Dhruv").build();
        conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        //first download the model
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CropImage.activity().start(skinmodel.this); // open image crop activity
                    }
                });




    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.next:


                startActivity(new Intent(skinmodel.this, gender.class));

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


        }


    }
    public void onBackPressed() {

        startActivity(new Intent(skinmodel.this,MainActivity.class));

    }

}
