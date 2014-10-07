/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Eric de Regter
 */
public class PeriodUnitTest {

    /**
     * creation of a period with begin time bt and end time et
     *
     * @param bt begin time bt must be earlier than end time et
     * @param et
     */
    //public Period(ITime bt, ITime et)
    @Test
    public void testCreationPeriod()
    {
        IPeriod period = null;

        //Normale waardes worden getest
        ITime bt = new Time(1993, 8, 20, 23, 12);
        ITime et = new Time(2014, 8, 20, 23, 12);

        try
        {
            period = new Period(bt, et);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Fout bij het aanmaken van period");
        }

        period = null;
        bt = null;
        et = null;

        //bt is later dan et
        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(1993, 8, 20, 23, 12);

        try
        {
            period = new Period(bt, et);
            fail("Period moet niet aangemaakt kunnen worden");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        period = null;
        bt = null;
        et = null;

        //bt en et zijn gelijk
        et = new Time(2014, 8, 20, 23, 12);
        bt = new Time(2014, 8, 20, 23, 12);
        

        try
        {
            period = new Period(bt, et);
            fail("BeginTime en EndTime moeten niet gelijk kunnen zijn");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        period = null;
        bt = null;
        et = null;

        //één minuut verschill tussen de tijdstippen
        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(2014, 8, 20, 23, 13);

        try
        {
            period = new Period(bt, et);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Fout bij het aanmaken van period");
        }

    }
    /**
     *
     * @return the length of this period expressed in minutes (always positive)
     */
    /**
     * beginTime will be the new begin time of this period
     *
     * @param beginTime must be earlier than the current end time of this period
     */
    /**
     * endTime will be the new end time of this period
     *
     * @param beginTime must be later than the current begin time of this period
     */
    /**
     * the begin and end time of this period will be moved up both with
     * [minutes] minutes
     *
     * @param minutes (a negative value is allowed)
     */
    /**
     * the end time of this period will be moved up with [minutes] minutes
     *
     * @param minutes minutes + length of this period must be positive
     */
    /**
     *
     * @param period
     * @return true if all moments within this period are included within
     * [period], otherwise false
     */
    /**
     *
     * @param period
     * @return if this period and [period] are consecutive or possess a common
     * intersection, then the smallest period p will be returned, for which this
     * period and [period] are part of p, otherwise null will be returned
     */
    /**
     *
     * @param period
     * @return the largest period which is part of this period and [period] will
     * be returned; if the intersection is empty null will be returned
     */

}
