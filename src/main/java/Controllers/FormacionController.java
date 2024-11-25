// src/main/java/Controllers/FormacionController.java
package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import util.Formacion;

public class FormacionController {

    @FXML
    private TextField denominationTextField;

    @FXML
    private TextField fromTextField;

    @FXML
    private TextField untilTextField;

    @FXML
    private TextField organizationTextField;

    private RootController rootController;

    public void setRootController(RootController rootController) {
        this.rootController = rootController;
    }

    @FXML
    private void onAddFormacion() {
        String denomination = denominationTextField.getText();
        String from = fromTextField.getText();
        String until = untilTextField.getText();
        String organization = organizationTextField.getText();

        Formacion formacion = new Formacion();
        formacion.setDenomination(denomination);
        formacion.setFrom(from);
        formacion.setUntil(until);
        formacion.setOrganization(organization);

        rootController.addFormacion(formacion);
    }
}