/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AEX;

import GUI.FXMLAEXBannerController;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class BannerController {

    private Timer timer;
    private IEffectenbeurs beurs;

    public BannerController(FXMLAEXBannerController application) {
        timer = new Timer("UpdateBanner", true);
        beurs = new MockEffectenbeurs();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                String string = "";
                for (IFonds f : beurs.getKoersen()) {
                    string = string.concat(f.getNaam() + ": " + f.getKoers() + " - ");
                }
                application.setKoersen(string);
            }
        }, 0, 1000);
    }
}
