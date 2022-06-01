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

@Controller
public class PurchaseController {

    private final String INVALID_ID = "Invalid book Id:";
    private final String OUT_OF_STOKE  = "Sorry... an item is out of stoke. You will not be charged!";
    private final String EMPTY = "";

    @Value("${store.errorMessage}")
    private String errorMessage = EMPTY;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BookService bookService;

    @Resource(name = "basketBean")
    private BasketList basketList;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/showpurchases")
    public String showPurchasesGET(Model model) {
        model.addAttribute("totalSales", purchaseService.getTotalAmount());
        model.addAttribute("purchases", purchaseService.findByDates());
        return "admin/show-purchases";
    }
    @PostMapping("/showpurchases")
    public String showPurchasesPOST(Model model) {
        return showPurchasesGET(model);
    }

    @GetMapping("/purchaseitem")
    public String purchaseItemGET(Model model) {
        return "error";
    }

    @PostMapping("/purchaseitem")
    public String purchaseItemPOST(@RequestParam("id") long id, Model model) {
        errorMessage = EMPTY;
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
        BasketBook basketBook = basketList.findById(id);
        double totalAmountToPay = basketBook.getPrice() - basketBook.getPrice() * basketBook.getDiscount() / 100;

        try {
            basketList.delete(id);
            book.decreaseQuantity();
            bookService.saveBook(book);
            purchaseService.savePurchase(new Purchase(totalAmountToPay));
        } catch (Exception e) {
            basketList.clearAllItemsOfSameKind(id);
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

    @GetMapping("/purchase-all-shopping-basket")
    public String purchaseAllBasketGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
        return "store";
    }

    @PostMapping("/purchase-all-shopping-basket")
    public String purchaseAllBasketPOST(Model model) {
        errorMessage = EMPTY;
        double totalAmountToPay = 0.0;
        try {
            for (BasketBook basketBook : basketList.getBasketList()){
                Book book = bookService.getBook(basketBook.id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + basketBook.id));
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

