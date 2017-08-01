package org.yoqu.one;

/**
 * @author yoqu
 * @date 2017年07月28日
 * @time 下午5:13
 * @email wcjiang2@iflytek.com
 */
public class ArrayFind {

    public static void main(String[] args) {
        int[][] values = {{1, 2}, {2, 3, 5}};

    }


    public boolean find(int target, int[][] array) {
        int oneKey = array.length / 2;
        int twoKey = array[oneKey].length / 2;
        while (true) {
            int[] one = array[oneKey];
            int centerValue = one[twoKey];
            if (target == centerValue) {
                return true;
            } else if (target > centerValue) {

            } else {

            }
        }
        return false;
    }
}
