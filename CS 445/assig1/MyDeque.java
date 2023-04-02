/**name: Xirui Ren
 * Section: Tu/Th 9:30 - 10:45
 * this class will create a deque that will store data in a circular way.
 */
import java.util.Arrays;

public class MyDeque<T> implements DequeInterface<T>
{
    protected T [] data;
    protected int front, back; // N is number of items
    protected int size;

    public MyDeque(MyDeque<T> old)// this is the construction that will copy the old array
    {
        T[] temp = (T[]) new Object[old.data.length];
        int secFro = old.front, secBac = old.back; 
        if(secFro > secBac)
        {
            if(secFro == old.data.length - 1)
            {
                temp[0] = old.data[secFro];
                for(int i = 0; i <= secBac;i++)
                {
                    temp[i+1] = old.data[i];
                }
            }
            else if(secFro < old.data.length - 1)
            {
                for(int i = secFro ,j = 0; i < old.data.length; i++,j++)
                {
                    temp[j] = old.data[i];
                }
                for(int i = 0, j = old.data.length-secFro; i <= secBac; i++,j++)
                {
                    temp[j] = old.data[i];
                }
            }
            front = 0;
            back= old.size - 1;
        }
        else
        {
            temp = Arrays.copyOf(old.data, old.capacity());
            front = old.front;
            back = old.back;
        }
        data = temp;
        size = old.size;
        
    }

    public MyDeque(int sz)//this contructor will create a new deque with the size
    {
        data = (T[]) new Object[sz];
        size = 0;
        front = 0;
        back = 0;
    }

    public boolean equals(MyDeque<T> rhs)// this method will test whether the two deques are equal or not.
    {
        if(rhs.size != size() || size == 0 || rhs.size == 0)
        {
            return false;
        }
        else
        {
            int tempF = front, tempB = back;
            int secFro = rhs.front;
            int secBac = rhs.back;
            T[] temp = Arrays.copyOf(rhs.data, rhs.capacity());
            T[] daTemp = Arrays.copyOf(data,data.length);
            int notEqu = 0;
            if(secFro > secBac)
            {
                if(secFro == rhs.data.length - 1)
                {
                    temp[0] = rhs.data[secFro];
                    for(int i = 0; i < secBac;i++)
                    {
                        temp[i+1] = rhs.data[i];
                    }
                }
                else if(secFro < rhs.data.length - 1)
                {
                    for(int i = secFro ,j = 0; i < rhs.data.length; i++,j++)
                    {
                        temp[j] = rhs.data[i];
                    }
                    for(int i = 0, j = rhs.data.length-secFro; i <= secBac; i++,j++)
                    {
                        temp[j] = rhs.data[i];
                    }
                }
                secFro = 0;
                secBac = rhs.size - 1;
            }
            if(front > back)
            {
                if(front == data.length - 1)
                {
                    daTemp[0] = data[secFro];
                    for(int i = 0; i <= back;i++)
                    {
                        daTemp[i+1] = data[i];
                    }
                }
                else if(secFro < data.length - 1)
                {
                    for(int i = front ,j = 0; i < data.length; i++,j++)
                    {
                        daTemp[j] = data[i];
                    }
                    for(int i = 0, j = data.length-secFro; i <= back; i++,j++)
                    {
                        daTemp[j] = data[i];
                    }
                }
                tempF = 0;
                tempB = size - 1;
            }
            for(int i = tempF, j = secFro; i <= tempB; i++, j++)
            {
                if(!daTemp[i].equals(temp[j]))
                {
                    notEqu++;
                }
            }
            if(notEqu != 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    public String toString()// this method will return the readable form of thr deque
    {
        StringBuilder b = new StringBuilder();
        if(size == 0)
        {
            return "Nothing in the deque";
        }
        else if(front < back)
        {
            for(int i = front; i <= back; i++)
            {
                b.append(data[i] + " ");
            }
        }
        else if(front > back)
        {
            if(front == data.length - 1)
            {
                b.append(data[front] + " ");
                for(int i = 0; i <= back; i++)
                {
                    b.append(data[i] + " ");
                }
            }
            else
            {
                for(int i = front; i < data.length; i++)
                {
                    b.append(data[i] + " ");
                }
                for(int j = 0; j <= back; j++)
                {
                    b.append(data[j] + " ");
                }
            }
        }
        return b.toString();
    }
    //this method will resize the deque when the deque is full.
    protected void resize()
    {
        T[] temp = (T[]) new Object[data.length * 2];
        if(front < back)
        {
            for(int i = front,j = 0; i <= back; i++, j++)
            {
                temp[j] = data[i];
            }
            back = size - 1;
        }
        else
        {
            int secBack = 0;
            if(front == data.length - 1)
            {
                temp[0] = data[front];
                for(int i = 0; i <= back;i++)
                    {
                        temp[i+1] = data[i];
                    }
            }
            else if(front < data.length - 1)
            {
                for(int i = front ,j = 0; i < data.length; i++,j++)
                {
                    temp[j] = data[i];
                }
                for(int i = 0, j = data.length-front; i <= back; i++,j++)
                {
                    temp[j] = data[i];
                    secBack = j;
                }
            }
            back = secBack;
        }
        data = Arrays.copyOf(temp, temp.length);
    }
    /** Adds a new entry to the front of this deque.
       @param newEntry  An object to be added. */
    public void addToFront(T newEntry)
    {
        
        if(size == data.length)
        {
            resize();
            front = 0;
        }
        if(size == 0)
        {
            data[data.length/2] = newEntry;
            front = data.length/2;
            back = data.length/2;
            size++;
        }
        else
        {
            if(front - 1 != -1)
            {
                front--;
                data[front] = newEntry;
                size++;
            }
            else
            {
                front = data.length - 1;
                data[front] =newEntry;
                size++;
            }
        }
    }
    /** Adds a new entry to the back of this deque.
       @param newEntry  An object to be added. */
    public void addToBack(T newEntry)
    {
        if(size == data.length)
        {
            resize();
            front = 0;
        }
        if(size == 0)
        {
            data[data.length/2] = newEntry;
            front = data.length/2;
            back = front;
            size++;
        }
        else
        {
            if(back + 1 != data.length)
            {
                back++;
                data[back] = newEntry;
                size++;
            }
            else
            {
                back = 0;
                data[back] =newEntry;
                size++;
            }
        }
        
    }
   
   /** Removes and returns the front entry of this deque.
       @return  The object at the front of the deque.
       @return  null if the deque is empty before the
                operation. */
    public T removeFront()
    {
        if(size == 0)
        {
            return null;
        }
        else
        {
            T temp = data[front];
            if(front < back)
            {
                size--;
                data[front] = null;
                front++;
            }
            else if(front > back)
            {
                if(front == data.length - 1)
                {
                   size--;
                   data[front] = null;
                   front = 0;
                }
                else if(front < data.length - 1)
                {
                    size--;
                    data[front] = null;
                    front++;
                }

            }
            else if(front == back)
            {
                data[front] = null;
                front = 0;
                back = 0;
                size = 0; 
            }
            return temp;
        }
   }
   /** Removes and returns the back entry of this deque.
       @return  The object at the back of the deque.
       @return  null if the deque is empty before the
                operation. */
   public T removeBack()
   {
        if(size == 0)
        {
            return null;
        }
        else
        {
            T temp = data[back];
            if(back > front)
            {
                size--;
                data[back] = null;
                back--;
            }
            else if(back < front)
            {
                if(back == 0)
                {
                    size--;
                    data[back] = null;
                    back = data.length-1;
                }
                else
                {
                    size--;
                    data[back] = null;
                    back--;
                }
            }
            else if(front == back)
            {
                data[back] = null;
                front = 0;
                back = 0;
                size = 0; 
            }
            return temp;
        }
   }
   
   /** Retrieves the front entry of this deque.
       @return  The object at the front of the deque.
       @return  null if the deque is empty. */
   public T getFront()
   {
        if(size == 0)
        {
            return null;
        }
        else 
        {
            return data[front];
        }
   }
   /** Retrieves the back entry of this deque.
       @return  The object at the back of the deque.
       @return  null if the deque is empty. */
   public T getBack()
   {
        if(size == 0)
        {
            return null;
        }
        else
        {
            return data[back];
        }
   }
   
   /** Detects whether this deque is empty.
       @return  True if the deque is empty, or false otherwise. */
   public boolean isEmpty()
   {
        if(size == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
   }
   
   /*  Removes all entries from this deque. */
   public void clear()
   {
        data = (T[]) new Object[size];
        front = 0;
        back = 0;
        size = 0;
   }
   
   /** Returns number of items currently stored in the Deque.
   	   @return an int equal to the logical size of the Deque.  */
   public int size()
   {
        return size;
   }
   
    /** Returns total capacity of the Deque, counting used and unused
        locations.  Note that this method would NOT typically be part
        of this interface.  It was added solely for testing and grading
        purposes.
   	   @return an int equal to the capacity of the Deque.  */ 
   public int capacity()
   {
        return data.length;
   }
}// deque class end