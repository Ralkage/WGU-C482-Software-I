package controller;

import model.Inventory;
import model.Part;
import model.Product;
import util.AlertManager;
import util.TypeValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Main screen controller.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class MainScreenController implements Initializable {
    Stage stage; // The stage.
    Parent scene; // The scene.

    private static int partIndex; // The part index.
    private static int productIndex; // The product index.

    @FXML
    private TextField searchForPartText;
    @FXML
    private TableView<Part> tableViewParts;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField searchForProductText;
    @FXML
    private TableView<Product> tableViewProducts;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    public void initialize(URL url, ResourceBundle rb) {
        tableViewParts.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableViewProducts.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * This method handles the OnAction event when a user searches for a part.
     * <p>
     * The search field will accept a Part ID or Name used as a search criterion.
     *
     * @param actionEvent the action event.
     */
    @FXML
    private void searchForPart(ActionEvent actionEvent) {
        int searchByPartId;
        String searchByPartName;

        if (TypeValidator.isInteger(searchForPartText.getText())) {
            searchByPartId = Integer.parseInt((searchForPartText.getText()));
            Inventory.lookupPart(searchByPartId);
            searchForPartText.clear();

            if (Inventory.lookupPart(searchByPartId) == null) {
                tableViewParts.setItems(Inventory.getAllParts());
                AlertManager.invalidSearchResults();
                return;
            }

            ObservableList<Part> temporaryProduct = FXCollections.observableArrayList();
            temporaryProduct.add(Inventory.lookupPart(searchByPartId));
            tableViewParts.setItems(temporaryProduct);
        } else {
            searchByPartName = searchForPartText.getText();
            Inventory.lookupPart(searchByPartName);
            searchForPartText.clear();

            if (Inventory.lookupPart(searchByPartName) == null || Inventory.lookupPart(searchByPartName).isEmpty()) {
                tableViewParts.setItems(Inventory.getAllParts());
                AlertManager.invalidSearchResults();
                return;
            }

            tableViewParts.setItems(Inventory.lookupPart(searchByPartName));
        }
    }

    /**
     * This method handles the OnAction event when a user searches for a product.
     * <p>
     * The search field will accept a Product ID or Name used as a search criterion.
     *
     * @param actionEvent the action event.
     */
    @FXML
    private void searchForProduct(ActionEvent actionEvent) {
        int searchByProductId;
        String searchByPartName;

        if (TypeValidator.isInteger(searchForProductText.getText())) {
            searchByProductId = Integer.parseInt((searchForProductText.getText()));
            Inventory.lookupProduct(searchByProductId);
            searchForProductText.clear();

            if (Inventory.lookupPart(searchByProductId) == null) {
                tableViewProducts.setItems(Inventory.getAllProducts());
                AlertManager.invalidSearchResults();
                return;
            }

            ObservableList<Product> temp = FXCollections.observableArrayList();
            temp.add(Inventory.lookupProduct(searchByProductId));
            tableViewProducts.setItems(temp);
        } else {
            searchByPartName = searchForProductText.getText();
            Inventory.lookupPart(searchByPartName);
            searchForProductText.clear();
            if (Inventory.lookupProduct(searchByPartName) == null || Inventory.lookupProduct(searchByPartName).isEmpty()) {
                tableViewProducts.setItems(Inventory.getAllProducts());
                AlertManager.invalidSearchResults();
                return;
            }

            tableViewProducts.setItems(Inventory.lookupProduct(searchByPartName));
        }
    }

    /**
     * Gets the part index.
     *
     * @return the part index.
     */
    public static int getPartIndex() {
        return partIndex;
    }

    /**
     * Gets the product index.
     *
     * @return the product index.
     */
    public static int getProductIndex() {
        return productIndex;
    }

    /**
     * This method handles the OnAction event add a part.
     *
     * @param actionEvent the action event.
     * @throws IOException the io exception.
     */
    public void onActionToAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPart.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method handles the OnAction event to add a product.
     *
     * @param actionEvent the action event.
     * @throws IOException the io exception.
     */
    public void onActionToAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProduct.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method handles the OnAction event to take the user to the modify part screen.
     *
     * @param actionEvent the action event.
     * @throws IOException the io exception.
     */
    @FXML
    public void onActionModifyPartScreen(ActionEvent actionEvent) throws IOException {
        Part modifyPart = tableViewParts.getSelectionModel().getSelectedItem();

        if (modifyPart == null) {
            AlertManager.invalidPartSelection();
            return;
        }

        partIndex = Inventory.getAllParts().indexOf(modifyPart);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPart.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method handles the OnAction event to take the user to the modify product screen.
     *
     * @param actionEvent the action event.
     * @throws IOException the io exception.
     */
    @FXML
    public void onActionModifyProductScreen(ActionEvent actionEvent) throws IOException {
        Product modifyProduct = tableViewProducts.getSelectionModel().getSelectedItem();

        if (modifyProduct == null) {
            AlertManager.invalidProductSelection();
            return;
        }

        productIndex = Inventory.getAllProducts().indexOf(modifyProduct);

        stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProduct.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * This method handles the OnAction event to delete a part.
     *
     * @param actionEvent the action event.
     */
    @FXML
    public void onActionDeletePart(ActionEvent actionEvent) {
        Part partToDelete = tableViewParts.getSelectionModel().getSelectedItem();
        if (partToDelete == null) {
            AlertManager.invalidPartSelection();
            return;
        }

        boolean deleteAction = AlertManager.deleteConfirmation(partToDelete.getName());

        if (deleteAction) {
            Inventory.deletePart(partToDelete);
            // The following line of code ensures that after a successful search and deletion occurs,
            // the part list is repopulated. Otherwise, the deleted part will still show even after it's
            // deleted from the main screen which cause the user to think that the part wasn't actually deleted.
            tableViewParts.setItems(Inventory.getAllParts());
        }
    }

    /**
     * This method handles the OnAction event to delete a product.
     *
     * @param actionEvent the action event.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent actionEvent) {
        Product toDelete = tableViewProducts.getSelectionModel().getSelectedItem();
        if (toDelete == null) {
            AlertManager.invalidProductSelection();
            return;
        }
        int i = toDelete.getAllAssociatedParts().size();
        if (i > 0) {
            AlertManager.inValidProductDeletion();
            return;
        }
        boolean delete = AlertManager.deleteConfirmation(toDelete.getName());
        if (delete) {
            Inventory.deleteProduct(toDelete);
            // The following lines of code ensures that after a successful search and deletion occurs,
            // the product list is repopulated. Otherwise, the deleted product will still show even after it's
            // deleted from the main screen which cause the user to think that the product wasn't actually deleted.
            tableViewProducts.setItems(Inventory.getAllProducts());
        }
    }


    /**
     * This method handles the onAction event when trying to exit out of the application.
     * <p>
     * A condition is added so that when the "Cancel" button is clicked,
     * it will not exit out of the application whereas the "Ok" button should.
     *
     * @param actionEvent the action event.
     */
    public void onActionExitButton(ActionEvent actionEvent) {
        if (AlertManager.exitConfirmation()) {
            System.exit(0);
        }
    }
}