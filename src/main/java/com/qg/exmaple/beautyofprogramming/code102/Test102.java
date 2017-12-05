package com.qg.exmaple.beautyofprogramming.code102;

/**
 * 编程之美 1-2 : 中国象棋将帅问题
 * @author FunriLy
 * Created by FunriLy on 2017/12/5.
 * From small beginnings comes great things.
 */
public class Test102 {

    /**
     * 定义格子的长度范围为 3
     */
    private static final int LEN = 3;
    /**
     * 定义最大的数字是 9
     */
    private static final int MAX_NUM = 9;

    /**
     * 解法1：采用两个变量
     */
    private static void chineseChess1() {
        for (int i=1; i<=MAX_NUM; i++) {
            for (int j=1; j<MAX_NUM; j++) {
                if (i % LEN != j % LEN) {
                    System.out.println("A = " + i + " --- B =" + j);
                }
            }
        }
    }

    /**
     * 解法2：采用一个变量
     */
    private static void chineseChess2() {
        // 将 i 看为九进制数 100
        int i = 81;
        while ((i--) != 0) {
            // i/9 代表第一位数， i%9 代表第二位数
            if ((i / MAX_NUM % LEN) == (i % MAX_NUM % LEN)) {
                continue;
            }
            // 因为是用 [0,8] 来代替 [1,9]，所以这里需要 +1 操作
            System.out.println("A = " + (i/MAX_NUM+1) + " --- B =" + (i%MAX_NUM+1));
        }
    }

    public static void main(String[] args) {
        System.out.println("解法1 : 采用两个变量");
        chineseChess1();
        System.out.println("======================");
        System.out.println("解法2 : 采用一个变量");
        chineseChess2();
    }
}
