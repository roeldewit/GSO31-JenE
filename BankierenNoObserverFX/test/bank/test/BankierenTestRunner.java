/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The test runner for the SuiteBankieren.class suite
 *
 * @author Astrid
 */
public class BankierenTestRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	//Run the entire suite
	Result result = JUnitCore.runClasses(SuiteBankieren.class);

        //Report on all failures in the suite
        result.getFailures().stream().forEach((failure) -> {
            System.out.println("Status: " + failure.toString());
        });
        System.out.println("Aantal testen uitgevoerd: "+result.getRunCount());
        System.exit(0);
    }
    
}
