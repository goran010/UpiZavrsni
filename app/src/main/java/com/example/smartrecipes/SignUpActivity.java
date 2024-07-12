package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    //Declaration
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;
    private UserDatabaseHelper userDatabaseHelper; //For db operations

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up); //Layout activity_sign_up

        //Finding UI components in its layout
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        editTextConfirmPassword = findViewById(R.id.edit_text_confirm_password);
        buttonSignUp = findViewById(R.id.button_sign_up);
        textViewLogin = findViewById(R.id.text_view_login);

        userDatabaseHelper = new UserDatabaseHelper(this);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim(); //Get username
                String password = editTextPassword.getText().toString().trim(); //Get password
                String confirmPassword = editTextConfirmPassword.getText().toString().trim(); //Get confirmerd password

                //Check for errors (display adequate message) or display message if registration is successful
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    if (userDatabaseHelper.addUser(username, password)) {
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Navigation to login screen (if user already has created account)
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
