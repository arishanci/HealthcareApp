/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package healthapp;

/**
 * @author arisha mirza
 * date: 05/12/24
 * UserProfileApp.java
 */

import java.io.Serializable;

// Define a class to hold user profile data
public class UserProfileData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private int age;
    private String location;

    // Constructor
    public UserProfileData(String name, int age, String location) {
        this.name = name;
        this.age = age;
        this.location = location;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getLocation() {
        return location;
    }
}
