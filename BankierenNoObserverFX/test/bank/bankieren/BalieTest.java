package bank.bankieren;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.internettoegang.Balie;
import bank.internettoegang.CentraleBank;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import bank.internettoegang.ICentraleBank;
import fontys.util.InvalidSessionException;
import java.rmi.RemoteException;
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
public class BalieTest {

    private static IBalie balieIng;
    private String accountnaam;
    private IBankiersessie sessie;
    
    public BalieTest()
    {
    }

    /**
     * @throws java.rmi.RemoteException
     */
    @BeforeClass
    public static void setUpClass() throws RemoteException
    {
        System.out.println("BalieTest - @BeforeClass: setUpClass()");
        ICentraleBank centrale = new CentraleBank();
        IBank ing = new Bank("ING", centrale);
        balieIng = new Balie(ing);
    }

    @AfterClass
    public static void tearDownClass()
    {
        System.out.println("BalieTest - @AfterClass: tearDownClass()");
        balieIng = null;
    }

    /**
     * @throws java.rmi.RemoteException
     */
    @Before
    public void setUp() throws RemoteException
    {
        System.out.println("BalieTest - @Before: setUp()");
        accountnaam = balieIng.openRekening("Eric", "Weert", "TestWW");
        sessie = balieIng.logIn(accountnaam, "TestWW");
    }

    @After
    public void tearDown()
    {
        System.out.println("BalieTest - @After: tearDown()");
        accountnaam = null;
        sessie = null;
    }

    /**
     * Test methode voor bank.internettoegang.Balie#openRekening()
     * 
     * creatie van een nieuwe bankrekening; het gegenereerde
     * bankrekeningnummer is identificerend voor de nieuwe bankrekening en
     * heeft een saldo van 0 euro
     *
     * @param naam van de eigenaar van de nieuwe bankrekening
     * @param plaats de woonplaats van de eigenaar van de nieuwe
     * bankrekening
     * @param wachtwoord van het account waarmee er toegang kan worden
     * verkregen tot de nieuwe bankrekening
     * @return null zodra naam of plaats een lege string of wachtwoord
     * minder dan vier of meer dan acht karakters lang is en anders de
     * gegenereerde accountnaam(8 karakters lang) waarmee er toegang tot de
     * nieuwe bankrekening kan worden verkregen
     * @throws fontys.util.InvalidSessionException
     * @throws java.rmi.RemoteException
     */
    @Test
    public void openRekening() throws InvalidSessionException, RemoteException
    {
        System.out.println("BalieTest - @Test: openRekening()");

        String accountnaam2 = balieIng.openRekening("Eric", "Weert", "TestWW");
        // Gegenereerde accountnaam is ongelijk alhoewel de klantgegevens volledig overeen komen
        assertThat("Rekeningnummer is gelijk", accountnaam, not(accountnaam2));

        // Bij openen bankrekening is het saldo €0,00
        assertEquals("Saldo niet gelijk", new Money(0, "€"), sessie.getRekening().getSaldo());
        
        // Bij openen bankrekening is naam klant juist opgeslagen
        assertEquals("Naam niet gelijk", "Eric", sessie.getRekening().getEigenaar().getNaam());
    }

    /**
     * Test methode voor bank.internettoegang.Balie#openRekening()
     * 
     * Test of de woonplaats, naam, wachtwoord verkeerd ingevoerd kan worden
     * 
     * Waarde null wordt gereturned i.v.m. ongeldige waarden
     * @throws java.rmi.RemoteException
     */
    @Test
    public void openRekeningOngeldigeWaarde() throws RemoteException
    {
        System.out.println("BalieTest - @Test: openRekeningOngeldigeWaarde()");

        // Foutieve invoeren
        assertNull("Ongeldig wachtwoord", balieIng.openRekening("Klant", "Eindhoven", "ddddgjgdghjgggdjhdhj"));
        assertNull("Ongeldige klantnaam", balieIng.openRekening("", "Eindhoven", "EDR1"));
        assertNull("Ongeldige plaatsnaam", balieIng.openRekening("Klant", "", "Test"));
    }

    /**
     * Test methode voor bank.internettoegang.Balie#openRekening()
     * 
     * NullPointerException bij waarde "Null" (niet opgevangen in code)
     * @throws java.rmi.RemoteException
     */
    @Test (expected = NullPointerException.class)
    public void openRekeningFoutNull() throws RemoteException
    {
        System.out.println("BalieTest - @Test: openRekeningFoutNull()");

        // Waarde null
        balieIng.openRekening("Eric", null, "TestWW");
    }

    /**
     * Test methode voor bank.internettoegang.Balie#logIn()
     * 
     * er wordt een sessie opgestart voor het login-account met de naam
     * accountnaam mits het wachtwoord correct is
     *
     * @param accountnaam
     * @param wachtwoord
     * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
     * tot de bankrekening die hoort bij het betreffende login- account mits
     * accountnaam en wachtwoord matchen, anders null
     * @throws java.rmi.RemoteException
     */
    @Test
    public void logIn() throws RemoteException
    {
        System.out.println("BalieTest - @Test: logIn()");
        
        // Sessie is aangemaakt
        assertNotNull("Geen sessie", sessie);
        
        IBankiersessie sessie2 = balieIng.logIn(accountnaam, "TestWW");
        // Sessie2 is aangemaakt
        assertNotNull("Geen sessie", sessie2);
        
        // Sessies zijn geldig
        assertTrue("Sessie ongeldig", sessie.isGeldig());
        assertTrue("Sessie ongeldig", sessie2.isGeldig());
        
        // Sessies zijn ongelijk
        assertThat("Sessies zijn gelijk", sessie, not(sessie2));
    }

    /**
     * Test methode voor bank.internettoegang.Balie#logIn()
     * 
     * er wordt een sessie opgestart voor het login-account met de naam
     * accountnaam mits het wachtwoord correct is
     *
     * Onjuiste invoer (sessie wordt niet opgestart), Null wordt gereturned
     * @throws java.rmi.RemoteException
     */
    @Test
    public void logInFouteInvoer() throws RemoteException
    {
        System.out.println("BalieTest - @Test: logInFouteInvoer()");
        
        // Sessie is aangemaakt
        assertNotNull("Geen sessie", sessie);
        
        // Foutieve invoeren
        assertNull("Verkeerd wachtwoord", balieIng.logIn(accountnaam, "Eric1 "));
        assertNull("Verkeerde accountnaam", balieIng.logIn("Eric ", "TestWW"));
        assertNull("Onbekend account", balieIng.logIn("Karel", "Karel"));
    }

}
