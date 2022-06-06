package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.repo.Purchase;
import hac.ex4.services.BookService;
import hac.ex4.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Controller that handle all purchases (user + admin)
 */
@Controller
public class PurchaseController {

    /** Error message - invalid book. */
    private final String INVALID_ID = "Invalid book Id:";
    /** Error message - out of stoke */
    private final String OUT_OF_STOKE  = "Sorry... an item is out of stoke. You will not be charged!";
    /** Empty string */
    private final String EMPTY = "";

    /** We inject a property from the application.properties  file */
    @Value("${store.errorMessage}")
    private String errorMessage = EMPTY;

    /** Service purchase (for transaction). */
    @Autowired
    private PurchaseService purchaseService;

    /** Service book (for transaction). */
    @Autowired
    private BookService bookService;

    /** The basket list */
    @Resource(name = "basketBean")
    private BasketList basketList;

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - show purchases page.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showpurchases")
    public String showPurchasesGET(Model model) {
        model.addAttribute("totalSales", purchaseService.getTotalAmount());
        model.addAttribute("purchases", purchaseService.findByDates());
        return "admin/show-purchases";
    }

    /**
     * POST request to show purchases (only for admin option).
     * @param model - The model for view.
     * @return - show purchases page.
     */
    @PostMapping("/showpurchases")
    public String showPurchasesPOST(Model model) {
        return showPurchasesGET(model);
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - Main store page.
     */
    @GetMapping("/purchaseitem")
    public String purchaseItemGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
        return "store";
    }

    /**
     * POST req to purchase a single book in the basket book.
     * @param id - id of the book to purchase.
     * @param model - The model for view.
     * @return - Notification page of the purchase (if succeeded or not)
     */
    @PostMapping("/purchaseitem")
    public String purchaseItemPOST(@RequestParam("id") long id, Model model) {
        errorMessage = EMPTY;
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
        BasketBook basketBook = basketList.findById(id);
        double totalAmountToPay = basketBook.getPrice() - basketBook.getPrice() * basketBook.getDiscount() / 100;

        try {
            basketList.decreaseBook(id);
            book.decreaseQuantity();
            bookService.saveBook(book);
            purchaseService.savePurchase(new Purchase(totalAmountToPay));
        } catch (Exception e) {
            basketList.deleteBook(id);
            errorMessage = String.format("Sorry... %s is out of stoke. You will not be charged!",
                    book.getName());
            totalAmountToPay = 0.00;
        } finally {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("totalAmountPay", totalAmountToPay);
            model.addAttribute("errorMessage", errorMessage);
        }
        return "purchase-item";
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - Main store page.
     */
    @GetMapping("/purchase-all-shopping-basket")
    public String purchaseAllBasketGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
        return "store";
    }

    /**
     * POST req to purchase all the books in the basket book.
     * @param model - The model for view.
     * @return - Notification page of the purchase (if succeeded or not).
     */
    @PostMapping("/purchase-all-shopping-basket")
    public String purchaseAllBasketPOST(Model model) {
        errorMessage = EMPTY;
        double totalAmountToPay = 0.0;
        try {
            for (BasketBook basketBook : basketList.getBasketList()){
                Book book = bookService.getBook(basketBook.getIdBasket()).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + basketBook.getIdBasket()));
                totalAmountToPay += basketBook.getQuantityOfSameItemInBasket() *
                        (book.getPrice() - book.getPrice() * book.getDiscount() / 100);

                book.setQuantity(book.getQuantity() - basketBook.getQuantityOfSameItemInBasket());
                bookService.saveBook(book);
            }
            basketList.clear();
            purchaseService.savePurchase(new Purchase(totalAmountToPay));
        } catch (Exception e) {
            errorMessage = OUT_OF_STOKE ;
            totalAmountToPay = 0.00;
        } finally {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("totalAmountPay", totalAmountToPay);
            model.addAttribute("errorMessage", errorMessage);
        }
        return "purchase-item";
    }
}

