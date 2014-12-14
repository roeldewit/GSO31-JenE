/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import bank.bankieren.Bank;
import bank.bankieren.IBank;
import bank.bankieren.Money;
import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class BalieTest {

    public BalieTest()
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
         */

        IBank bank = new Bank("ING");
        try
        {
            IBalie balie = new Balie(bank);
            String naam = "Eric";
            String plaats = "Weert";
            String wachtwoord = "EDR1";
            String accountnaam = balie.openRekening(naam, plaats, wachtwoord);
            String accountnaam2 = balie.openRekening(naam, plaats, wachtwoord);
            assertThat("Rekeningnummer is gelijk", accountnaam, not(accountnaam2));

            IBankiersessie sessie = balie.logIn(accountnaam, wachtwoord);
            try
            {
                assertEquals("Saldo niet gelijk", new Money(0, "€"), sessie.getRekening().getSaldo());
                assertEquals("Naam niet gelijk", naam, sessie.getRekening().getEigenaar().getNaam());
            }
            catch (InvalidSessionException ex)
            {
                System.out.println("Invalid session: " + ex.getMessage());
            }
            
            /**
             * Test of de woonplaats, naam, wachtwoord verkeerd ingevoerd kan worden
             * er wordt nooit IllegalArgument exception opgegooid?
             */
            assertNull("Rekening is aangemaakt", balie.openRekening("  ", "", "ddddgjgdghjgghhjjhjhdjjhdgdjhdhj"));
            assertNull("Rekening is aangemaakt", balie.openRekening("", "", "EDR1"));
            assertNull("Rekening is aangemaakt", balie.openRekening("", " ", "      "));
            
            /**
             * Test of de woonplaats, naam, wachtwoord null kunnen zijn
             * er wordt nooit IllegalArgument exception opgegooid?
             */
            
            try
            {
                assertNull("Rekening is aangemaakt", balie.openRekening(null, null, null));
                assertNull("Rekening is aangemaakt", balie.openRekening("Eric", null, null));
                assertNull("Rekening is aangemaakt", balie.openRekening("Eric", "Weert", null));
                
                assertNull("Rekening is aangemaakt", balie.openRekening(null, null, "sdlkfj"));
                assertNull("Rekening is aangemaakt", balie.openRekening(null, "Weert", null));
                assertNull("Rekening is aangemaakt", balie.openRekening(null, null, "lksjdf"));
            }
            catch (NullPointerException ex)
            {
                fail("Geen controle op null values");
            }
        }
        catch (RemoteException ex)
        {
            System.out.println("Remote ex: " + ex.getMessage());
        }
    }

    @Test
    public void login()
    {
        /**
         * er wordt een sessie opgestart voor het login-account met de naam
         * accountnaam mits het wachtwoord correct is
         *
         * @param accountnaam
         * @param wachtwoord
         * @return de gegenereerde sessie waarbinnen de gebruiker toegang krijgt
         * tot de bankrekening die hoort bij het betreffende login- account mits
         * accountnaam en wachtwoord matchen, anders null
         */
        
        IBank bank = new Bank("ABN");
        try
        {
            IBalie balie = new Balie(bank);
            String naam= "Eric";
            String woonplaats = "Weert";
            String wachtwoord = "Eric1";
            String accountnaam = balie.openRekening(naam, woonplaats, wachtwoord);
            IBankiersessie sessie = balie.logIn(accountnaam, wachtwoord);
            assertNotNull("Geen sessie", sessie);
            assertNull("Verkeerd wachtwoord", balie.logIn(accountnaam, "Eric1 "));
            assertNull("Verkeerde accountnaam", balie.logIn("Eric ", wachtwoord));
            assertNull("Onbekend account", balie.logIn("Karel", "Karel"));
        }
        catch(RemoteException ex)
        {
            System.out.println("Remote ex: " + ex.getMessage());
        }
    }

}
