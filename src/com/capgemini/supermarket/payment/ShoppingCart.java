package com.capgemini.supermarket.payment;

import com.capgemini.supermarket.stock.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.capgemini.supermarket.stock.Product.Stock;

public class ShoppingCart {

    private HashMap<Integer, List<Product>> shoppingList;

    public ShoppingCart()
    {
        shoppingList = new HashMap<>();
    }

    public HashMap<Integer, List<Product>> getShoppingList() {
        return shoppingList;
    }

    public void addToShoppingCart(int index, Product product)
    {
        List<Product> products = new ArrayList<>();
        if(shoppingList.get(index) != null)
        {
            products = shoppingList.get(index);
        }
        products.add(product);
        shoppingList.put(index, products);
    }
    
    public void removeFromShoppingCart(int index)
    {
        if(shoppingList.containsKey(index))
        {
            List<Product> products = new ArrayList<>();
            if(shoppingList.get(index) != null)
            {
                products = shoppingList.get(index);
            }
            if(products.size() > 0) {
                products.remove(0); //same item, easily removable.
                if(products.size() > 0) {
                    shoppingList.put(index, products);
                }
                else
                {
                    shoppingList.remove(index);
                }
            }

        }
    }
}
