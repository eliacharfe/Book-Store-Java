package hac.ex4.controllers;

import hac.ex4.repo.Book;
import hac.ex4.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller that handle all admin pages
 */
@Controller
public class AdminController {

    /** Default image path. */
    private final String DEFAULT_IMAGE_PATH = "/default_book_cover_2015.jpg";
    /** Error message - invalid book. */
    private final String INVALID_ID = "Invalid book Id:";

    /** Service book (for transaction) */
    @Autowired
    private BookService bookService;

    /** Reference to error controller */
    @Autowired
    private CustomErrorController customErrorController;

    /**
     * Return main admin page.
     * @param model - The model for view.
     * @return - Main admin page.
     */
    private String mainAdminPage(Model model){
        model.addAttribute("books", bookService.getBooks());
        return "admin/admin";
    }

    /**
     * Get a GET request and return main admin page.
     * @param model - The model for view.
     * @return - Main admin page.
     */
    @GetMapping("/admin")
    public String adminEditPage(Model model) {
        try {
           return mainAdminPage(model);
        } catch (Exception e) {
            return customErrorController.handleError(e.getMessage());
        }
    }

    /**
     * Return form add book page with default book in the input fields.
     * @param model - The model for view.
     * @return - Add book form page.
     */
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

    /**
     * Implementation of GET request (see the POST that correspond).
     * @param model - The model for view.
     * @return - Main admin page.
     */
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/addbook")
    public String addBookGET(Model model) {
        return mainAdminPage(model);
    }

    /**
     * Get a POST request with a book from a form and validate the book, if has constraints error, return
     * the same page form with the error messages, if is valid book - save the book in the database.
     * @param book - The book to add.
     * @param result - The result of the constraints.
     * @param model - The model for view.
     * @return - Main admin page (no error) or Add book form page (error).
     */
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

    /**
     * Implementation of GET request (see the POST that correspond).
     * @param id - The id of the book to edit.
     * @param model - The model for view.
     * @return - Update form page.
     */
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

    /**
     * Get a POST request with id and return the update form page.
     * @param id - The id of the book to edit.
     * @param model - The model for view.
     * @return - Update form page.
     */
    @PostMapping("/edit/{id}")
    public String editBookPOST(@PathVariable("id") long id, Model model) {
        return editBookGET(id, model);
    }

    /**
     * Implementation of GET request (see the POST that correspond).
     * @param id - id of the book.
     * @param model - The model for view.
     * @return - Main admin page.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/update/{id}")
    public String updateUserGET(@PathVariable("id") long id, Model model) {
            return mainAdminPage(model);
    }

    /**
     * Get a POST request from the form of update book, with the id and the book to validate, then if
     * the input is valid then update the book which has this id, else if has error return the form with
     * the error messages.
     * @param id - The id of the book.
     * @param book - The new updated book.
     * @param result - The result of the constraints.
     * @param model - The model for view.
     * @return - Main admin page (no error) or Update form page (error).
     */
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

    /**
     * Implementation of GET request (see the POST that correspond).
     * @param id - The id of the book to delete.
     * @param model - The model for view.
     * @return - Main admin page.
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteBookGET(@PathVariable("id") long id, Model model) {
            return mainAdminPage(model);
    }

    /**
     * Get a POST request to delete a book.
     * @param id - The id of the book to delete.
     * @param model - The model for view.
     * @return - Main admin page.
     */
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

