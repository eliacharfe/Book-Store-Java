package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findAllByOrderByDateTime();
    @Query(value = "SELECT sum(amount) FROM Purchase ")
     public double sumTotal();
//    Double countAllByAmount();
//    List<Book> findFirst5ByOrderByDiscountDesc();
    //List<Book> findByBookName(String name);
    /* add here the queries you may need - in addition to CRUD operations
    List<User> findUserByUserName(String userName);
    List<User> findByEmail(String email);
    List<User> findByUserNameAndEmail(String userName, String email);
    List<User> findFirst10ByOrderByUserNameDesc(); */
}
