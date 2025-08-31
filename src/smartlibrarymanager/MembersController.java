package smartlibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.util.List;

public class MembersController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField contactField;
    @FXML
    private TableView<Member> membersTable;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalMembersLabel;

    private MainController mainController;
    private ObservableList<Member> membersData;
    private FilteredList<Member> filteredMembers;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        // Set up table selection listener
        membersTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showMemberDetails(newValue));
    }

    public void initializeData(List<Member> members) {
        membersData = FXCollections.observableArrayList(members);
        filteredMembers = new FilteredList<>(membersData, p -> true);
        membersTable.setItems(filteredMembers);
        updateTotalMembersLabel();
    }

    @FXML
    private void handleAddMember() {
        String name = nameField.getText().trim();
        String contact = contactField.getText().trim();

        if (name.isEmpty() || contact.isEmpty()) {
            mainController.showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields!");
            return;
        }

        // Check for duplicate member
        for (Member member : membersData) {
            if (member.getName().equalsIgnoreCase(name)) {
                mainController.showAlert(Alert.AlertType.ERROR, "Error", "A member with this name already exists!");
                return;
            }
        }

        // Add new member
        int newId = membersData.isEmpty() ? 1 : membersData.get(membersData.size() - 1).getId() + 1;
        Member newMember = new Member(newId, name, contact);
        membersData.add(newMember);
        mainController.setMembers(membersData);

        mainController.showAlert(Alert.AlertType.INFORMATION, "Success", "Member added successfully!");
        clearFields();
        updateTotalMembersLabel();
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        filteredMembers.setPredicate(member -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return member.getName().toLowerCase().contains(searchText)
                    || member.getContact().toLowerCase().contains(searchText);
        });
    }

    @FXML
    private void handleClearSearch() {
        searchField.clear();
        filteredMembers.setPredicate(null);
    }

    private void showMemberDetails(Member member) {
        if (member != null) {
            nameField.setText(member.getName());
            contactField.setText(member.getContact());
        }
    }

    private void clearFields() {
        nameField.clear();
        contactField.clear();
        membersTable.getSelectionModel().clearSelection();
    }

    private void updateTotalMembersLabel() {
        totalMembersLabel.setText(membersData.size() + " Total Members");
    }
}
