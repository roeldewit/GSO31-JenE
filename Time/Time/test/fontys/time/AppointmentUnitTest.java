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
        catch(IllegalArgumentException ex)
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
        Appointment appointment = new Appointment("Vergadering met Software Team", period);
        
        Assert.assertEquals("getSubject is not correct", subject, appointment.getSubject());
        
        
        //Appointment without subject
        subject = "";
        appointment = new Appointment(subject, period);
        Assert.assertEquals("getSubject is not correct", subject, appointment.getSubject());
        
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
        
        Contact contact1 = new Contact(subject);
        Contact contact2 = new Contact(subject);
        Contact contact3 = new Contact(subject);
        Contact contact4 = new Contact(subject);
        
        appointment.addContact(contact1);
        appointment.addContact(contact2);
        appointment.addContact(contact3);
        appointment.addContact(contact4);
        
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
    public void testAddContact()
    {
        
    }
    /**
     * Removes a contact from this Appointment.
     *
     * @param c The contact that gets removed from this Appointment
     */
    
    @Test
    public void testRemoveContact()
    {
        
    }
}
