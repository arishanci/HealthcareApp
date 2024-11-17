/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author rodrigo bonorino
 * date: 16/11/24
 * VaccinationTracker.java (GUI Only)
 */
public class VaccinationTracker extends JFrame {

    public VaccinationTracker() {
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
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable vaccineTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(vaccineTable);
        scrollPane.setBounds(30, 50, 520, 150);
        panel.add(scrollPane);

        // Buttons
        JButton addButton = new JButton("Add Vaccination");
        addButton.setBounds(30, 220, 150, 30);
        JButton checkDueButton = new JButton("Check Overdue");
        checkDueButton.setBounds(200, 220, 150, 30);
        JButton travelRecButton = new JButton("Travel Recommendations");
        travelRecButton.setBounds(370, 220, 200, 30);

        panel.add(addButton);
        panel.add(checkDueButton);
        panel.add(travelRecButton);

        // Add the panel to the frame
        add(panel);
    }
    private void openAddVaccinationDialog(DefaultTableModel tableModel) {
        JDialog addDialog = new JDialog(this, "Add Vaccination", true);
        addDialog.setSize(400, 300);
        addDialog.setLayout(null);
        addDialog.setLocationRelativeTo(this);

        JLabel nameLabel = new JLabel("Vaccine Name:");
        nameLabel.setBounds(30, 30, 120, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 25);

        JLabel dateLabel = new JLabel("Date Received:");
        dateLabel.setBounds(30, 70, 120, 25);
        JTextField dateField = new JTextField();
        dateField.setBounds(150, 70, 200, 25);

        JLabel boosterLabel = new JLabel("Booster Due:");
        boosterLabel.setBounds(30, 110, 120, 25);
        JTextField boosterField = new JTextField();
        boosterField.setBounds(150, 110, 200, 25);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 160, 80, 30);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String booster = boosterField.getText();

            if (!name.isEmpty() && !date.isEmpty() && !booster.isEmpty()) {
                tableModel.addRow(new Object[]{name, date, booster});
                addDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(addDialog, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        addDialog.add(nameLabel);
        addDialog.add(nameField);
        addDialog.add(dateLabel);
        addDialog.add(dateField);
        addDialog.add(boosterLabel);
        addDialog.add(boosterField);
        addDialog.add(saveButton);

        addDialog.setVisible(true);
    }
     private void checkOverdueVaccinations(DefaultTableModel tableModel) {
        StringBuilder overdueList = new StringBuilder("Overdue Vaccinations:\n");
        boolean foundOverdue = false;

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String boosterDate = (String) tableModel.getValueAt(i, 2);
            if (boosterDate.compareTo(java.time.LocalDate.now().toString()) < 0) {
                overdueList.append(tableModel.getValueAt(i, 0)).append(" - Due: ").append(boosterDate).append("\n");
                foundOverdue = true;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                foundOverdue ? overdueList.toString() : "No overdue vaccinations.",
                "Overdue Check",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VaccinationTracker tracker = new VaccinationTracker();
            tracker.setVisible(true);
        });
    }
}
