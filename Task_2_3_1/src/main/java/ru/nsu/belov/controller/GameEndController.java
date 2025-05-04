package ru.nsu.belov.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ru.nsu.belov.Main;

import java.util.ArrayList;
import java.util.List;

public class GameEndController {
    @FXML private Label resultLabel;
    @FXML private HBox appleContainer;

    private final List<ImageView> apples = new ArrayList<>();
    private Timeline timeline;

    @FXML
    private void initialize() {
        Image appleImage = new Image(getClass().getResourceAsStream("/apple.png"));
        for (int i = 0; i < 11; i++) {
            ImageView apple = new ImageView(appleImage);
            apple.setFitHeight(50);
            apple.setPreserveRatio(true);
            apples.add(apple);
            appleContainer.getChildren().add(apple);
        }

        startAppleAnimation();
    }

    private void startAppleAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(30), event -> moveApples()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveApples() {
        double speed = 2;
        double sceneWidth = appleContainer.getScene().getWidth();

        for (ImageView apple : apples) {
            apple.setTranslateX(apple.getTranslateX() + speed);

            if (apple.localToScene(apple.getBoundsInLocal()).getMinX() > sceneWidth) {
                apple.setTranslateX(apple.getTranslateX() - sceneWidth - 100);
            }
        }
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