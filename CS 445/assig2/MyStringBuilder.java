/*
 * Name: Xirui Ren
 * Section: Tue/Thu 9:30-10:45
 * this is a stringBuilder class that we can use it to create a stringbuilder
 * Object and modify it by using a circular list.
 */
// CS 0445 Spring 2023
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a circular, doubly linked list of nodes.  See more comments below on the
// specific requirements for the class.

// You should use this class as the starting point for your implementation. 
// Note that all of the methods are listed -- you need to fill in the method
// bodies.  Note that you may want to add one or more private methods to help
// with your implementation -- that is fine.

// For more details on the general functionality of most of these methods, 
// see the specifications of the similar method in the StringBuilder class.  
public class MyStringBuilder
{
	// These are the only two instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or 
	// StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	// Note: This method is implemented for you.  See code below.  Also read
	// the comments.  The code here may be helpful for some of your other
	// methods.
	public MyStringBuilder(String s)
	{
		if (s == null || s.length() == 0)  // special case for empty String
		{
			firstC = null;
			length = 0;
		}
		else
		{
			firstC = new CNode(s.charAt(0));  // create first node
			length = 1;
			CNode currNode = firstC;
			// Iterate through remainder of the String, creating a new
			// node at the end of the list for each character.  Note
			// how the nodes are being linked and the current reference
			// being moved down the list.
			for (int i = 1; i < s.length(); i++)
			{
				CNode newNode = new CNode(s.charAt(i));  // create Node
				currNode.next = newNode;  	// link new node after current
				newNode.prev = currNode;	// line current before new node
				currNode = newNode;			// move down the list
				length++;
			}
			// After all nodes are created, connect end back to front to make
			// list circular
			currNode.next = firstC;//curr is the end and firstC is the first
			firstC.prev = currNode;//double corcular
		}
	}
	// Create a new MyStringBuilder initialized with the chars in array s. 
	// You may NOT create a String from the parameter and call the first
	// constructor above.  Rather, you must build your MyStringBuilder by
	// accessing the characters in the char array directly.  However, you
	// can approach this in a way similar to the other constructor.
	public MyStringBuilder(char [] s)
	{
		if (s == null || s.length == 0)  // special case for empty array
		{
			firstC = null;
			length = 0;
		}
		else//normal case
		{
			firstC = new CNode(s[0]);
			length = 1;
			CNode curr = firstC;
			for(int i = 1;i < s.length; i++)
			{
				CNode temp = new CNode(s[i]);
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
			curr.next = firstC;
			firstC.prev = curr;
		}
	}
	
	// Copy constructor -- make a new MyStringBuilder from an old one.  Be sure
	// that you make new nodes for the copy (traversing the nodes in the original
	// MyStringBuilder as you do)
	public MyStringBuilder(MyStringBuilder old)
	{
		if(old.length() == 0)//special case if the param MyStringBuilder Onject is invaild
		{
			firstC = null;
			length = 0;
		}
		else//normal case
		{
			firstC = new CNode(old.firstC.data);
			length = 1;
			CNode curr = firstC;
			CNode oldCurr = old.firstC;
			for(int i = 1; i < old.length();i++)
			{
				oldCurr = oldCurr.next;
				CNode temp = new CNode(oldCurr.data);
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
			curr.next = firstC;
			firstC.prev = curr;
		}
	}
	
	// Create a new empty MyStringBuilder
	public MyStringBuilder()
	{
		length = 0;
		firstC = null;
	}

	// Return the entire contents of the current MyStringBuilder as a String
	// For this method you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	// Note: This method is implemented for you.  See code below.
	public String toString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.next;
		}
		return new String(c);
	}
	
	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!  Note
	// that you cannot simply link the two MyStringBuilders together -- that is
	// very simple but it will intermingle the two objects, which you do not want.
	// Thus, you should copy the data in argument b to the end of the current
	// MyStringBuilder.
	 
	public MyStringBuilder append(MyStringBuilder b)
	{
		if(firstC == null || length == 0)//special case when the first node is null
		{								 // or the list is empty
			CNode curr = new CNode(b.firstC.data);
			firstC = curr;
			length++;
			CNode currN = firstC;
			CNode oldCurr = b.firstC;
			for(int i = 1; i < b.length();i++)//can put it out.
			{
				oldCurr = oldCurr.next;
				CNode temp = new CNode(oldCurr.data);
				currN.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
			currN.next = firstC;
			firstC.prev = currN; 
		}
		else//normal cases
		{
			CNode last = firstC.prev;
			CNode oldCurr = b.firstC;
			for(int i = 0; i < b.length();i++)//loop throught the b object to get its data
			{
				CNode temp = new CNode(oldCurr.data);
				last.next = temp;
				temp.prev = last;
				last = temp;
				oldCurr = oldCurr.next;
				length++;
			}
			last.next = firstC;
			firstC.prev = last;
		}
		return this;
	}

	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s)
	{
		CNode curr = new CNode(s.charAt(0));
		if(firstC == null || length == 0)			//case 1 when there's nothing in the list
		{
			firstC = curr;
			length = 1;
			for(int i = 1; i < s.length();i++) 		//loop through the list
			{
				CNode temp = new CNode(s.charAt(i));
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
			curr.next = firstC;
			firstC.prev = curr;
		}
		else		//case 2 when there's soemthing in the list.
		{
			CNode lastN = firstC.prev;
			lastN.next = curr;
			curr.prev = lastN;
			lastN = curr;
			length++;
			for(int i = 1; i < s.length();i++) 		//loop through the list
			{
				CNode temp = new CNode(s.charAt(i));
				lastN.next = temp;
				temp.prev = lastN;
				lastN = temp;
				length++;
			}
			lastN.next = firstC;
			firstC.prev = lastN;
		}
		return this;
	}

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c)
	{
		if(length == 0 || firstC == null)// special case
		{
			
			firstC = new CNode(c[0]);
			CNode curr = firstC;
			for(int i = 1; i < c.length;i++)
			{
				CNode temp = new CNode(c[i]);
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
		}
		else		//case 2 when there's soemthing in the list.
		{
			CNode curr = new CNode(c[0]);
			CNode lastN = firstC.prev;
			lastN.next = curr;
			curr.prev = lastN;
			lastN = curr;
			length++;
			for(int i = 1; i < c.length;i++) 		//loop through the list
			{
				CNode temp = new CNode(c[i]);
				lastN.next = temp;
				temp.prev = lastN;
				lastN = temp;
				length++;
			}
			lastN.next = firstC;
			firstC.prev = lastN;
		}
		return this;
	}

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c)
	{
		if(length == 0 || firstC == null)//special case
		{
			firstC = new CNode(c);
		}
		else//normal case when list exist
		{
			CNode curr = new CNode(c);
			CNode lastN = firstC.prev;
			lastN.next = curr;
			curr.prev = lastN;
			length++;
			curr.next = firstC;
			firstC.prev = curr;
		}
		return this;
	}

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index)
	{
		if(index >= length || index < 0)//spcial case when invaild input was given
		{
			throw new IndexOutOfBoundsException("Illegal Index " + index);
		}
		else//normal case
		{
			CNode curr = firstC;
			for(int i = 0; i < index;i++)
			{
				curr = curr.next;
			}
			return curr.data;
		}
	}

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder, 
	// only remove up until the end of the MyStringBuilder. Be careful for 
	// special cases!
	public MyStringBuilder delete(int start, int end)
	{
		if (start >= 0 && start < end)//if the input is vaild, do opperation
		{
			if(firstC == null || length == 0)//special case when list do not exist
			{
				return null;
			}
			else if(start == 0)//case 1 when delete from the front
			{
				CNode curr = firstC;
				for(int i = 0; i < end;i++)
				{
					curr = curr.next;
				}
				curr.prev = firstC.prev;
				firstC = curr;
				length = length - (end - start);
			}
			else //noraml cases
			{
				if(end < length)
				{
					CNode curr = firstC;
					int index = 0;
					for(int i = 0; i < start - 1;i++)
					{
						curr = curr.next;
						index++;
					}//now behind the node we want to delete
					CNode behindE = curr;
					for(int i = index; i < end;i++)
					{
						behindE = behindE.next;
					}//now behindE is behind the end position node
					curr.next = behindE;
					behindE.prev = curr.next;
					length = length - (end - start);
				}
				else//if end> length, delate all from index end till list's end
				{
					CNode curr = firstC;
					for(int i = 0; i < start - 1; i++)
					{
						curr = curr.next;
					}
					curr.next = firstC;
					firstC.prev = curr;
					length = length- (length - start);
				}
			}
		}
		return this;
	}

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).  If "index"
	// is the last character in the MyStringBuilder, go backward in the list in
	// order to make this operation faster (since the last character is simply
	// the previous of the first character)
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index)
	{
		if(index >= length() || index < 0)// speical case when index is invaild
		{
			return this;
		}
		else if(firstC == null || length == 0)//case 1 when the list is empty, can't do any thing
		{
			return null;
		}
		else if(length == 1)// case 2 when delet the last node
		{
			firstC = null;
			length--;
		}
		else if(index == 0)// case 3 when delete the first node
		{
			CNode curr = firstC.next;
			curr.prev = firstC.prev;
			firstC = curr;
			length--;
		}
		else if(index == length - 1)//case 4 when deleting from the back
		{
			CNode last = firstC.prev;
			last = last.prev;
			last = firstC.prev;
			last.next = firstC;
			length--;
		}
		else//case 5 when deleting from the middle
		{
			CNode before = firstC;
			for(int i = 0; i < index-1; i++)
			{
				before = before.next;
			}
			before.next = before.next.next;
			before.next.next.prev = before;
			length--;
		}
		return this;
	}

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str)// nested loop!
	{
		int index = -1;
		int truthTime = 0;
		CNode curr = firstC;
		int loop = 0;
		CNode temp = curr;
		while(loop < length)
		{
			for(int i = 0; i < str.length();i++)// loop through the str
			{
				if(temp.data == str.charAt(i))// if equal, then the truth value increase
				{
					truthTime++;
					if(truthTime == str.length())//if the truth value equals the length of the string, then find
					{	
						return loop;
					}
					temp = temp.next;// if the datas are equal, move the pointer to the next one.
				}
				else// if not equal, the truth value become 0 and break out the for loop
				{
					truthTime = 0;
					break;
				}
			}
			curr = curr.next;// nove the curr pointer to the next node
			temp = curr;// make the pointer that we are going to 
			loop++;		//use to compare with the str's data to point the 
						//node that vcurr is pointing
		}
		return index;
	}

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" == 
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str)
	{
		CNode curr = new CNode(str.charAt(0));
		if(firstC == null || length == 0)			//case 1 when there's nothing in the list
		{
			firstC = curr;
			length = 1;
			for(int i = 1; i < str.length();i++) 		//loop through the list
			{
				CNode temp = new CNode(str.charAt(i));
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
				length++;
			}
			curr.next = firstC;
			firstC.prev = curr;
		}
		else if(offset == length)// case 2 when the user wants to indert at the back of the list
		{
			append(str);
		}
		else if(offset > length || offset < 0)// case 3 when the input is invaild
		{
			return this;
		}
		else if(offset == 0)// case 4 when want to insert at the front
		{
			CNode firstN = curr;
			for(int i = 1; i < str.length(); i++)
			{
				CNode temp = new CNode(str.charAt(i));
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
			}
			CNode back = firstC.prev;
			CNode front = firstC;
			curr.next = front;
			front.prev = curr;
			back.next = firstN;
			firstN.prev = back;
			firstC = firstN;
			length = length + str.length();
		}
		else// case 5 when want to insert at the middle
		{
			CNode firstN = curr;
			for(int i = 1; i < str.length(); i++)
			{
				CNode temp = new CNode(str.charAt(i));
				curr.next = temp;
				temp.prev = curr;
				curr = temp;
			}
			CNode befNode = firstC;
			for(int i = 0;i < offset - 1; i++)
			{
				befNode = befNode.next;
			}
			CNode afNode = befNode.next;
			curr.next = afNode;
			afNode.prev = curr;
			befNode.next = firstN;
			firstN.prev = befNode;
			length = length + str.length();
		}
		return this;
	} 

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid, 
	// do nothing.
	public MyStringBuilder insert(int offset, char c)
	{
		CNode curr = new CNode(c);
		if(firstC == null || length == 0)			//case 1 when there's nothing in the list
		{
			firstC = new CNode(c);
			length = 1;
			
		}
		else if(offset == length)// case 2 when insert a back of the list
		{
			append(c);
		}
		else if(offset > length || offset < 0)// case 3 when inout is invaild, do nothing
		{
			return this;
		}
		else// normal cases
		{
			
			if(offset == 0)
			{
				CNode firstN = curr;
				CNode back = firstC.prev;
				CNode front = firstC;
				curr.next = front;
				front.prev = curr;
				back.next = firstN;
				firstN.prev = back;
				firstC = firstN;
				length++;
			}
			else
			{
				CNode befNode = firstC;
				for(int i = 0;i < offset - 1; i++)
				{
					befNode = befNode.next;
				}
				CNode afNode = befNode.next;
				
				befNode.next = curr;
				curr.prev = befNode;
				curr.next = afNode;
				afNode.prev = curr;
				length++;
			}
		}
		return this;
	}

	// Return the length of the current MyStringBuilder
	 
	public int length()
	{
		return length;
	}
 
	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.  This method should be done
	// as efficiently as possible.  In particular, you may NOT simply call
	// the delete() method followed by the insert() method, since that will
	// require an extra traversal of the linked list. 
	public MyStringBuilder replace(int start, int end, String str)
	{
		if(start < 0 || start >= length || end <= start)// case 1 when the input is invaild
		{
			return this;
		}
		else if (start >= 0 && start < end)// noraml cases
		{
			CNode temp = new CNode(str.charAt(0));
			CNode firstN = temp;
			for(int i = 1 ; i < str.length();i++)// create a linked list of the String
			{
				CNode strTemp = new CNode(str.charAt(i));
				temp.next = strTemp;
				strTemp.prev = temp;
				temp = strTemp;
			}
			if(firstC == null || length == 0)
			{
				return null;		
			}
			
			else if(start == 0)
			{
				CNode curr = firstC;
				for(int i = 0; i < end;i++)
				{
					curr = curr.next;
				}
				temp.next = curr;
				curr.prev = temp;
				firstN.prev = firstC.prev;
				firstC = firstN;
				length = length - (end - start) + str.length();
			}
			else
			{
				if(end < length)
				{
					CNode curr = firstC;
					int index = 0;
					for(int i = 0; i < start - 1;i++)
					{
						curr = curr.next;
						index++;
					}//now behind the node we want to delete
					CNode behindE = curr;
					for(int i = index; i < end;i++)
					{
						behindE = behindE.next;
					}//now behindE is behind the end position node
					curr.next = firstN;
					firstN.prev = curr.next;
					behindE.prev= temp;
					temp.next = behindE;
					length = length - (end - start)+str.length();
				}
				else
				{
					CNode curr = firstC;
					for(int i = 0; i < start - 1; i++)
					{
						curr = curr.next;
					}
					curr.next = firstN;
					firstN.prev = curr;
					temp. next = firstC;
					firstC.prev = temp;
					length = length- (length - start)+str.length();
				}
			}
		}
		return this;
	}

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder.  For this method
	// you should do the following:
	// 1) Create a character array of the appropriate length
	// 2) Fill the array with the proper characters from your MyStringBuilder
	// 3) Return a new String using the array as an argument, or
	//    return new String(charArray);
	 
	public String substring(int start, int end)
	{
		char[] charArray = new char[end - start];
		CNode curr = firstC;
		int index = 0;
		for(int i = 0; i < start; i++)
		{
			curr = curr.next;
			index++;
			
		}
		for(int i = index, j = 0; i < end; i++,j++)
		{
			charArray[j] = curr.data;
			curr = curr.next;
		}
		return new String(charArray);
	}

	// Return as a String the reverse of the contents of the MyStringBuilder.  Note
	// that this does NOT change the MyStringBuilder in any way.  See substring()
	// above for the basic approach.
	public String revString()
	{
		char [] c = new char[length];
		int i = 0;
		CNode currNode = firstC.prev;
		
		// Since list is circular, we cannot look for null in our loop.
		// Instead we count within our while loop to access each node.
		// Note that in this code we don't even access the prev references
		// since we are simply moving from front to back in the list.
		while (i < length)
		{
			c[i] = currNode.data;
			i++;
			currNode = currNode.prev;
		}
		return new String(c);

	}
	
	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data, next and prev fields directly.
	private class CNode
	{
		private char data;
		private CNode next, prev;

		public CNode(char c)
		{
			data = c;
			next = null;
			prev = null;
		}

		public CNode(char c, CNode n, CNode p)
		{
			data = c;
			next = n;
			prev = p;
		}
	}
}
