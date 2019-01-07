package sample.datamodel;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty firstName = new SimpleStringProperty("");;
    private SimpleStringProperty lastName = new SimpleStringProperty("");;
    private SimpleStringProperty email = new SimpleStringProperty("");;
    private SimpleStringProperty phone = new SimpleStringProperty("");;
    private SimpleStringProperty note = new SimpleStringProperty("");;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phone, String email, String note) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phone = new SimpleStringProperty(phone);
        this.email = new SimpleStringProperty(email);
        this.note = new SimpleStringProperty(note);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public SimpleStringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getNote() {
        return note.get();
    }

    public SimpleStringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName=" + firstName.get() +
                ", lastName=" + lastName.get() +
                ", email=" + email.get() +
                ", phone=" + phone.get() +
                ", note=" + note.get() +
                '}';
    }
}
