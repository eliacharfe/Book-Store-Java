package hac.ex4.classes;

import java.io.Serializable;

/**
 * Regular class that represent a book of the basket list.
 */
public class BasketBook implements Serializable {

    /** The ID of the book */
    public long id;
    /** The title of the book */
    public String name;
    /** The source to the image cover of the book */
    public String imageSrc;
    /** Counting the number of books of the same kind added to the list */
    public int quantityOfSameItemInBasket;
    /** The price of the book */
    public double price;
    /** The discount */
    public double discount;

    /**
     * Construct a basket book item.
     * @param id - The ID of the book.
     * @param name - The title of the book.
     * @param imageSrc - The source to the image cover of the book.
     * @param quantityOfSameItemInBasket - Counting the number of books of the same kind added to the list.
     * @param price - The price of the book.
     * @param discount - The discount.
     */
    public BasketBook(long id, String name, String imageSrc, int quantityOfSameItemInBasket,
                      double price, double discount) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
        this.quantityOfSameItemInBasket = quantityOfSameItemInBasket;
        this.price = price;
        this.discount = discount;
    }

    /**
     * Get ID book.
     * @return - ID
     */
    public long getIdBasket() {
        return id;
    }

    /**
     * Get quantity of same book in basket.
     * @return - quantity of same book in basket.
     */
    public int getQuantityOfSameItemInBasket(){
        return quantityOfSameItemInBasket;
    }

    /**
     * Get price of book.
     * @return - The price.
     */
    public double getPrice() {return price;}

    /**
     * Get the discount of the book.
     * @return - The discount.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Increase quantity of the same book in basket by 1.
     */
    public void increaseQuantityBasketItem(){
        this.quantityOfSameItemInBasket += 1;
    }

    /**
     * Decrease quantity of the same book in basket by 1.
     */
    public void decreaseQuantityBasketItem(){
        this.quantityOfSameItemInBasket -= 1;
    }
}

