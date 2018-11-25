package com.capgemini.supermarket.payment;

/**
 * For this example currency is only EUROS are used when referring to currency.
 */
public class Currency {
    private long value;

    //Get the full monetary value (not including cents)
    public long getValue()
    {
        return value - getPrecision();
    }

    //Get the cents of the monetary value
    public long getPrecision()
    {
        return value & 99;
    };

    /**
     * Price of a product or service in euros.
     * @param value price in cents
     */
    public Currency(long value)
    {
        this.value = value;
    }

    /**
     * Add value to
     * @param additionalMonitaryValue
     * @return
     */
    public Currency addValue(Currency additionalMonitaryValue)
    {
        this.value += additionalMonitaryValue.value;
        return this;
    }

    public Currency getPrecentage(int percentage)
    {
        return new Currency(value / percentage);
    }
}
