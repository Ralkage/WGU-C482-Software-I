package model;

/**
 * The Outsourced class.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class Outsourced extends Part {

    private String companyName; // The company name.

    /**
     * Instantiates a new Outsourced part.
     *
     * @param id          the id
     * @param name        the name
     * @param price       the price
     * @param stock       the stock
     * @param min         the min
     * @param max         the max
     * @param companyName the company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * Sets a company name of an outsourced part.
     *
     * @param companyName the company name to set for an outsourced part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Gets the company name of an outsourced part.
     *
     * @return the company name of an outsourced part.
     */
    public String getCompanyName() {
        return companyName;
    }
}
