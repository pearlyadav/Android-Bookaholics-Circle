package com.example.androidbookaholicscircle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    TextInputLayout textinputpassword, textinputemail, textinputcpass;
    Button signupButton;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        textinputemail = findViewById(R.id.textinputEmail);
        textinputpassword = findViewById(R.id.textinputPassword);
        textinputcpass = findViewById(R.id.textviewConfirmPassword);
        signupButton = findViewById(R.id.signupButton);

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();

        if(mUser!=null){
            String name = mUser.getDisplayName();
            Toast.makeText(this, "Hello there! " + name, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SuccessfulLoginActivity.class));
        }

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password, cpassword;

                email = textinputemail.getEditText().getText().toString().trim();
                password = textinputpassword.getEditText().getText().toString().trim();
                cpassword = textinputcpass.getEditText().getText().toString().trim();
                boolean valid = true;

                //Email Validator
                if(email.isEmpty()){
                    textinputemail.setError("Field can't be empty!");
                    valid = false;
                }else {
                    if( !Patterns.EMAIL_ADDRESS.matcher(email).matches() ){
                        textinputemail.setError("Invalid Email!");
                        valid = false;
                    }else{
                        textinputemail.setError("");
                    }
                }

                // Password Validator
                if(password.isEmpty()){
                    textinputpassword.setError("Field can't be empty!");
                    valid = false;
                }else{
                    if(password.length()<8){
                        textinputpassword.setError("Password too short!");
                        valid = false;
                    }else{

                        if(!PASSWORD_PATTERN.matcher(password).matches()){
                            textinputpassword.setError("Please include at least:\n" +
                                    "1 lowercase character\n" +
                                    "1 uppercase character\n" +
                                    "1 special character\n" +
                                    "and 1 Number for a strong password.");
                            valid = false;
                        }else{
                            textinputpassword.setError("");
                        }
                    }
                }

                // Confirm Password Validator
                if(cpassword.isEmpty()){
                    textinputcpass.setError("Field can't be empty!");
                    valid = false;
                }else{
                    if(!password.equals(cpassword)) {
                        textinputcpass.setError("Password doesn't match!");
                        valid = false;
                    }else{
                        textinputcpass.setError("");
                    }
                }

                if( valid ){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(RegisterPage.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(RegisterPage.this, "Congratulations, Successful SignUp!!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterPage.this, SuccessfulLoginActivity.class));
                            }else{
                                Toast.makeText(RegisterPage.this, "Some Problems Occured!!!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                } else{
                    Toast.makeText(RegisterPage.this, "You are not Authorised.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
