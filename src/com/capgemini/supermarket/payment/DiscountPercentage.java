package com.capgemini.supermarket.payment;

import com.capgemini.supermarket.stock.Product;

import java.util.ArrayList;
import java.util.List;

public abstract class DiscountPercentage implements IDiscountFormat{

    int percentage;

    @Override
    public Currency calculateDiscount(Product... products) {
        List<Product> productList = new ArrayList<>();

        for(Product product : products) {
            productList.add(product);
        }
        return new Currency(0);
    }
}
