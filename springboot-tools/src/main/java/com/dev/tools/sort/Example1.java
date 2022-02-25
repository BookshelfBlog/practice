package com.dev.tools.sort;

import com.dev.tools.util.ReflectionUtil;
import java.util.*;

/**
 * @ClassName : Example1  //类名
 * @Description : 冒泡  //描述
 * @Author :   //作者
 */
public class Example1 {

    /**
     * 冒泡
     * @describe 每次对比相邻两个元素，将较大的元素放在右边
     */
    public static void bubbleSort(int[] list){
        if (isEmpty(list) || list.length < 3){
            return;
        }
        boolean sort = true;
        for (int i = list.length - 1; i > 0 && sort; i--) {
            sort = false;
            for (int j = 0; j < i; j++) {
                if (list[j] > list[j + 1]) {
                    swap(list, j, j + 1);
                    sort = true;
                }
            }
        }
    }

    /**
     * 异或法则(a、b不能为同一引用)
     * 1.恒等律:x ^ 0 = x
     * 2.归零律:x ^ x = 0
     * 3.结合律:x ^ y ^ z = (x ^ y) ^ z = x ^ (y ^ z)
     * 4.自反律:x ^ y ^ x = y
     * 5.交换律:x ^ y = y ^ x
     */
    public static void swap(int[] arr, int a, int b) {
        if (arr[a] == arr[b]){
            return;
        }
        arr[a] = arr[a] ^ arr[b];
        arr[b] = arr[a] ^ arr[b];
        arr[a] = arr[a] ^ arr[b];
    }

    /**
     * 插入
     * @describe 每个元素与前一个元素比较，将较小的元素放在左边
     */
    public static void insertionSort(int[] list){
        if (isEmpty(list) || list.length < 3){
            return;
        }
        for (int i = 0; i < list.length - 1; i++) {
            for (int j = i + 1; j < list.length; j++) {
                if (list[i] > list[j]){
                    swap(list, i, j);
                }
            }
        }
    }

    /**
     * 选择
     * 循环比较数组元素，将较大数记录，单次循环结束交换元素位置
     */
    public static void selectionSort(int[] arr) {
        if (isEmpty(arr) || arr.length < 3){
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            if (i != min){
                swap(arr, i, min);
            }
        }
    }

    /**
     * 希尔
     */
    public static void hillSort(int[] arr) {
        if (isEmpty(arr) || arr.length < 3){
            return;
        }
        for (int i = arr.length / 2; i > 0; i /= 2) {
            for (int j = i; j < arr.length; j++) {
                for (int k = j - i; k >= 0; k -= i) {
                    if (arr[k + i] < arr[k]) {
                        swap(arr, k + i, k);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static void mergeSort(int[] arr, int l, int r) {
        if (isEmpty(arr) || arr.length < 3){
            return;
        }
        if (l == r){
            return;
        }
        int mid = l + ((r - l) >> 1);
        mergeSort(arr, l, mid);
        mergeSort(arr, mid + 1, r);
        merge(arr, l, mid, r);
    }

    public static void merge(int[] arr, int l, int mid, int r){
        int[] tem = new int[r - l + 1];
        int i = 0;
        int s1 = l;
        int s2 = mid + 1;
        while (s1 <= mid && s2 <= r){
            tem[i++] = arr[s1] <= arr[s2] ? arr[s1++] : arr[s2++];
        }
        while (s1 <= mid){
            tem[i++] = arr[s1++];
        }
        while (s2 <= r) {
            tem[i++] = arr[s2++];
        }
        System.arraycopy(tem, 0, arr, l, r - l + 1);
    }

    /**
     * 快排
     * 荷兰国旗问题
     * 1.随机找出一个基数与最后一个元素交换（使用随机数命中的概率更优）
     * 2.找相等区域的左边界与右边界
     * 3.根据边界将左、右区域的元素排序
     */
    public static void fastSort(int[] arr, int l,int r) {
        if (isEmpty(arr) || arr.length < 3){
            return;
        }
        if (l < r){
            swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
            int[] p = partition(arr, l, r);
            fastSort(arr, l, p[0] - 1);
            fastSort(arr, p[1] + 1, r);
        }
    }

    public static int[] partition(int[] arr, int l, int r) {
        //less区域置最左边
        int less = l - 1;
        int more = r;
        while (l < more) {
            if (arr[l] < arr[r]) {
                //把当前数归到less区域，下标+1
                swap(arr, ++less, l++);
            } else if (arr[l] > arr[r]) {
                //把当前数归到more区域，下标-1
                swap(arr, --more, l);
            } else {
                //推着equals区域右移
                l++;
            }
        }
        //把num放到equal区域
        swap(arr, more, r);
        //返回less区域和equal区域的边界，equal区域和more区域的边界
        return new int[] { less + 1, more };
    }

    public static boolean isEmpty(int[] list){
        return Objects.isNull(list) || list.length == 0;
    }

    /**
     * 对数器
     */
    public static void compore(int[] arr){
        Arrays.sort(arr);
    }

    public static void main(String[] args) {
        int[] integers = {8, 3, 2, 1, 4, 6, 4, 7, /*9, 0, 15, 26, 48, 95, 17, 31*/};
        //String[] methods = {"bubbleSort","hillSort","mergeSort","insertionSort","selectionSort","compore"};
        //for (int i = 0; i < methods.length; i++) {
        //    Class<?> clazz = ReflectionUtil.getClazz("com.dev.tools.sort.Example1");
        //    long begin = System.nanoTime();
        //    if (i == 2){
        //        Object test1 = ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(clazz, methods[i], int[].class, int.class, int.class), ReflectionUtil.newInstance(clazz), integers, 0, integers.length - 1);
        //    }else{
        //        Object test1 = ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(clazz, methods[i], int[].class), ReflectionUtil.newInstance(clazz), integers);
        //    }
        //    long end = System.nanoTime();
        //    System.out.println(Arrays.toString(integers));
        //    System.out.println(methods[i] + ":共耗时:" + (end - begin) + "ns");
        //}
        long begin = System.nanoTime();
        fastSort(integers, 0, integers.length - 1);
        long end = System.nanoTime();
        System.out.println("共耗时:" + (end - begin) + "ns");
        System.out.println(Arrays.toString(integers));
//        quickSort(integers,1,5);

    }
}
