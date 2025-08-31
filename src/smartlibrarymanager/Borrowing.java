package smartlibrarymanager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class Borrowing {

    private int id;
    private int bookId;
    private int memberId;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    public Borrowing(int id, int bookId, int memberId, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String toFileString() {
        String returnDateStr = (returnDate == null) ? "null" : returnDate.format(DATE_FORMATTER);
        return id + "," + bookId + "," + memberId + "," + borrowDate.format(DATE_FORMATTER) + "," + returnDateStr;
    }

    // JavaFX properties for table binding
    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleIntegerProperty bookIdProperty() {
        return new SimpleIntegerProperty(bookId);
    }

    public SimpleIntegerProperty memberIdProperty() {
        return new SimpleIntegerProperty(memberId);
    }

    public SimpleObjectProperty<LocalDate> borrowDateProperty() {
        return new SimpleObjectProperty<>(borrowDate);
    }

    public SimpleObjectProperty<LocalDate> returnDateProperty() {
        return new SimpleObjectProperty<>(returnDate);
    }

    // Helper method to get book title (requires access to books list)
    public SimpleStringProperty bookTitleProperty(List<Book> books) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return new SimpleStringProperty(book.getTitle());
            }
        }
        return new SimpleStringProperty("Unknown Book");
    }

    // Helper method to get member name (requires access to members list)
    public SimpleStringProperty memberNameProperty(List<Member> members) {
        for (Member member : members) {
            if (member.getId() == memberId) {
                return new SimpleStringProperty(member.getName());
            }
        }
        return new SimpleStringProperty("Unknown Member");
    }
}
