package com.mycompany.santosuruh;

public class FullHomeCleaning extends Service {
    private int roomCount;

    public FullHomeCleaning() {
        super("Full Home Cleaning", 300000);
    }

    public void setRoomCount(int roomCount) {
        this.roomCount = roomCount;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public double calculatePrice() {
        return roomCount > 5 ? 300000 + (roomCount - 5) * (300000 / 5) : 300000;
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan pembersihan menyeluruh untuk seluruh bagian rumah";
    }
    
    @Override
    public int getEstimatedDuration() {
        return 300; // 5 jam
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}