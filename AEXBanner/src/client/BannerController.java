/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import AEX.IFonds;
import AEX.IEffectenbeurs;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class BannerController {

    //Binding name for Effectenbeurs
    private static final String BINDING_NAME = "EffectenBeurs";

    //References to registry and effectenbeurs
    private Registry registry = null;
    private IEffectenbeurs beurs = null;

    private Timer timer;
    private FXMLAEXBannerController controller;

    public BannerController(FXMLAEXBannerController controller, String ipAddress, int portNumber) {
        this.controller = controller;

        System.out.println("Attempting to locate remote registry");
        //Locate registry
        try {
            registry = LocateRegistry.getRegistry(ipAddress, portNumber);
            System.out.println("Registry located");
        } catch (RemoteException ex) {
            System.out.println("Cannot locate registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
        }
        
        //Bind effectenbeurs
        if(registry != null) {
            try{
                beurs = (IEffectenbeurs) registry.lookup(BINDING_NAME);
            } catch(RemoteException ex){
                System.out.println("Cannot bind Effectenbeurs");
                System.out.println("RemoteException: " + ex.getMessage());
                beurs = null;
            } catch(NotBoundException ex){
                System.out.println("Cannot bind Effectenbeurs");
                System.out.println("NotBoundException: " + ex.getMessage());
                beurs = null;
            }
        }
        
        if (beurs != null) {
            timer = new Timer("UpdateBanner", true);
            timer.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    try {
                        String string = "";
                        for (IFonds f : beurs.getKoersen()) {
                            string = string.concat(f.getNaam() + ": " + f.getKoers() + " - ");
                        }
                        controller.setKoersen(string);
                    } catch (RemoteException ex) {
                        System.out.println("Cannot getKoersen from EffectenBeurs");
                        System.out.println("RemoteException: " + ex.getMessage());
                    }
                }
            }, 0, 1000);
        }
    }
}
