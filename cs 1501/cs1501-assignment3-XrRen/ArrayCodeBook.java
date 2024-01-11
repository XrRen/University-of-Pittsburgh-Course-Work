/**
 * An implementation of ExpansionCodeBookInterface using an array.
 */

public class ArrayCodeBook implements ExpansionCodeBookInterface {
    private static final int R = 256;        // alphabet size
    private String[] codebook;
    private int W;       // current codeword width
    private int minW;    // minimum codeword width
    private int maxW;    // maximum codeword width
    private int L;       // maximum number of codewords with 
                         // current codeword width (L = 2^W)
    private int code;    // next available codeword value
  

    public ArrayCodeBook(int minW, int maxW){
        this.maxW = maxW;
        this.minW = minW;
        initialize();    
    }
    public int size(){
        return code;
    }


    public int getCodewordWidth(boolean flushIfFull)
    {    
        /** when the logic size of the codebook is greater than or equal to the physical size of the codebook, and our
         * width haven't reach the max yet, then we know if we want to add more words to the dictionary we have to increment the current
         * width by one, so that't why will return w+1 not w.
         */
        if(code >= L && W < maxW)
        {
            return W + 1;
        }
        /** then if we reached the max of the codebook, we now have to check wether we need to rest or not.
         * if we are going to reset, then we know the width will become the min width after reset, so we will return minW
         * but if we are not going to reset, then the width of the codebook will stay at max width for ever, so we will just return maxW
         */
        else if(code >= L /*&& W >= maxW*/)
        {
            if(flushIfFull)
            {
                return minW;
            }
            else
            {
                return maxW;
            }
        }
        /** but for the normal case, we can just return our current width */
        else
        {
            return W;
        }
    }
    
    private void initialize(){
        codebook = new String[1 << maxW];
        W = minW;
        L = 1<<W;
        code = 0;
        // initialize symbol table with all 1-character strings
        for (int i = 0; i < R; i++)
            add("" + (char) i, false);
        add("", false); //R is codeword for EOF
    }

    public void add(String str, boolean flushIfFull) {
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
            codebook[code] = str;
            code++;
        }
    }

    public String getString(int codeword) {
        return codebook[codeword];
    }
    
}
