package board;

import javafx.beans.property.ObjectProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    private void setUp() {board = new Board(); }

    @Test
    void isValidMoveTest_shouldThrowException(){
        assertThrows(IllegalArgumentException.class, ()-> board.isValidMove(9,Direction.DOWN));
    }

    @Test
    void isValidMoveTest(){
        assertTrue(board.isValidMove(0,Direction.DOWN));
        assertFalse(board.isValidMove(0,Direction.UP));
        assertFalse(board.isValidMove(0,Direction.RIGHT));
    }


    @Test
    void getValidMovesTest(){
        Set<Direction> validMoves = board.getValidMoves(0);
        assertEquals(validMoves,board.getValidMoves(0));
    }

    @Test
    void getPieceCountTest(){
        assertEquals(8,board.getPieceCount());
    }

    @Test
    void positionPropertyTest(){
        ObjectProperty<Position> position = board.positionProperty(0);
        assertEquals(position,board.positionProperty(0));
    }

    @Test
    void getPieceType(){
        assertEquals(PieceType.BLUE,board.getPieceType(0));
    }

    @Test
    void getPiecePositionTest(){
        Position position = new Position(0,0);
        assertEquals(position,board.getPiecePosition(0));
    }

    @Test
    void getPiecePositionsTest(){
        List<Position> positions = board.getPiecePositions();
        assertEquals(positions,board.getPiecePositions());
    }

    @Test
    void winPlayerTest(){
        Player player1 = new Player(PieceType.BLUE,"player1",false,false);
        Player player2 = new Player(PieceType.RED,"player2",false,false);
        board.winPlayer(player1,player2);
        assertFalse(player1.isWon());
        assertFalse(player2.isWon());
    }

    @Test
    void getPieceNumberTest(){
        Position position = new Position(0,0);
        Position position2 = new Position(2,2);
        assertEquals(0,board.getPieceNumber(position));
        assertEquals(-1,board.getPieceNumber(position2));
    }

    @Test
    void toStringTest(){
        assertEquals("[BLUE ObjectProperty [value: (0,0)],RED ObjectProperty [value: (0,1)],BLUE ObjectProperty [value: (0,2)],RED ObjectProperty [value: (0,3)],RED ObjectProperty [value: (4,0)],BLUE ObjectProperty [value: (4,1)],RED ObjectProperty [value: (4,2)],BLUE ObjectProperty [value: (4,3)]]",board.toString());
    }
}
