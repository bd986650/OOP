package ru.nsu.belov.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ru.nsu.belov.Main;

import static ru.nsu.belov.Main.configService;

public class GameConfigController {
    @FXML private TextField rowsField;
    @FXML private TextField colsField;
    @FXML private TextField foodField;
    @FXML private TextField winLengthField;

    @FXML
    private void startGame() {
        try {
            int rows = Integer.parseInt(rowsField.getText());
            int cols = Integer.parseInt(colsField.getText());
            int food = Integer.parseInt(foodField.getText());
            int winLength = Integer.parseInt(winLengthField.getText());

            if (!isInRange(rows, 5, 30) || !isInRange(cols, 5, 30)) {
                System.out.println("Количество строк и столбцов должно быть от 5 до 30.");
                return;
            }

            if (!isInRange(food, 1, 16)) {
                System.out.println("Количество еды должно быть в пределах от 1 до 16.");
                return;
            }

            if (winLength < 2 || winLength > rows * cols) {
                System.out.println("Длина змейки для победы должна быть между 2 и " + (rows * cols));
                return;
            }

            Main.startGameWithConfig(rows, cols, food, winLength);
        } catch (NumberFormatException ex) {
            System.out.println("Ошибка ввода чисел. Попробуйте снова.");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    public static boolean isValidConfig(int rows, int cols, int food, int winLength) {
        return configService.validateConfig(rows, cols, food, winLength);
    }
}
