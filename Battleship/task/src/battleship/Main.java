package battleship;


import java.util.Scanner;

public class Main
{
    public static Scanner sc = new Scanner (System.in);
    public static void main (String[]args)
    {
        // clean gameField
        char[][] fieldOfPlayer1 = new char[10][10];
        char[][] p2Area = new char[10][10];
        for (int i = 0; i < 10; i++)
        {
            for (int j = 0; j < 10; j++)
            {
                fieldOfPlayer1[i][j] = '~';
                p2Area[i][j] = '~';
            }
        }


        battleReadinessPvP(fieldOfPlayer1, p2Area);
        fullBattle(fieldOfPlayer1, p2Area);


    }

    public static void fullBattle(char[][] p1Area, char[][] p2Area) {
        while(true) {
            gameFieldAfterEachShot(p2Area, p1Area);
            System.out.println("Player 1, it's your turn:");
            boolean winner;

            if (shotResultsAndWinner(p2Area)) winner = true;
            else winner = false;

            if (winner) {
                System.out.println("Player 1, you sank the last ship. You won. Congratulations!");
                break;
            }
            promptEnterKey();
            // now it is turn of Player 2
            gameFieldAfterEachShot(p1Area, p2Area);
            System.out.println("Player 2, it's your turn:");
            winner = shotResultsAndWinner(p1Area);
            if (winner) {
                System.out.println("Player 2, you sank the last ship. You won. Congratulations!");
                break;
            }
            promptEnterKey();
        }

    }

    public static boolean shotResultsAndWinner(char[][] field) {
        int[] shotHit = checkerShotHitOrMissed(field);
        boolean hit = shotHit[0] == 1;
        boolean shipSunk = oneShipSunk(field, shotHit);

        // terminate if all ships sunk
        if (allSunk(field)) {
            return true;
        } else if (hit && !shipSunk){
            // if you sank a shiP
            System.out.println();
            System.out.println("You sank a ship!");
        } else if (hit) {
            System.out.println();
            System.out.println("You hit a ship!");
        } else {
            System.out.println();
            System.out.println("You missed!");
        }
        return false;
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void battleReadinessPvP(char[][] p1Field, char[][] p2Field) {
        // player 1 places ships
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        readyForShot(p1Field);
        //after Enter pressed, player 2 places ships
        promptEnterKey();
        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        readyForShot(p2Field);
        promptEnterKey();
        System.out.println("The game starts!");
        System.out.println();
    }

    public static boolean oneShipSunk(char[][] fd, int[] arr) {
        int n = arr[1];
        int m = arr[2];
        boolean exsistInUpperRow = false;
        boolean exsistInNextCol = false;
        boolean exsistInLowerRow = false;
        boolean exsistInBeforeCol = false;
        if (n != 0 && fd[n - 1][m] == 'O') {
            exsistInUpperRow = true;
        }
        if (m != 9 && fd[n][m + 1] == 'O') {
            exsistInNextCol = true;
        }
        if (n != 9 && fd[n + 1][m] == 'O') {
            exsistInLowerRow = true;
        }
        if (m != 0 && fd[n][m - 1] == 'O') {
            exsistInBeforeCol = true;
        }
        boolean res = exsistInUpperRow || exsistInNextCol || exsistInLowerRow || exsistInBeforeCol == true ? true : false;
        return res;
    }

    public static boolean allSunk(char[][] field) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (field[i][j] == 'O')
                    return false;
            }
        }
        return true;
    }

    public static void gameFieldAfterEachShot (char[][]opponent, char[][] yourField)
    {
        System.out.println();
        char[] col = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
        System.out.print (" ");
        for (int k = 1; k < 11; k++)
        {
            System.out.printf (" %d", k);
        }
        System.out.println ();
        for (int i = 0; i < 10; i++)
        {
            System.out.print (col[i]);

            for (int j = 0; j < 10; j++)
            {
                if (opponent[i][j] == 'O') {
                    System.out.print (" ~");
                } else {
                    System.out.printf (" %s", opponent[i][j]);
                }
            }
            System.out.println ();
        }
        System.out.println("---------------------");
        gameFieldDisplay(yourField);
    }

    public static int[] inputForShot() {
        String str = sc.next();
        try {
            int row = str.charAt(0);
            int col = Integer.parseInt(str.substring(1));
            if (col < 11 && row > 64 && row < 75) {
                return new int[] {row - 65, col - 1};
            } else {
                return new int[]{-1};
            }
        } catch (Exception e) {
            return new int[] {-1};
        }
    }

    public static int[] checkerShotHitOrMissed(char[][] field) {
        //enter target
        int[] shot = inputForShot();
        boolean target = true;
        while (target) {
            if (shot[0] == -1) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                shot = inputForShot();
            } else {
                target = false;
            }
        }

        //mark target with 'X' if it hits else 'M'
        if (field[shot[0]][shot[1]] == 'O' || field[shot[0]][shot[1]] == 'X') {
            //if you hit a ship returns 'X'
            field[shot[0]][shot[1]] = 'X';
            //return 1 at intArrayIndexZero if hit
            return new int[] {1, shot[0], shot[1]};
        } else {
            //if you missed return 'M'
            field[shot[0]][shot[1]] = 'M';
            //return 0 at intArrayIndexZero if missed
            return new int[] {0, shot[0], shot[1]};
        }
    }

    public static void readyForShot(char[][] gameField) {
        // welcome output gameField
        gameFieldDisplay (gameField);

        //input for Aircraft Carrier
        aircraftArea (gameField);
        gameFieldDisplay (gameField);
        //input for Battleship
        battleshipArea (gameField);
        gameFieldDisplay (gameField);
        //input for Submarine
        submarineArea (gameField);
        gameFieldDisplay (gameField);
        // input for cruiser fg
        cruiserArea (gameField);
        gameFieldDisplay (gameField);
        //input for Destroyer
        destroyerShipArea (gameField);
        gameFieldDisplay (gameField);
    }

    public static boolean checksTooNearLocation (char[][] game, int[] valid)
    {
        //checks its location too near or same
        int colOrRow = valid[0];
        int startPos = valid[1];
        int lastPos = valid[2];
        if (colOrRow > 11) {
            //if ship placed horizontal
            colOrRow -= 65;
            startPos -= 1;
            lastPos -= 1;
            //checks for same row but the column after the last and before the star
            if (lastPos == 9) {
                if (game[colOrRow][startPos - 1] == 'O')
                    return true;
            } else if (startPos == 0) {
                if (game[colOrRow][lastPos + 1] == 'O')
                    return true;
            } else if (game[colOrRow][startPos - 1] == 'O') {
                return true;
            } else if (game[colOrRow][lastPos + 1] == 'O') {
                return true;
            }
            //check for one upper row
            if (colOrRow != 0) {
                while (startPos <= lastPos) {
                    if (game[colOrRow - 1][startPos] == 'O') {
                        return true;
                    }
                    startPos++;
                }
            }
            //checks for one LOWER row
            if (colOrRow != 9) {
                while (startPos <= lastPos) {
                    if (game[colOrRow + 1][startPos] == 'O') {
                        return true;
                    }
                    startPos++;
                }
            }
        } else {
            //if ship placed vertical
            // makes coordinates exactly same for board
            colOrRow = valid[0] - 1;
            startPos = valid[1] - 65;
            lastPos = valid[2] - 65;

            //checks same column edges
            if (lastPos == 9) {
                if (game[startPos - 1][colOrRow] == 'O')
                    return true;
            } else if (startPos == 0) {
                if (game[lastPos + 1][colOrRow] == 'O')
                    return true;
            } else if (game[startPos - 1][colOrRow] == 'O') {
                return true;
            } else if (game[lastPos + 1][colOrRow] == 'O') {
                return true;
            }

            //checks for the next column
            if (colOrRow != 9) {
                while (startPos <= lastPos) {
                    if (game[startPos][colOrRow + 1] == 'O') {
                        return true;
                    }
                    startPos++;
                }
            }
            //checks for the before column
            if (colOrRow != 0) {
                while (startPos <= lastPos) {
                    if (game[startPos][colOrRow] == 'O') {
                        return true;
                    }
                    startPos++;
                }
            }
        }

        return false;

    }

    public static int[] inputCoordinates ()
    {
        String str = sc.next();
        try
        {
            int col1 = (char) str.charAt (0);
            int row1 = Integer.parseInt (str.substring (1));
            if (col1 >= 65 && col1 <= 74 && row1 <= 10)
            {
                return new int[]
                        {
                                col1, row1};
            }
            else
            {
                System.out.println ("Mismatch");
                return new int[]
                        {
                                -1, -1};
            }

        } catch (Exception e)
        {
            System.out.println ("Error");

        }
        return new int[]
                {
                        -1, -1};
    }

    public static int[] lengthMatch (int[]arr1, int[]arr2)
    {
        if (arr1[0] == arr2[0])
        {
            return new int[]
                    {
                            arr1[0], Math.min (arr1[1], arr2[1]), Math.max (arr1[1], arr2[1])};
        }
        else if (arr1[1] == arr2[1])
        {
            return new int[]
                    {
                            arr1[1], Math.min (arr1[0], arr2[0]), Math.max (arr1[0], arr2[0])};
        }
        else
        {
            return new int[]
                    {
                            -1};
        }
    }

    public static void gameFieldDisplay (char[][]field)
    {
        char[] col = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
        System.out.print (" ");
        for (int k = 1; k < 11; k++)
        {
            System.out.printf (" %d", k);
        }
        System.out.println ();
        for (int i = 0; i < 10; i++)
        {
            System.out.print (col[i]);

            for (int j = 0; j < 10; j++)
            {
                System.out.printf (" %s", field[i][j]);
            }
            System.out.println ();
        }
        System.out.println();
    }

    public static void aircraftArea (char[][]gameField)
    {
        boolean borderly = true;
        System.out.println
                ("Enter the coordinates of the Aircraft Carrier (5 cells):");
        while (borderly)
        {
            int[] locat1 = inputCoordinates ();
            int[] locat2 = inputCoordinates ();
            int[] validLoc = lengthMatch (locat1, locat2);

            if (validLoc[0] == -1)
            {
                System.out.println ("Error! Wrong ship location! Try again:");
                borderly = true;
            }
            else if (validLoc[2] - validLoc[1] != 4)
            {
                System.out.println
                        ("Error! Wrong length of the Aircraft Carrier! Try again:");
                borderly = true;
            }
            else if (checksTooNearLocation(gameField, validLoc))
            {
                //checks for not near and same location
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
                borderly = true;
            }
            else
            {
                //puts Aircraft to gameField and puts O (char)
                int initialPos = validLoc[1];

                if (validLoc[0] > 11)
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[validLoc[0] - 65][initialPos - 1] = 'O';
                        initialPos++;
                    }
                }
                else
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[initialPos - 65][validLoc[0] - 1] = 'O';
                        initialPos++;
                    }
                }
                borderly = false;
            }
        }

    }

    public static void battleshipArea (char[][]gameField)
    {
        boolean borderly = true;
        System.out.println ("Enter the coordinates of the Battleship (4 cells):");
        while (borderly)
        {
            int[] locat1 = inputCoordinates ();
            int[] locat2 = inputCoordinates ();
            int[] validLoc = lengthMatch (locat1, locat2);

            if (validLoc[0] == -1)
            {
                System.out.println ("Error! Wrong ship location! Try again:");
                borderly = true;
            }
            else if (validLoc[2] - validLoc[1] != 3)
            {
                System.out.println
                        ("Error! Wrong length of the Battleship! Try again:");
                borderly = true;
            } else if (checksTooNearLocation(gameField, validLoc)) {
                //checks for not near and same location
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
                borderly = true;
            }
            else
            {
                //puts Battleship to gameField and puts O (char)
                int initialPos = validLoc[1];
                //if ship placed GORIZONTAL length
                if (validLoc[0] > 11)
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[validLoc[0] - 65][initialPos - 1] = 'O';
                        initialPos++;
                    }
                }
                else
                { // if ship placed VERTICAL length
                    while (initialPos <= validLoc[2])
                    {
                        gameField[initialPos - 65][validLoc[0] - 1] = 'O';
                        initialPos++;
                    }
                }
                borderly = false;
            }
        }

    }

    public static void submarineArea (char[][]gameField)
    {
        boolean borderly = true;
        System.out.println ("Enter the coordinates of the Submarine (3 cells):");
        while (borderly)
        {
            int[] locat1 = inputCoordinates ();
            int[] locat2 = inputCoordinates ();
            int[] validLoc = lengthMatch (locat1, locat2);

            if (validLoc[0] == -1)
            {
                System.out.println ("Error! Wrong ship location! Try again:");
                borderly = true;
            }
            else if (validLoc[2] - validLoc[1] != 2)
            {
                System.out.println
                        ("Error! Wrong length of the Submarine! Try again:");
                borderly = true;
            } else if (checksTooNearLocation(gameField, validLoc)) {
                //checks for not near and same location
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
                borderly = true;
            }
            else
            {
                //puts Submarine to gameField and puts O (char)
                int initialPos = validLoc[1];

                if (validLoc[0] > 11)
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[validLoc[0] - 65][initialPos - 1] = 'O';
                        initialPos++;
                    }
                }
                else
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[initialPos - 65][validLoc[0] - 1] = 'O';
                        initialPos++;
                    }
                }
                borderly = false;
            }
        }

    }

    public static void cruiserArea (char[][]gameField)
    {
        boolean borderly = true;
        System.out.println ("Enter the coordinates of the Cruiser (3 cells):");
        while (borderly)
        {
            int[] locat1 = inputCoordinates ();
            int[] locat2 = inputCoordinates ();
            int[] validLoc = lengthMatch (locat1, locat2);

            if (validLoc[0] == -1)
            {
                System.out.println ("Error! Wrong ship location! Try again:");
                borderly = true;
            }
            else if (validLoc[2] - validLoc[1] != 2)
            {
                System.out.println ("Error! Wrong length of the Cruiser! Try again:");
                borderly = true;
            } else if (checksTooNearLocation(gameField, validLoc)) {
                //checks for not near and same location
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
                borderly = true;
            }
            else
            {
                //puts Cruiser to gameField and puts O (char)
                int initialPos = validLoc[1];

                if (validLoc[0] > 11)
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[validLoc[0] - 65][initialPos - 1] = 'O';
                        initialPos++;
                    }
                }
                else
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[initialPos - 65][validLoc[0] - 1] = 'O';
                        initialPos++;
                    }
                }
                borderly = false;
            }
        }

    }

    public static void destroyerShipArea (char[][]gameField)
    {
        boolean borderly = true;
        System.out.println ("Enter the coordinates of the Destroyer (2 cells):");
        while (borderly)
        {
            int[] locat1 = inputCoordinates ();
            int[] locat2 = inputCoordinates ();
            int[] validLoc = lengthMatch (locat1, locat2);

            if (validLoc[0] == -1)
            {
                System.out.println ("Error! Wrong ship location! Try again:");
                borderly = true;
            }
            else if (validLoc[2] - validLoc[1] != 1)
            {
                System.out.println
                        ("Error! Wrong length of the Destroyer! Try again:");
                borderly = true;
            } else if (checksTooNearLocation(gameField, validLoc)) {
                //checks for not near and same location
                System.out.println("Error! You placed it too close to another one. Try again:");
                System.out.println();
                borderly = true;
            }
            else
            {
                //puts Destroyer to gameField and puts O (char)
                int initialPos = validLoc[1];

                if (validLoc[0] > 11)
                {
                    while (initialPos <= validLoc[2])
                   {
                        gameField[validLoc[0] - 65][initialPos - 1] = 'O';
                        initialPos++;
                    }
                }
                else
                {
                    while (initialPos <= validLoc[2])
                    {
                        gameField[initialPos - 65][validLoc[0] - 1] = 'O';
                        initialPos++;
                    }
                }
                borderly = false;
            }
        }

    }
}




