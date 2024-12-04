/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 01/12/24
 * ResourceFinder.java
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourceFinder extends JFrame {
    private JTextField locationField;
    private JTextArea resourceArea;
    private JFrame parentWindow;

    public ResourceFinder(JFrame parentWindow) {
        this.parentWindow = parentWindow;
        // main frame
        setTitle("Find Mental Health Resources");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Location input label and field
        JLabel locationLabel = new JLabel("Enter Your County:");
        locationLabel.setBounds(30, 20, 150, 25);
        panel.add(locationLabel);

        locationField = new JTextField();
        locationField.setBounds(180, 20, 150, 25);
        panel.add(locationField);

        // Find resources button
        JButton findButton = new JButton("Find Resources");
        findButton.setBounds(30, 60, 150, 30);
        panel.add(findButton);

        // Resources display
        resourceArea = new JTextArea();
        resourceArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resourceArea);
        scrollPane.setBounds(30, 110, 320, 80);
        panel.add(scrollPane);
        
        JButton backButton = new JButton("Back to Mental Health Tracker");
        backButton.setBounds(100, 200, 210, 30);
        panel.add(backButton);

        // actions find resource button
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {findResources();
                }
        });
        
        backButton.addActionListener(e -> returnToParentWindow()); //close window

        add(panel);
    }
    
    //logic for resource finder
    private void findResources() {
        String location = locationField.getText().trim();

        if (location.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a location.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // resources fetched from https://www.iacp.ie/
        String resources = switch (location.toLowerCase()) {
            case "carlow" -> "www.maryfoley.ie";
            case "cavan" -> "www.iacp.ie";
            case "clare" -> "www.armchaircouncelling.ie";
            case "cork" -> "powercounsellingcork@gmail.com";
            case "derry" -> "www.elainesharkey@gmail.com";
            case "donegal" -> "shaunaconaghan@hotmail.com";
            case "down" -> "www.newryprivateclinic.com";
            case "north dublin" -> "www.counsellingtherapymalahide.com";
            case "south dublin" -> "www.johnstowntherapy.com";
            case "galway" -> "www.clairemcgreetherapy.com";
            case "kerry" -> "www.racheljcouncelling.com";
            case "kildare" -> "www.sandracallaghan.ie";
            case "kilkenny" -> "www.martinamol@hotmail.com";
            case "laois" -> "councelling@portlaoisfrc.ie";
            case "limerick" -> "stephencook.ie";
            case "louth" -> "www.udoutherapy.ie";
            case "mayo" -> "www.johnstowntherapy.com";
            case "meath" -> "www.clairemcgreetherapy.com";
            case "offaly" -> "www.udoutherapy.ie";
            case "sligo" -> "www.johnstowntherapy.com";
            case "tipperary" -> "www.clairemcgreetherapy.com";
            case "waterford" -> "www.udoutherapy.ie";
            case "wexford" -> "www.johnstowntherapy.com";
            case "wicklow" -> "www.clairemcgreetherapy.com";
            default -> "No resources found for " + location + ". Try a major county.";
        };

        resourceArea.setText(resources);
    }
        private void returnToParentWindow() {
        this.setVisible(false); // Hide the ResourceFinder window
        parentWindow.setVisible(true);
    }
}