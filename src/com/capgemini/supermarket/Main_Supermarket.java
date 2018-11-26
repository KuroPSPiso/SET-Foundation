package com.capgemini.supermarket;

import com.capgemini.supermarket.payment.Currency;
import com.capgemini.supermarket.payment.ShoppingCart;
import com.capgemini.supermarket.stock.Product;

import java.util.Scanner;

import static com.capgemini.supermarket.stock.Product.Stock;

public class Main_Supermarket {

    private static ShoppingCart shoppingCart;
    private static Scanner sc;

    public static void main(String[] args) {
        shoppingCart = new ShoppingCart();
        //Data collection from io stream
        sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            printStockInformation();
            printShoppingCart();

            //print question
            System.out.println("write the following: 'add', 'remove', 'buy', or 'exit'");
            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                String data = sc.nextLine();
                //expected result is either exit or a number so remove all spaces.
                //try to parse to a number, if it fails check if it's equal to exit else return.
                boolean requiresInputToContinue = false;
                try {
                    requiresInputToContinue = parse(data);
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
                finally {
                    if(requiresInputToContinue) {
                        System.out.println("Press Enter to Continue");
                        sc.nextLine();
                    }
                }
            }
        }
    }

    private static void printShoppingCart() {
        System.out.println("Shopping cart:\nItem                |price");
        if(shoppingCart.getShoppingList().size() > 0) {
            Currency subTotalCost = shoppingCart.getSubTotalCost();
            Currency totalCostReduction = shoppingCart.getTotalCostReduction();
            Currency totalCost = shoppingCart.getTotalCost();
            Product[] cart = shoppingCart.getProductsInShoppingCart();
            for (int i = 0; i < cart.length; i++) {
                String name = cart[i].getName();
                name = String.format("%s%"+ (20 - name.length()) + "s", name, "");
                System.out.println(String.format("%s|%d,%02d", name, cart[i].getValue().getValue(), cart[i].getValue().getPrecision()));
            }
            System.out.println(String.format("Discount: %d,%02d", totalCostReduction.getValue(), totalCostReduction.getPrecision()));
            System.out.println(String.format("SubTotal: %d,%02d", subTotalCost.getValue(), subTotalCost.getPrecision()));
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

    public static boolean parse(String data) throws ParseFormatException {
        if(data.equals("add") || data.startsWith("add "))
        {
            String toConvert = null;
            if(data.startsWith("add ")) {
                String[] split = data.split(" ");
                toConvert = split[1];
            }
            if(toConvert == null)
            {
                System.out.println("Enter the item number:");
                toConvert = sc.nextLine();
            }
            if(toConvert != null)
            {
                try {
                    int val = Integer.parseInt(toConvert);
                    if(val < Stock.length && val >= 0)
                    {
                        shoppingCart.addToShoppingCart(val, Stock[val]);
                    }
                    else
                        throw new Exception();
                }catch (Exception ex)
                {
                    System.out.println("This item does not exist, please enter number of a item you want to add to the cart.");
                }
            }
        }
        else if(data.startsWith("remove "))
        {
            String toConvert = null;
            if(data.startsWith("remove ")) {
                String[] split = data.split(" ");
                toConvert = split[1];
            }
            if(toConvert == null)
            {
                System.out.println("Enter the item number:");
                toConvert = sc.nextLine();
            }
            if(toConvert != null) {
                try {
                    int val = Integer.parseInt(toConvert);
                    if (val < Stock.length && val >= 0)
                        shoppingCart.removeFromShoppingCart(val);
                    else
                        throw new Exception();
                } catch (Exception ex) {
                    System.out.println("Please enter number of a item you want to add to the cart.");
                }
            }
        }
        else if(data.equals("buy") || data.startsWith("buy "))
        {
            System.out.println("Enter amount to pay with (in cents):");
            try {
                String toConvert = null;
                if(data.startsWith("buy "))
                {
                    String[] dataSplit = data.split(" ");
                    toConvert = dataSplit[1];
                }
                if (toConvert == null){
                    toConvert = sc.nextLine();
                }
                int val = Integer.parseInt(toConvert);
                Currency paid = new Currency(val);
                Currency totalCost = shoppingCart.getTotalCost();
                paid.subtractValue(totalCost);
                if(paid.getValue() < 0 || paid.getPrecision() < 0) {
                    //payed too little or value is negative.
                    System.out.println("decline payment");
                    return true;
                }
                else {
                    System.out.println(
                            String.format(
                                    "%s, and the refund is %d,%02d",
                                    shoppingCart.isEmptyOrNull()?"Customer with a empty shoppingcart has paid for nothing":"Customer has paid",
                                    paid.getValue(),
                                    paid.getPrecision()
                            )
                    );
                    shoppingCart.clear();
                    System.out.println("Next customer please...");
                    return true;
                }

            } catch (NumberFormatException nfe) {
                System.out.println("Incorrect Input. Please enter a number of price");
            }
        }
        else
        {
            throw new ParseFormatException();
        }
        return false;
    }
}