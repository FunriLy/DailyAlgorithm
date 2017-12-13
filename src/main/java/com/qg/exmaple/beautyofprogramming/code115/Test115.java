package com.qg.exmaple.beautyofprogramming.code115;

import java.util.Arrays;
import java.util.Random;

/**
 * 编程之美 1-15 : 构造数独
 * @author FunriLy
 * Created by FunriLy on 2017/12/12.
 * From small beginnings comes great things.
 */
public class Test115 {

    private static final Random RANDOM = new Random();

    /**
     * 初始化 9*9 矩阵
     * @param chess 矩阵
     */
    private static void init(int[][] chess) {
        int chessSize = 9;
        if (chess == null || chess.length != chessSize) {
            throw new RuntimeException("非法输入！！！");
        }

        // 初始化
        for (int i=0; i<chessSize; i++) {
            for (int j=0; j<chessSize; j++) {
                chess[i][j] = 0;
            }
        }
        for (int i=0; i<chessSize; i++) {
            // 取 0-80 代表棋盘坐标范围
            int temp = RANDOM.nextInt(81);
            chess[temp/9][temp%9] = i+1;
        }
    }

    /**
     * 检查当前格子在 9*9 的矩阵中是否符合数独的要求
     * @param chess 9*9 矩阵
     * @param x 当前坐标 x
     * @param y 当前坐标 y
     * @return 若当前坐标合理返回true，否则返回false
     */
    private static boolean checkIsSafe(int[][] chess, int x, int y) {
        int chessSize = 9;
        if (chess == null || chess.length != chessSize || x < 0 || x >= chessSize || y < 0) {
            throw new RuntimeException("非法输入！！！");
        }

        int value = chess[x][y];

        // 检查行
        for (int i=0; i<chessSize; i++) {
            if (i != x && chess[i][y] == value) {
                return false;
            }
        }
        // 检查列
        for(int j=0; j<chessSize; j++) {
            if (j != y && chess[x][j] == value) {
                return false;
            }
        }

        // 检查九宫格
        int len = 3;
        for (int i=(x/len)*len; i<(x/len)*len+len; i++) {
            for (int j=(y/len)*len; j<(y/len)*len+len; j++) {
                if (i!=x && j!=y && chess[i][j] == value) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 采用回溯法创造数独棋盘
     */
    private static void create() {
        int x, y;
        int[][] chess = new int[9][9];
        init(chess);
        // 从坐标(0,0)开始
        int k = 0;
        while (true) {
            x = k / 9;
            y = k % 9;
            while (true) {
                // 当前值+1
                chess[x][y]++;
                // 不断寻找，直到第一个适合的数
                if (chess[x][y] == 10) {
                    chess[x][y] = 0;
                    k--;
                    break;
                } else if (checkIsSafe(chess, x, y)) {
                    k++;
                    break;
                }
            }
            // 如果81个数都找到，退出循环
            if (k == 81) {
                for (int[] num : chess) {
                    System.out.println(Arrays.toString(num));
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        // TODO : 本题解法虽然可以生成数独矩阵，但有比较明显的缺点，可以
        // 也可换用书中解法 2 : 置换矩阵法(该方法较为简单就不做记录了)
        create();
    }
}
