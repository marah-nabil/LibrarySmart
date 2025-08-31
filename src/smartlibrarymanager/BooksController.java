package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.util.List;

public class BooksController {
    
    @FXML private TextField titleField;
    @FXML private TextField authorField;
    @FXML private ComboBox<String> statusCombo;
    @FXML private TableView<Book> booksTable;
    @FXML private TextField searchField;
    @FXML private Label totalBooksLabel;
    
    private MainController mainController;
    private ObservableList<Book> booksData;
    private FilteredList<Book> filteredBooks;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    public void initialize() {
        // Initialize status combo box
        statusCombo.getItems().addAll("Available", "Borrowed");
        statusCombo.setValue("Available");
        
        // Set up table selection listener
        booksTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showBookDetails(newValue));
    }
    
    public void initializeData(List<Book> books) {
        booksData = FXCollections.observableArrayList(books);
        filteredBooks = new FilteredList<>(booksData, p -> true);
        booksTable.setItems(filteredBooks);
        updateTotalBooksLabel();
    }
    
    @FXML
    private void handleAddBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String status = statusCombo.getValue();
        
        if (title.isEmpty() || author.isEmpty()) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
            return;
        }
        
        // Check for duplicate title
        for (Book book : booksData) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                mainController.showAlert(Alert.AlertType.ERROR, "Error", "A book with this title already exists!");
                return;
            }
        }
        
        // Add new book
        int newId = booksData.isEmpty() ? 1 : booksData.get(booksData.size() - 1).getId() + 1;
        Book newBook = new Book(newId, title, author, status);
        booksData.add(newBook);
        mainController.setBooks(booksData);
        
        mainController.showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
        clearFields();
        updateTotalBooksLabel();
    }
    
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        filteredBooks.setPredicate(book -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return book.getTitle().toLowerCase().contains(searchText) ||
                   book.getAuthor().toLowerCase().contains(searchText);
        });
    }
    
    @FXML
    private void handleClearSearch() {
        searchField.clear();
        filteredBooks.setPredicate(null);
    }
    
    private void showBookDetails(Book book) {
        if (book != null) {
            titleField.setText(book.getTitle());
            authorField.setText(book.getAuthor());
            statusCombo.setValue(book.getStatus());
        }
    }
    
    private void clearFields() {
        titleField.clear();
        authorField.clear();
        statusCombo.setValue("Available");
        booksTable.getSelectionModel().clearSelection();
    }
    
    private void updateTotalBooksLabel() {
        totalBooksLabel.setText(booksData.size() + " Total Books");
    }
}
