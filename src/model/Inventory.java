package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Add a new part.
     *
     * @param newPart the new part to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add a new product.
     *
     * @param newProduct the new product to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Lookup part by id.
     *
     * @param partId the part id to lookup for.
     * @return the part
     */
    public static Part lookupPart(int partId) {
        for (Part searchPart : getAllParts()) {
            if (searchPart.getId() == partId) {
                return searchPart;
            }
        }
        return null;
    }

    /**
     * Lookup product by id.
     *
     * @param productId the product id to lookup.
     * @return the product
     */
    public static Product lookupProduct(int productId) {
        for (Product searchProduct : getAllProducts()) {
            if (searchProduct.getId() == productId) {
                return searchProduct;
            }
        }

        return null;
    }

    /**
     * Lookup part by name.
     *
     * @param partName the part name to lookup.
     * @return the observable list of parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();

        if (partName.length() == 0) {
            foundParts = allParts;
        } else {
            for (Part allParts : allParts) {
                if (allParts.getName().toLowerCase().contains(partName.toLowerCase())) {
                    foundParts.add(allParts);
                }
            }
        }

        return foundParts;
    }

    /**
     * Lookup product by name.
     *
     * @param productName the product name to lookup.
     * @return the observable list of products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();

        if (productName.length() == 0) {
            foundProducts = allProducts;
        } else {
            for (Product allProduct : allProducts) {
                if (allProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                    foundProducts.add(allProduct);
                }
            }
        }

        return foundProducts;
    }

    /**
     * Update a part.
     *
     * @param index        the index of the part to update.
     * @param selectedPart the selected part to update.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Update a product.
     *
     * @param index      the index of the part product update.
     * @param newProduct the new product to update.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Delete a part.
     *
     * @param selectedPart the selected part to delete.
     * @return true to delete and false to abort.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Delete a product.
     *
     * @param selectedProduct the selected product to delete.
     * @return true to delete and false to abort.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts.
     *
     * @return the all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products.
     *
     * @return all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Gets part number/ID.
     *
     * @return the part number/ID.
     */
    public static int getPartNumber() {
        return getAllParts().size();
    }

    /**
     * Gets product number/ID.
     *
     * @return the product number/ID.
     */
    public static int getProductNumber() {
        return getAllProducts().size();
    }
}
