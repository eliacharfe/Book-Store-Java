package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Fragments control.
 */
@Controller
public class FragmentsController {

    /** The basket list */
    @Resource(name = "basketBean")
    private BasketList basketList;

    /**
     * Return main store navbar.
     * @param model - The model for view.
     * @return - navbar html page.
     */
    @GetMapping("/navbar")
    public String getNavbar(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        return "includes/navbar";
    }

    /**
     * Return basket/purchases navbar.
     * @param model - The model for view.
     * @return - the navbar of basket and purchases html page.
     */
    @GetMapping("/navbar-basket-purchase")
    public String getNavbarBasketPurchase(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        return "includes/navbar-basket-purchase";
    }

}

