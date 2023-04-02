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

	public GamePlayer()
	{
		round = 0;
	}
	public GamePlayer(String userName, int r, int s, int o, int t)
	{
		name = userName;
		round = r;
		win = s;
		opts = o;
		tries = t;
		lose = round - win;
		playerTotal += t;
		comTotal += o;
		ratio = (double)playerTotal/comTotal;
	}
	public GamePlayer(String userName)
	{
		name = userName;
	}
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
	public void failure()
	{
		lose++;
		round++;
	}
	public String getName()
	{
		return name;
	}
	public String toStringFile()
	{
		StringBuilder B = new StringBuilder();
		B.append(round + "\n");
		B.append(win + "\n");
		B.append(comTotal + "\n");
		B.append(playerTotal + "\n");
		return B.toString();
	}
}