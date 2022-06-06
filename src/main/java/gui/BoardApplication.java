package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.tinylog.Logger;

public class BoardApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Logger.info("Starting application");
        Parent root = FXMLLoader.load(getClass().getResource("/starter.fxml"));
        stage.setTitle("Tic Tac Toe like Board Game");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

