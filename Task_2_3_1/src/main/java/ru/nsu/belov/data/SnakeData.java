package ru.nsu.belov.data;

import javafx.geometry.Point2D;
import ru.nsu.belov.model.Direction;

import java.util.Deque;
import java.util.LinkedList;

public class SnakeData {
    private Deque<Point2D> body = new LinkedList<>();
    private Direction currentDirection = Direction.RIGHT;

    public SnakeData(Point2D startPosition) {
        body.add(startPosition);
    }

    public Deque<Point2D> getBody() {
        return body;
    }

    public Point2D getHead() {
        return body.peekFirst();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}
