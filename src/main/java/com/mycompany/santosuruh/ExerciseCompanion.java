package com.mycompany.santosuruh;

public class ExerciseCompanion extends Service {
    private int duration;

    public ExerciseCompanion() {
        super("Nemenin Jalan", 36000);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public double calculatePrice() {
        return Math.ceil(duration / 30.0) * 36000;
    }
    
    @Override
    public String getServiceDescription() {
        return "Layanan teman olahraga dengan instruktur berpengalaman";
    }
    
    @Override
    public int getEstimatedDuration() {
        return duration > 0 ? duration : 90; // default 90 menit jika duration belum diset
    }
    
    @Override
    public boolean isAvailable(String location) {
        return true; // Implementasi default
    }
}