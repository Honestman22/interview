import java.util.Arrays;

public class BubboSort {

    public static void main(String[] args) {

        int[] a = {2,5,3,6};
        bubboSort(a);
        System.out.println(Arrays.toString(a));
    }

   public static void bubboSort(int[] array){

        int temp =0;
       for (int i = 0; i <array.length ; i++) {

           for (int j = i+1; j <array.length ; j++) {

               if (array[j-1]<array[j]){

               }else {
                   temp=array[j-1];
                   array[j-1] =array[j];
                   array[j]=temp;
               }
           }
       }
    }
}
