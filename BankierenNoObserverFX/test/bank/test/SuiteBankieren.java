/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bank.test;

import bank.bankieren.BalieTest;
import bank.bankieren.BankTest;
import bank.bankieren.RekeningTest;
import bank.bankieren.SessieTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The JUnit test suite that contains tests from the test package bank.bankieren.
 *
 * @author Astrid
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ BalieTest.class, BankTest.class, RekeningTest.class, SessieTest.class })
public class SuiteBankieren {
    
}
