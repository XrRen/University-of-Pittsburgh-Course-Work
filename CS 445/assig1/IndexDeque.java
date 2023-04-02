/**name: Xirui Ren
 * Section: Tu/Th 9:30 - 10:45
 * this class get or change elements from the deques.
 */
public class IndexDeque<T> extends MyDeque<T> implements Indexable<T>
{
    public IndexDeque(int sz)
    {
        super(sz);
    }
    public IndexDeque(IndexDeque<T> old)
    {
        super(old);
    }
    // Get and return the value located at logical location i from the front of the
	// collection, where the front position is logical index 0.  If the collection
	// has fewer than (i+1) items, or if i < 0, throw an IndexOutOfBoundsException 
    public T getFront(int i)
    {
        if(i < 0 || i >= size || i >= data.length)
        {
            throw new IndexOutOfBoundsException("Illegal Index " + i);
        }
        if(size == 0)
        {
            return null;
        }
        else
        {
            T temp;
            if(front < back)
            {
                if(front + i < data.length)
                {
                    temp = data[front + i];
                }
                else
                {
                    i = front + i;
                    int j = i - data.length;
                    temp = data[j];
                }
            }
            else
            {
                int j = 0;
                int q  = i + front;
                j = q - data.length;
                if(j > 0)
                {
                    temp = data[j];
                }
                else if(j == 0)
                {
                    temp = data[0];
                }
                else
                {
                    temp = data[front + i];
                }
            }
            return temp;
        }
    }
    // Get and return the value located at logical location i from the back of the
	// collection, where the back position is logical index 0.  If the collection
	// has fewer than (i+1) items, or if i < 0, throw an IndexOutOfBoundsException 	
    public T getBack(int i) 
    {
        if(i < 0 || i >= size || i >= data.length)
        {
            throw new IndexOutOfBoundsException("Illegal Index " + i);
        }
        if(size == 0)
        {
            return null;
        }
        else
        {
            T temp;
            if(front < back)
            {
                if(back - i >= 0)
                {
                    temp = data[back - i];
                }
                else
                {
                    i = back - i;
                    int j = data.length + i;
                    temp = data[j];
                }
            }
            else
            {
                int q  = back - i;
                if(q > 0)
                {
                    temp = data[q];
                }
                else if(q == 0)
                {
                    temp = data[0];
                }
                else
                {
                    temp = data[data.length + q];
                }
            }
            return temp;
        }
    }
    // Set the value located at logical location i from the front of the
	// collection, where the front position is logical index 0.  If the collection
	// has fewer than (i+1) items, or if i < 0, throw an IndexOutOfBoundsException 
    public void setFront(int i, T item)
    { 
        if(i < 0 || i >= size || i >= data.length)
        {
            throw new IndexOutOfBoundsException("Illegal Index " + i);
        }
        else
        {
            if(front < back)
            {
                if(front + i < data.length)
                {
                    data[front + i] = item;
                }
                else
                {
                    i = front + i;
                    int j = i - data.length;
                    data[j] = item;
                }
            }
            else
            {
                int j = 0;
                int q  = i + front;
                j = q - data.length;
                if(j > 0)
                {
                    data[j] = item;
                }
                else if(j == 0)
                {
                    data[0] = item;
                }
                else
                {
                    data[front + i] = item;
                }
            }
        }
    }
    // Set the value located at logical location i from the back of the
	// collection, where the back position is logical index 0.  If the collection
	// has fewer than (i+1) items, or if i < 0, throw an IndexOutOfBoundsException 	
    public void setBack(int i, T item)
    {
        if(i < 0 || i >= size || i >= data.length)
        {
            throw new IndexOutOfBoundsException("Illegal Index " + i);
        }
         
        else
        {
            if(front < back)
            {
                if(back - i >= 0)
                {
                    data[back - i] = item;
                }
                else
                {
                    i = back - i;
                    int j = data.length + i;
                    data[j] = item;
                }
            }
            else
            {
                int q  = back - i;
                if(q > 0)
                {
                    data[q] = item;
                }
                else if(q == 0)
                {
                    data[0] = item;
                }
                else
                {
                    data[data.length + q] = item;
                }
            }
        }
    }
    // Return the logical size of this Indexable.  Note that this is the same method
	// that is specified in DequeInterface<T>.  It is also part of this interface so
	// that we can iterate through the Indexable using the interface as the reference.
    public int size() // same size() as in DequeInterface<T>
    {
        return size;
    }
}
