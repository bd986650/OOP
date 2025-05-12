package ru.nsu.belov.data;

import javafx.geometry.Point2D;
import ru.nsu.belov.model.SnakeModel;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private final int rows;
    private final int cols;
    private final int maxLength;
    private SnakeModel snake;
    private List<Point2D> foodList;

    public GameData(int rows, int cols, int maxLength) {
        this.rows = rows;
        this.cols = cols;
        this.maxLength = maxLength;
        this.foodList = new ArrayList<>();
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public SnakeModel getSnake() {
        return snake;
    }

    public void setSnake(SnakeModel snake) {
        this.snake = snake;
    }

    public List<Point2D> getFoodList() {
        return foodList;
    }
} 