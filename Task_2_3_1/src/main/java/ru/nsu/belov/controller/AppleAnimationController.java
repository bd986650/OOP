package ru.nsu.belov.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AppleAnimationController {
    private final List<ImageView> apples = new ArrayList<>();
    private Timeline timeline;
    private final HBox container;

    public AppleAnimationController(HBox container) {
        this.container = container;
        initialize();
    }

    private void initialize() {
        Image appleImage = new Image(getClass().getResourceAsStream("/apple.png"));
        for (int i = 0; i < 11; i++) {
            ImageView apple = new ImageView(appleImage);
            apple.setFitHeight(50);
            apple.setPreserveRatio(true);
            apples.add(apple);
            container.getChildren().add(apple);
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
        double sceneWidth = container.getScene().getWidth();

        for (ImageView apple : apples) {
            apple.setTranslateX(apple.getTranslateX() + speed);

            if (apple.localToScene(apple.getBoundsInLocal()).getMinX() > sceneWidth) {
                apple.setTranslateX(apple.getTranslateX() - sceneWidth - 100);
            }
        }
    }

    public void stopAnimation() {
        if (timeline != null) {
            timeline.stop();
        }
    }
} 