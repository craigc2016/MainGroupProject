package com.example.craig.firebaseread.Pages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.craig.firebaseread.R;

import java.util.ArrayList;

/**
 * Created by craig on 25/04/2017.
 */

/*
This class is used to take in an array list of double values and an array of integer values
It will then set the item of each row with an image matching the image in the array.
It is used to set the list view row instead of the default
 */
class CustomAdapterCompare extends ArrayAdapter<Double>{
    private final ArrayList<Double> total;
    private final Integer[]imageList;
    /*
    Constructor of the class takes in the array list and array and sets
    and gets the reference to the layout of the custom row
     */
    public CustomAdapterCompare(Context context,ArrayList<Double> total,Integer[]imageList) {
        super(context, R.layout.custom_row_compare,total);
        this.total = total;
        this.imageList = imageList;
    }

    /*
    This is the method which sets the contents of each item for the
    list view.
     */
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflate = LayoutInflater.from(getContext());
        View customView = inflate.inflate(R.layout.custom_row_compare,parent,false);
        TextView txtView = (TextView) customView.findViewById(R.id.txtRow);
        txtView.setText("Total Price = €" + String.format("%.2f", total.get(position)));
        ImageView image = (ImageView) customView.findViewById(R.id.imageView);
        image.setImageResource(imageList[position]);
        return customView;
    }
}
