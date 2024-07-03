package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private SaveSignUpData saveSignUpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        saveSignUpData = new SaveSignUpData(this);

        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        textViewSignUp = findViewById(R.id.text_view_sign_up);

        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                if (saveSignUpData.checkUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Successful login", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, AddRecipeActivity.class);
                    startActivity(intent);
                    finish();
                } else if (saveSignUpData.checkUsername(username)) {
                    Toast.makeText(LoginActivity.this, "Wrong password", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Please register first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
