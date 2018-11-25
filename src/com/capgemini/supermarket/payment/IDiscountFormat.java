package com.capgemini.supermarket.payment;

import com.capgemini.supermarket.stock.Product;

public interface IDiscountFormat {

    public Currency calculateDiscount(Product... products);

}
