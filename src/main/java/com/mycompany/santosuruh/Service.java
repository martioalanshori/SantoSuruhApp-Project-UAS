/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.santosuruh;

/**
 *
 * @author DedSec
 */
public abstract class Service {
    private String name;
    private double price;
    
    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    // Abstract methods yang harus diimplementasikan oleh child classes
    public abstract String getServiceDescription();
    public abstract int getEstimatedDuration(); // dalam menit
    public abstract boolean isAvailable(String location);
    
    // Getter methods
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
}
