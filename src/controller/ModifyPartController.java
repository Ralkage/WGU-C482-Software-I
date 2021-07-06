package controller;

import util.AlertManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import util.TypeValidator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainScreenController.getPartIndex;
import static model.Inventory.getAllParts;
import static model.Inventory.updatePart;

/**
 * The Modify Part Controller.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class ModifyPartController implements Initializable {

    private int partNumber; // The part number.

    private final int partIndex = getPartIndex(); // The part index.

    @FXML
    private ToggleGroup partLocationToggle;
    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private TextField modifyPartIdText;

    @FXML
    private TextField modifyPartNameText;

    @FXML
    private TextField modifyPartInventoryText;

    @FXML
    private TextField modifyPartPriceText;

    @FXML
    private TextField modifyPartMaxText;

    @FXML
    private TextField modifyPartMinText;

    @FXML
    private Label machineIdLabel;

    @FXML
    private TextField modifyPartMachineIdText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // The Current part.
        Part currentPart = getAllParts().get(partIndex);
        partNumber = getAllParts().get(partIndex).getId();
        modifyPartIdText.setText(Integer.toString(currentPart.getId()));
        modifyPartNameText.setText(currentPart.getName());
        modifyPartPriceText.setText(Double.toString(currentPart.getPrice()));
        modifyPartInventoryText.setText(Integer.toString(currentPart.getStock()));
        modifyPartMinText.setText(Integer.toString(currentPart.getMin()));
        modifyPartMaxText.setText(Integer.toString(currentPart.getMax()));

        if (currentPart instanceof InHouse) {
            inHouseRadioButton.setSelected(true);
            machineIdLabel.setText("Machine ID");
            modifyPartMachineIdText.setText(Integer.toString(((InHouse) currentPart).getMachineId()));
        }
        if (currentPart instanceof Outsourced) {
            outsourcedRadioButton.setSelected(true);
            machineIdLabel.setText("Company Name");
            modifyPartMachineIdText.setText(((Outsourced) currentPart).getCompanyName());
        }
    }

    @FXML
    private void inHouseRadioButton(ActionEvent actionEvent) {
        machineIdLabel.setText("Machine ID");
    }

    @FXML
    private void outsourcedRadioButton(ActionEvent actionEvent) {
        machineIdLabel.setText("Company Name");
    }

    /**
     * On action add part.
     * <p>
     * RUNTIME ERROR
     * <p>
     * The application threw an error when attempting to convert a string
     * to an integer, but the string did not have the appropriate format or in this case,
     * it was left empty/null. A try catch was added to check for fields that were left empty
     * or were not an integer.
     *
     * @param actionEvent the action event
     * @throws IOException the io exception
     */
    @FXML
    public void onActionUpdatePart(ActionEvent actionEvent) throws IOException {
        try {
            String name = modifyPartNameText.getText();
            double price = Double.parseDouble(modifyPartPriceText.getText());
            int inventory = Integer.parseInt(modifyPartInventoryText.getText());
            int min = Integer.parseInt(modifyPartMinText.getText());
            int max = Integer.parseInt(modifyPartMaxText.getText());
            String machineId = modifyPartMachineIdText.getText();

            if (inHouseRadioButton.isSelected() && !TypeValidator.isInteger(modifyPartMachineIdText.getText())) {
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
                Part updateInHousePart = new InHouse(
                        partNumber, name, price, inventory, min, max, Integer.parseInt(machineId));
                updatePart(partIndex, updateInHousePart);
            } else if (outsourcedRadioButton.isSelected()) {
                Part updateOutsourcedPart = new Outsourced(partNumber, name, price, inventory, min, max, machineId);

                updatePart(partIndex, updateOutsourcedPart);
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
     * On action go to main screen.
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
