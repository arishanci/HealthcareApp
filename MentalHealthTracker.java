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

import org.jdatepicker.impl.*;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

public class MentalHealthTracker extends JFrame {

    private JTextField moodInputField;
    private JTextArea moodLogArea;
    private JDatePickerImpl datePicker;
    private MHTrackerApp trackerApp;

    public MentalHealthTracker() {
        // Initialize app class
        trackerApp = new MHTrackerApp();

        // Main frame
        setTitle("Mental Health Tracker");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Mood input label
        JLabel moodLabel = new JLabel("How do you feel today? (1-5):");
        moodLabel.setBounds(50, 30, 200, 30);
        panel.add(moodLabel);

        moodInputField = new JTextField();
        moodInputField.setBounds(250, 30, 50, 25);
        panel.add(moodInputField);

        // Date input using JDatePicker
        JLabel dateLabel = new JLabel("Select Date:");
        dateLabel.setBounds(50, 70, 100, 30);
        panel.add(dateLabel);

        datePicker = createDatePicker();
        datePicker.setBounds(150, 70, 200, 30);
        panel.add(datePicker);

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(370, 70, 80, 30);
        panel.add(submitButton);

        // Mood log display
        JLabel moodLogLabel = new JLabel("Mood Logs for the last 10 days:");
        moodLogLabel.setBounds(50, 120, 200, 25);
        panel.add(moodLogLabel);

        moodLogArea = new JTextArea();
        moodLogArea.setBounds(50, 150, 380, 200);
        moodLogArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(moodLogArea);
        scrollPane.setBounds(50, 150, 380, 200);
        panel.add(scrollPane);
        
        JLabel searchDateLabel = new JLabel("Search by Date:");
        searchDateLabel.setBounds(50, 420, 120, 25);
        panel.add(searchDateLabel);

        JDatePickerImpl searchDatePicker = createDatePicker();
        searchDatePicker.setBounds(150, 420, 200, 25);
        panel.add(searchDatePicker);
        
        JTextArea searchResultsArea = new JTextArea();
        searchResultsArea.setBounds(50, 460, 380, 30);
        searchResultsArea.setEditable(false);
        panel.add(searchResultsArea);
        
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(370, 420, 80, 30);
        panel.add(searchButton);

        // Delete button
        JButton deleteButton = new JButton("Delete Log");
        deleteButton.setBounds(180, 370, 130, 30);
        panel.add(deleteButton);
        
        // Find Resources button
        JButton resourcesButton = new JButton("Find Resources");
        resourcesButton.setBounds(60, 500, 170, 30);
        panel.add(resourcesButton);
       

        // Back button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setBounds(260, 500, 170, 30);
        panel.add(backButton);

        add(panel);

        // Actions all buttons
        submitButton.addActionListener(e -> addMoodEntry());
        deleteButton.addActionListener(e -> deleteMoodEntry());
        resourcesButton.addActionListener(e -> {this.setVisible(false);
            ResourceFinder resourceFinder = new ResourceFinder(this);
            resourceFinder.setVisible(true);
                }); 
        backButton.addActionListener(e -> dispose());
        
        searchButton.addActionListener(e -> {
        Date selectedDate = (Date) searchDatePicker.getModel().getValue();
    
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid date to search.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
        
    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    MoodEntry entry = trackerApp.searchMoodEntryByDate(localDate);
    
        if (entry != null) {
        searchResultsArea.setText("Mood Entry Found:\n" + entry);
            } else {
                     searchResultsArea.setText("No entry found.");
    }
});

    }
    
    //src for JDatePicker library: https://github.com/JDatePicker/JDatePicker
    //src for JDatePicker implementation code: https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
    private JDatePickerImpl createDatePicker() {
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        return new JDatePickerImpl(datePanel, new DateLabelFormatter());
    }
    
    //logic for adding to mood log
    private void addMoodEntry() {
    String moodInput = moodInputField.getText(); // Get user input
    int mood;

    // Validation for entry
    if (!moodInput.matches("\\d+")) { // Check if the input is only digits
        JOptionPane.showMessageDialog(this, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    mood = Integer.parseInt(moodInput); // Convert input to integer

    // Validate mood range between 1-5
    if (mood < 1 || mood > 5) {
        JOptionPane.showMessageDialog(this, "Please enter a number between 1-5", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validation for correct date
    Date selectedDate = (Date) datePicker.getModel().getValue();
    if (selectedDate == null) {
        JOptionPane.showMessageDialog(this, "Please select a valid date", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

    // Check if the date is in the future
    if (localDate.isAfter(LocalDate.now())) {
        JOptionPane.showMessageDialog(this, "Mood entry cannot be future dated", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Add mood entry tracker log
    boolean success = trackerApp.addMoodEntry(localDate, mood);
    if (!success) {
    JOptionPane.showMessageDialog(this, "Failed to add mood entry. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
    // Check if it was an update or a new entry
    MoodEntry existingEntry = trackerApp.searchMoodEntryByDate(localDate);
    
    if (existingEntry != null && existingEntry.getMood() == mood) {
        // Mood updated
        JOptionPane.showMessageDialog(this, "Mood updated for " + localDate + ".", "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
        // Mood added
        JOptionPane.showMessageDialog(this, "Mood added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    updateMoodLog(); // Update the log display
    moodInputField.setText(""); // Clear the input field
        }
    }

    private void deleteMoodEntry() {
    // Validate if a date is selected
    Date selectedDate = (Date) datePicker.getModel().getValue();
    if (selectedDate == null) {
        JOptionPane.showMessageDialog(this, "Please select a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
   
    //converts select date to a localDate instant 
    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
 
    boolean deleted = trackerApp.deleteMoodEntry(localDate);
    // checks if selected date is in the log
    if (!deleted) {
        JOptionPane.showMessageDialog(this, "No log found for the selected date.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    updateMoodLog(); // Update log
    JOptionPane.showMessageDialog(this, "Mood log deleted successfully.", "Confirmed", JOptionPane.INFORMATION_MESSAGE);
}

    //fetches latest data
    private void updateMoodLog() {
        moodLogArea.setText(trackerApp.getMoodLog());
    }
        
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MentalHealthTracker().setVisible(true));
    }
}

// converts string value input to date object
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 

    @Override
    public Object stringToValue(String text) throws ParseException { //parsed using above date format
        return dateFormatter.parse(text);
    }

    @Override
    public String valueToString(Object value) { //date object with above format is coverted back to string for storage
        if (value != null) {
            return dateFormatter.format((Date) value);
        }
        return "";
    }
}
