package com.qg.exmaple.beautyofprogramming.code111;

import java.util.Arrays;
import java.util.Random;

/**
 * 编程之美 1-12 : NIM(2) "拈"游戏分析
 * @author FunriLy
 * Created by FunriLy on 2017/12/11.
 * From small beginnings comes great things.
 */
public class Test112 {

    /*
    详情参考书本及README文档
     */

    private static final Random RANDOM = new Random();

    /**
     * 产生指定堆数的石头数组，且石头总数一定为奇数
     * @param number 指定数组长度
     * @return 石头数组
     */
    private static int[] createStoneList(int number) {
        int size = 2;
        if (number < size) {
            throw new RuntimeException("非法输入！！！");
        }
        int[] stoneList = new int[number];
        int sum = 0;
        for (int i=0; i<number; i++) {
            stoneList[i] = RANDOM.nextInt(10) + 1;
            sum += stoneList[i];
        }
        if (sum % size == 0) {
            stoneList[0] += 1;
        }
        return stoneList;
    }

    /**
     * 将给定数组进行一次规划，给出数组异或结果为0的解答
     * @param stoneList 给定石头数组
     * @return 异或为0的可能性解答
     */
    private static int[] processStoneList(int[] stoneList) {
        int listLength = 2;
        if (stoneList == null || stoneList.length < listLength) {
            throw new RuntimeException("非法输入！！！");
        }
        int size = stoneList.length;
        int xor = 0;
        for (int num : stoneList) {
            xor ^= num;
        }
        if (xor == 0) {
            // 如果数组本身异或结果为0，直接返回
            return stoneList;
        }

        int index = 0;
        int i = 0;
        for (; i<size; i++) {
            index = xor ^ stoneList[i];
            if (stoneList[i] >= index) {
                break;
            }
        }

        System.out.println("stoneList[" + i + "] 替换为 " + index);

        stoneList[i] = index;
        // 进行验证
        xor = 0;
        for (int num : stoneList) {
            xor ^= num;
        }
        if (xor != 0) {
            return new int[0];
        } else {
            return stoneList;
        }
    }

    public static void main(String[] args) {
        int size = 5;
        for (int i=0; i<size; i++) {
            int[] number = createStoneList(5);
            System.out.println(Arrays.toString(number));
            int[] temp = processStoneList(number);
            System.out.println(Arrays.toString(temp));
            System.out.println("======================");
        }
    }
}
