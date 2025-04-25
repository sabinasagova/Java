package game.of.life;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameOfLife {
    int size;
    int steps;
    char[][] arr;
    char[][] arr_new;

    ExecutorService es;
    public GameOfLife(int size, int steps){
        this.size = size;
        this.steps = steps;
        this.es = Executors.newFixedThreadPool(java.lang.Runtime.getRuntime().availableProcessors());
    }

    public int count_neighbours(int i, int j) {
        int alive = 0;
        int min_x = 0;
        int min_y = 0;
        int max_x = size-1;
        int max_y = size-1;
        int start_x = (i - 1 < min_x) ? size-1 : i-1;
        int start_y = (j - 1 < min_y) ? size-1 : j-1;
        int end_x =   (i + 1 > max_x) ? 0 : i+1;
        int end_y =   (j + 1 > max_y) ? 0 : j+1;

            if (arr[start_x][start_y] == 'X') {
                alive++;
            }

            if (arr[start_x][j] == 'X') {
                alive++;
            }
            if (arr[start_x][end_y] == 'X') {
                alive++;
            }
            if (arr[i][start_y] == 'X') {
                alive++;
            }
            if (arr[i][end_y] == 'X') {
                alive++;
            }
            if (arr[end_x][start_y] == 'X') {
                alive++;
            }
            if (arr[end_x][j] == 'X') {
                alive++;
            }
            if (arr[end_x][end_y] == 'X') {
                alive++;
            }

        return alive;
    }

    public void evaluate() {

        arr_new = new char[size][size];

        List<Callable<Integer>> list = new ArrayList<>();

        for (int i = 0; i < size; i++){
            list.add(new Step(i));
        }

        try {
            es.invokeAll(list);
        } catch (Exception e){
            return;
        }

        arr = arr_new;
    }
    public void start(BufferedReader br){
        String line;
        int y_axis = 0;
        arr = new char[size][size];
        try {
            for (int i = 0; i < size; i++){
                line = br.readLine();
                char[] chars = line.toCharArray();
                arr[y_axis] = chars;
                y_axis++;
            }

            for (int i = 0; i < steps; i++){
                evaluate();
            }

            for (int i = 0; i < size; i++){
                for (int j = 0; j < size; j++) {
                    System.out.print(arr[i][j]);
                }
                System.out.println();
            }

            es.shutdown();
        } catch(IOException e){
            System.out.println("Error");
        }
    }

    private class Step implements Callable<Integer> {

        int i;

        public Step(int i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            for (int j = 0; j < size; j++){
                if (arr[i][j] == 'X'){
                    int neighbours = count_neighbours(i,j);
                    if (neighbours < 2){
                        arr_new[i][j] = '_';
                    }
                    if (neighbours == 2 || neighbours == 3) {
                        arr_new[i][j] = 'X';
                    } if (neighbours > 3){
                        arr_new[i][j] = '_';
                    }

                } else if (arr[i][j] == '_'){
                    int neighbours = count_neighbours(i,j);
                    if (neighbours == 3){
                        arr_new[i][j] = 'X';
                    } else {
                        arr_new[i][j] = '_';
                    }
                }
            }

            return null;
        }
    }


}
