import java.util.Arrays;

public class QuickSort1 {

    public static void main(String[] args) {

        int[] a = {2, 5, 3, 6, 1, 0,9,8};
        quickSort(a,0,a.length-1);
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] array, int start, int end) {

        if (start>=end){
            return;
        }
        int low = start;
        int high = end;

        int base = array[start];

        while (low != high) {
            while (low != high) {
                if (array[high] > base) {
                    high--;
                } else {
                    array[low] = array[high];
                    low++;
                    break;
                }
            }
            while (low != high) {
                if (array[low] < base) {
                    low++;
                } else {
                    array[high] = array[low];
                    high--;
                    break;
                }
            }

        }
        array[low]=base;

        quickSort(array,start,low-1);
        quickSort(array,low+1,end);
    }
}
