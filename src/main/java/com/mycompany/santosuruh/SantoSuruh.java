package com.mycompany.santosuruh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.net.URI;
import javax.swing.border.*;
import java.util.List;
import java.util.ArrayList;

public class SantoSuruh extends JFrame {
    private static final String LOGO_PATH = "src/main/resources/logo.png";
    private static final String BG_PATH = "src/main/resources/background.jpg";
    private static final Color PRIMARY_COLOR = new Color(51, 153, 255);
    private static final Color SECONDARY_COLOR = new Color(255, 255, 255);
    private static final Font HEADER_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font REGULAR_FONT = new Font("Arial", Font.PLAIN, 14);
    
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private List<Service> services;
    private String currentUsername;
    private List<Order> orderHistory;
    private Map<String, User> registeredUsers;
    private static final String WHATSAPP_NUMBER = "6285156321404"; // Ganti dengan nomor WhatsApp yang sesuai

public class Order {
    private static int nextId = 1;  // Static counter for generating unique IDs
    private final int id;           // Unique ID for each order
    private Service service;
    private LocalDateTime dateTime;
    private double totalPrice;
    private int quantity;
    private String additionalInfo;
    private String status;
    private int rating;
    private String review;
    public void setRating(int rating) {
    this.rating = rating;
}
public void setRating(int rating, String review) {
    this.rating = rating;
    this.review = review;
}
    
    public Order(Service service, double totalPrice, int quantity, String additionalInfo) {
        this.id = nextId++;         // Assign and increment ID
        this.service = service;
        this.dateTime = LocalDateTime.now();
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.additionalInfo = additionalInfo;
        this.status = "ON_PROGRESS";
        this.rating = 0;
        this.review = "";
    }
    

        public String getFormattedDateTime() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            return dateTime.format(formatter);
        }

        // Getters
        public Service getService() { return service; }
        public LocalDateTime getDateTime() { return dateTime; }
        public double getTotalPrice() { return totalPrice; }
        public int getQuantity() { return quantity; }
        public String getAdditionalInfo() { return additionalInfo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getRating() { return rating; }
    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }
    public int getId() {
        return id;
    }
    // Existing getters remain the same...
}

    public SantoSuruh() {
        registeredUsers = new HashMap<>();
        services = initializeServices();
        orderHistory = new ArrayList<>();
        setupFrame();
        createLoginPanel();
    }


    private void setupFrame() {
        setTitle("SANTO SURUH - Aplikasi Layanan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image bg = ImageIO.read(new File(BG_PATH));
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    setBackground(new Color(240, 240, 240));
                }
            }
        };
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
    }

  private void createLoginPanel() {
    JPanel loginPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create modern gradient background for login panel
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(255, 255, 255, 240),
                0, getHeight(), new Color(240, 245, 255, 240)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            
            g2.dispose();
        }
    };
    
    loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
    loginPanel.setOpaque(false);
    loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

    // Create header panel
    JPanel headerPanel = createHeaderPanel();

    // Create form panel
    JPanel formPanel = new JPanel();
    formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
    formPanel.setOpaque(false);
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

    // Create input panels
    JPanel usernamePanel = createStyledInputPanel("Username");
    usernameField = new JTextField(20);
    styleTextField(usernameField);
    usernamePanel.add(usernameField);

    JPanel passwordPanel = createStyledInputPanel("Password");
    passwordField = new JPasswordField(20);
    styleTextField(passwordField);
    passwordPanel.add(passwordField);

    // Create button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    buttonPanel.setOpaque(false);
    
    JButton loginButton = createModernButton("Masuk");
    JButton registerButton = createModernButton("Daftar");
    
    loginButton.addActionListener(e -> handleLogin());
    registerButton.addActionListener(e -> showRegistrationDialog());
    
    buttonPanel.add(loginButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
    buttonPanel.add(registerButton);

    // Add components to form panel
    formPanel.add(usernamePanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 15)));
    formPanel.add(passwordPanel);
    formPanel.add(Box.createRigidArea(new Dimension(0, 25)));
    formPanel.add(buttonPanel);

    // Add panels to login panel
    loginPanel.add(headerPanel);
    loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));
    loginPanel.add(formPanel);

    // Add login panel to container panel
    JPanel containerPanel = new JPanel(new GridBagLayout());
    containerPanel.setOpaque(false);
    containerPanel.add(loginPanel);

    // Add container panel to main panel
    mainPanel.add(containerPanel, BorderLayout.CENTER);
    mainPanel.revalidate();
    mainPanel.repaint();
}

private JPanel createHeaderPanel() {
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.setOpaque(false);

    try {
        ImageIcon originalIcon = new ImageIcon(ImageIO.read(new File(LOGO_PATH)));
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(logoLabel);
    } catch (Exception e) {
        JLabel appNameLabel = new JLabel("SANTO SURUH");
        appNameLabel.setFont(new Font("Arial", Font.BOLD, 32));
        appNameLabel.setForeground(new Color(51, 51, 51));
        appNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(appNameLabel);
    }

    JLabel welcomeLabel = new JLabel("Selamat Datang");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    welcomeLabel.setForeground(new Color(51, 51, 51));
    welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    JLabel subLabel = new JLabel("Silakan masuk ke akun Anda");
    subLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    subLabel.setForeground(new Color(128, 128, 128));
    subLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    headerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    headerPanel.add(welcomeLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    headerPanel.add(subLabel);

    return headerPanel;
}
    // Helper method to style text fields
private void styleTextField(JTextField textField) {
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(8),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    ));
    textField.setPreferredSize(new Dimension(200, 35));
}
// Helper method to create styled input panels
private JPanel createStyledInputPanel(String labelText) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setOpaque(false);
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Label
    JLabel label = new JLabel(labelText);
    label.setFont(new Font("Arial", Font.BOLD, 12));
    label.setForeground(new Color(102, 102, 102));
    label.setAlignmentX(Component.LEFT_ALIGNMENT);
    panel.add(label);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));

    return panel;
}

private void createServicePanel() {
    mainPanel.removeAll();
    JPanel servicePanel = new JPanel();
    servicePanel.setLayout(new BoxLayout(servicePanel, BoxLayout.Y_AXIS));
    servicePanel.setOpaque(false);
    servicePanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

    // Modern header section with friendly welcome message
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.setOpaque(false);
    
    JPanel dashboardPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        dashboardPanel.setOpaque(false);

        JButton historyButton = createDashboardButton("Riwayat Pesanan", new Color(52, 152, 219));
        JButton statusButton = createDashboardButton("Status Pesanan", new Color(46, 204, 113));
        JButton logoutButton = createDashboardButton("Logout", new Color(231, 76, 60));

        historyButton.addActionListener(e -> showOrderHistory());
        statusButton.addActionListener(e -> showOrderStatus());
        logoutButton.addActionListener(e -> handleLogout());

        dashboardPanel.add(historyButton);
        dashboardPanel.add(statusButton);
        dashboardPanel.add(logoutButton);

    // Welcome text with modern typography
JLabel welcomeLabel = new JLabel("Halo, selamat datang kembali!");
welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
welcomeLabel.setForeground(Color.WHITE);
welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel userLabel = new JLabel(currentUsername);
userLabel.setFont(new Font("Arial", Font.BOLD, 32));
userLabel.setForeground(Color.GREEN);
userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel serviceLabel = new JLabel("Silakan pilih layanan yang Anda butuhkan.");
serviceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
serviceLabel.setForeground(Color.WHITE);
serviceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

 
    // Services Grid with compact cards
    JPanel servicesGrid = new JPanel(new GridLayout(0, 3, 20, 20)); // Changed to 3 columns
    servicesGrid.setOpaque(false);

    for (Service service : services) {
        servicesGrid.add(createModernServiceCard(service));
    }

    // Add all components with proper spacing
    headerPanel.add(welcomeLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
    headerPanel.add(userLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    headerPanel.add(serviceLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

    servicePanel.add(headerPanel);
    servicePanel.add(Box.createRigidArea(new Dimension(0, 20)));
    servicePanel.add(dashboardPanel);
    servicePanel.add(Box.createRigidArea(new Dimension(0, 30)));

    // Wrap services grid in scroll pane with custom styling
    JScrollPane scrollPane = new JScrollPane(servicesGrid);
    scrollPane.setOpaque(false);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    
    servicePanel.add(scrollPane);

    mainPanel.add(servicePanel);
    mainPanel.revalidate();
    mainPanel.repaint();
}

private JPanel createModernServiceCard(Service service) {
    JPanel card = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Create main background
            GradientPaint backgroundGradient = new GradientPaint(
                0, 0, new Color(255, 255, 255),
                0, getHeight(), new Color(245, 247, 250)
            );
            g2.setPaint(backgroundGradient);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);

            // Add subtle shadow effect
            g2.setColor(new Color(0, 0, 0, 20));
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

            g2.dispose();
        }
    };

    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    card.setOpaque(false);

    // Service Image (placeholder with service-specific image)
    JLabel imageLabel = new JLabel();
    try {
    String imagePath = "src/main/resources/services/" + service.getName().toLowerCase().replace(" ", "_") + ".png";
    ImageIcon originalIcon = new ImageIcon(ImageIO.read(new File(imagePath)));
    Image scaledImage = originalIcon.getImage().getScaledInstance(165, 125, Image.SCALE_SMOOTH);
    imageLabel.setIcon(new ImageIcon(scaledImage));
} catch (Exception e) {
        // Fallback to colored circle if image not found
        JPanel iconPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint iconGradient = new GradientPaint(
                    0, 0, new Color(51, 153, 255),
                    getWidth(), getHeight(), new Color(0, 119, 255)
                );
                g2.setPaint(iconGradient);
                g2.fillOval(0, 0, 50, 50);
                
                g2.dispose();
            }
        };
        iconPanel.setPreferredSize(new Dimension(50, 50));
        iconPanel.setMaximumSize(new Dimension(50, 50));
        imageLabel.add(iconPanel);
    }
    imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Service name with modern typography
    JLabel nameLabel = new JLabel(service.getName());
    nameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Reduced font size
    nameLabel.setForeground(new Color(51, 51, 51));
    nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Price with modern styling
    JLabel priceLabel = new JLabel(String.format("Rp. %,.0f", service.getPrice()));
    priceLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Reduced font size
    priceLabel.setForeground(new Color(51, 153, 255));
    priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Modern order button with proper padding
    JButton orderButton = new JButton("Pesan Sekarang") {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Button gradient
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(51, 153, 255),
                0, getHeight(), new Color(0, 119, 255)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Text
            g2.setFont(new Font("Arial", Font.BOLD, 12)); // Reduced font size
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            // Draw text shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.drawString(getText(), x + 1, y + 1);
            
            // Draw main text
            g2.setColor(Color.WHITE);
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
    };
    
    orderButton.setPreferredSize(new Dimension(120, 30)); // Reduced button size
    orderButton.setBorderPainted(false);
    orderButton.setContentAreaFilled(false);
    orderButton.setFocusPainted(false);
    orderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    orderButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    orderButton.addActionListener(e -> handleOrder(service));

    // Add components with proper spacing
    card.add(imageLabel);
    card.add(Box.createRigidArea(new Dimension(0, 10)));
    card.add(nameLabel);
    card.add(Box.createRigidArea(new Dimension(0, 5)));
    card.add(priceLabel);
    card.add(Box.createRigidArea(new Dimension(0, 10)));
    card.add(orderButton);

    return card;
}


    private JPanel createStyledPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255, 220));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(REGULAR_FONT);
        return field;
    }

private JButton createModernButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Button gradient
            GradientPaint gp = new GradientPaint(
                0, 0, new Color(51, 153, 255),
                0, getHeight(), new Color(0, 119, 255)
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Text shadow and main text
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            // Draw shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.drawString(getText(), x + 1, y + 1);
            
            // Draw main text
            g2.setColor(Color.WHITE);
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
    };
    
    button.setPreferredSize(new Dimension(160, 40));
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    return button;
}

private JButton createDashboardButton(String text, Color baseColor) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Create darker version of base color for gradient
            Color darkerColor = baseColor.darker();
            
            GradientPaint gp = new GradientPaint(
                0, 0, baseColor,
                0, getHeight(), darkerColor
            );
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            
            // Text
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            
            g2.setColor(Color.WHITE);
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
    };
    
    button.setPreferredSize(new Dimension(150, 40));
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    return button;
}
private void showRegistrationDialog() {
        JPanel registrationPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();
        
        registrationPanel.add(new JLabel("Username:"));
        registrationPanel.add(usernameField);
        registrationPanel.add(new JLabel("Password:"));
        registrationPanel.add(passwordField);
        registrationPanel.add(new JLabel("Konfirmasi Password:"));
        registrationPanel.add(confirmPasswordField);
        registrationPanel.add(new JLabel("Email:"));
        registrationPanel.add(emailField);
        registrationPanel.add(new JLabel("No. Telepon:"));
        registrationPanel.add(phoneField);
        
        int result = JOptionPane.showConfirmDialog(this, registrationPanel,
                "Registrasi Akun Baru", JOptionPane.OK_CANCEL_OPTION);
                
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();
            String phone = phoneField.getText();
            
            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Semua field harus diisi!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this,
                        "Password tidak cocok!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (registeredUsers.containsKey(username)) {
                JOptionPane.showMessageDialog(this,
                        "Username sudah terdaftar!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            registeredUsers.put(username, new User(username, password, email, phone));
            JOptionPane.showMessageDialog(this,
                    "Registrasi berhasil! Silakan login.",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

       private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.equals("admin") && password.equals("admin123")) {
            currentUsername = username;
            createServicePanel();
        } else if (registeredUsers.containsKey(username)) {
            User user = registeredUsers.get(username);
            if (user.getPassword().equals(password)) {
                currentUsername = username;
                createServicePanel();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Password salah!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username tidak ditemukan!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

 private void handleOrder(Service service) {
        // Panel untuk input detail pesanan
        JPanel orderPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField quantityField = new JTextField("1");
        JTextArea additionalInfoArea = new JTextArea(3, 20);
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();

        orderPanel.add(new JLabel("Jumlah:"));
        orderPanel.add(quantityField);
        orderPanel.add(new JLabel("Alamat:"));
        orderPanel.add(addressField);
        orderPanel.add(new JLabel("No. Telepon:"));
        orderPanel.add(phoneField);
        orderPanel.add(new JLabel("Informasi Tambahan:"));
        orderPanel.add(new JScrollPane(additionalInfoArea));

        int result = JOptionPane.showConfirmDialog(this, orderPanel, 
                "Detail Pesanan " + service.getName(), 
                JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                double totalPrice = service.getPrice() * quantity;
                String additionalInfo = "Alamat: " + addressField.getText() + "\n" +
                                     "No. Telepon: " + phoneField.getText() + "\n" +
                                     "Catatan: " + additionalInfoArea.getText();

                // Proses pembayaran
                if (processPayment(totalPrice)) {
                    // Buat objek Order baru
                    Order newOrder = new Order(service, totalPrice, quantity, additionalInfo);
                    orderHistory.add(newOrder);

                    // Tampilkan struk
                    showReceipt(newOrder);

                    // Tanya user apakah ingin menghubungi via WhatsApp
                    int whatsappChoice = JOptionPane.showConfirmDialog(this,
                            "Apakah Anda ingin menghubungi kami via WhatsApp?",
                            "Hubungi via WhatsApp",
                            JOptionPane.YES_NO_OPTION);

                    if (whatsappChoice == JOptionPane.YES_OPTION) {
                        openWhatsApp(service, newOrder);
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Jumlah harus berupa angka!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean processPayment(double totalPrice) {
        JPanel paymentPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField paymentField = new JTextField();
        
        paymentPanel.add(new JLabel("Total Pembayaran: "));
        paymentPanel.add(new JLabel(String.format("Rp. %,.0f", totalPrice)));
        paymentPanel.add(new JLabel("Jumlah yang dibayarkan: "));
        paymentPanel.add(paymentField);

        int result = JOptionPane.showConfirmDialog(this, paymentPanel,
                "Pembayaran",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double payment = Double.parseDouble(paymentField.getText());
                if (payment >= totalPrice) {
                    double change = payment - totalPrice;
                    JOptionPane.showMessageDialog(this,
                            String.format("Kembalian: Rp. %,.0f", change),
                            "Pembayaran Berhasil",
                            JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Pembayaran kurang!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Format pembayaran tidak valid!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private void showReceipt(Order order) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("=== STRUK PEMBAYARAN SANTO SURUH ===\n")
               .append("Tanggal: ").append(order.getFormattedDateTime()).append("\n")
               .append("Customer: ").append(currentUsername).append("\n")
               .append("--------------------------------\n")
               .append("Layanan: ").append(order.getService().getName()).append("\n")
               .append("Jumlah: ").append(order.getQuantity()).append("\n")
               .append("Harga per unit: Rp. ").append(String.format("%,.0f", order.getService().getPrice())).append("\n")
               .append("Total: Rp. ").append(String.format("%,.0f", order.getTotalPrice())).append("\n")
               .append("--------------------------------\n")
               .append("Detail Tambahan:\n").append(order.getAdditionalInfo()).append("\n")
               .append("--------------------------------\n")
               .append("Terima kasih telah menggunakan\n")
               .append("layanan SANTO SURUH!\n")
               .append("================================");

        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));

        JOptionPane.showMessageDialog(this,
                scrollPane,
                "Struk Pembayaran",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openWhatsApp(Service service, Order order) {
        try {
            String message = String.format("Halo, saya %s ingin mengonfirmasi pesanan:%n" +
                    "Layanan: %s%n" +
                    "Jumlah: %d%n" +
                    "Total: Rp. %,.0f%n" +
                    "%s",
                    currentUsername,
                    service.getName(),
                    order.getQuantity(),
                    order.getTotalPrice(),
                    order.getAdditionalInfo());

            String encodedMessage = java.net.URLEncoder.encode(message, "UTF-8");
            URI uri = new URI("https://api.whatsapp.com/send?phone=" + WHATSAPP_NUMBER + "&text=" + encodedMessage);
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Gagal membuka WhatsApp: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
private void showOrderHistory() {
    if (orderHistory.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Belum ada riwayat pesanan.",
                "Riwayat Pesanan",
                JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    StringBuilder history = new StringBuilder();
    history.append("=== RIWAYAT PESANAN ===\n\n");

    for (int i = 0; i < orderHistory.size(); i++) {
        Order order = orderHistory.get(i);
        history.append("Pesanan #").append(i + 1).append("\n")
               .append("Tanggal: ").append(order.getFormattedDateTime()).append("\n")
               .append("Layanan: ").append(order.getService().getName()).append("\n")
               .append("Jumlah: ").append(order.getQuantity()).append("\n")
               .append("Total: Rp. ").append(String.format("%,.0f", order.getTotalPrice())).append("\n");
        
        if (!order.getReview().isEmpty()) {
            history.append("Review: ").append(order.getReview()).append("\n");
        }
        history.append("--------------------------------\n");
    }

    JTextArea historyArea = new JTextArea(history.toString());
    historyArea.setEditable(false);
    historyArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

    JScrollPane scrollPane = new JScrollPane(historyArea);
    scrollPane.setPreferredSize(new Dimension(400, 500));

    JOptionPane.showMessageDialog(this,
            scrollPane,
            "Riwayat Pesanan",
            JOptionPane.INFORMATION_MESSAGE);
}

 private void showOrderStatus() {
    if (orderHistory.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Belum ada pesanan aktif.",
                "Status Pesanan",
                JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    JDialog statusDialog = new JDialog(this, "Status Pesanan", true);
    statusDialog.setLayout(new BorderLayout());
    
    // Main container with padding
    JPanel mainContainer = new JPanel();
    mainContainer.setLayout(new BoxLayout(mainContainer, BoxLayout.Y_AXIS));
    mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    mainContainer.setBackground(Color.WHITE);

    for (int i = 0; i < orderHistory.size(); i++) {
        Order order = orderHistory.get(i);
        mainContainer.add(createModernOrderCard(order, i + 1));
        mainContainer.add(Box.createRigidArea(new Dimension(0, 15)));
    }

    JScrollPane scrollPane = new JScrollPane(mainContainer);
    scrollPane.setBorder(null);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    scrollPane.setPreferredSize(new Dimension(450, 500));

    statusDialog.add(scrollPane);
    statusDialog.pack();
    statusDialog.setLocationRelativeTo(this);
    statusDialog.setVisible(true);
}

private JPanel createModernOrderCard(Order order, int orderNumber) {
    JPanel card = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.setColor(new Color(230, 230, 230));
            g2.setStroke(new BasicStroke(1f));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g2.dispose();
        }
    };
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
    card.setOpaque(false);

    // Header Panel (Order number and date)
    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setOpaque(false);
    JLabel orderNumberLabel = new JLabel("Pesanan #" + orderNumber);
    orderNumberLabel.setFont(new Font("Inter", Font.BOLD, 14));
    orderNumberLabel.setForeground(new Color(51, 51, 51));
    JLabel dateLabel = new JLabel(order.getFormattedDateTime());
    dateLabel.setFont(new Font("Inter", Font.PLAIN, 12));
    dateLabel.setForeground(new Color(128, 128, 128));
    headerPanel.add(orderNumberLabel, BorderLayout.WEST);
    headerPanel.add(dateLabel, BorderLayout.EAST);

    // Service info (centered)
    JPanel servicePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    servicePanel.setOpaque(false);
    JLabel serviceLabel = new JLabel(order.getService().getName());
    serviceLabel.setFont(new Font("Inter", Font.PLAIN, 13));
    serviceLabel.setForeground(new Color(51, 51, 51));
    serviceLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
    servicePanel.add(serviceLabel);

    // Status Panel (centered)
    JPanel statusContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
    statusContainer.setOpaque(false);
    JPanel statusPanel = createStatusBadge(order.getStatus());
    statusContainer.add(statusPanel);

    // Complete Order Button (only shown for ON_PROGRESS status)
    JButton completeButton = null;
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    if (order.getStatus().equals("ON_PROGRESS")) {
        completeButton = createMinimalistButton("Selesaikan Pesanan");
        completeButton.addActionListener(e -> showCompletionDialog(order));
        buttonPanel.add(completeButton);
    }

    // Price (centered)
    JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    pricePanel.setOpaque(false);
    JLabel priceLabel = new JLabel(String.format("Rp. %,d", (int)order.getTotalPrice()));
    priceLabel.setFont(new Font("Inter", Font.BOLD, 13));
    priceLabel.setForeground(new Color(51, 51, 51));
    pricePanel.add(priceLabel);

    // Rating display (only for COMPLETED status)
    JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
    ratingPanel.setOpaque(false);
    if (order.getStatus().equals("COMPLETED") && order.getRating() > 0) {
        ratingPanel.add(new JLabel("Rating: "));
        for (int i = 0; i < order.getRating(); i++) {
            JLabel star = new JLabel("★");
            star.setForeground(new Color(255, 193, 7));
            star.setFont(new Font("Inter", Font.PLAIN, 12));
            ratingPanel.add(star);
        }
    }

    // Add components to card
    card.add(headerPanel);
    card.add(servicePanel);
    card.add(Box.createRigidArea(new Dimension(0, 5)));
    card.add(statusContainer);
    if (completeButton != null) {
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(buttonPanel);
    }
    card.add(Box.createRigidArea(new Dimension(0, 5)));
    card.add(pricePanel);
    if (order.getStatus().equals("COMPLETED") && order.getRating() > 0) {
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(ratingPanel);
    }

    return card;
}

private JPanel createStatusBadge(String status) {
    JPanel badge = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Color bgColor = status.equals("COMPLETED") 
                ? new Color(209, 231, 221)  // Light green for completed
                : new Color(255, 243, 205); // Light yellow for on-progress
            
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            g2.dispose();
        }
    };
    badge.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 2));
    
    String displayStatus = status.equals("ON_PROGRESS") ? "Dalam Proses" : "Selesai";
    JLabel statusLabel = new JLabel(displayStatus);
    Color textColor = status.equals("COMPLETED")
        ? new Color(15, 81, 50)    // Dark green for completed
        : new Color(102, 77, 3);   // Dark yellow for on-progress
    
    statusLabel.setForeground(textColor);
    statusLabel.setFont(new Font("Inter", Font.PLAIN, 12));
    
    badge.add(statusLabel);
    badge.setOpaque(false);
    badge.setPreferredSize(new Dimension(100, 24));
    
    return badge;
}

private JDialog currentDialog = null;  // Class level variable to track current dialog
private JPanel orderCardsPanel; // Panel yang berisi kartu-kartu order

private void showCompletionDialog(Order order) {
    // Close any existing dialog
    if (currentDialog != null && currentDialog.isVisible()) {
        currentDialog.dispose();
    }

    // Create new dialog
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Selesaikan Pesanan", true);
    currentDialog = dialog;  // Store reference to current dialog
    
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(400, 200);
    dialog.setLocationRelativeTo(null);

    // Main panel with padding
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Confirmation message
    JLabel messageLabel = new JLabel("Apakah anda yakin ingin menyelesaikan pesanan ini?");
    messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    messageLabel.setFont(new Font("Inter", Font.PLAIN, 14));

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
    
    // Confirm button
    JButton confirmButton = new JButton("Ya, Selesaikan");
    confirmButton.setFont(new Font("Inter", Font.PLAIN, 12));
    confirmButton.addActionListener(e -> {
        dialog.dispose();
        // Show rating dialog before completing the order
        showModernRatingDialog(order, () -> {
            try {
                // Update order status
                order.setStatus("COMPLETED");
                
                // Remove and re-add the order card to update UI
                Container parent = getParent();
                if (parent != null) {
                    parent.remove(this);
                    parent.add(createModernOrderCard(order, order.getId()));
                    parent.revalidate();
                    parent.repaint();
                }
                
                // Show success message
                JOptionPane.showMessageDialog(
                    this,
                    "Pesanan berhasil diselesaikan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyelesaikan pesanan: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    });

    // Cancel button
    JButton cancelButton = new JButton("Batal");
    cancelButton.setFont(new Font("Inter", Font.PLAIN, 12));
    cancelButton.addActionListener(e -> dialog.dispose());

    // Add components
    buttonPanel.add(confirmButton);
    buttonPanel.add(cancelButton);

    mainPanel.add(Box.createVerticalGlue());
    mainPanel.add(messageLabel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(buttonPanel);
    mainPanel.add(Box.createVerticalGlue());

    dialog.add(mainPanel);
    dialog.setVisible(true);
}

private void showModernRatingDialog(Order order, Runnable onComplete) {
    JDialog ratingDialog = new JDialog(this, "Beri Rating", true);
    ratingDialog.setLayout(new BorderLayout());
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
    mainPanel.setBackground(Color.WHITE);

    // Rating stars
    JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    starsPanel.setOpaque(false);
    ButtonGroup ratingGroup = new ButtonGroup();
    JRadioButton[] stars = new JRadioButton[5];
    
    for (int i = 0; i < 5; i++) {
        final int rating = i + 1;
        stars[i] = new JRadioButton("★") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isSelected()) {
                    setForeground(new Color(255, 193, 7));
                } else {
                    setForeground(new Color(200, 200, 200));
                }
            }
        };
        stars[i].setFont(new Font("Inter", Font.PLAIN, 24));
        stars[i].setOpaque(false);
        stars[i].setFocusPainted(false);
        stars[i].addActionListener(e -> order.setRating(rating));
        ratingGroup.add(stars[i]);
        starsPanel.add(stars[i]);
    }

    // Review text area
    JTextArea reviewArea = new JTextArea(4, 25);
    reviewArea.setFont(new Font("Inter", Font.PLAIN, 13));
    reviewArea.setLineWrap(true);
    reviewArea.setWrapStyleWord(true);
    reviewArea.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(8),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    
    JPanel reviewPanel = new JPanel();
    reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
    reviewPanel.setOpaque(false);
    
    JLabel reviewLabel = new JLabel("Tulis review Anda (opsional)");
    reviewLabel.setFont(new Font("Inter", Font.PLAIN, 12));
    reviewLabel.setForeground(new Color(102, 102, 102));
    reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    reviewPanel.add(reviewLabel);
    reviewPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    reviewPanel.add(reviewArea);

    // Submit button
    JButton submitButton = createMinimalistButton("Kirim");
    submitButton.setPreferredSize(new Dimension(120, 35));
    submitButton.addActionListener(e -> {
        if (order.getRating() == 0) {
            JOptionPane.showMessageDialog(ratingDialog,
                    "Mohon berikan rating terlebih dahulu",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        order.setReview(reviewArea.getText().trim());
        ratingDialog.dispose();
        // Execute completion callback
        onComplete.run();
    });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    buttonPanel.add(submitButton);

    mainPanel.add(starsPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(reviewPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(buttonPanel);

    ratingDialog.add(mainPanel);
    ratingDialog.pack();
    ratingDialog.setLocationRelativeTo(this);
    ratingDialog.setVisible(true);
}

private JButton createMinimalistButton(String text) {
    JButton button = new JButton(text) {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (getModel().isPressed()) {
                g2.setColor(new Color(0, 110, 230));
            } else if (getModel().isRollover()) {
                g2.setColor(new Color(30, 140, 255));
            } else {
                g2.setColor(new Color(0, 122, 255));
            }
            
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Inter", Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
            g2.drawString(getText(), x, y);
            
            g2.dispose();
        }
    };
    
    button.setPreferredSize(new Dimension(100, 30));
    button.setBorderPainted(false);
    button.setContentAreaFilled(false);
    button.setFocusPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
    return button;
}

private void showModernRatingDialog(Order order) {
    JDialog ratingDialog = new JDialog(this, "Beri Rating", true);
    ratingDialog.setLayout(new BorderLayout());
    
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
    mainPanel.setBackground(Color.WHITE);

    // Title Panel untuk memastikan judul muncul
    JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    titlePanel.setOpaque(false);
    
    // Title Label dengan properti tambahan
    JLabel titleLabel = new JLabel("Beri Rating");
    titleLabel.setFont(new Font("Inter", Font.BOLD, 18));
    titleLabel.setForeground(Color.BLACK); // Memastikan warna teks terlihat
    titleLabel.setOpaque(false);
    
    titlePanel.add(titleLabel);
    
    // Rating stars
    JPanel starsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    starsPanel.setOpaque(false);
    ButtonGroup ratingGroup = new ButtonGroup();
    JRadioButton[] stars = new JRadioButton[5];
    
    for (int i = 0; i < 5; i++) {
        final int rating = i + 1;
        stars[i] = new JRadioButton("★") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (isSelected()) {
                    setForeground(new Color(255, 193, 7));
                } else {
                    setForeground(new Color(200, 200, 200));
                }
            }
        };
        stars[i].setFont(new Font("Inter", Font.PLAIN, 24));
        stars[i].setOpaque(false);
        stars[i].setFocusPainted(false);
        stars[i].addActionListener(e -> order.setRating(rating));
        ratingGroup.add(stars[i]);
        starsPanel.add(stars[i]);
    }

    // Review text area
    JTextArea reviewArea = new JTextArea(4, 25);
    reviewArea.setFont(new Font("Inter", Font.PLAIN, 13));
    reviewArea.setLineWrap(true);
    reviewArea.setWrapStyleWord(true);
    reviewArea.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(8),
        BorderFactory.createEmptyBorder(10, 10, 10, 10)
    ));
    
    JPanel reviewPanel = new JPanel();
    reviewPanel.setLayout(new BoxLayout(reviewPanel, BoxLayout.Y_AXIS));
    reviewPanel.setOpaque(false);
    
    JLabel reviewLabel = new JLabel("Tulis review Anda (opsional)");
    reviewLabel.setFont(new Font("Inter", Font.PLAIN, 12));
    reviewLabel.setForeground(new Color(102, 102, 102));
    reviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    reviewPanel.add(reviewLabel);
    reviewPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    reviewPanel.add(reviewArea);

    // Submit button
    JButton submitButton = createMinimalistButton("Kirim");
    submitButton.setPreferredSize(new Dimension(120, 35));
    submitButton.addActionListener(e -> {
        if (order.getRating() == 0) {
            JOptionPane.showMessageDialog(ratingDialog,
                    "Mohon berikan rating terlebih dahulu",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        order.setReview(reviewArea.getText().trim());
        ratingDialog.dispose();
    });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    buttonPanel.setOpaque(false);
    buttonPanel.add(submitButton);

    // Menambahkan komponen ke mainPanel
    mainPanel.add(titlePanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    mainPanel.add(starsPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(reviewPanel);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
    mainPanel.add(buttonPanel);

    ratingDialog.add(mainPanel);
    ratingDialog.pack();
    ratingDialog.setLocationRelativeTo(this);
    ratingDialog.setVisible(true);
}


private void handleLogout() {
    int confirm = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION);
            
    if (confirm == JOptionPane.YES_OPTION) {
        currentUsername = null;
        mainPanel.removeAll();
        createLoginPanel();
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
  
   private void showReviewDialog(Order order) {
    JPanel reviewPanel = new JPanel(new BorderLayout());
    reviewPanel.add(new JLabel("Berikan ulasan Anda:"), BorderLayout.NORTH);
    
    JTextArea reviewArea = new JTextArea(5, 30);
    reviewArea.setLineWrap(true);
    reviewArea.setWrapStyleWord(true);
    JScrollPane scrollPane = new JScrollPane(reviewArea);
    reviewPanel.add(scrollPane, BorderLayout.CENTER);
    
    int result = JOptionPane.showConfirmDialog(this,
            reviewPanel,
            "Review Layanan",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE);
            
    if (result == JOptionPane.OK_OPTION) {
        String review = reviewArea.getText().trim();
        if (!review.isEmpty()) {
            order.setReview(review);
            JOptionPane.showMessageDialog(this,
                    "Terima kasih atas ulasan Anda!",
                    "Review Berhasil",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
} 

private java.util.List<Service> initializeServices() {
    java.util.List<Service> serviceList = new ArrayList<>();
    serviceList.add(new CleaningService());
    serviceList.add(new MovingService());
    serviceList.add(new TransportService());
    serviceList.add(new FullHomeCleaning());
    serviceList.add(new ExerciseCompanion());
    serviceList.add(new PetCleaning());
    return serviceList;
}

private static class RoundedBorder extends AbstractBorder {
    private final int radius;
    private final Color borderColor;

    RoundedBorder(int radius) {
        this.radius = radius;
        this.borderColor = new Color(200, 200, 200);
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        g2.dispose();
    }
}
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new SantoSuruh().setVisible(true);
        });
    }
}