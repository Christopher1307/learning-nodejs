package Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.Cuota;
import util.Formacion;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RootController {

    @FXML
    private TableColumn<Formacion, String> DenominationTableColumn;

    @FXML
    private TableColumn<Formacion, String> FromTableColumn;

    @FXML
    private TableView<Cuota> HipotecaTable;

    @FXML
    private TableColumn<Cuota, Number> ResultTableColumn;

    @FXML
    private Button calculateButton;

    @FXML
    private Label capitalLabel;

    @FXML
    private TextField capitalTextField;

    @FXML
    private Label interestLabel;

    @FXML
    private TextField interestTextField;

    @FXML
    private TableColumn<Formacion, String> organizationTableColumn;

    @FXML
    private BorderPane root;

    @FXML
    private TableColumn<Formacion, String> untilTableColumn;

    @FXML
    private Label yearsLabel;

    @FXML
    private TextField yearsTextField;

    @FXML
    private TableView<Formacion> formacionTable;

    @FXML
    private TableColumn<Formacion, String> ResultTableColumnFormacion;

    private final OkHttpClient client = new OkHttpClient();
    private final List<Formacion> formaciones = new ArrayList<>();

    @FXML
    void initialize() {
        DenominationTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDenomination()));
        FromTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFrom()));
        untilTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUntil()));
        organizationTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrganization()));
        ResultTableColumnFormacion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getResult()));

        // Set up the ResultTableColumn to display the calculated results
        ResultTableColumn.setCellValueFactory(cellData -> cellData.getValue().cuotaProperty());
    }

    @FXML
    void onCalculateAction(ActionEvent event) {
        Formacion selectedFormacion = formacionTable.getSelectionModel().getSelectedItem();
        if (selectedFormacion == null) {
            System.out.println("No formation selected");
            return;
        }

        String capital = capitalTextField.getText();
        String interest = interestTextField.getText();
        String years = yearsTextField.getText();

        String url = String.format("http://localhost:3000/hipoteca?capital=%s&interes=%s&plazo=%s", capital, interest, years);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String jsonResponse = response.body().string();
                Gson gson = new Gson();
                // Parse the JSON object
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);
                // Extract the "cuotas" array
                JsonArray cuotasArray = jsonObject.getAsJsonArray("cuotas");
                Type listType = new TypeToken<List<Cuota>>() {}.getType();
                List<Cuota> cuotas = gson.fromJson(cuotasArray, listType);
                HipotecaTable.getItems().setAll(cuotas);

                // Update the result for the selected formation
                selectedFormacion.setResult("Calculated");
                formacionTable.refresh();
            } else {
                System.out.println("Request failed: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void onAddFormacionView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
            Parent root = loader.load();
            FormacionController formacionController = loader.getController();
            formacionController.setRootController(this);

            Stage stage = new Stage();
            stage.setTitle("Agregar Formacion");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFormacion(Formacion formacion) {
        formaciones.add(formacion);
        formacionTable.getItems().setAll(formaciones);
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }
}