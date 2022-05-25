package hac.ex4.controllers;

import hac.ex4.beans.BasketList;
import hac.ex4.classes.BasketBook;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
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
    private BookRepository bookRepository;
    private BookRepository getBookRepo() {
        return bookRepository;
    }

    @Resource(name = "basketBean")
    private BasketList basketList;

    @Autowired
    private PurchaseController purchaseController;

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
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", getBookRepo().findFirst5ByOrderByDiscountDesc());
        return "user/store";
    }

    @GetMapping("/admin")
    public String adminEditWindow(Model model) {
        model.addAttribute("books", getBookRepo().findAll());
        return "admin/admin";
    }

    @GetMapping("/addnewbookform")
    public String addNewBook(Model model) {
        model.addAttribute("book", new Book("No name",
                "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg",
                10, 79.99, 7.0));
        return "admin/add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/add-book";
        }
        getBookRepo().save(book);
        model.addAttribute("books", getBookRepo().findAll());
        return "admin/admin";
    }

    @PostMapping("/edit")
    public String editBook(@RequestParam("id") long id, Model model) {
        Book book = getBookRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "admin/update-book";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "admin/update-book";
        }
        getBookRepo().save(book);
        model.addAttribute("books", getBookRepo().findAll());
        return "admin/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = getBookRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        getBookRepo().delete(book);
        model.addAttribute("books", getBookRepo().findAll());
        return "admin/admin";
    }

    @PostMapping("/add-to-basket")
    public String addToBasket(@RequestParam("id") long id, Model model) {
        addNewItemToBasket(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("topFiveOnSale", getBookRepo().findFirst5ByOrderByDiscountDesc());
        return "user/store";
    }

    @PostMapping("/plus-to-basket")
    public String plusToBasket(@RequestParam("id") long id, Model model) {
        addNewItemToBasket(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @PostMapping("/decrease-item-from-basket/{id}")
    public String decreaseItemFromBasket(@PathVariable("id") long id, Model model) {
        basketList.delete(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasket(@PathVariable("id") long id, Model model) {
        basketList.clearAllItemsOfSameKind(id);
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @PostMapping("/empty-basket")
    public String deleteAllBasket(Model model) {
        basketList.clear();
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @PostMapping("/basketshopping")
    public String basketShoppingList(Model model) {
        model.addAttribute("countBasketItems", basketList.count().toString());
        model.addAttribute("basket", basketList.getBasketList());
        return "user/basket-shopping-list";
    }

    @PostMapping("/purchaseitem")
    public String purchaseItem(@RequestParam("id") long id, Model model) {
        errorMessage = "";
        Book book = getBookRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        BasketBook basketBook = basketList.findById(id);
        Double totalAmount = basketBook.getPrice() - basketBook.getPrice() * basketBook.getDiscount() / 100;
        basketList.delete(id);
        book.setQuantity(book.getQuantity() - 1);

        if (book.getQuantity() < 0) {
            book.setQuantity(0);
            basketList.clearAllItemsOfSameKind(id);
            errorMessage = String.format("Sorry... %s is out of stoke. You will not be charged!", book.getName());
            totalAmount = 0.00;
        }
        getBookRepo().save(book);
        purchaseController.savePurchase(basketList.count().toString(),totalAmount.toString(), errorMessage,  model);
        return "user/purchase-item";
    }

    void addNewItemToBasket(long id){
        Book book = getBookRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        basketList.add(book);
    }
}

