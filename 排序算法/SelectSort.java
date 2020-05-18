import java.util.Arrays;

public class SelectSort {
    public static void main(String[] args) {
        int[] a ={2,6,4,7,3};
        selectSort(a);
        System.out.println(Arrays.toString(a));
    }

    private static void selectSort(int[] array) {


        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }
}
