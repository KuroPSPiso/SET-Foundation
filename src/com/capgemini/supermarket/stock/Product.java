package com.capgemini.supermarket.stock;

import com.capgemini.supermarket.payment.Currency;
import com.capgemini.supermarket.payment.IDiscountFormat;

public class Product {
    private String name;
    private Currency value;
    private IDiscountFormat[] discounts;

    public Product(String name, Currency value) {
        this.name = name;
        this.value = value;
    }

    public Product(String name, Currency value, IDiscountFormat... discounts) {
        this.name = name;
        this.value = value;
        this.discounts = discounts;
    }

    public String getName() {
        return name;
    }

    public Currency getValue() {
        return value;
    }

    public IDiscountFormat[] getDiscounts() {
        return discounts;
    }
}
