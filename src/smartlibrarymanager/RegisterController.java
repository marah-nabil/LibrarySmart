package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

public class RegisterController {
    
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    
    private MainController mainController;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        
        // التحقق من الحقول الفارغة
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
            return;
        }
        
        // التحقق من تطابق كلمات المرور
        if (!password.equals(confirmPassword)) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            return;
        }
        
        // التحقق من البريد الإلكتروني المستخدم مسبقاً
        for (User user : mainController.getUsers()) {
            if (user.getEmail().equals(email)) {
                mainController.showAlert(Alert.AlertType.ERROR, "Error", "Email already registered!");
                return;
            }
        }
        
        // إنشاء مستخدم جديد
        int newId = mainController.getUsers().isEmpty() ? 1 : 
                   mainController.getUsers().get(mainController.getUsers().size() - 1).getId() + 1;
        String passwordHash = FileHandler.encryptPassword(password);
        
        User newUser = new User(newId, firstName, lastName, email, passwordHash);
        mainController.getUsers().add(newUser);
        mainController.saveAllData();
        
        mainController.showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
        mainController.showLoginScreen();
    }
    
    @FXML
    private void handleShowLogin() {
        mainController.showLoginScreen();
    }
}