package com.example.craig.firebaseread.AdminPages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.craig.firebaseread.Items.Product;
import com.example.craig.firebaseread.Pages.Config;
import com.example.craig.firebaseread.Pages.DatabasePersistentSetUp;
import com.example.craig.firebaseread.Items.Aldi;
import com.example.craig.firebaseread.Items.Dunnes;
import com.example.craig.firebaseread.R;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*
 This class is for the handling of the dunnes table of the application.
 It handles the declaration of the connection to the firebase and listens for the changes
 using listeners. It uses list arrays to hold the values that are from the firebase table.
 It handles the button actions of the page it uses two edit text areas to handle the input from
 the user.
 */
public class AdminAldi extends AppCompatActivity {
    /*
   Declares variables globally to be accessed throughout the application
    */
    private EditText editName;
    private EditText editPrice;
    private Button btnAdd;
    private Button btnEdit;
    private Firebase ref;
    private int counter = 0;
    private int counterEdit = 0;
    private FirebaseDatabase database;
    private DatabaseReference refProduct;
    private DatabaseReference refAldi;
    private String key = "";
    private ArrayList<Aldi> list;
    private String name = "";
    private String tempname = "";
    private ArrayAdapter<Aldi> adapter;
    private ListView listView;
    /*
   Acts as the main method of the application calls all
   the methods for editing and adding
    */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaldi);
        getIntent();
        setUpScreen();
        getAldiList();
    }

    /*
   This is used to set up the page declaring the instance for to use the
   devices storage to store the firebase tables. It declares all the components for
   the page. It attaches the listeners to the buttons to handle the actions.
    */
    public void setUpScreen() {
        DatabasePersistentSetUp.getDatabase();
        Firebase.setAndroidContext(this);
        ref = new Firebase(Config.FIREBASE_URL);
        database = FirebaseDatabase.getInstance();
        refAldi = database.getReference("Aldi");
        editName = (EditText) findViewById(R.id.editName);
        editPrice = (EditText) findViewById(R.id.editPrice);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        //btnDel = (Button) findViewById(R.id.btnRecords);
        listView = (ListView) findViewById(R.id.displayItems);
        editName.setVisibility(View.INVISIBLE);
        editPrice.setVisibility(View.INVISIBLE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem();
            }
        });
    }

    /*
   This method is used for to get the contents of the firebase table.
   It sets the adapter and adds the contents from the list array
   to the list view.
    */
    public void getAldiList() {
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        refAldi.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Aldi item = dataSnapshot.getValue(Aldi.class);
                if (!list.contains(item)) {
                    list.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            /*
            Used to handle if the connection to the database can not be made.
            It displays an error message to inform the user.
             */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occurred with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }//end get AldiList

    /*
   This is the method which handles the action of adding an item
   to the firebase table. It handles the actions of the user entering incorrect data.
   It checks before adding an item whether it is present or not. If so it rejects the
   addition and gives an error message. It sets some of the components to hide or make them
   non clickable.
    */
    public void addItem() {
        btnEdit.setEnabled(false);  // sets the button for not to be clickable
        counter++;  // used to handle the life cycle of the user input
        String addName = "";
        double addPrice = 0.0;

        // set the components to hide and blank the text areas
        if (counter == 1) {
            editName.setVisibility(View.VISIBLE);
            editPrice.setVisibility(View.VISIBLE);
            editPrice.setText("");
            editName.setText("");
        }

         /*
        Take in the input from the user and check for
        errors and handles them if encountered
         */
        if (counter == 2) {
            addName = editName.getText().toString();
            if (addName.equals("")) {
                CharSequence text = "Error must enter a valid name";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
                toast.show();
                addName = "";
            }
            try {
                addPrice = Double.parseDouble(editPrice.getText().toString());
            } catch (NumberFormatException e) {
                CharSequence text = "Error must enter a valid number";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                addPrice = 0.0;
            }
        }
        if (addName.isEmpty() || addPrice != 0.0) {
            counter = 1;
        }

        /*
        Used to check if the input name already exist in the table
        If so it will display a message and bring the user back to the start of the
        life cycle
         */
        for (Aldi item : list) {
            name = item.getName();
            if (name.matches(addName)) {
                CharSequence text = "Item already present try again!";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
                toast.show();
                Refresh();
                break;
            }
        }


        /*
        If the user enters valid data it gets a reference to the firebase database
        And creates a new node and gives it a unique id. It displays a message to inform the
        user and updates the listview.
         */
        if (!addName.equals("") && addPrice != 0.0 && !name.matches(addName)) {
            Aldi item = new Aldi(addName, addPrice);
            Firebase newRef = ref.child("Aldi").push();
            newRef.setValue(item);
            editName.setText("");
            editPrice.setText("");
            CharSequence text = "Item Added!";
            Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
            toast.show();
            counter = 0;
            btnEdit.setEnabled(true);
            editPrice.setVisibility(View.INVISIBLE);
            editName.setVisibility(View.INVISIBLE);
            addName = "";
            addPrice = 0.0;
        }
    }//end addMethod

    /*
   This is used to handle the editing of an item by the user.
   It sets components to hide and show or allow to be clicked.
   It displays error messages for incorrect input from the user.
   Before making changes to an item it checks the firebase table for any
   conflicts and if so displays an error message. Once the inputs are valid
   it will make the changes permanent to the item in the table.
    */
    public void editItem() {
        btnAdd.setEnabled(false);   // sets the button for not to be clickable
        counterEdit++;     // used to handle the life cycle of the user input
        String nameEdit;
        double priceEdit = 0.0;
        // take in input from the user
        nameEdit = editName.getText().toString().trim();

        //set a component to hide when the user clicks the button
        if (counterEdit == 1) {
            editName.setVisibility(View.VISIBLE);
        }
        /*
        checks the input from the user if incorrect
        displays an error message
         */
        if (counterEdit >= 2 && nameEdit.length() <= 0) {
            CharSequence text = "Must enter a valid name!";
            Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            editName.setFocusable(true);
            counterEdit = 2;
        }
         /*
        This is for to check if the name entered by the user is
        in the firebase table. If so it will extract that node and
        get the details which can be then used to update that item.
        It updates that item by using the unique key id. Displays a message
        if found and makes the edit price visible
         */
        Query query1 = refAldi.orderByChild("name");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Aldi item = ds.getValue(Aldi.class);
                    tempname = editName.getText().toString().trim();
                    if (item.getName().matches(tempname) && !tempname.isEmpty()) {
                        key = ds.getKey();
                        editName.setText("");
                        editPrice.setVisibility(View.VISIBLE);
                        CharSequence textFound = "Item found!";
                        Toast toastFound = Toast.makeText(getBaseContext(), textFound, Toast.LENGTH_SHORT);
                        toastFound.show();
                        break;
                    }
                }//end for loop
                //refAldi.removeEventListener(this);
            }

            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occurred with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        /*
        Checks to see if a key was found if not it means that the
        value entered by the user is not valid and displays an error message
        to the user.
         */
        if (counterEdit == 4 && key.equals("")) {
            CharSequence text = "Item could not be found try again!";
            Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            editName.setText("");
            counterEdit = 2;
        }

        /*
        If the key is not blank it will take input from the
        user for the new values of the item. If the user enters invalid
        information will display an error message
         */
        if (!key.equals("")) {
            nameEdit = editName.getText().toString().trim();
            try {
                priceEdit = Double.parseDouble(editPrice.getText().toString().trim());
            } catch (NumberFormatException e) {
                CharSequence text = "Error enter number!";
                Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
                toast.show();
                editPrice.setText("");
                editPrice.setFocusable(true);
                counterEdit = 1;
            }

            /*
            Used to check if the input name already exist in the table
            If so it will display a message and bring the user back to the start of the
            life cycle
            */
            for (Aldi item : list) {
                name = item.getName();
                if (name.matches(nameEdit)) {
                    CharSequence text = "Item already present try again!";
                    Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
                    toast.show();
                    counterEdit = 1;
                    break;
                }
            }
        }//end method

        /*
        If the user enters valid data it gets a reference to the firebase database
        And updates the node for the unique id key. It displays a message to inform the
        user and updates the listview. And refreshes the page
        */
        if (!nameEdit.equals("") && priceEdit != 0.0 && !name.matches(nameEdit)) {
            Aldi item = new Aldi (nameEdit, priceEdit);
            refAldi.child(key).setValue(item);
            CharSequence text = "Item updated!";
            Toast toast = Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT);
            toast.show();
            editName.setText("");
            editPrice.setText("");
            editPrice.setVisibility(View.INVISIBLE);
            editName.setVisibility(View.INVISIBLE);
            btnAdd.setEnabled(true);
            nameEdit = "";
            priceEdit = 0.0;
            counterEdit = 0;
            key = "";
            reloadAdminAldi();
        }//end if
    }//end editMethod


    /*
   Handles the button click for cancel option
    */
    public void Cancel(View view) {
        Intent in = new Intent(this, AdminAldi.class);
        startActivity(in);
        finish();
    }

    /*
    Handles the button click for main menu option
     */
    public void MainMenu(View view) {
        Intent in = new Intent(this, AdminHomeActivity.class);
        startActivity(in);
        finish();
    }

    /*
    Handles the button click for reloadAdminAldi option
     */
    public void reloadAdminAldi() {
        Intent in = new Intent(this, AdminAldi.class);
        startActivity(in);
        finish();
    }

    /*
    Handles the button click for refresh option
     */
    public void Refresh() {
        Intent in = new Intent(this, AdminHomeActivity.class);
        startActivity(in);
        finish();
    }
}