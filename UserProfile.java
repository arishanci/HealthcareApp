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
import java.io.*;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class UserProfile extends JFrame implements UserData {
    private JTextField nameField, ageField, locationField;
    private final JFrame mainMenu;
    
    public UserProfile(JFrame mainMenu) {
        this.mainMenu = mainMenu;

        // main frame gui
        setTitle("User Profile");
        setSize(300, 330);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel layout
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Label and text field setup
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        nameField = new JTextField();
        nameField.setBounds(100, 30, 150, 25);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30, 70, 100, 25);
        ageField = new JTextField();
        ageField.setBounds(100, 70, 150, 25);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setBounds(30, 110, 100, 25);
        locationField = new JTextField();
        locationField.setBounds(100, 110, 150, 25);

        JButton saveButton = new JButton("Save"); // Save profile button
        saveButton.setBounds(50, 180, 80, 30);
        JButton loadButton = new JButton("Load");
        loadButton.setBounds(150, 180, 80, 30);
        JButton backButton = new JButton("Back");
        backButton.setBounds(100, 220, 80, 30);
        

        // Add all labels and buttons to panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(locationLabel);
        panel.add(locationField);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(backButton);

        add(panel); //create panel

        // create file and manager instance
        File profileFile = new File("userProfile.data");
        UserProfileManager manager = new UserProfileManager(profileFile);

        // Save button action
        saveButton.addActionListener(e -> {
            try {
                // validation
                String name = nameField.getText().trim();
                if (name.isEmpty() || name.matches(".*\\d.*")) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid name.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int age = 0;
                try {
                    age = Integer.parseInt(ageField.getText().trim());
                    if (age <= 0 || age > 100) {
                        throw new NumberFormatException("Age must be between 1 and 100.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid age (1-100).", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String location = locationField.getText().trim();
                if (location.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Location cannot be empty.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // if all inputs met save profile
                UserProfileData profile = new UserProfileData(name, age, location);
                manager.saveUserProfile(profile);
                JOptionPane.showMessageDialog(this, "Profile saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save profile: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

             this.setVisible(false);
            mainMenu.setVisible(true);
        });

        // Load button action
        loadButton.addActionListener(e -> {
            try {
                List<UserProfileData> profiles = manager.loadAllProfiles();

                if (profiles.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No profiles found.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // Display the last saved profile
                    UserProfileData lastProfile = profiles.get(profiles.size() - 1);
                    nameField.setText(lastProfile.getName());
                    ageField.setText(String.valueOf(lastProfile.getAge()));
                    locationField.setText(lastProfile.getLocation());
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Failed to load profiles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
            backButton.addActionListener(e -> {
            this.setVisible(false); // Hide the UserProfile frame
            mainMenu.setVisible(true); // Show the main menu
        });
 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainMenu = new JFrame("Main Menu"); // Main menu frame
            mainMenu.setSize(400, 400);
            mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainMenu.setLocationRelativeTo(null);

            JButton openProfileButton = new JButton("Open User Profile");
            openProfileButton.addActionListener(e -> {
                UserProfile userProfile = new UserProfile(mainMenu); // Pass main menu frame to user profile
                userProfile.setVisible(true);
                mainMenu.setVisible(false); // Hide . main menu when profile is opened
            });

            mainMenu.add(openProfileButton);
            mainMenu.setLayout(null);
            openProfileButton.setBounds(150, 150, 150, 30);

            mainMenu.setVisible(true);
        });
    }
    
    public String getDetails() { //for  overview.java to grab details from
        StringBuilder details = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader("userProfile.data"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                details.append(line).append("\n");
            }
        } catch (IOException e) {
            details.append("Failed to load user profile data.\n");
        }
        return details.toString();
    }
    
    public void saveData() {
        String userDetails = getDetails(); // Get the details to save

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("userProfile.data"))) {
            writer.write(userDetails); // Write the details to the file
        } catch (IOException e) {
            System.out.println("Failed to save user profile data: " + e.getMessage());
        }
    }
    
}
