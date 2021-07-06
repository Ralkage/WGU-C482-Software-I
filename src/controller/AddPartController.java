package controller;

import util.AlertManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import util.TypeValidator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Add Part controller class which handles the AddPart form.
 *
 * @author Christian Lopez
 * Software I - C482s
 */
public class AddPartController implements Initializable {

    private int partNumber; // The part number.

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField addPartNameText;

    @FXML
    private TextField addPartInventoryText;

    @FXML
    private TextField addPartPriceText;

    @FXML
    private TextField addPartMaxText;

    @FXML
    private TextField addPartMinText;

    @FXML
    private Label machineIdLabel;

    @FXML
    private TextField addPartMachineIdText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partNumber = Inventory.getPartNumber() + 1;
        inHouseRadioButton.setSelected(true);
    }

    /**
     * This method handles the outsourced radio button which sets the text of Machine ID label
     * to Machine ID when this option is toggled.
     */
    @FXML
    private void inHouseRadioButton(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    /**
     * This method handles the outsourced radio button which sets the text of the Machine ID label
     * to Company name when this option is toggled.
     */
    @FXML
    private void outsourcedRadioButton(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * This method handles the OnAction event for adding new a part.
     * <p>
     * RUNTIME ERROR
     * <p>
     * The application threw an NumberFormatException exception error when attempting to convert a text field value
     * from a string to it's appropriate numeric type. In this scenario, the value which was an empty
     * string value, did not have the appropriate format or in this case, it was left empty/null.
     * A try catch was added to check for fields that were left empty or null
     * and could not be converted into their appropriate numeric types. This applies to all text field values and their
     * checks which should trigger the appropriate dialog went a value is incorrectly added.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionAddPart(ActionEvent actionEvent) throws IOException {
        try {
            String name = addPartNameText.getText();
            double price = Double.parseDouble(addPartPriceText.getText());
            int inventory = Integer.parseInt(addPartInventoryText.getText());
            int min = Integer.parseInt(addPartMinText.getText());
            int max = Integer.parseInt(addPartMaxText.getText());
            String machineId = addPartMachineIdText.getText();

            if (inHouseRadioButton.isSelected() && !TypeValidator.isInteger(addPartMachineIdText.getText())) {
                AlertManager.invalidType(machineIdLabel.getText(), "numeric");
                return;
            }

            if (name == null || name.isEmpty()) {
                AlertManager.invalidNameField();
                return;
            }

            if (min > max) {
                AlertManager.invalidRange();
                return;
            }

            if (price <= 0) {
                AlertManager.invalidPrice();
                return;
            }

            if (inventory < 1) {
                AlertManager.invalidInventoryLevel();
                return;
            }

            if (inventory < min || inventory > max) {
                AlertManager.invalidInventoryLevelRange();
                return;
            } else if (inHouseRadioButton.isSelected()) {
                Part addParts;
                addParts = new InHouse(
                        partNumber, name, price, inventory, min, max, Integer.parseInt(machineId));
                Inventory.addPart(addParts);
            } else if (outsourcedRadioButton.isSelected()) {

                Part addParts = new Outsourced(partNumber, name, price, inventory, min, max, machineId);

                Inventory.addPart(addParts);
            }
            onActionToMainScreen(actionEvent);
        } catch (NullPointerException | NumberFormatException e) {
            AlertManager.missingFieldValues();
        }
    }

    /**
     * This method handles the OnAction event when the user wants return
     * to the main screen without saving any form input.
     *
     * @param actionEvent the action event.
     * @throws IOException the io exception.
     */
    @FXML
    private void onActionCancel(ActionEvent actionEvent) throws IOException {
        AlertManager.cancelConfirmation();
        onActionToMainScreen(actionEvent);
    }

    /**
     * This method handles the OnAction event returning to the main screen.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionToMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
