package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
public class AdminController {

    @Value("${store.errorMessage}")
    private String errorMessage = "";

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomErrorController customErrorController;

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
    public String addBookGET(Model model) {
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
    public String updateUserGET(@PathVariable("id") long id, Model model) {
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
    public String deleteBookGET(@PathVariable("id") long id, Model model) {
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
}

