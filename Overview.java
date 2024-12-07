package healthapp;

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

    // Abstract method to be implemented by subclasses
    public abstract String getDetails();
    
    // Common method for saving user data (to be implemented in subclasses)
    public abstract void saveData();
}

// Subclass: VaccinationUser (A user who tracks vaccinations)
class VaccinationUser extends User {
    private String lastVaccinationDetails;

    public VaccinationUser(String name) {
        super(name);
    }

    public void setLastVaccinationDetails(String details) {
        this.lastVaccinationDetails = details;
    }

    @Override
    public String getDetails() {
        return "User: " + getName() + "\n" + "Last Vaccination: " + lastVaccinationDetails + "\n";
    }

    @Override
    public void saveData() {
        // Saving logic specific to vaccination user
        System.out.println("Saving vaccination data for " + getName());
    }
}

// Class for displaying Overview (using polymorphism)
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

        loadLastProfile();
    }

    private void loadLastProfile() {
        File profileFile = new File("userProfile.data");
        if (profileFile.exists()) {
            try {
                UserProfileManager manager = new UserProfileManager(profileFile);
                List<UserProfileData> profiles = manager.loadAllProfiles();

                if (profiles.isEmpty()) {
                    profileDetailsArea.setText("No profiles found.");
                } else {
                    // Get the last profile and treat it as a VaccinationUser (polymorphism)
                    UserProfileData lastProfile = profiles.get(profiles.size() - 1);
                    VaccinationUser vaccinationUser = new VaccinationUser(lastProfile.getName());

                    // Get the last vaccination details
                    String lastVaccination = getLastVaccinationDetails();
                    vaccinationUser.setLastVaccinationDetails(lastVaccination);

                    // Use polymorphism to display the details
                    profileDetailsArea.setText(vaccinationUser.getDetails());
                }
            } catch (IOException | ClassNotFoundException e) {
                profileDetailsArea.setText("Failed to load profile: " + e.getMessage());
            }
        } else {
            profileDetailsArea.setText("No profile file found.");
        }
    }

    private String getLastVaccinationDetails() {
        File vaccinationFile = new File("vaccination_records.txt");
        if (vaccinationFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(vaccinationFile))) {
                String line;
                String lastVaccination = "";
                while ((line = reader.readLine()) != null) {
                    lastVaccination = line; // Keep updating with the last line
                }

                if (!lastVaccination.isEmpty()) {
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
