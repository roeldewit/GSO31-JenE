/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import aex.IEffectenbeurs;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class AEXServer {

    //Set port number
    private static final int PORT_NUMBER = 1099;

    //Set binding name
    private static final String BINDING_NAME = "EffectenBeurs";

    //References to registry and effectenbeurs
    private Registry registry = null;
    private IEffectenbeurs beurs = null;

    public AEXServer() {
        //Print port number
        System.out.println("Server: Port number " + PORT_NUMBER);

        //Create beurs
        try {
            beurs = new MockEffectenbeurs();
            System.out.println("EffectenBeurs created.");
        } catch (RemoteException ex) {
            System.out.println("Cannot create effectenbeurs.");
            System.out.println("RemoteExcemption: " + ex.getMessage());
            beurs = null;
        }

        //Create registry at port number
        try {
            registry = LocateRegistry.createRegistry(PORT_NUMBER);
            System.out.println("Registry created on port number: " + PORT_NUMBER);
        } catch (RemoteException ex) {
            System.out.println("Cannot create registry");
            System.out.println("RemoteException: " + ex.getMessage());
            registry = null;
        }

        // Bind student administration using registry
        try {
            registry.rebind(BINDING_NAME, beurs);
        } catch (RemoteException ex) {
            System.out.println("Cannot bind student administration");
            System.out.println("RemoteException: " + ex.getMessage());
        }
    }

    // Print IP addresses and network interfaces
    private static void printIPAddresses() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            System.out.println("Server: IP Address: " + localhost.getHostAddress());
            // Just in case this host has multiple IP addresses....
            InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
            if (allMyIps != null && allMyIps.length > 1) {
                System.out.println("Server: Full list of IP addresses:");
                for (InetAddress allMyIp : allMyIps) {
                    System.out.println("    " + allMyIp);
                }
            }
        } catch (UnknownHostException ex) {
            System.out.println("Server: Cannot get IP address of local host");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }

        try {
            System.out.println("Server: Full list of network interfaces:");
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                System.out.println("    " + intf.getName() + " " + intf.getDisplayName());
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    System.out.println("        " + enumIpAddr.nextElement().toString());
                }
            }
        } catch (SocketException ex) {
            System.out.println("Server: Cannot retrieve network interface list");
            System.out.println("Server: UnknownHostException: " + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Welcome message
        System.out.println("SERVER USING REGISTRY");
        
        //Print IP addresses and network interfaces
        printIPAddresses();
        
        //Create server
        AEXServer server = new AEXServer();
    }

}
