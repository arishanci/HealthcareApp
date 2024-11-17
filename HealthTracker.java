/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 17/11/24
 * HealthTracker.java
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthTracker extends JFrame {

    private JTextField bloodPressureField;
    private JTextField sugarLevelField;
    private JTextField heartRateField;
    private JTextArea logArea;

    public HealthTracker() {
        // Frame settings
        setTitle("General Health Tracker");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        add(panel);

        // bp input
        JLabel bloodPressureLabel = new JLabel("Blood Pressure:");
        bloodPressureLabel.setBounds(50, 30, 150, 25);
        panel.add(bloodPressureLabel);

        bloodPressureField = new JTextField();
        bloodPressureField.setBounds(200, 30, 150, 25);
        panel.add(bloodPressureField);

        // sugar input
        JLabel sugarLevelLabel = new JLabel("Sugar Level:");
        sugarLevelLabel.setBounds(50, 80, 150, 25);
        panel.add(sugarLevelLabel);

        sugarLevelField = new JTextField();
        sugarLevelField.setBounds(200, 80, 150, 25);
        panel.add(sugarLevelField);

        // heart rate input
        JLabel heartRateLabel = new JLabel("Heart Rate:");
        heartRateLabel.setBounds(50, 130, 150, 25);
        panel.add(heartRateLabel);

        heartRateField = new JTextField();
        heartRateField.setBounds(200, 130, 150, 25);
        panel.add(heartRateField);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(50, 420, 300, 30);
        panel.add(backButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HealthTracker().setVisible(true));
    }
}
 
 
