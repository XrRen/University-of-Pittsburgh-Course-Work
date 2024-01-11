package lab4;
import java.util.Scanner;
public class xir19_lab4C
{
    public static void main(String[] args) {
        String str_explaination = "Hello user\n" + "Welcome to my little game: ~~~~ The right input ~~~~ \n" + "The rules are simple! Just input a number between 30 and 40!\n";
        String str_ask_user_input = "What's your number? ";
        String str_wrong_answer = "Try again :(";
        String str_correct_answer = "Correct!!!";
        int min_range = 30;
        int max_range = 40;
        System.out.println(str_explaination);
        Scanner s = new Scanner(System.in);
        while(true)
        {
            System.out.println(str_ask_user_input);
            int userInput = s.nextInt();
            if(userInput < min_range)
            {
                System.out.println(str_wrong_answer);
            }
            else if(userInput > max_range)
            {
                System.out.println(str_wrong_answer);
            }
            else if(userInput > min_range && userInput < max_range)
            {
                System.out.println(str_correct_answer);
                break;
            }
        }
        s.close();
    }
}