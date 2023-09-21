/*
 * Name: Xirui Ren
 * Section: Tue/Thu 9:30-10:45
 * Assig4 will run 5 sorting algorithms and find out which algorithm is the best and which is the worst. 
 *
 * Dependencies:  This program requires the following:
 *		Interface Sorter<T> in file Sorter.java (provided)
 *		Interface Partitionable<T> in file Partitionable.java (provided)
 *		Class SimplePivot<T> in file SimplePivot.java
 *		Class MedOfThree<T> in file MedOfThree.java
 *		Class RandomPivot<T> in file RandomPivot.java
 *		Class MedOfFive<T> in file MedOfFive.java
 *		Class QuickSort<T> in file QuickSort.java
 *		Class MergeSort<T> in file MergeSort.java
 */
import java.util.*;
public class Assig4
{
	public static Random R = new Random();  // Make a single Random object
	
	// Sorter data will be an ArrayList of Sorter<Integer> objects.  Note
	// the nested type parameters -- think about what they mean.  Polymorphism
	// allows this to work, since all of the sort classes will implement the
	// Sorter<T> interface.
	private ArrayList<Sorter<Integer>> sorts;
	// Data to sort will be an array of Integer
	private Integer [] temp,a;	
	private int size;
	private int runs;
	private final long seed = 1234;
	
	// Fill array with random data
	public void fillArray(Integer [] arr)
	{
		for (int i = 0; i < temp.length; i++)
		{
			// Values will be 0 <= X < 2 billion
			arr[i] = Integer.valueOf(R.nextInt(2000000000));
		}
	}
	
	// this method will shuffle the given array
	public void shuffle(Integer[] a,Random ran)
	{
		for(int i = 0 ; i < a.length; i++)
		{
			int index = ran.nextInt(a.length);
			Integer temp = a[i];
			a[i] = a[index];
			a[index] = temp; 
		}
	}
	public void copyArray(Integer [] orig, Integer [] copy)
	{
		for (int i = 0; i < orig.length; i++)
			copy[i] = orig[i];
	}
	// this method will find min recurses based on the given value. 
	public int getMinRec(ArrayList a, double lookFor)
	{
		if((a.indexOf(lookFor))%6 == 0)
		{
			return 5;
		}
		else if((a.indexOf(lookFor))%6 == 1)
		{
			return 10;
		}
		else if((a.indexOf(lookFor))%6 == 2)
		{
			return 20;
		}
		else if((a.indexOf(lookFor))%6 == 3)
		{
			return 40;
		}
		else if((a.indexOf(lookFor))%6 == 4)
		{
			return 80;
		}
		else if((a.indexOf(lookFor))%6 == 5)
		{
			return 160;
		}
		return 0;
	}
	// this method will find the algorithm's name based on the given value. 
	public String findName(ArrayList a, double lookFor)
	{
		if((a.indexOf(lookFor))/6 == 0)
		{
			return "Simple Pivot QuickSort";
		}
		else if((a.indexOf(lookFor))/6 == 1)
		{
			return "Median of Three QuickSort";
		}
		else if((a.indexOf(lookFor))/6 == 2)
		{
			return "Random Pivot QuickSort";
		}
		else if((a.indexOf(lookFor))/6 == 3)
		{
			return "Median of Five QuickSort";
		}
		else if((a.indexOf(lookFor))/6 == 4)
		{
			return "MergeSort";
		}
		return "nothing";
	}
	public Assig4(String N, String Nruns)
	{
		// Set the array size from the sz parameter, which is passed in from a
		// command line arbumetn -- see main() method below.
		int[] minA = {5,10,20,40,80,160};
		size = Integer.parseInt(N);
		runs = Integer.parseInt(Nruns);
		double start,end;
		double time;
		double average = 0;
		ArrayList<Double>ave = new ArrayList<>();
		System.out.println("Initialization information: ");
		System.out.println("Array size: " + size);
		System.out.println("Number of runs per test: " + runs +'\n');
		// Put the sorting objects into the ArrayList.  Note how each object is being
		// created.  The QuickSort<T> objects are passed implementations of the
		// Partitionable<T> interface in order to allow the 4 different ways of 
		// partitioning the data.
		sorts = new ArrayList<Sorter<Integer>>();
		sorts.add(new QuickSort<Integer>(new SimplePivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfThree<Integer>()));
		sorts.add(new QuickSort<Integer>(new RandomPivot<Integer>()));
		sorts.add(new QuickSort<Integer>(new MedOfFive<Integer>()));
		sorts.add(new MergeSort<Integer>());
		
		temp = new Integer[size];
		a = new Integer[size];
		double slow= 0, fast = 0;
		double[] fastAve = new double[5];
		double[] slowAve = new double[5];
		fillArray(temp);  // put random data into temp array
		
		// Iterate through all of the sorts and test each one
		for (int i = 0; i < sorts.size(); i++)// outest loop, loop through the five types of algorithms.
		{	
					
			for(int j = 0; j < minA.length; j++)// the 6 min value 
			{
				R.setSeed(seed);
				copyArray(temp, a);
				time = 0;
				sorts.get(i).setMin(minA[j]);
				for(int r = 0; r < runs; r++)// loop throught the runs to get the time.
				{
					shuffle(a,R);
					start = System.nanoTime();
					sorts.get(i).sort(a, a.length);
					end = System.nanoTime();
					double nano = end - start;
					double sec = nano/1000000000;
					time += sec ;
				}
				average = time/runs;
				ave.add(average);
			}
			fastAve[i] = ave.get(i*6);
			slowAve[i] = ave.get(i*6);
			for(int index = i*6 + 1; index < (i+1)*6; index++)// find the festest and alowest run of each algorithms
			{
				if(ave.get(index) < fastAve[i])
				{
					fastAve[i] = ave.get(index);
				}
				if(ave.get(index) > slowAve[i])
				{
					slowAve[i] = ave.get(index);
				}
			}
		}

		fast = fastAve[0];
		slow = slowAve[0];
		
		for(int i = 0; i < fastAve.length;i++)// find the overall festest and alowest run
		{
			if(fast > fastAve[i])
			{
				fast = fastAve[i];
			}
			if(slow < slowAve[i])
			{
				slow = slowAve[i];
			}
		}

		System.out.println("After the tests, here is the best setup:");
		System.out.println("Algorithm: " + findName(ave, fast));
		System.out.println("	Min Recurse:	" + getMinRec(ave, fast));
		System.out.println("	Average:	" + fast +" sec" +'\n');

		System.out.println("After the tests, here is the worst setup:");
		System.out.println("Algorithm: " + findName(ave, slow));
		System.out.println("	Min Recurse:	" + getMinRec(ave, slow));
		System.out.println("	Average:	" + slow +" sec" +'\n');

		System.out.println("Here are the per algorithm results:");
		System.out.println("Algorithm: Simple Pivot QuickSort");
		System.out.println("Best Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, fastAve[0]));
		System.out.println("	Average: " + fastAve[0] + '\n');

		System.out.println("Worst Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, slowAve[0]));
		System.out.println("	Average: " + slowAve[0] + '\n');

		System.out.println("Algorithm: Median of Three QuickSort");
		System.out.println("Best Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, fastAve[1]));
		System.out.println("	Average: " + fastAve[1] + '\n');

		System.out.println("Worst Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, slowAve[1]));
		System.out.println("	Average: " + slowAve[1] + '\n');

		System.out.println("Algorithm: Random Pivot QuickSort");
		System.out.println("Best Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, fastAve[2]));
		System.out.println("	Average: " + fastAve[2] + '\n');

		System.out.println("Worst Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, slowAve[2]));
		System.out.println("	Average: " + slowAve[2] + '\n');

		System.out.println("Algorithm: Median of Five QuickSort");
		System.out.println("Best Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, fastAve[3]));
		System.out.println("	Average: " + fastAve[3] + '\n');
		
		System.out.println("Worst Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, slowAve[3]));
		System.out.println("	Average: " + slowAve[3] + '\n');

		System.out.println("Algorithm: MergeSort");
		System.out.println("Best Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, fastAve[4]));
		System.out.println("	Average: " + fastAve[4] + '\n');

		System.out.println("Worst Result: ");
		System.out.println("	Min Recurse: " + getMinRec(ave, slowAve[4]));
		System.out.println("	Average: " + slowAve[4] + '\n');
	}				
	public static void main(String [] args)
	{
		new Assig4(args[0],args[1]);
	}
}

