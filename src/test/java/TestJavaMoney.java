import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import javax.money.*;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavaMoney {


    /**
     * ISO4217 déja existant à partir du JDK 7. Mais ne prend pas en compte les Monnaie Custom, ni les opérations
     * sur ces monnaies.
     * <p>
     * JP Morgan Chase, Goldman Sachs (expert), Credit Suisse,...
     * <p>
     * JSR354 supporte le ISO 4217 + custom currencies
     */

    @Test
    public void creationdecurrencuUnit() {

        // récupère les monnaies intégrées au JDK
        Currency current = Currency.getInstance("EUR");

        System.out.println("code :" + current.getCurrencyCode());
        System.out.println("symbol :" + current.getSymbol());
        System.out.println("symbol :" + current.getDefaultFractionDigits());

        CurrencyUnit euros = Monetary.getCurrency("EUR");
        euros = Monetary.getCurrency(new Locale("fr", "FR"));
        euros = Monetary.getCurrency(Locale.FRANCE);

        // on peut aussi créer notre propre CURRENCY
        CurrencyUnit bitcoin = CurrencyUnitBuilder.of("XBT", "default").setNumericCode(1).setDefaultFractionDigits(3).build();

    }

    @Test
    public void creationdeMontant() {
        CurrencyUnit euros = Monetary.getCurrency("EUR");
        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency(euros).setNumber(500.0).create();

        MonetaryAmount amount2 = Monetary.getDefaultAmountFactory().setCurrency("COP").setNumber(500_000L).create();

        // on ne peut pas mélanger 2 montants de monnaie différentes
        // MonetaryException
        MonetaryAmount amount3 = amount.add(amount2);

    }

    @Test
    public void operationdeMontant() {
        CurrencyUnit euros = Monetary.getCurrency("EUR");
        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency(euros).setNumber(500.0).create();
        MonetaryAmount amount2 = Monetary.getDefaultAmountFactory().setCurrency(euros).setNumber(200_000).create();

        MonetaryAmount amount3 = amount.multiply(2).add(amount2);

        // on ne peut pas mélanger 2 montants de monnaie différentes
        // MonetaryException
        System.out.println(amount3);

    }


    // la somme des arrondis..
    @Test
    public void laSommeDesArrondisPasEgaleAArrondiDesSommes() {

        Money m1 = Money.of(12.028, "EUR");
        Money m2 = Money.of(12.028, "EUR");

        Money m3 = m1.add(m2);

        //m3.getNumber()

        System.out.println(m3.getNumber());

        //MonetaryRounding

    }

    // il faut pouvoir récupérer les monnaies depuis un autre référentiel



    //

    @Test
    public void formatDesMonnaies() {

        // c'est ici que les arrondis sont faits
        MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.FRANCE);
        Money m1 = Money.of(12.028, "EUR");

        System.out.println(format.format(m1));

        MonetaryAmountFormat formatUS = MonetaryFormats.getAmountFormat(Locale.US);
        System.out.println(formatUS.format(m1));

        // créer son propre formateur:
        MonetaryAmountFormat custom = MonetaryFormats.getAmountFormat(AmountFormatQueryBuilder.of(Locale.US).set("pattern", "$###.## ¤;($###.##) ¤").build());
        System.out.println(custom.format(m1));
    }


    // exemple des indian rupees.


    // comment on les stocke en base de données ?

    // taxu de changes sont fournis par la Bnaque Central Europeenne et le FMI (Fond Monetaire Internationnela)

    @Test
    public void conversiondeMonnaies() {

        // c'est ici que les arrondis sont faits
        //MonetaryAmountFormat format = MonetaryFormats.getAmountFormat(Locale.FRANCE);
        //Money m1 = Money.of(12.028, "USD");

        ExchangeRateProvider imfRateProvider = MonetaryConversions
                .getExchangeRateProvider("IMF");
        ExchangeRateProvider ecbRateProvider = MonetaryConversions
                .getExchangeRateProvider("ECB");

        CurrencyConversion conversion = ecbRateProvider.getCurrencyConversion("EUR");


        //MonetaryAmount money = Money.of(10, "USD");
        MonetaryAmount money = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(500.0).create();

        System.out.println(money.with(conversion));
        //--> Attention, les MonetaryAmount doivent être créé avec le Amount Factory, sion
        // la récupération de l'exchangeRate ne fonctionne pas s'il est fait avec le Money.of()
        // marche pas je sais pas trop pourquoi.
        // peut etre car dans ce cas le provider de la CUrrency n'est pas spécifué.
        //
        System.out.println(conversion.getExchangeRate(money).getFactor());

        // Attnetion, ne marchera pas un jour ou les marchés sont fermés par exemple !



//        CurrencyConversion conversion = MonetaryConversions.getConversion("EUR");
//        System.out.println(conversion.getExchangeRate(m1).getFactor());
        //System.out.println(conversion.getExchangeRate(m1).getContext().getProviderName());
    }

    @Test
    public void arrondi() {
        MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(500.625).create();
        MonetaryRounding round = Monetary.getRounding(Monetary.getCurrency("EUR"));

        MonetaryAmount rounded = amount.with(round);
        System.out.println("round = " + rounded);
    }

    // Possibilité d'utiliser des MonetaryOperator avec with.
    // on pourrait aussi utiliser des lambda dans les with.

    @Test
    public void avoirSonPropreCurrencyProvider() {

        Monetary.getCurrencyProviderNames().stream().forEach(System.out::println);

        CurrencyUnit euros = Monetary.getCurrency("EUR");

        // https://dzone.com/articles/java-service-loader-vs-spring-factories-loader


    }

    @Test
    public void LaCurrencyUnitPeutAvoirUneDateDeValidite() {


        CurrencyUnit frf = Monetary.getCurrency("FRF");
        // https://dzone.com/articles/java-service-loader-vs-spring-factories-loader
        //CurrencyUnit euros = Monetary.getCurrency("EUR");

        // il est tout a fait possible d'utiliser les CurrencyCOntext pour porter des inforations complémentaires sur
        // la monnaie.
        frf.getContext();
        frf.getContext().getKeys(Object.class).stream().forEach(System.out::println);

        // il est possible d'enregistrer les Currencies qu'on vient de créer

        CurrencyContext context = CurrencyContextBuilder.of("default").set("creationDate", new Date()).build();
        CurrencyUnit bitcoin = CurrencyUnitBuilder.of("XBT", context)
                .setNumericCode(1)
                .setDefaultFractionDigits(3).build(true);

        // elles sont maintenant récupérables
        CurrencyUnit btc = Monetary.getCurrency("XBT");
        System.out.println(btc);

        // Pour récupérer une Currency valide sur une date données, on utilise les CurrencyQuery
        // Le CurrencyQuery fait une requete auprès d'un provider pour récupérer la currency la plus en adéquation
        // avec la requete.

        CurrencyQueryBuilder.of().setCurrencyCodes("EUR").set("validDate", new Date()).build();



    }


    // Expliquer l'arrondi HALF_EVEN ( par defaut) . C'est bien ca que l'on veut ?

    // les données sont mises en cache dans un répertoire local appelé .resourceCache.
    // il contient les XML récupérés de l'ECB et FMI.
    


}


