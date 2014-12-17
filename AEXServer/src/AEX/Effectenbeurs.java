/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AEX;

import fontys.observer.RemotePropertyListener;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class Effectenbeurs implements IEffectenbeurs, Serializable {

    public Effectenbeurs() {

    }

    @Override
    public IFonds[] getKoersen() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListener(RemotePropertyListener listener, String property) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
