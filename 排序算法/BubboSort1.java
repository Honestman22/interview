import java.util.Arrays;

public class BubboSort1 {

    public static void main(String[] args) {

        int[] a = {2,5,3,6,1,0};
        bubboSort(a);
        System.out.println(Arrays.toString(a));
    }

   public static void bubboSort(int[] array){

        int temp =0;
       for (int i = 0; i <array.length ; i++) {

           for (int j = 1; j <(array.length -i) ; j++) {

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
