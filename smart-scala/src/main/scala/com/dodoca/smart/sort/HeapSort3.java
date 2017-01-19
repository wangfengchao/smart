package com.dodoca.smart.sort;

/**
 * Created by  fc.w on 2017/1/16.
 */
public class HeapSort3 {

    public static void main(String[] args) {
        int [] arr = {2, 9, 7, 4, 5, 0, 3, 6, 1, 8};
        haepSort(arr);

        for (int i : arr) {
            System.out.print(i + "\t");
        }
    }

    public static void haepSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            createHeap(data, data.length - i - 1);
            swap(data, 0, data.length - i - 1);
        }
    }

    public static void createHeap(int[] data, int lastIndex) {
        for (int i = (lastIndex -1) / 2 ; i >= 0; i--) {
            int k = i;
            while ((k * 2 + 1) <= lastIndex) {
                int bigIndex = k * 2 + 1;
                if (bigIndex < lastIndex) {
                    if (data[bigIndex] < data[bigIndex + 1]) {
                        bigIndex++;
                    }
                }

                if (data[bigIndex] > data[k]) {
                    swap(data, k, bigIndex);
                    k = bigIndex;
                } else
                    break;
            }
        }
    }

    public static void swap(int[] data, int k, int bigIndex) {
        int tmp = data[k];
        data[k] = data[bigIndex];
        data[bigIndex] = tmp;
    }
}
