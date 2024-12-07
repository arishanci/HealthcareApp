/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza & rodrigo bonorino
 * date: 12/11/24
 * Overview.java
 */

import javax.swing.*;
import java.io.*;
import java.util.List;

// Superclass
abstract class User {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Abstract method
    public abstract String getDetails();
    
    // method to save data
    public abstract void saveData();
}

// tracks vaccine data
class VaccinationUser extends User {
    private String lastVaccinationDetails;

    public VaccinationUser(String name) {
        super(name);
    }
    //set vaccinedetails
    public void setLastVaccinationDetails(String details) {
        this.lastVaccinationDetails = details;
    }
    
    //override to getDetails from userprofile/vaccinationtracker
    @Override
    public String getDetails() {
        return "User: " + getName() + "\n" + "Last Vaccination: " + lastVaccinationDetails + "\n";
    }
    
    //saves vaccinationdata for specific user
    @Override
    public void saveData() {
        System.out.println("Saving vaccination data for " + getName());
    }
}

// overview display
public class Overview extends JFrame {
    private JTextArea profileDetailsArea;
    
    public Overview() {
        setTitle("Overview");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        profileDetailsArea = new JTextArea();
        profileDetailsArea.setEditable(false);
        add(new JScrollPane(profileDetailsArea));

        loadLastProfile(); //ensure only grabs last profile in userprofile.data
    }
    
    
    private void loadLastProfile() {
        File profileFile = new File("userProfile.data"); //logic for grabbing userprofile.data
        if (profileFile.exists()) { //if profile exists then it is added but if no profiles exist then isEmpty clause added
            try {
                UserProfileManager manager = new UserProfileManager(profileFile);
                List<UserProfileData> profiles = manager.loadAllProfiles();

                if (profiles.isEmpty()) {
                    profileDetailsArea.setText("No profiles found.");
                } else {
                    // Get the last profile and covert to vaccine user
                    UserProfileData lastProfile = profiles.get(profiles.size() - 1);
                    VaccinationUser vaccinationUser = new VaccinationUser(lastProfile.getName());

                    // grab last vaccine details
                    String lastVaccination = getLastVaccinationDetails();
                    vaccinationUser.setLastVaccinationDetails(lastVaccination);

                    // display vaccine details
                    profileDetailsArea.setText(vaccinationUser.getDetails());
                }
            } catch (IOException | ClassNotFoundException e) { //catch IOexception if profile is not found or failure to load
                profileDetailsArea.setText("Failed to load profile: " + e.getMessage());
            }
        } else {
            profileDetailsArea.setText("No profile file found.");
        }
    }
    
    //logic ensures last vaccination details are saved
    private String getLastVaccinationDetails() {
        File vaccinationFile = new File("vaccination_records.txt");
        if (vaccinationFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(vaccinationFile))) {
                String line;
                String lastVaccination = "";
                while ((line = reader.readLine()) != null) {
                    lastVaccination = line; // keeps updating with the last line added
                }

                if (!lastVaccination.isEmpty()) {   //if empty then empty field returned
                    String[] parts = lastVaccination.split(",");
                    return parts[0] + ", Received: " + parts[1] + ", Booster Due: " + parts[2];
                }
            } catch (IOException e) {
                return "Failed to load vaccination records: " + e.getMessage(); 
            }
        }
        return "No vaccination records found.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Overview().setVisible(true));
    }
}
