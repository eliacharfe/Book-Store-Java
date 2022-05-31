package hac.ex4.beans;

import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.ArrayList;

@Component
public class BasketList implements Serializable {

    private ArrayList<BasketBook> basketList;

    public BasketList() {
        this.basketList = new ArrayList<>();
    }

    public ArrayList<BasketBook> getBasketList() {
        return basketList;
    }

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

    public void clear() {
        basketList.clear();
    }

    public void delete(long idBook) {
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

    public void clearAllItemsOfSameKind(long idBook){
        basketList.removeIf(basketBook -> basketBook.getIdBasket() == idBook);
    }

    public Integer count(){
        Integer sumBasketBooks = 0;
        for(BasketBook basketBook : basketList){
            sumBasketBooks += basketBook.getQuantityOfSameItemInBasket();
        }
        return sumBasketBooks;
    }

    public BasketBook findById(long idBook){
      return basketList.stream().filter(basketBook -> basketBook.getIdBasket() == idBook).findAny().orElse(null);
    }

}

