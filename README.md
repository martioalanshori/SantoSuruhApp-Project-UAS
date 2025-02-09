# 🌟 Santo Suruh App
![alt text](https://github.com/martioalanshori/SantoSuruhApp-Project-UAS/blob/main/loginsantosuruh.png?raw=true)
![alt text](https://github.com/martioalanshori/SantoSuruhApp-Project-UAS/blob/main/menusantosuruh.png?raw=true)

![Java](https://img.shields.io/badge/Java-17-orange)
![Netbeans](https://img.shields.io/badge/Netbeans-17-blue)
![License](https://img.shields.io/badge/license-MIT-green)

Santo Suruh App adalah aplikasi desktop berbasis Java yang dikembangkan untuk membantu digitalisasi layanan jasa Santo Suruh. Aplikasi ini dibuat sebagai bagian dari proyek UAS Pemrograman Berbasis Objek 1.

## 📋 Fitur Utama

- **Sistem Autentikasi**: Login dan registrasi pengguna
- **Katalog Layanan**: Berbagai jasa yang tersedia
- **Sistem Pemesanan**: Proses order yang mudah dan intuitif
- **Manajemen Pesanan**: Tracking status dan history pesanan
- **Sistem Pembayaran**: Multiple payment methods
- **Rating & Review**: Feedback sistem untuk peningkatan layanan

## 🔧 Teknologi

- Java 17
- Java Swing untuk GUI
- Object-Oriented Programming (OOP)
- Maven untuk dependency management

## ⚙️ Instalasi

1. Clone repository:
```bash
git clone https://github.com/username/SantoSuruhApp-Project-UAS.git
```

2. Buka project di Netbeans IDE:
   - File > Open Project
   - Pilih folder project

3. Build project:
   - Clean and Build (Shift + F11)

4. Run aplikasi:
   - Run project (F6)
   - Atau jalankan file `SantoSuruh.java`

## 🚀 Penggunaan

### Credentials Demo
- Username: admin
- Password: admin123

### Menu Utama
1. Login menggunakan kredensial
2. Pilih layanan yang diinginkan
3. Isi detail pesanan
4. Pilih metode pembayaran
5. Konfirmasi pesanan

## 📚 Implementasi OOP

### 1. Polymorphism
```java
public class OrderProcessor {
    private final Service service;
    
    public void processOrder() {
        double price = service.calculatePrice();
        String description = service.getServiceDescription();
    }
}
```

### 2. Abstract Class & Method
```java
public abstract class Service {
    private String name;
    private double price;
    
    public abstract String getServiceDescription();
    public abstract int getEstimatedDuration();
}
```

### 3. Inheritance & Overriding
```java
public class CleaningService extends Service {
    @Override
    public String getServiceDescription() {
        return "Layanan pembersihan rumah";
    }
}
```

## 📝 Struktur Project

```
santo-suruh/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── mycompany/
│   │   │           └── santosuruh/
│   │   │               ├── model/
│   │   │               ├── view/
│   │   │               └── controller/
│   │   └── resources/
│   └── test/
└── pom.xml
```

## 👥 Tim Pengembang

- Muhammad Martio Al Anshori (23552011350)

## 📄 Lisensi

Project ini dilisensikan di bawah Lisensi MIT - lihat file [LICENSE.md](LICENSE.md) untuk detail.

## 🙏 Acknowledgments

- Terima kasih kepada Bapak Rudhi Wahyudi Febrianto, S.Kom., M.Kom. selaku dosen pembimbing
- Inspire by Santo Suruh original business by Susanto
- Materi pembelajaran dari e-learning UTB

## 📞 Kontak

Untuk pertanyaan dan saran, silakan hubungi:
- Email: martioalanshori@gmail.com
- LinkedIn: https://www.linkedin.com/in/martioalanshori
