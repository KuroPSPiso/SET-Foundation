package com.capgemini.supermarket;

import com.capgemini.calendar.CalendarFormatting;
import com.capgemini.supermarket.payment.Currency;
import com.capgemini.supermarket.payment.IDiscountFormat;
import com.capgemini.supermarket.payment.ShoppingCart;
import com.capgemini.supermarket.stock.Product;
import com.sun.deploy.util.StringUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static com.capgemini.supermarket.stock.Product.Stock;

public class Main_Supermarket {

    private static ShoppingCart shoppingCart;

    public static void main(String[] args) {
        shoppingCart = new ShoppingCart();
        //Data collection from io stream
        Scanner sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            printStockInformation();
            printShoppingCart();

            //print question
            System.out.println("write the following: 'add item', 'remove item', 'buy', or 'exit'");
            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                String data = sc.nextLine();
                //expected result is either exit or a number so remove all spaces.
                //try to parse to a number, if it fails check if it's equal to exit else return.
                try {
                    parse(data);
                    break failedReturn;
                } catch (ParseFormatException pfe) {
                    //check for exit else return
                    if (data.equals("exit")) {
                        isRunning = false;
                        break failedReturn;
                    }
                    else {
                        System.out.println("write the following: 'add item', 'remove item', 'buy', or 'exit'");
                        continue failedReturn;
                    }
                }
            }
        }
    }

    private static void printShoppingCart() {
        System.out.println("Shopping cart:\nItem                |price");
        if(shoppingCart.getShoppingList().size() > 0) {
            Currency totalCost = Currency.Zero();
            Currency totalCostReduction = Currency.Zero();
            Product[] cart = new Product[0];
            List<Product> subCart = new ArrayList<>();
            for(List<Product> products : shoppingCart.getShoppingList().values())
            {
                subCart.addAll(products);
            }
            cart = subCart.toArray(cart);
            HashMap<String, Product> productHashMap = new HashMap<>();
            for (int i = 0; i < cart.length; i++) {
                //add a every unique item in the shopping cart to a hashmap as each product scans the shopping list. (without this every discount can be counted multiple times)
                if(!productHashMap.containsKey(cart[i].getName().toLowerCase()))
                    productHashMap.put(cart[i].getName().toLowerCase(), cart[i]);

                String name = cart[i].getName();
                name = String.format("%s%"+ (20 - name.length()) + "s", name, "");
                System.out.println(String.format("%s|%d,%02d", name, cart[i].getValue().getValue(), cart[i].getValue().getPrecision()));
                totalCost.addValue(cart[i].getValue());
            }
            for(Product p : productHashMap.values())
            {
                if(p.getDiscounts() != null) {
                    for (IDiscountFormat idf : p.getDiscounts()) {
                        totalCostReduction.addValue(idf.calculateDiscount(cart));
                    }
                }
            }


            System.out.println(String.format("Discount: %d,%02d", totalCostReduction.getValue(), totalCostReduction.getPrecision()));
            System.out.println(String.format("SubTotal: %d,%02d", totalCost.getValue(), totalCost.getPrecision()));
            totalCost.subtractValue(totalCostReduction);
            System.out.println(String.format("Total  : %d,%02d", totalCost.getValue(), totalCost.getPrecision()));

        }else{
            System.out.println("No items in shopping cart...");
        }
    }

    private static void printStockInformation() {
        System.out.println("We currently have the following items in Stock:\nItem no.\t|Item                |Price   |SALE");
        for(int i = 0; i < Stock.length; i++)
        {
            Product p = Stock[i];
            String name = p.getName();
            name = String.format("%s%"+ (20 - name.length()) + "s", name, "");
            System.out.println(String.format("%s\t\t\t|%s|%5d,%02d|", i, name, p.getValue().getValue(), p.getValue().getPrecision()));
        }
    }

    public static void parse(String data) throws ParseFormatException {
        if(data.startsWith("add "))
        {
            String[] split = data.split(" ");
            if(split.length >= 2)
            {
                try {
                    int val = Integer.parseInt(split[1]);
                    if(val < Stock.length && val >= 0)
                    {
                        shoppingCart.addToShoppingCart(val, Stock[val]);
                    }
                    else
                        throw new Exception();
                }catch (Exception ex)
                {
                    System.out.println("Please enter number of a item you want to add to the cart.");
                }
            }
        }
        else if(data.startsWith("remove "))
        {
            String[] split = data.split(" ");
            try {
                int val = Integer.parseInt(split[1]);
                if(val < Stock.length && val >= 0)
                    shoppingCart.removeFromShoppingCart(val);
                else
                    throw new Exception();
            }catch (Exception ex)
            {
                System.out.println("Please enter number of a item you want to add to the cart.");
            }
        }
        else if(data.equals("buy"))
        {

        }
        else
        {
            throw new ParseFormatException();
        }
    }
}
