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
		int nextx, nexty=y;
		
		if(x == 8){
			nextx=0;
			nexty=y+1;
		}
		else{
			nextx=x++;
		}
		
		if(isSolved(puzzle))
			return puzzle;
		
		if(puzzle[y][x]==0){
			for(int i =1; i<10; i++){
				if(isTrue(puzzle, y, x, i))
					list.add(i);
				for(int j : list)
					System.out.println("List:  "+ j);
			}	
			for(int j : list){
				puzzle[y][x] = list.get(j);
				printPuzzle(puzzle);//prints here for testing 
				if(isSolved(puzzle)||(x==8&&y==8));
				else{
					solve(puzzle, nextx, nexty);
				}
			}
		}
		else{	
			printPuzzle(puzzle);
			solve(puzzle, nextx, nexty);
		}

		return puzzle;				
		}		
						
	//checks to see if an int possible in the puzzle at space [i][j] is acceptable
	public static boolean isTrue(int [][]puzzle, int y, int x, int possible){
		if(checkRow(puzzle, y, x, possible) || checkColumn(puzzle, y, x, possible) || checkBox(puzzle, y, x, possible))
			return false;
		else
			return true;
	}
	//checking the row, if there is a conflict ie possible is found in row already, return true
	public static boolean checkRow(int[][]puzzle, int y, int x, int possible){
		int ph = x;
		if(x == 0){
			for(ph = 1; ph <9; ph++){
				if(puzzle[y][ph] == possible)
					return true;
			}
		}
		else if(!(x==0) && !(x==8)){
			for(ph = x-1; ph >-1; ph--){
				if(puzzle[y][ph] == possible)
					return true;
			}
			for(ph = x+1; ph < 9; ph++){
				if(puzzle[y][ph] == possible)
					return true;
			}
		}
		else{ //x ==9
			for(ph = x-1; ph>=0; ph--){
				if(puzzle[y][ph] == possible)
					return true;
			}
		}
		return false;
	}
	public static boolean checkColumn(int[][]puzzle, int y, int x, int possible){
		int ph = y;
		if(y ==0){
			for(ph = 1; ph< 9; ph++){
				if(puzzle[ph][x] == possible)
					return true;
			}
		}
		else if(!(y==0) && !(y==9)){
			for(ph = y-1; ph>-1; ph--){
				if(puzzle[ph][x] == possible)
					return true;
			}
			for(ph = y+1; ph <9; ph++){
				if(puzzle[ph][x] == possible)
					return true;
			}
		}
		else{ //y == 9
			for(ph = y-1; ph>=0; ph--){
				if(puzzle[ph][x] == possible)
					return true;
			}
		}
		return false;
	}
	public static boolean checkBox(int[][]puzzle, int y, int x, int possible){
		//ly/lx are modulus indicators	
		int ly = y+1%3;
		int lx = x+1%3;
		boolean allow=  false;
		int x1 =0, x2=0, y1=0, y2=0;  //for external cases
		switch(lx){
			case 0: x1 = x-1;
				x2 = x-2;
				break;
			case 1: x1 = x+1;
				x2 = x+2;
				break;
			case 2: x1 = x+1;
				x2 = x-1;
				break;
		}
		switch(ly){
			case 0: y1 = y-1;
				y2 = y-2;
				break;
			case 1: y1 = y+1;
				y2 = y+2;
				break;
			case 2: y1 = y-1;
				y2 = y+1;
				break;
		}
		if( puzzle[y1][x1]==possible || puzzle[y1][x2]==possible || puzzle[y2][x1]==possible || puzzle[y2][x2]==possible )
			return true;
			
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
