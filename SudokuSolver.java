package BruteForceSudoku;

import java.util.ArrayList;

public class SudokuSolver {
	private int[][] sudoku;						//Our original sudoku Puzzle filled
												//	with zeroes in place of 
												//	blanks spaces
	private int w;								//The number of blocks wide a box
												//	in the sudoku puzzle is
	private int h;								//The number of blocks high a box
												//	in the sudoku puzzle is
	private int[] blanks;						//Our array filled with 1's initially
												//	that gets incremented 
												//	to go through every possible
												//	iteration of the sudoku puzzle 
	public static boolean solved = false;		//The controller that determines
												//	if the puzzle has been 
												//	solved successfully
	public static boolean unsolvable = false;	//The controller that determines if
												//	there is no valid correct input
												//	for the sudoku puzzle

	/**
	 * Constructor of the class SudokuSolver, which instantiates all the variables
	 * that we need the other methods to have.
	 * @param sudoku
	 * 				The original sudoku puzzle
	 * @param w
	 * 				The number of blocks wide a box in the sudoku puzzle is
	 * @param h
	 * 				The number of blocks high a box in the sudoku puzzle is
	 * @param blankspaces
	 * 				The number of blank squares in the sudoku puzzle
	 */
	public SudokuSolver(int[][] sudoku, int w, int h, int blankspaces) {
		this.sudoku = sudoku;
		this.w = w;
		this.h = h;
		blanks = populateBlanks(blankspaces);
	}

	/**
	 * Creates an array of a size equal to the number of blank spaces in the
	 * Sudoku Puzzle and return it.
	 * 
	 * @param blankspaces
	 *            the number of blank spaces in the Sudoku puzzle.
	 * @return An array with a size equal to the number of blank spaces filled
	 *         with 1's.
	 */
	private int[] populateBlanks(int blankspaces) {
		int[] tmp = new int[blankspaces];
		for (int i = 0; i < blankspaces; i++) {
			tmp[i] = 1;
		}
		return tmp;
	}

	/**
	 * Runs checkRows, checkCols, and checkBoxes, and turns on the global 
	 * solved boolean variable if all three return true. 
	 * @param array
	 * 		 	the array filled with the set of values that are being tested. 
	 */
	public void checkSolved(int[][] array) {
		if (checkRows(array)) {
			if (checkCols(array)) {
				if (checkBoxes(array)) {
					solved = true;
				}
			}
		}
	}
	/**
	 * Checks each column to see if they are legal in a sudoku puzzle.
	 * @param array
	 * 			the array filled with the set of values that are being tested. 
	 * @return true 
	 * 			if all columns are legal.
	 * 		   false
	 * 			otherwise. 
	 */
	private boolean checkCols(int[][] array) {
		ArrayList<Integer> numbers = new ArrayList<Integer>(w * h);
		for (int i = 0; i < w * h; i++) {
			for (int j = 0; j < w * h; j++) {
				if (numbers.contains(array[j][i])) {
					return false;
				}
				numbers.add(array[j][i]);
			}
			numbers.clear();
		}
		return true;
	}
	
	/**
	 * Checks each row to see if they are legal in a sudoku puzzle.
	 * @param array
	 * 			the array filled with the set of values that are being tested. 
	 * @return true 
	 * 			if all rows are legal.
	 * 		   false
	 * 			otherwise. 
	 */
	private boolean checkRows(int[][] array) {
		ArrayList<Integer> numbers = new ArrayList<Integer>(w * h);
		for (int i = 0; i < w * h; i++) {
			for (int j = 0; j < w * h; j++) {
				if (numbers.contains(array[i][j])) {
					return false;
				}
				numbers.add(array[i][j]);
			}
			numbers.clear();
		}
		return true;
	}

	/**
	 * Checks each box to see if they are legal in a sudoku puzzle.
	 * @param array
	 * 			the array filled with the set of values that are being tested. 
	 * @return true 
	 * 			if all boxes are legal.
	 * 		   false
	 * 			otherwise. 
	 */
	private boolean checkBoxes(int[][] array) {
		ArrayList<Integer> numbers = new ArrayList<Integer>(w * h);
		int ycount = 0;
		int xcount = 0;
		int j = 0;
		while(!(xcount >= w*h))
		{
			ycount = w * j;
			j++;
			if(ycount >= w*h)
			{
				ycount = 0;
				xcount = xcount + h;
				j = 0;
			}	
			numbers.clear();
			int xtmp = xcount;
			int ytmp = ycount;
			if(!(xcount >= w*h))
			{
				for(int i = 0; i < w*h ; i++)
				{
					if(numbers.contains(array[ytmp][xtmp]))
						return false;
					numbers.add(array[ytmp][xtmp]);
					if((i+1) % w == 0 && i+1 < w*h)
					{
						ytmp = ytmp - (w-1);
						xtmp++;
					}
					else
					{
						ytmp++;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Creates a copy of the sudoku puzzle and passes it off to be tested with new
	 * values in place of blanks.
	 * The main method that controls the rest of the testing process.
	 */
	public void Solver() {
		int[][] solvedsudoku = new int[w*h][w*h];
		while(solved == false && unsolvable == false)
		{
			int[][] tmp = new int[w * h][h * w];
			for (int y = 0; y < w * h; y++) {
				for (int x = 0; x < h * w; x++) {

					tmp[y][x] = sudoku[y][x];
				}
			}
			tmp = populateBlanks(tmp);
			checkSolved(tmp);
			incrementBlanks();
			solvedsudoku = tmp;
		}
		if(unsolvable)
		{
			printFail();
		}
		else{
		printPuzzle(solvedsudoku);
		}
	}
	
	/**
	 * Takes a copy of the original sudoku puzzle, and then fills it with the numbers
	 * that our brute force solver is currently on.
	 * 
	 * @param array
	 * 			a copy of the original sudoku puzzle. 
	 * @return tmp
	 * 			the array filled with values ready to be tested.
	 */
	private int[][] populateBlanks(int[][] array) {
		int[][] tmp = new int[w * h][h * w];
		for (int y = 0; y < w * h; y++) {
			for (int x = 0; x < h * w; x++) {

				tmp[x][y] = array[x][y];
			}
		}
		int j = 0;
		for (int i = 0; i < h * w; i++) {
			for (int k = 0; k < w * h; k++) {
				if (tmp[k][i] == 0) {
					tmp[k][i] = blanks[j];
					j++;
				}
			}
		}
		return tmp;
	}

	/**
	 * Increments a value in the array of values that are being tested in our brute
	 * 	force methodology.
	 * Determines if all values have been tested and ends the program 
	 * 	if there is no solution to the puzzle.
	 * If Sudoku is unsolvable, sets the global variable unsolvable to true
	 */
	private void incrementBlanks() {
		int counter = 0;
		for(int i = 0; i < blanks.length; i++)
		{
			if(blanks[i] == w*h)
			{
				blanks[i] = 1;
				counter++;
			}
			else{
				blanks[i]++;
				counter = 0;
				break;

			}
			if (counter == blanks.length)
			{
				unsolvable = true;	
			}
		}
	}
	
	/**
	 * Prints out the completed Sudoku puzzle when one is found.
	 * @param tmp
	 * 		the completed sudoku puzzle to be printed out.
	 */
	private void printPuzzle(int[][] tmp) {
		System.out.println();
		System.out.println("Original");
		for (int i = 0; i<w*h; i++){
			for (int j = 0; j<w*h; j++){

				System.out.print(sudoku[i][j] +" ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("Solved");
		for (int i = 0; i<w*h; i++){
			for (int j = 0; j<w*h; j++){

				System.out.print(tmp[i][j] +" ");
			}
			System.out.println();
		}
	}
	
	private void printFail()
	{
		System.out.println("Original:");
		for (int k = 0; k<w*h; k++){
			for (int j = 0; j<w*h; j++){

				System.out.print(sudoku[k][j] +" ");
			}
			System.out.println();
		}
		System.out.println("\nThere are no valid solutions for this Puzzle");
		
	}
}
