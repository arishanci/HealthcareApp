/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 12/11/24
 * UserProfile.java
 */
import javax.swing.*;

public class UserProfile extends JFrame {

    // Constructor
    public UserProfile() {
        // Main frame
        setTitle("User Profile");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setLocationRelativeTo(null);

        // Main panel layout
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // all labels with text fields of user info
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(100, 30, 150, 25); //creates the bounds for fields

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30, 70, 100, 25);
        JTextField ageField = new JTextField();
        ageField.setBounds(100, 70, 150, 25);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(30, 110, 100, 25);
        JTextField locationField = new JTextField();
        locationField.setBounds(100, 110, 150, 25);
        
        JButton saveButton = new JButton("Save"); //save profile
        saveButton.setBounds(100, 180, 80, 30);

        // adds above labels to panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(saveButton);

        add(panel);
    }
}

