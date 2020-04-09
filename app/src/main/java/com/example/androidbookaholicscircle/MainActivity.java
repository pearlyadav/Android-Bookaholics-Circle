package com.example.androidbookaholicscircle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button Login, Register;
    TextInputLayout textInputusername, textInputpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.buttonLogin);
        Register = findViewById(R.id.buttonRegister);
        textInputusername = findViewById(R.id.textinputUsername);
        textInputpassword = findViewById(R.id.textinputPassword);

        mAuth = FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(registerIntent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;

                email = textInputusername.getEditText().getText().toString().trim();
                password = textInputpassword.getEditText().getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Successful Login!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, SuccessfulLoginActivity.class));
                        }else
                            Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

    }
}
