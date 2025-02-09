package com.mycompany.santosuruh;

public class CleaningService extends Service {
    public CleaningService() {
        super("Jasa Kebersihan", 50000);
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan pembersihan rumah dengan peralatan lengkap";
    }
    
    @Override
    public int getEstimatedDuration() {
        return 120; // 2 jam
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}