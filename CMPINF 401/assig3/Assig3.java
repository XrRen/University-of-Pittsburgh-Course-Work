/**
* Name: Xirui Ren
* Course: CMPINF 0401
* Section: Tuesday && Thursday 9:30 a.m.
* Exp: This program will simulate a word game. And first, it will ask user for a name for the dictionary file. Then it will ask for player name,
* if player aleady exist in the PlayerList, then the program will ask the player to enter a password that is stored in the player list. If
* the player entered a name that is not in the player list, then the program will automaticly help the player to sign up for a new account.
* If the player didn't enter anything, then the program will ask for a username and help the player to sign up for a new account. Then the
* will introduce the game to the player as long as the user didn't enter "quit". Just like assignment2, this assignment will also perform
* a word game that ask the player to convert word1 to word2 in one minutes as long as the player enter "y" when ask "do you want to play 
* this game". If the player entered "n", the program will exit the loop and ask for another player as long as the player didn't enter
* "quit". If enter "quit", the program will exit the outer loop and print out the players info and save the new data into the player list
* file.
*/
import java.util.*;
import java.io.*;

public class Assig3
{
	static int steps = 0; //  a static variable that will help the program to count the user step.
	/**
	* this method will print out the main greeting of the game.
	*/
	public static void welcome()
	{
		System.out.println("Welcome to the Word Changer Program" +'\n');
		System.out.println("Please sign in with your name and password.");
		System.out.println("If you are a new player, leave your name field blank,");
		System.out.println("and we will set you up with a new account.");
		System.out.println("If you would like to end the game, enter 'Quit'.");

		System.out.printf("\t Name: ");
	}
	// this method will print out the rule of the game.
	public static void meun()
	{
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
	* @throws IOException
	*/
	public static String userStep(Scanner input, StringBuilder w1) throws IOException
	{
		String step = input.next();
		int changes = 0;
		String chanTo;
		String reStr;
		if(step.equalsIgnoreCase("c"))//change what to what?
		{
			changes = input.nextInt();
			chanTo = input.next(); input.nextLine();
			int chan2 = 0;
			chan2 = changes + 1;
			w1.replace(changes, chan2, chanTo);
			steps++;
		}
		else if(step.equalsIgnoreCase("i"))
		{
			changes = input.nextInt();
			chanTo = input.next(); 
			input.nextLine();
			w1.insert(changes,chanTo);
			steps++;
		}
		else if(step.equalsIgnoreCase("d"))
		{
			changes = input.nextInt();
			input.nextLine();
			w1.deleteCharAt(changes);
			steps++;
		}
		else if(!step.equalsIgnoreCase("d") && !step.equalsIgnoreCase("i") && !step.equalsIgnoreCase("c"))
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
		while(w1.equalsIgnoreCase(w2))
		{
			w1 = inFile.randWord(5,9);
		}
		return w1;
	}
	/**
	 * This method will create a new GmapePlayer
	 * @param p, the GamePlayer, which reprecent the new player.
	 * @param inScan, the Scanner, which used to read in user input
	 * @param players, the PlayerList, whihc use to check if the useranem exist or no.
	 * @return a new GamePlayer for the user. 
	 */
	public static GamePlayer newPlayer(GamePlayer p, Scanner inScan, PlayerList players)
	{
		String inName, inPass;
		System.out.println("Welcome new player!!!");
		System.out.printf("Please enter a name for your account (no spaces):  %n"); 
		inName = inScan.nextLine();
		while(players.containsName(inName))
		{
			System.out.printf("Sorry but the name " + inName + " is taken. Please enter another name:  %n");
			inName = inScan.nextLine();
		}
		System.out.printf("Please enter your password: %n");
		String conPass;
		inPass = inScan.next(); inScan.nextLine();
		System.out.printf("Confirm password: %n");
		conPass = inScan.next(); inScan.nextLine();
		while(!inPass.equals(conPass))
		{
			System.out.println("Sorry but your password didn't match!");
			System.out.printf("Please enter your password: %n");
			inPass = inScan.next(); inScan.nextLine();
			System.out.printf("Confirm password: %n");
			conPass = inScan.next(); inScan.nextLine();
		}
		p = new GamePlayer(inName,inPass);
		return p;
	}
	/**
	 * This method will help a new player set up their account
	 * @param allPlayers, the PlayerList, use to check the user input.
	 * @param userName, the String that the user input as their username.
	 * @param keyScan the scenner that used to read in user input.
	 * @return a new GamePlayer with name and password.
	 */
	public static GamePlayer newNamedP(PlayerList allPlayers, String userName, Scanner keyScan)
	{
		String inPass;
		GamePlayer player;
		System.out.println("Welcome new player!!!");
		System.out.printf("Please enter your password: %n");
		String conPass;
		inPass = keyScan.next(); keyScan.nextLine();
		System.out.printf("Confirm your password: %n");
		conPass = keyScan.next(); keyScan.nextLine();
		while(!inPass.equals(conPass))
		{
			System.out.println("Sorry but your password didn't match!");
			System.out.printf("Please enter your password: %n");
			inPass = keyScan.next(); keyScan.nextLine();
			System.out.printf("Confirm password: %n");
			conPass = keyScan.next(); keyScan.nextLine();
		}
		player = new GamePlayer(userName,inPass);
		return player;
	}
	/**
	 * This method will find the ecist player and check does the password match or not. 
	 * @param keyScan the scanner that used to read in user input
	 * @param players the PlayerList that will check the iser input.
	 * @param userName the String that represent the player's username.
	 * @return if the password and username match the file, then it will return the exist player, else a new player with new name and password.
	 */
	public static GamePlayer playerExist(Scanner keyScan, PlayerList players, String userName)
	{
		String inPass;
		System.out.printf("Please enter your password: %n");
		inPass = keyScan.next(); keyScan.nextLine();
		GamePlayer player = new GamePlayer(userName,inPass);
		int wrongTime = 0;
		while(players.authenticate(player) == null && wrongTime != 2)
		{
			System.out.printf("Sorry, the password you entered is not matched. Please re-enter your password: %n");
			inPass = keyScan.next(); keyScan.nextLine();
			player.setPass(inPass);
			wrongTime++;
			userName = player.getName();
		}
		if(wrongTime == 2 && players.authenticate(player) == null)
		{
		System.out.println("Your password still does not match, so your sign-in has been canceled.");
		System.out.println("Please register as a new player." +'\n');
		player = newPlayer(player, keyScan, players);
		}
		else
		{
			player = players.authenticate(player);
		}
		return player;
	}
	
	
	public static void main(String[] arg)throws IOException
	{
		String word1;
		String word2;
		
		PlayerList allPlayers = new PlayerList("players.txt");
		GamePlayer player = new GamePlayer();
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
		
		String userName;		

		welcome();
		
		userName = keyScan.nextLine();//either none, quit or old player name. not for new player
		System.out.println("");
		String inPass;
		if(userName.isBlank())
		{
			player = newPlayer(player, keyScan, allPlayers);
			userName = player.getName();
		}
		else if(allPlayers.containsName(userName))
		{
			player = playerExist(keyScan, allPlayers, userName);
		}
		else if(!(allPlayers.containsName(userName) || userName.equalsIgnoreCase("Quit")))
		{
			player = newNamedP(allPlayers, userName, keyScan);
		}
		while (!(userName.equalsIgnoreCase("Quit"))) // check the user at the beginning and get the data from file.
		{
			System.out.println();
			meun();
			System.out.println(player.getName() + ", would you like to try?(y/n) ");
			enter = keyScan.next(); keyScan.nextLine();
			
			Dictionary checkDict = new Dictionary();
			enter = enter;
			exit:// if ran out of words, it will break out to here.
			while(enter.equalsIgnoreCase("y") && !enter.equalsIgnoreCase("n"))
			{	 
				steps = 0;
				word1 = inDict.randWord(5,9);
				word2 = inDict.randWord(5,9);
				word1 = reRandomWord(inDict,word1,word2);
				boolean checkW1 = checkDict.contains(word1), checkW2 = checkDict.contains(word2);
				while(checkW1 || checkW2)
				{
					if(checkW1 && !checkW2)
					{
						word1 = inDict.randWord(5,9);
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
						word2 = inDict.randWord(5,9);
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
						word1 = inDict.randWord(5,9);
						word2 = inDict.randWord(5,9);
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
				while(!(word1.equalsIgnoreCase(word2)) && time.check())
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
				if(word1.equalsIgnoreCase(word2))
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
			allPlayers.addPlayer(player);
			System.out.println("Thank you for playing, " + userName + "!");
			welcome();
			userName = keyScan.nextLine();
			System.out.println("");
			if(userName.isBlank())
			{
				player = newPlayer(player, keyScan, allPlayers);
				userName = player.getName();
			}
			else if(allPlayers.containsName(userName))
			{
				player = playerExist(keyScan, allPlayers, userName);
			}
			else if(!(allPlayers.containsName(userName) || userName.equalsIgnoreCase("Quit")))
			{
				player = newNamedP(allPlayers, userName, keyScan);
			}
		}
		System.out.println("Here are the current stats: ");
		System.out.println(allPlayers.toString());
		System.out.println("Saving players back to the file...");
		allPlayers.saveList();
		//int begin = 0;
	}
}