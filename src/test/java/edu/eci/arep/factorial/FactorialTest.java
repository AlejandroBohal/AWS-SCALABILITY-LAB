package edu.eci.arep.factorial;

import edu.eci.arep.factorial.util.Factorial;
import org.junit.Before;
import org.junit.Test;


public class FactorialTest {
    Factorial calculator;
    @Before
    public void setUp(){
        calculator = new Factorial();
    }
    @Test
    public void shouldCalculateCoinChange(){
        System.out.println(calculator.factorial(25000));
    }
}
