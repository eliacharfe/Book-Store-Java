package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findFirst5ByOrderByDiscountDesc();
    List<Book> findBookByNameContains(String partName);
}
