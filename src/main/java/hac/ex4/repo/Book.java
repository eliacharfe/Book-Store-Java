package hac.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

   // @NotEmpty(value = IMAGE_URL)
    private String imageSrc;

    @Min(value = 0, message = "Quantity should be greater than or equal to 0")
    private int quantity;

    @Min(value = 1, message = "Price should be greater than 0 ")
    private double price;

    @Min(value = 1, message = "Discount percentage should be greater than or equal to 0 ")
    @Max(value = 100, message = "Discount percentage should be less than or equal to 100 ")
    private double discount;

    public Book() {}

    public Book(String name, String imageSrc, int quantity, double price, double discount) {
        this.name = name;
        this.imageSrc = imageSrc;
        this.quantity = quantity;
        this.price = price;
        this.discount = discount;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
    public String getImageSrc() {
        return imageSrc;
    }


    public void setQuantity(int quantity) {
        System.out.println("in set Q: " + quantity  + ", " + this.id);
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getDiscount() {
        return discount;
    }


    @Override
    public String toString() {
        return "Book{" + "id=" + id +
                     ", name=" + name +
                     ", quantity=" + quantity +
                     ", price=" + price +
                     ", discount=" + discount +
                     '}';
    }
}

