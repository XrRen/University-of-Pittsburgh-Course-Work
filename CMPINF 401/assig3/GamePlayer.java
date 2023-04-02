//This class is the blue print of the GamePlayer object. The programmer can use this class to creata GamePlayer object. 
public class GamePlayer
{
	private String name;
	private int round;
	private int win;
	private int lose;
	private double ratio = 1.0;
	private int playerTotal;
	private int comTotal;
	private int tries;
	private int opts;
	private String password;
	// this is the constructor, we can create a GamePlayer object by calling this consturctor.
	public GamePlayer()
	{
		round = 0;
	}
	// this is the constructor with param, we can create a GamePlayer object by calling this consturctor, and assign value to different instance
	// variable.
	public GamePlayer(String userName, String pass, int r, int s, int o, int t)
	{
		name = userName;
		password = pass;
		round = r;
		win = s;
		opts = o;
		tries = t;
		lose = round - win;
		playerTotal += t;
		comTotal += o;
		ratio = (double)playerTotal/comTotal;
	}

	// this is the constructor, we can create a GamePlayer object by calling this consturctor and a param. name.
	public GamePlayer(String userName)
	{
		name = userName;
	}
	public GamePlayer(String userName, String pass)
	{
		name = userName;
		password = pass;
	}
	// this method will set the password.
	public void setPass(String pass)
    {
		password = pass;
    }
	// this method will return the password.
	public String getPass()
	{
		return password;
	}

	/**
	 * This method will determine whether 2 gameplayers are equal or not
	 * @param p, which represent a gameplayer
	 * @return a boolean, if they are equal, return true. Else, return false.
	 */
	public boolean equals(GamePlayer p)
	{
		boolean equal = true;
		if(p.getName().equals(name) && p.getPass().equals(password))
		{
			equal = true;
		}
		else
		{
			equal = false;
		}
		return equal;
	}
	/**
	 * This method will return a string of the player's information
	 */
	public String toString()
	{
		StringBuilder B = new StringBuilder();
		B.append('\t' + "Name: " + name + '\n');
		B.append('\t' + "Rounds: " +  round + '\n');
		B.append('\t' + "Successes: " + win + '\n');
		B.append('\t' + "Failures: " + lose + '\n');
		B.append('\t' + "Ratio (successes only): " + ratio + '\n');
		return B.toString();
	}
	// if the player wins, this method will set the opts, tries, win,ratio, round , palyertotal, and computer move total.
	public void success(int t, int o)
	{
		opts = o;
		tries = t;
		win++;
		playerTotal += t;
		comTotal += o;
		ratio = (double)playerTotal/comTotal;
		round++;
	}
	// this method will count the lost round.
	public void failure()
	{
		lose++;
		round++;
	}
	// this method will return the name of the player's name.
	public String getName()
	{
		return name;
	}
	//format: Name,Password,Rounds,Successes,MinimumDistances,PlayerAttempts
	// this method will return a string of this player's raw data.
	public String toStringFile()
	{
		StringBuilder B = new StringBuilder();
		B.append(getName()+",");
		B.append(getPass()+",");
		B.append(round + ",");
		B.append(win + ",");
		B.append(comTotal + ",");
		B.append(playerTotal);
		return B.toString();
	}
}