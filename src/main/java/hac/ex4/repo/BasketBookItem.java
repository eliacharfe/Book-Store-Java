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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String name;

   // @NotEmpty(value = IMAGE_URL)
    private String imageSrc;

    @Min(value = 1, message = "Price should be greater than 0 ")
    private long price;

    @Min(value = 1, message = "Discount percentage should be greater than or equal to 0 ")
    @Max(value = 100, message = "Discount percentage should be less than or equal to 100 ")
    private long discount;

    public BasketBookItem() {}

    public BasketBookItem(String name, String imageSrc, long price, long discount) {
        this.name = name;
        this.imageSrc = imageSrc;
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

    public void setPrice(long price) {
        this.price = price;
    }
    public long getPrice() {
        return price;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }
    public long getDiscount() {
        return discount;
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

