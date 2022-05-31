package hac.ex4.services;

import hac.ex4.repo.Book;
import hac.ex4.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Transactional
    public void saveBook(Book book) {
        repository.save(book);
    }

    @Transactional
    public void deleteBook(Book book) {
        repository.delete(book);
    }

    @Transactional
    public Optional<Book> getBook(long id) {
        return repository.findById(id);
    }

    @Transactional
    public List<Book> findByNameContains(String partName){
        return repository.findBookByNameContains(partName);
    }

    @Transactional
    public List<Book> findTop5OnSales(){
        return repository.findFirst5ByOrderByDiscountDesc();
    }

    @Transactional
    public List<Book> getBooks() {
        return repository.findAll();
    }
}