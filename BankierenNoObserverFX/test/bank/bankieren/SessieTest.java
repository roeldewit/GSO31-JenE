package bank.bankieren;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import bank.internettoegang.Bankiersessie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SessieTest {

    public SessieTest()
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
    public void isGeldig()
    {
        /**
         * @returns true als de laatste aanroep van getRekening of maakOver voor
         * deze sessie minder dan GELDIGHEIDSDUUR geleden is en er geen
         * communicatiestoornis in de tussentijd is opgetreden, anders false
         */
        try
        {
            IBank bank = new Bank("ING");
            int bankrekeningnummer = bank.openRekening("Eric", "Weert");
            IBankiersessie sessie = new Bankiersessie(bankrekeningnummer, bank);

            try
            {
                assertEquals("Bankrekeningnummer klopt niet", sessie.getRekening().getNr(), bankrekeningnummer);
                assertEquals("Naam van de eigenaar klopt niet", "Eric", sessie.getRekening().getEigenaar().getNaam());
            }
            catch (InvalidSessionException ex)
            {
                fail("Geen actieve sessie");
            }

            assertTrue("Sessie niet geldig", sessie.isGeldig());

            int bankrekeningnummer2 = bank.openRekening("Joris", "Geleen");
            try
            {
                sessie.maakOver(bankrekeningnummer2, new Money(1000, "€"));
            }
            catch (NumberDoesntExistException | InvalidSessionException ex)
            {
                System.out.println("Exception ex: " + ex.getMessage());
            }

            sessie = new Bankiersessie(bankrekeningnummer2, bank);
            try
            {
                assertTrue(sessie.isGeldig());
                Thread.currentThread().sleep(1000);
                try
                {
                    sessie.maakOver(bankrekeningnummer, new Money(1, "€"));
                    fail("Sessie geldig");
                }
                catch (InvalidSessionException ex)
                {
                    assertTrue(true);
                }
                catch (RemoteException | NumberDoesntExistException ex)
                {
                    fail();
                }

                sessie = new Bankiersessie(bankrekeningnummer2, bank);
                try
                {
                    Thread.currentThread().sleep(590);
                    sessie.maakOver(bankrekeningnummer, new Money(20, "€"));
                    assertTrue("Sessie niet geldig", sessie.isGeldig());
                }
                catch (NumberDoesntExistException | InvalidSessionException | RemoteException ex)
                {
                    fail();
                }

            }
            catch (InterruptedException ex)
            {
                System.out.println("Interrupted: " + ex.getMessage());
            }

        }
        catch (RemoteException ex)
        {
            System.out.println("Remote ex: " + ex.getMessage());
        }
    }
}
