package com.capgemini.supermarket.payment;

/**
 * For this example currency is only EUROS are used when referring to currency.
 */
public class Currency {

    public static final Currency Zero(){ return new Currency(0); }
    private long value;

    //Get the full monetary value (not including cents)
    public long getValue()
    {
        return (value - getPrecision())/ 100;
    }

    //Get the cents of the monetary value
    public long getPrecision()
    {
        return value % 100;
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
        Currency currency = new Currency(this.value + additionalMonitaryValue.value);
        this.value = currency.value;
        return this;
    }

    public Currency subtractValue(Currency clearedMonitaryValue)
    {
        Currency currency = new Currency(this.value - clearedMonitaryValue.value);
        this.value = currency.value;
        return this;
    }

    public Currency getPrecentage(int percentage)
    {
        return new Currency((int)((double)value /percentage * 100));
    }
}
