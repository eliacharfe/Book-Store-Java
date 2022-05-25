package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Min(value = 1, message = "Purchase amount should be greater than 0 ")
    private double amount;

    @CreationTimestamp
    private LocalDateTime dateTime;

    public Purchase() {}

    public Purchase(double amount) {
        this.amount = amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getAmount() {
        return amount;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }



//    @Override
//    public String toString() {
//        return "Book{" + "id=" + id +
//                     ", name=" + name +
//                     ", quantity=" + quantity +
//                     ", price=" + price +
//                     ", discount=" + discount +
//                     '}';
//    }
}

