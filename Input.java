package BruteForceSudoku;

import java.util.*;
import java.io.*;

public class Input {
	
	/**
	 * Reads the file and interprets the data in the file into a 2D array,
	 * which it then passes to SudokuSolver to be solved.
	 * @param args
	 */
	public static void main(String[] args)
	    {
			Timer timer = new Timer();
			
			int blank = 0;
			int w = 0;
			int h = 0;
			int[][] sudoku = null;
			Scanner s = null;
			try {
				s = new Scanner(System.in);
				System.out.print("Enter a file location "
							   + "'C:\\location\\inputfile.txt': ");
				System.out.flush();
				String filename = s.nextLine();
				s = new Scanner(new BufferedReader(new FileReader(filename)));
				
				timer.start();
				
				h = s.nextInt();
				w = s.nextInt();
				int num = w * h;
				
				sudoku = new int[num][num];
				
				System.out.println("Row:" + h + " Col:" + w + "\n");
				while (s.hasNext()) {	
					for (int i = 0; i < num; i++) {					     
					       for (int j = 0; j < num; j++) {
					    	   int x = s.nextInt();
					    	   if(x == 0)
					    	   {
					    		   blank++;
					    	   }
					    	   sudoku[i][j] = x;
					       }
					}
				}

				}	
			catch (IOException  | NoSuchElementException e ) {
				System.err.println("Not a valid input file.\n" + e.getMessage());
				System.exit(1);
			}
			
			
			finally {
				if (s != null) 
					s.close();
				SudokuSolver solver = new SudokuSolver(sudoku, w, h, blank);
				solver.Solver();
				timer.stop();
				System.out.println("\n" + "Timer: " + timer.getDuration());
				
				
			}
			
	   }	
}