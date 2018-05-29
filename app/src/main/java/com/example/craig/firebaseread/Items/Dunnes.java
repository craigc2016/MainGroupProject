package com.example.craig.firebaseread.Items;

/*
This class is used for the item object for the dunnes items table
It sets the variables and has a toString method needed for to
turn the objects to display in text format for the list view
 */
public class Dunnes {
    private String name;
    private double price;
    private String key;
    /*
   Blank constructor needed for to query the firebase database
    */
    public Dunnes(){

    }
    /*
   constructor that sets the variables of the new item object
    */
    public Dunnes(String name,double price){
        this.name = name;
        this.price = price;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return "\nName : " + name + "\nPrice : " + "€"+String.format("%.2f", price) +"\nShopName : " + "Dunnes";
    }




}
