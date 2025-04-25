package sagova.sabina;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            int lineSize = (Integer.parseInt(br.readLine()));
            ReadWords(lineSize, br);

        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static void ReadWords(int lineSize, BufferedReader br) {
        List<String> tempWords = new ArrayList<String>();
        int numChars = 0;
        char ch;
        String current = "";
        boolean whitespace = true;
        boolean flag = false;
        boolean newline = false;

        try
        {
            int c;
            while ((c = br.read()) != -1){
                if (!Character.isWhitespace(c)){
                    whitespace = false;
                    flag = false;
                    current += (char) c;
                } else {
                    if ((char) c == '\n'){
                        if (flag){
                            flag = false;
                            if (tempWords.size() > 0){
                                WriteWords(lineSize, lineSize - tempWords.size() + 1, tempWords, newline);
                                tempWords.clear();
                                newline = true;
                            }
                            numChars = 0;
                        } else {
                            flag = true;
                        }
                    }
                    if (((current.length() + numChars + tempWords.size()) < lineSize + 1) && (current != "")){
                        tempWords.add(current);
                        numChars += current.length();
                        current = "";
                        whitespace = true;
                    }
                    else if (!whitespace){
                        if (tempWords.size() > 0){
                            WriteWords(lineSize, numChars, tempWords, newline);
                            tempWords.clear();
                            newline = false;
                        }

                        tempWords.add(current);
                        numChars = current.length();
                        current = "";
                        whitespace = true;
                    }
                }
            }
            if (tempWords.size() > 0){
                WriteWords(lineSize, lineSize - tempWords.size() + 1, tempWords, newline);
                tempWords.clear();
                numChars = 0;
                newline = false;
            }

        } catch (IOException e){
            System.out.println("Error");
        }
    }

    public static void WriteWords(int lineSize, int numOfCharsNow, List<String> Words, boolean endOfLine) {
        int spaceEveryWhere = 0;
        int spaceLeft = 0;
        String spaces = "";
        if ((numOfCharsNow + Words.size()) == (lineSize + 1) && (numOfCharsNow != lineSize)){
            spaceEveryWhere = 1;
        } else if (Words.size() > 1){
            spaceEveryWhere = (lineSize - numOfCharsNow) / (Words.size() - 1);
            spaceLeft =  (lineSize - numOfCharsNow) % (Words.size() - 1);
        } else if (Words.size() == 1){
            spaceEveryWhere = (lineSize - numOfCharsNow);
            spaceLeft = (lineSize - numOfCharsNow);
        }
        for (int i = 0; i < spaceEveryWhere; i++){
            spaces += " ";
        }

        try {
            if (endOfLine){
                System.out.print("\n");
            }
            for (int i = 0; i < Words.size() - 1; i++){
                System.out.print(Words.get(i) + spaces);
                if (i < spaceLeft){
                    System.out.print(" ");
                }
            }
            System.out.print(Words.get(Words.size() - 1));
            System.out.print("\n");
        } catch (Exception e){
            System.out.println("Error");
            return;
        }

    }
}