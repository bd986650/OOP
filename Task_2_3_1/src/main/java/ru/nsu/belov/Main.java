package ru.nsu.belov;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.belov.controller.GameController;
import ru.nsu.belov.service.ConfigService;
import ru.nsu.belov.service.GameService;

public class Main extends Application {
    private static Stage mainStage;

    public static final ConfigService configService = new ConfigService();
    public static final GameService gameService = new GameService();

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        showConfigScene();
    }

    public static void showConfigScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game_config.fxml"));
        Scene scene = new Scene(loader.load());

        mainStage.setTitle("Настройки");
        mainStage.setScene(scene);
        mainStage.setWidth(400);
        mainStage.setHeight(300);
        mainStage.show();
    }

    public static void startGameWithConfig(int rows, int cols, int food, int winLength) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game.fxml"));
        Scene scene = new Scene(loader.load());

        GameController controller = loader.getController();
        gameService.setGameController(controller);
        controller.initGame(rows, cols, food, winLength);

        int width = cols * 20 + 16;
        int height = rows * 20 + 139;

        mainStage.setScene(scene);
        mainStage.setWidth(width);
        mainStage.setHeight(height);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
