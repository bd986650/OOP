package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameModel {
    private final int rows;
    private final int cols;
    private final int maxLength;
    private Snake snake;
    private List<Point2D> foodList = new ArrayList<>();
    private Random random = new Random();

    public GameModel(int rows, int cols, int foodCount, int maxLength) {
        this.rows = rows;
        this.cols = cols;
        this.maxLength = maxLength;
        this.snake = new Snake(new Point2D(cols / 2.0, rows / 2.0));

        for (int i = 0; i < foodCount; i++) {
            placeFood();
        }
    }

    public Snake getSnake() {
        return snake;
    }

    public List<Point2D> getFood() {
        return foodList;
    }

    public boolean update() {
        Point2D nextHead = snake.getNextHeadPosition();
        boolean ateFood = foodList.remove(nextHead);

        if (isCollision(nextHead)) {
            return false;
        }

        snake.move(ateFood);
        if (ateFood) {
            placeFood();
        }
        return true;
    }

    public boolean isVictory() {
        return snake.getBody().size() >= maxLength;
    }

    private boolean isCollision(Point2D head) {
        return head.getX() < 0 || head.getX() >= cols ||
                head.getY() < 0 || head.getY() >= rows ||
                snake.isColliding(head);
    }

    private void placeFood() {
        Point2D point;
        do {
            point = new Point2D(random.nextInt(cols), random.nextInt(rows));
        } while (snake.isColliding(point) || foodList.contains(point));
        foodList.add(point);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getWinLength() {
        return maxLength;
    }
}
