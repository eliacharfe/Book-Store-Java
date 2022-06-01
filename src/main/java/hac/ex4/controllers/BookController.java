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
public class BookController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private BookService bookService;

    @Resource(name = "basketBean")
    private BasketList basketList;

    @Autowired
    private CustomErrorController customErrorController;

    @GetMapping("/")
    public String main(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
            return "user/store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/search")
    public String searchGET(@ModelAttribute("searchInput") String searchInput, Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findByNameContains(searchInput));
            return "user/store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("searchInput") String searchInput, Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findByNameContains(searchInput));
            return "user/store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/add-to-basket")
    public String addToBasket(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
            return "user/store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/add-to-basket")
    public String addToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
            return "user/store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/plus-to-basket-same-item")
    public String plusToBasketGET(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/plus-to-basket-same-item")
    public String plusToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketGET(@PathVariable("id") long id, Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.delete(id);
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasketGET(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.clearAllItemsOfSameKind(id);
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/empty-basket")
    public String deleteAllBasketGet(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/empty-basket")
    public String deleteAllBasketPOST(Model model) {
        try {
            basketList.clear();
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }


    @GetMapping("/basketshopping")
    public String basketShoppingListGET(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
           return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/basketshopping")
    public String basketShoppingListPOST(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    void addNewItemToBasket(long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        basketList.add(book);
    }
}

