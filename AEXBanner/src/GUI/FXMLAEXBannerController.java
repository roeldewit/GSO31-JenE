/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AEX.IEffectenbeurs;
import AEX.IFonds;
import AEX.MockEffectenbeurs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class FXMLAEXBannerController implements Initializable {

    @FXML
    private Label label;

    private IEffectenbeurs beurs;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label.relocate(500, label.getLayoutY());
        beurs = new MockEffectenbeurs();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                String s = "";
                for (IFonds f : beurs.getKoersen()) {
                    s = s.concat(f.getNaam() + ": " + f.getKoers() + " - ");
                }
                setKoersen(s);
                if (label.getLayoutX() + label.getWidth() < 500) {
                    label.relocate(500, label.getLayoutY());
                }
                label.relocate(label.getLayoutX() - 2, label.getLayoutY());
            }
        }.start();
    }

    public void setKoersen(String koersen) {
        label.setText(koersen);
    }

}
