package com.capgemini.supermarket.payment;

import com.capgemini.supermarket.stock.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public Product[] getProductsInShoppingCart()
    {
        Product[] cart = new Product[0];
        List<Product> subCart = new ArrayList<>();
        for(List<Product> products : shoppingList.values())
        {
            subCart.addAll(products);
        }
        return cart = subCart.toArray(cart);
    }

    public Currency getSubTotalCost()
    {
        Currency totalCost = Currency.Zero();
        Product[] cart = getProductsInShoppingCart();
        for(int i = 0; i < cart.length; i++)
            totalCost.addValue(cart[i].getValue());
        return totalCost;
    }

    //A generated hashmap of every unique item in the shopping cart, as each product scans the shopping list. (without this every discount can be counted multiple times)
    public HashMap<String, Product> getProductTypesInShoppingCart()
    {
        Product[] cart = getProductsInShoppingCart();
        HashMap<String, Product> productHashMap = new HashMap<>();
        for (int i = 0; i < cart.length; i++) {
            if (!productHashMap.containsKey(cart[i].getName().toLowerCase()))
                productHashMap.put(cart[i].getName().toLowerCase(), cart[i]);
        }
        return productHashMap;
    }

    public Currency getTotalCostReduction() {
        Currency totalCostReduction = Currency.Zero();
        HashMap<String, Product> productHashMap = getProductTypesInShoppingCart();
        for(Product p : productHashMap.values())
        {
            if(p.getDiscounts() != null) {
                for (IDiscountFormat idf : p.getDiscounts()) {
                    totalCostReduction.addValue(idf.calculateDiscount(getProductsInShoppingCart()));
                }
            }
        }
        return totalCostReduction;
    }

    public Currency getTotalCost()
    {
        Currency totalCost = getSubTotalCost().subtractValue(getTotalCostReduction());
        return totalCost;
    }

    public void clear() {
        this.shoppingList.clear();
    }

    public boolean isEmptyOrNull()
    {
        if(this.shoppingList == null)
            return true;
        if(this.shoppingList.size() == 0)
            return true;
        return false;
    }
}
