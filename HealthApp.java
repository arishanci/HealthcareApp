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
        
        JButton generalHealthButton = new JButton("General Health");
        generalHealthButton.setBounds(100, 200, 200, 30);

        // adds nav buttons to panel
        panel.add(userProfileButton);
        panel.add(mentalHealthButton);
        panel.add(vaccineRecordButton);
        panel.add(generalHealthButton);

        add(panel);

        // acction listener for accessing user profile
        userProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open UserProfile window
                UserProfile userProfile = new UserProfile();
                userProfile.setVisible(true);
            }
        });
        
        mentalHealthButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open MentalHealthTracker window
        new MentalHealthTracker().setVisible(true);
    }
});
        
        vaccineRecordButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Open MentalHealthTracker window
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

