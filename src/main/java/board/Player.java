package board;

/**
 * Represents a player.
 */
public class Player {

    /**
     * The side of the player.
     */
    private PieceType side;

    /**
     * The name of the player.
     */
    private String name;

    /**
     * The player won or not.
     */
    private boolean won;

    /**
     * The player moved or not.
     */
    private boolean moved;

    public Player(PieceType side, String name, boolean won, boolean moved) {
        this.side = side;
        this.name = name;
        this.won = won;
        this.moved = moved;
    }

    /**
     *{@return the name of the player}
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the player.
     *
     * @param name a name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *{@return whether a player has won or not}
     */
    public boolean isWon() {
        return won;
    }

    /**
     * Sets the won parameter of the player.
     *
     * @param won a boolean value
     */
    public void setWon(boolean won) {
        this.won = won;
    }

    /**
     *{@return whether a player has moved or not}
     */
    public boolean isMoved() {
        return moved;
    }

    /**
     * Sets the move parameter of the player.
     *
     * @param moved a boolean value
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
