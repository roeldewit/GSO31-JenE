/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.*;

/**
 *
 * @author HP user
 */
public class Time implements ITime {

    private Calendar calendar;

    /**
     * creation of a Time-object with year y, month m, day d, hours h and
     * minutes m; if the combination of y-m-d refers to a non-existing date a
     * correct value of this Time-object will be not guaranteed
     *
     * @param y
     * @param m 1≤m≤12
     * @param d 1≤d≤31
     * @param h 0≤h≤23
     * @param min 0≤m≤59
     */
    public Time(int y, int m, int d, int h, int min) {
        calendar.set(GregorianCalendar.YEAR, y);
        if (m <= 12 && m >= 1) {
            calendar.set(GregorianCalendar.MONTH, m);
        } else {
            throw new IllegalArgumentException("m not 1≤m≤12");
        }
        if (d <= 31 && d >= 1) {
            calendar.set(GregorianCalendar.DATE, d);
        } else {
            throw new IllegalArgumentException("d not 1≤d≤31");
        }
        if (h <= 23 && h >= 0) {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, h);
        } else {
            throw new IllegalArgumentException("h not 0≤h≤23");
        }
        if (min <= 59 && min >= 0) {
            calendar.set(GregorianCalendar.MINUTE, min);
        } else {
            throw new IllegalArgumentException("min not 0≤h≤59");
        }
    }

    /**
     *
     * @return the concerning year of this time
     */
    @Override
    public int getYear() {
        return calendar.get(GregorianCalendar.YEAR);
    }

    /**
     *
     * @return the number of the month of this time (1..12)
     */
    @Override
    public int getMonth() {
        return calendar.get(GregorianCalendar.MONTH);
    }

    /**
     *
     * @return the number of the day in the month of this time (1..31)
     */
    @Override
    public int getDay() {
        return calendar.get(GregorianCalendar.DATE);
    }

    /**
     *
     * @return the number of hours in a day of this time (0..23)
     */
    @Override
    public int getHours() {
        return calendar.get(GregorianCalendar.HOUR_OF_DAY);
    }

    /**
     *
     * @return the number of minutes in a hour of this time (0..59)
     */
    @Override
    public int getMinutes() {
        return calendar.get(GregorianCalendar.MINUTE);
    }

    /**
     *
     * @return the concerning day in the week of this time
     */
    @Override
    public DayInWeek getDayInWeek() {
        return DayInWeek.values()[calendar.get(GregorianCalendar.DAY_OF_WEEK)];
    }

    /**
     *
     * @param minutes (a negative value is allowed)
     * @return this time plus minutes
     */
    @Override
    public ITime plus(int minutes) {
        Calendar c = (Calendar) calendar.clone();
        c.add(GregorianCalendar.MINUTE, minutes);
        return new Time(c.get(GregorianCalendar.YEAR),
                c.get(GregorianCalendar.MONTH),
                c.get(GregorianCalendar.DATE),
                c.get(GregorianCalendar.HOUR_OF_DAY),
                c.get(GregorianCalendar.MINUTE));
    }

    /**
     *
     * @param time
     * @return the difference between this time and [time] expressed in minutes
     */
    @Override
    public int difference(ITime time) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int compareTo(ITime o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
