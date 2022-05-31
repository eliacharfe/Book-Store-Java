package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

@Controller
public class BookController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private BookService bookService;

    @Resource(name = "basketBean")
    private BasketList basketList;

//    @Autowired
//    private PurchaseController purchaseController;

    @Autowired
    private CustomErrorController customErrorController;

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

    @GetMapping("/")
    public String main(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("topFiveOnSale", bookService.findTop5OnSales());
            return "user/store";
        }
        catch (Exception e) {
            customErrorController.handleError();
        }
        return "user/store";
    }

    @GetMapping("/admin")
    public String adminEditWindow(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    @GetMapping("/addnewbookform")
    public String addNewBook(Model model) {
        model.addAttribute("book", new Book("No name",
                "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg",
                10, 79.99, 7.0));
        return "admin/add-book";
    }

    @GetMapping("/addbook")
    public String addBookGET(@Valid Book book, BindingResult result, Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }
    @PostMapping("/addbook")
    public String addBookPOST(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-book";
        }
        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    @GetMapping("/edit/{id}")
    public String editBookGET(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "admin/update-book";
    }
    @PostMapping("/edit/{id}")
    public String editBookPOST(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "admin/update-book";
    }

    @GetMapping("/update/{id}")
    public String updateUserGET(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }
    @PostMapping("/update/{id}")
    public String updateUserPOST(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "admin/update-book";
        }
        bookService.saveBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteBookGET(Model model) {
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }
    @PostMapping("/delete/{id}")
    public String deleteBookPOST(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        bookService.deleteBook(book);
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    @GetMapping("/search")
    public String searchGET(@ModelAttribute("searchInput") String searchInput, Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findByNameContains(searchInput) );
        return "user/store";
    }
    @PostMapping("/search")
    public String searchPOST(@ModelAttribute("searchInput") String searchInput, Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findByNameContains(searchInput) );
        return "user/store";
    }

    @GetMapping("/add-to-basket")
    public String addToBasket(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5OnSales());
        return "user/store";
    }
    @PostMapping("/add-to-basket")
    public String addToBasketPOST(@RequestParam("id") long id, Model model) {
        addNewItemToBasket(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", bookService.findTop5OnSales());
        return "user/store";
    }

    @GetMapping("/plus-to-basket-same-item")
    public String plusToBasketGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }
    @PostMapping("/plus-to-basket-same-item")
    public String plusToBasketPOST(@RequestParam("id") long id, Model model) {
        addNewItemToBasket(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @GetMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }
    @PostMapping("/minus-to-basket-same-item/{id}")
    public String decreaseItemFromBasketPOST(@PathVariable("id") long id, Model model) {
        basketList.delete(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @GetMapping("/delete-from-basket/{id}")
    public String deleteFromBasketGET(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }
    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasketPOST(@PathVariable("id") long id, Model model) {
        basketList.clearAllItemsOfSameKind(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @GetMapping("/empty-basket")
    public String deleteAllBasketGet(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }
    @PostMapping("/empty-basket")
    public String deleteAllBasketPOST(Model model) {
        basketList.clear();
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }


    @GetMapping("/basketshopping")
    public String basketShoppingListGET(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        }
        catch (Exception e){
            customErrorController.handleError();
        }
        return "user/basket-shopping-list";
    }
    @PostMapping("/basketshopping")
    public String basketShoppingListPOST(Model model) {
        try {
            model.addAttribute("countBasketItems", basketList.count().toString());
            model.addAttribute("basket", basketList.getBasketList());
            return "user/basket-shopping-list";
        }
        catch (Exception e){
            customErrorController.handleError();
        }
        return "user/basket-shopping-list";
    }

    void addNewItemToBasket(long id){
        Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        basketList.add(book);
    }
}

