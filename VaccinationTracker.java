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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VaccinationTracker tracker = new VaccinationTracker();
            tracker.setVisible(true);
        });
    }
}
