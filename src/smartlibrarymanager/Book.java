package smartlibrarymanager;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

    private int id;
    private String title;
    private String author;
    private String status;

    public Book(int id, String title, String author, String status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toFileString() {
        return id + "," + title + "," + author + "," + status;
    }

    // JavaFX properties for table binding
    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleStringProperty titleProperty() {
        return new SimpleStringProperty(title);
    }

    public SimpleStringProperty authorProperty() {
        return new SimpleStringProperty(author);
    }

    public SimpleStringProperty statusProperty() {
        return new SimpleStringProperty(status);
    }
}
