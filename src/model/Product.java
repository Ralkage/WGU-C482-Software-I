package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product class.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     * @param stock the stock
     * @param min   the min
     * @param max   the max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * The default constructor.
     */
    public Product() {
    }

    /**
     * Sets the id of a product.
     *
     * @param id the id to set for a product.
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Sets the name of a product.
     *
     * @param name the name to set for a product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the price/cost of a product.
     *
     * @param price the price/cost to set for a product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets the stock amount of a product.
     *
     * @param stock the stock amount to set for a product.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the minimum stock/inventory amount of a product.
     *
     * @param min the minimum stock/inventory amount to set for a product.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets the maximum stock/inventory amount of a product.
     *
     * @param max the maximum stock/inventory amount to set for a product.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Gets the product id.
     *
     * @return the id of a product.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of a product.
     *
     * @return the name of a product.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the price/cost of a product.
     *
     * @return the price/cost of a product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the stock/inventory amount of a product.
     *
     * @return the stock/inventory amount of a product.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock/inventory amount of a product.
     *
     * @return the minimum stock/inventory amount of a product.
     */
    public int getMin() {
        return min;
    }

    /**
     * Gets the maximum stock/inventory amount of a product.
     *
     * @return the maximum stock/inventory amount of a product.
     */
    public int getMax() {
        return max;
    }

    /**
     * Adds an associated part to a product.
     *
     * @param part the associating part to add to a product.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes/removes an associated part from a product.
     *
     * @param selectedAssociatedPart the selected associating part to delete/remove.
     * @return false boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Gets all the associated parts of a product..
     *
     * @return all associated parts of a product.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
