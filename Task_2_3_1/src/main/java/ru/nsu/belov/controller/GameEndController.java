package ru.nsu.belov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ru.nsu.belov.Main;

public class GameEndController {
    @FXML private Label resultLabel;
    @FXML private HBox appleContainer;
    @FXML private HBox topAppleContainer;

    private AppleAnimationController bottomAppleAnimation;
    private AppleAnimationController topAppleAnimation;

    @FXML
    private void initialize() {
        bottomAppleAnimation = new AppleAnimationController(appleContainer);
        topAppleAnimation = new AppleAnimationController(topAppleContainer);
    }

    public void setResult(String result) {
        resultLabel.setText(result);
    }

    @FXML
    private void restartGame() {
        try {
            Main.startGameWithConfig(
                Main.gameService.getModel().getRows(),
                Main.gameService.getModel().getCols(),
                Main.gameService.getModel().getFood().size(),
                Main.gameService.getModel().getWinLength(),
                150
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToSettings() {
        try {
            Main.showConfigScene();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}