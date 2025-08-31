package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;

public class MainController {
    
    @FXML private BorderPane rootLayout;
    @FXML private ScrollPane contentScrollPane;
    @FXML private Label statusLabel;
    @FXML private Label userLabel;
    
    private User currentUser;
    private List<User> users;
    private List<Book> books;
    private List<Member> members;
    private List<Borrowing> borrowings;
    
    // Initialize method called by FXMLLoader
    @FXML
    public void initialize() {
        loadData();
        updateStatus("Application started");
        showLoginScreen();
    }
    
    // Load data from files
    private void loadData() {
        users = FileHandler.loadUsers();
        books = FileHandler.loadBooks();
        members = FileHandler.loadMembers();
        borrowings = FileHandler.loadBorrowings();
        
        // If no data exists, initialize with default data
        if (users.isEmpty() || books.isEmpty() || members.isEmpty()) {
            FileHandler.initializeDefaultData();
            users = FileHandler.loadUsers();
            books = FileHandler.loadBooks();
            members = FileHandler.loadMembers();
            borrowings = FileHandler.loadBorrowings();
        }
    }
    
    // Show login screen
    public void showLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            VBox loginPane = loader.load();
            
            LoginController controller = loader.getController();
            controller.setMainController(this);
            
            contentScrollPane.setContent(loginPane);
            updateStatus("Please sign in to continue");
            userLabel.setText("Not signed in");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load login screen: " + e.getMessage());
        }
    }
    
    // Show registration screen
    public void showRegistrationScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            VBox registerPane = loader.load();
            
            RegisterController controller = loader.getController();
            controller.setMainController(this);
            
            contentScrollPane.setContent(registerPane);
            updateStatus("Create a new account");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load registration screen: " + e.getMessage());
        }
    }
    
    // Show dashboard
    public void showDashboard() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Please sign in first");
            showLoginScreen();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
            VBox dashboardPane = loader.load();
            
            DashboardController controller = loader.getController();
            controller.setMainController(this);
            controller.initializeData(currentUser, books, members, borrowings);
            
            contentScrollPane.setContent(dashboardPane);
            updateStatus("Dashboard loaded");
            userLabel.setText("Logged in as: " + currentUser.getFirstName() + " " + currentUser.getLastName());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load dashboard: " + e.getMessage());
        }
    }
    
    // Show books management
    public void showBooksManagement() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Please sign in first");
            showLoginScreen();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("books.fxml"));
            VBox booksPane = loader.load();
            
            BooksController controller = loader.getController();
            controller.setMainController(this);
            controller.initializeData(books);
            
            contentScrollPane.setContent(booksPane);
            updateStatus("Books management loaded");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load books management: " + e.getMessage());
        }
    }
    
    // Show members management
    public void showMembersManagement() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Please sign in first");
            showLoginScreen();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("members.fxml"));
            VBox membersPane = loader.load();
            
            MembersController controller = loader.getController();
            controller.setMainController(this);
            controller.initializeData(members);
            
            contentScrollPane.setContent(membersPane);
            updateStatus("Members management loaded");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load members management: " + e.getMessage());
        }
    }
    
    // Show borrowing management
    public void showBorrowingManagement() {
        if (currentUser == null) {
            showAlert(Alert.AlertType.WARNING, "Access Denied", "Please sign in first");
            showLoginScreen();
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("borrowing.fxml"));
            VBox borrowingPane = loader.load();
            
            BorrowingController controller = loader.getController();
            controller.setMainController(this);
            controller.initializeData(books, members, borrowings);
            
            contentScrollPane.setContent(borrowingPane);
            updateStatus("Borrowing management loaded");
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load borrowing management: " + e.getMessage());
        }
    }
    
    // Handle menu actions
    @FXML
    private void handleExit() {
        System.exit(0);
    }
    
    @FXML
    private void handleSignOut() {
        currentUser = null;
        showLoginScreen();
        updateStatus("Signed out successfully");
    }
    
    @FXML
    private void handleAbout() {
        showAlert(Alert.AlertType.INFORMATION, "About", 
            "Smart Library Manager\nVersion 1.0\n\nA JavaFX application for managing library operations.");
    }
    
    // Update status bar
    public void updateStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }
    
    // Show alert dialog
    public void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Getters and setters
    public User getCurrentUser() {
        return currentUser;
    }
    
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public List<User> getUsers() {
        return users;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
        FileHandler.saveBooks(books);
    }
    
    public List<Member> getMembers() {
        return members;
    }
    
    public void setMembers(List<Member> members) {
        this.members = members;
        FileHandler.saveMembers(members);
    }
    
    public List<Borrowing> getBorrowings() {
        return borrowings;
    }
    
    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
        FileHandler.saveBorrowings(borrowings);
    }
    
    // Save all data
    public void saveAllData() {
        FileHandler.saveUsers(users);
        FileHandler.saveBooks(books);
        FileHandler.saveMembers(members);
        FileHandler.saveBorrowings(borrowings);
        updateStatus("All data saved successfully");
    }
}