package com.capgemini.supermarket;

import com.capgemini.calendar.CalendarFormatting;
import com.capgemini.supermarket.payment.Currency;
import com.capgemini.supermarket.stock.Product;
import com.sun.deploy.util.StringUtils;

import java.util.List;
import java.util.Scanner;

public class Main_Supermarket {

    public static void main(String[] args) {
        //Data collection from io stream
        Scanner sc = new Scanner(System.in);
        //Boolean to create a looping cycle
        boolean isRunning = true;
        while(isRunning)
        {
            //print question
            Product[] stock ={
                    new Product("Robijn", new Currency(300)),
                    new Product("Robijn", new Currency(250)),
                    new Product("Robijn", new Currency(500)),
                    new Product("Robijn", new Currency(200)),
                    new Product("Robijn", new Currency(1000))
            };

            System.out.println("write the following: 'add item', 'remove item', 'buy', or 'exit'");
            //get answer loop internally when failed
            failedReturn:
            for(;;) {
                String data = sc.next();
                //expected result is either exit or a number so remove all spaces.
                data = StringUtils.trimWhitespace(data);

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
                        System.out.println("Please enter a number or type exit: ");
                        continue failedReturn;
                    }
                }
            }
        }
    }

    public static void parse(String data) throws ParseFormatException {
        if(data.startsWith("add "))
        {

        }
        else if(data.startsWith("remove "))
        {

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
