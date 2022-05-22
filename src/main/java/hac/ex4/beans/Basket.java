package hac.ex4.beans;

import org.springframework.stereotype.Component;

@Component(value="autowiredBasketDependency")
public class Basket {
    private String basket = "Arbitrary Basket";
    public Basket() {
    }
    public String toString() {
        return basket;
    }
    public void setBasket(String b) {
        basket = b;
    }
}