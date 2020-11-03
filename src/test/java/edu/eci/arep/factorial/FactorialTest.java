package edu.eci.arep.factorial;

import edu.eci.arep.factorial.util.Factorial;
import org.junit.Before;
import org.junit.Test;


/**
 * The type Factorial test.
 */
public class FactorialTest {
    /**
     * The Calculator.
     */
    Factorial calculator;

    /**
     * Set up.
     */
    @Before
    public void setUp(){
        calculator = new Factorial();
    }

    /**
     * Should calculate coin change.
     */
    @Test
    public void shouldCalculateCoinChange(){
        System.out.println(calculator.factorial(25000));
    }
}
