import java.io.*;
import java.util.*;

public class proj1
{
    public static void main(String[] arg)
    {
        Scanner user = new Scanner(System.in);
        System.out.println("Welcome, do you want to play? 1 for yes, 2 for no.  ");
        int play = user.nextInt();
        String[] words = {"smart", "april","harpy","cloud","lucky"};
        Random r = new Random();
        int wordPo = r.nextInt(5);
        String key = words[wordPo];
        while(play == 1)
        {
            int chance = 0;
            int win = 0;
            while(chance != 5)
            {
                System.out.println("please enter a word that is 5 letters long: ");
                String guess = user.next();
                while(guess.length() != 5)
                {
                    System.out.println("please enter a word 5 letters long: ");
                    guess = user.next();
                }
                int notEqual = 0;
                
                for(int i = 0; i < guess.length(); i++)
                {
                    notEqual = 0;
                    for(int j = 0; j < key.length();j++)
                    {
                        if(key.charAt(j) == guess.charAt(i) && j == i)
                        {
                            System.out.print("[" + guess.charAt(i) + "] ");
                            win++;
                            break;
                        }
                        else if(key.charAt(j) == guess.charAt(i) && j != i)
                        {
                            System.out.print("(" + guess.charAt(i) + ") ");
                            break;
                        }
                        else
                        {
                            notEqual++;
                        }
                        
                    }
                    if(win >= 5)
                    {
                        break;
                    }
                    if(notEqual == guess.length())
                    {
                        System.out.print(guess.charAt(i) + " ");
                    }
                }
                System.out.println("");
                chance++;
                if(win == 5)
                {
                    break;
                }
            }
            if(chance == 5)
            {
                System.out.println("");
                System.out.println("Sorry, you lost.");
            }
            if(win == 5)
            {
                System.out.println("");
                System.out.println("Yeah, you won! the word is " + key);
                chance = 5;
            }
            System.out.println("Welcome, do you want to play? 1 for yes, 2 for no.  ");
            play = user.nextInt();
        }
    }
}