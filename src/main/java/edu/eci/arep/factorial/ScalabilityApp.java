package edu.eci.arep.factorial;

import edu.eci.arep.factorial.util.Factorial;

import static spark.Spark.*;

public class ScalabilityApp {
    public static void main(String[] args) {
        Factorial calculator = new Factorial();
        port(getPort());
        get("/factorial/:number", (req, res) -> {
            int number = Integer.parseInt(req.params(":number"));
            return calculator.factorial(number);
        });
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 8080;
    }
}

