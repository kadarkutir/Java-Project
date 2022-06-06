package gui;


import board.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.tinylog.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BoardController {

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private Board model = new Board();

    private Player player1 = new Player(PieceType.BLUE,"player1",false,false);

    private Player player2 = new Player(PieceType.RED,"player2",false,false);

    private BooleanProperty player1Won = new SimpleBooleanProperty();

    private BooleanProperty player2Won = new SimpleBooleanProperty();

    private IntegerProperty numberOfMoves = new SimpleIntegerProperty();

    @FXML
    private GridPane board;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;

    @FXML
    private TextField numberOfMovesField;


    @FXML
    private void initialize() {
        createBoard();
        createPieces();
        setSelectablePositions();
        showSelectablePositions();
        registerHandlersAndListeners();
        createBindings();
    }

    private void createBindings() {
        numberOfMovesField.textProperty().bind(numberOfMoves.asString());
    }

    private void createBoard() {
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare();
                board.add(square, j, i);
            }
        }
    }

    private void resetGame() {
        model = new Board();
        player1Won.set(false);
        player2Won.set(false);
        createBoard();
    }

    public void setPlayer1Name(String name){
        player1.setName(name);
        player1Label.setText(name);
    }

    public void setPlayer2Name(String name){
        player2.setName(name);
        player2Label.setText(name);
    }

    private void registerHandlersAndListeners() {
        player1Won.addListener(this::handleGameOverPlayer1);
        player2Won.addListener(this::handleGameOverPlayer2);
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private void createPieces() {
        for (int i = 0; i < model.getPieceCount(); i++) {
            model.positionProperty(i).addListener(this::piecePositionChange);
            var piece = createPiece(Color.valueOf(model.getPieceType(i).name()));
            getSquare(model.getPiecePosition(i)).getChildren().add(piece);
        }
    }

    private Circle createPiece(Color color) {
        var piece = new Circle(25);
        piece.setFill(color);
        return piece;
    }

    private void handleGameOverPlayer1(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue){
        if (newValue) {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("End of the game");
            alert.setContentText(player1.getName() + " won the game.\n Moves:" + numberOfMovesField.getText());
            alert.showAndWait();
            Platform.exit();
        }
    }

    private void handleGameOverPlayer2(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue){
        if (newValue) {
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("End of the game");
            alert.setContentText(player2.getName() + " won the game.\n Moves:" + numberOfMovesField.getText());
            alert.showAndWait();
            Platform.exit();
        }
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var pieceNumber = model.getPieceNumber(selected);
                    var direction = Direction.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", pieceNumber, direction);
                    model.move(pieceNumber, direction);
                    numberOfMoves.set(numberOfMoves.get() + 1);
                    model.winPlayer(player1,player2);
                    if(player1.isWon()){
                        player1Won.set(true);
                    } else if (player2.isWon()) {
                        player2Won.set(true);
                    }
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }


    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.getPiecePositions());
            case SELECT_TO -> {
                var pieceNumber = model.getPieceNumber(selected);
                for (var direction : model.getValidMoves(pieceNumber)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void piecePositionChange(ObservableValue<? extends Position> observable, Position oldPosition, Position newPosition) {
        Logger.debug("Move: {} -> {}", oldPosition, newPosition);
        StackPane oldSquare = getSquare(oldPosition);
        StackPane newSquare = getSquare(newPosition);
        newSquare.getChildren().addAll(oldSquare.getChildren());
        oldSquare.getChildren().clear();
    }
}

