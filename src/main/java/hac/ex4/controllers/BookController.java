package hac.ex4.controllers;

import hac.ex4.repo.BasketBookItem;
import hac.ex4.repo.BasketBookItemRepository;
import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class BookController {

    @Value("${welcome.message}")
    private String message = "";

    private Integer countBasketItems = 0;

    @Autowired
    private BookRepository repository;

    private BookRepository getRepo() {
        return repository;
    }

    @Autowired
    private BasketBookItemRepository basketBookItemRepository;

    private BasketBookItemRepository getRepoBasketBookItem() {
        return basketBookItemRepository;
    }

    @GetMapping("/navbar")
    public String getNavbar(Model model) {
        model.addAttribute("countBasketItems", countBasketItems.toString());
        return "includes/navbar";
    }

    @GetMapping("/")
    public String main(Model model) {
        countBasketItems = 0;
        List<BasketBookItem> basketBookItemList = getRepoBasketBookItem().findAll();
        for (BasketBookItem basketItem : basketBookItemList) {
            countBasketItems += basketItem.getQuantityOfSameItem();
        }
        model.addAttribute("message", message);
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "welcome";
    }

    @GetMapping("/login")
    public String showSignUpForm(Model model) {
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @GetMapping("/addnewbookform")
    public String addNewBook(Model model) {
        model.addAttribute("book", new Book("No name",
                "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg",
                10, 79.99, 7.0));
        return "add-book";
    }

    @PostMapping("/addbook")
    public String addBook(@Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }
        getRepo().save(book);
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "update-book";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }
        getRepo().save(book);
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        getRepo().delete(book);
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @PostMapping("/add-to-basket")
    public String addToBasket(@RequestParam("id") long id, Model model) {
        countBasketItems++;
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

        BasketBookItem basketBookItem;
        try {
            basketBookItem = getRepoBasketBookItem().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
            if (basketBookItem.getQuantityOfSameItem() < book.getQuantity()) {
                basketBookItem.setQuantityOfSameItem(basketBookItem.getQuantityOfSameItem() + 1);
                message = "";
            } else {
                message = "This book is not available anymore";
            }
        } catch (IllegalArgumentException e) {
            basketBookItem = new BasketBookItem(id, book.getName(),
                    book.getImageSrc(), book.getPrice(), book.getDiscount(), 1);
        }

        getRepoBasketBookItem().save(basketBookItem);

        model.addAttribute("message", message);
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "welcome";
    }

    @PostMapping("/decrease-item-from-basket/{id}")
    public String decreaseItemFromBasket(@PathVariable("id") long id, Model model) {
        countBasketItems--;
        BasketBookItem basketBookItem = getRepoBasketBookItem().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        basketBookItem.setQuantityOfSameItem(basketBookItem.getQuantityOfSameItem() - 1);
        if (basketBookItem.getQuantityOfSameItem() <= 0)
            getRepoBasketBookItem().delete(basketBookItem);
        getRepoBasketBookItem().save(basketBookItem);
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("basketBooksItems", getRepoBasketBookItem().findAll());
        return "basket-shopping-list";
    }

    @PostMapping("/delete-from-basket/{id}")
    public String deleteFromBasket(@PathVariable("id") long id, Model model) {
        BasketBookItem basketBookItem = getRepoBasketBookItem().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        getRepoBasketBookItem().delete(basketBookItem);
        countBasketItems -= basketBookItem.getQuantityOfSameItem();
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("basketBooksItems", getRepoBasketBookItem().findAll());
        return "basket-shopping-list";
    }

    @PostMapping("/delete-all-basket")
    public String deleteAllBasket(Model model) {
        countBasketItems = 0;
        getRepoBasketBookItem().deleteAll();
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("basketBooksItems", getRepoBasketBookItem().findAll());
        return "basket-shopping-list";
    }

    @PostMapping("/basketshopping")
    public String basketShoppingList(Model model) {
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("basketBooksItems", getRepoBasketBookItem().findAll());
        return "basket-shopping-list";
    }

    @PostMapping("/purchaseitem")
    public String purchaseItem(@RequestParam("id") long id, Model model) {
        if (countBasketItems >= 1) {
            countBasketItems--;
        }

        BasketBookItem basketBookItem = getRepoBasketBookItem().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));

        basketBookItem.setQuantityOfSameItem(basketBookItem.getQuantityOfSameItem() - 1);
        if (basketBookItem.getQuantityOfSameItem() == 0)
            getRepoBasketBookItem().delete(basketBookItem);
        else getRepoBasketBookItem().save(basketBookItem);

        book.setQuantity(book.getQuantity() - 1);
       if (book.getQuantity() == 0)
           book.setQuantity(0);
//           getRepo().delete(book);
//        else
        getRepo().save(book);

        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("totalAmountPay", basketBookItem.getPrice() - basketBookItem.getPrice()
                * basketBookItem.getDiscount() / 100);

        return "purchase-item";
    }
}

