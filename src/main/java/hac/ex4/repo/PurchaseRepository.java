package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repository of the purchase class
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * Return the list of all purchases ordered by date.
     * @return - A list of all the purchases.
     */
    List<Purchase> findAllByOrderByDateTime();

    /**
     * Return the sum of all the amounts of the purchases.
     * @return - A double representing the sum.
     */
    @Query(value = "SELECT sum(amount) FROM Purchase ")
     public double sumTotal();
}
