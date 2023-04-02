/**
* Name: Xirui Ren
* Course: CMPINF 0401
* Section: Tuesday && Thursday 9:30 a.m.
* Exp: this LinkedCounter class can be used to create a counter that
* is grouped by linked list. Each digit stored in a node and linked to the next digit.
*/
public class LinkedCounter implements CountInterface
{
	private int radix;
	private Node front;
	private Node newNode;
	// implement the Node class so that LinkedList can be fonctioned.
	private class Node
	{
		//instance variable of the Node class
		private int data;
		private Node next;
		//constractor of the Node class
		public Node(int val)
		{
			data = val;
			next = null;
		}
		//constractor of the Node class
		public Node(int val, Node nextNode)
		{
			data = val;
			next = nextNode;
		}
	}
	//constractor of the LinkedCounter class, 
	//which takes in a argument that represent the radix
	public LinkedCounter(int base)
	{
		radix = base;
		front = new Node(0);
	}

	/**
	 * This method will add 1 to the counter value, increasing the values of all affected
	 * digits
	 */
    public void increment()
    {
		if(front == null)
		{
			front = new Node(0);
		}
		Node curr = front;
		newNode = new Node(0);
		boolean done = true;
		while(done)
		{
			curr.data++;
			if (curr.data == radix)
			{
				curr.data = 0;
				if (curr.next == null)
				{
					curr.next = newNode;
				}
				curr = curr.next;
			}
			else
			{
				done = false;
			}
		}
    }
	/**
	 * This method will reset the counter back to 0;
	 */
	public void reset()
    {
		front = new Node(0);
    }
	/**
	 * this method will return the digit of the counter
	 * @return an integer that reprent the digits if the counter.
	 */
	public int digits()
    {
		if(front == null)
		{
			front = new Node(0);
		}
		int dig = 0;
        Node curr = front;
		while(curr != null)
		{
			dig++;
			curr = curr.next;
		} 
		return dig;
    }
	
	/**
	 * this method will set the radix of the counter, and also
	 * change the digits in the old radix to the new radix.
	 * @param rad the integer that represent the radix that will changed to.
	 */
	public void setRadix(int rad)
    {
		if(front == null)
		{
			front = new Node(0);
		}
		int notDec = 0;
        Node curr = new Node(getDecimalInt()%rad);
		notDec = getDecimalInt()/rad;
		front = curr;	
		radix = rad;
		while(notDec != 0)
		{
			if(curr.next == null)
			{
				newNode = new Node(0);
				curr.next = newNode;
			}
			curr = curr.next;
			curr.data = notDec%rad;
			notDec = notDec/rad;
		}
    }
	
	/**
	 * this method will return the decimal number of the counter.
	 * @return an integer that represent the decimal number.
	 */
	public int getDecimalInt()
    {
		if(front == null)
		{
			front = new Node(0);
		}
        int sum = 0; 
		Node curr = front;
		int i = 0;
        while(curr != null)
		{
			sum += curr.data * Math.pow(radix,i);
			i++;
			curr = curr.next;
		}
		return sum;
    }
	// Return the "value" of this CountInterface as a decimal int value.
	// Note: You may NOT store the decimal value for the counter as an int
	// instance variable within your ArrayCounter or LinkedCounter.  In this
	// method you must calculate the decimal int value based on the individual
	// digits stored in your array or linked list.  This will require some
	// thought and perhaps some trial and error.
	
	/**
	 * this method will return true if the value of the counters are equals
	 * else return false.
	 * @return a boolean, true if they are equals, else return false.
	 * @param arg the CountInterface that represent the other counter that will be used
	 * to compare.
	 */
	public boolean equals(CountInterface arg)
    {
        if(arg.getDecimalInt() == getDecimalInt())
		{
			return true;
		}
		else
		{
			return false;
		}
    }
	// Return true if this CountInterface and the argument CounterInterface have 
	// the same value, regardless of the radix.  So, for example if a 
	// CounterInterface C1 was 1000 radix 8 and CounterInterface C2 was 512 radix
	// 10, the expression C1.equals(C2) would return true.  Note that you may use 
	// the getDecimalInt() method above to make this method very simple.

	/**
	 * this method will return a string that demenstate the counter in a readable way.
	 */
	public String toString()
    {
        StringBuilder b = new StringBuilder();
		if(front == null)
		{
			front = new Node(0);
		}
		Node curr = front;

		while(curr != null)
		{
			b.append(curr.data);
			curr = curr.next;
		}
		b.reverse();
		b.append(" ");
		b.append("R" + radix + "-L ");
		return b.toString();
    }
	// Return a String representation of the counter with the digits shown 
	// correctly. It should also show the radix and the type of counter (array or
	// linked list).  See sample output for content and format.
}
