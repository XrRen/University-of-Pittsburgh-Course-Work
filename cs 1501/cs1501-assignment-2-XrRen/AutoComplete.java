
/**
 * An implementation of the AutoCompleteInterface using a DLB Trie.
 */
/**
 * This is an AutoComplete class that can build the DLB trie, read from the DLB trie, check wheter a words is in the DLB tries and predict the
 * next possible words in the trie. 
 */

import java.util.ArrayList;

 public class AutoComplete implements AutoCompleteInterface {

  private DLBNode root; // thie is the root reference, it point to the root(the first DLBNode of the trie)
  private StringBuilder currentPrefix; /*this is a StringBuilder that used to keep track of what the char the user types and will be used to 
  determine where the prefix is in the trie or not and if the user want we can add that prefix to the trie.*/
  private DLBNode currentNode; // This is a DLBNode reference what will kept track of where am I in thr trie.
  private int length = 0; // This will store the height of the node that currentNode point to.
  private int wordLength; // This will store the length of the prefix
  private DLBNode currEndOfSB; // This is a pointer that pointing the node that stores the last character when adding a word.
  //TODO: Add more instance variables as needed

  /**
   * this it the cconstructor that will initialize all the data.
   */
  public AutoComplete(){
    root = null;
    currentPrefix = new StringBuilder();
    currentNode = null;
    wordLength = 0;
    currEndOfSB = null;
  }

  /**
   * This contain method will check wether the trie already have the words or not
   * @param word, The string what we want to add to the tree
   * @return true if the trie contains this word, else return false.
   */
  public boolean contain(String word)
  {
    boolean result = true;
    if (root != null) { // if root = null, then there's no word in the tries
      DLBNode current = root;
      for (int i = 0; i < word.length(); i++) { //treavers the tries to find wether the word is in the tree or not
        char c = word.charAt(i);
        while (current != null) {
          if (current.data == c) {
            break; // find the matched character in the trie
          }
          current = current.nextSibling; // if not match, go to the sibiling.
        }
        if (current == null) // when curr == null, means we reached the end and we didn't find the word.
          break;
        if(i < word.length()-1)// if we are not at the end of the word nor the trie, we go to it's child
          current = current.child;
      }
      if (current == null || current.isWord == false) // no found
        result = false;
    }

    return result;
  }

  /**
   * Adds a word to the dictionary in O(alphabet size*word.length()) time
   * @param word the String to be added to the dictionary
   * @return true if add is successful, false if word already exists
   * @throws IllegalArgumentException if word is the empty string
   */
    public boolean add(String word)
    { //start adding
      if (word == null || word.length() == 0)
        throw new IllegalArgumentException("calls put() with a null word"); // if the word is not valid, throw exceptiom
      if (root == null) { //if the root is null, create a now root
        root = new DLBNode(word.charAt(0));
        //root.size = 1;
      }
      if(contain(word))
      {
        return false; //if the trie contain the word, return false
      }
      DLBNode current = root; //set a pointer to point to root, and will be used to trevearse the trie
      for (int i = 0; i < word.length(); i++) { // foe every letters inside the word
        char c = word.charAt(i); //we set the current letter we are looking at
        while (current.nextSibling != null) { //first we check the sibiling list to check whether the letter exist in the trie or not
          if (current.data == c) { // if we find the letter in the sibiling list
            break; // we break from the while loop
          }
          current = current.nextSibling; // if not found, keep going through the sibiling list.
        }
        if(current.data == c)// if the data of the current node equals to the letter
        {
          current.size++; // we incerment the size
        }
        if (current.data != c) {//c not found in the sibiling list
          DLBNode temp = new DLBNode(c); // we create a new DLBNode with the letter
          temp.size = 1; // set the size to 1 since it only show up once
          temp.previousSibling = current; // connect the node with current node as it sibiling
          current.nextSibling = temp;
          current = current.nextSibling; // then we move down to that new sibiling
        }
        if (i != word.length() - 1) { // if not at the end of the word
          if(current.child == null){ //if the current node has no child
            DLBNode temp = new DLBNode(word.charAt(i+1));// create a new node with the letter after the word
            //temp.size = 1; //but this time we are not going to set the size to 1 because our current pointer haven't been there yet
            temp.parent = current;// connect temp node with the current node as it's child
            current.child = temp;
          }
          current = current.child; // move down to current node's child
        }
    }
    currEndOfSB = current; //after we successfully add the word to the trie, we keep a reference that point to the end of the word
    current.isWord = true; //we set the flag to true so that we know at this letter, there is a word exist
    return true;// return true so that we know we successfully added a word to our trie
      /*if(word == null || word.length() == 0)
      {
        throw(new IllegalArgumentException("Word is empty"));
      }
      
      else
      {
        if(root == null)
        {
          root = new DLBNode(word.charAt(0));
          DLBNode curr = root;
          curr.size = 1;
          for(int i = 1; i < word.length();i++)
          {
            DLBNode temp = new DLBNode(word.charAt(i));
            temp.size = 1;
            temp.parent = curr;
            curr.child = temp;
            curr = curr.child;
          }
          curr.isWord = true;
          return true;
        }
        else
        {
          if(contain(word))
          {
            return false;
          }
          else
          {
            DLBNode curr = root;
            for(int i = 0; i < word.length(); i++)
            {
              char c = word.charAt(i);
              while(curr.nextSibling != null)
              {
                if(curr.data == c)
                {
                  break;
                }
                curr = curr.nextSibling;
              }
              if(curr.data == word.charAt(i))
              {
                curr.size++;
              }
              if(curr.data != word.charAt(i))
              {
                DLBNode temp = new DLBNode(c);
                temp.size = 1;
                curr.nextSibling = temp;
                temp.previousSibling = curr;
                curr = curr.nextSibling;
              }
              if(i != word.length() - 1)
              {
                if(curr.child == null)
                {
                  DLBNode temp = new DLBNode(c);
                  temp.size = 1;
                  curr.child = temp;
                  temp.parent = curr;
                  curr = curr.child;
                }
                else
                {
                  curr = curr.child;
                  
                }
              }
            }
            curr.isWord = true;
            currEndOfSB = curr;
            return true;
          }
        }
      }*/
    }// end of add
  /**
   * appends the character c to the current prefix in O(alphabet size) time. 
   * This method doesn't modify the dictionary.
   * @param c: the character to append
   * @return true if the current prefix after appending c is a prefix to a word 
   * in the dictionary and false otherwise
   */
    public boolean advance(char c){ //begin advancing
      if(currentPrefix == null) //if there's no current prefix exist
      {
        currentPrefix = new StringBuilder(c); //initialize the string builder
        wordLength++;//incurment the size counter of the word
      }
      else
      {
        currentPrefix.append(c); // if we have a string builder already, we just oppend (O(1))
        wordLength++; // incurment the size counter
      }
      length++; // incurment how deep we assume we will go through the trie
      if(root == null) // if there's no root
      {
        length--; // decurment the length because we know we can't go down
        return false;
      }
      if(wordLength == 1) // if the word length is 1, it means we are at the beginning of the trie
      {
        DLBNode curr = root; // so we set a reference to point to root
        if(curr.data == c && wordLength == length) // if the letter matched
        {
          currentNode = curr; //we know we find the prefix in our trie, so we set currentNode to point to where the letter at
          
          return true; //report find prefix
        }
        else if(curr.data != c) //if not equal
        {
          while(curr.nextSibling != null) // we check the sibiling list
          {
            
            if(curr.data == c && wordLength == length) // if the data matched and word length matched with the depth we go through the tries
            {
              currentNode = curr; //we know we find the prefix in our trie, so we set currentNode to point to where the letter at
              return true; // report find
            }
            curr = curr.nextSibling; // go down through the sibiling list
          }
          if(curr.data == c && wordLength == length) // special case when we are at the last node of the sibiling list
          // we checked and find found
          {
            currentNode = curr; //we know we find the prefix in our trie, so we set currentNode to point to where the letter at
            return true; // report found
          }
        }
        length--; // if we never return true, it means we can't find it in our trie, so we decrease the depth we assumed
        return false; // reprot not found
      }
      else //if not at the beginning of the word, then we know we should not look at the root
      {
        DLBNode curr = currentNode; // so we set a reference to point to the currentNode we found before.
        if(curr.child != null) // we go down to the current node's child if it has a child
        {
          curr = curr.child;
          if(curr.data == c && wordLength == length) // if the child match
          {
            currentNode = curr;//we know we find the prefix in our trie, so we set currentNode to point to where the letter at
            return true;// report found
          }
          else if(curr.data != c) //if the the data not match
          {
            while(curr.nextSibling != null) // we check it's sibiling list
            {
              
              if(curr.data == c && wordLength == length)  // if the data matched and word length matched with the depth we go through the tries
              {
                currentNode = curr;//we know we find the prefix in our trie, so we set currentNode to point to where the letter at
                return true; // report found
                
              } 
              curr = curr.nextSibling; // not match, so we go to the next sibiling
            }
            if(curr.data == c && wordLength == length) // special case when we are at the last node of the sibiling list
            // if the last node matched
            {
              currentNode = curr; //we know we find the prefix in our trie, so we set currentNode to point to where the letter at
              return true; // report found
            }
          }
          length--; // if we never return true, it means we can't find it in our trie, so we decrease the depth we assumed
          return false; // report not found
        }
        length--;// if we never return true, it means we can't find it in our trie, so we decrease the depth we assumed
        return false; // report not found
      }
        
    }

  /**
   * removes the last character from the current prefix in O(alphabet size) time. This 
   * method doesn't modify the dictionary.
   * @throws IllegalStateException if the current prefix is the empty string
   */
    public void retreat(){
      if(currentPrefix == null || currentPrefix.length() == 0) //if there's no prefix exist
      {
        throw(new IllegalStateException("Word is empty")); // throw exception
      }
      else // else prefix exist
      {
        if(wordLength > 1) // if it's not the first letter or not only one letter exist in the prefix
        {
          if(currentNode.data == currentPrefix.charAt(wordLength - 1) && length == wordLength) // we check wether it match the data of our current
          //node or not and are that look and the same depth
          {
            currentPrefix.deleteCharAt(wordLength - 1); // then we can delete the last character of the prefix
            wordLength--; // decrease the word lengt
            length--; // decrease the depth we assume we went
            DLBNode curr = currentNode; // set a curr reference to point to current node so that we won't mess up currentNode reference
            if(curr.parent != null) //then we try to find the parent of the current node, if it exist
            {
              currentNode = curr.parent; // we set currentNode reference to point curr's parent
            }
            else //if the parent is null, then we need to find the parent of the whole sibiling lisr 
            {
              while(curr.parent == null) // iterate throught the sibiling list to find the one that connect with a parent
              {
                curr = curr.previousSibling; 
              }
              currentNode = curr.parent; // we set currentNode reference to point curr's parent
            }
          }
          else // letter not match or depth doesn't match
          {
            currentPrefix.deleteCharAt(wordLength - 1); // then we just delete the letter
            wordLength--; // decrease the word lentgh
          }
          
        }
        else // sepcial case when deleting the last character of the list
        {
          currentPrefix = new StringBuilder(); // set the prefix to a new StringBuilder
          currentNode = null; // set currentNode reference to null
          wordLength = 0; // set the word length to 0
          length = 0; //set the assumed depth to 0
        }
        
      }
      
    }

  /**
   * resets the current prefix to the empty string in O(1) time
   */
    public void reset(){
      //TODO: implement this method
      currentNode = root; // currentNode reference to root
      currentPrefix = new StringBuilder(); // create a new StringBuilder
      length = 0; //set assumed depth to 0
      wordLength = 0; // word length to 0
      currEndOfSB = null; // set the reference that point to the end of the word in the trie to null;
    }
    
  /**
   * @return true if the current prefix is a word in the dictionary and false
   * otherwise. The running time is O(1).
   */
    public boolean isWord(){
      //TODO: implement this method
      boolean sameLength = true; //a variable that check does the depth of the node are the same as the length of the word
      if(length != wordLength)
      {
        sameLength = false; // set the variable to false
      }
      return currentNode.isWord && sameLength;
    }

  /**
   * adds the current prefix as a word to the dictionary (if not already a word)
   * The running time is O(alphabet size*length of the current prefix). 
   */
    public void add(){
      //TODO: implement this method
      add(currentPrefix.toString()); //we add the current prefix to the trie by calling the add(String word) method to help us.
      wordLength = currentPrefix.length(); // we set the word length as the prefix length
      length = wordLength; // set the assumed depth as the word length
      currentNode = currEndOfSB; // set the currentNode pointer to point at the last node of the added word in the trie
      /*DLBNode curr = root;
      for(int i = 0; i < wordLength - 1;i++)
      {
        while(curr.nextSibling != null)
        {
          if(curr.data == currentPrefix.charAt(i))
          {
            break;
          }
          curr = curr.nextSibling;
        }
        if(curr.data == currentPrefix.charAt(i))
        {
          curr.size++;
        }
        curr = curr.child;
      }*/
    }

  /** 
   * @return the number of words in the dictionary that start with the current 
   * prefix (including the current prefix if it is a word). The running time is 
   * O(1).
   */
    public int getNumberOfPredictions(){
      if(length == wordLength) //if the depth of the node reference and the length of the word are the same
      {
        return currentNode.size; // return the size
      }
      return 0; // else means the prefix are not in the dictionary, return 0 
    }
  
  /**
   * retrieves one word prediction for the current prefix. The running time is 
   * O(prediction.length())
   * @return a String or null if no predictions exist for the current prefix
   */
    public String retrievePrediction(){
      /*if(!currentNode.isWord && currentNode.child == null)
      {
        return null;
      }*/
      StringBuilder result = new StringBuilder(currentPrefix); //create a new StringBuilder to store the prefix
      if(currentNode.isWord) //if the prefix is already a word
      {
        return currentPrefix.toString(); // return it
      }
      else if(currentNode.data == currentPrefix.charAt(wordLength-1) && length == wordLength) // else the data match and the depth match
      {
        DLBNode curr = currentNode; // set the curr reference to point to currentNode
        
        while(!curr.isWord) // iterate the trie to find the node that is the end of the word
        {
          curr = curr.child;
          result.append(curr.data); // append the data while iterating
        }
        return result.toString(); //after we break out the while, we know we habe a full word, then we return
      }
      return null; // if the prefix is not a prefix of our dictionary, return null
    }


  /* ==============================
   * Helper methods for debugging.
   * ==============================
   */

  //print the subtrie rooted at the node at the end of the start String
  public void printTrie(String start){
    System.out.println("==================== START: DLB Trie Starting from \""+ start + "\" ====================");
    if(start.equals("")){
      printTrie(root, 0);
    } else {
      DLBNode startNode = getNode(root, start, 0);
      if(startNode != null){
        printTrie(startNode.child, 0);
      }
    }
    
    System.out.println("==================== END: DLB Trie Starting from \""+ start + "\" ====================");
  }

  //a helper method for printTrie
  private void printTrie(DLBNode node, int depth){
    if(node != null){
      for(int i=0; i<depth; i++){
        System.out.print(" ");
      }
      System.out.print(node.data);
      if(node.isWord){
        System.out.print(" *");
      }
      System.out.println(" (" + node.size + ")");
      printTrie(node.child, depth+1);
      printTrie(node.nextSibling, depth);
    }
  }

  //return a pointer to the node at the end of the start String 
  //in O(start.length() - index)
  private DLBNode getNode(DLBNode node, String start, int index){
    if(start.length() == 0){
      return node;
    }
    DLBNode result = node;
    if(node != null){
      if((index < start.length()-1) && (node.data == start.charAt(index))) {
          result = getNode(node.child, start, index+1);
      } else if((index == start.length()-1) && (node.data == start.charAt(index))) {
          result = node;
      } else {
          result = getNode(node.nextSibling, start, index);
      }
    }
    return result;
  } 

  //The DLB node class
  private class DLBNode{
    private char data;
    private int size; // total usages of the node
    private boolean isWord;
    private DLBNode nextSibling;
    private DLBNode previousSibling;
    private DLBNode child;
    private DLBNode parent;

    private DLBNode(char data){
        this.data = data;
        size = 0;
        isWord = false; // is this node the end of the word
        nextSibling = previousSibling = child = parent = null;
    }
  }
}