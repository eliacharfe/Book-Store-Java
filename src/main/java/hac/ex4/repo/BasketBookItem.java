package hac.ex4.repo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class BasketBookItem {

    @Id
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

   // @NotEmpty(value = IMAGE_URL)
    private String imageSrc;

    private int quantityOfSameItem = 0;

    @Min(value = 1, message = "Price should be greater than 0 ")
    private double price;

    @Min(value = 1, message = "Discount percentage should be greater than or equal to 0 ")
    @Max(value = 100, message = "Discount percentage should be less than or equal to 100 ")
    private double discount;

    public BasketBookItem() {}

    public BasketBookItem(long id, String name, String imageSrc, double price, double discount,
                          int quantityOfSameItem) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
        this.price = price;
        this.discount = discount;
        this.quantityOfSameItem = quantityOfSameItem;
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

    public void setQuantityOfSameItem(int quantityOfSameItem){
        this.quantityOfSameItem = quantityOfSameItem;
    }
    public int getQuantityOfSameItem() {
        return quantityOfSameItem;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id +
                     ", name=" + name +
                     ", price=" + price +
                     ", discount=" + discount +
                     '}';
    }
}

