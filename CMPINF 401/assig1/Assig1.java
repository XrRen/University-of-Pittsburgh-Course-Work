/**
* Name: Xirui Ren
* Course: CMPINF 0401
* Section: Tuesday && Thursday 9:30 a.m.
* Exp: This program will simulate a witch shop. And first, it will ask user for a input, begin to shop or quit (1 or 0). If the user choose to shop,
* then it will ask whether the user wants to accept the challenge question or not ( 1 for yes, 0 for no). If accept, and got it right, they will get a 10% discount.
* if they got wrong, then they will have a 10% panetly. But if they choose not to accept the challenge question, then they will just receive the normal price.
* after that, the program will pull out a shopping manu. The user can choose around, make orders, change orders, check the price list, or just check out. If they choose to check out,
* they have to pay for what they actually buy. And if they gave too much money, they program will return the different, if they didn't give enough money, the program will keep 
* asking/waiting for the rest.
* After that user finished, this program will start again if there's another user, which is if the input is 1.
*/
import java.util.*;


public class Assig1
{
	static int  pyPfPr = 0, extEarPr = 0, extEarBagPr = 0, triWaPr = 0, triWaAutoPr = 0; //pric, try to build another pr to check the price change
	static int  puffTotal = 0, earTotal = 0, earBagTotal = 0, wandTotal = 0, autoWaTotal = 0;//total price of the order
	static int total = 0;
	static String[] product = {"Pygmy Puffs" , "Extendable Ears" , "bag of Extendable Ears", "Regular Trick Wands" , "Autographed Trick Wands"};
	
	/**
	* this method will determine whether the user paid just enough, not enough, or overpid.
	* @param x the integer, which reprensent the total amount.
	* @param y the integer, which reprensent the amount that the user insert.
	* @return it will return the difference between the actual paid and the actual total.
	*/
	private static int pay(int x,int y)
	{
		if((x - y) > 0)//means that they didn't pay enought
		{
			System.out.printf("\t You have payed %d in %d %n", y,x);
			System.out.printf("\t You still owed %d knuts %n", (x-y));
			return x-y;
		}
		else if((x-y) < 0)//payed too much
		{
			System.out.printf("\t You have payed %d in %d %n", y,x);
			System.out.printf("\t You have over payed %d knuts %n", (y-x));
			return x-y;
		}
		else
		{
			System.out.println("Thank you!!");
			return 0;
		}
	}
	/**
	* this method will whether the input is vaild or not
	* @param integer x, which reprensent the user input
	* @param integer y, reprensent the min
	* @param integer z, which reprensent the max.
	* @return x after test it vaild or not.
	*/
	private static int isLegel(int x, int y, int z)
	{
		Scanner inScan = new Scanner(System.in);
		while(x > y || x < z)
		{
			System.out.print("I am sorry, but that's a invaild input, please enter again(from " + z + " to " + y + ") => ");
			x = inScan.nextInt(); 
			inScan.nextLine();
		}
		return x;
	}
	/**
	* this method will test if the given input positive or negative.
	* @param integer x, which represent the user input.
	* @return 0 if x is smaller than 0, or xif x is bigger than 0.
	*/
	private static int ifNega(int x)
	{
		if(x < 0)
		{
			System.out.println("Negative number taken as 0");
			x = 0;
			return x;
		}
		else
		{
			return x;
		}	
	}
	/**
	*this method will print out the price list before the discount.
	*/
	private static void priceList()
	{
		pyPfPr = 290;
		extEarPr = 60;
		extEarBagPr = 160;
		triWaPr = 1479;
		triWaAutoPr = 2465;
		System.out.println("Here's our price list:");
		System.out.println("	Pygmy Puffs                         " + pyPfPr + " Knuts each");
		System.out.println("	Extendable Ears                      " + extEarPr + " Knuts each");
		System.out.println("	Extendable Ears(bage of three)      " + extEarBagPr +" Knuts each");
		System.out.println("	Trick Wands(regular)               " + triWaPr + " Knuts each");
		System.out.println("	Trick Wands(autographed)           " + triWaAutoPr + " Knuts each");
	}
	/**
	* this method will print out the price list after getting the discount question right.
	*/
	private static void priceAfterChallenge()
	{
		pyPfPr = 261;
		extEarPr =50;
		extEarBagPr = 140;
		triWaAutoPr = triWaPr;
		System.out.println("	Pygmy Puffs                         " + pyPfPr + " Knuts each");
		System.out.println("	Extendable Ears                      " + extEarPr + " Knuts each");
		System.out.println("	Extendable Ears(bage of three)      " + extEarBagPr +" Knuts each");
		System.out.println("	Trick Wands(regular)               " + triWaPr + " Knuts each");
		System.out.println("	Trick Wands(autographed)           " + triWaAutoPr + " Knuts each");
	}
	/**
	* this method will determine whether the user gets the challenge question right or wrong. 
	* @param x the String, which reprensent the user's input
	* @return a boolean value. If the user types the wrong answer, then it will return false, else, return true.
	*/
	private static boolean challenge(String x)
	{
		//Have a list of challenge questions and choose one randomly for each customer.
		if(x.equals("tomorrow") || x.equals("Tomorrow"))
		{
			System.out.println("Congratulation young fellow!! You got it right!");
			System.out.println("You will receive the following price:");
			priceAfterChallenge();
			return true;
		}
		else
		{	
			System.out.println("Sorry, but you got it wrong. You will have a 10% penalty on your overall bill.");
			return false;
		}
	}
	/**
	* this method will print out the ordering list.
	* @return option the integer which reprensent the user's choice of the meun
	*/
	private static int order()
	{
		Scanner inScan = new Scanner(System.in);
		System.out.println("What do you want to do next?" + '\n');
		System.out.println("		1) Update My Pygmy Puffs Order");
		System.out.println("		2) Update My Extendable Ears Order");
		System.out.println("		3) Update My Trick Wands Order");
		System.out.println("		4) Show the Price List");
		System.out.println("		5) Check Out");
		int option = inScan.nextInt();
		inScan.nextLine();
		return option;
	}
	// this method will find the total price of every bought things
	private static int totalOfAll(int x , int y)
	{
		return x * y;
	}
	/**
	* this method will find out how many bags of extendable ears there are.
	* @param x the integer, which reprensent the total number of extendable ears
	* @return y the integer, which reprensent the amount of bags of extendable ears.
	*/
	private static int bagsOfEars(int x)
	{
		int y = 0;
		if(x%3 == 0)
		{
			y = x/3;
			return y;
		}
		else
		{
			y = (x-(x%3))/3;
			return y;
		}
	}
	
	/**
	* here is the main entrance of the program.
	*/
	public static void main(String arg[])
	{
		int  pygPuff = 0, extenEar = 0, extEarBag = 0, trickWands = 0, autoWands = 0; // amount of orders, user input
		int extenEarNew = 0; // the amount of ears left after putting them in bags
		double penalty = 0; // penalty fee after getting the challenge question wrong.
		int payment = 0; // user input, the amount that the user pays.
		int gallen = 0, stickle = 0, knut = 0; // currency
		int  pyPrBef = 0, extEarPrBef = 0, extEarBagPrBef = 0, triWaPrBef = 0, triWaAutoPrBef = 0;// variables for extra credit
		int  pTotal = 0, eTotal = 0, eBagTotal = 0, wTotal = 0, aWaTotal = 0;// total price if no discount
		int totalBef = 0; // total prive before the discount
		String currency; // user input for the unit of the payments.
		Scanner inScan = new Scanner(System.in);
		System.out.println("Hey there! Welcome to Weasleys' Wizard Wheezes!");
		System.out.print("Is there anything we can help you with?(1 = yes or 0 = no) => ");
		int conform = inScan.nextInt(); 
		inScan.nextLine();
		conform = isLegel(conform,1,0);
		while(conform == 1) // main loop for the program to get started
		{
			pyPrBef = 290;
			extEarPrBef = 60;
			extEarBagPrBef = 160;
			triWaPrBef = 1479;
			triWaAutoPrBef = 2465;
			boolean truFal = true;
			priceList();
			System.out.print("Would you like to try a challenge equation? If you get it right,you will get discounted prices. ");
			System.out.print("But if you get it wrong, you must pay a 10% penalty on your overall bill.");
			
			System.out.print("(1 = yes, 0 = no) => ");
			int quesCon = inScan.nextInt();
			inScan.nextLine();
			System.out.print('\n');
			quesCon = isLegel(quesCon,1,0);
			if(quesCon == 1)
			{
				System.out.println("You do accept the challenge!!");
				System.out.println("\t Here is the question:");
				System.out.print("\t \t What is always coming but never arrives? (please type in your answer) => ");
				String ans = inScan.nextLine();
				truFal = challenge(ans);
			}
			int option = order();
			option = isLegel(option,5,1);
			while(option != 5 )
			{
				// puffs
				if(option == 1)
				{
					System.out.println("Here's your current order:");
					if(pygPuff == 0)
					{
						System.out.println("No Pygmy Puffs order");
					}
					else
					{
						System.out.println("You have " + pygPuff + " Pygmy Puffs in your order");
					}
					System.out.println("How many Pygmy Puffs would you like for " + pyPfPr + " each");
					pygPuff = inScan.nextInt();
					inScan.nextLine();
					pygPuff = ifNega(pygPuff);
					puffTotal = totalOfAll(pygPuff, pyPfPr);
					pTotal = totalOfAll(pygPuff,pyPrBef);
					option = order();
				}
				// ears
				else if(option == 2)
				{
					System.out.println("Here's your current order:");
					if(extenEar == 0 && extenEarNew == 0 && extEarBag == 0)
					{
						System.out.println("No Extendable Ears order");
					}
					else
					{
						if(extenEarNew == 0 && extEarBag != 0)
						{
							System.out.println("You have " + extEarBag +" bags of Extendable Ears in your order");
						}
						else if(extEarBag == 0 &&  extenEarNew != 0 )
						{
							System.out.println("You have " + extenEarNew + " Extendable Ears in your order");
						}
						else
						{	
							System.out.println("You have " + extenEarNew + " Extendable Ears and " + extEarBag +" bags of Extendable Ears in your order");
						}
					}
					System.out.println("How many Extendable Ears would you like for " + extEarPr + " each or " + extEarBagPr + " per bag of 3");
					System.out.println("(Please indicate only the total number you would like)");
					extenEar = inScan.nextInt();
					inScan.nextLine();
					extenEar = ifNega(extenEar);
					
					extEarBag = bagsOfEars(extenEar);
					extenEarNew = extenEar;
					extenEarNew -= 3*extEarBag;
					
					earTotal = totalOfAll(extenEarNew,extEarPr);
					eTotal = totalOfAll(extenEarNew,extEarPrBef);
					earBagTotal = totalOfAll(extEarBag,extEarBagPr);
					eBagTotal = totalOfAll(extEarBag,extEarBagPrBef);
					
					option = order();
				}
				
				else if(option == 3)
				{
					System.out.println("Here's your current order:");
					if(trickWands == 0 && autoWands == 0)
					{
						System.out.println("No Trick Wands(regular or autographed) order");
					}
					else if(trickWands !=0 && autoWands == 0)
					{
						System.out.println("You have " + trickWands + " Trick Wands(regular) in your order");
					}
					else if(trickWands == 0 && autoWands != 0)
					{
						System.out.println("You have " + autoWands + " Trick Wands(autographed) in your order");
					}
					else
					{
						System.out.println("You have " + autoWands + " Trick Wands(autographed) and "+ trickWands + " Trick Wands(regular)in your order");
					}
					System.out.println("How many Trick Wands(regular) would you like for " + triWaPr + " each");
					
					trickWands = inScan.nextInt();
					inScan.nextLine();
					trickWands = ifNega(trickWands);
					wandTotal = totalOfAll(trickWands,triWaPr);
					wTotal = totalOfAll(trickWands,triWaPrBef);
					
					if(truFal == true && quesCon == 1)
					{
						System.out.println("Note: Because you got the challenge question right, your cost for a regular or autographed trick wand will be the same.");
					}
					System.out.println("How many Trick Wands(autographed) would you like for " + triWaAutoPr + " each");

					autoWands = inScan.nextInt();
					inScan.nextLine();
					autoWands = ifNega(autoWands);
					autoWaTotal = totalOfAll(autoWands,triWaAutoPr);
					aWaTotal = totalOfAll(autoWands,triWaAutoPrBef);
					option = order();
				}
				//manu
				else if(option == 4)
				{
					if(truFal == true && quesCon == 1)
					{
						System.out.println("Here's our price list after you got the challenge question right!");
						priceAfterChallenge();
						option = order();
					}
					else
					{
						priceList();
						option = order();
					}
				}
				else
				{
					option = order();
				}				
			}
			/*puffTotal = 0, earTotal = 0, earBagTotal = 0, wandTotal = 0, autoWaTotal = 0;*/
			//use price to check what have been ordered!!
			//total
			
			// if bought nothing, then there should be some other out put.
			// when the custome want to check out, loop ended, start gathering money, change currency, refund, ask for money, gethering all the pieces of value together.
			//static int  pyPfPr = 0, extEarPr = 0, extEarBagPr = 0, triWaPr = 0, triWaAutoPr = 0; //price
			
			//need to be awared what item(s) had been purchased, use if-else to check the price, if the price != 0, then the items had been ordered by the customer
			//else, not order, so no need to be showing in the subtotal. keep in mind about the 10% panetly if truFal == false.
			//need round up for the 10% panetly

			System.out.print("Here is you subtotal:" + '\n');
			if(puffTotal > 0)
			{
				System.out.printf("\t %d %10s for " + pyPfPr + " each %25d knuts %n", pygPuff, product[0], puffTotal);
				total += puffTotal;
				totalBef += pTotal;
			}
			if(earTotal > 0)
			{
				System.out.printf("\t %d %10s for " + extEarPr + " each %22d knuts %n", extenEarNew, product[1], earTotal);
				total += earTotal;
				totalBef += eTotal;
			}
			if(earBagTotal > 0)
			{
				System.out.printf("\t %d %10s for " + extEarBagPr + " each %14d knuts %n", extEarBag, product[2], earBagTotal);
				total += earBagTotal;
				totalBef += eBagTotal;
			}
			if(wandTotal > 0)
			{
				System.out.printf("\t %d %10s for " + triWaPr + " each %16d knuts %n", trickWands, product[3], wandTotal);
				total += wandTotal;
				totalBef += wTotal;
			}
			if(autoWaTotal > 0)
			{
				System.out.printf("\t %d %10s for " + triWaAutoPr + " each %12d knuts %n", autoWands, product[4], autoWaTotal);
				total += autoWaTotal;
				totalBef += aWaTotal;
			}
			if(total > 0)
			{
				int differ = 0;
				differ = totalBef - total;
				System.out.printf("------------------------------------------------------------------------------------------- \n");
				System.out.printf("\t Total: %46d knuts %n",total );
				if(truFal == false) 
				{
					penalty = (double)total * 0.1 + 0.5;
					int newPena = (int)penalty;
					System.out.printf("------------------------------------------------------------------------------------------- \n");
					System.out.printf("\t 10%% panetly fee: %35d knuts %n", newPena);
					total += newPena;
					System.out.printf("------------------------------------------------------------------------------------------- \n");
					System.out.printf("\t New Total:%43d knuts %n",total);
				}
				else if(quesCon == 1 && truFal == true)
				{
					System.out.printf("------------------------------------------------------------------------------------------- \n");
					System.out.printf("\t You have saved: %37d knuts %n", differ);
				}
				System.out.println("Please make a payment in the following format:");
				System.out.println("\t <amount of money(which is an integer)><space><currency(either Knuts, Sickles, or Galleons)>");
				System.out.println("\t here is the currency exchange:");
				System.out.println("\t\t 29 Knuts = 1 Sickle \n \t\t 493 Knuts = 17 Sickles = 1 Galleon");
				while(total > 0)
				{
					//int newTotal = 0;
					System.out.print("\t Please insert your payment:=> ");
					payment = inScan.nextInt();
					//inScan.next();
					currency = inScan.next();
					inScan.nextLine();
					currency = currency.toLowerCase();
					if(currency.equals("knuts") || currency.equals("knut"))
					{
						total = pay(total,payment);
					}
					else if(currency.equals("sickles") || currency.equals("sickle"))
					{
						payment *= 29;
						total = pay(total,payment);
					}
					else if(currency.equals("galleon") || currency.equals("galleons"))
					{
						payment *= 493;
						total = pay(total,payment);
					}
				}
				
				if(total < 0)
				{
					int total1 = 0;
					int total2 = 0;
					total1 = total * -1;
					if(total1%493 != total1)
					{	
						total2 = total1%493;
						total = total1 - total2;
						gallen = total/493;
						if(total2%29 != total2)
						{
							knut = total2%29;
							total = total2 - knut;
							stickle = total/29;
							if(knut != 0)
							{
								System.out.println("Here is you change: " + gallen + " galleons, "  + stickle+ " sickles, and " +  knut + " knuts");
							}
							else
							{
								System.out.println("Here is you change: "  +  gallen + " galleons");
							}
						}
						else
						{
							if(knut != 0)
							{
								System.out.println("Here is you change: " + gallen + " galleon and "  + knut + " knuts");
							}
							else
							{
								System.out.println("Here is you change: "  +  gallen + " galleons");
							}
						}		
					}
					else
					{
						if(total1%29 != total1)
						{
							knut = total1%29;
							total = total1 - knut;
							stickle = total/29;
							if(knut != 0)
							{
								System.out.println("Here is you change: "  + stickle+ " sickles, and " +  knut + " knuts");
							}
							else
							{
								System.out.println("Here is you change: "  +  knut + " knuts");
							}
						}
						else
						{
							System.out.println("Here is you change: " + total1 + " knuts");
						}
					}
				}
			}
			
			else
			{
				System.out.println("Nothing had been purchased.");
			}
			System.out.println("Thank you for purchasing at WWW!!! Enjoying your day!!!! \n");
			
			
			// either the way out of the loop or keep going
			System.out.println("Hey there! Welcome to Weasleys' Wizard Wheezes!");
			System.out.print("\t Is there anything we can help you with?(1 = yes or 0 = no) => ");
			
			conform = inScan.nextInt(); 
			inScan.nextLine();
			conform = isLegel(conform,1,0);
			puffTotal = 0;
			earTotal = 0;
			earBagTotal = 0;
			wandTotal = 0;
			autoWaTotal = 0;
			extenEar = 0;
			pygPuff = 0; extenEarNew = 0; extEarBag = 0; trickWands = 0; autoWands = 0;
		}
		System.out.println("Have a great day!");
	}
}