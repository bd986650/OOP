package ru.nsu.belov.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Duration;
import ru.nsu.belov.Main;

import java.util.ArrayList;
import java.util.List;

import static ru.nsu.belov.Main.configService;

public class GameConfigController {
    @FXML private Slider rowsSlider;  
    @FXML private Slider colsSlider;  
    @FXML private TextField foodField;
    @FXML private TextField winLengthField;
    @FXML private HBox appleContainer;
    @FXML private ImageView logoImageView;
    @FXML private ComboBox<String> speedComboBox;

    private final List<ImageView> apples = new ArrayList<>();
    private Timeline timeline;

    @FXML
    private void initialize() {
        try {
            Image logo = new Image(getClass().getResource("/logo.png").toExternalForm());
            logoImageView.setImage(logo);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить логотип: " + e.getMessage());
        }

        speedComboBox.getSelectionModel().select("Средне");

        Image appleImage = new Image(getClass().getResourceAsStream("/apple.png"));
        for (int i = 0; i < 11; i++) { // 10 яблок
            ImageView apple = new ImageView(appleImage);
            apple.setFitHeight(50);
            apple.setPreserveRatio(true);
            apples.add(apple);
            appleContainer.getChildren().add(apple);
        }

        startAppleAnimation();
    }

    private void startAppleAnimation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(30), event -> moveApples()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moveApples() {
        double speed = 2;
        double sceneWidth = appleContainer.getScene().getWidth();

        for (ImageView apple : apples) {
            apple.setTranslateX(apple.getTranslateX() + speed);

            if (apple.localToScene(apple.getBoundsInLocal()).getMinX() > sceneWidth) {
                apple.setTranslateX(apple.getTranslateX() - sceneWidth - 100);
            }
        }
    }

    @FXML
    private void startGame() {
        try {
            int rows = (int) rowsSlider.getValue();
            int cols = (int) colsSlider.getValue();
            int food = Integer.parseInt(foodField.getText());
            int winLength = Integer.parseInt(winLengthField.getText());

            String selectedSpeed = speedComboBox.getSelectionModel().getSelectedItem();
            int speed = getSpeedFromSelection(selectedSpeed);

            if (!isInRange(food, 1, 16)) {
                showAlert("Ошибка", "Количество еды должно быть в пределах от 1 до 16.");
                return;
            }

            if (winLength < 2 || winLength > rows * cols) {
                System.out.println("Длина змейки для победы должна быть между 2 и " + (rows * cols));
                showAlert("Ошибка", "Длина змейки для победы должна быть между 2 и " + (rows * cols));
                return;
            }

            Main.startGameWithConfig(rows, cols, food, winLength, speed);

        } catch (NumberFormatException ex) {
            System.out.println("Ошибка ввода чисел. Попробуйте снова.");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int getSpeedFromSelection(String selection) {
        switch (selection) {
            case "Быстро":
                return 100;
            case "Средне":
                return 150;
            case "Медленно":
                return 200;
            default:
                return 150;
        }
    }

    public static boolean isValidConfig(int rows, int cols, int food, int winLength) {
        return configService.validateConfig(rows, cols, food, winLength);
    }
}
