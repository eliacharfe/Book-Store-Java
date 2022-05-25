package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class PurchaseController {

    @Value("${store.message}")
    private String message = "";

    @Autowired
    private PurchaseRepository purchaseRepository;
    private PurchaseRepository getPurchaseRepo() {
        return purchaseRepository;
    }


    @PostMapping("/showpurchases")
    public String deleteAllBasket(Model model) {
        model.addAttribute("purchases", getPurchaseRepo().findAll());
        return "admin/show-purchases";
    }

//    @GetMapping("/navbar-basket-purchase")
//    public String getNavbarBasketPurchase(Model model) {
//        BasketList basketList = basketList ;
//
//                model.addAttribute("countBasketItems", basketList.count().toString());
//        return "includes/navbar-basket-purchase";
//    }

//    @GetMapping("/navbar")
//    public String getNavbar(Model model) {
//        model.addAttribute("countBasketItems", basketList.count().toString());
//        return "includes/navbar";
//    }
//
//    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
//    public String controlMapping2(
//            @RequestParam("id") long id,
//            @ModelAttribute("handlePurchase") final Object mapping1FormObject,
//            final BindingResult mapping1BindingResult,
//            final Model model) {
//
//        model.addAttribute("transformationForm", mapping1FormObject);
//
//        return "user/purchase-item";
//    }

//    @PostMapping("/purchaseitem")
//    public String purchaseItem(@RequestParam("id") long id, Model model) {
//        Book book = getBookRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
//        BasketBook basketBook = basketList.findById(id);
//        double totalAmount = basketBook.getPrice() - basketBook.getPrice() * basketBook.getDiscount() / 100;
//        basketList.delete(id);
//        book.setQuantity(book.getQuantity() - 1);
//
//        if (book.getQuantity() == 0) {
//            book.setQuantity(0);
//            getBookRepo().delete(book);
//        }
//        getBookRepo().save(book);
//
//        model.addAttribute("countBasketItems", basketList.count().toString());
//        model.addAttribute("totalAmountPay", totalAmount);
//        return "user/purchase-item";
//    }

}

