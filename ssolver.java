//Trying to create a sudoku solver using backtracking
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class ssolver{
	private static int recDepth = 0;  //recursion depth
	public static void main(String [] args){
		//lets read it into a double array
		int [] [] puzzle = new int [9][9];
		puzzle = readin();
		printPuzzle(puzzle);
		puzzle = solve(puzzle, 0, 0);
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
	public static int[][] solve(int[][]puzzle, int x, int y){
		System.out.println("RecrDepth:  " + recDepth);
		recDepth++;
		//using backtracking for brute force power of the gods(norse cause they obviously most b.a.
		ArrayList<Integer> list = new ArrayList<Integer>();
		//next for both  x and y
		int nextx = getNextx(x);
		int nexty = getNexty(x, y);
		while(puzzle[y][x] != 0){  //progress until blank space
			x = nextx;
			y = nexty;
			if(isSolved(puzzle))
				return puzzle;
			nextx = getNextx(x);
			nexty = getNexty(x, y);
		}
		for(int i = 1; i<10; i++){
			if(isTrue(puzzle, y, x, i))
				list.add(i);
		}
		for(int i=0; i<list.size(); i++){
			puzzle[y][x]= list.get(i);
			if(isSolved(puzzle))
				return puzzle; 
			printPuzzle(puzzle);
			puzzle = solve(puzzle, nextx, nexty);
		}
		return puzzle;
	}		
						
	public static int getNextx(int x){
		if(x==8)
			return 0;
		else
			return x+1;
	}
	public static int getNexty(int x, int y){
		if(x ==8)
			return y+1;
		else
			return y;
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
	//check to see if puzzle is solved by checking all the boxes  a bit inefficient but balls cried the queen
	public static boolean isSolved(int[][]puzzle){
		for(int i = 0; i<9; i++){  //y rotation
			for(int j = 0; j<9; j++){
				if(puzzle[i][j]==0){
					return false;
				}
			}
		}
		return true;
	}
}
