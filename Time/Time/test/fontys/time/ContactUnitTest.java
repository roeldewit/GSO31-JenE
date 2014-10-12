/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.Iterator;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joris
 */
public class ContactUnitTest {

    public Contact c;

    public ContactUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        c = new Contact("Henk");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("GetName not correct", "Henk", c.getName());
    }

    @Test
    public void testAddAppointment() {
        /**
         * Adds an appointment to this contact's agenda. There may be no overlap
         * between appointments.
         *
         * @param a The appointment to add
         * @return True when the operation succeeded, False when it failed.
         */
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment app1 = new Appointment("", period);
        Assert.assertTrue("Adding 1st appointment failed.", c.addAppointment(app1));

        bt = new Time(2014, 8, 21, 18, 30);
        et = new Time(2014, 8, 21, 19, 30);
        period = new Period(bt, et);
        Appointment app2 = new Appointment("", period);
        Assert.assertFalse("Adding appointment with overlap worked (it shouldn't).", c.addAppointment(app2));

        bt = new Time(2014, 8, 21, 19, 30);
        et = new Time(2014, 8, 21, 20, 30);
        period = new Period(bt, et);
        Appointment app3 = new Appointment("", period);
        Assert.assertTrue("Adding 2nd appointment without overlap failed.", c.addAppointment(app3));
    }

    @Test
    public void testRemoveAppointment() {
        /**
         * Removes an appointment from this contact's agenda.
         *
         * @param a The appointment to remove.
         */
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment app1 = new Appointment("", period);
        c.addAppointment(app1);

        bt = new Time(2014, 8, 21, 19, 30);
        et = new Time(2014, 8, 21, 20, 30);
        period = new Period(bt, et);
        Appointment app2 = new Appointment("", period);
        c.addAppointment(app2);

        c.removeAppointment(app1);
        for (Iterator<Appointment> appIter = c.appointments(); appIter.hasNext();) {
            if (appIter.next() == app1) {
                fail("Appointment hasn't been removed.");
            }
        }
    }
}
