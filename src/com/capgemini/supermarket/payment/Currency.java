package com.capgemini.supermarket.payment;

public class Currency {
    long value;

    long getValue()
    {
        return value - getPrecision();
    }

    long getPrecision()
    {
        return value & 99;
    };
}
