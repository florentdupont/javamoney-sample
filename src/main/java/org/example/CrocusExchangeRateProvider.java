package org.example;

import javax.money.CurrencyUnit;
import javax.money.convert.*;

public class CrocusExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public ProviderContext getContext() {
        return null;
    }

    @Override
    public ExchangeRate getExchangeRate(ConversionQuery conversionQuery) {
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(ConversionQuery conversionQuery) {
        return null;
    }

    @Override
    public boolean isAvailable(ConversionQuery conversionQuery) {
        return false;
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term) {
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(CurrencyUnit term) {
        return null;
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term) {
        return false;
    }

    @Override
    public boolean isAvailable(String baseCode, String termCode) {
        return false;
    }

    @Override
    public ExchangeRate getExchangeRate(String baseCode, String termCode) {
        return null;
    }

    @Override
    public ExchangeRate getReversed(ExchangeRate rate) {
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(String termCode) {
        return null;
    }
}
