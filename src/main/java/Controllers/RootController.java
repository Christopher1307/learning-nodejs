package Controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import util.Cuota;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;

public class RootController {

    @FXML
    private TableView<Cuota> HipotecaTable;

    @FXML
    private TableColumn<Cuota, Number> NumeroTableColumn;

    @FXML
    private TableColumn<Cuota, Number> AnyoTableColumn;

    @FXML
    private TableColumn<Cuota, Number> MesTableColumn;

    @FXML
    private TableColumn<Cuota, Number> CapitalInicialTableColumn;

    @FXML
    private TableColumn<Cuota, Number> InteresesTableColumn;

    @FXML
    private TableColumn<Cuota, Number> CapitalAmortizadoTableColumn;

    @FXML
    private TableColumn<Cuota, Number> CuotaTableColumn;

    @FXML
    private TableColumn<Cuota, Number> CapitalPendienteTableColumn;

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
    private Label yearsLabel;

    @FXML
    private TextField yearsTextField;

    @FXML
    private TextArea responseTextArea;

    @FXML
    private BorderPane root;

    private final HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        NumeroTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNumero()));
        AnyoTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAnyo()));
        MesTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getMes()));
        CapitalInicialTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCapitalInicial()));
        InteresesTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIntereses()));
        CapitalAmortizadoTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCapitalAmortizado()));
        CuotaTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCuota()));
        CapitalPendienteTableColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCapitalPendiente()));
    }

    @FXML
    void onCalculateAction(ActionEvent event) {
        try {
            // Obtener los valores de los campos de texto
            String capital = capitalTextField.getText();
            String interes = interestTextField.getText();
            String plazo = yearsTextField.getText();

            // Verificar que los campos no están vacíos
            if (capital.isEmpty() || interes.isEmpty() || plazo.isEmpty()) {
                responseTextArea.setText("Por favor, ingresa todos los valores.");
                return;
            }

            // Construir la URL con los parámetros ingresados
            String apiUrl = String.format("http://localhost:3000/hipoteca?capital=%s&interes=%s&plazo=%s", capital, interes, plazo);

            // Crear un HttpRequest para realizar la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            // Realizar la solicitud y obtener la respuesta como String
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonResponse = response.body();

            // Imprimir la respuesta JSON para depuración
            System.out.println("JSON Response: " + jsonResponse);

            // Usar Gson para procesar el JSON
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

            if (jsonObject.has("cuotas")) {
                // Convertir el array de cuotas en una lista de objetos Cuota
                Type listType = new TypeToken<List<Cuota>>() {}.getType();
                List<Cuota> cuotasList = gson.fromJson(jsonObject.getAsJsonArray("cuotas"), listType);

                // Mostrar las cuotas en el TextArea
                cuotasList.forEach(System.out::println);

                // Cargar los datos al TableView
                HipotecaTable.getItems().setAll(cuotasList);
            } else {
                responseTextArea.setText("No se encontraron cuotas en la respuesta.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseTextArea.setText("Ocurrió un error al procesar los datos.");
        }
    }
}