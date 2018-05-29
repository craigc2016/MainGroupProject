package com.example.craig.firebaseread.Pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.craig.firebaseread.AdminPages.AdminHomeActivity;
import com.example.craig.firebaseread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/*
This class handles the user registering a new user for the system.
With an email and a password. It will check if the email entered is of proper
format and the password is of a minimum of 6 characters
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    /*
   Declares variables globally to be accessed throughout the application
    */
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    /*
    Acts as the main method of the application calls all
    the methods for editing and adding
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getIntent();
        DatabasePersistentSetUp.getDatabase(); // needed for the offline use of the application
        firebaseAuth = FirebaseAuth.getInstance();
        /*
        Checks if the user is found or not if so it will take them
        to the admin homepage
         */
        if(firebaseAuth.getCurrentUser()!=null){
            //Profile activity

            finish();
            startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));

        }

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //adds the listeners to the buttons
        buttonRegister.setOnClickListener(this);
        textViewSignin.setOnClickListener(this);

    }
    /*
    Method which takes in input from the user if the data
    entered is valid it will register the user with the system. And
    takes them to the admin login page.
     */
    private void registerUser(){

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter email",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }

        if (TextUtils.isEmpty(password)){
            //email is empty
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
            //stopping the function execution further
            return;

        }
        //If validations are ok
        //show progress bar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            //User is successfully registered and logged in

                            //Profile activity

                            finish();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        }

                        else{

                            Toast.makeText(SignUpActivity.this, "Failed to register" , Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                    }
                });
    }

    /*
     The method to handle the button clicks
      */
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }

        if(v == textViewSignin){
            //will open login Activity here
            startActivity(new Intent(this,LoginActivity.class));

        }

    }

    /*
    Handles the button click for home option
    */
    public void home(View view){
        Intent in = new Intent(this,HomeActivity.class);
        startActivity(in);
        finish();
    }

    /*
   Handles the button click for search option
    */
    public void search(View view){
        Intent in = new Intent(this,SearchActivity.class);
        startActivity(in);
        finish();
    }
    /*
    Handles the button click for login option
     */
    public void settings(View view){
        Intent in = new Intent(this,LoginActivity.class);
        startActivity(in);
        finish();
    }
}
