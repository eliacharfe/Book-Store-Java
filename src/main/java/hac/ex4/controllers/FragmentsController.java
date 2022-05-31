package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
public class FragmentsController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Resource(name = "basketBean")
    private BasketList basketList;


    @GetMapping("/navbar-basket-purchase")
    public String getNavbarBasketPurchase(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        return "includes/navbar-basket-purchase";
    }
    @GetMapping("/navbar")
    public String getNavbar(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        return "includes/navbar";
    }
}
