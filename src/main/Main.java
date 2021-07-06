package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class creates a new application.
 *
 * @author Christian Lopez
 * Software I - C482
 * <p>
 * FUTURE ENHANCEMENT
 * <p>
 * Multi-select should be introduced when deleting more than one part or product from the Main
 * screen table. This feature can also be added to the Add Product screen so that users are able add or remove
 * one or more associated parts instead of having to click then individually. The parts selected and added from the top
 * table should no longer appear if already associated to a product; this would lead to less confusion to whether or not
 * the part was already added and would eliminate the alert that a the part is already associated to a product.
 * Another feature that would prove beneficial to add to the Inventory Management System would be to add another column
 * to the Parts table. The row for this column will have a value associated to whether or not the part is in-house or
 * outsourced.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * This is the main method. The entry point of the Inventory Management System.
     * <p>
     * The javadocs folder is located within the root directory of this project and labeled as "javadocs".
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
