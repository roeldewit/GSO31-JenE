/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.bankieren;

import fontys.observer.RemotePropertyListener;
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
public class RekeningTest {
    
    private static IKlant klant;
    private IRekeningTbvBank rekening;
    
    public RekeningTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("RekeningTest - @BeforeClass: setUpClass()");
        klant = new Klant("Eric", "Weert");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("RekeningTest - @AfterClass: tearDownClass()");
        klant = null;
    }
    
    @Before
    public void setUp() {
        System.out.println("RekeningTest - @Before: setUp()");
        rekening = new Rekening(111111, klant, "€");
    }
    
    @After
    public void tearDown() {
        System.out.println("RekeningTest - @After: tearDown()");
        rekening = null;
    }


    /**
     * Test methode voor bank.bankieren.Rekening#muteer()
     */
    @Test
    public void testMuteer() {
        System.out.println("RekeningTest - @Test: testMuteer()");
        
        // Saldo en kredietlimiet controleren
        assertEquals("Saldo moet 0 zijn", new Money(0, "€"), rekening.getSaldo());
        assertEquals("Kredietlimiet moet -100 zijn", -10000, rekening.getKredietLimietInCenten());
        
        // Mutaties verwerken en controleren
        assertTrue("Positieve mutatie moet verwerkt worden", rekening.muteer(new Money(10000, "€")));
        assertEquals("Saldo moet 100 zijn", new Money(10000, "€"), rekening.getSaldo());
        assertTrue("Negatieve mutatie moet verwerkt worden", rekening.muteer(new Money(-20000, "€")));
        assertEquals("Saldo moet -100 zijn", new Money(-10000, "€"), rekening.getSaldo());
    }

    /**
     * Test methode voor bank.bankieren.Rekening#muteer()
     * 
     * Bedrag wat betaald moet worden is te hoog (controle kredietlimiet), betaling wordt niet verwerkt
     */
    @Test
    public void testMuteerLimietOverschreden() {
        System.out.println("RekeningTest - @Test: testMuteerLimietOverschreden()");
        
        // Saldo en kredietlimiet controleren
        assertEquals("Saldo moet 0 zijn", new Money(0, "€"), rekening.getSaldo());
        assertEquals("Kredietlimiet moet -100 zijn", -10000, rekening.getKredietLimietInCenten());
        
        // Mutaties verwerken en controleren (te hoog bedrag, mutatie wordt niet verwerkt)
        assertFalse("Limiet mag niet overschreden worden", rekening.muteer(new Money(-15000, "€")));
        assertEquals("Saldo moet 0 zijn (mutatie niet verwerkt)", new Money(0, "€"), rekening.getSaldo());        
    }

    /**
     * Test methode voor bank.bankieren.Rekening#muteer()
     * 
     * RuntimeException bij een bedrag met waarde 0
     */
    @Test (expected = RuntimeException.class)
    public void testMuteerFoutBedrag0() {
        System.out.println("RekeningTest - @Test: testMuteerFoutBedrag0()");
        
        // Bedrag van €0,00 verwerken
        rekening.muteer(new Money(0, "€"));
    }

    /**
     * Test methode voor bank.bankieren.Rekening#muteer()
     * 
     * NullPointerException bij waarde "Null" (niet opgevangen in code)
     */
    @Test (expected = NullPointerException.class)
    public void testMuteerFoutNull() {
        System.out.println("RekeningTest - @Test: testMuteerFoutNull()");
        
        // Null waarde
        rekening.muteer(null);
    }
}
