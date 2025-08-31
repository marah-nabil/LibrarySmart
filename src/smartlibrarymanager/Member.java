package smartlibrarymanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Member {

    private int id;
    private String name;
    private String contact;

    public Member(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String toFileString() {
        return id + "," + name + "," + contact;
    }

    // JavaFX properties for table binding
    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty nameProperty() {
        return new SimpleStringProperty(name);
    }

    public SimpleStringProperty contactProperty() {
        return new SimpleStringProperty(contact);
    }
}
