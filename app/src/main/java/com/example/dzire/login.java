package com.example.dzire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity implements View.OnClickListener {

    SignInButton signInButton;
    EditText emal,pwsd;
    Button singhout,contin;
    GoogleSignInClient mGoogleSignInClient;
    TextView hi,enamee;
    FirebaseAuth authi;
    String lemial;
    public static String L_email = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        authi = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1044903052208-8aiqdnhlja1h2oj6j50co5m9qcvc8o9g.apps.googleusercontent.com")

                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        hi = (TextView) findViewById(R.id.tvhi);
        enamee = (TextView) findViewById(R.id.gdispli);
        signInButton = (SignInButton) findViewById(R.id.gmsbutt);
        singhout = (Button) findViewById(R.id.gmsoutbutt);
        contin = (Button) findViewById(R.id.conbutttt);

        singhout.setOnClickListener(this);
        signInButton.setOnClickListener(this);


        contin.setOnClickListener(this);

        findViewById(R.id.gmsoutbutt).setVisibility(View.GONE);
        findViewById(R.id.tvhi).setVisibility(View.GONE);
        findViewById(R.id.gdispli).setVisibility(View.GONE);
        findViewById(R.id.conbutttt).setVisibility(View.GONE);

    }


        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == 101) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account);
                } catch (ApiException e) {
                    // Google Sign In failed, update UI appropriately

                    // ...
                }
            }
        }




    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        authi.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = authi.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            updateUI(null);

                        }

                        // ...
                    }
                });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = authi.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {


            findViewById(R.id.gmsbutt).setVisibility(View.GONE);


            findViewById(R.id.gmsoutbutt).setVisibility(View.VISIBLE);
            findViewById(R.id.tvhi).setVisibility(View.VISIBLE);
            findViewById(R.id.gdispli).setVisibility(View.VISIBLE);
            enamee.setText(currentUser.getDisplayName());
            lemial = currentUser.getEmail();
            L_email = lemial;
            findViewById(R.id.conbutttt).setVisibility(View.VISIBLE);

        } else {

        }

    }

    @Override
    public void onClick(View lb) {
        switch (lb.getId()) {

            case R.id.gmsoutbutt:
                authi.signOut();

                // Google sign out
                mGoogleSignInClient.signOut();
                findViewById(R.id.gmsbutt).setVisibility(View.VISIBLE);
                findViewById(R.id.gmsoutbutt).setVisibility(View.GONE);
                findViewById(R.id.tvhi).setVisibility(View.GONE);
                findViewById(R.id.gdispli).setVisibility(View.GONE);
                findViewById(R.id.conbutttt).setVisibility(View.GONE);
                break;

            case R.id.gmsbutt:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
                break;

            case R.id.conbutttt:
                if (MainActivity.kya == "a") {
                    startActivity(new Intent(login.this, upload.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                } else if (MainActivity.kya == "c"){
                startActivity(new Intent(login.this, gender.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }else {
                    startActivity(new Intent(login.this, skinmodel.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;





        }





        }
}

