// CMPINF 0401 Fall 2022
// Shell of the PlayerList class.
// This class represents a collection of players -- a very simple database.  The
// collection can be represented in various ways.  However, for this assignment
// you are required to use an array of GamePlayer.

// Note the imports below.  java.util.* is necessary for a Scanner and java.io.* is
// necessary for various File classes.
import java.util.*;
import java.io.*;

public class PlayerList
{
	private GamePlayer [] players;  // array of GamePlayer
	private int size;				// logical size
	private String file;			// name of file associated with this PlayerList
	private String fileName;
	// Initialize the list from a file.  Note that the file name is a parameter.  You
	// must open the file, read the data, making a new GamePlayer object for each line
	// and putting them into the array.  Your initial size for the array MUST be 3 and
	// if you fill it should resize by doubling the array size.  You will need to write
	// a resize() method to do the resizing.
	
	// Note that this method throws IOException. Because of this, any method that CALLS
	// this method must also either catch IOException or throw IOException.  Note that 
	// the main() in PlayerListTest.java throws IOException.  Keep this in mind for your
	// main program (Assig3.java).  Note that your saveList() method will also need
	// throws IOException in its header, since it is also accessing a file.
	public PlayerList(String fName) throws IOException
	{
		fileName = fName;
        File file = new File(fName);
		Scanner inFile = new Scanner(file);
		Scanner forSizeFile = new Scanner(file);
		players = new GamePlayer [3];
		int count = 0;
		int i = 0;
		while(forSizeFile.hasNextLine())
		{
			count++;
			forSizeFile.nextLine();
		}
		size = count;
		while(inFile.hasNextLine())
		{
			String [] arr;
            String line = inFile.nextLine();
            arr = line.split(",");
			String n,p;
			int r = 0, s = 0, o = 0, t = 0;
			n = arr[0];
			p = arr[1];
			r = Integer.parseInt(arr[2]);
			s = Integer.parseInt(arr[3]);
			o = Integer.parseInt(arr[4]);
			t = Integer.parseInt(arr[5]);
			GamePlayer player = new GamePlayer(n,p,r,s,o,t);
			if(i >= players.length)
			{
				players = resize(players);
			}
			players[i] = player;
            i++;
		}
	}
    // this method will resize ths PlayerList list if the size is not big enough.
	public GamePlayer [] resize(GamePlayer [] oldArray)
	{
		int old,newArr;
		old = oldArray.length;
		newArr = old * 2;
		GamePlayer [] newA = new GamePlayer[newArr];
		newA = Arrays.copyOf(oldArray, newArr);
		return newA;
	}
	// this method will return a int that represent the logic size of the array.
    public int size()
    {
		return size;
    }
	// this method will return a int that represent the physical size of the array.
    public int capacity()
    {
		return players.length;
    }
	// this method will return a GamePlayer with the give name.
	public GamePlayer getPlayer(String name)
	{
		for(int i = 0; i < size; i++)
		{
			if(players[i].getName().equals(name))
			{
				return players[i];
			}
		}
		return null;
	}
	// this method will return true if the plyaer list contain the String str, otherwise return false.
    public boolean containsName(String str)
    {
		boolean found = true;
		int f = 0;
		for(int i = 0; i < size(); i++)
		{
			if(!(players[i].getName().equals(str)))
			{
				f++;
			}
			else
			{
				found = true;
			}
		}
		if(f == size)
		{
			found = false;
		}
		return found;
    }
	// this method will return true if the player didn't ecist in the list, else return false.
    public boolean addPlayer(GamePlayer player)
    {
		boolean find = true;
		if(!containsName(player.getName()))
		{
			size++;
			if(size >= capacity())
			{
				players = resize(players);
			}
			GamePlayer [] newPlay = new GamePlayer[size];
			newPlay = Arrays.copyOf(players, players.length);
			newPlay[size-1] =player;
			players = Arrays.copyOf(newPlay, players.length);
			find = true;
		}
		else
		{
			find = false;
		}
		return find;
    }
	// this method will return true if the param GamaPlayer temp's name and password matches the exist player in the list.
    public GamePlayer authenticate(GamePlayer temp)
    {
		for(int i = 0; i < size; i++)
		{
			if(temp.equals(players[i]))
			{
				return players[i];
			}
		}
		return null;
    }
	// this method will return a String that contains the information of all players that in the player list.
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		b.append("Total players: " + size + '\n');
		for(int i = 0; i < size;i++)
		{
			b.append(players[i].toString() + '\n');
		}
		return b.toString();
	}
	// this mmathod will return a String and also update the players file with each player's raw data.
    public String saveList() throws IOException
    {
		PrintWriter writeIn = new PrintWriter(fileName);
		StringBuilder B = new StringBuilder();
		for(int i = 0; i < size;i++)
		{
			writeIn.println(players[i].toStringFile());
			B.append(players[i].toStringFile() + '\n');
		}
		writeIn.close();
		return B.toString();
    }
	// See program PlayerListTest.java to see the other methods that you will need for
	// your PlayerList class.  There are a lot of comments in that program explaining
	// the required effects of the methods.  Read that program very carefully before
	// completing the PlayerList class.  You will also need to complete the modified
	// GamePlayer class before the PlayerList class will work, since your array is an
	// array of GamePlayer objects.
	
	// You may also want to add some methods that are not tested in PlayerListTest.java.
	// Think about what you need to do to a PlayerList in your Assig3 program and write 
	// the methods to achieve those tasks.  However, make sure you are always thinking
	// about encapsulation and data abstraction.
}