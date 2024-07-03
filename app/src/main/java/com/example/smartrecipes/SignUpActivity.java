package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextNewUsername, editTextNewPassword, editTextRepeatPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;
    private SaveSignUpData saveSignUpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        saveSignUpData = new SaveSignUpData(this);

        editTextNewUsername = findViewById(R.id.edit_text_new_username);
        editTextNewPassword = findViewById(R.id.edit_text_new_password);
        editTextRepeatPassword = findViewById(R.id.edit_text_repeat_password);
        buttonSignUp = findViewById(R.id.button_sign_up);
        textViewLogin = findViewById(R.id.text_view_login);

        buttonSignUp.setOnClickListener(v -> {
            String username = editTextNewUsername.getText().toString().trim();
            String password = editTextNewPassword.getText().toString().trim();
            String repeatPassword = editTextRepeatPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (password.equals(repeatPassword)) {
                if (saveSignUpData.checkUsername(username)) {
                    Toast.makeText(SignUpActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
                } else {
                    if (saveSignUpData.addUser(username, password)) {
                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });

        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}