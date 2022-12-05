- It is assumed that when the user is asked to input numbers for how tall and wide
  they'd like the maze to be that they choose only integers and that those integers are ≥ 5 but ≤ 20,
  otherwise a message will be displayed informing the user of their invalid input(s)
- To run: 
  - compile every file: `javac <file.java>`
  - `java MyMaze`

Example:
```
greshbasic@Nebula:~/Desktop/ComSci/csci1933/projects/project4$ java MyMaze
Please input how tall you'd like the maze to be: 
5 
Please input how wide you'd like the maze to be: 
10

|---|---|---|---|---|---|---|---|---|---|
|   |                   |       |       | 
|   |   |---|   |---|   |   |   |   |   |
|       |       |   |       |   |   |   | 
|   |---|   |---|   |---|---|   |   |   |
|       |   |               |       |   
|---|---|   |---|   |   |---|---|---|   |
        |       |   |                   | 
|---|   |---|   |   |---|---|---|---|---|
|               |                       | 
|---|---|---|---|---|---|---|---|---|---|

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

|---|---|---|---|---|---|---|---|---|---|
| * | *   *   *   *   * | *   * | *   * | 
|   |   |---|   |---|   |   |   |   |   |
| *   * | *   * |   | *   * | * | * | * | 
|   |---|   |---|   |---|---|   |   |   |
| *   * | * |               | *   * | * 
|---|---|   |---|   |   |---|---|---|   |
  *   * | *   * |   |                   | 
|---|   |---|   |   |---|---|---|---|---|
| *   *   *   * |                       | 
|---|---|---|---|---|---|---|---|---|---|
```
Notice: The only code that I had created was the MyMaze.java file, everything else was provided to me.
