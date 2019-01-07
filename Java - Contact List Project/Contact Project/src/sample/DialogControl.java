package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

public class DialogControl {
    @FXML
    private DialogPane addContactDialog;

    @FXML
    private Button addContactButton;

    @FXML
    private ButtonType cancelContactButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea noteField;


    public Contact getNewContact(ContactData data){
        System.out.println("creating the new contact");
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneField.getText();
        String email = emailField.getText();
        String notes = noteField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || notes.isEmpty()){
            Alert duplicateAlert = new Alert(Alert.AlertType.INFORMATION);
            duplicateAlert.setTitle("Contact exists...");
            duplicateAlert.setHeaderText("All fields need to have entry. Please review the enty!");
            duplicateAlert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
            return new Contact();
        }
        else{
            Contact newContact = new Contact(firstName, lastName, phoneNumber, email, notes);
            return newContact;
        }
    }

}
