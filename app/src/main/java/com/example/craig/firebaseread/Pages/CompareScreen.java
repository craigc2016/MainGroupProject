package com.example.craig.firebaseread.Pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.craig.firebaseread.R;

import java.util.ArrayList;

import static com.example.craig.firebaseread.R.id.listTotal;
import static com.example.craig.firebaseread.R.id.listView;

/*
This class is used for the displaying of the totals for
the comparisons carried out over the different shops.
It handles the button actions for navigation around the application
 */
public class CompareScreen extends AppCompatActivity {
    private ListView listView;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        setUpScreen();
    }
    /*
    Used to reference the components and display the results
     */
    public void setUpScreen(){

        /*
        This is how the objects are passed from the other activity
         */
        getIntent();
        display();
    }

    /*
    Sets the components data from taken in objects from the search activity.
    populate the list view with the results of an array and array list and set up
    custom adapter. To set a text view and image view for the custom row
     */
    public void display(){
        Bundle b = getIntent().getExtras();
        ArrayList<Double> list = (ArrayList<Double>)b.getSerializable("totalList");
        Integer imageList[] = {R.drawable.aldi,R.drawable.tesco,R.drawable.lidl,R.drawable.dunnes};

        CustomAdapterCompare adapter = new CustomAdapterCompare(getBaseContext(),list,imageList);
        listView = (ListView) findViewById(listTotal);
        listView.setAdapter(adapter);

    }

    /*
    This is the option for going to the search page
    */
    public void search(View view){
        Intent in = new Intent(this,SearchActivity.class);
        startActivity(in);
        finish();
    }
    /*
    This is the option for going to the login page
    */
    public void settings(View view){
        Intent in = new Intent(this,LoginActivity.class);
        startActivity(in);
        finish();
    }
    /*
    This is the option for going to the home page
    */
    public void home(View view){
        Intent in = new Intent(this,HomeActivity.class);
        startActivity(in);
        finish();
    }



}
