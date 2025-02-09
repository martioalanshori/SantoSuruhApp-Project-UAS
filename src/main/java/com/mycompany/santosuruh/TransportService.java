package com.mycompany.santosuruh;

public class TransportService extends Service {
    private double distance;
    
    public TransportService() {
        super("Antar Jemput", 36000);
    }
    
    public void setDistance(double distance) {
        this.distance = distance;
    }
    
    public double getDistance() {
        return distance;
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan antar jemput dengan berbagai pilihan kendaraan";
    }
    
    @Override
    public int getEstimatedDuration() {
        return 60; // 1 jam
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}