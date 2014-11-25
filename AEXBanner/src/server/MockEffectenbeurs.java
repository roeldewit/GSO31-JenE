/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import AEX.Fonds;
import AEX.IEffectenbeurs;
import AEX.IFonds;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import fontys.observer.RemotePublisher;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class MockEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs, RemotePublisher {

    private IFonds[] koersen;
    private Random rand = new Random();
    private final Timer timer = new Timer("UpdateKoersen", true);
    private final BasicPublisher publisher;

    public MockEffectenbeurs() throws RemoteException {
        publisher = new BasicPublisher(new String[]{"koersen"});
        koersen = new IFonds[]{new Fonds("KPN", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0),
            new Fonds("DSM", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0),
            new Fonds("Ahold", Math.round((rand.nextDouble() * 200.0) * 100.0) / 100.0)};
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                for (IFonds f : koersen) {
                    double delta = (((rand.nextDouble() * 10.0) - 5.0) * 100.0) / 100.0;
                    if (f.getKoers() + Math.round(delta) < 0) {
                        f.setKoers(Math.round(f.getKoers() - delta));
                    } else {
                        f.setKoers(Math.round(f.getKoers() + delta));
                    }
                }
                publisher.inform(this, "koersen", null, koersen);
            }
        }, 5000, 5000);
    }

    @Override
    public IFonds[] getKoersen() {
        return koersen;
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.addListener(listener, property);
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        publisher.removeListener(listener, property);
    }

}
