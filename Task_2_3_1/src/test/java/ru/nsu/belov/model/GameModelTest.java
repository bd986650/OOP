package ru.nsu.belov.model;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private GameModel model;
    private final int rows = 10;
    private final int cols = 10;
    private final int foodCount = 3;
    private final int winLength = 5;

    @BeforeEach
    void setUp() {
        model = new GameModel(rows, cols, foodCount, winLength);
    }

    @Test
    @DisplayName("Начальная позиция змейки по центру поля")
    void initialSnakePosition_ShouldBeCenter() {
        Point2D expected = new Point2D(cols / 2, rows / 2);
        assertEquals(expected, model.getSnake().getHead());
    }

    @Test
    @DisplayName("Изначально спавнится заданное количество еды")
    void foodSpawnedInitially_ShouldMatchFoodCount() {
        List<Point2D> food = model.getFood();
        assertEquals(foodCount, food.size());
    }

    @Test
    @DisplayName("При обновлении змейка должна двигаться")
    void update_ShouldMoveSnake() {
        Point2D before = model.getSnake().getHead();
        model.update();
        Point2D after = model.getSnake().getHead();
        assertNotEquals(before, after);
    }

    @Test
    @DisplayName("Змейка ест еду при движении")
    void update_ShouldEatFoodIfOnPath() {
        SnakeModel snake = model.getSnake();
        Point2D nextHead = snake.getNextHeadPosition();

        model.getFood().clear();
        model.getFood().add(nextHead);

        int sizeBefore = snake.getBody().size();
        boolean alive = model.update();
        int sizeAfter = snake.getBody().size();

        assertAll(
                () -> assertTrue(alive),
                () -> assertEquals(sizeBefore + 1, sizeAfter)
        );
    }

    @Test
    @DisplayName("Игра заканчивается, если змейка врезается в стену")
    void update_ShouldGameOverWhenHitsWall() {
        SnakeModel snake = model.getSnake();
        snake.setDirection(Direction.LEFT);

        // Двигаемся до упора в стену
        while (model.update());

        // После этого следующее обновление должно вернуть false
        assertFalse(model.update());
    }

    @Test
    @DisplayName("Победа при достижении необходимой длины")
    void victoryCondition_ShouldTriggerWhenLengthReached() {
        SnakeModel snake = model.getSnake();
        for (int i = 0; i < winLength - 1; i++) {
            snake.move(true);
        }
        assertTrue(model.isVictory());
    }

    @Test
    @DisplayName("Проверка размеров игрового поля")
    void gameFieldDimensions_ShouldBeCorrect() {
        assertAll(
                () -> assertEquals(rows, model.getRows()),
                () -> assertEquals(cols, model.getCols()),
                () -> assertEquals(winLength, model.getWinLength())
        );
    }

    @Test
    @DisplayName("Еда не должна спавниться на теле змейки")
    void food_ShouldNotSpawnOnSnake() {
        for (Point2D food : model.getFood()) {
            assertFalse(model.getSnake().getBody().contains(food));
        }
    }
}
