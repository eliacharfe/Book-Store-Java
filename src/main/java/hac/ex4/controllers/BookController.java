package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class BookController {

    @Value("${index.message}")
    private String message;

    private Integer countBasketItems = 0;

    @Autowired
    private BookRepository repository;

    private BookRepository getRepo() {
        return repository;
    }

    @GetMapping("/navbar")
    public String getNavbar(Model model) {
        model.addAttribute("countBasketItems", countBasketItems.toString());
      return "navbar";
  }

    @GetMapping("/")
    public String main(Model model) {
//        model.addAttribute("message", message);
        model.addAttribute("countBasketItems", countBasketItems.toString());
       // model.addAttribute("books", getRepo().findAll());
        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "welcome";
    }

    @GetMapping("/login")
    public String showSignUpForm(Model model) {
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }

    @GetMapping("/addnewbookform")
    public String addNewBook(@Valid Book book, BindingResult result, Model model) {
        model.addAttribute("book", new Book("noname",
                "https://islandpress.org/sites/default/files/default_book_cover_2015.jpg",
                1,  35l, 6l));
        //model.addAttribute("books", getRepo().findAll());
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
        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
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

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
       Book book = getRepo()
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid book Id:" + id)
                );
        getRepo().delete(book);
        model.addAttribute("books", getRepo().findAll());
        return "admin";
    }


    @PostMapping("/add-to-basket")
    public String increaseBasket(@RequestParam("id") long id, Model model) {
        countBasketItems++;
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "welcome";
    }

    @PostMapping("/basketshopping")
    public String basketShoppingList(Model model) {
        model.addAttribute("countBasketItems", countBasketItems.toString());
        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "basket-shopping-list";
    }

    @PostMapping("/purchaseitem")
    public String purchaseItem(@RequestParam("id") long id, Model model) {
        model.addAttribute("countBasketItems", countBasketItems.toString());
//        model.addAttribute("topFiveOnSale", getRepo().findFirst5ByOrderByDiscountDesc());
        return "purchase-item";
    }
}

