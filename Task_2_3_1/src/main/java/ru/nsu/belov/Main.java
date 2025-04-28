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
        mainStage.setWidth(800);
        mainStage.setHeight(700);
        mainStage.show();
    }

    public static void startGameWithConfig(int rows, int cols, int food, int winLength, int speed) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/game.fxml"));
        Scene scene = new Scene(loader.load());

        GameController controller = loader.getController();
        gameService.setGameController(controller);
        controller.initGame(rows, cols, food, winLength, speed);

        mainStage.setScene(scene);
        mainStage.setWidth(800);
        mainStage.setHeight(600);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
