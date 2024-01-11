/**
 * An implementation of CompressionCodeBookInterface using a DLB Trie.
 */
 public class DLBCodeBook implements CompressionCodeBookInterface {

  private static final int R = 256;        // alphabet size
  private DLBNode root;
  public StringBuilder currentPrefix;
  private DLBNode currentNode;
  private int W;       // current codeword width
  private int minW;    // minimum codeword width
  private int maxW;    // maximum codeword width
  private int L;       // maximum number of codewords with 
                       // current codeword width (L = 2^W)
  private int code;    // next available codeword value


  public DLBCodeBook(int minW, int maxW){
    this.maxW = maxW;
    this.minW = minW;
    currentPrefix = new StringBuilder();
    currentNode = null;
    initialize();
  }

  public void add(String str, boolean flushIfFull){ //something wrong with compression
    boolean haveRoom = false;
    if(root == null){
      root = new DLBNode(str.charAt(0));
    }
    // if the logic size of the codeBook is less than the physical size, then we don't need to do anything to the codebook before adding
    if(code < L){
      haveRoom = true;
    }
    else
    {
      /**else if the codebook is full, then we check if the width of the codebook is still less than the max width
       * if it is still less than, then we can increment the current width and increment the physical size of the codebook by 2
       * Then we are still able to add words to our codebook
      */
      if(W < maxW)
      {
        W++;
        L = L*2;
        haveRoom = true;
      }
      /** if we reached the max, then now we need to check the user's input for reset or not. If they want to reset, then we will call the
       * initialize method to reset our codebook to default, and then we are still able to add the word to the codebook
      */
      else if(flushIfFull)
      {
          initialize();
          haveRoom = true; 
      }
      /** if they don't want to reset, then we will just leave it and say there's no room to add new word. */
      else
      {
          haveRoom = false;
      }   
    }
    if(haveRoom){
      if(str.length() > 0){
        add(root, code, str, 0);
      }
      code++;
    }
  }

  private void add(DLBNode node, int codeword, String word, int index){
    DLBNode current = node;
    char c = word.charAt(index);
    while(current != null){
      if(current.data == c){
        if(index == word.length() - 1){
          current.codeword = codeword;
        } else { //move down
          if(current.child == null){
            current.child = new DLBNode(word.charAt(index+1));
          }
          add(current.child, codeword, word, index+1);
        }
        break;
      } else {
        if(current.sibling == null){
          current.sibling = new DLBNode(c);
        }
        current = current.sibling;
      }
    }
  }

  public int getCodewordWidth(){
      return W;
  }

  private void initialize(){
    root = null;
    W = minW;
    L = 1<<W;
    code = 0;
    for (int i = 0; i < R; i++)
      add("" + (char) i, false);
    add("", false); //R is codeword for EOF
  }


  public boolean advance(char c){
    boolean result = false;
    currentPrefix.append(c);
    if(currentNode == null){
      currentNode = root;
      while(currentNode != null){
        if(currentNode.data == c){
          result = true;
          break;
        }
        currentNode = currentNode.sibling;
      }
    } else {
      DLBNode curr = currentNode.child;
      while(curr != null){
        if(curr.data == c){
          currentNode = curr;
          result = true;
          break;
        }
        curr = curr.sibling;
      }
    }    
    return result;
  }
  
  public void add(boolean flushIfFull){
    boolean haveRoom = false;
    // if the logic size of the codeBook is less than the physical size, then we don't need to do anything to the codebook before adding
    if(code < L){
      haveRoom = true;
    }
    else
    {
      /**else if the codebook is full, then we check if the width of the codebook is still less than the max width
       * if it is still less than, then we can increment the current width and increment the physical size of the codebook by 2
       * Then we are still able to add words to our codebook
      */
      if(W < maxW)
      {
        W++;
        L = L*2;
        haveRoom = true;
      }
      /** if we reached the max, then now we need to check the user's input for reset or not. If they want to reset, then we will call the
       * initialize method to reset our codebook to default, and then we are still able to add the word to the codebook
      */
      else if(flushIfFull)
      {
        initialize();
        haveRoom = true; 
      }
      /** if they don't want to reset, then we will just leave it and say there's no room to add new word. */
      else
      {
        haveRoom = false;
      }      
    }
    if(haveRoom){
      DLBNode newNode = 
        new DLBNode(currentPrefix.charAt(currentPrefix.length()-1));
  
      newNode.codeword = code;
      code++;
      newNode.sibling = currentNode.child;
      currentNode.child = newNode;        
    }

    currentNode = null;
    currentPrefix = new StringBuilder();

  }

  public int getCodeWord() {
    return currentNode.codeword;
  }

  //The DLB node class
  private class DLBNode{
    private char data;
    private DLBNode sibling;
    private DLBNode child;
    private Integer codeword;

    private DLBNode(char data){
        this.data = data;
        child = sibling = null;        
    }
  } 
}