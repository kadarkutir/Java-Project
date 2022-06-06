package board;

/**
 * Represents eight directions.
 */
public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),

    DOWN(1, 0),

    LEFT(0, -1),

    UP_LEFT(-1, -1),

    UP_RIGHT(-1, 1),

    DOWN_RIGHT(1, 1),

    DOWN_LEFT(1, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * {@return the change in the row coordinate when moving to the
     * direction}
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * {@return the change in the column coordinate when moving to the
     * direction}
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * {@return the direction that corresponds to the coordinate changes
     * given}
     *
     * @param rowChange the change in the row coordinate
     * @param colChange the change in the column coordinate
     */
    public static Direction of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }
}
