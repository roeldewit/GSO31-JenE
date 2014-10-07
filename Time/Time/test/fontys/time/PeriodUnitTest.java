/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.Assert;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runners.model.MultipleFailureException;

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
            fail("Fail bij het aanmaken van period");
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
            fail("Fail bij het aanmaken van period");
        }

    }

    /**
     *
     * @return the length of this period expressed in minutes (always positive)
     */
    @Test
    public void TestLength()
    {
        IPeriod period;
        ITime bt;
        ITime et;

        //verschil is 10 min
        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(2014, 8, 20, 23, 22);
        period = new Period(bt, et);

        Assert.assertEquals(10, period.length());

        bt = null;
        et = null;
        period = null;

        //verschil is een jaar (525.600) min
        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(2015, 8, 20, 23, 12);
        period = new Period(bt, et);
        Assert.assertEquals(525600, period.length());

    }

    /**
     * beginTime will be the new begin time of this period
     *
     * @param beginTime must be earlier than the current end time of this period
     */
    @Test
    public void testNewBeginTime()
    {
        //nieuwe begintijd is later dan begintijd
        IPeriod period;
        ITime bt;
        ITime et;

        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(2015, 8, 20, 23, 12);

        period = new Period(bt, et);
        try
        {
            period.setBeginTime(new Time(2014, 9, 20, 23, 12));
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("beginTijd is niet eerder dan eindtijd");
        }

        //nieuwe begintijd is eerder dan begintijd
        try
        {
            period.setBeginTime(new Time(2013, 8, 20, 23, 12));
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Fail bij setBeginTime");
        }

        //nieuwe begintijd is later dan eindtijd
        try
        {
            period.setBeginTime(new Time(2015, 9, 20, 23, 12));
            fail("begintijd mag niet later zijn dan eindtijd");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        //nieuwe begintijd is hetzelfde als de eindtijd
        try
        {
            period.setBeginTime(new Time(2015, 8, 20, 23, 12));
            fail("begintijd mag niet gelijk zijn aan de eindtijd");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

    }

    /**
     * endTime will be the new end time of this period
     *
     * @param beginTime must be later than the current begin time of this period
     */
    @Test
    public void TestNewEndTime()
    {
        IPeriod period;
        ITime bt;
        ITime et;

        bt = new Time(2012, 1, 29, 23, 9);
        et = new Time(2015, 8, 20, 23, 12);

        period = new Period(bt, et);

        //nieuwe eindtijd is eerder dan eindtijd
        try
        {
            period.setEndTime(new Time(2013, 3, 30, 23, 0));
        }
        catch (IllegalArgumentException ex)
        {
            fail("fail bij TestNewEndTime, nieuwe et eerder dan oude et");
        }

        //nieuwe eindtijd is later dan eindtijd
        try
        {
            period.setEndTime(new Time(2014, 3, 20, 12, 32));
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("fail bij TestNewEndTime, nieuwe et later dan oude et");
        }

        //nieuwe eindtijd is eerder dan begintijd
        try
        {
            period.setEndTime(new Time(2011, 4, 30, 12, 29));
            fail("Nieuwe eindtijd mag niet eerder zijn dan beginttijd");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        //nieuwe eindtijd tegelijkertijd met begintijd
        try
        {
            period.setEndTime(new Time(2012, 1, 29, 23, 9));
            fail("Begin en eindtijd mogen niet gelijk zijn aan elkaar");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }
    }

    /**
     * the begin and end time of this period will be moved up both with
     * [minutes] minutes
     *
     * @param minutes (a negative value is allowed)
     */
    @Test
    public void TestMovePeriod()
    {
        IPeriod period;
        IPeriod period2;
        ITime bt;
        ITime et;

        bt = new Time(2012, 6, 23, 21, 2);
        et = new Time(2014, 3, 23, 12, 9);

        period = new Period(bt, et);
        period2 = period;

        //verplaats met + aantal min
        try
        {
            period.move(300);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Kan period niet verplaatsen met + aantal min");
        }

        //verplaats met - aantal min
        try
        {
            period.move(-300);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Kan period niet verplaatsen met - aantal min");
        }

        //period en period2 moeten gelijk zijn aan elkaar
        if (period.length() == period2.length())
        {
            Assert.assertTrue(true);
        }
        else
        {
            fail("period en period2 zijn niet gelijk aan elkaar");
        }

    }

    /**
     * the end time of this period will be moved up with [minutes] minutes
     *
     * @param minutes minutes + length of this period must be positive
     */
    @Test
    public void TestChangeLengthWith()
    {
        IPeriod period;
        ITime bt;
        ITime et;

        bt = new Time(2014, 12, 2, 12, 9);
        et = new Time(2014, 12, 2, 12, 42);

        period = new Period(bt, et);

        //verander lengte met + 19 min
        try
        {
            period.changeLengthWith(19);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("Fail bij changelengthwith met + aantal min");
        }

        //verander lengte met - 525.600 min
        try
        {
            period.changeLengthWith(-525600);
            fail("period mag geen negatieve lengte hebben");
        }
        catch (IllegalArgumentException ex)
        {
            Assert.assertTrue(true);
        }

        //lengte blijft gelijk
        try
        {
            period.changeLengthWith(0);
            Assert.assertNotNull(period);
        }
        catch (IllegalArgumentException ex)
        {
            fail("lengte mag met 0 verandert worden");
        }

    }

    /**
     *
     * @param period
     * @return true if all moments within this period are included within
     * [period], otherwise false
     */
    @Test
    public void TestIsPartOf()
    {
        //eerste period
        IPeriod period;
        ITime bt;
        ITime et;

        bt = new Time(2012, 1, 13, 20, 3);
        et = new Time(2012, 7, 27, 11, 0);

        //tweede period
        IPeriod period2;
        ITime bt2;
        ITime et2;

        bt2 = new Time(1992, 8, 4, 10, 3);
        et2 = new Time(2014, 10, 7, 21, 20);

        period = new Period(bt, et);
        period2 = new Period(bt2, et2);

        //period ligt compleet in period2
        Assert.assertTrue("IsPartOf klopt niet", period.isPartOf(period2));
        Assert.assertFalse("IsPartOf klopt niet", period2.isPartOf(period));
    }

    /**
     *
     * @param period
     * @return if this period and [period] are consecutive or possess a common
     * intersection, then the smallest period p will be returned, for which this
     * period and [period] are part of p, otherwise null will be returned
     */
    @Test
    public void TestUnionWith()
    {
        IPeriod period1;
        ITime bt1;
        ITime et1;

        //aaneensluitend
        bt1 = new Time(1990, 2, 19, 3, 1);
        et1 = new Time(2020, 2, 19, 3, 1);

        IPeriod period2;
        ITime bt2;
        ITime et2;

        bt2 = new Time(2020, 2, 19, 3, 1);
        et2 = new Time(2021, 12, 29, 0, 1);

        IPeriod resultPeriod;
        IPeriod expectedPeriod;

        period1 = new Period(bt1, et1);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.unionWith(period2);

        Assert.assertNotNull(resultPeriod);

        Assert.assertTrue("Resultperiod moet groter zijn", resultPeriod.length() > period1.length());
        Assert.assertTrue("Resultperiod moet groter zijn", resultPeriod.length() > period2.length());

        //niet aaneensluitend period1 blijft gelijk
        resultPeriod = null;
        period2 = null;
        bt2 = null;
        et2 = null;

        bt2 = new Time(2021, 2, 19, 3, 1);
        et2 = new Time(2030, 1, 12, 3, 1);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.unionWith(period2);

        Assert.assertNull("Resultperiod moet null zijn", resultPeriod);

        //period2 is compleet binnen period1
        resultPeriod = null;
        period2 = null;
        bt2 = null;
        et2 = null;

        bt2 = new Time(2002, 9, 15, 12, 50);
        et2 = new Time(2012, 9, 15, 12, 50);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.unionWith(period2);

        Assert.assertNotNull("ResultPeriod moet niet null zijn", resultPeriod);
        Assert.assertEquals("De lengte van Resultperiod moet even groot zijn als de lengte van period1", resultPeriod.length(), period1.length());
        Assert.assertTrue("Resultperiod moet groter zijn", resultPeriod.length() > period2.length());

        //period1 is compleet binnen period2
        resultPeriod = period2.unionWith(period1);

        Assert.assertNotNull(resultPeriod);
        Assert.assertEquals("De lengte van resultPeriod moet gelijk zijn aan de lengte van period 1", resultPeriod.length(), period1.length());
        Assert.assertTrue("Resultperiod moet groter zijn", resultPeriod.length() > period2.length());

        //period1 en period2 overlappen elkaar, period 1 ligt voor period2
        resultPeriod = null;
        period2 = null;
        bt2 = null;
        et2 = null;

        bt2 = new Time(2015, 9, 15, 12, 50);
        et2 = new Time(2100, 9, 15, 12, 50);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.unionWith(period2);
        expectedPeriod = new Period(bt1, et2);

        Assert.assertNotNull("Resultperiod moet niet null zijn", resultPeriod);
        Assert.assertEquals("De lengte van resultPeriod moet gelijk zijn aan de lengte van expected", resultPeriod.length(), expectedPeriod.length());
        Assert.assertTrue("De lengte van resultPeriod moet groter zijn dan period1", resultPeriod.length() > period1.length());
        Assert.assertTrue("De lengte van resultPeriod moet groeter zijn dan period2", resultPeriod.length() > period2.length());

        //period1 en period2 overlappen elkaar, period1 ligt achter period2
        resultPeriod = null;
        period2 = null;
        bt2 = null;
        et2 = null;

        bt2 = new Time(1800, 9, 15, 12, 50);
        et2 = new Time(2015, 9, 15, 12, 50);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.unionWith(period2);
        expectedPeriod = new Period(bt2, et1);

        Assert.assertNotNull(resultPeriod);
        Assert.assertEquals("De lengte van resultPeriod moet gelijk zijn aan de lengte van expected", resultPeriod.length(), expectedPeriod.length());
        Assert.assertTrue("De lengte van resultPeriod moet groter zijn dan period1", resultPeriod.length() > period1.length());
        Assert.assertTrue("De lengte van resultPeriod moet groeter zijn dan period2", resultPeriod.length() > period2.length());
    }

    /**
     *
     * @param period
     * @return the largest period which is part of this period and [period] will
     * be returned; if the intersection is empty null will be returned
     */
    @Test
    public void TestIntersectionWith()
    {
        //period1 ligt voor period2
        
        //period1
        IPeriod period1;
        ITime bt1;
        ITime et1;
        
        bt1 = new Time(1990, 2, 19, 3, 1);
        et1 = new Time(2020, 2, 19, 3, 1);

        //period2
        IPeriod period2;
        ITime bt2;
        ITime et2;

        bt2 = new Time(2015, 2, 19, 3, 1);
        et2 = new Time(2021, 12, 29, 0, 1);

        IPeriod resultPeriod;
        IPeriod expectedPeriod;

        period1 = new Period(bt1, et1);
        period2 = new Period(bt2, et2);

        resultPeriod = period1.intersectionWith(period2);
        expectedPeriod = new Period(bt2, et1);
        
        Assert.assertNotNull("ResultPeriod moet niet null zijn", resultPeriod);
        Assert.assertEquals("ExpectedPeriod en ResultPeriod moeten even lang zijn", expectedPeriod.length(), resultPeriod.length());
        Assert.assertTrue("Lengte van ResultPeriod moet kleiner zijn dan de lengte van period1", resultPeriod.length() < period1.length());
        Assert.assertTrue("Lengte van ResultPeriod moet kleiner zijn dan de lengte van period2", resultPeriod.length() < period2.length());
        
        //period1 ligt achter period2
        expectedPeriod = null;
        period1 = null;
        bt1 = null;
        et1 = null;
        
        bt1 = new Time(2020, 12, 2, 3, 48);
        et1 = new Time(2050, 12, 3, 1, 49);
        period1 = new Period(bt1, et1);
        
        resultPeriod = period1.intersectionWith(period2);
        expectedPeriod = new Period(bt1, et2);
       
        Assert.assertNotNull("ResultPeriod moet niet null zijn", resultPeriod);
        Assert.assertEquals("ExpectedPeriod en ResultPeriod moeten even lang zijn", expectedPeriod.length(), resultPeriod.length());
        Assert.assertTrue(resultPeriod.length() < period1.length());
        Assert.assertTrue(resultPeriod.length() < period2.length());
        
        //period2 ligt helemaal in period1
        period2 = null;
        bt2 = null;
        et2 = null;
        
        bt2 = new Time(2022, 2, 30, 12, 11);
        et2 = new Time(2040, 12, 3, 1, 49);
        period2 = new Period(bt2, et2);
        
        resultPeriod = period1.intersectionWith(period2);
        
        Assert.assertNotNull("ResultPeriod moet niet gelijk zijn aan null", resultPeriod);
        
        Assert.assertEquals("ResultPeriod moet even lang zijn als period2", resultPeriod.length(), period2.length());
        Assert.assertTrue("ResultPeriod moet minder lang zijn dan period1", resultPeriod.length() < period1.length());
        
        //period1 ligt helemaal in period2
        period2 = null;
        bt2 = null;
        et2 = null;
        
        bt2 = new Time(1800, 12, 3, 1, 49);
        et2 = new Time(2100, 12, 3, 1, 49);
        period2 = new Period(bt2, et2);
        
        resultPeriod = period1.intersectionWith(period2);        
        
        Assert.assertNotNull(resultPeriod);
        
        Assert.assertEquals("ResultPeriod moet even lang zijn als period1", resultPeriod.length(), period1.length());
        Assert.assertTrue("ResultPeriod moet minder lang zijn dan period1", resultPeriod.length() < period2.length());
        
        //period1 en period2 liggen achtereenvolgend
        period2 = null;
        bt2 = null;
        et2 = null;
        
        bt2 = new Time(2050, 12, 3, 1, 49);
        et2 = new Time(2100, 12, 3, 1, 49);
        period2 = new Period(bt2, et2);
        
        resultPeriod = period1.intersectionWith(period2);  
        
        Assert.assertNull("ResultPeriod moet null zijn", resultPeriod);
        
    }
}
