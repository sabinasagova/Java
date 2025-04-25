package sagova.sabina.kalkulacka;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            Stack<Integer> stack = new Stack<>();
            String line;
            boolean corrupt = false;
            while ((line = br.readLine()) != null) {
                corrupt = false;
                if (line.isEmpty()){
                    continue;
                }
                String[] StrArray = line.split("\\s+");
                try {
                    for (String s : StrArray) {
                        if (s.isEmpty()){
                            continue;
                        }
                        if (s.matches("-?\\d+")) {
                            stack.push(Integer.parseInt(s));
                        } else if (s.matches("[+-/*]")) {
                            int n1 = stack.pop();
                            int n2 = stack.pop();
                            switch (s) {
                                case "+":
                                    stack.push(n1 + n2);
                                    break;
                                case "-":
                                    stack.push(n2 - n1);
                                    break;
                                case "/":
                                    if (n1 == 0) {
                                        System.out.println("Malformed expression");
                                    } else {
                                        stack.push(n2 / n1);
                                    }
                                    break;
                                default:
                                    stack.push(n2 * n1);
                                    break;
                            }
                        } else {
                            if (corrupt == false) {
                                System.out.println("Malformed expression");
                                corrupt = true;
                            }
                        }
                    }
                    if (stack.size() > 0) {
                        System.out.println(stack.pop());
                    }
                } catch (Exception e) {
                    System.out.println("Malformed expression");
                }
            }
        } catch (Exception e){
            System.out.println("Malformed expression");
        }
    }
}