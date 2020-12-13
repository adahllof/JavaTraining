package battleship;

public class Position {
    private int row;
    private int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void next(Position start, Position end) {
        if (start.getRow() < row) {
            row = start.getRow(); //If row above range move to start row
        } else if (row > end.getRow()) {
            row = end.getRow();
        }
        if (start.getCol() < col) {
            col = start.getCol(); //If col to the left of range move into range
        } else if (col > end.getCol()) {
            col = end.getCol();
        }
        if (col  < end.getCol()) {
            //The end of the row has not been reached
            col++;
        } else {
            row++;
            col = start.getCol();
        }
    }
    public static int[] rangeSize(Position start, Position end) {
        int[] size = new int[2];
        if (start.getRow() > end.getRow()) {
            size[0] = start.getRow() - end.getRow() + 1;
        } else {
            size[0] = end.getRow() - start.getRow() + 1;
        }
        if (start.getCol() > end.getCol()) {
            size[1] = start.getCol() - end.getCol() + 1;
        } else {
            size[1] = end.getCol() - start.getCol() + 1;
        }
        return size;
    }
    public boolean isGreater(Position p) {
        if (row > p.getRow()) {
            return true;
        } else return row == p.row && col >= p.col;
    }
    public boolean isEqualTo(Position p) {
        return row == p.row && col == p.col;
    }

    public boolean isLessOrEquals(Position p) {
        if (row < p.row) {
            return true;
        } else return row == p.row && col <= p.col;
    }
    public Position above() {
        return new Position(this.row - 1, this.col);
    }
    public Position below() {
        return new Position(this.row + 1, this.col);
    }
    public Position left() {
        return new Position(this.row, this.col - 1);
    }
    public Position right() {
        return new Position(this.row, this.col + 1);
    }
    public void goUp() {
        this.row--;
    }
    public void goDown() {
        this.row++;
    }
    public void goRight() {
        this.col++;
    }
    public void goLeft() {
        this.col--;
    }


}
