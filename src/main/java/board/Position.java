package board;

/**
 * A record for position.
 *
 * @param row the value of row
 * @param col the value of column
 */
public record Position(int row, int col) {

    /**
     * Returns a new position to the given direction.
     *
     * @param direction a given direction
     * @return a new position
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    /**
     * {@return the position whose vertical and horizontal distances from this
     * position are equal to the coordinate changes of the direction given}
     *
     * @param direction a given direction
     */
    public Position getPositionAt(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    public Position getUp() {
        return getPositionAt(Direction.UP);
    }

    public Position getRight() {
        return getPositionAt(Direction.RIGHT);
    }

    public Position getDown() {
        return getPositionAt(Direction.DOWN);
    }

    public Position getLeft() {
        return getPositionAt(Direction.LEFT);
    }

    public Position getUpRight() { return getPositionAt(Direction.UP_RIGHT); }

    public Position getUpLeft() { return getPositionAt(Direction.UP_LEFT); }

    public Position getDownRight() { return getPositionAt(Direction.DOWN_RIGHT); }

    public Position getDownLeft() { return getPositionAt(Direction.DOWN_LEFT); }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}

