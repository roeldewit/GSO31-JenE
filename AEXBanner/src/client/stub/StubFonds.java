/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.stub;

import AEX.IFonds;
import java.util.Random;

/**
 *
 * @author Astrid
 */
public class StubFonds implements IFonds {

    private final String naam;
    private double koers;
    
    public StubFonds(String naam, double koers) {
        this.naam = naam;
        this.koers = koers;
    }
    
    @Override
    public String getNaam() {
        return naam;
    }

    @Override
    public double getKoers() {
        return koers;
    }

    @Override
    public void setKoers(double koers) {
        this.koers = koers;
    }
    
    /**
     * Fluctuates the price of this stock
     * 
     * @param rng  A Random Number Generator (may not be null)
     */
    protected void fluctuate(Random rng) {
        koers += rng.nextGaussian() * koers / 1000.0;
    }
    
}
