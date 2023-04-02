/**
* Name: Xirui Ren
* Course: CMPINF 0401
* Section: Tuesday && Thursday 9:30 a.m.
* Exp: this ArrayCounter class can be used to create a counter by using array list.
*/
import java.util.*;
public class ArrayCounter implements CountInterface
{
    private int radix;
	private int max;
	private int [] counter;
	//constractor of the ArrayCounter that will pass in 2 ints.
	//the int base represent the raidx and maximum represent the max that will used to
	//set the size of the int array.
	public ArrayCounter(int base, int maximum)
	{
		radix = base;
		counter = new int[maximum];
		max = maximum;
	}
	/**
	 * this method will resize the array when called.
	 * @return a int array that have double the size of the old array with old array's data.
	 * @param size the integer, which reprensent the size of the old array.
	 * @param arr the int array, which represent the old arr.
	 */ 
    private int [] resize(int size, int[] arr)
	{
		int [] newA = new int[size*2];
		int j = size*2-1;
		for(int i = size-1; i >=0 ; i--)
		{
			newA[j] = arr[i];
			j--;
		}
		return newA;
	}
	/**
	 * this method will resize an array in an upside down way.
	 * @param size which represent the size of the old array.
	 * @param arr which represent the old array
	 * @return a new array that has double the size of the old array in an upside down way.
	 */
	public int [] resize1(int size, int[] arr)
	{
		int [] newA = new int[size*2];
		for(int i = 0; i < size ; i++)
		{
			newA[i] = arr[i];
		}
		return newA;
	}
	/**
	 * This method will increment the counter by using recursion.
	 * @param arr the int array that represent the counter array
	 * @param pos the integer which represent the position where the method is working on.
	 */
	private void increment(int[] arr,int pos)
	{
		if(pos < 0)
		{
			return;
		}
		if(arr[pos] < radix - 1)
		{
			arr[pos]++;
			return;
		}
		else if(pos > 0)
		{
			arr[pos] = 0;
			increment(arr, pos-1);
		}
	}
	/**
	 * This method will add 1 to the counter value, increasing the values of all affected
	 * digits
	 */
	public void increment()
    {
		int needResize = 0;
		for(int i = 0; i < max;i++)
		{
			if(counter[i] + 1 == radix)
			{
				needResize++;
			}
		}
		if(needResize == max)
		{
			counter = resize(counter.length,counter);
			max = counter.length;
		}
		increment(counter, counter.length-1);
    }
	// Add 1 to the counter value, increasing the values of all affected
	// digits. This method may cause the number of digits to increase, if
	// the new value cannot be represented with the previous digits.
	
	public void reset()
    {
        for(int i = 0; i < counter.length;i++)
        {
            counter[i] = 0;
        }
    }
	// Reset the counter back to its initial state (and value of 0).

	public int digits()
    {
		int x = 0;
		int zeros = 0;
		int digit = 0;
		for(int i = 0;i < max; i++)
		{
			if(counter[i] == 0)
			{
				zeros++;
			}
			if(counter[0] != 0)
			{
				digit++;
			}
			else
			{
				if(counter[i] != 0)
				{
					digit++;
				}
				else if(i-1 > 0)
				{
					if(counter[i-1] !=0 || x != 0)
					{
						digit++;
						x++;
					}
				}
			}
		}
		if(zeros == max)
		{
			digit++;
		}
		return digit;
    }
	// Return the number of digits being represented by the CountInterface
	// This should NOT include any leading 0s.
	// new to redo, something wrong with the setRadix
	public void setRadix(int rad)
    {
		int[] newCounter = new int[1];
		int notDec = 0;
		int dig = 0;
		newCounter[0] = getDecimalInt()%rad;
		notDec = getDecimalInt()/rad;
		radix = rad;
		dig++;
		do
		{
			while(dig + 1 >= newCounter.length)
			{
				newCounter = resize1(newCounter.length,newCounter);
			}
			newCounter[dig] = notDec%rad;
			notDec = notDec/rad;
			dig++;
		}while(notDec != 0);
		counter = Arrays.copyOf(newCounter, newCounter.length);
		int j = 0;
		for(int i = counter.length-1;i >= 0; i--)
		{
			counter[i] = newCounter[j];
			j++;
		}
		max = newCounter.length;
    }
	// Set the radix of this counter to the new value rad.  This will be
	// the "base" for the counter.  In order to do this you must convert
	// the digits in your current counter (with the current radix) into
	// new digits using the new radix.  See details in the Assignment sheet.
	
	public int getDecimalInt()
    {
        int sum = 0;
		int j = 0;
        for(int i = max-1; i >= 0 ;i--)
		{
			sum += counter[i] * Math.pow(radix,j);
			j++;
		}
		return sum;
    }
	// Return the "value" of this CountInterface as a decimal int value.
	// Note: You may NOT store the decimal value for the counter as an int
	// instance variable within your ArrayCounter or LinkedCounter.  In this
	// method you must calculate the decimal int value based on the individual
	// digits stored in your array or linked list.  This will require some
	// thought and perhaps some trial and error.
	
	public boolean equals(CountInterface arg)
    {
		if(getDecimalInt() == arg.getDecimalInt())
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

	public String toString()
    {
		int x = 0;
		int zeros = 0;
		StringBuilder b = new StringBuilder();
		for(int i = 0;i < max; i++)
		{
			if(counter[i] == 0)
			{
				zeros++;
			}
			if(counter[0] != 0)
			{
				b.append(counter[i]);
			}
			else
			{
				if(counter[i] != 0)
				{
					b.append(counter[i]);
				}
				else if(i-1 > 0)
				{
					if(counter[i-1] !=0 || x != 0)
					{
						b.append(counter[i]);
						x++;
					}
				}
			}
		}
		if(zeros == max)
		{
			b.append(counter[0]);
		}
		b.append(" ");
		b.append("R" + radix + "-A ");
		return b.toString();
    }
	// Return a String representation of the counter with the digits shown 
	// correctly. It should also show the radix and the type of counter (array or
	// linked list).  See sample output for content and format.
}
