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
 * @author Joris Douven
 */
public class Contact {

    private final String name;
    private final ArrayList<Appointment> appointments;

    /**
     * Creates a new Contact object with specified name.
     *
     * @param name The name of this contact
     */
    public Contact(String name)
    {
        this.name = name;
        appointments = new ArrayList<Appointment>();
    }

    /**
     * Gets the name of this contact
     *
     * @return the name of this contact
     */
    public String getName()
    {
        return name;
    }

    /**
     * Adds an appointment to this contact's agenda. There may be no overlap
     * between appointments.
     *
     * @param a The appointment to add
     * @return True when the operation succeeded, False when it failed.
     */
    boolean addAppointment(Appointment a)
    {
        if (appointments.size() > 0)
        {
            for (Appointment appointment : appointments)
            {
                if (a.getPeriod().intersectionWith(appointment.getPeriod()) == null)
                {
                    appointments.add(a);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            appointments.add(a);
            return true;
        }
        
        return false;
    }

    /**
     * Removes an appointment from this contact's agenda.
     *
     * @param a The appointment to remove.
     */
    void removeAppointment(Appointment a)
    {
        if(appointments.contains(a))
        {
            appointments.remove(a);
        }
    }

    /**
     * Iterates through the appointments in this contact's agenda.
     *
     * @return An iteration of the appointments
     */
    public ArrayList<Appointment> appointments()
    {
        return appointments;
    }
}
