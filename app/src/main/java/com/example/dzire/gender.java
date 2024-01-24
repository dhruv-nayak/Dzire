package com.example.dzire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import com.google.android.gms.tasks.Task;
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

public class gender extends AppCompatActivity implements View.OnClickListener {
    Button male, female, button ;
    FirebaseAutoMLRemoteModel remoteModel;
    FirebaseVisionImageLabeler labeler; //For running the image labeler
    FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder optionsBuilder; // Which option is use to run the labeler local or remotely
    ProgressDialog progressDialog; //Show the progress dialog while model is downloading...
    FirebaseModelDownloadConditions conditions; //Conditions to download the model
    FirebaseVisionImage image; // preparing the input image
    TextView textView; // Displaying the label for the input image
    ImageView imageView; //To display the selected image
    private FirebaseAutoMLLocalModel localModel;
    public static String opt,name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gender);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        male = (Button) findViewById(R.id.male);


        female = (Button) findViewById(R.id.female);






        male.setOnClickListener(this);
        female.setOnClickListener(this);


        textView = findViewById(R.id.text);
        button = findViewById(R.id.selectImage);


        imageView = findViewById(R.id.image);



        progressDialog = new ProgressDialog(gender.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().start(gender.this);
                //   fromRemoteModel();
            }
        });

    }


    private void setLabelerFromLocalModel(Uri uri) {
        localModel = new FirebaseAutoMLLocalModel.Builder()
                .setAssetFilePath("GenderDetectionByDzire/manifest.json")
                .build();
        try {
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options =
                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
                            .setConfidenceThreshold((float) 0.5)
                            .build();
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
            image = FirebaseVisionImage.fromFilePath(gender.this, uri);
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


                if (result != null) {




                        Uri uri = result.getUri(); //path of image in phone
                        imageView.setImageURI(uri); //set image in imageview
                        textView.setText(""); //so that previous text don't get append with new one
                        setLabelerFromLocalModel(uri);

                        // setLabelerFromRemoteLabel(uri);
                    } else
                        progressDialog.cancel();


                } else

                    progressDialog.cancel();
            }









    }

    private void setLabelerFromRemoteLabel(final Uri uri) {
        FirebaseModelManager.getInstance().isModelDownloaded(remoteModel)
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isComplete()) {
                            optionsBuilder = new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel);
                            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options = optionsBuilder
                                    .setConfidenceThreshold((float) 0.07)
                                    .build();
                            try {
                                labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
                                image = FirebaseVisionImage.fromFilePath(gender.this, uri);
                                processImageLabeler(labeler, image);
                            } catch (FirebaseMLException | IOException exception) {
                                Log.e("TAG", "onSuccess: " + exception);
                                Toast.makeText(gender.this, "Ml exeception", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(gender.this, "Not downloaded", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG", "onFailure: "+e );
                Toast.makeText(gender.this, "err"+e, Toast.LENGTH_SHORT).show();
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
                    textView.append("You Are :- "+eachlabel + ("") + "\n\n");
                }if (textView.getText().toString().equals("You Are :- "+ ("MALE") + "\n\n")) {

                    startActivity(new Intent(gender.this, Types.class));
                    opt = "a";
                    Toast.makeText(gender.this, "You Are Male And We Are Redirecting to Next Page " , Toast.LENGTH_SHORT).show();
                }else if(textView.getText().toString().equals("You Are :- "+ ("FEMALE") + "\n\n")){




                    startActivity(new Intent(gender.this, Types.class));
                    opt = "b";
                    Toast.makeText(gender.this, "You Are Female And We Are Redirecting to Next Page" , Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(gender.this, "Something went wrong! " , Toast.LENGTH_SHORT).show();


                }





            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("OnFail", "" + e);
                Toast.makeText(gender.this, "Something went wrong! " + e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fromRemoteModel() {
        progressDialog.show();                                         /* model name*/
        remoteModel = new FirebaseAutoMLRemoteModel.Builder("DzireGender").build();
        conditions = new FirebaseModelDownloadConditions.Builder().requireWifi().build();
        //first download the model
        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        CropImage.activity().start(gender.this); // open image crop activity
                    }
                });




    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {



            case R.id.male:

                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);



                    startActivity(new Intent(gender.this, Types.class));
                    opt = "a";

                break;





            case R.id.female:


                    startActivity(new Intent(gender.this, Types.class));
                    opt = "b";




        }

    }
    public void onBackPressed() {

        startActivity(new Intent(gender.this,MainActivity.class));

    }
}
