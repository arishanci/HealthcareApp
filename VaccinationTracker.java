package healthapp;

/**
 * @author rodrigo bonorino
 * date: 06/12/24
 * VaccinationTracker.java
 */
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JButton deleteButton = new JButton("Delete Vaccination");
        deleteButton.setBounds(200, 220, 150, 30);
        deleteButton.addActionListener(e -> deleteVaccination(vaccineTable));

        JButton checkDueButton = new JButton("Check Overdue");
        checkDueButton.setBounds(370, 220, 150, 30);
        checkDueButton.addActionListener(e -> openCheckOverdueScreen());

        JButton travelRecButton = new JButton("Travel Recommendations");
        travelRecButton.setBounds(30, 260, 200, 30);
        travelRecButton.addActionListener(e -> openTravelRecommendationsScreen());

        JButton backToMenuButton = new JButton("Back to Menu");
        backToMenuButton.setBounds(240, 260, 150, 30);
        backToMenuButton.addActionListener(e -> goBackToMenu());

        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(checkDueButton);
        panel.add(travelRecButton);
        panel.add(backToMenuButton);

        add(panel);

        // Save records when the window is closed
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.out.println("Window is closing. Saving records...");
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
            this.setVisible(true);
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250, 160, 80, 30);
        cancelButton.addActionListener(e -> {
            addVaccinationFrame.dispose();
            this.setVisible(true);
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
        addVaccinationFrame.setVisible(true);
    }

    private void deleteVaccination(JTable vaccineTable) {
        int selectedRow = vaccineTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a vaccination to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmed = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this vaccination record?", "Delete Record", JOptionPane.YES_NO_OPTION);
        if (confirmed == JOptionPane.YES_OPTION) {
            // Remove from the list and table
            vaccinationRecords.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
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
            this.setVisible(true);
        });

        overdueFrame.add(closeButton);

        overdueFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            String location = (String) locationDropdown.getSelectedItem();
            recommendationsArea.setText("Vaccination recommendations for " + location + ":\n\n");

            // recommendations 
            if (location.equals("Africa")) {
                recommendationsArea.append("Yellow Fever, Typhoid, Hepatitis A, Hepatitis B, Malaria\n");
            } else if (location.equals("Asia")) {
                recommendationsArea.append("Hepatitis A, Hepatitis B, Typhoid, Malaria, Japanese Encephalitis\n");
            } else if (location.equals("Europe")) {
                recommendationsArea.append("Hepatitis A, Measles, Mumps, Rubella\n");
            } else if (location.equals("North America")) {
                recommendationsArea.append("Flu, Hepatitis A, Hepatitis B\n");
            } else if (location.equals("South America")) {
                recommendationsArea.append("Yellow Fever, Typhoid, Hepatitis A, Hepatitis B\n");
            }
        });

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(150, 230, 100, 30);
        closeButton.addActionListener(e -> {
            travelFrame.dispose();
            this.setVisible(true);
        });

        travelFrame.add(showRecommendationsButton);
        travelFrame.add(closeButton);

        travelFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        travelFrame.setVisible(true);
    }

    private void goBackToMenu() {
        this.setVisible(false);
        HealthApp mainMenu = new HealthApp(); 
        mainMenu.setVisible(true); 
    }
    

    private void readRecordsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    VaccinationRecord record = new VaccinationRecord(parts[0], parts[1], parts[2]);
                    vaccinationRecords.add(record);
                    tableModel.addRow(new Object[]{parts[0], parts[1], parts[2]});
                }
            }
        } catch (IOException e) {
            System.out.println("No previous records found, starting fresh.");
        }
    }

    private void writeRecordsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (VaccinationRecord record : vaccinationRecords) {
                writer.write(record.getVaccineName() + "," + record.getDateReceived() + "," + record.getBoosterDue());
                writer.newLine();
            }
            System.out.println("Records saved successfully to " + filePath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to save records.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
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
