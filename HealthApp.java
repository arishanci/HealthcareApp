/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 12/11/24
 * HealthApp.java
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthApp extends JFrame {
    
    // Constructor
    public HealthApp() {
        // main frame
        setTitle("Health Application");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // create nav buttons
        JButton userProfileButton = new JButton("User Profile");
        userProfileButton.setBounds(100, 50, 200, 30);
        
        JButton mentalHealthButton = new JButton("Mental Health Tracker");
        mentalHealthButton.setBounds(100, 100, 200, 30);
        
        JButton vaccineRecordButton = new JButton("Vaccine Record");
        vaccineRecordButton.setBounds(100, 150, 200, 30);
        

        // adds nav buttons to panel
        panel.add(userProfileButton);
        panel.add(mentalHealthButton);
        panel.add(vaccineRecordButton);

        add(panel);

        // acction listener for accessing user profile
        userProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            UserProfile userProfile = new UserProfile(HealthApp.this); // Pass current frame as main menu
            userProfile.setVisible(true);
            dispose(); // Hide or close the current frame
            }
        });

        
        mentalHealthButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        dispose();  // Close the main menu        new MentalHealthTracker(mainMenu).setVisible(true);  // Open Mental Health Tracker and pass main menu
            }
        });

        
        vaccineRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VaccinationTracker().setVisible(true);
            }
        });
    }
    
    // main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HealthApp().setVisible(true);
            }
        });
    }
}

