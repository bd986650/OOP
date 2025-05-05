package ru.nsu.belov.controller;

import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.belov.model.Direction;

import java.util.ArrayList;

import static ru.nsu.belov.Main.gameService;

public class GameController {
    @FXML private Canvas canvas;

    private int rows, cols, foodCount, winLength, speed;
    private static final int CELL_SIZE = 15;
    private Image appleImage;

    public void initialize() {
        appleImage = new Image(getClass().getResource("/apple.png").toExternalForm());
    }

    public void initGame(int rows, int cols, int foodCount, int winLength, int speed) {
        this.rows = rows;
        this.cols = cols;
        this.foodCount = foodCount;
        this.winLength = winLength;
        this.speed = speed;

        gameService.initGame(rows, cols, foodCount, winLength, speed);
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyInput);

        draw();
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
        try {
            ru.nsu.belov.Main.showGameEndScene(resultMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        canvas.setWidth(cols * CELL_SIZE + 20);
        canvas.setHeight(rows * CELL_SIZE + 60);

        gc.setFill(Color.rgb(180, 200, 150));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3);
        gc.strokeRect(10, 30, cols * CELL_SIZE, rows * CELL_SIZE);

        gc.setFill(Color.BLACK);
        gc.setFont(Font.font("Monospaced", 24));
        gc.fillText("SNAKE GAME", canvas.getWidth() / 2 - 45, 25);

        gc.setFont(Font.font("Monospaced", 18));
        gc.fillText("Length: " + (gameService.getModel().getSnake().getBody().size()), canvas.getWidth() / 2 - 50, canvas.getHeight() - 15);

        renderSnake(gc);
        renderFood(gc);
    }

    private void renderSnake(GraphicsContext gc) {
        var snakeParts = new ArrayList<>(gameService.getModel().getSnake().getBody());

        for (Point2D part : snakeParts) {
            double x = 10 + part.getX() * CELL_SIZE;
            double y = 30 + part.getY() * CELL_SIZE;

            gc.setFill(Color.BLACK);
            gc.fillRoundRect(x, y, CELL_SIZE, CELL_SIZE, 4, 4);
        }
    }

    private void renderFood(GraphicsContext gc) {
        for (Point2D food : gameService.getModel().getFood()) {
            double x = 10 + food.getX() * CELL_SIZE;
            double y = 30 + food.getY() * CELL_SIZE;
            gc.drawImage(appleImage, x, y, CELL_SIZE, CELL_SIZE);
        }
    }
}
