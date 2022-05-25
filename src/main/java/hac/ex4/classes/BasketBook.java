package hac.ex4.classes;

import java.io.Serializable;

public class BasketBook implements Serializable {

    public long id;
    public String name;
    public String imageSrc;
    public int quantityOfSameItemInBasket;
    public double price;
    public double discount;

    public BasketBook(long id, String name, String imageSrc, int quantityOfSameItemInBasket,
                      double price, double discount) {
        this.id = id;
        this.name = name;
        this.imageSrc = imageSrc;
        this.quantityOfSameItemInBasket = quantityOfSameItemInBasket;
        this.price = price;
        this.discount = discount;
    }

    public long getIdBasket() {
        return id;
    }

    public void setNameBasket(String name) {
        this.name = name;
    }
    public String getNameBasket() {
        return name;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
    public String getImageSrc() {
        return imageSrc;
    }

    public void setQuantityOfSameItemInBasket(int quantityOfSameItemInBasket){
        this.quantityOfSameItemInBasket = quantityOfSameItemInBasket;
    }
    public int getQuantityOfSameItemInBasket(){
        return quantityOfSameItemInBasket;
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

}

