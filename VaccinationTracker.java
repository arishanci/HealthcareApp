/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author rodrigo bonorino
 * date: 01/12/24
 * VaccinationTracker.java
 */

// Class to store vaccination record details
class VaccinationRecord {
    private final String vaccineName;
    private final String dateReceived;
    private final String boosterDue;

    public VaccinationRecord(String vaccineName, String dateReceived, String boosterDue) {
        this.vaccineName = vaccineName;
        this.dateReceived = dateReceived;
        this.boosterDue = boosterDue;
    }

    public String getVaccineName() { return vaccineName; }
    public String getDateReceived() { return dateReceived; }
    public String getBoosterDue() { return boosterDue; }
}

public class VaccinationTracker extends JFrame {

    // Store table model and vaccination records list
    private final DefaultTableModel tableModel;
    private final ArrayList<VaccinationRecord> vaccinationRecords;

    public VaccinationTracker() {
        // Initialize vaccination records list to store all records
        vaccinationRecords = new ArrayList<>();

        // Main frame setup
        setTitle("Vaccination Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel setup
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Vaccination Passport Table
        JLabel passportLabel = new JLabel("Vaccination Passport:");
        passportLabel.setBounds(30, 20, 200, 25);
        panel.add(passportLabel);

        String[] columns = {"Vaccine", "Date Received", "Booster Due"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable vaccineTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(vaccineTable);
        scrollPane.setBounds(30, 50, 520, 150);
        panel.add(scrollPane);

        // Buttons
        JButton addButton = new JButton("Add Vaccination");
        addButton.setBounds(30, 220, 150, 30);
        addButton.addActionListener(e -> openAddVaccinationScreen());

        JButton checkDueButton = new JButton("Check Overdue");
        checkDueButton.setBounds(200, 220, 150, 30);
        checkDueButton.addActionListener(e -> openCheckOverdueScreen());

        JButton travelRecButton = new JButton("Travel Recommendations");
        travelRecButton.setBounds(370, 220, 200, 30);
        travelRecButton.addActionListener(e -> openTravelRecommendationsScreen());

        panel.add(addButton);
        panel.add(checkDueButton);
        panel.add(travelRecButton);

        // Add the panel to the frame
        add(panel);
    }

    // Opens form to add new vaccination record
    private void openAddVaccinationScreen() {
        JFrame addVaccinationFrame = new JFrame("Add Vaccination");
        addVaccinationFrame.setSize(400, 300);
        addVaccinationFrame.setLayout(null);
        addVaccinationFrame.setLocationRelativeTo(this);

        JLabel nameLabel = new JLabel("Vaccine Name:");
        nameLabel.setBounds(30, 30, 120, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 25);

        JLabel dateLabel = new JLabel("Date Received (YYYY-MM-DD):");
        dateLabel.setBounds(30, 70, 200, 25);
        JTextField dateField = new JTextField();
        dateField.setBounds(230, 70, 120, 25);

        JLabel boosterLabel = new JLabel("Booster Due (YYYY-MM-DD):");
        boosterLabel.setBounds(30, 110, 200, 25);
        JTextField boosterField = new JTextField();
        boosterField.setBounds(230, 110, 120, 25);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 160, 80, 30);

        // Save button adds new record to ArrayList and table
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String booster = boosterField.getText();

            if (!name.isEmpty() && !date.isEmpty() && !booster.isEmpty()) {
                // Create new record and add to ArrayList
                VaccinationRecord record = new VaccinationRecord(name, date, booster);
                vaccinationRecords.add(record);
                
                // Add record to table display
                tableModel.addRow(new Object[]{name, date, booster});
                addVaccinationFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(addVaccinationFrame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addVaccinationFrame.add(nameLabel);
        addVaccinationFrame.add(nameField);
        addVaccinationFrame.add(dateLabel);
        addVaccinationFrame.add(dateField);
        addVaccinationFrame.add(boosterLabel);
        addVaccinationFrame.add(boosterField);
        addVaccinationFrame.add(saveButton);

        addVaccinationFrame.setVisible(true);
    }

    // Opens screen showing overdue vaccinations
    private void openCheckOverdueScreen() {
        JFrame overdueFrame = new JFrame("Overdue Vaccinations");
        overdueFrame.setSize(400, 300);
        overdueFrame.setLayout(null);
        overdueFrame.setLocationRelativeTo(this);

        JLabel overdueLabel = new JLabel("Overdue Vaccinations:");
        overdueLabel.setBounds(20, 20, 200, 25);
        overdueFrame.add(overdueLabel);

        JTextArea overdueTextArea = new JTextArea();
        overdueTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(overdueTextArea);
        scrollPane.setBounds(20, 60, 350, 180);
        overdueFrame.add(scrollPane);

        // Check each record for overdue boosters
        StringBuilder overdueList = new StringBuilder();
        LocalDate today = LocalDate.now();
        boolean hasOverdue = false;

        for (VaccinationRecord record : vaccinationRecords) {
            LocalDate boosterDate = LocalDate.parse(record.getBoosterDue());
            if (boosterDate.isBefore(today)) {
                overdueList.append(record.getVaccineName())
                           .append(" - Due: ").append(record.getBoosterDue()).append("\n");
                hasOverdue = true;
            }
        }

        overdueTextArea.setText(hasOverdue ? overdueList.toString() : "No overdue vaccinations.");

        overdueFrame.setVisible(true);
    }

    // Opens screen showing travel vaccination recommendations
    private void openTravelRecommendationsScreen() {
        JFrame travelFrame = new JFrame("Travel Recommendations");
        travelFrame.setSize(400, 300);
        travelFrame.setLayout(null);
        travelFrame.setLocationRelativeTo(this);

        JLabel locationLabel = new JLabel("Select a Location:");
        locationLabel.setBounds(30, 30, 150, 25);
        travelFrame.add(locationLabel);

        String[] locations = {"Africa", "Asia", "Europe", "North America", "South America"};
        JComboBox<String> locationDropdown = new JComboBox<>(locations);
        locationDropdown.setBounds(180, 30, 150, 25);
        travelFrame.add(locationDropdown);

        JTextArea recommendationsArea = new JTextArea();
        recommendationsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(recommendationsArea);
        scrollPane.setBounds(30, 100, 320, 120);
        travelFrame.add(scrollPane);

        JButton showRecommendationsButton = new JButton("Show Recommendations");
        showRecommendationsButton.setBounds(120, 70, 180, 25);
        showRecommendationsButton.addActionListener(e -> {
            String selectedLocation = (String) locationDropdown.getSelectedItem();
            String recommendations = switch (selectedLocation) {
                case "Africa" -> "Recommended: Yellow Fever, Typhoid, Malaria Prophylaxis";
                case "Asia" -> "Recommended: Hepatitis A, Japanese Encephalitis, Typhoid";
                case "Europe" -> "Routine vaccines, Tick-borne Encephalitis in rural areas";
                case "North America" -> "Routine vaccines, Rabies (for wildlife exposure)";
                case "South America" -> "Yellow Fever, Hepatitis A, Typhoid";
                default -> "No specific recommendations.";
            };
            recommendationsArea.setText(recommendations);
        });

        travelFrame.add(showRecommendationsButton);
        travelFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VaccinationTracker tracker = new VaccinationTracker();
            tracker.setVisible(true);
        });
    }
}
