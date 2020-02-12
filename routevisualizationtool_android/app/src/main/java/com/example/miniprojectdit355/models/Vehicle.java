package com.example.miniprojectdit355.models;

public class Vehicle {
    int number;
    int color;
    public Vehicle(){

    }
    public Vehicle(int number,int color){
        this.number=number;
        this.color=color;
    }

    public int getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
