/*************************************************************************
 *  Compilation:  javac LZWmod.java
 *  Execution:    java LZWmod - < input.txt > output.lzw  (compress input.txt 
 *                                                         into output.lzw)
 *  Execution:    java LZWmod + < output.lzw > input.rec  (expand output.lzw 
 *                                                         into input.rec)
 *  Dependencies: BinaryStdIn.java BinaryStdOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *
 *************************************************************************/

 public class LZW {
    private static final int R = 256;        // alphabet size
    private static boolean flushIfFull = false;

    public static void compress() {
        // now the min of the codebook will be 9 and the max will be 16
        CompressionCodeBookInterface codebook = 
            new DLBCodeBook(9, 16);
        // we will first write the boolean flushIfFull, which is depend on the user's input, into our codebook
        BinaryStdOut.write(flushIfFull);
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            if(!codebook.advance(c)){ //found longest match
                int codeword = codebook.getCodeWord();
                BinaryStdOut.write(codeword, codebook.getCodewordWidth()); 
                codebook.add(flushIfFull);
                codebook.advance(c);
            }
        }
        int codeword = codebook.getCodeWord();
        BinaryStdOut.write(codeword, codebook.getCodewordWidth()); 

        BinaryStdOut.write(R, codebook.getCodewordWidth()); 
        BinaryStdOut.close();
    }


    public static void expand() {
        // now the min of the codebook will be 9 and the max will be 16
        ExpansionCodeBookInterface codebook = new ArrayCodeBook(9, 16);
        // we will first read the boolean flushIfFull, which is depend on the user's input when compressiong, from our codebook
        flushIfFull = BinaryStdIn.readBoolean();
        int codeword = BinaryStdIn.readInt(codebook.getCodewordWidth(flushIfFull));
        String val = codebook.getString(codeword);

        while (true) {
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(codebook.getCodewordWidth(flushIfFull));

            if (codeword == R) break;
            String s = codebook.getString(codeword);
            if (codebook.size() == codeword) s = val + val.charAt(0); // special case hack

            codebook.add(val + s.charAt(0), flushIfFull);
            val = s;

        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if(args[0].equals("-")) 
        {   
            /** now when the user wants to compress file, they got two options to handle the codebook is full
             * If they input n, then that means they want the keep the old codebook and just ignore all other add to codebook operations
             * If they input r, then that mean they want to rest the codebook, and get ride off all the codeword in the codebook.
             */
            if(args[1].equals("n"))
            {
                flushIfFull = false;
            }
            else if(args[1].equals("r")) // reset
            {
                flushIfFull = true;
            }
            compress();
        }
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }

}
