package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class StarterController {

    @FXML
    private TextField bluePlayerName;

    @FXML
    private TextField redPlayerName;

    @FXML
    private Label blueError;

    @FXML
    private Label redError;


    public void startAction(ActionEvent actionEvent) throws IOException {
        if(bluePlayerName.getText().isEmpty()){
            blueError.setText("Please enter your name");
        } else if (redPlayerName.getText().isEmpty()) {
            redError.setText("Please enter your name");
        } else {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui.fxml"));
            Parent root = loader.load();

            BoardController boardController = loader.getController();
            boardController.setPlayer1Name(bluePlayerName.getText());
            boardController.setPlayer2Name(redPlayerName.getText());

            stage.setScene(new Scene(root));
            stage.show();
            Logger.info("The blue player's name is set to {}, loading game scene", bluePlayerName.getText());
            Logger.info("The red player's name is set to {}, loading game scene", redPlayerName.getText());
        }
    }
}
