package com.example.smartrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    //UI components
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    //Helper class for the database operations
    private UserDatabaseHelper userDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); //Set layout for this activity (activity_login.xml)

        //Initializing of the UI components by finding them by their ID in the layout file
        editTextUsername = findViewById(R.id.edit_text_username);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        textViewRegister = findViewById(R.id.text_view_register);

        userDatabaseHelper = new UserDatabaseHelper(this); // Interaction with user database

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Retrieving the text entered int the username and password fields
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //Check if entered data is correct, if true create Intent to start MainActivity
                if (userDatabaseHelper.checkUser(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent); //Start MainActivity
                    finish(); //Finish current activity
                } else {
                    //If entered data is false, display message
                    Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });


        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create intent to start SignUpActivity when clicked (text under login button)
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
