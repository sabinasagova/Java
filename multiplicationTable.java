package cz.cuni.mff.java.example01;

public class MultiplicationTable {
    public static void main(String[] args){
        int parameter = Integer.parseInt(args[0]);
        for (int i = 1; i < 11; i++){
            if (parameter < 10) {
                System.out.printf("%2d * %d = %2d\n", i, parameter, i * parameter);
            }
            else{
                System.out.printf("%2d * %d = %3d\n", i, parameter, i * parameter);
            }
        }
    }
}

