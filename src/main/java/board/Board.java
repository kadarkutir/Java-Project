package board;

import javafx.beans.property.ObjectProperty;
import org.tinylog.Logger;

import java.util.*;

/**
 * Represents a board.
 */
public class Board {

    /**
     * Size of the row of the board.
     */
    public static final int BOARD_ROW = 5;

    /**
     * Size of the column of the board.
     */
    public static final int BOARD_COL = 4;

    /**
     * Array of storing the pieces.
     */
    private final Piece[] pieces;

    /**
     * First player object.
     */
    Player player1 = new Player(PieceType.BLUE,"player1",false,false);

    /**
     * Second player object.
     */
    Player player2 = new Player(PieceType.RED,"player2",false,false);

    /**
     * Creates a {@code Board} object that corresponds to the original
     * initial state of the puzzle.
     */
    public Board(){
        this(   new Piece(PieceType.BLUE,new Position(0,0)),
                new Piece(PieceType.RED,new Position(0,1)),
                new Piece(PieceType.BLUE,new Position(0,2)),
                new Piece(PieceType.RED,new Position(0,3)),
                new Piece(PieceType.RED,new Position(4,0)),
                new Piece(PieceType.BLUE,new Position(4,1)),
                new Piece(PieceType.RED,new Position(4,2)),
                new Piece(PieceType.BLUE,new Position(4,3))
        );
    }

    /**
     * Creates a {@code Board} object initializing the positions of the
     * pieces with the positions specified. The constructor expects an array of
     * eight {@code Piece} objects or eight {@code Piece} objects.
     *
     * @param pieces the initial positions of the pieces
     */
    public Board(Piece... pieces){
        checkPositions(pieces);
        this.pieces = pieces;
    }

    /**
     * Checks the positions of the pieces.
     *
     * @param pieces array of piece
     */
    private void checkPositions(Piece[] pieces) {
        if (pieces.length != 8) {
            throw new IllegalArgumentException();
        }
        for (var piece : pieces) {
            if (!isOnBoard(piece.getPosition())) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Returns whether the given position is on the board.
     *
     * @param position given position
     * @return whether the position is on the board
     */
    private boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_ROW &&
                position.col() >= 0 && position.col() < BOARD_COL;
    }

    /**
     * Returns whether the where the piece wants to move is valid or not.
     *
     * @param pieceNumber the number of the piece
     * @param direction the direction where the piece wants to move
     * @return whether the can move to the given direction
     */
    public boolean isValidMove(int pieceNumber, Direction direction) {
        if (pieceNumber < 0 || pieceNumber >= pieces.length) {
            throw new IllegalArgumentException();
        }
        Position newPosition = pieces[pieceNumber].getPosition().moveTo(direction);
        if (!isOnBoard(newPosition)) {
            return false;
        }
        for (var piece : pieces) {
            if (piece.getPosition().equals(newPosition)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the valid moves for a piece.
     *
     * @param pieceNumber the number of the piece
     * @return all the valid moves for a piece
     */
    public Set<Direction> getValidMoves(int pieceNumber) {
        EnumSet<Direction> validMoves = EnumSet.noneOf(Direction.class);
        for (var direction : Direction.values()) {
            if (isValidMove(pieceNumber, direction)) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     *{@return the number of pieces}
     */
    public int getPieceCount() { return pieces.length; }

    /**
     * Returns the position of a piece by its number.
     *
     * @param pieceNumber the number of the piece
     * @return the position of the piece
     */
    public ObjectProperty<Position> positionProperty(int pieceNumber) { return pieces[pieceNumber].positionProperty(); }

    /**
     * Returns the type of the given piece.
     *
     * @param pieceNumber the number of the piece
     * @return the type of the piece
     */
    public PieceType getPieceType(int pieceNumber) { return pieces[pieceNumber].getType(); }

    /**
     * Returns the position of a given piece.
     *
     * @param pieceNumber the number of the piece
     * @return the position of the piece
     */
    public Position getPiecePosition(int pieceNumber) { return pieces[pieceNumber].getPosition(); }

    /**
     * Returns the positions of all the pieces.
     *
     * @return the positions of the pieces
     */
    public List<Position> getPiecePositions() {
        List<Position> positions = new ArrayList<>(pieces.length);
        for (var piece : pieces) {
            positions.add(piece.getPosition());
        }
        return positions;
    }

    /**
     * Iterates through the pieces array
     * if it finds a win state sets {@code won} of the winning player true.
     *
     * @param player1 first player object
     * @param player2 second player object
     */
    public void winPlayer(Player player1,Player player2){
        for(var p:pieces){
            winPosition(p,player1,player2);
        }
    }

    /**
     * Checks whether a piece is in a winning state
     * if it is then sets {@code won} of the winning player true.
     *
     * @param piece a piece from pieces
     * @param player1 first player object
     * @param player2 second player object
     */
    private void winPosition(Piece piece,Player player1 ,Player player2) {
        if(piece.getType() == PieceType.BLUE) {
            if(getPieceTypeDown(piece) == PieceType.BLUE && getPieceTypeUp(piece) == PieceType.BLUE){
                player1.setWon(true);
            } else if (getPieceTypeLeft(piece) == PieceType.BLUE && getPieceTypeRight(piece) == PieceType.BLUE) {
                player1.setWon(true);
            }else if (getPieceTypeUpLeft(piece) == PieceType.BLUE && getPieceTypeDownRight(piece) == PieceType.BLUE) {
                player1.setWon(true);
            }else if (getPieceTypeUpRight(piece) == PieceType.BLUE && getPieceTypeDownLeft(piece) == PieceType.BLUE) {
                player1.setWon(true);
            }
        }
        if(piece.getType() == PieceType.RED) {
            if(getPieceTypeDown(piece) == PieceType.RED && getPieceTypeUp(piece) == PieceType.RED){
                player2.setWon(true);
            } else if (getPieceTypeLeft(piece) == PieceType.RED && getPieceTypeRight(piece) == PieceType.RED) {
                player2.setWon(true);
            }else if (getPieceTypeUpLeft(piece) == PieceType.RED && getPieceTypeDownRight(piece) == PieceType.RED) {
                player2.setWon(true);
            }else if (getPieceTypeUpRight(piece) == PieceType.RED && getPieceTypeDownLeft(piece) == PieceType.RED) {
                player2.setWon(true);
            }
        }
    }

    /**
     * Returns the type of the piece of which is upper
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeUp(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getUp());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }

    /**
     * Returns the type of the piece of which is bottom
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeDown(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getDown());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }

    /**
     * Returns the type of the piece of which is on the left
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeLeft(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getLeft());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }

    /**
     * Returns the type of the piece of which is on the right
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeRight(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getRight());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }


    /**
     * Returns the type of the piece of which is on the upper right
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeUpRight(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getUpRight());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }


    /**
     * Returns the type of the piece of which is on the bottom right
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeDownRight(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getDownRight());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }


    /**
     * Returns the type of the piece of which is on the upper left
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeUpLeft(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getUpLeft());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }


    /**
     * Returns the type of the piece of which is on the bottom left
     * to the given piece.
     *
     * @param piece a Piece object
     * @return type of the piece
     */
    private PieceType getPieceTypeDownLeft(Piece piece) {
        int pieceNum = getPieceNumber(piece.positionProperty().getValue().getDownLeft());
        if(pieceNum >= 0) {
            return pieces[pieceNum].getType();
        }else {
            return null;
        }
    }

    /**
     * Returns the number of the piece to a given position.
     * If it doesn't find it returns -1.
     *
     * @param position a Position object
     * @return the number of the piece
     */
    public int getPieceNumber(Position position) {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i].getPosition().equals(position)) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Moves the given piece to a given direction.
     * If a player moved one of his pieces the other one moves next.
     *
     * @param pieceNumber the number of the piece
     * @param direction the direction to move
     */
    public void move(int pieceNumber, Direction direction) {
        if(pieces[pieceNumber].getType() == PieceType.BLUE && !player1.isMoved()) {
            if (direction == Direction.UP) {
                if (canMoveUp(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.UP);
                    player1.setMoved(true);
                    player2.setMoved(false);
                }
            } else if (direction == Direction.DOWN) {
                if (canMoveDown(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.DOWN);
                    player1.setMoved(true);
                    player2.setMoved(false);
                }
            } else if (direction == Direction.LEFT) {
                if (canMoveLeft(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.LEFT);
                    player1.setMoved(true);
                    player2.setMoved(false);
                }
            } else if (direction == Direction.RIGHT) {
                if (canMoveRight(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.RIGHT);
                    player1.setMoved(true);
                    player2.setMoved(false);
                }
            } else {
                Logger.debug("{} piece cant move {} direction",pieceNumber,direction);
            }
        } else if (pieces[pieceNumber].getType() == PieceType.RED && !player2.isMoved()) {
            if (direction == Direction.UP) {
                if (canMoveUp(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.UP);
                    player1.setMoved(false);
                    player2.setMoved(true);
                }
            } else if (direction == Direction.DOWN) {
                if (canMoveDown(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.DOWN);
                    player1.setMoved(false);
                    player2.setMoved(true);
                }
            } else if (direction == Direction.LEFT) {
                if (canMoveLeft(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.LEFT);
                    player1.setMoved(false);
                    player2.setMoved(true);
                }
            } else if (direction == Direction.RIGHT) {
                if (canMoveRight(pieceNumber)) {
                    pieces[pieceNumber].moveTo(Direction.RIGHT);
                    player1.setMoved(false);
                    player2.setMoved(true);
                }
            } else {
                Logger.debug("{} piece cant move {} direction",pieceNumber,direction);
            }
        }
    }

    /**
     * Returns whether the piece can move up.
     *
     * @param pieceNumber the number of the piece
     * @return whether it can move there
     */
    private boolean canMoveUp(int pieceNumber) {
        return pieces[pieceNumber].positionProperty().getValue().row() != 0 && isEmpty(pieces[pieceNumber].positionProperty().getValue().getUp());
    }

    /**
     * Returns whether the piece can move down.
     *
     * @param pieceNumber the number of the piece
     * @return whether it can move there
     */
    private boolean canMoveDown(int pieceNumber) {
        return pieces[pieceNumber].positionProperty().getValue().row() != BOARD_ROW-1 && isEmpty(pieces[pieceNumber].positionProperty().getValue().getDown());
    }

    /**
     * Returns whether the piece can move left.
     *
     * @param pieceNumber the number of the piece
     * @return whether it can move there
     */
    private boolean canMoveLeft(int pieceNumber) {
        return pieces[pieceNumber].positionProperty().getValue().col() != 0 && isEmpty(pieces[pieceNumber].positionProperty().getValue().getLeft());
    }

    /**
     * Returns whether the piece can move right.
     *
     * @param pieceNumber the number of the piece
     * @return whether it can move there
     */
    private boolean canMoveRight(int pieceNumber) {
        return pieces[pieceNumber].positionProperty().getValue().col() != BOARD_COL-1 && isEmpty(pieces[pieceNumber].positionProperty().getValue().getRight());
    }

    /**
     * Returns whether a position is empty by a given position.
     *
     * @param position a Position object
     * @return whether a position is empty
     */
    private boolean isEmpty(Position position) {
        for (var p : pieces) {
            if(p.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        var sj = new StringJoiner(",", "[", "]");
        for (var piece : pieces) {
            sj.add(piece.toString());
        }
        return sj.toString();
    }

}
