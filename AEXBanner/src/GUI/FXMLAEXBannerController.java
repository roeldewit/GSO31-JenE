/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import AEX.BannerController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

/**
 *
 * @author Joris Douven, Eric de Regter
 */
public class FXMLAEXBannerController implements Initializable {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Slider speedSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label1.relocate(0, label1.getLayoutY());
        BannerController bannerController = new BannerController(this);

        new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (label1.getLayoutX() < label2.getLayoutX()) {
                    if (label1.getLayoutX() + label1.getWidth() < 0) {
                        label1.relocate(label2.getLayoutX() + label2.getWidth(), label2.getLayoutY());
                    } else {
                        label1.relocate(label1.getLayoutX() - getSpeed(), label1.getLayoutY());
                        label2.relocate(label1.getLayoutX() + label1.getWidth(), label1.getLayoutY());
                    }
                } else {
                    if (label2.getLayoutX() + label2.getWidth() < 0) {
                        label2.relocate(label1.getLayoutX() + label1.getWidth(), label1.getLayoutY());
                    } else {
                        label2.relocate(label2.getLayoutX() - getSpeed(), label2.getLayoutY());
                        label1.relocate(label2.getLayoutX() + label2.getWidth(), label2.getLayoutY());
                    }
                }
            }
        }.start();
    }

    public void setKoersen(String koersen) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label1.setText(koersen);
                label2.setText(koersen);
            }
        });
    }

    private double getSpeed() {
        return speedSlider.getValue() / 33.0 + 0.1;
    }

}
