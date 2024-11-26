package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            // Close the login window
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.close();

            // Open the main application window
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
                Parent root = loader.load();
                Stage mainStage = new Stage();
                mainStage.setTitle("Hipoteca Application");
                mainStage.setScene(new Scene(root));
                mainStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show an error message
            System.out.println("Invalid credentials");
        }
    }

    private boolean authenticate(String username, String password) {
        // Implement your authentication logic here
        return "user".equals(username) && "password".equals(password);
    }
}