/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package healthapp;

/**
 * @author ArishaMirza
 * 5/11/2024
 * 
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthApp extends JFrame {
    
    // Constructor to set up the main frame
    public HealthApp() {
        // Set up the frame
        setTitle("Health Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with layout
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create navigation buttons
        JButton userProfileButton = new JButton("User Profile");
        userProfileButton.setBounds(100, 50, 200, 30);
        
        JButton mentalHealthButton = new JButton("Mental Health Tracker");
        mentalHealthButton.setBounds(100, 100, 200, 30);
        
        JButton vaccineRecordButton = new JButton("Vaccine Record");
        vaccineRecordButton.setBounds(100, 150, 200, 30);

        // Add buttons to panel
        panel.add(userProfileButton);
        panel.add(mentalHealthButton);
        panel.add(vaccineRecordButton);

        // Add panel to frame
        add(panel);

        // Button action listeners
        userProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open UserProfile window
                UserProfile userProfile = new UserProfile();
                userProfile.setVisible(true);
            }
        });

        // You can add more action listeners for other buttons when the UI for those sections is ready
    }
    
    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HealthApp().setVisible(true);
            }
        });
    }
}

