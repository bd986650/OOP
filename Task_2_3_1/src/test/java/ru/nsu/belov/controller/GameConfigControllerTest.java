package ru.nsu.belov.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigControllerTest {

    @Test
    @DisplayName("Корректная конфигурация")
    void validConfig_ShouldReturnTrue() {
        assertTrue(GameConfigController.isValidConfig(16, 16, 3, 20));
    }

    @Test
    @DisplayName("Слишком мало строк")
    void tooFewRows_ShouldReturnFalse() {
        assertFalse(GameConfigController.isValidConfig(4, 10, 3, 10));
    }

    @Test
    @DisplayName("Слишком много колонок")
    void tooManyColumns_ShouldReturnFalse() {
        assertFalse(GameConfigController.isValidConfig(10, 31, 3, 10));
    }

    @Test
    @DisplayName("Некорректное количество еды")
    void invalidFood_ShouldReturnFalse() {
        assertAll(
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, 0, 10)),
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, 17, 10))
        );
    }

    @Test
    @DisplayName("Некорректная длина победы")
    void invalidWinLength_ShouldReturnFalse() {
        assertAll(
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, 3, 0)),
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, 3, 101))
        );
    }

    @Test
    @DisplayName("Пограничные значения")
    void edgeValues_ShouldReturnTrue() {
        assertAll(
                () -> assertTrue(GameConfigController.isValidConfig(16, 16, 1, 2)),
                () -> assertTrue(GameConfigController.isValidConfig(30, 30, 16, 900))
        );
    }

    @Test
    @DisplayName("Отрицательные значения")
    void negativeValues_ShouldReturnFalse() {
        assertAll(
                () -> assertFalse(GameConfigController.isValidConfig(-10, 10, 3, 20)),
                () -> assertFalse(GameConfigController.isValidConfig(10, -10, 3, 20)),
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, -1, 20)),
                () -> assertFalse(GameConfigController.isValidConfig(10, 10, 3, -5))
        );
    }

    @Test
    @DisplayName("Длина победы больше размера поля")
    void winLengthTooLargeForField_ShouldReturnFalse() {
        assertFalse(GameConfigController.isValidConfig(5, 5, 3, 26));
    }

    @Test
    @DisplayName("Слишком много еды для размера поля")
    void tooMuchFoodForFieldSize_ShouldReturnFalse() {
        assertFalse(GameConfigController.isValidConfig(3, 3, 10, 5));
    }

    @Test
    @DisplayName("Много еды + большая длина победы — допустимо")
    void foodAndWinLengthLargeButValid_ShouldReturnTrue() {
        assertTrue(GameConfigController.isValidConfig(16, 16, 10, 20));
    }
}
