import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numberOfWords = 0;
        int charCode = 0;
        try {
            while (charCode >= 0) {
                // Look for next word, skip any leading spaces
                do  {
                    charCode = reader.read(); // charCode should return -1 if end of stream is reached 
                } while (charCode >= 0 && Character.isWhitespace(charCode));
                if (charCode < 0) {
                    break;
                } else {
                    numberOfWords++;
                }
                //Read the rest of the word
                while (charCode > 0 && !(Character.isWhitespace(charCode))) {
                    charCode = reader.read();
                }
                if (charCode < 0) {
                    break;
                } 
            }
            System.out.println(numberOfWords);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
            
}
