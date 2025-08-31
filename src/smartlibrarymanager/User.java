package smartlibrarymanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;

    public User(int id, String firstName, String lastName, String email, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String toFileString() {
        return id + "," + firstName + "," + lastName + "," + email + "," + passwordHash;
    }

    // JavaFX properties for table binding
    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty firstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    public SimpleStringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public SimpleStringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }
}
