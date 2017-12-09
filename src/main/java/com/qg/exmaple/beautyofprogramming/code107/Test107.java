package com.qg.exmaple.beautyofprogramming.code107;

/**
 * 编程之美 1-7 : 光影切割问题
 * @author FunriLy
 * Created by FunriLy on 2017/12/9.
 * From small beginnings comes great things.
 */
public class Test107 {

    /*
    解法1：通过归纳找规律。
    在区间[A,B]中，对于n条直线有k个交点，可以得到一条规律 : 块数 = 1 + n + k
    书上对于找到的交点数组按X轴进行排序再查找。我觉得直接遍历整个数组判断即可，不知道为什么要有这个增加时间空间消耗的操作。
     */

    /*
    解法2：求逆序对
    区域内的交点个数等于一个边界上交点顺序相对于另一个边界交点顺序的逆序数的和。
    现在将求交点数问题转化为求一个数组逆序对。(在《剑指Offer》中看过关于逆序对的归并解法)
     */

    /**
     * 归并算法对给定数组进行排序，并求其逆序对数
     * @param data 给定数组
     * @param copy 辅助存储数组
     * @param start 开始索引
     * @param end 终止索引
     * @return 数组中逆序对数
     */
    private static int inversePairsCode(int[] data, int[] copy, int start, int end) {
        /*
        代码注解请看 https://github.com/FunriLy/Sword_Offer/blob/master/src/main/java/com/qg/funrily/offer36/Test36.java
         */
        if (start == end) {
            copy[start] = data[start];
            return 0;
        }
        int length = (end - start) / 2;
        int left = inversePairsCode(copy, data, start, start+length);
        int right = inversePairsCode(copy, data, start+length+1, end);

        int i = start+length;
        int j = end;
        int index = end;
        int count = 0;
        while (i>=start && j>=start+length+1) {
            if (data[i] > data[j]) {
                copy[index--] = data[i--];
                count += (j-start-length);
            } else {
                copy[index--] = data[j--];
            }
        }
        for (; i>=start; i--) {
            copy[index--] = data[i];
        }
        for (; j>=start+length+1; j--) {
            copy[index--] = data[j];
        }

        return left+right+count;
    }

    /**
     * 将给定数组进行复制后求其逆序对数
     * @param data 给定数组
     * @return 逆序对数
     */
    private static int inversePairs(int[] data) {
        if (data == null || data.length <= 1) {
            return -1;
        }
        int[] copy = new int[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return inversePairsCode(data, copy, 0, data.length-1);
    }

    public static void main(String[] args) {
        int[] data = {3, 2, 1};
        System.out.println(inversePairs(data));
    }
}
