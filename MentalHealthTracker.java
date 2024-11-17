/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 16/11/24
 * MentalHealthTracker.java
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MentalHealthTracker extends JFrame {
    
    private JTextField moodInputField;
    private JTextArea moodLogArea;
    private JButton findResourcesButton;
    
    // constructor
    public MentalHealthTracker() {
        // main frame
        setTitle("Mental Health Tracker");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // panel layout in frame
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // mood label for rating
        JLabel moodLabel = new JLabel("How do you feel today? (1-5):");
        moodLabel.setBounds(50, 30, 200, 25);
        panel.add(moodLabel);
        
        //input for mood rating
        moodInputField = new JTextField();
        moodInputField.setBounds(230, 30, 50, 25);
        panel.add(moodInputField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(140, 70, 80, 30);
        panel.add(submitButton);
        
        // mood log
        JLabel moodLogLabel = new JLabel("Mood Logs for the last 10 days:");
        moodLogLabel.setBounds(50, 120, 200, 25);
        panel.add(moodLogLabel);
        
        //displays last 10 days of logs
        moodLogArea = new JTextArea();
        moodLogArea.setBounds(50, 150, 300, 150);
        moodLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moodLogArea);
        scrollPane.setBounds(50, 150, 300, 150);
        panel.add(scrollPane);
        
        JButton findResourceButton = new JButton("Find resources based on your location");
        findResourceButton.setBounds(50, 320, 300, 30);
        panel.add(findResourceButton);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(125, 370, 150, 30);
        panel.add(backButton);

        add(panel);

        //to add functionality later for submit button
        submitButton.addActionListener(e -> {
         
        });

        backButton.addActionListener(e -> {
            dispose(); // close window
        });
    }

}


