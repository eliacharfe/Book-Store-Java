package hac.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Book entity
 */
@Entity
public class Book implements Serializable {

    /** The id of the book */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /** The title of the book */
    @NotEmpty(message = "Name is mandatory")
    private String name;

    /** The source of the image */
    @NotEmpty(message = "Image source is required")
    private String imageSrc;

    /** The quantity of the book */
    @Min(value = 0, message = "Quantity should be greater than or equal to 0")
    private int quantity;

    /** The price of the book. */
    @Min(value = 1, message = "Price should be greater than 0 ")
    private double price;

    /** The discount on the book. */
    @Min(value = 1, message = "Discount percentage should be greater than or equal to 0 ")
    @Max(value = 100, message = "Discount percentage should be less than or equal to 100 ")
    private double discount;

    /** Empty constructor. */
    public Book() {}

    /**
     * Construct a book.
     * @param name - The title of the book.
     * @param imageSrc - The path of the image of the book's cover.
     * @param quantity - The quantity of the book in the database.
     * @param price - The price of the book.
     * @param discount -The discount on the book.
     */
    public Book(String name, String imageSrc, int quantity, double price, double discount) {
        this.name = name.trim();
        this.imageSrc = imageSrc.trim();
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    /**
     * Set id
     * @param id - id to set.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Get the id.
     * @return - id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the name.
     * @param name - the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get the name.
     * @return - The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the url of the image.
     * @param imageSrc - new url
     */
    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
    /**
     * Get the url of the image.
     * @return - url image.
     */
    public String getImageSrc() {
        return imageSrc;
    }

    /**
     * Set the quantity.
     * @param quantity - new quantity.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Get the quantity.
     * @return - the current quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Set new price.
     * @param price - new price.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get the price.
     * @return - the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set new discount.
     * @param discount - new discount.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Get the discount.
     * @return - current discount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Decrease the quantity by 1.
     */
    public void decreaseQuantity(){
        this.quantity -= 1;
    }

    /**
     * Return the book as string.
     * @return - String.
     */
    @Override
    public String toString() {
        return "Book{" + "id=" + id +
                     ", name=" + name +
                     ", quantity=" + quantity +
                     ", price=" + price +
                     ", discount=" + discount +
                     '}';
    }
}

