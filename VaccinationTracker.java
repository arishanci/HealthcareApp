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

    private void writeRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (VaccinationRecord record : vaccinationRecords) {
                writer.write(record.getVaccineName() + "," + record.getDateReceived() + "," + record.getBoosterDue());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void readRecordsFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String vaccineName = parts[0];
                    String dateReceived = parts[1];
                    String boosterDue = parts[2];

                    VaccinationRecord record = new VaccinationRecord(vaccineName, dateReceived, boosterDue);
                    vaccinationRecords.add(record);
                    tableModel.addRow(new Object[]{vaccineName, dateReceived, boosterDue});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading records: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VaccinationTracker tracker = new VaccinationTracker();
            tracker.setVisible(true);
        });
    }
}
