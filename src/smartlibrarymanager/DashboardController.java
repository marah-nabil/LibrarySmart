package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.List;

public class DashboardController {
    
    @FXML private Label welcomeLabel;
    @FXML private Label totalBooksLabel;
    @FXML private Label availableBooksLabel;
    @FXML private Label membersLabel;
    @FXML private Label borrowingsLabel;
    
    @FXML private VBox totalBooksBox;
    @FXML private VBox availableBooksBox;
    @FXML private VBox membersBox;
    @FXML private VBox borrowingsBox;
    
    @FXML private Button goToBooksButton;
    @FXML private Button goToReadersButton;
    @FXML private Button goToBorrowingButton;
    @FXML private Button signOutButton;
    
    private MainController mainController;
    private User currentUser;
    private List<Book> books;
    private List<Member> members;
    private List<Borrowing> borrowings;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    public void initializeData(User currentUser, List<Book> books, List<Member> members, List<Borrowing> borrowings) {
        this.currentUser = currentUser;
        this.books = books;
        this.members = members;
        this.borrowings = borrowings;
        
        updateDashboard();
    }
    
    @FXML
    public void initialize() {
        // Set up button actions
        goToBooksButton.setOnAction(e -> handleGoToBooks());
        goToReadersButton.setOnAction(e -> handleGoToMembers());
        goToBorrowingButton.setOnAction(e -> handleGoToBorrowing());
        signOutButton.setOnAction(e -> handleSignOut());
    }
    
    private void updateDashboard() {
        // Update welcome message
        welcomeLabel.setText("Welcome, " + currentUser.getFirstName() + " " + currentUser.getLastName() + "!");
        
        // Calculate statistics
        int totalBooksCount = books.size();
        int availableBooksCount = 0;
        int membersCount = members.size();
        int activeBorrowingsCount = 0;
        
        for (Book book : books) {
            if ("Available".equals(book.getStatus())) {
                availableBooksCount++;
            }
        }
        
        for (Borrowing borrowing : borrowings) {
            if (borrowing.getReturnDate() == null) {
                activeBorrowingsCount++;
            }
        }
        
        // Update statistics labels
        totalBooksLabel.setText(String.valueOf(totalBooksCount));
        availableBooksLabel.setText(String.valueOf(availableBooksCount));
        membersLabel.setText(String.valueOf(membersCount));
        borrowingsLabel.setText(String.valueOf(activeBorrowingsCount));
        
        // Add hover effects to stat boxes
        setupStatBoxHoverEffects();
    }
    
    private void setupStatBoxHoverEffects() {
        // Add hover effects to make the UI more interactive
        totalBooksBox.setOnMouseEntered(e -> totalBooksBox.setStyle("-fx-cursor: hand;"));
        
        availableBooksBox.setOnMouseEntered(e -> availableBooksBox.setStyle(" -fx-cursor: hand;"));
        
        membersBox.setOnMouseEntered(e -> membersBox.setStyle("-fx-cursor: hand;"));
        
        borrowingsBox.setOnMouseEntered(e -> borrowingsBox.setStyle("-fx-cursor: hand;"));
        
        // Make stat boxes clickable
        totalBooksBox.setOnMouseClicked(e -> handleGoToBooks());
        membersBox.setOnMouseClicked(e -> handleGoToMembers());
        borrowingsBox.setOnMouseClicked(e -> handleGoToBorrowing());
    }
    
    @FXML
    private void handleGoToBooks() {
        mainController.showBooksManagement();
    }
    
    @FXML
    private void handleGoToMembers() {
        mainController.showMembersManagement();
    }
    
    @FXML
    private void handleGoToBorrowing() {
        mainController.showBorrowingManagement();
    }
    
    @FXML
    private void handleSignOut() {
        mainController.setCurrentUser(null);
        mainController.showLoginScreen();
    }
    
    // Method to refresh dashboard data
    public void refreshData() {
        books = mainController.getBooks();
        members = mainController.getMembers();
        borrowings = mainController.getBorrowings();
        updateDashboard();
    }
}
