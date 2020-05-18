import java.util.Arrays;

public class InsertSort {
    public static void main(String[] args) {
        int[] a ={2,6,4,7,3,1};
        insertSort(a);
        System.out.println(Arrays.toString(a));
    }

    private static void insertSort(int[] array) {


        for (int i = 1; i < array.length; i++) {
            int j = i;

            while (j>0&&array[j]<array[j-1]){
                int temp =array[j];
                array[j]=array[j-1];
                array[j-1]= temp;
                j--;
            }

        }
    }
}
