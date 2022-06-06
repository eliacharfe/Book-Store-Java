package hac.ex4.services;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Book service (for transaction implementation)
 */
@Service
public class BookService {

    /** Reference to book repo */
    @Autowired
    private BookRepository repository;

    /**
     * Save book
     * @param book - a book to save.
     */
    @Transactional
    public void saveBook(Book book) {
        repository.save(book);
    }

    /**
     * Delete a book
     * @param book - a book to delete
     */
    @Transactional
    public void deleteBook(Book book) {
        repository.delete(book);
    }

    /**
     * Get a book by id
     * @param id - The id of the book
     * @return - The book with that id
     */
    @Transactional
    public Optional<Book> getBook(long id) {
        return repository.findById(id);
    }

    /**
     * Return all books that their title contains this substring passed.
     * @param partName - A substring.
     * @return - List of books.
     */
    @Transactional
    public List<Book> findByNameContains(String partName){
        return repository.findBookByNameContains(partName);
    }

    /**
     * Return a list of 5 books that have the greater discount ordered by their discount rate.
     * @return - List 5 of books.
     */
    @Transactional
    public List<Book> findTop5onSales(){
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    /**
     * Get all books in the database.
     * @return - List of books.
     */
    @Transactional
    public List<Book> getBooks() {
        return repository.findAll();
    }
}
