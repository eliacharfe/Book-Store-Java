package hac.ex4.controllers;

import hac.ex4.repo.Purchase;
import hac.ex4.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@Controller
public class PurchaseController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private PurchaseRepository purchaseRepository;
    private PurchaseRepository getPurchaseRepo() {
        return purchaseRepository;
    }

    @PostMapping("/showpurchases")
    public String deleteAllBasket(Model model) {
        double totalSales = 0;
        for (Purchase purchase : getPurchaseRepo().findAll()){
            totalSales += purchase.getAmount();
        }
        model.addAttribute("totalSales", totalSales); // getPurchaseRepo().sumAllByAmount()
        model.addAttribute("purchases", getPurchaseRepo().findAllByOrderByDateTime());
        return "admin/show-purchases";
    }

    @RequestMapping("/save-purchase")
    public String savePurchase(@RequestParam("countBasketItems") String countBasketItems,
                               @RequestParam("totalAmountPay") String totalAmountPay,
                               @RequestParam("errorMessage") String errorMessage,
                               Model model) {
        double amount = Double.parseDouble(totalAmountPay);
        if (amount > 0)
            getPurchaseRepo().save(new Purchase(amount));

        model.addAttribute("countBasketItems", countBasketItems);
        model.addAttribute("totalAmountPay", amount);
        model.addAttribute("errorMessage", errorMessage);
        return "user/purchase-item";
    }
}

