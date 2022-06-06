package hac.ex4.beans;

import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Bean containing the basket list.
 */
@Component
public class BasketList implements Serializable {

    /** List of books added to basket. */
    private ArrayList<BasketBook> basketList;

    /** Initialize new array list. */
    public BasketList() {
        this.basketList = new ArrayList<>();
    }

    /**
     * Return the basket book list.
     * @return - basket list.
     */
    public ArrayList<BasketBook> getBasketList() {
        return basketList;
    }

    /**
     * Get a book and if doesn't exist add this book to the list, if it is existing already - increase
     * quantity of this book by 1.
     * @param book - The book to add to the basket list.
     */
    public void add (Book book) {
        for (BasketBook basketBook : basketList) {
            if (basketBook.getIdBasket() == book.getId()) {
                basketBook.increaseQuantityBasketItem();
                return;
            }
        }
        basketList.add(new BasketBook(book.getId(), book.getName(), book.getImageSrc(),
                1, book.getPrice(), book.getDiscount()));
    }

    /** Clear the list. */
    public void clear() {
        basketList.clear();
    }

    /**
     * Get an ID of a book and decrease 1 book in the basket that has the same ID, and if the quantity
     * of this particular book reach to 0 then delete this book from the list.
     * @param idBook - The ID of the book to decrease/erase from list.
     */
    public void decreaseBook(long idBook) {
        for (BasketBook basketBook : basketList) {
           if (basketBook.getIdBasket() == idBook){
               basketBook.decreaseQuantityBasketItem();
               if (basketBook.getQuantityOfSameItemInBasket() == 0){
                   basketList.remove(basketBook);
                   break;
               }
           }
        }
    }

    /**
     * Get an ID of a book and delete the book in the list that has this ID.
     * @param idBook - the id of the book.
     */
    public void deleteBook(long idBook){
        basketList.removeIf(basketBook -> basketBook.getIdBasket() == idBook);
    }

    /**
     * Count how many items in the basket list (if a book is appearing twice its count for different items).
     * @return - Number of items added to the basket.
     */
    public Integer count(){
        Integer sumBasketBooks = 0;
        for(BasketBook basketBook : basketList){
            sumBasketBooks += basketBook.getQuantityOfSameItemInBasket();
        }
        return sumBasketBooks;
    }

    /**
     * Get an ID of a book and return the basket book item that correspond to this ID.
     * @param idBook - The ID of the book.
     * @return - The basket book with this ID if exist, else null.
     */
    public BasketBook findById(long idBook){
      return basketList.stream().filter(basketBook -> basketBook.getIdBasket() == idBook).findAny().orElse(null);
    }

}

