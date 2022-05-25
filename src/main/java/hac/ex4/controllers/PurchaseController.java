package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.repo.Purchase;
import hac.ex4.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

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

    @RequestMapping("/save-purchase")
    public String savePurchase(@RequestParam("countBasketItems") String countBasketItems,
                               @RequestParam("totalAmountPay") String totalAmountPay,
                               Model model) {
        double amount = Double.parseDouble(totalAmountPay);
        getPurchaseRepo().save(new Purchase(amount));

        model.addAttribute("countBasketItems", countBasketItems);
        model.addAttribute("totalAmountPay", amount);
        return "user/purchase-item";
    }

}

