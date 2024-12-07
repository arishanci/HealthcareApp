/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 05/12/24
 * UserProfileApp.java
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserProfileManager {
    private final File file;

    // Constructor
    public UserProfileManager(File file) {
        this.file = file;
    }

    // Save profile to file
    public void saveUserProfile(UserProfileData profile) throws IOException {
        try {
            List<UserProfileData> profiles = loadAllProfiles();
            profiles.add(profile); // Add the new profile
            
            // Save the updated list of profiles to the file
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(profiles);
            }
        }   catch (ClassNotFoundException ex) {
            Logger.getLogger(UserProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Load profiles from file
    @SuppressWarnings("unchecked") // To suppress unchecked cast warning
    public List<UserProfileData> loadAllProfiles() throws IOException, ClassNotFoundException {
        if (!file.exists() || file.length() == 0) { 
            return new ArrayList<>(); // Return an empty list if the file does not exist or is empty
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read the list of profiles from the file
            return (List<UserProfileData>) ois.readObject();
        } catch (EOFException e) {
            return new ArrayList<>(); // Handle case where the file exists but is empty
        }
    }
}

