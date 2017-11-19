package org.example;

import javax.money.CurrencyQuery;
import javax.money.CurrencyUnit;
import javax.money.spi.CurrencyProviderSpi;
import java.util.Collections;
import java.util.Set;

public class CrocusCurrencyProvider implements CurrencyProviderSpi {

    @Override
    public String getProviderName() {
        return "CrocusCurrencyProvider";
    }

    @Override
    public boolean isCurrencyAvailable(CurrencyQuery query) {
        return true;
    }

    @Override
    public Set<CurrencyUnit> getCurrencies(CurrencyQuery query) {

        return Collections.emptySet();
    }
}
