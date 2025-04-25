package algoritmy;

public class QuickSort {
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static void quicksort(int arr[], int lo, int hi){
        if (lo < hi){
            int new_pivot = partition(arr, lo, hi);
            quicksort(arr, lo, new_pivot-1);
            quicksort(arr, new_pivot+1, hi);
        }
    }
    public static int partition(int arr[], int lo, int hi){
        int pivot = arr[hi];
        int i = (lo-1);

        for (int j = lo; j <= hi; j++){
            if (arr[j] < pivot){
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, hi);
        return (i + 1);
    }

    public static void main(String[] args){
        int[] arr = new int[args.length];
        for (int i = 0; i < args.length; i++){
            try{
                arr[i] = Integer.parseInt(args[i]);
            } catch (Exception e){
                System.out.println("Input error");
                return;
            }
        }

        quicksort(arr, 0, arr. length - 1);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
