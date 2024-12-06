/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VaccinationTracker extends JFrame {
    private final DefaultTableModel tableModel;
    private final ArrayList<VaccinationRecord> vaccinationRecords;
    private final String filePath = "vaccination_records.txt"; // File to store records

    public VaccinationTracker() {
        vaccinationRecords = new ArrayList<>();
        tableModel = new DefaultTableModel(new String[]{"Vaccine", "Date Received", "Booster Due"}, 0);

        // Load records from file
        readRecordsFromFile();

        // Main frame setup
        setTitle("Vaccination Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel passportLabel = new JLabel("Vaccination Passport:");
        passportLabel.setBounds(30, 20, 200, 25);
        panel.add(passportLabel);

        JTable vaccineTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(vaccineTable);
        scrollPane.setBounds(30, 50, 520, 150);
        panel.add(scrollPane);

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

        add(panel);

        // Save records when the window is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                writeRecordsToFile();
                e.getWindow().dispose();
            }
        });
    }

    private void openAddVaccinationScreen() {
        this.setVisible(false);

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

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String booster = boosterField.getText();

            if (name.isEmpty() || date.isEmpty() || booster.isEmpty()) {
                JOptionPane.showMessageDialog(addVaccinationFrame, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate.parse(date);
                LocalDate.parse(booster);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addVaccinationFrame, "Invalid date format. Use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            VaccinationRecord record = new VaccinationRecord(name, date, booster);
            vaccinationRecords.add(record);
            tableModel.addRow(new Object[]{name, date, booster});
            addVaccinationFrame.dispose();
            VaccinationTracker.this.setVisible(true);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250, 160, 80, 30);
        cancelButton.addActionListener(e -> {
            addVaccinationFrame.dispose();
            VaccinationTracker.this.setVisible(true);
        });

        addVaccinationFrame.add(nameLabel);
        addVaccinationFrame.add(nameField);
        addVaccinationFrame.add(dateLabel);
        addVaccinationFrame.add(dateField);
        addVaccinationFrame.add(boosterLabel);
        addVaccinationFrame.add(boosterField);
        addVaccinationFrame.add(saveButton);
        addVaccinationFrame.add(cancelButton);

        addVaccinationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addVaccinationFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                VaccinationTracker.this.setVisible(true);
            }
        });

        addVaccinationFrame.setVisible(true);
    }

    private void openCheckOverdueScreen() {
        this.setVisible(false);

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

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 250, 100, 30);
        closeButton.addActionListener(e -> {
            overdueFrame.dispose();
            VaccinationTracker.this.setVisible(true);
        });

        overdueFrame.add(closeButton);

        overdueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        overdueFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                VaccinationTracker.this.setVisible(true);
            }
        });

        overdueFrame.setVisible(true);
    }

    private void openTravelRecommendationsScreen() {
        this.setVisible(false);

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

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 230, 100, 30);
        closeButton.addActionListener(e -> {
            travelFrame.dispose();
            VaccinationTracker.this.setVisible(true);
        });

        travelFrame.add(showRecommendationsButton);
        travelFrame.add(closeButton);

        travelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        travelFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                VaccinationTracker.this.setVisible(true);
            }
        });

        travelFrame.setVisible(true);
    }

    private void readRecordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    VaccinationRecord record = new VaccinationRecord(data[0], data[1], data[2]);
                    vaccinationRecords.add(record);
                    tableModel.addRow(data);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing records found. Starting fresh.");
        }
    }

    private void writeRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (VaccinationRecord record : vaccinationRecords) {
                writer.write(record.getVaccineName() + "," + record.getDateReceived() + "," + record.getBoosterDue());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save records.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VaccinationTracker tracker = new VaccinationTracker();
            tracker.setVisible(true);
        });
    }
}

class VaccinationRecord {
    private final String vaccineName;
    private final String dateReceived;
    private final String boosterDue;

    public VaccinationRecord(String vaccineName, String dateReceived, String boosterDue) {
        this.vaccineName = vaccineName;
        this.dateReceived = dateReceived;
        this.boosterDue = boosterDue;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public String getBoosterDue() {
        return boosterDue;
    }
}
