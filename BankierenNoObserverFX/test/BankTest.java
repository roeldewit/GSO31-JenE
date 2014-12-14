/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.util.NumberDoesntExistException;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eric
 */
public class BankTest {

    public BankTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    @Test
    public void openRekening()
    {
        /**
         * Deze test failt om 2 redenen: 1. Openrekening mag een lege string als
         * parameter hebben, blijkbaar wel " " (een spatie?) 2. Er wordt niet
         * gekeken bij het openen van een rekening of de parameters niet null
         * zijn.
         */

        /**
         * creatie van een nieuwe bankrekening met een identificerend
         * rekeningnummer; alleen als de klant, geidentificeerd door naam en
         * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
         *
         * @param naam van de eigenaar van de nieuwe bankrekening
         * @param plaats de woonplaats van de eigenaar van de nieuwe
         * bankrekening
         * @return -1 zodra naam of plaats een lege string en anders het nummer
         * van de gecreeerde bankrekening
         */
        String banknaam = "ING";
        String klantnaam = "Eric";
        String klantplaats = "Weert";

        IBank ing = new Bank(banknaam);

        assertEquals("Banknaam klopt niet", "ING", ing.getName());

        /**
         * creatie van een nieuwe bankrekening met een identificerend
         * rekeningnummer;
         */
        int bankrekeningNr1 = ing.openRekening(klantnaam, klantplaats);
        int bankrekeningNr2 = ing.openRekening(klantnaam, klantplaats);

        assertThat(bankrekeningNr1, not(bankrekeningNr2));

        /**
         * alleen als de klant, geidentificeerd door naam en plaats, nog niet
         * bestaat wordt er ook een nieuwe klant aangemaakt
         */
        assertEquals("Klant niet gelijk", ing.getRekening(bankrekeningNr1).getEigenaar(), ing.getRekening(bankrekeningNr2).getEigenaar());

        String klantnaam2 = "Joris";
        String klantplaats2 = "Geleen";

        int bankrekeningNr3 = ing.openRekening(klantnaam2, klantplaats2);
        assertThat("Banknummers gelijk", bankrekeningNr1, not(bankrekeningNr3));
        assertThat("Banknummers gelijk", bankrekeningNr2, not(bankrekeningNr3));

        /**
         * BankrekeningNr1 & bankrekeningNr2 zijn van dezelfde klant (klant1)
         * BankrekeningNr3 is van een andere klant (klant2) Beide mogen niet
         * gelijk zijn aan elkaar
         */
        assertThat("Klant gelijk", ing.getRekening(bankrekeningNr2).getEigenaar(), not(ing.getRekening(bankrekeningNr3).getEigenaar()));

        /**
         * @return -1 zodra naam of plaats een lege string en anders het nummer
         * van de gecreeerde bankrekening
         *
         * niet gecontrolleerd op " ", ook niet op null values
         */
        assertEquals("Bankrekening wordt geopend", -1, ing.openRekening("Karel", "  "));
        assertEquals("Bankrekening wordt geopend", -1, ing.openRekening(" ", "Weert"));
        assertEquals("Bankrekening wordt geopend", -1, ing.openRekening("", ""));

        try
        {
            ing.openRekening(null, null);
            fail("Bankrekening mag niet geopend wordt met naam = null of plaats = null");
        }
        catch (IllegalArgumentException ex)
        {
            assertTrue(true);
        }

    }

    @Test
    public void maakOver()
    {

        /**
         * Deze test failt om: 1. Er wordt niet gecontrolleerd op null values
         */
        /**
         * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar
         * de bankrekening met nummer bestemming, mits het afschrijven van het
         * bedrag van de rekening met nr bron niet lager wordt dan de
         * kredietlimiet van deze rekening
         *
         * @param bron
         * @param bestemming ongelijk aan bron
         * @param bedrag is groter dan 0
         * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
         * @throws NumberDoesntExistException als een van de twee
         * bankrekeningnummers onbekend is
         */
        String banknaam = "ING";

        String klantnaam = "Eric";
        String klantplaats = "Weert";

        String klantnaam2 = "Joris";
        String klantplaats2 = "Geleen";

        IBank ing = new Bank(banknaam);

        int rekeningNr1 = ing.openRekening(klantnaam, klantplaats);
        int rekeningNr2 = ing.openRekening(klantnaam2, klantplaats2);

        try
        {
            //Afgeschreven bedrag is groter dan het kredietlimiet
            assertFalse("Geld overgmaakt", ing.maakOver(rekeningNr1, rekeningNr2, new Money(1000000, "€")));

            //Afgeschreven bedrag is kleiner dan het kredietlimiet (moet dus werken)
            assertTrue("Geld niet overgemaakt", ing.maakOver(rekeningNr1, rekeningNr2, new Money(10, "€")));
            assertEquals("Hoeveelheid geld klopt niet", new Money(-10, "€"), ing.getRekening(rekeningNr1).getSaldo());

            //Resultaat is dat het bedrag precies het kredietlimiet is
            assertTrue("Geld niet overgemaakt", ing.maakOver(rekeningNr1, rekeningNr2, new Money(10000 - 10, "€")));
            assertEquals("Hoeveelheid geld klopt niet", new Money(-10000, "€"), ing.getRekening(rekeningNr1).getSaldo());

            //Balans herstellen beide rekeningen hebben €0,00
            ing.maakOver(rekeningNr2, rekeningNr1, new Money(10000, "€"));
            assertEquals("Hoeveelheid geld niet gelijk", ing.getRekening(rekeningNr1).getSaldo(), ing.getRekening(rekeningNr2).getSaldo());

            //Resultaat is dat het bedrag net iets onder het kredietlimiet is
            assertFalse("Onder het kredietlimiet", ing.maakOver(rekeningNr2, rekeningNr1, new Money(10000 + 1, "€")));

            /**
             * @param bestemming ongelijk aan bron
             *
             * Er wordt niet opgegeven dat er een runtime exception wordt
             * opgegooid als de bron wel gelijk is aan de bestemming
             */
            try
            {
                ing.maakOver(rekeningNr2, rekeningNr2, new Money(1000, "€"));
                fail("Bron gelijk aan ontvanger");
            }
            catch (RuntimeException ex)
            {
                assertTrue(true);
            }

            try
            {
                ing.maakOver(rekeningNr1, rekeningNr1, new Money(1000, "€"));
                fail("Bron gelijk aan ontvanger");
            }
            catch (RuntimeException ex)
            {
                assertTrue(true);
            }

            try
            {
                ing.maakOver(rekeningNr2, rekeningNr1, new Money(0, "€"));
                fail("Bedrag mag niet 0 zijn");
            }
            catch (RuntimeException ex)
            {
                assertTrue(true);
            }

            try
            {
                ing.maakOver(rekeningNr2, rekeningNr1, null);
                fail("Bedrag mag niet null zijn");
            }
            catch (IllegalArgumentException ex)
            {
                assertTrue(true);
            }
            catch (NullPointerException ex)
            {
                fail("Geen controle op null waardes");
            }

        }
        catch (NumberDoesntExistException ex)
        {
            assertTrue(true);
        }
    }

    /**
     * @param nr
     * @return de bankrekening met nummer nr mits bij deze bank bekend, anders
     * null
     */
    @Test
    public void getRekening()
    {
        IBank ABN = new Bank("ABN");
        int rekeningNummer = ABN.openRekening("Karel", "Eindhoven");
        
        assertNull("Rekening mag niet bestaan", ABN.getRekening(92340988));
        assertNotNull("Geen rekening gevonden", ABN.getRekening(rekeningNummer));
    }
    
    /**
     * @return de naam van deze bank
     */
    @Test
    public void getName()
    {
        IBank ABN = new Bank("ABN");
        int rekeningNummer = ABN.openRekening("Karel", "Eindhoven");
        
        assertEquals("Naam niet gelijk", "ABN", ABN.getName());
        
        IBank ING = new Bank("");
        assertEquals("Naam niet gelijk", "", ING.getName());
        
        IBank fortis = new Bank("    ");
        assertEquals("Naam niet gelijk", "    ", fortis.getName());
    }
}
