 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package healthapp;

/**
 * @author arisha mirza
 * date: 22/11/24
 * HealthApp.java
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthApp extends JFrame {
    
    public HealthApp() {
        // Main frame setup
        setTitle("Health Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(173, 216, 230)); 
        
        // Main panel with a nice border
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10)); // Grid layout for better spacing
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        
        // Create nav buttons with modern look
        JButton userProfileButton = createButton("User Profile");
        JButton mentalHealthButton = createButton("Mental Health Tracker");
        JButton vaccineRecordButton = createButton("Vaccine Record");
        JButton overviewButton = createButton("Overview");

        // Add buttons to the panel
        panel.add(userProfileButton);
        panel.add(mentalHealthButton);
        panel.add(vaccineRecordButton);
        panel.add(overviewButton);

        add(panel);

        // Action listeners for buttons
        userProfileButton.addActionListener(e -> {
            UserProfile userProfile = new UserProfile(this);  // Pass 'this' (HealthApp instance)
            userProfile.setVisible(true);
            setVisible(false);  // Hide the main menu when user profile is opened
        });

        mentalHealthButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose(); // Close the main menu
        new MentalHealthTracker(HealthApp.this).setVisible(true); // Pass the main menu to the tracker
            }
        });

        vaccineRecordButton.addActionListener(e -> {
            dispose();
            new VaccinationTracker().setVisible(true);
        });

        overviewButton.addActionListener(e -> {
            new Overview().setVisible(true);
        });
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(70, 130, 180)); // Steel Blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding inside button
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Change color on hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Change to a lighter blue
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180)); // Revert to original color
            }
        });
        
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthApp().setVisible(true));
    }
}