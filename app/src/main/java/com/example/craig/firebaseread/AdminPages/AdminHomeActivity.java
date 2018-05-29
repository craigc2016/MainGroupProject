package com.example.craig.firebaseread.AdminPages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.craig.firebaseread.Pages.HomeActivity;
import com.example.craig.firebaseread.R;
import com.google.firebase.auth.FirebaseAuth;

/*
This class is used for the navigation around the admin options
of adding and editing items for each of the tables.
 */
public class AdminHomeActivity extends AppCompatActivity {
    /*
    Acts as the main method of the application calls all
    the methods for editing and adding
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        FirebaseAuth.getInstance().signOut();
    }

    /*
    This method handles the button action
    of the aldi admin page
     */
    public void newScreenAldi(View view){
        Intent in = new Intent(this,AdminAldi.class);
        startActivity(in);
        finish();
    }
    /*
    This method handles the button action
    of the dunnes admin page
     */
    public void newScreenDunnes(View view){
        Intent in = new Intent(this,AdminDunnes.class);
        startActivity(in);
        finish();
    }
    /*
    This method handles the button action
    of the product admin page
     */
    public void newScreenProduct(View view){
        Intent in = new Intent(this,AdminProduct.class);
        startActivity(in);
        finish();
    }
    /*
    This method handles the button action
    of the tesco admin page
     */
    public void newScreenTesco(View view){
        Intent in = new Intent(this,AdminTesco.class);
        startActivity(in);
        finish();
    }
    /*
    This method handles the button action
    of the lidl admin page
     */
    public void newScreenLidl(View view){
        Intent in = new Intent(this,AdminLidl.class);
        startActivity(in);
        finish();
    }
    /*
    This method handles the button action
    of the logout action
     */
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent in = new Intent(this,HomeActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(in);
        finish();
    }



}
