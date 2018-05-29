package com.example.craig.firebaseread.Pages;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
This class is used for the login page of the application
It allows the user to enter their details in the edit text views
Which will then check the database for the details and if
so it will take the user to the admin pages. If the details
are incorrect it will display an error message.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    /*
   Declares variables globally to be accessed throughout the application
    */
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private TextView txtDisplay;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    /*
     Acts as the main method of the application calls all
     the methods for editing and adding
      */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIntent();
        DatabasePersistentSetUp.getDatabase(); // needed for the offline use of the application
        firebaseAuth = FirebaseAuth.getInstance();

        /*
        Checks if the user is found or not if so it will take them
        to the admin homepage
         */
        if(firebaseAuth.getCurrentUser()!=null){
            finish();
            startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
        textViewSignUp = (TextView) findViewById(R.id.textViewSignUp);
        txtDisplay = (TextView) findViewById(R.id.txtName);
        progressDialog = new ProgressDialog(this);

        //adds the listeners to the buttons
        buttonSignIn.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    /*
    Method used to handle the user login. It will check the details
    entered by the user and checks them against the database
     */
    private void userLogin(){
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
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                        }else {
                            Context context = getApplicationContext();
                            CharSequence text = "ERROR USER NOT FOUND PLEASE TRY AGAIN!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }

                    }
                });
    }//end FirebaseAuth

    /*
    The method to handle the button clicks
     */
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();
        }

        if (view == textViewSignUp){
            finish();
            startActivity(new Intent(this,SignUpActivity.class));
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
