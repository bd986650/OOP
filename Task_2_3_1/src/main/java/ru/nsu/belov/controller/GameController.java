package ru.nsu.belov.controller;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import ru.nsu.belov.model.Direction;

import java.util.ArrayList;

import static ru.nsu.belov.Main.gameService;

public class GameController {
    @FXML private Canvas canvas;
    @FXML private Label statusLabel;
    @FXML private VBox endOverlay;

    private int rows, cols, foodCount, winLength;

    private static final int CELL_SIZE = 20;

    public void initGame(int rows, int cols, int foodCount, int winLength) {
        this.rows = rows;
        this.cols = cols;
        this.foodCount = foodCount;
        this.winLength = winLength;

        gameService.initGame(rows, cols, foodCount, winLength);

        endOverlay.setVisible(false);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyInput);
        statusLabel.setText("");
    }

    private void handleKeyInput(KeyEvent event) {
        switch (event.getCode()) {
            case UP, W -> gameService.setDirection(Direction.UP);
            case DOWN, S -> gameService.setDirection(Direction.DOWN);
            case LEFT, A -> gameService.setDirection(Direction.LEFT);
            case RIGHT, D -> gameService.setDirection(Direction.RIGHT);
        }
    }

    public void endGame(String resultMessage) {
        gameService.stop();
        statusLabel.setText(resultMessage);
        endOverlay.setVisible(true);
    }

    @FXML
    private void restartGame() {
        initGame(rows, cols, foodCount, winLength);
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvas.setWidth(cols * CELL_SIZE);
        canvas.setHeight(rows * CELL_SIZE);

        gc.setStroke(Color.LIGHTGRAY);
        for (int x = 0; x <= cols; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, canvas.getHeight());
        }
        for (int y = 0; y <= rows; y++) {
            gc.strokeLine(0, y * CELL_SIZE, canvas.getWidth(), y * CELL_SIZE);
        }

        var snakeParts = new ArrayList<>(gameService.getModel().getSnake().getBody());
        for (int i = 0; i < snakeParts.size(); i++) {
            Point2D part = snakeParts.get(i);
            gc.setFill(Color.BLUEVIOLET);
            gc.fillRect(part.getX() * CELL_SIZE, part.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        gc.setFill(Color.RED);
        for (var food : gameService.getModel().getFood()) {
            gc.fillOval(food.getX() * CELL_SIZE, food.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    @FXML
    private void goToSettings() {
        try {
            ru.nsu.belov.Main.showConfigScene();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
