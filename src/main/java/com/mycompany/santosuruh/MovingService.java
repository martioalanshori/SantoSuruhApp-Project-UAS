package com.mycompany.santosuruh;

public class MovingService extends Service {
    private double weight;
    
    public MovingService() {
        super("Mengangkat Barang", 130000);
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan pindahan dan angkut barang dengan tim profesional";
    }
    
    @Override
    public int getEstimatedDuration() {
        return 180; // 3 jam
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}