/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import AEX.IFonds;
import AEX.IEffectenbeurs;
import fontys.observer.RemotePropertyListener;
import java.beans.PropertyChangeEvent;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class BannerController implements RemotePropertyListener {

    //Binding name for Effectenbeurs
    private static final String BINDING_NAME = "EffectenBeurs";

    //References to registry and effectenbeurs
    private Registry registry = null;
    private IEffectenbeurs beurs = null;

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
        if (registry != null) {
            try {
                beurs = (IEffectenbeurs) registry.lookup(BINDING_NAME);
            } catch (RemoteException ex) {
                System.out.println("Cannot bind Effectenbeurs");
                System.out.println("RemoteException: " + ex.getMessage());
                beurs = null;
            } catch (NotBoundException ex) {
                System.out.println("Cannot bind Effectenbeurs");
                System.out.println("NotBoundException: " + ex.getMessage());
                beurs = null;
            }
        }

        if (beurs != null) {
            try {
                UnicastRemoteObject.exportObject(this, 1100);
                beurs.addListener(this, "koersen");
            } catch (RemoteException ex) {
                Logger.getLogger(BannerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {
        String string = "";
        IFonds[] koersen = (IFonds[]) evt.getNewValue();
        for (IFonds f : koersen) {
            string = string.concat(f.getNaam() + ": " + f.getKoers() + " - ");
        }
        controller.setKoersen(string);
    }
}
