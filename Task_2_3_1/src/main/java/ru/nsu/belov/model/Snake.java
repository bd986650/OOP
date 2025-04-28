package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import ru.nsu.belov.data.SnakeData;

import java.util.Deque;

public class Snake {
    private SnakeData data;

    public Snake(SnakeData data) {
        this.data = data;
    }

    public Deque<Point2D> getBody() {
        return data.getBody();
    }

    public Point2D getHead() {
        return data.getHead();
    }

    public void setDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            data.setCurrentDirection(newDirection);
        }
    }

    public void move(boolean grow) {
        Point2D nextHead = getNextHeadPosition();
        data.getBody().addFirst(nextHead);
        if (!grow) {
            data.getBody().pollLast();
        }
    }

    public boolean isColliding(Point2D point) {
        return data.getBody().contains(point);
    }

    private boolean isOppositeDirection(Direction dir) {
        return (data.getCurrentDirection() == Direction.UP && dir == Direction.DOWN) ||
                (data.getCurrentDirection() == Direction.DOWN && dir == Direction.UP) ||
                (data.getCurrentDirection() == Direction.LEFT && dir == Direction.RIGHT) ||
                (data.getCurrentDirection() == Direction.RIGHT && dir == Direction.LEFT);
    }

    public Point2D getNextHeadPosition() {
        Point2D head = data.getHead();
        switch (data.getCurrentDirection()) {
            case UP: return head.add(0, -1);
            case DOWN: return head.add(0, 1);
            case LEFT: return head.add(-1, 0);
            case RIGHT: return head.add(1, 0);
            default: return head;
        }
    }

    public Direction getDirection() {
        return data.getCurrentDirection();
    }
}
