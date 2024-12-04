/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 30/11/24
 * MHTrackerApp.java
 */

import java.time.LocalDate;
import java.util.ArrayList;

public class MHTrackerApp {

    // store mood entries
    private ArrayList<MoodEntry> moodLog;

    // Constructor
    public MHTrackerApp() {
        moodLog = new ArrayList<>();
    }

    // Add mood entry if it meets criteria 
    public boolean addMoodEntry(LocalDate date, int mood) {
        if (mood < 1 || mood > 5 || date.isAfter(LocalDate.now())) {
            return false; // Invalid input
        }
        moodLog.add(new MoodEntry(date, mood));
        return true;
    }

    // delete mood entry
    public boolean deleteMoodEntry(LocalDate date) {
        return moodLog.removeIf(entry -> entry.getDate().equals(date));
    }

    // Get mood log
    public String getMoodLog() {
        StringBuilder log = new StringBuilder(); //
        LocalDate tenDaysAgo = LocalDate.now().minusDays(10); //log of 10 days only
        int totalMood = 0; //tracks all total mood averages
        int count = 0; //tracks all mood entries
        
        //for every mood entry, if it is within the last 10 days, a count is added to the log
        for (MoodEntry entry : moodLog) {
            if (entry.getDate().isAfter(tenDaysAgo) || entry.getDate().isEqual(tenDaysAgo)) {
                log.append(entry).append("\n");
                totalMood += entry.getMood();
                count++;
            }
        }
        
        //for every entry, calculate the average else display msg 
        if (count > 0) {
            double averageMood = (double) totalMood / count;
            log.append("\nAverage Mood: ").append(String.format("%.2f", averageMood)); //change format to 2 decimal points
        } else {
            log.append("\nNo logs for the last 10 days.");
        }

        return log.toString();
    }

    // Getter for all mood entries
    public ArrayList<MoodEntry> getMoodLogEntries() {
        return moodLog;
    }
}

// MoodEntry class 
class MoodEntry {
    private LocalDate date;
    private int mood;

    public MoodEntry(LocalDate date, int mood) {
        this.date = date;
        this.mood = mood;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMood() {
        return mood;
    }

    @Override
    public String toString() {
        return date + ": Mood " + mood;
    }
}

