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

    private final String INVALID_ID = "Invalid book Id:";

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private BookService bookService;

    @Resource(name = "basketBean")
    private BasketList basketList;

    @Autowired
    private CustomErrorController customErrorController;

    public String storePage(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
        return "store";
    }

    public String basketPage(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "basket-shopping-list";
    }

    @GetMapping("/")
    public String main(Model model) {
        try {
            return storePage(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/search")
    public String searchGET(@ModelAttribute("searchInput") String searchInput, Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findByNameContains(searchInput));
            return "store";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("searchInput") String searchInput, Model model) {
        return searchGET(searchInput, model);
    }

    @GetMapping("/add-to-basket")
    public String addToBasketGET(Model model) {
        return storePage(model);
    }

    @PostMapping("/add-to-basket")
    public String addToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            return addToBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/plus-to-basket-same-item")
    public String plusToBasketGET(Model model) {
        return basketPage(model);
    }

    @PostMapping("/plus-to-basket-same-item")
    public String plusToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            return plusToBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketGET(@PathVariable("id") long id, Model model) {
        return basketPage(model);
    }

    @PostMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.delete(id);
            return decreaseItemFromBasketGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasketGET(@PathVariable("id") long id, Model model) {
        return basketPage(model);
    }

    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.clearAllItemsOfSameKind(id);
            return deleteFromBasketGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/empty-basket")
    public String deleteAllBasketGET(Model model) {
        return basketPage(model);
    }

    @PostMapping("/empty-basket")
    public String deleteAllBasketPOST(Model model) {
        try {
            basketList.clear();
            return deleteAllBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @GetMapping("/basketshopping")
    public String basketShoppingListGET(Model model) {
        return basketPage(model);
    }

    @PostMapping("/basketshopping")
    public String basketShoppingListPOST(Model model) {
        try {
            return basketShoppingListGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    void addNewItemToBasket(long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
        basketList.add(book);
    }
}

