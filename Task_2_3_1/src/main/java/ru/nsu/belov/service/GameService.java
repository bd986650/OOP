package ru.nsu.belov.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.belov.controller.GameController;
import ru.nsu.belov.model.Direction;
import ru.nsu.belov.model.GameModel;

public class GameService {
    private Timeline timeline;
    private GameModel model;
    private GameController controller;

    public void setGameController(GameController gameController) {
        this.controller = gameController;
    }

    public void setDirection(Direction direction) {
        model.getSnake().setDirection(direction);
    }

    public GameModel getModel() {
        return model;
    }

    public void initGame(int rows, int cols, int foodCount, int winLength, int speed) {
        model = new GameModel(rows, cols, foodCount, winLength);
        timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> updateGame())); // Используем переданную скорость
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateGame() {
        boolean alive = model.update();
        controller.draw();
        if (!alive) {
            controller.endGame("Game Over!");
        } else if (model.isVictory()) {
            controller.endGame("You Win!");
        }
    }

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
}
