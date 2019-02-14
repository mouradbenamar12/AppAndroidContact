package com.example.mourad.projetandroid.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mourad.projetandroid.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail,userPassword;
    private Button loginBtn;
    private ProgressBar loginProgress;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.login_email);
        userPassword = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.login_button);
        loginProgress = findViewById(R.id.login_progress);

        loginProgress.setVisibility(View.INVISIBLE);

        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this,HomeActivity.class);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginProgress.setVisibility(View.VISIBLE);
                loginBtn.setVisibility(View.INVISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()){

                    showMessage("Please Verify All Fields");

                } else {

                    signin(email,password);
                }


            }
        });
    }

    private void signin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    loginProgress.setVisibility(View.INVISIBLE);
                    loginBtn.setVisibility(View.VISIBLE);
                    updateUI();

                }else {

                    showMessage(task.getException().getMessage());
                }
            }
        });

    }

    private void updateUI() {

        startActivity(HomeActivity);
        finish();

    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

}
