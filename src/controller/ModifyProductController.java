package controller;

import util.AlertManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import util.TypeValidator;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static controller.MainScreenController.getProductIndex;
import static model.Inventory.*;

/**
 * The Modify Product Controller.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class ModifyProductController implements Initializable {

    private int productNumber; // The Product number.

    private Product associatedPart; // The Associated part.

    private Part currentPart; // The Current part.


    private final int productIndex = getProductIndex(); // The product index.

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList(); // The associated parts.

    @FXML
    private TextField modifyNameText;
    @FXML
    private TextField modifyProductIdText;
    @FXML
    private TextField modifyInventoryText;
    @FXML
    private TextField modifyPriceText;
    @FXML
    private TextField modifyMaxText;
    @FXML
    private TextField modifyMinText;
    @FXML
    private TextField searchForPartText;
    @FXML
    private TableView<Part> topAddTableView;
    @FXML
    private TableColumn<Part, Integer> addTableColumnId;
    @FXML
    private TableColumn<Part, String> addTableColumnName;
    @FXML
    private TableColumn<Part, Integer> addTableColumnInventory;
    @FXML
    private TableColumn<Part, Double> addTableColumnPrice;
    @FXML
    private TableView<Part> bottomRemoveTableView;
    @FXML
    private TableColumn<Part, Integer> removeTableColumnId;
    @FXML
    private TableColumn<Part, String> removeTableColumnName;
    @FXML
    private TableColumn<Part, Integer> removeTableColumnInventory;
    @FXML
    private TableColumn<Part, Double> removeTableColumnPrice;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        associatedPart = getAllProducts().get(productIndex);

        productNumber = getAllProducts().get(productIndex).getId();
        modifyProductIdText.setText(Integer.toString(associatedPart.getId()));
        modifyNameText.setText(associatedPart.getName());
        modifyPriceText.setText(Double.toString(associatedPart.getPrice()));
        modifyMinText.setText(Integer.toString(associatedPart.getMin()));
        modifyMaxText.setText(Integer.toString(associatedPart.getMax()));
        modifyInventoryText.setText(Integer.toString(associatedPart.getStock()));

        topAddTableView.setItems(getAllParts());
        addTableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        removeTableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removeTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts = associatedPart.getAllAssociatedParts();
        bottomRemoveTableView.setItems(associatedParts);
    }

    /**
     * This method handles the OnAction event when a user searches for a part on the add product screen.
     * <p>
     * The search field will accept a part ID or Name used as a search criterion.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void onActionSearchForPart(ActionEvent actionEvent) {
        int searchByPartId;
        String searchByPartName;

        if (TypeValidator.isInteger(searchForPartText.getText())) {
            searchByPartId = Integer.parseInt((searchForPartText.getText()));
            lookupPart(searchByPartId);

            searchForPartText.clear();

            if (lookupPart(searchByPartId) == null) {
                topAddTableView.setItems(getAllParts());
                AlertManager.invalidSearchResults();
                return;
            }

            ObservableList<Part> temp = FXCollections.observableArrayList();
            temp.add(lookupPart(searchByPartId));
            topAddTableView.setItems(temp);
        } else {
            searchByPartName = searchForPartText.getText();
            lookupPart(searchByPartName);
            searchForPartText.clear();

            if (lookupPart(searchByPartName) == null || lookupPart(searchByPartName).isEmpty()) {
                topAddTableView.setItems(getAllParts());
                AlertManager.invalidSearchResults();
                return;
            }

            topAddTableView.setItems(lookupPart(searchByPartName));
        }
    }

    /**
     * This method handles the OnAction event for associating a part to a product.
     * <p>
     * When the "Add" button is clicked, it will add the selected part to the bottom
     * table of the Add Product screen.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void onActionAddPartToProduct(ActionEvent actionEvent) {
        currentPart = topAddTableView.getSelectionModel().getSelectedItem();
        boolean iterate = false;

        if (currentPart == null) {
            AlertManager.invalidPartSelection();
        }
        if (currentPart != null) {
            int compareId = currentPart.getId();

            for (Part associatedPart : associatedParts) {
                if (associatedPart.getId() == compareId) {
                    AlertManager.invalidPartAssociation();
                    iterate = true;
                }
            }
            if (!iterate) {
                associatedPart.addAssociatedPart(currentPart);
            }
        }
    }

    /**
     * This method handles the OnAction event for removing an associated part from a product.
     * <p>
     * When the "Remove Associated Part" button is clicked, it will remove the selected part
     * from bottom table of the Add Product screen.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void onActionDeleteAssociatedPart(ActionEvent actionEvent) {
        currentPart = bottomRemoveTableView.getSelectionModel().getSelectedItem();

        if (currentPart == null) {
            AlertManager.invalidPartSelection();
        }
        if (currentPart != null) {
            boolean delete = AlertManager.deleteConfirmation(currentPart.getName());
            if (delete) {
                associatedPart.deleteAssociatedPart(currentPart);
            }
        }
    }

    /**
     * This method handles the OnAction event for saving a new product.
     * <p>
     * When the "Save" button is clicked, it will add the new product
     * that is created with any optionally associated parts included.
     *
     * @param actionEvent the action event
     */
    @FXML
    private void onActionUpdateProduct(ActionEvent actionEvent) throws IOException {
        try {
            String name = modifyNameText.getText();
            double price = Double.parseDouble(modifyPriceText.getText());
            int inventory = Integer.parseInt(modifyInventoryText.getText());
            int min = Integer.parseInt(modifyMinText.getText());
            int max = Integer.parseInt(modifyMaxText.getText());

            double sumOfParts = 0.00;

            for (Part part : associatedParts) {
                sumOfParts = sumOfParts + part.getPrice();
            }

            if (sumOfParts > price) {
                AlertManager.invalidCostToPrice();
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
            } else {
                Product newProduct = new Product(productNumber, name, price, inventory, min, max);
                Inventory.updateProduct(productIndex, newProduct);

                for (Part part : associatedParts) {
                    newProduct.addAssociatedPart(part);
                }
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
     * @param actionEvent the action event.
     * @throws IOException the io exception.
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
