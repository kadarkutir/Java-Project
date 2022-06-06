package board;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a piece
 */
public class Piece {

    /**
     * The type of the piece.
     */
    private final PieceType type;

    /**
     * The position of the piece.
     */
    private final ObjectProperty<Position> position = new SimpleObjectProperty<>();

    public Piece(PieceType type, Position position) {
        this.type = type;
        this.position.set(position);
    }

    /**
     *{@return the type of the piece}
     */
    public PieceType getType() { return type; }

    /**
     *{@return the position of the piece}
     */
    public Position getPosition() {
        return position.get();
    }

    public ObjectProperty<Position> positionProperty() {
        return position;
    }

    /**
     * Moves the piece to a given direction.
     *
     * @param direction a given direction
     */
    public void moveTo(Direction direction) {
        Position newPosition = position.get().moveTo(direction);
        position.set(newPosition);
    }

    @Override
    public String toString(){ return type.toString() + " " + position; }

}

