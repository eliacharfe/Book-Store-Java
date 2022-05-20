package hac.ex4.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketBookItemRepository extends JpaRepository<BasketBookItem, Long> {
    long countByQuantityOfSameItem(int quantity);
//    List<BasketBookItem> findAllById();
//    List<BasketBookItem>  findByBasketBookItemName(String name);
    //List<BasketBookItem> findByBookName(String name);
    /* add here the queries you may need - in addition to CRUD operations
    List<User> findUserByUserName(String userName);
    List<User> findByEmail(String email);
    List<User> findByUserNameAndEmail(String userName, String email);
    List<User> findFirst10ByOrderByUserNameDesc(); */
}
