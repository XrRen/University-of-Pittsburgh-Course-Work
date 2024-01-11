import java.util.Dictionary;

public class Crossword implements WordPuzzleInterface {
    /*
     * Fill out a word puzzle defined by an empty board. 
     * 
     *  @param board is a 2-d array representing the empty board to be filled
     *  The characters in the empty board can be:
     *    '+': any letter can go here
     *    '-': no letter is allowed to go here
     *     a letter: this letter has to remain as-is at the same position in the filled puzzle
     *     a value between '1' and '9': any letter can go here and the provided value is an upper bound on the number 
     *                                  of times the letter can appear in the filled board. If a letter has multiple 
     *                                  upper bounds, the largest bound is the effective one.
     *  @param dictionary is the dictinary to be used for filling out the puzzle. Check DictInterface for
     *                    more details on the operations provided by the dictionary
     *  @return a 2-d array representing the filled out puzzle or null if the puzzle has no solution
     */
    private char[][] filledBoard; // board that need to be returned and modified
    private StringBuilder[] colStr; // store the words on every colunms
    private StringBuilder[] rowStr; // sotre words on every rows
    private int[] maxCount;// store the maxium of appearance of a letter
    private int[] occured; // store the number of appearance of a letter
    private int[] minusRow; // store the position of '-' in each col
    private int[] minusCol; // store the position of '-' in each row
    private char[][] file; // a copy of the board that need to be filled
    private DictInterface dictionary; // dictionary 
    private int length; // length of the board
    private char[] alphabet = "eariotnslcudpmhgbfywkvxzjq" . toCharArray(); // reorder the alphabet. The most appeared letter will be in the front

    /**
     * @param row
     * @param col
     * @return true if the board is correctly filled. Else, return false, no colution to the board found
     */
    private boolean fillRec(int row, int col) // return the curr board when backtrack
    {
        /*for(int i = 0; i < file.length; i++)
        {
            System.out.println(rowStr[i]);
            //System.out.println(row);
            //System.out.println(col);
        }
        System.out.println(" ");*/
        boolean answer = false;
        //System.out.println(row);
        //System.out.println(col);
        if(row >= rowStr.length || col >= colStr.length)
        {
            return true;
        }
        // - case
        if(file[row][col] == '-')
        {    
            rowStr[row].append('-');
            colStr[col].append('-');
            filledBoard[row][col] = '-';
            minusRow[row] = rowStr[row].length() - 1;
            minusCol[col] = colStr[col].length() - 1;
             if(row == length && col == length)
            {
                //System.out.println(rowStr[col].length());
                return true;
            }
            else
            {
                if(col == length - 1 /*&& row < filledBoard.length*/)
                {
                    //System.out.println(1);
                    answer = fillRec(row+1, 0);
                }
                else//if(col < filledBoard.length)
                {
                    //System.out.println(2);
                    answer = fillRec(row, col+1);
                    //System.out.println(filledBoard[row][col]);
                    //System.out.println(col + " " + colStr[col].length());
                }
            }
             
            if(!answer)
            {
                rowStr[row].deleteCharAt(rowStr[row].length()-1);
                colStr[col].deleteCharAt(colStr[col].length()-1);
            }
            return answer;
            
        }
        //'+' case
        else if(file[row][col] == '+')
        {
            // iterate the reordered alphabet
            for(char i : alphabet) // need to rethink about this for loop because don't to retry the letter had put before.
            {
                //filledBoard[row][col] = i;
                //minusCol[col] = -1;
                //minusRow[row] = -1;
                if(isValid(row,col, i, maxCount[i-'a'])) // change to isValid, because we want to know is the letter valid not the whole board
                {
                    rowStr[row].append(i);
                    //System.out.println(rowStr[row].toString() + " " + row + " " + rowStr[row].length());
                    colStr[col].append(i);
                    filledBoard[row][col] = i;
                    //System.out.println(colStr[col].toString() + " " + col + " " + colStr[col].length());
                    //System.out.println("r " + row + " " + rowStr[row].length());
                    //System.out.println("c " + col + " " + colStr[col].length());
                    //System.out.println("b " + filledBoard.length);
                    occured[i-'a'] += 1;
                    if(row == length && col == length)
                    {
                        //System.out.println(rowStr[col].length());
                        answer =  true;
                        return true;
                    }
                    else
                    {
                        if(col == length - 1 /*&& row < filledBoard.length*/)
                        {
                            //System.out.println(1);
                            answer = fillRec(row+1, 0);
                        }
                        else//if(col < filledBoard.length)
                        {
                                //System.out.println(2);
                            answer = fillRec(row, col+1);
                                //System.out.println(filledBoard[row][col]);
                                //System.out.println(col + " " + colStr[col].length());
                        }        
                    }
                    if(!answer)
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length()-1);
                            //System.out.println(rowStr[row]);
                            //System.out.println(col);
                            //System.out.println(colStr[col]);
                        colStr[col].deleteCharAt(colStr[col].length()-1);
                            //System.out.println(colStr[col]);
                        occured[i-'a']-=1;
                    }
                }
            }   
                
            return answer;
        }
        // letter case
        else if(Character.isLetter(file[row][col]))
        {
            //minusCol[col] = -1;
            //minusRow[row] = -1;
            char letter = file[row][col];
            if(isValid(row, col, letter, 0))
            {
                rowStr[row].append(letter);
                colStr[col].append(letter);
                filledBoard[row][col] = letter;
                occured[letter-'a']+=1;
                if(row == length && col == length)
                {
                    return true;
                }
                else
                {
                    if(col == length - 1)
                    {
                            //System.out.println(1);
                            /*if(!isValid(row, col, file[row][col], 0))
                            {
                                return false;
                            }*/
                        answer = fillRec(row+1, 0);
                    }
                    else
                    {
                            //System.out.println(2);
                        answer = fillRec(row, col+1);
                            //System.out.println(filledBoard[row][col]);
                            //System.out.println(col + " " + colStr[col].length());
                    }
                                    
                }
                if(!answer)
                {
                    rowStr[row].deleteCharAt(rowStr[row].length()-1);
                            //System.out.println(rowStr[row]);
                            //System.out.println(col);
                            //System.out.println(colStr[col]);
                    colStr[col].deleteCharAt(colStr[col].length()-1);
                            //System.out.println(colStr[col]);
                    occured[letter-'a']-=1;
                    //return false;
                }
            }
            return answer;
        }
        // number case will be similar as the + case but with more datas to be stored and checked
        else if(Character.isDigit(file[row][col]))//still need to keep working on, it's for the number case
        {
            //minusCol[col] = -1;
            //minusRow[row] = -1;
            int maxOcc = file[row][col] - '0';
            for(char i : alphabet) // need to rethink about this for loop because don't to retry the letter had put before.
            {
                if(isValid(row,col,i,maxOcc)) // change to isValid, because we want to know is the letter valid not the whole board
                {
                    filledBoard[row][col] = i;
                    rowStr[row].append(i);
                    colStr[col].append(i);
                    occured[i-'a']+=1;
                    maxCount[i-'a'] = maxOcc;
                    if(maxOcc < maxCount[i-'a'] || maxCount[i-'a'] == 0)
                    {
                        maxCount[i-'a'] = maxOcc;
                    }
                        //int preMax = max_count[i-'a'];
                    if(row == length && col == length)
                    {
                            //System.out.println(rowStr[col].length());
                        answer =  true;
                        return true;
                    }
                    else
                    {
                        if(col == length - 1 && row < length)
                        {
                                //System.out.println(1);
                            answer = fillRec(row+1, 0);
                        }
                        else//if(col < filledBoard.length)
                        {
                                //System.out.println(2);
                            answer = fillRec(row, col+1);
                                //System.out.println(filledBoard[row][col]);
                                //System.out.println(col + " " + colStr[col].length());
                        }
                    }
                    if(!answer) // rethink about rest the max 
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length()-1);
                        colStr[col].deleteCharAt(colStr[col].length()-1);
                        occured[i-'a']-=1;
                        maxCount[i-'a'] = maxOcc;
                    }
                        //break;
                        // maybe not break but a recursive call because when backtrack, it will contiuns to check the rest of the letter
                } 
            }
            return answer;
        }
        return answer;
    } 
    /**
     * @param board
     * @param dictionary
     * @return the filled puzzle if exist. Else, return null
     */
    public char[][] fillPuzzle(char[][] board, DictInterface dictionary)
    {
        filledBoard = new char[board.length][board.length];
        colStr = new StringBuilder[board.length];
        rowStr = new StringBuilder[board.length];
        maxCount = new int[26];
        occured = new int[26];
        minusRow = new int[rowStr.length];
        minusCol = new int[colStr.length];
        file = board;
        length = file.length;
        boolean found = false;
        this.dictionary = dictionary;
        for(int i = 0; i < board.length;i++)
        {
            minusRow[i] = -1;
            minusCol[i] = -1;
            colStr[i] = new StringBuilder("");
            rowStr[i] = new StringBuilder("");
        }
        //double start = System.nanoTime();
        found = fillRec(0, 0);
        //double end = System.nanoTime();
		//double nano = end - start;
	    //double sec = nano/1000000000;
        //System.out.println(sec + " secoud");
        /*for(int r = 0; r < board.length; r++)
        {
            for(int c = 0; c < board.length; c++)
            {
                found = fillRec(r, c);
                if(found)
                {
                    break;
                }
            }
        }*/
        if(found)
        {
            return filledBoard;
        }
        else
        {
            return null;
        }

        
    }

    /*
     * Check if filledBoard is a correct filling for a given empty board
     * 
     * @param emptyBoard is a 2-d array representing an empty board
     * @param filledBoard is a 2-d array representing a filled out board
     * @param dictionary is the dictinary to be used for checking the filled out board
     * @return true if rules defined in fillPuzzle has been followed and 
     *  every row and column is a valid word in the dictionary. If a row
     *  or a column includes one or more '-', then each segment should be 
     *  a valid word in the dictionary; the method returns false otherwise
     */
    // check is the board filled as the file said
    // check if the occurence of the letter accurate
    // check is the word vaild
    /**
     * @param emptyBoard
     * @param filledBoard
     * @param dictionary
     * @return true if the puzzle is valid (followed the rule), else false
     */
    public boolean checkPuzzle(char[][] emptyBoard, char[][] filledBoard, DictInterface dictionary) // keep in mind about the '-' case, it means seperated 2 or more words
    {
        boolean valid = false;
        StringBuilder[] rowB = new StringBuilder[emptyBoard.length];
        StringBuilder[] colB = new StringBuilder[emptyBoard.length];
        int[] occ = new int[26];
        for(int i = 0; i < emptyBoard.length; i++)
        {
            rowB[i] = new StringBuilder("");
            colB[i] = new StringBuilder("");
        }
        if(emptyBoard.length != filledBoard.length)
        {
            return false;
        }
        for(int i = 0; i < filledBoard.length;i++)
        {
            for(int j = 0; j < filledBoard.length;j++)
            {
                if(filledBoard[i][j] != '-')
                {
                    if(Character.isUpperCase(filledBoard[i][j]))
                    {
                        occ[filledBoard[i][j] - 'A']++;
                    }
                    else
                    {
                        occ[filledBoard[i][j] - 'a']++;
                    }
                    
                }
            }
        }
        for(int r = 0; r < emptyBoard.length;r++)
        {
            for(int c = 0; c < emptyBoard.length ; c++)
            {
                rowB[r].append(filledBoard[r][c]);
                colB[c].append(filledBoard[r][c]);
                if(emptyBoard[r][c] == '-' && filledBoard[r][c] == '-')
                {
                    valid = true;
                }
                else if(emptyBoard[r][c] == '+' && Character.isLetter(filledBoard[r][c]))
                {
                    valid = true;
                }
                else if(Character.isLetter(emptyBoard[r][c]) && emptyBoard[r][c] == filledBoard[r][c])
                {
                    valid = true;
                }
                else if(Character.isDigit(emptyBoard[r][c]))
                {    
                    int temp = emptyBoard[r][c] - '0';
                    if(Character.isUpperCase(emptyBoard[r][c]))
                    {
                        //System.out.println(Character.valueOf(emptyBoard[r][c]));
                        if(occ[filledBoard[r][c] - 'A'] > temp)
                        {
                            //System.out.println("Yeah!");
                            return false;
                        }
                    }
                    else
                    {
                        //System.out.println(Character.valueOf(emptyBoard[r][c]));
                        if(occ[filledBoard[r][c] - 'a'] > temp)
                        {
                            //System.out.println("Yeah!");
                            return false;
                        }
                    }
                }
                else
                {
                    return false;
                    
                }
            }
        }
        
        for(int j = 0; j < emptyBoard.length;j++)
        {
            String[] withoutMiunsR = rowB[j].toString().split("-");
            String[] withoutMiunsC = colB[j].toString().split("-");
            if(withoutMiunsR.length > 1)
            {
                for(int i = 0; i < withoutMiunsR.length;i++)
                {
                    StringBuilder tempR = new StringBuilder(withoutMiunsR[i]);
                    int check = dictionary.searchPrefix(tempR);
                    if(check == 2 || check == 3)
                    {
                        valid = true;
                    }
                }
            }
            else
            {
                //System.out.println(rowB[j].toString());
                StringBuilder tempR = new StringBuilder(rowB[j]);
                int check = dictionary.searchPrefix(tempR);
                if(check == 2 || check == 3)
                {
                    valid = true;
                }
            }
            if(withoutMiunsC.length > 1)
            {
                for(int i = 0; i < withoutMiunsC.length;i++)
                {
                    StringBuilder tempC = new StringBuilder(withoutMiunsC[i]);
                    int check = dictionary.searchPrefix(tempC);
                    if(check == 2 || check == 3)
                    {
                        valid = true;
                    }
                }
            }
            else
            {
                StringBuilder tempC = new StringBuilder(colB[j]);
                int check = dictionary.searchPrefix(tempC);
                if(check == 2 || check == 3)
                {
                    valid = true;
                }
            }
        }
        return valid;
       
    }
/**
 * @param row
 * @param col
 * @param letter
 * @param maxOcc, 
 * @return true if the letter can be place at this spot. Else, false if the letter cannot be placed
 */
    private boolean isValid(int row, int col, char letter, int maxOcc)
    {  
        //System.out.println("check " + row +" " + col);
        
        
        colStr[col].append(letter);
        rowStr[row].append(letter);
        //System.out.println(colStr[col].toString() + " " + col);
        //System.out.println(rowStr[row].toString() + " " + row);
        boolean validRow = false;
        boolean validCol = false;
        boolean validNum = true;
        /*int[] tempMax = new int[26];
        tempMax[letter-'a'] = maxOcc;*/

        //System.out.println("occ: " + occured[Character.valueOf(letter)-97]);
        //System.out.println("max: " + tempMax[Character.valueOf(letter)-97]);

        if(maxOcc != 0)
        {
            int occ = occured[letter-'a'] + 1;
            if(occ > maxOcc)
            {
                //System.out.println("here!!!");
                rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                colStr[col].deleteCharAt(colStr[col].length() - 1);
                return false;
            }
        }
        int checkCol = 0;
        int checkRow = 0;
        if(minusCol[col] == -1 && minusRow[row] == -1)
        {
            if(colStr[col].length() == length)
            {
                checkCol = dictionary.searchPrefix(colStr[col]);
                //System.out.println("copyCol.length() == file.length");
                if(checkCol == 2 || checkCol == 3)
                {
                    //System.out.println(" 1 checkCol = 2");
                    validCol = true;
                }
            }
            else if(colStr[col].length() < length)
            {
                if(col < length - 1)
                {
                    if(file[row][col+1] == '-')
                    {
                        checkCol = dictionary.searchPrefix(colStr[col]);
                        if(checkCol == 2 || checkCol == 3)
                        {
                            //System.out.println("2 checkCol = 2");
                            validCol = true;
                        }
                    }
                }
                checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        //System.out.println("3 checkCol = 1");
                        validCol = true;
                    }
            }

            if(rowStr[row].length() == length)
            {
                checkRow = dictionary.searchPrefix(rowStr[row]);
                if(checkRow == 2 || checkRow == 3)
                {
                    //System.out.println("1 checkRow = 2");
                    validRow = true;
                }
                /*else if(checkCol != 2 && checkCol != 3)
                {
                    rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                    colStr[col].deleteCharAt(colStr[col].length() - 1);
                    //System.out.println(2);
                    return false;
                }*/
            }
            //row at the end of the board but col is not
            else if(rowStr[row].length() < length)
            {
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    //System.out.println("copyCol.length() < length");
                    if(row < length - 1)
                    {
                        if(file[row + 1][col] == '-')
                        {
                            if(checkRow == 2 || checkRow == 3)
                            {
                                //System.out.println("2 checkRow = 2");
                                validRow = true;
                            }
                        }
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        //System.out.println("3 checkRow = 1");
                        validRow = true;
                    }
                       /*else if(checkCol != 2 && checkCol != 3)
                        {
                            rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                            colStr[col].deleteCharAt(colStr[col].length() - 1);
                            //System.out.println(2);
                            return false;
                        }*/
            }
                /*else if(file[row][col+1] == '-' || )
                {
                    //System.out.println("file[row][col+1] == '-'");
                    if(checkCol == 2 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    if(checkRow == 2 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                    /*else if(checkCol != 2 && checkCol != 3)
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                        colStr[col].deleteCharAt(colStr[col].length() - 1);
                        //System.out.println(2);
                        return false;
                    }*/
                /*}
                /*else if(col == filledBoard.length - 1)
                {
                    System.out.println("col == filledBoard.length - 1");
                    int checkCol = dic.searchPrefix(copyCol);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        System.out.println(1);
                        validCol = true;
                    }
                    else if(checkCol != 2 && checkCol != 3)
                    {
                        validCol = false;
                    }
                }*/
                /*else
                {
                    //System.out.println("col-else");
                    
                    
                    /*else if(checkCol != 1 && checkCol != 3)
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                        colStr[col].deleteCharAt(colStr[col].length() - 1);
                        //System.out.println(2);
                        return false;
                    }*/
                //}
            //}
            /*if(colStr[col].length() == length || rowStr[row].length() == length)
            {
                //System.out.println("copyCol.length() == length");
                checkCol = dictionary.searchPrefix(colStr[col]);
                if(checkCol == 2 || checkCol == 3)
                {
                    //System.out.println(1);
                    validCol = true;
                }
                checkRow = dictionary.searchPrefix(rowStr[row]);
                if(checkRow == 2 || checkRow == 3)
                {
                    //System.out.println(1);
                    validRow = true;
                }
            }
            //row at the end of the board but col is not
            if(colStr[col].length() < length || rowStr[row].length() < length)
            {
                //System.out.println("copyCol.length() < length");
                if(col == length - 1 || row == length - 1)
                {
                    //System.out.println("col == length - 1");
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                }
                else if(file[row][col+1] == '-' || file[row + 1][col] == '-')
                {
                    //System.out.println("file[row][col+1] == '-'");
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                }
                else
                {
                    //System.out.println("col-else");
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                }
            }*/
        }
        // if there's '-' on the board
        else
        {
            if(minusCol[col] != -1 && minusRow[row] == -1)
            {
                if(col == length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col], colStr[col].length()-1, colStr[col].length()-1);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row == length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
                if(col < length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col], minusCol[col]+1, colStr[col].length()-1);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row < length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
            }
            else if(minusCol[col] == -1 && minusRow[row] != -1)
            {
                if(col == length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row == length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row], row, row);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
                if(col < length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row < length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row], minusRow[row]+1, rowStr[row].length() - 1);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
            }
            else
            {
                if(col == length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col], col,col);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row == length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row], row, row);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
                if(col < length - 1)
                {
                    checkCol = dictionary.searchPrefix(colStr[col], minusCol[col]+1,colStr[col].length() - 1);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        validCol = true;
                    }
                }
                if(row < length - 1)
                {
                    checkRow = dictionary.searchPrefix(rowStr[row], minusRow[row]+1, rowStr[row].length()-1);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        validRow = true;
                    }
                }
            }
            /*if(colStr[col].length() == length || rowStr[row].length() == length)
            {
                //System.out.println("copyCol.length() == length");
                checkCol = dictionary.searchPrefix(colStr[col],colStr[col].length() - 1, colStr[col].length() - 1);
                if(checkCol == 2 || checkCol == 3)
                {
                    //System.out.println(1);
                    validCol = true;
                }
                checkRow = dictionary.searchPrefix(rowStr[row], rowStr[row].length() - 1,rowStr[row].length() - 1);
                if(checkRow == 2 || checkRow == 3)
                {
                    //System.out.println(1);
                    validRow = true;
                }
                /*else if(checkCol != 2 && checkCol != 3)
                {
                    rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                    colStr[col].deleteCharAt(colStr[col].length() - 1);
                    //System.out.println(2);
                    return false;
                }*/
            //}
            //row at the end of the board but col is not
            /*if(colStr[col].length() < length || rowStr[row].length() < length)
            {
                //System.out.println("copyCol.length() < length");
                if(col == length - 1 || row == length - 1)
                {
                    //System.out.println("col == length - 1");
                    checkCol = dictionary.searchPrefix(colStr[col], colStr[col].length() - 1, colStr[col].length() - 1);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row], rowStr[row].length() - 1, rowStr[row].length() - 1);
                    if(checkRow == 2 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                    /*else if(checkCol != 2 && checkCol != 3)
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                        colStr[col].deleteCharAt(colStr[col].length() - 1);
                        //System.out.println(2);
                        return false;
                    }*/
                /*/}
                else if(row != 0 && col != 0)
                {
                    else if(file[row][col-1] == '-' || file[row -1][col] == '-')
                    {
                        //System.out.println("file[row][col+1] == '-'");
                        checkCol = dictionary.searchPrefix(colStr[col], col, colStr[col].length() - 1);
                        if(checkCol == 2 || checkCol == 3)
                        {
                            //System.out.println(1);
                            validCol = true;
                        }
                        checkRow = dictionary.searchPrefix(rowStr[row], row, rowStr[row].length() - 1);
                        if(checkRow == 2 || checkRow == 3)
                        {
                            //System.out.println(1);
                            validRow = true;
                        }
                        /*else if(checkCol != 2 && checkCol != 3)
                        {
                            rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                            colStr[col].deleteCharAt(colStr[col].length() - 1);
                            //System.out.println(2);
                            return false;
                        }*/
                    //}
                //}
                /*else if(col == filledBoard.length - 1)
                {
                    System.out.println("col == filledBoard.length - 1");
                    int checkCol = dic.searchPrefix(copyCol);
                    if(checkCol == 2 || checkCol == 3)
                    {
                        System.out.println(1);
                        validCol = true;
                    }
                    else if(checkCol != 2 && checkCol != 3)
                    {
                        validCol = false;
                    }
                }*/
                /*else
                {
                    //System.out.println("col-else");
                    checkCol = dictionary.searchPrefix(colStr[col]);
                    if(checkCol == 1 || checkCol == 3)
                    {
                        //System.out.println(1);
                        validCol = true;
                    }
                    checkRow = dictionary.searchPrefix(rowStr[row]);
                    if(checkRow == 1 || checkRow == 3)
                    {
                        //System.out.println(1);
                        validRow = true;
                    }
                    /*else if(checkCol != 1 && checkCol != 3)
                    {
                        rowStr[row].deleteCharAt(rowStr[row].length() - 1);
                        colStr[col].deleteCharAt(colStr[col].length() - 1);
                        //System.out.println(2);
                        return false;
                    }*/
                //}
            //}
        }
        rowStr[row].deleteCharAt(rowStr[row].length() - 1);
        colStr[col].deleteCharAt(colStr[col].length() - 1);
        return (validCol && validRow && validNum);
    }
    
}