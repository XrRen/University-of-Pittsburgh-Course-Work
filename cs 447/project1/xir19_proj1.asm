
#import java.io.*;
#import java.util.*;

#public class proj1
#{
#    public static void main(String[] arg)
#    {
#        Scanner user = new Scanner(System.in);
#        System.out.println("Welcome, do you want to play? 1 for yes, 2 for no.  ");
#        int play = user.nextInt();
#        String[] words = {"smart", "april","harpy","cloud","lucky"};
#        Random r = new Random();
#        int wordPo = r.nextInt(5);
#        String key = words[wordPo];
#        while(play == 1)
#        {
#            int chance = 0;
#            int win = 0;
#            while(chance != 5)
#            {
#                System.out.println("please enter a word that is 5 letters long: ");
#                String guess = user.next();
#                while(guess.length() != 5)
#                {
#                    System.out.println("please enter a word 5 letters long: ");
#                    guess = user.next();
#                }
#                int notEqual = 0;
#                
#                for(int i = 0; i < guess.length(); i++)
#                {
#                    notEqual = 0;
#                    for(int j = 0; j < key.length();j++)
#                    {
#                        if(key.charAt(j) == guess.charAt(i) && j == i)
#                        {
#                            System.out.print("[" + guess.charAt(i) + "] ");
#                            win++;
#                            break;
#                        }
#                        else if(key.charAt(j) == guess.charAt(i) && j != i)
#                        {
#                            System.out.print("(" + guess.charAt(i) + ") ");
#                            break;
#                        }
#                        else
#                        {
#                            notEqual++;
#                        }
#                        
#                    }
#                    if(win >= 5)
#                    {
#                        break;
#                    }
#                    if(notEqual == guess.length())
#                    {
#                        System.out.print(guess.charAt(i) + " ");
#                    }
#                }
#                System.out.println("");
#                chance++;
#                if(win == 5)
#                {
#                    break;
#                }
#            }
#            if(chance == 5)
#            {
#                System.out.println("");
#                System.out.println("Sorry, you lost.");
#            }
#            if(win == 5)
#            {
#                System.out.println("");
#               System.out.println("Yeah, you won! the word is " + key);
#                chance = 5;
#            }
#           System.out.println("Welcome, do you want to play? 1 for yes, 2 for no.  ");
#            play = user.nextInt();
#        }
#   }
#}


.data

newline:	.asciiz "\n"
seperator:	.asciiz "====================================="
intro:		.asciiz "Welcome to TNW, let's play a game"
input:		.asciiz "Do you want to play?"
play:		.asciiz "(1) Play"
exit:		.asciiz "(2) Quit"
guess:		.asciiz "Make your guess: "
win:		.asciiz "Congradulation! You won! The word is "
words:		.word	's','m','a','r','t',
			'h','a','r','p','y',
			'l','u','c','k','y',
			'a','p','r','i','l',
			'c','l','o','u','d'
#word1:		.asciiz "smart"
#word2:		.asciiz "happy"
#word3:		.asciiz "lucky"
#word4:		.asciiz "apple"
#word5:		.asciiz "cloud"

rule:		.asciiz "() means right letter but wrong position. [] means right letter and right poristion. If nothing around the letter, means letter is not in the word. \n"

lost:		.asciiz "Sorry, no more chances. You lose!"
r1:		.asciiz "["
r2:		.asciiz "]"
c1:		.asciiz "("
c2:		.asciiz ")"
end:		.asciiz "Thank you for playing!"
inputString:	.asciiz ""
.text
.globl main
# get the starting postion of the word
array_element_address:
# C.1 goes here
	move	t2,a1
	move	t1,a0
	mul	t0,t2,20
	add	t0,t0,t1
	move	v0,t0
	jr	ra
check_letter:
#for(int i = 0; i < inputString.length();i++)
#a0 = word
#a1 = userinput
	move	t2,a1
	move	t3,a0
	addi	t5,t3,0
	la 	a0, newline
	li 	v0, 4
	syscall
	
	li	s0,0 #position counter for key

	li	s2,0 #counter for win or not
	li	s3,0 #counter for search miss
_outloop: #use 2 loop. outer loop for the user's input, inner for hthe key
	lbu	t0,(t2)
	li	s1,0 #position counter for user's input.
	move	t3,t5	
_innerloop:
	lw	t1,(t3)
	sub	t4,t1,t0
	bne	t4,0,_notEqual
	bne	s0,s1,_notPosition
	la	a0,r1
	li	v0,4
	syscall
	move	a0,t0
	li	v0,11
	syscall
	la	a0,r2
	li	v0,4
	syscall
	
	addi	s2,s2,1
	addi	s1,s1,4
	j	_exit
_notEqual:
	addi	s3,s3,1
	j	_continue
_notPosition:
	la	a0,c1
	li	v0,4
	syscall
	move	a0,t0
	li	v0,11
	syscall
	la	a0,c2
	li	v0,4
	syscall
	j	_exit
	
_continue:
	addi	s1,s1,1
	addi	t3,t3,4
	ble	s1,4,_innerloop
	ble	s3,4,_exit
	
	move	a0,t0
	li	v0,11
	syscall
_exit:
	addi	s0,s0,1
	addi	t2,t2,1
	ble	s0,4,_outloop
	move	v0,s2
	jr	ra


main:
_outLoop:
	la 	a0, seperator
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	la 	a0, intro
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	la	a0,rule
	li	v0,4
	syscall
	
	la 	a0, seperator
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	la 	a0, input
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	la 	a0, play
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	la 	a0, exit
	li 	v0, 4
	syscall
	
	la 	a0, newline
	li 	v0, 4
	syscall
	
	li 	v0, 5 
 	syscall
 	move 	t0,v0
	bge 	t0, 2 ,_quit

	li 	v0, 42
	li 	a0, 0
	li 	a1, 5
	syscall
 	
 	move	a1,v0
	la 	a0,words
	jal	array_element_address
	move	s4,v0 #store the address of the beginning of the string.
	#li	v0,11 # albe to get the first letter, rethink how to get the whole word
	#syscall
_afterAssignWord:

	li 	v0, 4              
	la	a0, newline
	syscall
	
	
    	li	t7, 0
_inner_loop:
_ask_input:
    	la 	a0, guess
	li 	v0, 4
	syscall
	
	li      v0, 8
    	la      a0, inputString
    	li      a1, 6
	syscall
	
	move	a0,s4
	la	a1,inputString
	jal	check_letter
	
	move	s0,v0
	li 	v0, 4              
	la	a0, newline
	
	syscall
	
	bne	s0,5,_keepGoing
	
	la	a0, newline
	li	v0, 4
	syscall
	
	la	a0, win
	li	v0, 4
	syscall
	
	la	a0, inputString
	li	v0, 4
	syscall	
	
	la	a0, newline
	li	v0, 4
	syscall
	
	j	_backUp
	#sw v0, (a0)
	#move	t0,a0
_keepGoing:	
    	addi	t7, t7, 1
    	ble	t7, 4, _inner_loop
    	
    	la	a0, lost
	li	v0, 4
	syscall
	
	la	a0, newline
	li	v0, 4
	syscall
	
_backUp:
    	j	_outLoop
_quit:
  	la	a0, end
	li	v0, 4
	syscall
  	li	v0, 10          
    	syscall
