/**
* Name: Xirui Ren
* Course: CMPINF 0401
* Section: Tuesday && Thursday 9:30 a.m.
* Exp: This program will simulate a word game. And first, it will ask user for a name for the dictionary file. Then it will ask fpr player name,
* if player aleady exist. The the program will read the player file, and get the data from thr previous round. Then it will ask the 
* user do they want to play the game, if the imput is yea, then the game will satrt. Each game is one min long, if the user can change the fisrt word into the second word in
* that given one min, then they win, else they lost. The game will keep going until user enters "n". Or the game will end if the dictionary file ran out of words.Then after the game ends,
* it will ask for another player. If there's no more player, the program will end, and print out the data of the users who played the game.
*/
import java.util.*;
import java.io.*;

public class Assig2
{
	static int steps = 0;
	/**
	* this method will print out the main greeting o the game.
	*/
	public static void meun()
	{
		System.out.println('\n' + "Welcome to the Word Game, my friend!");
		System.out.println("Here is the rule: ");
		System.out.println("\t For each round you will see two randomly selected words from a dictionary file.");
		System.out.println("\t You will have 1 minute to convert the first word to the second");
		System.out.println("\t\t by using only the following changes: ");
        System.out.println("\t\t 1. Insert a character at position k (with k starting at 0)");
        System.out.println("\t\t 2. Delete a character at position k (with k starting at 0)");
        System.out.println("\t\t 3. Change a character at position k (with k starting at 0)");
		System.out.println("\t As a player Your goal is to make this conversion with the fewest changes as you can.");  
		System.out.println("\t Note that there may be more than one way to achieve this goal.\n");
	}
	/**
	* this method will print out the instruction of the game.
	*/
	public static void instruction()
	{
		System.out.println("What do you want to do?");
		System.out.println("\t C k v -- Change char at location k to value v");
		System.out.println("\t I k v -- Insert char at location k with value v");
		System.out.println("\t D k   -- Delete char at location k");
	}
	/**
	* this method will read the data from the existed player file and use it to build a new player with old data.
	* @param userName the String, which represent the name of the player.
	* @param userFile the File, which represent the file of that palyer
	* @return it will return a GamePlayer that contains the exist player data.
	*/
	public static GamePlayer playerExist(String userName, File userFile) throws IOException
	{
		Scanner readUser = new Scanner(userFile);
		int rou = readUser.nextInt(); readUser.nextLine();
		int suc = readUser.nextInt(); readUser.nextLine();
		int comT = readUser.nextInt(); readUser.nextLine();
		int playerT = readUser.nextInt(); readUser.nextLine();
		GamePlayer p = new GamePlayer(userName, rou, suc, comT, playerT);
		readUser.close();
		return p;
	}
	/**
	* this method will print out the start of the word game.
	* @param w1 the String, which reprensent Word 1.
	* @param w2 the String, which reprensent Word 2.
	*/
	public static void wordGame(String w1, String w2)
	{
		System.out.printf("Index:\t\t0123456789\n");
		System.out.printf("-----\t\t----------\n");
		System.out.println("Current Word: " + w1);
		System.out.println("Word 2: " + w2);
	}
	/**
	* this method will change word1 by reading user's input.
	* @param input the Scanner, which will read the input from the user.
	* @param w1 the StringBuilder, which will be modified by different user input.
	* @return it will return a String which is the word after modified.
	*/
	public static String userStep(Scanner input, StringBuilder w1)
	{
		String step = input.next();
		int changes = 0;
		String chanTo;
		String reStr;
		if(step.toLowerCase().equals("c"))//change what to what?
		{
			changes = input.nextInt();
			chanTo = input.next().toLowerCase(); input.nextLine();
			int chan2 = 0;
			chan2 = changes + 1;
			w1.replace(changes, chan2, chanTo);
			steps++;
		}
		else if(step.toLowerCase().equals("i"))
		{
			changes = input.nextInt();
			chanTo = input.next().toLowerCase(); 
			input.nextLine();
			w1.insert(changes,chanTo);
			steps++;
		}
		else if(step.toLowerCase().equals("d"))
		{
			changes = input.nextInt();
			input.nextLine();
			w1.deleteCharAt(changes);
			steps++;
		}
		else if(!step.toLowerCase().equals("d") && !step.toLowerCase().equals("i") && !step.toLowerCase().equals("c"))
		{
			changes = input.nextInt();
			input.nextLine();
			System.out.println("Can not reconize your command.");
			return w1.toString();
		}
		reStr = w1.toString();
		return reStr;
	}
	/**
	* this method will determine whether the twe dictionary contains the same exact same words or not.
	* @param myDict the Dictionary, which represent the dictionary that will be used to check wether a words has been used or not.
	* @param dicName the String, which reprensent the name of the dictionary that is used for words collection.
	* @return it will return a boolean, if it returns true, it means that all the words had been used, other wise,there're still words to be used.
	*/
	public static boolean equal(Dictionary myDict, String dicName)
	{
		boolean isEqual, x;
		StringBuilder b = new StringBuilder();
		
		int begin = 0;
		int f = 0;
		try
		{
			File file = new File(dicName);
			Scanner s = new Scanner(file);
			x = true;
			while(s.hasNextLine())
			{
				String word = s.nextLine();
				b.append(word + " ");
			}
			for(int end = 0; end < b.length(); end++)
			{	
				if(b.charAt(end) == ' ')
				{
					String dWord = b.substring(begin, end);
					if(!(myDict.contains(dWord)))
					{
						f++;
						return false;
					}
					begin = end + 1;
				}
			}
			s.close();
		}
		catch(IOException e)
		{
			x = false;
		}

		if(f > 0)
		{
			isEqual = false;
		}
		else
		{
			isEqual = true;
		}
		return isEqual;
	}
	/**
	* this method will re-assign word 1 if word 1 equals word 2.
	* @param inFile the Dictionary, which reprensent the dictionary we used for gathering words.
	* @param w1 the String, which represent word1
	* @param w2 the String, which represent word2
	* @return it will return a String w1. If w1 equals w2, then w1 will become a different word, which means word1 will be changed, too. Else, w1 stays the same, which means word1 will stay the same,too.
	*/
	public static String reRandomWord(Dictionary inFile, String w1, String w2)
	{
		while(w1.equals(w2))
		{
			w1 = inFile.randWord(5,9).toLowerCase();
		}
		return w1;
	}
	
	
	public static void main(String[] arg)throws IOException
	{
		StringBuilder playerList = new StringBuilder();
		String word1;
		String word2;
		
		String enter;
		Scanner keyScan = new Scanner(System.in);
		System.out.print("Dictionary file name? > ");
		String fileN = keyScan.nextLine();
		Dictionary inDict = new Dictionary(fileN);
		File cFile = new File(fileN);
		// let the user to re-enter the file name if they enter something that doesn't exist.		
		while(cFile.exists() != true || cFile.isFile() != true)
		{
			System.out.print("Please re-enter dictionary file name again > ");
			fileN = keyScan.nextLine();
			cFile = new File(fileN);
			inDict = new Dictionary(fileN);
			cFile.exists(); cFile.isFile();
		}
		meun();	
		String userName;		
		System.out.println("Please tell us your name (<enter> to quit)");
		userName = keyScan.nextLine();
		String userFileName = userName + ".txt";
		File userFile = new File(userFileName);
		int userLen = userFileName.length();
		
		while (userName != "") // check the user at the beginning and get the data from file.
		{
			playerList.append(userFileName + " ");
			if(userFile.exists() && userFile.isFile())
			{
				System.out.println("Welcome back " + userName + "!!");
			}
			else
			{
				System.out.println("Welcome " + userName + "!!");
			}
			System.out.println("Would you like to try?(y/n) ");
			enter = keyScan.next(); keyScan.nextLine();
			GamePlayer player;
			
			Dictionary checkDict = new Dictionary();
			
			if(userFile.exists() && userFile.isFile())
			{
				player = playerExist(userName, userFile);
			}
			else
			{
				int round = 0;
				int lose = 0;
				int win = 0;
				int comStep = 0;
				int playerStep = 0;
				player = new GamePlayer(userName, round, win, comStep, playerStep);
			}
			enter = enter.toLowerCase();
			exit:// if ran out of words, it will break out to here.
			while(enter.equals("y") && !enter.equals("n"))
			{	 
				steps = 0;
				word1 = inDict.randWord(5,9).toLowerCase();
				word2 = inDict.randWord(5,9).toLowerCase();
				word1 = reRandomWord(inDict,word1,word2);
				boolean checkW1 = checkDict.contains(word1), checkW2 = checkDict.contains(word2);
				while(checkW1 || checkW2)
				{
					if(checkW1 && !checkW2)
					{
						word1 = inDict.randWord(5,9).toLowerCase();
						word1 = reRandomWord(inDict,word1,word2);
						checkW1 = checkDict.contains(word1);
						if(!checkW1 && !checkW2)
						{
							checkDict.addWord(word1);
							checkDict.addWord(word2);
						}
					}
					else if(!checkW1 && checkW2)
					{
						word2 = inDict.randWord(5,9).toLowerCase();
						word1 = reRandomWord(inDict,word1,word2);
						checkW2 = checkDict.contains(word2);
						checkW1 = checkDict.contains(word1);
						if(!checkW1 && !checkW2)
						{
							checkDict.addWord(word1);
							checkDict.addWord(word2);
						}
					}
					else if(checkW1 && checkW2)
					{
						word1 = inDict.randWord(5,9).toLowerCase();
						word2 = inDict.randWord(5,9).toLowerCase();
						word1 = reRandomWord(inDict,word1,word2);
						checkW2 = checkDict.contains(word2);
						checkW1 = checkDict.contains(word1);
						if(!checkW1 && !checkW2)
						{
							checkDict.addWord(word1);
							checkDict.addWord(word2);
						}
						
						if(equal(checkDict,fileN))
						{
							System.out.println("Sorry, but you finished all the words!");
							break exit;// if ran out of words, break out from the loop.
						} 
					}
				}
				checkDict.addWord(word1);
				checkDict.addWord(word2);
				
				int dis = 0;
				dis = Dictionary.distance(word1,word2);
				System.out.println("Your goal is to convert " + word1 + " to " + word2 + " in " + dis + " steps\n"); // a pair of words each time
				MyTimer time = new MyTimer(60000);
				time.start();
				while(!(word1.equals(word2)) && time.check())
				{
					wordGame(word1,word2);
					instruction();
					System.out.print("Step => ");
					StringBuilder w1 = new StringBuilder();
					for(int i = 0; i < word1.length(); i++)
					{
						w1.append(word1.charAt(i));
					}
					if(time.check())
					{
						word1 = userStep(keyScan, w1);
					}
				}
				if(word1.equals(word2))
				{
					if(steps == dis)
					{
						System.out.println("Congratulation!! You won this round by using the minimum " + steps + " steps! Great job!!");
					}
					else
					{
						System.out.println("Congratulation!! You won this round by " + steps + " steps!");
					}
					player.success(steps,dis);
					time.stop();
				}
				else if(time.check() == false)
				{
					System.out.println("Sorry, but you run out of time.");
					player.failure();
				}
				System.out.print('\n');
				System.out.println("Here's your current stats: ");
				System.out.println(player.toString());
				System.out.println("Would you like to again try?(y/n) ");
				enter = keyScan.next(); keyScan.nextLine();
			}
			System.out.println("Thank you for playing, " + userName + "!");
			PrintWriter writeIn = new PrintWriter(userFileName);
			writeIn.println(player.toStringFile());
			writeIn.close();
			System.out.println("Please tell us your name (<enter> to quit)");
			userName = keyScan.nextLine();
			userFileName = userName + ".txt";
			userFile = new File(userFileName);
			if(userFile.exists() && userFile.isFile())
			{
				player = playerExist(userName, userFile);
			}
			else
			{
				int round = 0;
				int win = 0;
				int comStep = 0;
				int playerStep = 0;
				player = new GamePlayer(userName, round, win, comStep, playerStep);
			}
			
		}
		System.out.println("Thank you for playing");
		System.out.println("\nPrint out player file >");
		System.out.println(">");
		System.out.println(">");
		int begin = 0;
		for(int end = 0; end < playerList.length(); end++)
		{
			// it will read in the user file, and print out the data.
			if(playerList.charAt(end) == ' ')
			{
				String filePlayer = playerList.substring(begin, end);
				File playerF = new File(filePlayer);
				Scanner inPlayerF = new Scanner(playerF);
				System.out.println(">type " + filePlayer);
				int rounds_played = inPlayerF.nextInt();
				int successful_rounds = inPlayerF.nextInt();
				int optimal_edits_needed = inPlayerF.nextInt();
				int total_edits_needed = inPlayerF.nextInt();
				System.out.println(rounds_played);
				System.out.println(successful_rounds);
				System.out.println(optimal_edits_needed);
				System.out.println(total_edits_needed);
				begin = end + 1;
				inPlayerF.close();
			}
		}
	}
}