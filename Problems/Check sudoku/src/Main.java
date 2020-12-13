
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        boolean correct = true;
        if (n < 1 || n > 10) {
            //N out of range!
            correct = false;
        } else if (n == 1) {
            //special case, single element sudoku
            correct = scanner.nextInt() == 1;
        } else {
            //1 < N < 10 normal sudoku, N in range
            int[][] sudoku = new int[n * n][n * n];
            String strRow;
            scanner.nextLine(); //Move to next line
            for (int i = 0; i < n * n; i++) {
                strRow = scanner.nextLine();
                if (strRow.split(" ").length == n * n) {
                    //Correct length, convert strings to integer
                    for (int j = 0; j < n * n; j++) {
                        sudoku[i][j] = Integer.parseInt(strRow.split(" ")[j]);
                    }
                } else {
                    //Incorrect length, sudoku is not correct
                    correct = false;
                    break; //break out of for loop, i
                }
            }
            if (correct) {
                correct = checkSudoku(n, sudoku);
            }

        }
        if (correct) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    } //End of main method


    static boolean testRow(int n, int[] row) {
        int[] count = new int[n * n];
        boolean correct = true;
        for (int i = 0; i < n * n; i++) {
            if (row[i] > 0 && row[i] <= n * n) {
                //In range
                count[row[i] - 1]++;  // count[0] counts occurrences of 1, count[1] counts occurrences of 2,...
            } else {
                //Not in range
                correct = false;
                break;
            }

        }
        if (correct) {
            for (int i = 0; i < n * n; i++) {
                correct = count[i] == 1;
                if (!correct) {
                    break;
                }
            }
        }

        return correct;
    } //End of testRow method

    static boolean testCol(int n, int col, int[][] sudoku) {
        int[] count = new int[n * n];
        boolean correct = true;
        for (int i = 0; i < n * n; i++) {
            if (sudoku[i][col] > 0 && sudoku[i][col] <= n * n) {
                //In range
                count[sudoku[i][col] - 1]++;  // count[0] counts occurrences of 1, count[1] counts occurrences of 2,...
            } else {
                //Not in range
                correct = false;
                break;
            }

        }
        if (correct) {
            for (int i = 0; i < n * n; i++) {
                correct = count[i] == 1;
                if (!correct) {
                    break;
                }
            }
        }

        return correct;
    } //End of testCol method

    static boolean testSubArray(int startRow, int startCol, int[][] sudoku, int n) {
        int[] count = new int[n * n];
        for (int i = startRow; i < startRow + n; i++) {
            for (int j = startCol; j < startCol + n; j++) {
                count[sudoku[i][j] - 1]++;
            }
        }
        boolean correct = true;
        for (int i = 0; i < n * n; i++) {
            correct = count[i] == 1;
            if (!correct) {
                break;
            }
        }
        return correct;
    }
    static boolean checkSudoku(int n, int[][] sudoku) {
        boolean correct = true;
        // Check rows
        for (int i = 0; i < n * n; i++) {
            correct = testRow(n, sudoku[i]);
            if (!correct) {
                break;
            }
        }
        if (correct) {
            //Check columns
            for (int j = 1; j < n * n; j++) {
                correct = testCol(n, j, sudoku);
                if (!correct) {
                    break;
                }
            }
        }
        // Check sub arrays
        if (correct) {
            for (int startRow = 0; startRow < n * n; startRow += n) {
                for (int startCol = 0; startCol < n * n; startCol += n) {
                    correct = testSubArray(startRow, startCol, sudoku, n);
                    if (!correct) {
                        break;
                    }
                }
                if (!correct) {
                    break;
                }
            }

        }
        return correct;
    } //End of checkSudoku method
}
