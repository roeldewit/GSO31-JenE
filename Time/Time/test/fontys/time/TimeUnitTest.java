/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.security.InvalidParameterException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joris Douven
 */
public class TimeUnitTest {

    public TimeUnitTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    /*
     * creation of a Time-object with year y, month m, day d, hours h and
     * minutes m; if the combination of y-m-d refers to a non-existing date a
     * correct value of this Time-object will be not guaranteed
     *
     * @param y
     * @param m 1≤m≤12
     * @param d 1≤d≤31
     * @param h 0≤h≤23
     * @param m 0≤m≤59
     */
    @Test (expected = IllegalArgumentException.class)
    public void testExistingDate() {
        Time time = null;
        //Test normal values
        time = new Time(2014, 06, 18, 20, 36);
        assertNotNull(time);
        time = null;
        //Test wrong month
        time = new Time(2014, 13, 18, 20, 36);
        assertNotNull(time);
        time = null;
        //Test wrong day
        time = new Time(2014, 06, 53, 20, 36);
        assertNotNull(time);
        time = null;
        //Test wrong hour
        time = new Time(2014, 06, 18, 25, 36);
        assertNotNull(time);
        time = null;
        //Test wrong minutes
        time = new Time(2014, 06, 18, 20, 66);
        assertNotNull(time);
        time = null;
    }
}
