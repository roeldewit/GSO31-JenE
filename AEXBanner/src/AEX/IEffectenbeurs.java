/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aex;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public interface IEffectenbeurs extends Remote {

    public IFonds[] getKoersen() throws RemoteException;
}