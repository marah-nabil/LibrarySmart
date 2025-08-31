package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

public class LoginController {
    
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    
    private MainController mainController;
    
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        
        // التحقق من الحقول الفارغة
        if (email.isEmpty() || password.isEmpty()) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
            return;
        }
        
        // البحث عن المستخدم
        User user = null;
        for (User u : mainController.getUsers()) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }
        
        if (user == null) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "No user found with this email!");
            return;
        }
        
        // التحقق من كلمة المرور
        String encryptedPassword = FileHandler.encryptPassword(password);
        if (user.getPasswordHash().equals(encryptedPassword)) {
            mainController.setCurrentUser(user);
            mainController.showDashboard();
        } else {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Incorrect password!");
        }
    }
    
    @FXML
    private void handleShowRegister() {
        mainController.showRegistrationScreen();
    }
}