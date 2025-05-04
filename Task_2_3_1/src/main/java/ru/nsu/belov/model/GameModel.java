package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import ru.nsu.belov.data.GameData;
import ru.nsu.belov.data.SnakeData;

import java.util.Random;

public class GameModel {
    private GameData data;
    private Random random = new Random();

    public GameModel(int rows, int cols, int foodCount, int maxLength) {
        this.data = new GameData(rows, cols, maxLength);

        SnakeData snakeData = new SnakeData(new Point2D(cols / 2.0, rows / 2.0));
        this.data.setSnake(new SnakeModel(snakeData));

        for (int i = 0; i < foodCount; i++) {
            placeFood();
        }
    }

    public SnakeModel getSnake() {
        return data.getSnake();
    }

    public java.util.List<Point2D> getFood() {
        return data.getFoodList();
    }

    public boolean update() {
        Point2D nextHead = data.getSnake().getNextHeadPosition();
        boolean ateFood = data.getFoodList().remove(nextHead);

        if (isCollision(nextHead)) {
            return false;
        }

        data.getSnake().move(ateFood);

        if (ateFood) {
            placeFood();
        }
        return true;
    }

    public boolean isVictory() {
        return data.getSnake().getBody().size() >= data.getMaxLength();
    }

    private boolean isCollision(Point2D head) {
        if (head.getX() < 0 || head.getX() >= data.getCols() ||
            head.getY() < 0 || head.getY() >= data.getRows()) {
            return true;
        }

        var body = data.getSnake().getBody();
        var tail = body.peekLast();
        return body.stream()
            .filter(p -> !p.equals(tail))
            .anyMatch(p -> p.equals(head));
    }

    private void placeFood() {
        Point2D point;
        do {
            point = new Point2D(random.nextInt(data.getCols()), random.nextInt(data.getRows()));
        } while (data.getSnake().isColliding(point) || data.getFoodList().contains(point));
        data.getFoodList().add(point);
    }

    public int getRows() {
        return data.getRows();
    }

    public int getCols() {
        return data.getCols();
    }

    public int getWinLength() {
        return data.getMaxLength();
    }
}
