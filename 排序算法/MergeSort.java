import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] a = {2, 6, 4, 7, 3, 1,9};
        int[] ints = mergeSort(a);
        System.out.println(Arrays.toString(ints));
    }

    private static int[] mergeSort(int[] array) {
        if (array.length == 1) {
            return array;
        }
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        return merge(mergeSort(left), mergeSort(right));

    }

    private static int[] merge(int[] arr1, int[] arr2) {

        int[] a = new int[arr1.length + arr2.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < arr1.length || j < arr2.length) {
            if (i == arr1.length ) {
                a[k] = arr2[j];
                j++;
                k++;
                continue;
            }
            if (j == arr2.length ) {
                a[k] = arr1[i];
                i++;
                k++;
                continue;
            }
            if (i == arr1.length ||arr1[i] > arr2[j]) {
                a[k] = arr2[j];
                j++;
                k++;
            } else {
                a[k] = arr1[i];
                i++;
                k++;
            }
        }
        return a;
    }
}

