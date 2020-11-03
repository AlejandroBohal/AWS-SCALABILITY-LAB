package edu.eci.arep.factorial.util;
import java.math.BigInteger;

public class Factorial {
    public Factorial(){
    }
    public  BigInteger factorial(int n){
        BigInteger fact= new BigInteger("1");
        int i;
        if (n==0)
            fact= new BigInteger("1");
        else
            for(i=1;i<=n;i++)
                fact=fact.multiply(new BigInteger(String.valueOf(i)));
        return fact;
    }

}
