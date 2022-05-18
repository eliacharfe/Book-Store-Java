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
import java.util.List;

@Controller
public class BookController {

    @Value("${index.message}")
    private String message;

    @Autowired
    private BookRepository repository;

    private BookRepository getRepo() {
        return repository;
    }

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("books", getRepo().findAll());
        return "index"; //view
    }

//    @GetMapping("/")
//    public String main(Book book, Model model) {
//        model.addAttribute("course", someProperty);
//
//        // the name "books"  is bound to the VIEW
//        model.addAttribute("books", getRepo().findAll());
//        return "index";
//    }

//    @GetMapping("/signup")
//    public String showSignUpForm(Book user, Model model) {
//        //model.addAttribute("user", new User("noname","noemail"));
//        return "add-user";
//    }
//
//    @PostMapping("/adduser")
//    public String addBook(@Valid Book book, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "add-user";
//        }
//
//        getRepo().save(book);
//        model.addAttribute("books", getRepo().findAll());
//        return "index";
//    }


//    @PostMapping("/edit")
//    public String editBook(@RequestParam("id") long id, Model model) {
//
//        Book book = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
//
//        // the name "book"  is bound to the VIEW
//        model.addAttribute("book", book);
//        return "update-user";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateBook(@PathVariable("id") long id, @Valid Book book, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            book.setId(id);
//            return "update-user";
//        }
//
//        getRepo().save(book);
//        model.addAttribute("books", getRepo().findAll());
//        return "index";
//    }

//    @GetMapping("/delete/{id}")
//    public String deleteBook(@PathVariable("id") long id, Model model) {
//
//        Book book = getRepo()
//                .findById(id)
//                .orElseThrow(
//                    () -> new IllegalArgumentException("Invalid book Id:" + id)
//                );
//        getRepo().delete(book);
//        model.addAttribute("books", getRepo().findAll());
//        return "index";
//    }

//    @GetMapping(value="/json")
//    public String json (Model model) {
//        return "json";
//    }
//    /**
//     * a sample controller return the content of the DB in JSON format
//     * @return
//     */
//    @GetMapping(value="/getjson")
//    public @ResponseBody List<Book> getAll() {
//
//        return getRepo().findAll();
//    }
}

