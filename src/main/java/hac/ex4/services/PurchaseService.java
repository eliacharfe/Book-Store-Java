package hac.ex4.services;

import hac.ex4.repo.Purchase;
import hac.ex4.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Purchase service (for transaction implementation)
 */
@Service
public class PurchaseService {

    /** Reference to purchase repo */
    @Autowired
    private PurchaseRepository repository;

    /**
     * Save a purchase.
     * @param purchase - A purchase to save.
     */
    @Transactional
    public void savePurchase(Purchase purchase) {
        repository.save(purchase);
    }

    /**
     * Find all purchases ordered by dates.
     * @return - List of all puchases ordered by dates.
     */
    @Transactional
    public List<Purchase> findByDates () {
       return repository.findAllByOrderByDateTime();
    }

    /**
     * Return total of the amount of all purchases.
     * @return - the sum.
     */
    @Transactional
    public double getTotalAmount(){
        return repository.sumTotal();
    }

    /**
     * Get all purchases list.
     * @return - List of all purchases.
     */
    @Transactional
    public List<Purchase> getPurchases() {
        return repository.findAll();
    }
}
