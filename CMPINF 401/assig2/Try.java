import java.util.*;
import java.io.*;
public class Try
{
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
	public static void main(String [] argus) throws IOException
	{
		Dictionary myDict = new Dictionary();
		myDict.addWord("rodents");
		myDict.addWord("unusual");
		myDict.addWord("castle");
		myDict.addWord("princess");
		myDict.addWord("brute");
		myDict.addWord("revenge");
		myDict.addWord("swamp");
		myDict.addWord("prince");
		myDict.addWord("swordsman");
		myDict.addWord("despair");
		System.out.println(equal(myDict,"smallDict.txt"));
	}
}