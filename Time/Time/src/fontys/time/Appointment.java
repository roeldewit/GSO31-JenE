/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Joris Douven(Implementatie) Eric de Regter(Specificatie)
 */
public class Appointment {
    
    private String subject;
    private IPeriod period;
    private ArrayList<Contact> invitees;
    
    /**
     * Creates a new Appointments instance with a subject and a Period.
     * 
     * @param subject The subject of the appointment, can be an empty String (not null)
     * @param period The period of the appointment
     */
    
    public Appointment(String subject, IPeriod period)
    {
        this.subject = subject;
        this.period = period;
        invitees = new ArrayList<Contact>();
    }
    
    /**
     * Gets the subject of this Appointment.
     * 
     * @return the subject of this Appointment
     */
    
    public String getSubject()
    {
        return subject;
    }
    
    /**
     * Gets the period of this Appointment.
     * 
     * @return the period of this Appointment
     */
    
    public IPeriod getPeriod()
    {
        return period;
    }
    
    /**
     * Gets the invitees of this Appointment.
     * 
     * @return an iterator with the invitees
     */
    
    public Iterator<Contact> invitees()
    {
        return invitees.iterator();
    }
    
    /**
     * Adds a contact to this Appointment. Is only possible if there is no conflict in the agenda of the contact.
     * 
     * @param c The contact that gets added to this Appointment
     * @return true when the contact was added, false when it failed
     */
    
    public boolean addContact(Contact c)
    {
        //nog maken
        return false;
    }
    
    /**
     * Removes a contact from this Appointment.
     * 
     * @param c The contact that gets removed from this Appointment
     */
    
    public void removeContact(Contact c)
    {
        //nog maken
    }
}
