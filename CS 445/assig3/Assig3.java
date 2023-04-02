/*
 * Name: Xirui Ren
 * Section: Tue/Thu 9:30-10:45
 * Assig3 will try to find a phrase from a input grid and print out the location of the each word in the phrase if found.
 * First, this program will try to find the starting word of the phrase from the grid. Then if the findWord method find the word, then
 * findPhase method will try to find the phrase by finding the next word appears next until it found the whole phrase or not find
 * and back track till it can move forward again.
 */
import java.io.*;
import java.util.*;

public class Assig3
{
    /*
     * this is the main method, it will read in the grid, ask the user for the phrase and call the findPhase method to try to find
     * the phrase.
     */
    public static void main(String[] arg)
	{
        Scanner inScan = new Scanner(System.in);
		Scanner fReader;
		File fName;
        String fString = "";
       
       	// Make sure the file name is valid
        while (true)
        {
           try
           {
               System.out.println("Please enter grid filename:");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);
              
               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }

		// Parse input file to create 2-d grid of characters
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);
		
		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}

		// Show user the grid
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("Please enter the word to search for:");
        String word = (inScan.nextLine()).toLowerCase();
        while(!(word.equals("")))
        {
            boolean found = false;
            String[] phase = word.split(" ");
            int[] startRow = new int [phase.length];
            int [] startCol = new int [phase.length];
            int[] endRow = new int[phase.length];
            int[] endCol = new int[phase.length];
            System.out.println("Looking for " + word);
            System.out.println("Containing " + phase.length);
            for(int r = 0; (r < rows && !found);r++)
            {
                for(int c = 0; (c < cols && !found); c++)
                {   
                    //System.out.println("find!");
                    for(int i = 0; i < 4; i++)
                    {
                        found = findPhase(phase, theBoard, r, c,startRow,startCol,endRow,endCol,0,i);
                        if(found)
                        {
                            break;
                        }
                    }
                    
                    //System.out.println(found);
                }
            }
            if (found)
			{
				System.out.println("The phrase: " + word + " is found.");
                for(int i = 0 ; i < phase.length; i++)
                {
                    System.out.println("The word: " + phase[i]);
                    System.out.println("was found at location (" + startRow[i] + ", " + startCol[i] + ")" + " to " + "(" + endRow[i] + ", " + endCol[i]+ ")");
                }
				for (int i = 0; i < rows; i++)
				{
					for (int j = 0; j < cols; j++)
					{
						System.out.print(theBoard[i][j] + " ");
						theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
					}
					System.out.println();
				}
			}
			else
			{
				System.out.println("The phares: " + word);
				System.out.println("was not found");
                
			}
			
			System.out.println("Please enter the word to search for:");
        	word = (inScan.nextLine()).toLowerCase();
        }
    }
    /**
     * this is the findWord method, this method will find the word recursivily. 
     * @param w, which represen the word that want to be found in the grid
     * @param board, which rereprent the grid what will used to find words in it
     * @param r, which represent which row we are looking at
     * @param c, which represent which colume we are looking at
     * @param dir, which represent the direction we are looking for the word
     * @param num, represent the specific number of the words we are looking at
     * @param loc, which represent the location of the letter in the word that we are looking at
     * @param ec, which is the array that used to contain the end colume location of the found word
     * @param er, which is the array that used to contain the end row location of the found word
     * @param notFound, which is the boolean variable that will backtrack if cannot find the phrase
     * @return true if find the word, false if don't find.
     */
    public static boolean findWord(String w, char[][] board,int r, int c, int dir,int num, int loc,int[] ec,int[] er, boolean notFound)
    {
        boolean answer = false;
        //base case if the row value, colume value and the loc value is not vaild, reuturn false
        if(r < 0 || c < 0 || r >= board.length || c >= board[0].length || loc >= w.length())
        {
            //System.out.println(2);
            return false;
        }
        if(board[r][c] != w.charAt(loc))//base case if the letter doesn't match the letter we are looking for now, return false
        {
            //System.out.println(board[r][c] + " " + w.charAt(loc));
            return false;
        }
        
        else
        {
            board[r][c] = Character.toUpperCase(board[r][c]);
            if(loc == w.length() - 1)// base case if find the word, return true.
            {
                er[num] = r;
                ec[num] = c;
                return true;
            }
            else// the recursive case
            {
                
                if(dir == 0)//move right to find the word.
                {
                    answer = findWord(w, board, r, c+1, dir, num, loc+1,ec,er,notFound);
                }
                if(dir == 1 && !answer)//move up to find the word, if the previse call doesn't find the word.
                {
                    answer = findWord(w, board, r+1, c, dir, num, loc+1,ec,er,notFound);
                }
                if(dir == 2 && !answer)//move left to find the word, if the previse call doesn't find the word.
                {
                    answer = findWord(w, board, r, c-1, dir, num, loc+1,ec,er,notFound);
                }
                if(dir == 3 && !answer) //move down to find the word, if the previse call doesn't find the word.
                {
                    answer = findWord(w, board, r-1, c, dir, num, loc+1,ec,er,notFound);
                } 
                if(!notFound)// only enter if and only if we cannot find the phrase, this is if condition will turn the word back to lower case
                {
                    //System.out.println("1");
                    answer = false;
                }
                if(!answer)// this is the backtracking case, if tried every location and still not find, then backtrack and make uppercase to lowercase.
                {
                    board[r][c] = Character.toLowerCase(board[r][c]);
                }
                return answer;
            }
        }
        
    }
    /**
     * this is the findPhase method which will find the whole pharse from a grid
     * @param w, which represent the phrase by using an array
     * @param board, which represent the grid
     * @param r, which represent the row
     * @param c, which represent the colume
     * @param sr, which is the array that for storing the start row for the finded word
     * @param sc, which is the array that for storing the start colume for the finded word
     * @param er, which is the array that for storing the end row for the finded word
     * @param ec, which is the array that for storing the end colume for the finded word
     * @param wordNum, which reprent the specific word's location's number
     * @param dir, which represent the direction of finding the words.
     * @return true is find the phrase, else return false.
     */
    public static boolean findPhase(String[] w,char[][] board,int r,int c,int[] sr, int[] sc, int[] er, int[] ec, int wordNum,int dir)
    {
        boolean found = false;
        boolean foundW = false;
        sr[wordNum] = r;
        sc[wordNum] = c;
        //base case if the row value, colume value and the loc value is not vaild, reuturn false
        if(r < 0 || c < 0 || r >= board.length || c >= board[0].length || wordNum >= w.length || wordNum < 0)
        {
            return false;
        }
        
       //if(wordNum == 0)
        //{
            /*for(int i = 0; i < 4;i++)
            {
                foundW = findWord(w[wordNum], board, r, c, i, 0,wordNum,ec,er, true);
                if(foundW)
                {
                    dir = i;
                    break;
                }
            
            }*/
        //}
        /*   
        else
        {
            foundW =  findWord(w[wordNum], board, r, c, dir, wordNum, 0,ec,er,true);
        }*/
        foundW =  findWord(w[wordNum], board, r, c, dir, wordNum, 0,ec,er,true);   
        //base case if can't find the words, reuturn false
        if(!foundW)
        {
            return false;
        }
        else
        {
            if(wordNum == w.length - 1) // base case when finding the phrase, return true.
            {
                //System.out.println("find!");
                found = true;
                return found;
            }
            else// the recursice case.
            {
                int tempC = ec[wordNum];
                int tempR = er[wordNum];
                
                found = findPhase(w, board, tempR, tempC+1, sr,sc,er,ec, wordNum + 1 , 0);
                sr[wordNum] = r;
                sc[wordNum] = c;
                if(!found)// move down to find the phrase if the precious call cannot find.
                {
                    //System.out.println("D");
                    found = findPhase(w, board, tempR+1, tempC, sr,sc,er,ec, wordNum + 1 , 1);
                    sr[wordNum] = r;
                    sc[wordNum] = c;

                }
                if(!found)// move left to find the phrase if the precious call cannot find.
                {
                    //System.out.println("L");
                    found = findPhase(w, board, tempR, tempC-1, sr,sc,er,ec, wordNum + 1,2);
                    sr[wordNum] = r;
                    sc[wordNum] = c;
                }
                if(!found)// move up to find the phrase if the precious call cannot find.
                {
                    //System.out.println("U");
                    found = findPhase(w, board, tempR-1, tempC, sr,sc,er,ec, wordNum + 1,3);
                    sr[wordNum] = r;
                    sc[wordNum] = c;
                }
                if(!found)// backtracking to the previous words so that we can try other direction till we can move forward again.
                {
                   findWord(w[wordNum].toUpperCase(), board, r, c, dir, wordNum,0, ec, er, false);// turn the precious words back to lowercase.
                    board[tempR][tempC] = Character.toLowerCase(board[tempR][tempC]);
                    
                }
            }
            return found;
        }
    }
}