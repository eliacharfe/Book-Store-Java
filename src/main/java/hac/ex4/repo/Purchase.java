package hac.ex4.repo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Purchase entity
 */
@Entity
public class Purchase implements Serializable {

    /** The id of the purchase */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /** The amount of the purchase */
    @Min(value = 1, message = "Purchase amount should be greater than 0 ")
    private double amount;

    /** Date time of the creation of the purchase */
    @CreationTimestamp
    private LocalDateTime dateTime;

    /** Empty constructor. */
    public Purchase() {}

    /**
     * Cunstruct a purchase.
     * @param amount - The amount of the purchase.
     */
    public Purchase(double amount) {
        this.amount = amount;
    }

    /**
     * Set new amount.
     * @param amount - new amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Get amount.
     * @return - the amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Set the date.
     * @param dateTime - new date.
     */
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Get the date.
     * @return - The date.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }


}

