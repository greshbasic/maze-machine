// Names:
// x500s:

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

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

    /* TODO: Create a new maze using the algorithm found in the writeup. */
    public static MyMaze makeMaze(){
        System.out.println("Please input how tall you'd like the maze to be: ");
        Scanner numRowsScanner = new Scanner(System.in);
        int numRows = numRowsScanner.nextInt();
        System.out.println("Please input how wide you'd like the maze to be: ");
        Scanner numColsScanner = new Scanner(System.in);
        int numCols = numColsScanner.nextInt();
        
        // if a valid num of rows and cols
        if(numRows >= 5 && numRows <= 20 && numCols >= 5 && numCols <= 20){

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
            int[] adding = new int[2];
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
            for(int i = 0; i < numRows; i++){
                for(int j = 0; j < numCols; j++){
                   myMaze.maze[i][j].setVisited(false);
                }
            }
            return myMaze;
        }
        return null;
    }
           
    /* TODO: Print a representation of the maze to the terminal */
    public void printMaze() {
        // two strings: current row, and the border row below it, so if no border add " " to border row string, else add "-"
        // *** consider doing a printLin() method ala the one you did for chess project :D ***
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
        //     a = printRow()
        //     System.out.println(a[0])
        //     System.out.println(a[1])
        // if this is start row, don't put left border there |
        // loop through a row at a time
        //     if(maze[i][j].getVisited() == true){ add a " * "}
        //         if(maze[i][j].getBottom == true){ print a bottom border for it}
        //         if (maze[i][j].getRight == true){ print a right border for it}
        //         gg go next
    }

    public String[] printRow(int rowNumber){
        int cols = this.numCols;
        String[] rows = new String[2];
        String cellRow = "";
        String wallRow = "|";
        if(rowNumber != this.startRow){
            cellRow += "| ";
        } else {
            cellRow += "  ";
        }

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
        if(rowNumber != this.endRow){
            //cellRow += "|";
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

    /* TODO: Solve the maze using the algorithm found in the writeup. */


    /*
     I had the same issue. When you are checking for available neighbor cells, are you checking 
     if there is a wall between the current cell and the cell you are trying to visit?
     */
    public void solveMaze() {
        QGen<int[]> queue = new Q1Gen<int[]>();
        int[] adding = {startRow, 0};
        queue.add(adding);
        while(queue.length() > 0){
            adding = queue.remove();
            this.maze[adding[0]][adding[1]].setVisited(true);
            int currentRow = adding[0];
            int currentCol = adding[1];
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
        int currentRow = a[0];
        int currentCol = a[1];
        int elementCount = 0;
        ArrayList<String> possibleMoves = new ArrayList<>();
        // checking left
        if(currentCol - 1 >= 0 && currentCol < numCols && this.maze[currentRow][currentCol - 1].getVisited() == false){
            possibleMoves.add("L");
            elementCount += 1;
        }
        // checking right
        if(currentCol >= 0 && currentCol + 1 < numCols && this.maze[currentRow][currentCol + 1].getVisited() == false){
            possibleMoves.add("R");
            elementCount += 1;
        }
        // checking Above
        if(currentRow - 1 >= 0 && currentRow < numRows && this.maze[currentRow -1][currentCol].getVisited() == false){
            possibleMoves.add("A");
            elementCount += 1;
        }
        // checking Below
        if(currentRow >= 0 && currentRow + 1 < numRows && this.maze[currentRow+1][currentCol].getVisited() == false){
            possibleMoves.add("B");
            elementCount += 1;
        }

        if(elementCount == 0){
            return null;
        }
        int pos = new Random().nextInt(elementCount);
        String choice = possibleMoves.get(pos);
      
        return choice;
    }
    public static void main(String[] args){
        MyMaze hehe = makeMaze();
        hehe.printMaze();
        System.out.println("\n-------------------------\n");
        hehe.solveMaze();
        hehe.printMaze();

    }
}
