package com.example.craig.firebaseread.Items;


/*
This class is used for the item object for the product items table
It sets the variables and has a toString method needed for to
turn the objects to display in text format for the list view
 */
public class Product{

    private String name;
    private boolean selected;
    /*
    Blank constructor needed for to query the firebase database
     */
    public Product(){

    }
    /*
    constructor that sets the variables of the new item object
     */
    public Product(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }

    public boolean isSelected(){
        return selected;
    }

    public String toString(){
        return "\nName : " + name;
    }

}
