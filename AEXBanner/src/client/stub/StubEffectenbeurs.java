/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.stub;

import AEX.IEffectenbeurs;
import AEX.IFonds;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Astrid
 */
public class StubEffectenbeurs extends UnicastRemoteObject implements IEffectenbeurs {

    private IFonds[] fondsen;
    private final IFonds[] fondsen1;
    private final IFonds[] fondsen2;
    private BasicPublisher publisher;
    
    /** My random number generator */
    static private final Random RNG = new Random();  
    
    public StubEffectenbeurs() throws RemoteException {
        fondsen1 = new StubFonds[] {
            new StubFonds("Aegon", 7.022),
            new StubFonds("Ahold", 17.840),
            new StubFonds("AkzoNobel", 68.610),
            new StubFonds("Altice", 97.320),
            new StubFonds("Arcelor Mittal", 9.711),
            new StubFonds("ASML", 99.400),
            new StubFonds("Boskalis", 41.780),
            new StubFonds("Delta Lloyd", 16.790),
            new StubFonds("DSM", 50.400),
            new StubFonds("Fugro", 24.135), 
            new StubFonds("Gemalto", 69.000),
            new StubFonds("Heineken", 73.760),
            new StubFonds("ING Groep", 13.450),
            new StubFonds("Klepierre", 44.880),
            new StubFonds("KPN", 3.066),
            new StubFonds("OCI", 32.670),
            new StubFonds("Philips", 26.840),
            new StubFonds("Randstad", 53.310),
            new StubFonds("RD Shell A", 28.235),
            new StubFonds("Reed Elsevier", 22.675),
            new StubFonds("SBM Offshore", 10.590),
            new StubFonds("TNT Express", 5.470),
            new StubFonds("Unibail Rodamco", 258.600),
            new StubFonds("Unilever", 40.085),
            new StubFonds("Wolters Kluwer", 29.795)
        };
        fondsen2 = new StubFonds[] {
            new StubFonds("Aegon", 7.122),
            new StubFonds("Ahold", 17.940),
            new StubFonds("AkzoNobel", 68.710),
            new StubFonds("Altice", 97.420),
            new StubFonds("Arcelor Mittal", 9.811),
            new StubFonds("ASML", 99.500),
            new StubFonds("Boskalis", 41.880),
            new StubFonds("Delta Lloyd", 16.890),
            new StubFonds("DSM", 50.500),
            new StubFonds("Fugro", 24.235), 
            new StubFonds("Gemalto", 69.100),
            new StubFonds("Heineken", 73.860),
            new StubFonds("ING Groep", 13.550),
            new StubFonds("Klepierre", 44.980),
            new StubFonds("KPN", 3.166),
            new StubFonds("OCI", 32.770),
            new StubFonds("Philips", 26.940),
            new StubFonds("Randstad", 53.410),
            new StubFonds("RD Shell A", 28.335),
            new StubFonds("Reed Elsevier", 22.775),
            new StubFonds("SBM Offshore", 10.690),
            new StubFonds("TNT Express", 5.570),
            new StubFonds("Unibail Rodamco", 258.700),
            new StubFonds("Unilever", 40.185),
            new StubFonds("Wolters Kluwer", 29.895)
        };
        fondsen = fondsen1;

        // Show content of stock arrays for test purposes
        System.out.println("----------------------------------");
        System.out.println("Stub - Stocks: values first array");
        for (IFonds f : fondsen1) {
            System.out.println("Stock name: "+f.getNaam()+", stock price: "+f.getKoers());
        }
        System.out.println("----------------------------------");
        System.out.println("Stub - Stocks: values second array");
        for (IFonds f : fondsen2) {
            System.out.println("Stock name: "+f.getNaam()+", stock price: "+f.getKoers());
        }
        System.out.println("----------------------------------");
        System.out.println("Stub - Stocks: first array");
        publisher = new BasicPublisher(new String[]{"koersen"});

        Timer myTimer = new Timer(true);
        myTimer.schedule(new TimerTask() {
                @Override public void run() { 
                    /** Fluctuating stocks (mock) */
                    //fluctuate();
                    //System.out.println("Stub - Stocks: random fluctuation");
                    /** Known stocks, switch between two arrays (stub) */
                    if (Arrays.equals(fondsen, fondsen1)) {
                        fondsen = fondsen2;
                        System.out.println("Stub - Stocks: switch to second array");
                    }
                    else {
                        fondsen = fondsen1;
                        System.out.println("Stub - Stocks: switch to first array");
                    }
                    publisher.inform(this, "koersen", null, fondsen);                    
                }
            }, 0, 3500);        
    }
    
    @Override
    public IFonds[] getKoersen() throws RemoteException {
        return fondsen;
    }

    /** Fluctuate the prices of all stocks */
    private void fluctuate() {
        for (IFonds f : fondsen) {
            StubFonds sf = (StubFonds) f;
            if (RNG.nextInt(25) == 0)
                sf.fluctuate(RNG);
        }   
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
