package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository of the book class
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    /**
     * Return a list of 5 books that have the greater discount order by their discount rate.
     * @return - List of 5 books.
     */
    List<Book> findFirst5ByOrderByDiscountDesc();

    /**
     * Get a substring and return all books that their title contains this substring.
     * @param partName - The substring.
     * @return - A list of books.
     */
    List<Book> findBookByNameContains(String partName);
}
