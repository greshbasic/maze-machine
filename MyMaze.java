import java.util.Random;
import java.util.Scanner;

public class MyMaze{
    Cell[][] maze;
    int startRow;
    int endRow;
    int numRows;
    int numCols;

    public MyMaze(int rows, int cols, int startRow, int endRow) {
        this.numRows = rows;
        this.numCols = cols;
        this.startRow = startRow;
        this.endRow = endRow;
        this.maze = new Cell[rows][cols];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                maze[i][j] = new Cell();
            }
        }
    }

    public static MyMaze makeMaze(){
        System.out.println("Please input how tall you'd like the maze to be: ");
        Scanner numRowsScanner = new Scanner(System.in);
        int numRows = numRowsScanner.nextInt();
        System.out.println("Please input how wide you'd like the maze to be: ");
        Scanner numColsScanner = new Scanner(System.in);
        int numCols = numColsScanner.nextInt();
        
        // if a valid num of rows and cols
        if(numRows >= 5 && numRows <= 20 && numCols >= 5 && numCols <= 20){
            numRowsScanner.close();
            numColsScanner.close();

            // randomly deciding the end and start rows of this maze
            Random rand = new Random();
            int endRow = rand.nextInt(numRows);
            int startRow = rand.nextInt(numRows);

            // instantiaion of MyMaze object
            MyMaze myMaze = new MyMaze(numRows, numCols, startRow, endRow);

            // generation of the maze
            StackGen<int[]> stack = new Stack1Gen<int[]>();
            int[] currentTop = new int[2];
            currentTop[0] = startRow;
            currentTop[1] = 0;
            myMaze.maze[currentTop[0]][currentTop[1]].setVisited(true);
            stack.push(currentTop);
            while(stack.isEmpty() == false){
                currentTop = stack.top(); 
                String randomMove = myMaze.pickVisitable(currentTop, numRows, numCols);
                if(randomMove == null){
                    stack.pop();
                }
                // if randomMove equals null that means there were no visitable neighbors, thus pops and moves on
                if(randomMove != null){
                    if(randomMove.equals("L")){
                        // left stuff 
                        int[] addLeft = {currentTop[0], currentTop[1] - 1};
                        stack.push(addLeft);
                        myMaze.maze[addLeft[0]][addLeft[1]].setVisited(true);
                        myMaze.maze[addLeft[0]][addLeft[1]].setRight(false);
                    }
                    if(randomMove.equals("R")){
                        // right stuff
                        int[] addRight = {currentTop[0], currentTop[1] + 1};
                        stack.push(addRight);
                        myMaze.maze[addRight[0]][addRight[1]].setVisited(true);
                        myMaze.maze[currentTop[0]][currentTop[1]].setRight(false);
                    }
                    if(randomMove.equals("B")){
                        // below stuff 
                        int[] addBelow = {currentTop[0] + 1, currentTop[1]};
                        stack.push(addBelow);
                        myMaze.maze[addBelow[0]][addBelow[1]].setVisited(true);
                        myMaze.maze[currentTop[0]][currentTop[1]].setBottom(false);
                    }
                    if(randomMove.equals("A")){
                        // above stuff 
                        int[] addAbove = {currentTop[0] - 1, currentTop[1]};
                        stack.push(addAbove);
                        myMaze.maze[addAbove[0]][addAbove[1]].setVisited(true);
                        myMaze.maze[addAbove[0]][addAbove[1]].setBottom(false);
                    }
                }

            }
            // returning all cells back to their original unvisited state
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                   myMaze.maze[i][j].setVisited(false);
                }
            }
            return myMaze;
        }
        // if invalid input returns null
        numRowsScanner.close();
        numColsScanner.close();
        return null;
    }
           
    public void printMaze() {
        // prints the maze by first setting up the top boundary then uses printRow() helper method for each row
        String maze = "|";

        for(int i = 0; i < numCols; i++){
            maze += "---|";
        }
        System.out.println(maze);
        for(int i = 0; i < numRows; i++){
            String [] a = this.printRow(i);
            System.out.println(a[0]);
            System.out.println(a[1]);
        }
    }

    public String[] printRow(int rowNumber){
        // splits each row into two "sub-rows," cellRow being where the *'s and left and right walls will
        // be seen, and wallRow where only the top and bottom walls will be seen
        int cols = this.numCols;
        String[] rows = new String[2];
        String cellRow = "";
        String wallRow = "|";

        // leaving the starting cell's left wall open
        if(rowNumber != this.startRow){
            cellRow += "| ";
        } else {
            cellRow += "  ";
        }

        // looping through and adding *'s and walls to respective arrays
        for(int i = 0; i < cols; i++){
            if(this.maze[rowNumber][i].getVisited()){
                cellRow += "* ";
            } else {
                cellRow += "  ";
            }
            if(this.maze[rowNumber][i].getRight() == true){
                if(rowNumber == this.endRow && i == numCols - 1){
                    
                } else {
                    cellRow += "| ";
                }
            } else {
                cellRow += "  ";
            }
            if(this.maze[rowNumber][i].getBottom()){
                wallRow += "---|";
            } else {
                wallRow += "   |";
            }
        }
        rows[0] = cellRow;
        rows[1] = wallRow;
        if(rowNumber == numRows - 1){
            String temp = "|";
            for(int i = 0; i < numCols; i++){
                temp += "---|";
            }
            rows[1] = temp;
        }
        return rows;
    }

    public void solveMaze() {
        QGen<int[]> queue = new Q1Gen<int[]>();
        int[] adding = {startRow, 0};
        queue.add(adding);
        while(queue.length() > 0){
            adding = queue.remove();
            this.maze[adding[0]][adding[1]].setVisited(true);
            int currentRow = adding[0];
            int currentCol = adding[1];
            
            // if location that is about to be added to queue is actually the end,
            // it breaks, else it continues and checks all possible moves.
            // the possibility of a move is dependant on it being located next to the current cell,
            // it being unvisited, and there being no wall between the current cell and the neighbor cell
            if(adding[0] == endRow && adding[1] == numCols - 1){
                break;
            } else {
                if(currentCol - 1 >= 0 && currentCol < numCols && this.maze[currentRow][currentCol - 1].getVisited() == false && this.maze[currentRow][currentCol - 1].getRight() == false){
                    int[] addLeft = {currentRow, currentCol - 1};
                    queue.add(addLeft);
                }
                // checking right
                if(currentCol >= 0 && currentCol + 1 < numCols && this.maze[currentRow][currentCol + 1].getVisited() == false && this.maze[currentRow][currentCol].getRight() == false){
                    int[] addRight = {currentRow, currentCol + 1};
                    queue.add(addRight);
                }
                // checking below
                if(currentRow + 1 >= 0 && currentRow + 1 < numRows && this.maze[currentRow + 1][currentCol].getVisited() == false && this.maze[currentRow][currentCol].getBottom() == false){
                    int[] addBelow = {currentRow + 1, currentCol};
                    queue.add(addBelow);
                }
                // checking above
                if(currentRow > 0 && currentRow - 1 < numRows && this.maze[currentRow-1][currentCol].getVisited() == false && this.maze[currentRow-1][currentCol].getBottom() == false){
                    int[] addAbove = {currentRow - 1, currentCol};
                    queue.add(addAbove);
                }

            }
        }

    }

    public String pickVisitable(int[] a, int numRows, int numCols){
        // this method is what allows for a random selection of VALID moves
        // by adding letters representing directions to an array and randomly choosing
        // for that array after nulls are removed

        int currentRow = a[0];
        int currentCol = a[1];
        int elementCount = 0;
        String[] possibleMoves = new String[4];
        // checking left
        if(currentCol - 1 >= 0 && currentCol < numCols && this.maze[currentRow][currentCol - 1].getVisited() == false){
            possibleMoves[0] = "L";
            elementCount += 1;
        }
        // checking right
        if(currentCol >= 0 && currentCol + 1 < numCols && this.maze[currentRow][currentCol + 1].getVisited() == false){
            possibleMoves[1] = "R";
            elementCount += 1;
        }
        // checking Above
        if(currentRow - 1 >= 0 && currentRow < numRows && this.maze[currentRow -1][currentCol].getVisited() == false){
            possibleMoves[2] = "A";
            elementCount += 1;
        }
        // checking Below
        if(currentRow >= 0 && currentRow + 1 < numRows && this.maze[currentRow+1][currentCol].getVisited() == false){
            possibleMoves[3] = "B";
            elementCount += 1;
        }

        // elementCount being 0 means no possible moves, no need to continue
        if(elementCount == 0){
            return null;
        }
        String[] noNulls = new String[elementCount];
        int tracker = 0;
        
        // removal of nulls
        for(int i = 0; i < 4; i++){
            if(possibleMoves[i] != null){
                noNulls[tracker] = possibleMoves[i];
                tracker += 1;
            }
        }

        int pos = new Random().nextInt(elementCount);
        String choice = noNulls[pos];

        return choice;
    }
    public static void main(String[] args){
        // try catch block related to an invalid input and the NPE that one would cause
        boolean valid = false;
        while(valid == false){
            try {
                MyMaze maze = makeMaze();
                valid = true;
                maze.printMaze();
                System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                maze.solveMaze();
                maze.printMaze();
            } catch (Exception e){
                System.out.println("INVALID - Your dimensions need to be integers that are ≥ 5 but ≤ 20");
            }
        }
    }
}
}

