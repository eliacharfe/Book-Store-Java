package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.repo.Purchase;
import hac.ex4.repo.PurchaseRepository;
import hac.ex4.services.BookService;
import hac.ex4.services.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class PurchaseController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private BookService bookService;

    @Resource(name = "basketBean")
    private BasketList basketList;


    @GetMapping("/showpurchases")
    public String showPurchasesGET(Model model) {
        model.addAttribute("totalSales", purchaseService.getTotalAmount());
        model.addAttribute("purchases", purchaseService.findByDates());
        return "admin/show-purchases";
    }
    @PostMapping("/showpurchases")
    public String showPurchasesPOST(Model model) {
        model.addAttribute("totalSales", purchaseService.getTotalAmount());
        model.addAttribute("purchases", purchaseService.findByDates());
        return "admin/show-purchases";
    }

    @GetMapping("/purchaseitem")
    public String purchaseItemGET(Model model) {
        return "error";
    }
    @PostMapping("/purchaseitem")
    public String purchaseItemPOST(@RequestParam("id") long id, Model model) {
        errorMessage = "";
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        BasketBook basketBook = basketList.findById(id);
        double totalAmountToPay = basketBook.getPrice() - basketBook.getPrice() * basketBook.getDiscount() / 100;

        try {
            basketList.delete(id);
            book.decreaseQuantity();
            bookService.saveBook(book);
            purchaseService.savePurchase(new Purchase(totalAmountToPay));
        } catch (Exception e) {
            errorMessage = String.format("Sorry... %s is out of stoke. You will not be charged!", book.getName());
            totalAmountToPay = 0.00;
        } finally {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("totalAmountPay", totalAmountToPay);
            model.addAttribute("errorMessage", errorMessage);
        }

        return "user/purchase-item";
    }
}

