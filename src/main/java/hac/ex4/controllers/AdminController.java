package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class AdminController {

    private final String DEFAULT_IMAGE_PATH = "/default_book_cover_2015.jpg";
    private final String INVALID_ID = "Invalid book Id:";

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomErrorController customErrorController;

    private String mainAdminWindow(Model model){
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminEditWindow(Model model) {
        try {
           return mainAdminWindow(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/addnewbookform")
    public String addNewBook(Model model) {
        try {
            model.addAttribute("book", new Book("No name", DEFAULT_IMAGE_PATH, 10, 79.99, 7.0));
            return "admin/add-book";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/addbook")
    public String addBookGET(Model model) {
        return mainAdminWindow(model);
    }

    @PostMapping("/addbook")
    public String addBookPOST(@Valid Book book, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                return "admin/add-book";
            }
            bookService.saveBook(book);
            return addBookGET(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/edit/{id}")
    public String editBookGET(@PathVariable("id") long id, Model model) {
        try {
            Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
            model.addAttribute("book", book);
            return "admin/update-book";
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public String editBookPOST(@PathVariable("id") long id, Model model) {
        return editBookGET(id, model);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateUserGET(@PathVariable("id") long id, Model model) {
            return mainAdminWindow(model);
    }

    @PostMapping("/update/{id}")
    public String updateUserPOST(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
        try {
            if (result.hasErrors()) {
                book.setId(id);
                return "admin/update-book";
            }
            bookService.saveBook(book);
            return updateUserGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBookGET(@PathVariable("id") long id, Model model) {
            return mainAdminWindow(model);
    }

    @PostMapping("/delete/{id}")
    public String deleteBookPOST(@PathVariable("id") long id, Model model) {
        try {
            Book book = bookService.getBook(id).orElseThrow(() -> new IllegalArgumentException(INVALID_ID + id));
            bookService.deleteBook(book);
            return deleteBookGET(id, model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }
}

