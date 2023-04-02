public class Assig4DecTest 
{
    public static void main(String [] arg)
    {
        System.out.println("This is the Assigment 4 decrement tester.");
        System.out.println("It will test the decrement() method.");

        CountInterface C1 = new ArrayCounter(8, 3);
		LinkedCounter C2 = new LinkedCounter(8);

        System.out.print(C1.toString());
		System.out.println(" contains " + C1.digits() + " digits");
		System.out.print(C2.toString());
		System.out.println(" contains " + C2.digits() + " digits");
		System.out.println();
		
		for (int i = 1; i <= 511; i++)
		{
			C1.increment();
			C2.increment();
		}
		
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

		
    }
}
