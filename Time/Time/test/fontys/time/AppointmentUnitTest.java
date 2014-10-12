/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Eric de Regter
 */
public class AppointmentUnitTest {

    /**
     * Creates a new Appointments instance with a subject and a Period.
     *
     * @param subject The subject of the appointment, can be an empty String
     * (not null)
     * @param period The period of the appointment
     */
    @Test
    public void testCreationAppointment()
    {
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment appointment;

        //Create appointment with empty Subject
        try
        {
            appointment = new Appointment("", period);
            Assert.assertNotNull(appointment);
        }
        catch (IllegalArgumentException ex)
        {
            fail(ex.toString());
        }

        //Create appointment with subject
        try
        {
            appointment = new Appointment("Vergadering Software Team", period);
            Assert.assertNotNull(appointment);
        }
        catch (IllegalArgumentException ex)
        {
            fail(ex.toString());
        }
    }

    /**
     * Gets the subject of this Appointment.
     *
     * @return the subject of this Appointment
     */
    @Test
    public void testGetSubject()
    {
        //Appointment with subject
        String subject = "Vergadering met Software Team";
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);

        Appointment appointment;

        try
        {
            appointment = new Appointment("Vergadering met Software Team", period);
            Assert.assertEquals("getSubject is not correct", subject, appointment.getSubject());
        }
        catch (IllegalArgumentException ex)
        {
            fail("No appointment created");
        }

        //Appointment without subject
        subject = "";
        try
        {
            appointment = new Appointment(subject, period);
            Assert.assertEquals("getSubject is not correct", subject, appointment.getSubject());
        }
        catch (IllegalArgumentException ex)
        {
            fail("No appointment created");
        }

    }

    /**
     * Gets the period of this Appointment.
     *
     * @return the period of this Appointment
     */
    @Test
    public void testGetPeriod()
    {
        String subject = "Vergadering met Software Team";
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment appointment = new Appointment(subject, period);

        Assert.assertEquals("getPubject is not correct", period, appointment.getPeriod());
    }

    /**
     * Gets the invitees of this Appointment.
     *
     * @return an iterator with the invitees
     */
    @Test
    public void getInvitees()
    {
        String subject = "Vergadering met Software Team";
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment appointment = new Appointment(subject, period);

        Contact contact1 = new Contact("Eric");
        Contact contact2 = new Contact("Joris");
        Contact contact3 = new Contact("Jan");
        Contact contact4 = new Contact("Joep");

        try
        {
            appointment.addContact(contact1);
            appointment.addContact(contact2);
            appointment.addContact(contact3);
            appointment.addContact(contact4);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Failed to add a contact to the appointment");
        }

        Assert.assertEquals("Amount of invitees is not correct", 4, appointment.invitees().size());

        Assert.assertEquals("Contact does not match", contact1, appointment.invitees().get(0));
        Assert.assertEquals("Contact does not match", contact2, appointment.invitees().get(1));
        Assert.assertEquals("Contact does not match", contact3, appointment.invitees().get(2));
        Assert.assertEquals("Contact does not match", contact4, appointment.invitees().get(3));
    }

    /**
     * Adds a contact to this Appointment. Is only possible if there is no
     * conflict in the agenda of the contact.
     *
     * @param c The contact that gets added to this Appointment
     * @return true when the contact was added, false when it failed
     */
    @Test
    public void testAddRemoveContact()
    {
        //Add Appointment without conflict
        String subject = "Vergadering met Software Team";
        ITime bt = new Time(2014, 8, 21, 18, 00);
        ITime et = new Time(2014, 8, 21, 19, 00);
        IPeriod period = new Period(bt, et);
        Appointment appointment = new Appointment(subject, period);

        Contact contact = new Contact("Eric");
        try
        {
            appointment.addContact(contact);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Contact not added to appointment");
        }

        Assert.assertEquals("Contact does not match", contact, appointment.invitees().get(0));

        //Add Appointment with conflict
        String subject2 = "Team building";
        ITime bt2 = new Time(2014, 8, 21, 16, 00);
        ITime et2 = new Time(2014, 8, 21, 18, 30);
        IPeriod period2 = new Period(bt2, et2);
        Appointment appointment2 = new Appointment(subject2, period2);

        try
        {
            appointment2.addContact(contact);
            fail("Appointments may not conflict");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        /**
         * Removes a contact from this Appointment.
         *
         * @param c The contact that gets removed from this Appointment
         */
        Contact contact2 = new Contact("Joris");
        try
        {
            appointment.addContact(contact2);
            Assert.assertEquals("Amount of contacts for this appointment is not correct", 2, appointment.invitees().size());
        }
        catch (IllegalArgumentException ex)
        {
            fail("Failed to add contact to appointment");
        }

        try
        {
            appointment.removeContact(contact);
            Assert.assertEquals("Amount of invitees is not correct", 1, appointment.invitees().size());
        }
        catch (IllegalArgumentException ex)
        {
            fail("Contact is not invited for this appointment");
        }

    }

}
