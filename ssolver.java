//Trying to create a sudoku solver using dfs
import java.io.*;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.FileReader;
import java.util.ArrayList;
public class ssolver{
	public static void main(String [] args){
		//So for notation we're gonna hve the cube look like this:
		/*  _________________________
		*   | A1 A2 A3 |A4 A5 A6 | ...
		*   | B1 B2 B3 | .............
		*   | C1 C2 C3 | ............
		*/
		//lets read it into a double array
		int [] [] puzzle = new int [9][9];
		puzzle = readin();
		printPuzzle(puzzle);
		puzzle = solve(puzzle);
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
		try{
			BufferedReader reader = new BufferedReader(new FileReader("/Users/wechtera/Desktop/Practiceprogram/ssolverin.txt"));
		}catch(FileNotFoundException fnf){System.out.println("file not found skank");}
			int input;  //input into the puzzle
			String in;   //what we type into pre convert (. for blank space)
System.out.println("Beep");
			
			for(int i = 0; i<9; i++){  //rows
				System.out.println("\n______Row " + i + "_________\n");
				for(int j = 0; j<9; j++){  //columns
					System.out.print((i)+" x "+(j)+":  ");
					try{
						in = reader.readLine();
					}catch(IOException e){}
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
			}
		
		return puzzle;		
	}
	public static int[][] solve(int[][]puzzle){
		for(int i = 0; i<9; i++){
			for(int j = 0; j<9; j++){
				if(puzzle[i][j]==0){
				ArrayList<Integer> a = new ArrayList<Integer>();
				int place = 0;
					for(int k = 1; k<10; k++){
						if(isTrue(puzzle, i, j, k)){
							a.add(k);
						}
					}
				System.out.println(a.toString());
				if(a.size()==1){
					puzzle[i][j] = a.get(0);
					printPuzzle(puzzle);
					solve(puzzle);
				}
				}
			}
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
	/* So my logic behind dealing with how to check only the box no matter what the entry was, was 
	 * relatively pain in the ass since the logic is pretty long.  But while looking for commonalities t
	 * that were repeatable in all of the boxxes, i decided to us the modulus operator.
	           1   2   0
		_____________
	     1	|
	     2	|
	     0	|
		-------------	
	 * based on the sample base array no matter what they will be one of those three and i can use those coords of the 1,0, 
	 * guide which coords to check against
	 */
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
