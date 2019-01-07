package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.datamodel.Contact;
import sample.datamodel.ContactData;

import java.io.IOError;
import java.io.IOException;

public class Main extends Application {

    // Static global variable for the controller
    static Controller myControllerHandler;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Set up instance instead of using static load() method
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        setUserAgentStylesheet(STYLESHEET_MODENA);
        primaryStage.setTitle("Contact List");
        primaryStage.centerOnScreen();
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root, 600, 400));

        //Access to getController() through the instance... don't forget the type cast
        myControllerHandler = (Controller)loader.getController();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() throws Exception {
        try {
            System.out.println("Saving the data");
            myControllerHandler.getData().saveContacts();
            //dataToSave.saveContacts();
        } catch(IOError e) {
            System.out.println(e.getMessage());
        }
    }
}
