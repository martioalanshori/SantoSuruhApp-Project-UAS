package com.mycompany.santosuruh;

public class PetCleaning extends Service {
    public PetCleaning() {
        super("Membersihkan Kandang Hewan", 68000);
    }

    public double calculatePrice(int cages) {
        return cages * 68000;
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan pembersihan kandang hewan peliharaan";
    }
    
    @Override
    public int getEstimatedDuration() {
        return 90; // 1.5 jam
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}