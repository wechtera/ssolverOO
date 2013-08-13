//Trying to create a sudoku solver using backtracking
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class ssolver{
	private static int recDepth = 0;  //recursion depth
	private static int [][] solution = new int [9][9];  //cheating around it exitting incorectly
	public static void main(String [] args){
		//lets read it into a double array
		int [] [] puzzle = new int [9][9];
		puzzle = readin();
		printPuzzle(puzzle);
		solve(puzzle);
		System.out.println("========= SOLVED =============");
		printPuzzle(puzzle);
	}
	
	public static void printPuzzle(int[][]puzzle){
		System.out.print("\n_________________________");
		for(int i = 0; i<9; i++){
			System.out.print("\n| ");
			for(int j = 0; j<9; j++){
				System.out.print(puzzle[i][j]);
				System.out.print(' ');
				if((j+1)%3 == 0)
					System.out.print("| ");
			}
			if((i+1)%3 == 0)
				System.out.print("\n|-------+-------+-------|");
		}
		System.out.println();

	}


	//reading in puzzle
	public static int[][] readin(){
		int[] [] puzzle = new int [9][9];
		BufferedReader reader = null;//trying to fix reader not found error may not work	
		try{
			reader = new BufferedReader(new FileReader("/Users/wechtera/Desktop/Practiceprograms/ssolverOO/ssolverin.txt"));//source file
		}catch(FileNotFoundException fnf){System.out.println("file not found skank");}
			int input;  //input into the puzzle
			
			for(int i = 0; i<9; i++){  //rows
				System.out.println("\n______Row " + i + "_________\n");
				for(int j = 0; j<9; j++){  //columns
					System.out.print((i)+" x "+(j)+":  ");
					try{
						String in = reader.readLine();
						in = in.substring(0, 1);
						if(in.equals(".")){
							System.out.println("caughtca");
							input = 0;
						}
						else{
							input = Integer.parseInt(in);
						}
						puzzle[i][j]= input;
					}
					catch(IOException e){e.printStackTrace();}
				}
			}
		
		return puzzle;		
	}
	public static boolean solve(int[][]puzzle){
		//using backtracking
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(puzzle[i][j]!=0){
					continue;
				}
				for(int n = 1; n<10; n++){  //testing posabilities
					if(isTrue(puzzle, i, j, n)){
						puzzle[i][j] = n;
						if(solve(puzzle))
							return true;
						else{
							puzzle[i][j] = 0;  //backtrack here
						}
					}
				}
				return false; //no possibilities for the empty space
			}
		}
		return true; //hits end of the puzzle
	}		
						
	//checks to see if an int possible in the puzzle at space [i][j] is acceptable
	public static boolean isTrue(int [][]puzzle, int y, int x, int possible){
		if(checkRow(puzzle, x, possible) || checkColumn(puzzle, y, possible) || checkBox(puzzle, y, x, possible))
			return false;
		else
			return true;
	}
	//checking the row, if there is a conflict ie possible is found in row already, return true
	public static boolean checkRow(int[][]puzzle, int x, int possible){
		for(int i = 0; i<9; i++){
			if(puzzle[i][x]	== possible)
				return true;
		}
		return false;
 }
	public static boolean checkColumn(int[][]puzzle, int y, int possible){
		for(int i = 0; i<9; i++){
			if(puzzle[y][i] == possible)
				return true;
		}
		return false;
	}
	public static boolean checkBox(int[][]puzzle, int y, int x, int possible){
		//ly/lx are modulus indicators	
		int row = (x/3)*3;
		int column = (y/3)*3;

		for(int i = 0; i<3; i++){
			for(int j = 0; j<3; j++){
				if(puzzle[column+i][row+j] == possible)
					return true;
			}
		}
		return false;
	}
}
