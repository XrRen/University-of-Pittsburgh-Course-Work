// CMPINF 0401 Fall 2022
// CountInterface for Assignment 4
// Note the methods below.  You must implement all of these methods
// in your ArrayCounter and LinkedCounter classes.  See more details
// in the Assignment 4 document.

public interface CountInterface
{
	public void increment();
	// Add 1 to the counter value, increasing the values of all affected
	// digits. This method may cause the number of digits to increase, if
	// the new value cannot be represented with the previous digits.
	
	public void reset();
	// Reset the counter back to its initial state (and value of 0).

	public int digits();
	// Return the number of digits being represented by the CountInterface
	// This should NOT include any leading 0s.
	
	public void setRadix(int rad);
	// Set the radix of this counter to the new value rad.  This will be
	// the "base" for the counter.  In order to do this you must convert
	// the digits in your current counter (with the current radix) into
	// new digits using the new radix.  See details in the Assignment sheet.
	
	public int getDecimalInt();
	// Return the "value" of this CountInterface as a decimal int value.
	// Note: You may NOT store the decimal value for the counter as an int
	// instance variable within your ArrayCounter or LinkedCounter.  In this
	// method you must calculate the decimal int value based on the individual
	// digits stored in your array or linked list.  This will require some
	// thought and perhaps some trial and error.
	
	public boolean equals(CountInterface arg);
	// Return true if this CountInterface and the argument CounterInterface have 
	// the same value, regardless of the radix.  So, for example if a 
	// CounterInterface C1 was 1000 radix 8 and CounterInterface C2 was 512 radix
	// 10, the expression C1.equals(C2) would return true.  Note that you may use 
	// the getDecimalInt() method above to make this method very simple.

	public String toString();
	// Return a String representation of the counter with the digits shown 
	// correctly. It should also show the radix and the type of counter (array or
	// linked list).  See sample output for content and format.
}