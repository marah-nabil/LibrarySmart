package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;

public class BorrowingController {

    @FXML
    private ComboBox<Book> bookCombo;
    @FXML
    private ComboBox<Member> memberCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TableView<Borrowing> borrowingsTable;
     @FXML private TableColumn<Borrowing, Number> idCol;
     @FXML private TableColumn<Borrowing, String> bookCol;
     @FXML private TableColumn<Borrowing, String> memberCol;
     @FXML private TableColumn<Borrowing, LocalDate> borrowDateCol;
     @FXML private TableColumn<Borrowing, LocalDate> returnDateCol;
    @FXML
    private Label totalBorrowingsLabel;

    private MainController mainController;
    private ObservableList<Borrowing> borrowingsData;
    private List<Book> books;
    private List<Member> members;
    private Map<Integer, String> bookTitlesById;
    private Map<Integer, String> memberNamesById;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    private void wireTableColumns(){
        idCol.setCellValueFactory(c -> c.getValue().idProperty());
        borrowDateCol.setCellValueFactory(c -> c.getValue().borrowDateProperty());
        returnDateCol.setCellValueFactory(c -> c.getValue().returnDateProperty());
    
        bookCol.setCellValueFactory(c  -> 
         new SimpleStringProperty(bookTitlesById.getOrDefault(c.getValue().getBookId(),"Unknown Book")));
    
        memberCol.setCellValueFactory(c  -> 
         new SimpleStringProperty(memberNamesById.getOrDefault(c.getValue().getMemberId(),"Unknown Member")));
    }
    @FXML
    public void initialize() {
        // Set default date to today
        datePicker.setValue(LocalDate.now());

        // Set up combo box renderers
        bookCombo.setCellFactory(param -> new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " by " + item.getAuthor());
                }
            }
        });

        bookCombo.setButtonCell(new ListCell<Book>() {
            @Override
            protected void updateItem(Book item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " by " + item.getAuthor());
                }
            }
        });

        memberCombo.setCellFactory(param -> new ListCell<Member>() {
            @Override
            protected void updateItem(Member item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getContact() + ")");
                }
            }
        });

        memberCombo.setButtonCell(new ListCell<Member>() {
            @Override
            protected void updateItem(Member item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getContact() + ")");
                }
            }
        });
    }

    public void initializeData(List<Book> books, List<Member> members, List<Borrowing> borrowings) {
        this.books = books;
        this.members = members;
        
        bookTitlesById = books.stream().collect(Collectors.toMap(Book::getId,Book::getTitle));
        memberNamesById = members.stream().collect(Collectors.toMap(Member::getId,Member::getName));
        borrowingsData = FXCollections.observableArrayList(borrowings);
      
        // Set up borrowings table
        borrowingsTable.setItems(borrowingsData);
        
        wireTableColumns();
        updateBookCombo();
        memberCombo.getItems().setAll(members);
        updateTotalBorrowingsLabel();
    }

    @FXML
    private void handleBorrowBook() {
        Book selectedBook = bookCombo.getValue();
        Member selectedMember = memberCombo.getValue();
        LocalDate borrowDate = datePicker.getValue();

        if (selectedBook == null || selectedMember == null || borrowDate == null) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
            return;
        }

        if (borrowDate.isAfter(LocalDate.now())) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Borrow date cannot be in the future!");
            return;
        }

        // Update book status
        selectedBook.setStatus("Borrowed");

        // Create new borrowing record
        int newId = borrowingsData.isEmpty() ? 1 : borrowingsData.get(borrowingsData.size() - 1).getId() + 1;
        Borrowing newBorrowing = new Borrowing(newId, selectedBook.getId(), selectedMember.getId(), borrowDate, null);
        borrowingsData.add(newBorrowing);

        // Save changes
        mainController.setBooks(books);
        mainController.setBorrowings(borrowingsData);

        mainController.showAlert(Alert.AlertType.INFORMATION, "Success", "Book borrowed successfully!");
        clearFields();
        updateBookCombo();
        updateTotalBorrowingsLabel();
    }

    private void updateBookCombo() {
        // Only show available books
        bookCombo.getItems().clear();
        for (Book book : books) {
            if (book.getStatus().equals("Available")) {
                bookCombo.getItems().add(book);
            }
        }
    }

    private void clearFields() {
        bookCombo.setValue(null);
        memberCombo.setValue(null);
        datePicker.setValue(LocalDate.now());
    }

    private void updateTotalBorrowingsLabel() {
        long activeBorrowings = borrowingsData.stream()
                .filter(borrowing -> borrowing.getReturnDate() == null)
                .count();
        totalBorrowingsLabel.setText(activeBorrowings + " Active Borrowings");
    }
}
