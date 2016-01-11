package bank.bankieren;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.internettoegang.CentraleBank;
import bank.internettoegang.ICentraleBank;
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
 * @author Astrid
 */
public class BankTest {

    private static IBank ing;
    private static IBank abn;
    private int rekeningNr1;
    private int rekeningNr2;
    
    public BankTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
        System.out.println("BankTest - @BeforeClass: setUpClass()");
        ICentraleBank centrale = new CentraleBank();
        ing = new Bank("ING", centrale);
        abn = new Bank("ABN", centrale);
    }

    @AfterClass
    public static void tearDownClass()
    {
        System.out.println("BankTest - @AfterClass: tearDownClass()");
        ing = null;
        abn = null;
    }

    @Before
    public void setUp()
    {
        System.out.println("BankTest - @Before: setUp()");
        rekeningNr1 = ing.openRekening("Eric", "Weert");
        rekeningNr2 = ing.openRekening("Joost", "Geleen");
    }

    @After
    public void tearDown()
    {
        System.out.println("BankTest - @After: tearDown()");
        rekeningNr1 = 0;
        rekeningNr2 = 0;
    }

    /**
     * Test methode voor bank.bankieren.Bank#openRekening()
     * creatie van een nieuwe bankrekening met een identificerend
     * rekeningnummer; alleen als de klant, geidentificeerd door naam en
     * plaats, nog niet bestaat wordt er ook een nieuwe klant aangemaakt
     *
     * @param naam van de eigenaar van de nieuwe bankrekening
     * @param plaats de woonplaats van de eigenaar van de nieuwe
     * bankrekening
     * @return bij succes rekeningnummer, anders waarde -1 zodra naam of plaats een lege string en anders het nummer
     * van de gecreeerde bankrekening
     */
    @Test
    public void openRekening()
    {
        System.out.println("BankTest - @Test: openRekening()");

        String klantnaam = "Eric";
        String klantplaats = "Weert";

        /**
         * creatie van een nieuwe bankrekening met een identificerend
         * rekeningnummer;
         */
        int rekeningNr = ing.openRekening(klantnaam, klantplaats);

        assertThat(rekeningNr, not(rekeningNr1));

        /**
         * rekeningNr & rekeningNr1 zijn van dezelfde klant (klant1)
         * alleen als de klant, geidentificeerd door naam en plaats, nog niet
         * bestaat wordt er ook een nieuwe klant aangemaakt
         */
        assertEquals("Klant niet gelijk", ing.getRekening(rekeningNr).getEigenaar(), ing.getRekening(rekeningNr1).getEigenaar());

        /**
         * rekeningNr & rekeningNr1 zijn van dezelfde klant (klant1)
         * een klant mag verschillende rekeningen hebben bij dezelfde bank (in dit geval twee rekeningen bij de ING)
         */
        assertThat("Banknummers gelijk", rekeningNr, not(rekeningNr1));
        assertThat("Banknummers gelijk", rekeningNr1, not(rekeningNr2));

        /**
         * rekeningNr2 is van een andere klant (klant2) Beide mogen niet
         * gelijk zijn aan elkaar
         */
        assertThat("Klant gelijk", ing.getRekening(rekeningNr1).getEigenaar(), not(ing.getRekening(rekeningNr2).getEigenaar()));
    }

    /**
     * Test methode voor bank.bankieren.Bank#openRekening()
     * 
     * Waarde -1 wordt gereturned i.v.m. ongeldige waarden
     * 
     * @return -1 zodra naam of plaats een lege string en anders het nummer
     * van de gecreeerde bankrekening
     */
    @Test
    public void openRekeningOngeldigeWaarde()
    {
        System.out.println("BankTest - @Test: openRekeningOngeldigeWaarde()");

        // Foutieve invoeren
        assertEquals("Bankrekening wordt geopend", -1, abn.openRekening("Karel", ""));
        assertEquals("Bankrekening wordt geopend", -1, abn.openRekening("", "Weert"));
    }
    
    /**
     * Test methode voor bank.bankieren.Bank#openRekening()
     * 
     * NullPointerException bij waarde "Null" (niet opgevangen in code)
     */
    @Test(expected = NullPointerException.class)
    public void openRekeningFoutNull()
    {
        System.out.println("BankTest - @Test: openRekeningFoutNull()");
        
        // Null waarde
        ing.openRekening(null, null);
    }
    
    /**
     * Test methode voor bank.bankieren.Bank#maakOver()
     *
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
    @Test
    public void maakOver() throws NumberDoesntExistException
    {
        System.out.println("BankTest - @Test: maakOver()");
        
        //Afgeschreven bedrag is groter dan het kredietlimiet
        assertFalse("Geld overgemaakt", ing.maakOver(rekeningNr1, rekeningNr2, new Money(1000000, "€")));

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
    }

    /**
     * Test methode voor bank.bankieren.Bank#maakOver()
     *
     * RuntimeException bij gelijke rekeningnummers
     *
     * @param bron
     * @param bestemming ongelijk aan bron
     * @param bedrag is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException als een van de twee
     * bankrekeningnummers onbekend is
     */
    @Test (expected = RuntimeException.class)
    public void maakOverFoutBron() throws NumberDoesntExistException
    {
        System.out.println("BankTest - @Test: maakOverFoutBron()");

        // Rekeningnummers zijn gelijk
        ing.maakOver(rekeningNr2, rekeningNr2, new Money(1000, "€"));
    }

    /**
     * Test methode voor bank.bankieren.Bank#maakOver()
     *
     * RuntimeException bij een bedrag met waarde 0
     *
     * @param bron
     * @param bestemming ongelijk aan bron
     * @param bedrag is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException als een van de twee
     * bankrekeningnummers onbekend is
     */
    @Test (expected = RuntimeException.class)
    public void maakOverFoutBedrag0() throws NumberDoesntExistException
    {
        System.out.println("BankTest - @Test: maakOverFoutBedrag0()");

        // Bedrag is 0
        ing.maakOver(rekeningNr1, rekeningNr2, new Money(0, "€"));
    }    
    
    /**
     * Test methode voor bank.bankieren.Bank#maakOver()
     *
     * NumberDoesntExistException bij onbekend rekeningnummer
     *
     * @param bron
     * @param bestemming ongelijk aan bron
     * @param bedrag is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException als een van de twee
     * bankrekeningnummers onbekend is
     */
    @Test (expected = NumberDoesntExistException.class)
    public void maakOverFoutRekeningNr() throws NumberDoesntExistException
    {
        System.out.println("BankTest - @Test: maakOverFoutRekeningNr()");

        // Foutieve invoer
        ing.maakOver(rekeningNr1, 9999999, new Money(1000, "€"));
    }

    /**
     * Test methode voor bank.bankieren.Bank#maakOver()
     * 
     * NullPointerException bij bedrag "null" (niet opgevangen in code)
     * 
     * @param bron
     * @param bestemming ongelijk aan bron
     * @param bedrag is groter dan 0
     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
     * @throws NumberDoesntExistException als een van de twee
     * bankrekeningnummers onbekend is
     */
    @Test (expected = NullPointerException.class)
    public void maakOverFoutNull() throws NumberDoesntExistException
    {
        System.out.println("BankTest - @Test: maakOverFoutNull()");

        // Null waarde
        ing.maakOver(rekeningNr1, rekeningNr2, null);
    }

    /**
     * Test methode voor bank.bankieren.Bank#getRekening()
     * @param nr
     * @return de bankrekening met nummer nr mits bij deze bank bekend, anders
     * null
     */
    @Test
    public void getRekening()
    {
        int rekeningNummer = abn.openRekening("Karel", "Eindhoven");
        
        assertNull("Rekening mag niet bestaan", abn.getRekening(92340988));
        assertNotNull("Geen rekening gevonden", abn.getRekening(rekeningNummer));
    }
    
    /**
     * Test methode voor bank.bankieren.Bank#getName()
     * @return de naam van deze bank
     */
    @Test
    public void getName()
    {        
        assertEquals("Naam niet gelijk", "ING", ing.getName());
        assertEquals("Naam niet gelijk", "ABN", abn.getName());
        
        ICentraleBank centrale = new CentraleBank();
        IBank fortis = new Bank("    ", centrale);
        assertEquals("Naam niet gelijk", "    ", fortis.getName());
    }
}
