package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * Controller that handle all store (user pages, not including the purchase)
 */
@Controller
public class BookController {

    /** Error message - invalid book. */
    private final String INVALID_ID = "Invalid book Id:";

    /** Service for transaction */
    @Autowired
    private BookService bookService;

    /** The basket list */
    @Resource(name = "basketBean")
    private BasketList basketList;

    /** Error controller reference*/
    @Autowired
    private CustomErrorController customErrorController;

    /**
     * Return the main store page.
     * @param model - The model for view.
     * @return - Main store page.
     */
    public String storePage(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5onSales());
        return "store";
    }

    /**
     * Return basket page.
     * @param model - The model for view.
     * @return - Basket page.
     */
    public String basketPage(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "basket-shopping-list";
    }

    /**
     * Get a GET request and return the main store page.
     * @param model - The model for view.
     * @return - Main store page.
     */
    @GetMapping("/")
    public String main(Model model) {
        try {
            return storePage(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param searchInput - The substring to search.
     * @param model - The model for view.
     * @return - Main store page with the books that fits the research.
     */
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

    /**
     * Get a POST request including a substring of name of book to search in the database and return
     * all book that their name contains this substring.
     * @param searchInput - The substring.
     * @param model - The model for view.
     * @return - Main store page with the books that fits the research.
     */
    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("searchInput") String searchInput, Model model) {
        return searchGET(searchInput, model);
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - Main store page.
     */
    @GetMapping("/add-to-basket")
    public String addToBasketGET(Model model) {
        return storePage(model);
    }

    /**
     * Get a  POST req to add a book into the basket.
     * @param id - The id of the book to add (if exist already in the list - increase the quantity of
     *           the book).
     * @param model - The model for view.
     * @return - Main store page.
     */
    @PostMapping("/add-to-basket")
    public String addToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            return addToBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - Basket page.
     */
    @GetMapping("/plus-to-basket-same-item")
    public String plusToBasketGET(Model model) {
        return basketPage(model);
    }

    /**
     * Get a POST req to increase a book existing in the basket.
     * @param id - The id of the book to increase.
     * @param model - The model for view.
     * @return - Basket page.
     */
    @PostMapping("/plus-to-basket-same-item")
    public String plusToBasketPOST(@RequestParam("id") long id, Model model) {
        try {
            addNewItemToBasket(id);
            return plusToBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param id - The id of the book to decrease.
     * @param model - The model for view.
     * @return - Basket page.
     */
    @GetMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketGET(@PathVariable("id") long id, Model model) {
        return basketPage(model);
    }

    /**
     * Get a POST req to decrease a book existing in the basket.
     * @param id - The id of the book to decrease (if reaches 0 quantity of same book in basket - remove
     *           book from the basket list).
     * @param model - The model for view.
     * @return - Basket page.
     */
    @PostMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.decreaseBook(id);
            return decreaseItemFromBasketGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param id - The id of the book.
     * @param model - The model for view.
     * @return - Basket page.
     */
    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasketGET(@PathVariable("id") long id, Model model) {
        return basketPage(model);
    }

    /**
     * Get a POST req to delete a specific book existing in the basket.
     * @param id - The id of the book.
     * @param model - The model for view.
     * @return - Basket page.
     */
    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasketPOST(@PathVariable("id") long id, Model model) {
        try {
            basketList.deleteBook(id);
            return deleteFromBasketGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Implementation of GET request (see the POST route that correspond).
     * @param model - The model for view.
     * @return - Basket Page.
     */
    @GetMapping("/empty-basket")
    public String deleteAllBasketGET(Model model) {
        return basketPage(model);
    }

    /**
     * POST request to clear all books from the basket.
     * @param model - The model for view.
     * @return - Basket Page.
     */
    @PostMapping("/empty-basket")
    public String deleteAllBasketPOST(Model model) {
        try {
            basketList.clear();
            return deleteAllBasketGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * GET request to show the basket page.
     * @param model - The model for view.
     * @return - Basket Page.
     */
    @GetMapping("/basketshopping")
    public String basketShoppingListGET(Model model) {
        return basketPage(model);
    }

    /**
     * POST request to show the basket page.
     * @param model - The model for view.
     * @return - Basket Page.
     */
    @PostMapping("/basketshopping")
    public String basketShoppingListPOST(Model model) {
        try {
            return basketShoppingListGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Add book to basket list.
     * @param id - The id of the book.
     */
    void addNewItemToBasket(long id) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
        basketList.add(book);
    }
}

