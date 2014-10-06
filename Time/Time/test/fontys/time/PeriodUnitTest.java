/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import org.junit.Assert;
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
    @Test (expected = IllegalArgumentException.class)
    public void testCreationPeriod()
    {
        Period period = null;
        ITime bt = new Time(1993, 8, 20, 23, 12);
        ITime et = new Time(2014, 8, 20, 23, 12);
        period = new Period(bt, et);
        Assert.assertNotNull(period);
        
        period = null;
        bt = null;
        et = null;
        
        bt = new Time(2014, 8, 20, 23, 12);
        et = new Time(1993, 8, 20, 23, 12);
        period = new Period(bt, et);
        Assert.assertNotNull(period);
        
    }

    
}
