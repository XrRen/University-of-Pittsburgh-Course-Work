public class MedOfFive <T extends Comparable<? super T>>  implements Partitionable<T>
{
// 12.17
	/** Task: Partitions an array as part of quick sort into two subarrays
	 *        called Smaller and Larger that are separated by a single
	 *        element called the pivot. 
	 *        Elements in Smaller are <= pivot and appear before the 
	 *        pivot in the array.
	 *        Elements in Larger are >= pivot and appear after the 
	 *        pivot in the array.
	 *  @param a      an array of Comparable objects
	 *  @param first  the integer index of the first array element; 
	 *                first >= 0 and < a.length 
	 *  @param last   the integer index of the last array element; 
	 *                last - first >= 3; last < a.length
	 *  @return the index of the pivot */
	public int partition(T[] a, int first, int last)
	{
        if(a.length < 5)
        {
            throw new IllegalArgumentException("Array must contain at least 5 elements");
        }
	    int mid = (first + last)/2;
        int leftMed = (first + mid)/2;
        int rightMed = (mid + last)/2;
	    MedOfFive(a, first, leftMed, mid,rightMed, last);
	  
	  // Assertion: The pivot is a[mid]; a[first] <= pivot and 
	  // a[last] >= pivot, so do not compare these two array elements
	  // with pivot.
	  
	  // move pivot to next-to-last position in array
	  swap(a, mid, last - 1);
	  int pivotIndex = last - 1;
	  T pivot = a[pivotIndex];
	  
	  // determine subarrays Smaller = a[first..endSmaller]
	  // and                 Larger  = a[endSmaller+1..last-1]
	  // such that elements in Smaller are <= pivot and 
	  // elements in Larger are >= pivot; initially, these subarrays are empty

	  int indexFromLeft = first + 1; 
	  int indexFromRight = last - 2; 
	  boolean done = false;
	  while (!done)
	  {
	    // starting at beginning of array, leave elements that are < pivot;
	    // locate first element that is >= pivot; you will find one,
	    // since last element is >= pivot
	    while (a[indexFromLeft].compareTo(pivot) < 0)
	      indexFromLeft++;
	      
	    // starting at end of array, leave elements that are > pivot; 
	    // locate first element that is <= pivot; you will find one, 
	    // since first element is <= pivot
	    while (a[indexFromRight].compareTo(pivot) > 0)
	      indexFromRight--;
	      
	    assert a[indexFromLeft].compareTo(pivot) >= 0 && 
	           a[indexFromRight].compareTo(pivot) <= 0;
	           
	    if (indexFromLeft < indexFromRight)
	    {
	      swap(a, indexFromLeft, indexFromRight);
	      indexFromLeft++;
	      indexFromRight--;
	    }
	    else 
	      done = true;
	  } // end while
	  
	  // place pivot between Smaller and Larger subarrays
	  swap(a, pivotIndex, indexFromLeft);
	  pivotIndex = indexFromLeft;
	  
	  // Assertion:
	  //   Smaller = a[first..pivotIndex-1]
	  //   Pivot = a[pivotIndex]
	  //   Larger = a[pivotIndex+1..last]
	  
	  return pivotIndex; 
	} // end partition

	// 12.16
	// this will order the five varible into into ascending order
	private static <T extends Comparable<? super T>>
	        void MedOfFive(T[] a, int first,int lMed, int mid, int rMed, int last)
	{
        order(a, first, lMed); 
        order(a, first, mid);
        order(a, first, rMed);
        order(a, first, last);
        order(a, lMed, mid);
        order(a, lMed, rMed);
        order(a, lMed, last);
        order(a, mid, rMed);
        order(a, mid, last); 
        order(a, rMed, last);
	} // end sortFirstMiddleLast

	/** Task: Orders two given array elements into ascending order
	 *        so that a[i] <= a[j].
	 *  @param a  an array of Comparable objects
	 *  @param i  an integer >= 0 and < array.length
	 *  @param j  an integer >= 0 and < array.length */
	private static <T extends Comparable<? super T>>
	        void order(T[] a, int i, int j)
	{
	  if (a[i].compareTo(a[j]) > 0)
	    swap(a, i, j);
	} // end order

  /** Task: Swaps the array elements a[i] and a[j].
   *  @param a  an array of objects
   *  @param i  an integer >= 0 and < a.length
   *  @param j  an integer >= 0 and < a.length */
  private static void swap(Object[] a, int i, int j)
  {
    Object temp = a[i];
    a[i] = a[j];
    a[j] = temp; 
  } // end swap

  public static <T extends Comparable<? super T>> 
	       void insertionSort(T[] a, int n)
	{
		insertionSort(a, 0, n - 1);
	} // end insertionSort

  public static <T extends Comparable<? super T>> 
	       void insertionSort(T[] a, int first, int last)
	{
		int unsorted, index;
		
		for (unsorted = first + 1; unsorted <= last; unsorted++)
		{   // Assertion: a[first] <= a[first + 1] <= ... <= a[unsorted - 1]
		
			T firstUnsorted = a[unsorted];
			
			insertInOrder(firstUnsorted, a, first, unsorted - 1);
		} // end for
	} // end insertionSort

  private static <T extends Comparable<? super T>> 
	        void insertInOrder(T element, T[] a, int begin, int end)
	{
		int index;
		
		for (index = end; (index >= begin) && (element.compareTo(a[index]) < 0); index--)
		{
			a[index + 1] = a[index]; // make room
		} // end for
		
		// Assertion: a[index + 1] is available
		a[index + 1] = element;  // insert
	} // end insertInOrder
   
}
