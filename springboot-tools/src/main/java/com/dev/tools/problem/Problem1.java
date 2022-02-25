package com.dev.tools.problem;

/**
 * @author niu hao
 * @describe
 * @date 2022-02-2022/2/24
 */
public class Problem1 {
    /**
     * 求小和
     *
     * 在一个数组中，一个数左侧所有小于它的数的总和，叫做它的小和
     *
     * @param arr
     * @param l
     * @param r
     * @return int
     */
    public static int smallSum(int[] arr, int l, int r) {
        if (l == r){
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return smallSum(arr, l, mid) + smallSum(arr, mid + 1, r) + merger(arr, l, mid, r);
    }

    public static int merger(int[] arr, int l, int m, int r) {
        int[] tmp = new int[r - l + 1];
        int i = 0,res = 0;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            tmp[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= m){
            tmp[i++] = arr[p1++];
        }
        while (p2 <= m){
            tmp[i++] = arr[p2++];
        }
        System.arraycopy(tmp, 0, arr, l, r - l + 1);
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1,9,5,4,6,8};
        System.out.println(smallSum(arr, 0, arr.length - 1));
    }
}
