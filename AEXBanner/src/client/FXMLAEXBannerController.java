/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

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
    private TextField tf_IPAddress;

    @FXML
    private TextField tf_portNumber;

    @FXML
    private Button button_Connect;

    @FXML
    private Slider speedSlider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label1.setText("");
        label2.setText("");
        label1.relocate(0, label1.getLayoutY());
        label2.relocate(label1.getLayoutX() + label1.getWidth(), label1.getLayoutY());

    }

    public void connect(Event evt) {
        FXMLAEXBannerController controller = this;
        Thread t;
        t = new Thread(() -> {
            int portNumber = -1;
            try {
                portNumber = Integer.parseInt(tf_portNumber.getText());
                if (portNumber < 0) {
                    throw new NumberFormatException("Number negative");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Not a number! " + ex.getMessage());
            }
            BannerController bannerController = new BannerController(controller, tf_IPAddress.getText(), portNumber);
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
        });
        t.start();
        button_Connect.setDisable(true);
    }

    public void setKoersen(String koersen) {
        Platform.runLater(() -> {
            label1.setText(koersen);
            label2.setText(koersen);
        });
    }

    private double getSpeed() {
        return speedSlider.getValue() / 33.0 + 0.1;
    }
}
