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
    public Contact(String name) {
        this.name = name;
        appointments = new ArrayList<Appointment>();
    }

    /**
     * Gets the name of this contact
     *
     * @return the name of this contact
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an appointment to this contact's agenda. There may be no overlap
     * between appointments.
     *
     * @param a The appointment to add
     * @return True when the operation succeeded, False when it failed.
     */
    boolean addAppointment(Appointment a) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes an appointment from this contact's agenda.
     *
     * @param a The appointment to remove.
     */
    void removeAppointment(Appointment a) {
        throw new UnsupportedOperationException();
    }

    /**
     * Iterates through the appointments in this contact's agenda.
     *
     * @return An iteration of the appointments
     */
    public Iterator<Appointment> appointments() {
        throw new UnsupportedOperationException();
    }
}
