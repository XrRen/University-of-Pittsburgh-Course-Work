// CMPINF 0401 Fall 2022
// Main program for Assignment 4
// This program should compile and execute using your ArrayCounter and
// LinkedCounter classes and the output should match that shown in file
// a4out.txt.
// Required files for this program are:
// 	 CountInterface.java (given to you)
// 	 ArrayCounter.java (you must write)
// 	 LinkedCounter.java (you must write)

public class Assig4Test
{
	public static void main(String [] args)
	{
		// ArrayCounter constructor contains 2 arguments.  The first is the
		// base, or radix of the count.  The second is the initial maximum
		// number of digits.  LinkedCounter always is sized precisely, so it
		// has only the radix in the constructor.  For both classes, you may
		// assume that the value for radix will be <= 10.
		CountInterface C1 = new ArrayCounter(8, 3);
		CountInterface C2 = new LinkedCounter(8);
		
		// Note that the digits, the radix and the type of counter are shown
		// in the toString() method.  See a4out.txt for the formatting.
		System.out.print(C1.toString());
		System.out.println(" contains " + C1.digits() + " digits");
		System.out.print(C2.toString());
		System.out.println(" contains " + C2.digits() + " digits");
		System.out.println();
		
		// As the count increases more digits will be needed.  For ArrayCounter
		// this may require resizing of the array.  See the output.
		for (int i = 1; i <= 511; i++)
		{
			C1.increment();
			C2.increment();
		}
		
		// Note that the array in the ArrayCounter may be larger than the
		// actual number of digits being used.  However, only the necessary
		// digits should be shown in the toString() method.
		System.out.print(C1.toString());
		System.out.println(" contains " + C1.digits() + " digits");
		System.out.print(C2.toString());
		System.out.println(" contains " + C2.digits() + " digits");
		System.out.println();
		
		C1.increment();
		C2.increment();
		System.out.print(C1.toString());
		System.out.println(" contains " + C1.digits() + " digits");
		System.out.print(C2.toString());
		System.out.println(" contains " + C2.digits() + " digits");
		System.out.println();
		
		// Trying some other instances, using an array of CounterInterface
		// to store the objects.  Note that this is using polymorphism!
		CountInterface [] A = new CountInterface[4];
		A[0] = new ArrayCounter(4, 3);
		A[1] = new LinkedCounter(4);
		A[2] = new ArrayCounter(2, 8);
		A[3] = new LinkedCounter(2);
		for (int i = 1; i <= 255; i++)
		{
			for (int j = 0; j < A.length; j++)
				A[j].increment();	
		}
		for (int j = 0; j < A.length; j++)
		{
			System.out.println(A[j].toString() + " contains " + A[j].digits() + " digits");	
		}
		System.out.println();
		for (int j = 0; j < A.length; j++)
		{
			A[j].increment();	
		}		
		for (int j = 0; j < A.length; j++)
		{
			System.out.println(A[j].toString() + " contains " + A[j].digits() + " digits");	
		}
		System.out.println();
		
		// Testing equals method
		System.out.println("Testing equals() method...");
		A[1].increment();
		A[2].increment();
		for (int i = 0; i < A.length; i++)
		{
			for (int j = 0; j < A.length; j++)
			{
				if (A[i].equals(A[j]))
					System.out.println(A[i] + " equals " + A[j]);
				else
					System.out.println(A[i] + " does not equal " + A[j]);
			}
		}
		System.out.println();
		
		// Testing radix conversion method setRadix
		CountInterface [] B = new CountInterface[2];
		B[0] = C1; // Assign to Array Counter
		B[1] = C2; // Assign to Linked Counter
		System.out.println("Testing radix conversion for Counters...");
		for (int i = 0; i < B.length; i++)
		{
			System.out.print(B[i].toString());
			System.out.println(" contains " + B[i].digits()
						 + " digits with int value " + B[i].getDecimalInt());
			B[i].setRadix(4);
			System.out.print(B[i].toString());
			System.out.println(" contains " + B[i].digits()
						 + " digits with int value " + B[i].getDecimalInt());
			B[i].setRadix(10);
			System.out.print(B[i].toString());
			System.out.println(" contains " + B[i].digits()
						 + " digits with int value " + B[i].getDecimalInt());
			B[i].setRadix(2);
			System.out.print(B[i].toString());
			System.out.println(" contains " + B[i].digits()
						 + " digits with int value " + B[i].getDecimalInt());
			B[i].setRadix(8);
			System.out.print(B[i].toString());
			System.out.println(" contains " + B[i].digits()
						 + " digits with int value " + B[i].getDecimalInt());
			System.out.println();
		}
		
		System.out.println();
	}
}