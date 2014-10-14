/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AEX;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class MockEffectenbeurs implements IEffectenbeurs {

    private IFonds[] koersen;
    Random rand = new Random();
    Timer timer = new Timer("UpdateKoersen", true);

    public MockEffectenbeurs() {
        koersen = new IFonds[]{new Fonds("KPN", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0),
            new Fonds("DSM", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0),
            new Fonds("Ahold", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0)};
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                for (IFonds f : koersen) {
                    f.setKoers(Math.round((f.getKoers() + ((rand.nextDouble() * 10.0) - 5.0)) * 100.0) / 100.0);
                }
            }
        }, 10000, 10000);
    }

    @Override
    public IFonds[] getKoersen() {
        return koersen;
    }

}
