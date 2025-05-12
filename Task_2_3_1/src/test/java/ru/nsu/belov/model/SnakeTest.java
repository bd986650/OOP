package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nsu.belov.data.SnakeData;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {

    private SnakeModel snake;
    private SnakeData snakeData;
    private Point2D start;

    @BeforeEach
    void setUp() {
        start = new Point2D(5, 5);
        snakeData = new SnakeData(start);
        snake = new SnakeModel(snakeData);
    }

    @Test
    @DisplayName("Проверка начальной позиции головы и тела змейки")
    void initialSnake_ShouldHaveCorrectHeadAndBody() {
        assertAll(
                () -> assertEquals(start, snake.getHead()),
                () -> assertEquals(1, snake.getBody().size())
        );
    }

    @Test
    @DisplayName("Движение без роста - тело остаётся того же размера")
    void moveWithoutGrowth_ShouldUpdateHeadOnly() {
        snake.move(false);
        assertAll(
                () -> assertEquals(new Point2D(6, 5), snake.getHead()),
                () -> assertEquals(1, snake.getBody().size())
        );
    }

    @Test
    @DisplayName("Движение с ростом - тело увеличивается на 1")
    void moveWithGrowth_ShouldGrowSnake() {
        snake.move(true);
        assertAll(
                () -> assertEquals(new Point2D(6, 5), snake.getHead()),
                () -> assertEquals(2, snake.getBody().size())
        );
    }

    @Test
    @DisplayName("Изменение направления на допустимое")
    void setDirection_ValidChange_ShouldUpdateDirection() {
        snake.setDirection(Direction.DOWN);
        assertEquals(Direction.DOWN, snake.getDirection());
    }

    @Test
    @DisplayName("Изменение направления на противоположное - игнорируется")
    void setDirection_OppositeChange_ShouldIgnore() {
        snake.setDirection(Direction.LEFT);
        assertEquals(Direction.RIGHT, snake.getDirection()); // Начальное направление - RIGHT
    }

    @Test
    @DisplayName("Проверка столкновения - должно быть True")
    void isColliding_WhenPointInsideBody_ShouldReturnTrue() {
        snake.move(true); // Змейка увеличится
        assertTrue(snake.isColliding(new Point2D(5, 5)));
    }

    @Test
    @DisplayName("Проверка столкновения - должно быть False")
    void isColliding_WhenPointNotInBody_ShouldReturnFalse() {
        snake.move(true);
        assertFalse(snake.isColliding(new Point2D(10, 10)));
    }

    @Test
    @DisplayName("Следующая позиция головы по умолчанию (вправо)")
    void getNextHeadPosition_DefaultRight_ShouldReturnCorrectPosition() {
        assertEquals(new Point2D(6, 5), snake.getNextHeadPosition());
    }

    @Test
    @DisplayName("Следующая позиция головы после смены направления")
    void getNextHeadPosition_AfterDirectionChange_ShouldReturnCorrectPosition() {
        snake.setDirection(Direction.UP);
        assertEquals(new Point2D(5, 4), snake.getNextHeadPosition());
    }
}
