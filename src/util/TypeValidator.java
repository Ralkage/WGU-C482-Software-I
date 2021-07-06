package util;

/**
 * The `TypeValidator` utility class.
 * <p>
 * This utility class is used throughout the Inventory Management System to check
 * whether or not an input value is actually an integer.
 *
 * @author Christian Lopez
 * Software I - C482
 */
public class TypeValidator {
    /**
     * This method checks if the input is of type Integer.
     *
     * @param input the input that will be checked.
     * @return returns true if the input value contains and integer or false if it doesn't.
     */
    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
