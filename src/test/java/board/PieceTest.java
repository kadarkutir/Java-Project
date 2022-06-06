package board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    @Test
    void toStringTest(){
        Piece piece = new Piece(PieceType.BLUE,new Position(0,0));
        assertEquals("BLUE ObjectProperty [value: (0,0)]",piece.toString());
    }
}
