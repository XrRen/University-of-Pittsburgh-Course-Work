/**name: Xirui Ren
 * Section: Tu/Th 9:30 - 10:45
 * this class add or remove elements from the deques.
 */
import java.util.Arrays;

public class IndexAddRemoveDeque<T> extends IndexDeque<T> implements IndexableAddRemove<T>
{
    public IndexAddRemoveDeque(int sz)
    {
        super(sz);
    }
    public IndexAddRemoveDeque(IndexAddRemoveDeque<T> old)
    {
        super(old);
    }
    private void reorder()
    {
        T[] temp = (T[]) new Object[data.length];
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
        front = 0;
        data = Arrays.copyOf(temp, temp.length);
        
    }
    // Add the value at logical location i from the front of the
	// collection, where the front position is logical index 0.
    public void addToFront(int i, T item)
    {
        size++;
        if(size == data.length)
        {
            resize();
        }
        if(size != 1 && size != 2)
        {
            reorder();
            back = size - 1;
            shiftL(back, front + i);
            data[front + i] = item;
        }
        else
        {
            if(size == 1)
            {
                data[front + i] = item;
                front = front + i;
                back = front;  
            }
            else
            {
                back = size - 1;
                shiftL(back, front + i);
                data[front + i] = item;
            }
        }
    }
    // Add the value at logical location i from the back of the
	// collection, where the back position is logical index 0.
    public void addToBack(int i, T item)
    {
        size++;
        if(size == data.length)
        {
            resize();
        }
        if(size != 1 && size != 2)
        {
            reorder();
            shiftL(back, back - i);
            data[back - i] = item;
            back = size - 1;
        }
        else
        {
            if(size == 1)
            {
                data[front + i] = item;
                front = front + i;
                back = front;
            }
            else
            {
                back = size - 1;
                shiftL(back, back - i);
                data[back - i] = item;
                
            }
            
        }
    }
    // remove the value at logical location i from the front of the
	// collection, where the front position is logical index 0.
    public T removeFront(int i)
    {
        if(size != 1)
        {
          reorder();  
        }
        T temp = data[front + i];
        shiftR(front + i, back);
        data[back] = null;
        back--;
        size--;
        return temp;
    }
    // remove the value at logical location i from the back of the
	// collection, where the back position is logical index 0.
    public T removeBack(int i)
    {
        if(size != 1)
        {
          reorder();  
        }
        T temp = data[back - i];
        shiftR(back - i, back);
        data[back] = null;
        back--;
        size--;
        return temp;
    }
    // this method will shift the deque to the left
    private void shiftL(int start, int end)
	{
		for (int i = start; i > end; i--)
		{
			data[i] = data[i-1];
		}
	}
    // this method will shift the deque to the right
    private void shiftR(int start, int end)
	{
		for (int i = start; i < end; i++)
		{
			data[i] = data[i+1];
		}
	}
}//IndexAddRemoveQeque end
