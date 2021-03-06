/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joris Douven
 */
public class TimeUnitTest {

    public TimeUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void testExistingDate() {

        ITime time;

        /**
         * creation of a Time-object with year y, month m, day d, hours h and
         * minutes m; if the combination of y-m-d refers to a non-existing date
         * a correct value of this Time-object will be not guaranteed
         */
        // @param y
        // @param m 1≤m≤12
        //Test wrong month
        try {
            time = new Time(2014, 0, 18, 20, 36);
            fail("Month >= 1 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        try {
            time = new Time(2014, 1, 18, 20, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Month = 1 doesn't work");
        }
        try {
            time = new Time(2014, 12, 18, 20, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Month = 12 doesn't work");
        }
        try {
            time = new Time(2014, 13, 18, 20, 36);
            fail("Month <= 12 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }

        // @param d 1≤d≤31
        //Test wrong day
        try {
            time = new Time(2014, 06, 0, 20, 36);
            fail("Day >= 1 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        try {
            time = new Time(2014, 06, 1, 20, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Day = 1 doesn't work");
        }
        try {
            time = new Time(2014, 06, 31, 20, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Day = 31 doesn't work");
        }
        try {
            time = new Time(2014, 06, 32, 20, 36);
            fail("Day <= 31 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        // @param h 0≤h≤23
        //Test wrong hour
        try {
            time = new Time(2014, 06, 18, -1, 36);
            fail("hour >=0 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        try {
            time = new Time(2014, 06, 18, 0, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("hour = 1 doesn't work");
        }
        try {
            time = new Time(2014, 06, 18, 23, 36);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("hour = 23 doesn't work");
        }
        try {
            time = new Time(2014, 06, 18, 24, 36);
            fail("hour <=23 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        // @param min 0≤m≤59
        //Test wrong minutes
        try {
            time = new Time(2014, 06, 18, 20, -1);
            fail("Minute >= 0 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
        try {
            time = new Time(2014, 06, 18, 20, 0);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Minute = 0 doesn't work");
        }
        try {
            time = new Time(2014, 06, 18, 20, 59);
            assertNotNull(time);
        } catch (IllegalArgumentException ex) {
            fail("Minute = 59 doesn't work");
        }
        try {
            time = new Time(2014, 06, 18, 20, 60);
            fail("Minute <= 59 check doesn't work");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void testPlus() {

        //Test if minutes add up
        ITime t1 = new Time(2014, 06, 15, 20, 00).plus(30);
        ITime t2 = new Time(2014, 06, 15, 20, 30);
        assertEquals("(minutes)New time not correct", 0, t2.compareTo(t1));

        //Test if minutes get subtracted
        t1 = new Time(2014, 06, 15, 20, 00).plus(-60);
        t2 = new Time(2014, 06, 15, 19, 00);
        assertEquals("(hours)New time not correct", 0, t2.compareTo(t1));

        //Test if hours add up
        t1 = new Time(2014, 06, 15, 20, 00).plus(120);
        t2 = new Time(2014, 06, 15, 22, 00);
        assertEquals("(hours)New time not correct", 0, t2.compareTo(t1));

        //Test if days add up
        t1 = new Time(2014, 06, 15, 20, 00).plus(300);
        t2 = new Time(2014, 06, 16, 01, 00);
        assertEquals("(days)New time not correct", 0, t2.compareTo(t1));

        //Test if months add up
        t1 = new Time(2014, 06, 15, 20, 00).plus(43200);
        t2 = new Time(2014, 07, 15, 20, 00);
        assertEquals("(months)New time not correct", 0, t2.compareTo(t1));

        //Test if years add up
        t1 = new Time(2014, 06, 15, 20, 00).plus(525600);
        t2 = new Time(2015, 06, 15, 20, 00);
        assertEquals("(years)New time not correct", 0, t2.compareTo(t1));
    }

    @Test
    public void testDifference() {
        //Test difference
        ITime t1 = new Time(2014, 06, 15, 20, 00);
        ITime t2 = new Time(2014, 06, 15, 20, 30);
        assertEquals("Difference calculation incorrect", -30, t1.difference(t2));
    }
}
