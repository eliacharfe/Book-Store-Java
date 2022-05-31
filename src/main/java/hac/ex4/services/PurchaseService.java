package hac.ex4.services;

import hac.ex4.repo.Purchase;
import hac.ex4.repo.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repository;

    @Transactional
    public void savePurchase(Purchase purchase) {
        repository.save(purchase);
    }

    @Transactional
    public Optional<Purchase> getPurchase(long id) {
        return repository.findById(id);
    }

    @Transactional
    public List<Purchase> findByDates () {
       return repository.findAllByOrderByDateTime();
    }

    @Transactional
    public double getTotalAmount(){
        return repository.sumTotal();
    }

    @Transactional
    public List<Purchase> getPurchases() {
        return repository.findAll();
    }
}
