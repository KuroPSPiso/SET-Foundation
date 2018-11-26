package com.capgemini.supermarket.stock;

import com.capgemini.supermarket.payment.Currency;
import com.capgemini.supermarket.payment.IDiscountFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Product {

    public static final Product[] Stock ={
            new Product("Robijn", new Currency(300), products -> {
                //conditional
                long discountValue = 0;
                int flaconDetection = 0;
                int foundRobijn = 0;
                Currency robijnCurrency = Currency.Zero();
                Currency returnCurrency = Currency.Zero();
                for(Product product : products)
                {
                    if(product.getName().toLowerCase().equals("flacon"))
                    {
                        flaconDetection += 1;
                    }
                    if(product.getName().toLowerCase().equals("robijn"))
                    {
                        robijnCurrency = product.getValue();
                        foundRobijn += 1;
                    }
                }

                if(foundRobijn > 0)
                {
                    for(;foundRobijn > 0; foundRobijn--)
                    {
                        if(flaconDetection >= 2)
                        {
                            returnCurrency.addValue(robijnCurrency.getPrecentage(31));
                            flaconDetection -= 2;
                        }
                    }
                }

                return returnCurrency;
            }),
            new Product("Brinta", new Currency(250)),
            new Product("Chinese Groenten", new Currency(500)),
            new Product("Kwark", new Currency(200), products -> {
                long valueOfKwark = 100;
                Currency returnDiscount = Currency.Zero();

                //if it's wednesday, add the value over the current value to the return discount variable.
                if(LocalDate.now().getDayOfWeek() == DayOfWeek.WEDNESDAY)
                    for(Product p : products)
                        if(p.getName().toLowerCase().equals("kwark"))
                            returnDiscount.addValue(new Currency(p.getValue().getValue() * 100 - p.getValue().getPrecision() - 100));

                return returnDiscount;
            }),
            new Product("Luiers", new Currency(1000), products -> {
                Currency returnDiscount = Currency.Zero();
                int diaperCount = 0;
                Product diaper = null;
                for(Product p : products)
                    if(p.getName().toLowerCase().equals("luiers")) {
                        diaperCount++;
                        diaper = p;
                    }
                if(diaperCount >=4)
                    diaperCount /= 4; //for every 4 packs of diapers, 1 is free.
                else
                    diaperCount = 0; //if unable to divide set the count to 0 (reusing the variable)
                for(; diaperCount > 0; diaperCount--)
                    returnDiscount.addValue(diaper.getValue());

                return returnDiscount;
            }),
            new Product("Flacon", new Currency(130))
    };

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
