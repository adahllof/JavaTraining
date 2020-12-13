package battleship;

import java.io.IOException;
import java.util.Scanner;

public class GameBoard {
    private final int gameBoardSize;
    private final char[][] gameBoard;
    private final char fogOfWar = '~';
    private final char ship = 'O';
    private final char hit = 'X';
    private final char miss = 'M';

    private void clear() {
        for (int row = 0; row < gameBoardSize; row++ ) {
            for (int col = 0; col < gameBoardSize; col++) {
                this.gameBoard[row][col] = fogOfWar;
            }
        }
    }
    public GameBoard (int size) {
        //** Initiate all positions to fogOfWar symbol */
        this.gameBoardSize = size;
        this.gameBoard = new char[gameBoardSize][gameBoardSize];
        clear();
    }

    public void placeShips() {
        clear();
        System.out.println();
        printBoard(false, false);
        readShip(5, "Aircraft Carrier");
        System.out.println();
        printBoard(false, false);
        readShip(4, "Battleship");
        System.out.println();
        printBoard(false, false);
        readShip(3, "Submarine");
        System.out.println();
        printBoard(false, false);
        readShip(3, "Cruiser");
        System.out.println();
        printBoard(false,false);
        readShip(2, "Destroyer");
        System.out.println();
        printBoard(false,false);
        System.out.println();
    }
    public boolean fireAtEnemy(GameBoard enemyBoard) {
        Position p;
        p = readPosition();

        boolean hit = enemyBoard.fireAtPosition(p);
        if (hit) {
            enemyBoard.printSunk(p);
        } else {
            System.out.println("You missed!");
        }
        System.out.println();
        printBoard(false, false);
        return enemyBoard.countRemaining() <= 0;
    }

        public void fire() {
            Position p;
            p = readPosition();

            boolean hit = fireAtPosition(p);
            printBoard(true, false);
            System.out.println();
            if (hit && shipSunk(p) && countRemaining() > 0) {
                System.out.print("You sank a ship!");
            } else if (hit && shipSunk(p)) {
                System.out.print("You sank the last ship! You won. Congratulations!");
            } else if (hit) {
                System.out.print("You hit a ship!");
            } else {
                System.out.print("You missed!");
            }
            System.out.println();
            printBoard(false, false);



        }
    private void printSunk(Position p) {
        System.out.println();
        if (shipSunk(p) && countRemaining() > 0) {
            System.out.print("You sank a ship!");
        } else if (shipSunk(p)) {
            System.out.print("You sank the last ship! You won. Congratulations!");
        } else  {
            System.out.print("You hit a ship!");
        }
    }
    public void play() {
        Position p;

        do {
            p = readPosition();
            boolean hit = fireAtPosition(p);
            printBoard(true, false);
            System.out.println();
            if (hit && shipSunk(p) && countRemaining() > 0) {
                System.out.print("You sank a ship!");
            } else if (hit && shipSunk(p)) {
                System.out.print("You sank the last ship! You won. Congratulations!");
            } else if (hit) {
                System.out.print("You hit a ship!");
            } else {
                    System.out.print("You missed!");
            }
        } while (countRemaining() > 0 ) ;

            //Game over!  All ship was sunk!
        }


    private char getCharAtPosition(Position p) {
        if (!(p == null) && inRange(p)) {
            return gameBoard[p.getRow()][p.getCol()];
        } else {
            return (char) 0x0000;
        }
    }
    private void setCharAtPosition(Position p, char c) {
        if (!(p == null)) {
            gameBoard[p.getRow()][p.getCol()] = c;
        }
    }
    private boolean fireAtPosition(Position p) {
        if (!(p == null )) {
            switch (getCharAtPosition(p)) {
                case ship: //Hit
                    setCharAtPosition(p, hit);
                    return true;

                case fogOfWar: // Miss
                    setCharAtPosition(p, miss);
                    return false;

                case hit: //A ship was hit in a position were it was hit before
                    return true;

                default: // Should only happen when firing on the same position again
                    return false;
            }

        } else{
            return  false;  //Miss!
        }
    }
    private Position readPosition() {
        printBoard(true, false);
        System.out.println();

        Position p;
        boolean validInput;
        try (Scanner scanner = new Scanner(System.in)) {
            String input;
            do {
                System.out.println();
                input = scanner.nextLine();
                p = str2pos(input);
                validInput = inRange(p);
                if (!validInput) {
                    System.out.println();
                    System.out.println("Error You entered the wrong coordinates! Try again:");
                    System.out.println();
                }
            } while (!validInput);
            return p;
        } catch (Exception e) {
            System.out.println("Error! " + e.getMessage());
            return null;
        }
    }


    private boolean isTooClose(Position startOfShip, Position endOfShip) {
        /** Check if the ship is placed over or to close to another ship */
        Position start = new Position(startOfShip.getRow() - 1, startOfShip.getCol() - 1);
        Position end = new Position(endOfShip.getRow() + 1, endOfShip.getCol() + 1);
        boolean tooClose = false;
        for (Position current = start; current.isLessOrEquals(end); current.next(start, end)) {
            tooClose = isOccupied(current);
            if (tooClose) {
                break;
            }
        }
        return tooClose;
    }
    private boolean isOccupied(Position p) {
        if (inRange(p)) {
            return !(gameBoard[p.getRow()][p.getCol()] == fogOfWar);
        } else {
            return false;
        }
    }

    private void readShip(int sizeOfShip, String typeOfShip) {
        boolean validInput;
        Position start = new Position(-1,-1);
        Position end = new Position(-1,-1);
        int size = 0;
        String input;
        String[] coordinates;
        System.out.println("Enter the coordinates of the " + typeOfShip + " (" + sizeOfShip + " cells):");
        try (Scanner scanner = new Scanner(System.in)) {
            do {
                input = scanner.nextLine();
                coordinates = input.split("\\s+", 2);
                start = str2pos(coordinates[0]);
                end = str2pos(coordinates[1]);
                if (!end.isGreater(start)) {
                    Position temp; //Swap start end if incorrect order
                    temp = start;
                    start = end;
                    end = temp;
                }
                validInput = inRange(start) && inRange(end);
                if (validInput) {
                    int[] sizeArray;
                    sizeArray = Position.rangeSize(start, end);
                    if (sizeArray[0] == 1) {
                        size = sizeArray[1];
                    } else if (sizeArray[1] == 1) {
                        size = sizeArray[0];
                    } else {
                        validInput = false;
                        size = 0;
                    }
                }
                if (validInput) {
                    if (!(size == sizeOfShip)) {
                        validInput = false;
                        System.out.println();
                        System.out.println("Error! Wrong length of the " + typeOfShip + "! Try again!");
                    }
                } else {
                    System.out.println();
                    System.out.println("Error! Wrong ship location! Try again!");
                }
                if (validInput && isTooClose(start, end)) {
                    validInput = false;
                    System.out.println();
                    System.out.println("Error! The ship is too close to another one! Try again!");
                }
            } while (!validInput);

        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println();
            System.out.println("Input error: " + message);

        }
        for (Position p = start; p.isLessOrEquals(end); p.next(start, end)) {
            gameBoard[p.getRow()][p.getCol()] = ship;
        }
    }

    private static int char2row(char c) {
        return (int)c - 0x0041;
        }

    private static Position str2pos(String s) {
        Position p;
        if (!(s == null)) {
            int row = char2row(s.charAt(0)); //first char should be a letter signifying a column
            int col = Integer.parseInt(s.substring(1)) - 1;
            p = new Position(row, col);
        } else {
            p = null;
        }
        return p;
        }


    private boolean inRange(Position p) {
        if (!(p == null)) {
            if (p.getRow() < 0 || p.getRow() >= gameBoardSize) {
                return false;
            } else if (p.getCol() < 0 || p.getCol() >= gameBoardSize) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private int sizeOfShip(Position start, Position end) {
        //Return -1 if not a valid range
        int[] size;
        size = Position.rangeSize(start, end);
        if (size[0] == 1) {
            return size[1];
        } else if (size[1] == 1) {
            return size[0];
        } else {
            return 0; //Not a valid ship size!
        }
    }
    private void printSeparator() {
        for (int i = 0; i <= gameBoardSize; i++) {
            System.out.print("--");
        }
       System.out.println();
    }
    public void printBoard(boolean hideShips, boolean printSeparator) {
        System.out.println();
        //Print the column headers
        System.out.print("  ");
        for (int i = 1; i <= gameBoardSize; i++) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        //Print the rest of the board
        for (int row = 0; row < gameBoardSize; row++) {
            System.out.print((char) (row + 0x0041) + " ");
            for (int col = 0; col < gameBoardSize; col++) {
                if (hideShips && gameBoard[row][col] == ship) {
                    System.out.print(fogOfWar + " ");
                } else {
                    System.out.print(gameBoard[row][col] + " ");
                }
            }
            System.out.println();
        }
        if (printSeparator) printSeparator();
        else System.out.println();
    }
    private int countRemaining() {
        //Returns the number of remaining positions to hit before all ships have been sunk
        int count = 0;
        for (int row = 0; row < gameBoardSize; row++) {
            for (int col = 0; col < gameBoardSize; col++) {
                if (gameBoard[row][col] == ship) {
                   count++;
                }
            }
        }
        return count;
    }
    private boolean shipSunk(Position p) {
        // Check if a ship at position p has been sunk
        if (getCharAtPosition(p) == hit) {
            //continue checking
            Position pos = new Position(p.getRow(),p.getCol());
            boolean shipSunk = true;
            pos.goLeft();
            while (getCharAtPosition(pos) == hit || getCharAtPosition(pos) == ship) {
                shipSunk = hit == getCharAtPosition(pos);
                pos.goLeft();
            }
            pos.setPosition(p.getRow(), p.getCol());
            pos.goRight();
            while (shipSunk && (getCharAtPosition(pos) == hit || getCharAtPosition(pos) == ship)) {
                shipSunk = hit == getCharAtPosition(pos);
                pos.goRight();
            }
            pos.setPosition(p.getRow(), p.getCol());
            pos.goUp();
            while (shipSunk && (getCharAtPosition(pos) == hit || getCharAtPosition(pos) == ship)) {
                shipSunk = hit == getCharAtPosition(pos);
                pos.goUp();
            }
            pos.setPosition(p.getRow(), p.getCol());
            pos.goDown();
            while (shipSunk && (getCharAtPosition(pos) == hit || getCharAtPosition(pos) == ship)) {
                shipSunk = hit == getCharAtPosition(pos);
                pos.goDown();
            }
            return shipSunk;
            } else {
                return false;
            }
    }
}
