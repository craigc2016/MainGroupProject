package com.example.craig.firebaseread.Items;


/*
This class is used for the item object for the lidl items table
It sets the variables and has a toString method needed for to
turn the objects to display in text format for the list view
 */
public class Lidl {
    private String name;
    private double price;
    /*
   Blank constructor needed for to query the firebase database
    */
    public Lidl(){

    }
    /*
    constructor that sets the variables of the new item object
     */
    public Lidl(String name,double price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return "\nName : " + name + "\nPrice : " + "€"+String.format("%.2f", price) +"\nShopName : " + "Lidl";
    }

}
