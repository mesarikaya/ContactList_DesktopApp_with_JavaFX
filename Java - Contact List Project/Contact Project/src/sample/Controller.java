package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class Controller {
    @FXML
    private SplitPane mainPane;

    @FXML
    private TableView<Contact> contactsTable;

    @FXML
    private TableColumn<Contact, String> firstName;

    @FXML
    private TableColumn<Contact, String> lastName;

    @FXML
    private TableColumn<Contact, String> phoneNumber;

    @FXML
    private TableColumn<Contact, String> email;

    @FXML
    private TableColumn<Contact, String> note;

    @FXML
    private Button closeButton;

    @FXML
    private Button aboutButton;

    @FXML
    private ContextMenu listContextMenu;

    /*private final ObservableList<Contact> contactListItems = FXCollections.observableArrayList(
            new Contact("Ergin", "Sarikaya", "0631957005", "mesarikaya@gmail.com", ""),
            new Contact("Ergin", "Sarikaya", "0631957005",  "mesarikaya@gmail.com",""),
            new Contact("Ergin", "Sarikaya", "0631957005",  "mesarikaya@gmail.com",""),
            new Contact("Ergin", "Sarikaya", "0631957005",  "mesarikaya@gmail.com","")
    );*/

    private ContactData data;

    // Initialize the main frame during Application opening
    public void initialize() {

        // CREATE SAMPLE DATA ENTRY
        // This is to set the CellValueFactory method that specifies a cell factory for each column.
        // The cell factories are implemented by using the PropertyValueFactory class,
        // which uses the firstName, lastName, and phone and notes as properties of the table columns
        // as references to the corresponding methods of the Person class.
        firstName.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<Contact, String>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<Contact, String>("email"));
        note.setCellValueFactory(new PropertyValueFactory<Contact, String>("note"));

        // LOAD THE DATA
        data = new ContactData();
        data.loadContacts();
        contactsTable.setItems(data.getContacts());

        // make Columns Cell Editable
        makeCellEditable(firstName);
        makeCellEditable(lastName);
        makeCellEditable(phoneNumber);
        makeCellEditable(email);
        makeCellEditable(note);


        //Create a Right click delete functionality
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Contact item = contactsTable.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
        mainPane.setContextMenu(listContextMenu);
    }

    // Open the Add contact dialog panel
    @FXML
    public void openDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }

        // Add the APPLY and CANCEL buttons
        dialog.getDialogPane().getButtonTypes().add(ButtonType.APPLY);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // Focus on Dialog Screen and do not let any other dialog
        // to be opened before it closes
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.APPLY) {
            DialogControl dialogController = fxmlLoader.getController();
            Contact newContact = dialogController.getNewContact(data);
            if (newContact.getEmail()==""){
                System.out.println("Do nothing email is duplicate or one of the fields are not entered.");
            }
            else{
                System.out.println("New contact is: " + newContact.toString());
                data.addContact(newContact);
                data.saveContacts();
            }

        }
    }

    // Make cells of the contact list editable on the main frame and dynamically enable modifying the underlying data
    public void makeCellEditable(TableColumn<Contact, String> column){
        column.setCellFactory(TextFieldTableCell.<Contact>forTableColumn());
        if (column.getId().toString().equals("firstName")){
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<Contact, String> t) -> {
                        // Set the item to the new value
                        ((Contact) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    });
        }else if (column.getId().toString().equals("lastName")){
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<Contact, String> t) -> {
                        // Set the item to the new value
                        ((Contact) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLastName(t.getNewValue());
                    });
        }else if (column.getId().toString().equals("phoneNumber")){
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<Contact, String> t) -> {
                        // Set the item to the new value
                        ((Contact) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPhone(t.getNewValue());
                    });
        }else if (column.getId().toString().equals("email")){
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<Contact, String> t) -> {
                        // Set the item to the new value
                        ((Contact) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setEmail(t.getNewValue());
                    });
        }else if (column.getId().toString().equals("note")){
            column.setOnEditCommit(
                    (TableColumn.CellEditEvent<Contact, String> t) -> {
                        // Set the item to the new value
                        ((Contact) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setNote(t.getNewValue());
                    });
        }
    }

    // Get Contact Data
    public ContactData getData(){
        return data;
    }

    // Create Button Click actions
    public void onButtonClicked(ActionEvent e) {
        if(e.getSource().equals(closeButton)) {//Action: close the relevant frame on Close Button Click
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }else if(e.getSource().equals(aboutButton)){//Action: Show information  dialog on About Button Click
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About the Tool");
            alert.setHeaderText("This is a tool to keep track of contacts. It enables add/delete/edit functionalities for a contact.");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                    System.out.println("Pressed OK.");
                }
            });
        }
    }

    // Send Delete the data request on delete button clcik from the context menu that opens up after right click to the contact list row/item
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) {
        Contact selectedItem = contactsTable.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            if(keyEvent.getCode().equals(KeyCode.DELETE)) {
                deleteItem(selectedItem);
            }
        }
    }

    // Delete the item fromt he data
    public void deleteItem(Contact item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Contact Item");
        alert.setHeaderText("Delete contact: " + "\nFirst name: " + item.getFirstName() + "\tLast name: " + item.getLastName()  +
                            "\nEmail: " + item.getEmail() + "\tPhone: " + item.getPhone());
        alert.setContentText("Are you sure?  Press OK to confirm, or cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && (result.get() == ButtonType.OK)) {
            data.deleteContact(item);
        }
    }

}
