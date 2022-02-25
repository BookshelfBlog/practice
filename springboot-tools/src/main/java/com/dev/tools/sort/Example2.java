package com.dev.tools.sort;

import java.util.Arrays;

/**
 * @author niu hao
 * @describe
 * @date 2022-02-2022/2/25
 */
public class Example2 {
    public static void heapSort(int[] arr){
        if (arr == null || arr.length < 2){
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            heapInsert(arr, i);
        }
        int heapSize = arr.length;
        swap(arr, 0, --heapSize);
        while (heapSize > 0){
            heapIfy(arr, 0, heapSize);
            swap(arr, 0, --heapSize);
        }
    }

    public static void heapInsert(int[] arr,int index){
        while (arr[index] > arr[(index - 1) / 2]){
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public static void heapIfy(int[] arr, int index, int heapSize) {
        int p1 = index * 2 + 1;
        while (p1 < heapSize){
            int largest = p1 + 1 < heapSize && arr[p1] < arr[p1 + 1] ? p1 + 1 : p1;
            largest = arr[index] < arr[largest] ? largest : index;
            if (index == largest){
                break;
            }
            swap(arr, index, largest);
            index = largest;
            p1 = index * 2 + 1;
        }
    }

    public static void swap(int[] arr, int a, int b){
        if (a == b){
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    public static void main(String[] args) {
        int[] arr = {1,2,2,5,9,6,8,3,4,1};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
