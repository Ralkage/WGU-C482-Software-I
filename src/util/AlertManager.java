package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * The `AlertManager` utility class.
 * <p>
 * This utility class is used throughout the application to serve up different
 * alert types depending on the action being performed.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class AlertManager {
    /**
     * This method is used a building block for creating alerts of different types within the different
     * methods introduced in this class.
     *
     * @param title     The title for the alert dialog.
     * @param message   The message contained within the alert dialog.
     * @param alertType The type of alert (NONE, INFORMATION, WARNING, ERROR, or CONFIRMATION)
     */
    private static void alertTemplate(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(String.valueOf(message));
        alert.showAndWait();
    }

    /**
     * This method handles the invalid name field error dialog.
     */
    public static void invalidNameField() {
        String title = "Error Dialog";
        String message = "The name field is either empty or the format is invalid.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid price error dialog.
     */
    public static void invalidPrice() {
        String title = "Error Dialog";
        String message = "The price cannot be negative or $0.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid range error dialog.
     */
    public static void invalidRange() {
        String title = "Error Dialog";
        String message = "The MIN must be less than or equal to MAX.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid type error dialog.
     *
     * @param fieldName the field name.
     * @param fieldType the field type.
     */
    public static void invalidType(String fieldName, String fieldType) {
        String title = "Error Dialog";
        String message = "The " + fieldName + " field must contain a " + fieldType + " value only.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid inventory level error dialog.
     */
    public static void invalidInventoryLevel() {
        String title = "Error Dialog";
        String message = "The minimum stock level must be set to at least 1.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid inventory level range error dialog.
     */
    public static void invalidInventoryLevelRange() {
        String title = "Error Dialog";
        String message = "The inventory level must be between MIN and MAX.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid part selection error dialog.
     */
    public static void invalidPartSelection() {
        String title = "Error Dialog";
        String message = "You must first select a part in order to perform this action.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid product selection error dialog.
     */
    public static void invalidProductSelection() {
        String title = "Error Dialog";
        String message = "You must first select a product in order to perform this action.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid search results information dialog.
     */
    public static void invalidSearchResults() {
        String title = "Information Dialog";
        String message = "No results found for this search criteria.";
        Alert.AlertType errorAlert = Alert.AlertType.INFORMATION;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid cost to price error dialog.
     */
    public static void invalidCostToPrice() {
        String title = "Error Dialog";
        String message = "Price must be greater than the overall cost of associated parts.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid part association error dialog.
     */
    public static void invalidPartAssociation() {
        String title = "Error Dialog";
        String message = "The selected part is already associated to this product.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the invalid product deletion error dialog.
     */
    public static void inValidProductDeletion() {
        String title = "Error Dialog";
        String message = "You cannot delete products with associated parts.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the missing field values error dialog.
     */
    public static void missingFieldValues() {
        String title = "Error Dialog";
        String message = "All fields are required.";
        Alert.AlertType errorAlert = Alert.AlertType.ERROR;

        alertTemplate(title, message, errorAlert);
    }

    /**
     * This method handles the delete confirmation dialog.
     *
     * @param delete the object to be deleted.
     * @return returns true or false.
     */
    public static boolean deleteConfirmation(String delete) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation Dialog");
        confirmation.setHeaderText("Are you sure that you want to delete " + delete + "?");
        Optional<ButtonType> result = confirmation.showAndWait();

        // The orElse() method is used instead of get() because get() will throw an exception if the value is null.
        return result.orElse(null) == ButtonType.OK;
    }

    /**
     * This method handles the cancel confirmation dialog.
     */
    public static void cancelConfirmation() {
        String title = "Confirmation Dialog";
        String message = "Are you sure you want leave this screen without saving?";
        Alert.AlertType confirmation = Alert.AlertType.CONFIRMATION;

        alertTemplate(title, message, confirmation);
    }

    /**
     * This method handles the cancel confirmation dialog.
     *
     * @return true if the OK button is clicked or false if the cancel button is clicked instead.
     */
    public static boolean exitConfirmation() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation Dialog");
        confirmation.setHeaderText("Are you sure you want exit out of this application?");
        Optional<ButtonType> result = confirmation.showAndWait();

        // The orElse() method is used instead of get() because get() will throw an exception if the value is null.
        return result.orElse(null) == ButtonType.OK;
    }
}
