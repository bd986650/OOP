package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import java.util.Deque;
import java.util.LinkedList;

public class Snake {
    private Deque<Point2D> body = new LinkedList<>();
    private Direction currentDirection = Direction.RIGHT;

    public Snake(Point2D startPosition) {
        body.add(startPosition);
    }

    public Point2D getHead() {
        return body.peekFirst();
    }

    public Deque<Point2D> getBody() {
        return body;
    }

    public void setDirection(Direction newDirection) {
        if (!isOppositeDirection(newDirection)) {
            currentDirection = newDirection;
        }
    }

    public void move(boolean grow) {
        Point2D nextHead = getNextHeadPosition();
        body.addFirst(nextHead);
        if (!grow) {
            body.pollLast();
        }
    }

    public boolean isColliding(Point2D point) {
        return body.contains(point);
    }

    private boolean isOppositeDirection(Direction dir) {
        return (currentDirection == Direction.UP && dir == Direction.DOWN) ||
                (currentDirection == Direction.DOWN && dir == Direction.UP) ||
                (currentDirection == Direction.LEFT && dir == Direction.RIGHT) ||
                (currentDirection == Direction.RIGHT && dir == Direction.LEFT);
    }

    Point2D getNextHeadPosition() {
        Point2D head = getHead();
        return switch (currentDirection) {
            case UP -> head.add(0, -1);
            case DOWN -> head.add(0, 1);
            case LEFT -> head.add(-1, 0);
            case RIGHT -> head.add(1, 0);
        };
    }

    public Direction getDirection() {
        return currentDirection;
    }
}
