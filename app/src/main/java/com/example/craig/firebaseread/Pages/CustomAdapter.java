package com.example.craig.firebaseread.Pages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.craig.firebaseread.Items.Product;
import com.example.craig.firebaseread.R;

import java.util.ArrayList;

/*
This class is used for the adapter for the list view on the search page.
It displays a text view and a checkbox which allows the user to select the
choice of items. It takes in parameters of context and an array list of items
from the search activity class. It handles the list view scrolling so it does not
reset the choices of the user.
 */
class CustomAdapter extends ArrayAdapter<Product> {
    private static ArrayList<Product> itemsIn; //the array list is made static so that it saves the choices
    private Context context;
    /*
    Acts as the main method for the class initializing the variables
     */
    CustomAdapter(Context context, ArrayList<Product> items){
        super(context, R.layout.custom_row,items);
        this.context = context;
        this.itemsIn = new ArrayList<>();
        this.itemsIn.addAll(items);
    }
    /*
    This is the getter method used to return the contents
    of the array list.
     */
    public static ArrayList<Product> getItems(){
        return itemsIn;
    }

    /*
    This is used to hold the contents of the list view when the
    user scrolls down the list view.
     */
    static class ViewHolder {
        protected TextView itemText;
        protected CheckBox checkBox;
    }

    /*
    Handles the view of the list view it is used to create each row of the
    list view and keeps track of all the items that are selected.
     */
    public View getView(final int position, View convertView, final ViewGroup parent){
        ViewHolder viewHolder = null;
        /*
        Checks to see if the view is null and if so it creates a row
        for each of the products.
         */
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_row,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.itemText = (TextView) convertView.findViewById(R.id.txtList);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkList);
            convertView.setTag(viewHolder);
            /*
            Keeps track of the checkbox that is selected and uses
            tags to keep track of the choice
             */
            viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    Product item = (Product) cb.getTag();
                    item.setSelected(cb.isChecked());
                }
            });

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /*
        Creates the object item of product and sets its name and keeps
        track of its position
         */
        Product item = itemsIn.get(position);
        viewHolder.itemText.setText(item.getName());
        viewHolder.checkBox.setChecked(item.isSelected());
        viewHolder.checkBox.setTag(item);
        return convertView;
    }//end getView


}//end class

