package com.example.craig.firebaseread.Pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.craig.firebaseread.Items.Aldi;
import com.example.craig.firebaseread.Items.Dunnes;
import com.example.craig.firebaseread.Items.Lidl;
import com.example.craig.firebaseread.Items.Product;
import com.example.craig.firebaseread.Items.Tesco;
import com.example.craig.firebaseread.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

/*
This class is used to handle the user input for the search page. That is
the user will be given a list of products that are all in the other shops
So the user will click what they would like to search for and then it will
display the price of that item across all the other shops. On run time the
application will pull down all the items in the tables and store them in
an array list. It will then compare the choices of the user against these arraylists
when they hit the compare button and will add them all up and bring the user to the compare
page which will display the total of all the items chosen. It will use the serializable class
to put the results of the totals in an array list and then serializable the objects so they
can be sent to the compare activity.
 */
public class SearchActivity extends AppCompatActivity implements Serializable{
    /*
    Declares variables globally to be accessed throughout the application
     */
    private FirebaseDatabase database;
    private DatabaseReference refProduct,refAldi,refTesco,refLidl,refDunnes;
    private ArrayList<Product>listProduct;
    private ArrayList<Aldi> listAldi;
    private ArrayList<Tesco> listTesco;
    private ArrayList<Lidl> listLidl;
    private ArrayList<Dunnes>listDunnes;
    private ArrayList<Double>listTotal;
    private ListView listView;
    private double totalAldi,totalTesco,totalLidl,totalDunnes;
    private double priceAldi=0.0,priceTesco=0.0,priceLidl=0.0,priceDunnes=0.0;
    private TextView t;
    private ArrayList<String> listNames;
    private CustomAdapter adapter;
    private int counter=0;

    /*
    Acts as the main method of the application calls all
    the methods for editing and adding
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getIntent();
        DatabasePersistentSetUp.getDatabase();
        database = FirebaseDatabase.getInstance();
        refProduct = database.getReference("Product");
        refAldi = database.getReference("Aldi");
        refTesco = database.getReference("Tesco");
        refLidl = database.getReference("Lidl");
        refDunnes = database.getReference("Dunnes");
        getProduct();
        getAldi();
        getTesco();
        getLidl();
        getDunnes();
        checkButtonClick();

    }
    /*
    This method is used for to get the contents of the firebase table.
    It sets the adapter and adds the contents from the list array
    to the list view.
     */
    public void getProduct() {
        refProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listProduct = new ArrayList();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Product item = ds.getValue(Product.class);
                    listProduct.add(item);
                }
                CustomAdapter ad = new CustomAdapter(getBaseContext(), listProduct);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(ad);
            }
            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occured with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
        });
    }//end getAldi

    /*
    Get the aldi details from the firebase database and save into an array list.
    Used for to compare the values from the user.
     */
    public void getAldi(){
        listAldi = new ArrayList<>();
        refAldi.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Aldi item = ds.getValue(Aldi.class);
                    listAldi.add(item);
                }
            }

            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occured with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    /*
    Get the tesco details from the firebase database and save into an array list.
    Used for to compare the values from the user.
     */
    public void getTesco(){
        listTesco = new ArrayList<>();
        refTesco.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Tesco item = ds.getValue(Tesco.class);
                    listTesco.add(item);
                }
            }

            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occured with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    /*
    Get the lidl details from the firebase database and save into an array list.
    Used for to compare the values from the user.
     */
    public void getLidl(){
        listLidl = new ArrayList<>();
        refLidl.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Lidl item = ds.getValue(Lidl.class);
                    listLidl.add(item);
                }
            }

            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occured with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }
    /*
    Get the dunnes details from the firebase database and save into an array list.
    Used for to compare the values from the user.
     */
    public void getDunnes(){
        listDunnes = new ArrayList<>();
        refDunnes.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Dunnes item = ds.getValue(Dunnes.class);
                    listDunnes.add(item);
                }
            }

            /*
           Used to handle if the connection to the database can not be made.
           It displays an error message to inform the user.
            */
            public void onCancelled(DatabaseError databaseError) {
                Context context = getApplicationContext();
                CharSequence text = "Error occured with DATABASE CONNECTION TRY AGAIN!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }


    /*
    Handles the button click of the compare button on screen
     */
    private void checkButtonClick() {
        //t = (TextView) findViewById(R.id.textRes);
        final Button compare = (Button) findViewById(R.id.btnCompare);
        compare.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                compareItems();

            }
        });
    }//end buttonClick

    /*
    This is the method for handling all the comparisons of the arraylists against
    the products chosen by the user.
     */
    private void compareItems(){
        counter ++; //increments the life cycle of the method
        ArrayList<Product> basket = new ArrayList<>();;
        listNames = new ArrayList<>();
        /*
        This gets the product list and checks to see which
        ones are selected by the user. It will then add these
        choices to a basket list array which will be compared
        against the other list arrays
         */
        if(counter == 1){
            ArrayList<Product> productList = adapter.getItems();
            listTotal = new ArrayList<>();
            for(int i=0;i<productList.size();i++){
                Product item = productList.get(i);
                if(item.isSelected()){
                    basket.add(item);
                }
            }
            /*
           Checks the basket list array and checks the list array for
           the aldi table. Which will then add up the price of the items
           that match.
             */
            String aldiName="", productName1="";
            for(int i =0;i<listAldi.size();i++){
                Aldi aldi = listAldi.get(i);
                aldiName = aldi.getName();
                priceAldi = aldi.getPrice();
                for(int x=0;x<basket.size();x++){
                    Product p1 = basket.get(x);
                    productName1 = p1.getName();
                    if(aldiName.equals(productName1)){
                        totalAldi+=priceAldi;
                        break;
                    }
                }//end inner loop to search basket
            }//end outter loop for Aldi
            /*
           Checks the basket list array and checks the list array for
           the tesco table. Which will then add up the price of the items
           that match.
             */
            String tescoName="", productName2="";
            for(int i =0;i<listTesco.size();i++){
                Tesco tesco = listTesco.get(i);
                tescoName = tesco.getName();
                priceTesco = tesco.getPrice();
                for(int x=0;x<basket.size();x++){
                    Product p1 = basket.get(x);
                    productName2 = p1.getName();
                    if(tescoName.equals(productName2)){
                        totalTesco+=priceTesco;
                        break;
                    }
                }//end inner loop to search basket
            }//end outter loop for Tesco item search

            /*
           Checks the basket list array and checks the list array for
           the lidl table. Which will then add up the price of the items
           that match.
             */
            String lidlName="", productName3="";
            for(int i =0;i<listLidl.size();i++){
                Lidl itemLidl = listLidl.get(i);
                lidlName = itemLidl.getName();
                priceLidl = itemLidl.getPrice();
                for(int x=0;x<basket.size();x++){
                    Product p1 = basket.get(x);
                    productName3 = p1.getName();
                    if(lidlName.equals(productName3)){
                        totalLidl+=priceLidl;
                        break;
                    }
                }//end inner loop to search basket
            }//end outter loop for Tesco item search

            /*
           Checks the basket list array and checks the list array for
           the dunnes table. Which will then add up the price of the items
           that match.
             */
            String dunnesName="", productDunnesName="";
            for(int i =0;i<listDunnes.size();i++){
                Dunnes itemDunnes = listDunnes.get(i);
                dunnesName = itemDunnes.getName();
                priceDunnes = itemDunnes.getPrice();
                for(int x=0;x<basket.size();x++){
                    Product p1 = basket.get(x);
                    productDunnesName = p1.getName();
                    if(dunnesName.equals(productDunnesName)){
                        totalDunnes+=priceDunnes;
                        break;
                    }
                }//end inner loop to search basket
            }//end outter loop for Tesco item search
            //Adds all the totals for the items to a list array
            listTotal.add(totalAldi);
            listTotal.add(totalTesco);
            listTotal.add(totalLidl);
            listTotal.add(totalDunnes);

            /*
            This takes the list array from the list total
            and puts them into a bundle and then serialize the objects
            and then puts them into a bundle and starts the activity
             */
            Intent in = new Intent(this,CompareScreen.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("totalList",listTotal);
            in.putExtras(bundle);
            startActivity(in);
            finish();
        }
        /*
        Resets the counters and the total variables
         */
        if(counter > 1){
            counter = 0;
            totalAldi = 0.0;
            totalTesco = 0.0;
            totalLidl = 0.0;
            totalDunnes = 0.0;
        }

    }//end comparison

    /*
    Handles the button click for login option
     */
    public void login(View view){
        Intent in = new Intent(this,LoginActivity.class);
        startActivity(in);
        finish();
    }
    /*
    Handles the button click for home option
     */
    public void home(View view){
        Intent in = new Intent(this,HomeActivity.class);
        startActivity(in);
        finish();
    }


}//end class
